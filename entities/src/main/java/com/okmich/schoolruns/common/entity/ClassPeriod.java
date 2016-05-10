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
@Table(name = "class_period")
public class ClassPeriod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "class_period_id")
    private Integer classPeriodId;
    @Basic(optional = false)
    @Column(name = "period_number")
    private String periodNumber;
    @Basic(optional = false)
    @Column(name = "start_time")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Basic(optional = false)
    @Column(name = "end_time")
    @Temporal(TemporalType.TIME)
    private Date endTime;
    @Column(name = "break_time")
    private boolean breakTime;
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
    @Column(name = "school_section_id")
    @Basic(optional = false)
    private Integer schoolSectionId;

    public ClassPeriod() {
    }

    public ClassPeriod(Integer classPeriodId) {
        this.classPeriodId = classPeriodId;
    }

    public Integer getClassPeriodId() {
        return classPeriodId;
    }

    public void setClassPeriodId(Integer classPeriodId) {
        this.classPeriodId = classPeriodId;
    }

    public String getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(String periodNumber) {
        this.periodNumber = periodNumber;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classPeriodId != null ? classPeriodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClassPeriod)) {
            return false;
        }
        ClassPeriod other = (ClassPeriod) object;
        if ((this.classPeriodId == null && other.classPeriodId != null)
                || (this.classPeriodId != null && !this.classPeriodId.equals(other.classPeriodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.ClassPeriod[ classPeriodId=" + classPeriodId + " ]";
    }

    /**
     * @return the schoolSectionId
     */
    public Integer getSchoolSectionId() {
        return schoolSectionId;
    }

    /**
     * @param schoolSectionId the schoolSectionId to set
     */
    public void setSchoolSectionId(Integer schoolSectionId) {
        this.schoolSectionId = schoolSectionId;
    }

    /**
     * @return the breakTime
     */
    public boolean isBreakTime() {
        return breakTime;
    }

    /**
     * @param breakTime the breakTime to set
     */
    public void setBreakTime(boolean breakTime) {
        this.breakTime = breakTime;
    }
}
