/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCDate;
import com.okmich.common.entity.criteria.WCString;
import java.util.Date;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
public class LiabilityQueryCriteria extends BaseEntityQueryCriteria {

    public static final String description = "description";
    public static final String assumeDate = "assumeDate";
    @RelatedEntity(entityAlias = "a", referencedEntity = "school")
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "liabilityType")
    public static final String liabilityTypeId = "liabilityTypeId";

    public LiabilityQueryCriteria() {
        super(true);
    }

    @Override
    public String getEntityName() {
        return "Liability";
    }

    /**
     *
     * @param description
     * @param wclause
     */
    public void setDescription(String description, WCString wclause) {
        setParameter(LiabilityQueryCriteria.description, wclause, description);
    }

    /**
     *
     * @param val1
     * @param wclause
     * @param val2
     */
    public void setAssumeDateDate(Date val1, WCDate wclause, Date val2) {
        setParameter(LiabilityQueryCriteria.assumeDate, wclause, val1, val2);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(LiabilityQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param liabilityTypeId the liabilityTypeId to set
     */
    public void setLiabilityTypeId(Integer liabilityTypeId) {
        setParameter(LiabilityQueryCriteria.liabilityTypeId, liabilityTypeId);
    }
}
