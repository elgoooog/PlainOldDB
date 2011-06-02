package com.elgoooog.podb.loader;

import com.elgoooog.podb.model.Model;
import com.elgoooog.podb.test.objects.AnotherPlanet;
import com.elgoooog.podb.test.objects.JavaTypes;
import com.elgoooog.podb.test.objects.Planet;
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
        PodbContext context = loader.loadConfiguration("config/podb.xml");
        Map<Class<?>, Model> modelMap = context.getModelMap();

        assertEquals(5, modelMap.size());

        assertTrue(modelMap.containsKey(Planet.class));
        assertTrue(modelMap.containsKey(AnotherPlanet.class));
        assertTrue(modelMap.containsKey(JavaTypes.class));

        assertEquals("Planet", modelMap.get(Planet.class).getTable());
        assertEquals("Planet",modelMap.get(AnotherPlanet.class).getTable());
        assertEquals("JavaTypes", modelMap.get(JavaTypes.class).getTable());
    }
}
