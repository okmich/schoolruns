/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.TxnCategory;
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
public class TxnCategoryBean extends _BaseBean {

    @ManagedProperty("#{financeSetupService}")
    private FinanceSetupService financeSetupService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private TxnCategory txnCategory = new TxnCategory();
    private DataModel<TxnCategory> txnCategoryModel;

    public TxnCategoryBean() {
    }

    /**
     * @returns
     */
    public String saveTxnCategory() {
        try {
            TxnCategory _txnCategory = financeSetupService.saveTxnCategory(getTxnCategory());
            ((List<TxnCategory>) txnCategoryModel.getWrappedData()).add(_txnCategory);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(TxnCategoryBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setTxnCategory(new TxnCategory());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(TxnCategoryBean.class.getName()).log(Level.INFO, "" + getTxnCategory(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setTxnCategory(new TxnCategory());
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
     * @return the txnCategory
     */
    public TxnCategory getTxnCategory() {
        return txnCategory;
    }

    /**
     * @param txnCategory the txnCategory to set
     */
    public void setTxnCategory(TxnCategory txnCategory) {
        this.txnCategory = txnCategory;
    }

    /**
     * @return the txnCategoryModel
     */
    public DataModel<TxnCategory> getTxnCategoryModel() {
        try {
            txnCategoryModel = new ListDataModel<>(
                    financeSetupService.findTxnCategories());
        } catch (Exception ex) {
            Logger.getLogger(TxnCategoryBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return txnCategoryModel;
    }

    /**
     * @param txnCategoryModel the txnCategoryModel to set
     */
    public void setTxnCategoryModel(DataModel<TxnCategory> txnCategoryModel) {
        this.txnCategoryModel = txnCategoryModel;
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
