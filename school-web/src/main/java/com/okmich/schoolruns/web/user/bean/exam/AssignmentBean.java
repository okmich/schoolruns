/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.exam;

import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Assignment;
import com.okmich.schoolruns.common.entity.AssignmentScore;
import com.okmich.schoolruns.common.entity.SchoolTerm;
import com.okmich.schoolruns.school.service.SchoolAcademicService;
import com.okmich.schoolruns.school.service.SchoolStudentService;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.school.service.repo.AssignmentQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolStudentQueryCriteria;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.common.notification.MessageGenerator;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.List;
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
public class AssignmentBean extends _BaseBean {

    @ManagedProperty("#{schoolAcademicService}")
    private SchoolAcademicService schoolAcademicService;
    @ManagedProperty("#{schoolStudentService}")
    private SchoolStudentService schoolStudentService;
    @ManagedProperty("#{messageGenerator}")
    private MessageGenerator messageGenerator;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private Assignment assignment;
    private Integer schoolClassId;
    private Integer gradeLevelId;
    private Integer schoolSubjectId;

    /**
     * Creates a new instance of AssignmentBean
     */
    public AssignmentBean() {
    }

    /**
     * @param schoolAcademicService the schoolAcademicService to set
     */
    public void setSchoolAcademicService(SchoolAcademicService schoolAcademicService) {
        this.schoolAcademicService = schoolAcademicService;
    }

    /**
     * @param schoolStudentService the schoolStudentService to set
     */
    public void setSchoolStudentService(SchoolStudentService schoolStudentService) {
        this.schoolStudentService = schoolStudentService;
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

    public String createAssignment() {
        sessionBean.setAssignment(getAssignment());
        String outcome = saveAssignment();
        //send a mail to parents if the notify button was selected
        if (!outcome.isEmpty() && getAssignment().isNotifyParentBegin()) {
            //do the mail sending
            //initiatiate a mail to the schools admin
            final List<SchoolStudentData> studentDataList;
            SchoolStudentQueryCriteria criteria = new SchoolStudentQueryCriteria();
            criteria.setSchoolYearId(schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
            if (getAssignment().getSchoolClass().getSchoolClassId() != null
                    || getAssignment().getSchoolClass().getSchoolClassId() != 0) {
                //the assignment is to class only
                criteria.setSchoolClassId(getAssignment().getSchoolClass().getSchoolClassId());
            } else {
                //the assignment is set to all the grade level
                criteria.setGradeLevelId(getAssignment().getGradeLevel().getGradeLevelId());
            }
            studentDataList = schoolStudentService.findSchoolStudents(criteria);
            //now send the mail to all parents of the student
            //send the mail in a separate thread to reduce wait time for users of 
            //application
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            messageGenerator.sendAssignmentCreationMessage(
                                    getAssignment(), studentDataList);
                        }
                    }).start();
        }
        return outcome;
    }

    public String saveAssignment() {
        setAssignment(sessionBean.getAssignment()); //get the assignment in context
        getAssignment().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getAssignment().setSchoolTerm(new SchoolTerm(schoolSessionBean.getSchoolCalendarData().getSchoolTermId()));
        try {
            this.assignment = schoolAcademicService.saveAssignment(getAssignment());
            return "/schooluser/exams/assignmentsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findAssignments() {
        AssignmentQueryCriteria criteria = buildQueryCriteria();
        try {
            List<Assignment> exams = schoolAcademicService.findAssignments(criteria);
            sessionBean.setAssignmentModel(new ListDataModel(exams));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String deleteAssignment() {
        try {
            if (!sessionBean.getAssignment().getStatus().equals(CommonConstants.STATUS_INACTIVE)) {
                FacesUtil.getFacesContext().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot Delete Assignment", ""));
                return "";
            }
            schoolAcademicService.deleteAssignment(sessionBean.getAssignment()); //delete the object from db
            //remove the reference from the session datamodel
            ((List<Assignment>) sessionBean.getAssignmentModel().getWrappedData()).remove(sessionBean.getAssignment());

            //initiatiate a mail to the parents
            //messageGenerator.sendAssignmentCancellationMessage(sessionBean.getAssignment());

            //go to search page
            return retToSearch();
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     *
     * @param assignment
     */
    public String prepareAssignmentScoreSheet() {
        if (sessionBean.getAssignment().getStatus() != null
                && !sessionBean.getAssignment().getStatus().equals(CommonConstants.STATUS_INACTIVE)) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Score Sheet already exists"));
            return null;
        }
        setAssignment(sessionBean.getAssignment());
        getAssignment().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            schoolAcademicService.prepareAssignmentScoreSheet(getAssignment());
            sessionBean.getAssignment().setStatus(CommonConstants.STATUS_LOCKED);
            //empty the assignment model in session
            sessionBean.setAssignmentModel(new ListDataModel<Assignment>());
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
    public String findAssignmentScores() {
        try {
            List<AssignmentScore> assignmentScores = schoolAcademicService.findAssignmentScores(
                    sessionBean.getAssignment().getAssignmentId());
            sessionBean.setAssignmentScoreModel(new ListDataModel(assignmentScores));
            return "/schooluser/exams/assignmentscores";
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String saveAssignmentScores() {
        List<AssignmentScore> assignmentScores = (List<AssignmentScore>) sessionBean
                .getAssignmentScoreModel().getWrappedData();
        setAssignment(sessionBean.getAssignment()); //get the assignment in context
        getAssignment().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername()); //set the current user
        try {
            schoolAcademicService.saveAssignmentScores(getAssignment(), assignmentScores);
            return "/schooluser/exams/assignmentdetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    public String commitAssignmentScores() {
        setAssignment(sessionBean.getAssignment()); //get the assignment in context
        try {
            getAssignment().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            //save against the database
            List<AssignmentScore> assignmentScores = (List<AssignmentScore>) sessionBean
                    .getAssignmentScoreModel().getWrappedData();
            schoolAcademicService.commitAssignmentScores(getAssignment(),
                    assignmentScores);
            //send a mail to parents if the notify button was selected
            if (getAssignment().isNotifyParentResult()) {
                //do the mail sending
                //initiatiate a mail to the schools admin
                messageGenerator.sendAssignmentCommittalMessage(getAssignment());
            }
            //notify the client
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Assignment Scores Committed", ""));
            return "/schooluser/exams/assignmentdetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findAssignment() {
        if (getAssignment().getAssignmentId() != null) { //ensuring selection
            String editMode = FacesUtil.getRequestParameter("editMode");
            //set edit mode
            sessionBean.setEditMode(editMode);
//            //check if we are going to the scores page and ascertain that the status of exam
//            if (url.indexOf("examscores") != -1) {
//                if (status.equals(CommonConstants.STATUS_INACTIVE)) {
//                    //flag an error
//                    FacesUtil.getFacesContext().addMessage(null,
//                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                            "Error:", "Score Sheet not yet ready. Please prepare score sheet"));
//                    return "";
//                } else if (status.equals(CommonConstants.STATUS_ACTIVE)
//                        && editMode.equals("COMMIT")) { //hey you cannot commit this
//                    FacesUtil.getFacesContext().addMessage(null,
//                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                            "Error:", "Score Sheet already committed"));
//                    return "";
//                }
//            }
            setAssignment(getAssignment());
            sessionBean.setAssignment(getAssignment());
            return "/schooluser/exams/assignmentdetails";
        }
        //nothing was selected
        return null;
    }

    /**
     * sends the user back to the assignmentsearch page. clears the edit mode
     * and the assignment session reference
     *
     * @return the search page address
     */
    public String retToSearch() {
        sessionBean.setAssignment(null);
        sessionBean.setEditMode(null);
        return "/schooluser/exams/assignmentsearch";
    }

    /**
     * @return the assignment
     */
    public Assignment getAssignment() {
        if (assignment == null) {
            this.assignment = new Assignment();
        }
        return assignment;
    }

    /**
     * @param assignment the assignment to set
     */
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
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

    private AssignmentQueryCriteria buildQueryCriteria() {
        AssignmentQueryCriteria crit = new AssignmentQueryCriteria();
        crit.setSchoolTermId(schoolSessionBean.getSchoolCalendarData().getSchoolTermId());

        if (gradeLevelId != null && gradeLevelId != 0) {
            crit.setGradeLevelId(gradeLevelId);
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
}
