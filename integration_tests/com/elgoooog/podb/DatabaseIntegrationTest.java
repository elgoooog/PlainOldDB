package com.elgoooog.podb;

import com.elgoooog.podb.model.binding.*;
import com.elgoooog.podb.test.*;
import com.elgoooog.podb.test.binding.HeldBinding;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Nicholas Hauschild
 *         Date: 5/4/11
 *         Time: 10:45 PM
 */
public class DatabaseIntegrationTest {
    private Database database;

    @Before
    public void initDatabase() throws Exception {
        Binding.addBinding(new IntBinding());
        Binding.addBinding(new LongBinding());
        Binding.addBinding(new FloatBinding());
        Binding.addBinding(new DoubleBinding());
        Binding.addBinding(new CharBinding());
        Binding.addBinding(new ByteBinding());
        Binding.addBinding(new ShortBinding());
        Binding.addBinding(new BooleanBinding());
        Binding.addBinding(new StringBinding());
        Binding.addBinding(new ByteArrayBinding());
        Binding.addBinding(new HeldBinding());
        database = new MySQLDatabase();
    }

    @Test
    public void createTest_defaultNames() throws Exception {
        database.create(new Planet("Earth", 500, 1000));
    }

    @Test
    public void createTest_givenNames() throws Exception {
        database.create(new AnotherPlanet("Mars", 200, 1));
    }

    @Test
    public void readTest() throws Exception {
        Collection<Planet> planets = database.read(Planet.class);
        assertEquals(2, planets.size());
    }

    @Test
    public void updateTest() throws Exception {
        database.update(new Planet("Earth", 500, 1000000));
        database.update(new AnotherPlanet("Mars", 300, 1));
    }

    @Test
    public void deleteTest() throws Exception {
        database.delete(new Planet("Earth", 1,1));
        database.delete(new AnotherPlanet("Mars", 1,1));
    }

    @Test
    public void testCreate_javaTypes() throws Exception {
        database.create(new JavaTypes(10000, 1000000000L, 3.14, 4.56f, (byte)26, true, 'a',
                (short)345, new byte[]{(byte)23, (byte)13}, "hello"));
    }

    @Test
    public void readTest_javaTypes() throws Exception {
        Collection<JavaTypes> javaTypess = database.read(JavaTypes.class);
        assertTrue(javaTypess.size() >= 1);
    }

    @Test
    public void testUpdate_javaTypes() throws Exception {
        JavaTypes javaTypes = new JavaTypes(12345, 123456789L, 4.13, 6.45f, (byte)-21, false, 'A', (short)112,
                new byte[]{(byte)15, (byte)7}, "hello world");
        javaTypes.setId(1);
        database.update(javaTypes);
    }

    @Test
    public void testDelete_javaTypes() throws Exception {
        JavaTypes javaTypes = new JavaTypes();
        javaTypes.setId(1);
        database.delete(javaTypes);
    }

    @Test
    public void testCreate_holder() {
        Held held = new Held(1, "held1");
        database.create(held);
        Holder holder = new Holder(1, "holder1", held);
        database.create(holder);
    }

    @Test
    public void testRead_holder() {
        Collection<Holder> holders = database.read(Holder.class);
        assertEquals(1, holders.size());
    }

    @Test
    public void testRead_checkHeldValues() {
        Collection<Holder> holders = database.read(Holder.class);
        assertEquals(1, holders.size());

        Holder holder = holders.iterator().next();
        assertEquals(1, holder.getId());
        assertEquals("holder1", holder.getName());

        Held held = holder.getHeld();
        assertEquals(1, held.getId());
        assertEquals("held1", held.getName());
    }

    @Test
    public void testUpdate_holder() {
        Held held = new Held(2, "held2");
        database.create(held);
        Holder holder = new Holder(1, "holder1", held);
        database.update(holder);
    }

    @Test
    public void testDelete_holder() {
        Held held = new Held(1, "held1");
        Holder holder = new Holder(1, "holder1", held);
        database.delete(holder);
        database.delete(held);
        database.delete(new Held(2, ""));
    }
}
