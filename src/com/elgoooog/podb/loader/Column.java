package com.elgoooog.podb.loader;

import java.lang.reflect.Field;

/**
 * @author Nicholas Hauschild
 *         Date: 5/9/11
 *         Time: 7:24 AM
 */
public class Column {
    private String dbColumn;
    private Field field;
    private boolean isPrimaryKey;

    public Column(String _dbColumn, Field _field, boolean _isPrimaryKey) {
        dbColumn = _dbColumn;
        field = _field;
        isPrimaryKey = _isPrimaryKey;
    }

    public String getDbColumn() {
        return dbColumn;
    }

    public void setDbColumn(String _dbColumn) {
        dbColumn = _dbColumn;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field _field) {
        field = _field;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }
}
