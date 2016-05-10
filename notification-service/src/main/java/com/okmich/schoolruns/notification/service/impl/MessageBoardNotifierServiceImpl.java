/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.notification.service.impl;

import com.okmich.common.exception.BusinessException;
import com.okmich.schoolruns.notification.service.NotificationServiceImpl;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.repo.EmployeeMessageRepo;
import com.okmich.schoolruns.notification.service.repo.ParentMessageRepo;
import com.okmich.schoolruns.notification.service.repo.SchoolMessageRepo;
import com.okmich.common.util.StringUtil;
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
@Service("messageBoardNotifierServiceImpl")
@Transactional(readOnly = true)
public class MessageBoardNotifierServiceImpl extends BaseNotifierServiceImpl
        implements NotifierService {

    static final Logger LOG = Logger.getLogger(MessageBoardNotifierServiceImpl.class.getName());
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

    public MessageBoardNotifierServiceImpl() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Async
    public void dispatchMessage(MessageData messageData) throws Exception {
        //do nothing implementation
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
}
