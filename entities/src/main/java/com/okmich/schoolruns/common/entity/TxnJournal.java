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

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "txn_journal")
public class TxnJournal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "txn_journal_id")
    private Integer txnJournalId;
    @Basic(optional = false)
    @Column(name = "narration")
    private String narration;
    @Basic(optional = false)
    @Column(name = "txn_date")
    @Temporal(TemporalType.DATE)
    private Date txnDate;
    @Basic(optional = false)
    @Column(name = "effective_date")
    @Temporal(TemporalType.DATE)
    private Date effectiveDate;
    @Column(name = "txn_ref")
    private String txnRef;
    @Column(name = "alt_ref")
    private String altRef;
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
    @Basic(optional = false)
    @Column(name = "school_id")
    private Integer schoolId;
    @JoinColumn(name = "txn_type_id", referencedColumnName = "txn_type_id")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private TxnType txnType;
    private transient BigDecimal amount;

    public TxnJournal() {
        this.txnType = new TxnType();
    }

    public TxnJournal(Integer txnJournalId) {
        this();
        this.txnJournalId = txnJournalId;
    }

    public Integer getTxnJournalId() {
        return txnJournalId;
    }

    public void setTxnJournalId(Integer txnJournalId) {
        this.txnJournalId = txnJournalId;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
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

    public String getTxnRef() {
        return txnRef;
    }

    public void setTxnRef(String txnRef) {
        this.txnRef = txnRef;
    }

    public String getAltRef() {
        return altRef;
    }

    public void setAltRef(String altRef) {
        this.altRef = altRef;
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
     * @return the txnType
     */
    public TxnType getTxnType() {
        return txnType;
    }

    /**
     * @param txnType the txnType to set
     */
    public void setTxnType(TxnType txnType) {
        this.txnType = txnType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnJournalId != null ? txnJournalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TxnJournal)) {
            return false;
        }
        TxnJournal other = (TxnJournal) object;
        if ((this.txnJournalId == null && other.txnJournalId != null) || (this.txnJournalId != null && !this.txnJournalId.equals(other.txnJournalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.TxnJournal[ txnJournalId=" + txnJournalId + " ]";
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
}
