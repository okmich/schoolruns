/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.Term;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface TermRepo extends JpaRepository<Term, Integer> {

    @Query(name = "getTermByName",
            value = "SELECT t FROM Term t WHERE LOWER(t.description) = ?1")
    List<Term> getTermByName(String termName);
}
