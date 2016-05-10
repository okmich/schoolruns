/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.GradeLevel;
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
public class GradeLevelBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private GradeLevel gradeLevel = new GradeLevel();
    private DataModel<GradeLevel> gradeLevelModel;

    public GradeLevelBean() {
    }

    /**
     * @return
     */
    public String saveGradeLevel() {
        try {
            getGradeLevel().setModifiedBy(getUserLoginSessionBean().getUserLogin().getUsername());
            GradeLevel _gradeLevel = commonService.saveGradeLevel(getGradeLevel());
            ((List<GradeLevel>) gradeLevelModel.getWrappedData()).add(_gradeLevel);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(GradeLevelBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setGradeLevel(new GradeLevel());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(GradeLevelBean.class.getName()).log(Level.INFO, "" + getGradeLevel(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setGradeLevel(new GradeLevel());
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
     * @return the gradeLevel
     */
    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }

    /**
     * @param gradeLevel the gradeLevel to set
     */
    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    /**
     * @return the gradeLevelModel
     */
    public DataModel<GradeLevel> getGradeLevelModel() {
        try {
            gradeLevelModel = new ListDataModel<GradeLevel>(
                    commonService.findGradeLevels());
        } catch (Exception ex) {
            Logger.getLogger(GradeLevelBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return gradeLevelModel;
    }

    /**
     * @param gradeLevelModel the gradeLevelModel to set
     */
    public void setGradeLevelModel(DataModel<GradeLevel> gradeLevelModel) {
        this.gradeLevelModel = gradeLevelModel;
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
