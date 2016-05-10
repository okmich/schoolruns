package com.okmich.schoolruns.calendar.service;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.okmich.common.exception.BusinessException;
import com.okmich.common.util.DateRange;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.calendar.service.dao.AttendanceDao;
import com.okmich.schoolruns.calendar.service.data.Attendance;
import com.okmich.schoolruns.calendar.service.data.AttendanceSummary;
import com.okmich.schoolruns.calendar.service.data.EmployeeAttendanceTable;
import com.okmich.schoolruns.calendar.service.data.StudentAttendanceTable;
import com.okmich.schoolruns.calendar.service.repo.CalendarCriteriaSearchRepo;
import com.okmich.schoolruns.calendar.service.repo.EmployeeAttendanceRepo;
import com.okmich.schoolruns.calendar.service.repo.StudentAttendanceRepo;
import com.okmich.schoolruns.common.entity.Employee;
import com.okmich.schoolruns.common.entity.EmployeeAttendance;
import com.okmich.schoolruns.common.entity.SchoolPref;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolTerm;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.common.entity.StudentAttendance;
import com.okmich.schoolruns.school.service.repo.SchoolPrefRepo;
import com.okmich.schoolruns.school.service.repo.SchoolTermRepo;
import com.okmich.schoolruns.school.service.repo.SchoolYearRepo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Service("attendanceService")
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private static final Logger LOG = Logger.getLogger(AttendanceServiceImpl.class.getName());
    /**
     * attendanceDao
     */
    @Autowired
    private AttendanceDao attendanceDao;
    /**
     * calendarCriteriaSearchRepo
     */
    @Autowired
    private CalendarCriteriaSearchRepo calendarCriteriaSearchRepo;
    /**
     * employeeAttendanceRepo
     */
    @Autowired
    private EmployeeAttendanceRepo employeeAttendanceRepo;
    /**
     * studentAttendanceRepo
     */
    @Autowired
    private StudentAttendanceRepo studentAttendanceRepo;
    /**
     * schoolTermRepo
     */
    @Autowired
    private SchoolTermRepo schoolTermRepo;
    /**
     * schoolYearRepo
     */
    @Autowired
    private SchoolYearRepo schoolYearRepo;
    /**
     * schoolPrefRepo
     */
    @Autowired
    private SchoolPrefRepo schoolPrefRepo;

    /**
     * default constructor
     */
    public AttendanceServiceImpl() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void saveClassAttendance(SchoolTerm schoolTerm, Date date,
            List<Attendance<SchoolStudent>> studentAttendances) throws BusinessException {
        //initialise the entity list
        List<StudentAttendance> attendances = new ArrayList<>(studentAttendances.size());
        List<StudentAttendance> attendancesToDelete = new ArrayList<>();
        for (Attendance<SchoolStudent> studAttend : studentAttendances) {
            if (studAttend.isPresent()) {
                attendances.add(getStudentAttendance(studAttend, date, schoolTerm));
            } else if (studAttend.getAttendanceId() != null && !studAttend.isPresent()) {
                //this mean that the record was previous marked as present but has been
                //corrected
                attendancesToDelete.add(getStudentAttendance(studAttend, date, schoolTerm));
            }
        }
        try {
            //save the list
            studentAttendanceRepo.save(attendances);
            //delete the supposed corrections
            studentAttendanceRepo.deleteInBatch(attendancesToDelete);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void saveClassAttendance(SchoolTerm schoolTerm,
            StudentAttendanceTable studentsAttendances) throws BusinessException {
        //we will want to get the attendance object column-wise
        Collection<Date> dates = studentsAttendances.getDates();
        List<Attendance<SchoolStudent>> schoolStudentAttends;
        //loop throught the table column-wise and add to a list
        for (Date date : dates) {
            schoolStudentAttends = (List<Attendance<SchoolStudent>>) studentsAttendances.getColumn(date);
            saveClassAttendance(schoolTerm, date, schoolStudentAttends);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void saveUploadedClassAttendance(SchoolTerm schoolTerm, Integer schoolClassId,
            StudentAttendanceTable studentAttendances) throws BusinessException {
        //we will iterate through this collection column wise
        List<SchoolStudent> schoolStudents = studentAttendances.getSchoolStudents();
        List<Date> dates = studentAttendances.getDates();
        //the list of attendance to save
        List<StudentAttendance> studentAttends;
        Attendance<SchoolStudent> studAttend;
        StudentAttendance studentAttendance;
        for (Date date : dates) {
            try {
                //delete all existing attendance for that day
                deleteStudentAttendance(schoolTerm, date);
                studentAttends = new ArrayList<>();
                for (SchoolStudent stud : schoolStudents) {
                    studAttend = studentAttendances.getAttendance(stud, date);
                    if (studAttend.isPresent()) {
                        studentAttendance = studentAttendanceRepo.findByStudentAndDate(
                                stud.getSchoolStudentId(), date);
                        if (studentAttendance == null) {
                            studentAttendance = new StudentAttendance();
                            studentAttendance.setAttendDate(date);
                            studentAttendance.setSchoolStudent(stud);
                            studentAttendance.setModifiedBy(schoolTerm.getModifiedBy());
                            studentAttendance.setModifiedTime(new Date());
                            studentAttendance.setSchoolTermId(schoolTerm.getSchoolTermId());
                            studentAttendance.setStatus(CommonConstants.STATUS_ACTIVE);
                        }
                        studentAttends.add(studentAttendance);
                    }
                }
                studentAttendanceRepo.save(studentAttends);
            } catch (Exception ex) {
                Logger.getLogger(AttendanceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<Attendance<SchoolStudent>> findStudentAttendances(
            Integer schoolTermId, Integer schoolClassId, Date date) {

        return attendanceDao.findStudentAttendance(
                schoolTermId, schoolClassId, date);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public StudentAttendanceTable findStudentsAttendances(
            Integer schoolTermId, Integer schoolClassId, DateRange dateRange) {

        return attendanceDao.findStudentsAttendances(
                schoolTermId, schoolClassId, dateRange);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<AttendanceSummary> findStudentSummaryAttendance(
            DimensionConst dimConst, Integer dimConstId,
            PeriodConst periodConst, Integer perConstId) {
        DateRange dateRange = null;
        SchoolTerm schoolTerm = null;
        SchoolPref schoolPref = null;
        switch (periodConst) {
            case TERM:
                schoolTerm = schoolTermRepo.findOne(perConstId);
                dateRange = new DateRange(
                        schoolTerm.getStartDate(), schoolTerm.getClosingDate());
                break;
            case YEAR:
                SchoolYear schoolYear = schoolYearRepo.findOne(perConstId);
                schoolPref = schoolPrefRepo.findOne(schoolYear.getSchoolId());
                dateRange = new DateRange(
                        schoolYear.getStartDate(),
                        schoolYear.getClosingDate());
                break;
            case NONE:
            default:
                dateRange = new DateRange(new Date(0), new Date());
        }
        if (schoolPref != null) {
            dateRange.setIterationPolicy(DateRange.IterationPolicy.valueOf(schoolPref.getAvailDays()));
        } else {
            dateRange.setIterationPolicy(DateRange.IterationPolicy.WEEKDAYS);
        }
        //run the summary statement
        return attendanceDao.findStudentSummaryAttendance(
                dimConst, dimConstId,
                periodConst, perConstId, dateRange);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<AttendanceSummary> findStudentSummaryAttendance(
            DimensionConst dimConst, Integer dimConstId, DateRange dateRange) {
        return attendanceDao.findStudentSummaryAttendance(dimConst, dimConstId,
                dateRange);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void saveEmployeeAttendance(SchoolTerm schoolTerm, Date date,
            List<Attendance<Employee>> empAttendances) throws BusinessException {
        //initialise the entity list
        List<EmployeeAttendance> attendances = new ArrayList<>(empAttendances.size());
        List<EmployeeAttendance> attendancesToDelete = new ArrayList<>();
        for (Attendance<Employee> empAttend : empAttendances) {
            if (empAttend.isPresent()) {
                attendances.add(getEmployeeAttendance(empAttend, date, schoolTerm));
            } else if (empAttend.getAttendanceId() != null && !empAttend.isPresent()) {
                //this mean that the record was previous marked as present but has been
                //corrected
                attendancesToDelete.add(getEmployeeAttendance(empAttend, date, schoolTerm));
            }
        }
        try {
            //save the list
            employeeAttendanceRepo.save(attendances);
            //delete the supposed corrections
            employeeAttendanceRepo.deleteInBatch(attendancesToDelete);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void saveEmployeeAttendance(SchoolTerm schoolTerm,
            EmployeeAttendanceTable empAttendances) throws BusinessException {
        //we will want to get the attendance object column-wise
        Collection<Date> dates = empAttendances.getColumnIndexes();
        List<Attendance<Employee>> employeeAttends;
        //loop throught the table column-wise and add to a list
        for (Date date : dates) {
            employeeAttends = (List<Attendance<Employee>>) empAttendances.getColumn(date);
            saveEmployeeAttendance(schoolTerm, date, employeeAttends);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void saveUploadedEmployeeAttendance(SchoolTerm schoolTerm,
            EmployeeAttendanceTable empAttendances) throws BusinessException {
        //we will iterate through this collection column wise
        List<Employee> employees = empAttendances.getEmployees();
        List<Date> dates = empAttendances.getDates();
        //the list of attendance to save
        List<EmployeeAttendance> empAttends;
        Attendance<Employee> empAttend;
        EmployeeAttendance employeeAttendance;
        for (Date date : dates) {
            try {
                //delete all existing attendance for that day
                deleteEmployeeAttendance(schoolTerm, date);
                empAttends = new ArrayList<>();
                for (Employee emp : employees) {
                    empAttend = empAttendances.getAttendance(emp, date);
                    if (empAttend.isPresent()) {
                        employeeAttendance = employeeAttendanceRepo.findByEmployeeAndDate(
                                emp.getEmployeeId(), date);
                        if (employeeAttendance == null) {
                            employeeAttendance = new EmployeeAttendance();
                            employeeAttendance.setAttendDate(date);
                            employeeAttendance.setEmployee(emp);
                            employeeAttendance.setModifiedBy(schoolTerm.getModifiedBy());
                            employeeAttendance.setModifiedTime(new Date());
                            employeeAttendance.setSchoolTermId(schoolTerm.getSchoolTermId());
                            employeeAttendance.setStatus(CommonConstants.STATUS_ACTIVE);
                        }
                        empAttends.add(employeeAttendance);
                    }
                }
                employeeAttendanceRepo.save(empAttends);
            } catch (Exception ex) {
                Logger.getLogger(AttendanceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<Attendance<Employee>> findEmployeeAttendances(
            Integer schoolTermId, Date date) {

        return attendanceDao.findEmployeeAttendance(
                schoolTermId, date);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public EmployeeAttendanceTable findEmployeesAttendances(
            Integer schoolTermId, DateRange dateRange) {

        return attendanceDao.findEmployeesAttendances(
                schoolTermId, dateRange);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<AttendanceSummary> findEmployeeSummaryAttendance(
            DimensionConst dimConst, Integer dimConstId,
            PeriodConst periodConst, Integer perConstId) {
        DateRange dateRange = null;
        SchoolPref schoolPref = null;
        switch (periodConst) {
            case TERM:
                SchoolTerm schoolTerm = schoolTermRepo.findOne(perConstId);
                schoolPref = schoolPrefRepo.findSchoolPrefBySchoolYear(schoolTerm.getSchoolYearId());
                dateRange = new DateRange(
                        schoolTerm.getStartDate(), schoolTerm.getClosingDate());
                break;
            case YEAR:
                SchoolYear schoolYear = schoolYearRepo.findOne(perConstId);
                schoolPref = schoolPrefRepo.findOne(schoolYear.getSchoolId());
                dateRange = new DateRange(
                        schoolYear.getStartDate(),
                        schoolYear.getClosingDate());
                break;
            case NONE:
            default:
                dateRange = new DateRange(new Date(0), new Date());
        }

        if (schoolPref != null) {
            dateRange.setIterationPolicy(DateRange.IterationPolicy.valueOf(schoolPref.getAvailDays()));
        } else {
            dateRange.setIterationPolicy(DateRange.IterationPolicy.WEEKDAYS);
        }

        //run the summary statement
        return attendanceDao.findEmployeeSummaryAttendance(
                dimConst, dimConstId,
                periodConst, perConstId, dateRange);
    }

    /**
     *
     *
     * @param studAttend
     * @param date
     * @param schoolTerm
     * @return StudentAttendance
     */
    private StudentAttendance getStudentAttendance(
            Attendance<SchoolStudent> studAttend, Date date, SchoolTerm schoolTerm) {
        StudentAttendance sa;

        if (studAttend.getAttendanceId() != null) { //new entity
            sa = studentAttendanceRepo.findOne(studAttend.getAttendanceId());
        } else {
            sa = new StudentAttendance();
            sa.setAttendDate(date);
            sa.setSchoolStudent(studAttend.getType());
            sa.setSchoolTermId(schoolTerm.getSchoolTermId());
            sa.setStatus(CommonConstants.STATUS_ACTIVE);
            sa.setStudentAttendanceId(studAttend.getAttendanceId());
        }
        sa.setModifiedBy(schoolTerm.getModifiedBy());
        sa.setModifiedTime(new Date());

        return sa;
    }

    /**
     *
     *
     * @param empAttend
     * @param date
     * @param schoolTerm
     * @return EmployeeAttendance
     */
    private EmployeeAttendance getEmployeeAttendance(
            Attendance<Employee> empAttend, Date date, SchoolTerm schoolTerm) {
        EmployeeAttendance ea;

        if (empAttend.getAttendanceId() != null) { //new entity
            ea = employeeAttendanceRepo.findOne(empAttend.getAttendanceId());
        } else {
            ea = new EmployeeAttendance();

            ea.setAttendDate(date);
            ea.setEmployee(empAttend.getType());
            ea.setEmployeeAttendanceId(empAttend.getAttendanceId());
            ea.setSchoolTermId(schoolTerm.getSchoolTermId());
            ea.setStatus(CommonConstants.STATUS_ACTIVE);
        }
        ea.setModifiedBy(schoolTerm.getModifiedBy());
        ea.setModifiedTime(new Date());

        return ea;
    }

    /**
     *
     *
     * @param sa
     * @return
     */
    private Attendance<SchoolStudent> getAttendance(StudentAttendance sa) {
        Attendance<SchoolStudent> attendance = new Attendance<>();

        attendance.setAttendDate(sa.getAttendDate());
        attendance.setAttendanceId(sa.getStudentAttendanceId());
        attendance.setModifiedBy(sa.getModifiedBy());
        attendance.setModifiedTime(sa.getModifiedTime());
        attendance.setPresent(true);
        attendance.setSchoolTermId(sa.getSchoolTermId());
        attendance.setStatus(sa.getStatus());
        attendance.setType(sa.getSchoolStudent());

        return attendance;
    }

    /**
     *
     * @param schoolTerm
     * @param date
     * @throws Exception
     */
    private void deleteEmployeeAttendance(SchoolTerm schoolTerm, Date date)
            throws Exception {

        List<EmployeeAttendance> employeeAttendances =
                employeeAttendanceRepo.findAttendanceByDate(
                schoolTerm.getSchoolTermId(), date);
        employeeAttendanceRepo.delete(employeeAttendances);
    }

    /**
     *
     * @param schoolTerm
     * @param date
     */
    private void deleteStudentAttendance(SchoolTerm schoolTerm, Date date) {

        List<StudentAttendance> studentAttendances =
                studentAttendanceRepo.findAttendanceByDate(
                schoolTerm.getSchoolTermId(), date);
        studentAttendanceRepo.delete(studentAttendances);
    }
}