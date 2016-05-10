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
@Table(name = "store_activity_type")
public class StoreActivityType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "store_activity_type_code")
    private String storeActivityTypeCode;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    public StoreActivityType() {
    }

    public StoreActivityType(String storeActivityTypeCode) {
        this.storeActivityTypeCode = storeActivityTypeCode;
    }

    public StoreActivityType(String storeActivityTypeCode, String description) {
        this.storeActivityTypeCode = storeActivityTypeCode;
        this.description = description;
    }

    public String getStoreActivityTypeCode() {
        return storeActivityTypeCode;
    }

    public void setStoreActivityTypeCode(String storeActivityTypeCode) {
        this.storeActivityTypeCode = storeActivityTypeCode;
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
        hash += (storeActivityTypeCode != null ? storeActivityTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StoreActivityType)) {
            return false;
        }
        StoreActivityType other = (StoreActivityType) object;
        if ((this.storeActivityTypeCode == null && other.storeActivityTypeCode != null) || (this.storeActivityTypeCode != null && !this.storeActivityTypeCode.equals(other.storeActivityTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.StoreActivityType[ storeActivityTypeCode=" + storeActivityTypeCode + " ]";
    }
}
