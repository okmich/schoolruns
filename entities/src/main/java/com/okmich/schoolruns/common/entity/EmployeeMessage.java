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
@Table(name = "employee_message")
public class EmployeeMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "staff_message_id")
    private Integer staffMessageId;
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @ManyToOne(optional = false)
    private Employee employee;
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

    public EmployeeMessage() {
        this.status = "A";
        this.modifiedTime = new Date();
    }

    /**
     * constructor for a convenient creation of a new object of this type
     *
     * @param _messageAddress
     * @param _employee
     * @param _message
     */
    public EmployeeMessage(String _messageAddress, Employee _employee, Message _message) {
        this();
        this.employee = _employee;
        this.messageAddress = _messageAddress;
        this.message = _message;
    }

    public EmployeeMessage(Integer staffMessageId) {
        this.staffMessageId = staffMessageId;
    }

    public Integer getStaffMessageId() {
        return staffMessageId;
    }

    public void setStaffMessageId(Integer staffMessageId) {
        this.staffMessageId = staffMessageId;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        hash += (staffMessageId != null ? staffMessageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeMessage)) {
            return false;
        }
        EmployeeMessage other = (EmployeeMessage) object;
        if ((this.staffMessageId == null && other.staffMessageId != null) || (this.staffMessageId != null && !this.staffMessageId.equals(other.staffMessageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.EmployeeMessage[ staffMessageId=" + staffMessageId + " ]";
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
