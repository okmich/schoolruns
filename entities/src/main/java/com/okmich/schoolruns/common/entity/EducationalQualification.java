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
@Table(name = "educational_qualification")
@NamedQueries({
    @NamedQuery(name = "EducationalQualification.findAll", query = "SELECT e FROM EducationalQualification e")})
public class EducationalQualification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "educational_qual_id")
    private String educationalQualId;
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

    public EducationalQualification() {
    }

    public EducationalQualification(String educationalQualId) {
        this.educationalQualId = educationalQualId;
    }

    public EducationalQualification(String educationalQualId, String description, Date modifiedTime, String modifiedBy) {
        this.educationalQualId = educationalQualId;
        this.description = description;
        this.modifiedTime = modifiedTime;
        this.modifiedBy = modifiedBy;
    }

    public String getEducationalQualId() {
        return educationalQualId;
    }

    public void setEducationalQualId(String educationalQualId) {
        this.educationalQualId = educationalQualId;
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
        hash += (educationalQualId != null ? educationalQualId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EducationalQualification)) {
            return false;
        }
        EducationalQualification other = (EducationalQualification) object;
        if ((this.educationalQualId == null && other.educationalQualId != null) || (this.educationalQualId != null && !this.educationalQualId.equals(other.educationalQualId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.EducationalQualification[ educationalQualId=" + educationalQualId + " ]";
    }
}
