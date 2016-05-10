/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.schoolruns.common.entity.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface FinanceCriteriaSearchRepo extends Serializable {

    /**
     *
     *
     * @param criteria - AccountQueryCriteria
     * @return List<Account>
     */
    public List<Account> findAccounts(AccountQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - AssetQueryCriteria
     * @return List<Asset>
     */
    public List<Asset> findAssets(AssetQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - EmployeePayrollQueryCriteria
     * @return List<EmployeePayroll>
     */
    public List<EmployeePayroll> findEmployeePayrolls(EmployeePayrollQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - ExpenseQueryCriteria
     * @return List<Expense>
     */
    public List<Expense> findExpenses(ExpenseQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - FeeQueryCriteria
     * @return List<Fee>
     */
    public List<Fee> findFees(FeeQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - FeeGroupQueryCriteria
     * @return List<FeeGroup>
     */
    public List<FeeGroup> findFeeGroups(FeeGroupQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - FeeReceiptQueryCriteria
     * @return List<FeeReceipt>
     */
    public List<FeeReceipt> findFeeReceipts(FeeReceiptQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - LiabilityQueryCriteria
     * @return List<Liability>
     */
    public List<Liability> findLiabilities(LiabilityQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - PayrollQueryCriteria
     * @return List<Payroll>
     */
    public List<Payroll> findPayrolls(PayrollQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - ReceiptQueryCriteria
     * @return List<Receipt>
     */
    public List<Receipt> findReceipts(ReceiptQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - StoreActivityQueryCriteria
     * @return List<StoreActivity>
     */
    public List<StoreActivity> findStoreActivities(StoreActivityQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - StoreItemQueryCriteria
     * @return List<StoreItem>
     */
    public List<StoreItem> findStoreItems(StoreItemQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - StoreQueryCriteria
     * @return List<Store>
     */
    public List<Store> findStores(StoreQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - TxnJournalQueryCriteria
     * @return List<TxnJournal>
     */
    public List<TxnJournal> findTxnJournals(TxnJournalQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - TxnJournalDetailQueryCriteria
     * @return List<TxnJournalDetail>
     */
    public List<TxnJournalDetail> findTxnJournalDetails(TxnJournalDetailQueryCriteria criteria);

    /**
     *
     *
     * @param criteria - TxnTypeQueryCriteria
     * @return List<TxnType>
     */
    public List<TxnType> findTxnTypes(TxnTypeQueryCriteria criteria);
}