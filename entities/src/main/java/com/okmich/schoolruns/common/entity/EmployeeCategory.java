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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "employee_category")
public class EmployeeCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "employee_category_id")
    private String employeeCategoryId;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "is_academic")
    private boolean academic;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;

    public EmployeeCategory() {
    }

    public EmployeeCategory(String employeeCategoryId) {
        this.employeeCategoryId = employeeCategoryId;
    }

    public String getEmployeeCategoryId() {
        return employeeCategoryId;
    }

    public void setEmployeeCategoryId(String employeeCategoryId) {
        this.employeeCategoryId = employeeCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAcademic() {
        return academic;
    }

    public void setAcademic(boolean isAcademic) {
        this.academic = isAcademic;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeCategoryId != null ? employeeCategoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeCategory)) {
            return false;
        }
        EmployeeCategory other = (EmployeeCategory) object;
        if ((this.employeeCategoryId == null && other.employeeCategoryId != null) || (this.employeeCategoryId != null && !this.employeeCategoryId.equals(other.employeeCategoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.EmployeeCategory[ employeeCategoryId=" + employeeCategoryId + " ]";
    }
}
