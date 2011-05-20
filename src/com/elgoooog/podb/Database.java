package com.elgoooog.podb;

import java.util.Collection;

/**
 * @author Nicholas Hauschild
 *         Date: 5/4/11
 *         Time: 7:36 PM
 */
public interface Database {
    public static final String MYSQL = MySQLDatabase.DRIVER;

    void create(Object object);

    <T> Collection<T> read(Class<T> clazz);

    void update(Object object);

    void delete(Object object);
}
