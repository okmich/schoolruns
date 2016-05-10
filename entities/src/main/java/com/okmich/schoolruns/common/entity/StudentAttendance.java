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
@Table(name = "student_attendance")
public class StudentAttendance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "student_attendance_id")
    private Integer studentAttendanceId;
    @Basic(optional = false)
    @Column(name = "attend_date")
    @Temporal(TemporalType.DATE)
    private Date attendDate;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Column(name = "school_term_id")
    @Basic(optional = false)
    private Integer schoolTermId;
    @JoinColumn(name = "school_student_id", referencedColumnName = "school_student_id")
    @ManyToOne(optional = false)
    private SchoolStudent schoolStudent;

    public StudentAttendance() {
        this.schoolStudent = new SchoolStudent();
    }

    public StudentAttendance(Integer studentAttendanceId) {
        this.studentAttendanceId = studentAttendanceId;
    }

    public Integer getStudentAttendanceId() {
        return studentAttendanceId;
    }

    public void setStudentAttendanceId(Integer studentAttendanceId) {
        this.studentAttendanceId = studentAttendanceId;
    }

    public Date getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(Date attendDate) {
        this.attendDate = attendDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SchoolStudent getSchoolStudent() {
        return schoolStudent;
    }

    public void setSchoolStudent(SchoolStudent schoolStudent) {
        this.schoolStudent = schoolStudent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentAttendanceId != null ? studentAttendanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StudentAttendance)) {
            return false;
        }
        StudentAttendance other = (StudentAttendance) object;
        if ((this.studentAttendanceId == null && other.studentAttendanceId != null)
                || (this.studentAttendanceId != null
                && !this.studentAttendanceId.equals(other.studentAttendanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.StudentAttendance[ studentAttendanceId="
                + studentAttendanceId + " ]";
    }

    /**
     * @return the schoolTermId
     */
    public Integer getSchoolTermId() {
        return schoolTermId;
    }

    /**
     * @param schoolTermId the schoolTermId to set
     */
    public void setSchoolTermId(Integer schoolTermId) {
        this.schoolTermId = schoolTermId;
    }
}
