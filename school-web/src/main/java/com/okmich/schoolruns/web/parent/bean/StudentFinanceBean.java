/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.parent.bean;

import com.okmich.common.exception.TechnicalException;
import com.okmich.schoolruns.common.entity.FeeReceipt;
import com.okmich.schoolruns.common.entity.Receipt;
import com.okmich.schoolruns.common.service.JdbcService;
import com.okmich.schoolruns.finance.service.FinanceService;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.ResultSetDataModel;
import javax.sql.rowset.CachedRowSet;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class StudentFinanceBean extends _BaseBean implements TagConstant {

    @ManagedProperty("#{financeService}")
    private FinanceService financeService;
    @ManagedProperty("#{jdbcService}")
    private JdbcService jdbcService;
    @ManagedProperty("#{parentSessionBean}")
    private ParentSessionBean parentSessionBean;
    @ManagedProperty("#{studentBean}")
    private StudentBean studentBean;
    private FeeReceipt feeReceipt;
    private Receipt receipt;
    private CartesianChartModel chartModel;
    private PieChartModel pieChartModel;
    private DataModel<Receipt> receiptModel;
    private int yearId;
    /**
     * BY_STUDENT_ONLY
     */
    private static final String SUMMARY_FOR_STUDENT = "SELECT v.school_name, v.school_id, v.academic_year, "
            + "v.school_year_id, v.year_id, v.term, v.school_term_id, sum(v.amount) amount "
            + "FROM v_student_fee_payment v where v.student_id = ${STUDENT_ID} group by v.school_name, "
            + "v.school_id, v.academic_year, v.school_year_id, v.term, v.school_term_id";
    /**
     * BY_STUDENT_YEAR
     */
    private static final String SUMMARY_FOR_STUDENT_BY_YEAR = "SELECT v.student, v.student_id, "
            + "v.school_student_id, v.academic_year, v.school_year_id, v.term, v.school_term_id, "
            + "v.school_name, sum(v.amount) amount FROM v_student_fee_payment v "
            + "where v.student_id = ${STUDENT_ID} and v.school_year_id = ${SCHOOL_YEAR_ID} "
            + "group by v.student, v.student_id, v.school_student_id, v.academic_year, "
            + "v.school_year_id, v.term, v.school_term_id, v.school_name "
            + "order by v.school_term_id desc";
    /**
     * RECEIPT_DETAILS
     */
    private static final String RECEIPT_DETAILS = "SELECT v.school_name, v.academic_year, "
            + "v.school_year_id, v.year_id, v.term, v.school_term_id, v.amount, v.receipt_amount, "
            + "v.school_student_id, v.fee_receipt_id, v.receipt_id, "
            + "v.reversal, v.fee_type, v.txn_date, v.effective_date, v.payment_mode, v.payment_number, "
            + "v.narration, v.receipt_number, v.receipt_id, v.payer FROM v_student_fee_payment v "
            + "where v.student_id = ${STUDENT_ID} and school_term_id = ${SCHOOL_TERM_ID} "
            + "order by v.txn_date";

    /**
     * Creates a new instance of StudentAssignmentBean
     */
    public StudentFinanceBean() {
    }

    /**
     * @param financeService the financeService to set
     */
    public void setFinanceService(FinanceService financeService) {
        this.financeService = financeService;
    }

    /**
     * @param jdbcService the jdbcService to set
     */
    public void setJdbcService(JdbcService jdbcService) {
        this.jdbcService = jdbcService;
    }

    /**
     * @param parentSessionBean the parentSessionBean to set
     */
    public void setParentSessionBean(ParentSessionBean parentSessionBean) {
        this.parentSessionBean = parentSessionBean;
    }

    /**
     * @param studentBean the studentBean to set
     */
    public void setStudentBean(StudentBean studentBean) {
        this.studentBean = studentBean;
    }

    /**
     *
     * @return
     */
    public String findStudentPaymentSummary() {
        //get the query for all payment summed by year, term and schoolid
        String query = SUMMARY_FOR_STUDENT.replace(STUDENT_TAG,
                "" + studentBean.getStudentData().getStudentId());
        CachedRowSet rowSet;
        try {
            rowSet = jdbcService.executeQuery(query);
            studentBean.setStudentFinanceModel(new ResultSetDataModel(rowSet));
            //fill the chart model
            createChartModelForPaymentSummary(rowSet);
            return "/parent/student/finance/index";
        } catch (TechnicalException ex) {
            Logger.getLogger(StudentFinanceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

//    /**
//     *
//     * @return
//     */
//    public String findPaymentSummaryByYear() {
//        //get the query for all payment summed by year, term and schoolid
//        String query = SUMMARY_FOR_STUDENT.replace(STUDENT_TAG,
//                "" + studentBean.getStudentData().getStudentId());
//        CachedRowSet rowSet;
//        try {
//            rowSet = jdbcService.executeQuery(query);
//            studentBean.setStudentFinanceModel(new ResultSetDataModel(rowSet));
//            return "/parent/student/finance/index";
//        } catch (TechnicalException ex) {
//            Logger.getLogger(StudentFinanceBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "";
//    }
    /**
     *
     * @return
     */
    public String findYearlyPaymentSummary() {
        String sYearId = FacesUtil.getRequestParameter("schoolYearId");
        //get the query for all payment summed by year, term and schoolid
        String query = SUMMARY_FOR_STUDENT_BY_YEAR.replace(STUDENT_TAG,
                "" + studentBean.getStudentData().getStudentId());
        query = query.replace(SCHOOL_YEAR_TAG, sYearId);
        CachedRowSet rowSet;
        try {
            rowSet = jdbcService.executeQuery(query);
            studentBean.setStudentFinanceModel(new ResultSetDataModel(rowSet));
            //fill the chart model
            this.pieChartModel = createChartModelForYearlyPaymentSummary(rowSet);
            return "/parent/student/finance/feepaymentbyyear";
        } catch (TechnicalException ex) {
            Logger.getLogger(StudentFinanceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findTermPaymentDetails() {
        String schoolTermId = FacesUtil.getRequestParameter("schoolTermId");
        //get the query for all payment summed by year, term and schoolid
        String query = RECEIPT_DETAILS.replace(STUDENT_TAG,
                "" + studentBean.getStudentData().getStudentId());
        query = query.replace(SCHOOL_TERM_TAG, schoolTermId);
        CachedRowSet rowSet;
        try {
            rowSet = jdbcService.executeQuery(query);
            studentBean.setStudentFinanceModel(new ResultSetDataModel(rowSet));
            return "/parent/student/finance/feepaymentbyterm";
        } catch (Exception ex) {
            Logger.getLogger(StudentFinanceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findFeePaymentDetails() {
        String fpId = FacesUtil.getRequestParameter("feeReceiptId");
        Integer _fpId = Integer.parseInt(fpId, 10);

        this.setFeeReceipt(financeService.findFeeReceipt(
                _fpId));
        return "";
    }

    /**
     *
     * @return
     */
    public String findReceiptDetails() {
        String rId = FacesUtil.getRequestParameter("receiptId");
        Integer _rId = Integer.parseInt(rId, 10);
        try {
            this.setReceipt(financeService.findReceipt(
                    _rId));
            this.setReceiptModel(new ListDataModel<>(
                    financeService.findReceipts(_rId)));
            return "/parent/student/finance/receiptdetails";
        } catch (Exception ex) {
            Logger.getLogger(StudentFinanceBean.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * @return the feeReceipt
     */
    public FeeReceipt getFeeReceipt() {
        return feeReceipt;
    }

    /**
     * @param feeReceipt the feeReceipt to set
     */
    public void setFeeReceipt(FeeReceipt feeReceipt) {
        this.feeReceipt = feeReceipt;
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
     * @return the chartModel
     */
    public CartesianChartModel getChartModel() {
        return chartModel;
    }

    /**
     * @return the pieChartModel
     */
    public PieChartModel getPieChartModel() {
        return pieChartModel;
    }

    /**
     *
     * @param rowSet
     * @return
     */
    private void createChartModelForPaymentSummary(CachedRowSet rowSet) {
        this.chartModel = new CartesianChartModel();
        ChartSeries payHistSeries = new ChartSeries();
        payHistSeries.setLabel("Payment History Summary");
        try {
            rowSet.beforeFirst();
            while (rowSet.next()) {
                payHistSeries.set(
                        rowSet.getString("academic_year"),
                        rowSet.getBigDecimal("amount"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentFinanceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        chartModel.addSeries(payHistSeries);
    }

    /**
     *
     * @param rowSet
     * @return
     */
    private PieChartModel createChartModelForYearlyPaymentSummary(CachedRowSet rowSet) {
        PieChartModel pieModel = new PieChartModel();

        try {
            rowSet.beforeFirst();
            while (rowSet.next()) {
                pieModel.set(
                        rowSet.getString("term"),
                        rowSet.getBigDecimal("amount"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentFinanceBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pieModel;
    }
}