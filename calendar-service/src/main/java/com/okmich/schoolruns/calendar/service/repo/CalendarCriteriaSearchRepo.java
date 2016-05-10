/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.repo;

import com.okmich.schoolruns.common.entity.EmployeeAttendance;
import com.okmich.schoolruns.common.entity.Holiday;
import com.okmich.schoolruns.common.entity.StudentAttendance;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface CalendarCriteriaSearchRepo extends Serializable {

    /**
     *
     * @param criteria
     * @return List<Holiday>
     */
    List<Holiday> findHolidays(HolidayQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<EmployeeAttendance>
     */
    List<EmployeeAttendance> findEmployeeAttendances(EmployeeAttendanceQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<StudentAttendance>
     */
    List<StudentAttendance> findStudentAttendances(StudentAttendanceQueryCriteria criteria);
}
