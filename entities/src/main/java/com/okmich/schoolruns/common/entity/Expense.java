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
 * @since Aug 4, 2013
 * @company Okmich Ltd.
 */
@Entity
@Table(name = "expense")
public class Expense implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "expense_id")
    private Integer expenseId;
    @Basic(optional = false)
    @Column(name = "narration")
    private String narration;
    @Basic(optional = false)
    @Column(name = "txn_date")
    @Temporal(TemporalType.DATE)
    private Date txnDate;
    @Basic(optional = false)
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "txn_ref")
    private String txnRef;
    @Column(name = "reversal")
    private boolean reversal;
    @Column(name = "prev_expense_id")
    private Integer prevExpenseId;
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
    @JoinColumn(name = "school_term_id", referencedColumnName = "school_term_id")
    @ManyToOne(optional = false)
    private SchoolTerm schoolTerm;
    @JoinColumn(name = "txn_type_id", referencedColumnName = "txn_type_id")
    @ManyToOne(optional = false)
    private TxnType txnType;

    public Expense() {
        this.schoolTerm = new SchoolTerm();
        this.txnType = new TxnType();
    }

    public Expense(Integer expenseId) {
        this();
        this.expenseId = expenseId;
    }

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public TxnType getTxnType() {
        return txnType;
    }

    public void setTxnType(TxnType txnType) {
        this.txnType = txnType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (expenseId != null ? expenseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Expense)) {
            return false;
        }
        Expense other = (Expense) object;
        if ((this.expenseId == null && other.expenseId != null) || (this.expenseId != null && !this.expenseId.equals(other.expenseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.Expense[ expenseId=" + expenseId + " ]";
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
     * @return the prevExpenseId
     */
    public Integer getPrevExpenseId() {
        return prevExpenseId;
    }

    /**
     * @param prevExpenseId the prevExpenseId to set
     */
    public void setPrevExpenseId(Integer prevExpenseId) {
        this.prevExpenseId = prevExpenseId;
    }

    /**
     * @return the schoolTerm
     */
    public SchoolTerm getSchoolTerm() {
        return schoolTerm;
    }

    /**
     * @param schoolTerm the schoolTerm to set
     */
    public void setSchoolTerm(SchoolTerm schoolTerm) {
        this.schoolTerm = schoolTerm;
    }
}