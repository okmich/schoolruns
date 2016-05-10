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
public class SchoolClassTeacherQueryCriteria extends BaseEntityQueryCriteria {

    @RelatedEntity(entityAlias = "a", referencedEntity = "employee")
    public static final String employeeId = "employeeId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "employee")
    public static final String staffNumber = "staffNumber";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolYear")
    public static final String schoolYearId = "schoolYearId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "schoolClass")
    public static final String schoolClassId = "schoolClassId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "schoolClass")
    public static final String code = "code";
    @RelatedEntity(entityAlias = "d", referencedEntity = "allocationType")
    public static final String allocationTypeId = "allocationTypeId";

    public SchoolClassTeacherQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "SchoolClassTeacher";
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(Integer employeeId) {
        setParameter(SchoolClassTeacherQueryCriteria.employeeId, employeeId);
    }

    /**
     * @param staffNumber the staffNumber to set
     */
    public void setStaffNumber(String staffNumber) {
        setParameter(SchoolClassTeacherQueryCriteria.staffNumber, staffNumber);
    }

    /**
     * @param schoolYearId the schoolYearId to set
     */
    public void setSchoolYearId(Integer schoolYearId) {
        setParameter(SchoolClassTeacherQueryCriteria.schoolYearId, schoolYearId);
    }

    /**
     * @param schoolClassId the schoolClassId to set
     */
    public void setSchoolClassId(Integer schoolClassId) {
        setParameter(SchoolClassTeacherQueryCriteria.schoolClassId, schoolClassId);
    }

    /**
     * @param code the code to set
     */
    public void setSchoolClassCode(String code) {
        setParameter(SchoolClassTeacherQueryCriteria.code, code);
    }

    /**
     * @param allocationTypeId the allocationTypeId to set
     */
    public void setAllocationTypeId(Integer allocationTypeId) {
        setParameter(SchoolClassTeacherQueryCriteria.allocationTypeId, allocationTypeId);
    }
}
