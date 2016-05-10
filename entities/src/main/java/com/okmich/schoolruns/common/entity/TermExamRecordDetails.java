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
import javax.validation.constraints.Min;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "term_exam_record_details")
public class TermExamRecordDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "term_exam_record_details_id")
    private Integer termExamRecordDetailsId;
    @Min(value = 0)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "avg_assignment_score")
    private double avgAssignmentScore;
    @Basic(optional = false)
    @Column(name = "final_score")
    private double finalScore;
    @Column(name = "total_credit")
    private Short totalCredit;
    @Column(name = "comment")
    private String comment;
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
    @JoinColumn(name = "exam_score_id", referencedColumnName = "exam_score_id")
    @ManyToOne(optional = false)
    private ExamScore examScore;
    @JoinColumn(name = "term_exam_record_id", referencedColumnName = "term_exam_record_Id")
    @ManyToOne(optional = false)
    private TermExamRecord termExamRecord;
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @ManyToOne(optional = false)
    private Employee employee;
    @JoinColumn(name = "school_subject_id", referencedColumnName = "school_subject_id")
    @ManyToOne(optional = false)
    private SchoolSubject schoolSubject;
    @JoinColumn(name = "grade_band_code", referencedColumnName = "grade_band_code")
    @ManyToOne(optional = false)
    private GradeBand gradeBand;

    public TermExamRecordDetails() {
        this.gradeBand = new GradeBand();
        this.schoolSubject = new SchoolSubject();
        this.examScore = new ExamScore();
        this.employee = new Employee();
        this.termExamRecord = new TermExamRecord();
    }

    public TermExamRecordDetails(Integer termExamRecordDetailsId) {
        this();
        this.termExamRecordDetailsId = termExamRecordDetailsId;
    }

    public Integer getTermExamRecordDetailsId() {
        return termExamRecordDetailsId;
    }

    public void setTermExamRecordDetailsId(Integer termExamRecordDetailsId) {
        this.termExamRecordDetailsId = termExamRecordDetailsId;
    }

    public double getAvgAssignmentScore() {
        return avgAssignmentScore;
    }

    public void setAvgAssignmentScore(double avgAssignmentScore) {
        this.avgAssignmentScore = avgAssignmentScore;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public Short getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(Short totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public ExamScore getExamScore() {
        return examScore;
    }

    public void setExamScore(ExamScore examScore) {
        this.examScore = examScore;
    }

    public TermExamRecord getTermExamRecord() {
        return termExamRecord;
    }

    public void setTermExamRecord(TermExamRecord termExamRecord) {
        this.termExamRecord = termExamRecord;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public SchoolSubject getSchoolSubject() {
        return schoolSubject;
    }

    public void setSchoolSubject(SchoolSubject schoolSubject) {
        this.schoolSubject = schoolSubject;
    }

    public GradeBand getGradeBand() {
        return gradeBand;
    }

    public void setGradeBand(GradeBand gradeBand) {
        this.gradeBand = gradeBand;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (termExamRecordDetailsId != null ? termExamRecordDetailsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TermExamRecordDetails)) {
            return false;
        }
        TermExamRecordDetails other = (TermExamRecordDetails) object;
        if ((this.termExamRecordDetailsId == null && other.termExamRecordDetailsId != null) || (this.termExamRecordDetailsId != null && !this.termExamRecordDetailsId.equals(other.termExamRecordDetailsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.TermExamRecordDetails[ termExamRecordDetailsId=" + termExamRecordDetailsId + " ]";
    }
}
