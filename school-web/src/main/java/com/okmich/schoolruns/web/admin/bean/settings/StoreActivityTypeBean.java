/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.StoreActivityType;
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
public class StoreActivityTypeBean extends _BaseBean {

    @ManagedProperty("#{storeInventoryService}")
    private StoreInventoryService storeInventoryService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private StoreActivityType storeActivityType = new StoreActivityType();
    private DataModel<StoreActivityType> storeActivityTypeModel;

    public StoreActivityTypeBean() {
    }

    /**
     * @return
     */
    public String saveStoreActivityType() {
        try {
            StoreActivityType _storeActivityType = storeInventoryService.saveStoreActivityType(getStoreActivityType());
            ((List<StoreActivityType>) storeActivityTypeModel.getWrappedData()).add(_storeActivityType);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(StoreActivityTypeBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setStoreActivityType(new StoreActivityType());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(StoreActivityTypeBean.class.getName()).log(Level.INFO, "" + getStoreActivityType(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setStoreActivityType(new StoreActivityType());
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
     * @return the storeActivityType
     */
    public StoreActivityType getStoreActivityType() {
        return storeActivityType;
    }

    /**
     * @param storeActivityType the storeActivityType to set
     */
    public void setStoreActivityType(StoreActivityType storeActivityType) {
        this.storeActivityType = storeActivityType;
    }

    /**
     * @return the storeActivityTypeModel
     */
    public DataModel<StoreActivityType> getStoreActivityTypeModel() {
        try {
            storeActivityTypeModel = new ListDataModel<>(
                    storeInventoryService.findStoreActivityTypes());
        } catch (Exception ex) {
            Logger.getLogger(StoreActivityTypeBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return storeActivityTypeModel;
    }

    /**
     * @param storeActivityTypeModel the storeActivityTypeModel to set
     */
    public void setStoreActivityTypeModel(DataModel<StoreActivityType> storeActivityTypeModel) {
        this.storeActivityTypeModel = storeActivityTypeModel;
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
