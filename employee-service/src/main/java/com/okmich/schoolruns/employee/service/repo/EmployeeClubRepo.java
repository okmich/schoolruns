/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.employee.service.repo;

import com.okmich.schoolruns.common.entity.Club;
import com.okmich.schoolruns.common.entity.Employee;
import com.okmich.schoolruns.common.entity.EmployeeClub;
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
public interface EmployeeClubRepo extends JpaRepository<EmployeeClub, Integer> {

    @Query(name = "findEmployeeClubss", value = "SELECT e FROM EmployeeClub e WHERE "
    + "e.employee.employeeId = ?1")
    List<EmployeeClub> findEmployeeClubs(Integer employeeId);

    @Query(name = "findDuplicate", value = "SELECT e FROM EmployeeClub e WHERE "
    + "e.employee = ?1 AND e.club = ?2 AND e.schoolYear = ?3 AND e.employeeClubId <> ?4")
    List<EmployeeClub> findDuplicate(Employee employee, Club club,
            SchoolYear schoolYear, Integer employeeId);
}