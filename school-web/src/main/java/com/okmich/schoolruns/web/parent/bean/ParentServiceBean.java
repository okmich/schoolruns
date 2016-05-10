/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.parent.bean;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.util.StringUtil;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.UserLogin;
import com.okmich.schoolruns.security.service.SecurityService;
import com.okmich.schoolruns.student.service.StudentService;
import com.okmich.schoolruns.student.service.repo.ParentQueryCriteria;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.common.notification.MessageGenerator;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class ParentServiceBean extends _BaseBean {

    @ManagedProperty("#{parentSessionBean}")
    private ParentSessionBean parentSessionBean;
    //have the school session bean for the purpose of changing the web theme
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{studentBean}")
    private StudentBean studentBean;
    @ManagedProperty("#{securityService}")
    private SecurityService securityService;
    @ManagedProperty("#{studentService}")
    private StudentService studentService;
    @ManagedProperty("#{messageGenerator}")
    private MessageGenerator messageGenerator;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private Parent parent;
    private String username;
    private String password;
    private String newPassword;
    private String confirmPassword;

    /**
     * Creates a new instance of ParentLoginBean
     */
    public ParentServiceBean() {
    }

    /**
     * @param parentSessionBean the parentSessionBean to set
     */
    public void setParentSessionBean(ParentSessionBean parentSessionBean) {
        this.parentSessionBean = parentSessionBean;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @param studentBean1 the studentBean to set
     */
    public void setStudentBean(StudentBean studentBean1) {
        this.studentBean = studentBean1;
    }

    /**
     * @param securityService the securityService to set
     */
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * @param studentService the studentService to set
     */
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @param messageGenerator the messageGenerator1 to set
     */
    public void setMessageGenerator(MessageGenerator messageGenerator1) {
        this.messageGenerator = messageGenerator1;
    }

    /**
     * @return the parent
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Parent parent) {
        this.parent = parent;
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

    public String performLogin() {
        schoolSessionBean.setWebTheme("bluesky");
        try {
            //perform login validation.
            UserLogin _userLogin = securityService.findUserLoginForParent(getUsername());
            if (_userLogin == null) {
                throw new BusinessException("ERROR_CANNOT_FIND_LOGIN");
            }
            if (!securityService.validatePassword(getPassword(), _userLogin)) {
                throw new BusinessException("ERROR_INVALID_PASSWORD");
            }
            ParentQueryCriteria criteria = new ParentQueryCriteria();
            criteria.setUserLoginId(_userLogin.getUserLoginId());
            List<Parent> parents = studentService.findParents(criteria);
            if (parents.isEmpty()) {
                throw new BusinessException("ERROR_CANNOT_FIND_PARENT_RECORD");
            }
            this.parent = parents.get(0);
            this.parent.setUserLoginId(_userLogin.getUserLoginId());
            //on success, call the userSessionBean init method setting the UserLogin object
            parentSessionBean.setParent(this.parent);
            parentSessionBean.setUserLogin(_userLogin);
            //
            return "/parent/console?faces-redirect=true";
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    /**
     * @return String
     */
    public String prepareToEditAccount() {
        parentSessionBean.setEditMode(EDIT);
        return "";
    }

    /**
     * @return
     *
     */
    public String saveAccountDetails() {
        Parent _parent = parentSessionBean.getParent();
        _parent.setModifiedBy(_parent.getPhoneNumber());
        try {
            //update the parent 
            studentService.saveParent(_parent);
            //update the session edit mode
            parentSessionBean.setEditMode(null);
            return "";
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String preparePasswordChange() {
        this.userLoginSessionBean.setUserLogin(
                parentSessionBean.getUserLogin());
        return "/passwordchange?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public String performLogout() {
        try {
            //on success, call the userSessionBean init method setting the UserLogin object
            parentSessionBean.cleanUp();
            studentBean.goHome();            //
            return "/parent/index?faces-redirect=true";
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    public String saveParentDetails() {
        try {
            Parent _parent = parentSessionBean.getParent();
            _parent.setModifiedBy(parentSessionBean.getUserLogin().getUsername());

            _parent = studentService.saveParent(_parent);
            parentSessionBean.setParent(_parent);
            //report the new state
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Record Saved", ""));
            //
            return "/parent/profile?faces-redirect=true";
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    public String mergeDuplicateAccount() {
        try {
            //on success, 

            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    getUsername() + " merged with your current account", ""));
            return "/parent/accountmerge?faces-redirect=true";
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    public String performPasswordChange() {
        //do password change 
        try {
            try {
                if (!getNewPassword().equals(getConfirmPassword())) {
                    throw new Exception("ERROR_PASSWORDS_DONT_MATCH");
                }
            } catch (Exception ex) {
                FacesUtil.getFacesContext().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                        StringUtil.getNestedErrorMessage(ex)));
                return null;
            }
            boolean success = securityService.changePassword(
                    parentSessionBean.getUserLogin(), getPassword(), getNewPassword());
            if (success) {
                UserLogin _userLogin = parentSessionBean.getUserLogin();
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
}
