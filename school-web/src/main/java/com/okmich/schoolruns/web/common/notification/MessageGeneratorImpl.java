/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.notification;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.calendar.service.data.SchoolCalendarData;
import com.okmich.schoolruns.common.entity.Assignment;
import com.okmich.schoolruns.common.entity.AssignmentType;
import com.okmich.schoolruns.common.entity.ExamBatch;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.Receipt;
import com.okmich.schoolruns.common.entity.School;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.common.entity.StudentDiscipline;
import com.okmich.schoolruns.common.entity.UserLogin;
import com.okmich.schoolruns.common.service.CommonService;
import com.okmich.schoolruns.employee.service.data.EmployeeData;
import com.okmich.schoolruns.notification.service.NotificationService;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.data.MessageRecipient;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.school.service.SchoolStudentService;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.school.service.repo.ExamBatchClassRepo;
import com.okmich.schoolruns.security.service.SecurityService;
import com.okmich.schoolruns.student.service.StudentService;
import com.okmich.schoolruns.student.service.data.StudentData;
import com.okmich.schoolruns.web.common.FacesUtil;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 *
 * @author Michael
 */
@Service("messageGenerator")
@Transactional(readOnly = true)
public class MessageGeneratorImpl implements MessageGenerator {

    @Autowired
    private ExamBatchClassRepo examBatchClassRepo;
    @Autowired
    private CommonService commonService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SchoolStudentService schoolStudentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private VelocityEngine velocityEngine;
    /**
     * DEFAULT_SENDER
     */
    private static final String DEFAULT_SENDER = "SchoolRuns.com";
    /**
     * SCHOOL_CREATION_TITLE
     */
    private static final String SCHOOL_CREATION_TITLE = "Welcome to SchoolRuns!!";
    /**
     * PASSWORD_RESET_TITLE
     */
    private static final String PASSWORD_RESET_TITLE = "Password Reset!!";
    /**
     * PASSWORD_CHANGE_TITLE
     */
    private static final String PASSWORD_CHANGE_TITLE = "Password Changed!!";
    /**
     * PARENT_ACTIVATION_TITLE
     */
    private static final String PARENT_ACTIVATION_TITLE = "Parent-Ward:: Link Activation";
    /**
     * PARENT_CHANGE_TITLE
     */
    private static final String PARENT_DEACTIVATION_TITLE = "Parent-Ward:: Link Deactivation";
    /**
     * PARENT_CREATION_TITLE
     */
    private static final String PARENT_CREATION_TITLE = "Welcome to SchoolRuns!!";
    /**
     * RECEIPT_TITLE
     */
    private static final String RECEIPT_TITLE = "Receipt of Payment";
    /**
     * STAFF_CREATION_TITLE
     */
    private static final String STAFF_CREATION_TITLE = "Welcome to SchoolRuns!!";

    public MessageGeneratorImpl() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getMessageForTemplate(String templateFile, Map model) {
        return VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, templateFile, model);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public MessageData generateGenericMessage(MessageData messageData) {
        Map model = new HashMap(2);
        model.put("content", messageData.getContent());
        model.put("sender", messageData.getSender());
        String content = getMessageForTemplate(TemplateFiles.HTML_GENERIC_TEMPLATE, model);
        messageData.setContent(content);

        return messageData;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendCustomMessage(MessageData messageData) {
        char chnnlCode = CommonConstants.MSG_CHNNL_EMAIL;
        if (chnnlCode == messageData.getMessageChannelCode().charAt(0)) {
            messageData = generateGenericMessage(messageData);
        }
        try {
            schoolService.sendNotificationMessage(messageData);
        } catch (BusinessException ex) {
            Logger.getLogger(MessageGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendPostSchoolCreationMessage(final School school) {
        //email message
        final MessageData emailMessage = new MessageData();
        Map model = new HashMap(3);
        model.put("school", school);
        UserLogin userLogin = schoolService.findSchoolAdminLogin(school.getSchoolId());
        model.put("userLogin", userLogin);
        model.put("schoolurl", getSchoolUrl(school.getSchoolId()));
        String content = getMessageForTemplate(TemplateFiles.HTML_SCHOOL_CREATION_NOTICE_TEMPLATE, model);
        emailMessage.setContent(content);
        emailMessage.setExpiryDate(new Date());
        emailMessage.setLiveDate(new Date());
        emailMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_EMAIL));
        emailMessage.getMessageRecipients().add(
                new MessageRecipient(
                school.getSchoolId(), userLogin.getTitle(), userLogin.getEmail()));
        emailMessage.setModifiedBy(school.getModifiedBy());
        emailMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_SCHOOL_ADMIN));
        emailMessage.setSchoolId(school.getSchoolId());
        emailMessage.setTitle(SCHOOL_CREATION_TITLE);
        //sms message
        final MessageData smsMessage = new MessageData();
        content = getMessageForTemplate(TemplateFiles.SMS_SCHOOL_CREATION_TEMPLATE, model);
        smsMessage.setContent(content);
        smsMessage.setExpiryDate(new Date());
        smsMessage.setLiveDate(new Date());
        smsMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_SMS));
        smsMessage.getMessageRecipients().add(
                new MessageRecipient(
                school.getSchoolId(), userLogin.getTitle(), userLogin.getPhoneNumber()));
        smsMessage.setModifiedBy(school.getModifiedBy());
        smsMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_SCHOOL_ADMIN));
        smsMessage.setSchoolId(school.getSchoolId());
        smsMessage.setTitle(SCHOOL_CREATION_TITLE);
        smsMessage.setSender(DEFAULT_SENDER);

        //start a runnable thread to do the remaining activity in its own thread
        //while main control is returned to the calling method
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    notificationService.sendMessage(emailMessage);
                    notificationService.sendMessage(smsMessage);
                } catch (BusinessException ex) {
                    Logger.getLogger(MessageGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        new Thread(runnable).start();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendPasswordResetMessage(final UserLogin userLogin) {
        //email message
        final MessageData emailMessage = new MessageData();
        Map model = new HashMap(1);
        model.put("userLogin", userLogin);
        String content = getMessageForTemplate(TemplateFiles.HTML_PASSWORD_RESET_TEMPLATE, model);
        emailMessage.setContent(content);
        emailMessage.setExpiryDate(new Date());
        emailMessage.setLiveDate(new Date());
        emailMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_EMAIL));
        emailMessage.getMessageRecipients().add(
                new MessageRecipient(
                userLogin.getUserLoginId(), userLogin.getUsername(), userLogin.getEmail()));
        emailMessage.setModifiedBy(userLogin.getUsername());
        emailMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_USER));
        emailMessage.setSchoolId(userLogin.getSchool().getSchoolId());
        emailMessage.setTitle(PASSWORD_RESET_TITLE);

        //start a runnable thread to do the remaining activity in its own thread
        //while main control is returned to the calling method
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (userLogin.getEmail() != null && !userLogin.getEmail().isEmpty()) {
                        notificationService.sendMessage(emailMessage);
                    }
                } catch (BusinessException ex) {
                    Logger.getLogger(MessageGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        new Thread(runnable).start();
    }

    @Override
    public void sendPasswordChangeMessage(final UserLogin userLogin) {
        //email message
        final MessageData emailMessage = new MessageData();
        Map model = new HashMap(1);
        model.put("userLogin", userLogin);
        String content = getMessageForTemplate(TemplateFiles.HTML_PASSWORD_CHANGE_TEMPLATE, model);
        emailMessage.setContent(content);
        emailMessage.setExpiryDate(new Date());
        emailMessage.setLiveDate(new Date());
        emailMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_EMAIL));
        emailMessage.getMessageRecipients().add(
                new MessageRecipient(
                userLogin.getUserLoginId(), userLogin.getUsername(), userLogin.getEmail()));
        emailMessage.setModifiedBy(userLogin.getUsername());
        emailMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_USER));
        emailMessage.setSchoolId(userLogin.getSchool().getSchoolId());
        emailMessage.setTitle(PASSWORD_CHANGE_TITLE);

        //start a runnable thread to do the remaining activity in its own thread
        //while main control is returned to the calling method
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    notificationService.sendMessage(emailMessage);
                } catch (BusinessException ex) {
                    Logger.getLogger(MessageGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        new Thread(runnable).start();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendPostEmployeeCreationMessage(final List<EmployeeData> employeeDataList) {
        for (EmployeeData employeeData : employeeDataList) {
            sendPostEmployeeCreationMessage(employeeData);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendPostEmployeeCreationMessage(final EmployeeData employeeData) {
        UserLogin userLogin = securityService.findUserLogin(employeeData.getUserLoginId());
        //email message
        final MessageData emailMessage = new MessageData();
        Map model = new HashMap(2);
        model.put("employee", employeeData.getEmployee());
        model.put("userLogin", userLogin);
        String content = getMessageForTemplate(TemplateFiles.HTML_STAFF_CREATION_NOTICE_TEMPLATE, model);
        emailMessage.setContent(content);
        emailMessage.setExpiryDate(new Date());
        emailMessage.setLiveDate(new Date());
        emailMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_EMAIL));
        emailMessage.getMessageRecipients().add(
                new MessageRecipient(
                employeeData.getEmployeeId(), employeeData.getFullname(), employeeData.getEmail()));
        emailMessage.setModifiedBy(employeeData.getModifiedBy());
        emailMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_STAFF));
        emailMessage.setSchoolId(employeeData.getSchoolId());
        emailMessage.setTitle(STAFF_CREATION_TITLE);
        //sms message
        final MessageData smsMessage = new MessageData();
        content = getMessageForTemplate(TemplateFiles.SMS_STAFF_CREATION_TEMPLATE, model);
        smsMessage.setContent(content);
        smsMessage.setExpiryDate(new Date());
        smsMessage.setLiveDate(new Date());
        smsMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_SMS));
        smsMessage.getMessageRecipients().add(
                new MessageRecipient(
                employeeData.getEmployeeId(), employeeData.getFullname(), employeeData.getMobileNo()));
        smsMessage.setModifiedBy(employeeData.getModifiedBy());
        smsMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_STAFF));
        smsMessage.setSchoolId(employeeData.getSchoolId());
        smsMessage.setTitle(STAFF_CREATION_TITLE);
        smsMessage.setSender(DEFAULT_SENDER);
        //send messages
        try {
            if (employeeData.getEmail() != null && !employeeData.getEmail().isEmpty()) {
                notificationService.sendMessage(emailMessage);
            }
            if (employeeData.getMobileNo() != null && !employeeData.getMobileNo().isEmpty()) {
                notificationService.sendMessage(smsMessage);
            }
        } catch (BusinessException ex) {
            Logger.getLogger(MessageGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendPostParentCreationMessage(final List<Parent> parentList, final School school) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Parent parent : parentList) {
                    sendPostParentCreationMessage(parent, school);
                }
            }
        }).start();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendPostParentCreationMessage(final Parent parent, School school) {
        UserLogin userLogin;
        try {
            //userLogin = securityService.resetPasswordByMobile(parent.getPhoneNumber(), true);
            userLogin = securityService.findUserLoginForParent(parent.getPhoneNumber());
        } catch (Exception ex) {
            Logger.getLogger(MessageGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        //email message
        final MessageData emailMessage = new MessageData();
        Map model = new HashMap(2);
        model.put("parent", parent);
        model.put("userLogin", userLogin);
        model.put("sender", school.getName());
        String content = getMessageForTemplate(TemplateFiles.HTML_PARENT_CREATION_NOTICE_TEMPLATE, model);
        emailMessage.setContent(content);
        emailMessage.setExpiryDate(new Date());
        emailMessage.setLiveDate(new Date());
        emailMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_EMAIL));
        emailMessage.getMessageRecipients().add(
                new MessageRecipient(
                parent.getPhoneNumber(), parent.getFullname(), parent.getEmail()));
        emailMessage.setModifiedBy(parent.getModifiedBy());
        emailMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_PARENTS));
        emailMessage.setSchoolId(school.getSchoolId());
        emailMessage.setTitle(PARENT_CREATION_TITLE);
        //sms message
        final MessageData smsMessage = new MessageData();
        content = getMessageForTemplate(TemplateFiles.SMS_PARENT_CREATION_TEMPLATE, model);
        smsMessage.setContent(content);
        smsMessage.setExpiryDate(new Date());
        smsMessage.setLiveDate(new Date());
        smsMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_SMS));
        smsMessage.getMessageRecipients().add(
                new MessageRecipient(
                parent.getPhoneNumber(), parent.getFullname(), parent.getPhoneNumber()));
        smsMessage.setModifiedBy(parent.getModifiedBy());
        smsMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_PARENTS));
        smsMessage.setSchoolId(school.getSchoolId());
        smsMessage.setTitle(PARENT_CREATION_TITLE);
        smsMessage.setSender(school.getName());

        //start a runnable thread to do the remaining activity in its own thread
        //while main control is returned to the calling method
        try {
            if (parent.getEmail() != null && !parent.getEmail().isEmpty()) {
                notificationService.sendMessage(emailMessage);
            }
            if (parent.getPhoneNumber() != null && !parent.getPhoneNumber().isEmpty()) {
                notificationService.sendMessage(smsMessage);
            }
        } catch (BusinessException ex) {
            Logger.getLogger(MessageGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendPostUserLoginCreationMessage(UserLogin userLogin) {
        //email message
        final MessageData emailMessage = new MessageData();
        Map model = new HashMap(2);
        model.put("userLogin", userLogin);
        model.put("defaultRole", userLogin.getDefaultSystemRole().getDescription());
        String content = getMessageForTemplate(TemplateFiles.HTML_USERLOGIN_CREATION_NOTICE_TEMPLATE, model);
        emailMessage.setContent(content);
        emailMessage.setExpiryDate(new Date());
        emailMessage.setLiveDate(new Date());
        emailMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_EMAIL));
        emailMessage.getMessageRecipients().add(
                new MessageRecipient(
                userLogin.getPhoneNumber(), userLogin.getTitle(), userLogin.getEmail()));
        emailMessage.setModifiedBy(userLogin.getModifiedBy());
        emailMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_USER));
        emailMessage.setSchoolId(null);
        emailMessage.setTitle(STAFF_CREATION_TITLE);
        emailMessage.setSender(DEFAULT_SENDER);
        //sms message
        final MessageData smsMessage = new MessageData();
        content = getMessageForTemplate(TemplateFiles.SMS_USERLOGIN_CREATION_TEMPLATE, model);
        smsMessage.setContent(content);
        smsMessage.setExpiryDate(new Date());
        smsMessage.setLiveDate(new Date());
        smsMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_SMS));
        smsMessage.getMessageRecipients().add(
                new MessageRecipient(
                userLogin.getUserLoginId(), userLogin.getTitle(), userLogin.getPhoneNumber()));
        smsMessage.setModifiedBy(userLogin.getModifiedBy());
        smsMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_USER));
        smsMessage.setSchoolId(null);
        smsMessage.setTitle(STAFF_CREATION_TITLE);
        smsMessage.setSender(DEFAULT_SENDER);

        //start a runnable thread to do the remaining activity in its own thread
        //while main control is returned to the calling method
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    notificationService.sendMessage(emailMessage);
                    notificationService.sendMessage(smsMessage);
                } catch (BusinessException ex) {
                    Logger.getLogger(MessageGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        new Thread(runnable).start();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendParentDeassignMessage(final StudentData studentData, final Parent parent,
            final UserLogin userLogin) {
        //sms message
        final MessageData smsMessage = new MessageData();
        Map model = new HashMap(2);
        model.put("student", studentData.getFullname());
        String content = getMessageForTemplate(TemplateFiles.SMS_PARENT_STUDENT_DEASSIGN_TEMPLATE, model);
        smsMessage.setContent(content);
        smsMessage.setExpiryDate(new Date());
        smsMessage.setLiveDate(new Date());
        smsMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_SMS));
        smsMessage.getMessageRecipients().add(
                new MessageRecipient(
                parent.getPhoneNumber(), parent.getFullname(), parent.getPhoneNumber()));
        smsMessage.setModifiedBy(studentData.getModifiedBy());
        smsMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_PARENTS));
        smsMessage.setSchoolId(userLogin.getSchool().getSchoolId());
        smsMessage.setTitle(PARENT_DEACTIVATION_TITLE);
        smsMessage.setSender(userLogin.getSchool().getName());

        //start a runnable thread to do the remaining activity in its own thread
        //while main control is returned to the calling method
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (parent.getPhoneNumber() != null && !parent.getPhoneNumber().isEmpty()) {
                        notificationService.sendMessage(smsMessage);
                    }
                } catch (BusinessException ex) {
                    Logger.getLogger(MessageGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        new Thread(runnable).start();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendParentAssignMessage(StudentData studentData, final Parent parent,
            final UserLogin userLogin) {
        //sms message
        final MessageData smsMessage = new MessageData();
        Map model = new HashMap(2);
        model.put("student", studentData.getFullname());
        String content = getMessageForTemplate(TemplateFiles.SMS_PARENT_STUDENT_ASSIGN_TEMPLATE, model);
        smsMessage.setContent(content);
        smsMessage.setExpiryDate(new Date());
        smsMessage.setLiveDate(new Date());
        smsMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_SMS));
        smsMessage.getMessageRecipients().add(
                new MessageRecipient(
                parent.getPhoneNumber(), parent.getFullname(), parent.getPhoneNumber()));
        smsMessage.setModifiedBy(studentData.getModifiedBy());
        smsMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_PARENTS));
        smsMessage.setSchoolId(userLogin.getSchool().getSchoolId());
        smsMessage.setTitle(PARENT_ACTIVATION_TITLE);
        smsMessage.setSender(userLogin.getSchool().getName());

        //start a runnable thread to do the remaining activity in its own thread
        //while main control is returned to the calling method
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (parent.getPhoneNumber() != null && !parent.getPhoneNumber().isEmpty()) {
                        notificationService.sendMessage(smsMessage);
                    }
                } catch (BusinessException ex) {
                    Logger.getLogger(MessageGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        new Thread(runnable).start();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendParentDisciplinaryMessage(StudentDiscipline studentDiscipline,
            final UserLogin userLogin) {
        final Parent parent = schoolStudentService.findStudentParent(
                studentDiscipline.getSchoolStudent().getSchoolStudentId());
        if (parent == null) {
            return;
        }
        //sms message
        final MessageData smsMessage = new MessageData();
        Map model = new HashMap(2);
        model.put("student", studentDiscipline.getSchoolStudent().getStudent().getFullname());
        model.put("title", studentDiscipline.getTitle());
        model.put("severity", studentDiscipline.getWarningLevel().getDescription());
        String content = getMessageForTemplate(TemplateFiles.SMS_PARENT_STUDENT_DISCIPLINE_TEMPLATE, model);
        smsMessage.setContent(content);
        smsMessage.setExpiryDate(new Date());
        smsMessage.setLiveDate(new Date());
        smsMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_SMS));
        smsMessage.getMessageRecipients().add(
                new MessageRecipient(
                parent.getPhoneNumber(), parent.getFullname(), parent.getPhoneNumber()));
        smsMessage.setModifiedBy(studentDiscipline.getModifiedBy());
        smsMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_PARENTS));
        smsMessage.setSchoolId(userLogin.getSchool().getSchoolId());
        smsMessage.setTitle(PARENT_ACTIVATION_TITLE);
        smsMessage.setSender(userLogin.getSchool().getName());

        //start a runnable thread to do the remaining activity in its own thread
        //while main control is returned to the calling method
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (parent.getPhoneNumber() != null && !parent.getPhoneNumber().isEmpty()) {
                        notificationService.sendMessage(smsMessage);
                    }
                } catch (BusinessException ex) {
                    Logger.getLogger(MessageGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        new Thread(runnable).start();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendAssignmentCreationMessage(Assignment assignment,
            List<SchoolStudentData> dataList) {
        SchoolSubject schoolSubject = schoolService.findSchoolSubject(
                assignment.getSchoolSubject().getSchoolSubjectId());
        //assign the full object to assignment
        assignment.setSchoolSubject(schoolSubject);
        AssignmentType assignmentType = commonService.findAssignmentType(
                assignment.getAssignmentType().getAssignmentTypeCode());
        //assign
        assignment.setAssignmentType(assignmentType);
        //for each of the student, send the message to their parents
        for (SchoolStudentData ssd : dataList) {
            sendNewAssignmentMessage(ssd, assignment);
        }
    }
//
//    /**
//     * {@inheritDoc }
//     */
//    @Override
//    public void sendAssignmentCancellationMessage(Assignment assignment) {
//    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendAssignmentCommittalMessage(Assignment assignment) {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendExamKickoffNotice(ExamBatch examBatch, SchoolCalendarData schoolCalendarData) {
        final String messageTitle = "EXAMS BEGIN";
        List<SchoolStudent> schoolStudents = examBatchClassRepo.
                findSchoolStudentForExamBatch(examBatch.getExamBatchId());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        MessageData smsMessage;
        for (SchoolStudent schoolStudent : schoolStudents) {
            smsMessage = new MessageData();
            Map model = new HashMap(2);
            model.put("termdescription", schoolCalendarData.getTermDescription());
            model.put("examTypeCode", examBatch.getExamType().getDescription());
            model.put("student", schoolStudent.getStudent().getFirstname());
            model.put("startDate", df.format(examBatch.getStartDate()));
            model.put("endDate", df.format(examBatch.getEndDate()));
            String content = getMessageForTemplate(TemplateFiles.SMS_EXAM_BEGIN_NOTICE_TEMPLATE, model);
            smsMessage.setContent(content);
            smsMessage.setExpiryDate(new Date());
            smsMessage.setLiveDate(new Date());
            smsMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_SMS));
            smsMessage.setModifiedBy(examBatch.getModifiedBy());
            smsMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_PARENTS));
            smsMessage.setSchoolId(examBatch.getSchoolSection().getSchool().getSchoolId());
            smsMessage.setTitle(messageTitle);
            smsMessage.setSender(examBatch.getSchoolSection().getSchool().getName());
            Parent parent = schoolStudent.getStudent().getParent();
            //start a runnable thread to do the remaining activity in its own thread
            sendMessageToParent(parent, smsMessage);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendExamResultNotice(ExamBatch examBatch) {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendReceiptNotice(Receipt receipt, UserLogin userLogin) {
        final Parent parent = studentService.findParent(
                receipt.getMobileNo());
        if (parent == null) {
            return;
        }
        //sms message
        final MessageData smsMessage = new MessageData();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        Map model = new HashMap(2);
        model.put("amount", df.format(receipt.getAmount().abs()));
        model.put("receiptNumber", receipt.getReceiptNumber());
        String content = getMessageForTemplate(TemplateFiles.SMS_RECEIPT_NOTICE_TEMPLATE, model);
        smsMessage.setContent(content);
        smsMessage.setExpiryDate(new Date());
        smsMessage.setLiveDate(new Date());
        smsMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_SMS));
        smsMessage.getMessageRecipients().add(
                new MessageRecipient(parent.getPhoneNumber(),
                parent.getFullname(), parent.getPhoneNumber()));
        smsMessage.setModifiedBy(userLogin.getUsername());
        smsMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_PARENTS));
        smsMessage.setSchoolId(userLogin.getSchool().getSchoolId());
        smsMessage.setTitle(RECEIPT_TITLE);
        smsMessage.setSender(userLogin.getSchool().getName());
        //start a runnable thread to do the remaining activity in its own thread
        //while main control is returned to the calling method
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (parent.getPhoneNumber() != null && !parent.getPhoneNumber().isEmpty()) {
                        notificationService.sendMessage(smsMessage);
                    }
                } catch (BusinessException ex) {
                    Logger.getLogger(MessageGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        new Thread(runnable).start();
    }

    /**
     *
     * @param schoolId
     * @return
     */
    private String getSchoolUrl(Integer schoolId) {
        HttpServletRequest request = FacesUtil.getHttpServletRequest();
        String url = request.getRequestURL().toString().replace(request.getRequestURI(),
                request.getContextPath() + "/" + schoolId + ".sch");
        return url;
    }

    /**
     * the worker method that handles the broadcast of this event to parent
     *
     * @param schoolStudentData
     * @param assignment
     *
     */
    private void sendNewAssignmentMessage(SchoolStudentData schoolStudentData,
            Assignment assignment) {
        //return if the parentId is null or the parent property is empty
        if (schoolStudentData.getParentId() == null
                || schoolStudentData.getParent().isEmpty()) {
            return;
        }
        //sms message
        final MessageData smsMessage = new MessageData();
        Map model = new HashMap(2);
        model.put("student", schoolStudentData.getFirstname());
        model.put("assignmentType", assignment.getAssignmentType().getDescription());
        model.put("subject", assignment.getSchoolSubject().getSubject().getDescription());
        model.put("submission", new SimpleDateFormat("dd/MM/yyyy").format(
                assignment.getSubmissionDate()));
        String content = getMessageForTemplate(TemplateFiles.SMS_PARENT_ASSIGNMENT_TEMPLATE, model);
        smsMessage.setContent(content);
        smsMessage.setExpiryDate(new Date());
        smsMessage.setLiveDate(new Date());
        smsMessage.setMessageChannelCode(String.valueOf(CommonConstants.MSG_CHNNL_SMS));
        smsMessage.getMessageRecipients().add(
                new MessageRecipient(schoolStudentData.getParentId(),
                schoolStudentData.getParent(), schoolStudentData.getParentId()));
        smsMessage.setModifiedBy(assignment.getModifiedBy());
        smsMessage.setParticipantCategoryCode(String.valueOf(CommonConstants.PART_CAT_PARENTS));
        smsMessage.setSchoolId(schoolStudentData.getSchoolId());
        smsMessage.setTitle(RECEIPT_TITLE);
        smsMessage.setSender(schoolStudentData.getSchool());

        try {
            notificationService.sendMessage(smsMessage);
        } catch (BusinessException ex) {
            Logger.getLogger(MessageGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * sends a message represented by messageData to parent held by
     * <code>parent</code>
     *
     * @param parent
     * @param messageData
     */
    private void sendMessageToParent(final Parent parent, final MessageData messageData) {
        //while main control is returned to the calling method
        try {
            if (parent.getPhoneNumber() != null && !parent.getPhoneNumber().isEmpty()) {
                notificationService.sendMessage(messageData);
            }
        } catch (BusinessException ex) {
            Logger.getLogger(MessageGeneratorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}