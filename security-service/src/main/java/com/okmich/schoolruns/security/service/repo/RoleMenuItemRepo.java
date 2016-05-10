/**
 *
 */
package com.okmich.schoolruns.security.service.repo;

import com.okmich.schoolruns.common.entity.MenuItem;
import com.okmich.schoolruns.common.entity.RoleMenuItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Michael
 *
 */
@Repository
public interface RoleMenuItemRepo extends JpaRepository<RoleMenuItem, Integer> {

    @Query(name = "findRoleModuleByModule",
    value = "SELECT r.menuItem FROM RoleMenuItem r "
    + "WHERE r.menuItem.menuItemId = ?1 AND r.status = 'A'")
    List<MenuItem> findRoleMenuItemByMenuItem(Integer menuItemId);

    @Query(name = "findRoleModuleByRole",
    value = "SELECT r.menuItem FROM RoleMenuItem r "
    + "WHERE r.systemRole.systemRoleId = ?1")
    List<MenuItem> findRoleMenuItemByRole(Integer systemRoleId);

    @Query(name = "findByRoleMenuItem", value = "SELECT r FROM RoleMenuItem r "
    + "WHERE r.systemRole.systemRoleId = ?1 AND r.menuItem.menuItemId = ?2")
    RoleMenuItem findByRoleMenuItem(Integer systemRoleId, Integer menuItemId);
}
