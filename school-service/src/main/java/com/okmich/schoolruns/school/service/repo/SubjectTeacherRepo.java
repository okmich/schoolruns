/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.SubjectTeacher;
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
public interface SubjectTeacherRepo extends JpaRepository<SubjectTeacher, Integer> {

    @Transactional(readOnly = false)
    @Query(name = "findByYearSubjectTeacher", value = "SELECT s FROM SubjectTeacher s WHERE "
    + "s.employee.employeeId = ?1 AND s.schoolSubject.schoolSubjectId = ?2")
    List<SubjectTeacher> findBySubjectTeacher(
            Integer employeeId, Integer schoolSubjectId);

    @Transactional(readOnly = false)
    @Query(name = "findAllSubjectByTeacher", value = "SELECT s FROM SubjectTeacher s "
    + "WHERE s.employee.employeeId = ?1")
    List<SubjectTeacher> findAllSubjectByTeacher(Integer employeeId);
}
