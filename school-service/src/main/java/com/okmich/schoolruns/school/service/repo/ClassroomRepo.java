/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.Classroom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface ClassroomRepo extends JpaRepository<Classroom, Integer> {

    @Query(name = "findSchoolClassrooms",
    value = "SELECT c FROM Classroom c WHERE c.schoolId = ?1 ORDER BY c.code")
    List<Classroom> findSchoolClassrooms(Integer schoolId);

    @Query(name = "findDupClassrooms",
    value = "SELECT c FROM Classroom c WHERE c.schoolId = ?1 AND c.code = ?2")
    List<Classroom> findDupClassrooms(Integer schoolId, String code);
}
