/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.Country;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface CountryRepo extends JpaRepository<Country, String> {

    @Query(name = "getAnyCountryByName",
    value = "SELECT c FROM Country c WHERE LOWER(c.description) LIKE ?1")
    public List<Country> getAnyCountryByName(String searchDescription);
}
