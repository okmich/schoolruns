/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.staff;

import com.okmich.schoolruns.common.entity.SystemRole;
import com.okmich.schoolruns.common.entity.UserLogin;
import com.okmich.schoolruns.security.service.SecurityService;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class SchoolUserSecurityBean extends _BaseBean {

    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{securityService}")
    private SecurityService securityService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private UserLogin userLogin;
    private DualListModel<SystemRole> systemRoleDualList;

    /**
     * Creates a new instance of SchoolUserSecurityBean
     */
    public SchoolUserSecurityBean() {
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @param securityService the securityService to set
     */
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public String findUserRole() {
        try {
            Integer employeeId = Integer.valueOf(FacesUtil.getRequestParameter("employeeId"));
            this.userLogin = securityService.findUserLoginForEmployee(employeeId);
            if (userLogin != null) {
                sessionBean.setUserLogin(userLogin);
                return "/schooluser/staff/usersecurity";
            } else {
                throw new Exception("NULL_USERLOGIN");
            }
        } catch (Exception ex) {
            processException(ex);
            return null;
        }
    }

    public String assignRolesToUser() {
        try {
            securityService.assignOrRevokeRolesFromUser(sessionBean.getUserLogin(),
                    this.systemRoleDualList.getTarget());
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "", "Assignment Complete"));
            return null;
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String performUserPasswordReset() {
        try {
            securityService.resetPasswordByEmail(
                    sessionBean.getUserLogin().getEmail(), false);
            //prepare a mail to send new password to email.
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "", "Password Reset Complete"));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * @return the rolesList
     */
    public DualListModel<SystemRole> getSystemRoleDualList() {
        try {
            List<SystemRole> assignedRoles = securityService.findAssignedRolesToUser(
                    sessionBean.getUserLogin().getUserLoginId());
            List<SystemRole> unassignedModule = securityService.findNonAdminSystemRoles();
            unassignedModule.removeAll(assignedRoles);

            //initialize and set values of source and target
            this.systemRoleDualList = new DualListModel<>(
                    unassignedModule,
                    assignedRoles);
        } catch (Exception ex) {
            processException(ex);
        }
        return systemRoleDualList;
    }

    public String backToSearch() {
        try {
            sessionBean.setUserLogin(null);
            return "/schooluser/security/usersearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * @param systemRoleDualList the systemRoleDualList to set
     */
    public void setSystemRoleDualList(DualListModel<SystemRole> systemRoleDualList) {
        this.systemRoleDualList = systemRoleDualList;
    }

    /**
     * @return the userLogin
     */
    public UserLogin getUserLogin() {
        return userLogin;
    }

    /**
     * @param userLogin1 the userLogin to set
     */
    public void setUserLogin(UserLogin userLogin1) {
        this.userLogin = userLogin1;
    }
}
