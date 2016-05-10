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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "address_id")
    private Integer addressId;
    @Column(name = "street_line_1")
    private String streetLine1;
    @Column(name = "street_line_2")
    private String streetLine2;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @JoinColumn(name = "state_code", referencedColumnName = "state_code")
    @ManyToOne
    private State state;
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    @ManyToOne
    private City city;

    public Address() {
        this(null);
    }

    public Address(Integer addressId) {
        this(addressId, new Date());
    }

    public Address(Integer addressId, Date modifiedTime) {
        this.addressId = addressId;
        this.modifiedTime = modifiedTime;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getStreetLine1() {
        return streetLine1;
    }

    public void setStreetLine1(String streetLine1) {
        this.streetLine1 = streetLine1;
    }

    public String getStreetLine2() {
        return streetLine2;
    }

    public void setStreetLine2(String streetLine2) {
        this.streetLine2 = streetLine2;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public State getState() {
        if (state == null) {
            setState(new State());
        }
        return state;
    }

    public void setState(State states) {
        this.state = states;
    }

    public City getCity() {
        if (city == null) {
            setCity(new City());
        }
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressId != null ? addressId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.addressId == null && other.addressId != null) || (this.addressId != null && !this.addressId.equals(other.addressId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.Address[ addressId=" + addressId + ", "
                + this.getStreetLine1() + " ]";
    }

    @PrePersist
    @PreUpdate
    private void updateTrigger() {
        if (this.state == null || this.state.getStateCode() == null) {
            setState(null);
        }
        if (this.city == null || this.city.getCityId() == null) {
            setCity(null);
        }
    }
}
