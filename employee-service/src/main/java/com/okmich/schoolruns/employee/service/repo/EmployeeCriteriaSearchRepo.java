/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.employee.service.repo;

import com.okmich.schoolruns.common.entity.Employee;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface EmployeeCriteriaSearchRepo {

    public List<Employee> findEmployees(EmployeeQueryCriteria criteria);
}
