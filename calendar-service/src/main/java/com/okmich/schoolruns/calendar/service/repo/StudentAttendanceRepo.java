/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.repo;

import com.okmich.schoolruns.common.entity.StudentAttendance;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface StudentAttendanceRepo extends JpaRepository<StudentAttendance, Integer> {

    @Query(name = "findByStudentAndDate",
    value = "SELECT s FROM StudentAttendance s WHERE s.schoolStudent.schoolStudentId = ?1 AND s.attendDate = ?2")
    StudentAttendance findByStudentAndDate(Integer employeeId, Date date);

    @Query(name = "findAttendanceByDate",
    value = "SELECT s FROM StudentAttendance s WHERE s.schoolTermId = ?1 AND s.attendDate = ?2")
    List<StudentAttendance> findAttendanceByDate(Integer schoolTermId, Date date);
}
