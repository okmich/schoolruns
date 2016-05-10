/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.data;

import com.okmich.common.BusinessData;
import com.okmich.schoolruns.common.entity.TxnJournal;
import com.okmich.schoolruns.common.entity.TxnJournalDetail;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
public class TxnData extends BusinessData {

    /**
     * txnJournalId
     */
    private Integer txnJournalId;
    /**
     * narration
     */
    private String narration;
    /**
     * txnDate
     */
    private Date txnDate;
    /**
     * effectiveDate
     */
    private Date effectiveDate;
    /**
     * txnRef
     */
    private String txnRef;
    /**
     * altRef
     */
    private String altRef;
    /**
     * amount
     */
    private BigDecimal amount;
    /**
     * txnJournalDetailId
     */
    private Integer txnJournalDetailId;
    /**
     * dcSign
     */
    private String dcSign;
    /**
     * txnJournal
     */
    private TxnJournal txnJournal;
    /**
     * txnTypeId
     */
    private Integer txnTypeId;
    /**
     * txnTypeCode
     */
    private String txnTypeCode;
    /**
     * txnType
     */
    private String txnType;
    /**
     * debitAccountId
     */
    private Integer debitAccountId;
    /**
     * debitAccount
     */
    private String debitAccount;
    /**
     * creditAccountId
     */
    private Integer creditAccountId;
    /**
     * creditAccount
     */
    private String creditAccount;
    /**
     * schoolId
     */
    private Integer schoolId;
    /**
     * school
     */
    private String school;
    /**
     * txnCategoryCode
     */
    private String txnCategoryCode;
    /**
     * txnCategory
     */
    private String txnCategory;

    public TxnData() {
    }

    public TxnData(TxnJournal txnJournal) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public TxnData(TxnJournalDetail txnJournalDetail) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * @return the txnJournalId
     */
    public Integer getTxnJournalId() {
        return txnJournalId;
    }

    /**
     * @param txnJournalId the txnJournalId to set
     */
    public void setTxnJournalId(Integer txnJournalId) {
        this.txnJournalId = txnJournalId;
    }

    /**
     * @return the narration
     */
    public String getNarration() {
        return narration;
    }

    /**
     * @param narration the narration to set
     */
    public void setNarration(String narration) {
        this.narration = narration;
    }

    /**
     * @return the txnDate
     */
    public Date getTxnDate() {
        return txnDate;
    }

    /**
     * @param txnDate the txnDate to set
     */
    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    /**
     * @return the effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @param effectiveDate the effectiveDate to set
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @return the txnRef
     */
    public String getTxnRef() {
        return txnRef;
    }

    /**
     * @param txnRef the txnRef to set
     */
    public void setTxnRef(String txnRef) {
        this.txnRef = txnRef;
    }

    /**
     * @return the altRef
     */
    public String getAltRef() {
        return altRef;
    }

    /**
     * @param altRef the altRef to set
     */
    public void setAltRef(String altRef) {
        this.altRef = altRef;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the txnJournalDetailId
     */
    public Integer getTxnJournalDetailId() {
        return txnJournalDetailId;
    }

    /**
     * @param txnJournalDetailId the txnJournalDetailId to set
     */
    public void setTxnJournalDetailId(Integer txnJournalDetailId) {
        this.txnJournalDetailId = txnJournalDetailId;
    }

    /**
     * @return the dcSign
     */
    public String getDcSign() {
        return dcSign;
    }

    /**
     * @param dcSign the dcSign to set
     */
    public void setDcSign(String dcSign) {
        this.dcSign = dcSign;
    }

    /**
     * @return the txnJournal
     */
    public TxnJournal getTxnJournal() {
        return txnJournal;
    }

    /**
     * @param txnJournal the txnJournal to set
     */
    public void setTxnJournal(TxnJournal txnJournal) {
        this.txnJournal = txnJournal;
    }

    /**
     * @return the txnTypeId
     */
    public Integer getTxnTypeId() {
        return txnTypeId;
    }

    /**
     * @param txnTypeId the txnTypeId to set
     */
    public void setTxnTypeId(Integer txnTypeId) {
        this.txnTypeId = txnTypeId;
    }

    /**
     * @return the txnTypeCode
     */
    public String getTxnTypeCode() {
        return txnTypeCode;
    }

    /**
     * @param txnTypeCode the txnTypeCode to set
     */
    public void setTxnTypeCode(String txnTypeCode) {
        this.txnTypeCode = txnTypeCode;
    }

    /**
     * @return the txnType
     */
    public String getTxnType() {
        return txnType;
    }

    /**
     * @param txnType the txnType to set
     */
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    /**
     * @return the debitAccountId
     */
    public Integer getDebitAccountId() {
        return debitAccountId;
    }

    /**
     * @param debitAccountId the debitAccountId to set
     */
    public void setDebitAccountId(Integer debitAccountId) {
        this.debitAccountId = debitAccountId;
    }

    /**
     * @return the debitAccount
     */
    public String getDebitAccount() {
        return debitAccount;
    }

    /**
     * @param debitAccount the debitAccount to set
     */
    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    /**
     * @return the creditAccountId
     */
    public Integer getCreditAccountId() {
        return creditAccountId;
    }

    /**
     * @param creditAccountId the creditAccountId to set
     */
    public void setCreditAccountId(Integer creditAccountId) {
        this.creditAccountId = creditAccountId;
    }

    /**
     * @return the creditAccount
     */
    public String getCreditAccount() {
        return creditAccount;
    }

    /**
     * @param creditAccount the creditAccount to set
     */
    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    /**
     * @return the schoolId
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * @return the school
     */
    public String getSchool() {
        return school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * @return the txnCategoryCode
     */
    public String getTxnCategoryCode() {
        return txnCategoryCode;
    }

    /**
     * @param txnCategoryCode the txnCategoryCode to set
     */
    public void setTxnCategoryCode(String txnCategoryCode) {
        this.txnCategoryCode = txnCategoryCode;
    }

    /**
     * @return the txnCategory
     */
    public String getTxnCategory() {
        return txnCategory;
    }

    /**
     * @param txnCategory the txnCategory to set
     */
    public void setTxnCategory(String txnCategory) {
        this.txnCategory = txnCategory;
    }
    
    
}
