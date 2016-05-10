/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
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

/**
 *
 * @author Michael
 * @since Jul 30, 2013
 * @company Okmich Ltd.
 */
@Entity
@Table(name = "fee_receipt")
public class FeeReceipt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fee_receipt_id")
    private Integer feeReceiptId;
    @Basic(optional = false)
    @Column(name = "amount")
    private BigDecimal amount;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Column(name = "school_term_id")
    @Basic(optional = false)
    private Integer schoolTermId;
    @JoinColumn(name = "fee_group_id", referencedColumnName = "fee_group_id")
    @ManyToOne(optional = false)
    private FeeGroup feeGroup;
    @JoinColumn(name = "school_student_id", referencedColumnName = "school_student_id")
    @ManyToOne(optional = false)
    private SchoolStudent schoolStudent;
    @JoinColumn(name = "receipt_id", referencedColumnName = "receipt_id")
    @ManyToOne(optional = false)
    private Receipt receipt;
    @Column(name = "narration")
    private String narration;

    public FeeReceipt() {
        this.feeGroup = new FeeGroup();
        this.receipt = new Receipt();
        this.schoolStudent = new SchoolStudent();
    }

    public FeeReceipt(Integer feeReceiptId) {
        this();
        this.feeReceiptId = feeReceiptId;
    }

    public Integer getFeeReceiptId() {
        return feeReceiptId;
    }

    public void setFeeReceiptId(Integer feeReceiptId) {
        this.feeReceiptId = feeReceiptId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
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
     * @return the schoolTermId
     */
    public Integer getSchoolTermId() {
        return schoolTermId;
    }

    /**
     * @param schoolTermId the schoolTermId to set
     */
    public void setSchoolTermId(Integer schoolTermId) {
        this.schoolTermId = schoolTermId;
    }

    /**
     * @return the feeGroup
     */
    public FeeGroup getFeeGroup() {
        return feeGroup;
    }

    /**
     * @param feeGroup the feeGroup to set
     */
    public void setFeeGroup(FeeGroup feeGroup) {
        this.feeGroup = feeGroup;
    }

    /**
     * @return the receipt
     */
    public Receipt getReceipt() {
        return receipt;
    }

    /**
     * @param receipt the receipt to set
     */
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    /**
     * @return the schoolStudent
     */
    public SchoolStudent getSchoolStudent() {
        return schoolStudent;
    }

    /**
     * @param schoolStudent the schoolStudent to set
     */
    public void setSchoolStudent(SchoolStudent schoolStudent) {
        this.schoolStudent = schoolStudent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (feeReceiptId != null ? feeReceiptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FeeReceipt)) {
            return false;
        }
        FeeReceipt other = (FeeReceipt) object;
        if ((this.feeReceiptId == null && other.feeReceiptId != null) || (this.feeReceiptId != null && !this.feeReceiptId.equals(other.feeReceiptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.FeeCollReceipt[ feeCollReceiptId=" + feeReceiptId + " ]";
    }
}