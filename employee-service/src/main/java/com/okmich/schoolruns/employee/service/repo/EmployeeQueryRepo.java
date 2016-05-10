/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.employee.service.repo;

import com.okmich.schoolruns.common.entity.EmployeeQuery;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface EmployeeQueryRepo extends JpaRepository<EmployeeQuery, Integer> {

    @Query(name = "findEmployeeQueries",
    value = "SELECT e FROM EmployeeQuery e WHERE e.employee.employeeId = ?1")
    List<EmployeeQuery> findEmployeeQueries(Integer employeeId);
}
