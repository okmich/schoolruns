/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.ProfessionalQualification;
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
public class ProfessionalQualificationBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private ProfessionalQualification professionalQualification = new ProfessionalQualification();
    private DataModel<ProfessionalQualification> professionalQualificationModel;

    public ProfessionalQualificationBean() {
    }

    /**
     * @return
     */
    public String saveProfessionalQualification() {
        try {
            getProfessionalQualification().setModifiedBy(getUserLoginSessionBean().getUserLogin().getUsername());
            ProfessionalQualification _professionalQualification =
                    commonService.saveProfessionalQualification(getProfessionalQualification());
            ((List<ProfessionalQualification>) professionalQualificationModel.getWrappedData()).add(_professionalQualification);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(ProfessionalQualificationBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setProfessionalQualification(new ProfessionalQualification());
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
        setProfessionalQualification(new ProfessionalQualification());
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
     * @return the professionalQualification
     */
    public ProfessionalQualification getProfessionalQualification() {
        return professionalQualification;
    }

    /**
     * @param professionalQualification the professionalQualification to set
     */
    public void setProfessionalQualification(ProfessionalQualification professionalQualification) {
        this.professionalQualification = professionalQualification;
    }

    /**
     * @return the professionalQualificationModel
     */
    public DataModel<ProfessionalQualification> getProfessionalQualificationModel() {
        try {
            professionalQualificationModel = new ListDataModel<ProfessionalQualification>(
                    commonService.findProfessionalQualifications());
        } catch (Exception ex) {
            Logger.getLogger(ProfessionalQualificationBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return professionalQualificationModel;
    }

    /**
     * @param professionalQualificationModel the professionalQualificationModel
     * to set
     */
    public void setProfessionalQualificationModel(DataModel<ProfessionalQualification> professionalQualificationModel) {
        this.professionalQualificationModel = professionalQualificationModel;
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
