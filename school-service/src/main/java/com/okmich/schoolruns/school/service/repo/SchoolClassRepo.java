/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.SchoolClass;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface SchoolClassRepo extends JpaRepository<SchoolClass, Integer> {

    @Query(name = "findByCode", value = "SELECT s FROM SchoolClass s WHERE s.code = ?1 AND s.schoolId = ?2")
    SchoolClass findByCode(String code, Integer schoolId);

    @Query(name = "findBySchool", value = "SELECT s FROM SchoolClass s "
    + "WHERE s.schoolId = ?1 ORDER BY s.gradeLevel.gradeLevelId, s.description")
    List<SchoolClass> findBySchool(Integer schoolId);
}
