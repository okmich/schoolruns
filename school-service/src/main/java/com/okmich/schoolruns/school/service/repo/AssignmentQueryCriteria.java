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
public class AssignmentQueryCriteria extends BaseEntityQueryCriteria {

    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolTerm")
    public static final String schoolTermId = "schoolTermId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolTerm")
    @NestedRelatedEntity(nestedEntityAlias = "w", nestedEntityReferenced = "term")
    public static final String termId = "termId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolSubject")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "subject")
    public static final String subjectId = "subjectId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolSubject")
    public static final String schoolSubjectId = "schoolSubjectId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolSubject")
    public static final String subjectCode = "subjectCode";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolSubject")
    @NestedRelatedEntity(nestedEntityAlias = "y", nestedEntityReferenced = "gradeLevel")
    public static final String gradeLevelId = "gradeLevelId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolSubject")
    @NestedRelatedEntity(nestedEntityAlias = "x", nestedEntityReferenced = "school")
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "assignmentType")
    public static final String assignmentTypeCode = "assignmentTypeCode";
    @RelatedEntity(entityAlias = "d", referencedEntity = "schoolClass")
    public static final String schoolClassId = "schoolClassId";

    public AssignmentQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "Assignment";
    }

    /**
     * @param _schoolTermId the schoolTermId to set
     */
    public void setSchoolTermId(Integer _schoolTermId) {
        setParameter(AssignmentQueryCriteria.schoolTermId, _schoolTermId);
    }

    /**
     * @param _termId the _termId to set
     */
    public void setTermId(Integer _termId) {
        setParameter(AssignmentQueryCriteria.termId, _termId);
    }

    /**
     * @param _subjectId the subjectId to set
     */
    public void setSubjectId(Integer _subjectId) {
        setParameter(AssignmentQueryCriteria.subjectId, _subjectId);
    }

    /**
     * @param _schoolSubjectId the schoolSubjectId to set
     */
    public void setSchoolSubjectId(Integer _schoolSubjectId) {
        setParameter(AssignmentQueryCriteria.schoolSubjectId, _schoolSubjectId);
    }

    /**
     * @param _subjectCode the subjectCode to set
     */
    public void setSubjectCode(String _subjectCode) {
        setParameter(AssignmentQueryCriteria.subjectCode, _subjectCode);
    }

    /**
     * @param _gradeLevelId the gradeLevelId to set
     */
    public void setGradeLevelId(Integer _gradeLevelId) {
        setParameter(AssignmentQueryCriteria.gradeLevelId, _gradeLevelId);
    }

    /**
     * @param _schoolId the schoolId to set
     */
    public void setSchoolId(Integer _schoolId) {
        setParameter(AssignmentQueryCriteria.schoolId, _schoolId);
    }

    /**
     * @param _assignmentTypeCode the assignmentTypeCode to set
     */
    public void setAssignmentTypeCode(String _assignmentTypeCode) {
        setParameter(AssignmentQueryCriteria.assignmentTypeCode, _assignmentTypeCode);
    }

    /**
     * @param _schoolClassId the schoolClassId to set
     */
    public void setSchoolClassId(Integer _schoolClassId) {
        setParameter(AssignmentQueryCriteria.schoolClassId, _schoolClassId);
    }
}
