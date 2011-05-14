package com.elgoooog.podb.loader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author Nicholas Hauschild
 *         Date: 5/8/11
 *         Time: 5:19 PM
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
        List<String> packages = parse(stream);
        List<Class<?>> classes = preLoadPackages(packages);

        Map<Class<?>, Model> models = new HashMap<Class<?>, Model>();
        for(Class<?> clazz : classes) {
            Model model = preLoadClass(clazz);
            models.put(clazz, model);
        }

        return new TableModelContext(models);
    }

    protected List<String> parse(InputStream stream) {
        XMLStreamReader streamReader = null;
        List<String> packages = null;
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            streamReader = inputFactory.createXMLStreamReader(stream);

            packages = parse(streamReader);
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
        return packages;
    }

    protected List<String> parse(XMLStreamReader streamReader) throws XMLStreamException {
        String currentElement;
        List<String> packages = new ArrayList<String>();

        while(streamReader.hasNext()) {
            switch(streamReader.next()) {
                case XMLStreamReader.START_ELEMENT:
                    currentElement = streamReader.getName().getLocalPart();
                    if("package".equalsIgnoreCase(currentElement)) {
                        packages.add(streamReader.getElementText());
                    }
                default:
                // do nothing
            }
        }
        return packages;
    }

    protected List<Class<?>> preLoadPackages(List<String> packages) {
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
