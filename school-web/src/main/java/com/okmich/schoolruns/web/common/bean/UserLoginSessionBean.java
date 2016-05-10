/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.bean;

import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Menu;
import com.okmich.schoolruns.common.entity.MenuItem;
import com.okmich.schoolruns.common.entity.RelatedTask;
import com.okmich.schoolruns.common.entity.UserLogin;
import com.okmich.schoolruns.employee.service.EmployeeService;
import com.okmich.schoolruns.employee.service.data.EmployeeData;
import com.okmich.schoolruns.notification.service.NotificationService;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.data.ParticipantCategoryEnum;
import com.okmich.schoolruns.security.service.SecurityService;
import com.okmich.schoolruns.web.admin.bean.security.UserLoginBean;
import com.okmich.schoolruns.web.common.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;

/**
 *
 * @author Michael
 */
@ManagedBean
@SessionScoped
public class UserLoginSessionBean implements Serializable {

    @ManagedProperty("#{employeeService}")
    private EmployeeService employeeService;
    @ManagedProperty("#{securityService}")
    private SecurityService securityService;
    @ManagedProperty("#{notificationService}")
    private NotificationService notificationService;
    Map<Menu, List<MenuItem>> menuMap;
    Map<String, List<RelatedTask>> menuItemMap;
    private MenuModel menuModel;
    private Map<String, MenuModel> relatedTaskModelMap;
    private UserLogin userLogin;

    public UserLoginSessionBean() {
        this.menuMap = new HashMap<>();
        this.menuItemMap = new HashMap<>();
        this.menuModel = new DefaultMenuModel();
        this.relatedTaskModelMap = new HashMap<>();
    }

    public String saveUserLoginProfile() {
        try {
            UserLogin _userLogin = securityService.saveUserLogin(
                    getUserLogin());
            //prepare a mail to send new password to email.
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "", "Changes Saved"));
        } catch (Exception ex) {
            Logger.getLogger(UserLoginBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    StringUtil.getNestedErrorMessage(ex)));
        }
        return "";
    }

    /**
     * initializes the properties for a user session like the menu user name
     * titles and other role verification exercises.
     *
     * @param userLogin
     */
    public void init(UserLogin _userLogin) throws Exception {
        setUserLogin(_userLogin);

//        Map<Menu, List<MenuItem>> menuDataStructure =
//                securityService.loadUserLoginMenuStructure(_userLogin);

//        buildMenuModel();
//        buildRelatedTaskModel();
    }

    /**
     *
     *
     */
    public void cleanUp() {
        setUserLogin(null);
        this.menuMap = null;
        this.menuItemMap = null;
        this.menuModel = null;
        this.relatedTaskModelMap = null;
    }

    /**
     * if the menu Map does not contain the menu for the menu item then ignore
     * the menu item.
     *
     * @param MenuItem
     */
    private void addToMenuMap(MenuItem _menuItem) {
        if (menuMap.containsKey(_menuItem.getMenu())) {
            menuMap.get(_menuItem.getMenu()).add(_menuItem);
        }
    }

    /**
     *
     *
     * @param RoleRelatedTask
     */
    private void addToRelatedTaskMap(RelatedTask rt) {
        if (menuItemMap.containsKey(rt.getContextCode())) {
            menuItemMap.get(rt.getContextCode()).add(rt);
        } else {
            List<RelatedTask> relTask = new ArrayList<>();
            relTask.add(rt);

            menuItemMap.put(rt.getContextCode(), relTask);
        }
    }

//    /**
//     *
//     *
//     * @param label
//     * @param outcome
//     * @param target
//     * @param url
//     * @param icon
//     * @return org.primefaces.component.menuitem.MenuItem
//     */
//    private org.primefaces.component.menuitem.MenuItem getMenuItem(String label, String outcome,
//            String target, String url, String icon) {
//        org.primefaces.component.menuitem.MenuItem menuItem =
//                new org.primefaces.component.menuitem.MenuItem();
//
//        menuItem.setValue(label);
//        menuItem.setOutcome(outcome);
//        menuItem.setTarget(target);
//        menuItem.setStyleClass("menuitem");
//        if (url != null && !url.isEmpty()) {
//            menuItem.setUrl(url);
//        }
//        menuItem.setIcon(icon);
//
//        return menuItem;
//    }
//
//    /**
//     *
//     *
//     */
//    private void buildMenuModel() {
//        Submenu submenu;
//        for (Menu menu : menuMap.keySet()) {
//            submenu = new Submenu();
//            submenu.setLabel(menu.getName());
//            System.out.println(">>>> " + menu.getName());
//            for (MenuItem menuItem : menuMap.get(menu)) {
//                submenu.getChildren().add(
//                        getMenuItem(menuItem.getLabel(), menuItem.getOutcome(),
//                        menuItem.getTargetFrame(), menuItem.getUrl(), menuItem.getIcon()));
//                System.out.println(">>>> >>>> " + menuItem.getOutcome());
//            }
//
//            this.getMenuModel().addSubmenu(submenu);
//        }
//    }

    /**
     *
     */
//    private void buildRelatedTaskModel() {
//        MenuModel _menuModel;
//        System.out.println("\n\n RELATED TASK");
//        for (String _contextCode : menuItemMap.keySet()) {
//            _menuModel = new DefaultMenuModel();
//            System.out.println(">>>> " + _contextCode);
//            for (RelatedTask _relTask : menuItemMap.get(_contextCode)) {
//                _menuModel.addMenuItem(getMenuItem(_relTask.getLabel(), _relTask.getOutcome(),
//                        "", _relTask.getUrl(), _relTask.getIcon()));
//                System.out.println(">>>> >>>> " + _relTask.getLabel() + "(" + _relTask.getOutcome() + ")");
//            }
//            this.relatedTaskModelMap.put(_contextCode, menuModel);
//        }
//    }

    /**
     * @return the menuModel
     */
    public MenuModel getMenuModel() {
        return menuModel;
    }

    /**
     * @param menuModel the menuModel to set
     */
    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }

    /**
     * @return the relatedTaskModelMap
     */
    public Map<String, MenuModel> getRelatedTaskModelMap() {
        return relatedTaskModelMap;
    }

    /**
     * @param relatedTaskModelMap the relatedTaskModelMap to set
     */
    public void setRelatedTaskModelMap(Map<String, MenuModel> relatedTaskModelMap) {
        this.relatedTaskModelMap = relatedTaskModelMap;
    }

    private SecurityService getSecurityService() {
        return this.securityService;
    }

    /**
     * @param securityService the securityService to set
     */
    public void setSecurityService(SecurityService securityService1) {
        this.securityService = securityService1;
    }

    /**
     * @param employeeService the employeeService to set
     */
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * @param notificationService the notificationService to set
     */
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * @return the userLogin
     */
    public UserLogin getUserLogin() {
        return userLogin;
    }

    /**
     * @param userLogin the userLogin to set
     */
    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    /**
     *
     * @return
     */
    public DataModel<MessageData> getMessageModel() {
        List<MessageData> messages = new ArrayList<>();
        int role = getUserLogin().getDefaultSystemRole().getSystemRoleId();
        if (CommonConstants.CONSTANT_YES.equals(getUserLogin().getIsAdmin())) {
            messages = new ArrayList<>();
        } else if (CommonConstants.ROLE_SCHOOL_ADMIN == role) {
            messages = notificationService.findMessages(
                    getUserLogin().getSchool().getSchoolId(),
                    ParticipantCategoryEnum.SCHOOLS, true);
        } else { //this is an employee login
            EmployeeData eData = employeeService.findEmployeeByLogin(
                    getUserLogin().getUserLoginId());
            if (eData != null) {
                messages = notificationService.findMessages(
                        eData.getEmployeeId(),
                        ParticipantCategoryEnum.EMPLOYEES, true);
            }
        }
        return new ListDataModel<>(messages);
    }
}
