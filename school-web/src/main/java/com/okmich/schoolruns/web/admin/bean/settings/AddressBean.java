/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.City;
import com.okmich.schoolruns.common.entity.State;
import com.okmich.common.exception.BusinessException;
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
public final class AddressBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private State state = new State();
    private City city = new City();
    private DataModel<State> stateModel;
    private DataModel<City> cityModel;
    private String stateCode;

    /**
     *
     */
    public AddressBean() {
        setEditMode("VIEW");
        if (getState() == null) {
            setState(new State());
        }
        if (getCity() == null) {
            setCity(new City());
        }
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
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * @return the city
     */
    public City getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * @return the stateModel
     */
    public DataModel getStateModel() {
        return stateModel;
    }

    /**
     * @param stateModel the stateModel to set
     */
    public void setStateModel(DataModel stateModel) {
        this.stateModel = stateModel;
    }

    /**
     * @return the cityModel
     */
    public DataModel getCityModel() {
        return cityModel;
    }

    /**
     * @param cityModel the cityModel to set
     */
    public void setCityModel(DataModel cityModel) {
        this.cityModel = cityModel;
    }

    public String saveState() {
        try {
            getState().setModifiedBy(getUserLoginSessionBean().getUserLogin().getUsername());
            commonService.saveStates(getState());
            setEditMode("VIEW");
            findStates();
            setState(new State());
        } catch (BusinessException ex) {
            Logger.getLogger(AddressBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "Error"));
        }
        return "";
    }

    public String saveCity() {
        try {
            getCity().setModifiedBy(getUserLoginSessionBean().getUserLogin().getUsername());
            commonService.saveCity(getCity());
            setEditMode("VIEW");
            setCity(new City());
        } catch (BusinessException ex) {
            Logger.getLogger(AddressBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "Error"));
        }
        return null;
    }

    public String findStates() {
        try {
            List<State> stateList = commonService.findStatess();
            this.stateModel = new ListDataModel<State>(stateList);
        } catch (Exception ex) {
            Logger.getLogger(AddressBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "Error"));
        }
        return "";
    }

    public String findCities() {
        try {
            List<City> citiesList = commonService.findCitiesByState(getStateCode());
            this.cityModel = new ListDataModel<City>(citiesList);
        } catch (Exception ex) {
            Logger.getLogger(AddressBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "Error"));
        }
        return "";
    }

    public String findState() {
        setEditMode(FacesUtil.getRequestParameter("editMode"));
        return "";
    }

    public String findCity() {
        setEditMode(FacesUtil.getRequestParameter("editMode"));
        return "";
    }

    public String clearState() {
        setState(new State());
        return "";
    }

    public String prepareToAddState() {
        setState(new State());
        setEditMode("CREATE");
        return "";
    }

    public String prepareToAddCity() {
        setCity(new City());
        setEditMode("CREATE");
        return "";
    }

    public String clearCity() {
        setCity(new City());
        return "";
    }

    /**
     * @return the stateCode
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * @param stateCode the stateCode to set
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
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