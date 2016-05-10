/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.security.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.schoolruns.common.entity.Employee;
import com.okmich.schoolruns.common.entity.MenuItem;
import com.okmich.schoolruns.common.entity.Menu;
import com.okmich.schoolruns.common.entity.RelatedTask;
import com.okmich.schoolruns.common.entity.SystemRole;
import com.okmich.schoolruns.common.entity.UserLogin;
import com.okmich.schoolruns.security.service.repo.UserLoginQueryCriteria;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Michael
 */
public interface SecurityService extends Serializable {

    /**
     *
     * @param userLogin
     * @return UserLogin
     * @throws BusinessException
     */
    UserLogin createUserLogin(UserLogin userLogin) throws BusinessException;

    /**
     * saves a system user.
     *
     * @param userLogin - userLogin
     * @return
     */
    UserLogin saveUserLogin(UserLogin userLogin) throws BusinessException;

    /**
     *
     * @param userLoginId
     * @return
     * @throws BusinessException
     */
    UserLogin findUserLogin(Integer userLoginId);

    /**
     * find the userLogin for the employee with userLoginId as parameter
     *
     * @param employeeId
     * @return UserLogin
     */
    UserLogin findUserLoginForEmployee(Integer employeeId);

    /**
     * find the userLogin for the parent with parentId as parameter
     *
     * @param parentId - phone number
     * @return UserLogin
     */
    UserLogin findUserLoginForParent(String parentId);

    /**
     * Get the any users UserLogin object by passing the user's username.
     *
     * @param userName - String
     * @throws BusinessException BusinessException
     * @return returns the UserLogin object.
     */
    UserLogin findUserLogin(String userName);

    /**
     * Retrieve all the available list of UserLogin.
     *
     * @param criteria
     * @return
     * @throws BusinessException
     */
    List<UserLogin> findUserLoginList(UserLoginQueryCriteria criteria) throws BusinessException;

    /**
     * encode password.
     *
     * @param userLogin - userLogin
     * @throws BusinessException BusinessException
     * @return encoded password.
     */
    String getEncodedPassword(UserLogin userLogin) throws BusinessException;

    /**
     * Reset the user password whose userLogin is provided
     *
     * @param userLogin - userLogin
     * @throws BusinessException BusinessException
     * @return the user object UserLogin whose password has been successfully
     * reset
     */
    UserLogin resetPassword(UserLogin userLogin) throws BusinessException;

    /**
     * Reset the user password whose email is provided
     *
     * @param email - email
     * @throws BusinessException BusinessException
     * @return the user object UserLogin whose password has been successfully
     * reset
     */
    UserLogin resetPasswordByEmail(String email, boolean isParent) throws BusinessException;

    /**
     * Reset the user password whose phone number is provided
     *
     * @param phoneNumber - phoneNumber
     * @param isParent -
     * @throws BusinessException BusinessException
     * @return the user object UserLogin whose password has been successfully
     * reset
     */
    UserLogin resetPasswordByMobile(String phoneNumber, boolean isParent) throws BusinessException;

    /**
     * get availiable userRole list.
     *
     * @throws BusinessException BusinessException
     * @return availiable userRoleList.
     */
    List<SystemRole> findSystemRoleList();

    /**
     * Generate a random password string.
     *
     * @throws BusinessException BusinessException
     * @return Random string which is used as a password.
     */
    String generateRandomPassword() throws BusinessException;

    /**
     * Change the password of this user to the new password pass into the method
     * as a parameter.
     *
     * @return true if password has successfuly changed.
     * @param userLogin The details of user logged in.
     * @param newPassword the current password of the user.
     * @param newPassword the new password provide by the user.
     * @throws BusinessException when fails in operation.
     */
    boolean changePassword(UserLogin userLogin, String currentPassword, String newPassword)
            throws BusinessException;

    /**
     * Validate the row password with the encoded password.
     *
     * @param password the row password entered by the user.
     * @param userLogin the user login.
     * @return {@link Boolean} true if the passwords are matched.
     * @throws BusinessException when fails in operation.
     */
    boolean validatePassword(String password, UserLogin userLogin)
            throws BusinessException;

    /**
     *
     * @param userName
     * @param password
     * @return UserLogin
     * @throws BusinessException
     */
    UserLogin doUserLogin(String userName, String password) throws BusinessException;

    /**
     * create a system role.
     *
     * @param systemRole - systemRole
     * @throws BusinessException BusinessException
     * @return system role.
     */
    SystemRole saveSystemRole(SystemRole systemRole) throws BusinessException;

    /**
     * find systemRole by systemRoleId.
     *
     * @param systemRoleId the id of the systemRole.
     * @return system role object.
     * @throws BusinessException when fails.
     */
    SystemRole findSystemRole(int systemRoleId) throws BusinessException;

    /**
     * find all available system roles
     *
     * @return a list of SystemRole Object
     */
    List<SystemRole> findAllSystemRoles();

    /**
     * find all available system roles for which their isAdmin property is false
     *
     * @return a list of SystemRole Object for non admin
     */
    List<SystemRole> findNonAdminSystemRoles();

    /**
     * find all available system roles for which their isAdmin property is true
     *
     * @return a list of SystemRole Object for non admin
     */
    List<SystemRole> findAdminSystemRoles();

    /**
     * Get all the Menus as a list.
     *
     * @return list of menus.
     * @throws BusinessException when fails.
     */
    List<Menu> findAllMenus();

    /**
     * create a menu.
     *
     * @param systemRole - systemRole
     * @throws BusinessException BusinessException
     * @return menu.
     */
    Menu saveMenu(Menu menu) throws BusinessException;

    /**
     * create a menu item
     *
     * @param menuItem - menuItem
     * @throws BusinessException BusinessException
     * @return menuItem.
     */
    MenuItem saveMenuItem(MenuItem menuItem) throws BusinessException;

    /**
     * find a menu item by pk
     *
     * @param menuItemId
     * @return MenuItem
     */
    MenuItem findMenuItem(Integer menuItemId) throws BusinessException;

    /**
     * find a list of menu items by menu Id
     *
     * @param menuId
     * @return list of menu items
     * @throws BusinessException
     */
    List<MenuItem> findMenuItemsByMenu(Integer menuId)
            throws BusinessException;

    /**
     * find all available menu items
     *
     * @return
     */
    List<MenuItem> findAllMenuItems();

    /**
     * create a related task
     *
     * @param relatedTask - relatedTask
     * @throws BusinessException BusinessException
     * @return relatedTask.
     */
    RelatedTask saveRelatedTask(RelatedTask relatedTask)
            throws BusinessException;

    /**
     * find a related task by pk
     *
     * @param relatedTaskId
     * @return RelatedTask
     */
    RelatedTask findRelatedTask(Integer relatedTaskId) throws BusinessException;

    /**
     * find a list of related task by context id
     *
     * @param contextId
     * @return list of related task
     * @throws BusinessException
     */
    List<RelatedTask> findRelatedTaskByContext(String contextId);

    /**
     * find all available related task
     *
     * @return
     * @throws BusinessException
     */
    List<RelatedTask> findAllRelatedTasks();

    /**
     * find all menus that have been assigned to a system role
     *
     * @param systemRoleId
     * @return List<RoleMenu>
     */
    List<Menu> findAssignedMenusToRoles(Integer systemRoleId);

    /**
     * find all menu item assigned to a system role.
     *
     * @param systemRoleId
     * @return List<RoleMenuItem>
     */
    List<MenuItem> findAssignedMenuItemsToRoles(Integer systemRoleId);

    /**
     * find all related tasks assigned to a given role
     *
     * @param systemRoleId
     * @return List<RoleRelatedTask>
     */
    List<RelatedTask> findAssignedRelatedTaksToRoles(Integer systemRoleId);

    /**
     * find all roles assigned to a User
     *
     * @param userLoginId
     * @return List<UserRole>
     * @throws BusinessException
     */
    List<SystemRole> findAssignedRolesToUser(Integer userLoginId)
            throws BusinessException;

    /**
     * Add or remove a list of menus from a specified role
     *
     * @param systemRole
     * @param menuList
     * @throws BusinessException
     */
    void assignOrRevokeMenusFromRole(SystemRole systemRole,
            List<Menu> menuList) throws BusinessException;

    /**
     * add or remove a list of menu Item from a specified role
     *
     * @param systemRole
     * @param menuItemList
     * @throws BusinessException
     */
    void assignOrRevokeMenuItemsFromRole(SystemRole systemRole,
            List<MenuItem> menuItemList) throws BusinessException;

    /**
     * add or remove a list of relatedTask items from a system role.
     *
     * @param systemRole
     * @param relatedTaskIdList
     * @throws BusinessException
     */
    void assignOrRevokeRelatedTaskFromRole(SystemRole systemRole,
            List<RelatedTask> relatedTaskList) throws BusinessException;

    /**
     * add or remove a list of roles from a user
     *
     * @param userLogin
     * @param roleIdList
     * @throws BusinessException
     */
    void assignOrRevokeRolesFromUser(UserLogin userLogin,
            List<SystemRole> roleIdList) throws BusinessException;

    /**
     * returns true if the {@link UserLogin} for this userLoginId is either a
     * primary of secondary school administrator or false otherwise.
     *
     * Primary administrator are the administrator that are attached with the
     * school creation and don't necessarily belong to a physical user.
     * Secondary administrator are those {@link Employee} who have been assigned
     * the SCHOOL_ADMINISTRATOR role by a primary administrator.
     *
     * @param userLoginId
     * @return true if the {@link UserLogin} for this userLoginId is either a
     * primary of secondary school administrator or false otherwise.
     */
    boolean isSchoolAdmin(Integer userLoginId);

    /**
     *
     *
     * @param userLogin
     * @return Map<Menu, List<MenuItem>>
     */
    Map<Menu, List<MenuItem>> loadUserLoginMenuStructure(UserLogin userLogin);
}
