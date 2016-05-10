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
@Table(name = "employee_sport")
public class EmployeeSport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "employee_sport_id")
    private Integer employeeSportId;
    @Column(name = "comment")
    private String comment;
    @Column(name = "date_joined")
    @Temporal(TemporalType.DATE)
    private Date dateJoined;
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
    @JoinColumn(name = "school_year_id", referencedColumnName = "school_year_id")
    @ManyToOne(optional = false)
    private SchoolYear schoolYear;
    @JoinColumn(name = "sport_category_id", referencedColumnName = "sport_category_id")
    @ManyToOne(optional = false)
    private SportCategory sportCategory;
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @ManyToOne(optional = false)
    private Employee employee;

    /**
     *
     */
    public EmployeeSport() {
        this.employee = new Employee();
        this.schoolYear = new SchoolYear();
        this.sportCategory = new SportCategory();
    }

    public EmployeeSport(Integer employeeSportId) {
        this.employeeSportId = employeeSportId;
    }

    public Integer getEmployeeSportId() {
        return employeeSportId;
    }

    public void setEmployeeSportId(Integer employeeSportId) {
        this.employeeSportId = employeeSportId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
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

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

    public SportCategory getSportCategory() {
        return sportCategory;
    }

    public void setSportCategory(SportCategory sportCategory) {
        this.sportCategory = sportCategory;
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
        hash += (employeeSportId != null ? employeeSportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeSport)) {
            return false;
        }
        EmployeeSport other = (EmployeeSport) object;
        if ((this.employeeSportId == null && other.employeeSportId != null) || (this.employeeSportId != null && !this.employeeSportId.equals(other.employeeSportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.EmployeeSport[ employeeSportId=" + employeeSportId + " ]";
    }
}
