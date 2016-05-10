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
public class SchoolClassQueryCriteria extends BaseEntityQueryCriteria {

    public static final String code = "code";
    public static final String description = "description";
    @RelatedEntity(entityAlias = "a", referencedEntity = "gradeLevel")
    public static final String gradeLevelId = "gradeLevelId";
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "stream")
    public static final String streamId = "streamId";
    @RelatedEntity(entityAlias = "d", referencedEntity = "schoolSection")
    public static final String schoolSectionId = "schoolSectionId";
    @RelatedEntity(entityAlias = "d", referencedEntity = "schoolSection")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "section")
    public static final String sectionId = "sectionId";

    public SchoolClassQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "SchoolClass";
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        setParameter(SchoolClassQueryCriteria.code, code);
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        setParameter(SchoolClassQueryCriteria.description, description);
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description, WCString wClause) {
        setParameter(SchoolClassQueryCriteria.description, wClause, description);
    }

    /**
     * @param gradeLevelId the gradeLevelId to set
     */
    public void setGradeLevelId(Integer gradeLevelId) {
        setParameter(SchoolClassQueryCriteria.gradeLevelId, gradeLevelId);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(SchoolClassQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param streamId the streamId to set
     */
    public void setStreamId(Integer streamId) {
        setParameter(SchoolClassQueryCriteria.streamId, streamId);
    }

    /**
     * @param _schoolSectionId the schoolSectionId to set
     */
    public void setSchoolSectionId(Integer _schoolSectionId) {
        setParameter(SchoolClassQueryCriteria.schoolSectionId, _schoolSectionId);
    }

    /**
     * @param sectionId the sectionId to set
     */
    public void setSectionId(Integer sectionId) {
        setParameter(SchoolClassQueryCriteria.sectionId, sectionId);
    }
}
