/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.exception.ErrorConstants;
import com.okmich.common.util.DateUtil;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.*;
import com.okmich.schoolruns.employee.service.repo.EmployeeRepo;
import com.okmich.schoolruns.finance.service.repo.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
@Service("financeService")
@Transactional
public class FinanceServiceImpl implements FinanceService {

    /**
     *
     */
    private static final Logger LOG = Logger.getLogger(FinanceServiceImpl.class.getName());
    /**
     * assetRepo
     */
    @Autowired
    private AssetRepo assetRepo;
    /**
     * feeRepo
     */
    @Autowired
    private EmployeeRepo employeeRepo;
    /**
     * employeePayrollItemRepo
     */
    @Autowired
    private EmployeePayrollItemRepo employeePayrollItemRepo;
    /**
     * employeePayrollRepo
     */
    @Autowired
    private EmployeePayrollRepo employeePayrollRepo;
    /**
     * expenseRepo
     */
    @Autowired
    private ExpenseRepo expenseRepo;
    /**
     * feeCollRepo
     */
    @Autowired
    private FeeGroupRepo feeGroupRepo;
    /**
     * feeReceiptRepo
     */
    @Autowired
    private FeeReceiptRepo feeReceiptRepo;
    /**
     * liabilityRepo
     */
    @Autowired
    private LiabilityRepo liabilityRepo;
    /**
     * payrollRepo
     */
    @Autowired
    private PayrollRepo payrollRepo;
    /**
     * payStructureRepo
     */
    @Autowired
    private PayStructureRepo payStructureRepo;
    /**
     * receiptRepo
     */
    @Autowired
    private ReceiptRepo receiptRepo;
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
    /**
     * txnJournalService
     */
    @Autowired
    private TxnJournalService txnJournalService;

    /**
     *
     */
    public FinanceServiceImpl() {
    }

    @Override
    public Expense createExpense(Expense expense) throws BusinessException {
        expense.setModifiedTime(new Date());
        expense.setStatus(CommonConstants.STATUS_ACTIVE);
        //create a 
        TxnJournal txnJournal = getTxnJournalForExpense(expense,
                txnTypeRepo.findOne(expense.getTxnType().getTxnTypeId()));
        try {
            txnJournalService.createTxnJournal(txnJournal);
            return expenseRepo.save(expense);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    @Override
    public Expense findExpense(Integer expenseId) {
        return expenseRepo.findOne(expenseId);
    }

    @Override
    public List<Expense> findExpenses(ExpenseQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findExpenses(criteria);
    }

    @Override
    public void reverseExpense(Expense expense) throws BusinessException {
        Expense _expense = findExpense(expense.getExpenseId());
        //ensure the entity is not null or a reversal
        if (_expense == null || _expense.isReversal()) {
            throw new BusinessException(ErrorConstants.INVALID_RECEIPT);
        }
        //check if a previous reversal was done
        List<Expense> expenses = expenseRepo.findExpenseReversal(_expense.getExpenseId());
        if (!expenses.isEmpty()) {
            throw new BusinessException(ErrorConstants.RECORD_ALREADY_REVERSED);
        }
        Expense e = new Expense();
        e.setAmount(_expense.getAmount().abs().negate());
        e.setModifiedTime(new Date());
        e.setModifiedBy(expense.getModifiedBy());
        e.setNarration(expense.getNarration());
        e.setPrevExpenseId(_expense.getExpenseId()); //set the linkage 
        e.setReversal(true);
        e.setStatus(CommonConstants.STATUS_ACTIVE);
        e.setTxnDate(new Date());
        e.setTxnRef(_expense.getTxnRef());
        e.setTxnType(_expense.getTxnType());
        //create a 
        TxnJournal txnJournal = getTxnJournalForExpense(e,
                txnTypeRepo.findOne(expense.getTxnType().getTxnTypeId()));
        try {
            txnJournalService.reverseTxnJournal(txnJournal);
            expenseRepo.save(e);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * ************************************************************
     * ************************** FEES API ************************
     * ************************************************************
     */
    /**
     * {@inheritDoc }
     */
    @Override
    public FeeReceipt saveFeeReceipt(FeeReceipt feeReceipt) throws BusinessException {
        //get the outstanding on the receipt
        BigDecimal sumOutstanding = getAllReceiptSum(feeReceipt.getReceipt());
        System.out.println("sumOutstanding >>>>>>> " + sumOutstanding);
        if (sumOutstanding.intValue() >= 0) {
            //throw error is no outstanding is on the receipt
            throw new BusinessException(ErrorConstants.RECEIPT_VAL_ALREADY_UTILIZED);
        }
        //get the outstanding on the fee using the feeGroup cost
        BigDecimal feeOutstanding = getStudentOutStandingOnFee(feeReceipt);
        System.out.println("feeOutstanding>>>>>>> " + feeOutstanding);
        if (feeOutstanding.intValue() <= 0) {
            //throw error if the fee has been fully paid
            throw new BusinessException(ErrorConstants.FEE_ALREADY_PAID);
        }
        //the amount paid will be the less of either the fee outstanding or 
        //the receipt amount
        BigDecimal amountPaid = sumOutstanding.abs().min(feeOutstanding.abs());
        //get the original paying receipt
        Receipt _receipt = receiptRepo.findOne(feeReceipt.getReceipt().getReceiptId());
        //generate a receipt entry to match off the receipt
        Receipt receipt = new Receipt();
        receipt.setAmount(amountPaid.abs());
        receipt.setEffectiveDate(new Date());
        receipt.setModifiedBy(feeReceipt.getModifiedBy());
        receipt.setModifiedTime(new Date());
        receipt.setNarration(feeReceipt.getNarration());
        receipt.setPayer("");
        receipt.setPaymentMode(_receipt.getPaymentMode());
        receipt.setPaymentNumber("");
        receipt.setReceipt(_receipt);
        receipt.setSchoolTerm(_receipt.getSchoolTerm());
        receipt.setSchoolId(_receipt.getSchoolId());
        receipt.setStatus(CommonConstants.STATUS_ACTIVE);
        receipt.setTranSource(CommonConstants.TRAN_SOURCE_SYSTEM);
        receipt.setTxnDate(new Date());
        receipt.setTxnType(_receipt.getTxnType());

        //save the feeReceipt amount but edit the amount to only what was used 
        feeReceipt.setAmount(amountPaid);
        feeReceipt.setModifiedTime(new Date());
        feeReceipt.setStatus(CommonConstants.STATUS_ACTIVE);
        try {
            receipt = receiptRepo.save(receipt);
            //set the receipt to the reversal record
            feeReceipt.setReceipt(receipt);
            return feeReceiptRepo.save(feeReceipt);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void reverseFeeReceipt(FeeReceipt feeReceipt) throws BusinessException {
        //confirm that the receipt is a real first-hand receipt and not a reversal
        FeeReceipt _feeReceipt = feeReceiptRepo.findOne(feeReceipt.getFeeReceiptId());
        //get the receiptId of any previou receipt 
        //if the value is not null, then this was a reversal and not a receipt
        if (_feeReceipt.getReceipt().getReceipt().getReceiptId() != null) {
            throw new BusinessException(ErrorConstants.CANNOT_REVERSE_A_REVERSAL);
        }
        //generate an entry to credit the receipt thereby increate its outstanding
        Receipt receipt = new Receipt();
        receipt.setAmount(_feeReceipt.getAmount().abs());
        receipt.setEffectiveDate(new Date());
        receipt.setModifiedBy(feeReceipt.getModifiedBy());
        receipt.setModifiedTime(new Date());
        receipt.setNarration(feeReceipt.getNarration());
        receipt.setPayer("");
        receipt.setPaymentMode("");
        receipt.setPaymentNumber("");
        receipt.setReceipt(_feeReceipt.getReceipt());
        receipt.setSchoolTerm(_feeReceipt.getReceipt().getSchoolTerm());
        receipt.setStatus(CommonConstants.STATUS_ACTIVE);
        receipt.setTranSource(CommonConstants.TRAN_SOURCE_SYSTEM);
        receipt.setTxnDate(new Date());
        receipt.setTxnType(_feeReceipt.getReceipt().getTxnType());
        //negate the feeReceipt amount and save same to reverse this utilization
        feeReceipt.setFeeReceiptId(null);
        feeReceipt.setAmount(_feeReceipt.getAmount().negate());
        feeReceipt.setModifiedTime(new Date());
        feeReceipt.setFeeGroup(_feeReceipt.getFeeGroup());

        try {
            receipt = receiptRepo.save(receipt);
            //set the receipt to the reversal record
            feeReceipt.setReceipt(receipt);
            feeReceiptRepo.save(feeReceipt);
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
    public FeeReceipt findFeeReceipt(Integer feeReceiptId) {
        return feeReceiptRepo.findOne(feeReceiptId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<FeeReceipt> findFeeReceipts(FeeReceiptQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findFeeReceipts(criteria);
    }

    /**
     * ************************************************************
     * ************************** PAYROLL API ************************
     * ************************************************************
     */
    /**
     * {@inheritDoc }
     */
    @Override
    public Payroll createPayroll(final Payroll payroll, SchoolPref schoolPref) throws BusinessException {
        validatePayroll(payroll); //validation
        Payroll _payroll;
        payroll.setModifiedTime(new Date());
        payroll.setStatus(CommonConstants.STATUS_INACTIVE);
        try {
            _payroll = payrollRepo.save(payroll);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
        //get taxation variables
        boolean shouldTax = schoolPref.isTaxationEnabled();
        BigDecimal taxRate = shouldTax ? schoolPref.getTaxRate() : BigDecimal.ZERO;

        List< Employee> employees = employeeRepo.findActiveEmployee(
                payroll.getSchool().getSchoolId());
        EmployeePayroll _employeePayroll;
        EmployeePayrollItem _employeePayrollItem;
        BigDecimal employeeTax;
        PayStructure _payStructure;
        List<PayStructureItem> payStructureItems;
        String payPeriod = new SimpleDateFormat("MM/YYYY").format(payroll.getPaymentDate());
        //iterate throught all employee and create them a payroll for that month
        for (Employee employee : employees) {
            if (employee.getPayStructureId() == null) {
                throw new BusinessException(ErrorConstants.NO_PAY_STRUCTURE_EMPLOYEE,
                        employee.getFullname());
            }
            _payStructure = payStructureRepo.findOne(employee.getPayStructureId());
            _employeePayroll = new EmployeePayroll();
            employeeTax = BigDecimal.ZERO;

            _employeePayroll.setEmployeeCategory(employee.getEmployeeCategory().getDescription());
            _employeePayroll.setEmployeeId(employee.getEmployeeId());
            _employeePayroll.setEmployeeName(employee.getFullname());
            _employeePayroll.setEmployeePayrollId(null);
            _employeePayroll.setEmployeePosition(employee.getEmployeePosition() != null
                    ? employee.getEmployeePosition().getDescription() : "");
            _employeePayroll.setGross(_payStructure.getTotalAmount());
            _employeePayroll.setModifiedBy(payroll.getModifiedBy());
            _employeePayroll.setModifiedTime(new Date());
            _employeePayroll.setNet(null); //we will calculate by the entity's preprocessing trigger
            _employeePayroll.setPayPeriod(payPeriod);
            _employeePayroll.setPayStructure(_payStructure);
            _employeePayroll.setPayroll(_payroll);
            _employeePayroll.setStatus(CommonConstants.STATUS_INACTIVE);
            _employeePayroll.setTax(employeeTax);

            _employeePayroll = employeePayrollRepo.save(_employeePayroll);

            payStructureItems = _payStructure.getPayStructureItemList();
            for (PayStructureItem psi : payStructureItems) {
                _employeePayrollItem = new EmployeePayrollItem();
                _employeePayrollItem.setEmployeePayroll(_employeePayroll);
                _employeePayrollItem.setEmployeePayrollItemId(null);
                _employeePayrollItem.setGross(psi.getAmount());
                _employeePayrollItem.setModifiedBy(payroll.getModifiedBy());
                _employeePayrollItem.setModifiedTime(new Date());
                _employeePayrollItem.setNet(null); //the calculation is left to an active record
                _employeePayrollItem.setPayStructureItemId(psi.getPayStructureItemId());
                _employeePayrollItem.setStatus(CommonConstants.STATUS_ACTIVE);
                _employeePayrollItem.setTax(psi.isTaxable()
                        ? psi.getAmount().multiply(taxRate.divide(BigDecimal.valueOf(100), MathContext.DECIMAL64))
                        : BigDecimal.ZERO);
                employeeTax = employeeTax.add(_employeePayrollItem.getTax()); //add up to the tax value
                _employeePayrollItem.setTitle(psi.getTitle());

                employeePayrollItemRepo.save(_employeePayrollItem);
            }
            _employeePayroll.setTax(employeeTax);
            employeePayrollRepo.save(_employeePayroll);
        }
        return payroll;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Payroll findPayroll(Integer payrollId) {
        return payrollRepo.findOne(payrollId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<Payroll> findPayrolls(PayrollQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findPayrolls(criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void approvePayroll(Payroll payroll) throws BusinessException {
        Payroll _payroll = payrollRepo.findOne(payroll.getPayrollId());
        if (_payroll == null) {
            throw new BusinessException(ErrorConstants.CANNOT_FIND_PAYROLL);
        }
        if (!CommonConstants.STATUS_INACTIVE.equals(_payroll.getStatus())) {
            throw new IllegalStateException(ErrorConstants.PAYROLL_ALREADY_APPROVED);
        }
        List<EmployeePayroll> employeePayrolls = employeePayrollRepo.findByPayroll(
                _payroll.getPayrollId());
        //
        BigDecimal totalGross = BigDecimal.ZERO;
        BigDecimal totalTax = BigDecimal.ZERO;
        BigDecimal totalNet = BigDecimal.ZERO;
        for (EmployeePayroll _employeePayroll : employeePayrolls) {
            totalGross = totalGross.add(_employeePayroll.getGross());
            totalTax = totalTax.add(_employeePayroll.getTax());
            totalNet = totalNet.add(_employeePayroll.getNet());

            _employeePayroll.setStatus(CommonConstants.STATUS_ACTIVE);
        }
        _payroll.setModifiedBy(payroll.getModifiedBy());
        _payroll.setModifiedTime(new Date());
        _payroll.setStatus(CommonConstants.STATUS_ACTIVE);
        _payroll.setTotalGross(totalGross);
        _payroll.setTotalNet(totalNet);
        _payroll.setTotalTax(totalTax);
        try {
            employeePayrollRepo.save(employeePayrolls);
            payrollRepo.save(_payroll);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void deletePayroll(Payroll payroll) throws BusinessException {
        Payroll _payroll = payrollRepo.findOne(payroll.getPayrollId());
        if (_payroll == null) {
            throw new BusinessException(ErrorConstants.CANNOT_FIND_PAYROLL);
        }
        if (!CommonConstants.STATUS_INACTIVE.equals(_payroll.getStatus())) {
            throw new IllegalStateException(ErrorConstants.PAYROLL_ALREADY_APPROVED);
        }
        List<EmployeePayroll> employeePayrolls = employeePayrollRepo.findByPayroll(
                _payroll.getPayrollId());
        List<EmployeePayrollItem> employeePayrollItems = employeePayrollItemRepo.findByPayroll(
                _payroll.getPayrollId());
        try {
            employeePayrollItemRepo.deleteInBatch(employeePayrollItems);
            employeePayrollRepo.deleteInBatch(employeePayrolls);
            payrollRepo.delete(_payroll);
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
    public List<EmployeePayroll> findEmployeePayrolls(EmployeePayrollQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findEmployeePayrolls(criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<EmployeePayrollItem> findEmployeePayrollItems(Integer employeePayrollId) {
        return employeePayrollItemRepo.findByEmployeePayroll(employeePayrollId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EmployeePayrollItem makeDeduction(final EmployeePayrollItem employeePayrollItem,
            SchoolPref schoolPref) throws BusinessException {
        //calculate the tax value
        employeePayrollItem.setTax(
                calculateTax(employeePayrollItem, schoolPref));
        return makeChangesToPayroll(employeePayrollItem, false);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EmployeePayrollItem makeAddition(EmployeePayrollItem employeePayrollItem,
            SchoolPref schoolPref) throws BusinessException {
        //calculate the tax value
        employeePayrollItem.setTax(
                calculateTax(employeePayrollItem, schoolPref));
        return makeChangesToPayroll(employeePayrollItem, true);
    }

    /**
     * validating duplicate payroll within a month
     *
     * @param payroll
     * @throws BusinessException
     */
    private void validatePayroll(Payroll payroll) throws BusinessException {
        String monthYear = new SimpleDateFormat("MM/yyyy").format(payroll.getPaymentDate());
        List payrolls = payrollRepo.findPayrollForMonth(payroll.getSchool().getSchoolId(),
                monthYear);
        if (!payrolls.isEmpty()) {
            throw new BusinessException(ErrorConstants.PAYROLL_FOR_MONTH_EXISTS);
        }
    }

    /**
     * calculates the tax on amount given the tax setting on a school
     *
     * @param employeePayrollItem
     * @param schoolPref
     * @return BigDecimal
     */
    private BigDecimal calculateTax(EmployeePayrollItem employeePayrollItem,
            SchoolPref schoolPref) {
        BigDecimal tax = BigDecimal.ZERO;
        if (schoolPref.isTaxationEnabled() && employeePayrollItem.isApplyTax()) {
            BigDecimal taxRate = schoolPref.getTaxRate();
            tax = employeePayrollItem.getGross().multiply(taxRate.divide(
                    BigDecimal.valueOf(100), MathContext.DECIMAL64));
        }
        return tax;
    }

    /**
     * makes changes to an existing EmployeePayroll which could be adding
     * bonuses or deductions to the existing pending payroll.
     *
     * @param arg - the payroll item
     * @param isAddition - if true, then we are making addition else we are
     * making deduction
     * @return EmployeePayrollItem
     * @throws BusinessException
     */
    private EmployeePayrollItem makeChangesToPayroll(EmployeePayrollItem arg, boolean isAddition)
            throws BusinessException {
        EmployeePayroll _employeePayroll = employeePayrollRepo.findOne(
                arg.getEmployeePayroll().getEmployeePayrollId());
        if (_employeePayroll == null) {
            throw new BusinessException(ErrorConstants.CANNOT_FIND_PAYROLL);
        }
        if (!CommonConstants.STATUS_INACTIVE.equals(_employeePayroll.getStatus())) {
            throw new IllegalStateException(ErrorConstants.PAYROLL_ALREADY_APPROVED);
        }
        arg.setModifiedTime(new Date());
        arg.setStatus(CommonConstants.STATUS_ACTIVE);
        if (isAddition) {
            arg.setGross(arg.getGross().abs());
            arg.setTax(arg.getTax().abs());
        } else {
            arg.setGross(arg.getGross().negate());
            arg.setTax(arg.getTax().negate());
        }
        try {
            EmployeePayrollItem _empPayrollItem = employeePayrollItemRepo.save(arg);
            //reflect the change on the parent payroll record
            _employeePayroll.setGross(_employeePayroll.getGross().add(arg.getGross()));
            _employeePayroll.setTax(_employeePayroll.getTax().add(arg.getTax()));
            //_employeePayroll.getNet().add(arg.getGross()); this will be calculated by the trigger
            employeePayrollRepo.save(_employeePayroll);

            return _empPayrollItem;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * ************************************************************
     * ********************** RECEIPTING API *********************
     * ************************************************************
     */
    /**
     * {@inheritDoc }
     */
    @Override
    public Receipt createReceipt(Receipt receipt) throws BusinessException {
        receipt.setModifiedTime(new Date());
        receipt.setStatus(CommonConstants.STATUS_ACTIVE);
        if (CommonConstants.PAYMENT_MODE_CHEQUE.equals(
                receipt.getPaymentMode())) {
            DateUtil dateUtil = DateUtil.getInstance();
            Date effDate = dateUtil.addDaysToDate(receipt.getTxnDate(), 3);
            receipt.setEffectiveDate(dateUtil.getNextNoneWeekendHolidayDate(effDate));
        } else {
            receipt.setEffectiveDate(receipt.getTxnDate());
        }

        receipt.setReceiptNumber(createReceiptNumber(receipt));
        receipt.setTranSource(CommonConstants.TRAN_SOURCE_ORIGINAL);
        //create a new txnJournal
        try {
            //set as credit sign
            receipt.setAmount(receipt.getAmount().abs().negate());
            receipt = receiptRepo.save(receipt);
            //create txnJournal for this
            TxnType txnType = txnTypeRepo.findOne(receipt.getTxnType().getTxnTypeId());
            //sset to receipt
            receipt.setTxnType(txnType);
            txnJournalService.createTxnJournal(createTxnJournal(receipt));
            //return the receipt
            return receipt;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void reverseReceipt(Receipt receipt) throws BusinessException {
        Receipt _receipt = findReceipt(receipt.getReceiptId());
        if (_receipt == null) {
            throw new BusinessException(ErrorConstants.INVALID_RECEIPT);
        }
        //I think we should check for previous reversal to prevent duplicate
        if (_receipt.isReversal()) {
            throw new BusinessException(ErrorConstants.RECORD_ALREADY_REVERSED);
        }
        BigDecimal totalSubSum = receiptRepo.findUtilizedSum(receipt.getReceiptId());
        if (totalSubSum != null && totalSubSum.intValue() != 0) {
            throw new BusinessException(ErrorConstants.RECEIPT_ALREADY_USED);
        }
        Receipt newReceipt = new Receipt();
        newReceipt.setAmount(_receipt.getAmount().abs());
        newReceipt.setEffectiveDate(new Date());
        newReceipt.setModifiedBy(receipt.getModifiedBy());
        newReceipt.setModifiedTime(new Date());
        newReceipt.setNarration("REVERALS::::: " + receipt.getNarration());
        newReceipt.setPayer(_receipt.getPayer());
        newReceipt.setPaymentMode(_receipt.getPaymentMode());
        newReceipt.setPaymentNumber(_receipt.getPaymentNumber());
        newReceipt.setReceipt(_receipt);
        newReceipt.setReceiptNumber(_receipt.getReceiptNumber() + "/R");
        newReceipt.setReversal(true);
        newReceipt.setSchoolTerm(_receipt.getSchoolTerm());
        newReceipt.setSchoolId(_receipt.getSchoolId());
        newReceipt.setStatus(CommonConstants.STATUS_ACTIVE);
        newReceipt.setTranSource(CommonConstants.TRAN_SOURCE_ORIGINAL);
        newReceipt.setTxnDate(new Date());
        newReceipt.setTxnType(_receipt.getTxnType());
        try {
            receiptRepo.save(newReceipt);
            //create txnJournal for this
            txnJournalService.reverseTxnJournal(createTxnJournal(newReceipt));
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
    public Receipt findReceipt(Integer receiptId) {
        Receipt _receipt = receiptRepo.findOne(receiptId);
        _receipt.setReceiptBalance(getReceiptBalance(receiptId));
        return _receipt;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<Receipt> findReceipts(Integer receiptId) {
        List<Receipt> receipts = new ArrayList<>();
        receipts.add(receiptRepo.findOne(receiptId));
        receipts.addAll(receiptRepo.findSubReceipt(receiptId));

        return receipts;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public BigDecimal getReceiptBalance(Integer receiptId) {
        BigDecimal amount = BigDecimal.ZERO;
        Receipt _receipt = receiptRepo.findOne(receiptId);
        if (_receipt == null) { //if the base receipt is null return zero
            return amount;
        }
        amount = amount.add(_receipt.getAmount());
        BigDecimal totalSubSum = receiptRepo.findUtilizedSum(receiptId);
        //return the sum of totalSubSum and amount
        if (totalSubSum != null) {
            amount = amount.add(totalSubSum);
        }
        return amount;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<Receipt> findReceipts(ReceiptQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findReceipts(criteria);
    }

    /**
     * ************************************************************
     * ******************** ASSET AND LIABILITY *******************
     * ************************************************************
     */
    /**
     * {@inheritDoc }
     */
    @Override
    public Asset saveAsset(Asset asset) throws BusinessException {
        asset.setModifiedTime(new Date());
        try {
            asset.setAmount(asset.getUnitPrice().multiply(BigDecimal.valueOf(asset.getUnitCount())));
            return assetRepo.save(asset);
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
    public Asset findAsset(Integer assetId) {
        return assetRepo.findOne(assetId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<Asset> findAssets(AssetQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findAssets(criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void removeAsset(Asset asset) throws BusinessException {
        Asset _asset = findAsset(asset.getAssetId());
        _asset.setStatus(CommonConstants.STATUS_INACTIVE);
        //save the 
        try {
            assetRepo.save(asset);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Liability saveLiability(Liability liability) throws BusinessException {
        liability.setModifiedTime(new Date());
        try {
            return liabilityRepo.save(liability);
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
    public Liability findLiability(Integer liabilityId) {
        return liabilityRepo.findOne(liabilityId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<Liability> findLiabilities(LiabilityQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findLiabilities(criteria);
    }

    /**
     * creates a String of the format IXXXXX as receipt number
     *
     * @param receipt
     * @return String - formulated receipt number
     */
    private String createReceiptNumber(Receipt receipt) {
        Long l;
        DateUtil dateUtil = DateUtil.getInstance();
        int year = dateUtil.getYearValueFromDate(receipt.getTxnDate());
        Date firstDay = dateUtil.getStringAsDate("01/01/" + year);
        Date lastDay = dateUtil.getStringAsDate("31/12/" + year);
        l = receiptRepo.findReceiptYearCount(receipt.getSchoolTerm().getSchoolTermId(), firstDay, lastDay);

        return "I" + receipt.getSchoolTerm().getSchoolTermId() + "-"
                + StringUtil.leftPad("" + (l == null ? 1 : l.toString()), 4, '0');
    }

    /**
     * create a txn journal from receipt
     *
     * @param receipt
     * @return TxnJournals
     */
    private TxnJournal createTxnJournal(Receipt receipt) {
        TxnJournal txnJournal = new TxnJournal();
        txnJournal.setAltRef(null);
        txnJournal.setAmount(receipt.getAmount());
        txnJournal.setEffectiveDate(receipt.getEffectiveDate());
        txnJournal.setModifiedBy(receipt.getModifiedBy());
        txnJournal.setModifiedTime(new Date());
        txnJournal.setNarration(receipt.getNarration());
        txnJournal.setSchoolId(receipt.getTxnType().getSchoolId());
        txnJournal.setTxnDate(receipt.getTxnDate());
        txnJournal.setTxnJournalId(null);
        txnJournal.setTxnRef(receipt.getReceiptNumber());
        txnJournal.setTxnType(receipt.getTxnType());

        return txnJournal;
    }

    /**
     *
     *
     * @param _feeCollBatch
     * @param gradLevelId
     * @return FeeGroup
     */
    private FeeGroup getFeeGroup(Integer schoolId, Integer feeTypeCode, Integer gradLevelId) {
        List<FeeGroup> feeGroups = feeGroupRepo.findBySchoolFeeType(
                schoolId,
                feeTypeCode);
        if (feeGroups.isEmpty()) {
            return null;
        }
        for (FeeGroup feeGroup : feeGroups) {
            if (feeGroup.isApplyAll()
                    || feeGroup.getGradeLevel().getGradeLevelId() == null) {
                feeGroup.setTotalFee(getFeeGroupTotal(feeGroup));
                return feeGroup;
            }
            if (gradLevelId.equals(
                    feeGroup.getGradeLevel().getGradeLevelId())) {
                feeGroup.setTotalFee(getFeeGroupTotal(feeGroup));
                return feeGroup;
            }
        }
        return null;
    }

    /**
     *
     *
     * @param feeGroup
     * @return
     */
    private BigDecimal getFeeGroupTotal(FeeGroup feeGroup) {
        List<Fee> fees = feeGroup.getFeeList();
        BigDecimal totalfee = BigDecimal.ZERO;
        for (Fee fee : fees) {
            totalfee = totalfee.add(fee.getAmount());
        }
        return totalfee;
    }

    /**
     * returns the amount unutilized on a receipt. This will be the sum of all
     * the entry on that receipt the receipt included
     *
     * @param receipt
     * @return BigDecimal
     */
    private BigDecimal getAllReceiptSum(Receipt receipt) {
        List<Receipt> receipts = findReceipts(receipt.getReceiptId());
        BigDecimal outstanding = BigDecimal.ZERO;
        for (Receipt r : receipts) {
            outstanding = outstanding.add(r.getAmount());
        }
        return outstanding;
    }

    /**
     * returns the outstanding on the fee payment of a student on a fee group
     *
     * @param feeReceipt
     * @return BigDecimal
     */
    private BigDecimal getStudentOutStandingOnFee(FeeReceipt feeReceipt) {
        FeeGroup feeGroup = feeGroupRepo.findOne(feeReceipt.getFeeGroup().getFeeGroupId());
        BigDecimal totalFee = getFeeGroupTotal(feeGroup);
        BigDecimal totalPaid = feeReceiptRepo.findTotalPaidBySchoolStudent(
                feeReceipt.getSchoolStudent(), feeReceipt.getFeeGroup());
        totalPaid = totalPaid == null ? BigDecimal.ZERO : totalPaid;
        //return the difference between the total and the paid
        return totalFee.subtract(totalPaid);
    }

    private TxnJournal getTxnJournalForExpense(Expense expense, TxnType txnType) {

        TxnJournal txnJournal = new TxnJournal();
        txnJournal.setAltRef(null);
        txnJournal.setAmount(expense.getAmount());
        txnJournal.setEffectiveDate(expense.getTxnDate());
        txnJournal.setModifiedBy(expense.getModifiedBy());
        txnJournal.setModifiedTime(expense.getModifiedTime());
        txnJournal.setNarration(expense.getNarration());
        txnJournal.setSchoolId(txnType.getSchoolId());
        txnJournal.setTxnDate(expense.getTxnDate());
        txnJournal.setTxnJournalId(null);
        txnJournal.setTxnRef(expense.getTxnRef());
        txnJournal.setTxnType(expense.getTxnType());

        return txnJournal;
    }
}
