/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.IdentificationMeans;
import com.okmich.schoolruns.common.entity.Language;
import com.okmich.schoolruns.common.entity.MaritalStatus;
import com.okmich.schoolruns.common.entity.Religion;
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
public final class ProfileSettingBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private IdentificationMeans identificationMeans = new IdentificationMeans();
    private Language language;
    private MaritalStatus maritalStatus = new MaritalStatus();
    private Religion religion = new Religion();
    private DataModel<Religion> religionModel;
    private DataModel<IdentificationMeans> identificationModel;
    private DataModel<Language> languageModel;
    private DataModel<MaritalStatus> maritalStatusModel;

    public ProfileSettingBean() {
        clearForm();
    }

    /**
     * @return
     */
    public String saveIdentificationMeans() {
        try {
            IdentificationMeans _idMean = commonService.saveIdentificationMeans(getIdentificationMeans());
            ((List<IdentificationMeans>) identificationModel.getWrappedData()).add(_idMean);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(ProfileSettingBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    /**
     * @return
     */
    public String saveLanguage() {
        getLanguage().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            Language _language = commonService.saveLanguage(getLanguage());
            ((List<Language>) languageModel.getWrappedData()).add(_language);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(ProfileSettingBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    /**
     * @return
     */
    public String saveMaritalStatus() {
        getMaritalStatus().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            MaritalStatus _maritalStatus = commonService.saveMaritalStatus(getMaritalStatus());
            ((List<MaritalStatus>) maritalStatusModel.getWrappedData()).add(_maritalStatus);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(ProfileSettingBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    /**
     * @return
     */
    public String saveReligion() {
        getReligion().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            Religion _religion = commonService.saveReligion(getReligion());
            ((List<Religion>) religionModel.getWrappedData()).add(_religion);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(ProfileSettingBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setIdentificationMeans(new IdentificationMeans());
        setLanguage(new Language());
        setMaritalStatus(new MaritalStatus());
        setReligion(new Religion());
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
        setReligion(new Religion());
        setIdentificationMeans(new IdentificationMeans());
        setLanguage(new Language());
        setMaritalStatus(new MaritalStatus());
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
     * @return the religion
     */
    public Religion getReligion() {
        return religion;
    }

    /**
     * @param religion the religion to set
     */
    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    /**
     * @return the religionModel
     */
    public DataModel<Religion> getReligionModel() {
        try {
            setReligionModel(new ListDataModel<Religion>(
                    commonService.findReligions()));
        } catch (Exception ex) {
            Logger.getLogger(ProfileSettingBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return religionModel;
    }

    /**
     * @param religionModel the religionModel to set
     */
    public void setReligionModel(DataModel<Religion> religionModel) {
        this.religionModel = religionModel;
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

    /**
     * @return the maritalStatus
     */
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * @param maritalStatus the maritalStatus to set
     */
    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * @return the identificationModel
     */
    public DataModel<IdentificationMeans> getIdentificationModel() {
        try {
            setIdentificationModel(new ListDataModel<IdentificationMeans>(
                    commonService.findIdentificationMeans()));
        } catch (Exception ex) {
            Logger.getLogger(ProfileSettingBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return identificationModel;
    }

    /**
     * @param identificationModel the identificationModel to set
     */
    public void setIdentificationModel(DataModel<IdentificationMeans> identificationModel) {
        this.identificationModel = identificationModel;
    }

    /**
     * @return the languageModel
     */
    public DataModel<Language> getLanguageModel() {
        try {
            setLanguageModel(new ListDataModel<Language>(
                    commonService.findLanguages()));
        } catch (Exception ex) {
            Logger.getLogger(ProfileSettingBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return languageModel;
    }

    /**
     * @param languageModel the languageModel to set
     */
    public void setLanguageModel(DataModel<Language> languageModel) {
        this.languageModel = languageModel;
    }

    /**
     * @return the maritalStatusModel
     */
    public DataModel<MaritalStatus> getMaritalStatusModel() {
        try {
            setMaritalStatusModel(new ListDataModel<MaritalStatus>(
                    commonService.findMaritalStatuss()));
        } catch (Exception ex) {
            Logger.getLogger(ProfileSettingBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return maritalStatusModel;
    }

    /**
     * @param maritalStatusModel the maritalStatusModel to set
     */
    public void setMaritalStatusModel(DataModel<MaritalStatus> maritalStatusModel) {
        this.maritalStatusModel = maritalStatusModel;
    }

    /**
     * @return the identificationMeans
     */
    public IdentificationMeans getIdentificationMeans() {
        return identificationMeans;
    }

    /**
     * @param identificationMeans the identificationMeans to set
     */
    public void setIdentificationMeans(IdentificationMeans identificationMeans) {
        this.identificationMeans = identificationMeans;
    }

    /**
     * @return the language
     */
    public Language getLanguage() {
        if (this.language == null) {
            this.language = new Language();
        }
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(Language language) {
        this.language = language;
    }
}