/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.common.entity.StudentSubject;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Repository
public interface StudentSubjectRepo extends JpaRepository<StudentSubject, Integer> {

    /**
     *
     * @param schoolStudentId
     * @param schoolSubjectId
     *
     * @return List<StudentSubject>
     */
    @Transactional(readOnly = false)
    @Query(name = "findByStudentSubject", value = "SELECT s FROM StudentSubject s "
    + "WHERE s.schoolStudent.schoolStudentId = ?1 AND "
    + "s.schoolSubject.schoolSubjectId = ?2 AND s.status = 'A'")
    List<StudentSubject> findByStudentSubject(
            Integer schoolStudentId, Integer schoolSubjectId);

    /**
     *
     * @param schoolStudentId
     * @return List<StudentSubject>
     */
    @Transactional(readOnly = false)
    @Query(name = "findAllSubjectByStudent", value = "SELECT s FROM StudentSubject s "
    + "WHERE s.schoolStudent.schoolStudentId = ?1")
    List<StudentSubject> findAllSubjectByStudent(Integer schoolStudentId);

    /**
     *
     * @param schoolStudentId
     * @return List<SchoolSubject>
     */
    @Transactional(readOnly = false)
    @Query(name = "findSubjectByStudent", value = "SELECT s.schoolSubject FROM StudentSubject s "
    + "WHERE s.schoolStudent.schoolStudentId = ?1 AND s.status = 'A'")
    List<SchoolSubject> findSubjectByStudent(Integer schoolStudentId);

    /**
     *
     * @param schoolSubjectId
     * @return List<SchoolStudent>
     */
    @Transactional(readOnly = false)
    @Query(name = "findStudentBySubject", value = "SELECT s.schoolStudent FROM StudentSubject s "
    + "WHERE s.schoolSubject.schoolSubjectId = ?1 AND s.status = 'A'")
    List<SchoolStudent> findStudentBySubject(Integer schoolSubjectId);

    /**
     *
     * @param schoolClassId
     * @return List<SchoolSubject>
     */
    @Transactional(readOnly = false)
    @Query(name = "findSubjectsByClass", 
    value = "SELECT s FROM SchoolSubject s where (s.schoolId, s.gradeLevel.gradeLevelId) = "
    + "(SELECT c.schoolId, c.gradeLevel.gradeLevelId from SchoolClass c where c.schoolClassId = ?1)")
    List<SchoolSubject> findSubjectsByClass(Integer schoolClassId);
}
