/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.common.entity.criteria.WCString;
import com.okmich.schoolruns.common.entity.TxnType;
import com.okmich.schoolruns.finance.service.FinanceSetupService;
import com.okmich.schoolruns.finance.service.repo.TxnTypeQueryCriteria;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
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
public class TxnTypeBean extends _BaseBean {

    @ManagedProperty("#{financeSetupService}")
    private FinanceSetupService financeSetupService;
    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private TxnType txnType = new TxnType();
    private String txnTypeCode;
    private String txnCategoryCode;
    private DataModel<TxnType> txnTypeDataModel;

    public TxnTypeBean() {
    }

    /**
     * @param financeSetupService the financeSetupService to set
     */
    public void setFinanceSetupService(FinanceSetupService financeSetupService) {
        this.financeSetupService = financeSetupService;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @param financeSessionBean the financeSessionBean to set
     */
    public void setFinanceSessionBean(FinanceSessionBean financeSessionBean1) {
        this.financeSessionBean = financeSessionBean1;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @return
     */
    public String saveTxnType() {
        getTxnType().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getTxnType().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        try {
            TxnType _txnType = financeSetupService.saveTxnType(getTxnType());
            List<TxnType> txnTypes = ((List<TxnType>) txnTypeDataModel.getWrappedData());
            if (!txnTypes.contains(_txnType)) {
                txnTypes.add(_txnType);
            } else {
                txnTypes.set(txnTypes.indexOf(_txnType), _txnType);
            }
            financeSessionBean.getTxnTypes().clear();
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(TxnTypeBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    /**
     * @return
     */
    public String findTxnTypes() {
        doFindTxnTypes();
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
        setTxnType(new TxnType());
        setEditMode(null);
        return "";
    }

    /**
     * @return the txnType
     */
    public TxnType getTxnType() {
        if (txnType == null) {
            txnType = new TxnType();
        }
        return txnType;
    }

    /**
     * @param txnType the txnType to set
     */
    public void setTxnType(TxnType txnType) {
        this.txnType = txnType;
    }

    /**
     * @return the txnTypeCode
     */
    public String getTxnTypeCode() {
        return txnTypeCode;
    }

    /**
     * @param txnTypeCode the txnTypeCode to set
     */
    public void setTxnTypeCode(String txnTypeCode) {
        this.txnTypeCode = txnTypeCode;
    }

    /**
     * @return the txnCategoryCode
     */
    public String getTxnCategoryCode() {
        return txnCategoryCode;
    }

    /**
     * @param txnCategoryCode the txnCategoryCode to set
     */
    public void setTxnCategoryCode(String txnCategoryCode) {
        this.txnCategoryCode = txnCategoryCode;
    }

    /**
     * @return the txnTypeDataModel
     */
    public DataModel<TxnType> getTxnTypeDataModel() {
        if (txnTypeDataModel == null) {
            doFindTxnTypes();
        }
        return txnTypeDataModel;
    }

    /**
     * @param txnTypeDataModel the txnTypeDataModel to set
     */
    public void setTxnTypeDataModel(DataModel<TxnType> txnTypeDataModel) {
        this.txnTypeDataModel = txnTypeDataModel;
    }

    /**
     *
     */
    private void doFindTxnTypes() {
        TxnTypeQueryCriteria criteria = new TxnTypeQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        if (this.txnTypeCode != null && !this.txnTypeCode.trim().isEmpty()) {
            criteria.setTxnTypeCode(this.txnTypeCode, WCString.LIKE);
        }
        if (this.txnCategoryCode != null && !this.txnCategoryCode.isEmpty()) {
            criteria.setTxnCategoryCode(txnCategoryCode);
        }
        try {
            this.txnTypeDataModel = new ListDataModel<>(
                    financeSetupService.findTxnTypes(criteria));
        } catch (Exception ex) {
            processException(ex);
        }
    }
}