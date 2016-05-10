/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.setting;

import com.okmich.common.entity.criteria.OrderClause;
import com.okmich.schoolruns.calendar.service.SchoolCalendarService;
import com.okmich.schoolruns.calendar.service.repo.HolidayQueryCriteria;
import com.okmich.schoolruns.common.entity.Holiday;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@ViewScoped
public class HolidayBean extends _BaseBean {

    @ManagedProperty("#{schoolCalendarService}")
    private SchoolCalendarService schoolCalendarService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    private Holiday holiday;
    private DataModel<Holiday> holidayModel;

    /**
     * Creates a new instance of HolidayBean
     */
    public HolidayBean() {
    }

    /**
     * @param schoolCalendarService the schoolCalendarService to set
     */
    public void setSchoolCalendarService(SchoolCalendarService schoolCalendarService) {
        this.schoolCalendarService = schoolCalendarService;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @return
     */
    public String saveHoliday() {
        try {
            getHoliday().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getHoliday().setSchoolYearId(schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
            Holiday _holiday = schoolCalendarService.saveHoliday(getHoliday());
            List<Holiday> holidays = ((List<Holiday>) getHolidayModel().getWrappedData());
            if (holidays.contains(_holiday)) {
                holidays.remove(_holiday);
            }
            holidays.add(_holiday);
            clearForm();
            return "";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

//    /**
//     * @return
//     */
//    public String recreatePreviousHolidays() {
//        try {
//            getHoliday().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
//            getHoliday().setSchool(schoolSessionBean.getSchool());
//            Holiday _holiday = schoolService.saveHoliday(getHoliday());
//            ((List<Holiday>) getHolidayModel().getWrappedData()).add(_holiday);
//            if (!schoolSessionBean.getHolidays().contains(_holiday)) {
//                schoolSessionBean.getHolidays().add(_holiday);
//            }
//            clearForm();
//            return "";
//        } catch (Exception ex) {
//            Logger.getLogger(HolidayBean.class.getName()).log(Level.SEVERE, null, ex);
//            FacesUtil.getFacesContext().addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
//        }
//        return "";
//    }
    /**
     *
     * @return
     */
    public String prepareForCreate() {
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setHoliday(null);
        setEditMode(null);
        return "";
    }

    /**
     * @return the holiday
     */
    public Holiday getHoliday() {
        if (this.holiday == null) {
            this.holiday = new Holiday();
        }
        return holiday;
    }

    /**
     * @param holiday the holiday1 to set
     */
    public void setHoliday(Holiday holiday1) {
        this.holiday = holiday1;
    }

    /**
     * @return the holidayModel
     */
    public DataModel<Holiday> getHolidayModel() {
        try {
            holidayModel = new ListDataModel<>(
                    schoolCalendarService.findHolidays(buildQueryCriteria()));
        } catch (Exception ex) {
            Logger.getLogger(HolidayBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return holidayModel;
    }

    /**
     * @param holidayModel the holidayModel to set
     */
    public void setHolidayModel(DataModel<Holiday> holidayModel) {
        this.setHolidayModel(holidayModel);
    }

    private HolidayQueryCriteria buildQueryCriteria() {
        HolidayQueryCriteria criteria = new HolidayQueryCriteria();

        criteria.setSchoolYearId(schoolSessionBean.getSchoolCalendarData().getSchoolYearId());

        return criteria;
    }
}