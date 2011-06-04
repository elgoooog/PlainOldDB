package com.elgoooog.podb.loader;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Nicholas Hauschild
 *         Date: 6/3/11
 *         Time: 12:12 AM
 */
public class ConfigurationParserTest {
    @Test
    public void parseTest() throws Exception {
        ConfigurationParser configParser = new ConfigurationParser();
        Map<String, List<String>> configMap = configParser.parse(new FileInputStream(new File("config/podb.xml")));
        List<String> modelPackages = configMap.get("model");
        assertEquals(1, modelPackages.size());
        assertTrue(modelPackages.contains("com.elgoooog.podb.test.objects"));

        List<String> bindingPackages = configMap.get("binding");
        assertEquals(2, bindingPackages.size());
        assertTrue(bindingPackages.contains("com.elgoooog.podb.test.binding"));
        assertTrue(bindingPackages.contains("com.elgoooog.podb.model.binding"));
    }
}
