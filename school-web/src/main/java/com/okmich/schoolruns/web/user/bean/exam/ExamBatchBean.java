/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.exam;

import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.ExamBatch;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.school.service.SchoolAcademicService;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.school.service.repo.ExamBatchQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolStudentQueryCriteria;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.common.notification.MessageGenerator;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.List;
import java.util.logging.Logger;
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
public class ExamBatchBean extends _BaseBean {

    private static final Logger LOG = Logger.getLogger(ExamBatchBean.class.getName());
    @ManagedProperty("#{schoolAcademicService}")
    private SchoolAcademicService schoolAcademicService;
    @ManagedProperty("#{messageGenerator}")
    private MessageGenerator messageGenerator;
    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private ExamBatch examBatch;
    private List<SchoolClass> schoolClasses;
    private List<ExamBatch> examBatchs;

    /**
     * Creates a new instance of ExamBatchBean
     */
    public ExamBatchBean() {
    }

    /**
     * @param messageGenerator the messageGenerator to set
     */
    public void setMessageGenerator(MessageGenerator messageGenerator1) {
        this.messageGenerator = messageGenerator1;
    }

    /**
     * @param schoolAcademicService the schoolAcademicService to set
     */
    public void setSchoolAcademicService(SchoolAcademicService schoolAcademicService) {
        this.schoolAcademicService = schoolAcademicService;
    }

    /**
     * @param schoolService the schoolService to set
     */
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
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
     * @return
     */
    public String createExamBatch() {
        getExamBatch().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getExamBatch().getSchoolTerm().setSchoolTermId(
                schoolSessionBean.getSchoolCalendarData().getSchoolTermId());
        try {
            ExamBatch _examBatch = schoolAcademicService.createExamBatch(getExamBatch());
            Integer _schoolSectionId = getExamBatch().getSchoolSection().getSchoolSectionId();
            if (_schoolSectionId != null && _schoolSectionId != 0) {
                schoolSessionBean.setSchoolSectionId(_schoolSectionId);
            }
            if (!_examBatch.getExamType().getExamTypeCode().equals(CommonConstants.EXAM_TYPE_ADMISSION_EXAM)) {
                sessionBean.setExamBatch(_examBatch);
                sessionBean.setEditMode(CREATE);
                return "/schooluser/exams/exambatchclasses?faces-redirect=true";
            } else {
                return retToSearch();
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
    public String findExamBatches() {
        try {
            sessionBean.setExamBatchModel(new ListDataModel(getExamBatches()));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findExamBatch() {
        if (getExamBatch().getExamBatchId() != null) {
            sessionBean.setExamBatch(getExamBatch());
            sessionBean.setEditMode(FacesUtil.getRequestParameter("editMode"));
            return "/schooluser/exams/exambatchdetails";
        }
        return null;
    }

    /**
     *
     *
     * @return
     */
    public String editExamBatch() {
        getExamBatch().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            schoolAcademicService.saveExamBatch(getExamBatch());
            return retToSearch();
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String publishExamSessionScores() {
        setExamBatch(sessionBean.getExamBatch());
        getExamBatch().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            sessionBean.setExamBatch(schoolAcademicService.beginExamBatch(sessionBean.getExamBatch()));
            ((List<ExamBatch>) sessionBean.getExamBatchModel().getWrappedData()).clear();
            //send a message to parent on the exam session kick off
            if (getExamBatch().isNotifyParentResult()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        messageGenerator
                                .sendExamResultNotice(getExamBatch());
                    }
                }).start();
            }
            //explain what happenedss
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Exam Sessions marked to begin.\nExams have been created and registered",
                    ""));
            return "";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     *
     * @return String
     */
    public String saveExamBatchClasses() {
        setExamBatch(sessionBean.getExamBatch());
        getExamBatch().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            schoolAcademicService.saveExamBatchClasses(sessionBean.getExamBatch(), getSchoolClasses());
            //send any exambegin notice 
            if (getExamBatch().isNotifyParentBegin()) {
            }
            if (sessionBean.getEditMode().equals(CREATE)) {
                sessionBean.setExamBatch(null);
                return "/schooluser/exams/exambatchsearch";
            } else {
                return "/schooluser/exams/exambatchdetails";
            }
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     *
     * @return String
     */
    public String beginExamBatch() {
        setExamBatch(sessionBean.getExamBatch());
        getExamBatch().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            sessionBean.setExamBatch(schoolAcademicService.beginExamBatch(sessionBean.getExamBatch()));
            ((List<ExamBatch>) sessionBean.getExamBatchModel().getWrappedData()).clear();
            //send a message to parent on the exam session kick off
            if (getExamBatch().isNotifyParentBegin()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        messageGenerator.sendExamKickoffNotice(
                                sessionBean.getExamBatch(),
                                schoolSessionBean.getSchoolCalendarData());
                    }
                }).start();
            }
            //explain what happenedss
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Exam Sessions marked to begin.\nExams have been created and registered",
                    ""));
            return "/schooluser/exams/exambatchsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findClassesForExam() {
        this.schoolClasses = schoolAcademicService.findExamBatchClasses(
                sessionBean.getExamBatch().getExamBatchId());
        return "/schooluser/exams/exambatchclasses";
    }

    /**
     * return all the exam sessions for the school term. This is also used in
     * for populating list items on pages
     *
     * @return all exam sessions for this school term
     */
    public List<ExamBatch> getExamBatches() {
        ExamBatchQueryCriteria crit = new ExamBatchQueryCriteria();
        crit.setSchoolTermId(schoolSessionBean.getSchoolCalendarData().getSchoolTermId());
        return schoolAcademicService.findExamBatchs(crit);
    }

    public String retToSearch() {
        sessionBean.setEditMode(null);
        sessionBean.setExamBatch(null);
        return "/schooluser/exams/exambatchsearch";
    }

    /**
     * @return the examBatch
     */
    public ExamBatch getExamBatch() {
        if (examBatch == null) {
            this.examBatch = new ExamBatch();
        }
        return examBatch;
    }

    /**
     * @param examBatch the examBatch to set
     */
    public void setExamBatch(ExamBatch examBatch) {
        this.examBatch = examBatch;
    }

    /**
     * @return the schoolClasses
     */
    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    /**
     * @param schoolClasses the schoolClasses to set
     */
    public void setSchoolClasses(List<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }
}
