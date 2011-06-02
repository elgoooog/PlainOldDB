package com.elgoooog.podb.loader;

import com.elgoooog.podb.model.Model;
import com.elgoooog.podb.model.binding.Binding;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 5/10/11
 *         Time: 7:23 AM
 */
public class TableModelContext {
    private Map<Class<?>, Model> modelMap;
    private Map<Class<?>, Binding> bindings;

    public TableModelContext() {
        modelMap = new HashMap<Class<?>, Model>();
        bindings = new HashMap<Class<?>, Binding>();
    }

    public TableModelContext(Map<Class<?>, Model> _modelMap, Map<Class<?>, Binding> _bindings) {
        modelMap = _modelMap;
        bindings = _bindings;
    }

    public Model getModel(Class<?> clazz) {
        Model model = modelMap.get(clazz);
        if(model == null) {
            model = ModelLoader.loadClass(clazz);
            modelMap.put(clazz, model);
        }
        return model;
    }

    public Model getModel(Object object) {
        return getModel(object.getClass());
    }

    public Map<Class<?>, Model> getModelMap() {
        return modelMap;
    }

    public Binding getBinding(Class<?> clazz) {
        return bindings.get(clazz);
    }

    public Map<Class<?>, Binding> getBindingsMap() {
        return bindings;
    }
}
