/**
 *
 */
package com.okmich.schoolruns.security.service.repo;

import com.okmich.schoolruns.common.entity.RelatedTask;
import com.okmich.schoolruns.common.entity.RoleRelatedTask;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Michael
 *
 */
@Repository
public interface RoleRelatedTaskRepo extends
        JpaRepository<RoleRelatedTask, Integer> {

    @Query(name = "findRoleRelatedTaskByModule", value = "SELECT r.relatedTask FROM RoleRelatedTask r WHERE r.relatedTask.relatedTaskId = ?1")
    List<RelatedTask> findRoleRelatedTaskByModule(Integer relatedTaskId);

    @Query(name = "findRoleRelatedTaskByRole", value = "SELECT r.relatedTask FROM RoleRelatedTask r WHERE r.systemRole.systemRoleId = ?1")
    List<RelatedTask> findRoleRelatedTaskByRole(Integer systemRoleId);

    @Query(name = "findByRoleRelatedTask", value = "SELECT r FROM RoleRelatedTask r WHERE r.systemRole.systemRoleId = ?1 AND r.relatedTask NOT IN ?2")
    List<RoleRelatedTask> findByRoleRelatedTask(Integer systemRoleId, List<RelatedTask> roleRelatedTask);
}
