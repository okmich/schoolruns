/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.security.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCString;




/**
 *
 * @author Michael
 */
public class UserLoginQueryCriteria extends BaseEntityQueryCriteria {

    public static final String title = "title";
    public static final String username = "username";
    public static final String email = "email";
    public static final String phoneNumber = "phoneNumber";
    public static final String loginAttempts = "loginAttempts";
    public static final String isAdmin = "isAdmin";
    @RelatedEntity(entityAlias = "a", referencedEntity = "school")
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "school")
    public static final String name = "name";
    public static final String schoolAdmin = "schoolAdmin";
    @RelatedEntity(entityAlias = "b", referencedEntity = "systemRole")
    public static final String systemRoleId = "systemRoleId";

    public UserLoginQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "UserLogin";
    }

    /**
     * @param _title the title to set
     */
    public void setTitle(String _title) {
        setParameter(UserLoginQueryCriteria.title, _title);
    }

    /**
     * @param _title the title to set
     */
    public void setTitle(String _title, WCString wClause) {
        setParameter(UserLoginQueryCriteria.title, wClause, _title);
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        setParameter(UserLoginQueryCriteria.username, username);
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username, WCString wClause) {
        setParameter(UserLoginQueryCriteria.username, wClause, username);
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        setParameter(UserLoginQueryCriteria.email, email);
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email, WCString wClause) {
        setParameter(UserLoginQueryCriteria.email, wClause, email);
    }

    /**
     * @param _phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String _phoneNumber) {
        setParameter(UserLoginQueryCriteria.title, _phoneNumber);
    }

    /**
     * @param _phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String _phoneNumber, WCString wClause) {
        setParameter(UserLoginQueryCriteria.phoneNumber, wClause, _phoneNumber);
    }

    /**
     * @param loginAttempts the loginAttempts to set
     */
    public void setLoginAttempts(Integer loginAttempts) {
        setParameter(UserLoginQueryCriteria.loginAttempts, loginAttempts);
    }

    /**
     * @param isAdmin the isAdmin to set
     */
    public void setIsAdmin(String isAdmin) {
        setParameter(UserLoginQueryCriteria.isAdmin, isAdmin);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(UserLoginQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param schoolName the schoolName to set
     */
    public void setSchoolName(String schoolName) {
        setParameter(UserLoginQueryCriteria.name, schoolName);
    }

    /**
     * @param schoolName the schoolName to set
     */
    public void setSchoolName(String schoolName, WCString wClause) {
        setParameter(UserLoginQueryCriteria.name, wClause, schoolName);
    }

    /**
     * @param username the username to set
     */
    public void setSchoolAdmin() {
        setParameter(UserLoginQueryCriteria.schoolAdmin);
    }

    /**
     * @param _systemRoleId the systemRoleId to set
     */
    public void setDefaultRole(Integer _systemRoleId) {
        setParameter(UserLoginQueryCriteria.systemRoleId, _systemRoleId);
    }
}
