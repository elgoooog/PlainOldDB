package com.elgoooog.podb.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 5/9/11
 *         Time: 9:44 PM
 */
public class AnnotationUtils {
    private AnnotationUtils() {
        //
    }

    public static Annotations getAnnotations(AnnotatedElement annotatedElement) {
        Annotations annotations = new Annotations();
        for(Annotation annotation : annotatedElement.getAnnotations()) {
            annotations.put(annotation.annotationType(), annotation);
        }
        return annotations;
    }

    public static class Annotations {
        private Map<Class<? extends Annotation>, Annotation> annotations = new HashMap<Class<? extends Annotation>, Annotation>();

        private void put(Class<? extends Annotation> annotationClass, Annotation annotation) {
            annotations.put(annotationClass, annotation);
        }

        public int size() {
            return annotations.size();
        }

        @SuppressWarnings("unchecked")
        public <T extends Annotation> T get(Class<T> clazz) {
            return (T) annotations.get(clazz);
        }
    }
}
