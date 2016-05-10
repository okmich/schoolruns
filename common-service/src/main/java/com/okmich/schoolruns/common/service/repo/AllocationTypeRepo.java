/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.AllocationType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface AllocationTypeRepo extends JpaRepository<AllocationType, Integer> {

    @Query(name = "getAnyAllocationTypeByDes",
    value = "SELECT a FROM AllocationType a WHERE LOWER(a.description) LIKE ?1")
    List<AllocationType> getAnyAllocationTypeByDes(String strAllocationType);
}
