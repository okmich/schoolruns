/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.NestedRelatedEntity;
import com.okmich.common.entity.criteria.RelatedEntity;

/**
 *
 * @author Michael
 */
public class ExamQueryCriteria extends BaseEntityQueryCriteria {

    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolTerm")
    public static final String schoolTermId = "schoolTermId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolTerm")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "term")
    public static final String termId = "termId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolClass")
    public static final String schoolClassId = "schoolClassId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolClass")
    public static final String code = "code";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolClass")
    @NestedRelatedEntity(nestedEntityAlias = "y", nestedEntityReferenced = "gradeLevel")
    public static final String gradeLevelId = "gradeLevelId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolClass")
    @NestedRelatedEntity(nestedEntityAlias = "x", nestedEntityReferenced = "school")
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "schoolSubject")
    @NestedRelatedEntity(nestedEntityAlias = "w", nestedEntityReferenced = "subject")
    public static final String subjectId = "subjectId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "schoolSubject")
    public static final String schoolSubjectId = "schoolSubjectId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "schoolSubject")
    public static final String subjectCode = "subjectCode";
    @RelatedEntity(entityAlias = "d", referencedEntity = "examBatch")
    public static final String examBatchId = "examBatchId";

    /**
     *
     */
    public ExamQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "Exam";
    }

    /**
     * @param _schoolTermId the schoolTermId to set
     */
    public void setSchoolTermId(Integer _schoolTermId) {
        setParameter(ExamQueryCriteria.schoolTermId, _schoolTermId);
    }

    /**
     * @param _termId the termId to set
     */
    public void setTermId(Integer _termId) {
        setParameter(ExamQueryCriteria.termId, _termId);
    }

    /**
     * @param _schoolClassId the schoolClassId to set
     */
    public void setSchoolClassId(Integer _schoolClassId) {
        setParameter(ExamQueryCriteria.schoolClassId, _schoolClassId);
    }

    /**
     * @param _code the code to set
     */
    public void setSchoolClassCode(String _code) {
        setParameter(ExamQueryCriteria.code, _code);
    }

    /**
     * @param _gradeLevelId the gradeLevelId to set
     */
    public void setGradeLevelId(Integer _gradeLevelId) {
        setParameter(ExamQueryCriteria.gradeLevelId, _gradeLevelId);
    }

    /**
     * @param _schoolId the schoolId to set
     */
    public void setSchoolId(Integer _schoolId) {
        setParameter(ExamQueryCriteria.schoolId, _schoolId);
    }

    /**
     * @param _subjectId the subjectId to set
     */
    public void setSubjectId(Integer _subjectId) {
        setParameter(ExamQueryCriteria.subjectId, _subjectId);
    }

    /**
     * @param _schoolSubjectId the schoolSubjectId to set
     */
    public void setSchoolSubjectId(Integer _schoolSubjectId) {
        setParameter(ExamQueryCriteria.schoolSubjectId, _schoolSubjectId);
    }

    /**
     * @param _subjectCode the subjectCode to set
     */
    public void setSubjectCode(String _subjectCode) {
        setParameter(ExamQueryCriteria.subjectCode, _subjectCode);
    }

    /**
     * @param _examBatchId the examBatchId to set
     */
    public void setExamBatchId(Integer _examBatchId) {
        setParameter(ExamQueryCriteria.examBatchId, _examBatchId);
    }
}
