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
import javax.persistence.FetchType;
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
@Table(name = "employee_attendance")
public class EmployeeAttendance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "employee_attendance_id")
    private Integer employeeAttendanceId;
    @Basic(optional = false)
    @Column(name = "attend_date")
    @Temporal(TemporalType.DATE)
    private Date attendDate;
    @Column(name = "school_term_id")
    private Integer schoolTermId;
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @ManyToOne(optional = false)
    private Employee employee;

    public EmployeeAttendance() {
        this.employee = new Employee();
    }

    public EmployeeAttendance(Integer employeeAttendanceId) {
        this.employeeAttendanceId = employeeAttendanceId;
    }

    public Integer getEmployeeAttendanceId() {
        return employeeAttendanceId;
    }

    public void setEmployeeAttendanceId(Integer employeeAttendanceId) {
        this.employeeAttendanceId = employeeAttendanceId;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeAttendanceId != null ? employeeAttendanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeAttendance)) {
            return false;
        }
        EmployeeAttendance other = (EmployeeAttendance) object;
        if ((this.employeeAttendanceId == null && other.employeeAttendanceId != null) || (this.employeeAttendanceId != null && !this.employeeAttendanceId.equals(other.employeeAttendanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.EmployeeAttendance[ employeeAttendanceId=" + employeeAttendanceId + " ]";
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
