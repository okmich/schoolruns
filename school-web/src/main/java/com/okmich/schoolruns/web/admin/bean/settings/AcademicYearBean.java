/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.AcademicYear;
import com.okmich.schoolruns.common.service.CommonService;
import com.okmich.schoolruns.web.common.bean.ApplicationListBean;
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
public class AcademicYearBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{applicationListBean}")
    private ApplicationListBean applicationListBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private AcademicYear academicYear = new AcademicYear();
    private DataModel<AcademicYear> academicYearModel;

    public AcademicYearBean() {
    }

    /**
     * @return
     */
    public String saveAcademicYear() {
        try {
            getAcademicYear().setModifiedBy(getUserLoginSessionBean().getUserLogin().getUsername());
            AcademicYear _academicYear = commonService.saveAcademicYear(getAcademicYear());
            ((List<AcademicYear>) academicYearModel.getWrappedData()).add(_academicYear);
            if (!applicationListBean.getAcademicYearList().contains(_academicYear)) {
                applicationListBean.getAcademicYearList().add(_academicYear);
            }

            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(AcademicYearBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setAcademicYear(new AcademicYear());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(AcademicYearBean.class.getName()).log(Level.INFO, "" + getAcademicYear(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setAcademicYear(new AcademicYear());
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
     * @return the academicYear
     */
    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    /**
     * @param academicYear the academicYear to set
     */
    public void setAcademicYear(AcademicYear academicYear1) {
        this.academicYear = academicYear1;
    }

    /**
     * @return the academicYearModel
     */
    public DataModel<AcademicYear> getAcademicYearModel() {
        try {
            academicYearModel = new ListDataModel<AcademicYear>(
                    commonService.findAcademicYears());
        } catch (Exception ex) {
            Logger.getLogger(AcademicYearBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return academicYearModel;
    }

    /**
     * @param academicYearModel the academicYearModel to set
     */
    public void setAcademicYearModel(DataModel<AcademicYear> academicYearModel) {
        this.academicYearModel = academicYearModel;
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
     * @param applicationListBean the applicationListBean to set
     */
    public void setApplicationListBean(ApplicationListBean applicationListBean) {
        this.applicationListBean = applicationListBean;
    }
}
