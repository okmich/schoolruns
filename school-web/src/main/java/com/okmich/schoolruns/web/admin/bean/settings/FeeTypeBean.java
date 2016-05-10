/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.FeeType;
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
public class FeeTypeBean extends _BaseBean {

    @ManagedProperty("#{financeSetupService}")
    private FinanceSetupService financeSetupService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private FeeType feeType = new FeeType();
    private DataModel<FeeType> feeTypeModel;

    public FeeTypeBean() {
    }

    /**
     * @return
     */
    public String saveFeeType() {
        try {
            FeeType _feeType = financeSetupService.saveFeeType(getFeeType());
            ((List<FeeType>) feeTypeModel.getWrappedData()).add(_feeType);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(FeeTypeBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setFeeType(new FeeType());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(FeeTypeBean.class.getName()).log(Level.INFO, "" + getFeeType(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setFeeType(new FeeType());
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
     * @return the feeType
     */
    public FeeType getFeeType() {
        return feeType;
    }

    /**
     * @param feeType the feeType to set
     */
    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    /**
     * @return the feeTypeModel
     */
    public DataModel<FeeType> getFeeTypeModel() {
        try {
            feeTypeModel = new ListDataModel<>(
                    financeSetupService.findFeeTypes());
        } catch (Exception ex) {
            Logger.getLogger(FeeTypeBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return feeTypeModel;
    }

    /**
     * @param feeTypeModel the feeTypeModel to set
     */
    public void setFeeTypeModel(DataModel<FeeType> feeTypeModel) {
        this.feeTypeModel = feeTypeModel;
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
