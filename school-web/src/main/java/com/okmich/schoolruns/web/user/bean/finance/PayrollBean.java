/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.common.entity.criteria.OrderClause;
import com.okmich.common.entity.criteria.OrderType;
import com.okmich.common.entity.criteria.WCDate;
import com.okmich.schoolruns.common.entity.EmployeePayroll;
import com.okmich.schoolruns.common.entity.EmployeePayrollItem;
import com.okmich.schoolruns.common.entity.Payroll;
import com.okmich.schoolruns.finance.service.FinanceService;
import com.okmich.schoolruns.finance.service.repo.EmployeePayrollQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.PayrollQueryCriteria;
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
public class PayrollBean extends _BaseBean {

    @ManagedProperty("#{financeService}")
    private FinanceService financeService;
    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private Payroll payroll;
    private EmployeePayroll employeePayroll;
    private EmployeePayrollItem employeePayrollItem;
    private Date startDate;
    private Date endDate;

    /**
     * Creates a new instance of PayrollBean
     */
    public PayrollBean() {
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
     * @return the payroll
     */
    public Payroll getPayroll() {
        if (this.payroll == null) {
            this.payroll = new Payroll();
        }
        return payroll;
    }

    /**
     * @param payroll the payroll to set
     */
    public void setPayroll(Payroll payroll) {
        this.payroll = payroll;
    }

    /**
     * @return the employeePayroll
     */
    public EmployeePayroll getEmployeePayroll() {
        if (this.employeePayroll == null) {
            this.employeePayroll = new EmployeePayroll();
        }
        return employeePayroll;
    }

    /**
     * @param employeePayroll the employeePayroll to set
     */
    public void setEmployeePayroll(EmployeePayroll employeePayroll) {
        this.employeePayroll = employeePayroll;
    }

    /**
     * @return the employeePayrollItem
     */
    public EmployeePayrollItem getEmployeePayrollItem() {
        if (this.employeePayrollItem == null) {
            this.employeePayrollItem = new EmployeePayrollItem();
        }
        return employeePayrollItem;
    }

    /**
     * @param employeePayrollItem the employeePayrollItem to set
     */
    public void setEmployeePayrollItem(EmployeePayrollItem employeePayrollItem) {
        this.employeePayrollItem = employeePayrollItem;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return
     */
    public String createPayroll() {
        setPayroll(financeSessionBean.getPayroll()); //the page used the session version 
        getPayroll().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getPayroll().setSchool(schoolSessionBean.getSchool());
        try {
            financeService.createPayroll(getPayroll(),
                    schoolSessionBean.getSchoolPref());
            return backToPayrollHome();
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findPayrolls() {
        PayrollQueryCriteria criteria = new PayrollQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        if (getStartDate() != null && getEndDate() != null) {
            criteria.setPaymentDate(getStartDate(), WCDate.BETWEEN, getEndDate());
        } else if (getStartDate() != null) {
            criteria.setPaymentDate(getStartDate());
        }
        List<OrderClause> vec = new ArrayList<>(1);
        vec.add(new OrderClause(PayrollQueryCriteria.paymentDate, OrderType.DESC));
        criteria.setOrderByColumn(vec);
        try {
            List<Payroll> payrolls = financeService.findPayrolls(criteria);
            financeSessionBean.setPayrollModel(new ListDataModel(payrolls));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findPayroll() {
        if (getPayroll().getPayrollId() != null) {
            financeSessionBean.setPayroll(getPayroll());
            return findEmployeePayrolls();
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String approvePayroll() {
        setPayroll(financeSessionBean.getPayroll()); //the page used the session version 
        getPayroll().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            financeService.approvePayroll(getPayroll());
            return backToPayrollHome();
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String rejectPayroll() {
        setPayroll(financeSessionBean.getPayroll()); //the page used the session version 
        try {
            financeService.deletePayroll(getPayroll());
            return backToPayrollHome();
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String backToPayrollHome() {
        financeSessionBean.setPayroll(null);
        financeSessionBean.setEmployeePayroll(null);
        return "/schooluser/finance/payrollsearch";
    }

    /**
     *
     * @return
     */
    public String findEmployeePayrolls() {
        EmployeePayrollQueryCriteria criteria = new EmployeePayrollQueryCriteria();
        criteria.setPayrollId(financeSessionBean.getPayroll().getPayrollId());
        List<OrderClause> vec = new ArrayList<>(1);
        vec.add(new OrderClause(EmployeePayrollQueryCriteria.employeeName));
        criteria.setOrderByColumn(vec);
        try {
            List<EmployeePayroll> employeePayrolls =
                    financeService.findEmployeePayrolls(criteria);
            financeSessionBean.setEmployeePayrollModel(
                    new ListDataModel(employeePayrolls));

            return "/schooluser/finance/employeepayrollsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findEmployeePayroll() {
        if (getEmployeePayroll().getEmployeePayrollId() != null) {
            financeSessionBean.setEmployeePayroll(getEmployeePayroll());
            List<EmployeePayrollItem> entList = financeService.findEmployeePayrollItems(
                    getEmployeePayroll().getEmployeePayrollId());
            financeSessionBean.setEmployeePayrollItemModel(new ListDataModel<>(entList));
            return "/schooluser/finance/employeepayrolldetails";
        }
        return "";
    }

    /**
     *
     *
     * @return
     */
    public String prepareForDeduction() {
        financeSessionBean.setEditMode(DELETE); //use delete to stand for deduction
        this.employeePayrollItem = new EmployeePayrollItem();
        this.employeePayrollItem.setEmployeePayroll(financeSessionBean.getEmployeePayroll());
        return "/schooluser/finance/employeepayrollitemdetails";
    }

    /**
     *
     *
     * @return
     */
    public String prepareForAdditions() {
        financeSessionBean.setEditMode(CREATE); //use delete to stand for addition
        this.employeePayrollItem = new EmployeePayrollItem();
        this.employeePayrollItem.setEmployeePayroll(financeSessionBean.getEmployeePayroll());
        return "/schooluser/finance/employeepayrollitemdetails";
    }

    /**
     *
     * @return
     */
    public String makeDeduction() {
        getEmployeePayrollItem().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getEmployeePayrollItem().setEmployeePayroll(financeSessionBean.getEmployeePayroll());
        try {
            EmployeePayrollItem empPayrollItem = financeService.makeDeduction(
                    getEmployeePayrollItem(), schoolSessionBean.getSchoolPref());
            if (empPayrollItem != null) {
                //changes made
                reflectEmployeePayrollChanges(empPayrollItem);

                ((List<EmployeePayrollItem>) financeSessionBean.getEmployeePayrollItemModel()
                        .getWrappedData()).add(empPayrollItem);
            }
            return "/schooluser/finance/employeepayrolldetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String makeAdditions() {
        getEmployeePayrollItem().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getEmployeePayrollItem().setEmployeePayroll(financeSessionBean.getEmployeePayroll());
        try {
            EmployeePayrollItem empPayrollItem = financeService.makeAddition(
                    getEmployeePayrollItem(), schoolSessionBean.getSchoolPref());
            if (empPayrollItem != null) {
                //changes made
                reflectEmployeePayrollChanges(empPayrollItem);

                ((List<EmployeePayrollItem>) financeSessionBean.getEmployeePayrollItemModel()
                        .getWrappedData()).add(empPayrollItem);
            }
            return "/schooluser/finance/employeepayrolldetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @param arg
     */
    private void reflectEmployeePayrollChanges(EmployeePayrollItem arg) {
        financeSessionBean.getEmployeePayroll().setGross(
                financeSessionBean.getEmployeePayroll().getGross().add(
                arg.getGross()));
        financeSessionBean.getEmployeePayroll().setTax(
                financeSessionBean.getEmployeePayroll().getTax().add(
                arg.getTax()));
        financeSessionBean.getEmployeePayroll().setNet(
                financeSessionBean.getEmployeePayroll().getNet().add(
                arg.getNet()));

        List<EmployeePayroll> employeerolls = (List<EmployeePayroll>) financeSessionBean.
                getEmployeePayrollModel().getWrappedData();
        //remove the old employee payroll
        int index = employeerolls.indexOf(financeSessionBean.getEmployeePayroll());
        employeerolls.remove(financeSessionBean.getEmployeePayroll());
        //add the new value
        employeerolls.add(index, financeSessionBean.getEmployeePayroll());
        //
        financeSessionBean.setEmployeePayrollModel(
                new ListDataModel<>(employeerolls));
    }
}
