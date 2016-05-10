/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.NestedRelatedEntity;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCDate;
import java.util.Date;

/**
 *
 * @author Michael
 */
public class EmployeeAttendanceQueryCriteria extends BaseEntityQueryCriteria {

    public static final String attendDate = "attendDate";
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolTerm")
    public static final String schoolTermId = "schoolTermId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolTerm")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "schoolYear")
    public static final String schoolYearId = "schoolYearId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolTerm")
    @NestedRelatedEntity(nestedEntityAlias = "y", nestedEntityReferenced = "school")
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "employee")
    public static final String employeeId = "employeeId";

    public EmployeeAttendanceQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "EmployeeAttendance";
    }

    /**
     * @param date1 the attendDate to set
     */
    public void setAttendDate(Date date1) {
        setParameter(EmployeeAttendanceQueryCriteria.attendDate, date1);
    }

    /**
     *
     * @param date1
     * @param wClause
     * @param date2
     */
    public void setAttendDate(Date date1, WCDate wClause, Date date2) {
        setParameter(EmployeeAttendanceQueryCriteria.attendDate, wClause, date1, date2);
    }

    /**
     * @param schoolTermId the schoolTermId to set
     */
    public void setSchoolTermId(Integer schoolTermId) {
        setParameter(EmployeeAttendanceQueryCriteria.schoolTermId, schoolTermId);
    }

    /**
     * @param schoolYearId the schoolYearId to set
     */
    public void setSchoolYearId(Integer schoolYearId) {
        setParameter(EmployeeAttendanceQueryCriteria.schoolYearId, schoolYearId);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(EmployeeAttendanceQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(Integer employeeID) {
        setParameter(EmployeeAttendanceQueryCriteria.employeeId, employeeID);
    }
}
