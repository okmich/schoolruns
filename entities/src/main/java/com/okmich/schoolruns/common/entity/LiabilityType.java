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
@Table(name = "liability_type")
public class LiabilityType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "liability_type_id")
    private Integer liabilityTypeId;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    public LiabilityType() {
    }

    public LiabilityType(Integer liabilityTypeId) {
        this.liabilityTypeId = liabilityTypeId;
    }

    public LiabilityType(Integer liabilityTypeId, String description) {
        this.liabilityTypeId = liabilityTypeId;
        this.description = description;
    }

    public Integer getLiabilityTypeId() {
        return liabilityTypeId;
    }

    public void setLiabilityTypeId(Integer liabilityTypeId) {
        this.liabilityTypeId = liabilityTypeId;
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
        hash += (liabilityTypeId != null ? liabilityTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LiabilityType)) {
            return false;
        }
        LiabilityType other = (LiabilityType) object;
        if ((this.liabilityTypeId == null && other.liabilityTypeId != null) || (this.liabilityTypeId != null && !this.liabilityTypeId.equals(other.liabilityTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.LiabilityType[ liabilityTypeId=" + liabilityTypeId + " ]";
    }
    
}
