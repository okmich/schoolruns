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
import com.okmich.schoolruns.employee.service.repo.EmployeeRepo;
import com.okmich.schoolruns.school.service.repo.SchoolStudentRepo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 * @since Aug 13, 2013
 * @company Okmich Ltd.
 */
@Repository(value = "attendanceDao")
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class AttendanceDaoImpl implements AttendanceDao {

    /**
     * entityManager
     */
    @PersistenceContext
    private EntityManager entityManager;
    /**
     * employeeRepo
     */
    @Autowired
    private EmployeeRepo employeeRepo;
    /**
     * schoolStudentRepo
     */
    @Autowired
    private SchoolStudentRepo schoolStudentRepo;

    /**
     * AttendanceDaoImpl
     */
    public AttendanceDaoImpl() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Attendance<SchoolStudent>> findStudentAttendance(Integer schoolTermId,
            Integer schoolClassId, Date date) {
        Query query = entityManager.createNativeQuery(SqlScripts.FIND_CLASS_ATTENDANCE_FOR_DAY);
        query.setParameter(1, date);
        query.setParameter(2, schoolClassId);
        query.setParameter(3, schoolTermId);
        //execute query
        List<Object[]> resultSet = query.getResultList();
        //return adapted result
        return createStudentAttendanceList(resultSet, date);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public StudentAttendanceTable findStudentsAttendances(Integer schoolTermId,
            Integer schoolClassId, DateRange dateRange) {
        StudentAttendanceTable attendanceTable = new StudentAttendanceTable();
        List<Date> dates = dateRange.days();
        attendanceTable.setTableHeader(dates);
        List<SchoolStudent> schoolStudents = schoolStudentRepo.findByClass(schoolTermId, schoolClassId);
        attendanceTable.setSchoolStudents(schoolStudents);
        List<Attendance<SchoolStudent>> _attendanceList;
        for (Date date : dates) {
            _attendanceList = findStudentAttendance(schoolTermId, schoolClassId, date);
            attendanceTable.addAttendances(_attendanceList);
        }
        return attendanceTable;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<AttendanceSummary> findStudentSummaryAttendance(
            DimensionConst dimConst, Integer dimConstId,
            PeriodConst periodConst, Integer periodConstId,
            DateRange dateRange) {
        StringBuilder whereBuilder = applyDimensionConst(dimConst, dimConstId);


        switch (periodConst) {
            case TERM:
                whereBuilder.append(" and (sa.school_term_id is null or sa.school_term_id = ")
                        .append(periodConstId).append(")");
                break;
            case YEAR:
                whereBuilder.append(" and ss.school_year_id = ").append(periodConstId);
                break;
            case NONE:
            default:
        }
        String query = SqlScripts.STUDENT_SUMMARY_ATTENDANCE_SQL.replace(
                SqlScripts.WHERE_PLACEHOLDER, whereBuilder.toString());

        List<Object[]> resultSet = entityManager.createNativeQuery(query).getResultList();
        return createAttendanceSummary(resultSet, dateRange);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<AttendanceSummary> findStudentSummaryAttendance(
            DimensionConst dimConst, Integer dimConstId,
            DateRange dateRange) {
        //create the where clause
        StringBuilder whereBuilder = applyDimensionConst(dimConst, dimConstId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        whereBuilder.append(" and sa.attend_date BETWEEN ").append(sdf.format(dateRange.getStartDate()))
                .append(" AND ").append(sdf.format(dateRange.getEndDate()));
        //execute the summary query with the where clause builder
        return executeSummaryQuery(whereBuilder, dateRange);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Attendance<Employee>> findEmployeeAttendance(
            Integer schoolTermId, Date date) {
        Query query = entityManager.createNativeQuery(SqlScripts.FIND_EMP_ATTENDANCE_FOR_DAY);
        query.setParameter(1, date);
        query.setParameter(2, schoolTermId);
        //execute query
        List<Object[]> resultSet = query.getResultList();
        //return adapted result
        return createEmployeeAttendanceList(resultSet, date, schoolTermId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EmployeeAttendanceTable findEmployeesAttendances(
            Integer schoolTermId, DateRange dateRange) {
        EmployeeAttendanceTable attendanceTable = new EmployeeAttendanceTable();
        List<Date> dates = dateRange.days();
        attendanceTable.setTableHeader(dates);
        List<Employee> employees = employeeRepo.findByTerm(schoolTermId);
        attendanceTable.setEmployees(employees);
        List<Attendance<Employee>> _attendanceList;
        for (Date date : dates) {
            _attendanceList = findEmployeeAttendance(schoolTermId, date);
            attendanceTable.addAttendances(_attendanceList);
        }
        return attendanceTable;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<AttendanceSummary> findEmployeeSummaryAttendance(
            DimensionConst dimConst, Integer dimzConstId,
            PeriodConst periodConst, Integer perConstId,
            DateRange dateRange) {
        StringBuilder whereBuilder = new StringBuilder();

        switch (periodConst) {
            case YEAR:
                whereBuilder.append(" and ea.school_term_id in (select school_term_id "
                        + "from school_term where school_year_id = ").
                        append(perConstId).append(")");
                break;
            case TERM:
            case NONE:
            default:
                whereBuilder.append(" and (ea.school_term_id is null or ea.school_term_id = ")
                        .append(perConstId).append(")");

        }
        String query = SqlScripts.EMP_SUMMARY_ATTENDANCE_SQL.replace(
                SqlScripts.WHERE_PLACEHOLDER, whereBuilder.toString());

        List<Object[]> resultSet = entityManager.createNativeQuery(query).getResultList();
        return createAttendanceSummary(resultSet, dateRange);
    }

    /**
     *
     *
     * @param resultSet
     * @param date
     * @return List<Attendance<SchoolStudent>>
     */
    private List<Attendance<SchoolStudent>> createStudentAttendanceList(
            List<Object[]> resultSet, Date date) {
        List<Attendance<SchoolStudent>> dataList = new ArrayList<>(resultSet.size());
        Attendance<SchoolStudent> data;
        SchoolStudent ss;
        for (Object[] objs : resultSet) {
            data = new Attendance<>();
            ss = new SchoolStudent((Integer) objs[0]);
            ss.getStudent().setSurname(objs[1].toString());
            ss.getStudent().setFirstname(objs[2] == null ? "" : objs[2].toString());
            ss.getSchoolClass().setCode(objs[3].toString());
            data.setType(ss);
            data.setAttendDate(date);
            data.setPresent(objs[5] != null);
            data.setAttendanceId(objs[5] == null ? null : (Integer) objs[5]);
            data.setSchoolTermId(objs[6] == null ? null : (Integer) objs[6]);

            dataList.add(data);
        }
        return dataList;
    }

    /**
     *
     *
     * @param dimConst
     * @param dimConstId
     * @return
     */
    private StringBuilder applyDimensionConst(DimensionConst dimConst, Integer dimConstId) {
        StringBuilder whereBuilder = new StringBuilder();
        switch (dimConst) {
            case CLASS:
                whereBuilder.append(" and ss.school_class_id = ").append(dimConstId);
                break;
            case SCHOOL:
                whereBuilder.append(" and ss.school_class_id in (select school_class_id "
                        + "from school_class where school_id = ").append(dimConstId).append(")");
                break;
            case SCHOOL_STUDENT:
                whereBuilder.append(" and ss.school_student_id = ").append(dimConstId);
                break;
            case STUDENT:
                whereBuilder.append(" and ss.student_id = ").append(dimConstId);
                break;
            default:
        }
        return whereBuilder;
    }

    /**
     *
     * @param whereBuilder
     * @param dateRange
     * @return List<AttendanceSummary>
     */
    private List<AttendanceSummary> executeSummaryQuery(StringBuilder whereBuilder,
            DateRange dateRange) {
        String query = SqlScripts.STUDENT_SUMMARY_ATTENDANCE_SQL.replace(
                SqlScripts.WHERE_PLACEHOLDER, whereBuilder.toString());
        //execute query witht the entityManager
        List<Object[]> resultSet = entityManager.createNativeQuery(query).getResultList();
        //return the values from result set
        return createAttendanceSummary(resultSet, dateRange);
    }

    /**
     *
     * @param resultSet
     * @return
     */
    private List<AttendanceSummary> createAttendanceSummary(List<Object[]> resultSet,
            DateRange dateRange) {
        List<AttendanceSummary> summaries = new ArrayList<>(resultSet.size());
        AttendanceSummary summary;
        for (Object[] objs : resultSet) {
            summary = new AttendanceSummary();
            summary.setId(Integer.valueOf("" + objs[0]));
            summary.setName(objs[1] + "" + objs[2] + " (" + objs[3] + ")");
            summary.setCount(Integer.valueOf("" + objs[4]));
            summary.setTotal(dateRange.dayCount());

            summaries.add(summary);
        }

        return summaries;
    }

    /**
     *
     *
     * @param resultSet
     * @param date
     * @return List<Attendance<Employee>>
     */
    private List<Attendance<Employee>> createEmployeeAttendanceList(
            List<Object[]> resultSet, Date date, Integer schoolTermId) {
        List<Attendance<Employee>> dataList = new ArrayList<>(resultSet.size());
        Attendance<Employee> data;
        Employee emp;
        for (Object[] objs : resultSet) {
            data = new Attendance<>();
            emp = new Employee((Integer) objs[0]);
            emp.setSurname(objs[1].toString());
            emp.setOthernames(objs[2] == null ? "" : objs[2].toString());
            emp.setStaffNumber(objs[3] == null ? "" : objs[3].toString());
            data.setType(emp);
            data.setAttendDate(date);
            data.setPresent(objs[4] != null);
            data.setAttendanceId(objs[4] == null ? null : (Integer) objs[4]);
            if (schoolTermId == null) {
                data.setSchoolTermId(objs[5] == null ? null : (Integer) objs[5]);
            } else {
                data.setSchoolTermId(schoolTermId);
            }
            dataList.add(data);
        }
        return dataList;
    }
}
