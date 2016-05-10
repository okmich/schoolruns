/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.GradeLevel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface GradeLevelRepo extends JpaRepository<GradeLevel, Integer> {

    @Query(name = "findGradeByGradeName",
    value = "SELECT g FROM GradeLevel g WHERE g.description = ?1")
    List<GradeLevel> findGradeByGradeName(String description);
}
