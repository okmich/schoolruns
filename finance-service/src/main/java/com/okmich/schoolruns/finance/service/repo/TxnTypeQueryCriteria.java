/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCString;

/**
 *
 * @author Michael Enudi
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
public class TxnTypeQueryCriteria extends BaseEntityQueryCriteria {

    public static final String txnTypeCode = "txnTypeCode";
    public static final String description = "description";
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "txnCategory")
    public static final String txnCategoryCode = "txnCategoryCode";

    public TxnTypeQueryCriteria() {
        super(true);
    }

    @Override
    public String getEntityName() {
        return "TxnType";
    }

    /**
     * @param txnTypeCode the txnTypeCode to set
     */
    public void setTxnTypeCode(String txnTypeCode, WCString wclause) {
        setParameter(TxnTypeQueryCriteria.txnTypeCode, wclause, txnTypeCode);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(TxnTypeQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param txnCategoryCode the txnCategoryCode to set
     */
    public void setTxnCategoryCode(String txnCategoryCode) {
        setParameter(TxnTypeQueryCriteria.txnCategoryCode, txnCategoryCode);
    }
}
