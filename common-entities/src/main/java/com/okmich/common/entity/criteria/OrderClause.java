package com.okmich.common.entity.criteria;

import java.io.Serializable;

/**
 *
 * @author Michael Enudi
 * @compay Leadway Assurance Company Ltd.
 * @since May 23, 2008, 7:56 AM
 */
public class OrderClause implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * orderByColumn
     */
    private String orderByColumn;
    /**
     * OrderType
     */
    private OrderType orderType;

    /**
     * Creates a new instance of OrderClause
     */
    public OrderClause() {
    }

    /**
     * Creates a new instance of OrderClause
     */
    public OrderClause(String orderByColumn) {
        setOrderByColumn(orderByColumn);
        setOrderType(OrderType.ASC);
    }

    /**
     * Creates a new instance of OrderClause
     */
    public OrderClause(String orderByColumn, OrderType orderType) {
        setOrderByColumn(orderByColumn);
        setOrderType(orderType);
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof OrderClause)) {
            return false;
        }
        OrderClause other = (OrderClause) object;
        if (this.orderByColumn != other.orderByColumn && (this.orderByColumn == null ||
                !this.orderByColumn.equals(other.orderByColumn))) {
            return false;
        }
        return true;
    }
}
