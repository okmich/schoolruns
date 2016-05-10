/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.security.service.repo;

import com.okmich.common.entity.repo.CriteriaSearchWorker;
import com.okmich.schoolruns.common.entity.UserLogin;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Repository("UserLoginCriteriaSearchRepo")
@Transactional(readOnly = true)
public class UserLoginCriteriaSearchRepoImpl implements UserLoginCriteriaSearchRepo {

    /**
     *
     */
    private CriteriaSearchWorker<UserLogin, UserLoginQueryCriteria> repoWorker;
    @PersistenceContext
    private EntityManager entityManager;

    public UserLoginCriteriaSearchRepoImpl() {
        repoWorker =
                new CriteriaSearchWorker<UserLogin, UserLoginQueryCriteria>();
    }

    @Override
    public List<UserLogin> findUserLogins(UserLoginQueryCriteria criteria) {
        return repoWorker.findByCriteria(entityManager, criteria);
    }
}
