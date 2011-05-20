package com.elgoooog.podb.model.fields;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/17/11
 *         Time: 11:26 PM
 */
public class BooleanSqlField extends SqlField {
    private boolean value;

    public BooleanSqlField(int index, Boolean _value) {
        super(index);
        value = _value;
    }

    @Override
    public void setOnStatement(PreparedStatement statement) throws SQLException {
        statement.setBoolean(index, value);
    }
}
