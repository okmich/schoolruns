/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.ProfessionalQualification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface ProfessionalQualificationRepo extends JpaRepository<ProfessionalQualification, Integer> {

    @Query(name = "getAnyEducationalQualificationByDes",
            value = "SELECT p FROM ProfessionalQualification p WHERE LOWER(p.description) = ?1")
    List<ProfessionalQualification> getAnyProfessionalQualificationByDes(
            String strProfessionalQualification);
}
