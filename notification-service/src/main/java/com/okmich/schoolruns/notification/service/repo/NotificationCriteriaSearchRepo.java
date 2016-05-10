/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.notification.service.repo;

import com.okmich.schoolruns.common.entity.Message;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface NotificationCriteriaSearchRepo extends Serializable {

    /**
     *
     * @param criteria
     * @return List<Message>
     */
    List<Message> findMessages(MessageQueryCriteria criteria);
}
