package com.okmich.schoolruns.calendar.service;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.okmich.common.exception.BusinessException;
import com.okmich.common.util.DateRange;
import com.okmich.schoolruns.calendar.service.data.Attendance;
import com.okmich.schoolruns.calendar.service.data.AttendanceSummary;
import com.okmich.schoolruns.calendar.service.data.EmployeeAttendanceTable;
import com.okmich.schoolruns.calendar.service.data.StudentAttendanceTable;
import com.okmich.schoolruns.common.entity.Employee;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolTerm;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface AttendanceService extends Serializable {

    /**
     *
     *
     *
     * @param schoolTermId
     * @param date
     * @param attendance
     */
    void saveClassAttendance(SchoolTerm schoolTerm, Date date,
            List<Attendance<SchoolStudent>> studentAttendance) throws BusinessException;

    /**
     *
     *
     * @param schoolTerm
     * @param studentsAttendances
     * @throws BusinessException
     */
    void saveClassAttendance(SchoolTerm schoolTerm,
            StudentAttendanceTable studentsAttendances) throws BusinessException;

    /**
     * this method does the same thing as {@link AttendanceService#saveClassAttendance(com.okmich.schoolruns.common.entity.SchoolTerm, com.okmich.schoolruns.calendar.service.data.StudentAttendanceTable) )
     * } except that it is expected to be called from the attendance upload
     * mechanism and as a result the {@link Attendance<SchoolStudent>} in the
     * collection do not have their {@link Attendance#attendanceId} set.
     *
     * @param schoolTerm
     * @param schoolClassId
     * @param studentAttendances
     * @throws BusinessException
     */
    void saveUploadedClassAttendance(SchoolTerm schoolTerm, Integer schoolClassId,
            StudentAttendanceTable studentAttendances) throws BusinessException;

    /**
     *
     *
     * @param schoolTermId
     * @param schoolClassId
     * @param date
     * @return List<Attendance<SchoolStudent>>
     */
    List<Attendance<SchoolStudent>> findStudentAttendances(
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
     * @return List<AttendanceSummary>
     */
    List<AttendanceSummary> findStudentSummaryAttendance(
            DimensionConst dimConst, Integer dimConstId,
            PeriodConst periodConst, Integer perConstId);

    /**
     *
     *
     * @param dimConst
     * @param dimConstId
     * @param dateRange
     * @return List<AttendanceSummary>
     */
    List<AttendanceSummary> findStudentSummaryAttendance(
            DimensionConst dimConst, Integer dimConstId,
            DateRange dateRange);

    /**
     * *
     * *****************************
     * ****** EMPLOYEE *****************************
     *
     */
    /**
     *
     *
     *
     * @param schoolTermId
     * @param date
     * @param attendance
     */
    void saveEmployeeAttendance(SchoolTerm schoolTerm, Date date,
            List<Attendance<Employee>> empAttendance) throws BusinessException;

    /**
     *
     *
     * @param schoolTerm
     * @param empAttendances
     * @throws BusinessException
     */
    void saveEmployeeAttendance(SchoolTerm schoolTerm,
            EmployeeAttendanceTable empAttendances) throws BusinessException;

    /**
     * this method does the same thing as {@link AttendanceService#saveEmployeeAttendance(com.okmich.schoolruns.common.entity.SchoolTerm, com.okmich.schoolruns.calendar.service.data.EmployeeAttendanceTable)
     * } except that it is expected to be called from the attendance upload
     * mechanism and as a result the {@link Attendance<Employee>} in the
     * collection do not have their {@link Attendance#attendanceId} set.
     *
     * @param schoolTerm
     * @param empAttendances
     * @throws BusinessException
     */
    void saveUploadedEmployeeAttendance(SchoolTerm schoolTerm,
            EmployeeAttendanceTable empAttendances) throws BusinessException;

    /**
     *
     *
     * @param schoolTermId
     * @param schoolClassId
     * @param date
     * @return List<Attendance<Employee>>
     */
    List<Attendance<Employee>> findEmployeeAttendances(
            Integer schoolTermId, Date date);

    /**
     *
     *
     * @param schoolTermId
     * @param dateRange
     * @return StudentAttendanceTable
     */
    EmployeeAttendanceTable findEmployeesAttendances(
            Integer schoolTermId, DateRange dateRange);

    /**
     *
     *
     * @param dimConst
     * @param dimConstId
     * @param periodConst
     * @param perConstId
     * @return List<AttendanceSummary>
     */
    List<AttendanceSummary> findEmployeeSummaryAttendance(
            DimensionConst dimConst, Integer dimConstId,
            PeriodConst periodConst, Integer perConstId);
}
