package com.elgoooog.podb.loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 6/3/11
 *         Time: 12:07 AM
 */
public class PackageReader {
    public List<Class<?>> getClassesFromPackages(List<String> packages) {
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
}
