package com.elgoooog.podb.model.binding;

import com.elgoooog.podb.Database;
import com.elgoooog.podb.model.fields.SqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/26/11
 *         Time: 12:11 AM
 */
public abstract class Binding {
    public abstract SqlField newSqlField(Object value, int index);

    public abstract Object getValue(ResultSet rs, String key, Database database) throws SQLException;

    public abstract Class<?> getBindingClass();
}
