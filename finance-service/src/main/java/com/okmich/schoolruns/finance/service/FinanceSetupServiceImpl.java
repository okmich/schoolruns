/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.exception.ErrorConstants;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
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
import com.okmich.schoolruns.finance.service.repo.*;
import java.math.BigDecimal;
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
@Service("financeSetupService")
@Transactional
public class FinanceSetupServiceImpl implements FinanceSetupService {

    /**
     *
     */
    private static final Logger LOG = Logger.getLogger(FinanceSetupServiceImpl.class.getName());
    /**
     * accountTypeRepo
     */
    @Autowired
    private AccountTypeRepo accountTypeRepo;
    /**
     * accountRepo
     */
    @Autowired
    private AccountRepo accountRepo;
    /**
     * assetTypeRepo
     */
    @Autowired
    private AssetTypeRepo assetTypeRepo;
    /**
     * liabilityTypeRepo
     */
    @Autowired
    private LiabilityTypeRepo liabilityTypeRepo;
    /**
     * feeGroupRepo
     */
    @Autowired
    private FeeGroupRepo feeGroupRepo;
    /**
     * feeRepo
     */
    @Autowired
    private FeeRepo feeRepo;
    /**
     * payStructureRepo
     */
    @Autowired
    private PayStructureRepo payStructureRepo;
    /**
     * payStructureItemRepo
     */
    @Autowired
    private PayStructureItemRepo payStructureItemRepo;
    /**
     * feeTypeRepo
     */
    @Autowired
    private FeeTypeRepo feeTypeRepo;
    /**
     * txnCategoryRepo
     */
    @Autowired
    private TxnCategoryRepo txnCategoryRepo;
    /**
     * txnTypeRepo
     */
    @Autowired
    private TxnTypeRepo txnTypeRepo;
    /**
     * financeCriteriaSearchRepo
     */
    @Autowired
    private FinanceCriteriaSearchRepo financeCriteriaSearchRepo;

    public FinanceSetupServiceImpl() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public AccountType saveAccountType(AccountType accountType) throws BusinessException {
        try {
            return accountTypeRepo.save(accountType);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public AccountType findAccountType(String accountTypeId) {
        return accountTypeRepo.findOne(accountTypeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<AccountType> findAccountTypes() {
        return accountTypeRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Account saveAccount(Account account) throws BusinessException {
        account.setCreateDate(new Date());
        if (account.getAccountId() == null) {
            if (!accountRepo.findSchoolAccount(
                    account.getSchoolId(), account.getAccountCode()).isEmpty()) {
                throw new BusinessException(ErrorConstants.CODE_ALREADY_IN_USE);
            }
        }
        try {
            account.setModifiedTime(new Date());
            return accountRepo.save(account);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Account findAccount(Integer accountId) {
        return accountRepo.findOne(accountId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Account> findAccounts(AccountQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findAccounts(criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public AssetType saveAssetType(AssetType assetType) throws BusinessException {
        try {
            return assetTypeRepo.save(assetType);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public AssetType findAssetType(Integer assetTypeId) {
        return assetTypeRepo.findOne(assetTypeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<AssetType> findAssetTypes() {
        return assetTypeRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Fee saveFee(Fee fee) throws BusinessException {
        fee.setModifiedTime(new Date());
        if (fee.getStatus() == null) {
            fee.setStatus(CommonConstants.STATUS_ACTIVE);
        }
        try {
            return feeRepo.save(fee);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Fee findFee(Integer feeId) {
        return feeRepo.findOne(feeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<Fee> findFees(FeeQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findFees(criteria);
    }

    @Override
    public FeeGroup saveFeeGroup(FeeGroup feeGroup) throws BusinessException {
        //find duplication based on school, grade_level and fee_type
        if (feeGroup.getFeeGroupId() == null) { //new object
            List<FeeGroup> feeGroups;
            feeGroups = feeGroupRepo.findBySchoolGradeType(feeGroup.getSchoolId(),
                    feeGroup.getGradeLevel().getGradeLevelId(),
                    feeGroup.getFeeType().getFeeTypeCode());
            if (!feeGroups.isEmpty()) {
                throw new BusinessException(ErrorConstants.DUPLICATE_SCHOOL_GRADE_TYPE);
            }
            //find duplication based on fee_type and apply_all
            feeGroups = feeGroupRepo.findByTypeApplyMode(feeGroup.getSchoolId(),
                    feeGroup.getFeeType().getFeeTypeCode());
            if (!feeGroups.isEmpty()) {
                throw new BusinessException(ErrorConstants.DUPLICATE_APPLICATION_FOR_TYPE);
            }
        }

        feeGroup.setModifiedTime(new Date());
        if (feeGroup.getStatus() == null) {
            feeGroup.setStatus(CommonConstants.STATUS_ACTIVE);
        }
        try {
            return feeGroupRepo.save(feeGroup);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public FeeGroup findFeeGroup(Integer feeGroupId) {
        FeeGroup feeGroup = feeGroupRepo.findOne(feeGroupId);
        if (feeGroup == null) {
            return null;
        }
        System.out.println(">>>>>>>> " + feeGroup.getFeeList());
        feeGroup.setFeeList(feeGroup.getFeeList());
        
        System.out.println(">>>>>>>> " + feeGroup.getFeeList());
        return feeGroup;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<FeeGroup> findFeeGroups(FeeGroupQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findFeeGroups(criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public FeeType saveFeeType(FeeType feeType) throws BusinessException {
        try {
            return feeTypeRepo.save(feeType);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public FeeType findFeeType(Integer feeTypeId) {
        return feeTypeRepo.findOne(feeTypeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<FeeType> findFeeTypes() {
        return feeTypeRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public LiabilityType saveLiabilityType(LiabilityType liabilityType) throws BusinessException {
        try {
            return liabilityTypeRepo.save(liabilityType);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public LiabilityType findLiabilityType(Integer liabilityTypeId) {
        return liabilityTypeRepo.findOne(liabilityTypeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<LiabilityType> findLiabilityTypes() {
        return liabilityTypeRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public PayStructure savePayStructure(PayStructure payStructure) throws BusinessException {
        List<PayStructureItem> payStructureItems = payStructure.getPayStructureItemList();
        if (payStructureItems.isEmpty()) {
            throw new BusinessException(ErrorConstants.NO_ITEM_SELECTED);
        }
        BigDecimal totAmount = BigDecimal.ZERO;

        for (PayStructureItem psi : payStructureItems) {
            psi = findPayStructureItem(psi.getPayStructureItemId());
            if (psi != null) {
                totAmount = totAmount.add(psi.getAmount());
            }
        }
        payStructure.setTotalAmount(totAmount);
        if (payStructure.getStatus() == null || payStructure.getStatus().isEmpty()) {
            payStructure.setStatus(CommonConstants.STATUS_ACTIVE);
        }
        payStructure.setModifiedTime(new Date());
        try {
            return payStructureRepo.save(payStructure);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public PayStructure findPayStructure(Integer payStructureId) {
        PayStructure payStructure = payStructureRepo.findOne(payStructureId);
        if (payStructure == null) {
            return null;
        }
        payStructure.setPayStructureItemList(payStructure.getPayStructureItemList());
        return payStructure;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<PayStructure> findPayStructures(Integer schoolId) {
        return payStructureRepo.findPayStructureBySchool(schoolId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public PayStructureItem savePayStructureItem(PayStructureItem payStructureItem) throws BusinessException {
        if (payStructureItem.getStatus() == null || payStructureItem.getStatus().isEmpty()) {
            payStructureItem.setStatus(CommonConstants.STATUS_ACTIVE);
        }
        payStructureItem.setModifiedTime(new Date());
        try {
            return payStructureItemRepo.save(payStructureItem);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public PayStructureItem findPayStructureItem(Integer payStructureItemId) {
        return payStructureItemRepo.findOne(payStructureItemId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<PayStructureItem> findPayStructureItems(Integer schoolId) {
        return payStructureItemRepo.findPayStructureItemBySchool(schoolId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public TxnCategory saveTxnCategory(TxnCategory txnCategory) throws BusinessException {
        try {
            return txnCategoryRepo.save(txnCategory);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public TxnCategory findTxnCategory(Integer txnCategoryId) {
        return txnCategoryRepo.findOne(txnCategoryId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<TxnCategory> findTxnCategories() {
        return txnCategoryRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public TxnType createTxnType(TxnType txnType) throws BusinessException {
        txnType.setCreateDate(new Date());
        if (!txnTypeRepo.findSchoolTxnType(
                txnType.getSchoolId(), txnType.getTxnTypeCode()).isEmpty()) {
            throw new BusinessException(ErrorConstants.CODE_ALREADY_IN_USE);
        }
        if (txnType.getCreditAccount().getAccountId() == null
                && txnType.getDebitAccount().getAccountId() == null) {
            throw new BusinessException(ErrorConstants.NEITHER_ACCOUNTS_SPECIFIED);
        }
        try {
            txnType.setModifiedTime(new Date());
            return txnTypeRepo.save(txnType);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public TxnType findTxnType(Integer txnTypeId) {
        return txnTypeRepo.findOne(txnTypeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<TxnType> findTxnTypes(TxnTypeQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findTxnTypes(criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public TxnType saveTxnType(TxnType txnType) throws BusinessException {
        if (txnType.getCreditAccount().getAccountId() == null
                && txnType.getDebitAccount().getAccountId() == null) {
            throw new BusinessException(ErrorConstants.NEITHER_ACCOUNTS_SPECIFIED);
        }
        try {
            txnType.setModifiedTime(new Date());
            return txnTypeRepo.save(txnType);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }
}
