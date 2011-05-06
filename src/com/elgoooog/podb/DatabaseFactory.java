package com.elgoooog.podb;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 5/5/11
 *         Time: 9:12 PM
 */
public class DatabaseFactory {
    private static Map<String, Database> databases = new HashMap<String, Database>();

    static {
        databases.put(MySQLDatabase.DRIVER, new MySQLDatabase());
    }

    public static Database getDatabase(String driverClass) {
        return databases.get(driverClass);
    }
}
