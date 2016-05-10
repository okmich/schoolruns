/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.notification.service.repo;

import com.okmich.schoolruns.common.entity.Message;
import com.okmich.schoolruns.common.entity.ParentMessage;
import com.okmich.schoolruns.notification.service.data.MessageRecipient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Repository
public interface ParentMessageRepo extends JpaRepository<ParentMessage, Integer> {

    @Transactional(readOnly = true)
    @Query(name = "findMessagesForParents",
    value = "SELECT p.message FROM ParentMessage p WHERE p.parent.phoneNumber = ?1 "
    + "ORDER BY p.message.modifiedTime DESC")
    List<Message> findMessagesForParents(String phoneNumber);

    @Transactional(readOnly = true)
    @Query(name = "findUnreadMessagesForParent",
    value = "SELECT p.message FROM ParentMessage p WHERE p.parent.phoneNumber = ?1 "
    + "AND p.status = 'A' ORDER BY p.message.modifiedTime DESC")
    List<Message> findUnreadMessages(String phoneNumber);

    @Transactional(readOnly = true)
    @Query(name = "findParentMessage",
    value = "SELECT p FROM ParentMessage p WHERE p.parent.phoneNumber = ?1 "
    + "AND p.message.messageId = ?2")
    ParentMessage findMessage(String phoneNumber, Integer messageId);

    @Transactional(readOnly = true)
    @Query(name = "findMessage",
    value = "SELECT p FROM ParentMessage p WHERE p.message.messageId = ?1 "
    + "ORDER BY p.parent.surname")
    List<ParentMessage> findMessage(Integer messageId);
}
