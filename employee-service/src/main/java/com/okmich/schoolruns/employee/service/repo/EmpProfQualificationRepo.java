/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.employee.service.repo;

import com.okmich.schoolruns.common.entity.EmpProfQualification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface EmpProfQualificationRepo extends JpaRepository<EmpProfQualification, Integer> {

    @Query(name = "findEmployeeProfQualification",
    value = "SELECT e FROM EmpProfQualification e WHERE e.employee.employeeId = ?1 ORDER BY e.modifiedTime DESC")
    List<EmpProfQualification> findEmployeeProfQualification(Integer employeeId);
}
