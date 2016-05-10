/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "club_type")
public class ClubType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "club_type_id")
    private Integer clubTypeId;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    public ClubType() {
    }

    public ClubType(Integer clubId) {
        this.clubTypeId = clubId;
    }

    public ClubType(Integer clubId, String description) {
        this.clubTypeId = clubId;
        this.description = description;
    }

    public Integer getClubTypeId() {
        return clubTypeId;
    }

    public void setClubTypeId(Integer clubTypeId) {
        this.clubTypeId = clubTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clubTypeId != null ? clubTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClubType)) {
            return false;
        }
        ClubType other = (ClubType) object;
        if ((this.clubTypeId == null && other.clubTypeId != null)
                || (this.clubTypeId != null && !this.clubTypeId.equals(other.clubTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.ClubType[ clubTypeId=" + clubTypeId + " ]";
    }
}
