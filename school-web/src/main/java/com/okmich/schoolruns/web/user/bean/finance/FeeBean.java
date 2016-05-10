/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.schoolruns.common.entity.Fee;
import com.okmich.schoolruns.finance.service.FinanceSetupService;
import com.okmich.schoolruns.finance.service.repo.FeeQueryCriteria;
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
public class FeeBean extends _BaseBean {

    @ManagedProperty("#{financeSetupService}")
    private FinanceSetupService financeSetupService;
    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private Fee fee = new Fee();
    private String feeCategory;
    private DataModel<Fee> feeModel;

    public FeeBean() {
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
    public String saveFee() {
        getFee().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getFee().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        try {
            Fee _fee = financeSetupService.saveFee(getFee());
            List<Fee> fees = ((List<Fee>) feeModel.getWrappedData());
            if (!fees.contains(_fee)) {
                fees.add(_fee);
            } else {
                fees.set(fees.indexOf(_fee), _fee);
            }
            financeSessionBean.getFees().clear();
            clearForm();
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * @return
     */
    public String findFees() {
        doFindFees();
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
        setFee(new Fee());
        setEditMode(null);
        return "";
    }

    /**
     * @return the fee
     */
    public Fee getFee() {
        if (fee == null) {
            fee = new Fee();
        }
        return fee;
    }

    /**
     * @param fee the fee to set
     */
    public void setFee(Fee fee) {
        this.fee = fee;
    }

    /**
     * @return the feeCategory
     */
    public String getFeeCategory() {
        return feeCategory;
    }

    /**
     * @param feeCategory the feeCategory to set
     */
    public void setFeeCategory(String feeCategory) {
        this.feeCategory = feeCategory;
    }

    /**
     * @return the feeModel
     */
    public DataModel<Fee> getFeeModel() {
        doFindFees();
        return feeModel;
    }

    /**
     * @param feeModel the feeModel to set
     */
    public void setFeeModel(DataModel<Fee> feeModel) {
        this.feeModel = feeModel;
    }

    /**
     *
     */
    private void doFindFees() {
        FeeQueryCriteria criteria = new FeeQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        if (this.feeCategory != null && !this.feeCategory.isEmpty()) {
            criteria.setFeeCategory(feeCategory);
        }
        try {
            this.feeModel = new ListDataModel<>(
                    financeSetupService.findFees(criteria));
        } catch (Exception ex) {
            processException(ex);
        }
    }
}