package com.okmich.common.entity.criteria;

/**
 * Represents a date where clauses in a JPQL or SQL statement
 * It support the 'BETWEEN', expression.
 *
 * @author Michael Enudi
 * @compay Leadway Assurance Company Ltd.
 * @since November 17, 2007, 6:03 AM
 */
public class WCDate {

    /**
     * BETWEEN - used to specify the between expression in a JPQL or
     * SQL statement.
     */
    public static final WCDate BETWEEN = new WCDate("BETWEEN");
    /**
     * BETWEEN - used to specify the between expression is a JPQL or
     * SQL statement.
     */
    public static final WCDate NOT_BETWEEN = new WCDate("NOT BETWEEN");
    /**
     * IS_NULL - used to specify the is null expression in a JPQL or
     * SQL statement.
     */
    public final static WCDate IS_NULL = new WCDate("IS NULL");
    /**
     * IS_NOT_NULL - used to specify the not like expression in a JPQL or
     * SQL statement.
     */
    public final static WCDate IS_NOT_NULL = new WCDate("IS NOT NULL");
    /**
     * EQUAL - represent the equal sign in JPQL or SQL statements
     */
    public final static WCDate EQUAL = new WCDate("=");
    /**
     * NOT_EQUAL - represent the not equal sign in JPQL or SQL statements
     */
    public final static WCDate NOT_EQUAL = new WCDate("<>");
    /**
     * GREATER_THAN - used to specify the greater than symbol in a JPQL or
     * SQL statement.
     */
    public static final WCDate GREATER_THAN = new WCDate(">");
    /**
     * LESS_THAN - used to specify the less than symbol in a JPQL or
     * SQL statement.
     */
    public static final WCDate LESS_THAN = new WCDate("<");
    /**
     * GREATER_OR_EQUAL - used to specify the greater than or eqaul to symbol in a JPQL or
     * SQL statement.
     */
    public static final WCDate GREATER_OR_EQUAL = new WCDate(">=");
    /**
     * LESS_OR_EQUAL - used to specify the less than or eqaul to symbol in a JPQL or
     * SQL statement.
     */
    public static final WCDate LESS_OR_EQUAL = new WCDate("<=");
    /**
     * operator
     */
    private String operator;

    /**WCDate
     * Creates a new instance of WCDate
     *
     * @param String - aString representing the operator sign for this instance
     */
    private WCDate(String operator) {
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
