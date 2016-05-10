/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.StudyMedium;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface StudyMediumRepo extends JpaRepository<StudyMedium, Integer> {

    @Query(name = "findSameStudyMedium",
            value = "SELECT s FROM StudyMedium s WHERE LOWER(s.description) = ?1")
    List<StudyMedium> findSameStudyMedium(String sMName);
}
