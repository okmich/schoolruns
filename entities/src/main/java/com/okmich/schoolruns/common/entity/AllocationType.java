/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "allocation_type")
public class AllocationType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "allocation_type_id")
    private Integer allocationTypeId;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;

    public AllocationType() {
    }

    public AllocationType(Integer allocationTypeId) {
        this.allocationTypeId = allocationTypeId;
    }

    public AllocationType(Integer allocationTypeId, String description, Date modifiedTime, String modifiedBy) {
        this.allocationTypeId = allocationTypeId;
        this.description = description;
        this.modifiedTime = modifiedTime;
        this.modifiedBy = modifiedBy;
    }

    public Integer getAllocationTypeId() {
        return allocationTypeId;
    }

    public void setAllocationTypeId(Integer allocationTypeId) {
        this.allocationTypeId = allocationTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (allocationTypeId != null ? allocationTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AllocationType)) {
            return false;
        }
        AllocationType other = (AllocationType) object;
        if ((this.allocationTypeId == null && other.allocationTypeId != null) || (this.allocationTypeId != null && !this.allocationTypeId.equals(other.allocationTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.AllocationType[ allocationTypeId=" + allocationTypeId + " ]";
    }
}
