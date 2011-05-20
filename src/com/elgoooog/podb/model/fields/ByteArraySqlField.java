package com.elgoooog.podb.model.fields;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/17/11
 *         Time: 11:29 PM
 */
public class ByteArraySqlField extends SqlField {
    private byte[] value;

    public ByteArraySqlField(int index, byte[] _value) {
        super(index);
        value = _value;
    }

    @Override
    public void setOnStatement(PreparedStatement statement) throws SQLException {
        statement.setBinaryStream(index, new ByteArrayInputStream(value));
    }
}
