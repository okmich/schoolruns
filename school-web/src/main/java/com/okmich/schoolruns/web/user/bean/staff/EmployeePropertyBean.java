/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.staff;

import com.okmich.schoolruns.common.entity.EmpEduQualification;
import com.okmich.schoolruns.common.entity.EmpProfQualification;
import com.okmich.schoolruns.common.entity.EmployeeAward;
import com.okmich.schoolruns.common.entity.EmployeeClub;
import com.okmich.schoolruns.common.entity.EmployeeQuery;
import com.okmich.schoolruns.common.entity.EmployeeSport;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.employee.service.EmployeeService;
import com.okmich.schoolruns.notification.service.NotificationService;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.data.ParticipantCategoryEnum;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@ViewScoped
public class EmployeePropertyBean extends _BaseBean {

    /**
     * LOG
     */
    private final Logger LOG = Logger.getLogger(EmployeePropertyBean.class.getName());
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{employeeService}")
    private EmployeeService employeeService;
    @ManagedProperty("#{notificationService}")
    private NotificationService notificationService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private EmpEduQualification empEduQualification;
    private EmpProfQualification empProfQualification;
    private EmployeeAward employeeAward;
    private MessageData messageData;
    private EmployeeSport employeeSport;
    private EmployeeClub employeeClub;
    private EmployeeQuery employeeQuery;

    /**
     * Creates a new instance of EmployeePropertyBean
     */
    public EmployeePropertyBean() {
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * @param employeeService the employeeService to set
     */
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * @param notificationService the notificationService to set
     */
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    public String saveEmpEduQualification() {
        if (getEmpEduQualification().getDateObtained().after(new Date())) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "OBTAINING CERTIFICATION IN A FUTURE DATE", ""));
            return "";
        }
        getEmpEduQualification().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getEmpEduQualification().setEmployee(sessionBean.getEmployeeData().getEmployee());
        try {
            EmpEduQualification _emEduQualification = employeeService.
                    saveEmpEduQualification(getEmpEduQualification());
            return "/schooluser/staff/qualifications";
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    public String saveEmpProfQualification() {
        if (getEmpProfQualification().getDateObtained().after(new Date())) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "OBTAINING CERTIFICATION IN A FUTURE DATE", ""));
            return "";
        }
        getEmpProfQualification().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getEmpProfQualification().setEmployee(sessionBean.getEmployeeData().getEmployee());
        try {
            EmpProfQualification _empProfQualification = employeeService.
                    saveEmpProfQualification(getEmpProfQualification());
            return "/schooluser/staff/qualifications";
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    public String saveEmployeeAward() {
        if (getEmployeeAward().getDateObtained().after(new Date())) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "CANNOT AWARD IN A FUTURE DATE", ""));
            return "";
        }
        getEmployeeAward().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getEmployeeAward().setEmployee(sessionBean.getEmployeeData().getEmployee());
        try {
            EmployeeAward _empAward = employeeService.
                    saveEmployeeAward(getEmployeeAward());
            return "/schooluser/staff/awards";
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    public String saveEmployeeQuery() {
        if (getEmployeeQuery().getQueryDate().after(new Date())) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "CANNOT QUERY IN A FUTURE DATE", ""));
            return "";
        }
        getEmployeeQuery().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getEmployeeQuery().setEmployee(sessionBean.getEmployeeData().getEmployee());
        getEmployeeQuery().setSchoolYear(schoolSessionBean.getSchoolCalendarData().getSchoolYear());
        try {
            employeeService.createEmployeeQuery(getEmployeeQuery());
            return "/schooluser/staff/queries";
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    public String saveEmployeeSport() {
        if (getEmployeeSport().getDateJoined().after(new Date())) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "CANNOT JOIN IN FUTURE DATE", ""));
            return "";
        }
        getEmployeeSport().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getEmployeeSport().setEmployee(sessionBean.getEmployeeData().getEmployee());
        getEmployeeSport().setSchoolYear(new SchoolYear(
                schoolSessionBean.getSchoolCalendarData().getSchoolYearId()));
        try {
            employeeService.saveEmployeeSport(getEmployeeSport());
            return "/schooluser/staff/extracurricular";
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    public String saveEmployeeClub() {
        if (getEmployeeClub().getDateJoined().after(new Date())) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "OCANNOT JOIN IN FUTURE DATE", ""));
            return "";
        }
        getEmployeeClub().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getEmployeeClub().setEmployee(sessionBean.getEmployeeData().getEmployee());
        getEmployeeClub().setSchoolYear(new SchoolYear(
                schoolSessionBean.getSchoolCalendarData().getSchoolYearId()));
        try {
            employeeService.saveEmployeeClub(getEmployeeClub());
            return "/schooluser/staff/extracurricular";
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    public String sendMessage() {
        getEmpProfQualification().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            EmpProfQualification _empProfQualification = employeeService.
                    saveEmpProfQualification(getEmpProfQualification());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    public void prepareNewMessage(ActionEvent event) {
        setMessageData(new MessageData());
    }

    /**
     * @return the empEduQualificationModel
     */
    public DataModel<EmpEduQualification> getEmpEduQualificationModel() {
        List<EmpEduQualification> dataList = employeeService.findEmpEduQualifications(
                sessionBean.getEmployeeData().getEmployeeId());
        return new ListDataModel<>(dataList);
    }

    /**
     * @return the empProfQualificationModel
     */
    public DataModel<EmpProfQualification> getEmpProfQualificationModel() {
        List<EmpProfQualification> dataList = employeeService.findEmpProfQualifications(
                sessionBean.getEmployeeData().getEmployeeId());
        return new ListDataModel<>(dataList);
    }

    /**
     * @return the employeeAwardModel
     */
    public DataModel<EmployeeAward> getEmployeeAwardModel() {
        List<EmployeeAward> dataList = employeeService.findEmployeeAwards(
                sessionBean.getEmployeeData().getEmployeeId());
        return new ListDataModel<>(dataList);
    }

    /**
     * @return the employeeSportModel
     */
    public DataModel<EmployeeSport> getEmployeeSportModel() {
        List<EmployeeSport> dataList = employeeService.findEmployeeSports(
                sessionBean.getEmployeeData().getEmployeeId());
        return new ListDataModel<>(dataList);
    }

    /**
     * @return the employeeQueryModel
     */
    public DataModel<EmployeeQuery> getEmployeeQueryModel() {
        List<EmployeeQuery> dataList = employeeService.findEmployeeQueries(
                sessionBean.getEmployeeData().getEmployeeId());
        return new ListDataModel<>(dataList);
    }

    /**
     * @return the messageDataModel
     */
    public DataModel<MessageData> getMessageDataModel() {
        List<MessageData> dataList = notificationService.findMessages(
                sessionBean.getEmployeeData().getEmployeeId(),
                ParticipantCategoryEnum.EMPLOYEES, false);
        return new ListDataModel<>(dataList);
    }

    /**
     * @return the clubModel
     */
    public DataModel<EmployeeClub> getEmployeeClubModel() {
        List<EmployeeClub> dataList = employeeService.findEmployeeClubs(
                sessionBean.getEmployeeData().getEmployeeId());
        return new ListDataModel<>(dataList);
    }

    /**
     * @return the empEduQualification
     */
    public EmpEduQualification getEmpEduQualification() {
        if (empEduQualification == null) {
            this.empEduQualification = new EmpEduQualification();
        }
        return empEduQualification;
    }

    /**
     * @param empEduQualification the empEduQualification to set
     */
    public void setEmpEduQualification(EmpEduQualification empEduQualification) {
        this.empEduQualification = empEduQualification;
    }

    /**
     * @return the empProfQualification
     */
    public EmpProfQualification getEmpProfQualification() {
        if (empProfQualification == null) {
            this.empProfQualification = new EmpProfQualification();
        }
        return empProfQualification;
    }

    /**
     * @param empProfQualification the empProfQualification to set
     */
    public void setEmpProfQualification(EmpProfQualification empProfQualification) {
        this.empProfQualification = empProfQualification;
    }

    /**
     * @return the messageData
     */
    public MessageData getMessageData() {
        if (messageData == null) {
            messageData = new MessageData();
        }
        return messageData;
    }

    /**
     * @param messageData the messageData to set
     */
    public void setMessageData(MessageData messageData) {
        this.messageData = messageData;
    }

    /**
     * @return the employeeAward
     */
    public EmployeeAward getEmployeeAward() {
        if (employeeAward == null) {
            this.employeeAward = new EmployeeAward();
        }
        return employeeAward;
    }

    /**
     * @param employeeAward the employeeAward to set
     */
    public void setEmployeeAward(EmployeeAward employeeAward) {
        this.employeeAward = employeeAward;
    }

    /**
     * @return the employeeSport
     */
    public EmployeeSport getEmployeeSport() {
        if (employeeSport == null) {
            this.employeeSport = new EmployeeSport();
        }
        return employeeSport;
    }

    /**
     * @param employeeSport the employeeSport to set
     */
    public void setEmployeeSport(EmployeeSport employeeSport) {
        this.employeeSport = employeeSport;
    }

    /**
     * @return the employeeClub
     */
    public EmployeeClub getEmployeeClub() {
        if (employeeClub == null) {
            this.employeeClub = new EmployeeClub();
        }
        return employeeClub;
    }

    /**
     * @param employeeClub the employeeClub to set
     */
    public void setEmployeeClub(EmployeeClub employeeClub) {
        this.employeeClub = employeeClub;
    }

    /**
     * @return the employeeQuery
     */
    public EmployeeQuery getEmployeeQuery() {
        if (employeeQuery == null) {
            this.employeeQuery = new EmployeeQuery();
        }
        return employeeQuery;
    }

    /**
     * @param employeeQuery the employeeQuery to set
     */
    public void setEmployeeQuery(EmployeeQuery employeeQuery) {
        this.employeeQuery = employeeQuery;
    }
}