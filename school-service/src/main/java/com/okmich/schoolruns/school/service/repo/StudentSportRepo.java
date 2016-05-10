/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.common.entity.SportCategory;
import com.okmich.schoolruns.common.entity.StudentSport;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface StudentSportRepo extends JpaRepository<StudentSport, Integer> {

    @Query(name = "findStudentSports", value = "SELECT s FROM StudentSport s WHERE "
    + "s.schoolStudent.student.studentId = ?1")
    public List<StudentSport> findStudentSports(Integer studentId);

    @Query(name = "findStudentSports", value = "SELECT s FROM StudentSport s WHERE "
    + "s.schoolStudent.schoolYear.schoolId = ?1 AND s.schoolStudent.registrationNo = ?2")
    public List<StudentSport> findStudentSports(Integer schoolId, String registrationNo);

    @Query(name = "findDuplicate", value = "SELECT s FROM StudentSport s WHERE "
    + "s.schoolStudent = ?1 AND s.sportCategory = ?2 AND s.schoolYear = ?3 AND s.studentSportId <> ?4")
    List<StudentSport> findDuplicate(SchoolStudent schoolStudent, SportCategory sportCategory,
            SchoolYear schoolYear, Integer studentSportId);
}
