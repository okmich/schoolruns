/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.SportCategory;
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
public class SportCategoryBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private SportCategory sportCategory = new SportCategory();
    private DataModel<SportCategory> sportCategoryModel;

    public SportCategoryBean() {
    }

    /**
     * @return
     */
    public String saveSportCategory() {
        try {
            SportCategory _sportCategory = commonService.saveSportCategory(getSportCategory());
            ((List<SportCategory>) sportCategoryModel.getWrappedData()).add(_sportCategory);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(SportCategoryBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setSportCategory(new SportCategory());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(SportCategoryBean.class.getName()).log(Level.INFO, "" + getSportCategory(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setSportCategory(new SportCategory());
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
     * @return the sportCategory
     */
    public SportCategory getSportCategory() {
        return sportCategory;
    }

    /**
     * @param sportCategory the sportCategory to set
     */
    public void setSportCategory(SportCategory sportCategory) {
        this.sportCategory = sportCategory;
    }

    /**
     * @return the sportCategoryModel
     */
    public DataModel<SportCategory> getSportCategoryModel() {
        try {
            sportCategoryModel = new ListDataModel<SportCategory>(
                    commonService.findSportCategories());
        } catch (Exception ex) {
            Logger.getLogger(SportCategoryBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return sportCategoryModel;
    }

    /**
     * @param sportCategoryModel the sportCategoryModel to set
     */
    public void setSportCategoryModel(DataModel<SportCategory> sportCategoryModel) {
        this.sportCategoryModel = sportCategoryModel;
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
