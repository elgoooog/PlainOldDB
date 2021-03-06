package com.elgoooog.podb.loader;

import com.elgoooog.podb.model.Column;
import com.elgoooog.podb.model.ModelHelper;
import com.elgoooog.podb.test.objects.AnotherPlanet;
import com.elgoooog.podb.test.objects.Planet;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Nicholas Hauschild
 *         Date: 5/9/11
 *         Time: 9:14 PM
 */
public class ModelHelperTest {
    @Test
    public void getTableTest_object() throws Exception {
        ModelHelper helper = new ModelHelper();
        String table = helper.getTable(new Planet());
        assertEquals("Planet", table);
    }

    @Test
    public void getTableTest_class() throws Exception {
        ModelHelper helper = new ModelHelper();
        String table = helper.getTable(AnotherPlanet.class);
        assertEquals("Planet", table);
    }

    @Test
    public void getColumnsTest_object() throws Exception {
        ModelHelper helper = new ModelHelper();
        List<Column> columns = helper.getColumns(new Planet());

        assertEquals(3, columns.size());
        assertTrue(columns.contains(new Column("name", Planet.class.getDeclaredField("name"), true, false)));
        assertTrue(columns.contains(new Column("radius", Planet.class.getDeclaredField("radius"), false, false)));
        assertTrue(columns.contains(new Column("population", Planet.class.getDeclaredField("population"), false, false)));
    }

    @Test
    public void getColumnsTest_class() throws Exception {
        ModelHelper helper = new ModelHelper();
        List<Column> columns = helper.getColumns(AnotherPlanet.class);

        assertEquals(3, columns.size());
        assertTrue(columns.contains(new Column("name", AnotherPlanet.class.getDeclaredField("planetName"), true, false)));
        assertTrue(columns.contains(new Column("radius", AnotherPlanet.class.getDeclaredField("planetRadius"), false, false)));
        assertTrue(columns.contains(new Column("population", AnotherPlanet.class.getDeclaredField("planetPopulation"), false, false)));
    }
}
