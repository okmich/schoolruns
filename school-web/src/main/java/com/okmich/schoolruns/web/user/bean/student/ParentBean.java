/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.student;

import com.okmich.common.entity.criteria.OrderClause;
import com.okmich.common.entity.criteria.WCString;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.notification.service.NotificationService;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.data.ParticipantCategoryEnum;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.school.service.SchoolStudentService;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.school.service.repo.SchoolParentQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolStudentQueryCriteria;
import com.okmich.schoolruns.student.service.StudentService;
import com.okmich.schoolruns.student.service.data.StudentData;
import com.okmich.schoolruns.student.service.repo.ParentQueryCriteria;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.ExcelFileImportBean;
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
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class ParentBean extends _BaseBean {

    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{notificationService}")
    private NotificationService notificationService;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{schoolStudentService}")
    private SchoolStudentService schoolStudentService;
    @ManagedProperty("#{studentService}")
    private StudentService studentService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{messageGenerator}")
    private MessageGenerator messageGenerator;
    @ManagedProperty("#{excelFileImportBean}")
    private ExcelFileImportBean excelFileImportBean;
    private Parent parent;
    private MessageData messageData;
    private String surname;
    private String email;
    private String phoneNumber;
    private String stateCode;

    /**
     * Creates a new instance of ParentBean
     */
    public ParentBean() {
        if (this.parent == null) {
            this.parent = new Parent();
        }
    }

    public String createParent() {
        getParent().setStatus(CommonConstants.STATUS_ACTIVE);
        saveParent();
        return "";
    }

    public String createParents() {
        //set the fields
        getParent().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            //reshape the uploaded data
            final List<Parent> parents =
                    excelFileImportBean.reshapeToParent();
            studentService.createParents(parents);
            //send message to parent
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (Parent _parent : parents) {
                            messageGenerator.sendPostParentCreationMessage(_parent,
                                    schoolSessionBean.getSchool());
                        }
                    } catch (Exception e) {
                        processException(e);
                    }
                }
            }).start();
            return "/schooluser/parent/parentsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String saveParent() {
        //set the fields
        getParent().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            studentService.saveParent(getParent());
            //send message to parent
            new Thread(new Runnable() {
                @Override
                public void run() {
                    messageGenerator.sendPostParentCreationMessage(getParent(),
                            schoolSessionBean.getSchool());
                }
            }).start();
            return "/schooluser/parent/parentnew";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String updateParent() {
        setParent(sessionBean.getParent());
        //set the fields
        getParent().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            studentService.saveParent(getParent());
            return "/schooluser/parent/parentsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String findParents() {
        try {
            List<Parent> parents = schoolStudentService.findSchoolParents(buildQueryCriteria());
            sessionBean.setParentModel(new ListDataModel<>(parents));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String findParent() {
        if (getParent().getPhoneNumber() != null) {
            sessionBean.setEditMode(FacesUtil.getRequestParameter("editMode"));
            sessionBean.setParent(getParent());
            return "/schooluser/parent/parentdetails";
        }
        return null;
    }

    public String findStudentParent() {
        Parent _parent = studentService.findStudentParent(
                sessionBean.getSchoolStudentData().getStudentId());
        if (_parent == null) {
            sessionBean.setEditMode("PARENTSELECT");
            return "/schooluser/parent/newparent";
        } else {
            setParent(_parent);
            return "/schooluser/student/parents";
        }
    }

    public String prepareForParentSelect() {
        sessionBean.setEditMode("PARENTSELECT");
        return "/schooluser/parent/parentsearch";
    }

    public String prepareForCreate() {
        sessionBean.setEditMode("CREATE");
        return "/schooluser/parent/newparent";
    }

    public void nullifyEditMode(ActionEvent event) {
        sessionBean.setEditMode(null);
    }

    public String saveAndAssignParent() {
        //set the fields
        getParent().setStatus(CommonConstants.STATUS_ACTIVE);
        getParent().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            studentService.assignNewParent(getParent(), sessionBean.getSchoolStudentData().getStudentData());
            //send message to parent
            new Thread(new Runnable() {
                @Override
                public void run() {
                    messageGenerator.sendPostParentCreationMessage(getParent(),
                            schoolSessionBean.getSchool());
                    messageGenerator.sendParentAssignMessage(
                            sessionBean.getSchoolStudentData().getStudentData(), getParent(),
                            userLoginSessionBean.getUserLogin());
                }
            }).start();
            sessionBean.setEditMode("");
            return "/schooluser/student/studentdetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String assignParent() {
        if (getParent() == null || getParent().getPhoneNumber() == null) {
            return "";
        }
        StudentData studentData = sessionBean.getSchoolStudentData().getStudentData();
        //set the fields
        studentData.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            studentService.assignParentToStudent(studentData, getParent());
            //send message to parent
            messageGenerator.sendParentAssignMessage(studentData, getParent(),
                    userLoginSessionBean.getUserLogin());
            //set the student record
            sessionBean.getSchoolStudentData().setParentId(getParent().getPhoneNumber());
            return "/schooluser/student/studentdetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String deassignParent() {
        StudentData studentData = sessionBean.getSchoolStudentData().getStudentData();
        //set the fields
        studentData.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            studentService.deassignParentFromStudent(studentData, getParent());
            //send message to parent
            messageGenerator.sendParentDeassignMessage(studentData, getParent(),
                    userLoginSessionBean.getUserLogin());
            return "/schooluser/student/studentdetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String doDoneParent() {
        sessionBean.setParent(null);
        sessionBean.setEditMode(null);
        return "/schooluser/parent/parentsearch";
    }

    public String doDoneNewParent() {
        sessionBean.setParent(null);
        sessionBean.setEditMode(null);
        return "/schooluser/student/studentdetails";
    }

    /**
     * @return the messageDataModel
     */
    public DataModel<MessageData> getMessageDataModel() {
        List<MessageData> dataList = notificationService.findMessages(
                sessionBean.getParent().getPhoneNumber(),
                ParticipantCategoryEnum.PARENTS, false);
        return new ListDataModel<>(dataList);
    }

    /**
     * @return the schoolStudentModel
     */
    public DataModel<SchoolStudentData> getSchoolStudentModel() {
        SchoolStudentQueryCriteria criteria = new SchoolStudentQueryCriteria();
        criteria.setSchoolYearId(schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
        criteria.setParentNumber(sessionBean.getParent().getPhoneNumber());
        List<SchoolStudentData> dataList = schoolStudentService.findSchoolStudents(
                criteria);
        return new ListDataModel<>(dataList);
    }

    /**
     * @param messageGenerator the messageGenerator1 to set
     */
    public void setMessageGenerator(MessageGenerator messageGenerator1) {
        this.messageGenerator = messageGenerator1;
    }

    /**
     * @param schoolService the schoolService to set
     */
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @param schoolStudentService1 the schoolStudentService to set
     */
    public void setSchoolStudentService(SchoolStudentService schoolStudentService1) {
        this.schoolStudentService = schoolStudentService1;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean1) {
        this.userLoginSessionBean = userLoginSessionBean1;
    }

    /**
     * @param studentService the studentService to set
     */
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * @param notificationService the notificationService1 to set
     */
    public void setNotificationService(NotificationService notificationService1) {
        this.notificationService = notificationService1;
    }

    /**
     * @param excelFileImportBean1 the excelFileImportBean to set
     */
    public void setExcelFileImportBean(ExcelFileImportBean excelFileImportBean1) {
        this.excelFileImportBean = excelFileImportBean1;
    }

    /**
     * @return the parent
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Parent parent) {
        this.parent = parent;
    }

    /**
     * @return the messageData
     */
    public MessageData getMessageData() {
        if (messageData == null) {
            this.messageData = new MessageData();
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the stateCode
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * @param stateCode the stateCode to set
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    /**
     *
     * @return
     */
    private SchoolParentQueryCriteria buildQueryCriteria() {
        SchoolParentQueryCriteria crit = new SchoolParentQueryCriteria();
        crit.setSchoolId(schoolSessionBean.getSchool().getSchoolId());

        if (this.surname != null && !this.surname.trim().isEmpty()) {
            crit.setSurname(this.surname, WCString.LIKE);
        }
        if (this.phoneNumber != null && !this.phoneNumber.trim().isEmpty()) {
            crit.setPhoneNumber(this.phoneNumber, WCString.LIKE);
        }
        if (this.email != null && !this.email.trim().isEmpty()) {
            crit.setEmail(this.email, WCString.LIKE);
        }

        Vector<OrderClause> vec = new Vector<>(1);
        vec.add(new OrderClause(ParentQueryCriteria.surname));
        crit.setOrderByColumn(vec);

        return crit;
    }
}
