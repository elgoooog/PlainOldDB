package com.elgoooog.podb.model.fields;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/17/11
 *         Time: 12:23 AM
 */
public class IntSqlField extends SqlField {
    private int value;

    public IntSqlField(int index, int _value) {
        super(index);
        value = _value;
    }

    @Override
    public void setOnStatement(PreparedStatement statement) throws SQLException {
        statement.setInt(index, value);
    }
}
