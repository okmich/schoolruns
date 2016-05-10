/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.repo;

import com.okmich.schoolruns.common.entity.ClassPeriod;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface ClassPeriodRepo extends JpaRepository<ClassPeriod, Integer> {

    @Query(name = "findClassPeriods", value = "SELECT c FROM ClassPeriod c "
    + "WHERE c.schoolSectionId = ?1 ORDER BY c.periodNumber")
    List<ClassPeriod> findClassPeriods(Integer schoolSectionId);
}
