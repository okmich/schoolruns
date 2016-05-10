/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.AssetType;
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
public class AssetTypeBean extends _BaseBean {

    @ManagedProperty("#{financeSetupService}")
    private FinanceSetupService financeSetupService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private AssetType assetType = new AssetType();
    private DataModel<AssetType> assetTypeModel;

    public AssetTypeBean() {
    }

    /**
     * @return
     */
    public String saveAssetType() {
        try {
            AssetType _assetType = financeSetupService.saveAssetType(getAssetType());
            ((List<AssetType>) assetTypeModel.getWrappedData()).add(_assetType);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(AssetTypeBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setAssetType(new AssetType());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(AssetTypeBean.class.getName()).log(Level.INFO, "" + getAssetType(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setAssetType(new AssetType());
        setEditMode(null);
        return "";
    }

    /**
     * @param financeSetupService the financeSetupService to set
     */
    public void setFinanceSetupService(FinanceSetupService financeSetupService) {
        this.financeSetupService = financeSetupService;
    }

    /**
     * @return the assetType
     */
    public AssetType getAssetType() {
        return assetType;
    }

    /**
     * @param assetType the assetType to set
     */
    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    /**
     * @return the assetTypeModel
     */
    public DataModel<AssetType> getAssetTypeModel() {        
        try {
            assetTypeModel = new ListDataModel<>(financeSetupService.findAssetTypes());
        } catch (Exception ex) {
            Logger.getLogger(AssetTypeBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return assetTypeModel;
    }

    /**
     * @param assetTypeModel the assetTypeModel to set
     */
    public void setAssetTypeModel(DataModel<AssetType> assetTypeModel) {
        this.assetTypeModel = assetTypeModel;
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
