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
@Table(name = "emp_edu_qualification")
public class EmpEduQualification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "emp_edu_qualification_id")
    private Integer empEduQualificationId;
    @Column(name = "date_obtained")
    @Temporal(TemporalType.DATE)
    private Date dateObtained;
    @Column(name = "institution")
    private String institution;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @JoinColumn(name = "educational_qualification_id", referencedColumnName = "educational_qual_id")
    @ManyToOne(optional = false)
    private EducationalQualification educationalQualification;
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @ManyToOne(optional = false)
    private Employee employee;

    public EmpEduQualification() {
    }

    public EmpEduQualification(Integer empEduQualificationId) {
        this.empEduQualificationId = empEduQualificationId;
    }

    public Integer getEmpEduQualificationId() {
        return empEduQualificationId;
    }

    public void setEmpEduQualificationId(Integer empEduQualificationId) {
        this.empEduQualificationId = empEduQualificationId;
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

    public EducationalQualification getEducationalQualification() {
        if (educationalQualification == null) {
            this.educationalQualification = new EducationalQualification();
        }
        return educationalQualification;
    }

    public void setEducationalQualification(EducationalQualification educationalQualification) {
        this.educationalQualification = educationalQualification;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empEduQualificationId != null ? empEduQualificationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpEduQualification)) {
            return false;
        }
        EmpEduQualification other = (EmpEduQualification) object;
        if ((this.empEduQualificationId == null && other.empEduQualificationId != null) || (this.empEduQualificationId != null && !this.empEduQualificationId.equals(other.empEduQualificationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.EmpEduQualification[ empEduQualificationId=" + empEduQualificationId + " ]";
    }

    /**
     * @return the dateObtained
     */
    public Date getDateObtained() {
        return dateObtained;
    }

    /**
     * @param dateObtained the dateObtained to set
     */
    public void setDateObtained(Date dateObtained) {
        this.dateObtained = dateObtained;
    }

    /**
     * @return the institution
     */
    public String getInstitution() {
        return institution;
    }

    /**
     * @param institution the institution to set
     */
    public void setInstitution(String institution) {
        this.institution = institution;
    }
}
