/**
 *
 */
package com.okmich.schoolruns.security.service.repo;

import com.okmich.schoolruns.common.entity.SystemRole;
import com.okmich.schoolruns.common.entity.UserRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Michael
 *
 */
@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {

    @Query(name = "findUserRoleByUser",
    value = "SELECT u.systemRole FROM UserRole u WHERE u.userLogin.userLoginId = ?1")
    List<SystemRole> findUserRoleByUser(Integer userLoginId);

    @Query(name = "findAllUserRoleByUser",
    value = "SELECT u FROM UserRole u WHERE u.userLogin.userLoginId = ?1")
    List<UserRole> findAllUserRoleByUser(Integer userLoginId);
}
