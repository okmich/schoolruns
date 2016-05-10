/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Michael
 * @since Sep 26, 2013
 * @company Okmich Ltd.
 */
@Entity
@Table(name = "fee_group")
public class FeeGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fee_group_id")
    private Integer feeGroupId;
    @Column(name = "description")
    private String description;
    @Column(name = "statutory")
    private boolean statutory;
    @Column(name = "apply_all")
    private boolean applyAll;
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
    @Column(name = "school_id")
    @Basic(optional = false)
    private Integer schoolId;
    @JoinColumn(name = "fee_type_code", referencedColumnName = "fee_type_code")
    @ManyToOne(optional = false)
    private FeeType feeType;
    @JoinColumn(name = "grade_level_id", referencedColumnName = "grade_level_id")
    @ManyToOne
    private GradeLevel gradeLevel;
    @JoinTable(name = "fee_grouping", joinColumns = {
        @JoinColumn(name = "fee_group_id", referencedColumnName = "fee_group_id")}, inverseJoinColumns = {
        @JoinColumn(name = "fee_id", referencedColumnName = "fee_id")})
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private List<Fee> feeList;
    @Transient
    private BigDecimal totalFee;

    public FeeGroup() {
    }

    public FeeGroup(Integer feeGroupId) {
        this.feeGroupId = feeGroupId;
    }

    public Integer getFeeGroupId() {
        return feeGroupId;
    }

    public void setFeeGroupId(Integer feeGroupId) {
        this.feeGroupId = feeGroupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatutory() {
        return statutory;
    }

    public void setStatutory(boolean statutory) {
        this.statutory = statutory;
    }

    public boolean isApplyAll() {
        return applyAll;
    }

    public void setApplyAll(boolean applyAll) {
        this.applyAll = applyAll;
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

    public List<Fee> getFeeList() {
        if (this.feeList == null) {
            this.feeList = new ArrayList<>();
        }
        return feeList;
    }

    public void setFeeList(List<Fee> feeList) {
        this.feeList = feeList;
    }

    public FeeType getFeeType() {
        if (feeType == null) {
            this.feeType = new FeeType();
        }
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public GradeLevel getGradeLevel() {
        if (gradeLevel == null) {
            this.gradeLevel = new GradeLevel();
        }
        return gradeLevel;
    }

    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (feeGroupId != null ? feeGroupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FeeGroup)) {
            return false;
        }
        FeeGroup other = (FeeGroup) object;
        if ((this.feeGroupId == null && other.feeGroupId != null) || (this.feeGroupId != null && !this.feeGroupId.equals(other.feeGroupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.FeeGroup[ feeGroupId=" + feeGroupId + " ]";
    }

    /**
     *
     */
    protected void trigger() {
        if (this.gradeLevel == null
                || this.gradeLevel.getGradeLevelId() == null) {
            this.gradeLevel = null;
        }
    }

    /**
     * @return the totalFee
     */
    public BigDecimal getTotalFee() {
        return totalFee;
    }

    /**
     * @param totalFee the totalFee to set
     */
    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }
}
