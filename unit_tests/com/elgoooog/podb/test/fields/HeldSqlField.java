package com.elgoooog.podb.test.fields;

import com.elgoooog.podb.model.fields.SqlField;
import com.elgoooog.podb.test.objects.Held;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/29/11
 *         Time: 3:28 PM
 */
public class HeldSqlField extends SqlField {
    private Held value;

    public HeldSqlField(int index, Held _value) {
        super(index);
        value = _value;
    }

    @Override
    public void setOnStatement(PreparedStatement statement) throws SQLException {
        statement.setInt(index, value.getId());
    }
}
