/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.WarningLevel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface WarningLevelRepo extends JpaRepository<WarningLevel, Integer> {

    @Query(name = "getWarningLevelByName",
    value = "SELECT w FROM WarningLevel w WHERE LOWER(w.description) = ?1")
    List<WarningLevel> getWarningLevelByName(String description);
}
