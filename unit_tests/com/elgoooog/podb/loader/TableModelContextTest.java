package com.elgoooog.podb.loader;

import com.elgoooog.podb.model.Model;
import com.elgoooog.podb.test.objects.AnotherPlanet;
import com.elgoooog.podb.test.objects.Planet;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Nicholas Hauschild
 *         Date: 5/12/11
 *         Time: 11:54 PM
 */
public class TableModelContextTest {
    @Test
    public void getModelTest_object() throws Exception {
        TableModelContext context = new TableModelContext();
        Model model = context.getModel(new Planet());
        assertNotNull(model);
        assertEquals("Planet", model.getTable());
    }

    @Test
    public void getModelTest_class() throws Exception {
        TableModelContext context = new TableModelContext();
        Model model = context.getModel(AnotherPlanet.class);
        assertNotNull(model);
        assertEquals("Planet", model.getTable());
    }
}
