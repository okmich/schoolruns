/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.schoolruns.common.entity.Account;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael Enudi
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {

    /**
     *
     *
     * @param schoolId
     * @param accountCode
     * @return List<Account>
     */
    @Query(name = "findSchoolAccount", value = "SELECT a FROM Account a "
    + "WHERE a.schoolId = ?1 AND a.accountCode = ?2")
    List<Account> findSchoolAccount(Integer schoolId, String accountCode);
}
