/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.common.entity.criteria.WCString;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Store;
import com.okmich.schoolruns.common.entity.StoreActivity;
import com.okmich.schoolruns.common.entity.StoreItem;
import com.okmich.schoolruns.finance.service.StoreInventoryService;
import com.okmich.schoolruns.finance.service.repo.StoreActivityQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.StoreItemQueryCriteria;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class StoreBean extends _BaseBean {

    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{storeInventoryService}")
    private StoreInventoryService storeInventoryService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private Store store;
    private StoreItem storeItem;
    private StoreActivity storeActivity;
    private String itemTypeId;
    private String itemName;
    private Integer storeItemId;
    private String storeActivityTypeCode;
    private String url;
    private DataModel<Store> storeModel;

    /**
     * Creates a new instance of StoreBean
     */
    public StoreBean() {
    }

    /**
     * @param financeSessionBean the financeSessionBean to set
     */
    public void setFinanceSessionBean(FinanceSessionBean financeSessionBean) {
        this.financeSessionBean = financeSessionBean;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @param storeInventoryService the storeInventoryService to set
     */
    public void setStoreInventoryService(StoreInventoryService storeInventoryService) {
        this.storeInventoryService = storeInventoryService;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     *
     * @return
     */
    public String createStore() {
        getStore().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getStore().setSchool(schoolSessionBean.getSchool());
        try {
            Store _store = storeInventoryService.createStore(getStore());
            financeSessionBean.setStore(_store);
            return "/schooluser/finance/storedetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     *
     * @return
     */
    public String findStores() {
        try {
            List<Store> stores = storeInventoryService.findSchoolStores(
                    schoolSessionBean.getSchool().getSchoolId());
            financeSessionBean.setStoreDataModel(new ListDataModel(stores));
        } catch (Exception ex) {
            processException(ex);
        }
        return "/schooluser/finance/storesearch";
    }

    /**
     *
     *
     * @return
     */
    public String findStore() {
        if (getStore().getStoreId() != null) { //ensuring selection
            financeSessionBean.setStore(getStore());
            return "/schooluser/finance/storedetails";
        }
        //nothing was selected
        return "";
    }

    /**
     *
     * @return
     */
    public String saveStore() {
        setStore(financeSessionBean.getStore());
        getStore().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            storeInventoryService.saveStore(getStore());
            return retToSearch();
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String createStoreItem() {
        setStoreItem(financeSessionBean.getStoreItem());
        getStoreItem().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getStoreItem().setStore(financeSessionBean.getStore());
        try {
            StoreItem _storeItem = storeInventoryService.createStoreItem(getStoreItem());
            //add this to the existing list of storeItems
            financeSessionBean.setStoreItem(_storeItem);
            return "/schooluser/finance/storedetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findStoreItems() {
        StoreItemQueryCriteria criteria = buildStoreItemQueryCriteria();
        try {
            List<StoreItem> storeItems = storeInventoryService.findStoreItems(criteria);
            financeSessionBean.setStoreItemDataModel(new ListDataModel(storeItems));
        } catch (Exception ex) {
            processException(ex);
        }
        return "/schooluser/finance/storeitemsearch";
    }

    /**
     *
     * @return
     */
    public String findStoreItem() {
        if (getStoreItem().getStoreItemId() != null) { //ensuring selection
            financeSessionBean.setStoreItem(getStoreItem());
            String editMode = FacesUtil.getRequestParameter("editMode");
            //set edit mode
            financeSessionBean.setEditMode(editMode);
            return "/schooluser/finance/storeitemdetails";
        }
        //nothing was selected
        return "";
    }

    public String saveStoreItem() {
        getStoreItem().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            storeInventoryService.saveStoreItem(getStoreItem());
            return "/schooluser/finance/storedetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String preStoreItemAction() {
        setStoreItem(financeSessionBean.getStoreItem());
        getStoreItem().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        return "/schooluser/finance/storeevent";
    }

    public String issueStoreItem() {
        getStoreItem().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            storeInventoryService.issueStoreItem(getStoreItem());
            return "/schooluser/finance/storedetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String lendStoreItem() {
        getStoreItem().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            storeInventoryService.lendStoreItem(getStoreItem());
            return "/schooluser/finance/storedetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String restockStoreItem() {
        getStoreItem().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            storeInventoryService.returnStoreItem(getStoreItem());
            return "/schooluser/finance/storedetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String returnStoreItem() {
        getStoreItem().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            storeInventoryService.returnStoreItem(getStoreItem());
            return "/schooluser/finance/storedetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String deleteStoreItem() {
        getStoreItem().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getStoreItem().setStore(financeSessionBean.getStore());
        try {
            storeInventoryService.removeStoreItem(getStoreItem());
            //add this to the existing list of storeItems
            ((List<StoreItem>) financeSessionBean.getStoreItemDataModel().getWrappedData())
                    .add(getStoreItem());
            return "/schooluser/finance/storedetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findStoreActivities() {
        if (financeSessionBean.getStoreItem() != null) {
            this.storeItemId = financeSessionBean.getStoreItem().getStoreItemId();
        }
        try {
            financeSessionBean.setStoreActivityModel(new ListDataModel<>(
                    storeInventoryService.findStoreActivities(
                    buildStoreItemActQueryCriteria())));
            return "/schooluser/finance/storeactivitysearch";
        } catch (Exception e) {
            processException(e);
            return null;
        }
    }

    public String retToSearch() {
        financeSessionBean.setEditMode(null);
        financeSessionBean.setStore(null);
        return "/schooluser/finance/storesearch";
    }

    public String retToItemSearch() {
        financeSessionBean.setEditMode(null);
        financeSessionBean.setStoreItem(null);
        return "/schooluser/finance/storeitemsearch";
    }

    public String retFromStoreActivity() {
        financeSessionBean.setStoreActivityModel(null);
        financeSessionBean.setEditMode(null);
        if (CREATE.equals(financeSessionBean.getEditMode())) {
            return "/schooluser/finance/storedetails";
        } else {
            return "/schooluser/finance/storeitemdetails";
        }
    }

    /**
     * @return the store
     */
    public Store getStore() {
        if (this.store == null) {
            this.store = new Store();
        }
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * @return the storeItem
     */
    public StoreItem getStoreItem() {
        if (this.storeItem == null) {
            this.storeItem = new StoreItem();
        }
        return storeItem;
    }

    /**
     * @param storeItem the storeItem to set
     */
    public void setStoreItem(StoreItem storeItem) {
        this.storeItem = storeItem;
    }

    /**
     * @return the storeActivity
     */
    public StoreActivity getStoreActivity() {
        return storeActivity;
    }

    /**
     * @param storeActivity the storeActivity to set
     */
    public void setStoreActivity(StoreActivity storeActivity) {
        this.storeActivity = storeActivity;
    }

    /**
     * @return the itemTypeId
     */
    public String getItemTypeId() {
        return itemTypeId;
    }

    /**
     * @param itemTypeId the itemTypeId to set
     */
    public void setItemTypeId(String itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the storeItemId
     */
    public Integer getStoreItemId() {
        return storeItemId;
    }

    /**
     * @param storeItemId the storeItemId to set
     */
    public void setStoreItemId(Integer storeItemId) {
        this.storeItemId = storeItemId;
    }

    /**
     * @return the storeActivityTypeCode
     */
    public String getStoreActivityTypeCode() {
        return storeActivityTypeCode;
    }

    /**
     * @param storeActivityTypeCode the storeActivityTypeCode to set
     */
    public void setStoreActivityTypeCode(String storeActivityTypeCode) {
        this.storeActivityTypeCode = storeActivityTypeCode;
    }

    /**
     * @return the storeModel
     */
    public DataModel<Store> getStoreModel() {
        storeModel = new ListDataModel<>(
                storeInventoryService.findSchoolStores(
                schoolSessionBean.getSchool().getSchoolId()));
        return storeModel;
    }

    /**
     * @param storeModel the storeModel to set
     */
    public void setStoreModel(DataModel<Store> storeModel) {
        this.storeModel = storeModel;
    }

    private StoreItemQueryCriteria buildStoreItemQueryCriteria() {
        StoreItemQueryCriteria criteria = new StoreItemQueryCriteria();
        criteria.setStoreId(financeSessionBean.getStore().getStoreId());

        if (this.itemName != null && !this.itemName.trim().isEmpty()) {
            criteria.setName(this.itemName, WCString.LIKE);
        }
        if (this.itemTypeId != null && !this.itemTypeId.isEmpty()) {
            criteria.setStoreItemTypeCode(this.itemTypeId);
        }
        if (this.getStatus() != null && !this.getStatus().isEmpty()) {
            criteria.setStatus(this.getStatus());
        } else {
            criteria.setStatus(CommonConstants.STATUS_ACTIVE);
        }

        return criteria;
    }

    /**
     *
     * @return
     */
    private StoreActivityQueryCriteria buildStoreItemActQueryCriteria() {
        StoreActivityQueryCriteria criteria = new StoreActivityQueryCriteria();
        criteria.setStoreId(financeSessionBean.getStore().getStoreId());

        if (this.storeItemId != null && this.storeItemId != 0) {
            criteria.setStoreItemId(this.storeItemId);
        }
        if (this.storeActivityTypeCode != null && !this.storeActivityTypeCode.isEmpty()) {
            criteria.setStoreActivityTypeCode(this.storeActivityTypeCode);
        }
        if (this.itemName != null && !this.itemName.trim().isEmpty()) {
            criteria.setItemName(this.itemName, WCString.LIKE);
        }

        return criteria;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}