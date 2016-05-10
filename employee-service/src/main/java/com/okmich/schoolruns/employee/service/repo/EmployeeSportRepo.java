/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.employee.service.repo;

import com.okmich.schoolruns.common.entity.EmployeeSport;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface EmployeeSportRepo extends JpaRepository<EmployeeSport, Integer> {

    @Query(name = "findEmployeeSports",
    value = "SELECT e FROM EmployeeSport e WHERE e.employee.employeeId = ?1")
    List<EmployeeSport> findEmployeeSports(Integer employeeId);
}
