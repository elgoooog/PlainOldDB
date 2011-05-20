package com.elgoooog.podb.model.fields;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/17/11
 *         Time: 11:27 PM
 */
public class ShortSqlField extends SqlField {
    private short value;

    public ShortSqlField(int index, Short _value) {
        super(index);
        value = _value;
    }

    @Override
    public void setOnStatement(PreparedStatement statement) throws SQLException {
        statement.setShort(index, value);
    }
}
