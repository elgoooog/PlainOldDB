package com.elgoooog.podb;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * @author Nicholas Hauschild
 *         Date: 5/8/11
 *         Time: 5:29 PM
 */
public class DatabaseFactoryTest {
    @Test
    public void getDatabaseTest() throws Exception {
        Database database = DatabaseFactory.getDatabase(MySQLDatabase.DRIVER);
        assertTrue(database instanceof MySQLDatabase);
    }
}
