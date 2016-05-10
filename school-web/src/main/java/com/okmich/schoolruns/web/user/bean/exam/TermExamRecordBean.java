/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.exam;

import com.okmich.common.entity.criteria.OrderClause;
import com.okmich.common.entity.criteria.OrderType;
import com.okmich.common.entity.criteria.WCString;
import com.okmich.schoolruns.common.entity.TermExamRecord;
import com.okmich.schoolruns.common.entity.TermExamRecordDetails;
import com.okmich.schoolruns.school.service.SchoolAcademicService;
import com.okmich.schoolruns.school.service.repo.TermExamRecordDetailsQueryCriteria;
import com.okmich.schoolruns.school.service.repo.TermExamRecordQueryCriteria;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.common.notification.MessageGenerator;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
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
public class TermExamRecordBean extends _BaseBean {

    @ManagedProperty("#{schoolAcademicService}")
    private SchoolAcademicService schoolAcademicService;
    @ManagedProperty("#{messageGenerator}")
    private MessageGenerator messageGenerator;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private TermExamRecord termExamRecord;
    private TermExamRecordDetails termExamRecordDetails;
    private Integer examBatchId;
    private Integer schoolClassId;
    private Integer gradeLevelId;
    private Integer schoolSubjectId;
    private Integer schoolStudentId;
    private String surname;

    /**
     * Creates a new instance of TermExamRecordBean
     */
    public TermExamRecordBean() {
    }

    /**
     * @param schoolAcademicService the schoolAcademicService to set
     */
    public void setSchoolAcademicService(SchoolAcademicService schoolAcademicService) {
        this.schoolAcademicService = schoolAcademicService;
    }

    /**
     * @param messageGenerator the messageGenerator to set
     */
    public void setMessageGenerator(MessageGenerator messageGenerator1) {
        this.messageGenerator = messageGenerator1;
    }

    /**
     * @param sessionBean1 the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean1) {
        this.sessionBean = sessionBean1;
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
     *
     *
     * @return
     */
    public String findTermExamRecords() {
        TermExamRecordQueryCriteria criteria = buildQueryCriteria();
        try {
            List<TermExamRecord> termExamRecords = schoolAcademicService.findTermExamRecords(criteria);
            sessionBean.setTermExamRecordModel(new ListDataModel(termExamRecords));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     *
     * @return
     */
    public String findTermExamRecord() {
        if (getTermExamRecord() != null) { //ensuring selection
            setTermExamRecord(schoolAcademicService.findTermExamRecord(
                    getTermExamRecord().getTermExamRecordId()));
            return "/schooluser/exams/termexamrecorddetails";
        }
        //nothing was selected
        return null;
    }

    /**
     *
     *
     * @return
     */
    public String findTermExamRecordDetailss() {
        TermExamRecordDetailsQueryCriteria criteria = buildDetailsQueryCriteria();
        try {
            List<TermExamRecordDetails> termExamRecordDetailses =
                    schoolAcademicService.findTermExamRecordDetails(criteria);
            sessionBean.setTermExamRecordDetailsModel(new ListDataModel(termExamRecordDetailses));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     *
     * @return
     */
    public String findTermExamRecordDetails() {
        if (getTermExamRecordDetails() != null) { //ensuring selection
            setTermExamRecordDetails(getTermExamRecordDetails());
            return "/schooluser/exams/termexamrecorddetailsdetails";
        }
        //nothing was selected
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

    /**
     * @return the termExamRecordDetails
     */
    public TermExamRecordDetails getTermExamRecordDetails() {
        return termExamRecordDetails;
    }

    /**
     * @param termExamRecordDetails the termExamRecordDetails to set
     */
    public void setTermExamRecordDetails(TermExamRecordDetails termExamRecordDetails) {
        this.termExamRecordDetails = termExamRecordDetails;
    }

    /**
     * @return the examBatchId
     */
    public Integer getExamBatchId() {
        return examBatchId;
    }

    /**
     * @param examBatchId the examBatchId to set
     */
    public void setExamBatchId(Integer examBatchId) {
        this.examBatchId = examBatchId;
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
     * @return the schoolSubjectId
     */
    public Integer getSchoolSubjectId() {
        return schoolSubjectId;
    }

    /**
     * @param schoolSubjectId the schoolSubjectId to set
     */
    public void setSchoolSubjectId(Integer schoolSubjectId) {
        this.schoolSubjectId = schoolSubjectId;
    }

    /**
     * @return the schoolStudentId
     */
    public Integer getSchoolStudentId() {
        return schoolStudentId;
    }

    /**
     * @param schoolStudentId the schoolStudentId to set
     */
    public void setSchoolStudentId(Integer schoolStudentId) {
        this.schoolStudentId = schoolStudentId;
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
     *
     *
     * @return TermExamRecordQueryCriteria
     */
    public TermExamRecordQueryCriteria buildQueryCriteria() {
        TermExamRecordQueryCriteria criteria = new TermExamRecordQueryCriteria();
        criteria.setSchoolTermId(schoolSessionBean.getSchoolCalendarData().getSchoolTermId());
        if (this.examBatchId != null && examBatchId != 0) {
            criteria.setExamBatchId(this.examBatchId);
        }
        if (this.schoolClassId != null && schoolClassId != 0) {
            criteria.setSchoolClassId(this.schoolClassId);
        }
        if (this.surname != null && this.surname.trim().isEmpty()) {
            criteria.setSurname(this.surname, WCString.LIKE);
        }
        if (this.gradeLevelId != null && gradeLevelId != 0) {
            criteria.setGradeLevelId(this.gradeLevelId);
        }
        Vector<OrderClause> vec = new Vector<>();
        vec.add(new OrderClause(TermExamRecordQueryCriteria.surname));
        criteria.setOrderByColumn(vec);

        return criteria;
    }

    /**
     *
     *
     * @return TermExamRecordQueryCriteria
     */
    public TermExamRecordDetailsQueryCriteria buildDetailsQueryCriteria() {
        TermExamRecordDetailsQueryCriteria criteria =
                new TermExamRecordDetailsQueryCriteria();

        if (this.examBatchId != null && this.examBatchId != 0) {
            criteria.setExamBatchId(this.examBatchId);
        }
        if (this.schoolSubjectId != null && this.schoolSubjectId != 0) {
            criteria.setSchoolSubjectId(this.schoolSubjectId);
        }
        if (this.schoolStudentId != null && this.schoolStudentId != 0) {
            criteria.setSchoolStudentId(this.schoolStudentId);
        }
        if (this.gradeLevelId != null && this.gradeLevelId != 0) {
            criteria.setGradeLevelId(this.gradeLevelId);
        }
        Vector<OrderClause> vec = new Vector<>();
        vec.add(new OrderClause(TermExamRecordDetailsQueryCriteria.finalScore, OrderType.DESC));
        criteria.setOrderByColumn(vec);

        return criteria;
    }
}
