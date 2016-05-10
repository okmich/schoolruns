/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.notification.service.repo;

import com.okmich.schoolruns.common.entity.Employee;
import com.okmich.schoolruns.common.entity.EmployeeMessage;
import com.okmich.schoolruns.common.entity.Message;
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
public interface EmployeeMessageRepo extends JpaRepository<EmployeeMessage, Integer> {

    @Transactional(readOnly = true)
    @Query(name = "findMessagesForEmployee",
    value = "SELECT e.message FROM EmployeeMessage e WHERE e.employee.employeeId = ?1 "
    + "ORDER BY e.message.expiryDate DESC")
    List<Message> findMessagesForEmployee(Integer employeeId);

    @Transactional(readOnly = true)
    @Query(name = "findUnreadMessagesForEmployee",
    value = "SELECT e.message FROM EmployeeMessage e WHERE e.employee.employeeId = ?1 "
    + "AND e.status = 'A' ORDER BY e.message.modifiedTime DESC")
    List<Message> findUnreadMessages(Integer employeeId);

    @Transactional(readOnly = true)
    @Query(name = "findEmployeeMessage",
    value = "SELECT e FROM EmployeeMessage e WHERE e.employee.employeeId = ?1 "
    + "AND e.message.messageId = ?2")
    EmployeeMessage findMessage(Integer employeeId, Integer messageId);

    @Transactional(readOnly = true)
    @Query(name = "findMessage",
    value = "SELECT e FROM EmployeeMessage e WHERE e.message.messageId = ?1 "
    + "ORDER BY e.employee.surname")
    List<EmployeeMessage> findMessage(Integer messageId);
}
