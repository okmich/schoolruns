/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.data;

import com.okmich.common.BaseData;
import java.util.Date;

/**
 *
 * @author Michael
 * @since Aug 13, 2013
 * @company Okmich Ltd.
 */
public class Attendance<T> extends BaseData {

    private Integer attendanceId;
    private T t;
    private Date attendDate;
    private boolean present;
    private Integer schoolTermId;
    private String schoolTerm;
    private Integer schoolYearId;
    private String schoolYear;

    public Attendance() {
    }

    /**
     * @return the attendanceId
     */
    public Integer getAttendanceId() {
        return attendanceId;
    }

    /**
     * @param attendanceId the attendanceId to set
     */
    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    /**
     * @return the t
     */
    public T getType() {
        return t;
    }

    /**
     * @param t the t to set
     */
    public void setType(T t) {
        this.t = t;
    }

    /**
     * @return the attendDate
     */
    public Date getAttendDate() {
        return attendDate;
    }

    /**
     * @param attendDate the attendDate to set
     */
    public void setAttendDate(Date attendDate) {
        this.attendDate = attendDate;
    }

    /**
     * @return the present
     */
    public boolean isPresent() {
        return present;
    }

    /**
     * @param present the present to set
     */
    public void setPresent(boolean present) {
        this.present = present;
    }

    /**
     * @return the schoolTermId
     */
    public Integer getSchoolTermId() {
        return schoolTermId;
    }

    /**
     * @param schoolTermId the schoolTermId to set
     */
    public void setSchoolTermId(Integer schoolTermId) {
        this.schoolTermId = schoolTermId;
    }

    /**
     * @return the schoolTerm
     */
    public String getSchoolTerm() {
        return schoolTerm;
    }

    /**
     * @param schoolTerm the schoolTerm to set
     */
    public void setSchoolTerm(String schoolTerm) {
        this.schoolTerm = schoolTerm;
    }

    /**
     * @return the schoolYearId
     */
    public Integer getSchoolYearId() {
        return schoolYearId;
    }

    /**
     * @param schoolYearId the schoolYearId to set
     */
    public void setSchoolYearId(Integer schoolYearId) {
        this.schoolYearId = schoolYearId;
    }

    /**
     * @return the schoolYear
     */
    public String getSchoolYear() {
        return schoolYear;
    }

    /**
     * @param schoolYear the schoolYear to set
     */
    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }
//
//    public void setSchoolTerm(SchoolTerm schoolTerm1) {
//        if (schoolTerm1 == null) {
//            this.schoolTerm = schoolTerm1.getTerm() == null ? "" : schoolTerm1.getTerm().getDescription();
//            this.schoo
//        }
//    }
}
