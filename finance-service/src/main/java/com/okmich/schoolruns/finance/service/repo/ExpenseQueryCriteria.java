/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.NestedRelatedEntity;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCDate;
import com.okmich.common.entity.criteria.WCString;
import java.util.Date;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
public class ExpenseQueryCriteria extends BaseEntityQueryCriteria {

    public static final String reversal = "reversal";
    public static final String narration = "narration";
    public static final String txnDate = "txnDate";
    @RelatedEntity(entityAlias = "a", referencedEntity = "txnType")
    public static final String txnTypeId = "txnTypeId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolTerm")
    public static final String schoolTermId = "schoolTermId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolTerm")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "schooYearId")
    public static final String schoolYearId = "schoolYearId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolTerm")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "schooYearId")
    public static final String schoolId = "schoolId";

    public ExpenseQueryCriteria() {
        super(true);
    }

    @Override
    public String getEntityName() {
        return "Expense";
    }

    /**
     *
     */
    public void setReversal() {
        setParameter(ExpenseQueryCriteria.reversal);
    }

    /**
     *
     *
     * @param narration
     * @param whereClause
     */
    public void setNarration(String narration, WCString whereClause) {
        setParameter(ExpenseQueryCriteria.schoolId, whereClause, schoolId);
    }

    /**
     *
     *
     * @param txnDate1
     * @param whereClause
     * @param txnDate2
     */
    public void setTxnDate(Date txnDate1, WCDate whereClause, Date txnDate2) {
        setParameter(ExpenseQueryCriteria.txnDate, whereClause, txnDate1, txnDate2);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(ExpenseQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param schoolTermId the schoolTermId to set
     */
    public void setSchoolTermId(Integer schoolTermId) {
        setParameter(ExpenseQueryCriteria.schoolTermId, schoolTermId);
    }

    /**
     * @param schoolYearId the schoolYearId to set
     */
    public void setSchoolYearId(Integer schoolYearId) {
        setParameter(ExpenseQueryCriteria.schoolYearId, schoolYearId);
    }

    /**
     * @param txnTypeId the txnTypeId to set
     */
    public void setTxnTypeId(Integer txnTypeId) {
        setParameter(ExpenseQueryCriteria.txnTypeId, txnTypeId);
    }
}