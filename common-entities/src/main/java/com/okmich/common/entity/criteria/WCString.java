package com.okmich.common.entity.criteria;

/**
 * Represents a String where clause in a JPQL or SQL statement It support the
 * 'LIKE', expression.
 * 
 * @author Michael Enudi
 * @compay Leadway Assurance Company Ltd.
 * @since November 17, 2007, 5:42 AM
 */
public class WCString {

	/**
	 * LIKE - used to specify the not like expression in a JPQL or SQL
	 * statement.
	 */
	public final static WCString LIKE = new WCString("LIKE");
	/**
	 * NOT_LIKE - used to specify the not like expression in a JPQL or SQL
	 * statement.
	 */
	public final static WCString NOT_LIKE = new WCString("NOT LIKE");
	/**
	 * IS_NULL - used to specify the is null expression in a JPQL or SQL
	 * statement.
	 */
	public final static WCString IS_NULL = new WCString("IS NULL");
	/**
	 * IS_NOT_NULL - used to specify the not like expression in a JPQL or SQL
	 * statement.
	 */
	public final static WCString IS_NOT_NULL = new WCString("IS NOT NULL");
	/**
	 * EQUAL - represent the equal sign in JPQL or SQL statements
	 */
	public final static WCString EQUAL = new WCString("=");
	/**
	 * NOT_EQUAL - represent the not equal sign in JPQL or SQL statements
	 */
	public final static WCString NOT_EQUAL = new WCString("<>");
	/**
	 * IN - represent the IN sign for SQL statements
	 */
	public final static WCString IN = new WCString("IN");
	/**
	 * NOT_IN - represents the NOT_IN sign for PL/SQL
	 */
	public final static WCString NOT_IN = new WCString("NOT IN");
	/**
	 * operator
	 */
	private String operator;

	/**
	 * Creates a new instance of WCString
	 * 
	 * @param String
	 *            - aString representing the operator sign for this instance
	 */
	private WCString(String operator) {
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
