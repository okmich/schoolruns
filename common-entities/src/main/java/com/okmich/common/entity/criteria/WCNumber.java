package com.okmich.common.entity.criteria;

/**
 * Represents a Number where clauses in a JPQL or SQL statement
 * It support the 'IS NULL', expression.
 *
 * @author Michael Enudi
 * @compay Leadway Assurance Company Ltd.
 * @since November 17, 2007, 6:18 AM
 */
public class WCNumber {

    /**
     * GREATER_THAN - used to specify the greater than symbol in a JPQL or
     * SQL statement.
     */
    public static final WCNumber GREATER_THAN = new WCNumber(">");
    /**
     * LESS_THAN - used to specify the less than symbol in a JPQL or
     * SQL statement.
     */
    public static final WCNumber LESS_THAN = new WCNumber("<");
    /**
     * GREATER_OR_EQUAL - used to specify the greater than or eqaul to symbol in a JPQL or
     * SQL statement.
     */
    public static final WCNumber GREATER_OR_EQUAL = new WCNumber(">=");
    /**
     * LESS_OR_EQUAL - used to specify the less than or eqaul to symbol in a JPQL or
     * SQL statement.
     */
    public static final WCNumber LESS_OR_EQUAL = new WCNumber("<=");
    /**
     * BETWEEN - used to specify the between expression in a JPQL or
     * SQL statement.
     */
    public static final WCNumber BETWEEN = new WCNumber("BETWEEN");
    /**
     * BETWEEN - used to specify the between expression is a JPQL or
     * SQL statement.
     */
    public static final WCNumber NOT_BETWEEN = new WCNumber("NOT BETWEEN");
    /**
     * IS_NULL - used to specify the is null expression in a JPQL or
     * SQL statement.
     */
    public final static WCNumber IS_NULL = new WCNumber("IS NULL");
    /**
     * IS_NOT_NULL - used to specify the not like expression in a JPQL or
     * SQL statement.
     */
    public final static WCNumber IS_NOT_NULL = new WCNumber("IS NOT NULL");
    /**
     * EQUAL - represent the equal sign in JPQL or SQL statements
     */
    public final static WCNumber EQUAL = new WCNumber("=");
    /**
     * NOT_EQUAL - represent the not equal sign in JPQL or SQL statements
     */
    public final static WCNumber NOT_EQUAL = new WCNumber("<>");
    /**
     * operator
     */
    private String operator;

    /**WCNumber
     * Creates a new instance of WCNumber
     *
     * @param String - aString representing the operator sign for this instance
     */
    private WCNumber(String operator) {
        this.operator = operator;
    }

    /**
     * returns the operator references as a string
     *
     * @return the operator symbol
     */
    @Override
    public String toString() {
        return operator;
    }
}
