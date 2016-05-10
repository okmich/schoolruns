/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.exception;

/**
 *
 * @author Michael
 */
public class TechnicalException extends RuntimeException {

    /**
     * Creates a new instance of
     * <code>TechnicalException</code> without detail message.
     */
    public TechnicalException() {
    }

    /**
     * Constructs an instance of
     * <code>TechnicalException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public TechnicalException(String msg) {
        super(msg);
    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
