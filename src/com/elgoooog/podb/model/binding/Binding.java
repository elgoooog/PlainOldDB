package com.elgoooog.podb.model.binding;

import com.elgoooog.podb.model.fields.SqlField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 5/26/11
 *         Time: 12:11 AM
 */
public abstract class Binding {
    private static Map<Class<?>, Binding> bindings = new HashMap<Class<?>, Binding>();

    public abstract SqlField newSqlField(Object value, int index);

    public abstract Object getValue(ResultSet rs, String key) throws SQLException;

    public abstract Class<?> getBindingClass();

    public static Binding getBinding(Class<?> clazz) {
        return bindings.get(clazz);
    }

    public static void addBinding(Binding binding) {
        bindings.put(binding.getBindingClass(), binding);
    }
}
