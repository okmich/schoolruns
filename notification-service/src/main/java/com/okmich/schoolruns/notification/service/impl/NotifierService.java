package com.okmich.schoolruns.notification.service.impl;

import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.repo.MessageRepo;
import java.io.Serializable;

/**
 *
 * @author Michael
 */
public interface NotifierService extends Serializable {

    /**
     * dispatch text-based message to a list of recipient using a channel
     * defined by the implementation
     *
     * @param messageData - containing the data to be dispatch, the list of
     * recipient and channel code
     * @throws Exception - if error occurs during message dispatch
     */
    void dispatchMessage(MessageData messageData) throws Exception;

    /**
     *
     * @param messageRepo
     */
    void setMessageRepo(MessageRepo messageRepo);
}
