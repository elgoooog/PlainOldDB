package com.elgoooog.podb.model;

import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 5/9/11
 *         Time: 7:14 AM
 */
public class Model {
    private String table;
    private List<Column> columns;

    public String getTable() {
        return table;
    }

    public void setTable(String _table) {
        table = _table;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> _columns) {
        columns = _columns;
    }

    public void addColumn(Column column) {
        columns.add(column);
    }
}
