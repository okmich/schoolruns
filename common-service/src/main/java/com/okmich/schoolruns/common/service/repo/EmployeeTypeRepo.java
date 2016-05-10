/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.EmployeeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface EmployeeTypeRepo extends JpaRepository<EmployeeType, String> {

    @Query(name = "findActiveEmployeeType",
    value = "SELECT e FROM EmployeeType e ORDER BY e.description")
    List<EmployeeType> findActiveEmployeeType();
}
