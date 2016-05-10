/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.parent.bean;

import com.okmich.schoolruns.notification.service.NotificationService;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.data.MessageRecipient;
import com.okmich.schoolruns.notification.service.data.ParticipantCategoryEnum;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class ParentNotificationBean extends _BaseBean {

    @ManagedProperty("#{notificationService}")
    private NotificationService notificationService;
    @ManagedProperty("#{parentSessionBean}")
    private ParentSessionBean parentSessionBean;
    private MessageData messageData;

    /**
     * Creates a new instance of ParentNotificationBean
     */
    public ParentNotificationBean() {
    }

    /**
     * @param notificationService the notificationService to set
     */
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * @param parentSessionBean the parentSessionBean to set
     */
    public void setParentSessionBean(ParentSessionBean parentSessionBean) {
        this.parentSessionBean = parentSessionBean;
    }

    /**
     *
     * @return
     */
    public String markMessageAsRead() {
        String _msgId = FacesUtil.getRequestParameter("messageId");
        if (_msgId != null && !_msgId.isEmpty()) {
            MessageData data = new MessageData();
            data.setMessageId(Integer.parseInt(_msgId));
            data.getMessageRecipients().add(new MessageRecipient(
                    parentSessionBean.getParent().getPhoneNumber(),
                    null, null));
            data.setModifiedBy(parentSessionBean.getParent().getPhoneNumber());
            //call the service method
            notificationService.markMessageAsRead(data,
                    ParticipantCategoryEnum.PARENTS);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findMessageDetails() {
        String _msgId = FacesUtil.getRequestParameter("messageId");
        if (_msgId != null && !_msgId.isEmpty()) {
            setMessageData(notificationService.findMessage(
                    Integer.parseInt(_msgId)));
            return "/parent/notification/notificationdetails";
        }
        return "";
    }

    /**
     *
     * @param val
     * @return
     */
    public String truncate(String val) {
        if (val == null || val.length() < 50) {
            return val;
        }
        return new StringBuilder(
                val.substring(0, Math.min(50, val.length())))
                .append("...").toString();
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
}
