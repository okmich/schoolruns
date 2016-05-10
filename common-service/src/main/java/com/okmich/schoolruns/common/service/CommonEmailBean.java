/*
 * AKURA, This application manages the daily activities of a Teacher and a Student of a School>
 *
 * Copyright (C) 2012 Virtusa Corporation.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.okmich.schoolruns.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Virtusa Corporation
 */
public class CommonEmailBean {
    
    /** String attribute for to name. */
    private static final String TO_NAME = ", toName=";
    
    /** String attribute for to address. */
    private static final String TO_ADDRESS = ", toAddress=";
    
    /** String attribute for from name. */
    private static final String FROM_NAME = ", fromName=";
    
    /** String attribute for from address. */
    private static final String FROM_ADDRESS = "CommonEmailBean [fromAddress=";
    
    /**
     * The fromAddress to be set in the email.
     */
    private String fromAddress;
    
    /**
     * The fromName to be set in the email.
     */
    private String fromName;
    
    /**
     * The toAddress to be set in the email.
     */
    private String toAddress;
    
    /**
     * The toName to be set in the email.
     */
    private String toName;
    
    /**
     * The CCAddresses to be set in the email.
     */
    private List<String> ccAddresses;
    
    /**
     * The bccAddress to be set in the email.
     */
    private String bccAddresses;
    
    /**
     * The subject to be set in the email.
     */
    private String subject;
    
    /**
     * The emailContentInfo to be set in order to generate content of the email.
     */
    private Map<String, Object> objectMap = null;
    
    /**
     * The mailType to be set in order to generate content according to this mail type.
     */
    private String mailTemplate = null;

	/**
     * The templateHome to be set in order to identified template location.
     */
    private String templateHome = null;
    
    /**
     * attachMail to verify want to attach to the mail.
     */
    private boolean attachMail;
    
    /**
     * @return the attachMail
     */
    
    public boolean isAttachMail() {
    
        return attachMail;
    }
    
    /**
     * attachementPath to get path of the attachment.
     */
    private String attachementPath;
    
    /**
     * @return the attachementPath
     */
    
    public String getAttachementPath() {
    
        return attachementPath;
    }
    
    /**
     * @param attachementPathRef attachment path to set
     */
    
    public void setAttachementPath(String attachementPathRef) {
    
        this.attachementPath = attachementPathRef;
    }
    
    /**
     * @param attachMailRef attache mail to set
     */
    public void setAttachMail(boolean attachMailRef) {
    
        this.attachMail = attachMailRef;
    }
    
    /**
     * Default constructor.
     */
    public CommonEmailBean() {
    
        this.fromAddress = "";
        this.fromName = "";
        this.toAddress = "";
        this.toName = "";
        this.subject = "";
    }
    
    // =====================
    // Getters and Setters
    // =====================
    
    /**
     * @return the fromAddress
     */
    public String getFromAddress() {
    
        return fromAddress;
    }
    
    /**
     * gets the cc Address list.
     * @return ccAddresses as List
     */
    
    public List<String> getCcAddresses() {
    
        return ccAddresses;
    }

    /**
     * set the ccAddresses .
     * @param ccAddressesArg List of addresses
     */
    public void setCcAddresses(List<String> ccAddressesArg) {
    
        this.ccAddresses = ccAddressesArg;
    }
    /**
     * add the cc address to list.
     * @param ccAddress value to set
     */
    public void addCCAddress(String ccAddress){
        
        if(ccAddresses==null){
            ccAddresses = new ArrayList<String>();
        }
        if(ccAddress!=null && !ccAddress.isEmpty() ){
            ccAddresses.add(ccAddress);
        }
    }

    /**
     * @param strFromAddress the fromAddress to set
     */
    public void setFromAddress(String strFromAddress) {
    
        this.fromAddress = strFromAddress;
    }
    
    /**
     * @return the fromName
     */
    public String getFromName() {
    
        return fromName;
    }
    
    /**
     * @param strFromName the fromName to set
     */
    public void setFromName(String strFromName) {
    
        this.fromName = strFromName;
    }
    
    /**
     * @return the toAddress
     */
    public String getToAddress() {
    
        return toAddress;
    }
    
    /**
     * @param strToAddress the toAddress to set
     */
    public void setToAddress(String strToAddress) {
    
        this.toAddress = strToAddress;
    }
    
    /**
     * @return the toName
     */
    public String getToName() {
    
        return toName;
    }
    
    /**
     * @param strToName the toName to set
     */
    public void setToName(String strToName) {
    
        this.toName = strToName;
    }
    

    
    /**
     * @return the bccAddresses
     */
    public String getBccAddresses() {
    
        return bccAddresses;
    }
    
    /**
     * @param strBccAddresses the bccAddresses to set
     */
    public void setBccAddresses(String strBccAddresses) {
    
        this.bccAddresses = strBccAddresses;
    }
    
    /**
     * @return the subject
     */
    public String getSubject() {
    
        return subject;
    }
    
    /**
     * @param strSubject the subject to set
     */
    public void setSubject(String strSubject) {
    
        this.subject = strSubject;
    }
    
    /**
     * @return the objectMap
     */
    public Map<String, Object> getObjectMap() {
    
        return objectMap;
    }
    
    /**
     * @param mapObjectMap the objectMap to set
     */
    public void setObjectMap(Map<String, Object> mapObjectMap) {
    
        this.objectMap = mapObjectMap;
    }
    
    /**
     * @return the mailType
     */
    public String getMailTemplate() {
    
        return mailTemplate;
    }
    
    /**
     * @param strMailType the mailType to set
     */
    public void setMailTemplate(String strMailType) {
    
        this.mailTemplate = strMailType;
    }
    
    /**
     * @return the templateHome
     */
    public String getTemplateHome() {
    
        return templateHome;
    }
    
    /**
     * @param strTemplateHome the templateHome to set
     */
    public void setTemplateHome(String strTemplateHome) {
    
        this.templateHome = strTemplateHome;
    }
    
    /**
     * Returns the details for the CommonEmailBean object.
     * 
     * @return - the CommonEmailBean object details.
     */
    @Override
    public String toString() {
    
        return FROM_ADDRESS + fromAddress + FROM_NAME + fromName + TO_ADDRESS + toAddress + TO_NAME + toName;
    }
    
    /* *//**
     * @return the mailType
     */
    /*
     * public String getMailType() { return mailType; }
     */
    /* *//**
     * @param strMailType the mailType to set
     */
    /*
     * public void setMailType(String strMailType) { this.mailType = strMailType; }
     */
    
}
