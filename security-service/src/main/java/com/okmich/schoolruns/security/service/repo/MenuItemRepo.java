/**
 *
 */
package com.okmich.schoolruns.security.service.repo;

import com.okmich.schoolruns.common.entity.MenuItem;
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
public interface MenuItemRepo extends JpaRepository<MenuItem, Integer> {

    /**
     *
     * @param menuId
     * @return
     */
    @Query(name = "findMenuItemsByMenu", value = "SELECT m FROM MenuItem m WHERE m.menu.menuId = ?1")
    List<MenuItem> findMenuItemsByMenu(Integer menuId);

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Query(name = "findMenuItemByUserRole",
    value = "SELECT m FROM MenuItem m LEFT JOIN m.module mo WHERE "
    + "(m.module IS NULL OR m.module IN ("
    + "SELECT o.module FROM SchoolModule o WHERE o.school.schoolId = ?1)) "
    + "AND m.menuItemId IN ("
    + "SELECT r.menuItem.menuItemId FROM RoleMenuItem r WHERE r.systemRole in (?2)) "
    + "AND m.menu.menuId = ?3")
    List<MenuItem> findMenuItemByUserRole(Integer schoolId, List<SystemRole> systemRoles,
            Integer menuId);

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Query(name = "findMenuItemByUserRole2",
    value = "SELECT m FROM MenuItem m LEFT JOIN m.module mo WHERE "
    + "m.menu.menuId = ?1 "
    + "AND m.menuItemId IN ("
    + "SELECT r.menuItem.menuItemId FROM RoleMenuItem r WHERE r.systemRole in (?2))")
    List<MenuItem> findMenuItemByUserRole(Integer menuId, List<SystemRole> systemRoles);
}
