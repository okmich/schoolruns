/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.web.util.fileimport;

import com.okmich.common.util.api.CommonConstants;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Michael
 */
public abstract class FileImportUtil implements Serializable {

    /**
     * returns a date of parsed from the pre-formatted String as "dd/MM/yyyy".
     * If the format is not strictly obey, a ParseException is thrown
     *
     * @param dateString - date in string format - dd/MM/yyyy
     * @return Date - a data object parsed from the String
     * @throws Exception - if a parseException occurs
     */
    public static Date toDate(String dateString) throws Exception {
        //check for null or empty string
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                CommonConstants.SYSTEM_DATE_FORMAT);
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException ex) {
            throw ex;
        }
    }

    /**
     * returns true if the String parameter is not null, and either "true",
     * "TRUE" or 1. otherwise return false
     *
     * @param booleanString
     * @return true if the String parameter is not null, and either "true",
     * "TRUE" or 1. otherwise return false
     */
    public static boolean toBoolean(String booleanString) {
        if (booleanString != null
                && (booleanString.equals("1") || booleanString.toUpperCase().equals("Y")
                || booleanString.toLowerCase().equals("true"))) {
            return true;
        }
        return false;
    }
}
