package com.elgoooog.podb.loader;

import com.elgoooog.podb.model.Model;
import com.elgoooog.podb.model.binding.Binding;
import com.elgoooog.podb.test.binding.HeldBinding;
import com.elgoooog.podb.test.objects.AnotherPlanet;
import com.elgoooog.podb.test.objects.Held;
import com.elgoooog.podb.test.objects.Planet;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Nicholas Hauschild
 *         Date: 5/8/11
 *         Time: 8:04 PM
 */
public class ModelLoaderTest {
    @Test
    public void loadClassTest() throws Exception {
        Model planetModel = ModelLoader.loadClass(Planet.class);
        assertEquals("Planet", planetModel.getTable());
        assertEquals(3, planetModel.getColumns().size());

        Model anotherPlanetModel = ModelLoader.loadClass(AnotherPlanet.class);
        assertEquals("Planet", anotherPlanetModel.getTable());
        assertEquals(3, anotherPlanetModel.getColumns().size());
    }

    @Test
    public void loadConfigurationTest() throws Exception {
        ModelLoader loader = new ModelLoader(new ConfigParserStub(), new PkgReaderStub());
        PodbContext context = loader.loadConfiguration("config/podb.xml");

        Map<Class<?>, Binding> bindingMap = context.getBindingsMap();
        Map<Class<?>, Model> modelMap = context.getModelMap();

        assertEquals(1, bindingMap.size());
        assertTrue(bindingMap.containsKey(Held.class));
        assertNotNull(bindingMap.get(Held.class));
        assertTrue(bindingMap.get(Held.class) instanceof HeldBinding);

        assertEquals(1, modelMap.size());
        assertTrue(modelMap.containsKey(Planet.class));
        assertNotNull(modelMap.get(Planet.class));
        assertEquals("Planet", modelMap.get(Planet.class).getTable());
    }

    private class ConfigParserStub extends ConfigurationParser {
        @Override
        public Map<String, List<String>> parse(InputStream is) {
            Map<String, List<String>> configMap = new HashMap<String, List<String>>();
            List<String> modelList = new ArrayList<String>();
            List<String> bindingList = new ArrayList<String>();

            modelList.add("model1");
            bindingList.add("binding1");

            configMap.put("model", modelList);
            configMap.put("binding", bindingList);

            return configMap;
        }
    }

    private class PkgReaderStub extends PackageReader {
        @Override
        public List<Class<?>> getClassesFromPackages(List<String> packages) {
            List<Class<?>> classes = new ArrayList<Class<?>>();
            if(packages.contains("model1")) {
                classes.add(Planet.class);
                return classes;
            } else if(packages.contains("binding1")) {
                classes.add(HeldBinding.class);
                return classes;
            } else {
                throw new RuntimeException("fail");
            }
        }
    }
}
