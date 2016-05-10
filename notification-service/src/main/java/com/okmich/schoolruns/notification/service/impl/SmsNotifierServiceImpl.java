/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.notification.service.impl;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.util.StringUtil;
import com.okmich.schoolruns.notification.service.NotificationServiceImpl;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.data.MessageRecipient;
import com.okmich.schoolruns.notification.service.repo.EmployeeMessageRepo;
import com.okmich.schoolruns.notification.service.repo.ParentMessageRepo;
import com.okmich.schoolruns.notification.service.repo.SchoolMessageRepo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Service("smsNotifierServiceImpl")
@Transactional(readOnly = true)
public class SmsNotifierServiceImpl extends BaseNotifierServiceImpl
        implements NotifierService {

    static final Logger LOG = Logger.getLogger(SmsNotifierServiceImpl.class.getName());
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

    public SmsNotifierServiceImpl() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Async
    public void dispatchMessage(MessageData messageData) throws Exception {
        try {
            sendSMS(messageData);
        } catch (Exception ex) {
            Logger.getLogger(NotificationServiceImpl.class.getName())
                    .log(Level.SEVERE, ex.getMessage(), ex);
        }
        //having send the message, it is important to save the action or activity
        try {
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

    private void sendSMS(MessageData data) throws Exception {
        String smsUrl = "http://www.supertextng.com/api.php?username=okmich&password=okoro394&destination=#RECIPIENT&sender=#{SENDER}&message=#CONTENT";

        smsUrl = smsUrl.replace("#{SENDER}", encodeString(data.getSender()));
        smsUrl = smsUrl.replace("#CONTENT", encodeString(data.getContent()));

        String smsPushUrl;
        URL url;

        LOG.log(Level.INFO, " >>>>>>>>>>>>>>>>>>>>>>>>>> doing Sms dispatch {0}", data);
        for (MessageRecipient mr : data.getMessageRecipients()) {
            if (mr.getAddress() != null && !mr.getAddress().trim().isEmpty()) {
                smsPushUrl = smsUrl.replace("#RECIPIENT", encodeString(mr.getAddress()));
                LOG.log(Level.INFO, "smsUrl >>>>>>>>>>>>>>>>>>>>>>>>>> doing Sms dispatch {0}", smsPushUrl);
                try {
                    url = new URL(smsPushUrl);
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(url.openStream()));
                    String smsOutcome = buffer.readLine();
                    LOG.log(Level.INFO, "sms push outcome >>>>>>>>>>>>>>>>>>>>>>>>>> {0}", smsOutcome);
                } catch (IOException ex) {
                    Logger.getLogger(SmsNotifierServiceImpl.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     *
     * @param val
     * @return
     * @throws Exception
     */
    public String encodeString(String val) throws Exception {
        try {
            return val == null ? "" : URLEncoder.encode(val, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SmsNotifierServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
    }
}
