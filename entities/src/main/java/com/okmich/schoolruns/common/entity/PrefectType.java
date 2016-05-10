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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "prefect_type")
public class PrefectType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "prefect_type_id")
    private Integer prefectTypeId;
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

    public PrefectType() {
    }

    public PrefectType(Integer prefectTypeId) {
        this.prefectTypeId = prefectTypeId;
    }

    public PrefectType(Integer prefectTypeId, String description, Date modifiedTime, String modifiedBy) {
        this.prefectTypeId = prefectTypeId;
        this.description = description;
        this.modifiedTime = modifiedTime;
        this.modifiedBy = modifiedBy;
    }

    public Integer getPrefectTypeId() {
        return prefectTypeId;
    }

    public void setPrefectTypeId(Integer prefectTypeId) {
        this.prefectTypeId = prefectTypeId;
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
        hash += (prefectTypeId != null ? prefectTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrefectType)) {
            return false;
        }
        PrefectType other = (PrefectType) object;
        if ((this.prefectTypeId == null && other.prefectTypeId != null) || (this.prefectTypeId != null && !this.prefectTypeId.equals(other.prefectTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.PrefectType[ prefectTypeId=" + prefectTypeId + " ]";
    }
}
