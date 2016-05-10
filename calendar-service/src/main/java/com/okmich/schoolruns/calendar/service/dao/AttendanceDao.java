/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.dao;

import com.okmich.common.util.DateRange;
import com.okmich.schoolruns.calendar.service.DimensionConst;
import com.okmich.schoolruns.calendar.service.PeriodConst;
import com.okmich.schoolruns.calendar.service.data.Attendance;
import com.okmich.schoolruns.calendar.service.data.AttendanceSummary;
import com.okmich.schoolruns.calendar.service.data.EmployeeAttendanceTable;
import com.okmich.schoolruns.calendar.service.data.StudentAttendanceTable;
import com.okmich.schoolruns.common.entity.Employee;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Michael
 * @since Aug 13, 2013
 * @company Okmich Ltd.
 */
public interface AttendanceDao extends Serializable {

    /**
     *
     * @param schoolTermId
     * @param schoolClassId
     * @param date
     * @return
     */
    List<Attendance<SchoolStudent>> findStudentAttendance(
            Integer schoolTermId, Integer schoolClassId, Date date);

    /**
     *
     *
     * @param schoolTermId
     * @param schoolClassId
     * @param dateRange
     * @return StudentAttendanceTable
     */
    StudentAttendanceTable findStudentsAttendances(
            Integer schoolTermId, Integer schoolClassId, DateRange dateRange);

    /**
     *
     *
     * @param dimConst
     * @param dimConstId
     * @param periodConst
     * @param perConstId
     * @param dateRange
     * @return List<AttendanceSummary>
     */
    List<AttendanceSummary> findStudentSummaryAttendance(
            DimensionConst dimConst, Integer dimConstId, PeriodConst periodConst,
            Integer perConstId, DateRange dateRange);

    /**
     *
     *
     * @param dimConst
     * @param dimConstId
     * @param dateRange
     * @return List<AttendanceSummary>
     */
    List<AttendanceSummary> findStudentSummaryAttendance(
            DimensionConst dimConst, Integer dimConstId, DateRange dateRange);

    /**
     *
     *
     * @param schoolTermId
     * @param date
     * @return List<Attendance<Employee>>
     */
    List<Attendance<Employee>> findEmployeeAttendance(Integer schoolTermId, Date date);

    /**
     *
     *
     * @param schoolTermId
     * @param dateRange
     * @return EmployeeAttendanceTable
     */
    EmployeeAttendanceTable findEmployeesAttendances(Integer schoolTermId, DateRange dateRange);

    /**
     *
     *
     * @param dimConst
     * @param dimConstId
     * @param periodConst
     * @param perConstId
     * @param dateRange
     * @return List<AttendanceSummary>s
     */
    List<AttendanceSummary> findEmployeeSummaryAttendance(
            DimensionConst dimConst, Integer dimConstId,
            PeriodConst periodConst, Integer perConstId, DateRange dateRange);
}
