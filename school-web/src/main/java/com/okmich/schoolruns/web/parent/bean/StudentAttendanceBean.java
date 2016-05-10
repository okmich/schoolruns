/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.parent.bean;

import com.okmich.schoolruns.calendar.service.AttendanceService;
import com.okmich.schoolruns.common.service.JdbcService;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ResultSetDataModel;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class StudentAttendanceBean extends _BaseBean {

    @ManagedProperty("#{attendanceService}")
    private AttendanceService attendanceService;
    @ManagedProperty("#{jdbcService}")
    private JdbcService jdbcService;
    @ManagedProperty("#{parentSessionBean}")
    private ParentSessionBean parentSessionBean;
    @ManagedProperty("#{studentBean}")
    private StudentBean studentBean;
    /**
     * STD_ATTENDANCE_QUERY
     */
    private static final String STD_ATTENDANCE_QUERY = "SELECT v.school_name, "
            + "v.academic_year, v.school_year_id, v.school_term_id, v.school_term, "
            + "v.term_start, v.term_close, v.attendance FROM v_student_attendance_summary v "
            + "where v.student_id = ${STUDENT_ID}";
    /**
     * STD_ATTENDANCE_BY_YEAR_QUERY
     */
    private static final String STD_ATTENDANCE_BY_YEAR_QUERY = "SELECT v.school_name, "
            + "v.academic_year, v.school_year_id, v.year_start, v.year_close, "
            + "count(v.attendance) attendance FROM v_student_attendance_summary v "
            + "where v.student_id = ${STUDENT_ID} "
            + "group by v.school_name, v.academic_year, v.school_year_id, "
            + "v.school_term_id, v.school_term";
    /**
     * STUDENT_TAG
     */
    private static final String STUDENT_TAG = "${STUDENT_ID}";

    /**
     * Creates a new instance of StudentAttendanceBean
     */
    public StudentAttendanceBean() {
    }

    /**
     * @param attendanceService the attendanceService to set
     */
    public void setAttendanceService(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * @param jdbcService the jdbcService to set
     */
    public void setJdbcService(JdbcService jdbcService) {
        this.jdbcService = jdbcService;
    }

    /**
     * @param parentSessionBean the parentSessionBean to set
     */
    public void setParentSessionBean(ParentSessionBean parentSessionBean) {
        this.parentSessionBean = parentSessionBean;
    }

    /**
     * @param studentBean the studentBean to set
     */
    public void setStudentBean(StudentBean studentBean) {
        this.studentBean = studentBean;
    }

    /**
     *
     * @return
     */
    public DataModel getYearTermModel() {
        CachedRowSet cachedRowSet;
        String sqlQuery = STD_ATTENDANCE_QUERY.replace(STUDENT_TAG,
                "" + studentBean.getStudentData().getStudentId());
        //do the job
        try {
            cachedRowSet = jdbcService.executeQuery(sqlQuery);
            return new ResultSetDataModel(cachedRowSet);
        } catch (Exception e) {
            processException(e);
        }
        return new ResultSetDataModel();
    }

    /**
     *
     * @return
     */
    public DataModel getYearModel() {
        CachedRowSet cachedRowSet;
        String sqlQuery = STD_ATTENDANCE_BY_YEAR_QUERY.replace(STUDENT_TAG,
                "" + studentBean.getStudentData().getStudentId());
        //do the job
        try {
            cachedRowSet = jdbcService.executeQuery(sqlQuery);
            return new ResultSetDataModel(cachedRowSet);
        } catch (Exception e) {
            processException(e);
        }
        return new ResultSetDataModel();
    }
}
