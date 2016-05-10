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
@Table(name = "student_sport")
public class StudentSport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "student_sport_id")
    private Integer studentSportId;
    @Column(name = "description")
    private String description;
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
    @JoinColumn(name = "school_student_id", referencedColumnName = "school_student_id")
    @ManyToOne(optional = false)
    private SchoolStudent schoolStudent;
    @JoinColumn(name = "sport_category_id", referencedColumnName = "sport_category_id")
    @ManyToOne(optional = false)
    private SportCategory sportCategory;
    @JoinColumn(name = "school_year_id", referencedColumnName = "school_year_id")
    @ManyToOne(optional = false)
    private SchoolYear schoolYear;

    public StudentSport() {
        this(null);
    }

    public StudentSport(Integer studentSportId) {
        this.studentSportId = studentSportId;
    }

    public Integer getStudentSportId() {
        return studentSportId;
    }

    public void setStudentSportId(Integer studentSportId) {
        this.studentSportId = studentSportId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public SchoolStudent getSchoolStudent() {
        if (schoolStudent == null) {
            schoolStudent = new SchoolStudent();
        }
        return schoolStudent;
    }

    public void setSchoolStudent(SchoolStudent schoolStudent) {
        this.schoolStudent = schoolStudent;
    }

    public SportCategory getSportCategory() {
        if (sportCategory == null) {
            sportCategory = new SportCategory();
        }
        return sportCategory;
    }

    public void setSportCategory(SportCategory sportCategory) {
        this.sportCategory = sportCategory;
    }

    public SchoolYear getSchoolYear() {
        if (schoolYear == null) {
            schoolYear = new SchoolYear();
        }
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentSportId != null ? studentSportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentSport)) {
            return false;
        }
        StudentSport other = (StudentSport) object;
        if ((this.studentSportId == null && other.studentSportId != null) || (this.studentSportId != null && !this.studentSportId.equals(other.studentSportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.StudentSport[ studentSportId=" + studentSportId + " ]";
    }
}
