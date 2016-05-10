/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "school_student")
public class SchoolStudent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "school_student_id")
    private Integer schoolStudentId;
    @Column(name = "registration_no")
    private String registrationNo;
    @Basic(optional = false)
    @Column(name = "prefect_flag")
    private boolean prefectFlag;
    @Column(name = "admission_date")
    @Temporal(TemporalType.DATE)
    private Date admissionDate;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @JoinColumn(name = "school_year_id", referencedColumnName = "school_year_id")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private SchoolYear schoolYear;
    @JoinColumn(name = "school_class_id", referencedColumnName = "school_class_id")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private SchoolClass schoolClass;
    @JoinColumn(name = "prefect_type_id", referencedColumnName = "prefect_type_id")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private PrefectType prefectType;
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Student student;

    /**
     * default constructor
     */
    public SchoolStudent() {
        this(null);
    }

    /**
     * default constructor with initial parameter
     *
     * @param schoolStudentId
     */
    public SchoolStudent(Integer schoolStudentId) {
        this.schoolStudentId = schoolStudentId;
        this.student = new Student();
        this.schoolYear = new SchoolYear();
        this.schoolClass = new SchoolClass();
    }

    public Integer getSchoolStudentId() {
        return schoolStudentId;
    }

    public void setSchoolStudentId(Integer schoolStudentId) {
        this.schoolStudentId = schoolStudentId;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public boolean getPrefectFlag() {
        return prefectFlag;
    }

    public void setPrefectFlag(boolean prefectFlag) {
        this.prefectFlag = prefectFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    /**
     * @return the pictureUrl
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * @param pictureUrl the pictureUrl to set
     */
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * @return the admissionDate
     */
    public Date getAdmissionDate() {
        return admissionDate;
    }

    /**
     * @param admissionDate the admissionDate to set
     */
    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    /**
     * @return the prefectType
     */
    public PrefectType getPrefectType() {
        return prefectType;
    }

    /**
     * @param prefectType the prefectType to set
     */
    public void setPrefectType(PrefectType prefectType) {
        this.prefectType = prefectType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolStudentId != null ? schoolStudentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolStudent)) {
            return false;
        }
        SchoolStudent other = (SchoolStudent) object;
        if ((this.schoolStudentId == null && other.schoolStudentId != null)
                || (this.schoolStudentId != null && !this.schoolStudentId.equals(other.schoolStudentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.student == null ? ""
                : student.getFullname() + "(" + this.getRegistrationNo() + ")";
    }

    @PrePersist
    @PreUpdate
    protected void trigger() {
        if (this.schoolClass == null || this.schoolClass.getSchoolClassId() == null) {
            this.schoolClass = null;
        }
    }
}