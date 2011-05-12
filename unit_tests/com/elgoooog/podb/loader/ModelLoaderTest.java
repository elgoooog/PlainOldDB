package com.elgoooog.podb.loader;

import org.junit.Test;

import java.util.Collections;

/**
 * @author Nicholas Hauschild
 *         Date: 5/8/11
 *         Time: 8:09 PM
 */
public class ModelLoaderTest {
    @Test
    public void preLoadPackagesTest() throws Exception {
        ModelLoader modelLoader = new ModelLoader();
        modelLoader.preLoadPackages(Collections.singletonList("com.elgoooog.podb.test"));
    }
}
