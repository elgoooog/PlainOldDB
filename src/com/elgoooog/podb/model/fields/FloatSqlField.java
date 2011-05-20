package com.elgoooog.podb.model.fields;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/17/11
 *         Time: 11:11 PM
 */
public class FloatSqlField extends SqlField {
    private float value;

    public FloatSqlField(int index, Float _value) {
        super(index);
        value = _value;
    }

    @Override
    public void setOnStatement(PreparedStatement statement) throws SQLException {
        statement.setFloat(index, value);
    }
}
