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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "exam_batch_class")
public class ExamBatchClass implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_batch_class_id")
    private Integer examBatchClassId;
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
    @JoinColumn(name = "exam_batch_id", referencedColumnName = "exam_batch_id")
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private ExamBatch examBatch;
    @JoinColumn(name = "school_class_id", referencedColumnName = "school_class_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SchoolClass schoolClass;
    @Transient
    private SchoolPref schoolPref;

    public ExamBatchClass() {
        this.examBatch = new ExamBatch();
        this.schoolClass = new SchoolClass();
    }

    /**
     * @return the examBatchClassId
     */
    public Integer getExamBatchClassId() {
        return examBatchClassId;
    }

    /**
     * @param examBatchClassId the examBatchClassId to set
     */
    public void setExamBatchClassId(Integer examBatchClassId) {
        this.examBatchClassId = examBatchClassId;
    }

    /**
     * @return the schoolClass
     */
    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    /**
     * @param schoolClass the schoolClass to set
     */
    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the modifiedTime
     */
    public Date getModifiedTime() {
        return modifiedTime;
    }

    /**
     * @param modifiedTime the modifiedTime to set
     */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * @return the modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @param modifiedBy the modifiedBy to set
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return the examBatch
     */
    public ExamBatch getExamBatch() {
        return examBatch;
    }

    /**
     * @param examBatch the examBatch to set
     */
    public void setExamBatch(ExamBatch examBatch) {
        this.examBatch = examBatch;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.examBatchClassId != null ? this.examBatchClassId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamBatchClass)) {
            return false;
        }
        ExamBatchClass other = (ExamBatchClass) object;
        if ((this.examBatchClassId == null && other.examBatchClassId != null) || (this.examBatchClassId != null
                && !this.examBatchClassId.equals(other.examBatchClassId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.ExamBatchClass[ examBatchId=" + this.examBatchClassId + " ]";
    }

    /**
     * @return the schoolPref
     */
    public SchoolPref getSchoolPref() {
        return schoolPref;
    }

    /**
     * @param schoolPref the schoolPref to set
     */
    public void setSchoolPref(SchoolPref schoolPref) {
        this.schoolPref = schoolPref;
    }
}
