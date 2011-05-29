package com.elgoooog.podb.model.binding;

import com.elgoooog.podb.model.fields.FloatSqlField;
import com.elgoooog.podb.model.fields.SqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/26/11
 *         Time: 12:26 AM
 */
public class FloatBinding extends Binding {
    @Override
    public SqlField newSqlField(Object value, int index) {
        return new FloatSqlField(index, (Float) value);
    }

    @Override
    public Object getValue(ResultSet rs, String key) throws SQLException {
        return rs.getFloat(key);
    }

    @Override
    public Class<?> getBindingClass() {
        return float.class;
    }
}
