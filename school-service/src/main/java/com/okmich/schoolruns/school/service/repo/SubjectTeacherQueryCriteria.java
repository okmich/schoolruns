/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.RelatedEntity;

/**
 *
 * @author Michael
 */
public class SubjectTeacherQueryCriteria extends BaseEntityQueryCriteria {

    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolSubject")
    public static final String schoolSubjectId = "schoolSubjectId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolSubject")
    public static final String subjectCode = "subjectCode";
    public static final String schoolYearId = "schoolYearId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "employee")
    public static final String employeeId = "employeeId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "employee")
    public static final String schoolId = "schoolId";

    public SubjectTeacherQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "SubjectTeacher";
    }

    /**
     * @param schoolSubjectId the schoolSubjectId to set
     */
    public void setSchoolSubjectId(Integer schoolSubjectId) {
        setParameter(SubjectTeacherQueryCriteria.schoolSubjectId, schoolSubjectId);
    }

    /**
     * @param subjectCode the subjectCode to set
     */
    public void setSubjectCode(String subjectCode) {
        setParameter(SubjectTeacherQueryCriteria.subjectCode, subjectCode);
    }

    /**
     * @param schoolYearId the schoolYearId to set
     */
    public void setSchoolYearId(Integer schoolYearId) {
        setParameter(SubjectTeacherQueryCriteria.schoolYearId, schoolYearId);
    }

    /**
     * @param aEmployeeId the employeeId to set
     */
    public void setEmployeeId(Integer aEmployeeId) {
        setParameter(SubjectTeacherQueryCriteria.employeeId, aEmployeeId);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(SubjectTeacherQueryCriteria.schoolId, schoolId);
    }
}
