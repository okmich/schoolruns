/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
public class StoreQueryCriteria extends BaseEntityQueryCriteria {

    public static final String schoolId = "schoolId";

    public StoreQueryCriteria() {
        super(true);
    }

    @Override
    public String getEntityName() {
        return "Store";
    }


    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(StoreQueryCriteria.schoolId, schoolId);
    }
}
