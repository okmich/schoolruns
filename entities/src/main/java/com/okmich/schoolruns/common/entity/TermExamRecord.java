/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "term_exam_record")
public class TermExamRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "term_exam_record_Id")
    private Integer termExamRecordId;
    @Column(name = "perf_description")
    private String perfDescription;
    @Min(value = 0)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "total_term_score")
    private float totalTermScore;
    @Min(value = 0)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "total_credit_weight")
    private int totalcreditWeight;
    @Max(value = 5)
    @Min(value = 0)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "cgpa")
    private float cgpa;
    @Column(name = "class_position")
    private Short classPosition;
    @Column(name = "comments")
    private String comments;
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
    @ManyToOne(optional = false)
    private ExamBatch examBatch;
    @JoinColumn(name = "school_student_id", referencedColumnName = "school_student_id")
    @ManyToOne(optional = false)
    private SchoolStudent schoolStudent;
    @Transient
    private List<TermExamRecordDetails> termExamRecordDetailsList;

    /**
     *
     */
    public TermExamRecord() {
        this.schoolStudent = new SchoolStudent();
        this.examBatch = new ExamBatch();
    }

    public TermExamRecord(Integer termexamrecordId) {
        this();
        this.termExamRecordId = termexamrecordId;
    }

    public Integer getTermExamRecordId() {
        return termExamRecordId;
    }

    public void setTermExamRecordId(Integer termexamrecordId) {
        this.termExamRecordId = termexamrecordId;
    }

    public String getPerfDescription() {
        return perfDescription;
    }

    public void setPerfDescription(String perfDescription) {
        this.perfDescription = perfDescription;
    }

    /**
     * @return the totalTermScore
     */
    public float getTotalTermScore() {
        return totalTermScore;
    }

    /**
     * @param totalTermScore the totalTermScore to set
     */
    public void setTotalTermScore(float totalTermScore) {
        this.totalTermScore = totalTermScore;
    }

    /**
     * @return the totalcreditWeight
     */
    public int getTotalcreditWeight() {
        return totalcreditWeight;
    }

    /**
     * @param totalcreditWeight the totalcreditWeight to set
     */
    public void setTotalcreditWeight(int totalcreditWeight) {
        this.totalcreditWeight = totalcreditWeight;
    }

    public float getCgpa() {
        return cgpa;
    }

    public void setCgpa(float cgpa) {
        this.cgpa = cgpa;
    }

    public Short getClassPosition() {
        return classPosition;
    }

    public void setClassPosition(Short classPosition) {
        this.classPosition = classPosition;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    /**
     * @return the schoolStudent
     */
    public SchoolStudent getSchoolStudent() {
        return schoolStudent;
    }

    /**
     * @param schoolStudent the schoolStudent to set
     */
    public void setSchoolStudent(SchoolStudent schoolStudent) {
        this.schoolStudent = schoolStudent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (termExamRecordId != null ? termExamRecordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TermExamRecord)) {
            return false;
        }
        TermExamRecord other = (TermExamRecord) object;
        if ((this.termExamRecordId == null && other.termExamRecordId != null) || (this.termExamRecordId != null && !this.termExamRecordId.equals(other.termExamRecordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.TermExamRecord[ termexamrecordId=" + termExamRecordId + " ]";
    }

    /**
     * @return the termExamRecordDetailsList
     */
    public List<TermExamRecordDetails> getTermExamRecordDetailsList() {
        return termExamRecordDetailsList;
    }

    /**
     * @param termExamRecordDetailsList the termExamRecordDetailsList to set
     */
    public void setTermExamRecordDetailsList(List<TermExamRecordDetails> termExamRecordDetailsList) {
        this.termExamRecordDetailsList = termExamRecordDetailsList;
    }
}
