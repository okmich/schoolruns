/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.school.service.SchoolStudentService;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface SchoolStudentRepo extends JpaRepository<SchoolStudent, Integer> {

    @Query(name = "findBySchool", value = "SELECT s FROM SchoolStudent s "
    + "WHERE s.schoolYear.schoolId = ?1 ORDER BY s.student.surname")
    List<SchoolStudent> findBySchool(Integer schoolId);

    @Query(name = "findByRegNo", value = "SELECT s FROM SchoolStudent s "
    + "WHERE s.schoolYear.schoolId = ?1 AND s.registrationNo = ?2")
    List<SchoolStudent> findByRegistrationNo(Integer schoolId, String regisNo);

    @Query(name = "findByClass", value = "SELECT s FROM SchoolStudent s "
    + "WHERE s.status = 'A' AND s.schoolYear.schoolYearId = "
    + "(select st.schoolYearId FROM SchoolTerm st WHERE st.schoolTermId = ?1) "
    + "AND s.schoolClass.schoolClassId = ?2  ORDER BY s.student.surname ")
    List<SchoolStudent> findByClass(Integer schoolTermId, Integer schoolClassId);

    /**
     * returns all school student who belonged to a class for a certain school
     * year
     *
     * @param schoolYearId
     * @param schoolClassId
     * @return List<SchoolStudent>
     */
    @Query(name = "findByYearClass", value = "SELECT s FROM SchoolStudent s "
    + "WHERE s.schoolYear.schoolYearId = ?1 AND s.schoolClass.schoolClassId = ?2 ORDER BY s.student.surname ")
    List<SchoolStudent> findByYearClass(Integer schoolYearId, Integer schoolClassId);

    /**
     * this method is supposed to have been the same as {@link SchoolStudentRepo#findOne(java.io.Serializable)
     * } but during testing, it was discovered that the complex joins from the
     * hibernate ORM engine causes {@link SchoolStudents} without a class to
     * return null. The simple sql here therefore will not generate such a join
     * thereby bypassing the null generation and return the actual entity.
     *
     * The incident was exposed while implementing {@link SchoolStudentService#assignStudentToClass(java.util.List, com.okmich.schoolruns.school.service.data.SchoolStudentData, boolean)
     * }
     *
     * @param schoolStudentId - the id of {@link SchoolStudent}
     * @return SchoolStudent or null if the entity doesn't exist.
     */
    @Query(name = "findSchoolStudent", value = "SELECT s FROM SchoolStudent s "
    + "WHERE s.schoolStudentId = ?1")
    SchoolStudent findSchoolStudent(Integer schoolStudentId);
}