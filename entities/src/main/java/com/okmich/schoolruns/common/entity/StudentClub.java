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
@Table(name = "student_club")
public class StudentClub implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "student_club_id")
    private Integer studentClubId;
    @Column(name = "narration")
    private String narration;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "date_joined")
    @Temporal(TemporalType.DATE)
    private Date dateJoined;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @JoinColumn(name = "club_id", referencedColumnName = "club_id")
    @ManyToOne(optional = false)
    private Club club;
    @JoinColumn(name = "school_student_id", referencedColumnName = "school_student_id")
    @ManyToOne(optional = false)
    private SchoolStudent schoolStudent;
    @JoinColumn(name = "school_year_id", referencedColumnName = "school_year_id")
    @ManyToOne(optional = false)
    private SchoolYear schoolYear;

    public StudentClub() {
        this(null);
    }

    public StudentClub(Integer studentClubId) {
        this.studentClubId = studentClubId;
    }

    public Integer getStudentClubId() {
        return studentClubId;
    }

    public void setStudentClubId(Integer studentClubId) {
        this.studentClubId = studentClubId;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
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

    public Club getClub() {
        if (this.club == null) {
            this.club = new Club();
        }
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public SchoolStudent getSchoolStudent() {
        if (this.schoolStudent == null) {
            schoolStudent = new SchoolStudent();
        }
        return schoolStudent;
    }

    public void setSchoolStudent(SchoolStudent schoolStudent) {
        this.schoolStudent = schoolStudent;
    }

    public SchoolYear getSchoolYear() {
        if (this.schoolYear == null) {
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
        hash += (studentClubId != null ? studentClubId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentClub)) {
            return false;
        }
        StudentClub other = (StudentClub) object;
        if ((this.studentClubId == null && other.studentClubId != null) || (this.studentClubId != null && !this.studentClubId.equals(other.studentClubId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.StudentClub[ studentClubId=" + studentClubId + " ]";
    }

    /**
     * @return the dateJoined
     */
    public Date getDateJoined() {
        return dateJoined;
    }

    /**
     * @param dateJoined the dateJoined to set
     */
    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }
}
