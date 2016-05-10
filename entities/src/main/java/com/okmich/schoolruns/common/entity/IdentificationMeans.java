/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "identification_means")
public class IdentificationMeans implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "identification_means_id")
    private String identificationMeansId;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    public IdentificationMeans() {
    }

    public IdentificationMeans(String identificationMeansId) {
        this.identificationMeansId = identificationMeansId;
    }

    public IdentificationMeans(String identificationMeansId, String description) {
        this.identificationMeansId = identificationMeansId;
        this.description = description;
    }

    public String getIdentificationMeansId() {
        return identificationMeansId;
    }

    public void setIdentificationMeansId(String identificationMeansId) {
        this.identificationMeansId = identificationMeansId;
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
        hash += (identificationMeansId != null ? identificationMeansId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IdentificationMeans)) {
            return false;
        }
        IdentificationMeans other = (IdentificationMeans) object;
        if ((this.identificationMeansId == null && other.identificationMeansId != null) || (this.identificationMeansId != null && !this.identificationMeansId.equals(other.identificationMeansId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.IdentificationMeans[ identificationMeansId=" + identificationMeansId + " ]";
    }
}
