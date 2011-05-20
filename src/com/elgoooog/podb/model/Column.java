package com.elgoooog.podb.model;

import java.lang.reflect.Field;

/**
 * @author Nicholas Hauschild
 *         Date: 5/9/11
 *         Time: 7:25 AM
 */
public class Column {
    private String dbColumn;
    private Field field;
    private boolean isPrimaryKey;
    private boolean isAutoIncrement;

    public Column(String _dbColumn, Field _field, boolean _isPrimaryKey, boolean _isAutoIncrement) {
        dbColumn = _dbColumn;
        field = _field;
        isPrimaryKey = _isPrimaryKey;
        isAutoIncrement = _isAutoIncrement;
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

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        } else if(other instanceof Column) {
            Column o = (Column) other;
            return dbColumn.equals(o.dbColumn) && field.equals(o.field) && isPrimaryKey == o.isPrimaryKey
                    && isAutoIncrement == o.isAutoIncrement;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return dbColumn.hashCode() + field.hashCode();
    }
}
