/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.Stream;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface StreamRepo extends JpaRepository<Stream, Integer> {

    @Query(name = "getStreamByName",
    value = "SELECT s FROM Stream s WHERE s.description = ?1")
    List<Stream> findAnyStreamByName(String description);
}
