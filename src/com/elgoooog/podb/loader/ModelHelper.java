package com.elgoooog.podb.loader;

import com.elgoooog.podb.annotation.PrimaryKey;
import com.elgoooog.podb.annotation.Table;
import com.elgoooog.podb.exception.MissingAnnotationException;
import com.elgoooog.podb.util.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 5/9/11
 *         Time: 7:22 AM
 */
public class ModelHelper {
    public String getTable(Object object) {
        return getTable(object.getClass());
    }

    public String getTable(Class<?> clazz) {
        Annotation annotation = clazz.getAnnotation(Table.class);
        if(annotation == null) {
            throw new MissingAnnotationException(clazz, Table.class);
        }

        String tableName = ((Table) annotation).name();
        if("".equals(tableName)) {
            return clazz.getSimpleName();
        }
        return tableName;
    }

    public List<Column> getColumns(Object crudObject) {
        return getColumns(crudObject.getClass());
    }

    public List<Column> getColumns(Class<?> clazz) {
        List<Column> columns = new ArrayList<Column>();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            AnnotationUtils.Annotations annotations = AnnotationUtils.getAnnotations(field);
            com.elgoooog.podb.annotation.Column columnAnnotation = annotations.get(com.elgoooog.podb.annotation.Column.class);
            if(columnAnnotation == null) {
                continue;
            }

            boolean isPrimaryKey = annotations.get(PrimaryKey.class) != null;

            String columnName = columnAnnotation.name();
            if("".equals(columnName)) {
                columnName= field.getName();
            }
            columns.add(new Column(columnName, field, isPrimaryKey));
        }
        return columns;
    }

//    public String getColumnsString(Object crudObject) {
//        List<Column> columns = getColumns(crudObject);
//
//        StringBuilder builder = new StringBuilder("(");
//        for(Column column : columns) {
//            builder.append(column.getName()).append(',');
//        }
//        builder.deleteCharAt(builder.length() - 1);
//        builder.append(") values (");
//        for(Column column : columns) {
//            builder.append('\'').append(column.getValue()).append('\'').append(',');
//        }
//        builder.deleteCharAt(builder.length() - 1);
//        builder.append(")");
//        return builder.toString();
//    }

//    public String getColumnsUpdateString(Object crudObject) {
//        List<Column> columns = getColumns(crudObject);
//
//        StringBuilder builder = new StringBuilder();
//        for(Column column : columns) {
//            builder.append(column.getName()).append("='").append(column.getValue()).append("',");
//        }
//        builder.deleteCharAt(builder.length() - 1);
//        return builder.toString();
//    }

//    public String equalsPrimaryKey(Object crudObject) {
//        Field[] fields = crudObject.getClass().getDeclaredFields();
//        for(Field field : fields) {
//            Annotation pkAnnotation = field.getAnnotation(PrimaryKey.class);
//            if(pkAnnotation == null) {
//                continue;
//            }
//
//            Annotation columnAnnotation = field.getAnnotation(com.elgoooog.podb.annotation.Column.class);
//            if(columnAnnotation == null) {
//                continue;
//            }
//
//            field.setAccessible(true);
//            String value;
//            try {
//                Object val = field.get(crudObject);
//                value = val.toString();
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException("Couldn't access field " +field.getName(), e);
//            }
//
//            String columnName = ((com.elgoooog.podb.annotation.Column) columnAnnotation).name();
//
//            if("".equals(columnName)) {
//                return field.getName() + "='" +value+"'";
//            } else {
//                return columnName + "='" +value+"'";
//            }
//        }
//        return "";
//    }
}
