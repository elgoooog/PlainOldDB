package com.elgoooog.podb;

import com.elgoooog.podb.annotation.Column;
import com.elgoooog.podb.loader.TableModelContext;
import com.elgoooog.podb.model.Model;
import com.elgoooog.podb.model.SqlData;
import com.elgoooog.podb.model.binding.Binding;
import com.elgoooog.podb.model.fields.SqlField;
import com.elgoooog.podb.util.PropertyLoader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @author Nicholas Hauschild
 *         Date: 5/4/11
 *         Time: 7:38 PM
 */
public class MySQLDatabase implements Database {
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final PropertyLoader __PROPERTY_LOADER = new PropertyLoader();
    static final String CONNECTION_STRING = System.getProperty("podb.db.connectionString");
    static final String USERNAME = System.getProperty("podb.db.username");
    static final String PASSWORD = System.getProperty("podb.db.password");

    private TableModelContext tableModelContext;

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't find JDBC driver " + DRIVER, e);
        }
    }

    public MySQLDatabase() {
        tableModelContext = new TableModelContext();
    }

    public MySQLDatabase(TableModelContext context) {
        tableModelContext = context;
    }

    protected Model getModel(Object crudObject) {
        return getModel(crudObject.getClass());
    }

    protected Model getModel(Class<?> clazz) {
        return tableModelContext.getModel(clazz);
    }

    public void create(Object crudObject) {
        Model model = getModel(crudObject);
        SqlData sqlData = createSql(model, crudObject);
        executeSql(sqlData);
    }

    public <T> Collection<T> read(Class<T> clazz) {
        Model model = getModel(clazz);
        return executeRead(model, clazz);
    }

    public void update(Object crudObject) {
        Model model = getModel(crudObject);
        SqlData sqlData = updateSql(model, crudObject);
        executeSql(sqlData);
    }

    public void delete(Object crudObject) {
        Model model = getModel(crudObject);
        SqlData sqlData = deleteSql(model, crudObject);
        executeSql(sqlData);
    }

    protected void executeSql(SqlData sqlData) {
        System.out.println(sqlData.getSql());
        Connection conn = getConnection();
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement(sqlData.getSql());
            for(SqlField sqlField : sqlData.getSqlFields()) {
                sqlField.setOnStatement(statement);
            }
            statement.execute();
        } catch(SQLException e) {
            throw new RuntimeException("Failed to execute sql", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                conn.close();
            } catch (SQLException e) {
                // tough luck
            }
        }
    }

    protected <T> Collection<T> executeRead(Model model, Class<T> clazz) {
        Connection conn = getConnection();
        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(readSql(model));
            return processResultSet(rs, clazz);
        } catch(SQLException e) {
            throw new RuntimeException("Failed to execute sql", e);
        } catch(Exception e) {
            throw new RuntimeException("Failed to process results", e);
        }
        finally {
            try {
                if (rs != null) {
					rs.close();
				}
                if (statement != null) {
                    statement.close();
                }
                conn.close();
            } catch (SQLException e) {
                // tough luck
            }
        }
    }

    protected <T> Collection<T> processResultSet(ResultSet rs, Class<T> clazz) throws Exception {
        Set<T> results = new HashSet<T>();

        while(rs.next()) {
            Constructor<T> constructor = clazz.getConstructor();
            T t = constructor.newInstance();

            Map<String, Field> columns = new HashMap<String, Field>();
            for(Field field : clazz.getDeclaredFields()) {
                Annotation annotation = field.getAnnotation(Column.class);
                if(annotation == null) {
                    continue;
                }

                String columnName = ((Column) annotation).name();
                if("".equals(columnName)) {
                    columnName= field.getName();
                }

                columns.put(columnName, field);
            }

            for(Map.Entry<String, Field> entry : columns.entrySet()) {
                Field field = entry.getValue();
                field.setAccessible(true);
                Class<?> type = field.getType();
                Binding binding = Binding.getBinding(type);
                field.set(t, binding.getValue(rs, entry.getKey(), this));
            }
            results.add(t);
        }

        return results;
    }

    protected Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't get connection to database", e);
        }
        return conn;
    }

    protected SqlData createSql(Model model, Object crudObject) {
        SqlData sqlData = new SqlData();
        List<com.elgoooog.podb.model.Column> columns = model.getColumns();
        StringBuilder builder = new StringBuilder("insert into ");
        builder.append(model.getTable());
        builder.append(" (");
        int columnCount = 0;
        for(com.elgoooog.podb.model.Column column : columns) {
            if(!column.isPrimaryKey() || !column.isAutoIncrement()) {
                builder.append(column.getDbColumn()).append(',');
                ++columnCount;
                addField(sqlData, column.getField(), crudObject, columnCount);
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(") values (");
        for(int i = 0; i < columnCount; ++i) {
            builder.append("?,");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(");");
        sqlData.setSql(builder.toString());
        return sqlData;
    }

    protected String readSql(Model model) {
        StringBuilder builder = new StringBuilder("select * from ");
        builder.append(model.getTable());
        builder.append(';');
        return builder.toString();
    }

    protected SqlData updateSql(Model model, Object crudObject) {
        SqlData sqlData = new SqlData();
        List<com.elgoooog.podb.model.Column> columns = model.getColumns();
        StringBuilder builder = new StringBuilder("update ");
        builder.append(model.getTable());
        builder.append(" set ");
        int columnIndex = 1;
        for(com.elgoooog.podb.model.Column column : columns) {
            builder.append(column.getDbColumn()).append("=?,");
            Field f = column.getField();
            addField(sqlData, f, crudObject, columnIndex);
            ++columnIndex;
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(" where ");
        builder.append(buildEqualsPrimaryKeyString(model, crudObject, sqlData));
        builder.append(';');
        sqlData.setSql(builder.toString());
        return sqlData;
    }

    protected SqlData deleteSql(Model model, Object crudObject) {
        SqlData sqlData = new SqlData();
        String equalsPrimaryKeyString = buildEqualsPrimaryKeyString(model, crudObject, sqlData);
        StringBuilder builder = new StringBuilder("delete from ");
        builder.append(model.getTable());
        builder.append(" where ");
        builder.append(equalsPrimaryKeyString);
        builder.append(';');
        sqlData.setSql(builder.toString());
        return sqlData;
    }

    protected void addField(SqlData sqlData, Field field, Object crudObject, int index) {
        field.setAccessible(true);
        Class<?> type = field.getType();
        try {
            Binding binding = Binding.getBinding(type);
            sqlData.addSqlField(binding.newSqlField(field.get(crudObject), index));
        } catch(IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void addField(SqlData sqlData, Field field, Object crudObject) {
        int index = sqlData.getSqlFields().size() + 1;
        addField(sqlData, field, crudObject, index);
    }

    protected String buildEqualsPrimaryKeyString(Model model, Object crudObject, SqlData sqlData) {
        List<com.elgoooog.podb.model.Column> columns = model.getColumns();
        for(com.elgoooog.podb.model.Column column : columns) {
            if(!column.isPrimaryKey()) {
                continue;
            }

            Field f = column.getField();
            addField(sqlData, f, crudObject);

            return column.getDbColumn() + "=?";
        }
        return "";
    }
}
