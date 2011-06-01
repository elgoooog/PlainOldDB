package com.elgoooog.podb.model.binding;

import com.elgoooog.podb.Database;
import com.elgoooog.podb.model.fields.BooleanSqlField;
import com.elgoooog.podb.model.fields.SqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/26/11
 *         Time: 12:20 AM
 */
public class BooleanBinding extends Binding {
    @Override
    public SqlField newSqlField(Object value, int index) {
        return new BooleanSqlField(index, (Boolean) value);
    }

    @Override
    public Object getValue(ResultSet rs, String key, Database database) throws SQLException {
        return rs.getBoolean(key);
    }

    @Override
    public Class<?> getBindingClass() {
        return boolean.class;
    }
}
