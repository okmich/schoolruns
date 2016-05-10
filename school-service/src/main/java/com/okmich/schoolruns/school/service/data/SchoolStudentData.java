/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.data;

import com.okmich.schoolruns.common.entity.PrefectType;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.common.entity.Student;
import com.okmich.schoolruns.student.service.data.StudentData;
import java.util.Date;

/**
 *
 * @author Michael
 */
public final class SchoolStudentData extends StudentData {

    private Integer schoolStudentId;
    private String registrationNo;
    private boolean prefectFlag;
    private Date admissionDate;
    private Integer schoolYearId;
    private String schoolYear;
    private Integer schoolClassId;
    private String schoolClassCode;
    private String schoolClass;
    private String gradeLevel;
    private String prefectType;
    private Integer prefectTypeId;
    private String pictureUrl;
    private Integer schoolId;
    private String school;

    /**
     *
     */
    public SchoolStudentData() {
    }

    /**
     *
     * @param schoolStudentId
     */
    public SchoolStudentData(Integer schoolStudentId) {
        this.schoolStudentId = schoolStudentId;
    }

    /**
     *
     * @param schoolStudent
     */
    public SchoolStudentData(SchoolStudent schoolStudent) {
        setSchoolStudent(schoolStudent);
    }

    /**
     * @return the schoolStudentId
     */
    public Integer getSchoolStudentId() {
        return schoolStudentId;
    }

    /**
     * @param schoolStudentId the schoolStudentId to set
     */
    public void setSchoolStudentId(Integer schoolStudentId) {
        this.schoolStudentId = schoolStudentId;
    }

    /**
     * @return the registrationNo
     */
    public String getRegistrationNo() {
        return registrationNo;
    }

    /**
     * @param registrationNo the registrationNo to set
     */
    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    /**
     * @return the prefectFlag
     */
    public boolean isPrefectFlag() {
        return prefectFlag;
    }

    /**
     * @param prefectFlag the prefectFlag to set
     */
    public void setPrefectFlag(boolean prefectFlag) {
        this.prefectFlag = prefectFlag;
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

    /**
     * @return the schoolClassId
     */
    public Integer getSchoolClassId() {
        return schoolClassId;
    }

    /**
     * @param schoolClassId the schoolClassId to set
     */
    public void setSchoolClassId(Integer schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    /**
     * @return the schoolClassCode
     */
    public String getSchoolClassCode() {
        return schoolClassCode;
    }

    /**
     * @param schoolClassCode the schoolClassCode to set
     */
    public void setSchoolClassCode(String schoolClassCode) {
        this.schoolClassCode = schoolClassCode;
    }

    /**
     * @return the schoolClass
     */
    public String getSchoolClass() {
        return schoolClass;
    }

    /**
     * @param schoolClass the schoolClass to set
     */
    public void setSchoolClass(String schoolClass) {
        this.schoolClass = schoolClass;
    }

    /**
     * @return the prefectType
     */
    public String getPrefectType() {
        return prefectType;
    }

    /**
     * @param prefectType the prefectType to set
     */
    public void setPrefectType(String prefectType) {
        this.prefectType = prefectType;
    }

    /**
     * @return the prefectTypeId
     */
    public Integer getPrefectTypeId() {
        return prefectTypeId;
    }

    /**
     * @param prefectTypeId the prefectTypeId to set
     */
    public void setPrefectTypeId(Integer prefectTypeId) {
        this.prefectTypeId = prefectTypeId;
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

    /**
     * @return the gradeLevel
     */
    public String getGradeLevel() {
        return gradeLevel;
    }

    /**
     * @param gradeLevel the gradeLevel to set
     */
    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    /**
     * @return the school
     */
    public String getSchool() {
        return school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(String school) {
        this.school = school;
    }

    public SchoolStudent getSchoolStudent() {
        SchoolStudent schoolStudent = new SchoolStudent();
        if (this.getStudentId() != null) {
            schoolStudent.setStudent(new Student(this.getStudentId()));
        }

        schoolStudent.setAdmissionDate(this.admissionDate);
        schoolStudent.setModifiedBy(this.modifiedBy);
        schoolStudent.setModifiedTime(this.modifiedTime);
        schoolStudent.setPrefectFlag(this.prefectFlag);
        if (this.prefectTypeId != null && this.prefectTypeId != 0) {
            schoolStudent.setPrefectType(new PrefectType(this.prefectTypeId));
        }
        schoolStudent.setPictureUrl(this.pictureUrl);
        schoolStudent.setRegistrationNo(this.registrationNo);
        if (this.schoolClassId != null) {
            schoolStudent.setSchoolClass(new SchoolClass(this.schoolClassId));
        }
        schoolStudent.setSchoolStudentId(this.schoolStudentId);
        if (this.schoolYearId != null) {
            schoolStudent.setSchoolYear(new SchoolYear(this.schoolYearId));
        }
        schoolStudent.setStatus(this.status);
        return schoolStudent;
    }

    public void setSchoolStudent(SchoolStudent schoolStudent) {
        if (schoolStudent == null) {
            return;
        }
        this.admissionDate = schoolStudent.getAdmissionDate();
        this.modifiedBy = schoolStudent.getModifiedBy();
        this.modifiedTime = schoolStudent.getModifiedTime();
        this.prefectFlag = schoolStudent.getPrefectFlag();
        PrefectType _prefectType = schoolStudent.getPrefectType();
        if (_prefectType != null) {
            this.prefectType = _prefectType.getDescription();
            this.prefectTypeId = _prefectType.getPrefectTypeId();
        }
        this.pictureUrl = schoolStudent.getPictureUrl();
        this.registrationNo = schoolStudent.getRegistrationNo();
        SchoolClass _schoolClass = schoolStudent.getSchoolClass();
        if (_schoolClass != null) {
            this.schoolClass = _schoolClass.getDescription();
            this.schoolClassCode = _schoolClass.getCode();
            this.schoolClassId = _schoolClass.getSchoolClassId();
            this.gradeLevel = _schoolClass.getGradeLevel() == null
                    ? "" : _schoolClass.getGradeLevel().getDescription();
        }
        this.schoolStudentId = schoolStudent.getSchoolStudentId();
        SchoolYear _schoolYear = schoolStudent.getSchoolYear();
        if (_schoolYear != null) {
            this.schoolYear = _schoolYear.getAcademicYear() == null ? ""
                    : _schoolYear.getAcademicYear().getDescription();
            this.schoolYearId = _schoolYear.getSchoolYearId();
            this.schoolId = _schoolYear.getSchoolId();
        }
        this.status = schoolStudent.getStatus();
        if (schoolStudent.getStudent() != null) {
            setStudent(schoolStudent.getStudent());
        }
    }

    public StudentData getStudentData() {
        StudentData data = new StudentData();

        data.setAddress(this.getAddress());
        data.setBirthDate(this.getBirthDate());
        data.setBloodGroup(this.getBloodGroup());
        data.setCountry(this.getCountry());
        data.setCountryCode(this.getCountryCode());
        data.setFirstname(this.getFirstname());
        data.setGender(this.getGender());
        data.setGenotype(this.getGenotype());
        data.setLanguage(this.getLanguage());
        data.setLanguageId(this.getLanguageId());
        data.setModifiedBy(this.modifiedBy);
        data.setModifiedTime(this.modifiedTime);
        data.setOthernames(this.getOthernames());
        data.setParent(this.getParent());
        data.setParentId(this.getParentId());
        data.setStateOrigin(this.getStateOrigin());
        data.setStateOriginCode(this.getStateOriginCode());
        data.setStudentId(this.getStudentId());
        data.setSurname(this.getSurname());
        data.setStatus(this.status);

        return data;
    }

    public void setStudentData(StudentData studentData) {
        if (studentData == null) {
            return;
        }
        this.setAddress(studentData.getAddress());
        this.setBirthDate(studentData.getBirthDate());
        this.setBloodGroup(studentData.getBloodGroup());
        this.setCountry(studentData.getCountry());
        this.setCountryCode(studentData.getCountryCode());
        this.setFirstname(studentData.getFirstname());
        this.setGender(studentData.getGender());
        this.setGenotype(studentData.getGenotype());
        this.setLanguage(studentData.getLanguage());
        this.setLanguageId(studentData.getLanguageId());
        this.setOthernames(studentData.getOthernames());
        this.setParent(studentData.getParent());
        this.setParentId(studentData.getParentId());
        this.setStateOrigin(studentData.getStateOrigin());
        this.setStateOriginCode(studentData.getStateOriginCode());
        this.setStudentId(studentData.getStudentId());
        this.setSurname(studentData.getSurname());
    }
}
