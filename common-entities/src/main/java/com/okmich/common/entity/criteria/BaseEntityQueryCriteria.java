package com.okmich.common.entity.criteria;

import java.util.Date;

/**
 *
 * @author Michael Enudi
 * @compay Leadway Assurance Company Ltd.
 * @since April 22, 2008, 12:01 PM
 */
public abstract class BaseEntityQueryCriteria extends BaseQueryCriteria {

    /**
     *
     */
    private static final long serialVersionUID = -4307512526415396419L;
    public static final String status = "status";
    public static final String modifiedTime = "modifiedTime";
    public static final String modifiedBy = "modifiedBy";

    /**
     * Creates a new instance of BaseEntityQueryCriteria
     */
    protected BaseEntityQueryCriteria() {
        super();
    }

    /**
     * {@inheritDoc }
     */
    protected BaseEntityQueryCriteria(boolean _whereClauseRequired) {
        super(_whereClauseRequired);
    }

    public void setStatus(WCBase wClause) {
        setParameter(BaseEntityQueryCriteria.status, wClause);
    }

    public void setStatus(String status) {
        setParameter(BaseEntityQueryCriteria.status, status);
    }

    public void setStatus(String status, WCString wClause) {
        setParameter(BaseEntityQueryCriteria.status, wClause, status);
    }

    public void setCreateDate(Date modifiedDate) {
        setParameter(BaseEntityQueryCriteria.modifiedTime, modifiedDate);
    }

    public void setCreateDate(Date modifiedDate, WCDate wClause) {
        setParameter(BaseEntityQueryCriteria.modifiedTime, wClause, modifiedDate);
    }

    public void setCreateDate(Date modifiedDate, WCDate wClause, Date modifiedDate1) {
        setParameter(BaseEntityQueryCriteria.modifiedTime, wClause, modifiedDate,
                modifiedDate1);
    }

    public void setCreatedBy(String modifiedBy) {
        setParameter(BaseEntityQueryCriteria.modifiedBy, modifiedBy);
    }

    public void setCreatedBy(String modifiedBy, WCString wClause) {
        setParameter(BaseEntityQueryCriteria.modifiedBy, wClause, modifiedBy);
    }
}
