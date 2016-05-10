/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.EmployeeType;
import com.okmich.schoolruns.common.service.CommonService;
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
public class EmployeeTypeBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private EmployeeType employeeType = new EmployeeType();
    private DataModel<EmployeeType> employeeTypeModel;

    public EmployeeTypeBean() {
    }

    /**
     * @return
     */
    public String saveEmployeeType() {
        try {
            getEmployeeType().setModifiedBy(getUserLoginSessionBean().getUserLogin().getUsername());
            EmployeeType _employeeType = commonService.saveEmployeeType(getEmployeeType());
            ((List<EmployeeType>) employeeTypeModel.getWrappedData()).add(_employeeType);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(EmployeeTypeBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setEmployeeType(new EmployeeType());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(EmployeeTypeBean.class.getName()).log(Level.INFO, "" + getEmployeeType(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setEmployeeType(new EmployeeType());
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
     * @return the employeeType
     */
    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    /**
     * @param employeeType the employeeType to set
     */
    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    /**
     * @return the employeeTypeModel
     */
    public DataModel<EmployeeType> getEmployeeTypeModel() {
        try {
            employeeTypeModel = new ListDataModel<EmployeeType>(
                    commonService.findEmployeeTypes());
        } catch (Exception ex) {
            Logger.getLogger(EmployeeTypeBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return employeeTypeModel;
    }

    /**
     * @param employeeTypeModel the employeeTypeModel to set
     */
    public void setEmployeeTypeModel(DataModel<EmployeeType> employeeTypeModel) {
        this.employeeTypeModel = employeeTypeModel;
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
