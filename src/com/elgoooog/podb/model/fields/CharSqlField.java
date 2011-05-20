package com.elgoooog.podb.model.fields;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/17/11
 *         Time: 11:06 PM
 */
public class CharSqlField extends SqlField {
    private char value;

    public CharSqlField(int index, char _value) {
        super(index);
        value = _value;
    }

    @Override
    public void setOnStatement(PreparedStatement statement) throws SQLException {
        statement.setByte(index, (byte) value);
    }
}
