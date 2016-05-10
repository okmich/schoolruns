/*
 * SmallListTypes.java
 *
 * Created on October 26, 2007, 4:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.okmich.common.web.util.staticlist;

/**
 *
 * @author OKALI CHIGOZIE DAMIAN
 */
public interface SmallListTypes {

    /**
     * Separates Fields in resource file Small Lists are "pickled" within
     * resource file property values.
     */
    String SMALLLIST_FIELD_DELIMITER = "¬";
    /**
     * Separates records in resource file. Small Lists are "pickled" within
     * resource file property values.
     */
    String SMALLLIST_RECORD_DELIMITER = "\\|";
    /**
     * Used for array construction
     */
    int SMALLLIST_MAX_RECORDS = 80;
    /**
     * The resource file name
     */
    String SMALLLIST_PROPERTY_FILE = "staticlists";
    /**
     * Used to convert package to file ref Regular Expression
     */
    String MEMBER_OPERATOR = ".";
    /**
     * Used to convert package to file ref
     */
    String FILE_SEPARATOR = System.getProperty("file.separator");
    /**
     * LIST ENTRIES STARTS HERE
     * ============================================++++++++++++++++++++===================
     */
    /**
     * SMALLLIST_GENDER_TYPE
     */
    String SMALLLIST_NO_YES_TYPE = "NO_YES_TYPE";
    /**
     * SMALLLIST_GENDER_TYPE
     */
    String SMALLLIST_GENDER_TYPE = "GENDER_TYPE";
    /**
     * SMALLLIST_STATUS_TYPE
     */
    String SMALLLIST_STATUS_TYPE = "STATUS_TYPE";
    /**
     * SMALLLIST_GENDER_TYPE
     */
    String SMALLLIST_EXAM_STATUS_TYPE = "EXAM_STATUS_TYPE";
    /**
     * SMALLLIST_STATUS_TYPE
     */
    String SMALLLIST_DC_SIGN_TYPE = "DC_SIGN_TYPE";
    /**
     * SMALLLIST_FEE_CAT_TYPE
     */
    String SMALLLIST_FEE_CAT_TYPE = "FEE_CAT_TYPE";
    /**
     * SMALLLIST_PAY_MODE_TYPE
     */
    String SMALLLIST_PAY_MODE_TYPE = "PAY_MODE_TYPE";
    /**
     * SMALLLIST_AVAIL_DAY_TYPE
     */
    String SMALLLIST_AVAIL_DAY_TYPE = "AVAIL_DAY_TYPE";
    /**
     * SMALLLIST_GENO_TYPE
     */
    String SMALLLIST_GENO_TYPE = "GENO_TYPE";
    /**
     * SMALLLIST_RELATIONSHIP_TYPE
     */
    String SMALLLIST_RELATIONSHIP_TYPE = "RELATIONSHIP_TYPE";
    /**
     * SMALLLIST_EBOOK_TYPE
     */
    String SMALLLIST_EBOOK_TYPE = "EBOOK_TYPE";
}
