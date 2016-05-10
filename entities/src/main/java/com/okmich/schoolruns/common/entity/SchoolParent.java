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
 * @since Dec 5, 2013
 * @company Okmich Ltd.
 */
@Entity
@Table(name = "school_parent")
public class SchoolParent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "school_parent_id")
    private Integer schoolParentId;
    @Basic(optional = false)
    @Column(name = "school_id")
    private Integer schoolId;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @JoinColumn(name = "parent_id", referencedColumnName = "phone_number")
    @ManyToOne(optional = false)
    private Parent parent;

    public SchoolParent() {
    }

    public SchoolParent(Integer schoolParentId) {
        this.schoolParentId = schoolParentId;
    }

    public SchoolParent(Parent parent, Integer schoolId) {
        this.parent = parent;
        this.schoolId = schoolId;
        this.modifiedTime = new Date();
    }

    public Integer getSchoolParentId() {
        return schoolParentId;
    }

    public void setSchoolParentId(Integer schoolParentId) {
        this.schoolParentId = schoolParentId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolParentId != null ? schoolParentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolParent)) {
            return false;
        }
        SchoolParent other = (SchoolParent) object;
        if ((this.schoolParentId == null && other.schoolParentId != null) || (this.schoolParentId != null && !this.schoolParentId.equals(other.schoolParentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.SchoolParent[ schoolParentId=" + schoolParentId + " ]";
    }
}
