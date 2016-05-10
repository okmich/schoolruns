/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.notification.service.data;

import com.okmich.common.BaseData;
import com.okmich.schoolruns.common.entity.Message;
import com.okmich.schoolruns.common.entity.MessageChannel;
import com.okmich.schoolruns.common.entity.ParticipantCategory;
import com.okmich.schoolruns.common.entity.School;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Michael
 */
public final class MessageData extends BaseData {

    private Integer messageId;
    private String title;
    private String content;
    private Date liveDate;
    private Date expiryDate;
    private String sender;
    private String school;
    private Integer schoolId;
    private String participantCategory;
    private String participantCategoryCode;
    private String messageChannel;
    private String messageChannelCode;
    private String attachmentPath;
    private boolean fileAttached;
    private String attachmentName;
    private List<MessageRecipient> messageRecipients;

    public MessageData() {
    }

    public MessageData(Message message) {
        setMessage(message);
    }

    /**
     * @return the messageId
     */
    public Integer getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
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
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the liveDate
     */
    public Date getLiveDate() {
        return liveDate;
    }

    /**
     * @param liveDate the liveDate to set
     */
    public void setLiveDate(Date liveDate) {
        this.liveDate = liveDate;
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
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the school
     */
    public String getSchool() {
        return school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * @return the schooId
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     * @param schooId the schooId to set
     */
    public void setSchoolId(Integer schooId) {
        this.schoolId = schooId;
    }

    /**
     * @return the participantCategory
     */
    public String getParticipantCategory() {
        return participantCategory;
    }

    /**
     * @param participantCategory the participantCategory to set
     */
    public void setParticipantCategory(String participantCategory) {
        this.participantCategory = participantCategory;
    }

    /**
     * @return the participantCategoryCode
     */
    public String getParticipantCategoryCode() {
        return participantCategoryCode;
    }

    /**
     * @param participantCategoryCode the participantCategoryCode to set
     */
    public void setParticipantCategoryCode(String participantCategoryCode) {
        this.participantCategoryCode = participantCategoryCode;
    }

    /**
     * @return the messageChannel
     */
    public String getMessageChannel() {
        return messageChannel;
    }

    /**
     * @param messageChannel the messageChannel to set
     */
    public void setMessageChannel(String messageChannel) {
        this.messageChannel = messageChannel;
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
     * @return the attachmentPath
     */
    public String getAttachmentPath() {
        return attachmentPath;
    }

    /**
     * @param attachmentPath the attachmentPath to set
     */
    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    /**
     * @return the fileAttached
     */
    public boolean isFileAttached() {
        return fileAttached;
    }

    /**
     * @param fileAttached the fileAttached to set
     */
    public void setFileAttached(boolean fileAttached) {
        this.fileAttached = fileAttached;
    }

    /**
     * @return the attachmentName
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * @param attachmentName the attachmentName to set
     */
    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    /**
     * @return the messageRecipients
     */
    public List<MessageRecipient> getMessageRecipients() {
        if (messageRecipients == null) {
            this.messageRecipients = new ArrayList<>();
        }
        return messageRecipients;
    }

    /**
     * @param messageRecipients the messageRecipients to set
     */
    public void setMessageRecipients(List<MessageRecipient> messageRecipients) {
        this.messageRecipients = messageRecipients;
    }

    /**
     *
     * @param Message the message to set
     */
    public void setMessage(Message message) {
        if (message == null) {
            return;
        }
        this.attachmentPath = message.getAttachmentPath();
        this.content = message.getContent();
        this.expiryDate = message.getExpiryDate();
        this.liveDate = message.getLiveDate();
        MessageChannel _messageChannel = message.getMessageChannel();
        if (_messageChannel != null) {
            this.messageChannel = _messageChannel.getDescription();
            this.messageChannelCode = _messageChannel.getMessageChannelCode();
        }
        this.messageId = message.getMessageId();
        //this.messageRecipients
        ParticipantCategory _participantCategory = message.getParticipantCategory();
        if (_participantCategory != null) {
            this.participantCategory = _participantCategory.getDescription();
            this.participantCategoryCode = _participantCategory.getParticipantCategoryCode();
        }
        School _school = message.getSchool();
        if (_school != null) {
            this.schoolId = _school.getSchoolId();
            this.school = _school.getName();
        }
        this.title = message.getTitle();

        this.setModifiedBy(message.getModifiedBy());
        this.setModifiedTime(message.getModifiedTime());
        this.setStatus(message.getStatus());
    }

    /**
     * a factory to create and populate a Message object
     *
     * @return Message
     */
    public Message getMessage() {
        Message message = new Message();

        message.setAttachmentPath(this.attachmentPath);
        message.setContent(this.content);
        message.setExpiryDate(this.expiryDate);
        message.setLiveDate(this.liveDate);
        message.setMessageChannel(new MessageChannel(this.messageChannelCode));
        message.setMessageId(this.messageId);
        message.setModifiedBy(this.modifiedBy);
        message.setModifiedTime(this.modifiedTime);
        message.setParticipantCategory(
                new ParticipantCategory(this.participantCategoryCode));
        if (this.schoolId != null && this.schoolId != 0) {
            message.setSchool(new School(this.schoolId));
        }
        message.setStatus(this.status);
        message.setTitle(this.title);

        return message;
    }
}
