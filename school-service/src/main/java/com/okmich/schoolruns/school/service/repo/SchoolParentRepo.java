/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.SchoolParent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface SchoolParentRepo extends JpaRepository<SchoolParent, Integer> {

    /**
     * returns a list of scalar value if a record with same parent and school id
     * is found. The method is solely for tracking duplicate school parent
     * before creation.
     *
     * @param phoneNumber
     * @param schoolId
     * @return List
     */
    @Query(name = "findExisting",
    value = "SELECT 1 FROM SchoolParent s WHERE s.parent.phoneNumber = ?1 AND s.schoolId = ?2 ")
    List findExisting(String phoneNumber, Integer schoolId);
}
