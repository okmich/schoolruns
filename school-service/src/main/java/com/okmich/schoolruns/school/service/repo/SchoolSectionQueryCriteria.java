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
public class SchoolSectionQueryCriteria extends BaseEntityQueryCriteria {

    @RelatedEntity(entityAlias = "a", referencedEntity = "section")
    public static final String sectionId = "sectionId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "employee")
    public static final String employeeId = "employeeId";
    @RelatedEntity(entityAlias = "d", referencedEntity = "school")
    public static final String schoolId = "schoolId";

    public SchoolSectionQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "SchoolSection";
    }

    /**
     * @param sectionId the sectionId to set
     */
    public void setSectionId(Integer sectionId) {
        setParameter(SchoolSectionQueryCriteria.sectionId, sectionId);
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(Integer employeeId) {
        setParameter(SchoolSectionQueryCriteria.employeeId, employeeId);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(SchoolSectionQueryCriteria.schoolId, schoolId);
    }
}
