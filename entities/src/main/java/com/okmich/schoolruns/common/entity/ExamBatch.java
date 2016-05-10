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
@Table(name = "exam_batch")
public class ExamBatch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "exam_batch_id")
    private Integer examBatchId;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
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
    @Basic(optional = false)
    @Column(name = "notify_parent_result")
    private boolean notifyParentResult;
    @Basic(optional = false)
    @Column(name = "notify_parent_begin")
    private boolean notifyParentBegin;
    @JoinColumn(name = "school_section_id", referencedColumnName = "school_section_id")
    @ManyToOne(optional = false)
    private SchoolSection schoolSection;
    @JoinColumn(name = "school_term_id", referencedColumnName = "school_term_id")
    @ManyToOne(optional = false)
    private SchoolTerm schoolTerm;
    @JoinColumn(name = "exam_type_code", referencedColumnName = "exam_type_code")
    @ManyToOne(optional = false)
    private ExamType examType;

    public ExamBatch() {
        this.examType = new ExamType();
        this.schoolSection = new SchoolSection();
        this.schoolTerm = new SchoolTerm();
    }

    public ExamBatch(Integer examBatchId) {
        this();
        this.examBatchId = examBatchId;
    }

    public Integer getExamBatchId() {
        return examBatchId;
    }

    public void setExamBatchId(Integer examBatchId) {
        this.examBatchId = examBatchId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public ExamType getExamType() {
        return examType;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
    }

    public SchoolTerm getSchoolTerm() {
        return schoolTerm;
    }

    public void setSchoolTerm(SchoolTerm schoolTerm) {
        this.schoolTerm = schoolTerm;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the notifyParentResult
     */
    public boolean isNotifyParentResult() {
        return notifyParentResult;
    }

    /**
     * @param notifyParentResult the notifyParentResult to set
     */
    public void setNotifyParentResult(boolean notifyParentResult) {
        this.notifyParentResult = notifyParentResult;
    }

    /**
     * @return the notifyParentBegin
     */
    public boolean isNotifyParentBegin() {
        return notifyParentBegin;
    }

    /**
     * @param notifyParentBegin the notifyParentBegin to set
     */
    public void setNotifyParentBegin(boolean notifyParentBegin) {
        this.notifyParentBegin = notifyParentBegin;
    }

    /**
     * @return the schoolSection
     */
    public SchoolSection getSchoolSection() {
        return schoolSection;
    }

    /**
     * @param schoolSection the schoolSection to set
     */
    public void setSchoolSection(SchoolSection schoolSection) {
        this.schoolSection = schoolSection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (examBatchId != null ? examBatchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamBatch)) {
            return false;
        }
        ExamBatch other = (ExamBatch) object;
        if ((this.examBatchId == null && other.examBatchId != null) || (this.examBatchId != null && !this.examBatchId.equals(other.examBatchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.ExamBatch[ examBatchId=" + examBatchId + " ]";
    }

    @PreUpdate
    @PrePersist
    protected void trigger() {
        if (this.schoolSection == null || this.schoolSection.getSchoolSectionId() == null
                || this.schoolSection.getSchoolSectionId() == 0) {
            setSchoolSection(null);
        }
    }
}