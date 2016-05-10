/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.NestedRelatedEntity;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCBase;
import com.okmich.common.entity.criteria.WCDate;
import com.okmich.common.entity.criteria.WCString;
import java.util.Date;

/**
 *
 * @author Michael
 */
public class SchoolStudentQueryCriteria extends BaseEntityQueryCriteria {

    public static final String registrationNo = "registrationNo";
    public static final String admissionDate = "admissionDate";
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolYear")
    public static final String schoolId = "schoolId";
    @NestedRelatedEntity(nestedEntityAlias = "w", nestedEntityReferenced = "school")
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolYear")
    public static final String name = "name";
    @NestedRelatedEntity(nestedEntityAlias = "x", nestedEntityReferenced = "academicYear")
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolYear")
    public static final String yearId = "yearId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolYear")
    public static final String schoolYearId = "schoolYearId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolClass")
    public static final String schoolClassId = "schoolClassId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "prefectType")
    public static final String prefectTypeId = "prefectTypeId";
    @RelatedEntity(entityAlias = "d", referencedEntity = "student")
    public static final String studentId = "studentId";
    @RelatedEntity(entityAlias = "d", referencedEntity = "student")
    public static final String surname = "surname";
    @RelatedEntity(entityAlias = "d", referencedEntity = "student")
    public static final String firstname = "firstname";
    @RelatedEntity(entityAlias = "d", referencedEntity = "student")
    public static final String gender = "gender";
    @RelatedEntity(entityAlias = "d", referencedEntity = "student")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "state")
    public static final String stateCode = "stateCode";
    @RelatedEntity(entityAlias = "d", referencedEntity = "student")
    @NestedRelatedEntity(nestedEntityAlias = "v", nestedEntityReferenced = "parent")
    public static final String phoneNumber = "phoneNumber";
    @RelatedEntity(entityAlias = "f", referencedEntity = "schoolClass")
    @NestedRelatedEntity(nestedEntityAlias = "y", nestedEntityReferenced = "gradeLevel")
    public static final String gradeLevelId = "gradeLevelId";

    public SchoolStudentQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "SchoolStudent";
    }

    /**
     * @param registrationNo the registrationNo to set
     */
    public void setRegistrationNo(String registrationNo) {
        setParameter(SchoolStudentQueryCriteria.registrationNo, registrationNo);
    }

    /**
     * @param registrationNo the registrationNo to set
     * @param wclause
     */
    public void setRegistrationNo(String registrationNo, WCString wclause) {
        setParameter(SchoolStudentQueryCriteria.registrationNo, wclause, registrationNo);
    }

    /**
     * @param admissionDate the admissionDate to set
     */
    public void setAdmissionDate(Date admissionDate) {
        setParameter(SchoolStudentQueryCriteria.admissionDate, admissionDate);
    }

    /**
     *
     * @param admissionDate
     * @param wClause
     * @param aAdmissionDate
     */
    public void setAdmissionDate(Date admissionDate, WCDate wClause, Date aAdmissionDate) {
        setParameter(SchoolStudentQueryCriteria.admissionDate, wClause, admissionDate, aAdmissionDate);
    }

    /**
     * @param yearId the yearId to set
     */
    public void setYearId(Integer yearId) {
        setParameter(SchoolStudentQueryCriteria.yearId, yearId);
    }

    /**
     * @param schoolYearId the schoolYearId to set
     */
    public void setSchoolYearId(Integer schoolYearId) {
        setParameter(SchoolStudentQueryCriteria.schoolYearId, schoolYearId);
    }

    /**
     *
     * @param wClause
     */
    public void setSchoolClassId(WCBase wClause) {
        setParameter(SchoolStudentQueryCriteria.schoolClassId, wClause);
    }

    /**
     * @param schoolClassId the schoolClassId to set
     */
    public void setSchoolClassId(Integer schoolClassId) {
        setParameter(SchoolStudentQueryCriteria.schoolClassId, schoolClassId);
    }

    /**
     * @param prefectTypeId the prefectTypeId to set
     */
    public void setPrefectTypeId(Integer prefectTypeId) {
        setParameter(SchoolStudentQueryCriteria.prefectTypeId, prefectTypeId);
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(Integer studentId) {
        setParameter(SchoolStudentQueryCriteria.studentId, studentId);
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        setParameter(SchoolStudentQueryCriteria.surname, surname);
    }

    /**
     *
     * @param surname
     * @param wClause
     */
    public void setSurname(String surname, WCString wClause) {
        setParameter(SchoolStudentQueryCriteria.surname, wClause, surname);
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        setParameter(SchoolStudentQueryCriteria.firstname, firstname);
    }

    /**
     *
     * @param firstname
     * @param wClause
     */
    public void setFirstname(String firstname, WCString wClause) {
        setParameter(SchoolStudentQueryCriteria.firstname, wClause, firstname);
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        setParameter(SchoolStudentQueryCriteria.gender, gender);
    }

    /**
     * @param stateCode the stateCode to set
     */
    public void setStateCode(String stateCode) {
        setParameter(SchoolStudentQueryCriteria.stateCode, stateCode);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(SchoolStudentQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param _schoolName the name to set
     */
    public void setSchoolName(String _schoolName) {
        setParameter(SchoolStudentQueryCriteria.name, _schoolName);
    }

    /**
     * @param _schoolName the name to set
     */
    public void setSchoolName(String _schoolName, WCString wClause) {
        setParameter(SchoolStudentQueryCriteria.name, wClause, _schoolName);
    }

    /**
     * @param gradeLevelId the gradeLevelId to set
     */
    public void setGradeLevelId(Integer gradeLevelId) {
        setParameter(SchoolStudentQueryCriteria.gradeLevelId, gradeLevelId);
    }

    /**
     * @param _phoneNumber the _phoneNumber to set
     */
    public void setParentNumber(String _phoneNumber) {
        setParameter(SchoolStudentQueryCriteria.phoneNumber, _phoneNumber);
    }
}