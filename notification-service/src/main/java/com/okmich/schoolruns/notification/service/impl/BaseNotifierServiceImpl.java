/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.notification.service.impl;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Employee;
import com.okmich.schoolruns.common.entity.EmployeeMessage;
import com.okmich.schoolruns.common.entity.Message;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.ParentMessage;
import com.okmich.schoolruns.common.entity.School;
import com.okmich.schoolruns.common.entity.SchoolMessage;
import com.okmich.schoolruns.notification.service.NotificationServiceImpl;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.data.MessageRecipient;
import com.okmich.schoolruns.notification.service.repo.EmployeeMessageRepo;
import com.okmich.schoolruns.notification.service.repo.MessageRepo;
import com.okmich.schoolruns.notification.service.repo.ParentMessageRepo;
import com.okmich.schoolruns.notification.service.repo.SchoolMessageRepo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Part of the job of the NotifierService is to persist the message recipients
 * and the message after dispatching it. This abstract class provides a common
 * implementation of all the notifier services that will extends it and provides
 * the persistence function for them so that the concrete NotifierService
 * implementation can focus on the message dispatching proper
 *
 * @author Michael
 */
public abstract class BaseNotifierServiceImpl implements Serializable {

    /**
     * to be set by the setter method
     */
    private MessageRepo messageRepo;

    public BaseNotifierServiceImpl() {
    }

    public void saveDispatchedMessage(MessageData messageData) throws Exception {
        try {
            Message message = messageData.getMessage();
            //save the message recipients as well
            switch (messageData.getParticipantCategoryCode().charAt(0)) {
                case CommonConstants.PART_CAT_PARENTS:
                    getParentMessageRepo().save(
                            getParentMessages(messageData.getMessageRecipients(), message));
                    break;
                case CommonConstants.PART_CAT_STAFF:
                    getEmployeeMessageRepo().save(
                            getEmployeeMessages(messageData.getMessageRecipients(), message));
                    break;
                case CommonConstants.PART_CAT_SCHOOL_ADMIN:
                    getSchoolMessageRepo().save(
                            getSchoolMessages(messageData.getMessageRecipients(), message));
                    break;
                case CommonConstants.PART_CAT_USER:
                    //to be implemented
                    break;
                default:
                //do nothing
            }
            message.setStatus(CommonConstants.STATUS_EXPIRED);
            messageRepo.save(message);
        } catch (Exception ex) {
            Logger.getLogger(NotificationServiceImpl.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     *
     * @return
     */
    public abstract EmployeeMessageRepo getEmployeeMessageRepo();

    /**
     *
     * @return
     */
    public abstract ParentMessageRepo getParentMessageRepo();

    /**
     *
     * @return
     */
    public abstract SchoolMessageRepo getSchoolMessageRepo();

    /**
     *
     *
     * @param messageRecipients
     * @param message
     * @return List<EmployeeMessage>
     */
    private List<EmployeeMessage> getEmployeeMessages(List<MessageRecipient> messageRecipients,
            Message message) {
        List<EmployeeMessage> employeeMessages = new ArrayList<>(messageRecipients.size());
        for (MessageRecipient messageRecipient : messageRecipients) {
            employeeMessages.add(new EmployeeMessage(messageRecipient.getAddress(),
                    new Employee((Integer) messageRecipient.getId()), message));
        }
        return employeeMessages;
    }

    /**
     *
     *
     * @param messageRecipients
     * @param message
     * @return List<ParentMessage>
     */
    private List<ParentMessage> getParentMessages(List<MessageRecipient> messageRecipients,
            Message message) {
        List<ParentMessage> parentMessages = new ArrayList<>(messageRecipients.size());
        for (MessageRecipient messageRecipient : messageRecipients) {
            parentMessages.add(new ParentMessage(messageRecipient.getAddress(),
                    new Parent((String) messageRecipient.getId()), message));
        }
        return parentMessages;
    }

    /**
     *
     * @param messageRecipients
     * @param message
     * @return List<SchoolMessage>
     */
    private List<SchoolMessage> getSchoolMessages(List<MessageRecipient> messageRecipients,
            Message message) {
        List<SchoolMessage> schoolMessages = new ArrayList<>(messageRecipients.size());
        for (MessageRecipient messageRecipient : messageRecipients) {
            schoolMessages.add(new SchoolMessage(messageRecipient.getAddress(),
                    new School((Integer) messageRecipient.getId()), message));
        }
        return schoolMessages;
    }

    /**
     *
     * @param _messageRepo
     */
    public void setMessageRepo(MessageRepo _messageRepo) {
        this.messageRepo = _messageRepo;
    }
}
