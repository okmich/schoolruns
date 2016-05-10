/**
 * 
 */
package com.okmich.schoolruns.security.service.repo;

import com.okmich.schoolruns.common.entity.RelatedTask;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * @author Michael
 * 
 */
@Repository
public interface RelatedTaskRepo extends JpaRepository<RelatedTask, Integer> {

	@Query(name = "findRelatedTaskByMenuItem", value = "SELECT r FROM RelatedTask r WHERE r.contextCode = ?1")
	List<RelatedTask> findRelatedTaskForContext(String contextCode);
}
