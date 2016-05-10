/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

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
public interface SchoolYearRepo extends JpaRepository<SchoolYear, Integer> {

    @Query(name = "findAllBySchool", value = "SELECT s FROM SchoolYear s "
    + "WHERE s.schoolId = ?1 ORDER BY s.closingDate DESC")
    List<SchoolYear> findAllBySchool(Integer schoolId);

    /**
     * returns a list of schoolYears where the closing date for the term is
     * greater than our new start date
     *
     * @param schoolId
     * @param startDate
     * @return List<SchoolYear>
     */
    @Query(name = "findOverlappingTerms", value = "SELECT s FROM SchoolYear s "
    + "WHERE s.schoolId = ?1 AND s.startDate BETWEEN ?2 AND ?3")
    List<SchoolYear> findOverlappingYears(Integer schoolId, Date startDate, Date closingDate);

    /**
     * return the schools current year
     *
     * @param schoolId
     * @return SchoolYear
     */
    @Query(name = "findSchoolCurrentYear", value = "SELECT s FROM SchoolYear s WHERE s.schoolId = ?1 "
    + "AND currentYear = true")
    SchoolYear findSchoolCurrentYear(Integer schoolId);
}
