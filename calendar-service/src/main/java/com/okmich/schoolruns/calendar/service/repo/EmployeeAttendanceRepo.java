/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.repo;

import com.okmich.schoolruns.common.entity.EmployeeAttendance;
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
public interface EmployeeAttendanceRepo extends JpaRepository<EmployeeAttendance, Integer> {

    @Query(name = "findByEmployeeAndDate",
    value = "SELECT e FROM EmployeeAttendance e WHERE e.employee.employeeId = ?1 AND e.attendDate = ?2")
    EmployeeAttendance findByEmployeeAndDate(Integer employeeId, Date date);

    @Query(name = "findAttendanceByDate",
    value = "SELECT e FROM EmployeeAttendance e WHERE e.schoolTermId = ?1 AND e.attendDate = ?2")
    List<EmployeeAttendance> findAttendanceByDate(Integer schoolTermId, Date date);
}
