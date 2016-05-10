/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.EmployeeCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface EmployeeCategoryRepo extends JpaRepository<EmployeeCategory, String> {

    @Query(name = "findActiveEmployeeCategories",
    value = "SELECT e FROM EmployeeCategory e ORDER BY e.description")
    List<EmployeeCategory> findActiveEmployeeCategories();
}
