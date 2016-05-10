/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.parent.bean;

import com.okmich.common.entity.criteria.OrderClause;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.UserLogin;
import com.okmich.schoolruns.notification.service.NotificationService;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.data.ParticipantCategoryEnum;
import com.okmich.schoolruns.school.service.SchoolStudentService;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.school.service.repo.SchoolStudentQueryCriteria;
import com.okmich.schoolruns.security.service.SecurityService;
import com.okmich.schoolruns.student.service.data.StudentData;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Michael
 */
@ManagedBean
@SessionScoped
public class ParentSessionBean extends _BaseBean {

    @ManagedProperty("#{schoolStudentService}")
    private SchoolStudentService schoolStudentService;
    @ManagedProperty("#{securityService}")
    private SecurityService securityService;
    @ManagedProperty("#{notificationService}")
    private NotificationService notificationService;
    private Parent parent;
    private UserLogin userLogin;
    private List<StudentData> studentDataList;
    private List<SchoolStudentData> schoolStudentDataList;

    /**
     * Creates a new instance of ParentSessionBean
     */
    public ParentSessionBean() {
    }

    /**
     * @param notificationService1 the notificationService to set
     */
    public void setNotificationService(NotificationService notificationService1) {
        this.notificationService = notificationService1;
    }

    /**
     * @param schoolStudentService the schoolStudentService to set
     */
    public void setSchoolStudentService(SchoolStudentService schoolStudentService) {
        this.schoolStudentService = schoolStudentService;
    }

    /**
     * @param securityService1 the securityService to set
     */
    public void setSecurityService(SecurityService securityService1) {
        this.securityService = securityService1;
    }

    /**
     *
     */
    public void cleanUp() {
        this.parent = null;
        setUserLogin(null);
        this.studentDataList = null;
        this.schoolStudentDataList = null;
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
        if (parent != null) {
            SchoolStudentQueryCriteria criteria =
                    new SchoolStudentQueryCriteria();
            criteria.setParentNumber(parent.getPhoneNumber());
            List<OrderClause> vec = new ArrayList<>();
            vec.add(new OrderClause(SchoolStudentQueryCriteria.firstname));
            criteria.setOrderByColumn(vec);
            //fetch all school student
            this.schoolStudentDataList =
                    schoolStudentService.findSchoolStudents(criteria);
            //set the userLogin 
            setUserLogin(securityService.findUserLogin(parent.getUserLoginId()));
            System.out.println(">>>>>>> " + getUserLogin());
        }
    }

    /**
     * @return the studentDataList
     */
    public List<StudentData> getStudentDataList() {
        if (this.studentDataList == null) {
            this.studentDataList = new ArrayList<>();
        }
        return studentDataList;
    }

    /**
     * @return the schoolStudentDataList
     */
    public List<SchoolStudentData> getSchoolStudentDataList() {
        if (this.schoolStudentDataList == null) {
            this.schoolStudentDataList = new ArrayList<>();
        }
        return schoolStudentDataList;
    }

    /**
     * @return the messageDataList
     */
    public List<MessageData> getMessageDataList() {
        return notificationService.findMessages(
                this.parent.getPhoneNumber(),
                ParticipantCategoryEnum.PARENTS, true);
    }

    /**
     * @return the userLogin
     */
    public UserLogin getUserLogin() {
        return userLogin;
    }

    /**
     * @param userLogin the userLogin to set
     */
    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }
}
