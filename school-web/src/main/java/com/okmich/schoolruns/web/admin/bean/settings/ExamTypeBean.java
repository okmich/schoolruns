/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.ExamType;
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
public class ExamTypeBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private ExamType examType = new ExamType();
    private DataModel<ExamType> examTypeModel;

    public ExamTypeBean() {
    }

    /**
     * @return
     */
    public String saveExamType() {
        try {
            ExamType _examType = commonService.saveExamType(getExamType());
            ((List<ExamType>) examTypeModel.getWrappedData()).add(_examType);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(ExamTypeBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setExamType(new ExamType());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(ExamTypeBean.class.getName()).log(Level.INFO, "" + getExamType(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setExamType(new ExamType());
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
     * @return the examType
     */
    public ExamType getExamType() {
        return examType;
    }

    /**
     * @param examType the examType to set
     */
    public void setExamType(ExamType examType) {
        this.examType = examType;
    }

    /**
     * @return the examTypeModel
     */
    public DataModel<ExamType> getExamTypeModel() {
        try {
            examTypeModel = new ListDataModel<ExamType>(
                    commonService.findExamTypes());
        } catch (Exception ex) {
            Logger.getLogger(ExamTypeBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return examTypeModel;
    }

    /**
     * @param examTypeModel the examTypeModel to set
     */
    public void setExamTypeModel(DataModel<ExamType> examTypeModel) {
        this.examTypeModel = examTypeModel;
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
