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

    public TableModelContext loadConfiguration(String fileLocation) {
        return loadConfiguration(new File(fileLocation));
    }

    public TableModelContext loadConfiguration(File file) {
        try {
            return loadConfiguration(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not Found!", e);
        }
    }

    public TableModelContext loadConfiguration(InputStream stream) {
        Map<String, List<String>> config = parse(stream);
        List<Class<?>> classes = getClassesFromPackages(config.get("model"));
        List<Class<?>> bindings = getClassesFromPackages(config.get("binding"));

        Map<Class<?>, Model> models = new HashMap<Class<?>, Model>();
        for(Class<?> clazz : classes) {
            Model model = preLoadClass(clazz);
            models.put(clazz, model);
        }

        Map<Class<?>, Binding> bindingMap = new HashMap<Class<?>, Binding>();
        for(Class<?> clazz : bindings) {
            try {
                Binding binding = (Binding) clazz.newInstance();
                bindingMap.put(binding.getBindingClass(), binding);
            } catch(Exception e) {
                //continue on exceptions
            }
        }

        return new TableModelContext(models, bindingMap);
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
        String currentElement = "";
        Map<String, List<String>> config = new HashMap<String, List<String>>();
        Stack<String> currentXPath = new Stack<String>();

        while(streamReader.hasNext()) {
            int current = streamReader.next();
            switch(current) {
                case XMLStreamReader.START_ELEMENT:
                    currentElement = streamReader.getName().getLocalPart();
                    currentXPath.push(currentElement);
                    break;
                case XMLStreamReader.END_ELEMENT:
                    currentXPath.pop();
                    if(currentXPath.size() > 0) {
                        currentElement = currentXPath.peek();
                    }
                    break;
                case XMLStreamReader.CHARACTERS:
                    if(!"podb".equals(currentElement)) {
                        List<String> configValues = config.get(currentElement);
                        if(configValues == null) {
                            configValues = new ArrayList<String>();
                            config.put(currentElement, configValues);
                        }
                        configValues.add(streamReader.getText().trim());
                    }
                    break;
                default:
                    // do nothing
                    break;
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
