/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.StoreItemType;
import com.okmich.schoolruns.finance.service.StoreInventoryService;
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
public class StoreItemTypeBean extends _BaseBean {

    @ManagedProperty("#{storeInventoryService}")
    private StoreInventoryService storeInventoryService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private StoreItemType storeItemType = new StoreItemType();
    private DataModel<StoreItemType> storeItemTypeModel;

    public StoreItemTypeBean() {
    }

    /**
     * @return
     */
    public String saveStoreItemType() {
        try {
            StoreItemType _storeItemType = storeInventoryService.saveStoreItemType(getStoreItemType());
            ((List<StoreItemType>) storeItemTypeModel.getWrappedData()).add(_storeItemType);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(StoreItemTypeBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setStoreItemType(new StoreItemType());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(StoreItemTypeBean.class.getName()).log(Level.INFO, "" + getStoreItemType(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setStoreItemType(new StoreItemType());
        setEditMode(null);
        return "";
    }

    /**
     * @return the storeInventoryService
     */
    public StoreInventoryService getStoreInventoryService() {
        return storeInventoryService;
    }

    /**
     * @param storeInventoryService the storeInventoryService to set
     */
    public void setStoreInventoryService(StoreInventoryService storeInventoryService) {
        this.storeInventoryService = storeInventoryService;
    }

    /**
     * @return the storeItemType
     */
    public StoreItemType getStoreItemType() {
        return storeItemType;
    }

    /**
     * @param storeItemType the storeItemType to set
     */
    public void setStoreItemType(StoreItemType storeItemType) {
        this.storeItemType = storeItemType;
    }

    /**
     * @return the storeItemTypeModel
     */
    public DataModel<StoreItemType> getStoreItemTypeModel() {
        try {
            storeItemTypeModel = new ListDataModel<>(
                    storeInventoryService.findStoreItemTypes());
        } catch (Exception ex) {
            Logger.getLogger(StoreItemTypeBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return storeItemTypeModel;
    }

    /**
     * @param storeItemTypeModel the storeItemTypeModel to set
     */
    public void setStoreItemTypeModel(DataModel<StoreItemType> storeItemTypeModel) {
        this.storeItemTypeModel = storeItemTypeModel;
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
