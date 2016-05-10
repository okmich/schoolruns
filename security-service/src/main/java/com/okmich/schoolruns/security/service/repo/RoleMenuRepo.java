/**
 *
 */
package com.okmich.schoolruns.security.service.repo;

import com.okmich.schoolruns.common.entity.Menu;
import com.okmich.schoolruns.common.entity.RoleMenu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Michael
 *
 */
@Repository
public interface RoleMenuRepo extends JpaRepository<RoleMenu, Integer> {

    @Query(name = "findRoleMenuByMenu", value = "SELECT r.menu FROM RoleMenu r WHERE r.menu.menuId = ?1")
    List<Menu> findRoleMenuByMenu(Integer menuId);

    @Query(name = "findRoleMenuByRole",
    value = "SELECT r.menu FROM RoleMenu r WHERE r.systemRole.systemRoleId = ?1")
    List<Menu> findRoleMenuByRole(Integer systemRoleId);

    @Query(name = "findByRoleMenu", value = "SELECT r FROM RoleMenu r "
    + "WHERE r.systemRole.systemRoleId = ?1 AND r.menu.menuId = ?2")
    RoleMenu findByRoleMenu(Integer systemRoleId, Integer menuId);
}
