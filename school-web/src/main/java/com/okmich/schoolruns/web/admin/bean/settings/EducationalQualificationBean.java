/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.EducationalQualification;
import com.okmich.schoolruns.common.service.CommonService;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
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
public class EducationalQualificationBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private EducationalQualification educationalQualification = new EducationalQualification();
    private DataModel<EducationalQualification> educationalQualificationModel;

    public EducationalQualificationBean() {
    }

    /**
     * @return
     */
    public String saveEducationalQualification() {
        try {
            getEducationalQualification().setModifiedBy(getUserLoginSessionBean().getUserLogin().getUsername());
            EducationalQualification _educationalQualification =
                    commonService.saveEducationalQualification(getEducationalQualification());
            ((List<EducationalQualification>) educationalQualificationModel.getWrappedData()).add(
                    _educationalQualification);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(EducationalQualificationBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setEducationalQualification(new EducationalQualification());
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
        setEducationalQualification(new EducationalQualification());
        setEditMode(null);
        return "";
    }

    /**
     * @return the commonService
     */
    public CommonService getCommonService() {
        return commonService;
    }

    /**
     * @param commonService the commonService to set
     */
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     * @return the educationalQualification
     */
    public EducationalQualification getEducationalQualification() {
        return educationalQualification;
    }

    /**
     * @param educationalQualification the educationalQualification to set
     */
    public void setEducationalQualification(EducationalQualification educationalQualification) {
        this.educationalQualification = educationalQualification;
    }

    /**
     * @return the educationalQualificationModel
     */
    public DataModel<EducationalQualification> getEducationalQualificationModel() {
        try {
            educationalQualificationModel = new ListDataModel<EducationalQualification>(
                    commonService.findEducationalQualifications());
        } catch (Exception ex) {
            Logger.getLogger(EducationalQualificationBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return educationalQualificationModel;
    }

    /**
     * @param educationalQualificationModel the educationalQualificationModel to
     * set
     */
    public void setEducationalQualificationModel(DataModel<EducationalQualification> educationalQualificationModel) {
        this.educationalQualificationModel = educationalQualificationModel;
    }

    /**
     * @return the userLoginSessionBean
     */
    public UserLoginSessionBean getUserLoginSessionBean() {
        return userLoginSessionBean;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }
}
