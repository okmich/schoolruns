/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.repo;

import com.okmich.common.entity.repo.CriteriaSearchWorker;
import com.okmich.schoolruns.common.entity.EmployeeAttendance;
import com.okmich.schoolruns.common.entity.Holiday;
import com.okmich.schoolruns.common.entity.StudentAttendance;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Repository("calendarCriteriaSearchRepo")
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class CalendarCriteriaSearchRepoImpl implements CalendarCriteriaSearchRepo {

    /**
     * holidayRepoWorker - holiday Criteria Search Repo
     */
    private CriteriaSearchWorker<Holiday, HolidayQueryCriteria> holidayRepoWorker;
    /**
     * employeeAttendanceRepoWorker - employee attendance Criteria Search Repo
     */
    private CriteriaSearchWorker<EmployeeAttendance, EmployeeAttendanceQueryCriteria> employeeAttendanceRepoWorker;
    /**
     * studentAttendanceRepoWorker - student attendance Criteria Search Repo
     */
    private CriteriaSearchWorker<StudentAttendance, StudentAttendanceQueryCriteria> studentAttendanceRepoWorker;
    /**
     * entityManager
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     *
     */
    public CalendarCriteriaSearchRepoImpl() {
        employeeAttendanceRepoWorker = new CriteriaSearchWorker<>();
        studentAttendanceRepoWorker = new CriteriaSearchWorker<>();
        holidayRepoWorker = new CriteriaSearchWorker<>();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Holiday> findHolidays(HolidayQueryCriteria criteria) {
        return holidayRepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<EmployeeAttendance> findEmployeeAttendances(EmployeeAttendanceQueryCriteria criteria) {
        return employeeAttendanceRepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<StudentAttendance> findStudentAttendances(StudentAttendanceQueryCriteria criteria) {
        return studentAttendanceRepoWorker.findByCriteria(entityManager, criteria);
    }
}
