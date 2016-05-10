/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.Facility;
import com.okmich.schoolruns.common.entity.SchoolFacility;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Michael
 */
public interface SchoolFacilityRepo extends JpaRepository<SchoolFacility, Integer> {

    /**
     *
     * @param schoolId
     * @return List<SchoolFacility>
     */
    @Query(name = "findSchoolFacilityBySchool", value = "SELECT s FROM SchoolFacility s "
    + "WHERE s.school.schoolId = ?1")
    List<SchoolFacility> findSchoolFacilityBySchool(Integer schoolId);

    /**
     *
     * @param schoolId
     * @return List<Facility>
     */
    @Query(name = "findFacilityBySchool", value = "SELECT s.facility FROM SchoolFacility s "
    + "WHERE s.school.schoolId = ?1")
    List<Facility> findSchoolFacilities(Integer schoolId);

    /**
     *
     * @param schoolId
     * @param facilityId
     * @return SchoolFacility
     */
    @Query(name = "findBySchoolFacility", value = "SELECT s FROM SchoolFacility s WHERE s.school.schoolId = ?1 AND s.facility.facilityId = ?2")
    SchoolFacility findBySchoolFacility(Integer schoolId, Integer facilityId);
}
