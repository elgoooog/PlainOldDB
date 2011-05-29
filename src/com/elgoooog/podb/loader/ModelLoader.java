package com.elgoooog.podb.loader;

import com.elgoooog.podb.model.Model;
import com.elgoooog.podb.model.ModelHelper;
import com.elgoooog.podb.model.binding.Binding;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author Nicholas Hauschild
 *         Date: 5/8/11
 *         Time: 5:20 PM
 */
public class ModelLoader {
    private static ModelHelper modelHelper = new ModelHelper();

    public TableModelContext preLoad(String fileLocation) {
        return preLoad(new File(fileLocation));
    }

    public TableModelContext preLoad(File file) {
        try {
            return preLoad(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not Found!", e);
        }
    }

    public TableModelContext preLoad(InputStream stream) {
       Map<String, List<String>> config = parse(stream);
        List<Class<?>> classes = getClassesFromPackages(config.get("package"));
        List<Class<?>> bindings = getClassesFromPackages(config.get("binding"));
        loadBindings(bindings);

        Map<Class<?>, Model> models = new HashMap<Class<?>, Model>();
        for(Class<?> clazz : classes) {
            Model model = preLoadClass(clazz);
            models.put(clazz, model);
        }

        return new TableModelContext(models);
    }

    protected Map<String, List<String>> parse(InputStream stream) {
        XMLStreamReader streamReader = null;
        Map<String, List<String>> config = null;
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            streamReader = inputFactory.createXMLStreamReader(stream);

            config = parse(streamReader);
        } catch (XMLStreamException e) {
            throw new RuntimeException("failed to parse stream", e);
        } finally {
            try {
                if (streamReader != null) {
                    streamReader.close();
                }
            } catch (Exception e) {
                // tough luck
            }
        }
        return config;
    }

    protected Map<String, List<String>> parse(XMLStreamReader streamReader) throws XMLStreamException {
        String currentElement;
        Map<String, List<String>> config = new HashMap<String, List<String>>();
        List<String> packages = new ArrayList<String>();
        List<String> bindingsPackages = new ArrayList<String>();
        config.put("package", packages);
        config.put("binding", bindingsPackages);

        while(streamReader.hasNext()) {
            switch(streamReader.next()) {
                case XMLStreamReader.START_ELEMENT:
                    currentElement = streamReader.getName().getLocalPart();
                    if("package".equalsIgnoreCase(currentElement)) {
                        packages.add(streamReader.getElementText());
                    }
                    else if("binding".equalsIgnoreCase(currentElement)) {
                        bindingsPackages.add(streamReader.getElementText());
                    }
                default:
                // do nothing
            }
        }
        return config;
    }

    protected List<Class<?>> getClassesFromPackages(List<String> packages) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for(String packageName : packages) {
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = null;
            try {
                resources = loader.getResources(path);
            } catch(IOException e) {
                System.err.println("Failed to find classes in package: " + packageName);
            }

            if(resources != null) {
                while(resources.hasMoreElements()) {
                    URL resource = resources.nextElement();
                    File directory = new File(resource.getFile());
                    classes.addAll(findClasses(directory, packageName));
                }
            }
        }
        return classes;
    }

    protected List<Class<?>> findClasses(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if(!directory.exists()) {
            return classes;
        }
        for(File file : directory.listFiles()) {
            if(file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else {
                String clazz = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Class.forName(clazz));
                } catch(ClassNotFoundException e) {
                    System.err.println("Failed to find class: " + clazz);
                }
            }
        }
        return classes;
    }

    protected void loadBindings(List<Class<?>> bindings) {
        for(Class<?> bindingClazz : bindings) {
            try {
                Binding binding = (Binding) bindingClazz.newInstance();
                Binding.addBinding(binding);
            } catch(Exception e) {
                //continue on exceptions
            }
        }
    }

    protected Model preLoadClass(Class<?> clazz) {
        return loadClass(clazz);
    }

    public static Model loadClass(Class<?> clazz) {
        Model model = new Model();
        model.setTable(modelHelper.getTable(clazz));
        model.setColumns(modelHelper.getColumns(clazz));
        return model;
    }
}
