/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.setting;

import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.SchoolSection;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.school.service.repo.SchoolSectionQueryCriteria;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.Date;
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
public class SchoolSectionBean extends _BaseBean {

    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    private SchoolSection schoolSection = new SchoolSection();
    private DataModel<SchoolSection> schoolSectionModel;

    /**
     * Creates a new instance of SchoolSectionBean
     */
    public SchoolSectionBean() {
    }

    /**
     * @param schoolService the schoolService to set
     */
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
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
    public String saveSchoolSection() {
        try {
            getSchoolSection().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getSchoolSection().setSchool(schoolSessionBean.getSchool());
            SchoolSection _schoolSection = schoolService.saveSchoolSection(getSchoolSection());
            ((List<SchoolSection>) getSchoolSectionModel().getWrappedData()).add(_schoolSection);
            if (!schoolSessionBean.getSchoolSections().contains(_schoolSection)) {
                schoolSessionBean.getSchoolSections().add(_schoolSection);
            }
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(SchoolSectionBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
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
        setEditMode("MODIFY");
        getSchoolSection().setModifiedTime(new Date());
        getSchoolSection().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getSchoolSection().setStatus(CommonConstants.STATUS_ACTIVE);
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setSchoolSection(new SchoolSection());
        setEditMode(null);
        return "";
    }

    /**
     * @return the schoolSection
     */
    public SchoolSection getSchoolSection() {
        if (this.schoolSection == null) {
            this.schoolSection = new SchoolSection();
        }
        return schoolSection;
    }

    /**
     * @param schoolSection the schoolSection to set
     */
    public void setSchoolSection(SchoolSection schoolSection1) {
        this.schoolSection = schoolSection1;
    }

    /**
     * @return the schoolSectionModel
     */
    public DataModel<SchoolSection> getSchoolSectionModel() {
        try {
            schoolSectionModel = new ListDataModel<>(
                    schoolService.findSchoolSections(buildQueryCriteria()));
        } catch (Exception ex) {
            Logger.getLogger(SchoolSectionBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return schoolSectionModel;
    }

    /**
     * @param schoolSectionModel the schoolSectionModel to set
     */
    public void setSchoolSectionModel(DataModel<SchoolSection> schoolSectionModel) {
        this.setSchoolSectionModel(schoolSectionModel);
    }

    private SchoolSectionQueryCriteria buildQueryCriteria() {
        SchoolSectionQueryCriteria criteria = new SchoolSectionQueryCriteria();
        //set school
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());

        return criteria;
    }
}
