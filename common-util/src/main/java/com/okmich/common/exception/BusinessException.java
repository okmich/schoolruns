/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.exception;

/**
 *
 * @author Michael
 */
public class BusinessException extends Exception {

    private String param;

    /**
     * Creates a new instance of
     * <code>BusinessException</code> without detail message.
     */
    public BusinessException() {
    }

    /**
     * Constructs an instance of
     * <code>BusinessException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message, String param) {
        super(message);
        this.param = param;
    }

    /**
     * @return the param
     */
    public String getParam() {
        return param;
    }

    /**
     * @param param the param to set
     */
    public void setParam(String param) {
        this.param = param;
    }
}
