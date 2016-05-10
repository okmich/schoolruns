/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "fee_type")
public class FeeType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fee_type_code")
    private Integer feeTypeCode;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    public FeeType() {
    }

    public FeeType(Integer feeTypeCode) {
        this.feeTypeCode = feeTypeCode;
    }

    public FeeType(Integer feeTypeCode, String description) {
        this.feeTypeCode = feeTypeCode;
        this.description = description;
    }

    public Integer getFeeTypeCode() {
        return feeTypeCode;
    }

    public void setFeeTypeCode(Integer feeTypeCode) {
        this.feeTypeCode = feeTypeCode;
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
        hash += (feeTypeCode != null ? feeTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FeeType)) {
            return false;
        }
        FeeType other = (FeeType) object;
        if ((this.feeTypeCode == null && other.feeTypeCode != null) || (this.feeTypeCode != null && !this.feeTypeCode.equals(other.feeTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.FeeType[ feeTypeCode=" + feeTypeCode + " ]";
    }
}
