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
@Table(name = "emp_prof_qualification")
public class EmpProfQualification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "emp_prof_qualification_id")
    private Integer empProfQualificationId;
    @Column(name = "date_obtained")
    @Temporal(TemporalType.DATE)
    private Date dateObtained;
    @Column(name = "institution")
    private String institution;
    @Column(name = "active_member")
    private boolean activeMember;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @JoinColumn(name = "professional_qualification_id", referencedColumnName = "professional_qual_id")
    @ManyToOne(optional = false)
    private ProfessionalQualification professionalQualification;
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @ManyToOne(optional = false)
    private Employee employee;

    public EmpProfQualification() {
        this.professionalQualification = new ProfessionalQualification();
        this.employee = new Employee();
    }

    public EmpProfQualification(Integer empProfQualificationId) {
        this();
        this.empProfQualificationId = empProfQualificationId;
    }

    public Integer getEmpProfQualificationId() {
        return empProfQualificationId;
    }

    public void setEmpProfQualificationId(Integer empProfQualificationId) {
        this.empProfQualificationId = empProfQualificationId;
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

    public ProfessionalQualification getProfessionalQualification() {
        return professionalQualification;
    }

    public void setProfessionalQualification(ProfessionalQualification professionalQualification) {
        this.professionalQualification = professionalQualification;
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
        hash += (empProfQualificationId != null ? empProfQualificationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpProfQualification)) {
            return false;
        }
        EmpProfQualification other = (EmpProfQualification) object;
        if ((this.empProfQualificationId == null && other.empProfQualificationId != null) || (this.empProfQualificationId != null && !this.empProfQualificationId.equals(other.empProfQualificationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.EmpProfQualification[ empProfQualificationId=" + empProfQualificationId + " ]";
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

    /**
     * @return the activeMember
     */
    public boolean isActiveMember() {
        return activeMember;
    }

    /**
     * @param activeMember the activeMember to set
     */
    public void setActiveMember(boolean activeMember) {
        this.activeMember = activeMember;
    }
}
