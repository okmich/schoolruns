/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.LiabilityType;
import com.okmich.schoolruns.finance.service.FinanceSetupService;
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
public class LiabilityTypeBean extends _BaseBean {

    @ManagedProperty("#{financeSetupService}")
    private FinanceSetupService financeSetupService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private LiabilityType liabilityType = new LiabilityType();
    private DataModel<LiabilityType> liabilityTypeModel;

    public LiabilityTypeBean() {
    }

    /**
     * @return
     */
    public String saveLiabilityType() {
        try {
            LiabilityType _liabilityType = financeSetupService.saveLiabilityType(getLiabilityType());
            ((List<LiabilityType>) liabilityTypeModel.getWrappedData()).add(_liabilityType);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(LiabilityTypeBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setLiabilityType(new LiabilityType());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(LiabilityTypeBean.class.getName()).log(Level.INFO, "" + getLiabilityType(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setLiabilityType(new LiabilityType());
        setEditMode(null);
        return "";
    }

    /**
     * @return the financeSetupService
     */
    public FinanceSetupService getFinanceSetupService() {
        return financeSetupService;
    }

    /**
     * @param financeSetupService the financeSetupService to set
     */
    public void setFinanceSetupService(FinanceSetupService financeSetupService) {
        this.financeSetupService = financeSetupService;
    }

    /**
     * @return the liabilityType
     */
    public LiabilityType getLiabilityType() {
        return liabilityType;
    }

    /**
     * @param liabilityType the liabilityType to set
     */
    public void setLiabilityType(LiabilityType liabilityType) {
        this.liabilityType = liabilityType;
    }

    /**
     * @return the liabilityTypeModel
     */
    public DataModel<LiabilityType> getLiabilityTypeModel() {
        try {
            liabilityTypeModel = new ListDataModel<>(
                    financeSetupService.findLiabilityTypes());
        } catch (Exception ex) {
            Logger.getLogger(LiabilityTypeBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return liabilityTypeModel;
    }

    /**
     * @param liabilityTypeModel the liabilityTypeModel to set
     */
    public void setLiabilityTypeModel(DataModel<LiabilityType> liabilityTypeModel) {
        this.liabilityTypeModel = liabilityTypeModel;
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
