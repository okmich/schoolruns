/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.Club;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface ClubRepo extends JpaRepository<Club, Integer> {

    @Query(name = "findSchoolClubByType", value = "SELECT c FROM Club c WHERE "
    + "c.school.schoolId = ?1 AND  c.clubType.clubTypeId = ?2")
    List<Club> findSchoolClubByType(Integer schoolId, Integer clubTypeId);

    @Query(name = "findSchoolClubs", value = "SELECT c FROM Club c WHERE "
    + "c.school.schoolId = ?1")
    List<Club> findSchoolClubs(Integer schoolId);
}
