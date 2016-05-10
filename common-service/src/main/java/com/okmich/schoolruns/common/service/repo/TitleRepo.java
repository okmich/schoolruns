/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.Title;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface TitleRepo extends JpaRepository<Title, String> {

    @Query(name = "getTitleByName",
    value = "SELECT t FROM Title t WHERE LOWER(t.description) = ?1")
    List<Title> getTitleByName(String termName);
}
