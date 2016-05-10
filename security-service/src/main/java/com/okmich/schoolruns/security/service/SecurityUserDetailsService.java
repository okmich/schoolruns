/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.security.service;

import com.okmich.common.exception.ErrorConstants;
import com.okmich.schoolruns.common.entity.SystemRole;
import com.okmich.schoolruns.common.entity.UserInfo;
import com.okmich.schoolruns.common.entity.UserLogin;
import com.okmich.schoolruns.security.service.repo.UserLoginRepo;
import com.okmich.schoolruns.security.service.repo.UserRoleRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Michael
 */
@Service("securityUserDetailsService")
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserLoginRepo userLoginRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;

    public SecurityUserDetailsService() {
    }

    @Override
    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin userLogin = userLoginRepo.findByUsername(username);
        if (userLogin == null) {
            throw new UsernameNotFoundException(ErrorConstants.INVALID_USERNAME);
        }
        UserInfo userInfo = buildUserInfo(userLogin);
        //get the system roles for the user
        List<SystemRole> sysRoles = userRoleRepo.findUserRoleByUser(userLogin.getUserLoginId());
        for (SystemRole sr : sysRoles) {
            userInfo.addAuthourity(new SimpleGrantedAuthority(sr.getCode()));
        }
        return userInfo;
    }

    private UserInfo buildUserInfo(UserLogin userLogin) {
        UserInfo userInfo = new UserInfo();

        userInfo.setChangeOnNextLogin(userLogin.getChangeOnNextLogin());
        userInfo.setEmail(userLogin.getEmail());
        userInfo.setGeneratedPassword(userLogin.getGeneratedPassword());
        userInfo.setIsAdmin(userLogin.getIsAdmin());
        userInfo.setLoginAttempts(userLogin.getLoginAttempts());
        userInfo.setModifiedBy(userLogin.getModifiedBy());
        userInfo.setModifiedTime(userLogin.getModifiedTime());
        userInfo.setPassword(userLogin.getPassword());
        userInfo.setSchool(userLogin.getSchool());
        userInfo.setStatus(userLogin.getStatus());
        userInfo.setUserLoginId(userLogin.getUserLoginId());
        userInfo.setUsername(userLogin.getUsername());

        return userInfo;
    }
}
