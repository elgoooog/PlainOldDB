package com.elgoooog.podb.model;

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

            PrimaryKey primaryKey = annotations.get(PrimaryKey.class);
            boolean isPrimaryKey = primaryKey != null;
            boolean isAutoIncrement = isPrimaryKey && primaryKey.autoIncrement();

            String columnName = columnAnnotation.name();
            if("".equals(columnName)) {
                columnName= field.getName();
            }
            columns.add(new Column(columnName, field, isPrimaryKey, isAutoIncrement));
        }
        return columns;
    }
}
