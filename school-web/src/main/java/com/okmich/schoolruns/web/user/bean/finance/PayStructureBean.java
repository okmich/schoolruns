/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.schoolruns.common.entity.PayStructure;
import com.okmich.schoolruns.finance.service.FinanceSetupService;
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
public class PayStructureBean extends _BaseBean {

    @ManagedProperty("#{financeSetupService}")
    private FinanceSetupService financeSetupService;
    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private PayStructure payStructure = new PayStructure();
    private DataModel<PayStructure> payStructureModel;

    public PayStructureBean() {
    }

    /**
     * @param financeSetupService the financeSetupService to set
     */
    public void setFinanceSetupService(FinanceSetupService financeSetupService) {
        this.financeSetupService = financeSetupService;
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
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @return
     */
    public String savePayStructure() {
        getPayStructure().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getPayStructure().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        try {
            PayStructure _payStructure = financeSetupService.savePayStructure(getPayStructure());
            List<PayStructure> payStructures = ((List<PayStructure>) payStructureModel.getWrappedData());
            if (!payStructures.contains(_payStructure)) {
                payStructures.add(_payStructure);
            } else {
                payStructures.set(payStructures.indexOf(_payStructure), _payStructure);
            }
            financeSessionBean.getPayStructures().clear();
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(PayStructureBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    /**
     * @return
     */
    public String findPayStructures() {
        doFindPayStructures();
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        if (getPayStructure().getPayStructureId() != null) {
            setPayStructure(financeSetupService.findPayStructure(
                    getPayStructure().getPayStructureId()));
        }
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setPayStructure(new PayStructure());
        setEditMode(null);
        return "";
    }

    /**
     * @return the payStructure
     */
    public PayStructure getPayStructure() {
        if (payStructure == null) {
            payStructure = new PayStructure();
        }
        return payStructure;
    }

    /**
     * @param payStructure the payStructure to set
     */
    public void setPayStructure(PayStructure payStructure) {
        this.payStructure = payStructure;
    }

    /**
     * @return the payStructureModel
     */
    public DataModel<PayStructure> getPayStructureModel() {
        doFindPayStructures();
        return payStructureModel;
    }

    /**
     * @param payStructureModel the payStructureModel to set
     */
    public void setPayStructureModel(DataModel<PayStructure> payStructureModel) {
        this.payStructureModel = payStructureModel;
    }

    /**
     *
     */
    private void doFindPayStructures() {
        try {
            this.payStructureModel = new ListDataModel<>(
                    financeSetupService.findPayStructures(schoolSessionBean.getSchool().getSchoolId()));
        } catch (Exception ex) {
            processException(ex);
        }
    }
}