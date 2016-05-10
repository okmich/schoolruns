/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.schoolruns.common.entity.PayStructure;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael Enudi
 * @since Aug 20, 2013
 * @company Okmich Ltd.
 */
@Repository
public interface PayStructureRepo extends JpaRepository<PayStructure, Integer> {

    @Query(name = "findPayStructureBySchool",
    value = "SELECT p FROM PayStructure p WHERE p.schoolId = ?1 ORDER BY p.title")
    List<PayStructure> findPayStructureBySchool(Integer schoolId);
}
