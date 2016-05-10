package com.okmich.common.entity.criteria;

/**
 *
 * @author Michael Enudi
 * @compay Leadway Assurance Company Ltd.
 * @since February 16, 2008, 6:23 AM
 */
public class WCBase {

    /**
     * NOT - represent the NOT operator in JPQL or SQL statements
     */
    public final static WCBase NOT = new WCBase("NOT");
    /**
     * EQUAL - represent the equal sign in JPQL or SQL statements
     */
    public final static WCBase EQUAL = new WCBase("=");
    /**
     * NOT_EQUAL - represent the not equal sign in JPQL or SQL statements
     */
    public final static WCBase NOT_EQUAL = new WCBase("<>");
    /**
     * IS_NULL - used to specify the is null expression in a JPQL or SQL
     * statement.
     */
    public final static WCBase IS_NULL = new WCBase("IS NULL");
    /**
     * IS_NOT_NULL - used to specify the not like expression in a JPQL or SQL
     * statement.
     */
    public final static WCBase IS_NOT_NULL = new WCBase("IS NOT NULL");
    /**
     * operator
     */
    private String operator;

    /**
     * WCBase Creates a new instance of WCDate
     *
     * @param String - aString representing the operator sign for this instance
     */
    protected WCBase(String operator) {
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