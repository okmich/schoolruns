/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.AllocationType;
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
public class AllocationTypeBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private AllocationType allocationType = new AllocationType();
    private DataModel<AllocationType> allocationTypeModel;

    public AllocationTypeBean() {
    }

    /**
     * @return
     */
    public String saveAllocationType() {
        try {
            getAllocationType().setModifiedBy(getUserLoginSessionBean().getUserLogin().getUsername());
            AllocationType _allocationType = commonService.saveAllocationType(getAllocationType());
            ((List<AllocationType>) allocationTypeModel.getWrappedData()).add(_allocationType);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(AllocationTypeBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setAllocationType(new AllocationType());
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
        setAllocationType(new AllocationType());
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
     * @return the allocationType
     */
    public AllocationType getAllocationType() {
        return allocationType;
    }

    /**
     * @param allocationType the allocationType to set
     */
    public void setAllocationType(AllocationType allocationType) {
        this.allocationType = allocationType;
    }

    /**
     * @return the allocationTypeModel
     */
    public DataModel<AllocationType> getAllocationTypeModel() {
        try {
            allocationTypeModel = new ListDataModel<AllocationType>(
                    commonService.findAllocationTypes());
        } catch (Exception ex) {
            Logger.getLogger(AllocationTypeBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return allocationTypeModel;
    }

    /**
     * @param allocationTypeModel the allocationTypeModel to set
     */
    public void setAllocationTypeModel(DataModel<AllocationType> allocationTypeModel) {
        this.allocationTypeModel = allocationTypeModel;
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
