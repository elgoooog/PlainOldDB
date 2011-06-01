package com.elgoooog.podb.model.binding;

import com.elgoooog.podb.Database;
import com.elgoooog.podb.model.fields.ByteSqlField;
import com.elgoooog.podb.model.fields.SqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/26/11
 *         Time: 12:23 AM
 */
public class ByteBinding extends Binding {
    @Override
    public SqlField newSqlField(Object value, int index) {
        return new ByteSqlField(index, (Byte) value);
    }

    @Override
    public Object getValue(ResultSet rs, String key, Database database) throws SQLException {
        return rs.getByte(key);
    }

    @Override
    public Class<?> getBindingClass() {
        return byte.class;
    }
}
