/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.Religion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface ReligionRepo extends JpaRepository<Religion, Integer> {

    @Query(name = "getAnyReligionByName",
    value = "SELECT r FROM Religion r WHERE LOWER(r.description) = ?1")
    List<Religion> getAnyReligionByName(String religionName);
}
