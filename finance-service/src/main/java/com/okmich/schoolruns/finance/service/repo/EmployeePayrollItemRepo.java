/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.schoolruns.common.entity.EmployeePayrollItem;
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
public interface EmployeePayrollItemRepo extends JpaRepository<EmployeePayrollItem, Integer> {

    @Query(name = "findByEmployeePayroll",
    value = "SELECT e FROM EmployeePayrollItem e WHERE e.employeePayroll.employeePayrollId = ?1")
    List<EmployeePayrollItem> findByEmployeePayroll(Integer employeePayrollId);

    @Query(name = "findEmployeePayrollItemByPayroll",
    value = "SELECT e FROM EmployeePayrollItem e WHERE e.employeePayroll.payroll.payrollId = ?1")
    List<EmployeePayrollItem> findByPayroll(Integer payrollId);
}
