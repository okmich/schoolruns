/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "school_class")
public class SchoolClass implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "school_class_id")
    private Integer schoolClassId;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "same_subject_flag")
    private boolean sameSubjectFlag;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "school_id")
    @Basic(optional = false)
    private Integer schoolId;
    @JoinColumn(name = "grade_level_id", referencedColumnName = "grade_level_id")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private GradeLevel gradeLevel;
    @JoinColumn(name = "stream_id", referencedColumnName = "stream_id")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Stream stream;
    @JoinColumn(name = "school_section_id", referencedColumnName = "school_section_id")
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private SchoolSection schoolSection;

    public SchoolClass() {
        this.sameSubjectFlag = true;
    }

    public SchoolClass(Integer schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public Integer getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(Integer schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the sameSubjectFlag
     */
    public boolean isSameSubjectFlag() {
        return sameSubjectFlag;
    }

    /**
     * @param sameSubjectFlag the sameSubjectFlag to set
     */
    public void setSameSubjectFlag(boolean sameSubjectFlag) {
        this.sameSubjectFlag = sameSubjectFlag;
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

    public GradeLevel getGradeLevel() {
        if (this.gradeLevel == null) {
            this.gradeLevel = new GradeLevel();
        }
        return gradeLevel;
    }

    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Stream getStream() {
        if (this.stream == null) {
            this.stream = new Stream();
        }
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }

    /**
     * @return the schoolSection
     */
    public SchoolSection getSchoolSection() {
        if (this.schoolSection == null) {
            this.schoolSection = new SchoolSection();
        }
        return schoolSection;
    }

    /**
     * @param schoolSection the schoolSection to set
     */
    public void setSchoolSection(SchoolSection schoolSection) {
        this.schoolSection = schoolSection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolClassId != null ? schoolClassId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolClass)) {
            return false;
        }
        SchoolClass other = (SchoolClass) object;
        if ((this.schoolClassId == null && other.schoolClassId != null)
                || (this.schoolClassId != null && !this.schoolClassId.equals(other.schoolClassId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.SchoolClass[ schoolClassId=" + schoolClassId + " ]";
    }
}