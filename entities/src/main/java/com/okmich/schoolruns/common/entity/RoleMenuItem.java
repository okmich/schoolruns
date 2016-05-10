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
@Table(name = "role_menu_item")
public class RoleMenuItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "role_menu_item_id")
    private Integer roleMenuItemId;
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
    @JoinColumn(name = "menu_item_id", referencedColumnName = "menu_item_id")
    @ManyToOne(optional = false)
    private MenuItem menuItem;
    @JoinColumn(name = "system_role_id", referencedColumnName = "system_role_id")
    @ManyToOne(optional = false)
    private SystemRole systemRole;

    public RoleMenuItem() {
    }

    public RoleMenuItem(Integer roleMenuItemId) {
        this.roleMenuItemId = roleMenuItemId;
    }

    public Integer getRoleMenuItemId() {
        return roleMenuItemId;
    }

    public void setRoleMenuItemId(Integer roleMenuItemId) {
        this.roleMenuItemId = roleMenuItemId;
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

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public SystemRole getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleMenuItemId != null ? roleMenuItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoleMenuItem)) {
            return false;
        }
        RoleMenuItem other = (RoleMenuItem) object;
        if ((this.roleMenuItemId == null && other.roleMenuItemId != null) || (this.roleMenuItemId != null && !this.roleMenuItemId.equals(other.roleMenuItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.RoleMenuItem[ roleMenuItemId=" + roleMenuItemId + " ]";
    }
}
