/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.exam;

import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Exam;
import com.okmich.schoolruns.common.entity.ExamBatch;
import com.okmich.schoolruns.common.entity.ExamBatchClass;
import com.okmich.schoolruns.common.entity.ExamScore;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.common.entity.TermExamRecord;
import com.okmich.schoolruns.school.service.SchoolAcademicService;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.school.service.data.ExamScoreTable;
import com.okmich.schoolruns.school.service.repo.ExamQueryCriteria;
import com.okmich.schoolruns.web.common.ClassExamScoresExporter;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.ExcelFileImportBean;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIInput;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class ExamBean extends _BaseBean {

    @ManagedProperty("#{schoolAcademicService}")
    private SchoolAcademicService schoolAcademicService;
    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{excelFileImportBean}")
    private ExcelFileImportBean excelFileImportBean;
    private Exam exam;
    private ExamBatch examBatch;
    private ExamBatchClass examBatchClass;
    private Integer schoolClassId;
    private Integer schoolSubjectId;
    private Integer examBatchId;
    private List<SchoolClass> schoolClasses;

    /**
     * Creates a new instance of ExamBean
     */
    public ExamBean() {
    }

    /**
     * @param excelFileImportBean1 the excelFileImportBean to set
     */
    public void setExcelFileImportBean(ExcelFileImportBean excelFileImportBean1) {
        this.excelFileImportBean = excelFileImportBean1;
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
    public String findExams() {
        ExamQueryCriteria criteria = buildQueryCriteria();
        try {
            List<Exam> exams = schoolAcademicService.findExams(criteria);
            sessionBean.setExamModel(new ListDataModel(exams));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findExam() {
        if (getExam().getExamId() != null) { //ensuring selection
            sessionBean.setExam(getExam());
            String editMode = FacesUtil.getRequestParameter("editMode");
            //set edit mode
            sessionBean.setEditMode(editMode);
            return "/schooluser/exams/examdetails";
        }
        //nothing was selected
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareExamScoreSheet() {
        setExam(sessionBean.getExam());
        if (getExam().getStatus() != null && !getExam().getStatus().equals(CommonConstants.STATUS_INACTIVE)) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Score Sheet already exists"));
            return "";
        }
        getExam().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            schoolAcademicService.prepareExamScoreSheet(getExam());
            sessionBean.getExam().setStatus(CommonConstants.STATUS_LOCKED);
            //remove the items from the data  model
            List<Exam> exams = (List<Exam>) sessionBean.getExamModel().getWrappedData();
            exams.remove(getExam());
            sessionBean.getExamModel().setWrappedData(exams);
            //notify the client
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Score Sheet ready for scores entry", ""));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareClassExamScoreSheet() {
        getExamBatchClass().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            schoolAcademicService.prepareClassExamScoreSheet(getExamBatch(), getSchoolClassId());
            getExamBatchClass().setStatus(CommonConstants.STATUS_ACTIVE);
            //notify the client
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Score Sheet ready for scores entry for the entire class", ""));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findExamScores() {
        try {
            List<ExamScore> examScores = schoolAcademicService.findExamScore(
                    sessionBean.getExam().getExamId());
            sessionBean.setExamScoreModel(new ListDataModel(examScores));
            return "/schooluser/exams/examscores";
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String saveExamScores() {
        setExam(sessionBean.getExam());
        List<ExamScore> examScores = (List<ExamScore>) sessionBean.getExamScoreModel().getWrappedData();
        getExam().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            schoolAcademicService.saveExamScores(getExam(), examScores);
            return "/schooluser/exam/examsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String saveClassExamScores() {
        ExamBatchClass _examBatchClass = sessionBean.getExamBatchClass();
        try {
            _examBatchClass.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            //save against the database
            schoolAcademicService.saveExamScores(_examBatchClass, sessionBean.getExamScoreTable());
            //notify the client
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Class Score Sheet saved", ""));
            //remove the session property
            sessionBean.setExamScoreTable(null);
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String saveExam() {
        setExam(sessionBean.getExam());
        getExam().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            schoolAcademicService.saveExam(getExam());
            return "/schooluser/exam/examsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String commitExamScores() {
        List<ExamScore> examScores = (List<ExamScore>) sessionBean.getExamScoreModel().getWrappedData();
        getExam().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            schoolAcademicService.commitExamScores(getExam(), examScores);
            examScores.clear(); //emptying the model
            return "/schooluser/exam/examsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String downloadClassScoreSheet() {
        try {
            //build the data structure
            ExamScoreTable examScoreTable = schoolAcademicService.findClassExamsScores(this.examBatchId, this.schoolClassId);
            ClassExamScoresExporter exporter = ClassExamScoresExporter.getInstance("class exam results", examScoreTable);
            //run the export
            exporter.runExport();
        } catch (Exception e) {
            processException(e);
        }

        return "";
    }

    /**
     *
     * @return
     */
    public String uploadClassScoreSheet() {
        ExamBatchClass _examBatchClass = sessionBean.getExamBatchClass();
        try {
            _examBatchClass.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            //set the dependent variable
            excelFileImportBean.setSchool(schoolSessionBean.getSchool());
            //reshape the uploaded data
            ExamScoreTable examScoreTable
                    = excelFileImportBean.reshapeToExamScoreTable();
            //save exams score from the upload
            schoolAcademicService.saveExamScores(
                    sessionBean.getExamBatchClass(), examScoreTable);
            //notify the client
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Class Score Sheet saved", ""));
            return "/schooluser/exams/classexamsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String commitClassExamScores() {
        ExamBatchClass _examBatchClass = sessionBean.getExamBatchClass();
        try {
            _examBatchClass.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            //save against the database
            schoolAcademicService.commitExamScores(_examBatchClass, sessionBean.getExamScoreTable());
            //notify the client
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Class Score Sheet Committed", ""));
            sessionBean.setExamScoreTable(null);
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String prepareForClassScoreEntry() {
        String outcome = "";
        this.examBatchClass = new ExamBatchClass();
        try {
            // get the examBatch class in session
            sessionBean.setExamBatchClass(
                    schoolAcademicService.findExamBatchClass(this.examBatchId, this.schoolClassId));
            //also get the exam score table in session
            sessionBean.setExamScoreTable(schoolAcademicService.findClassExamsScores(
                    getExamBatchId(), getSchoolClassId()));
            //keep the schoolClass value
            setSchoolClassId(getSchoolClassId());
            outcome = "/schooluser/exams/classscoresheet";
        } catch (Exception e) {
            processException(e);
        }
        return outcome;
    }

    /**
     *
     * @return
     */
    public String retToSearch() {
        sessionBean.setEditMode(null);
        sessionBean.setExam(null);
        return "/schooluser/exams/examsearch";
    }

    /**
     *
     * @return
     */
    public String retToClassExamSearch() {
        sessionBean.setEditMode(null);
        sessionBean.setExamBatchClass(null);
        sessionBean.setExamScoreTable(null);
        return "/schooluser/exams/classexamsearch";
    }

    /**
     * this event is fired when the exam batch is selected on the
     * classexamsearchpage.
     *
     * It generates list items for the class menu
     *
     * @param event
     */
    public void examBatchSelectEvent(ValueChangeEvent event) {
        Object object = ((UIInput) event.getComponent()).getValue();
        if (object == null) {
            setSchoolClasses(new ArrayList<SchoolClass>());
            return;
        }
        this.examBatchId = (Integer) object;
        setExamBatch(schoolAcademicService.findExamBatch(this.examBatchId));
        setSchoolClasses(schoolAcademicService.findExamBatchClasses(this.examBatchId));
    }

    /**
     * this event is fired when the schoo class is selected on the
     * classexamsearchpage.
     *
     * THis method prepares the class for either scoresheet preparation,
     * download, upload or committal
     *
     * @param event
     */
    public void schoolClassSelectEvent(ValueChangeEvent event) {
        Object object = ((UIInput) event.getComponent()).getValue();
        if (object == null) {
            return;
        }
        setSchoolClassId(Integer.valueOf(object.toString())); //get the school class
        setExamBatchClass(schoolAcademicService.findExamBatchClass(getExamBatchId(), getSchoolClassId()));
        if (getExamBatchClass() == null) {
            //report error to the client
            setExamBatchClass(new ExamBatchClass());
        }
    }

    public String publishExamClassScores() {
        getExamBatchClass().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            List<TermExamRecord> termExamRecords = schoolAcademicService.publishExamScores(getExamBatchClass());
            sessionBean.setTermExamRecordModel(new ListDataModel(termExamRecords));
            //remove session references
            sessionBean.setExamScoreTable(null);
            //we will not send mail here. Instead mails will be sent when the 
            //general publishing is done from the exam batch level

            return "/schooluser/exam/termexamrecordsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * @return the exam
     */
    public Exam getExam() {
        if (exam == null) {
            this.exam = new Exam();
        }
        return exam;
    }

    /**
     * @param exam the exam to set
     */
    public void setExam(Exam exam) {
        this.exam = exam;
    }

    /**
     * @return the examBatch
     */
    public ExamBatch getExamBatch() {
        return examBatch;
    }

    /**
     * @param examBatch the examBatch to set
     */
    public void setExamBatch(ExamBatch examBatch) {
        this.examBatch = examBatch;
    }

    /**
     * @return the examBatchClass
     */
    public ExamBatchClass getExamBatchClass() {
        return examBatchClass;
    }

    /**
     * @param examBatchClass the examBatchClass to set
     */
    public void setExamBatchClass(ExamBatchClass examBatchClass) {
        this.examBatchClass = examBatchClass;
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

    public List<SelectItem> getExamStatus() {
        List<SelectItem> selectItems = new ArrayList<>(3);

        selectItems.add(new SelectItem(CommonConstants.STATUS_ACTIVE, "Active"));
        selectItems.add(new SelectItem(CommonConstants.STATUS_INACTIVE, "Inactive"));
        selectItems.add(new SelectItem(CommonConstants.STATUS_LOCKED, "Locked"));
        selectItems.add(new SelectItem(CommonConstants.STATUS_PUBLISHED, "Published"));

        return selectItems;
    }

    /**
     *
     *
     * @return
     */
    private ExamQueryCriteria buildQueryCriteria() {
        ExamQueryCriteria crit = new ExamQueryCriteria();
        crit.setSchoolTermId(schoolSessionBean.getSchoolCalendarData().getSchoolTermId());

        if (examBatchId != null && examBatchId != 0) {
            crit.setExamBatchId(examBatchId);
        }
        if (schoolClassId != null && schoolClassId != 0) {
            crit.setSchoolClassId(schoolClassId);
        }
        if (schoolSubjectId != null && schoolSubjectId != 0) {
            crit.setSchoolSubjectId(schoolSubjectId);
        }
        if (getStatus() != null && !getStatus().isEmpty()) {
            crit.setStatus(getStatus());
        }
        return crit;
    }

    public Short getStudentSubjectScore(SchoolStudent schoolStudent, SchoolSubject schoolSubject) {
        if (sessionBean.getExamScoreTable() != null) {
            ExamScore examScore = sessionBean.getExamScoreTable().getExamScore(
                    schoolStudent, schoolSubject);
            if (examScore == null) {
                return examScore.getScore();
            }
        }
        return null;
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
