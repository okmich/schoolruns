/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.ClubType;
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
public class ClubTypeBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private ClubType clubType = new ClubType();
    private DataModel<ClubType> clubTypeModel;

    public ClubTypeBean() {
    }

    /**
     * @return
     */
    public String saveClubType() {
        try {
            ClubType _clubType = commonService.saveClubType(getClubType());
            ((List<ClubType>) clubTypeModel.getWrappedData()).add(_clubType);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(ClubTypeBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setClubType(new ClubType());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(ClubTypeBean.class.getName()).log(Level.INFO, "" + getClubType(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setClubType(new ClubType());
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
     * @return the clubType
     */
    public ClubType getClubType() {
        return clubType;
    }

    /**
     * @param clubType the clubType to set
     */
    public void setClubType(ClubType clubType) {
        this.clubType = clubType;
    }

    /**
     * @return the clubTypeModel
     */
    public DataModel<ClubType> getClubTypeModel() {
        try {
            clubTypeModel = new ListDataModel<ClubType>(
                    commonService.findClubTypes());
        } catch (Exception ex) {
            Logger.getLogger(ClubTypeBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return clubTypeModel;
    }

    /**
     * @param clubTypeModel the clubTypeModel to set
     */
    public void setClubTypeModel(DataModel<ClubType> clubTypeModel) {
        this.clubTypeModel = clubTypeModel;
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
