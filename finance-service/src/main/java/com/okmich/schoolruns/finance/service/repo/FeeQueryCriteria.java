/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;

/**
 *
 * @author Michael
 * @since Jul 29, 2013
 * @company Okmich Ltd.
 */
public class FeeQueryCriteria extends BaseEntityQueryCriteria {

    private static final String schoolId = "schoolId";
    private static final String feeCategory = "feeCategory";

    public FeeQueryCriteria() {
    }

    /**
     *
     * @return
     */
    @Override
    public String getEntityName() {
        return "Fee";
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(FeeQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param feeCategory the feeCategory to set
     */
    public void setFeeCategory(String feeCategory) {
        setParameter(FeeQueryCriteria.feeCategory, feeCategory);
    }
}
