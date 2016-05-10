/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.EducationalQualification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface EducationalQualificationRepo extends JpaRepository<EducationalQualification, String> {

    @Query(name = "getAnyEducationalQualificationByDes",
            value = "SELECT e FROM EducationalQualification e WHERE LOWER(e.description) LIKE ?1")
    List<EducationalQualification> getAnyEducationalQualificationByDes(String strEducationalQualification);
}
