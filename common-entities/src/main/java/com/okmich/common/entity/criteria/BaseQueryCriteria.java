package com.okmich.common.entity.criteria;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Michael Enudi
 * @compay Leadway Assurance Company Ltd.
 * @since February 16, 2008, 5:04 AM
 */
public abstract class BaseQueryCriteria implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * WHERE
     */
    private final static String WHERE = "WHERE";
    /**
     * AND
     */
    private final static String AND = "AND";
    /**
     * JOIN
     */
    private static final String JOIN = "JOIN";
    /**
     * ORDER_BY
     */
    private final static String ORDER_BY = "ORDER BY";
    /**
     * valueMap - map will hold the place Holder against the value itself
     * example <:cityName, >
     */
    private Map<String, Object> parameters;
    /**
     * SELECT_STRING
     */
    private final static String INIT_QUERY = "SELECT o FROM {PRIMARY_ENTITY} o";
    /**
     * sqlString
     */
    private StringBuilder sqlString = new StringBuilder("");
    /**
     * LIKE_SIGN
     */
    private static final String LIKE_SIGN = "%";
    /**
     * SINGLE_QUOTE
     */
    private final String SINGLE_QUOTE = "'";
    /**
     * EMPTY_STRING
     */
    private final String EMPTY_STRING = " ";
    /**
     * PREFIX_HOLD
     */
    private static final String PREFIX_HOLD = ":";
    /**
     * *************************************************************************
     */
    //new additions made here in order to improve on the query filtering options and 
    //capacity of the system by using native queries where JPQL will not suffice    
    /**
     * COMMA_SEPERATOR
     */
    private static final String COMMA_SEPERATOR = ",";
    /**
     * BRACKET_LEFT
     */
    private static final String BRACKET_LEFT = "(";
    /**
     * BRACKET_RIGHT
     */
    private static final String BRACKET_RIGHT = ")";
    /**
     * *************************************************************************
     */
    /**
     * whereClauseAdded
     */
    private boolean whereClauseAdded = false;
    /**
     *
     */
    public Set<OrderClause> orderByClauseSet = new HashSet<>();
    /**
     * initialQuery
     */
    private String initialQuery;
    /**
     * whereClauseRequired - this feature was added as a mechanism to ensure
     * that users cannot run a query over this instance without using at least a
     * where criteria
     *
     * @since 10/07/2013
     * @author Michael Enudi
     */
    private boolean whereClauseRequired = false;

    /**
     * Creates a new instance of BaseQueryCriteria
     */
    protected BaseQueryCriteria() {
        parameters = new HashMap<>();
        initialQuery = INIT_QUERY;
    }

    /**
     * Creates a new instance of BaseQueryCriteria
     */
    protected BaseQueryCriteria(boolean _whereClauseRequired) {
        this();
        this.whereClauseRequired = _whereClauseRequired;
    }

    /**
     * return the name of the entity being queried
     *
     * @return String - entity name
     */
    public abstract String getEntityName();

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with no operand. This implies that the data type of the column is
     * boolean
     *
     * @param String - columnName
     */
    protected void setParameter(String columnName) {
        Map<String, String> map = columnFromRelatedEntity(columnName);
        String objectFieldName = "o." + columnName;
        if (map != null) {
            objectFieldName = getSingleMapKey(map);
        }
        appendToQueryString(objectFieldName + EMPTY_STRING + WCBase.EQUAL + EMPTY_STRING + Boolean.TRUE);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with only one operand. The implied operator here is the
     * WCString.EQUAL operator
     *
     * @param String - columnName
     * @param String - the value
     *
     */
    protected void setParameter(String columnName, String value) {
        setParameter(columnName, WCString.EQUAL, value);
    }

    /**
     * This is a newly introduced case insentitive WHERE comparison clause
     * method constructor by DEBO. set the parameter for criteria. This method
     * is applicable to where clauses with only one operand and case insensitive
     * comparison in the where clause. The implied operator here is the
     * WCString.EQUAL operator
     *
     * @param String - columnName
     * @param String - the value
     *
     */
    protected void setParameterCI(String columnName, String value) {
        setParameterCI(columnName, WCString.EQUAL, value);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with only one operand. The implied operator here is the
     * WCNumber.EQUAL operator
     *
     * @param String - columnName
     * @param Long - the value
     *
     */
    protected void setParameter(String columnName, Long value) {
        setParameter(columnName, WCNumber.EQUAL, value);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with only one operand. The implied operator here is the
     * WCNumber.EQUAL operator
     *
     * @param String - columnName
     * @param Integer - the value
     *
     */
    protected void setParameter(String columnName, Integer value) {
        setParameter(columnName, WCNumber.EQUAL, value);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with only one operand. The implied operator here is the
     * WCNumber.EQUAL operator
     *
     * @param String - columnName
     * @param Double - the value
     *
     */
    protected void setParameter(String columnName, Double value) {
        setParameter(columnName, WCNumber.EQUAL, value);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with only one operand. The implied operator here is the
     * WCNumber.EQUAL operator
     *
     * @param String - columnName
     * @param BigDecimal - the value
     *
     */
    protected void setParameter(String columnName, BigDecimal value) {
        setParameter(columnName, WCNumber.EQUAL, value);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with only one operand. The implied operator here is the
     * WCDate.EQUAL operator
     *
     * @param String - columnName
     * @param Date - the value
     *
     */
    protected void setParameter(String columnName, Date value) {
        setParameter(columnName, WCDate.EQUAL, value);
    }

    /**
     * set the parameter for criteria. This method is applicable for where
     * clauses like 'IS NULL', 'IS NOT NULL'.
     *
     * 10/06/2013 - a change was made for this method to remove the need for
     * mapping for this unary operations like NULL or NOT NULL
     *
     * @param String - columnName
     * @param WCString - the Where Clause type
     * @throws IllegalArgumentException - where the WCBase is not
     * {@link WCBase#IS_NOT_NULL} and {@link WCBase#IS_NULL}
     */
    protected void setParameter(String columnName, WCBase whereClause) {
        if (!whereClause.equals(WCBase.IS_NOT_NULL)
                && !whereClause.equals(WCBase.IS_NULL)) {
            throw new IllegalArgumentException("Invalid where option");
        }
        Map<String, String> map = entityFromRelatedEntity(columnName);
        if (map == null) {
            appendToQueryString("o." + columnName + EMPTY_STRING + whereClause.toString());
        } else {
            appendToQueryString(getSingleMapKey(map) + EMPTY_STRING + whereClause.toString());
        }
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with only one operand and the operator sign is non default. . If
     * the default operator is expected, choose the appropriate method with
     * default operator
     *
     * @param String - columnName
     * @param String - the Where Clause type
     * @param WCString - the value
     */
    protected void setParameter(String columnName, WCString whereClause, String value) {
        Map<String, String> map = columnFromRelatedEntity(columnName);
        String objectFieldName = "o." + columnName;
        if (map != null) {
            objectFieldName = getSingleMapKey(map);
        }
        objectFieldName = "LOWER(" + objectFieldName + ")";
        if (whereClause.equals(WCString.LIKE) || whereClause.equals(WCString.NOT_LIKE)) {
            appendToQueryString(objectFieldName + EMPTY_STRING + whereClause + EMPTY_STRING
                    + SINGLE_QUOTE + LIKE_SIGN + value.toLowerCase() + LIKE_SIGN + SINGLE_QUOTE);
        } else {
            appendToQueryString(objectFieldName + EMPTY_STRING + whereClause + EMPTY_STRING
                    + SINGLE_QUOTE + value.toLowerCase() + SINGLE_QUOTE);
        }
    }

    /**
     * This is a newly introduced case insentitive WHERE comparison clause
     * method constructor by DEBO. set the parameter for criteria. This method
     * is applicable to where clauses with only one operand and the operator
     * sign is non default for case insensitive comparison . If the default
     * operator is expected, choose the appropriate method with default operator
     *
     * @param String - columnName
     * @param String - the Where Clause type
     * @param WCString - the value
     */
    protected void setParameterCI(String columnName, WCString whereClause, String value) {
        Map<String, String> map = columnFromRelatedEntity(columnName);
        String objectFieldName = "o." + columnName + "";
        if (map != null) {
            objectFieldName = getSingleMapKey(map);
        }
        objectFieldName = "LOWER(" + objectFieldName + ")";
        if (whereClause.equals(WCString.LIKE) || whereClause.equals(WCString.NOT_LIKE)) {
            appendToQueryString(objectFieldName + EMPTY_STRING + whereClause + EMPTY_STRING
                    + SINGLE_QUOTE + LIKE_SIGN + value.toLowerCase() + LIKE_SIGN + SINGLE_QUOTE);
        } else {
            appendToQueryString(objectFieldName + EMPTY_STRING + whereClause + EMPTY_STRING
                    + SINGLE_QUOTE + value.toLowerCase() + SINGLE_QUOTE);
        }
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with only one operand and the operator sign is non default. If
     * the default operator is expected, choose the appropriate method with
     * default operator
     *
     * @param String - columnName
     * @param WCNumber - the Where Clause type
     * @param Long - the value
     *
     */
    protected void setParameter(String columnName, WCNumber whereClause, Long value) {
        Map<String, String> map = columnFromRelatedEntity(columnName);
        String objectFieldName = "o." + columnName;
        if (map != null) {
            objectFieldName = getSingleMapKey(map);
            columnName = map.get(objectFieldName);
        }
        appendToQueryString(objectFieldName + EMPTY_STRING + whereClause + EMPTY_STRING + PREFIX_HOLD + columnName);
        getParameters().put(columnName, value);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with only one operand and the operator sign is non default. If
     * the default operator is expected, choose the appropriate method with
     * default operator
     *
     * @param String - columnName
     * @param WCNumber - the Where Clause type
     * @param Integer - the value
     *
     */
    protected void setParameter(String columnName, WCNumber whereClause, Integer value) {
        Map<String, String> map = columnFromRelatedEntity(columnName);
        String objectFieldName = "o." + columnName;
        if (map != null) {
            objectFieldName = getSingleMapKey(map);
            columnName = map.get(objectFieldName);
        }
        appendToQueryString(objectFieldName + EMPTY_STRING + whereClause + EMPTY_STRING + PREFIX_HOLD + columnName);
        getParameters().put(columnName, value);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with only one operand and the operator sign is non default. If
     * the default operator is expected, choose the appropriate method with
     * default operator
     *
     * @param String - columnName
     * @param WCNumber - the Where Clause type
     * @param Double - the value
     */
    protected void setParameter(String columnName, WCNumber whereClause, Double value) {
        Map<String, String> map = columnFromRelatedEntity(columnName);
        String objectFieldName = "o." + columnName;
        if (map != null) {
            objectFieldName = getSingleMapKey(map);
            columnName = map.get(objectFieldName);
        }
        appendToQueryString(objectFieldName + EMPTY_STRING + whereClause + EMPTY_STRING + PREFIX_HOLD + columnName);
        getParameters().put(columnName, value);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with only one operand and the operator sign is non default. If
     * the default operator is expected, choose the appropriate method with
     * default operator
     *
     * @param String - columnName
     * @param WCNumber - the Where Clause type
     * @param BigDecimal - the value
     *
     */
    protected void setParameter(String columnName, WCNumber whereClause, BigDecimal value) {
        Map<String, String> map = columnFromRelatedEntity(columnName);
        String objectFieldName = "o." + columnName;
        if (map != null) {
            objectFieldName = getSingleMapKey(map);
            columnName = map.get(objectFieldName);
        }
        appendToQueryString(objectFieldName + EMPTY_STRING + whereClause + EMPTY_STRING + PREFIX_HOLD + columnName);
        getParameters().put(columnName, value);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with only one operand and the operator sign is non default. If
     * the default operator is expected, choose the appropriate method with
     * default operator
     *
     * @param String - columnName
     * @param WCDate - the Where Clause type
     * @param Date - the value
     *
     */
    protected void setParameter(String columnName, WCDate whereClause, Date value) {
        Map<String, String> map = columnFromRelatedEntity(columnName);
        String objectFieldName = "o." + columnName;
        if (map != null) {
            objectFieldName = getSingleMapKey(map);
            columnName = map.get(objectFieldName);
        }
        appendToQueryString(objectFieldName + EMPTY_STRING + whereClause + EMPTY_STRING + PREFIX_HOLD + columnName);
        getParameters().put(columnName, value);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with two operands. This method assumes that the last two
     * parameter are of type Date. If a different type would be inserted, try
     * other overloaded method of the same name.If the whereClause is null, the
     * default is WCDate.BETWEEN
     *
     * @param String - columnName
     * @param WCDate - the Where Clause type
     * @param Date - the date1
     * @param Date - the date2
     */
    protected void setParameter(String columnName, WCDate whereClause, Date date1, Date date2) {
        Map<String, String> map = columnFromRelatedEntity(columnName);
        String objectFieldName = "o." + columnName;
        String columnName1 = columnName + "1";
        String columnName2 = columnName + "2";
        if (map != null) {
            objectFieldName = getSingleMapKey(map);
            columnName = map.get(objectFieldName);
            columnName1 = columnName + "1";
            columnName2 = columnName + "2";
        }
        appendToQueryString(objectFieldName + EMPTY_STRING + whereClause + EMPTY_STRING + PREFIX_HOLD + columnName1
                + EMPTY_STRING + AND + EMPTY_STRING + PREFIX_HOLD + columnName2);
        getParameters().put(columnName1, date1);
        getParameters().put(columnName2, date2);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with two operands. This method assumes that the last two
     * parameter are of type Long. If a different type would be inserted, try
     * other overloaded method of the same name.
     *
     * @param String - columnName
     * @param WCNumber - the Where Clause type
     * @param Long - the value1
     * @param Long - the value2
     */
    protected void setParameter(String columnName, WCNumber whereClause, Long value1, Long value2) {
        setParameter(columnName, whereClause, value1, value2);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with two operands. This method assumes that the last two
     * parameter are of type Integer. If a different type would be inserted, try
     * other overloaded method of the same name.
     *
     * @param String - columnName
     * @param WCNumber - the Where Clause type
     * @param Integer - the value1
     * @param Integer - the value2
     */
    protected void setParameter(String columnName, WCNumber whereClause, Integer value1, Integer value2) {
        setParameter(columnName, whereClause, value1, value2);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with two operands. This method assumes that the last two
     * parameter are of type Double. If a different type would be inserted, try
     * other overloaded method of the same name.
     *
     * @param String - columnName
     * @param WCNumber - the Where Clause type
     * @param Double - the value1
     * @param Double - the value2
     */
    protected void setParameter(String columnName, WCNumber whereClause, Double value1, Double value2) {
        setParameter(columnName, whereClause, value1, value2);
    }

    /**
     * set the parameter for criteria. This method is applicable to where
     * clauses with two operands. This method assumes that the last two
     * parameter are of type BigDecimal. If a different type would be inserted,
     * try other overloaded method of the same name.
     *
     * @param String - columnName
     * @param WCNumber - the Where Clause type
     * @param BigDecimal - the value1
     * @param BigDecimal - the value2
     */
    protected void setParameter(String columnName, WCNumber whereClause, BigDecimal value1, BigDecimal value2) {
        setParameter(columnName, whereClause, value1, value2);
    }

    /**
     * a central management point for setting the parameters with two-value
     * argument.
     *
     * @param String - columnName
     * @param WCNumber - the Where Clause type
     * @param Object - the value1
     * @param Object - the value2
     */
    public void setParameter(String columnName, WCNumber whereClause, Object value1, Object value2) {
        Map<String, String> map = columnFromRelatedEntity(columnName);
        String objectFieldName = "o." + columnName;
        String columnName1 = columnName + "1";
        String columnName2 = columnName + "2";
        if (map != null) {
            objectFieldName = getSingleMapKey(map);
            columnName = map.get(objectFieldName);
            columnName1 = columnName + "1";
            columnName2 = columnName + "2";
        }
        appendToQueryString(objectFieldName + EMPTY_STRING + whereClause + EMPTY_STRING + PREFIX_HOLD + columnName1
                + EMPTY_STRING + AND + EMPTY_STRING + PREFIX_HOLD + columnName2);
        getParameters().put(columnName1, value1);
        getParameters().put(columnName2, value2);
    }
    //The new addition starts here

    /**
     * *******************************************************************************************
     */
    /**
     * This method handles the assignment of multiple-entry comparison
     * parameters to queries
     *
     * @param columnName java.lang.String
     * @param whereClause com.leadway.util.entity.eao.WCString
     * @param values java.lang.Object[]
     */
    protected void setParameter(String columnName, WCString whereClause, Object[] values) {
        Map<String, String> map = columnFromRelatedEntity(columnName);
        String objectFieldName = "o." + columnName;
        String tempParamClause = "";
        if (map != null) {
            objectFieldName = getSingleMapKey(map);
        }
        //builds and appends the parameters here
        tempParamClause = buildDelimitedParameters(values);

        appendToQueryString(objectFieldName + EMPTY_STRING + whereClause
                + EMPTY_STRING + BRACKET_LEFT + tempParamClause + BRACKET_RIGHT);
    }

    /**
     * values java.lang.Object[]
     */
    private String buildDelimitedParameters(Object[] values) {
        String returnValue = "";
        Object objClass = values[0];

        for (Object obj : values) {
            if (objClass instanceof String) {
                returnValue += SINGLE_QUOTE + obj.toString() + SINGLE_QUOTE + COMMA_SEPERATOR;
            } else {
                returnValue += String.valueOf(obj) + COMMA_SEPERATOR;
            }
        }
        return returnValue.substring(0, returnValue.length() - 1);
    }
    //The new addition ends here

    /**
     * *******************************************************************************************
     */
    /**
     * a map of the field name against the parameter
     *
     * @return Map
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     *
     *
     * @param columnName
     * @return Object
     */
    public Object getParameter(String columnName) {
        Object retObj;
        if (columnName == null || columnName.trim().equals("")) {
            return null;
        } else {
            retObj = getParameters().get(columnName);
        }
        return retObj;
    }

    /**
     * return the full Persistence Query String
     *
     * @return String
     */
    public String getSqlString() {
        StringBuilder returnString = new StringBuilder(initialQuery).append(sqlString);
        if (orderByClauseSet.size() > 0) {
            returnString.append(EMPTY_STRING + ORDER_BY);
        }
        for (OrderClause orderClause : orderByClauseSet) {
            if (!returnString.toString().endsWith(ORDER_BY)) {
                returnString.append(",");
            }
            returnString.append(EMPTY_STRING).append(orderClause.getOrderByColumn()).append(EMPTY_STRING).append(orderClause.getOrderType());
        }
        return returnString.toString();
    }

    @Override
    public int hashCode() {
        return (this.parameters == null ? 0 : this.parameters.hashCode())
                + (this.sqlString == null ? 0 : this.sqlString.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseQueryCriteria other = (BaseQueryCriteria) obj;
        if (!Objects.equals(this.parameters, other.parameters)) {
            return false;
        }
        return Objects.equals(this.sqlString, other.sqlString);
    }

    /**
     * returns a loaded map if the column is on the related entity. The map is
     * used to get the various join attributes from the related entity
     *
     * @param String - the name of the column
     * @return boolean - if the column is on the related entity and not the
     * primary entity
     */
    private Map<String, String> columnFromRelatedEntity(String columnName) {
        Map<String, String> map = FieldAnnotationProcessor.processField(this, columnName);
        if (map != null) {
            boolean isNested = false;
            String entityAlias = map.get("entityAlias");
            String referencedEntity = map.get("referencedEntity");
            String nestedEntityAlias = map.get("nestedEntityAlias");
            String nestedEntityReferenced = map.get("nestedEntityReferenced");
            if (nestedEntityReferenced != null && !nestedEntityReferenced.equals("")
                    && nestedEntityAlias != null && !nestedEntityAlias.equals("")) {
                isNested = true;
            }
            map.clear();
            if (isNested) {
                map.put(nestedEntityAlias + "." + columnName, columnName);
            } else {
                map.put(entityAlias + "." + columnName, columnName);
            }
            addJoinClauseToInitialQuery(entityAlias, referencedEntity, nestedEntityAlias, nestedEntityReferenced);
        }
        return map;
    }

    /**
     *
     * @param columnName
     * @return
     */
    private Map<String, String> entityFromRelatedEntity(String columnName) {
        Map<String, String> map = FieldAnnotationProcessor.processField(this, columnName);
        if (map != null) {
            boolean isNested = false;
            String entityAlias = map.get("entityAlias");
            String referencedEntity = map.get("referencedEntity");
            String nestedEntityAlias = map.get("nestedEntityAlias");
            String nestedEntityReferenced = map.get("nestedEntityReferenced");
            if (nestedEntityReferenced != null && !nestedEntityReferenced.equals("")
                    && nestedEntityAlias != null && !nestedEntityAlias.equals("")) {
                isNested = true;
            }
            map.clear();
            if (isNested) {
                map.put(entityAlias + "." + nestedEntityReferenced, columnName);
            } else {
                map.put("o." + referencedEntity, columnName);
            }
        }
        return map;
    }

    /**
     * appends the whereString passed into it to sqlString
     *
     * @param whereString
     */
    private void appendToQueryString(String whereString) {
        if (!whereClauseAdded) {
            sqlString.append(EMPTY_STRING + WHERE + EMPTY_STRING).append(whereString);
        } else {
            sqlString.append(EMPTY_STRING + AND + EMPTY_STRING).append(whereString);
        }
        whereClauseAdded = true;
    }

    /**
     * receives a map and takes out the key of the first map entry
     *
     * @param Map
     * @return String - key of first map entry
     */
    private String getSingleMapKey(Map<String, String> map) {
        String returnVal = "";
        if (map.size() > 0) {
            Set<String> set = map.keySet();
            for (String key : set) {
                returnVal = key;
                break;
            }
        } else {
            returnVal = "";
        }
        return returnVal;
    }

    /**
     * the simple function takes a field name like "o.productName" and puts the
     * field name withing a lower() function like lower(o.productName).
     *
     * @param String
     * @returns String
     */
    /* private String insertLowerFunction(String fieldName)
     {
     return "LOWER(" + fieldName + ")";
     }*/
    /**
     * adds a join clause to the initial standard query
     *
     * @param String
     */
    private void addJoinClauseToInitialQuery(String entityAlias, String referencedEntity,
            String nestedEntityAlias, String nestedEntityReferenced) {
        String toAppend = EMPTY_STRING + JOIN + EMPTY_STRING + "o." + referencedEntity
                + EMPTY_STRING + entityAlias;
        if (!initialQuery.contains(toAppend)) {
            initialQuery += toAppend;
        }
        toAppend = "";
        if (nestedEntityReferenced != null && !nestedEntityReferenced.equals("")
                && nestedEntityAlias != null && !nestedEntityAlias.equals("")) {
            toAppend += EMPTY_STRING + JOIN + EMPTY_STRING + entityAlias + "." + nestedEntityReferenced
                    + EMPTY_STRING + nestedEntityAlias;
        }
        if (!initialQuery.contains(toAppend)) {
            initialQuery += toAppend;
        }
    }
    /////////////////// added on 23/05/2008 to support order by clauses in query/////

    /**
     * provides a way to set the column that will be used for sorting
     *
     * @param Vector<OrderClause> - an array of OrderClause object to specify
     * the order by column
     */
    public void setOrderByColumn(List<OrderClause> orderClauseVector) {
        for (int i = 0; i < orderClauseVector.size(); i++) {
            OrderClause orderClause = orderClauseVector.get(i);
            Map<String, String> map = FieldAnnotationProcessor.processField(this, orderClause.getOrderByColumn());
            if (map != null) {
                if (map.containsKey("nestedEntityReferenced")) {
                    orderClause.setOrderByColumn(map.get("nestedEntityAlias") + "." + orderClause.getOrderByColumn());
                    orderByClauseSet.add(orderClause);
                } else if (map.containsKey("referencedEntity")) {
                    orderClause.setOrderByColumn(map.get("entityAlias") + "." + orderClause.getOrderByColumn());
                    orderByClauseSet.add(orderClause);
                }
                addJoinClauseToInitialQuery(map.get("entityAlias"), map.get("referencedEntity"),
                        map.get("nestedEntityAlias"), map.get("nestedEntityReferenced"));
            } else {
                orderClause.setOrderByColumn("o." + orderClause.getOrderByColumn());
                orderByClauseSet.add(orderClause);
            }
        }
    }

    /**
     * @return true if at least a criteria must be set before the query is run
     * or false other
     */
    public boolean isWhereClauseRequired() {
        return whereClauseRequired;
    }
}
