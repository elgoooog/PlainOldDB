package com.elgoooog.podb;

import java.util.Collection;

/**
 * @author Nicholas Hauschild
 *         Date: 5/4/11
 *         Time: 7:35 PM
 */
public interface Database {
    public static final String MYSQL = MySQLDatabase.DRIVER;

    void create(Object crudObject);

    <T> Collection<T> read(Class<T> clazz);

    void update(Object crudObject);

    void delete(Object crudObject);
}
