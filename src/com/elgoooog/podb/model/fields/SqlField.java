package com.elgoooog.podb.model.fields;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Nicholas Hauschild
 *         Date: 5/17/11
 *         Time: 12:22 AM
 */
public abstract class SqlField {
    protected int index;

    public SqlField(int _index) {
        index = _index;
    }

    public int getIndex() {
        return index;
    }

    public abstract void setOnStatement(PreparedStatement statement) throws SQLException;
}
