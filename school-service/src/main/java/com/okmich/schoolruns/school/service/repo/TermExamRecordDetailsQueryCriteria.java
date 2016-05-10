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
public class TermExamRecordDetailsQueryCriteria extends BaseEntityQueryCriteria {

    public static final String finalScore = "finalScore";
    @RelatedEntity(entityAlias = "a", referencedEntity = "examScore")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "exam")
    public static final String examId = "examId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "termExamRecord")
    public static final String termExamRecordId = "termExamRecordId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "termExamRecord")
    @NestedRelatedEntity(nestedEntityAlias = "y", nestedEntityReferenced = "examBatch")
    public static final String examBatchId = "examBatchId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "termExamRecord")
    @NestedRelatedEntity(nestedEntityAlias = "x", nestedEntityReferenced = "schoolStudent")
    public static final String schoolStudentId = "schoolStudentId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "schoolSubject")
    public static final String schoolSubjectId = "schoolSubjectId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "schoolSubject")
    public static final String subjectCode = "subjectCode";
    @RelatedEntity(entityAlias = "c", referencedEntity = "schoolSubject")
    @NestedRelatedEntity(nestedEntityAlias = "w", nestedEntityReferenced = "gradeLevel")
    public static final String gradeLevelId = "gradeLevelId";
    @RelatedEntity(entityAlias = "d", referencedEntity = "gradeBand")
    public static final String gradeBandCode = "gradeBandCode";

    public TermExamRecordDetailsQueryCriteria() {
        super(true);
    }

    @Override
    public String getEntityName() {
        return "TermExamRecordDetails";
    }

    /**
     * @param examId the examId to set
     */
    public void setExamId(Integer examId) {
        setParameter(TermExamRecordDetailsQueryCriteria.examId, examId);
    }

    /**
     * @param termExamRecordId the termExamRecordId to set
     */
    public void setTermExamRecordId(Integer termExamRecordId) {
        setParameter(TermExamRecordDetailsQueryCriteria.termExamRecordId, termExamRecordId);
    }

    /**
     * @param examBatchId the examBatchId to set
     */
    public void setExamBatchId(Integer examBatchId) {
        setParameter(TermExamRecordDetailsQueryCriteria.examBatchId, examBatchId);
    }

    /**
     * @param schoolStudentId the schoolStudentId to set
     */
    public void setSchoolStudentId(Integer schoolStudentId) {
        setParameter(TermExamRecordDetailsQueryCriteria.schoolStudentId, schoolStudentId);
    }

    /**
     * @param schoolSubjectId the schoolSubjectId to set
     */
    public void setSchoolSubjectId(Integer schoolSubjectId) {
        setParameter(TermExamRecordDetailsQueryCriteria.schoolSubjectId, schoolSubjectId);
    }

    /**
     * @param subjectCode the subjectCode to set
     */
    public void setSubjectCode(String subjectCode) {
        setParameter(TermExamRecordDetailsQueryCriteria.subjectCode, subjectCode);
    }

    /**
     * @param gradeLevelId the gradeLevelId to set
     */
    public void setGradeLevelId(Integer gradeLevelId) {
        setParameter(TermExamRecordDetailsQueryCriteria.gradeLevelId, gradeLevelId);
    }

    /**
     * @param gradeBandCode the gradeBandCode to set
     */
    public void setGradeBandCode(String gradeBandCode) {
        setParameter(TermExamRecordDetailsQueryCriteria.gradeBandCode, gradeBandCode);
    }
}