package com.elgoooog.podb;

import com.elgoooog.podb.test.AnotherPlanet;
import com.elgoooog.podb.test.Planet;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static junit.framework.Assert.assertEquals;

/**
 * @author Nicholas Hauschild
 *         Date: 5/4/11
 *         Time: 10:45 PM
 */
public class CrudObjectTest {
    private Database database;

    @Before
    public void initDatabase() throws Exception {
        database = DatabaseFactory.getDatabase(Database.MYSQL);
    }

    @Test
    public void testCreate_defaultNames() throws Exception {
        database.create(new Planet("Earth", 500, 1000));
    }

    @Test
    public void testCreate_givenNames() throws Exception {
        database.create(new AnotherPlanet("Mars", 200, 1));
    }

    @Test
    public void testRead() throws Exception {
        Collection<Planet> planets = database.read(Planet.class);
        assertEquals(2, planets.size());
        for(Object obj : planets) {
            System.out.println(obj);
        }
    }

    @Test
    public void testUpdate() throws Exception {
        database.update(new Planet("Earth", 500, 1000000));
        database.update(new AnotherPlanet("Mars", 300, 1));
    }

    @Test
    public void testDelete() throws Exception {
        database.delete(new Planet("Earth", 1,1));
        database.delete(new AnotherPlanet("Mars", 1,1));
    }
}
