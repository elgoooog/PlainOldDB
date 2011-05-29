package com.elgoooog.podb.model.binding;

import com.elgoooog.podb.model.fields.CharSqlField;
import com.elgoooog.podb.model.fields.SqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/26/11
 *         Time: 12:23 AM
 */
public class CharBinding extends Binding {
    @Override
    public SqlField newSqlField(Object value, int index) {
        return new CharSqlField(index, (Character) value);
    }

    @Override
    public Object getValue(ResultSet rs, String key) throws SQLException {
        return (char) rs.getByte(key);
    }

    @Override
    public Class<?> getBindingClass() {
        return char.class;
    }
}
