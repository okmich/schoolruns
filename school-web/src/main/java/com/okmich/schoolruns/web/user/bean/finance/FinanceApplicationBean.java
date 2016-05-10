/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.schoolruns.common.entity.AccountType;
import com.okmich.schoolruns.common.entity.AssetType;
import com.okmich.schoolruns.common.entity.FeeType;
import com.okmich.schoolruns.common.entity.LiabilityType;
import com.okmich.schoolruns.common.entity.StoreActivityType;
import com.okmich.schoolruns.common.entity.StoreItemType;
import com.okmich.schoolruns.common.entity.TxnCategory;
import com.okmich.schoolruns.finance.service.FinanceSetupService;
import com.okmich.schoolruns.finance.service.StoreInventoryService;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Michael
 */
@ManagedBean
@ApplicationScoped
public class FinanceApplicationBean extends _BaseBean {

    @ManagedProperty("#{financeSetupService}")
    private FinanceSetupService financeSetupService;
    @ManagedProperty("#{storeInventoryService}")
    private StoreInventoryService storeInventoryService;
    private List<AccountType> accountTypes;
    private List<AssetType> assetTypes;
    private List<FeeType> feeTypes;
    private List<LiabilityType> liabilityTypes;
    private List<StoreItemType> storeItemTypes;
    private List<StoreActivityType> storeActivityTypes;
    private List<TxnCategory> txnCategories;

    /**
     * Creates a new instance of FinanceApplicationBean
     */
    public FinanceApplicationBean() {
    }

    @PostConstruct
    public void init() {
        reinitialiseApplicationList();
    }

    /**
     * @param financeSetupService the financeSetupService to set
     */
    public void setFinanceSetupService(FinanceSetupService financeSetupService) {
        this.financeSetupService = financeSetupService;
    }

    /**
     * @param storeInventoryService the storeInventoryService to set
     */
    public void setStoreInventoryService(StoreInventoryService storeInventoryService) {
        this.storeInventoryService = storeInventoryService;
    }

    /**
     * reinitialiseApplicationList
     *
     * @return
     */
    public String reinitialiseApplicationList() {
        try {
            //
            this.accountTypes = financeSetupService.findAccountTypes();
            this.assetTypes = financeSetupService.findAssetTypes();
            this.feeTypes = financeSetupService.findFeeTypes();
            this.liabilityTypes = financeSetupService.findLiabilityTypes();
            this.storeActivityTypes = storeInventoryService.findStoreActivityTypes();
            this.storeItemTypes = storeInventoryService.findStoreItemTypes();
            this.txnCategories = financeSetupService.findTxnCategories();
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * @return the accountTypes
     */
    public List<AccountType> getAccountTypes() {
        return accountTypes;
    }

    /**
     * @return the assetTypes
     */
    public List<AssetType> getAssetTypes() {
        return assetTypes;
    }

    /**
     * @return the feeTypes
     */
    public List<FeeType> getFeeTypes() {
        return feeTypes;
    }

    /**
     * @return the liabilityTypes
     */
    public List<LiabilityType> getLiabilityTypes() {
        return liabilityTypes;
    }

    /**
     * @return the storeActivityTypes
     */
    public List<StoreActivityType> getStoreActivityTypes() {
        return storeActivityTypes;
    }

    /**
     * @return the storeItemTypes
     */
    public List<StoreItemType> getStoreItemTypes() {
        return storeItemTypes;
    }

    /**
     * @return the txnCategories
     */
    public List<TxnCategory> getTxnCategories() {
        return txnCategories;
    }
}
