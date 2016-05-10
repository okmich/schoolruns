package com.okmich.schoolruns.security.service;

import com.okmich.schoolruns.common.entity.UserRole;
import com.okmich.schoolruns.common.entity.UserLogin;
import com.okmich.common.exception.BusinessException;
import com.okmich.common.exception.ErrorConstants;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.*;
import com.okmich.schoolruns.security.service.repo.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Service("securityService")
@Transactional
public class SecurityServiceImpl implements SecurityService {

    /**
     * holds userRoledao.
     */
    @Autowired
    private UserRoleRepo userRoleRepo;
    /**
     * holds the passwordEncoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * holds the userLoginDao.
     */
    @Autowired
    private UserLoginRepo userLoginRepo;
    /**
     * holds the systemRoleRepo.
     */
    @Autowired
    private UserLoginCriteriaSearchRepo userLoginCriteriaSearchRepo;
    /**
     * holds the role specific to menu.
     */
    @Autowired
    private RoleMenuRepo roleMenuRepo;
    /**
     * holds the role specific to menu item.
     */
    @Autowired
    private RoleMenuItemRepo roleMenuItemRepo;
    /**
     * holds the role specific to menu item.
     */
    @Autowired
    private RoleRelatedTaskRepo roleRelatedTaskRepo;
    /**
     * holds the menu item repo.
     */
    @Autowired
    private MenuItemRepo menuItemRepo;
    /**
     * holds the menus.
     */
    @Autowired
    private MenuRepo menuRepo;
    /**
     * holds the menus.
     */
    @Autowired
    private RelatedTaskRepo relatedTaskRepo;
    /**
     * holds the systemRoleRepo.
     */
    @Autowired
    private SystemRoleRepo systemRoleRepo;
    /**
     * The length of the generated password.
     */
    public static final int PASSWORDLENGTH = 10;
    /**
     * A string use to generate a random password.
     */
    public static final String RANDOMSTRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public SecurityServiceImpl() {
    }

    /**
     * create a system user.
     *
     * @param userLogin - userLogin
     * @throws BusinessException BusinessException
     * @return flag indicates whether userLogin creation is succeeded.
     */
    @Override
    public UserLogin createUserLogin(UserLogin userLogin) throws BusinessException {
        String userName = userLogin.getUsername();
        /**
         * validate user name
         */
        if (!userLoginRepo.findDuplicateByEmailAndRole(
                userLogin.getEmail(),
                userLogin.getDefaultSystemRole().getSystemRoleId()).isEmpty()) {
            throw new BusinessException(
                    ErrorConstants.ERROR_MESSAGE_UNIQUE_EMAIL);
        }
        if (findUserLogin(userName) != null) {
            throw new BusinessException(
                    ErrorConstants.ERROR_MESSAGE_UNIQUE_UNAME);
        }
        userLogin.setPassword(passwordEncoder.encodePassword(
                userLogin.getPassword(), userLogin.getUsername()));
        userLogin.setModifiedTime(new Date());
        userLogin.setStatus(CommonConstants.STATUS_ACTIVE);
        try {
            //save User Object
            return userLoginRepo.save(userLogin);
        } catch (Exception ex) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     *
     * @param userLogin
     * @return
     * @throws BusinessException
     */
    @Override
    public UserLogin saveUserLogin(UserLogin userLogin)
            throws BusinessException {
        UserLogin _userLogin = findUserLogin(userLogin.getUserLoginId());

        _userLogin.setEmail(userLogin.getEmail());
        _userLogin.setIsAdmin(userLogin.getIsAdmin());
        _userLogin.setModifiedBy(userLogin.getModifiedBy());
        _userLogin.setModifiedTime(userLogin.getModifiedTime());
        _userLogin.setPhoneNumber(userLogin.getPhoneNumber());
        _userLogin.setSchoolAdmin(userLogin.isSchoolAdmin());
        _userLogin.setStatus(userLogin.getStatus());
        try {
            //save User Object
            return userLoginRepo.save(_userLogin);
        } catch (Exception ex) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public UserLogin findUserLogin(Integer userLoginId) {
        return userLoginRepo.findOne(userLoginId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserLogin> findUserLoginList(UserLoginQueryCriteria criteria) {
        return userLoginCriteriaSearchRepo.findUserLogins(criteria);
    }

    /**
     * Retrieve the available UserLogin object.
     *
     * @param userName - username.
     * @return UserLogin object.
     * @throws BusinessException - throw detailed exception.
     */
    @Override
    @Transactional(readOnly = true)
    public UserLogin findUserLogin(String userName) {
        return userLoginRepo.findByUsername(userName);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public UserLogin resetPassword(UserLogin userLogin) throws BusinessException {
        UserLogin _userLogin = new UserLogin();
        try {
            //generate new password
            String newPassword = generateRandomPassword();
            userLogin.setPassword(passwordEncoder.encodePassword(newPassword,
                    userLogin.getUsername()));
            userLogin.setGeneratedPassword(Boolean.TRUE);
            userLogin.setChangeOnNextLogin(CommonConstants.CONSTANT_YES);
            userLogin.setModifiedTime(new Date());
            userLogin = userLoginRepo.save(userLogin);
            //return new password
            _userLogin.setPassword(newPassword);
            _userLogin.setUsername(userLogin.getUsername());
            _userLogin.setEmail(userLogin.getEmail());
            _userLogin.setUserLoginId(userLogin.getUserLoginId());
            _userLogin.setPhoneNumber(userLogin.getPhoneNumber());
            if (_userLogin.getSchool().getSchoolId() != null) {
                _userLogin.setSchool(userLogin.getSchool());
            }
            return _userLogin;
        } catch (Exception ex) {
            Logger.getLogger(SecurityService.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public UserLogin resetPasswordByEmail(String email, boolean isParent) throws BusinessException {
        //find the user for the email
        UserLogin userLogin;
        if (isParent) {
            userLogin = userLoginRepo.findParentUserByEmail(email);
        } else {
            userLogin = userLoginRepo.findNonParentUserByEmail(email);
        }
        if (userLogin == null) {
            throw new BusinessException(ErrorConstants.ERROR_INVALID_EMAIL);
        }
        //call the reset method
        return resetPassword(userLogin);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public UserLogin resetPasswordByMobile(String phoneNumber, boolean isParent) throws BusinessException {
        //find the user for this phone number
        UserLogin userLogin;
        if (isParent) {
            userLogin = userLoginRepo.findParentUserByPhoneNumber(phoneNumber);
        } else {
            userLogin = userLoginRepo.findByPhoneNumber(phoneNumber);
        }
        if (userLogin == null) {
            throw new BusinessException(ErrorConstants.INVALID_PHONE_NUMBER);
        }
        //call the resetPassword method
        return resetPassword(userLogin);
    }

    /**
     * Generate a random password.
     *
     * @throws BusinessException BusinessException
     * @return Returns the randomly generated pasword.
     */
    @Override
    @Transactional(readOnly = true)
    public String generateRandomPassword() throws BusinessException {

        Random random = new Random();
        StringBuilder password = new StringBuilder(PASSWORDLENGTH);
        for (int i = 0; i < PASSWORDLENGTH; i++) {
            password.append(RANDOMSTRING.charAt(random.nextInt(RANDOMSTRING.length())));
        }
        return password.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean changePassword(UserLogin userLogin, String currentPassword, String newPassword)
            throws BusinessException {
        try {
            if (!validatePassword(currentPassword, userLogin)) {
                throw new BusinessException(ErrorConstants.INVALID_PASSWORD);
            }
            UserLogin _userLogin = userLoginRepo.findOne(userLogin.getUserLoginId());
            if (_userLogin != null) {
                _userLogin.setPassword(passwordEncoder.encodePassword(newPassword,
                        userLogin.getUsername()));
                _userLogin.setModifiedTime(new Date());
                userLoginRepo.save(_userLogin);
                return true;
            }
            return false;
        } catch (Exception ex) {
            Logger.getLogger(SecurityService.class.getName()).log(Level.SEVERE, null, ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validatePassword(String password, UserLogin userLogin)
            throws BusinessException {
        return passwordEncoder.isPasswordValid(userLogin.getPassword(),
                password, userLogin.getUsername());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public UserLogin findUserLoginForEmployee(Integer employeeId) {
        return userLoginRepo.findEmployeeUserLogin(employeeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public UserLogin findUserLoginForParent(String parentId) {
        return userLoginRepo.findParentLogin(parentId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<SystemRole> findSystemRoleList() {
        return systemRoleRepo.findSystemRoleList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SystemRole saveSystemRole(SystemRole systemRole)
            throws BusinessException {
        return systemRoleRepo.save(systemRole);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public SystemRole findSystemRole(int systemRoleId) throws BusinessException {
        return systemRoleRepo.findOne(systemRoleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<SystemRole> findAllSystemRoles() {
        return systemRoleRepo.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<SystemRole> findNonAdminSystemRoles() {
        return systemRoleRepo.findNonAdminSystemRoles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<SystemRole> findAdminSystemRoles() {
        return systemRoleRepo.findAdminSystemRoles();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Menu> findAllMenus() {
        return menuRepo.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Menu saveMenu(Menu menu) throws BusinessException {
        return menuRepo.save(menu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItem saveMenuItem(MenuItem menuItem) throws BusinessException {
        return menuItemRepo.save(menuItem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public MenuItem findMenuItem(Integer menuItemId) throws BusinessException {
        return menuItemRepo.findOne(menuItemId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<MenuItem> findMenuItemsByMenu(Integer menuId)
            throws BusinessException {
        return menuItemRepo.findMenuItemsByMenu(menuId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<MenuItem> findAllMenuItems() {
        List<MenuItem> menuItems;
        try {
            menuItems = (List<MenuItem>) menuItemRepo.findAll();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return new ArrayList<>();
        }
        return menuItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RelatedTask saveRelatedTask(RelatedTask relatedTask)
            throws BusinessException {
        return relatedTaskRepo.save(relatedTask);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<RelatedTask> findRelatedTaskByContext(String contextCode) {
        return relatedTaskRepo.findRelatedTaskForContext(contextCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public RelatedTask findRelatedTask(Integer relatedTaskId)
            throws BusinessException {
        return relatedTaskRepo.findOne(relatedTaskId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<RelatedTask> findAllRelatedTasks() {
        List<RelatedTask> relatedTasks;
        try {
            relatedTasks = (List<RelatedTask>) relatedTaskRepo.findAll();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return new ArrayList<>();
        }
        return relatedTasks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Menu> findAssignedMenusToRoles(Integer systemRoleId) {
        List<Menu> menus;
        try {
            menus = (List<Menu>) roleMenuRepo.findRoleMenuByRole(systemRoleId);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return new ArrayList<>();
        }
        return menus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<MenuItem> findAssignedMenuItemsToRoles(Integer systemRoleId) {
        List<MenuItem> menuItems;
        try {
            menuItems = roleMenuItemRepo.findRoleMenuItemByRole(systemRoleId);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return new ArrayList<>();
        }
        return menuItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<RelatedTask> findAssignedRelatedTaksToRoles(
            Integer systemRoleId) {
        List<RelatedTask> relatedTasks;
        try {
            relatedTasks = roleRelatedTaskRepo.findRoleRelatedTaskByRole(systemRoleId);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return new ArrayList<>();
        }
        return relatedTasks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<SystemRole> findAssignedRolesToUser(Integer userLoginId)
            throws BusinessException {
        return userRoleRepo.findUserRoleByUser(userLoginId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignOrRevokeMenusFromRole(SystemRole systemRole,
            List<Menu> menuList) throws BusinessException {
        // get existing role menu item for the user
        List<Menu> existingMenu = roleMenuRepo.findRoleMenuByRole(systemRole.getSystemRoleId());
        // disable role menu item not existing in the new list
        RoleMenu roleMenu;
        for (Menu _menu : menuList) {
            if (!existingMenu.contains(_menu)) {
                roleMenu = new RoleMenu();

                roleMenu.setMenu(_menu);
                roleMenu.setModifiedBy(systemRole.getModifiedBy());
                roleMenu.setStatus(CommonConstants.STATUS_ACTIVE);
                roleMenu.setSystemRole(systemRole);
                roleMenuRepo.save(roleMenu);

                //add to the existing menu
                existingMenu.add(_menu);
            }
        }
        for (Menu _menu : existingMenu) {
            if (!menuList.contains(_menu)) {
                roleMenu = roleMenuRepo.findByRoleMenu(
                        systemRole.getSystemRoleId(), _menu.getMenuId());
                //delete that rolemenu from the system
                roleMenuRepo.delete(roleMenu);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignOrRevokeMenuItemsFromRole(SystemRole systemRole,
            List<MenuItem> menuItemList) throws BusinessException {
        // get existing role menu item for the user
        List<MenuItem> existingMenuItem = roleMenuItemRepo.findRoleMenuItemByRole(systemRole.getSystemRoleId());
        // disable role menu item not existing in the new list
        RoleMenuItem roleMenuItem;
        for (MenuItem r : menuItemList) {
            if (!existingMenuItem.contains(r)) {
                roleMenuItem = new RoleMenuItem();

                roleMenuItem.setMenuItem(r);
                roleMenuItem.setModifiedBy(systemRole.getModifiedBy());
                roleMenuItem.setStatus(CommonConstants.STATUS_ACTIVE);
                roleMenuItem.setSystemRole(systemRole);
                roleMenuItemRepo.save(roleMenuItem);

                //add to the existing menu
                existingMenuItem.add(r);
            }
        }
        // save all the others in the list that are new
        for (MenuItem r : menuItemList) {
            if (!existingMenuItem.contains(r)) {
                roleMenuItem = roleMenuItemRepo.findByRoleMenuItem(
                        systemRole.getSystemRoleId(), r.getMenuItemId());
                //delete that rolemenu from the system
                roleMenuItemRepo.delete(roleMenuItem);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignOrRevokeRelatedTaskFromRole(SystemRole systemRole,
            List<RelatedTask> relatedTaskList) throws BusinessException {
        // get existing related tasks for the user
        List<RelatedTask> existingRelTask = roleRelatedTaskRepo.findRoleRelatedTaskByRole(
                systemRole.getSystemRoleId());
        // disable related task roles not existing in the new list
        RoleRelatedTask roleRelatedTask;
        for (RelatedTask r : relatedTaskList) {
            if (!existingRelTask.contains(r)) {
                roleRelatedTask = new RoleRelatedTask();

                roleRelatedTask.setRelatedTask(r);
                roleRelatedTask.setModifiedBy(systemRole.getModifiedBy());
                roleRelatedTask.setStatus(CommonConstants.STATUS_ACTIVE);
                roleRelatedTask.setSystemRole(systemRole);
                roleRelatedTaskRepo.save(roleRelatedTask);

                //add to the existing menu
                existingRelTask.add(r);
            }
        }
        //delete the removed value
        List<RoleRelatedTask> roleRelatedTasks = roleRelatedTaskRepo.findByRoleRelatedTask(
                systemRole.getSystemRoleId(), relatedTaskList);
        //delete that rolemenu from the system
        roleRelatedTaskRepo.delete(roleRelatedTasks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignOrRevokeRolesFromUser(UserLogin userLogin,
            List<SystemRole> roleList) throws BusinessException {

        List<UserRole> _userRole =
                userRoleRepo.findAllUserRoleByUser(userLogin.getUserLoginId());
        try {
            //delete all the school menu
            userRoleRepo.delete(_userRole);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        UserRole userRole;
        List<UserRole> userRoles = new ArrayList<>();
        for (SystemRole sr : roleList) {
            userRole = new UserRole();

            userRole.setModifiedBy(userLogin.getModifiedBy());
            userRole.setModifiedTime(new Date());
            userRole.setStatus(CommonConstants.STATUS_ACTIVE);
            userRole.setSystemRole(sr);
            userRole.setUserLogin(userLogin);

            userRoles.add(userRole);
        }
        try {
            //create a list of user roles
            userRoleRepo.save(userRoles);
        } catch (Exception e) {
            Logger.getLogger(SecurityServiceImpl.class.getName()).log(
                    Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * encode password.
     *
     * @param userLogin - userLogin
     * @throws BusinessException BusinessException
     * @return encoded password.
     */
    @Override
    public String getEncodedPassword(UserLogin userLogin)
            throws BusinessException {

        return passwordEncoder.encodePassword(userLogin.getPassword(),
                userLogin.getUsername());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public UserLogin doUserLogin(String userName, String password) throws BusinessException {
        UserLogin userLogin = findUserLogin(userName);
        if (userLogin == null) {
            throw new BusinessException(ErrorConstants.INVALID_PASSWORD);
        }
        //validate password
        if (validatePassword(password, userLogin)) {
            //increase the login attempt
            try {
                userLogin.setLoginAttempts(
                        (userLogin.getLoginAttempts() == null ? 0 : userLogin.getLoginAttempts())
                        + 1);
                userLogin.setModifiedBy(userLogin.getUsername());
                userLogin.setModifiedTime(new Date());
                //save User Object
                userLoginRepo.save(userLogin);
            } catch (Exception ex) {
                throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
            }
            return userLogin;
        } else {
            throw new BusinessException(ErrorConstants.INVALID_PASSWORD);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public boolean isSchoolAdmin(Integer userLoginId) {
        UserLogin userLogin = findUserLogin(userLoginId);
        if (userLogin == null) {
            return false;
        }
        if (userLogin.isSchoolAdmin()) {
            return true;
        } else {
            List<UserRole> userRoles = userLogin.getUserRoleList();
            for (UserRole ur : userRoles) {
                if (ur.getSystemRole().equals(new SystemRole(CommonConstants.ROLE_SCHOOL_ADMIN))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Map<Menu, List<MenuItem>> loadUserLoginMenuStructure(UserLogin _userLogin) {
        Map<Menu, List<MenuItem>> menuModel = new HashMap<>();
        //get the school Id
        Integer schoolId = _userLogin.getSchool() == null
                || _userLogin.getSchool().getSchoolId() == null
                ? 0 : _userLogin.getSchool().getSchoolId();
        //get all system role for user
        List<SystemRole> systemRoles = systemRoleRepo.findAllRolesForUserLogin(
                _userLogin.getDefaultSystemRole().getSystemRoleId(),
                _userLogin.getUserLoginId());
        //getthe list of menu
        List<Menu> menus;
        if (schoolId != 0) { //this is for a school user
            menus = menuRepo.findMenuByUserRole(schoolId, systemRoles);
            for (Menu menu : menus) {
                menuModel.put(menu, menuItemRepo.findMenuItemByUserRole(
                        schoolId, systemRoles, menu.getMenuId()));
            }
        } else { //this is for an admin user
            menus = menuRepo.findMenuByUserRole(systemRoles);
            for (Menu menu : menus) {
                menuModel.put(menu, menuItemRepo.findMenuItemByUserRole(
                        menu.getMenuId(), systemRoles));
            }
        }
        return menuModel;
    }
}