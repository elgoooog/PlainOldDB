package com.elgoooog.podb.model.binding;

import com.elgoooog.podb.model.fields.ByteArraySqlField;
import com.elgoooog.podb.model.fields.SqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/26/11
 *         Time: 12:22 AM
 */
public class ByteArrayBinding extends Binding {
    @Override
    public SqlField newSqlField(Object value, int index) {
        return new ByteArraySqlField(index, (byte[]) value);
    }

    @Override
    public Object getValue(ResultSet rs, String key) throws SQLException {
        return rs.getBytes(key);
    }

    @Override
    public Class<?> getBindingClass() {
        return byte[].class;
    }
}
