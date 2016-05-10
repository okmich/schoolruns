/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.schoolruns.common.entity.Account;
import com.okmich.schoolruns.common.entity.AccountType;
import com.okmich.schoolruns.common.entity.AssetType;
import com.okmich.schoolruns.common.entity.Fee;
import com.okmich.schoolruns.common.entity.FeeGroup;
import com.okmich.schoolruns.common.entity.FeeType;
import com.okmich.schoolruns.common.entity.LiabilityType;
import com.okmich.schoolruns.common.entity.PayStructure;
import com.okmich.schoolruns.common.entity.PayStructureItem;
import com.okmich.schoolruns.common.entity.TxnCategory;
import com.okmich.schoolruns.common.entity.TxnType;
import com.okmich.schoolruns.finance.service.repo.AccountQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.FeeGroupQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.FeeQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.TxnTypeQueryCriteria;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
public interface FinanceSetupService extends Serializable {

    /**
     *
     * @param accountType
     * @return AccountType
     * @throws BusinessException
     */
    AccountType saveAccountType(AccountType accountType) throws BusinessException;

    /**
     *
     * @param accountTypeId
     * @return AccountType
     */
    AccountType findAccountType(String accountTypeId);

    /**
     *
     * @return List<AccountType>
     */
    List<AccountType> findAccountTypes();

    /**
     *
     *
     * @param account
     * @return Account
     * @throws BusinessException
     */
    Account saveAccount(Account account) throws BusinessException;

    /**
     *
     *
     * @param accountId
     * @return Account
     */
    Account findAccount(Integer accountId);

    /**
     *
     *
     * @param criteria
     * @return List<AccountType>
     */
    List<Account> findAccounts(AccountQueryCriteria criteria);

    /**
     *
     * @param assetType
     * @return AssetType
     * @throws BusinessException
     */
    AssetType saveAssetType(AssetType assetType) throws BusinessException;

    /**
     *
     * @param assetTypeId
     * @return AssetType
     */
    AssetType findAssetType(Integer assetTypeId);

    /**
     *
     *
     * @param fee
     * @return
     * @throws BusinessException
     */
    Fee saveFee(Fee fee) throws BusinessException;

    /**
     *
     *
     * @param feeId
     * @return
     */
    Fee findFee(Integer feeId);

    /**
     *
     *
     * @param criteria
     * @return
     */
    List<Fee> findFees(FeeQueryCriteria criteria);

    /**
     *
     *
     * @param feeGroup
     * @return FeeGroup
     * @throws BusinessException
     */
    FeeGroup saveFeeGroup(FeeGroup feeGroup) throws BusinessException;

    /**
     *
     *
     * @param feeGroupId
     * @return
     */
    FeeGroup findFeeGroup(Integer feeGroupId);

    /**
     *
     *
     * @param criteria
     * @return
     */
    List<FeeGroup> findFeeGroups(FeeGroupQueryCriteria criteria);

    /**
     *
     * @return List<AssetType>
     */
    List<AssetType> findAssetTypes();

    /**
     *
     * @param feeType
     * @return FeeType
     * @throws BusinessException
     */
    FeeType saveFeeType(FeeType feeType) throws BusinessException;

    /**
     *
     * @param feeTypeId
     * @return FeeType
     */
    FeeType findFeeType(Integer feeTypeId);

    /**
     *
     * @return List<FeeType>
     */
    List<FeeType> findFeeTypes();

    /**
     *
     * @param liabilityType
     * @return LiabilityType
     * @throws BusinessException
     */
    LiabilityType saveLiabilityType(LiabilityType liabilityType) throws BusinessException;

    /**
     *
     * @param liabilityTypeId
     * @return LiabilityType
     */
    LiabilityType findLiabilityType(Integer liabilityTypeId);

    /**
     *
     * @param payStructure
     * @return PayStructure
     * @throws BusinessException
     */
    PayStructure savePayStructure(PayStructure payStructure) throws BusinessException;

    /**
     *
     * @param payStructureId
     * @return PayStructure
     */
    PayStructure findPayStructure(Integer payStructureId);

    /**
     *
     * @return List<PayStructure>
     */
    List<PayStructure> findPayStructures(Integer schoolId);

    /**
     *
     * @param payStructureItem
     * @return PayStructureItem
     * @throws BusinessException
     */
    PayStructureItem savePayStructureItem(PayStructureItem payStructureItem) throws BusinessException;

    /**
     *
     * @param payStructureItemId
     * @return PayStructureItem
     */
    PayStructureItem findPayStructureItem(Integer payStructureItemId);

    /**
     *
     * @return List<PayStructureItem>
     */
    List<PayStructureItem> findPayStructureItems(Integer schoolId);

    /**
     *
     * @return List<LiabilityType>
     */
    List<LiabilityType> findLiabilityTypes();

    /**
     *
     * @param txnCategory
     * @return TxnCategory
     * @throws BusinessException
     */
    TxnCategory saveTxnCategory(TxnCategory txnCategory) throws BusinessException;

    /**
     *
     * @param txnCategoryId
     * @return TxnCategory
     */
    TxnCategory findTxnCategory(Integer txnCategoryId);

    /**
     *
     * @return List<TxnCategory>
     */
    List<TxnCategory> findTxnCategories();

    /**
     *
     * @param txnType
     * @return TxnType
     * @throws BusinessException
     */
    TxnType createTxnType(TxnType txnType) throws BusinessException;

    /**
     *
     * @param txnTypeId
     * @return TxnType
     */
    TxnType findTxnType(Integer txnTypeId);

    /**
     *
     * @param criteria
     * @return List<TxnType>
     */
    List<TxnType> findTxnTypes(TxnTypeQueryCriteria criteria);

    /**
     *
     * @param txnType
     * @return TxnType
     * @throws BusinessException
     */
    TxnType saveTxnType(TxnType txnType) throws BusinessException;
}
