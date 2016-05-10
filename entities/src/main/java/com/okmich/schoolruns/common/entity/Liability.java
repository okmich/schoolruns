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
import javax.validation.constraints.Min;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "liability")
public class Liability implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "liability_id")
    private Integer liabilityId;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  
    @Min(value = 0)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "assume_date")
    @Temporal(TemporalType.DATE)
    private Date assumeDate;
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
    @JoinColumn(name = "school_id", referencedColumnName = "school_id")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private School school;
    @JoinColumn(name = "liability_type_id", referencedColumnName = "liability_type_id")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private LiabilityType liabilityType;

    public Liability() {
        this.school = new School();
    }

    public Liability(Integer liabilityId) {
        this();
        this.liabilityId = liabilityId;
    }

    public Integer getLiabilityId() {
        return liabilityId;
    }

    public void setLiabilityId(Integer liabilityId) {
        this.liabilityId = liabilityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getAssumeDate() {
        return assumeDate;
    }

    public void setAssumeDate(Date assumeDate) {
        this.assumeDate = assumeDate;
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
     * @return the school
     */
    public School getSchool() {
        return school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(School school) {
        this.school = school;
    }

    /**
     * @return the liabilityType
     */
    public LiabilityType getLiabilityType() {
        if (this.liabilityType == null) {
            this.liabilityType = new LiabilityType();
        }
        return liabilityType;
    }

    /**
     * @param liabilityType the liabilityType to set
     */
    public void setLiabilityType(LiabilityType liabilityType) {
        this.liabilityType = liabilityType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (liabilityId != null ? liabilityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Liability)) {
            return false;
        }
        Liability other = (Liability) object;
        if ((this.liabilityId == null && other.liabilityId != null) || (this.liabilityId != null && !this.liabilityId.equals(other.liabilityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.Liability[ liabilityId=" + liabilityId + " ]";
    }
}
