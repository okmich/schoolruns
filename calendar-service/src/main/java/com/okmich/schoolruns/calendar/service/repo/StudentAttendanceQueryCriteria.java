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
public class StudentAttendanceQueryCriteria extends BaseEntityQueryCriteria {

    public static final String attendDate = "attendDate";
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolTerm")
    public static final String schoolTermId = "schoolTermId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "schoolTerm")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "schoolYear")
    public static final String schoolYearId = "schoolYearId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    public static final String schoolStudentId = "schoolStudentId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    @NestedRelatedEntity(nestedEntityAlias = "y", nestedEntityReferenced = "student")
    public static final String studentId = "studentId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    @NestedRelatedEntity(nestedEntityAlias = "x", nestedEntityReferenced = "school")
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "schoolStudent")
    @NestedRelatedEntity(nestedEntityAlias = "w", nestedEntityReferenced = "schoolClass")
    public static final String schoolClassId = "schoolClassId";

    public StudentAttendanceQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "StudentAttendance";
    }

    /**
     * @param date1 the attendDate to set
     */
    public void setAttendDate(Date date1) {
        setParameter(StudentAttendanceQueryCriteria.attendDate, date1);
    }

    /**
     *
     * @param date1
     * @param wClause
     * @param date2
     */
    public void setAttendDate(Date date1, WCDate wClause, Date date2) {
        setParameter(StudentAttendanceQueryCriteria.attendDate, wClause, date1, date2);
    }

    /**
     * @param schoolTermId the schoolTermId to set
     */
    public void setSchoolTermId(Integer schoolTermId) {
        setParameter(StudentAttendanceQueryCriteria.schoolTermId, schoolTermId);
    }

    /**
     * @param schoolYearId the schoolYearId to set
     */
    public void setSchoolYearId(Integer schoolYearId) {
        setParameter(StudentAttendanceQueryCriteria.schoolYearId, schoolYearId);
    }

    /**
     * @param schoolStudentId the schoolStudentId to set
     */
    public void setSchoolStudentId(Integer schoolStudentId) {
        setParameter(StudentAttendanceQueryCriteria.schoolStudentId, schoolStudentId);
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(Integer studentId) {
        setParameter(StudentAttendanceQueryCriteria.studentId, studentId);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(StudentAttendanceQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param schoolClassId the schoolClassId to set
     */
    public void setSchoolClassId(Integer schoolClassId) {
        setParameter(StudentAttendanceQueryCriteria.schoolClassId, schoolClassId);
    }
}