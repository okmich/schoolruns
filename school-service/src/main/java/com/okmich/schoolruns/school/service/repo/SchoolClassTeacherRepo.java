/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.SchoolClassTeacher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface SchoolClassTeacherRepo extends JpaRepository<SchoolClassTeacher, Integer> {

    @Query(name = "findBySchoolTeacher", value = "SELECT s FROM SchoolClassTeacher s WHERE s.schoolClass.schoolClassId = ?1 "
    + "AND s.employee.employeeId = ?2 AND s.allocationType.allocationTypeId = ?3 AND "
    + "s.schoolYear.schoolYearId = ?4 AND s.status= 'A'")
    List<SchoolClassTeacher> findBySchoolTeacher(Integer schoolClassId, Integer employeeId,
            Integer allocationTypeId, Integer schoolYearId);
}
