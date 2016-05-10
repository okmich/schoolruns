/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.CtrlParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface CtrlParameterRepo extends JpaRepository<CtrlParameter, String> {

    @Query(name = "findCtrlParameterValue",
    value = "SELECT c.value FROM CtrlParameter c WHERE c.code = ?1 AND c.status = 'A'")
    String findCtrlParameterValue(String code);
}
