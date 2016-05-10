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
 * @since Jul 29, 2013
 * @company Okmich Ltd.
 */
public class FeeReceiptQueryCriteria extends BaseEntityQueryCriteria {

    public static final String reversal = "reversal";
    @RelatedEntity(entityAlias = "a", referencedEntity = "feeGroup")
    public static final String feeGroupId = "feeGroupId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "feeGroup")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "feeType")
    public static final String feeTypeCode = "feeTypeCode";
    @RelatedEntity(entityAlias = "a", referencedEntity = "feeGroup")
    @NestedRelatedEntity(nestedEntityAlias = "y", nestedEntityReferenced = "gradeLevel")
    public static final String gradeLevelId = "gradeLevelId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "feeGroup")
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    public static final String schoolStudentId = "schoolStudentId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    public static final String registrationNo = "registrationNo";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    @NestedRelatedEntity(nestedEntityAlias = "w", nestedEntityReferenced = "schoolClass")
    public static final String schoolClassId = "schoolClassId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    @NestedRelatedEntity(nestedEntityAlias = "w", nestedEntityReferenced = "student")
    public static final String studentId = "studentId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    @NestedRelatedEntity(nestedEntityAlias = "w", nestedEntityReferenced = "student")
    public static final String surname = "surname";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    @NestedRelatedEntity(nestedEntityAlias = "v", nestedEntityReferenced = "schoolYear")
    public static final String schoolYearId = "schoolYearId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "receipt")
    public static final String receiptNumber = "receiptNumber";
    @RelatedEntity(entityAlias = "c", referencedEntity = "receipt")
    public static final String txnDate = "txnDate";
    @RelatedEntity(entityAlias = "c", referencedEntity = "receipt")
    public static final String paymentMode = "paymentMode";
    public static final String schoolTermId = "schoolTermId";

    public FeeReceiptQueryCriteria() {
    }

    /**
     *
     * @return
     */
    @Override
    public String getEntityName() {
        return "FeeReceipt";
    }

    /**
     * @param feeGroupId the feeGroupId to set
     */
    public void setFeeGroupId(Integer feeGroupId) {
        setParameter(FeeReceiptQueryCriteria.feeGroupId, feeGroupId);
    }

    /**
     * @param feeTypeCode the feeTypeCode to set
     */
    public void setFeeTypeCode(Integer feeTypeCode) {
        setParameter(FeeReceiptQueryCriteria.feeTypeCode, feeTypeCode);
    }

    /**
     * @param gradeLevelId the gradeLevelId to set
     */
    public void setGradeLevelId(Integer gradeLevelId) {
        setParameter(FeeReceiptQueryCriteria.gradeLevelId, gradeLevelId);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(FeeReceiptQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param schoolStudentId the schoolStudentId to set
     */
    public void setSchoolStudentId(Integer schoolStudentId) {
        setParameter(FeeReceiptQueryCriteria.schoolStudentId, schoolStudentId);
    }

    /**
     * @param registrationNo the registrationNo to set
     */
    public void setRegistrationNo(String registrationNo, WCString wclause) {
        setParameter(FeeReceiptQueryCriteria.registrationNo, wclause, registrationNo);
    }

    /**
     * @param schoolClassId the schoolClassId to set
     */
    public void setSchoolClassId(Integer schoolClassId) {
        setParameter(FeeReceiptQueryCriteria.schoolClassId, schoolClassId);
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(Integer studentId) {
        setParameter(FeeReceiptQueryCriteria.studentId, studentId);
    }

    /**
     *
     * @param surname
     * @param wclause
     */
    public void setSurname(String surname, WCString wclause) {
        setParameter(FeeReceiptQueryCriteria.surname, wclause, surname);
    }

    /**
     * @param schoolYearId the schoolYearId to set
     */
    public void setSchoolYearId(Integer schoolYearId) {
        setParameter(FeeReceiptQueryCriteria.schoolYearId, schoolYearId);
    }

    /**
     * @param schoolTermId the schoolTermId to set
     */
    public void setSchoolTermId(Integer schoolTermId) {
        setParameter(FeeReceiptQueryCriteria.schoolTermId, schoolTermId);
    }

    /**
     *
     * @param receiptNumber
     * @param wclause
     */
    public void setReceiptNumber(String receiptNumber, WCString wclause) {
        setParameter(FeeReceiptQueryCriteria.receiptNumber, receiptNumber);
    }

    /**
     *
     * @param txnDate1
     * @param wclause
     * @param txnDate2
     */
    public void setTxnDate(Date txnDate1, WCDate wclause, Date txnDate2) {
        setParameter(FeeReceiptQueryCriteria.txnDate, wclause, txnDate1, txnDate2);
    }

    /**
     * @param paymentMode the paymentMode to set
     */
    public void setPaymentMode(String paymentMode) {
        setParameter(FeeReceiptQueryCriteria.paymentMode, paymentMode);
    }

    /**
     *
     */
    public void setReversal() {
        setParameter(FeeReceiptQueryCriteria.reversal);
    }
}
