/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.security;

import com.okmich.common.entity.criteria.WCString;
import com.okmich.common.exception.BusinessException;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.School;
import com.okmich.schoolruns.common.entity.SystemRole;
import com.okmich.schoolruns.common.entity.UserLogin;
import com.okmich.schoolruns.security.service.SecurityService;
import com.okmich.schoolruns.security.service.repo.UserLoginQueryCriteria;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.ApplicationListBean;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.common.notification.MessageGenerator;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import com.okmich.schoolruns.web.user.bean.finance.FinanceSessionBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.ListDataModel;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class UserLoginBean extends _BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{applicationListBean}")
    private ApplicationListBean applicationListBean;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{securityService}")
    private SecurityService securityService;
    @ManagedProperty("#{messageGenerator}")
    private MessageGenerator messageGenerator;
    private Integer systemRoleId;
    private UserLogin userLogin;
    private String title;
    private String schoolName;
    private String phoneNumber;
    private boolean schoolAdmin;
    private boolean adminUser;
    private String email;
    private String newPassword;
    private String confirmPassword;
    private DualListModel<SystemRole> rolesList;
    private static final String USER_DETAIL = "/admin/security/userdetails";
    private static final String USER_SEARCH = "/admin/security/usersearch";
    private static final String INDEX_PAGE = "/index";

    public UserLoginBean() {
    }

    /**
     * created primarily for non-school users creation
     *
     * @return
     */
    public String createUserLogin() {
        try {
            getUserLogin().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getUserLogin().setLoginAttempts(0);
            getUserLogin().setSchool(null);
            getUserLogin().setSchoolAdmin(false);
            getUserLogin().setIsAdmin(CommonConstants.CONSTANT_YES);
            String _pWord = getUserLogin().getPassword();
            UserLogin _userLogins = securityService.createUserLogin(
                    getUserLogin());
            //send a message to the client
            List<SystemRole> systemRoleList = applicationListBean.getSystemRoleList();
            getUserLogin().setDefaultSystemRole(systemRoleList.get(
                    systemRoleList.indexOf(getUserLogin().getDefaultSystemRole())));
            //send message to the user
            getUserLogin().setPassword(_pWord);
            messageGenerator.sendPostUserLoginCreationMessage(getUserLogin());
            return "/admin/security/usersearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * created primarily for non-school users update
     *
     * @return
     */
    public String saveUserLogin() {
        sessionBean.getUserLogin().setModifiedBy(
                userLoginSessionBean.getUserLogin().getUsername());
        sessionBean.getUserLogin().setIsAdmin(CommonConstants.CONSTANT_YES);
        sessionBean.getUserLogin().setSchoolAdmin(false);
        sessionBean.getUserLogin().setSchool(null);
        try {
            UserLogin _userLogins = securityService.saveUserLogin(
                    sessionBean.getUserLogin());
            //send a message to the client
            List<SystemRole> systemRoleList = applicationListBean.getSystemRoleList();
            getUserLogin().setDefaultSystemRole(systemRoleList.get(
                    systemRoleList.indexOf(getUserLogin().getDefaultSystemRole())));
            messageGenerator.sendPostUserLoginCreationMessage(getUserLogin());
            return "/admin/security/usersearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String findUserLogins() {
        try {
            List<UserLogin> userLogins = securityService.findUserLoginList(
                    buildQueryCriteria());
            sessionBean.setUserLoginModel(new ListDataModel<>(userLogins));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findUserLogin() {
        if (getUserLogin().getUserLoginId() == null) {
            return "";
        }
        try {
            if (getUserLogin().getIsAdmin() != null
                    && getUserLogin().getIsAdmin().equals(CommonConstants.CONSTANT_NO)) {
                FacesUtil.getFacesContext().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "CANNOT EDIT A SCHOOL USER LOGIN", ""));
                return "";
            }
            sessionBean.setUserLogin(getUserLogin());
            return USER_DETAIL;
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String backToSearch() {
        try {
            sessionBean.setUserLogin(null);
            return USER_SEARCH;
        } catch (Exception ex) {
            Logger.getLogger(UserLoginBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    public String assignRolesToUser() {
        try {
            securityService.assignOrRevokeRolesFromUser(getSessionBean().getUserLogin(),
                    this.rolesList.getTarget());
            return USER_DETAIL;
        } catch (BusinessException ex) {
            Logger.getLogger(UserLoginBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String performLogin() {
        String toViewId;
        try {
            //perform login validation.
            UserLogin _userLogin = securityService.doUserLogin(getUsername(), getPassword());
            if (_userLogin.getSchool().getSchoolId() == null) { //this is a non-school user
                toViewId = "/admin/_ah_workspace?faces-redirect=true";
            } else {
                verifySchoolUserLogin(_userLogin);
                Map<String, Object> properties = new HashMap<>(2);
                properties.put("maxAge", 60 * 60 * 24 * 365);
                //set the cookie
                FacesUtil.getFacesContext().getExternalContext()
                        .addResponseCookie("schoolId",
                        _userLogin.getSchool().getSchoolId().toString(), properties);
                //initialize the scholSessonBean
                schoolSessionBean.init(_userLogin.getSchool().getSchoolId());
                toViewId = "/common/console?faces-redirect=true";
            }
            //on success, call the userSessionBean init method setting the UserLogin object
            userLoginSessionBean.init(_userLogin);
            //
            return toViewId;
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String performLogout() {
        //call the cleanUp method of all the session bean used as value holders
        userLoginSessionBean.cleanUp();
        sessionBean.cleanUp();
        financeSessionBean.cleanUp();
        return "/index?faces-redirect=true";
    }

    public String performPasswordChange() {
        //do password change 
        try {
            try {
                if (!getNewPassword().equals(getConfirmPassword())) {
                    throw new Exception("ERROR_NEW_PASSWORD_DONT_MATCH");
                }
            } catch (Exception ex) {
                FacesUtil.getFacesContext().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        StringUtil.getNestedErrorMessage(ex)));
                return null;
            }
            boolean success = securityService.changePassword(
                    getUserLoginSessionBean().getUserLogin(), getPassword(), getNewPassword());
            if (success) {
                UserLogin _userLogin = getUserLoginSessionBean().getUserLogin();
                _userLogin.setPassword(getNewPassword());
                //send an email alert
                messageGenerator.sendPasswordChangeMessage(_userLogin);
                FacesUtil.getFacesContext().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "", "Changes saved"));
            } else {
                throw new Exception("ERROR_OCCURED");
            }
        } catch (Exception ex) {
            processException(ex);
        }

        return null;
    }

    public String performPasswordRecovery() {
        try {
            UserLogin _userLogin = securityService.resetPasswordByEmail(getEmail(), false);
            //send an email alert
            messageGenerator.sendPasswordResetMessage(_userLogin);
            //prepare a mail to send new password to email.
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "", "Password sent to your mail address"));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String performUserPasswordReset() {
        try {
            UserLogin _userLogin = securityService.resetPasswordByEmail(
                    getSessionBean().getUserLogin().getEmail(), false);
            //prepare a mail to send new password to email.
            //send an email alert
            messageGenerator.sendPasswordResetMessage(_userLogin);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "", "Password Reset Complete"));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * @return the userSessionBean
     */
    public UserLoginSessionBean getUserLoginSessionBean() {
        return userLoginSessionBean;
    }

    /**
     * @param userSessionBean the userSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @param financeSessionBean1 the financeSessionBean to set
     */
    public void setFinanceSessionBean(FinanceSessionBean financeSessionBean1) {
        this.financeSessionBean = financeSessionBean1;
    }

    /**
     * @param applicationListBean1 the applicationListBean to set
     */
    public void setApplicationListBean(ApplicationListBean applicationListBean1) {
        this.applicationListBean = applicationListBean1;
    }

    /**
     * @return the securityService
     */
    public SecurityService getSecurityService() {
        return securityService;
    }

    /**
     * @param securityService the securityService to set
     */
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * @param messageGenerator the messageGenerator1 to set
     */
    public void setMessageGenerator(MessageGenerator messageGenerator1) {
        this.messageGenerator = messageGenerator1;
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

    /**
     * @return the systemRoleId
     */
    public Integer getSystemRoleId() {
        return systemRoleId;
    }

    /**
     * @param systemRoleId the systemRoleId to set
     */
    public void setSystemRoleId(Integer systemRoleId) {
        this.systemRoleId = systemRoleId;
    }

    /**
     * @return the userLogin
     */
    public UserLogin getUserLogin() {
        if (userLogin == null) {
            this.userLogin = new UserLogin();
        }
        return userLogin;
    }

    /**
     * @param userLogin the userLogin to set
     */
    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * @return the schoolName
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * @param schoolName the schoolName to set
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the sessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * @return the rolesList
     */
    public DualListModel<SystemRole> getRolesList() {
        try {
            List<SystemRole> assignedRoles = securityService.findAssignedRolesToUser(
                    sessionBean.getUserLogin().getUserLoginId());
            List<SystemRole> unassignedModule = securityService.findAllSystemRoles();
            unassignedModule.removeAll(assignedRoles);

            if (this.rolesList == null) {
                //initialize and set values of source and target
                this.rolesList = new DualListModel<>(
                        unassignedModule,
                        assignedRoles);
            }
        } catch (Exception ex) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", StringUtil.getNestedErrorMessage(ex)));
            Logger.getLogger(UserLoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rolesList;
    }

    /**
     * @param rolesList the rolesList to set
     */
    public void setRolesList(DualListModel<SystemRole> rolesList) {
        this.rolesList = rolesList;
    }

    private UserLoginQueryCriteria buildQueryCriteria() {
        UserLoginQueryCriteria crit = new UserLoginQueryCriteria();

        if (title != null && !title.trim().isEmpty()) {
            crit.setTitle(title, WCString.LIKE);
        }
        if (email != null && !email.trim().isEmpty()) {
            crit.setEmail(email, WCString.LIKE);
        }
        if (schoolAdmin) {
            crit.setSchoolAdmin();
        }
        if (adminUser) {
            crit.setIsAdmin(CommonConstants.CONSTANT_YES);
        }
        if (systemRoleId != null && systemRoleId != 0) {
            crit.setDefaultRole(systemRoleId);
        }
        if (this.phoneNumber != null && !this.phoneNumber.trim().isEmpty()) {
            crit.setPhoneNumber(this.phoneNumber, WCString.LIKE);
        }
        if (this.schoolName != null && !this.schoolName.trim().isEmpty()) {
            crit.setSchoolName(this.schoolName, WCString.LIKE);
        }

        return crit;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * @return the schoolSessionBean
     */
    public SchoolSessionBean getSchoolSessionBean() {
        return schoolSessionBean;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
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
     * @return the adminUser
     */
    public boolean isAdminUser() {
        return adminUser;
    }

    /**
     * @param adminUser the adminUser to set
     */
    public void setAdminUser(boolean adminUser) {
        this.adminUser = adminUser;
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

    /**
     * this is a simple test that the school user is logging into a correct
     * school session If the school session has not been set, then the check is
     * passed
     *
     * @param _userLogin
     * @throws BusinessException
     */
    private void verifySchoolUserLogin(UserLogin _userLogin)
            throws BusinessException {
        School _school = schoolSessionBean.getSchool();
        if (_school != null) {
            Integer _schoolId = _userLogin.getSchool().getSchoolId();
            if (!_schoolId.equals(_school.getSchoolId())) {
                throw new BusinessException("ERROR_INVALID_SCHOOL_LOGIN");
            }
        }
    }
}
