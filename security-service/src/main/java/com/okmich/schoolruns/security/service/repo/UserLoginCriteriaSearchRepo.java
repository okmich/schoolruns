/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.security.service.repo;

import com.okmich.schoolruns.common.entity.UserLogin;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface UserLoginCriteriaSearchRepo {

    public List<UserLogin> findUserLogins(UserLoginQueryCriteria criteria);
}
