/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "receipt")
public class Receipt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "receipt_id")
    private Integer receiptId;
    @Column(name = "receipt_number")
    private String receiptNumber;
    @Basic(optional = false)
    @Column(name = "txn_date")
    @Temporal(TemporalType.DATE)
    private Date txnDate;
    @Basic(optional = false)
    @Column(name = "effective_date")
    @Temporal(TemporalType.DATE)
    private Date effectiveDate;
    @Basic(optional = false)
    @Column(name = "payer")
    private String payer;
    @Basic(optional = false)
    @Column(name = "narration")
    private String narration;
    @Basic(optional = false)
    @Column(name = "amount")
    private BigDecimal amount;
    @Basic(optional = false)
    @Column(name = "payment_mode")
    private String paymentMode;
    @Column(name = "payment_number")
    private String paymentNumber;
    @Column(name = "fee_type")
    private boolean feeType;
    @Column(name = "reversal")
    private boolean reversal;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "school_id")
    @Basic(optional = false)
    private Integer schoolId;
    @Basic(optional = false)
    @Column(name = "tran_source")
    private String tranSource;
    @Column(name = "mobile_no")
    private String mobileNo;
    @JoinColumn(name = "prev_receipt_id", referencedColumnName = "receipt_id")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Receipt receipt;
    @JoinColumn(name = "txn_type_id", referencedColumnName = "txn_type_id")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private TxnType txnType;
    @JoinColumn(name = "school_term_id", referencedColumnName = "school_term_id")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private SchoolTerm schoolTerm;
    @Transient
    private BigDecimal receiptBalance;

    public Receipt() {
        this.schoolTerm = new SchoolTerm();
        this.txnType = new TxnType();
    }

    public Receipt(Integer receiptId) {
        this();
        this.receiptId = receiptId;
    }

    public Integer getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Integer receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    /**
     * @return the feeType
     */
    public boolean isFeeType() {
        return feeType;
    }

    /**
     * @param feeType the feeType to set
     */
    public void setFeeType(boolean feeType) {
        this.feeType = feeType;
    }

    /**
     * @return the reversal
     */
    public boolean isReversal() {
        return reversal;
    }

    /**
     * @param reversal the reversal to set
     */
    public void setReversal(boolean reversal) {
        this.reversal = reversal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return the receipt
     */
    public Receipt getReceipt() {
        if (this.receipt == null) { //there is no need to set a null value here
            return new Receipt();
        }
        return receipt;
    }

    /**
     * @param receipt the receipt to set
     */
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public TxnType getTxnType() {
        return txnType;
    }

    public void setTxnType(TxnType txnType) {
        this.txnType = txnType;
    }

    public SchoolTerm getSchoolTerm() {
        return schoolTerm;
    }

    public void setSchoolTerm(SchoolTerm schoolTerm) {
        this.schoolTerm = schoolTerm;
    }

    /**
     * @return the receiptBalance
     */
    public BigDecimal getReceiptBalance() {
        return receiptBalance;
    }

    /**
     * @param receiptBalance the receiptBalance to set
     */
    public void setReceiptBalance(BigDecimal receiptBalance) {
        this.receiptBalance = receiptBalance;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receiptId != null ? receiptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receipt)) {
            return false;
        }
        Receipt other = (Receipt) object;
        if ((this.receiptId == null && other.receiptId != null) || (this.receiptId != null && !this.receiptId.equals(other.receiptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.Receipt[ receiptId=" + receiptId + " ]";
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
     * @return the tranSource
     */
    public String getTranSource() {
        return tranSource;
    }

    /**
     * @param tranSource the tranSource to set
     */
    public void setTranSource(String tranSource) {
        this.tranSource = tranSource;
    }

    /**
     * @return the mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * @param mobileNo the mobileNo to set
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
