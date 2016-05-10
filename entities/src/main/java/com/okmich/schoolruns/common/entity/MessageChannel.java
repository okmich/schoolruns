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
@Table(name = "message_channel")
public class MessageChannel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "message_channel_code")
    private String messageChannelCode;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    public MessageChannel() {
    }

    public MessageChannel(String messageChannelCode) {
        this.messageChannelCode = messageChannelCode;
    }

    public MessageChannel(String messageChannelCode, String description) {
        this.messageChannelCode = messageChannelCode;
        this.description = description;
    }

    public String getMessageChannelCode() {
        return messageChannelCode;
    }

    public void setMessageChannelCode(String messageChannelCode) {
        this.messageChannelCode = messageChannelCode;
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
        hash += (messageChannelCode != null ? messageChannelCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessageChannel)) {
            return false;
        }
        MessageChannel other = (MessageChannel) object;
        if ((this.messageChannelCode == null && other.messageChannelCode != null) || (this.messageChannelCode != null && !this.messageChannelCode.equals(other.messageChannelCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.MessageChannel[ messageChannelCode=" + messageChannelCode + " ]";
    }
}
