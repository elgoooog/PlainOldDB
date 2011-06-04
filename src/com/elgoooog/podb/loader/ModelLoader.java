package com.elgoooog.podb.loader;

import com.elgoooog.podb.model.Model;
import com.elgoooog.podb.model.ModelHelper;
import com.elgoooog.podb.model.binding.Binding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 5/8/11
 *         Time: 5:20 PM
 */
public class ModelLoader {
    private static ModelHelper modelHelper = new ModelHelper();
    private ConfigurationParser configParser;
    private PackageReader packageReader;

    public ModelLoader(final ConfigurationParser _configParser, PackageReader _packageReader) {
        configParser = _configParser;
        packageReader = _packageReader;
    }

    public PodbContext loadConfiguration(String fileLocation) {
        return loadConfiguration(new File(fileLocation));
    }

    public PodbContext loadConfiguration(File file) {
        try {
            return loadConfiguration(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not Found!", e);
        }
    }

    public PodbContext loadConfiguration(InputStream stream) {
        Map<String, List<String>> config = configParser.parse(stream);
        List<Class<?>> classes = packageReader.getClassesFromPackages(config.get("model"));
        List<Class<?>> bindings = packageReader.getClassesFromPackages(config.get("binding"));

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

        return new PodbContext(models, bindingMap);
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
