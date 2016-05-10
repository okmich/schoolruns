package com.okmich.common.util;

/**
 *
 * @author Patrick Aniah
 */
import java.io.*;

public class UnknownMoneyElementException extends Exception implements Serializable {

    private String unknownMoneyElement;

    /**
     * Creates a new instance of UnknownMoneyElementException
     */
    public UnknownMoneyElementException(String message) {
        super(message);
    }

    public UnknownMoneyElementException(String message, String unknownMoneyElement) {
        super(message);
        setUnknownMoneyElement(unknownMoneyElement);
    }

    public String getUnknownMoneyElement() {
        return unknownMoneyElement;
    }

    public void setUnknownMoneyElement(String unknownMoneyElement) {
        this.unknownMoneyElement = unknownMoneyElement;
    }
}
