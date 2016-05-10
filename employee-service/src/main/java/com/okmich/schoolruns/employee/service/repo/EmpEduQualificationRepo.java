/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.employee.service.repo;

import com.okmich.schoolruns.common.entity.EmpEduQualification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface EmpEduQualificationRepo extends JpaRepository<EmpEduQualification, Integer> {

    @Query(name = "findEmployeeEduQualification",
    value = "SELECT e FROM EmpEduQualification e WHERE e.employee.employeeId = ?1 ORDER BY e.modifiedTime DESC")
    List<EmpEduQualification> findEmployeeEduQualification(Integer employeeId);
}
