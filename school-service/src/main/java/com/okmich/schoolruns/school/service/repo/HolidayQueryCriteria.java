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
public class HolidayQueryCriteria extends BaseEntityQueryCriteria {

    public static final String description = "description";
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolTerm")
    public static final String schoolTermId = "schoolTermId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolTerm")
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolTerm")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "schoolYear")
    public static final String schoolYearId = "schoolYearId";

    public HolidayQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "Holiday";
    }

    /**
     * @param _description the description to set
     */
    public void setDescription(String _description, WCString wclause) {
        setParameter(HolidayQueryCriteria.description, wclause, _description);
    }

    /**
     * @param _schoolTermId the schoolTermId to set
     */
    public void setSchoolTermId(Integer _schoolTermId) {
        setParameter(HolidayQueryCriteria.schoolTermId, _schoolTermId);
    }

    /**
     * @param _schoolId the schoolId to set
     */
    public void setSchoolId(Integer _schoolId) {
        setParameter(HolidayQueryCriteria.schoolId, _schoolId);
    }

    /**
     * @param _schoolYearId the schoolYearId to set
     */
    public void setSchoolYearId(Integer _schoolYearId) {
        setParameter(HolidayQueryCriteria.schoolYearId, _schoolYearId);
    }
}