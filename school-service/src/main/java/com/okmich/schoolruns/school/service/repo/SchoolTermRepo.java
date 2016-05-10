/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.SchoolTerm;
import com.okmich.schoolruns.common.entity.SchoolYear;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface SchoolTermRepo extends JpaRepository<SchoolTerm, Integer> {

    /**
     * find all the school term ever registered by the school
     *
     * @return List<SchoolTerm>
     */
    @Query(name = "findAllBySchool", value = "SELECT s FROM SchoolTerm s "
    + "WHERE s.schoolYearId = ?1 ORDER BY s.closingDate DESC")
    List<SchoolTerm> findTermsBySchoolYear(int schoolYearId);

    /**
     * returns a list of schoolTerms where the closing date for the term is
     * greater than our new start date
     *
     * @param schoolYearId
     * @param startDate
     * @return List<SchoolTerm>
     */
    @Query(name = "findOverlappingTerms", value = "SELECT s FROM SchoolTerm s "
    + "WHERE s.schoolYearId = ?1 AND s.closingDate > ?2")
    List<SchoolTerm> findOverlappingTerms(Integer schoolYearId, Date startDate);

    /**
     * return the schools current year
     *
     * @param schoolYearId
     * @return SchoolYear
     */
    @Query(name = "findSchoolCurrentTerm", value = "SELECT s FROM SchoolTerm s "
    + "WHERE s.schoolYearId = ?1 AND currentTerm = true")
    SchoolTerm findSchoolCurrentTerm(Integer schoolYearId);

    /**
     *
     *
     * @param schoolTermId
     * @return SchoolYear
     */
    @Query(name = "findSchoolYearByTerm", value = "SELECT s FROM SchoolYear s "
    + "WHERE s.schoolYearId = (SELECT o.schoolYearId FROM SchoolTerm o WHERE o.schoolTermId = ?1)")
    SchoolYear findSchoolYearByTerm(Integer schoolTermId);
}
