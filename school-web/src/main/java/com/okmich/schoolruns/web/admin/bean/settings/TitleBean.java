/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.Title;
import com.okmich.schoolruns.common.service.CommonService;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class TitleBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private Title title = new Title();
    private DataModel<Title> titleModel;

    public TitleBean() {
    }

    /**
     * @return
     */
    public String saveTitle() {
        try {
            Title _title = commonService.saveTitle(getTitle());
            ((List<Title>) titleModel.getWrappedData()).add(_title);
            clearForm();
            return "";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForCreate() {
        setTitle(new Title());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(TitleBean.class.getName()).log(Level.INFO, "" + getTitle(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setTitle(new Title());
        setEditMode("");
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
     * @return the title
     */
    public Title getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * @return the titleModel
     */
    public DataModel<Title> getTitleModel() {
        try {
            titleModel = new ListDataModel<Title>(
                    commonService.findTitles());
        } catch (Exception ex) {
            processException(ex);
        }
        return titleModel;
    }

    /**
     * @param titleModel the titleModel to set
     */
    public void setTitleModel(DataModel<Title> titleModel) {
        this.titleModel = titleModel;
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
