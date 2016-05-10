/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.City;
import com.okmich.schoolruns.common.entity.State;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface CityRepo extends JpaRepository<City, Integer> {

    @Query(name = "searchCity",
    value = "SELECT c FROM City c WHERE LOWER(c.name) LIKE ?1")
    List<City> searchCity(String searchDescription);

    @Query(name = "findCityByState",
    value = "SELECT c FROM City c WHERE c.state.stateCode =  ?1")
    List<City> findCityByState(String stateCode);

    @Query(name = "findCityByStates",
    value = "SELECT c FROM City c WHERE c.state IN ?1")
    List<City> findCityByStates(List<State> stateList);
}
