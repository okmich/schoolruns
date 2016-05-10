/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.SportCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface SportCategoryRepo extends JpaRepository<SportCategory, Integer> {

    @Query(name = "getSportCategoryByName",
    value = "SELECT s FROM SportCategory s WHERE LOWER(s.description) = ?1")
    List<SportCategory> getSportCategoryByName(String description);
}
