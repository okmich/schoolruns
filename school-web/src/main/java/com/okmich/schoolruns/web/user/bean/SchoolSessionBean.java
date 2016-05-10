/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean;

import com.okmich.common.entity.criteria.OrderClause;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.calendar.service.SchoolCalendarService;
import com.okmich.schoolruns.calendar.service.data.SchoolCalendarData;
import com.okmich.schoolruns.common.entity.*;
import com.okmich.schoolruns.employee.service.EmployeeService;
import com.okmich.schoolruns.employee.service.data.EmployeeData;
import com.okmich.schoolruns.employee.service.repo.EmployeeQueryCriteria;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.school.service.repo.SchoolClassQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolSubjectQueryCriteria;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Michael
 */
@ManagedBean
@SessionScoped
public final class SchoolSessionBean extends _BaseBean {

    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{schoolCalendarService}")
    private SchoolCalendarService schoolCalendarService;
    @ManagedProperty("#{employeeService}")
    private EmployeeService employeeService;
    private List<Classroom> classrooms;
    private List<EmployeeData> employees;
    private List<EmployeeData> teachingStaffs;
    private List<EmployeeData> nonTeachingStaffs;
    private List<SchoolClass> schoolClasses;
    private List<SchoolSubject> schoolSubjects;
    private List<SchoolSection> schoolSections;
    private List<SchoolTerm> schoolTerms;
    private List<SchoolYear> schoolYears;
    private SchoolPref schoolPref;
    private SchoolCalendarData schoolCalendarData;
    private School school;
    private Integer gradeLevelId;
    private Integer schoolSectionId;
    //preference properties
    private String webTheme;
    private String appTitle;
    private String schoolSlogan;
    private String icon;
    private String defaultLocale;
    private static final String DEFAULT_TITLE = "SchoolRuns.com";
    private static final String DEFAULT_ICON = "resources/images/school-runs-logo_small.png";

    /**
     * Creates a new instance of SchoolSessionBean
     */
    public SchoolSessionBean() {
        cleanup();
    }

    /**
     * @param schoolService the schoolService to set
     */
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    /**
     * @param schoolCalendarService the schoolCalendarService to set
     */
    public void setSchoolCalendarService(SchoolCalendarService schoolCalendarService) {
        this.schoolCalendarService = schoolCalendarService;
    }

    /**
     * @param employeeService1 the employeeService to set
     */
    public void setEmployeeService(EmployeeService employeeService1) {
        this.employeeService = employeeService1;
    }

    /**
     * initialize property object in this class this is the primary gateway to
     * the class
     *
     * @param school
     */
    public void init(Integer schoolId) throws Exception {
        SchoolPref _schoolPref = schoolService.findSchoolPref(schoolId);
        if (_schoolPref == null) {
            throw new Exception("ERROR_CANNOT_FIND_SCHOOL_PREF");
        }
        setSchoolPref(_schoolPref);
        setSchool(_schoolPref.getSchool());
        try {
            //set the academic year for the school
            this.schoolCalendarData = schoolCalendarService.findSchoolCurrentPeriod(
                    _schoolPref.getSchool().getSchoolId());
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * cleaning up the class property. this will mostly be called on a logout
     * event
     */
    public void cleanup() {
        this.appTitle = null;
        this.classrooms = null;
        this.employees = null;
        this.schoolClasses = null;
        this.schoolSections = null;
        this.schoolSubjects = null;
        this.schoolYears = null;
        this.schoolTerms = null;
        this.schoolPref = null;
        this.schoolSlogan = null;
        this.schoolCalendarData = null;
        this.defaultLocale = null;
        this.webTheme = "bluesky";
        this.icon = DEFAULT_ICON;
    }

    /**
     *
     * @param school1
     */
    public void setSchool(School school1) {
        this.school = school1;
    }

    /**
     * @return the school
     */
    public School getSchool() {
        return school;
    }

    /**
     * @return the facilities
     */
    public List<Facility> getFacilities() {
        return schoolService.findSchoolFacilitiesBySchool(getSchool().getSchoolId());
    }

    /**
     * @return the schoolClasses
     */
    public List<SchoolClass> getSchoolClasses() {
        if (this.schoolClasses == null) {
            this.schoolClasses = schoolService.findSchoolClasses(
                    getSchool().getSchoolId());
        }
        return schoolClasses;
    }

    /**
     * @return the schoolSubjects
     */
    public List<SchoolSubject> getSchoolSubjects() {
        if (this.schoolSubjects == null) {
            this.schoolSubjects = schoolService.findSchoolSubjects(
                    getSchool().getSchoolId());
        }
        return schoolSubjects;
    }

    /**
     * @param schoolSubjects the schoolSubjects to set
     */
    public void setSchoolSubjects(List<SchoolSubject> schoolSubjects) {
        this.schoolSubjects = schoolSubjects;
    }

    /**
     * @return the schoolTerms
     */
    public List<SchoolTerm> getSchoolTerms() {
        if (this.schoolTerms == null) {
            this.schoolTerms = schoolCalendarService.findSchoolTerms(
                    getSchool().getSchoolId(),
                    this.schoolCalendarData.getSchoolYearId());
        }
        return schoolTerms;
    }

    /**
     * @param schoolTerms the schoolTerms to set
     */
    public void setSchoolTerms(List<SchoolTerm> schoolTerms) {
        this.schoolTerms = schoolTerms;
    }

    /**
     * @return the schoolSections
     */
    public List<SchoolSection> getSchoolSections() {
        if (this.schoolSections == null) {
            this.schoolSections = schoolService.findSchoolSections(
                    getSchool().getSchoolId());
        }
        return schoolSections;
    }

    /**
     * @param schoolSections the schoolSections to set
     */
    public void setSchoolSections(List<SchoolSection> schoolSections) {
        this.schoolSections = schoolSections;
    }

    /**
     * @return the schoolYears
     */
    public List<SchoolYear> getSchoolYears() {
        if (this.schoolYears == null) {
            this.schoolYears = schoolCalendarService.findSchoolYears(
                    getSchool().getSchoolId());
        }
        return schoolYears;
    }

    /**
     * @param schoolYears the schoolYears to set
     */
    public void setSchoolYears(List<SchoolYear> schoolYears) {
        this.schoolYears = schoolYears;
    }

    /**
     * @return the employees
     */
    public List<EmployeeData> getEmployees() {
        if (this.employees == null) {
            EmployeeQueryCriteria criteria = new EmployeeQueryCriteria();
            criteria.setSchoolId(getSchool().getSchoolId());

            List<OrderClause> vec = new ArrayList<>();
            vec.add(new OrderClause(EmployeeQueryCriteria.surname));
            criteria.setOrderByColumn(vec);
            this.employees = employeeService.findEmployees(criteria);
        }
        //return employees;
        return employees;
    }

    /**
     * @param employees the employees to set
     */
    public void setEmployees(List<EmployeeData> employees) {
        this.employees = employees;
    }

    /**
     *
     * @return
     */
    public List<EmployeeData> getTeachingStaffs() {
        if (this.teachingStaffs == null) {
            EmployeeQueryCriteria criteria = new EmployeeQueryCriteria();
            criteria.setSchoolId(school.getSchoolId());
            //criteria.set
            criteria.setStatus(CommonConstants.STATUS_ACTIVE);

            List<OrderClause> vec = new ArrayList<>();
            vec.add(new OrderClause(EmployeeQueryCriteria.surname));
            criteria.setOrderByColumn(vec);

            this.teachingStaffs = employeeService.findEmployees(criteria);
        }

        return teachingStaffs;
    }

    /**
     * @return the nonTeachingStaffs
     */
    public List<EmployeeData> getNonTeachingStaffs() {
        if (this.nonTeachingStaffs == null) {
            EmployeeQueryCriteria criteria = new EmployeeQueryCriteria();
            criteria.setSchoolId(school.getSchoolId());
            //criteria.set
            criteria.setStatus(CommonConstants.STATUS_ACTIVE);

            List<OrderClause> vec = new ArrayList<>();
            vec.add(new OrderClause(EmployeeQueryCriteria.surname));
            criteria.setOrderByColumn(vec);

            this.teachingStaffs = employeeService.findEmployees(criteria);
        }
        return nonTeachingStaffs;
    }

    /**
     * @return the schoolPref
     */
    public SchoolPref getSchoolPref() {
        return schoolPref;
    }

    /**
     * @param _schoolPref the schoolPref to set
     */
    public void setSchoolPref(SchoolPref _schoolPref) {
        this.schoolPref = _schoolPref;
        if (_schoolPref != null) {
            setIcon(_schoolPref.getLogoUrl());
            setWebTheme(_schoolPref.getWebpageTheme());
            setAppTitle(_schoolPref.getSchool() != null ? _schoolPref.getSchool().getName() : null);
            this.schoolSlogan = _schoolPref.getSchool() != null ? _schoolPref.getSchool().getSlogan() : "";
        }
    }

    /**
     * @return the schoolCalendarData
     */
    public SchoolCalendarData getSchoolCalendarData() {
        if (this.schoolCalendarData == null) {
            this.schoolCalendarData = new SchoolCalendarData();
            this.schoolCalendarData.setTermDescription("No Year in context");
        }
        return schoolCalendarData;
    }

    /**
     * @param schoolCalendarData the schoolCalendarData to set
     */
    public void setSchoolCalendarData(SchoolCalendarData schoolCalendarData) {
        this.schoolCalendarData = schoolCalendarData;
    }

    /**
     *
     * @return
     */
    public List<Club> getSchoolClubs() {
        return schoolService.findClubs(getSchool().getSchoolId());
    }

    /**
     * **************************************************************
     * EVENT TO CHANGE THE ENTIRE SCHOOL YEAR OR TERM
     * **************************************************************
     */
    public void changeSchoolYearEvent(ValueChangeEvent event) {
        Integer _schoolYearId = null;
        HtmlSelectOneMenu yearMenu = (HtmlSelectOneMenu) event.getComponent();
        Object _yearValue = yearMenu.getValue();
        if (_yearValue != null) {
            _schoolYearId = Integer.valueOf(_yearValue.toString());
        }
        if (_schoolYearId != null) {
            try {
                //reset the school term list
                setSchoolTerms(schoolCalendarService.findSchoolTerms(getSchool().getSchoolId(),
                        _schoolYearId));
                //change the year portion
                SchoolYear schoolYear = schoolCalendarService.findSchoolYear(_schoolYearId);
                getSchoolCalendarData().setSchoolYear(schoolYear);
            } catch (Exception e) {
                processException(e);
            }
        }
    }

    public void changeSchoolTermEvent(ValueChangeEvent event) {
        Integer _schoolTermId = null;
        HtmlSelectOneMenu termMenu = (HtmlSelectOneMenu) event.getComponent();
        Object _termValue = termMenu.getValue();
        if (_termValue != null) {
            _schoolTermId = Integer.valueOf(_termValue.toString());
        }
        if (_schoolTermId != null) {
            try {
                //change the term portion
                SchoolTerm schoolTerm = schoolCalendarService.findSchoolTerm(_schoolTermId);
                getSchoolCalendarData().setSchoolTerm(schoolTerm);
            } catch (Exception e) {
                processException(e);
            }
        }
    }

    /**
     * **************************************************************
     * EVENT TO CHANGE THE subject or class list based on grade
     * **************************************************************
     */
    /**
     * **************************************************************
     * EVENT TO CHANGE THE subject or class list based on grade
     * **************************************************************
     */
    public void changeSchoolSectionEvent(ValueChangeEvent event) {
        Object object = ((UIInput) event.getComponent()).getValue();
        this.schoolSectionId = (Integer) object;
    }

    public void changeGradeEvent(ValueChangeEvent event) {
        Object object = ((UIInput) event.getComponent()).getValue();
        this.gradeLevelId = (Integer) object;
    }

    /*
     * @return
     *
     */
    public List<SchoolClass> getSchoolClassByGrade() {
        SchoolClassQueryCriteria criteria = new SchoolClassQueryCriteria();
        criteria.setSchoolId(this.school.getSchoolId());
        if (this.gradeLevelId != null) {
            criteria.setGradeLevelId(this.gradeLevelId);
        }
        List<OrderClause> vec = new ArrayList<>(1);
        vec.add(new OrderClause(SchoolClassQueryCriteria.description));
        criteria.setOrderByColumn(vec);

        return schoolService.findSchoolClasses(criteria);
    }

    /*
     * @return
     *
     */
    public List<SchoolClass> getSchoolClassBySection() {
        SchoolClassQueryCriteria criteria = new SchoolClassQueryCriteria();
        criteria.setSchoolSectionId(this.schoolSectionId);
        List<OrderClause> vec = new ArrayList<>(1);
        vec.add(new OrderClause(SchoolClassQueryCriteria.description));
        criteria.setOrderByColumn(vec);

        return schoolService.findSchoolClasses(criteria);
    }

    /**
     *
     * @return
     */
    public List<SchoolSubject> getSchoolSubjectByGrade() {
        SchoolSubjectQueryCriteria criteria = new SchoolSubjectQueryCriteria();
        criteria.setSchoolId(this.school.getSchoolId());
        if (this.gradeLevelId != null) {
            criteria.setGradeLevelId(this.gradeLevelId);
        }
        List<OrderClause> vec = new ArrayList<>(2);
        vec.add(new OrderClause(SchoolSubjectQueryCriteria.gradeLevelId));
        vec.add(new OrderClause(SchoolSubjectQueryCriteria.subjectCode));
        criteria.setOrderByColumn(vec);

        return schoolService.findSchoolSubjects(criteria);
    }

    /**
     * ************************************************
     * PREFERENCE METHODS ************************************************
     */
    /**
     * @return the appTitle
     */
    public final String getAppTitle() {
        if (appTitle == null) {
            appTitle = DEFAULT_TITLE;
        }
        return appTitle;
    }

    /**
     * @param appTitle the appTitle to set
     */
    public final void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    /**
     * @return the icon
     */
    public final String getIcon() {
        if (icon == null) {
            icon = DEFAULT_ICON;
        }
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public final void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the webTheme
     */
    public String getWebTheme() {
        return webTheme;
    }

    /**
     * @param webTheme the webTheme to set
     */
    public void setWebTheme(String webTheme) {
        this.webTheme = webTheme;
    }

    /**
     * @return the defaultLocale
     */
    public String getDefaultLocale() {
        return defaultLocale;
    }

    /**
     * @param defaultLocale the defaultLocale to set
     */
    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    /**
     * @return the schoolSlogan
     */
    public String getSchoolSlogan() {
        return schoolSlogan;
    }

    /**
     * @return the gradeLevelId
     */
    public Integer getGradeLevelId() {
        return gradeLevelId;
    }

    /**
     * @param gradeLevelId the gradeLevelId to set
     */
    public void setGradeLevelId(Integer gradeLevelId) {
        this.gradeLevelId = gradeLevelId;
    }

    /**
     * @return the schoolSectionId
     */
    public Integer getSchoolSectionId() {
        return schoolSectionId;
    }

    /**
     * @param schoolSectionId the schoolSectionId to set
     */
    public void setSchoolSectionId(Integer schoolSectionId) {
        this.schoolSectionId = schoolSectionId;
    }

    /**
     * @return the classrooms
     */
    public List<Classroom> getClassrooms() {
        if (classrooms == null) {
            classrooms = schoolService.findSchoolClassrooms(
                    getSchool().getSchoolId());
        }
        return classrooms;
    }

    /**
     * @param classrooms the classrooms to set
     */
    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }
}