/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.common.entity.criteria.WCDate;
import com.okmich.common.entity.criteria.WCString;
import com.okmich.schoolruns.common.entity.Expense;
import com.okmich.schoolruns.common.entity.SchoolTerm;
import com.okmich.schoolruns.finance.service.FinanceService;
import com.okmich.schoolruns.finance.service.repo.ExpenseQueryCriteria;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class ExpenseBean extends _BaseBean {

    @ManagedProperty("#{financeService}")
    private FinanceService financeService;
    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private Expense expense;
    private String description;
    private Integer txnTypeId;
    private Date fromDate, toDate;
    private boolean currentYear;

    /**
     * Creates a new instance of ExpenseBean
     */
    public ExpenseBean() {
        this.expense = new Expense();
    }

    /**
     * @param financeService the financeService to set
     */
    public void setFinanceService(FinanceService financeService) {
        this.financeService = financeService;
    }

    /**
     * @param financeSessionBean the financeSessionBean to set
     */
    public void setFinanceSessionBean(FinanceSessionBean financeSessionBean) {
        this.financeSessionBean = financeSessionBean;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @return the expense
     */
    public Expense getExpense() {
        if (expense == null) {
            this.expense = new Expense();
        }
        return expense;
    }

    /**
     * @param expense the expense to set
     */
    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the txnTypeId
     */
    public Integer getTxnTypeId() {
        return txnTypeId;
    }

    /**
     * @param txnTypeId the txnTypeId to set
     */
    public void setTxnTypeId(Integer txnTypeId) {
        this.txnTypeId = txnTypeId;
    }

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the currentYear
     */
    public boolean isCurrentYear() {
        return currentYear;
    }

    /**
     * @param currentYear the currentYear to set
     */
    public void setCurrentYear(boolean currentYear) {
        this.currentYear = currentYear;
    }

    public String saveExpense() {
        getExpense().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getExpense().setSchoolTerm(
                new SchoolTerm(schoolSessionBean.getSchoolCalendarData().getSchoolTermId()));
        try {
            Expense _expense = financeService.createExpense(getExpense());
            //if all goes well add the to the data model as the only result
            List<Expense> expenses = new ArrayList<>(1);
            expenses.add(_expense);
            financeSessionBean.setExpenseModel(new ListDataModel<>(expenses));
            return "/schooluser/finance/expensesearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String findExpenses() {
        ExpenseQueryCriteria criteria = buildQueryCriteria();
        try {
            List<Expense> expenses = financeService.findExpenses(criteria);
            financeSessionBean.setExpenseModel(new ListDataModel(expenses));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String findExpense() {
        if (getExpense().getExpenseId() != null) {
            setExpense(getExpense());
            return "/schooluser/finance/expensedetails";
        }
        return "";
    }

    public String prepareForReverse() {
        if (getExpense().getExpenseId() != null) {
            setExpense(getExpense());
            return "/schooluser/finance/expensedetails";
        }
        return "";
    }

    public String reverseExpense() {
        getExpense().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            financeService.reverseExpense(getExpense());
            return "/schooluser/finance/expensesearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    private ExpenseQueryCriteria buildQueryCriteria() {
        ExpenseQueryCriteria criteria = new ExpenseQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());

        if (this.description != null && !this.description.trim().isEmpty()) {
            criteria.setNarration(this.description, WCString.LIKE);
        }
        if (this.txnTypeId != null && this.txnTypeId != 0) {
            criteria.setTxnTypeId(this.txnTypeId);
        }
        if (this.fromDate != null && this.toDate != null) {
            criteria.setTxnDate(fromDate, WCDate.BETWEEN, toDate);
        }
        if (currentYear) {
            criteria.setSchoolYearId(schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
        } else {
            criteria.setSchoolTermId(schoolSessionBean.getSchoolCalendarData().getSchoolTermId());
        }
        return criteria;
    }
}
