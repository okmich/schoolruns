/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Min;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "store_activity")
public class StoreActivity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "store_activity_id")
    private Integer storeActivityId;
    // @Max(value=?)
    @Min(value = 0)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "quantity")
    private Float quantity;
    @Column(name = "narration")
    private String narration;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @JoinColumn(name = "store_item_id", referencedColumnName = "store_item_id")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private StoreItem storeItem;
    @JoinColumn(name = "store_activity_type_code", referencedColumnName = "store_activity_type_code")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private StoreActivityType storeActivityType;

    public StoreActivity() {
        this.storeItem = new StoreItem();
        this.storeActivityType = new StoreActivityType();
    }

    public StoreActivity(Integer storeActivityId) {
        this();
        this.storeActivityId = storeActivityId;
    }

    public Integer getStoreActivityId() {
        return storeActivityId;
    }

    public void setStoreActivityId(Integer storeActivityId) {
        this.storeActivityId = storeActivityId;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public StoreItem getStoreItem() {
        return storeItem;
    }

    public void setStoreItem(StoreItem storeItem) {
        this.storeItem = storeItem;
    }

    public StoreActivityType getStoreActivityType() {
        return storeActivityType;
    }

    public void setStoreActivityType(StoreActivityType storeActivityType) {
        this.storeActivityType = storeActivityType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storeActivityId != null ? storeActivityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StoreActivity)) {
            return false;
        }
        StoreActivity other = (StoreActivity) object;
        if ((this.storeActivityId == null && other.storeActivityId != null) || (this.storeActivityId != null && !this.storeActivityId.equals(other.storeActivityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.StoreActivity[ storeActivityId=" + storeActivityId + " ]";
    }
}
