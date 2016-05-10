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
public class ReceiptQueryCriteria extends BaseEntityQueryCriteria {

    public static final String receiptNumber = "receiptNumber";
    public static final String txnDate = "txnDate";
    public static final String effectiveDate = "effectiveDate";
    public static final String paymentMode = "paymentMode";
    public static final String paymentNumber = "paymentNumber";
    public static final String tranSource = "tranSource";
    public static final String mobileNo = "mobileNo";
    public static final String feeType = "feeType";
    public static final String reversal = "reversal";
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "txnType")
    public static final String txnTypeId = "txnTypeId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "txnType")
    public static final String txnTypeCode = "txnTypeCode";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolTerm")
    public static final String schoolTermId = "schoolTermId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolTerm")
    public static final String schoolYearId = "schoolYearId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "receipt")
    private static String receiptId = "receiptId";

    public ReceiptQueryCriteria() {
        super(true);
    }

    @Override
    public String getEntityName() {
        return "Receipt";
    }

    /**
     * @param _receiptNumber the receiptNumber to set
     */
    public void setReceiptNumber(String _receiptNumber) {
        setParameter(ReceiptQueryCriteria.receiptNumber, _receiptNumber);
    }

    /**
     * @param _receiptNumber the receiptNumber to set
     * @param wclause - String value where clause
     */
    public void setReceiptNumber(String _receiptNumber, WCString wclause) {
        setParameter(ReceiptQueryCriteria.receiptNumber, wclause, _receiptNumber);
    }

    /**
     * @param _txnDate the txnDate to set
     */
    public void setTxnDate(Date _txnDate) {
        setParameter(ReceiptQueryCriteria.txnDate, _txnDate);
    }

    /**
     *
     * @param _txnDate
     * @param wclause
     * @param _txnDate2
     */
    public void setTxnDate(Date _txnDate, WCDate wclause, Date _txnDate2) {
        setParameter(ReceiptQueryCriteria.txnDate, wclause, _txnDate, _txnDate2);
    }

    /**
     * @param _effectiveDate the effectiveDate to set
     */
    public void setEffectiveDate(Date _effectiveDate) {
        setParameter(ReceiptQueryCriteria.effectiveDate, _effectiveDate);
    }

    /**
     *
     * @param _effectiveDate
     * @param wclause
     * @param _effectiveDate2
     */
    public void setEffectiveDate(Date _effectiveDate, WCDate wclause, Date _effectiveDate2) {
        setParameter(ReceiptQueryCriteria.effectiveDate, wclause, _effectiveDate, _effectiveDate2);
    }

    /**
     * @param _paymentMode the _paymentMode to set
     */
    public void setPaymentMode(String _paymentMode) {
        setParameter(ReceiptQueryCriteria.paymentMode, _paymentMode);
    }

    /**
     * @param _paymentNumber the paymentNumber to set
     */
    public void setPaymentNumber(String _paymentNumber) {
        setParameter(ReceiptQueryCriteria.paymentNumber, _paymentNumber);
    }

    /**
     * @param _txnTypeId the txnTypeId to set
     */
    public void setTxnTypeId(Integer _txnTypeId) {
        setParameter(ReceiptQueryCriteria.txnTypeId, _txnTypeId);
    }

    /**
     * @param _txnTypeCode the txnTypeCode to set
     */
    public void setTxnTypeCode(String _txnTypeCode) {
        setParameter(ReceiptQueryCriteria.txnTypeCode, _txnTypeCode);
    }

    /**
     * @param _schoolId the schoolId to set
     */
    public void setSchoolId(Integer _schoolId) {
        setParameter(ReceiptQueryCriteria.schoolId, _schoolId);
    }

    /**
     * @param _schoolTermId the _schoolTermId to set
     */
    public void setSchoolTermId(Integer _schoolTermId) {
        setParameter(ReceiptQueryCriteria.schoolTermId, _schoolTermId);
    }

    /**
     * @param _schoolYearId the schoolYearId to set
     */
    public void setSchoolYearId(Integer _schoolYearId) {
        setParameter(ReceiptQueryCriteria.schoolYearId, _schoolYearId);
    }

    /**
     * @param aReceiptId the receiptId to set
     */
    public void setPrevReceiptId(String aReceiptId) {
        setParameter(ReceiptQueryCriteria.receiptId, aReceiptId);
    }

    /**
     * @param _tranSource the tranSource to set
     */
    public void setTranSource(String _tranSource) {
        setParameter(ReceiptQueryCriteria.tranSource, _tranSource);
    }

    /**
     * @param _mobileNo the mobileNo to set
     */
    public void setMobileNo(String _mobileNo) {
        setParameter(ReceiptQueryCriteria.mobileNo, _mobileNo);
    }

    /**
     *
     */
    public void setFeeType() {
        setParameter(ReceiptQueryCriteria.feeType);
    }

    /**
     *
     */
    public void setReversal() {
        setParameter(ReceiptQueryCriteria.reversal);
    }
}
