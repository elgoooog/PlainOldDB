package com.elgoooog.podb.model;

import com.elgoooog.podb.model.fields.SqlField;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 5/17/11
 *         Time: 12:48 AM
 */
public class SqlData {
    private String sql;
    private List<SqlField> sqlFields = new ArrayList<SqlField>();

    public void addSqlField(SqlField field) {
        sqlFields.add(field);
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String _sql) {
        sql = _sql;
    }

    public List<SqlField> getSqlFields() {
        return sqlFields;
    }
}
