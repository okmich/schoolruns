/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.notification.service.repo;

import com.okmich.common.entity.repo.CriteriaSearchWorker;
import com.okmich.schoolruns.common.entity.Message;
import com.okmich.schoolruns.notification.service.data.MessageData;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Repository("notificationCriteriaSearchRepo")
@Transactional(readOnly = true)
public class NotificationCriteriaSearchRepoImpl implements NotificationCriteriaSearchRepo {

    /**
     * messagerepoWorker - Section head Criteria Search Repo
     */
    private CriteriaSearchWorker<Message, MessageQueryCriteria> messagerepoWorker;
    /**
     * entityManager
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     *
     */
    public NotificationCriteriaSearchRepoImpl() {
        messagerepoWorker =
                new CriteriaSearchWorker<Message, MessageQueryCriteria>();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Message> findMessages(MessageQueryCriteria criteria) {
        return messagerepoWorker.findByCriteria(entityManager, criteria);
    }
}