package com.elgoooog.podb.util;

import com.elgoooog.podb.annotation.Column;
import com.elgoooog.podb.annotation.PrimaryKey;
import com.elgoooog.podb.test.objects.AnotherPlanet;
import com.elgoooog.podb.test.objects.Planet;
import org.junit.Test;

import java.lang.reflect.Field;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Nicholas Hauschild
 *         Date: 5/9/11
 *         Time: 9:45 PM
 */
public class AnnotationUtilsTest {
    @Test
    public void getAnnotationsTest_noAnnotations() throws Exception {
        Class<?> clazz = Class.class;

        AnnotationUtils.Annotations annotationMap = AnnotationUtils.getAnnotations(clazz);
        assertEquals(0, annotationMap.size());
    }

    @Test
    public void getAnnotationsTest_oneAnnotation_noVal() throws Exception {
        Class<Planet> planetClass = Planet.class;
        Field field = planetClass.getDeclaredField("radius");

        AnnotationUtils.Annotations annotationMap = AnnotationUtils.getAnnotations(field);
        assertEquals(1, annotationMap.size());
        Column annotation = annotationMap.get(Column.class);
        assertEquals("", annotation.name());
    }

    @Test
    public void getAnnotationsTest_oneAnnotation_withVal() throws Exception {
        Class<AnotherPlanet> planetClass = AnotherPlanet.class;
        Field field = planetClass.getDeclaredField("planetRadius");

        AnnotationUtils.Annotations annotationMap = AnnotationUtils.getAnnotations(field);
        assertEquals(1, annotationMap.size());
        Column annotation = annotationMap.get(Column.class);
        assertEquals("radius", annotation.name());
    }

    @Test
    public void getAnnotationsTest_twoAnnotation_noVal() throws Exception {
        Class<Planet> planetClass = Planet.class;
        Field field = planetClass.getDeclaredField("name");

        AnnotationUtils.Annotations annotationMap = AnnotationUtils.getAnnotations(field);
        assertEquals(2, annotationMap.size());
        Column columnAnnotation = annotationMap.get(Column.class);
        assertEquals("", columnAnnotation.name());
        PrimaryKey pkAnnotation = annotationMap.get(PrimaryKey.class);
        assertNotNull(pkAnnotation);
    }

    @Test
    public void getAnnotationsTest_twoAnnotation_withVal() throws Exception {
        Class<AnotherPlanet> planetClass = AnotherPlanet.class;
        Field field = planetClass.getDeclaredField("planetName");

        AnnotationUtils.Annotations annotationMap = AnnotationUtils.getAnnotations(field);
        assertEquals(2, annotationMap.size());
        Column columnAnnotation = annotationMap.get(Column.class);
        assertEquals("name", columnAnnotation.name());
        PrimaryKey pkAnnotation = annotationMap.get(PrimaryKey.class);
        assertNotNull(pkAnnotation);
    }
}
