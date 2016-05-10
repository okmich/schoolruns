/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCString;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
public class StoreItemQueryCriteria extends BaseEntityQueryCriteria {

    public static final String name = "name";
    public static final String description = "description";
    @RelatedEntity(entityAlias = "a", referencedEntity = "store")
    public static final String storeId = "storeId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "storeItemType")
    public static final String storeItemTypeCode = "storeItemTypeCode";

    public StoreItemQueryCriteria() {
        super(true);
    }

    @Override
    public String getEntityName() {
        return "StoreItem";
    }

    /**
     *
     * @param name
     * @param wclause
     */
    public void setName(String name, WCString wclause) {
        setParameter(StoreItemQueryCriteria.name, wclause, name);
    }

    /**
     *
     *
     * @param description
     * @param wclause
     */
    public void setDescription(String description, WCString wclause) {
        setParameter(StoreItemQueryCriteria.description, wclause, name);
    }

    /**
     * @param storeId the storeId to set
     */
    public void setStoreId(Integer storeId) {
        setParameter(StoreItemQueryCriteria.storeId, storeId);
    }

    /**
     * @param storeItemTypeCode the storeItemTypeCode to set
     */
    public void setStoreItemTypeCode(String storeItemTypeCode) {
        setParameter(StoreItemQueryCriteria.storeItemTypeCode, storeItemTypeCode);
    }
}