/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.EventType;
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
public class EventTypeBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{applicationListBean}")
    private ApplicationListBean applicationListBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private EventType eventType = new EventType();
    private DataModel<EventType> eventTypeModel;

    public EventTypeBean() {
    }

    /**
     * @return
     */
    public String saveEventType() {
        try {
            getEventType().setModifiedBy(getUserLoginSessionBean().getUserLogin().getUsername());
            EventType _eventType = commonService.saveEventType(getEventType());
            ((List<EventType>) eventTypeModel.getWrappedData()).add(_eventType);
            if (!applicationListBean.getEventTypeList().contains(_eventType)) {
                applicationListBean.getEventTypeList().add(_eventType);
            }

            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(EventTypeBean.class.getName()).log(Level.SEVERE, null, ex);
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
        setEventType(new EventType());
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        Logger.getLogger(EventTypeBean.class.getName()).log(Level.INFO, "" + getEventType(), "");
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setEventType(new EventType());
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
     * @return the eventType
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(EventType eventType1) {
        this.eventType = eventType1;
    }

    /**
     * @return the eventTypeModel
     */
    public DataModel<EventType> getEventTypeModel() {
        try {
            eventTypeModel = new ListDataModel<EventType>(
                    commonService.findEventTypes());
        } catch (Exception ex) {
            Logger.getLogger(EventTypeBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return eventTypeModel;
    }

    /**
     * @param eventTypeModel the eventTypeModel to set
     */
    public void setEventTypeModel(DataModel<EventType> eventTypeModel) {
        this.eventTypeModel = eventTypeModel;
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