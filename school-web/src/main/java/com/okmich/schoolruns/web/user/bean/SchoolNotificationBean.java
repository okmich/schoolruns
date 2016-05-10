/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean;

import com.okmich.common.entity.criteria.WCDate;
import com.okmich.common.entity.criteria.WCString;
import com.okmich.common.exception.ErrorConstants;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.employee.service.data.EmployeeData;
import com.okmich.schoolruns.notification.service.NotificationService;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.data.MessageRecipient;
import com.okmich.schoolruns.notification.service.repo.MessageQueryCriteria;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.student.service.StudentService;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.common.notification.MessageGenerator;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class SchoolNotificationBean extends _BaseBean {

    @ManagedProperty("#{notificationService}")
    private NotificationService notificationService;
    @ManagedProperty("#{studentService}")
    private StudentService studentService;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{messageGenerator}")
    private MessageGenerator messageGenerator;
    private MessageData messageData;
    private String title;
    private Date expiryDate;
    private String participationCategoryCode;
    private String messageChannelCode;
    private Integer gradeLevelId;
    private Integer schoolSectionId;
    private Integer schoolClassId;
    private boolean custom = false;
    private boolean sms = true;

    /**
     * Creates a new instance of SchoolNotificationBean
     */
    public SchoolNotificationBean() {
    }

    /**
     * @param notificationService1 the notificationService to set
     */
    public void setNotificationService(NotificationService notificationService1) {
        this.notificationService = notificationService1;
    }

    /**
     * @param studentService the studentService to set
     */
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * @param schoolSessionBean1 the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean1) {
        this.schoolSessionBean = schoolSessionBean1;
    }

    /**
     * @param sessionBean1 the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean1) {
        this.sessionBean = sessionBean1;
    }

    /**
     * @param userLoginSessionBean1 the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean1) {
        this.userLoginSessionBean = userLoginSessionBean1;
    }

    /**
     * @param messageGenerator the messageGenerator to set
     */
    public void setMessageGenerator(MessageGenerator messageGenerator1) {
        this.messageGenerator = messageGenerator1;
    }

    /**
     *
     * @return String
     */
    public String findMessages() {
        try {
            List<MessageData> _messageList = notificationService.findMessages(buildQueryCriteria());
            sessionBean.setMessageModel(new ListDataModel<>(_messageList));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return String
     */
    public String findMessageDetails() {
        if (getMessageData().getMessageId() != null) {
            setMessageData(notificationService.findMessage(getMessageData().getMessageId()));
            return "/schooluser/notification/notificationdetails";
        }
        return "";
    }

    /**
     *
     * @return String
     */
    public String sendMessages() {
        getMessageData().setModifiedBy(
                userLoginSessionBean.getUserLogin().getUsername());
        getMessageData().setLiveDate(new Date());
        try {
            //send the message
            messageGenerator.sendCustomMessage(getMessageData());
            //tell the user of the success of the message dispatch
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Message sent successfully", ""));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return String
     */
    public String sendMessage() {
        getMessageData().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getMessageData().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        getMessageData().setStatus(CommonConstants.STATUS_ACTIVE);
        getMessageData().setSender(schoolSessionBean.getSchool().getName());
        //validation
        try {
            if (getMessageData().getExpiryDate().before(new Date())) {
                throw new Exception("ERROR_INVALID EXPIRY DATE: in the past");
            }
            char cat = getMessageData().getParticipantCategoryCode().charAt(0);
            if (cat == CommonConstants.PART_CAT_SCHOOL_ADMIN
                    || cat == CommonConstants.PART_CAT_USER) {
                throw new Exception("ERROR_INVALID PARTICIPANT CATEGORY");
            }
        } catch (Exception ex) {
            processException(ex);
            return "";
        }
        //execute
        try {
            //if the message is a parent custom message, we should at this 
            //point prepare the recipient listing
            if (CommonConstants.PART_CAT_CUSTOM
                    == getMessageData().getParticipantCategoryCode().charAt(0)) {
                List<Parent> parents;
                if (getSchoolClassId() != null && getSchoolClassId() != 0) {
                    parents = studentService.findParentsForSchoolClass(
                            schoolSessionBean.getSchoolCalendarData().getSchoolYearId(),
                            getSchoolClassId());
                } else if (getGradeLevelId() != null && getGradeLevelId() != 0) {
                    parents = studentService.findParentsForSchoolGradeLevel(
                            schoolSessionBean.getSchoolCalendarData().getSchoolYearId(),
                            getGradeLevelId());
                } else if (getSchoolSectionId() != null && getSchoolSectionId() != 0) {
                    parents = studentService.findParentsForSchoolSection(
                            schoolSessionBean.getSchoolCalendarData().getSchoolYearId(),
                            getSchoolSectionId());
                } else {
                    throw new Exception(ErrorConstants.INVALID_PARTICIPANT_CODE);
                }
                if (parents.isEmpty()) {
                    throw new Exception("ERROR_EMPTY_RECIPIENT_LIST");
                }
                String address = null;
                for (Parent p : parents) {
                    address = getMessageData().getMessageChannelCode().charAt(0)
                            == CommonConstants.MSG_CHNNL_EMAIL
                            ? p.getEmail() : p.getPhoneNumber();
                    if (address != null && !address.trim().isEmpty()) {
                        getMessageData().getMessageRecipients().add(
                                new MessageRecipient(p.getPhoneNumber(), p.getFullname(), address));
                    }
                }
            }
            //send the message
            messageGenerator.sendCustomMessage(getMessageData());
            //tell the user of the success of the message dispatch
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Message sent successfully", ""));
            this.messageData = null;
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * the first basic assumption
     *
     * @return String
     */
    public String sendMessageToEmployee() {
        //validation
        try {
            if (getMessageData().getExpiryDate().before(new Date())) {
                throw new Exception("INVALID EXPIRY DATE: in the past");
            }
        } catch (Exception ex) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            return "";
        }
        getMessageData().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getMessageData().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        getMessageData().setStatus(CommonConstants.STATUS_ACTIVE);
        getMessageData().setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_STAFF));
        getMessageData().setSender(schoolSessionBean.getSchool().getName());
        EmployeeData _employeeData = sessionBean.getEmployeeData();
        String address;
        switch (getMessageData().getMessageChannelCode().charAt(0)) {
            case CommonConstants.MSG_CHNNL_EMAIL:
                address = _employeeData.getEmail();
                break;
            case CommonConstants.MSG_CHNNL_SMS:
                address = _employeeData.getMobileNo();
                break;
            default:
                address = "";
        }
        getMessageData().getMessageRecipients().add(new MessageRecipient(
                _employeeData.getEmployeeId(),
                _employeeData.getFullname(),
                address));
        //execute
        try {
            //send the message
            notificationService.sendMessage(getMessageData());
            //tell the user of the success of the message dispatch
            return "/schooluser/staff/messageboard";
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
    public String sendMessageToParent() {
        //validation
        try {
            if (getMessageData().getExpiryDate().before(new Date())) {
                throw new Exception("INVALID EXPIRY DATE: in the past");
            }
        } catch (Exception ex) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            return "";
        }
        getMessageData().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getMessageData().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        getMessageData().setStatus(CommonConstants.STATUS_ACTIVE);
        getMessageData().setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_PARENTS));
        getMessageData().setSender(schoolSessionBean.getSchool().getName());
        Parent _parent = sessionBean.getParent();
        String address;
        switch (getMessageData().getMessageChannelCode().charAt(0)) {
            case CommonConstants.MSG_CHNNL_EMAIL:
                address = _parent.getEmail();
                break;
            case CommonConstants.MSG_CHNNL_SMS:
                address = _parent.getPhoneNumber();
                break;
            default:
                address = "";
        }
        getMessageData().getMessageRecipients().add(new MessageRecipient(
                _parent.getPhoneNumber(),
                _parent.getFullname(),
                address));
        //execute
        try {
            //send the message
            notificationService.sendMessage(getMessageData());
            //tell the user of the success of the message dispatch
            return "/schooluser/parent/messageboard";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * @return the messageData
     */
    public MessageData getMessageData() {
        if (this.messageData == null) {
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
     *
     * @param event
     */
    public void changeReceipientCategoryEvent(ValueChangeEvent event) {
        HtmlSelectOneMenu menu = (HtmlSelectOneMenu) event.getComponent();
        Object _recipentValue = menu.getValue();
        if (_recipentValue != null) {
            char receiptCatValue = _recipentValue.toString().charAt(0);
            this.custom = receiptCatValue == CommonConstants.PART_CAT_CUSTOM;
        }
    }

    /**
     *
     * @param event
     */
    public void changeMessageChannelEvent(ValueChangeEvent event) {
        HtmlSelectOneMenu menu = (HtmlSelectOneMenu) event.getComponent();
        Object _channelValue = menu.getValue();
        if (_channelValue != null) {
            char messageChannelValue = _channelValue.toString().charAt(0);
            this.sms = messageChannelValue == CommonConstants.MSG_CHNNL_SMS;
        }
    }

    /**
     *
     * @return
     */
    private MessageQueryCriteria buildQueryCriteria() {
        MessageQueryCriteria mCriteria = new MessageQueryCriteria();
        mCriteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        if (getExpiryDate() != null) {
            mCriteria.setExpiryDate(getExpiryDate(), WCDate.LESS_OR_EQUAL);
        }
        if (getMessageChannelCode() != null && !getMessageChannelCode().trim().isEmpty()) {
            mCriteria.setMessageChannelCode(getMessageChannelCode());
        }
        if (getParticipationCategoryCode() != null && !getParticipationCategoryCode().trim().isEmpty()) {
            mCriteria.setParticipantCategoryCode(getParticipationCategoryCode());
        }
        if (getTitle() != null && !getTitle().trim().isEmpty()) {
            mCriteria.setTitle(getTitle(), WCString.LIKE);
        }
        return mCriteria;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the expiryDate
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * @param expiryDate the expiryDate to set
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @return the participationCategoryCode
     */
    public String getParticipationCategoryCode() {
        return participationCategoryCode;
    }

    /**
     * @param participationCategoryCode the participationCategoryCode to set
     */
    public void setParticipationCategoryCode(String participationCategoryCode) {
        this.participationCategoryCode = participationCategoryCode;
    }

    /**
     * @return the messageChannelCode
     */
    public String getMessageChannelCode() {
        return messageChannelCode;
    }

    /**
     * @param messageChannelCode the messageChannelCode to set
     */
    public void setMessageChannelCode(String messageChannelCode) {
        this.messageChannelCode = messageChannelCode;
    }

    /**
     * @return the custom
     */
    public boolean isCustom() {
        return custom;
    }

    /**
     * @param custom the custom to set
     */
    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    /**
     * @return the sms
     */
    public boolean isSms() {
        return sms;
    }

    /**
     * @param sms the sms to set
     */
    public void setSms(boolean sms) {
        this.sms = sms;
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
     * @return the schoolSectionId
     */
    public Integer getSchoolSectionId() {
        return schoolSectionId;
    }

    /**
     * @param schoolSectionId the schoolSectionId to set
     */
    public void setSchoolSectionId(Integer schoolSectionId) {
        this.schoolSectionId = schoolSectionId;
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
}