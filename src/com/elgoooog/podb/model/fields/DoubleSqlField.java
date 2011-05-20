package com.elgoooog.podb.model.fields;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/17/11
 *         Time: 11:12 PM
 */
public class DoubleSqlField extends SqlField {
    private double value;

    public DoubleSqlField(int index, Double _value) {
        super(index);
        value = _value;
    }

    @Override
    public void setOnStatement(PreparedStatement statement) throws SQLException {
        statement.setDouble(index, value);
    }
}
