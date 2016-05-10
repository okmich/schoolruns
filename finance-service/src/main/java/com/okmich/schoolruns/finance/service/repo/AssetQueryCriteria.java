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
public class AssetQueryCriteria extends BaseEntityQueryCriteria {

    public static final String description = "description";
    public static final String acquireDate = "acquireDate";
    @RelatedEntity(entityAlias = "a", referencedEntity = "school")
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "assetType")
    public static final String assetTypeId = "assetTypeId";

    public AssetQueryCriteria() {
        super(true);
    }

    @Override
    public String getEntityName() {
        return "Asset";
    }

    /**
     *
     * @param description
     * @param wclause
     */
    public void setDescription(String description, WCString wclause) {
        setParameter(AssetQueryCriteria.description, wclause, description);
    }

    /**
     *
     * @param val1
     * @param wclause
     * @param val2
     */
    public void setAcquireDate(Date val1, WCDate wclause, Date val2) {
        setParameter(AssetQueryCriteria.acquireDate, wclause, val1, val2);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(AssetQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param assetTypeId the assetTypeId to set
     */
    public void setAssetTypeId(Integer assetTypeId) {
        setParameter(AssetQueryCriteria.assetTypeId, assetTypeId);
    }
}
