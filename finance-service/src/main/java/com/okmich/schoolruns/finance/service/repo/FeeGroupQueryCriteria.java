/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.RelatedEntity;

/**
 *
 * @author Michael
 * @since Jul 29, 2013
 * @company Okmich Ltd.
 */
public class FeeGroupQueryCriteria extends BaseEntityQueryCriteria {

    public static final String description = "description";
    public static final String statutory = "statutory";
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "gradeLevel")
    public static final String gradeLevelId = "gradeLevelId";
    @RelatedEntity(entityAlias = "c", referencedEntity = "feeType")
    public static final String feeTypeCode = "feeTypeCode";

    public FeeGroupQueryCriteria() {
    }

    /**
     *
     * @return
     */
    @Override
    public String getEntityName() {
        return "FeeGroup";
    }

    public void setStatutory() {
        setParameter(FeeGroupQueryCriteria.statutory);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(FeeGroupQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param gradeLevelId the gradeLevelId to set
     */
    public void setGradeLevelId(Integer gradeLevelId) {
        setParameter(FeeGroupQueryCriteria.gradeLevelId, gradeLevelId);
    }

    /**
     * @param feeTypeCode the feeTypeCode to set
     */
    public void setFeeTypeCode(Integer feeTypeCode) {
        setParameter(FeeGroupQueryCriteria.feeTypeCode, feeTypeCode);
    }
}
