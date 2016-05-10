/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common;

import com.okmich.common.util.DateUtil;
import com.okmich.schoolruns.common.entity.Address;
import com.okmich.schoolruns.common.entity.School;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Michael
 * @since Sep 14, 2013
 * @company Okmich Ltd.
 */
public abstract class SchoolDocGenerator {//extends XDocReportExportable {

    /**
     *
     *
     * @param templateId
     * @param in
     * @throws IOException
     * @throws IllegalArgumentException - if templateId or input stream is null
     */
    public SchoolDocGenerator(String templateId, InputStream in) throws IOException {
//        super(templateId, in);
    }

    /**
     *
     *
     * @param address
     * @return String
     */
    public String getAddress(Address address) {
        if (address == null) {
            return "";
        }
        StringBuilder addressBuilder = new StringBuilder("")
                .append(address.getStreetLine1() != null ? address.getStreetLine1() : "");
        addressBuilder.append(" ");
        addressBuilder.append(address.getStreetLine2() != null ? address.getStreetLine2() : "");
        addressBuilder.append("\n");
        addressBuilder.append(address.getCity() != null ? address.getCity().getName() : "");
        addressBuilder.append(", ");
        addressBuilder.append(address.getState() != null ? address.getState().getName() : "");

        return addressBuilder.toString();
    }

    /**
     
     * @param date
     * @return *
     */
    public String getDate(Date date) {
        if (date == null) {
            return "";
        }
        //return a string value of the date
        return DateUtil.getInstance().getDateAsString(date);
    }

    protected void setSchoolDefaultFields(Map<String, Object> context, School school,
            String logoUrl) {

//        getFieldsMetadata().addFieldAsImage("logo");

        context.put("email", school.getEmailAddress());
        context.put("phoneNo", school.getContactNo());
        context.put("schoolName", school.getName());
        context.put("schoolAddress", getAddress(school.getAddress()));

//        if (logoUrl != null && !logoUrl.trim().isEmpty()) {
//            try {
//                context.put("logo",
//                        new UrlImageProvider(new URL(logoUrl)));
//            } catch (MalformedURLException ex) {
//                Logger.getLogger(SchoolDocGenerator.class.getName()).log(
//                        Level.SEVERE, null, ex);
//            }
//        }
    }
}
