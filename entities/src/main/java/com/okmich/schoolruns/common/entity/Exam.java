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
@Table(name = "exam")
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "exam_id")
    private Integer examId;
    @Basic(optional = false)
    @Column(name = "exam_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date examTime;
    @Basic(optional = false)
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
    @JoinColumn(name = "school_term_id", referencedColumnName = "school_term_id")
    @ManyToOne(optional = false)
    private SchoolTerm schoolTerm;
    @JoinColumn(name = "school_class_id", referencedColumnName = "school_class_id")
    @ManyToOne(optional = false)
    private SchoolClass schoolClass;
    @JoinColumn(name = "school_subject_id", referencedColumnName = "school_subject_id")
    @ManyToOne(optional = false)
    private SchoolSubject schoolSubject;
    @JoinColumn(name = "exam_batch_id", referencedColumnName = "exam_batch_id")
    @ManyToOne(optional = false)
    private ExamBatch examBatch;

    public Exam() {
        this.schoolClass = new SchoolClass();
        this.schoolSubject = new SchoolSubject();
        this.schoolTerm = new SchoolTerm();
        this.examBatch = new ExamBatch();
        this.status = "I";
    }

    public Exam(Integer examId) {
        this.examId = examId;
    }

    public Exam(Integer examId, Date examTime, short maxScore, String status, Date modifiedTime, String modifiedBy) {
        this.examId = examId;
        this.examTime = examTime;
        this.maxScore = maxScore;
        this.status = status;
        this.modifiedTime = modifiedTime;
        this.modifiedBy = modifiedBy;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Date getExamTime() {
        return examTime;
    }

    public void setExamTime(Date examTime) {
        this.examTime = examTime;
    }

    public short getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(short maxScore) {
        this.maxScore = maxScore;
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

    public SchoolTerm getSchoolTerm() {
        return schoolTerm;
    }

    public void setSchoolTerm(SchoolTerm schoolTerm) {
        this.schoolTerm = schoolTerm;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public SchoolSubject getSchoolSubject() {
        return schoolSubject;
    }

    public void setSchoolSubject(SchoolSubject schoolSubject) {
        this.schoolSubject = schoolSubject;
    }

    public ExamBatch getExamBatch() {
        return examBatch;
    }

    public void setExamBatch(ExamBatch examBatch) {
        this.examBatch = examBatch;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (examId != null ? examId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exam)) {
            return false;
        }
        Exam other = (Exam) object;
        if ((this.examId == null && other.examId != null) || (this.examId != null && !this.examId.equals(other.examId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.Exam[ examId=" + examId + " ]";
    }
}
