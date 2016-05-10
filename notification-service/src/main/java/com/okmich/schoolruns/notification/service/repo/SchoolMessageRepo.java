/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.notification.service.repo;

import com.okmich.schoolruns.common.entity.Message;
import com.okmich.schoolruns.common.entity.SchoolMessage;
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
public interface SchoolMessageRepo extends JpaRepository<SchoolMessage, Integer> {

    /**
     *
     *
     * @param schoolId
     * @return
     */
    @Transactional(readOnly = true)
    @Query(name = "findMessagesForSchool",
    value = "SELECT s.message FROM SchoolMessage s WHERE s.school.schoolId = ?1 "
    + "ORDER BY s.message.expiryDate DESC")
    List<Message> findMessagesForSchool(Integer schoolId);

    @Transactional(readOnly = true)
    @Query(name = "findUnreadMessagesForSchool",
    value = "SELECT s.message FROM SchoolMessage s WHERE s.school.schoolId = ?1 "
    + "AND s.status = 'A' ORDER BY s.message.modifiedTime DESC")
    List<Message> findUnreadMessages(Integer schoolId);

    @Transactional(readOnly = true)
    @Query(name = "findSchoolMessage",
    value = "SELECT s FROM SchoolMessage s WHERE s.school.schoolId = ?1 "
    + "AND s.message.messageId = ?2")
    SchoolMessage findMessage(Integer employeeId, Integer messageId);

    @Transactional(readOnly = true)
    @Query(name = "findMessage",
    value = "SELECT s FROM SchoolMessage s WHERE s.message.messageId = ?1")
    List<SchoolMessage> findMessage(Integer messageId);
}
