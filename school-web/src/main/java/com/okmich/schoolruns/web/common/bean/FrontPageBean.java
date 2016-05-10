/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class FrontPageBean extends _BaseBean {

    private String appTitle;
    private String logoUrl;

    /**
     * Creates a new instance of FrontPageBean
     */
    public FrontPageBean() {
    }

    /**
     * @return the appTitle
     */
    public String getAppTitle() {
        if (this.appTitle == null) {
            this.appTitle = "schoolruns.com";
        }
        return appTitle;
    }

    /**
     * @param appTitle the appTitle to set
     */
    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    /**
     * @return the logoUrl
     */
    public String getLogoUrl() {
        if (this.logoUrl == null) {
            this.logoUrl = "/resources/images/default.pw.png";
        }
        return logoUrl;
    }

    /**
     * @param logoUrl the logoUrl to set
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}