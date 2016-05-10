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
@Table(name = "assignment")
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "assignment_id")
    private Integer assignmentId;
    @Column(name = "description")
    private String description;
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Column(name = "submission_date")
    @Temporal(TemporalType.DATE)
    private Date submissionDate;
    @Column(name = "max_score")
    private short maxScore;
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
    @Basic(optional = false)
    @Column(name = "exclude_ca")
    private boolean excludeCa = true;
    @JoinColumn(name = "school_term_id", referencedColumnName = "school_term_id")
    @ManyToOne(optional = false)
    private SchoolTerm schoolTerm;
    @JoinColumn(name = "school_subject_id", referencedColumnName = "school_subject_id")
    @ManyToOne(optional = false)
    private SchoolSubject schoolSubject;
    @JoinColumn(name = "grade_level_id", referencedColumnName = "grade_level_id")
    @ManyToOne(optional = false)
    private GradeLevel gradeLevel;
    @JoinColumn(name = "school_class_id", referencedColumnName = "school_class_id")
    @ManyToOne
    private SchoolClass schoolClass;
    @JoinColumn(name = "assignment_type_code", referencedColumnName = "assignment_type_code")
    @ManyToOne(optional = false)
    private AssignmentType assignmentType;

    public Assignment() {
        this.assignmentType = new AssignmentType();
        this.schoolSubject = new SchoolSubject();
        this.schoolTerm = new SchoolTerm();
        this.gradeLevel = new GradeLevel();
        this.createDate = new Date();
    }

    public Assignment(Integer assignmentId) {
        this();
        this.assignmentId = assignmentId;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
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
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the maxScore
     */
    public short getMaxScore() {
        return maxScore;
    }

    /**
     * @param maxScore the maxScore to set
     */
    public void setMaxScore(short maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * @return the gradeLevel
     */
    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }

    /**
     * @param gradeLevel the gradeLevel to set
     */
    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
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
     * @return the excludeCa
     */
    public boolean isExcludeCa() {
        return excludeCa;
    }

    /**
     * @param excludeCa the excludeCa to set
     */
    public void setExcludeCa(boolean excludeCa) {
        this.excludeCa = excludeCa;
    }

    public SchoolTerm getSchoolTerm() {
        return schoolTerm;
    }

    public void setSchoolTerm(SchoolTerm schoolTerm) {
        this.schoolTerm = schoolTerm;
    }

    /**
     * @return the schoolClass
     */
    public SchoolClass getSchoolClass() {
        if (this.schoolClass == null) {
            this.schoolClass = new SchoolClass();
        }
        return schoolClass;
    }

    /**
     * @param schoolClass the schoolClass to set
     */
    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public SchoolSubject getSchoolSubject() {
        return schoolSubject;
    }

    public void setSchoolSubject(SchoolSubject schoolSubject) {
        this.schoolSubject = schoolSubject;
    }

    public AssignmentType getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(AssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assignmentId != null ? assignmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assignment)) {
            return false;
        }
        Assignment other = (Assignment) object;
        if ((this.assignmentId == null && other.assignmentId != null) || (this.assignmentId != null && !this.assignmentId.equals(other.assignmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.Assignment[ assignmentId=" + assignmentId + " ]";
    }

    @PrePersist
    @PreUpdate
    protected void trigger() {
        if (getSchoolClass().getSchoolClassId() == null) {
            this.schoolClass = null;
        }
    }
}
