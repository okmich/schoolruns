/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.AssignmentType;
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
public class AssignmentTypeBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private AssignmentType assignmentType = new AssignmentType();
    private DataModel<AssignmentType> assignmentTypeModel;

    public AssignmentTypeBean() {
    }

    /**
     * @return
     */
    public String saveAssignmentType() {
        try {
            AssignmentType _assignmentType = commonService.saveAssignmentType(getAssignmentType());
            ((List<AssignmentType>) assignmentTypeModel.getWrappedData()).add(_assignmentType);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(AssignmentTypeBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setAssignmentType(new AssignmentType());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(AssignmentTypeBean.class.getName()).log(Level.INFO, "" + getAssignmentType(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setAssignmentType(new AssignmentType());
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
     * @return the assignmentType
     */
    public AssignmentType getAssignmentType() {
        return assignmentType;
    }

    /**
     * @param assignmentType the assignmentType to set
     */
    public void setAssignmentType(AssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }

    /**
     * @return the assignmentTypeModel
     */
    public DataModel<AssignmentType> getAssignmentTypeModel() {
        try {
            assignmentTypeModel = new ListDataModel<AssignmentType>(
                    commonService.findAssignmentTypes());
        } catch (Exception ex) {
            Logger.getLogger(AssignmentTypeBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return assignmentTypeModel;
    }

    /**
     * @param assignmentTypeModel the assignmentTypeModel to set
     */
    public void setAssignmentTypeModel(DataModel<AssignmentType> assignmentTypeModel) {
        this.assignmentTypeModel = assignmentTypeModel;
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
