/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.template;

import java.io.InputStream;
import java.io.Serializable;

/**
 *
 * @author Michael
 * @since Sep 5, 2013
 * @company Okmich Ltd.
 */
public class DocumentTemplateLocator implements Serializable {

    /**
     * letterHeadTemplate
     */
    public static DocumentTemplateLocator letterHeadTemplate =
            new DocumentTemplateLocator("letter-headed-paper.jasper");
    /**
     * ***********************************************************************
     *************************** FINANCE RELATED *****************************
     * ***********************************************************************
     */
    /**
     * receiptTemplate
     */
    public static DocumentTemplateLocator receiptTemplate =
            new DocumentTemplateLocator("finance-receipt-template.jasper");
    /**
     * ***********************************************************************
     *************************** STAFF RELATED ******************************
     * ***********************************************************************
     */
    /**
     * staffDataPageTemplate
     */
    public static DocumentTemplateLocator staffDataPageTemplate =
            new DocumentTemplateLocator("staff-data-page.jasper");
    /**
     * staffListingTemplate
     */
    public static DocumentTemplateLocator staffListingTemplate =
            new DocumentTemplateLocator("staff-listing.jasper");
    /**
     * staffListingCategoryTemplate
     */
    public static DocumentTemplateLocator staffListingCategoryTemplate =
            new DocumentTemplateLocator("staff-listing-by-category.jasper");
    /**
     * ***********************************************************************
     *************************** STUDENT RELATED *****************************
     * ***********************************************************************
     */
    /**
     * studentDataPageTemplate
     */
    public static DocumentTemplateLocator studentDataPageTemplate =
            new DocumentTemplateLocator("student-data-page.jasper");
    /**
     *
     */
    private String templateFileName;

    /**
     * DocumentTemplateLocator
     */
    private DocumentTemplateLocator(String templateFile) {
        this.templateFileName = templateFile;
    }

    /**
     *
     *
     * @return
     */
    public InputStream value() {
        return DocumentTemplateLocator.class.getResourceAsStream(this.templateFileName);
    }
}
