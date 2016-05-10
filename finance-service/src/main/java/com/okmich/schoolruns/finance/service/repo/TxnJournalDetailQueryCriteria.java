/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.NestedRelatedEntity;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCDate;
import java.util.Date;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
public class TxnJournalDetailQueryCriteria extends BaseEntityQueryCriteria {

    public static final String accountCode = "accountCode";
    @RelatedEntity(entityAlias = "a", referencedEntity = "txnJournal")
    public static final String txnDate = "txnDate";
    @RelatedEntity(entityAlias = "a", referencedEntity = "txnJournal")
    public static final String txnJournalId = "txnJournalId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "txnJournal")
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "txnJournal")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "txnType")
    public static final String txnTypeId = "txnTypeId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "txnJournal")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "txnType")
    public static final String txnTypeCode = "txnTypeCode";

    public TxnJournalDetailQueryCriteria() {
        super(true);
    }

    @Override
    public String getEntityName() {
        return "TxnJournalDetail";
    }

    /**
     * @param accountCode the accountCode to set
     */
    public void setAccountCode(String accountCode) {
        setParameter(TxnJournalDetailQueryCriteria.accountCode, accountCode);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(TxnJournalDetailQueryCriteria.schoolId, schoolId);
    }

    /**
     *
     * @param val1
     * @param wclause
     * @param val2
     */
    public void setTxnDate(Date val1, WCDate wclause, Date val2) {
        setParameter(TxnJournalDetailQueryCriteria.txnDate, wclause, val1, val2);
    }

    /**
     * @param txnJournalId the txnJournalId to set
     */
    public void setTxnJournalId(Integer txnJournalId) {
        setParameter(TxnJournalDetailQueryCriteria.txnJournalId, txnJournalId);
    }

    /**
     * @param txnTypeId the txnTypeId to set
     */
    public void setTxnTypeId(Integer txnTypeId) {
        setParameter(TxnJournalDetailQueryCriteria.txnTypeId, txnTypeId);
    }

    /**
     * @param txnTypeCode the txnTypeCode to set
     */
    public void setTxnTypeCode(String txnTypeCode) {
        setParameter(TxnJournalDetailQueryCriteria.txnTypeCode, txnTypeCode);
    }
}
