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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "school_subject")
public class SchoolSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "school_subject_id")
    private Integer schoolSubjectId;
    @Column(name = "subject_code")
    private String subjectCode;
    @Basic(optional = false)
    @Column(name = "credit_weight")
    private short creditWeight;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    @ManyToOne(optional = false)
    private Subject subject;
    @JoinColumn(name = "grade_level_id", referencedColumnName = "grade_level_id")
    @ManyToOne(optional = false)
    private GradeLevel gradeLevel;
    @Column(name = "school_id")
    @Basic(optional = false)
    private Integer schoolId;

    public SchoolSubject() {
        this.creditWeight = 1;
    }

    public SchoolSubject(Integer schoolSubjectId) {
        this();
        this.schoolSubjectId = schoolSubjectId;
    }

    public Integer getSchoolSubjectId() {
        return schoolSubjectId;
    }

    public void setSchoolSubjectId(Integer schoolSubjectId) {
        this.schoolSubjectId = schoolSubjectId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    /**
     * @return the creditWeight
     */
    public short getCreditWeight() {
        return creditWeight;
    }

    /**
     * @param creditWeight the creditWeight to set
     */
    public void setCreditWeight(short creditWeight) {
        this.creditWeight = creditWeight;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Subject getSubject() {
        if (this.subject == null) {
            this.subject = new Subject();
        }
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public GradeLevel getGradeLevel() {
        if (this.gradeLevel == null) {
            this.gradeLevel = new GradeLevel();
        }
        return gradeLevel;
    }

    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolSubjectId != null ? schoolSubjectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolSubject)) {
            return false;
        }
        SchoolSubject other = (SchoolSubject) object;
        if ((this.schoolSubjectId == null && other.schoolSubjectId != null)
                || (this.schoolSubjectId != null && !this.schoolSubjectId.equals(other.schoolSubjectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.SchoolSubject[ schoolSubjectId=" + schoolSubjectId + " ]";
    }
}
