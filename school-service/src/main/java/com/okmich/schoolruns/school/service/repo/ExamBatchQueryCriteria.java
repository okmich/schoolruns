/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.NestedRelatedEntity;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCBase;

/**
 *
 * @author Michael
 */
public class ExamBatchQueryCriteria extends BaseEntityQueryCriteria {

    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolTerm")
    public static final String schoolTermId = "schoolTermId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolTerm")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "term")
    public static final String termId = "termId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolSection")
    public static final String schoolSectionId = "schoolSectionId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "examType")
    public static final String examTypeCode = "examTypeCode";

    public ExamBatchQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "ExamBatch";
    }

    /**
     * @param _schoolTermId the schoolTermId to set
     */
    public void setSchoolTermId(Integer _schoolTermId) {
        setParameter(ExamBatchQueryCriteria.schoolTermId, _schoolTermId);
    }

    /**
     * @param _termId the termId to set
     */
    public void setTermId(Integer _termId) {
        setParameter(ExamBatchQueryCriteria.termId, _termId);
    }

    /**
     * @param _examTypeCode the examTypeCode to set
     */
    public void setExamTypeCode(String _examTypeCode) {
        setParameter(ExamBatchQueryCriteria.examTypeCode, _examTypeCode);
    }

    /**
     * @param _schoolSectionId the schoolSectionId to set
     */
    public void setSchoolSectionId(Integer _schoolSectionId) {
        setParameter(ExamBatchQueryCriteria.schoolSectionId, _schoolSectionId);
    }

    /**
     * @param wclause the WCBase to set
     */
    public void setSchoolSectionId(WCBase wclause) {
        setParameter(ExamBatchQueryCriteria.schoolSectionId, wclause);
    }
}