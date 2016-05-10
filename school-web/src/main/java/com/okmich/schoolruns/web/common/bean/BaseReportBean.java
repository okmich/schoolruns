/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.bean;

import com.okmich.report.util.ExportFormat;
import com.okmich.schoolruns.web.common.FacesUtil;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Michael
 * @since Oct 10, 2013
 * @company Okmich Ltd.
 */
public abstract class BaseReportBean extends _BaseBean {

    private static final String V_SCHOOL_TITLE_QUERY = "SELECT school_id, name, "
            + "email, contact_no, web_site, address, motto, logo_url "
            + "FROM v_school_title_pref WHERE school_id = ";

    public BaseReportBean() {
    }

    /**
     *
     * @param schoolId
     * @return
     */
    public static String getSchoolTitleQuery(Integer schoolId) {
        return V_SCHOOL_TITLE_QUERY + schoolId;
    }

    /**
     * creates in a map the parameters that will be used to populate the header
     * of all school reports
     *
     * @param rowSet
     * @return Map<String, Object>
     */
    public static Map<String, Object> getSchoolTitleParameters(CachedRowSet cachedRowSet) {
        if (cachedRowSet.size() < 1) {
            throw new IllegalArgumentException("no data in rowset");
        }
        Map<String, Object> params = new HashMap<>();
        try {
            while (cachedRowSet.next()) {
                params.put("schoolName", cachedRowSet.getString("name"));
                params.put("schoolEmail", cachedRowSet.getString("email"));
                params.put("schoolPhoneNumber", cachedRowSet.getString("contact_no"));
                params.put("schoolWebsite", cachedRowSet.getString("web_site"));
                params.put("schoolAddress", cachedRowSet.getString("address"));
                params.put("schoolMotto", cachedRowSet.getString("motto"));
                params.put("logo_url", cachedRowSet.getString("logo_url"));
                params.put("schoolId", cachedRowSet.getString("school_id"));
            }
            return params;
        } catch (SQLException ex) {
            Logger.getLogger(BaseReportBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     *
     *
     * @return ExportFormat
     */
    protected ExportFormat getRequestedFormat() {
        String format = FacesUtil.getRequestParameter("EXPORT_FORMAT");
        if (format == null) {
            return null;
        }
        switch (format) {
            case "CSV":
                return ExportFormat.CSV_FORMAT;
            case "HTML":
                return ExportFormat.HTML_FORMAT;
            case "PDF":
                return ExportFormat.PDF_FORMAT;
            case "XLS":
                return ExportFormat.XLS_FORMAT;
            case "XLSX":
                return ExportFormat.XLSX_FORMAT;
        }
        return null;
    }
}
