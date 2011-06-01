package com.elgoooog.podb.model.binding;

import com.elgoooog.podb.Database;
import com.elgoooog.podb.model.fields.SqlField;
import com.elgoooog.podb.model.fields.StringSqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/26/11
 *         Time: 12:28 AM
 */
public class StringBinding extends Binding {
    @Override
    public SqlField newSqlField(Object value, int index) {
        return new StringSqlField(index, (String) value);
    }

    @Override
    public Object getValue(ResultSet rs, String key, Database database) throws SQLException {
        return rs.getString(key);
    }

    @Override
    public Class<?> getBindingClass() {
        return String.class;
    }
}
