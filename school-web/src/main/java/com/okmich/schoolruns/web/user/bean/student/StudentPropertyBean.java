/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.student;

import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.common.entity.StudentAward;
import com.okmich.schoolruns.common.entity.StudentClub;
import com.okmich.schoolruns.common.entity.StudentDiscipline;
import com.okmich.schoolruns.common.entity.StudentSport;
import com.okmich.schoolruns.school.service.SchoolStudentService;
import com.okmich.schoolruns.student.service.StudentService;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.common.notification.MessageGenerator;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
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
public class StudentPropertyBean extends _BaseBean {

    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{schoolStudentService}")
    private SchoolStudentService schoolStudentService;
    @ManagedProperty("#{studentService}")
    private StudentService studentService;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{messageGenerator}")
    private MessageGenerator messageGenerator;
    private DataModel<StudentDiscipline> studentDisciplineModel;
    private DataModel<StudentSport> studentSportModel;
    private DataModel<StudentClub> studentClubModel;
    private DataModel<StudentAward> studentAwardModel;
    private StudentDiscipline studentDiscipline;
    private StudentSport studentSport;
    private StudentClub studentClub;
    private StudentAward studentAward;

    /**
     * Creates a new instance of StudentPropertyBean
     */
    public StudentPropertyBean() {
    }

    /**
     * @param messageGenerator the messageGenerator to set
     */
    public void setMessageGenerator(MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * @param schoolStudentService1 the schoolStudentService1 to set
     */
    public void setSchoolStudentService(SchoolStudentService schoolStudentService1) {
        this.schoolStudentService = schoolStudentService1;
    }

    /**
     * @param studentService1 the studentService to set
     */
    public void setStudentService(StudentService studentService1) {
        this.studentService = studentService1;
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
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean1) {
        this.userLoginSessionBean = userLoginSessionBean1;
    }

    public String saveStudentClub() {
        if (getStudentClub().getDateJoined().after(new Date())) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "FUTURE DATE", ""));
            return "";
        }
        getStudentClub().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getStudentClub().setSchoolYear(new SchoolYear(
                schoolSessionBean.getSchoolCalendarData().getSchoolYearId()));
        getStudentClub().setSchoolStudent(sessionBean.getSchoolStudentData().getSchoolStudent());
        getStudentClub().setStatus(CommonConstants.STATUS_ACTIVE);
        try {
            StudentClub _studentClub = schoolStudentService.saveStudentClub(getStudentClub());
            ((List<StudentClub>) getStudentClubModel().getWrappedData()).
                    add(_studentClub);
            return "/schooluser/student/curricular";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String saveStudentDiscipline() {
        if (getStudentDiscipline().getDateCommitted().after(new Date())) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "FUTURE DATE", ""));
            return "";
        }
        getStudentDiscipline().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getStudentDiscipline().setSchoolYear(new SchoolYear(
                schoolSessionBean.getSchoolCalendarData().getSchoolYearId()));
        getStudentDiscipline().setSchoolStudent(sessionBean.getSchoolStudentData().getSchoolStudent());
        getStudentDiscipline().setStatus(CommonConstants.STATUS_ACTIVE);
        try {
            StudentDiscipline _studentDisc = schoolStudentService.saveStudentDiscipline(
                    getStudentDiscipline());
            ((List<StudentDiscipline>) getStudentDisciplineModel().getWrappedData()).
                    add(_studentDisc);
            //send parent mesage
            messageGenerator.sendParentDisciplinaryMessage(_studentDisc,
                    userLoginSessionBean.getUserLogin());
            //return to user
            return "/schooluser/student/discipline";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String saveStudentSport() {
        getStudentSport().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getStudentSport().setSchoolYear(new SchoolYear(
                schoolSessionBean.getSchoolCalendarData().getSchoolYearId()));
        getStudentSport().setSchoolStudent(sessionBean.getSchoolStudentData().getSchoolStudent());
        getStudentSport().setStatus(CommonConstants.STATUS_ACTIVE);
        try {
            StudentSport _studentSport = schoolStudentService.saveStudentSport(
                    getStudentSport());
            ((List<StudentSport>) getStudentSportModel().getWrappedData()).
                    add(_studentSport);
            return "/schooluser/student/curricular";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String saveStudentAward() {
        if (getStudentAward().getDateObtained().after(new Date())) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "FUTURE DATE", ""));
            return "";
        }
        getStudentAward().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getStudentAward().setStudent(sessionBean.getSchoolStudentData().getStudent());
        getStudentAward().setStatus(CommonConstants.STATUS_ACTIVE);
        try {
            StudentAward _studentAward = studentService.saveStudentAward(
                    getStudentAward());
            ((List<StudentAward>) getStudentAwardModel().getWrappedData()).
                    add(_studentAward);
            return "/schooluser/student/awards";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * @return the studentDisciplineModel
     */
    public DataModel<StudentDiscipline> getStudentDisciplineModel() {
        if (studentDisciplineModel == null) {
            List<StudentDiscipline> studentDisciplines =
                    schoolStudentService.findStudentDisciplines(schoolSessionBean.getSchool().getSchoolId(),
                    sessionBean.getSchoolStudentData().getRegistrationNo());
            studentDisciplineModel = new ListDataModel<>(studentDisciplines);
        }
        return studentDisciplineModel;
    }

    /**
     * @param studentDisciplineModel the studentDisciplineModel to set
     */
    public void setStudentDisciplineModel(DataModel<StudentDiscipline> studentDisciplineModel) {
        this.studentDisciplineModel = studentDisciplineModel;
    }

    /**
     * @return the studentSportModel
     */
    public DataModel<StudentSport> getStudentSportModel() {
        if (studentSportModel == null) {
            List<StudentSport> studentSports =
                    schoolStudentService.findStudentSports(schoolSessionBean.getSchool().getSchoolId(),
                    sessionBean.getSchoolStudentData().getRegistrationNo());
            studentSportModel = new ListDataModel<>(studentSports);
        }
        return studentSportModel;
    }

    /**
     * @param studentSportModel the studentSportModel to set
     */
    public void setStudentSportModel(DataModel<StudentSport> studentSportModel) {
        this.studentSportModel = studentSportModel;
    }

    /**
     * @return the studentClubModel
     */
    public DataModel<StudentClub> getStudentClubModel() {
        if (studentClubModel == null) {
            List<StudentClub> studentClubs =
                    schoolStudentService.findStudentClubs(schoolSessionBean.getSchool().getSchoolId(),
                    sessionBean.getSchoolStudentData().getRegistrationNo());
            studentClubModel = new ListDataModel<>(studentClubs);
        }
        return studentClubModel;
    }

    /**
     * @return the studentAwardModel
     */
    public DataModel<StudentAward> getStudentAwardModel() {
        if (studentAwardModel == null) {
            List<StudentAward> studentAwards =
                    studentService.findStudentAwards(
                    sessionBean.getSchoolStudentData().getStudent().getStudentId());
            studentAwardModel = new ListDataModel<>(studentAwards);
        }
        return studentAwardModel;
    }

    /**
     * @param studentClubModel the studentClubModel to set
     */
    public void setStudentClubModel(DataModel<StudentClub> studentClubModel) {
        this.studentClubModel = studentClubModel;
    }

    /**
     * @return the studentDiscipline
     */
    public StudentDiscipline getStudentDiscipline() {
        if (studentDiscipline == null) {
            studentDiscipline = new StudentDiscipline();
        }
        return studentDiscipline;
    }

    /**
     * @param studentDiscipline the studentDiscipline to set
     */
    public void setStudentDiscipline(StudentDiscipline studentDiscipline) {
        this.studentDiscipline = studentDiscipline;
    }

    /**
     * @return the studentSport
     */
    public StudentSport getStudentSport() {
        if (studentSport == null) {
            studentSport = new StudentSport();
        }
        return studentSport;
    }

    /**
     * @param studentSport the studentSport to set
     */
    public void setStudentSport(StudentSport studentSport) {
        this.studentSport = studentSport;
    }

    /**
     * @return the studentClub
     */
    public StudentClub getStudentClub() {
        if (studentClub == null) {
            studentClub = new StudentClub();
        }
        return studentClub;
    }

    /**
     * @param studentClub the studentClub to set
     */
    public void setStudentClub(StudentClub studentClub) {
        this.studentClub = studentClub;
    }

    /**
     * @return the studentAward
     */
    public StudentAward getStudentAward() {
        if (studentAward == null) {
            studentAward = new StudentAward();
        }
        return studentAward;
    }

    /**
     * @param studentAward the studentAward to set
     */
    public void setStudentAward(StudentAward studentAward) {
        this.studentAward = studentAward;
    }

    public String truncate(String val) {
        if (val == null || val.length() < 40) {
            return val;
        }
        return new StringBuilder(
                val.substring(0, Math.min(40, val.length())))
                .append("...").toString();
    }
}
