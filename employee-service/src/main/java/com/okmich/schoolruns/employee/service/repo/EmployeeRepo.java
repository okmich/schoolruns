/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.employee.service.repo;

import com.okmich.schoolruns.common.entity.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    @Query(name = "findEmployeeByEmail", value = "SELECT e FROM Employee e WHERE e.email = ?1")
    List<Employee> findEmployeeByEmail(String email);

    @Query(name = "findEmployeeByName", value = "SELECT e FROM Employee e "
    + "WHERE e.surname = ?1 AND e.othernames = ?2 AND e.schoolId = ?3")
    List<Employee> findEmployeeByName(String surname, String othernames, Integer schoolId);

    @Query(name = "findByStaffNumber", value = "SELECT e FROM Employee e "
    + "WHERE e.schoolId = ?1 AND e.staffNumber = ?2")
    List<Employee> findByStaffNumber(Integer schoolId, String ids);

    @Query(name = "findActiveStaff", value = "SELECT e FROM Employee e "
    + "WHERE e.schoolId = ?1 AND e.status = 'A'")
    List<Employee> findActiveEmployee(Integer schoolId);

    @Query(name = "findByUserLogin", value = "SELECT e FROM Employee e "
    + "WHERE e.userLoginId = ?1")
    Employee findByUserLogin(Integer userLoginId);

    @Query(name = "findByUsername", value = "SELECT * FROM employee e join user_login u on "
    + "e.user_login_id = u.user_login_id WHERE u.username = ?1", nativeQuery = true)
    Employee findByUsername(String username);

    @Query(name = "findByTerm",
    value = "select * from employee where date_of_hire < "
    + "(select start_date from school_term where school_term_id = ?1)",
    nativeQuery = true)
    List<Employee> findByTerm(Integer schoolTermId);
}
