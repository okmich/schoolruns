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
@Table(name = "parent_message")
public class ParentMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "parent_message_id")
    private Integer parentMessageId;
    @JoinColumn(name = "parent_id", referencedColumnName = "phone_number")
    @ManyToOne(optional = false)
    private Parent parent;
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

    public ParentMessage() {
        this.status = "A";
        this.modifiedTime = new Date();
    }

    /**
     * constructor for a convenient creation of a new object of this type
     *
     * @param _messageAddress
     * @param _parent
     * @param _message
     */
    public ParentMessage(String _messageAddress, Parent _parent, Message _message) {
        this();
        this.parent = _parent;
        this.messageAddress = _messageAddress;
        this.message = _message;
    }

    public ParentMessage(Integer parentMessageId) {
        this.parentMessageId = parentMessageId;
    }

    public Integer getParentMessageId() {
        return parentMessageId;
    }

    public void setParentMessageId(Integer parentMessageId) {
        this.parentMessageId = parentMessageId;
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

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
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
        hash += (parentMessageId != null ? parentMessageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParentMessage)) {
            return false;
        }
        ParentMessage other = (ParentMessage) object;
        if ((this.parentMessageId == null && other.parentMessageId != null) || (this.parentMessageId != null && !this.parentMessageId.equals(other.parentMessageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.ParentMessage[ parentMessageId=" + parentMessageId + " ]";
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
