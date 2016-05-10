/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.CtrlParameter;
import com.okmich.schoolruns.common.service.CommonService;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
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
public class CtrlParameterBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private CtrlParameter ctrlParameter = new CtrlParameter();
    private DataModel<CtrlParameter> ctrlParameterModel;

    public CtrlParameterBean() {
    }

    /**
     * @return
     */
    public String saveCtrlParameter() {
        try {
            getCtrlParameter().setModifiedBy(getUserLoginSessionBean().getUserLogin().getUsername());
            CtrlParameter _ctrlParameter = getCommonService().saveCtrlParameter(getCtrlParameter());
            ((List<CtrlParameter>) ctrlParameterModel.getWrappedData()).add(_ctrlParameter);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(CountryBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(CountryBean.class.getName()).log(Level.INFO, "" + getCtrlParameter(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setCtrlParameter(new CtrlParameter());
        setEditMode(null);
        return "";
    }

    /**
     * @return the commonService
     */
    public CommonService getCommonService() {
        return commonService;
    }

    /**
     * @param commonService the commonService to set
     */
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
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

    /**
     * @return the ctrlParameter
     */
    public CtrlParameter getCtrlParameter() {
        return ctrlParameter;
    }

    /**
     * @param ctrlParameter the ctrlParameter to set
     */
    public void setCtrlParameter(CtrlParameter ctrlParameter) {
        this.ctrlParameter = ctrlParameter;
    }

    /**
     * @return the ctrlParameterModel
     */
    public DataModel<CtrlParameter> getCtrlParameterModel() {
        try {
            ctrlParameterModel =
                    new ListDataModel<CtrlParameter>(commonService.findCtrlParameters());
        } catch (Exception ex) {
            Logger.getLogger(CountryBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return ctrlParameterModel;
    }

    /**
     * @param ctrlParameterModel the ctrlParameterModel to set
     */
    public void setCtrlParameterModel(DataModel<CtrlParameter> ctrlParameterModel) {
        this.ctrlParameterModel = ctrlParameterModel;
    }
}
