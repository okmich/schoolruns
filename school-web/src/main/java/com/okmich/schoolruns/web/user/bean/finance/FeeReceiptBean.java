/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.common.entity.criteria.OrderClause;
import com.okmich.common.entity.criteria.OrderType;
import com.okmich.common.entity.criteria.WCDate;
import com.okmich.common.entity.criteria.WCString;
import com.okmich.schoolruns.common.entity.FeeReceipt;
import com.okmich.schoolruns.finance.service.FinanceService;
import com.okmich.schoolruns.finance.service.repo.FeeReceiptQueryCriteria;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.Date;
import java.util.List;
import java.util.Vector;
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
public class FeeReceiptBean extends _BaseBean {

    @ManagedProperty("#{financeService}")
    private FinanceService financeService;
    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private FeeReceipt feeReceipt = new FeeReceipt();
    private String receiptNumber;
    private String surname;
    private Integer schoolClassId;
    private Integer gradeLevelId;
    private Date fromDate;
    private Date toDate;
    private boolean currentYear = true;
    private boolean currentTerm = true;

    /**
     * Creates a new instance of ReceiptBean
     */
    public FeeReceiptBean() {
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
     * @return the feeReceipt
     */
    public FeeReceipt getFeeReceipt() {
        if (this.feeReceipt == null) {
            this.feeReceipt = new FeeReceipt();
        }
        return feeReceipt;
    }

    /**
     * @param feeReceipt the feeReceipt to set
     */
    public void setFeeReceipt(FeeReceipt feeReceipt) {
        this.feeReceipt = feeReceipt;
    }

    /**
     * @return the receiptNumber
     */
    public String getReceiptNumber() {
        return receiptNumber;
    }

    /**
     * @param receiptNumber the receiptNumber to set
     */
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the schoolClassId
     */
    public Integer getSchoolClassId() {
        return schoolClassId;
    }

    /**
     * @param schoolClassId the schoolClassId to set
     */
    public void setSchoolClassId(Integer schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    /**
     * @return the gradeLevelId
     */
    public Integer getGradeLevelId() {
        return gradeLevelId;
    }

    /**
     * @param gradeLevelId the gradeLevelId to set
     */
    public void setGradeLevelId(Integer gradeLevelId) {
        this.gradeLevelId = gradeLevelId;
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

    /**
     * @return the currentTerm
     */
    public boolean isCurrentTerm() {
        return currentTerm;
    }

    /**
     * @param currentTerm the currentTerm to set
     */
    public void setCurrentTerm(boolean currentTerm) {
        this.currentTerm = currentTerm;
    }

    public String saveFeeReceipt() {
        getFeeReceipt().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getFeeReceipt().setReceipt(financeSessionBean.getReceipt());
        getFeeReceipt().setSchoolTermId(schoolSessionBean.getSchoolCalendarData().getSchoolTermId());
        try {
            financeService.saveFeeReceipt(getFeeReceipt());
            financeSessionBean.setEditMode(VIEW);
            return "/schooluser/finance/receiptdetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String saveAnotherFeeReceipt() {
        saveFeeReceipt();
        //reset the editmode
        financeSessionBean.setEditMode(EDIT);

        return "/schooluser/finance/feereceiptdetails";
    }

    public String findFeeReceipt() {
        if (getFeeReceipt().getFeeReceiptId() != null) {
            financeSessionBean.setEditMode(VIEW);
            return "/schooluser/finance/feereceiptdetails";
        }
        return "";
    }

    public String findFeeReceipts() {
        FeeReceiptQueryCriteria criteria = buildQueryCriteria();
        try {
            List<FeeReceipt> feeReceipts = financeService.findFeeReceipts(criteria);
            financeSessionBean.setFeeReceiptModel(new ListDataModel(feeReceipts));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String reverseFeeReceipt() {
        getFeeReceipt().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            financeService.reverseFeeReceipt(getFeeReceipt());
            return "/schooluser/finance/feereceiptsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    private FeeReceiptQueryCriteria buildQueryCriteria() {
        FeeReceiptQueryCriteria criteria = new FeeReceiptQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());

        if (currentTerm) {
            criteria.setSchoolTermId(schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
        } else if (currentYear) {
            criteria.setSchoolYearId(schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
        }
        if (this.gradeLevelId != null && this.gradeLevelId != 0) {
            criteria.setGradeLevelId(this.gradeLevelId);
        }
        if (this.schoolClassId != null && this.schoolClassId != 0) {
            criteria.setSchoolClassId(this.schoolClassId);
        }
        if (this.receiptNumber != null && !this.receiptNumber.trim().isEmpty()) {
            criteria.setReceiptNumber(this.receiptNumber, WCString.LIKE);
        }
        if (this.fromDate != null && this.toDate != null) {
            criteria.setTxnDate(this.fromDate, WCDate.BETWEEN, this.toDate);
        }
        if (this.surname != null && !this.surname.trim().isEmpty()) {
            criteria.setSurname(this.surname, WCString.LIKE);
        }

        Vector<OrderClause> vec = new Vector<>();
        vec.add(new OrderClause(FeeReceiptQueryCriteria.modifiedTime, OrderType.DESC));
        criteria.setOrderByColumn(vec);

        return criteria;
    }
}