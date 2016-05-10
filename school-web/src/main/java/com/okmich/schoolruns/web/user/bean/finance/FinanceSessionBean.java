/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.common.entity.criteria.OrderClause;
import com.okmich.schoolruns.common.entity.Account;
import com.okmich.schoolruns.common.entity.Asset;
import com.okmich.schoolruns.common.entity.EmployeePayroll;
import com.okmich.schoolruns.common.entity.EmployeePayrollItem;
import com.okmich.schoolruns.common.entity.Expense;
import com.okmich.schoolruns.common.entity.Fee;
import com.okmich.schoolruns.common.entity.FeeGroup;
import com.okmich.schoolruns.common.entity.FeeReceipt;
import com.okmich.schoolruns.common.entity.Liability;
import com.okmich.schoolruns.common.entity.PayStructure;
import com.okmich.schoolruns.common.entity.Payroll;
import com.okmich.schoolruns.common.entity.Receipt;
import com.okmich.schoolruns.common.entity.Store;
import com.okmich.schoolruns.common.entity.StoreActivity;
import com.okmich.schoolruns.common.entity.StoreItem;
import com.okmich.schoolruns.common.entity.TxnJournal;
import com.okmich.schoolruns.common.entity.TxnJournalDetail;
import com.okmich.schoolruns.common.entity.TxnType;
import com.okmich.schoolruns.finance.service.FinanceSetupService;
import com.okmich.schoolruns.finance.service.repo.AccountQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.FeeGroupQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.FeeQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.TxnTypeQueryCriteria;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.List;
import java.util.Vector;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@SessionScoped
public class FinanceSessionBean extends _BaseBean {

    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{financeSetupService}")
    private FinanceSetupService financeSetupService;
    private EmployeePayroll employeePayroll;
    private Payroll payroll;
    private Receipt receipt;
    private Store store;
    private StoreItem storeItem;
    private List<Account> accounts;
    private List<Fee> fees;
    private List<FeeGroup> feeGroups;
    private List<TxnType> txnTypes;
    private List<PayStructure> payStructures;
    private List<SchoolStudentData> schoolStudentDataList;
    private DataModel<Asset> assetModel;
    private DataModel<EmployeePayroll> employeePayrollModel;
    private DataModel<EmployeePayrollItem> employeePayrollItemModel;
    private DataModel<Expense> expenseModel;
    private DataModel<FeeReceipt> feeReceiptModel;
    private DataModel<Liability> liabilityModel;
    private DataModel<Payroll> payrollModel;
    private DataModel<Receipt> receiptModel;
    private DataModel<StoreActivity> storeActivityModel;
    private DataModel<Store> storeDataModel;
    private DataModel<StoreItem> storeItemDataModel;
    private DataModel<TxnJournal> txnJournalModel;
    private DataModel<TxnJournalDetail> txnJournalDetailModel;

    /**
     * Creates a new instance of FinanceSessionBean
     */
    public FinanceSessionBean() {
    }

    /**
     * sets all the values within to null to be call on logout
     */
    public void cleanUp() {
        this.accounts = null;
        this.assetModel = null;
        this.expenseModel = null;
        this.feeGroups = null;
        this.feeReceiptModel = null;
        this.fees = null;
        this.liabilityModel = null;
        this.payStructures = null;
        this.store = null;
        this.storeActivityModel = null;
        this.storeDataModel = null;
        this.storeItem = null;
        this.storeItemDataModel = null;
        this.txnJournalDetailModel = null;
        this.txnJournalModel = null;
        this.txnTypes = null;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @param financeSetupService the financeSetupService to set
     */
    public void setFinanceSetupService(FinanceSetupService financeSetupService) {
        this.financeSetupService = financeSetupService;
    }

    /**
     *
     * @return
     */
    public List<Account> getAccounts() {
        if (accounts == null || accounts.isEmpty()) {
            AccountQueryCriteria criteria = new AccountQueryCriteria();
            criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());

            Vector<OrderClause> vec = new Vector<>(1);
            vec.add(new OrderClause(AccountQueryCriteria.accountCode));
            criteria.setOrderByColumn(vec);

            accounts = financeSetupService.findAccounts(criteria);
        }
        return accounts;
    }

    /**
     * @return the feeGroups
     */
    public List<FeeGroup> getFeeGroups() {
        if (feeGroups == null || feeGroups.isEmpty()) {
            FeeGroupQueryCriteria criteria = new FeeGroupQueryCriteria();
            criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());

            Vector<OrderClause> vec = new Vector<>(1);
            vec.add(new OrderClause(FeeGroupQueryCriteria.description));
            criteria.setOrderByColumn(vec);

            feeGroups = financeSetupService.findFeeGroups(criteria);
        }
        return feeGroups;
    }

    /**
     * @return the fees
     */
    public List<Fee> getFees() {
        if (fees == null || fees.isEmpty()) {
            FeeQueryCriteria criteria = new FeeQueryCriteria();
            criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());

            Vector<OrderClause> vec = new Vector<>(1);
            vec.add(new OrderClause(FeeQueryCriteria.modifiedTime));
            criteria.setOrderByColumn(vec);

            fees = financeSetupService.findFees(criteria);
        }
        return fees;
    }

    /**
     *
     * @return
     */
    public List<TxnType> getTxnTypes() {
        if (txnTypes == null || txnTypes.isEmpty()) {
            TxnTypeQueryCriteria criteria = new TxnTypeQueryCriteria();
            criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());

            Vector<OrderClause> vec = new Vector<>(1);
            vec.add(new OrderClause(TxnTypeQueryCriteria.description));
            criteria.setOrderByColumn(vec);

            txnTypes = financeSetupService.findTxnTypes(criteria);
        }
        return txnTypes;
    }

    /**
     * @return the employeePayroll
     */
    public EmployeePayroll getEmployeePayroll() {
        return employeePayroll;
    }

    /**
     * @param employeePayroll the employeePayroll to set
     */
    public void setEmployeePayroll(EmployeePayroll employeePayroll) {
        this.employeePayroll = employeePayroll;
    }

    /**
     * @return the payroll
     */
    public Payroll getPayroll() {
        return payroll;
    }

    /**
     * @param payroll the payroll to set
     */
    public void setPayroll(Payroll payroll) {
        this.payroll = payroll;
    }

    /**
     * @return the receipt
     */
    public Receipt getReceipt() {
        return receipt;
    }

    /**
     * @param receipt the receipt to set
     */
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    /**
     * @return the store
     */
    public Store getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * @return the storeItem
     */
    public StoreItem getStoreItem() {
        return storeItem;
    }

    /**
     * @param storeItem the storeItem to set
     */
    public void setStoreItem(StoreItem storeItem) {
        this.storeItem = storeItem;
    }

    /**
     * @return the assetModel
     */
    public DataModel<Asset> getAssetModel() {
        return assetModel;
    }

    /**
     * @param assetModel the assetModel to set
     */
    public void setAssetModel(DataModel<Asset> assetModel) {
        this.assetModel = assetModel;
    }

    /**
     * @return the employeePayrollModel
     */
    public DataModel<EmployeePayroll> getEmployeePayrollModel() {
        return employeePayrollModel;
    }

    /**
     * @param employeePayrollModel the employeePayrollModel to set
     */
    public void setEmployeePayrollModel(DataModel<EmployeePayroll> employeePayrollModel) {
        this.employeePayrollModel = employeePayrollModel;
    }

    /**
     * @return the employeePayrollItemModel
     */
    public DataModel<EmployeePayrollItem> getEmployeePayrollItemModel() {
        return employeePayrollItemModel;
    }

    /**
     * @param employeePayrollItemModel the employeePayrollItemModel to set
     */
    public void setEmployeePayrollItemModel(DataModel<EmployeePayrollItem> employeePayrollItemModel) {
        this.employeePayrollItemModel = employeePayrollItemModel;
    }

    /**
     * @return the expenseModel
     */
    public DataModel<Expense> getExpenseModel() {
        return expenseModel;
    }

    /**
     * @param expenseModel the expenseModel to set
     */
    public void setExpenseModel(DataModel<Expense> expenseModel) {
        this.expenseModel = expenseModel;
    }

    /**
     * @return the feeReceiptModel
     */
    public DataModel<FeeReceipt> getFeeReceiptModel() {
        return feeReceiptModel;
    }

    /**
     * @param feeReceiptModel the feeReceiptModel to set
     */
    public void setFeeReceiptModel(DataModel<FeeReceipt> feeReceiptModel) {
        this.feeReceiptModel = feeReceiptModel;
    }

    /**
     * @return the liabilityModel
     */
    public DataModel<Liability> getLiabilityModel() {
        return liabilityModel;
    }

    /**
     * @param liabilityModel the liabilityModel to set
     */
    public void setLiabilityModel(DataModel<Liability> liabilityModel) {
        this.liabilityModel = liabilityModel;
    }

    /**
     * @return the payrollModel
     */
    public DataModel<Payroll> getPayrollModel() {
        return payrollModel;
    }

    /**
     * @param payrollModel the payrollModel to set
     */
    public void setPayrollModel(DataModel<Payroll> payrollModel) {
        this.payrollModel = payrollModel;
    }

    /**
     * @return the receiptModel
     */
    public DataModel<Receipt> getReceiptModel() {
        return receiptModel;
    }

    /**
     * @param receiptModel the receiptModel to set
     */
    public void setReceiptModel(DataModel<Receipt> receiptModel) {
        this.receiptModel = receiptModel;
    }

    /**
     * @return the storeActivityModel
     */
    public DataModel<StoreActivity> getStoreActivityModel() {
        return storeActivityModel;
    }

    /**
     * @param storeActivityModel the storeActivityModel to set
     */
    public void setStoreActivityModel(DataModel<StoreActivity> storeActivityModel) {
        this.storeActivityModel = storeActivityModel;
    }

    /**
     * @return the storeDataModel
     */
    public DataModel<Store> getStoreDataModel() {
        return storeDataModel;
    }

    /**
     * @param storeDataModel the storeDataModel to set
     */
    public void setStoreDataModel(DataModel<Store> storeDataModel) {
        this.storeDataModel = storeDataModel;
    }

    /**
     * @return the storeItemDataModel
     */
    public DataModel<StoreItem> getStoreItemDataModel() {
        return storeItemDataModel;
    }

    /**
     * @param storeItemDataModel the storeItemDataModel to set
     */
    public void setStoreItemDataModel(DataModel<StoreItem> storeItemDataModel) {
        this.storeItemDataModel = storeItemDataModel;
    }

    /**
     * @return the txnJournalModel
     */
    public DataModel<TxnJournal> getTxnJournalModel() {
        return txnJournalModel;
    }

    /**
     * @param txnJournalDataModel the txnJournalModel to set
     */
    public void setTxnJournalModel(DataModel<TxnJournal> txnJournalModel) {
        this.txnJournalModel = txnJournalModel;
    }

    /**
     * @return the txnJournalDetailModel
     */
    public DataModel<TxnJournalDetail> getTxnJournalDetailModel() {
        return txnJournalDetailModel;
    }

    /**
     * @param txnJournalDetailModel the txnJournalDetailModel to set
     */
    public void setTxnJournalDetailModel(DataModel<TxnJournalDetail> txnJournalDetailModel) {
        this.txnJournalDetailModel = txnJournalDetailModel;
    }

    /**
     * @return the payStructures
     */
    public List<PayStructure> getPayStructures() {
        if (payStructures == null || payStructures.isEmpty()) {
            payStructures = financeSetupService.findPayStructures(
                    schoolSessionBean.getSchool().getSchoolId());
        }
        return payStructures;
    }

    /**
     * @return the schoolStudentDataList
     */
    public List<SchoolStudentData> getSchoolStudentDataList() {
        return schoolStudentDataList;
    }

    /**
     * @param schoolStudentDataList the schoolStudentDataList to set
     */
    public void setSchoolStudentDataList(List<SchoolStudentData> schoolStudentDataList) {
        this.schoolStudentDataList = schoolStudentDataList;
    }
}
