/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.bean;

import com.okmich.schoolruns.common.entity.City;
import com.okmich.schoolruns.common.entity.State;
import com.okmich.schoolruns.common.service.CommonService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Michael
 */
@ManagedBean
@ApplicationScoped
public class AddressApplicationBean implements Serializable {

    /**
     * citiesList
     */
    private List<City> citiesList;
    /**
     * citiesList
     */
    private List<State> statesList;
    /**
     * citiesList
     */
    private Map<String, List<City>> stateCitiesMap;
    /**
     * commonService
     */
    @ManagedProperty("#{commonService}")
    private CommonService commonService;

    public AddressApplicationBean() {
    }

    @PostConstruct
    public void initilizeBean() {
        try {
            statesList = new ArrayList<>();
            statesList.addAll(commonService.findStatess());
            stateCitiesMap = new HashMap<>();
            for (State st : statesList) {
                //set up the map entry
                stateCitiesMap.put(st.getStateCode(),
                        commonService.findCitiesByState(st.getStateCode()));
            }
        } catch (Exception ex) {
            Logger.getLogger(AddressApplicationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fireStateChangedEvent(ValueChangeEvent event) {
        try {
            setCitiesList(null); //re-initializing the cities list
            HtmlSelectOneMenu stateMenu = (HtmlSelectOneMenu) event.getComponent();
            String _stateCode = stateMenu.getValue().toString();

            presetCity(_stateCode);
        } catch (Exception e) {
            Logger.getLogger(AddressApplicationBean.class.getName()).log(
                    Level.SEVERE, e.getMessage(), e);
        }
    }

    public List<City> getCitiesList() {
        if (citiesList == null) {
            citiesList = commonService.findCitiesByState(null);
        }
        return citiesList;
    }

    public void setCitiesList(List<City> citiesList) {
        this.citiesList = citiesList;
    }

    public List<State> getStatesList() {
        if (statesList == null) {
            initilizeBean();
        }
        return statesList;
    }

    public void setStatesList(List<State> statesList) {
        this.statesList = statesList;
    }

    /**
     * @return the stateCitiesMap
     */
    public Map<String, List<City>> getStateCitiesMap() {
        return stateCitiesMap;
    }

    /**
     * @param stateCitiesMap the stateCitiesMap to set
     */
    public void setStateCitiesMap(Map<String, List<City>> stateCitiesMap) {
        this.stateCitiesMap = stateCitiesMap;
    }

    /**
     *
     * @param stateCode
     */
    public void presetCity(String stateCode) {
        try {
            getCitiesList().clear();
            if (stateCode != null && !stateCode.trim().isEmpty()) {
                getCitiesList().addAll(getStateCitiesMap().get(stateCode));
            }
        } catch (Exception e) {
            Logger.getLogger(AddressApplicationBean.class.getName()).log(
                    Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * @param commonService the commonService to set
     */
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }
}
