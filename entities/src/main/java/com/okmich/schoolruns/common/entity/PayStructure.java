/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

/**
 *
 * @author Michael
 * @since Aug 20, 2013
 * @company Okmich Ltd.
 */
@Entity
@Table(name = "pay_structure")
public class PayStructure implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pay_structure_id")
    private Integer payStructureId;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Min(value = 0)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Column(name = "school_id")
    @Basic(optional = false)
    private Integer schoolId;
    @JoinTable(name = "pay_structure_item_mapping", joinColumns = {
        @JoinColumn(name = "pay_structure_id", referencedColumnName = "pay_structure_id")},
    inverseJoinColumns = {
        @JoinColumn(name = "pay_structure_item_id", referencedColumnName = "pay_structure_item_id")})
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private List<PayStructureItem> payStructureItemList;

    public PayStructure() {
        this.payStructureItemList = new ArrayList<>();
    }

    public PayStructure(Integer payStructureId) {
        this();
        this.payStructureId = payStructureId;
    }

    public Integer getPayStructureId() {
        return payStructureId;
    }

    public void setPayStructureId(Integer payStructureId) {
        this.payStructureId = payStructureId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public List<PayStructureItem> getPayStructureItemList() {
        return payStructureItemList;
    }

    public void setPayStructureItemList(List<PayStructureItem> payStructureItemList) {
        this.payStructureItemList = payStructureItemList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (payStructureId != null ? payStructureId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayStructure)) {
            return false;
        }
        PayStructure other = (PayStructure) object;
        if ((this.payStructureId == null && other.payStructureId != null) || (this.payStructureId != null && !this.payStructureId.equals(other.payStructureId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.PayStructure[ payStructureId=" + payStructureId + " ]";
    }

    /**
     * @return the schoolId
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }
}
