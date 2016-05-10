package com.okmich.schoolruns.web.admin.bean.security;

import com.okmich.common.exception.BusinessException;
import com.okmich.schoolruns.common.entity.Menu;
import com.okmich.schoolruns.common.entity.MenuItem;
import com.okmich.schoolruns.common.entity.RelatedTask;
import com.okmich.schoolruns.common.entity.SystemRole;
import com.okmich.schoolruns.security.service.SecurityService;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.NavContant;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public final class SystemRoleBean extends _BaseBean {

    @ManagedProperty("#{securityService}")
    private SecurityService securityService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    private SystemRole systemRole;
    private DualListModel<Menu> rolesMenuList;
    private DualListModel<MenuItem> rolesMenuItemList;
    private DualListModel<RelatedTask> rolesRelatedTaskList;
    private List<SelectItem> menuItemList;
    private Integer menuId;

    public SystemRoleBean() {
        if (getSystemRole() == null) {
            setSystemRole(new SystemRole());
        }
    }

    /**
     * @param userService the userService to set
     */
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * @return the systemRole
     */
    public SystemRole getSystemRole() {
        return systemRole;
    }

    /**
     * @param systemRole the systemRole to set
     */
    public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }

    /**
     * @return the rolesMenuList
     */
    public DualListModel<Menu> getRolesMenuList() {
        List<Menu> assignedMenus = securityService.findAssignedMenusToRoles(
                sessionBean.getSystemRole().getSystemRoleId());
        List<Menu> unassignedMenu = new ArrayList();
        for (Menu mod : securityService.findAllMenus()) {
            if (!assignedMenus.contains(mod)) {
                unassignedMenu.add(mod);
            }
        }
        if (this.rolesMenuList == null) {
            //initialize and set values of source and target
            this.rolesMenuList = new DualListModel<>(
                    unassignedMenu,
                    assignedMenus);
        }
        return rolesMenuList;
    }

    /**
     * @param rolesMenuList the rolesMenuList to set
     */
    public void setRolesMenuList(DualListModel<Menu> rolesMenuList) {
        this.rolesMenuList = rolesMenuList;
    }

    /**
     * @return the rolesMenuItemList
     */
    public DualListModel<MenuItem> getRolesMenuItemList() {
        if (this.rolesMenuItemList == null) {
            this.rolesMenuItemList = new DualListModel<>(
                    securityService.findAllMenuItems(),
                    securityService.findAssignedMenuItemsToRoles(
                    getSessionBean().getSystemRole().getSystemRoleId()));
        }
        return rolesMenuItemList;
    }

    /**
     * @param rolesMenuItemList the rolesMenuItemList to set
     */
    public void setRolesMenuItemList(DualListModel<MenuItem> rolesMenuItemList) {
        this.rolesMenuItemList = rolesMenuItemList;
    }

    /**
     * @return the rolesRelatedTaskList
     */
    public DualListModel<RelatedTask> getRolesRelatedTaskList() {
        if (this.rolesRelatedTaskList == null) {
            this.setRolesRelatedTaskList(new DualListModel<>(
                    securityService.findAllRelatedTasks(),
                    securityService.findAssignedRelatedTaksToRoles(
                    sessionBean.getSystemRole().getSystemRoleId())));
        }
        return rolesRelatedTaskList;
    }

    /**
     * @param rolesRelatedTaskList the rolesRelatedTaskList to set
     */
    public void setRolesRelatedTaskList(DualListModel<RelatedTask> rolesRelatedTaskList) {
        this.rolesRelatedTaskList = rolesRelatedTaskList;
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
    public void setUserLoginSessionBean(UserLoginSessionBean userSessionBean) {
        this.userLoginSessionBean = userSessionBean;
    }

    /**
     * @return the appSessionBean
     */
    public SessionBean getSessionBean() {
        return sessionBean;
    }

    /**
     * @param appSessionBean the appSessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     *
     * @return
     */
    public String prepareToCreate() {
        sessionBean.setSystemRole(new SystemRole());
        sessionBean.setEditMode(CREATE);
        return "/admin/security/roledetails";
    }

    /**
     *
     * @return
     */
    public String saveSystemRole() {
        setSystemRole(sessionBean.getSystemRole());
        try {
            getSystemRole().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            securityService.saveSystemRole(getSystemRole());
            sessionBean.setSystemRole(null);
            return NavContant.SECURITY_ADM_ROLESEARCH;
        } catch (BusinessException ex) {
            processException(ex);
            return null;
        }
    }

    /**
     *
     * @return
     */
    public String findSystemRoles() {
        try {
            sessionBean.setSystemRoleModel(
                    new ListDataModel(securityService.findAllSystemRoles()));
            return null;
        } catch (Exception ex) {
            Logger.getLogger(SystemRoleBean.class.getName()).log(Level.SEVERE, null, ex);
            processException(ex);
        }
        return null;
    }

    public String findSystemRole() {
        if (getSystemRole() != null && getSystemRole().getSystemRoleId() != null) {
            getSessionBean().setSystemRole(getSystemRole());
            setSystemRole(getSystemRole());
            getSessionBean().setEditMode(
                    FacesUtil.getRequestParameter("editMode"));

            return NavContant.SECURITY_ADM_ROLEDETAILS;
        }
        return "";
    }

    public String assignMenuToRole() {
        try {
            securityService.assignOrRevokeMenusFromRole(getSessionBean().getSystemRole(),
                    getRolesMenuList().getTarget());
            return NavContant.SECURITY_ADM_ROLEDETAILS;
        } catch (Exception ex) {
            processException(ex);
            return null;
        }
    }

    public String assignMenuItemsToRole() {
        try {
            securityService.assignOrRevokeMenuItemsFromRole(getSessionBean().getSystemRole(),
                    getRolesMenuItemList().getTarget());
            return NavContant.SECURITY_ADM_ROLEDETAILS;
        } catch (Exception ex) {
            processException(ex);
            return null;
        }
    }

    public String assignRelatedTaskToRoles() {
        try {
            securityService.assignOrRevokeRelatedTaskFromRole(getSessionBean().getSystemRole(),
                    getRolesRelatedTaskList().getTarget());
            return NavContant.SECURITY_ADM_ROLEDETAILS;
        } catch (Exception ex) {
            processException(ex);
            return null;
        }
    }

    public String backToMainContextDetails() {
        setSystemRole(getSessionBean().getSystemRole());

        return NavContant.SECURITY_ADM_ROLEDETAILS;
    }

    public void fireMenuValueChangeEvent(ValueChangeEvent event) {
        HtmlSelectOneMenu menuMenu = (HtmlSelectOneMenu) event.getComponent();
        Object _itemValue = menuMenu.getValue();
        if (_itemValue != null) {
            this.menuId = Integer.valueOf(_itemValue.toString());
        }
        Logger.getLogger(SystemRoleBean.class.getName()).log(Level.INFO, "{0}",
                this.menuId);
        try {
            getRolesMenuItemList().setSource(
                    securityService.findMenuItemsByMenu(this.menuId));
        } catch (Exception ex) {
            processException(ex);
        }
    }

    public void fireMenuItemValueChangeEvent(ValueChangeEvent event) {
        try {
            rolesRelatedTaskList.setSource(
                    securityService.findRelatedTaskByContext("TODO"));
        } catch (Exception ex) {
            processException(ex);
        }
    }

    /**
     * @return the menuId
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * @param menuId the menuId to set
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public List<Menu> getMenuList() {
        if (sessionBean.getSystemRole() != null) {
            return securityService.findAssignedMenusToRoles(
                    sessionBean.getSystemRole().getSystemRoleId());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * @return the menuItemList
     */
    public List<SelectItem> getMenuItemList() {
        if (menuItemList == null) {
            menuItemList = new ArrayList<>();
            List<MenuItem> _list = securityService.findAllMenuItems();
            for (MenuItem mi : _list) {
                menuItemList.add(new SelectItem(mi.getMenuItemId(),
                        new StringBuilder(mi.getMenu().getName()).append("- ").
                        append(mi.getLabel()).toString()));
            }
        }
        return menuItemList;
    }

    /**
     * @param menuItemList the menuItemList to set
     */
    public void setMenuItemList(List<SelectItem> menuItemList) {
        this.menuItemList = menuItemList;
    }
}