package com.elgoooog.podb.loader;

import com.elgoooog.podb.model.Model;
import com.elgoooog.podb.test.AnotherPlanet;
import com.elgoooog.podb.test.JavaTypes;
import com.elgoooog.podb.test.Planet;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Nicholas Hauschild
 *         Date: 5/8/11
 *         Time: 8:04 PM
 */
public class ModelLoaderTest {
    @Test
    public void parseTest() throws Exception {
        ModelLoader modelLoader = new ModelLoader();
        List<String> packages = modelLoader.parse(new FileInputStream(new File("config/podb.xml")));
        assertEquals(1, packages.size());
        assertTrue(packages.contains("com.elgoooog.podb.test"));
    }

    @Test
    public void preLoadPackagesTest() throws Exception {
        ModelLoader modelLoader = new ModelLoader();
        List<Class<?>> classes = modelLoader.preLoadPackages(Collections.singletonList("com.elgoooog.podb.test"));
        assertEquals(3, classes.size());
        assertTrue(classes.contains(Planet.class));
        assertTrue(classes.contains(AnotherPlanet.class));
        assertTrue(classes.contains(JavaTypes.class));
    }

    @Test
    public void findClassesTest() throws Exception {
        ModelLoader modelLoader = new ModelLoader();
        List<Class<?>> classes = modelLoader.findClasses(
                new File("out/test/PlainOldDB/com/elgoooog/podb/test"), "com.elgoooog.podb.test");
        assertEquals(3, classes.size());
        assertTrue(classes.contains(Planet.class));
        assertTrue(classes.contains(AnotherPlanet.class));
        assertTrue(classes.contains(JavaTypes.class));
    }

    @Test
    public void loadClassTest() throws Exception {
        Model planetModel = ModelLoader.loadClass(Planet.class);
        assertEquals("Planet", planetModel.getTable());
        assertEquals(3, planetModel.getColumns().size());

        Model anotherPlanetModel = ModelLoader.loadClass(AnotherPlanet.class);
        assertEquals("Planet", anotherPlanetModel.getTable());
        assertEquals(3, anotherPlanetModel.getColumns().size());
    }
}
