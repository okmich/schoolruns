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
@Table(name = "txn_journal_detail")
public class TxnJournalDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "txn_journal_detail_id")
    private Integer txnJournalDetailId;
    @Basic(optional = false)
    @Column(name = "account_code")
    private String accountCode;
    @Basic(optional = false)
    @Column(name = "dc_sign")
    private String dcSign;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
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
    @JoinColumn(name = "txn_journal_id", referencedColumnName = "txn_journal_id")
    @ManyToOne(optional = false,cascade= CascadeType.REFRESH)
    private TxnJournal txnJournal;

    public TxnJournalDetail() {
        this.txnJournal = new TxnJournal();
    }

    public TxnJournalDetail(Integer txnJournalDetailId) {
        this();
        this.txnJournalDetailId = txnJournalDetailId;
    }

    public Integer getTxnJournalDetailId() {
        return txnJournalDetailId;
    }

    public void setTxnJournalDetailId(Integer txnJournalDetailId) {
        this.txnJournalDetailId = txnJournalDetailId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getDcSign() {
        return dcSign;
    }

    public void setDcSign(String dcSign) {
        this.dcSign = dcSign;
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

    public TxnJournal getTxnJournal() {
        return txnJournal;
    }

    public void setTxnJournal(TxnJournal txnJournal) {
        this.txnJournal = txnJournal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnJournalDetailId != null ? txnJournalDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TxnJournalDetail)) {
            return false;
        }
        TxnJournalDetail other = (TxnJournalDetail) object;
        if ((this.txnJournalDetailId == null && other.txnJournalDetailId != null) || (this.txnJournalDetailId != null && !this.txnJournalDetailId.equals(other.txnJournalDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.TxnJournalDetail[ txnJournalDetailId=" + txnJournalDetailId + " ]";
    }
}
