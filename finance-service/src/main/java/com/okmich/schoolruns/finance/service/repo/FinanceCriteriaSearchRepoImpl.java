/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.common.entity.repo.CriteriaSearchWorker;
import com.okmich.schoolruns.common.entity.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael Enudi
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
@Repository("financeCriteriaSearchRepo")
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class FinanceCriteriaSearchRepoImpl implements FinanceCriteriaSearchRepo {

    /**
     * entityManager
     */
    @PersistenceContext
    private EntityManager entityManager;
    /**
     * accountSearchWorker
     */
    private CriteriaSearchWorker<Account, AccountQueryCriteria> accountSearchWorker;
    /**
     * assetSearchWorker
     */
    private CriteriaSearchWorker<Asset, AssetQueryCriteria> assetSearchWorker;
    /**
     * employPayrollSearchWorker
     */
    private CriteriaSearchWorker<EmployeePayroll, EmployeePayrollQueryCriteria> employeePayrollSearchWorker;
    /**
     * expenseSearchWorker
     */
    private CriteriaSearchWorker<Expense, ExpenseQueryCriteria> expenseSearchWorker;
    /**
     * feeSearchWorker
     */
    private CriteriaSearchWorker<Fee, FeeQueryCriteria> feeSearchWorker;
    /**
     * feeGroupSearchWorker
     */
    private CriteriaSearchWorker<FeeGroup, FeeGroupQueryCriteria> feeGroupSearchWorker;
    /**
     * feeReceiptSearchWorker
     */
    private CriteriaSearchWorker<FeeReceipt, FeeReceiptQueryCriteria> feeReceiptSearchWorker;
    /**
     * /**
     * liabilitySearchWorker
     */
    private CriteriaSearchWorker<Liability, LiabilityQueryCriteria> liabilitySearchWorker;
    /**
     * payrollSearchWorker
     */
    private CriteriaSearchWorker<Payroll, PayrollQueryCriteria> payrollSearchWorker;
    /**
     * receiptSearchWorker
     */
    private CriteriaSearchWorker<Receipt, ReceiptQueryCriteria> receiptSearchWorker;
    /**
     * storeActivitySearchWorker
     */
    private CriteriaSearchWorker<StoreActivity, StoreActivityQueryCriteria> storeActivitySearchWorker;
    /**
     * storeItemSearchWorker
     */
    private CriteriaSearchWorker<StoreItem, StoreItemQueryCriteria> storeItemSearchWorker;
    /**
     * storeSearchWorker
     */
    private CriteriaSearchWorker<Store, StoreQueryCriteria> storeSearchWorker;
    /**
     * txnJournalSearchWorker
     */
    private CriteriaSearchWorker<TxnJournal, TxnJournalQueryCriteria> txnJournalSearchWorker;
    /**
     * txnJournalSearchWorker
     */
    private CriteriaSearchWorker<TxnJournalDetail, TxnJournalDetailQueryCriteria> txnJournalDetailSearchWorker;
    /**
     * txnTypeDetailSearchWorker
     */
    private CriteriaSearchWorker<TxnType, TxnTypeQueryCriteria> txnTypeSearchWorker;

    /**
     * default constructor
     */
    public FinanceCriteriaSearchRepoImpl() {
        this.accountSearchWorker = new CriteriaSearchWorker<>();
        this.assetSearchWorker = new CriteriaSearchWorker<>();
        this.employeePayrollSearchWorker = new CriteriaSearchWorker<>();
        this.expenseSearchWorker = new CriteriaSearchWorker<>();
        this.feeSearchWorker = new CriteriaSearchWorker<>();
        this.feeGroupSearchWorker = new CriteriaSearchWorker<>();
        this.feeReceiptSearchWorker = new CriteriaSearchWorker<>();
        this.liabilitySearchWorker = new CriteriaSearchWorker<>();
        this.payrollSearchWorker = new CriteriaSearchWorker<>();
        this.receiptSearchWorker = new CriteriaSearchWorker<>();
        this.storeActivitySearchWorker = new CriteriaSearchWorker<>();
        this.storeItemSearchWorker = new CriteriaSearchWorker<>();
        this.storeSearchWorker = new CriteriaSearchWorker<>();
        this.txnJournalDetailSearchWorker = new CriteriaSearchWorker<>();
        this.txnJournalSearchWorker = new CriteriaSearchWorker<>();
        this.txnTypeSearchWorker = new CriteriaSearchWorker<>();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Account> findAccounts(AccountQueryCriteria criteria) {
        return accountSearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Asset> findAssets(AssetQueryCriteria criteria) {
        return assetSearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<EmployeePayroll> findEmployeePayrolls(EmployeePayrollQueryCriteria criteria) {
        return employeePayrollSearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Expense> findExpenses(ExpenseQueryCriteria criteria) {
        return expenseSearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Fee> findFees(FeeQueryCriteria criteria) {
        return feeSearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<FeeGroup> findFeeGroups(FeeGroupQueryCriteria criteria) {
        return feeGroupSearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<FeeReceipt> findFeeReceipts(FeeReceiptQueryCriteria criteria) {
        return feeReceiptSearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Liability> findLiabilities(LiabilityQueryCriteria criteria) {
        return liabilitySearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Payroll> findPayrolls(PayrollQueryCriteria criteria) {
        return payrollSearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Receipt> findReceipts(ReceiptQueryCriteria criteria) {
        return receiptSearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<StoreActivity> findStoreActivities(StoreActivityQueryCriteria criteria) {
        return storeActivitySearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<StoreItem> findStoreItems(StoreItemQueryCriteria criteria) {
        return storeItemSearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Store> findStores(StoreQueryCriteria criteria) {
        return storeSearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<TxnJournal> findTxnJournals(TxnJournalQueryCriteria criteria) {
        return txnJournalSearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<TxnJournalDetail> findTxnJournalDetails(TxnJournalDetailQueryCriteria criteria) {
        return txnJournalDetailSearchWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<TxnType> findTxnTypes(TxnTypeQueryCriteria criteria) {
        return txnTypeSearchWorker.findByCriteria(entityManager, criteria);
    }
}