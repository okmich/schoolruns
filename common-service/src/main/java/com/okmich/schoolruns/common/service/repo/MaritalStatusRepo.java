/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;


import com.okmich.schoolruns.common.entity.MaritalStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface MaritalStatusRepo extends JpaRepository<MaritalStatus, Integer> {

    @Query(name = "getAnyMaritalStatusByName",
    value = "SELECT m FROM MaritalStatus m WHERE LOWER(m.description) = ?1")
    List<MaritalStatus> getAnyMaritalStatusByName(String maritalStatusName);
}
