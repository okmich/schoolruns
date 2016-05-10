package com.okmich.common.util;

/**
 *
 * @author Patrick Aniah
 */
import java.io.*;

public class MoneyOutOfRangeException extends RuntimeException implements Serializable {

    private String moneyValue;

    /**
     * Creates a new instance of MoneyOutOfRangeException
     */
    public MoneyOutOfRangeException(String message) {
        super(message);
    }

    public String getMoneyValue() {
        return moneyValue;
    }

    public void setMoneyValue(String moneyValue) {
        this.moneyValue = moneyValue;
    }
}
