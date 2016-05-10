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
@Table(name = "store_item_type")
public class StoreItemType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "store_item_type_code")
    private String storeItemTypeCode;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    public StoreItemType() {
    }

    public StoreItemType(String storeItemTypeCode) {
        this.storeItemTypeCode = storeItemTypeCode;
    }

    public StoreItemType(String storeItemTypeCode, String description) {
        this.storeItemTypeCode = storeItemTypeCode;
        this.description = description;
    }

    public String getStoreItemTypeCode() {
        return storeItemTypeCode;
    }

    public void setStoreItemTypeCode(String storeItemTypeCode) {
        this.storeItemTypeCode = storeItemTypeCode;
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
        hash += (storeItemTypeCode != null ? storeItemTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StoreItemType)) {
            return false;
        }
        StoreItemType other = (StoreItemType) object;
        if ((this.storeItemTypeCode == null && other.storeItemTypeCode != null) || (this.storeItemTypeCode != null && !this.storeItemTypeCode.equals(other.storeItemTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.StoreItemType[ storeItemTypeCode=" + storeItemTypeCode + " ]";
    }
}
