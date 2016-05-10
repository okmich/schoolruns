/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.repo;

import com.okmich.schoolruns.common.entity.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface EventRepo extends JpaRepository<Event, Integer> {

    @Query(name = "findSchoolEvents", value = "SELECT e FROM Event e WHERE e.schoolYearId = ?1 ORDER BY e.startDate")
    List<Event> findSchoolEvents(Integer schoolYearId);

    @Query(name = "findEventForHoliday", value = "SELECT e FROM Event e WHERE e.holidayId = ?1")
    Event findEventForHoliday(Integer holidayId);
}
