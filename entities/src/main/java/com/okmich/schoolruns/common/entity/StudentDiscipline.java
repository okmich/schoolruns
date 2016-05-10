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
@Table(name = "student_discipline")
public class StudentDiscipline implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "student_discipline_id")
    private Integer studentDisciplineId;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Column(name = "comment")
    private String comment;
    @Column(name = "date_committed")
    @Temporal(TemporalType.DATE)
    private Date dateCommitted;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @JoinColumn(name = "warning_level_id", referencedColumnName = "warning_level_id")
    @ManyToOne(optional = false)
    private WarningLevel warningLevel;
    @JoinColumn(name = "school_year_id", referencedColumnName = "school_year_id")
    @ManyToOne(optional = false)
    private SchoolYear schoolYear;
    @JoinColumn(name = "school_student_id", referencedColumnName = "school_student_id")
    @ManyToOne(optional = false)
    private SchoolStudent schoolStudent;

    public StudentDiscipline() {
        this(null);
    }

    public StudentDiscipline(Integer studentDisciplineId) {
        this.studentDisciplineId = studentDisciplineId;
        this.schoolStudent = new SchoolStudent();
        this.warningLevel = new WarningLevel();
        this.schoolYear = new SchoolYear();
    }

    public Integer getStudentDisciplineId() {
        return studentDisciplineId;
    }

    public void setStudentDisciplineId(Integer studentDisciplineId) {
        this.studentDisciplineId = studentDisciplineId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the dateCommitted
     */
    public Date getDateCommitted() {
        return dateCommitted;
    }

    /**
     * @param dateCommitted the dateCommitted to set
     */
    public void setDateCommitted(Date dateCommitted) {
        this.dateCommitted = dateCommitted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public WarningLevel getWarningLevel() {
        if (this.warningLevel == null) {
            this.warningLevel = new WarningLevel();
        }
        return warningLevel;
    }

    public void setWarningLevel(WarningLevel warningLevel) {
        this.warningLevel = warningLevel;
    }

    public SchoolYear getSchoolYear() {
        if (this.schoolYear == null) {
            this.schoolYear = new SchoolYear();
        }
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

    public SchoolStudent getSchoolStudent() {
        if (this.schoolStudent == null) {
            this.schoolStudent = new SchoolStudent();
        }
        return schoolStudent;
    }

    public void setSchoolStudent(SchoolStudent schoolStudent) {
        this.schoolStudent = schoolStudent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentDisciplineId != null ? studentDisciplineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentDiscipline)) {
            return false;
        }
        StudentDiscipline other = (StudentDiscipline) object;
        if ((this.studentDisciplineId == null && other.studentDisciplineId != null)
                || (this.studentDisciplineId != null && !this.studentDisciplineId.equals(other.studentDisciplineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.StudentDiscipline[ studentDisciplineId="
                + studentDisciplineId + " ]";
    }
}
