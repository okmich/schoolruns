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
@Table(name = "subject_teacher")
public class SubjectTeacher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "subject_teacher_id")
    private Integer subjectTeacherId;
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
    @Column(name = "school_year_id")
    private Integer schoolYearId;
    @JoinColumn(name = "school_subject_id", referencedColumnName = "school_subject_id")
    @ManyToOne(optional = false)
    private SchoolSubject schoolSubject;
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @ManyToOne(optional = false)
    private Employee employee;

    /**
     *
     */
    public SubjectTeacher() {
        this(null);
    }

    public SubjectTeacher(Integer subjectTeacherId) {
        this.subjectTeacherId = subjectTeacherId;
        this.employee = new Employee();
        this.schoolSubject = new SchoolSubject();
    }

    public Integer getSubjectTeacherId() {
        return subjectTeacherId;
    }

    public void setSubjectTeacherId(Integer subjectTeacherId) {
        this.subjectTeacherId = subjectTeacherId;
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
     * @return the schoolYearId
     */
    public Integer getSchoolYearId() {
        return schoolYearId;
    }

    /**
     * @param schoolYearId the schoolYearId to set
     */
    public void setSchoolYearId(Integer schoolYearId) {
        this.schoolYearId = schoolYearId;
    }

    public SchoolSubject getSchoolSubject() {
        return schoolSubject;
    }

    public void setSchoolSubject(SchoolSubject schoolSubject) {
        this.schoolSubject = schoolSubject;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subjectTeacherId != null ? subjectTeacherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SubjectTeacher)) {
            return false;
        }
        SubjectTeacher other = (SubjectTeacher) object;
        if ((this.subjectTeacherId == null && other.subjectTeacherId != null)
                || (this.subjectTeacherId != null
                && !this.subjectTeacherId.equals(other.subjectTeacherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.SubjectTeacher[ subjectTeacherId=" + subjectTeacherId + " ]";
    }
}
