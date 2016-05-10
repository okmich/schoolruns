/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.parent.bean;

import com.okmich.common.exception.TechnicalException;
import com.okmich.schoolruns.common.entity.TermExamRecord;
import com.okmich.schoolruns.common.entity.TermExamRecordDetails;
import com.okmich.schoolruns.common.service.JdbcService;
import com.okmich.schoolruns.school.service.SchoolAcademicService;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ResultSetDataModel;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class StudentTermExamBean extends _BaseBean implements TagConstant {

    @ManagedProperty("#{jdbcService}")
    private JdbcService jdbcService;
    @ManagedProperty("#{parentSessionBean}")
    private ParentSessionBean parentSessionBean;
    @ManagedProperty("#{schoolAcademicService}")
    private SchoolAcademicService schoolAcademicService;
    @ManagedProperty("#{studentBean}")
    private StudentBean studentBean;
    private TermExamRecord termExamRecord;
    private DataModel<TermExamRecordDetails> termExamRecordDetailModel;
    /**
     * TERM_EXAMS_BY_STUDENT
     */
    private final static String TERM_EXAMS_BY_STUDENT = "SELECT school_name, academic_year, "
            + "term, exam_type, total_term_score, total_credit_weight, cgpa, class_position, "
            + "registration_no, term_exam_record_id, school_id  FROM v_student_term_exam_record v "
            + "where v.student_id = ${STUDENT_ID} "
            + "order by academic_year desc, term desc";

    /**
     * Creates a new instance of StudentTermExamBean
     */
    public StudentTermExamBean() {
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
     * @param schoolAcademicService the schoolAcademicService to set
     */
    public void setSchoolAcademicService(SchoolAcademicService schoolAcademicService) {
        this.schoolAcademicService = schoolAcademicService;
    }

    /**
     * @param studentBean the studentBean to set
     */
    public void setStudentBean(StudentBean studentBean) {
        this.studentBean = studentBean;
    }

    /**
     * @return the termExamRecordDetailModel
     */
    public DataModel<TermExamRecordDetails> getTermExamRecordDetailModel() {
        return termExamRecordDetailModel;
    }

    /**
     * @param termExamRecordDetailModel the termExamRecordDetailModel to set
     */
    public void setTermExamRecordDetailModel(DataModel<TermExamRecordDetails> termExamRecordDetailModel) {
        this.termExamRecordDetailModel = termExamRecordDetailModel;
    }

    /**
     *
     * @return
     */
    public String findStudentTermExamHistory() {
        //get the query for term exams for this student
        String query = TERM_EXAMS_BY_STUDENT.replace(STUDENT_TAG,
                "" + studentBean.getStudentData().getStudentId());
        CachedRowSet rowSet;
        try {
            rowSet = jdbcService.executeQuery(query);
            studentBean.setStudentTermExamModel(new ResultSetDataModel(rowSet));

            return "/parent/student/exams/index";
        } catch (TechnicalException ex) {
            Logger.getLogger(StudentFinanceBean.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findStudentTermExamDetails() {
        String _termExmRcdId = FacesUtil.getRequestParameter("termExamRecordId");
        try {
            this.termExamRecord = schoolAcademicService.findTermExamRecord(
                    Integer.parseInt(_termExmRcdId));

            return "/parent/student/exams/termexamdetails";
        } catch (Exception ex) {
            Logger.getLogger(StudentFinanceBean.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String printTermExamReport() {

        return "";
    }

    /**
     * @return the termExamRecord
     */
    public TermExamRecord getTermExamRecord() {
        return termExamRecord;
    }

    /**
     * @param termExamRecord the termExamRecord to set
     */
    public void setTermExamRecord(TermExamRecord termExamRecord) {
        this.termExamRecord = termExamRecord;
    }
}
