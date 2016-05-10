/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.repo;

import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.common.entity.TimetableEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface TimetableEntryRepo extends JpaRepository<TimetableEntry, Integer> {

    /**
     * returns a list of {@link TimetableEntry } for a school class id passed in
     * as parameter
     *
     * @param schoolClassId
     * @return List<TimetableEntry>
     */
    @Query(name = "findTimetableEntriesForClass",
    value = "SELECT t FROM TimetableEntry t WHERE t.schoolClass.schoolClassId = ?1 "
    + "ORDER BY t.weekday, t.classPeriod.periodNumber")
    List<TimetableEntry> findTimetableEntriesForClass(Integer schoolClassId);

    /**
     * returns a list of {@link TimetableEntry } for a class teacher with id
     * passed in as parameter {@code employeeId}
     *
     * @param employeeId
     * @return List<TimetableEntry>
     */
    @Query(name = "findTimetableEntriesForTeacher",
    value = "SELECT t FROM TimetableEntry t WHERE t.employee.employeeId = ?1 "
    + "ORDER BY t.weekday, t.classPeriod.periodNumber")
    List<TimetableEntry> findTimetableEntriesForTeacher(Integer employeeId);

    /**
     * returns a list of {@link TimetableEntry } for a list of
     * {@link SchoolSubject} passed in as parameter {@code schoolSubjects}
     *
     * @param List<SchoolSubject>
     * @return List<TimetableEntry>
     */
    @Query(name = "findTimetableEntriesForSubjects",
    value = "SELECT t FROM TimetableEntry t WHERE t.schoolSubject IN ?1 "
    + "ORDER BY t.weekday, t.classPeriod.periodNumber")
    List<TimetableEntry> findTimetableEntriesForSubjects(List<SchoolSubject> schoolSubjects);

    /**
     *
     *
     * @param weekdayCode
     * @param schoolSubjectId
     * @param classPeriodId
     * @param schoolClassId
     * @return List<TimetableEntry>
     */
    @Query(name = "findDuplicateEntries", value = "SELECT t FROM TimetableEntry t "
    + "WHERE t.weekday.code = ?1 AND t.schoolSubject.schoolSubjectId = ?2 AND "
    + "t.classPeriod.classPeriodId = ?3 AND t.schoolClass.schoolClassId = ?4")
    List<TimetableEntry> findDuplicateEntries(String weekdayCode,
            Integer schoolSubjectId, Integer classPeriodId, Integer schoolClassId);

    /**
     *
     *
     * @param weekdayCode
     * @param classPeriodId
     * @param employeeId
     * @return List<TimetableEntry>
     */
    @Query(name = "findDuplicateEntries2", value = "SELECT t FROM TimetableEntry t "
    + "WHERE t.weekday.code = ?1 AND t.classPeriod.classPeriodId = ?2 "
    + "AND t.employee.employeeId = ?3")
    List<TimetableEntry> findDuplicateEntries(String weekdayCode,
            Integer classPeriodId, Integer employeeId);
}
