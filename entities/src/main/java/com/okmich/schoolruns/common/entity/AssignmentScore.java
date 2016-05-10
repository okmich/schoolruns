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
@Table(name = "assignment_score")
public class AssignmentScore implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "assignment_score_id")
    private Integer assignmentScoreId;
    @Basic(optional = false)
    @Column(name = "score")
    private short score;
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
    @JoinColumn(name = "school_student_id", referencedColumnName = "school_student_id")
    @ManyToOne(optional = false)
    private SchoolStudent schoolStudent;
    @JoinColumn(name = "assignment_id", referencedColumnName = "assignment_id")
    @ManyToOne(optional = false)
    private Assignment assignment;

    public AssignmentScore() {
        this.schoolStudent = new SchoolStudent();
        this.assignment = new Assignment();
        this.score = 0;
    }

    public AssignmentScore(Integer assignmentScoreId) {
        this.assignmentScoreId = assignmentScoreId;
    }

    public AssignmentScore(Integer assignmentScoreId, short score, String status, Date modifiedTime, String modifiedBy) {
        this.assignmentScoreId = assignmentScoreId;
        this.score = score;
        this.status = status;
        this.modifiedTime = modifiedTime;
        this.modifiedBy = modifiedBy;
    }

    public Integer getAssignmentScoreId() {
        return assignmentScoreId;
    }

    public void setAssignmentScoreId(Integer assignmentScoreId) {
        this.assignmentScoreId = assignmentScoreId;
    }

    public short getScore() {
        return score;
    }

    public void setScore(short score) {
        this.score = score;
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

    public SchoolStudent getSchoolStudent() {
        return schoolStudent;
    }

    public void setSchoolStudent(SchoolStudent schoolStudent) {
        this.schoolStudent = schoolStudent;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assignmentScoreId != null ? assignmentScoreId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssignmentScore)) {
            return false;
        }
        AssignmentScore other = (AssignmentScore) object;
        if ((this.assignmentScoreId == null && other.assignmentScoreId != null) || (this.assignmentScoreId != null && !this.assignmentScoreId.equals(other.assignmentScoreId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[assignmentScoreId=" + assignmentScoreId + ", scores=" + score + " ]";
    }
}
