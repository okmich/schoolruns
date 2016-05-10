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
@Table(name = "school_class_teacher")
public class SchoolClassTeacher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "school_class_teacher_id")
    private Integer schoolClassTeacherId;
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
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @ManyToOne(optional = false)
    private Employee employee;
    @JoinColumn(name = "school_year_id", referencedColumnName = "school_year_id")
    @ManyToOne(optional = false)
    private SchoolYear schoolYear;
    @JoinColumn(name = "school_class_id", referencedColumnName = "school_class_id")
    @ManyToOne(optional = false)
    private SchoolClass schoolClass;
    @JoinColumn(name = "allocation_type_id", referencedColumnName = "allocation_type_id")
    @ManyToOne(optional = false)
    private AllocationType allocationType;

    public SchoolClassTeacher() {
    }

    public SchoolClassTeacher(Integer schoolClassTeacherId) {
        this.schoolClassTeacherId = schoolClassTeacherId;
    }

    public Integer getSchoolClassTeacherId() {
        return schoolClassTeacherId;
    }

    public void setSchoolClassTeacherId(Integer schoolClassTeacherId) {
        this.schoolClassTeacherId = schoolClassTeacherId;
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

    public Employee getEmployee() {
        if (employee == null) {
            this.employee = new Employee();
        }
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public SchoolYear getSchoolYear() {
        if (schoolYear == null) {
            this.schoolYear = new SchoolYear();
        }
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear1) {
        this.schoolYear = schoolYear1;
    }

    public SchoolClass getSchoolClass() {
        if (schoolClass == null) {
            this.schoolClass = new SchoolClass();
        }
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public AllocationType getAllocationType() {
        if (allocationType == null) {
            this.allocationType = new AllocationType();
        }
        return allocationType;
    }

    public void setAllocationType(AllocationType allocationType) {
        this.allocationType = allocationType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolClassTeacherId != null ? schoolClassTeacherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolClassTeacher)) {
            return false;
        }
        SchoolClassTeacher other = (SchoolClassTeacher) object;
        if ((this.schoolClassTeacherId == null && other.schoolClassTeacherId != null)
                || (this.schoolClassTeacherId != null
                && !this.schoolClassTeacherId.equals(other.schoolClassTeacherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.SchoolClassTeacher[ schoolClassTeacherId="
                + schoolClassTeacherId + " ]";
    }
}
