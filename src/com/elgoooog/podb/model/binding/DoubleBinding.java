package com.elgoooog.podb.model.binding;

import com.elgoooog.podb.Database;
import com.elgoooog.podb.model.fields.DoubleSqlField;
import com.elgoooog.podb.model.fields.SqlField;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/26/11
 *         Time: 12:25 AM
 */
public class DoubleBinding extends Binding {
    @Override
    public SqlField newSqlField(Object value, int index) {
        return new DoubleSqlField(index, (Double)value);
    }

    @Override
    public Object getValue(ResultSet rs, String key, Database database) throws SQLException {
        return rs.getDouble(key);
    }

    @Override
    public Class<?> getBindingClass() {
        return double.class;
    }
}
