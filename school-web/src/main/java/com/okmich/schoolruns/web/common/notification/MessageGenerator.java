/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.notification;

import com.okmich.schoolruns.calendar.service.data.SchoolCalendarData;
import com.okmich.schoolruns.common.entity.Assignment;
import com.okmich.schoolruns.common.entity.ExamBatch;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.Receipt;
import com.okmich.schoolruns.common.entity.School;
import com.okmich.schoolruns.common.entity.StudentDiscipline;
import com.okmich.schoolruns.common.entity.UserLogin;
import com.okmich.schoolruns.employee.service.data.EmployeeData;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.student.service.data.StudentData;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Michael
 */
public interface MessageGenerator extends Serializable {

    /**
     * returns a String after merging the
     * <code>model</code> with the
     * <code>templateFile</code> using the VelocityEngine
     *
     * @param templateFile
     * @param model
     * @return String - the product of merging the template file with the model
     */
    String getMessageForTemplate(String templateFile, Map<String, ?> model);

    /**
     *
     * @param messageData
     * @return MessageData
     */
    MessageData generateGenericMessage(MessageData messageData);

    /**
     *
     * @param messageData
     * @return MessageData
     */
    void sendCustomMessage(MessageData messageData);

    /**
     *
     * @param userLogin
     */
    void sendPostUserLoginCreationMessage(UserLogin userLogin);

    /**
     *
     *
     * @param school
     */
    void sendPostSchoolCreationMessage(School school);

    /**
     *
     * @param employeeData
     */
    void sendPostEmployeeCreationMessage(EmployeeData employeeData);

    /**
     *
     * @param employeeDataList
     */
    void sendPostEmployeeCreationMessage(List<EmployeeData> employeeDataList);

    /**
     *
     * @param parent
     */
    void sendPostParentCreationMessage(Parent parent, School school);

    /**
     * creates a new thread that handles the call to
     * sendPostParentCreationMessage(Parent parent, School school) for each
     * parent in the list
     *
     * @param parentList
     * @param school
     */
    void sendPostParentCreationMessage(final List<Parent> parentList, final School school);

    /**
     *
     * @param userLogin
     */
    void sendPasswordChangeMessage(UserLogin userLogin);

    /**
     *
     * @param userLogin
     */
    void sendPasswordResetMessage(UserLogin userLogin);

    /**
     *
     * @param studentData
     * @param parent
     */
    void sendParentDeassignMessage(StudentData studentData, Parent parent,
            UserLogin userLogin);

    /**
     *
     * @param studentData
     * @param parent
     */
    void sendParentAssignMessage(StudentData studentData, Parent parent,
            UserLogin userLogin);

    /**
     *
     * @param studentDiscipline
     * @param parent
     * @param userLogin
     */
    public void sendParentDisciplinaryMessage(StudentDiscipline studentDiscipline,
            UserLogin userLogin);

    /**
     *
     *
     * @param assignment
     * @param dataList - List<SchoolStudentData>
     */
    void sendAssignmentCreationMessage(Assignment assignment, List<SchoolStudentData> dataList);

//    /**
//     *
//     *
//     * @param assignment
//     */
//    void sendAssignmentCancellationMessage(Assignment assignment);
    /**
     *
     *
     * @param assignment
     */
    void sendAssignmentCommittalMessage(Assignment assignment);

    /**
     *
     *
     * @param examBatch
     * @param schoolCalendarData
     */
    void sendExamKickoffNotice(ExamBatch examBatch, SchoolCalendarData schoolCalendarData);

    /**
     *
     * @param examBatch
     */
    void sendExamResultNotice(ExamBatch examBatch);

    /**
     *
     * @param examBatch
     */
    void sendReceiptNotice(Receipt receipt, UserLogin userLogin);
}
