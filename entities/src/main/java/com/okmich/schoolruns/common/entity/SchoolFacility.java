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
@Table(name = "school_facility")
public class SchoolFacility implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "school_facility_id")
    private Integer schoolFacilityId;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @JoinColumn(name = "facility_id", referencedColumnName = "facility_id")
    @ManyToOne(optional = false)
    private Facility facility;
    @JoinColumn(name = "school_id", referencedColumnName = "school_id")
    @ManyToOne(optional = false)
    private School school;

    public SchoolFacility() {
    }

    public SchoolFacility(Integer schoolFacilityId) {
        this.schoolFacilityId = schoolFacilityId;
    }

    public SchoolFacility(Integer schoolFacilityId, Date modifiedTime, String modifiedBy) {
        this.schoolFacilityId = schoolFacilityId;
        this.modifiedTime = modifiedTime;
        this.modifiedBy = modifiedBy;
    }

    public Integer getSchoolFacilityId() {
        return schoolFacilityId;
    }

    public void setSchoolFacilityId(Integer schoolFacilityId) {
        this.schoolFacilityId = schoolFacilityId;
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

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolFacilityId != null ? schoolFacilityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolFacility)) {
            return false;
        }
        SchoolFacility other = (SchoolFacility) object;
        if ((this.schoolFacilityId == null && other.schoolFacilityId != null) || (this.schoolFacilityId != null && !this.schoolFacilityId.equals(other.schoolFacilityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.SchoolFacility[ schoolFacilityId=" + schoolFacilityId + " ]";
    }
}
