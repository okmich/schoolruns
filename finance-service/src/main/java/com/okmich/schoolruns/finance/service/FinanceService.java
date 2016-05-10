/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.schoolruns.common.entity.Asset;
import com.okmich.schoolruns.common.entity.EmployeePayroll;
import com.okmich.schoolruns.common.entity.EmployeePayrollItem;
import com.okmich.schoolruns.common.entity.Expense;
import com.okmich.schoolruns.common.entity.FeeReceipt;
import com.okmich.schoolruns.common.entity.Liability;
import com.okmich.schoolruns.common.entity.Payroll;
import com.okmich.schoolruns.common.entity.Receipt;
import com.okmich.schoolruns.common.entity.SchoolPref;
import com.okmich.schoolruns.finance.service.repo.AssetQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.EmployeePayrollQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.ExpenseQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.FeeReceiptQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.LiabilityQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.PayrollQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.ReceiptQueryCriteria;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
public interface FinanceService extends Serializable {

    /**
     *
     *
     * @param expense
     * @return
     * @throws BusinessException
     */
    Expense createExpense(Expense expense) throws BusinessException;

    /**
     *
     *
     * @param expenseId
     * @return
     */
    Expense findExpense(Integer expenseId);

    /**
     *
     *
     * @param criteria
     * @return
     */
    List<Expense> findExpenses(ExpenseQueryCriteria criteria);

    /**
     *
     *
     * @param expense
     * @throws BusinessException
     */
    void reverseExpense(Expense expense) throws BusinessException;

    /**
     * ************************************************************
     * ************************* FEE APIS *************************
     * ************************************************************
     */
    /**
     *
     *
     * @param feeReceipt
     * @return
     * @throws BusinessException
     */
    FeeReceipt saveFeeReceipt(FeeReceipt feeReceipt) throws BusinessException;

    /**
     *
     *
     * @param feeReceipt
     * @throws BusinessException
     */
    void reverseFeeReceipt(FeeReceipt feeReceipt) throws BusinessException;

    /**
     *
     *
     * @param feeReceiptId
     * @return
     * @throws BusinessException
     */
    FeeReceipt findFeeReceipt(Integer feeReceiptId) ;

    /**
     *
     *
     * @param criteria
     * @return List<FeeReceipt>
     */
    List<FeeReceipt> findFeeReceipts(FeeReceiptQueryCriteria criteria);

    /**
     * ************************************************************
     * ************************* PAYROLL APIS *************************
     * ************************************************************
     */
    /**
     * create a payroll register for all staff that belong to a particular
     * institution for a specific month. Payroll created with this method is
     * very INACTIVE can be approved to take effect or reject in which case it
     * is deleted from the system
     *
     * <p> Once this object is created, a number of {@link EmployeePayroll} will
     * be create with each also have a number of {@link EmployeePayrollItem}
     * </p>
     *
     * @param payroll - to be created
     * @param schoolPref -
     * @return Payroll - created
     * @throws BusinessException - if a payroll has been previously created for
     * the institution for that period
     */
    Payroll createPayroll(Payroll payroll, SchoolPref schoolPref) throws BusinessException;

    /**
     * find a payroll summary record by its primary key
     *
     * @param payrollId
     * @return Payroll
     */
    Payroll findPayroll(Integer payrollId);

    /**
     * find a list of Payroll that match the criteria specified by the
     * {@code PayrollQueryCriteria} object
     *
     * @param criteria
     * @return List<Payroll>
     */
    List<Payroll> findPayrolls(PayrollQueryCriteria criteria);

    /**
     * approves a Payroll and all the composing EmployeePayroll records. Sets
     * them to active and generates a TxnJournal for the payment and also
     * another for Tax (if applicable).
     *
     * @param payroll
     * @throws BusinessException
     */
    void approvePayroll(Payroll payroll) throws BusinessException;

    /**
     * deletes the Payroll objects and all its relationships. This includes
     * EmployeePayroll and EmployeePayrollItem
     *
     * @param payroll
     * @throws BusinessException
     */
    void deletePayroll(Payroll payroll) throws BusinessException;

    /**
     * fina all EmployeePayroll that match the criteria specified by
     * {@code EmployeePayrollQueryCriteria}
     *
     * @param criteria
     * @return List<EmployeePayroll>
     */
    List<EmployeePayroll> findEmployeePayrolls(EmployeePayrollQueryCriteria criteria);

    /**
     * returns all the constituting payroll items that sum up to the
     * EmployeePayroll object whose primary key is {@code employeePayrollId}
     *
     * @param employeePayrollId
     * @return List<EmployeePayrollItem>
     */
    List<EmployeePayrollItem> findEmployeePayrollItems(Integer employeePayrollId);

    /**
     * adds a new negative value of {@link EmployeePayrollItem} to an pending
     * EmployeePayroll. <p> Payroll for this must be in the INACTIVE state for
     * this method to complete successfully </p>
     *
     * @param employeePayrollItem
     * @param SchoolPref
     * @return EmployeePayrollItem
     * @throws BusinessException -if error occurs or if the parent Payroll
     * instance is not in the INACTIVE state
     */
    EmployeePayrollItem makeDeduction(EmployeePayrollItem employeePayrollItem,
            SchoolPref schoolPref)
            throws BusinessException;

    /**
     * adds a non negative value of {@link EmployeePayrollItem} to an pending
     * EmployeePayroll. <p> Payroll for this must be in the INACTIVE state for
     * this method to complete successfully </p>
     *
     * @param employeePayrollItem
     * @param SchoolPref
     * @return EmployeePayrollItem
     * @throws BusinessException -if error occurs or if the parent Payroll
     * instance is not in the INACTIVE state
     */
    EmployeePayrollItem makeAddition(EmployeePayrollItem employeePayrollItem,
            SchoolPref schoolPref)
            throws BusinessException;

    /**
     * ************************************************************
     * ********************** RECEIPTING APIS *********************
     * ************************************************************
     */
    /**
     * creates a new receipt. generates the receipt number and the receipt Id
     * and sends the newly populated object back to the caller
     *
     * @param receipt - receipt txn to create
     * @return Receipt - a saved receipt with receipt number and receiptId
     * @throws BusinessException - if error occurs
     */
    Receipt createReceipt(Receipt receipt) throws BusinessException;

    /**
     * reverses a previously created and save receipt. A saved receipt reversal
     * inverses the amount value of the original receipt
     *
     *
     * @param receipt - receipt values for the reversal
     * @throws BusinessException
     */
    void reverseReceipt(Receipt receipt) throws BusinessException;

    /**
     * finds and returns a receipt with the {@code receiptId)
     *
     * @param receiptId - the receipt id
     * @return Receipt - the receipt
     */
    Receipt findReceipt(Integer receiptId);

    /**
     * a schedule of receipt loaded by the receiptId of the original receipt
     *
     * @param receiptId
     * @return List<Receipt>
     */
    List<Receipt> findReceipts(Integer receiptId);

    /**
     * returns the unutilized balance on the receipt number
     *
     * @param receiptId
     * @return BigDecimal
     */
    BigDecimal getReceiptBalance(Integer receiptId);

    /**
     * returns a list of receipt that match a specific criteria composed by the
     * {@link ReceiptQueryCriteria}
     *
     * @param criteria
     * @return List<Receipt>
     */
    List<Receipt> findReceipts(ReceiptQueryCriteria criteria);

    /**
     * ************************************************************
     * ******************** ASSET AND LIABILITY *******************
     * ************************************************************
     */
    /**
     *
     * @param asset
     * @return
     * @throws BusinessException
     */
    Asset saveAsset(Asset asset) throws BusinessException;

    /**
     *
     *
     * @param assetId
     * @return
     */
    Asset findAsset(Integer assetId);

    /**
     *
     *
     * @param criteria
     * @return
     */
    List<Asset> findAssets(AssetQueryCriteria criteria);

    /**
     *
     *
     * @param asset
     * @throws BusinessException
     */
    void removeAsset(Asset asset) throws BusinessException;

    /**
     *
     *
     * @param liability
     * @return
     * @throws BusinessException
     */
    Liability saveLiability(Liability liability) throws BusinessException;

    /**
     *
     *
     * @param liabilityId
     * @return
     */
    Liability findLiability(Integer liabilityId);

    /**
     *
     *
     * @param criteria
     * @return
     */
    List<Liability> findLiabilities(LiabilityQueryCriteria criteria);
}
