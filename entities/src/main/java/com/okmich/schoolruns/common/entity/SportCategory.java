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
@Table(name = "sport_category")
public class SportCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sport_category_id")
    private Integer sportCategoryId;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    public SportCategory() {
    }

    public SportCategory(Integer sportCategoryId) {
        this.sportCategoryId = sportCategoryId;
    }

    public SportCategory(Integer sportCategoryId, String description) {
        this.sportCategoryId = sportCategoryId;
        this.description = description;
    }

    public Integer getSportCategoryId() {
        return sportCategoryId;
    }

    public void setSportCategoryId(Integer sportCategoryId) {
        this.sportCategoryId = sportCategoryId;
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
        hash += (sportCategoryId != null ? sportCategoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SportCategory)) {
            return false;
        }
        SportCategory other = (SportCategory) object;
        if ((this.sportCategoryId == null && other.sportCategoryId != null) || (this.sportCategoryId != null && !this.sportCategoryId.equals(other.sportCategoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.SportCategory[ sportCategoryId=" + sportCategoryId + " ]";
    }
}
