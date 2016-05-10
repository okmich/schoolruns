/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.NestedRelatedEntity;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCString;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
public class StoreActivityQueryCriteria extends BaseEntityQueryCriteria {

    @RelatedEntity(entityAlias = "a", referencedEntity = "storeItem")
    public static final String storeItemId = "storeItemId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "storeItem")
    private static String name = "name";
    @RelatedEntity(entityAlias = "a", referencedEntity = "storeItem")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "store")
    public static final String storeId = "storeId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "storeActivityType")
    public static final String storeActivityTypeCode = "storeActivityTypeCode";

    public StoreActivityQueryCriteria() {
        super(true);
    }

    @Override
    public String getEntityName() {
        return "StoreActivity";
    }

    /**
     * @param aName the name to set
     */
    public void setItemName(String aName, WCString wclause) {
        setParameter(StoreActivityQueryCriteria.name, wclause, aName);
    }

    /**
     * @param storeItemId the storeItemId to set
     */
    public void setStoreItemId(Integer storeItemId) {
        setParameter(StoreActivityQueryCriteria.storeItemId, storeItemId);
    }

    /**
     * @param storeId the storeId to set
     */
    public void setStoreId(Integer storeId) {
        setParameter(StoreActivityQueryCriteria.storeId, storeId);
    }

    /**
     * @param storeActivityTypeCode the storeActivityTypeCode to set
     */
    public void setStoreActivityTypeCode(String storeActivityTypeCode) {
        setParameter(StoreActivityQueryCriteria.storeActivityTypeCode, storeActivityTypeCode);
    }
}