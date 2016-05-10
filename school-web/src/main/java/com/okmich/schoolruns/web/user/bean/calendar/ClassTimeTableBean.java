/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.calendar;

import com.okmich.schoolruns.calendar.service.SchoolCalendarService;
import com.okmich.schoolruns.calendar.service.data.TtEntryTable;
import com.okmich.schoolruns.common.entity.ClassPeriod;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.common.entity.TimetableEntry;
import com.okmich.schoolruns.common.entity.Weekday;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class ClassTimeTableBean extends _BaseBean {

    @ManagedProperty("#{schoolCalendarService}")
    private SchoolCalendarService schoolCalendarService;
    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private Integer schoolClassId;
    private Integer schoolSubjectId;

    /**
     * Creates a new instance of ClassTimeTableBean
     */
    public ClassTimeTableBean() {
    }

    /**
     * @param schoolCalendarService the schoolCalendarService to set
     */
    public void setSchoolCalendarService(SchoolCalendarService schoolCalendarService) {
        this.schoolCalendarService = schoolCalendarService;
    }

    /**
     * @param schoolService the schoolService to set
     */
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    /**
     * @param schoolSessionBean1 the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean1) {
        this.schoolSessionBean = schoolSessionBean1;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @return the schoolClassId
     */
    public Integer getSchoolClassId() {
        return schoolClassId;
    }

    /**
     * @param schoolClassId the schoolClassId to set
     */
    public void setSchoolClassId(Integer schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public String createTimeTableEntry() {
        TimetableEntry ttEntry = sessionBean.getTimetableEntry();
        if (ttEntry == null) {
            return "";
        }
        ttEntry.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            ttEntry = schoolCalendarService.createTimetableEntry(
                    ttEntry);
            //set the new entry into the session table
            sessionBean.getTtEntryTable().setValue(
                    ttEntry.getWeekday(),
                    ttEntry.getClassPeriod(), ttEntry);
            return "/schooluser/calendar/classtimetablesearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String deleteTimeTableEntry() {
        TimetableEntry ttEntry = sessionBean.getTimetableEntry();
        if (ttEntry == null) {
            return "";
        }
        try {
            schoolCalendarService.deleteTimetableEntry(ttEntry);
            //set the a entry into the session table
            sessionBean.getTtEntryTable().remove(
                    ttEntry.getWeekday(),
                    ttEntry.getClassPeriod());
            return "/schooluser/calendar/classtimetablesearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @param event
     */
    public void changeSchoolClassEvent(ValueChangeEvent event) {
        Integer _schoolClassId = null;
        HtmlSelectOneMenu sectionMenu = (HtmlSelectOneMenu) event.getComponent();
        Object _classValue = sectionMenu.getValue();
        if (_classValue != null) {
            _schoolClassId = Integer.valueOf(_classValue.toString());
        }
        if (_schoolClassId != null) {
            setSchoolClassId(_schoolClassId);
            findClassTimeTable();
        }
    }

    /**
     *
     */
    public void findClassTimeTable() {
        if (getSchoolClassId() != null) {
            TtEntryTable ttEntryTable = schoolCalendarService.findClassTimetable(
                    getSchoolClassId(), schoolSessionBean.getSchoolPref());
            sessionBean.setTtEntryTable(ttEntryTable);
        }
    }

    /**
     *
     * @return
     */
    public String findTimetableEntry() {
        String weekdayCode = FacesUtil.getRequestParameter("weekdayCode");
        Integer classPeriodId = parseInt(FacesUtil.getRequestParameter("classPeriodId"));
        TimetableEntry timetableEntry = sessionBean.getTtEntryTable().getTimetableEntry(
                new Weekday(weekdayCode), new ClassPeriod(classPeriodId));
        //set the edit mode based on the status of the object
        if (timetableEntry.getTimetableEntryId() == null) {
            sessionBean.setEditMode(CREATE);
            timetableEntry.setClassPeriod(schoolCalendarService.findClassPeriod(classPeriodId));
            //in case the client uses an existing session and did not select a class
            if (schoolClassId == null || schoolClassId == 0) {
                FacesUtil.getFacesContext().addMessage("",
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "SELECT A CLASS", ""));
                return "";
            }
            SchoolClass sc = schoolSessionBean.getSchoolClasses().get(
                    schoolSessionBean.getSchoolClasses().indexOf(
                    new SchoolClass(schoolClassId)));
            timetableEntry.setSchoolClass(sc);
            //set the grade level for the schoolSessionBean
            schoolSessionBean.setGradeLevelId(sc.getGradeLevel().getGradeLevelId());
        } else {
            sessionBean.setEditMode(VIEW);
        }
        //set on the session object
        sessionBean.setTimetableEntry(timetableEntry);
        //move to the next url
        return "/schooluser/calendar/classtimetabledetails";
    }

    /**
     *
     * @return
     */
    public String goBack() {
        sessionBean.setEditMode(null);
        try {
            setSchoolClassId(sessionBean.getTimetableEntry().
                    getSchoolClass().getSchoolClassId());
        } catch (Exception ex) {
            Logger.getLogger(ClassTimeTableBean.class.getName()).log(
                    Level.SEVERE, ex.getMessage(), ex);
        }
        sessionBean.setTimetableEntry(null);
        return "/schooluser/calendar/classtimetablesearch";
    }

    /**
     * parse String to Integer. Return 0 is NumberException occurs or if the
     * String is null
     *
     * @param val
     * @return
     */
    private Integer parseInt(String val) {
        if (val != null) {
            try {
                return Integer.parseInt(val, 10);
            } catch (Exception ex) {
                Logger.getLogger(ClassTimeTableBean.class.getName()).log(
                        Level.SEVERE, ex.getMessage(), ex);
            }
        }
        return 0;
    }

    /**
     * @return the schoolSubjectId
     */
    public Integer getSchoolSubjectId() {
        return schoolSubjectId;
    }

    /**
     * @param schoolSubjectId the schoolSubjectId to set
     */
    public void setSchoolSubjectId(Integer schoolSubjectId) {
        this.schoolSubjectId = schoolSubjectId;
    }
}