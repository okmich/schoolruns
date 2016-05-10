/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.School;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface SchoolRepo extends JpaRepository<School, Integer> {

    @Query(name = "findSameSchoolTown", value = "SELECT s FROM School s WHERE s.name = ?1 AND s.address.city.cityId = ?2")
    List<School> findSameSchoolTown(String name, Integer cityId);

    @Query(name = "findSchoolByEmail", value = "SELECT s FROM School s WHERE s.emailAddress = ?1")
    List<School> findSchoolByEmail(String email);
}
