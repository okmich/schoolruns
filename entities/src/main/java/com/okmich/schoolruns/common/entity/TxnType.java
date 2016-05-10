/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "txn_type")
public class TxnType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "txn_type_id")
    private Integer txnTypeId;
    @Column(name = "school_id")
    @Basic(optional = false)
    private Integer schoolId;
    @Basic(optional = false)
    @Column(name = "txn_type_code")
    private String txnTypeCode;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
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
    @JoinColumn(name = "db_account_id", referencedColumnName = "account_id")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Account debitAccount;
    @JoinColumn(name = "cr_account_id", referencedColumnName = "account_id")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Account creditAccount;
    @JoinColumn(name = "txn_category_code", referencedColumnName = "txn_category_code")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private TxnCategory txnCategory;

    /**
     *
     */
    public TxnType() {
        this.txnCategory = new TxnCategory();
    }

    public TxnType(Integer txnTypeId) {
        this();
        this.txnTypeId = txnTypeId;
    }

    public Integer getTxnTypeId() {
        return txnTypeId;
    }

    public void setTxnTypeId(Integer txnTypeId) {
        this.txnTypeId = txnTypeId;
    }

    public String getTxnTypeCode() {
        return txnTypeCode;
    }

    public void setTxnTypeCode(String txnTypeCode) {
        this.txnTypeCode = txnTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
     * @return the debitAccount
     */
    public Account getDebitAccount() {
        if (debitAccount == null) {
            this.debitAccount = new Account();
        }
        return debitAccount;
    }

    /**
     * @param debitAccount the debitAccount to set
     */
    public void setDebitAccount(Account debitAccount) {
        this.debitAccount = debitAccount;
    }

    /**
     * @return the creditAccount
     */
    public Account getCreditAccount() {
        if (creditAccount == null) {
            this.creditAccount = new Account();
        }
        return creditAccount;
    }

    /**
     * @param creditAccount the creditAccount to set
     */
    public void setCreditAccount(Account creditAccount) {
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
     * @return the txnCategory
     */
    public TxnCategory getTxnCategory() {
        return txnCategory;
    }

    /**
     * @param txnCategory the txnCategory to set
     */
    public void setTxnCategory(TxnCategory txnCategory) {
        this.txnCategory = txnCategory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnTypeId != null ? txnTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TxnType)) {
            return false;
        }
        TxnType other = (TxnType) object;
        if ((this.txnTypeId == null && other.txnTypeId != null) || (this.txnTypeId != null && !this.txnTypeId.equals(other.txnTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.TxnType[ txnTypeId=" + txnTypeId + " ]";
    }

    @PrePersist
    @PreUpdate
    protected void trigger() {
        if (getCreditAccount().getAccountId() == null
                || getCreditAccount().getAccountId() == 0) {
            setCreditAccount(null);
        }
        if (getDebitAccount().getAccountId() == null
                || getDebitAccount().getAccountId() == 0) {
            setDebitAccount(null);
        }
    }
}
