/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.IdentificationMeans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface IdentificationMeansRepo extends JpaRepository<IdentificationMeans, Integer> {
}
