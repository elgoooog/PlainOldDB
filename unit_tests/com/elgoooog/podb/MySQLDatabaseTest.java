package com.elgoooog.podb;

import com.elgoooog.podb.exception.MissingAnnotationException;
import org.junit.Test;

/**
 * @author Nicholas Hauschild
 *         Date: 5/8/11
 *         Time: 5:33 PM
 */
public class MySQLDatabaseTest {
    @Test(expected = MissingAnnotationException.class)
    public void createTest_noTableAnnotation() throws Exception {
        MySQLDatabase database = new MySQLDatabase();
        database.create(new Object());
    }
}
