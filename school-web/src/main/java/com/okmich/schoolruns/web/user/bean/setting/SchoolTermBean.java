/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.setting;

import com.okmich.schoolruns.calendar.service.SchoolCalendarService;
import com.okmich.schoolruns.common.entity.SchoolTerm;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.List;
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
public class SchoolTermBean extends _BaseBean {

    @ManagedProperty("#{schoolCalendarService}")
    private SchoolCalendarService schoolCalendarService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    private SchoolTerm schoolTerm = new SchoolTerm();
    private DataModel<SchoolTerm> schoolTermModel;

    /**
     * Creates a new instance of SchoolTermBean
     */
    public SchoolTermBean() {
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
     * @param schoolCalendarService the schoolCalendarService to set
     */
    public void setSchoolCalendarService(SchoolCalendarService schoolCalendarService) {
        this.schoolCalendarService = schoolCalendarService;
    }

    /**
     * @return
     */
    public String saveSchoolTerm() {
        try {
            if (getSchoolTerm().getClosingDate().before(getSchoolTerm().getStartDate())) {
                throw new Exception("CLOSING_DAY_BEFORE_STARTUP_DATE");
            }
            getSchoolTerm().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getSchoolTerm().setSchoolYearId(
                    schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
            SchoolTerm _schoolTerm = schoolCalendarService.saveSchoolTerm(getSchoolTerm());
            ((List<SchoolTerm>) getSchoolTermModel().getWrappedData()).add(_schoolTerm);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(SchoolTermBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
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
        setSchoolTerm(new SchoolTerm());
        setEditMode(null);
        return "";
    }

    /**
     * @return the schoolTerm
     */
    public SchoolTerm getSchoolTerm() {
        if (schoolTerm == null) {
            this.schoolTerm = new SchoolTerm();
        }
        return schoolTerm;
    }

    /**
     * @param schoolTerm the schoolTerm to set
     */
    public void setSchoolTerm(SchoolTerm schoolTerm1) {
        this.schoolTerm = schoolTerm1;
    }

    /**
     * @return the schoolTermModel
     */
    public DataModel<SchoolTerm> getSchoolTermModel() {
        try {
            schoolTermModel = new ListDataModel<>(
                    schoolCalendarService.findSchoolTerms(
                    schoolSessionBean.getSchool().getSchoolId(),
                    schoolSessionBean.getSchoolCalendarData().getSchoolYearId()));
        } catch (Exception ex) {
            Logger.getLogger(SchoolTermBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return schoolTermModel;
    }

    /**
     * @param schoolTermModel the schoolTermModel to set
     */
    public void setSchoolTermModel(DataModel<SchoolTerm> schoolTermModel) {
        this.setSchoolTermModel(schoolTermModel);
    }
}