package com.elgoooog.podb;

import com.elgoooog.podb.exception.MissingAnnotationException;
import com.elgoooog.podb.loader.Column;
import com.elgoooog.podb.loader.Model;
import com.elgoooog.podb.loader.TableModelContext;
import com.elgoooog.podb.test.AnotherPlanet;
import com.elgoooog.podb.test.Planet;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Nicholas Hauschild
 *         Date: 5/8/11
 *         Time: 5:33 PM
 */
public class MySQLDatabaseTest {
    @Test
    public void getModelTest_object() throws Exception {
        Map<Class<?>, Model> modelMap = new HashMap<Class<?>, Model>();
        modelMap.put(Planet.class, new Model());
        TableModelContext context = new TableModelContext(modelMap);
        MySQLDatabase database = new MySQLDatabase(context);
        assertNotNull(database.getModel(new Planet()));
        assertNotNull(database.getModel(new AnotherPlanet()));
    }

    @Test
    public void getModelTest_class() throws Exception {
        Map<Class<?>, Model> modelMap = new HashMap<Class<?>, Model>();
        modelMap.put(AnotherPlanet.class, new Model());
        TableModelContext context = new TableModelContext(modelMap);
        MySQLDatabase database = new MySQLDatabase(context);
        assertNotNull(database.getModel(AnotherPlanet.class));
        assertNotNull(database.getModel(Planet.class));
    }

    @Test(expected = MissingAnnotationException.class)
    public void getModelTest_notAnnotatedProperly() throws Exception {
        MySQLDatabase database = new MySQLDatabase();
        database.getModel(Object.class);
    }

    @Test
    public void createSqlTest() throws Exception {
        Field blah = Planet.class.getDeclaredField("name");
        Field frog = Planet.class.getDeclaredField("radius");
        Field blog = Planet.class.getDeclaredField("population");

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("blah", blah, false));
        columns.add(new Column("frog", frog, false));
        columns.add(new Column("blog", blog, false));

        Planet planet = new Planet("halb", 123, 456);

        Model model = new Model();
        model.setColumns(columns);
        model.setTable("bomb");

        MySQLDatabase database = new MySQLDatabase();
        String createSql = database.createSql(model, planet);

        assertEquals("insert into bomb (blah,frog,blog) values ('halb','123','456');", createSql);
    }

    @Test
    public void readSqlTest() throws Exception {
        Model model = new Model();
        model.setTable("bomb");

        MySQLDatabase database = new MySQLDatabase();
        String readSql = database.readSql(model);

        assertEquals("select * from bomb;", readSql);
    }

    @Test
    public void updateSqlTest() throws Exception {
        Field blah = Planet.class.getDeclaredField("name");
        Field frog = Planet.class.getDeclaredField("radius");
        Field blog = Planet.class.getDeclaredField("population");

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("blah", blah, false));
        columns.add(new Column("frog", frog, true));
        columns.add(new Column("blog", blog, false));

        Planet planet = new Planet("halb", 123, 456);

        Model model = new Model();
        model.setColumns(columns);
        model.setTable("bomb");

        MySQLDatabase database = new MySQLDatabase();
        String updateSql = database.updateSql(model, planet);

        assertEquals("update bomb set blah='halb',frog='123',blog='456' where frog='123';", updateSql);
    }

    @Test
    public void deleteSqlTest() throws Exception {
        Field blah = Planet.class.getDeclaredField("name");
        Field frog = Planet.class.getDeclaredField("radius");
        Field blog = Planet.class.getDeclaredField("population");

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("blah", blah, false));
        columns.add(new Column("frog", frog, false));
        columns.add(new Column("blog", blog, true));

        Planet planet = new Planet("halb", 123, 456);

        Model model = new Model();
        model.setColumns(columns);
        model.setTable("bomb");

        MySQLDatabase database = new MySQLDatabase();
        String deleteSql = database.deleteSql(model, planet);

        assertEquals("delete from bomb where blog='456';", deleteSql);
    }

    @Test
    public void buildColumnStringTest() throws Exception {
        Field blah = Planet.class.getDeclaredField("name");
        Field frog = Planet.class.getDeclaredField("radius");
        Field blog = Planet.class.getDeclaredField("population");

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("blah", blah, false));
        columns.add(new Column("frog", frog, false));
        columns.add(new Column("blog", blog, false));

        Planet planet = new Planet("halb", 123, 456);

        Model model = new Model();
        model.setColumns(columns);

        MySQLDatabase database = new MySQLDatabase();
        String columnString = database.buildColumnString(model, planet);

        assertEquals("(blah,frog,blog) values ('halb','123','456')", columnString);
    }

    @Test
    public void buildColumnUpdateStringTest() throws Exception {
        Field blah = Planet.class.getDeclaredField("name");
        Field frog = Planet.class.getDeclaredField("radius");
        Field blog = Planet.class.getDeclaredField("population");

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("blah", blah, false));
        columns.add(new Column("frog", frog, false));
        columns.add(new Column("blog", blog, false));

        Planet planet = new Planet("halb", 123, 456);

        Model model = new Model();
        model.setColumns(columns);

        MySQLDatabase database = new MySQLDatabase();
        String columnUpdateString = database.buildColumnUpdateString(model, planet);

        assertEquals("blah='halb',frog='123',blog='456'", columnUpdateString);
    }

    @Test
    public void buildEqualsPrimaryKeyStringTest() throws Exception {
        Field frog = Planet.class.getDeclaredField("radius");
        Field blah = Planet.class.getDeclaredField("name");

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("blah", blah, false));
        columns.add(new Column("frog", frog, true));

        Planet planet = new Planet("halb", 123, 456);

        Model model = new Model();
        model.setColumns(columns);

        MySQLDatabase database = new MySQLDatabase();
        String equalsPrimaryKeyString = database.buildEqualsPrimaryKeyString(model, planet);

        assertEquals("frog='123'", equalsPrimaryKeyString);
    }
}
