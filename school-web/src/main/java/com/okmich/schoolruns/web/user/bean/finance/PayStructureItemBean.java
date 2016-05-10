/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.schoolruns.common.entity.PayStructureItem;
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
public class PayStructureItemBean extends _BaseBean {

    @ManagedProperty("#{financeSetupService}")
    private FinanceSetupService financeSetupService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    private PayStructureItem payStructureItem;
    private DataModel<PayStructureItem> payStructureItemModel;

    /**
     * Creates a new instance of PayStructureItemBean
     */
    public PayStructureItemBean() {
    }

    /**
     * @param financeSetupService the financeSetupService to set
     */
    public void setFinanceSetupService(FinanceSetupService financeSetupService) {
        this.financeSetupService = financeSetupService;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @return
     */
    public String savePayStructureItem() {
        try {
            getPayStructureItem().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getPayStructureItem().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
            PayStructureItem _payStructureItem = financeSetupService.savePayStructureItem(getPayStructureItem());
            List<PayStructureItem> payStructureItems = ((List<PayStructureItem>) getPayStructureItemModel().getWrappedData());
            if (payStructureItems.contains(_payStructureItem)) {
                payStructureItems.remove(_payStructureItem);
            }
            payStructureItems.add(_payStructureItem);
            clearForm();
            return "";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForCreate() {
        setEditMode("CREATE");
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
        setPayStructureItem(null);
        setEditMode(null);
        return "";
    }

    /**
     * @return the payStructureItem
     */
    public PayStructureItem getPayStructureItem() {
        if (this.payStructureItem == null) {
            this.payStructureItem = new PayStructureItem();
        }
        return payStructureItem;
    }

    /**
     * @param payStructureItem the payStructureItem1 to set
     */
    public void setPayStructureItem(PayStructureItem payStructureItem1) {
        this.payStructureItem = payStructureItem1;
    }

    /**
     * @return the payStructureItemModel
     */
    public DataModel<PayStructureItem> getPayStructureItemModel() {
        try {
            payStructureItemModel = new ListDataModel<>(
                    getPayStructureItems());
        } catch (Exception ex) {
            Logger.getLogger(PayStructureItemBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return payStructureItemModel;
    }

    /**
     * @param payStructureItemModel the payStructureItemModel to set
     */
    public void setPayStructureItemModel(DataModel<PayStructureItem> payStructureItemModel) {
        this.setPayStructureItemModel(payStructureItemModel);
    }

    /**
     *
     *
     * @return
     */
    public List<PayStructureItem> getPayStructureItems() {
        return financeSetupService.findPayStructureItems(
                schoolSessionBean.getSchool().getSchoolId());
    }
}