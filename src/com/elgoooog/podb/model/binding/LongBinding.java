package com.elgoooog.podb.model.binding;

import com.elgoooog.podb.Database;
import com.elgoooog.podb.model.fields.LongSqlField;
import com.elgoooog.podb.model.fields.SqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/26/11
 *         Time: 12:27 AM
 */
public class LongBinding extends Binding {
    @Override
    public SqlField newSqlField(Object value, int index) {
        return new LongSqlField(index, (Long)value);
    }

    @Override
    public Object getValue(ResultSet rs, String key, Database database) throws SQLException {
        return rs.getLong(key);
    }

    @Override
    public Class<?> getBindingClass() {
        return long.class;
    }
}
