/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "user_login")
public class UserLogin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_login_id")
    private Integer userLoginId;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "login_attempts")
    private Integer loginAttempts;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "change_on_next_login")
    private String changeOnNextLogin;
    @Basic(optional = false)
    @Column(name = "generated_password")
    private boolean generatedPassword;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Column(name = "version")
    @Version
    private Short version;
    @Basic(optional = false)
    @Column(name = "is_admin")
    private String isAdmin;
    @Column(name = "school_admin")
    private boolean schoolAdmin;
    @JoinColumn(name = "school_id", referencedColumnName = "school_id")
    @ManyToOne
    private School school;
    @JoinColumn(name = "default_role_id", referencedColumnName = "system_role_id")
    @ManyToOne(optional = false)
    private SystemRole systemRole;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userLogin")
    private List<UserRole> userRoleList;

    public UserLogin() {
    }

    public UserLogin(Integer userLoginId) {
        this.userLoginId = userLoginId;
    }

    public Integer getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(Integer userLoginId) {
        this.userLoginId = userLoginId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
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

    public String getChangeOnNextLogin() {
        return changeOnNextLogin;
    }

    public void setChangeOnNextLogin(String changeOnNextLogin) {
        this.changeOnNextLogin = changeOnNextLogin;
    }

    public boolean getGeneratedPassword() {
        return generatedPassword;
    }

    public void setGeneratedPassword(boolean generatedPassword) {
        this.generatedPassword = generatedPassword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return the schoolAdmin
     */
    public boolean isSchoolAdmin() {
        return schoolAdmin;
    }

    /**
     * @param schoolAdmin the schoolAdmin to set
     */
    public void setSchoolAdmin(boolean schoolAdmin) {
        this.schoolAdmin = schoolAdmin;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public SystemRole getDefaultSystemRole() {
        if (systemRole == null) {
            this.systemRole = new SystemRole();
        }
        return systemRole;
    }

    public void setDefaultSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }

    public School getSchool() {
        if (school == null) {
            this.school = new School();
        }
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userLoginId != null ? userLoginId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserLogin)) {
            return false;
        }
        UserLogin other = (UserLogin) object;
        if ((this.userLoginId == null && other.userLoginId != null) || (this.userLoginId != null && !this.userLoginId.equals(other.userLoginId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.UserLogin[ userLoginId=" + userLoginId + " ]";
    }

    @PreUpdate
    @PrePersist
    protected void trigger() {
        setTitle(title != null ? title.toUpperCase() : "");
        if (this.school == null || this.school.getSchoolId() == null) {
            setSchool(null);
        }
    }
}
