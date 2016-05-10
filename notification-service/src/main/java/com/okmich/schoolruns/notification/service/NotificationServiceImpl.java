/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.notification.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.*;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.data.MessageRecipient;
import com.okmich.schoolruns.notification.service.data.ParticipantCategoryEnum;
import com.okmich.schoolruns.notification.service.impl.NotifierService;
import com.okmich.schoolruns.notification.service.repo.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * implements the {@link NotificationService} interface
 */
@Service("notificationService")
@Transactional
public class NotificationServiceImpl implements NotificationService {

    /**
     * emailNotifierServiceImpl
     */
    @Autowired
    private NotifierService emailNotifierServiceImpl;
    /**
     * messageBoardNotifierServiceImpl
     */
    @Autowired
    private NotifierService messageBoardNotifierServiceImpl;
    /**
     * smsNotifierServiceImpl
     */
    @Autowired
    private NotifierService smsNotifierServiceImpl;
    /**
     * messageRepo
     */
    @Autowired
    private MessageRepo messageRepo;
    /**
     * employeeMessageRepo
     */
    @Autowired
    private EmployeeMessageRepo employeeMessageRepo;
    /**
     * parentMessageRepo
     */
    @Autowired
    private ParentMessageRepo parentMessageRepo;
    /**
     * schoolMessageRepo
     */
    @Autowired
    private SchoolMessageRepo schoolMessageRepo;
    /**
     * notificationCriteriaSearchRepo
     */
    @Autowired
    private NotificationCriteriaSearchRepo notificationCriteriaSearchRepo;

    /**
     *
     */
    public NotificationServiceImpl() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void sendMessage(MessageData messageData) throws BusinessException {
        if (messageData.getMessageRecipients().isEmpty()) {
            throw new BusinessException("NULL RECIPIENT");
        }
        if (messageData.getLiveDate() == null) {
            messageData.setLiveDate(new Date());
        }
        //save the message
        try {
            messageData.setStatus(CommonConstants.STATUS_ACTIVE);
            Message message = messageData.getMessage();
            //save message to database
            message = messageRepo.save(message);
            //set the id on message data
            messageData.setMessageId(message.getMessageId());

            //send message to recipients
            switch (messageData.getMessageChannelCode().charAt(0)) {
                case CommonConstants.MSG_CHNNL_BOARD:
                    messageBoardNotifierServiceImpl.setMessageRepo(messageRepo);
                    messageBoardNotifierServiceImpl.dispatchMessage(messageData);
                    break;
                case CommonConstants.MSG_CHNNL_EMAIL:
                    emailNotifierServiceImpl.setMessageRepo(messageRepo);
                    emailNotifierServiceImpl.dispatchMessage(messageData);
                    break;
                case CommonConstants.MSG_CHNNL_SMS:
                    smsNotifierServiceImpl.setMessageRepo(messageRepo);
                    smsNotifierServiceImpl.dispatchMessage(messageData);
                    break;
                default:
                    throw new BusinessException("UNKNOWN Message Channel TYPE");
            }
        } catch (Exception ex) {
            Logger.getLogger(NotificationServiceImpl.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public MessageData findMessage(Integer messageId) {
        Message message = messageRepo.findOne(messageId);
        if (message == null) {
            return null;
        }
        MessageData messageData = new MessageData(message);
        switch (messageData.getParticipantCategoryCode().charAt(0)) {
            case CommonConstants.PART_CAT_STAFF:
                List<EmployeeMessage> employeeMessages = employeeMessageRepo.findMessage(
                        messageData.getMessageId());
                for (EmployeeMessage e : employeeMessages) {
                    messageData.getMessageRecipients().add(
                            new MessageRecipient(messageData.getMessageId(),
                            e.getEmployee().getSurname() + " " + e.getEmployee().getOthernames(),
                            e.getMessageAddress()));
                }
                break;
            case CommonConstants.PART_CAT_PARENTS:
                List<ParentMessage> parentMessages = parentMessageRepo.findMessage(
                        messageData.getMessageId());
                for (ParentMessage p : parentMessages) {
                    messageData.getMessageRecipients().add(
                            new MessageRecipient(messageData.getMessageId(),
                            p.getParent().getSurname() + " " + p.getParent().getOthernames(),
                            p.getMessageAddress()));
                }
                break;
            case CommonConstants.PART_CAT_SCHOOL_ADMIN:
                List<SchoolMessage> schoolMessages = schoolMessageRepo.findMessage(
                        messageData.getMessageId());
                for (SchoolMessage s : schoolMessages) {
                    messageData.getMessageRecipients().add(
                            new MessageRecipient(messageData.getMessageId(),
                            s.getSchool().getName(),
                            s.getMessageAddress()));
                }
                break;
            case CommonConstants.PART_CAT_USER:
            default:
            //do nothing

        }
        return messageData;
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public List<MessageData> findMessages(Object id,
            ParticipantCategoryEnum participantCategoryEnum, boolean isUnread) {
        List<Message> messages;
        switch (participantCategoryEnum) {
            case SCHOOLS:
                messages = isUnread ? schoolMessageRepo.findUnreadMessages((Integer) id)
                        : schoolMessageRepo.findMessagesForSchool((Integer) id);
                break;
            case EMPLOYEES:
                messages = isUnread ? employeeMessageRepo.findUnreadMessages((Integer) id)
                        : employeeMessageRepo.findMessagesForEmployee((Integer) id);
                break;
            case PARENTS:
                messages = isUnread ? parentMessageRepo.findUnreadMessages((String) id)
                        : parentMessageRepo.findMessagesForParents((String) id);
                break;
            default:
                messages = new ArrayList<>(0);
        }
        List<MessageData> dataList = new ArrayList<>(messages.size());
        for (Message message : messages) {
            dataList.add(new MessageData(message));
        }
        return dataList;
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public List<MessageData> findMessages(MessageQueryCriteria criteria) {
        List<Message> messages = notificationCriteriaSearchRepo.findMessages(criteria);
        List<MessageData> dataList = new ArrayList<>(messages.size());
        for (Message message : messages) {
            dataList.add(new MessageData(message));
        }
        return dataList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void markMessageAsRead(MessageData messageData,
            ParticipantCategoryEnum participantCategoryEnum) {
        MessageRecipient msgRecpt = messageData.getMessageRecipients().get(0);
        if (msgRecpt.getId() == null) {
            return;
        }
        switch (participantCategoryEnum) {
            case EMPLOYEES: //employee message
                EmployeeMessage eMsg = employeeMessageRepo.findMessage(
                        Integer.valueOf(msgRecpt.getId().toString()),
                        messageData.getMessageId());
                eMsg.setStatus(CommonConstants.STATUS_INACTIVE);
                eMsg.setModifiedBy(messageData.getModifiedBy());
                eMsg.setModifiedBy(messageData.getModifiedBy());
                employeeMessageRepo.save(eMsg);
                break;
            case PARENTS://parent message
                ParentMessage pMsg = parentMessageRepo.findMessage(
                        msgRecpt.getId().toString(),
                        messageData.getMessageId());
                pMsg.setStatus(CommonConstants.STATUS_INACTIVE);
                pMsg.setModifiedBy(messageData.getModifiedBy());
                pMsg.setModifiedTime(new Date());
                parentMessageRepo.save(pMsg);
                break;
            case SCHOOLS:
                SchoolMessage sMsg = schoolMessageRepo.findMessage(
                        Integer.valueOf(msgRecpt.getId().toString()),
                        messageData.getMessageId());
                sMsg.setStatus(CommonConstants.STATUS_INACTIVE);
                sMsg.setModifiedBy(messageData.getModifiedBy());
                sMsg.setModifiedTime(new Date());
                schoolMessageRepo.save(sMsg);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void markMessageAsUnread(MessageData messageData,
            ParticipantCategoryEnum participantCategoryEnum) {
        MessageRecipient msgRecpt = messageData.getMessageRecipients().get(0);
        if (msgRecpt.getId() == null) {
            return;
        }
        switch (participantCategoryEnum) {
            case EMPLOYEES: //employee message
                EmployeeMessage eMsg = employeeMessageRepo.findMessage(
                        Integer.valueOf(msgRecpt.getId().toString()),
                        messageData.getMessageId());
                eMsg.setStatus(CommonConstants.STATUS_ACTIVE);
                eMsg.setModifiedBy(messageData.getModifiedBy());
                employeeMessageRepo.save(eMsg);
                break;
            case PARENTS://parent message
                ParentMessage pMsg = parentMessageRepo.findMessage(
                        msgRecpt.getId().toString(),
                        messageData.getMessageId());
                pMsg.setStatus(CommonConstants.STATUS_ACTIVE);
                pMsg.setModifiedBy(messageData.getModifiedBy());
                parentMessageRepo.save(pMsg);
                break;
            case SCHOOLS:
                SchoolMessage sMsg = schoolMessageRepo.findMessage(
                        Integer.valueOf(msgRecpt.getId().toString()),
                        messageData.getMessageId());
                sMsg.setStatus(CommonConstants.STATUS_ACTIVE);
                sMsg.setModifiedBy(messageData.getModifiedBy());
                schoolMessageRepo.save(sMsg);
        }
    }
}