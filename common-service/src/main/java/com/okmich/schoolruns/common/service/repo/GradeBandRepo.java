/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.GradeBand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface GradeBandRepo extends JpaRepository<GradeBand, Integer> {

    /**
     * returns the GradeBand for a scoreValue
     *
     * @param scoreValue
     * @return GradeBand
     */
    @Query(name = "findGradeBand", value = "SELECT g FROM GradeBand g WHERE (g.minScore <= ?1 or g.minScore <= 0) AND "
    + "(g.maxScore > ?1 or g.maxScore >= 100)")
    GradeBand findGradeBand(float scoreValue);
}
