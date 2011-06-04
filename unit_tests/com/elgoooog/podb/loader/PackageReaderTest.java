package com.elgoooog.podb.loader;

import com.elgoooog.podb.test.objects.AnotherPlanet;
import com.elgoooog.podb.test.objects.JavaTypes;
import com.elgoooog.podb.test.objects.Planet;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Nicholas Hauschild
 *         Date: 6/3/11
 *         Time: 12:17 AM
 */
public class PackageReaderTest {
    @Test
    public void preLoadPackagesTest() throws Exception {
        PackageReader packageReader = new PackageReader();
        List<Class<?>> classes = packageReader.getClassesFromPackages(Collections.singletonList("com.elgoooog.podb.test"));
        assertEquals(7, classes.size());
        assertTrue(classes.contains(Planet.class));
        assertTrue(classes.contains(AnotherPlanet.class));
        assertTrue(classes.contains(JavaTypes.class));
    }

    @Test
    public void findClassesTest() throws Exception {
        PackageReader packageReader = new PackageReader();
        List<Class<?>> classes = packageReader.findClasses(
                new File("out/test/PlainOldDB/com/elgoooog/podb/test"), "com.elgoooog.podb.test");
        assertEquals(7, classes.size());
        assertTrue(classes.contains(Planet.class));
        assertTrue(classes.contains(AnotherPlanet.class));
        assertTrue(classes.contains(JavaTypes.class));
    }
}
