/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.employee.service.repo;

import com.okmich.schoolruns.common.entity.EmployeeAward;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface EmployeeAwardRepo extends JpaRepository<EmployeeAward, Integer> {

    @Query(name = "findEmployeeAward",
    value = "SELECT e FROM EmployeeAward e WHERE e.employee.employeeId = ?1")
    List<EmployeeAward> findEmployeeAward(Integer employeeId);
}
