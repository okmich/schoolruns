/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCDate;
import java.util.Date;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
public class TxnJournalQueryCriteria extends BaseEntityQueryCriteria {

    public static final String schoolId = "schoolId";
    public static final String txnDate = "txnDate";
    public static final String effectiveDate = "effectiveDate";
    @RelatedEntity(entityAlias = "a", referencedEntity = "txnType")
    public static final String txnTypeId = "txnTypeId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "txnType")
    public static final String txnTypeCode = "txnTypeCode";

    public TxnJournalQueryCriteria() {
        super(true);
    }

    @Override
    public String getEntityName() {
        return "TxnJournal";
    }

    /**
     *
     * @param val1
     * @param wclause
     * @param val2
     */
    public void setTxnDate(Date val1, WCDate wclause, Date val2) {
        setParameter(TxnJournalQueryCriteria.txnDate, wclause, val1, val2);
    }

    /**
     *
     * @param val1
     * @param wclause
     * @param val2
     */
    public void setEffectiveDate(Date val1, WCDate wclause, Date val2) {
        setParameter(TxnJournalQueryCriteria.effectiveDate, wclause, val1, val2);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(TxnJournalQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param txnTypeId the txnTypeId to set
     */
    public void setTxnTypeId(Integer txnTypeId) {
        setParameter(TxnJournalQueryCriteria.txnTypeId, txnTypeId);
    }

    /**
     * @param txnTypeCode the txnTypeCode to set
     */
    public void setTxnTypeCode(String txnTypeCode) {
        setParameter(TxnJournalQueryCriteria.txnTypeCode, txnTypeCode);
    }
}
