/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.ClubType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface ClubTypeRepo extends JpaRepository<ClubType, Integer> {

    @Query(name = "getAnyClubSocietyByName",
    value = "SELECT c FROM ClubType c WHERE LOWER(c.description) LIKE ?1")
    List<ClubType> getAnyClubTypeByName(String clubSocietyName);
}
