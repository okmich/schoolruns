/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.notification.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.data.ParticipantCategoryEnum;
import com.okmich.schoolruns.notification.service.repo.MessageQueryCriteria;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface NotificationService extends Serializable {

    /**
     * broadcast message to one or more recipient. The message channel for
     * sending is determined by the {@link MessageData#getMessageChannelCode() }
     * method with the potential wildcard participants is specified by the value
     * of {@link MessageData#getParticipantCategoryCode() }
     *
     * @param messageData - the message to be sent. Also includes the channel
     * and participant list.
     * @throws BusinessException - if any error occurs during sending
     */
    void sendMessage(MessageData messageData) throws BusinessException;

    /**
     * returns the message whose messageId is given. Along with the message, all
     * the message recipients are returned along side the message.
     *
     * @param messageId
     * @return MessageData
     */
    MessageData findMessage(Integer messageId);

    /**
     * find a list of messages that meet the query composed in the
     * {@link MessageQueryCriteria} object
     *
     * @param criteria - a composed query criteria
     * @return List<MessageData> - a list of message that meets the composed
     * criteria object
     */
    List<MessageData> findMessages(MessageQueryCriteria criteria);

    /**
     * find a list of message for the system entity which type is determined by
     * the value of the enum {@link ParticipantCategoryEnum}
     *
     * @param id - the primary key of the type of entity
     * @param participantCategoryEnum - to specify the type of user
     * @param isUread - if true all the messages returned will be unread
     * messages only
     * @return List<MessageData> - a list of messages for the entity with id
     */
    List<MessageData> findMessages(Object id,
            ParticipantCategoryEnum participantCategoryEnum, boolean isUnread);

    /**
     * sets the status of the respective recipient of the message to inactive
     * which flags the message as unread. Read message do not show up on the
     * portal board of the application during notification. Once a message is
     * marked as read, it can be reversed to the unread status by calling the 
     * {@link NotificationService#markMessageAsUnread(com.okmich.schoolruns.notification.service.data.MessageData, com.okmich.schoolruns.notification.service.data.ParticipantCategoryEnum)
     * }
     *
     * @param messageData
     * @param participantCategoryEnum
     */
    void markMessageAsRead(MessageData messageData,
            ParticipantCategoryEnum participantCategoryEnum);

    /**
     * sets the status of the respective recipient of the message to active
     * which flags the message as read. Read message do not show up on the
     * portal board of the application during notification. Once a message is
     * marked as Unread, it can be reversed to the read status by calling the 
     * {@link NotificationService#markMessageAsRead(com.okmich.schoolruns.notification.service.data.MessageData, com.okmich.schoolruns.notification.service.data.ParticipantCategoryEnum)
     * }
     *
     * @param messageData
     * @param participantCategoryEnum
     */
    void markMessageAsUnread(MessageData messageData,
            ParticipantCategoryEnum participantCategoryEnum);
}
