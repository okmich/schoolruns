/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.schoolruns.common.entity.EmployeePayroll;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael Enudi
 * @since Aug 21, 2013
 * @company Okmich Ltd.
 */
@Repository
public interface EmployeePayrollRepo extends JpaRepository<EmployeePayroll, Integer> {

    @Query(name = "findByEmployeePayrollPayroll",
    value = "SELECT e FROM EmployeePayroll e WHERE e.payroll.payrollId = ?1 ORDER BY e.employeeName")
    List<EmployeePayroll> findByPayroll(Integer payrollId);
}
