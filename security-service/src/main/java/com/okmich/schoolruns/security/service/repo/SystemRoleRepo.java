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
public interface SystemRoleRepo extends JpaRepository<SystemRole, Integer> {

    /**
     * returns all active system roles in the system
     *
     * @return List<SystemRole>
     */
    @Query(name = "findSystemRoleList", value = "SELECT s FROM SystemRole s WHERE s.status = 'A'")
    List<SystemRole> findSystemRoleList();

    /**
     * returns all active system roles in the system for which isAdmin is false
     *
     * @return List<SystemRole>
     */
    @Query(name = "findNonAdminSystemRoles", value = "SELECT s FROM SystemRole s WHERE s.status = 'A' AND s.admin = false")
    List<SystemRole> findNonAdminSystemRoles();

    /**
     * returns all active system roles in the system for which isAdmin is true
     *
     * @return List<SystemRole>
     */
    @Query(name = "findAdminSystemRoles", value = "SELECT s FROM SystemRole s WHERE s.status = 'A' AND s.admin = true")
    List<SystemRole> findAdminSystemRoles();

    @Query(name = "findAllRolesForUserLogin",
    value = "SELECT u.systemRole FROM UserRole u WHERE u.systemRole.systemRoleId = ?1 "
    + "OR u.systemRole IN (SELECT o.systemRole FROM UserLogin o WHERE o.userLoginId = ?2)")
    List<SystemRole> findAllRolesForUserLogin(Integer systemRoleId,
            Integer userLoginId);
}
