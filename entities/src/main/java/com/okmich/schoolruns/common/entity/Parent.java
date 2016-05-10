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
@Table(name = "parent")
public class Parent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Basic(optional = false)
    @Column(name = "surname")
    private String surname;
    @Column(name = "othernames")
    private String othernames;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Column(name = "alternate_number")
    private String alternateNumber;
    @Basic(optional = false)
    @Column(name = "relationship")
    private String relationship;
    @Column(name = "job_industry")
    private String jobIndustry;
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
    @JoinColumn(name = "origin_state_code", referencedColumnName = "state_code")
    @ManyToOne
    private State state;
    @JoinColumn(name = "office_address_id", referencedColumnName = "address_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
    @JoinColumn(name = "residential_address_id", referencedColumnName = "address_id")
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Address address1;
    @JoinColumn(name = "title_code", referencedColumnName = "title_code")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Title title;
    @Column(name = "user_login_id")
    private Integer userLoginId;

    public Parent() {
        this.address1 = new Address();
    }

    public Parent(String _phoneNumber) {
        this.phoneNumber = _phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOthernames() {
        return othernames;
    }

    public void setOthernames(String othernames) {
        this.othernames = othernames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlternateNumber() {
        return alternateNumber;
    }

    public void setAlternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getJobIndustry() {
        return jobIndustry;
    }

    public void setJobIndustry(String jobIndustry) {
        this.jobIndustry = jobIndustry;
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

    public Address getAddress() {
        if (address == null) {
            return new Address();
        }
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress1() {
        if (address1 == null) {
            address1 = new Address();
        }
        return address1;
    }

    public void setAddress1(Address address1) {
        this.address1 = address1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parent)) {
            return false;
        }
        Parent other = (Parent) object;
        if ((this.phoneNumber == null && other.phoneNumber != null)
                || (this.phoneNumber != null && !this.phoneNumber.equals(other.phoneNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.Parent[ phoneNumber=" + phoneNumber + " ]";
    }

    @PrePersist
    @PreUpdate
    protected void updateTrigger() {
        setSurname(getSurname().toUpperCase());
        setOthernames(getOthernames() == null ? ""
                : getOthernames().toUpperCase());
        if (getTitle().getTitleCode() == null || getTitle().getTitleCode().trim().isEmpty()) {
            this.title = null;
        }
        if (getState().getStateCode() == null || getState().getStateCode().isEmpty()) {
            this.state = null;
        }
    }

    /**
     * @return the state
     */
    public State getState() {
        if (state == null) {
            state = new State();
        }
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * @return the title
     */
    public Title getTitle() {
        if (title == null) {
            title = new Title();
        }
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * utility to concatenate out a full name for this record
     *
     * @return String
     */
    public String getFullname() {
        return new StringBuilder(this.surname == null ? "" : this.surname).
                append(" ").append(this.othernames == null ? "" : this.othernames).toString();
    }

    /**
     * @return the userLoginId
     */
    public Integer getUserLoginId() {
        return userLoginId;
    }

    /**
     * @param userLoginId the userLoginId to set
     */
    public void setUserLoginId(Integer userLoginId) {
        this.userLoginId = userLoginId;
    }
}
