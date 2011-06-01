package com.elgoooog.podb.model.binding;

import com.elgoooog.podb.Database;
import com.elgoooog.podb.model.fields.ShortSqlField;
import com.elgoooog.podb.model.fields.SqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/26/11
 *         Time: 12:27 AM
 */
public class ShortBinding extends Binding {
    @Override
    public SqlField newSqlField(Object value, int index) {
        return new ShortSqlField(index, (Short) value);
    }

    @Override
    public Object getValue(ResultSet rs, String key, Database database) throws SQLException {
        return rs.getShort(key);
    }

    @Override
    public Class<?> getBindingClass() {
        return short.class;
    }
}
