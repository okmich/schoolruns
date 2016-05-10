/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.WarningLevel;
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
public class WarningLevelBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private WarningLevel warningLevel = new WarningLevel();
    private DataModel<WarningLevel> warningLevelModel;

    public WarningLevelBean() {
    }

    /**
     * @return
     */
    public String saveWarningLevel() {
        try {
            WarningLevel _warningLevel = commonService.saveWarningLevel(getWarningLevel());
            ((List<WarningLevel>) warningLevelModel.getWrappedData()).add(_warningLevel);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(WarningLevelBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setWarningLevel(new WarningLevel());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(WarningLevelBean.class.getName()).log(Level.INFO, "" + getWarningLevel(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setWarningLevel(new WarningLevel());
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
     * @return the warningLevel
     */
    public WarningLevel getWarningLevel() {
        return warningLevel;
    }

    /**
     * @param warningLevel the warningLevel to set
     */
    public void setWarningLevel(WarningLevel warningLevel) {
        this.warningLevel = warningLevel;
    }

    /**
     * @return the warningLevelModel
     */
    public DataModel<WarningLevel> getWarningLevelModel() {
        try {
            warningLevelModel = new ListDataModel<WarningLevel>(
                    commonService.findWarningLevels());
        } catch (Exception ex) {
            Logger.getLogger(WarningLevelBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return warningLevelModel;
    }

    /**
     * @param warningLevelModel the warningLevelModel to set
     */
    public void setWarningLevelModel(DataModel<WarningLevel> warningLevelModel) {
        this.warningLevelModel = warningLevelModel;
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