/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.EmployeePosition;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface EmployeePositionRepo extends JpaRepository<EmployeePosition, Integer> {

    @Query(name = "findActiveEmployeePosition",
    value = "SELECT e FROM EmployeePosition e ORDER BY e.description")
    List<EmployeePosition> findActiveEmployeePosition();
}
