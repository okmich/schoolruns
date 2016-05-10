/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.schoolruns.common.entity.Store;
import com.okmich.schoolruns.common.entity.StoreActivity;
import com.okmich.schoolruns.common.entity.StoreActivityType;
import com.okmich.schoolruns.common.entity.StoreItem;
import com.okmich.schoolruns.common.entity.StoreItemType;
import com.okmich.schoolruns.finance.service.repo.StoreActivityQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.StoreItemQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.StoreQueryCriteria;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
public interface StoreInventoryService extends Serializable {

    /**
     *
     *
     * @param store
     * @return
     * @throws BusinessException
     */
    Store createStore(Store store) throws BusinessException;

    /**
     *
     *
     * @param storeId
     * @return
     */
    Store findStore(Integer storeId);

    /**
     * return all stores that are owned the school with id {@code schoolId)
     *
     * @param schoolId - id of school
     * @return List<Store> - all stores owned the school with id {@code schoolId)
     */
    List<Store> findSchoolStores(Integer schoolId);

    /**
     *
     *
     * @param criteria
     * @return List<Store>
     */
    List<Store> findStores(StoreQueryCriteria criteria);

    /**
     *
     *
     * @param store
     * @return
     * @throws BusinessException
     */
    Store saveStore(Store store) throws BusinessException;

    /**
     *
     * @param storeItemType
     * @return StoreItemType
     * @throws BusinessException
     */
    StoreItemType saveStoreItemType(StoreItemType storeItemType) throws BusinessException;

    /**
     *
     * @param storeItemId
     * @return StoreItemType
     */
    StoreItemType findStoreItemType(Integer storeItemTypeId);

    /**
     *
     * @return List<StoreItemType>
     */
    List<StoreItemType> findStoreItemTypes();

    /**
     *
     * @param storeActivityType
     * @return StoreActivityType
     * @throws BusinessException
     */
    StoreActivityType saveStoreActivityType(StoreActivityType storeActivityType) throws BusinessException;

    /**
     *
     * @param storeActivityTypeId
     * @return StoreActivityType
     */
    StoreActivityType findStoreActivityType(String storeActivityTypeId);

    /**
     *
     * @return List<StoreActivityType>
     */
    List<StoreActivityType> findStoreActivityTypes();

    /**
     *
     *
     * @param storeItem
     * @return
     * @throws BusinessException
     */
    StoreItem createStoreItem(StoreItem storeItem) throws BusinessException;

    /**
     *
     *
     * @param storeItem
     * @return StoreItem
     * @throws BusinessException
     */
    StoreItem saveStoreItem(StoreItem storeItem) throws BusinessException;

    /**
     *
     *
     * @param storeItem
     * @return StoreItem
     * @throws BusinessException
     */
    void removeStoreItem(StoreItem storeItem) throws BusinessException;

    /**
     *
     *
     * @param storeItem
     * @throws BusinessException
     */
    void addStoreItem(StoreItem storeItem) throws BusinessException;

    /**
     *
     *
     * @param storeItem
     * @throws BusinessException
     */
    void issueStoreItem(StoreItem storeItem) throws BusinessException;

    /**
     *
     *
     * @param storeItem
     * @throws BusinessException
     */
    void lendStoreItem(StoreItem storeItem) throws BusinessException;

    /**
     *
     *
     * @param storeItem
     * @throws BusinessException
     */
    void returnStoreItem(StoreItem storeItem) throws BusinessException;

    /**
     *
     *
     * @param criteria
     * @return
     */
    List<StoreItem> findStoreItems(StoreItemQueryCriteria criteria);

    /**
     *
     *
     * @param criteria
     * @return
     */
    List<StoreActivity> findStoreActivities(StoreActivityQueryCriteria criteria);
}
