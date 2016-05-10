/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.WCString;

/**
 *
 * @author Michael
 */
public class HolidayQueryCriteria extends BaseEntityQueryCriteria {

    public static final String description = "description";
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
     * @param _schoolYearId the schoolYearId to set
     */
    public void setSchoolYearId(Integer _schoolYearId) {
        setParameter(HolidayQueryCriteria.schoolYearId, _schoolYearId);
    }
}