/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.EmployeeCategory;
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
public class EmployeeCategoryBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private EmployeeCategory employeeCategory = new EmployeeCategory();
    private DataModel<EmployeeCategory> employeeCategoryModel;

    public EmployeeCategoryBean() {
    }

    /**
     * @return
     */
    public String saveEmployeeCategory() {
        try {
            getEmployeeCategory().setModifiedBy(getUserLoginSessionBean().getUserLogin().getUsername());
            EmployeeCategory _employeeCategory = commonService.saveEmployeeCategory(getEmployeeCategory());
            ((List<EmployeeCategory>) employeeCategoryModel.getWrappedData()).add(_employeeCategory);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(EmployeeCategoryBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setEmployeeCategory(new EmployeeCategory());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(EmployeeCategoryBean.class.getName()).log(Level.INFO, "" + getEmployeeCategory(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setEmployeeCategory(new EmployeeCategory());
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
     * @return the employeeCategory
     */
    public EmployeeCategory getEmployeeCategory() {
        return employeeCategory;
    }

    /**
     * @param employeeCategory the employeeCategory to set
     */
    public void setEmployeeCategory(EmployeeCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    /**
     * @return the employeeCategoryModel
     */
    public DataModel<EmployeeCategory> getEmployeeCategoryModel() {
        try {
            employeeCategoryModel = new ListDataModel<EmployeeCategory>(
                    commonService.findEmployeeCategories());
        } catch (Exception ex) {
            Logger.getLogger(EmployeeCategoryBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return employeeCategoryModel;
    }

    /**
     * @param employeeCategoryModel the employeeCategoryModel to set
     */
    public void setEmployeeCategoryModel(DataModel<EmployeeCategory> employeeCategoryModel) {
        this.employeeCategoryModel = employeeCategoryModel;
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
