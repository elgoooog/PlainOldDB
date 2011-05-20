package com.elgoooog.podb.loader;

import com.elgoooog.podb.model.Model;
import com.elgoooog.podb.test.AnotherPlanet;
import com.elgoooog.podb.test.JavaTypes;
import com.elgoooog.podb.test.Planet;
import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Nicholas Hauschild
 *         Date: 5/13/11
 *         Time: 12:21 AM
 */
public class ModelLoaderIntegrationTest {
    @Test
    public void preLoadTest() throws Exception {
        ModelLoader loader = new ModelLoader();
        TableModelContext context = loader.preLoad("config/podb.xml");

        Map<Class<?>, Model> modelMap = context.getModelMap();
        assertEquals(3, modelMap.size());

        assertTrue(modelMap.containsKey(Planet.class));
        assertTrue(modelMap.containsKey(AnotherPlanet.class));
        assertTrue(modelMap.containsKey(JavaTypes.class));

        assertEquals("Planet", modelMap.get(Planet.class).getTable());
        assertEquals("Planet",modelMap.get(AnotherPlanet.class).getTable());
        assertEquals("JavaTypes", modelMap.get(JavaTypes.class).getTable());
    }
}
