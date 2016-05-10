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
@Table(name = "employee_query")
public class EmployeeQuery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "employee_query_id")
    private Integer employeeQueryId;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "query")
    private String query;
    @Basic(optional = false)
    @Column(name = "query_date")
    @Temporal(TemporalType.DATE)
    private Date queryDate;
    @Column(name = "reply")
    private String reply;
    @Column(name = "reply_date")
    @Temporal(TemporalType.DATE)
    private Date replyDate;
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
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @ManyToOne(optional = false)
    private Employee employee;
    @JoinColumn(name = "school_year_id", referencedColumnName = "school_year_id")
    @ManyToOne(optional = false)
    private SchoolYear schoolYear;
    @JoinColumn(name = "warning_level_id", referencedColumnName = "warning_level_id")
    @ManyToOne(optional = false)
    private WarningLevel warningLevel;

    /**
     *
     */
    public EmployeeQuery() {
        this.employee = new Employee();
        this.schoolYear = new SchoolYear();
        this.warningLevel = new WarningLevel();
    }

    /**
     *
     * @param employeeQueryId
     */
    public EmployeeQuery(Integer employeeQueryId) {
        this.employeeQueryId = employeeQueryId;
    }

    public Integer getEmployeeQueryId() {
        return employeeQueryId;
    }

    public void setEmployeeQueryId(Integer employeeQueryId) {
        this.employeeQueryId = employeeQueryId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

    public WarningLevel getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(WarningLevel warningLevel) {
        this.warningLevel = warningLevel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeQueryId != null ? employeeQueryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeQuery)) {
            return false;
        }
        EmployeeQuery other = (EmployeeQuery) object;
        if ((this.employeeQueryId == null && other.employeeQueryId != null) || (this.employeeQueryId != null && !this.employeeQueryId.equals(other.employeeQueryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.EmployeeQuery[ employeeQueryId="
                + employeeQueryId + " ]";
    }
}
