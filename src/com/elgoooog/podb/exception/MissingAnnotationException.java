package com.elgoooog.podb.exception;

/**
 * @author Nicholas Hauschild
 *         Date: 5/8/11
 *         Time: 5:40 PM
 */
public class MissingAnnotationException extends RuntimeException {
    public MissingAnnotationException(Class<?> clazz, Class<?> annotation) {
        super("Class " + clazz.getName() + " is missing annotation " + annotation.getName());
    }
}
