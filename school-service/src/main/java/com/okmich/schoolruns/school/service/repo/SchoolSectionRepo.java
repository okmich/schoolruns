/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.SchoolSection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface SchoolSectionRepo extends JpaRepository<SchoolSection, Integer> {

    @Query(name = "findAllBySchool", value = "SELECT s FROM SchoolSection s "
    + "WHERE s.school.schoolId = ?1 ORDER BY s.section.description DESC")
    List<SchoolSection> findAllBySchool(Integer schoolId);

    @Query(name = "findBySchoolSection", value = "SELECT s FROM SchoolSection s WHERE s.section.sectionId = ?1 AND "
    + "s.school.schoolId = ?2")
    List<SchoolSection> findBySchoolSection(Integer sectionId, 
            Integer schoolId);
}
