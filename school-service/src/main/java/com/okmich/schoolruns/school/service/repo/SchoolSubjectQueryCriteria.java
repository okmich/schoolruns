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
public class SchoolSubjectQueryCriteria extends BaseEntityQueryCriteria {

    public static final String subjectCode = "subjectCode";
    @RelatedEntity(entityAlias = "a", referencedEntity = "subject")
    public static final String subjectId = "subjectId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "gradeLevel")
    public static final String gradeLevelId = "gradeLevelId";
    public static final String schoolId = "schoolId";

    public SchoolSubjectQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "SchoolSubject";
    }

    /**
     * @param subjectCode the subjectCode to set
     */
    public void setSubjectCode(String subjectCode) {
        setParameter(SchoolSubjectQueryCriteria.subjectCode, subjectCode);
    }

    /**
     * @param subjectId the subjectId to set
     */
    public void setSubjectId(Integer subjectId) {
        setParameter(SchoolSubjectQueryCriteria.subjectId, subjectId);
    }

    /**
     * @param gradeLevelId the gradeLevelId to set
     */
    public void setGradeLevelId(Integer gradeLevelId) {
        setParameter(SchoolSubjectQueryCriteria.gradeLevelId, gradeLevelId);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(SchoolSubjectQueryCriteria.schoolId, schoolId);
    }
}