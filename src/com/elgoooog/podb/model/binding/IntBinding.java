package com.elgoooog.podb.model.binding;

import com.elgoooog.podb.Database;
import com.elgoooog.podb.model.fields.IntSqlField;
import com.elgoooog.podb.model.fields.SqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/26/11
 *         Time: 12:11 AM
 */
public class IntBinding extends Binding {
    @Override
    public SqlField newSqlField(Object value, int index) {
        return new IntSqlField(index, (Integer)value);
    }

    @Override
    public Object getValue(ResultSet rs, String key, Database database) throws SQLException {
        return rs.getInt(key);
    }

    @Override
    public Class<?> getBindingClass() {
        return int.class;
    }
}
