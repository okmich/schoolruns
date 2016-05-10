/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.EmployeePosition;
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
public class EmployeePositionBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private EmployeePosition employeePosition = new EmployeePosition();
    private DataModel<EmployeePosition> employeePositionModel;

    public EmployeePositionBean() {
    }

    /**
     * @return
     */
    public String saveEmployeePosition() {
        try {
            getEmployeePosition().setModifiedBy(getUserLoginSessionBean().getUserLogin().getUsername());
            EmployeePosition _employeePosition = commonService.saveEmployeePosition(getEmployeePosition());
            ((List<EmployeePosition>) employeePositionModel.getWrappedData()).add(_employeePosition);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(EmployeePositionBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setEmployeePosition(new EmployeePosition());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(EmployeePositionBean.class.getName()).log(Level.INFO, "" + getEmployeePosition(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setEmployeePosition(new EmployeePosition());
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
     * @return the employeePosition
     */
    public EmployeePosition getEmployeePosition() {
        return employeePosition;
    }

    /**
     * @param employeePosition the employeePosition to set
     */
    public void setEmployeePosition(EmployeePosition employeePosition) {
        this.employeePosition = employeePosition;
    }

    /**
     * @return the employeePositionModel
     */
    public DataModel<EmployeePosition> getEmployeePositionModel() {
        try {
            employeePositionModel = new ListDataModel<EmployeePosition>(
                    commonService.findEmployeePositions());
        } catch (Exception ex) {
            Logger.getLogger(EmployeePositionBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return employeePositionModel;
    }

    /**
     * @param employeePositionModel the employeePositionModel to set
     */
    public void setEmployeePositionModel(DataModel<EmployeePosition> employeePositionModel) {
        this.employeePositionModel = employeePositionModel;
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
