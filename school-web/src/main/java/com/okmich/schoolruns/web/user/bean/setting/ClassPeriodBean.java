/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.setting;

import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.calendar.service.SchoolCalendarService;
import com.okmich.schoolruns.common.entity.ClassPeriod;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@ViewScoped
public class ClassPeriodBean extends _BaseBean {

    @ManagedProperty("#{schoolCalendarService}")
    private SchoolCalendarService schoolCalendarService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    private ClassPeriod classPeriod = new ClassPeriod();
    private DataModel<ClassPeriod> classPeriodModel;
    private Integer schoolSectionId;

    /**
     * Creates a new instance of ClassPeriodBean
     */
    public ClassPeriodBean() {
    }

    /**
     * @param schoolCalendarService1 the schoolCalendarService to set
     */
    public void setSchoolCalendarService(SchoolCalendarService schoolCalendarService1) {
        this.schoolCalendarService = schoolCalendarService1;
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
    public String saveClassPeriod() {
        try {
            getClassPeriod().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            ClassPeriod _classPeriod = schoolCalendarService.saveClassPeriod(getClassPeriod());
            ((List<ClassPeriod>) getClassPeriodModel().getWrappedData()).add(_classPeriod);
            clearForm();
            return "";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

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
        if (this.schoolSectionId != null) {
            setEditMode("MODIFY");
            getClassPeriod().setModifiedTime(new Date());
            getClassPeriod().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getClassPeriod().setStatus(CommonConstants.STATUS_ACTIVE);
            getClassPeriod().setSchoolSectionId(schoolSectionId);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setClassPeriod(new ClassPeriod());
        setEditMode(null);
        return "";
    }

    /**
     *
     * @param event
     */
    public void changeSchoolSectionEvent(ValueChangeEvent event) {
        Integer _ssectionId = null;
        HtmlSelectOneMenu sectionMenu = (HtmlSelectOneMenu) event.getComponent();
        Object _sectionValue = sectionMenu.getValue();
        if (_sectionValue != null) {
            _ssectionId = Integer.valueOf(_sectionValue.toString());
        }
        if (_ssectionId != null) {
            setSchoolSectionId(_ssectionId);
            getClassPeriod().setSchoolSectionId(_ssectionId);
        }

    }

    /**
     * @return the classPeriod
     */
    public ClassPeriod getClassPeriod() {
        if (this.classPeriod == null) {
            this.classPeriod = new ClassPeriod();
        }
        return classPeriod;
    }

    /**
     * @param classPeriod the classPeriod to set
     */
    public void setClassPeriod(ClassPeriod classPeriod1) {
        this.classPeriod = classPeriod1;
    }

    /**
     * @return the classPeriodModel
     */
    public DataModel<ClassPeriod> getClassPeriodModel() {
        if (this.schoolSectionId != null) {
            try {
                classPeriodModel = new ListDataModel<>(
                        schoolCalendarService.findClassPeriods(getSchoolSectionId()));
            } catch (Exception ex) {
                processException(ex);
            }
        }
        return classPeriodModel;
    }

    /**
     * @param classPeriodModel the classPeriodModel to set
     */
    public void setClassPeriodModel(DataModel<ClassPeriod> classPeriodModel) {
        this.setClassPeriodModel(classPeriodModel);
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
}
