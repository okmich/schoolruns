/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.data;

import com.okmich.common.BaseData;
import com.okmich.schoolruns.common.entity.AcademicYear;
import com.okmich.schoolruns.common.entity.SchoolTerm;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.common.entity.Term;
import java.util.Date;

/**
 *
 * @author Michael
 */
public final class SchoolCalendarData extends BaseData {

    private Integer schoolYearId;
    private boolean currentYear;
    private Date yearStartDate;
    private Date yearClosingDate;
    private Integer yearId;
    private String academicYear;
    private Integer schoolId;
    private Integer schoolTermId;
    private Date termStartDate;
    private Date termClosingDate;
    private boolean currentTerm;
    private Integer termId;
    private String termDescription;

    public SchoolCalendarData() {
    }

    /**
     * creates a {@code SchoolCalendarData} object whose fields are initialized
     * by the {@code schoolTerm} parameter
     *
     * @param schoolTerm
     */
    public SchoolCalendarData(SchoolTerm schoolTerm) {
        setSchoolTerm(schoolTerm);
    }

    /**
     * creates a {@code SchoolCalendarData} object whose fields are initialized
     * by the {@code schoolYear} parameter
     *
     * @param schoolYear
     */
    public SchoolCalendarData(SchoolYear schoolYear) {
        setSchoolYear(schoolYear);
    }

    /**
     * creates a {@code SchoolCalendarData} object whose fields are initialized
     * by the {@code schoolYear} and {@code schoolTerm} fields passed in as
     * parameter
     *
     * @param schoolYear
     * @param schoolTerm
     */
    public SchoolCalendarData(SchoolYear schoolYear, SchoolTerm schoolTerm) {
        setSchoolTerm(schoolTerm);
        setSchoolYear(schoolYear);
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
     * @return the currentYear
     */
    public boolean isCurrentYear() {
        return currentYear;
    }

    /**
     * @param currentYear the currentYear to set
     */
    public void setCurrentYear(boolean currentYear) {
        this.currentYear = currentYear;
    }

    /**
     * @return the yearStateDate
     */
    public Date getYearStartDate() {
        return yearStartDate;
    }

    /**
     * @param yearStateDate the yearStateDate to set
     */
    public void setYearStartDate(Date yearStateDate) {
        this.yearStartDate = yearStateDate;
    }

    /**
     * @return the yearClosingDate
     */
    public Date getYearClosingDate() {
        return yearClosingDate;
    }

    /**
     * @param yearClosingDate the yearClosingDate to set
     */
    public void setYearClosingDate(Date yearClosingDate) {
        this.yearClosingDate = yearClosingDate;
    }

    /**
     * @return the yearId
     */
    public Integer getYearId() {
        return yearId;
    }

    /**
     * @param yearId the yearId to set
     */
    public void setYearId(Integer yearId) {
        this.yearId = yearId;
    }

    /**
     * @return the academicYear
     */
    public String getAcademicYear() {
        return academicYear;
    }

    /**
     * @param academicYear the academicYear to set
     */
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    /**
     * @return the schoolId
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
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
     * @return the termStartDate
     */
    public Date getTermStartDate() {
        return termStartDate;
    }

    /**
     * @param termStartDate the termStartDate to set
     */
    public void setTermStartDate(Date termStartDate) {
        this.termStartDate = termStartDate;
    }

    /**
     * @return the termClosingDate
     */
    public Date getTermClosingDate() {
        return termClosingDate;
    }

    /**
     * @param termClosingDate the termClosingDate to set
     */
    public void setTermClosingDate(Date termClosingDate) {
        this.termClosingDate = termClosingDate;
    }

    /**
     * @return the currentTerm
     */
    public boolean isCurrentTerm() {
        return currentTerm;
    }

    /**
     * @param currentTerm the currentTerm to set
     */
    public void setCurrentTerm(boolean currentTerm) {
        this.currentTerm = currentTerm;
    }

    /**
     * @return the termId
     */
    public Integer getTermId() {
        return termId;
    }

    /**
     * @param termId the termId to set
     */
    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    /**
     * @return the termDescription
     */
    public String getTermDescription() {
        return termDescription;
    }

    /**
     * @param termDescription the termDescription to set
     */
    public void setTermDescription(String termDescription) {
        this.termDescription = termDescription;
    }

    //factory methods
    public void setSchoolYear(SchoolYear schoolYear) {
        if (schoolYear != null) {
            if (schoolYear.getAcademicYear() != null) {
                setYearId(schoolYear.getAcademicYear().getYearId());
                setAcademicYear(schoolYear.getAcademicYear().getDescription());
            }
            setCurrentYear(schoolYear.isCurrentYear());
            setModifiedBy(schoolYear.getModifiedBy());
            setModifiedTime(schoolYear.getModifiedTime());
            setSchoolId(schoolYear.getSchoolId());
            setSchoolYearId(schoolYear.getSchoolYearId());
            setStatus(schoolYear.getStatus());
            setYearClosingDate(schoolYear.getClosingDate());
            setYearStartDate(schoolYear.getStartDate());
        }
    }

    //factory methods
    public SchoolYear getSchoolYear() {
        SchoolYear schoolYear = new SchoolYear();

        schoolYear.setAcademicYear(new AcademicYear(this.yearId));
        schoolYear.setClosingDate(this.yearClosingDate);
        schoolYear.setCurrentYear(this.currentYear);
        schoolYear.setModifiedBy(this.modifiedBy);
        schoolYear.setModifiedTime(this.modifiedTime);
        schoolYear.setSchoolId(this.schoolId);
        schoolYear.setSchoolYearId(this.schoolYearId);
        schoolYear.setStartDate(this.yearStartDate);
        schoolYear.setStatus(this.status);

        return schoolYear;
    }

    public void setSchoolTerm(SchoolTerm schoolTerm) {
        if (schoolTerm != null) {
            setCurrentTerm(schoolTerm.isCurrentTerm());
            if (schoolTerm.getTerm() != null) {
                setTermDescription(schoolTerm.getTerm().getDescription());
                setTermId(schoolTerm.getTerm().getTermId());
            }
            setModifiedBy(schoolTerm.getModifiedBy());
            setModifiedTime(schoolTerm.getModifiedTime());
            setSchoolTermId(schoolTerm.getSchoolTermId());
            setSchoolYearId(schoolTerm.getSchoolYearId());
            setStatus(schoolTerm.getStatus());
            setTermClosingDate(schoolTerm.getClosingDate());
            setTermStartDate(schoolTerm.getStartDate());
        }
    }

    public SchoolTerm getSchoolTerm() {
        SchoolTerm schoolTerm = new SchoolTerm();

        schoolTerm.setClosingDate(this.termClosingDate);
        schoolTerm.setCurrentTerm(this.currentTerm);
        schoolTerm.setModifiedBy(this.modifiedBy);
        schoolTerm.setModifiedTime(this.modifiedTime);
        schoolTerm.setSchoolTermId(this.schoolTermId);
        schoolTerm.setSchoolYearId(this.schoolYearId);
        schoolTerm.setStartDate(this.termStartDate);
        schoolTerm.setStatus(this.status);
        schoolTerm.setTerm(new Term(this.termId));

        return schoolTerm;
    }
}
