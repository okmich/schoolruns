/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.parent.bean;

import com.okmich.schoolruns.common.service.JdbcService;
import com.okmich.schoolruns.school.service.SchoolAcademicService;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class StudentAssignmentBean extends _BaseBean {

    @ManagedProperty("#{schoolAcademicService}")
    private SchoolAcademicService schoolAcademicService;
    @ManagedProperty("#{jdbcService}")
    private JdbcService jdbcService;
    @ManagedProperty("#{parentSessionBean}")
    private ParentSessionBean parentSessionBean;
    @ManagedProperty("#{studentBean}")
    private StudentBean studentBean;
    
    private static final String BY_SCHOOL_YEAR_TERM = "";
    /**
     * Creates a new instance of StudentAssignmentBean
     */
    public StudentAssignmentBean() {
    }

    /**
     * @param schoolAcademicService the schoolAcademicService to set
     */
    public void setSchoolAcademicService(SchoolAcademicService schoolAcademicService) {
        this.schoolAcademicService = schoolAcademicService;
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
}
