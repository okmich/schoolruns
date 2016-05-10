/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.exception.ErrorConstants;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.*;
import com.okmich.schoolruns.finance.service.repo.*;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
@Service("storeInventoryService")
@Transactional
public class StoreInventoryServiceImpl implements StoreInventoryService {

    /**
     * LOG
     */
    private static final Logger LOG = Logger.getLogger(StoreInventoryServiceImpl.class.getName());
    /**
     * storeActivityRepo
     */
    @Autowired
    private StoreActivityRepo storeActivityRepo;
    /**
     * storeActivityTypeRepo
     */
    @Autowired
    private StoreActivityTypeRepo storeActivityTypeRepo;
    /**
     * storeItemRepo
     */
    @Autowired
    private StoreItemRepo storeItemRepo;
    /**
     * storeItemTypeRepo
     */
    @Autowired
    private StoreItemTypeRepo storeItemTypeRepo;
    /**
     * storeRepo
     */
    @Autowired
    private StoreRepo storeRepo;
    /**
     * financeCriteriaSearchRepo
     */
    @Autowired
    private FinanceCriteriaSearchRepo financeCriteriaSearchRepo;

    public StoreInventoryServiceImpl() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Store createStore(Store store) throws BusinessException {
        store.setModifiedTime(new Date());
        if (!storeRepo.findStoreByName(store.getSchool().getSchoolId(), store.getName()).isEmpty()) {
            throw new BusinessException(ErrorConstants.STORE_ALREADY_EXIST);
        }
        store.setStatus(CommonConstants.STATUS_ACTIVE);
        try {
            return storeRepo.save(store);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Store findStore(Integer storeId) {
        return storeRepo.findOne(storeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<Store> findSchoolStores(Integer schoolId) {
        return storeRepo.findStoresBySchool(schoolId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<Store> findStores(StoreQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findStores(criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Store saveStore(Store store) throws BusinessException {
        store.setModifiedTime(new Date());
        try {
            return storeRepo.save(store);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public StoreItemType saveStoreItemType(StoreItemType storeItemType) throws BusinessException {
        try {
            return storeItemTypeRepo.save(storeItemType);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public StoreItemType findStoreItemType(Integer storeItemTypeId) {
        return storeItemTypeRepo.findOne(storeItemTypeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<StoreItemType> findStoreItemTypes() {
        return storeItemTypeRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public StoreActivityType saveStoreActivityType(
            StoreActivityType storeActivityType) throws BusinessException {
        try {
            return storeActivityTypeRepo.save(storeActivityType);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public StoreActivityType findStoreActivityType(String storeActivityTypeId) {
        return storeActivityTypeRepo.findOne(storeActivityTypeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<StoreActivityType> findStoreActivityTypes() {
        return storeActivityTypeRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public StoreItem createStoreItem(StoreItem storeItem) throws BusinessException {
        if (storeItem.getQuantity() == null
                || storeItem.getQuantity() <= 0d) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM_QUANTITY);
        }
        //check for duplicate item
        if (!storeItemRepo.findStoreItemByName(storeItem.getStore().getStoreId(), storeItem.getName()).isEmpty()) {
            throw new BusinessException(ErrorConstants.DUPLICATE_STORE_ITEM_NAME);
        }
        try {
            storeItem.setStoreItemId(0);
            storeItem.setStatus(CommonConstants.STATUS_ACTIVE);
            storeItem.setModifiedTime(new Date());
            StoreItem _storeItem = storeItemRepo.save(storeItem);
            //create a store activity
            _storeItem.setNarration(storeItem.getNarration());
            StoreActivity storeActivity = createStoreActivity(
                    _storeItem, FinanceConstants.STORE_ACT_NEW);
            storeActivityRepo.save(storeActivity);
            return _storeItem;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public StoreItem saveStoreItem(StoreItem storeItem) throws BusinessException {
        StoreItem _storeItem = storeItemRepo.findOne(storeItem.getStoreItemId());
        if (_storeItem == null) {
            throw new BusinessException(ErrorConstants.CANNOT_FIND_ITEM);
        }
        //check for duplicate item
        List<StoreItem> storeItems = storeItemRepo.findStoreItemByName(
                storeItem.getStore().getStoreId(), storeItem.getName());
        if (!storeItems.contains(storeItem)) {
            throw new BusinessException(ErrorConstants.DUPLICATE_STORE_ITEM_NAME);
        }
        //transfer fields
        _storeItem.setDescription(storeItem.getDescription());
        _storeItem.setModifiedBy(storeItem.getModifiedBy());
        _storeItem.setName(storeItem.getName());
        _storeItem.setStoreItemType(storeItem.getStoreItemType());
        try {
            _storeItem.setModifiedTime(new Date());
            return storeItemRepo.save(_storeItem);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void removeStoreItem(StoreItem storeItem) throws BusinessException {
        StoreItem _storeItem = storeItemRepo.findOne(storeItem.getStoreItemId());
        if (_storeItem == null) {
            throw new BusinessException(ErrorConstants.CANNOT_FIND_ITEM);
        }
        //create a store activity
        StoreActivity storeActivity = createStoreActivity(storeItem, FinanceConstants.STORE_ACT_DEL);
        try {
            _storeItem.setModifiedTime(new Date());
            _storeItem.setStatus(CommonConstants.STATUS_INACTIVE);
            storeActivityRepo.save(storeActivity);
            storeItemRepo.save(_storeItem);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void addStoreItem(StoreItem storeItem) throws BusinessException {
        addToStore(storeItem, FinanceConstants.STORE_ACT_RESTOCK);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void issueStoreItem(StoreItem storeItem) throws BusinessException {
        removeFromStore(storeItem, FinanceConstants.STORE_ACT_ISSUE);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void lendStoreItem(StoreItem storeItem) throws BusinessException {
        removeFromStore(storeItem, FinanceConstants.STORE_ACT_LEND);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void returnStoreItem(StoreItem storeItem) throws BusinessException {
        addToStore(storeItem, FinanceConstants.STORE_ACT_RETURN);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<StoreItem> findStoreItems(StoreItemQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findStoreItems(criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<StoreActivity> findStoreActivities(StoreActivityQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findStoreActivities(criteria);
    }

    /**
     *
     *
     * @param storeItem
     * @param actType
     * @throws BusinessException
     */
    private void addToStore(StoreItem storeItem, String actType) throws BusinessException {
        if (storeItem.getQuantity() == null
                || storeItem.getQuantity() <= 0d) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM_QUANTITY);
        }
        StoreItem _storeItem = storeItemRepo.findOne(storeItem.getStoreItemId());
        if (_storeItem == null) {
            throw new BusinessException(ErrorConstants.CANNOT_FIND_ITEM);
        }
        if (_storeItem.getQuantity() < storeItem.getQuantity()) {
            throw new BusinessException(ErrorConstants.OUT_OF_STOCK);
        }
        _storeItem.setQuantity(_storeItem.getQuantity() + storeItem.getQuantity());
        //create a store activity
        StoreActivity storeActivity = createStoreActivity(storeItem, actType);
        try {
            _storeItem.setModifiedTime(new Date());
            storeActivityRepo.save(storeActivity);
            storeItemRepo.save(_storeItem);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     *
     * @param storeItem
     * @param actType
     * @throws BusinessException
     */
    private void removeFromStore(StoreItem storeItem, String actType) throws BusinessException {
        if (storeItem.getQuantity() == null
                || storeItem.getQuantity() <= 0d) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM_QUANTITY);
        }
        StoreItem _storeItem = storeItemRepo.findOne(storeItem.getStoreItemId());
        if (_storeItem == null) {
            throw new BusinessException(ErrorConstants.CANNOT_FIND_ITEM);
        }
        if (_storeItem.getQuantity() < storeItem.getQuantity()) {
            throw new BusinessException(ErrorConstants.OUT_OF_STOCK);
        }
        _storeItem.setQuantity(_storeItem.getQuantity() - storeItem.getQuantity());
        //create a store activity
        StoreActivity storeActivity = createStoreActivity(storeItem, actType);
        try {
            _storeItem.setModifiedTime(new Date());
            storeActivityRepo.save(storeActivity);
            storeItemRepo.save(_storeItem);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     *
     *
     * @param storeItem
     * @param storeActType
     * @return StoreActivity
     */
    private StoreActivity createStoreActivity(StoreItem storeItem, String storeActType) {
        StoreActivity storeActivity = new StoreActivity();

        storeActivity.setModifiedBy(storeItem.getModifiedBy());
        storeActivity.setModifiedTime(new Date());
        String narration = storeItem.getNarration();
        if (narration == null || narration.isEmpty()) {
            StoreActivityType sat = findStoreActivityType(storeActType);
            narration = sat == null ? "" : sat.getDescription();
        }
        storeActivity.setNarration(narration);
        storeActivity.setQuantity(storeItem.getQuantity());
        storeActivity.setStatus(CommonConstants.STATUS_ACTIVE);
        storeActivity.setStoreItem(storeItem);
        storeActivity.setStoreActivityType(
                new StoreActivityType(storeActType));

        return storeActivity;
    }
}
