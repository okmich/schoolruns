/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.common.entity.criteria.OrderClause;
import com.okmich.common.entity.criteria.OrderType;
import com.okmich.common.entity.criteria.WCDate;
import com.okmich.schoolruns.common.entity.TxnJournal;
import com.okmich.schoolruns.common.entity.TxnJournalDetail;
import com.okmich.schoolruns.finance.service.TxnJournalService;
import com.okmich.schoolruns.finance.service.repo.TxnJournalDetailQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.TxnJournalQueryCriteria;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.application.FacesMessage;
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
public class TxnJournalBean extends _BaseBean {

    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{txnJournalService}")
    private TxnJournalService txnJournalService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private TxnJournal txnJournal;
    private TxnJournalDetail txnJournalDetail;
    private Date fromDate;
    private Date toDate;
    private Integer txnTypeId;
    private String accountCode;

    /**
     * Creates a new instance of TxnJournalBean
     */
    public TxnJournalBean() {
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
     * @param txnJournalService the txnJournalService to set
     */
    public void setTxnJournalService(TxnJournalService txnJournalService) {
        this.txnJournalService = txnJournalService;
    }

    /**
     * @param userLoginSessionBean1 the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean1) {
        this.userLoginSessionBean = userLoginSessionBean1;
    }

    /**
     * @return the txnJournal
     */
    public TxnJournal getTxnJournal() {
        if (txnJournal == null) {
            this.txnJournal = new TxnJournal();
        }
        return txnJournal;
    }

    /**
     * @param txnJournal the txnJournal to set
     */
    public void setTxnJournal(TxnJournal txnJournal) {
        this.txnJournal = txnJournal;
    }

    /**
     * @return the txnJournalDetail
     */
    public TxnJournalDetail getTxnJournalDetail() {
        return txnJournalDetail;
    }

    /**
     * @param txnJournalDetail the txnJournalDetail to set
     */
    public void setTxnJournalDetail(TxnJournalDetail txnJournalDetail) {
        this.txnJournalDetail = txnJournalDetail;
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
     *
     * @return
     */
    public String transferAccount() {
        getTxnJournal().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getTxnJournal().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        try {
            TxnJournal _txnJournal = txnJournalService.createTxnJournal(getTxnJournal());
            if (_txnJournal != null) {
                setTxnJournal(_txnJournal);
                findTxnJournal();
                FacesUtil.getFacesContext().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "",
                        "Transfer Completed!!!"));
                return "/schooluser/finance/txnjournaldetails";
            }
            return "";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findTxnJournals() {
        TxnJournalQueryCriteria criteria = buildQueryCriteria();
        try {
            List<TxnJournal> dataList = txnJournalService.findTxnJournals(criteria);
            financeSessionBean.setTxnJournalModel(new ListDataModel(dataList));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findTxnJournal() {
        if (getTxnJournal().getTxnJournalId() != null) {
            setTxnJournal(getTxnJournal());
            List<TxnJournalDetail> dataList =
                    txnJournalService.findTxnJournalDetails(getTxnJournal().getTxnJournalId());
            financeSessionBean.setTxnJournalDetailModel(new ListDataModel(dataList));
            return "/schooluser/finance/txnjournaldetails";
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findTxnJournalDetails() {
        TxnJournalDetailQueryCriteria criteria = buildDetailQueryCriteria();
        try {
            List<TxnJournalDetail> dataList = txnJournalService.findTxnJournalDetails(criteria);
            financeSessionBean.setTxnJournalDetailModel(new ListDataModel(dataList));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findTxnJournalDetail() {
        if (getTxnJournalDetail().getTxnJournalDetailId() != null) {
            setTxnJournalDetail(getTxnJournalDetail());
            return "/schooluser/finance/txnjournaldetaildetails";
        }
        return "";
    }

    public String retToMasterSearch() {
        financeSessionBean.setEditMode(null);
        financeSessionBean.setTxnJournalDetailModel(null);
        return "/schooluser/finance/txnjournalsearch";
    }

    private TxnJournalQueryCriteria buildQueryCriteria() {
        TxnJournalQueryCriteria criteria = new TxnJournalQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());

        if (this.fromDate != null && this.toDate != null) {
            criteria.setTxnDate(fromDate, WCDate.BETWEEN, toDate);
        }
        if (this.txnTypeId != null && this.txnTypeId != 0) {
            criteria.setTxnTypeId(this.txnTypeId);
        }

        Vector<OrderClause> vec = new Vector<>();
        vec.add(new OrderClause(TxnJournalQueryCriteria.modifiedTime, OrderType.DESC));
        criteria.setOrderByColumn(vec);

        return criteria;
    }

    private TxnJournalDetailQueryCriteria buildDetailQueryCriteria() {
        TxnJournalDetailQueryCriteria criteria = new TxnJournalDetailQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());

        if (this.accountCode != null && !this.accountCode.isEmpty()) {
            criteria.setAccountCode(this.accountCode);
        }
        if (this.fromDate != null && this.toDate != null) {
            criteria.setTxnDate(fromDate, WCDate.BETWEEN, toDate);
        }
        if (this.txnTypeId != null) {
            criteria.setTxnTypeId(this.txnTypeId);
        }

        Vector<OrderClause> vec = new Vector<>();
        vec.add(new OrderClause(TxnJournalQueryCriteria.modifiedTime, OrderType.DESC));
        criteria.setOrderByColumn(vec);
        return criteria;
    }

    public String truncate(String val) {
        if (val == null || val.length() < 20) {
            return val;
        }
        return new StringBuilder(
                val.substring(0, Math.min(20, val.length())))
                .append("...").toString();
    }
}
