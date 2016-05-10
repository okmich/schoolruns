/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "txn_category")
public class TxnCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "txn_category_code")
    private String txnCategoryCode;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    public TxnCategory() {
    }

    public TxnCategory(String txnCategoryCode) {
        this.txnCategoryCode = txnCategoryCode;
    }

    public TxnCategory(String txnCategoryCode, String description) {
        this.txnCategoryCode = txnCategoryCode;
        this.description = description;
    }

    public String getTxnCategoryCode() {
        return txnCategoryCode;
    }

    public void setTxnCategoryCode(String txnCategoryCode) {
        this.txnCategoryCode = txnCategoryCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txnCategoryCode != null ? txnCategoryCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TxnCategory)) {
            return false;
        }
        TxnCategory other = (TxnCategory) object;
        if ((this.txnCategoryCode == null && other.txnCategoryCode != null) || (this.txnCategoryCode != null && !this.txnCategoryCode.equals(other.txnCategoryCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.TxnCategory[ txnCategoryCode=" + txnCategoryCode + " ]";
    }
}
