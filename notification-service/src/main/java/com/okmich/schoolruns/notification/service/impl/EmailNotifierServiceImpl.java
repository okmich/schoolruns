/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.notification.service.impl;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.service.CommonService;
import com.okmich.schoolruns.notification.service.NotificationServiceImpl;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.data.MessageRecipient;
import com.okmich.schoolruns.notification.service.repo.EmployeeMessageRepo;
import com.okmich.schoolruns.notification.service.repo.ParentMessageRepo;
import com.okmich.schoolruns.notification.service.repo.SchoolMessageRepo;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Service("emailNotifierServiceImpl")
@Transactional(readOnly = true)
public class EmailNotifierServiceImpl extends BaseNotifierServiceImpl implements NotifierService {

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
     * commonService
     */
    @Autowired
    private CommonService commonService;
    /**
     * LOG
     */
    static final Logger LOG = Logger.getLogger(EmailNotifierServiceImpl.class.getName());
    /**
     * mailSender
     */
    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;

    public EmailNotifierServiceImpl() {
    }

    @Override
    @Async
    public void dispatchMessage(MessageData messageData) throws Exception {
        javaMailSenderImpl.setUsername(
                commonService.findCtrlParameterValue(CommonConstants.CTRL_PARAM_PICASA_SERVICE_USERNAME));
        javaMailSenderImpl.setPassword(
                commonService.findCtrlParameterValue(CommonConstants.CTRL_PARAM_PICASA_SERVICE_PASSWD));
        // use the true flag to indicate you need a multipart message
        MimeMessage message = javaMailSenderImpl.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        for (MessageRecipient mp : messageData.getMessageRecipients()) {
            helper.addTo(mp.getAddress());
        }
        helper.setSubject(messageData.getTitle());
        helper.setText(messageData.getContent(), true);
        if (messageData.isFileAttached()) {
            helper.addAttachment(messageData.getAttachmentName(), new File(messageData.getAttachmentPath()));
        }
        try {
            javaMailSenderImpl.send(message);
            //having send the message, it is important to save the action or activity
            //call the super.dispatchMessage()
            saveDispatchedMessage(messageData);
        } catch (Exception ex) {
            Logger.getLogger(NotificationServiceImpl.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    @Override
    public EmployeeMessageRepo getEmployeeMessageRepo() {
        return employeeMessageRepo;
    }

    @Override
    public ParentMessageRepo getParentMessageRepo() {
        return parentMessageRepo;
    }

    @Override
    public SchoolMessageRepo getSchoolMessageRepo() {
        return schoolMessageRepo;
    }
}
