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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "school_message")
public class SchoolMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "school_message_id")
    private Integer schoolMessageId;
    @JoinColumn(name = "school_id", referencedColumnName = "school_id")
    @ManyToOne(optional = false)
    private School school;
    @JoinColumn(name = "message_id", referencedColumnName = "message_id")
    @ManyToOne(optional = false)
    private Message message;
    @Column(name = "message_address")
    private String messageAddress;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;

    public SchoolMessage() {
        this.status = "A";
        this.modifiedTime = new Date();
    }

    /**
     * a convenient constructor for create a new SchoolMessage
     *
     * @param _messageAddress
     * @param _school
     * @param _message
     */
    public SchoolMessage(String _messageAddress, School _school, Message _message) {
        this();
        this.school = _school;
        this.messageAddress = _messageAddress;
        this.message = _message;
    }

    public SchoolMessage(Integer schoolMessageId) {
        this.schoolMessageId = schoolMessageId;
    }

    public Integer getSchoolMessageId() {
        return schoolMessageId;
    }

    public void setSchoolMessageId(Integer schoolMessageId) {
        this.schoolMessageId = schoolMessageId;
    }

    /**
     * @return the messageAddress
     */
    public String getMessageAddress() {
        return messageAddress;
    }

    /**
     * @param messageAddress the messageAddress to set
     */
    public void setMessageAddress(String messageAddress) {
        this.messageAddress = messageAddress;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolMessageId != null ? schoolMessageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolMessage)) {
            return false;
        }
        SchoolMessage other = (SchoolMessage) object;
        if ((this.schoolMessageId == null && other.schoolMessageId != null) || (this.schoolMessageId != null && !this.schoolMessageId.equals(other.schoolMessageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.SchoolMessage[ schoolMessageId=" + schoolMessageId + " ]";
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @param modifiedBy the modifiedBy to set
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return the modifiedTime
     */
    public Date getModifiedTime() {
        return modifiedTime;
    }

    /**
     * @param modifiedTime the modifiedTime to set
     */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
