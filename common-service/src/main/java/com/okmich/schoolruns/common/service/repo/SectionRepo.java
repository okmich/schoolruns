/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.Section;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface SectionRepo extends JpaRepository<Section, Integer> {

    @Query(name = "getSectionByName",
    value = "SELECT s FROM Section s WHERE s.description = ?1")
    List<Section> findAnySectionByName(String description);
}
