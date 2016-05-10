/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.common.entity.criteria.OrderClause;
import com.okmich.common.entity.criteria.OrderType;
import com.okmich.common.entity.criteria.WCDate;
import com.okmich.common.entity.criteria.WCString;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Receipt;
import com.okmich.schoolruns.common.entity.SchoolTerm;
import com.okmich.schoolruns.finance.service.FinanceService;
import com.okmich.schoolruns.finance.service.repo.ReceiptQueryCriteria;
import com.okmich.schoolruns.school.service.SchoolStudentService;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.school.service.repo.SchoolStudentQueryCriteria;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.common.notification.MessageGenerator;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class ReceiptBean extends _BaseBean {

    @ManagedProperty("#{financeService}")
    private FinanceService financeService;
    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;
    @ManagedProperty("#{messageGenerator}")
    private MessageGenerator messageGenerator;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{schoolStudentService}")
    private SchoolStudentService schoolStudentService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private Receipt receipt = new Receipt();
    private String receiptNumber;
    private String txnTypeCode;
    private Integer txnTypeId;
    private Date fromDate;
    private Date toDate;
    private boolean feeType;
    private boolean currentTerm;
    private boolean currentYear;
    private BigDecimal receiptTotal;

    /**
     * Creates a new instance of ReceiptBean
     */
    public ReceiptBean() {
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
     * @param messageGenerator the messageGenerator to set
     */
    public void setMessageGenerator(MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @param schoolStudentService the schoolStudentService to set
     */
    public void setSchoolStudentService(SchoolStudentService schoolStudentService1) {
        this.schoolStudentService = schoolStudentService1;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @return the receipt
     */
    public Receipt getReceipt() {
        if (receipt == null) {
            receipt = new Receipt();
        }
        return receipt;
    }

    /**
     * @param receipt the receipt to set
     */
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
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
     * @return the txnTypeCode
     */
    public String getTxnTypeCode() {
        return txnTypeCode;
    }

    /**
     * @param txnTypeCode the txnTypeCode to set
     */
    public void setTxnTypeCode(String txnTypeCode) {
        this.txnTypeCode = txnTypeCode;
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
     * @return the feeType
     */
    public boolean isFeeType() {
        return feeType;
    }

    /**
     * @param feeType the feeType to set
     */
    public void setFeeType(boolean feeType) {
        this.feeType = feeType;
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
     * @return the receiptTotal
     */
    public BigDecimal getReceiptTotal() {
        return receiptTotal;
    }

    /**
     * @param receiptTotal the receiptTotal to set
     */
    public void setReceiptTotal(BigDecimal receiptTotal) {
        this.receiptTotal = receiptTotal;
    }

    public String prepareForCreate() {
        financeSessionBean.setReceipt(new Receipt());
        financeSessionBean.setEditMode(CREATE);
        return "/schooluser/finance/receiptdetails";
    }

    /**
     *
     * @return
     */
    public String createReceipt() {
        setReceipt(financeSessionBean.getReceipt()); //the page used the session version 
        getReceipt().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        //cos I need the school value sometime later
        SchoolTerm _schooTerm = new SchoolTerm(
                schoolSessionBean.getSchoolCalendarData().getSchoolTermId());
        getReceipt().setSchoolTerm(_schooTerm);
        getReceipt().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        try {
            Receipt _receipt = financeService.createReceipt(getReceipt());
            financeSessionBean.setReceipt(_receipt);
            //generate message for create receipt
            messageGenerator.sendReceiptNotice(receipt,
                    userLoginSessionBean.getUserLogin());
            if (_receipt.isFeeType()) {
                return "/schooluser/finance/feereceiptallocation";
            } else {
                return "/schooluser/finance/receiptdetails";
            }
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String reverseReceipt() {
        setReceipt(financeSessionBean.getReceipt());;
        getReceipt().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getReceipt().setSchoolTerm(
                new SchoolTerm(
                schoolSessionBean.getSchoolCalendarData().getSchoolTermId()));
        getReceipt().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        try {
            financeService.reverseReceipt(getReceipt());
            return retToSearch();
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findReceipt() {
        if (getReceipt().getReceiptId() != null) {
            getReceipt().setReceiptBalance(
                    financeService.getReceiptBalance(
                    getReceipt().getReceiptId()));
            financeSessionBean.setReceipt(getReceipt());
            financeSessionBean.setEditMode(VIEW);
            return "/schooluser/finance/receiptdetails";
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findReceipts() {
        ReceiptQueryCriteria criteria = buildQueryCriteria();
        try {
            List<Receipt> receipts = financeService.findReceipts(criteria);
            financeSessionBean.setReceiptModel(new ListDataModel(receipts));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String retToSearch() {
        financeSessionBean.setEditMode(null);
        financeSessionBean.setReceipt(null);
        return "/schooluser/finance/receiptsearch";
    }

    public String findReceiptDetails() {
        try {
            List<Receipt> receipts = financeService.findReceipts(
                    financeSessionBean.getReceipt().getReceiptId());
            //set the data model
            financeSessionBean.setReceiptModel(new ListDataModel<>(receipts));
            BigDecimal total = BigDecimal.ZERO;
            for (Receipt r : receipts) {
                total = total.add(r.getAmount());
            }
            setReceiptTotal(receiptTotal);
            return "/schooluser/finance/receiptstatement";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String prepareForAllocation() {
        boolean usedUpValue =
                financeSessionBean.getReceipt().getReceiptBalance().intValue() >= 0;
        if (usedUpValue) {
            FacesUtil.getFacesContext().addMessage("",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "ERROR_RECEIPT_FULLY_UTILIZED", ""));
            return "";
        }
        financeSessionBean.setEditMode(EDIT);
        if (financeSessionBean.getSchoolStudentDataList() == null) {
            financeSessionBean.setSchoolStudentDataList(getSchoolStudents());
        }
        return "/schooluser/finance/feereceiptallocation";
    }

    /**
     *
     * @param name
     * @return
     */
    private List<SchoolStudentData> getSchoolStudents() {
        SchoolStudentQueryCriteria criteria = new SchoolStudentQueryCriteria();
        criteria.setSchoolYearId(schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
        //criteria.setSurname(name, WCString.LIKE);

        Vector<OrderClause> vec = new Vector<>();
        vec.add(new OrderClause(SchoolStudentQueryCriteria.surname));
        criteria.setOrderByColumn(vec);
        return schoolStudentService.findSchoolStudents(criteria);
    }

    private ReceiptQueryCriteria buildQueryCriteria() {
        ReceiptQueryCriteria criteria = new ReceiptQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        criteria.setTranSource(CommonConstants.TRAN_SOURCE_ORIGINAL);
        //criteria.setReversal(); //we don't want reversals
        if (currentTerm) {
            criteria.setSchoolTermId(schoolSessionBean.getSchoolCalendarData().getSchoolTermId());
        } else if (currentYear) {
            criteria.setSchoolYearId(schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
        }
        if (feeType) {
            criteria.setFeeType();
        }
        if (this.txnTypeId != null && this.txnTypeId != 0) {
            criteria.setTxnTypeId(this.txnTypeId);
        }
        if (this.receiptNumber != null && !this.receiptNumber.trim().isEmpty()) {
            criteria.setReceiptNumber(this.receiptNumber, WCString.LIKE);
        }
        if (this.fromDate != null && this.toDate != null) {
            criteria.setTxnDate(this.fromDate, WCDate.BETWEEN, this.toDate);
        }
        List<OrderClause> vec = new ArrayList<>();
        vec.add(new OrderClause(ReceiptQueryCriteria.modifiedTime, OrderType.DESC));
        criteria.setOrderByColumn(vec);

        return criteria;
    }
//    public class ReceiptGenerator extends SchoolDocGenerator {
//
//        private Receipt _receipt;
//        private School _school;
//
//        public ReceiptGenerator(Receipt receipt, School school) throws IOException {
//            super("receiptTemplate",
//                    DocumentTemplateLocator.receiptTemplate);
//            this._receipt = receipt;
//            this._school = school;
//        }
//
//        @Override
//        public Map<String, Object> getDocumentParameter() {
//
//            Map<String, Object> context = new HashMap<>();
//
//            String url = schoolSessionBean.getSchoolPref().getLogoUrl();
//            //set the default school fields
//            setSchoolDefaultFields(context, _school, url);
//            //set other context
//            context.put("receiptNumber", _receipt.getReceiptNumber());
//            context.put("userName", _receipt.getModifiedBy());
//            context.put("amount",
//                    MoneyUtil.getMoneyDigitsInStandardForm(_receipt.getAmount().abs().doubleValue()));
//            context.put("paymentDate", DateUtil.getInstance().getDateAsString(
//                    _receipt.getEffectiveDate()));
//            context.put("amountInWords",
//                    MoneyUtil.convertToWords(_receipt.getAmount().abs()));
//            context.put("payer", _receipt.getPayer());
//            context.put("narration", _receipt.getNarration());
//            context.put("paymentMode", _receipt.getPaymentMode());
//            context.put("paymentDate", DateUtil.getInstance().getDateAsString(
//                    _receipt.getEffectiveDate()));
//            context.put("chequeNumber", _receipt.getPaymentNumber() == null ? " "
//                    : _receipt.getPaymentNumber());
//
//            return context;
//        }
//    }
}
