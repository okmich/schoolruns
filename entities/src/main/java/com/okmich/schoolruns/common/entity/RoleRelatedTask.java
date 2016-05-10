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
 * @since Oct 1, 2013
 * @company Okmich Ltd.
 */
@Entity
@Table(name = "role_related_task")
public class RoleRelatedTask implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "role_related_task_id")
    private Integer roleRelatedTaskId;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "version")
    private Short version;
    @JoinColumn(name = "system_role_id", referencedColumnName = "system_role_id")
    @ManyToOne(optional = false)
    private SystemRole systemRole;
    @JoinColumn(name = "related_task_id", referencedColumnName = "related_task_id")
    @ManyToOne(optional = false)
    private RelatedTask relatedTask;

    public RoleRelatedTask() {
    }

    public RoleRelatedTask(Integer roleRelatedTaskId) {
        this.roleRelatedTaskId = roleRelatedTaskId;
    }

    public Integer getRoleRelatedTaskId() {
        return roleRelatedTaskId;
    }

    public void setRoleRelatedTaskId(Integer roleRelatedTaskId) {
        this.roleRelatedTaskId = roleRelatedTaskId;
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

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public SystemRole getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }

    public RelatedTask getRelatedTask() {
        return relatedTask;
    }

    public void setRelatedTask(RelatedTask relatedTask) {
        this.relatedTask = relatedTask;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleRelatedTaskId != null ? roleRelatedTaskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoleRelatedTask)) {
            return false;
        }
        RoleRelatedTask other = (RoleRelatedTask) object;
        if ((this.roleRelatedTaskId == null && other.roleRelatedTaskId != null) || (this.roleRelatedTaskId != null && !this.roleRelatedTaskId.equals(other.roleRelatedTaskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.RoleRelatedTask[ roleRelatedTaskId=" + roleRelatedTaskId + " ]";
    }
}
