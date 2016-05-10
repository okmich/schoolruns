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
@Table(name = "school_section")
public class SchoolSection implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "school_section_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schoolSectionId;
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
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    @ManyToOne
    private Section section;
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @ManyToOne
    private Employee employee;
    @JoinColumn(name = "school_id", referencedColumnName = "school_id")
    @ManyToOne(optional = false)
    private School school;

    public SchoolSection() {
    }

    public SchoolSection(Integer schoolSectionId) {
        this.schoolSectionId = schoolSectionId;
    }

    public Integer getSchoolSectionId() {
        return schoolSectionId;
    }

    public void setSchoolSectionId(Integer schoolSectionId) {
        this.schoolSectionId = schoolSectionId;
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

    public Section getSection() {
        if (section == null) {
            this.section = new Section();
        }
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Employee getEmployee() {
        if (employee == null) {
            return new Employee();
        }
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public School getSchool() {
        if (school == null) {
            this.school = new School();
        }
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolSectionId != null ? schoolSectionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolSection)) {
            return false;
        }
        SchoolSection other = (SchoolSection) object;
        if ((this.schoolSectionId == null && other.schoolSectionId != null) || (this.schoolSectionId != null && !this.schoolSectionId.equals(other.schoolSectionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.SectionHead[ sectionHeadId=" + schoolSectionId + " ]";
    }
}
