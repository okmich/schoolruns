/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.Club;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.common.entity.StudentClub;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface StudentClubRepo extends JpaRepository<StudentClub, Integer> {

    @Query(name = "findStudentClubss", value = "SELECT s FROM StudentClub s WHERE "
    + "s.schoolStudent.student.studentId = ?1")
    List<StudentClub> findStudentClubs(Integer studentId);

    @Query(name = "findStudentClubs", value = "SELECT s FROM StudentClub s WHERE "
    + "s.schoolStudent.schoolYear.schoolId = ?1 AND s.schoolStudent.registrationNo = ?2")
    List<StudentClub> findStudentClubs(Integer schoolId, String registrationNo);

    @Query(name = "findDuplicate", value = "SELECT s FROM StudentClub s WHERE "
    + "s.schoolStudent = ?1 AND s.club = ?2 AND s.schoolYear = ?3 AND s.studentClubId <> ?4")
    List<StudentClub> findDuplicate(SchoolStudent schoolStudent, Club club,
            SchoolYear schoolYear, Integer studentClubId);
}
