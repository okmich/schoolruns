/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.SchoolPref;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface SchoolPrefRepo extends JpaRepository<SchoolPref, Integer> {

    @Query(name = "findSchoolPrefBySchool",
    value = "SELECT s FROM SchoolPref s WHERE s.school.schoolId = (SELECT o.schoolId FROM SchoolYear o WHERE o.schoolYearId = ?1)")
    SchoolPref findSchoolPrefBySchoolYear(Integer schoolYearId);
}
