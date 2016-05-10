package com.okmich.schoolruns.calendar.service;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.okmich.schoolruns.common.entity.ClassPeriod;
import com.okmich.schoolruns.common.entity.Event;
import com.okmich.schoolruns.common.entity.Holiday;
import com.okmich.schoolruns.common.entity.SchoolPref;
import com.okmich.schoolruns.common.entity.SchoolTerm;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.common.entity.TimetableEntry;
import com.okmich.common.exception.BusinessException;
import com.okmich.schoolruns.calendar.service.data.SchoolCalendarData;
import com.okmich.schoolruns.calendar.service.data.TtEntryTable;
import com.okmich.schoolruns.calendar.service.repo.HolidayQueryCriteria;
import com.okmich.schoolruns.common.entity.Weekday;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface SchoolCalendarService extends Serializable {

    /**
     *
     *
     * @param schoolCalendarData
     * @return SchoolCalendarData
     * @throws BusinessException
     */
    SchoolCalendarData createKickOffYearTerm(SchoolCalendarData schoolCalendarData)
            throws BusinessException;

    /**
     * save a school Year which marks a school academic year
     *
     * @param schoolYear
     * @return SchoolYear
     * @throws BusinessException
     */
    SchoolYear saveSchoolYear(SchoolYear schoolYear) throws BusinessException;

    /**
     * find a school year object by its Id
     *
     * @param schoolYearId
     * @return SchoolYear
     */
    SchoolYear findSchoolYear(int schoolYearId);

    /**
     * find all academic year record of a school by the school Id. The listing
     * is order by descending order with the most current one at the top
     *
     * @param schoolId
     * @return List<SchoolYear>
     */
    List<SchoolYear> findSchoolYears(int schoolId);

    /**
     * find the current academic year record of a school. The current most is
     * usually is one at position 0 if
     * {@link SchoolCalendarService#findSchoolYears(int)} is called.
     *
     * @param schoolId
     * @return SchoolYear
     * @throws BusinessException
     */
    SchoolYear findSchoolCurrentYear(int schoolId) throws BusinessException;

    /**
     * persists a schoolTerm record for a particular school
     *
     * @param schoolTerm
     * @return SchoolTerm
     * @throws BusinessException
     */
    SchoolTerm saveSchoolTerm(SchoolTerm schoolTerm) throws BusinessException;

    /**
     * find a schoolTerm by it ID
     *
     * @param schoolTermId
     * @return SchoolTerm
     */
    SchoolTerm findSchoolTerm(int schoolTermId);

    /**
     * find all SchoolTerm records belonging to a school
     *
     * @param schoolId
     * @param schoolYearlId
     * @return List<SchoolTerm>
     */
    List<SchoolTerm> findSchoolTerms(int schoolId, int schoolYearId);

    /**
     * find the current academic term record of a school. The current most is
     * usually is one at position 0 if {@link SchoolCalendarService#findSchoolTerms(int)
     * } is called.
     *
     * @param schoolId
     * @return SchoolYear
     * @throws BusinessException
     */
    SchoolTerm findSchoolCurrentTerm(int schoolId) throws BusinessException;

    /**
     * a call to this method wraps calls to {@link SchoolCalendarService#findSchoolCurrentYear(int)
     * } and {@link SchoolCalendarService#findSchoolCurrentTerm(int) } in one
     * call. The object returned contains information for both calls
     *
     * @param schoolId
     * @return SchoolCalendarData - a {@link SchoolCalendarData}
     * @throws BusinessException - if error occurs
     */
    SchoolCalendarData findSchoolCurrentPeriod(int schoolId) throws BusinessException;

    /**
     * an entry on a schools class timetable are marked by {@link ClassPeriod}.
     * This methods saves the details of a ClassPeriod for a school's class in
     * the database
     *
     * @param classPeriod
     * @return ClassPeriod
     * @throws BusinessException
     */
    ClassPeriod saveClassPeriod(ClassPeriod classPeriod) throws BusinessException;

    /**
     * find a ClassPeriod record which classPeriodId is given as parameter.
     * Return null if object not found
     *
     * @param classPeriodId
     * @return ClassPeriod - the ClassPeriod whose Id was provided or null if
     * not found
     */
    ClassPeriod findClassPeriod(Integer classPeriodId);

    /**
     * find the whole ClassPeriod set for a school section. The list is expected
     * to be finite as class period are usually chunk of time between open and
     * closing time for daily school activities
     *
     * @param schoolSectionId
     * @return List<ClassPeriod>
     */
    List<ClassPeriod> findClassPeriods(Integer schoolSectionId);

    /**
     * create a new event record in the system
     *
     * @param event
     * @return Event
     * @throws BusinessException
     */
    Event createEvent(Event event) throws BusinessException;

    /**
     * return an event record whose primary keys is given as {@code eventId}
     *
     * @param eventId
     * @return Event
     * @throws BusinessException
     */
    Event findEvent(Integer eventId) throws BusinessException;

    /**
     * returns all event for a given school's year value
     *
     * @param schoolYearId
     * @return List<Event>
     */
    List<Event> findSchoolEvents(Integer schoolYearId);

    /**
     * save the timetableEntry to the database for a particular weekday, class,
     * subject, class period and optional teacher and/or classroom. Given the
     * timetable settings on {@link SchoolPref#ttableContainClassroom} and
     * {@link SchoolPref#ttableContainTeacher} , validation are done to check
     * the optionality of the affected fields. When
     * {@link SchoolPref#ttableResolveSubTeacher} is true, a check is done to
     * ensure that the class teacher must have been assigned the subject prior
     * to Timetable settings
     *
     * @param timetableEntry
     * @return TimetableEntry
     * @throws BusinessException - if error occurs during the operation
     */
    TimetableEntry createTimetableEntry(TimetableEntry timetableEntry) throws BusinessException;

    /**
     * find the {@code TimetableEntry} record with the timetableEntryId. A null
     * object is return if no record is found
     *
     * @param timetableEntryId
     * @return TimetableEntry - a valid record for {@code timetableEntryId} or
     * null if no object is found
     */
    TimetableEntry findTimetableEntry(Integer timetableEntryId);

    /**
     * return a table of {@code TimetableEntry} for the given class
     * {@code schoolClassId}. The table is defined as values of
     * {@code TimetableEnty} of a list of {@link Weekday} against another list
     * of {@link ClassPeriod}
     *
     * @param schoolClassId
     * @param schoolPref
     * @return TtEntryTable
     */
    TtEntryTable findClassTimetable(Integer schoolClassId, SchoolPref schoolPref);

    /**
     * return a list of {@code TimetableEntry} for the given employee
     * {@code employeeId}
     *
     * @param employeeId
     * @return List<TimetableEntry>
     */
    List<TimetableEntry> findTeacherTimetable(Integer employeeId);

    /**
     * return a list of {@code TimetableEntry} for the given school student
     * {@code schoolStudentId}. This method is particular needed for students
     * and schools which allows student to make choices for major and elective
     * subjects which means that different students in a class could have
     * different timetable distributions
     *
     * @param schoolStudentId
     * @return TtEntryTable
     */
    TtEntryTable findStudentTimetable(Integer schoolStudentId);

    /**
     * update the {@code TimetableEntry} record in the database and also given
     * respect to existing database rules
     *
     * @param timetableEntry
     * @throws BusinessException - if error occurs
     */
    //void saveTimetableEntry(TimetableEntry timetableEntry) throws BusinessException;
    /**
     * remove the timetableEntry from the database
     *
     * @param timetableEntry
     * @throws BusinessException
     */
    void deleteTimetableEntry(TimetableEntry timetableEntry) throws BusinessException;

    /**
     *
     *
     * @param holiday
     * @return
     * @throws BusinessException
     */
    Holiday createHoliday(Holiday holiday) throws BusinessException;

    /**
     *
     * @param holidayId
     * @return
     * @throws BusinessException
     */
    Holiday findHoliday(Integer holidayId) throws BusinessException;

    /**
     *
     * @param HolidayQueryCriteria
     * @return
     * @throws BusinessException
     */
    List<Holiday> findHolidays(HolidayQueryCriteria criteria) throws BusinessException;

    /**
     *
     *
     * @param holiday
     * @return
     * @throws BusinessException
     */
    Holiday saveHoliday(Holiday holiday) throws BusinessException;
}
