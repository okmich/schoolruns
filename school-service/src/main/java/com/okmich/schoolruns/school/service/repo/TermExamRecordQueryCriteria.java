/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.NestedRelatedEntity;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCString;

/**
 *
 * @author Michael
 */
public class TermExamRecordQueryCriteria extends BaseEntityQueryCriteria {

    public static final String totalTermScore = "totalTermScore";
    public static final String cgpa = "cgpa";
    @RelatedEntity(entityAlias = "a", referencedEntity = "examBatch")
    public static final String examBatchId = "examBatchId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "examBatch")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "schoolTerm")
    public static final String schoolTermId = "schoolTermId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "examBatch")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "schoolTerm")
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    public static final String schoolStudentId = "schoolStudentId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    public static final String registrationNo = "registrationNo";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    @NestedRelatedEntity(nestedEntityAlias = "y", nestedEntityReferenced = "student")
    public static final String studentId = "studentId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    @NestedRelatedEntity(nestedEntityAlias = "y", nestedEntityReferenced = "student")
    public static final String surname = "surname";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    @NestedRelatedEntity(nestedEntityAlias = "x", nestedEntityReferenced = "schoolClass")
    public static final String schoolClassId = "schoolClassId";

    public TermExamRecordQueryCriteria() {
        super(true);
    }

    @Override
    public String getEntityName() {
        return "TermExamRecord";
    }

    /**
     * @param examBatchId the examBatchId to set
     */
    public void setExamBatchId(Integer examBatchId) {
        setParameter(TermExamRecordQueryCriteria.examBatchId, examBatchId);
    }

    /**
     * @param schoolTermId the schoolTermId to set
     */
    public void setSchoolTermId(Integer schoolTermId) {
        setParameter(TermExamRecordQueryCriteria.schoolTermId, schoolTermId);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(TermExamRecordQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param schoolStudentId the schoolStudentId to set
     */
    public void setSchoolStudentId(Integer schoolStudentId) {
        setParameter(TermExamRecordQueryCriteria.schoolStudentId, schoolStudentId);
    }

    /**
     * @param registrationNo the registrationNo to set
     */
    public void setRegistrationNo(String registrationNo) {
        setParameter(TermExamRecordQueryCriteria.registrationNo, registrationNo);
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname, WCString wClause) {
        setParameter(TermExamRecordQueryCriteria.surname, wClause, surname);
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(Integer studentId) {
        setParameter(TermExamRecordQueryCriteria.studentId, studentId);
    }

    /**
     * @param schoolClassId the schoolClassId to set
     */
    public void setSchoolClassId(Integer schoolClassId) {
        setParameter(TermExamRecordQueryCriteria.schoolClassId, schoolClassId);
    }

    /**
     * @param gradeLevelId the gradeLevelId to set
     */
    public void setGradeLevelId(Integer gradeLevelId) {
        //setParameter(TermExamRecordQueryCriteria.schoolClassId, gradeLevelId);
    }
}
