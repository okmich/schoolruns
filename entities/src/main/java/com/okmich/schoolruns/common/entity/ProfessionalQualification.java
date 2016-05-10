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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "professional_qualification")
@NamedQueries({
    @NamedQuery(name = "ProfessionalQualification.findAll", query = "SELECT p FROM ProfessionalQualification p")})
public class ProfessionalQualification implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "professional_qual_id")
    private Integer professionalQualId;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;

    public ProfessionalQualification() {
    }

    public ProfessionalQualification(Integer professionalQualId) {
        this.professionalQualId = professionalQualId;
    }

    public ProfessionalQualification(Integer professionalQualId, String description, Date modifiedTime, String modifiedBy) {
        this.professionalQualId = professionalQualId;
        this.description = description;
        this.modifiedTime = modifiedTime;
        this.modifiedBy = modifiedBy;
    }

    public Integer getProfessionalQualId() {
        return professionalQualId;
    }

    public void setProfessionalQualId(Integer professionalQualId) {
        this.professionalQualId = professionalQualId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (professionalQualId != null ? professionalQualId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProfessionalQualification)) {
            return false;
        }
        ProfessionalQualification other = (ProfessionalQualification) object;
        if ((this.professionalQualId == null && other.professionalQualId != null) || (this.professionalQualId != null && !this.professionalQualId.equals(other.professionalQualId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.ProfessionalQualification[ professionalQualId=" + professionalQualId + " ]";
    }
    
}
