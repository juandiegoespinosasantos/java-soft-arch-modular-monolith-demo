package com.jdespinosa.demo.restaurant.commons.exception;

/**
 * Not Found Exception.
 *
 * @author juandiegoespinosasantos@outlook.com
 * @version Feb 10, 2026
 * @since 17
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String entity, Object id) {
        super(entity + ".id=" + id + " not found");
    }

    public NotFoundException(String message) {
        super(message);
    }
}