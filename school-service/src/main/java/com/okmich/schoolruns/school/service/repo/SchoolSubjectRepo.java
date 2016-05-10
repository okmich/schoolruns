/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.SchoolSubject;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface SchoolSubjectRepo extends JpaRepository<SchoolSubject, Integer> {

    @Query(name = "findBySchool", value = "SELECT s FROM SchoolSubject s "
    + "WHERE s.schoolId = ?1 ORDER BY s.gradeLevel.gradeLevelId")
    List<SchoolSubject> findBySchool(Integer schoolId);

    @Query(name = "findByCode", value = "SELECT s FROM SchoolSubject s "
    + "WHERE s.schoolId = ?1 AND s.subjectCode = ?2")
    List<SchoolSubject> findByCode(Integer schoolId, String code);

    @Query(name = "findByGradeSubject", value = "SELECT s FROM SchoolSubject s "
    + "WHERE s.schoolId = ?1 AND s.gradeLevel.gradeLevelId = ?2 "
    + "AND s.subject.subjectId = ?3")
    List<SchoolSubject> findByGradeSubject(Integer schoolId, Integer gradeLevelId,
            Integer subjectId);

    @Query(name = "findByGrade", value = "SELECT s FROM SchoolSubject s "
    + "WHERE s.schoolId = ?1 AND s.gradeLevel.gradeLevelId = ?2 ")
    List<SchoolSubject> findByGrade(Integer schoolId, Integer gradeLevelId);
}
