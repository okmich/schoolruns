/**
 *
 */
package com.okmich.schoolruns.security.service.repo;

import com.okmich.schoolruns.common.entity.Menu;
import com.okmich.schoolruns.common.entity.SystemRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Michael
 *
 */
@Repository
public interface MenuRepo extends JpaRepository<Menu, Integer> {

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Query(name = "findMenuByUserRole", value = "SELECT m FROM Menu m LEFT JOIN m.module mo WHERE "
    + "m.admin = false AND (m.module IS NULL OR m.module IN ("
    + "SELECT o.module FROM SchoolModule o WHERE o.school.schoolId = ?1)) "
    + "AND m.menuId IN ("
    + "SELECT r.menu.menuId FROM RoleMenu r WHERE r.systemRole in (?2))")
    List<Menu> findMenuByUserRole(Integer schoolId, List<SystemRole> systemRoles);

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Query(name = "findMenuByUserRole2", value = "SELECT m FROM Menu m LEFT JOIN m.module mo WHERE "
    + "m.admin = true  "
    + "AND m.menuId IN ("
    + "SELECT r.menu.menuId FROM RoleMenu r WHERE r.systemRole in (?1))")
    List<Menu> findMenuByUserRole(List<SystemRole> systemRoles);
}