/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.StudentDiscipline;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface StudentDisciplineRepo extends JpaRepository<StudentDiscipline, Integer> {

    @Query(name = "findStudentDisciplines", value = "SELECT s FROM StudentDiscipline s WHERE "
    + "s.schoolStudent.student.studentId = ?1")
    List<StudentDiscipline> findStudentDisciplines(Integer studentId);

    @Query(name = "findStudentDisciplines", value = "SELECT s FROM StudentDiscipline s WHERE "
    + "s.schoolStudent.schoolYear.schoolId = ?1 AND s.schoolStudent.registrationNo = ?2")
    List<StudentDiscipline> findStudentDisciplines(Integer schoolId, String registrationNo);
}
