/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.schoolruns.common.entity.Account;
import com.okmich.schoolruns.finance.service.FinanceSetupService;
import com.okmich.schoolruns.finance.service.repo.AccountQueryCriteria;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@ViewScoped
public class AccountBean extends _BaseBean {

    @ManagedProperty("#{financeSetupService}")
    private FinanceSetupService financeSetupService;
    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private Account account = new Account();
    private String accountCode;
    private String accountTypeCode;
    private DataModel<Account> accountDataModel;

    public AccountBean() {
    }

    /**
     * @param financeSetupService the financeSetupService to set
     */
    public void setFinanceSetupService(FinanceSetupService financeSetupService) {
        this.financeSetupService = financeSetupService;
    }

    /**
     * @param financeSessionBean the financeSessionBean to set
     */
    public void setFinanceSessionBean(FinanceSessionBean financeSessionBean1) {
        this.financeSessionBean = financeSessionBean1;
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
     * @return
     */
    public String saveAccount() {
        getAccount().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getAccount().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        try {
            Account _account = financeSetupService.saveAccount(getAccount());
            List<Account> accounts = ((List<Account>) accountDataModel.getWrappedData());
            if (!accounts.contains(_account)) {
                accounts.add(_account);
            } else {
                accounts.set(accounts.indexOf(_account), _account);
            }
            financeSessionBean.getAccounts().clear();
            clearForm();
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * @return
     */
    public String findAccounts() {
        doFindAccounts();
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setAccount(new Account());
        setEditMode(null);
        return "";
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        if (account == null) {
            account = new Account();
        }
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * @return the accountCode
     */
    public String getAccountCode() {
        return accountCode;
    }

    /**
     * @param accountCode the accountCode to set
     */
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    /**
     * @return the accountTypeCode
     */
    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    /**
     * @param accountTypeCode the accountTypeCode to set
     */
    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    /**
     * @return the accountDataModel
     */
    public DataModel<Account> getAccountDataModel() {
        if (accountDataModel == null) {
            doFindAccounts();
        }
        return accountDataModel;
    }

    /**
     * @param accountDataModel the accountDataModel to set
     */
    public void setAccountDataModel(DataModel<Account> accountDataModel) {
        this.accountDataModel = accountDataModel;
    }

    /**
     *
     */
    private void doFindAccounts() {
        AccountQueryCriteria criteria = new AccountQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        if (this.accountCode != null && !this.accountCode.trim().isEmpty()) {
            criteria.setAccountCode(accountCode);;
        }
        if (this.accountTypeCode != null && !this.accountTypeCode.trim().isEmpty()) {
            criteria.setAccountTypeId(accountTypeCode);
        }
        try {
            this.accountDataModel = new ListDataModel<>(
                    financeSetupService.findAccounts(criteria));
        } catch (Exception ex) {
            processException(ex);
        }
    }
}