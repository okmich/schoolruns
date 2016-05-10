package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "school_pref")
public class SchoolPref implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "school_id")
    private Integer schoolId;
    @Basic(optional = false)
    @Column(name = "logo_url")
    private String logoUrl;
    @Basic(optional = false)
    @Column(name = "webpage_theme")
    private String webpageTheme;
    @Column(name = "system_gen_staff_reg_no")
    private boolean systemGenStaffRegNo;
    @Column(name = "system_gen_student_reg_no")
    private boolean systemGenStudentRegNo;
    @Column(name = "class_start")
    @Temporal(TemporalType.TIME)
    private Date classStart;
    @Column(name = "class_end")
    @Temporal(TemporalType.TIME)
    private Date classEnd;
    @Column(name = "ttable_contain_teacher")
    private boolean ttableContainTeacher;
    @Column(name = "ttable_contain_classroom")
    private boolean ttableContainClassroom;
    @Column(name = "ttable_resolve_sub_teacher")
    private boolean ttableResolveSubTeacher;
    @Column(name = "quiz_cont_assessment")
    private boolean quizContAssessment;
    @Column(name = "final_quiz_weight")
    private Integer finalQuizWeight;
    @Column(name = "sub_max_score")
    private Integer subMaxScore = 100;//this should be rationally 100
    @Column(name = "taxation_enabled")
    private boolean taxationEnabled;
    @Column(name = "tax_rate")
    private BigDecimal taxRate;
    @Column(name = "avail_days")
    private String availDays;
    @Column(name = "staff_avail_days")
    private String staffAvailDays;
    @Column(name = "google_username")
    private String googleUsername;
    @Column(name = "google_password")
    private String googlePassword;
    @Column(name = "pic_album_id")
    private String picAlbumId;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @JoinColumn(name = "school_id", referencedColumnName = "school_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private School school;

    public SchoolPref() {
    }

    public SchoolPref(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * @param logoUrl the logoUrl to set
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getWebpageTheme() {
        return webpageTheme;
    }

    public void setWebpageTheme(String webpageTheme) {
        this.webpageTheme = webpageTheme;
    }

    /**
     * @return the systemGenStaffRegNo
     */
    public boolean isSystemGenStaffRegNo() {
        return systemGenStaffRegNo;
    }

    /**
     * @param systemGenStaffRegNo the systemGenStaffRegNo to set
     */
    public void setSystemGenStaffRegNo(boolean systemGenStaffRegNo) {
        this.systemGenStaffRegNo = systemGenStaffRegNo;
    }

    /**
     * @return the systemGenStudentRegNo
     */
    public boolean isSystemGenStudentRegNo() {
        return systemGenStudentRegNo;
    }

    /**
     * @param systemGenStudentRegNo the systemGenStudentRegNo to set
     */
    public void setSystemGenStudentRegNo(boolean systemGenStudentRegNo) {
        this.systemGenStudentRegNo = systemGenStudentRegNo;
    }

    /**
     * @return the classStart
     */
    public Date getClassStart() {
        return classStart;
    }

    /**
     * @param classStart the classStart to set
     */
    public void setClassStart(Date classStart) {
        this.classStart = classStart;
    }

    /**
     * @return the classEnd
     */
    public Date getClassEnd() {
        return classEnd;
    }

    /**
     * @param classEnd the classEnd to set
     */
    public void setClassEnd(Date classEnd) {
        this.classEnd = classEnd;
    }

    /**
     * @return the ttableContainTeacher
     */
    public boolean getTtableContainTeacher() {
        return ttableContainTeacher;
    }

    /**
     * @param ttableContainTeacher the ttableContainTeacher to set
     */
    public void setTtableContainTeacher(boolean ttableContainTeacher) {
        this.ttableContainTeacher = ttableContainTeacher;
    }

    /**
     * @return the ttableContainClassroom
     */
    public boolean getTtableContainClassroom() {
        return ttableContainClassroom;
    }

    /**
     * @param ttableContainClassroom the ttableContainClassroom to set
     */
    public void setTtableContainClassroom(boolean ttableContainClassroom) {
        this.ttableContainClassroom = ttableContainClassroom;
    }

    /**
     * @return the ttableResolveSubTeacher
     */
    public boolean getTtableResolveSubTeacher() {
        return ttableResolveSubTeacher;
    }

    /**
     * @param ttableResolveSubTeacher the ttableResolveSubTeacher to set
     */
    public void setTtableResolveSubTeacher(boolean ttableResolveSubTeacher) {
        this.ttableResolveSubTeacher = ttableResolveSubTeacher;
    }

    /**
     * @return the quizContAssessment
     */
    public boolean isQuizContAssessment() {
        return quizContAssessment;
    }

    /**
     * @param quizContAssessment the quizContAssessment to set
     */
    public void setQuizContAssessment(boolean quizContAssessment) {
        this.quizContAssessment = quizContAssessment;
    }

    /**
     * @return the finalQuizWeight
     */
    public Integer getFinalQuizWeight() {
        return finalQuizWeight;
    }

    /**
     * @param finalQuizWeight the finalQuizWeight to set
     */
    public void setFinalQuizWeight(Integer finalQuizWeight) {
        this.finalQuizWeight = finalQuizWeight;
    }

    /**
     * @return the subMaxScore
     */
    public Integer getSubMaxScore() {
        return subMaxScore;
    }

    /**
     * @param subMaxScore the subMaxScore to set
     */
    public void setSubMaxScore(Integer subMaxScore) {
        this.subMaxScore = subMaxScore;
    }

    /**
     * @return the taxationEnabled
     */
    public boolean isTaxationEnabled() {
        return taxationEnabled;
    }

    /**
     * @param taxationEnabled the taxationEnabled to set
     */
    public void setTaxationEnabled(boolean taxationEnabled) {
        this.taxationEnabled = taxationEnabled;
    }

    /**
     * @return the availDays
     */
    public String getAvailDays() {
        return availDays;
    }

    /**
     * @param availDays the availDays to set
     */
    public void setAvailDays(String availDays) {
        this.availDays = availDays;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolId != null ? schoolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolPref)) {
            return false;
        }
        SchoolPref other = (SchoolPref) object;
        if ((this.schoolId == null && other.schoolId != null) || (this.schoolId != null && !this.schoolId.equals(other.schoolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.SchoolPref[ schoolId=" + schoolId + " ]";
    }

    /**
     * @return the staffAvailDays
     */
    public String getStaffAvailDays() {
        return staffAvailDays;
    }

    /**
     * @param staffAvailDays the staffAvailDays to set
     */
    public void setStaffAvailDays(String staffAvailDays) {
        this.staffAvailDays = staffAvailDays;
    }

    /**
     * @return the googleUsername
     */
    public String getGoogleUsername() {
        return googleUsername;
    }

    /**
     * @param googleUsername the googleUsername to set
     */
    public void setGoogleUsername(String googleUsername) {
        this.googleUsername = googleUsername;
    }

    /**
     * @return the googlePassword
     */
    public String getGooglePassword() {
        return googlePassword;
    }

    /**
     * @param googlePassword the googlePassword to set
     */
    public void setGooglePassword(String googlePassword) {
        this.googlePassword = googlePassword;
    }

    /**
     * @return the taxRate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * @param taxRate the taxRate to set
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * @return the picAlbumId
     */
    public String getPicAlbumId() {
        return picAlbumId;
    }

    /**
     * @param picAlbumId the picAlbumId to set
     */
    public void setPicAlbumId(String picAlbumId) {
        this.picAlbumId = picAlbumId;
    }
}
