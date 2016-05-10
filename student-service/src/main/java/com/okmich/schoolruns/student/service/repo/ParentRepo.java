/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.student.service.repo;

import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.SchoolYear;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface ParentRepo extends JpaRepository<Parent, String> {

    @Query(name = "findSchoolParent", value = "SELECT DISTINCT s.student.parent FROM SchoolStudent s "
    + "where s.schoolYear = ?1")
    List<Parent> findSchoolParents(SchoolYear schoolYear);

    @Query(name = "findSchoolSectionParents",
    value = "SELECT p FROM Parent p WHERE p.phoneNumber IN ("
    + "SELECT s.parent.phoneNumber FROM SchoolStudent ss JOIN ss.student s "
    + "JOIN ss.schoolClass sc WHERE "
    + "s.parent IS NOT NULL AND ss.schoolYear.schoolYearId = ?1 AND "
    + "sc.schoolSection.schoolSectionId = ?2)")
    List<Parent> findSchoolSectionParents(Integer schoolYearId, Integer schoolSectionId);

    @Query(name = "findSchoolGradeParents",
    value = "SELECT p FROM Parent p WHERE p.phoneNumber IN ("
    + "SELECT s.parent.phoneNumber FROM SchoolStudent ss JOIN ss.student s "
    + "JOIN ss.schoolClass sc WHERE "
    + "s.parent IS NOT NULL AND ss.schoolYear.schoolYearId = ?1 AND "
    + "sc.gradeLevel.gradeLevelId = ?2)")
    List<Parent> findSchoolGradeParents(Integer schoolYearId, Integer gradeLevelId);

    @Query(name = "findSchoolClassParents",
    value = "SELECT p FROM Parent p WHERE p.phoneNumber IN ("
    + "SELECT s.parent.phoneNumber FROM SchoolStudent ss JOIN ss.student s WHERE "
    + "s.parent IS NOT NULL AND ss.schoolYear.schoolYearId = ?1 AND "
    + "ss.schoolClass.schoolClassId = ?2)")
    List<Parent> findSchoolClassParents(Integer schoolYearId, Integer schoolClassId);

    @Query(name = "findSchoolStudentParent",
    value = "SELECT ss.student.parent FROM SchoolStudent ss WHERE ss.schoolStudentId = ?1")
    Parent findSchoolStudentParent(Integer schoolStudentId);
}