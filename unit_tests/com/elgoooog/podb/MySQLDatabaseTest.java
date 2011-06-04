package com.elgoooog.podb;

import com.elgoooog.podb.exception.MissingAnnotationException;
import com.elgoooog.podb.loader.ConfigurationParser;
import com.elgoooog.podb.loader.ModelLoader;
import com.elgoooog.podb.loader.PackageReader;
import com.elgoooog.podb.loader.PodbContext;
import com.elgoooog.podb.model.Column;
import com.elgoooog.podb.model.Model;
import com.elgoooog.podb.model.SqlData;
import com.elgoooog.podb.model.fields.IntSqlField;
import com.elgoooog.podb.model.fields.SqlField;
import com.elgoooog.podb.model.fields.StringSqlField;
import com.elgoooog.podb.test.objects.AnotherPlanet;
import com.elgoooog.podb.test.objects.Planet;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

import static junit.framework.Assert.*;

/**
 * @author Nicholas Hauschild
 *         Date: 5/8/11
 *         Time: 5:34 PM
 */
public class MySQLDatabaseTest {
    private PodbContext context;

    @Before
    public void setupBindings() throws Exception {
        ModelLoader loader = new ModelLoader(new ConfigurationParser(), new PackageReader());
        context = loader.loadConfiguration("config/podb.xml");
    }

    @Test
    public void getModelTest_object() throws Exception {
        Map<Class<?>, Model> modelMap = new HashMap<Class<?>, Model>();
        modelMap.put(Planet.class, new Model());
        PodbContext context = new PodbContext(modelMap, null);
        MySQLDatabase database = new MySQLDatabase(context);
        assertNotNull(database.getModel(new Planet()));
        assertNotNull(database.getModel(new AnotherPlanet()));
    }

    @Test
    public void getModelTest_class() throws Exception {
        Map<Class<?>, Model> modelMap = new HashMap<Class<?>, Model>();
        modelMap.put(AnotherPlanet.class, new Model());
        PodbContext context = new PodbContext(modelMap, null);
        MySQLDatabase database = new MySQLDatabase(context);
        assertNotNull(database.getModel(AnotherPlanet.class));
        assertNotNull(database.getModel(Planet.class));
    }

    @Test(expected = MissingAnnotationException.class)
    public void getModelTest_notAnnotatedProperly() throws Exception {
        MySQLDatabase database = new MySQLDatabase(context);
        database.getModel(Object.class);
    }

    @Test
    public void createSqlTest() throws Exception {
        Field blah = Planet.class.getDeclaredField("name");
        Field frog = Planet.class.getDeclaredField("radius");
        Field blog = Planet.class.getDeclaredField("population");

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("blah", blah, false, false));
        columns.add(new Column("frog", frog, false, false));
        columns.add(new Column("blog", blog, false, false));

        Planet planet = new Planet("halb", 123, 456);

        Model model = new Model();
        model.setColumns(columns);
        model.setTable("bomb");

        MySQLDatabase database = new MySQLDatabase(context);
        SqlData sqlData = database.createSql(model, planet);

        assertEquals("insert into bomb (blah,frog,blog) values (?,?,?);", sqlData.getSql());
        List<SqlField> sqlFields = sqlData.getSqlFields();
        assertEquals(3, sqlFields.size());

        Set<Class<?>> classes = new HashSet<Class<?>>();
        for(SqlField field : sqlFields) {
            classes.add(field.getClass());
        }

        assertEquals(2, classes.size());
        assertTrue(classes.contains(StringSqlField.class));
        assertTrue(classes.contains(IntSqlField.class));
    }

    @Test
    public void readSqlTest() throws Exception {
        Model model = new Model();
        model.setTable("bomb");

        MySQLDatabase database = new MySQLDatabase(context);
        String readSql = database.readSql(model);

        assertEquals("select * from bomb;", readSql);
    }

    @Test
    public void updateSqlTest() throws Exception {
        Field blah = Planet.class.getDeclaredField("name");
        Field frog = Planet.class.getDeclaredField("radius");
        Field blog = Planet.class.getDeclaredField("population");

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("blah", blah, false, false));
        columns.add(new Column("frog", frog, true, false));
        columns.add(new Column("blog", blog, false, false));

        Planet planet = new Planet("halb", 123, 456);

        Model model = new Model();
        model.setColumns(columns);
        model.setTable("bomb");

        MySQLDatabase database = new MySQLDatabase(context);
        SqlData sqlData = database.updateSql(model, planet);

        assertEquals("update bomb set blah=?,frog=?,blog=? where frog=?;", sqlData.getSql());
        List<SqlField> sqlFields = sqlData.getSqlFields();
        assertEquals(4, sqlFields.size());

        Set<Class<?>> classes = new HashSet<Class<?>>();
        for(SqlField field : sqlFields) {
            classes.add(field.getClass());
        }

        assertEquals(2, classes.size());
        assertTrue(classes.contains(StringSqlField.class));
        assertTrue(classes.contains(IntSqlField.class));
    }

    @Test
    public void deleteSqlTest() throws Exception {
        Field blah = Planet.class.getDeclaredField("name");
        Field frog = Planet.class.getDeclaredField("radius");
        Field blog = Planet.class.getDeclaredField("population");

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("blah", blah, false, false));
        columns.add(new Column("frog", frog, false, false));
        columns.add(new Column("blog", blog, true, false));

        Planet planet = new Planet("halb", 123, 456);

        Model model = new Model();
        model.setColumns(columns);
        model.setTable("bomb");

        MySQLDatabase database = new MySQLDatabase(context);
        SqlData sqlData = database.deleteSql(model, planet);

        assertEquals("delete from bomb where blog=?;", sqlData.getSql());
        List<SqlField> sqlFields = sqlData.getSqlFields();
        assertEquals(1, sqlFields.size());

        Set<Class<?>> classes = new HashSet<Class<?>>();
        for(SqlField field : sqlFields) {
            classes.add(field.getClass());
        }

        assertEquals(1, classes.size());
        assertTrue(classes.contains(IntSqlField.class));
    }

    @Test
    public void buildEqualsPrimaryKeyStringTest() throws Exception {
        Field frog = Planet.class.getDeclaredField("radius");
        Field blah = Planet.class.getDeclaredField("name");

        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("blah", blah, false, false));
        columns.add(new Column("frog", frog, true, false));

        Planet planet = new Planet("halb", 123, 456);

        Model model = new Model();
        model.setColumns(columns);

        MySQLDatabase database = new MySQLDatabase(context);
        String equalsPrimaryKeyString = database.buildEqualsPrimaryKeyString(model, planet, new SqlData());

        assertEquals("frog=?", equalsPrimaryKeyString);
    }
}
