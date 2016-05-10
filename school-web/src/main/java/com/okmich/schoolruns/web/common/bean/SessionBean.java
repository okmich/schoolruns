/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.bean;

import com.okmich.schoolruns.calendar.service.data.Attendance;
import com.okmich.schoolruns.calendar.service.data.AttendanceSummary;
import com.okmich.schoolruns.calendar.service.data.EmployeeAttendanceTable;
import com.okmich.schoolruns.calendar.service.data.StudentAttendanceTable;
import com.okmich.schoolruns.calendar.service.data.TtEntryTable;
import com.okmich.schoolruns.common.entity.*;
import com.okmich.schoolruns.employee.service.data.EmployeeData;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.school.service.data.ExamScoreTable;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.student.service.data.StudentData;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {

    /**
     * assignment
     */
    private Assignment assignment;
    /**
     * exam
     */
    private Exam exam;
    /**
     * employeeData
     */
    private EmployeeData employeeData;
    /**
     * parent
     */
    private Parent parent;
    /**
     * userLogin
     */
    private UserLogin userLogin;
    /**
     * systemRole
     */
    private SystemRole systemRole;
    /**
     * school
     */
    private School school;
    /**
     * student
     */
    private StudentData studentData;
    /**
     * schoolStudent
     */
    private SchoolStudentData schoolStudentData;
    /**
     * examBatch
     */
    private ExamBatch examBatch;
    /**
     * examBatchClass
     */
    private ExamBatchClass examBatchClass;
    /**
     *
     */
    private ExamScoreTable examScoreTable;
    /**
     * timetableEntry
     */
    private TimetableEntry timetableEntry;
    /**
     * editMode
     */
    private String editMode;
    /**
     * employeeModel
     */
    private DataModel<EmployeeData> employeeModel;
    /**
     * parentModel
     */
    private DataModel<Parent> parentModel;
    /**
     * schoolModel
     */
    private DataModel<School> schoolModel;
    /**
     * schoolStudentModel
     */
    private DataModel<SchoolStudentData> schoolStudentModel;
    /**
     * systemRoleModel
     */
    private DataModel<SystemRole> systemRoleModel;
    /**
     * userLoginModel
     */
    private DataModel<UserLogin> userLoginModel;
    /**
     * messageModel
     */
    private DataModel<MessageData> messageModel;
    /**
     * examBatchModel
     */
    private DataModel<ExamBatch> examBatchModel;
    /**
     * examModel
     */
    private DataModel<Exam> examModel;
    /**
     * assignmentModel
     */
    private DataModel<Assignment> assignmentModel;
    /**
     * assignmentScoreModel
     */
    private DataModel<AssignmentScore> assignmentScoreModel;
    /**
     * examScoreModel
     */
    private DataModel<ExamScore> examScoreModel;
    /**
     * termExamRecordModel
     */
    private DataModel<TermExamRecord> termExamRecordModel;
    /**
     * termExamRecordDetailsModel
     */
    private DataModel<TermExamRecordDetails> termExamRecordDetailsModel;
    /**
     * ebookModel
     */
    private DataModel<Ebook> ebookModel;
    /**
     * photoAlbumModel
     */
    private DataModel<PicAlbum> picAlbumModel;
    /**
     * studentAttendanceList
     */
    private List<Attendance<SchoolStudent>> studentAttendanceList;
    /**
     * studentAttendanceTable
     */
    private StudentAttendanceTable studentAttendanceTable;
    /**
     * attendanceSummaryModel
     */
    private DataModel<AttendanceSummary> attendanceSummaryModel;
    /**
     * employeeAttendanceList
     */
    private List<Attendance<Employee>> employeeAttendanceList;
    /**
     * employeeAttendanceTable
     */
    private EmployeeAttendanceTable employeeAttendanceTable;
    /**
     * ttEntryTable
     */
    private TtEntryTable ttEntryTable;

    /**
     * default constructor
     */
    public SessionBean() {
    }

    /**
     * cleaning up by setting to null all the property of this session bean to
     * be called by a logout action
     */
    public void cleanUp() {
        this.assignment = null;
        this.assignmentModel = null;
        this.assignmentScoreModel = null;
        this.attendanceSummaryModel = null;
        this.editMode = null;
        this.employeeAttendanceList = null;
        this.employeeAttendanceList = null;
        this.employeeAttendanceTable = null;
        this.employeeData = null;
        this.employeeModel = null;
        this.exam = null;
        this.examBatch = null;
        this.examBatchClass = null;
        this.examBatchModel = null;
        this.examModel = null;
        this.examScoreModel = null;
        this.examScoreTable = null;
        this.messageModel = null;
        this.parent = null;
        this.parentModel = null;
        this.school = null;
        this.schoolModel = null;
        this.schoolStudentData = null;
        this.schoolStudentModel = null;
        this.studentAttendanceList = null;
        this.studentAttendanceTable = null;
        this.studentData = null;
        this.systemRole = null;
        this.systemRoleModel = null;
        this.termExamRecordDetailsModel = null;
        this.termExamRecordModel = null;
        this.userLogin = null;
        this.userLoginModel = null;
    }

    /**
     * @return the assignment
     */
    public Assignment getAssignment() {
        return assignment;
    }

    /**
     * @param assignment the assignment to set
     */
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    /**
     * @return the exam
     */
    public Exam getExam() {
        return exam;
    }

    /**
     * @param exam the exam to set
     */
    public void setExam(Exam exam) {
        this.exam = exam;
    }

    /**
     * @return the employeeData
     */
    public EmployeeData getEmployeeData() {
        return employeeData;
    }

    /**
     * @param employeeData the employeeData to set
     */
    public void setEmployeeData(EmployeeData employeeData) {
        this.employeeData = employeeData;
    }

    /**
     * @return the parent
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Parent parent) {
        this.parent = parent;
    }

    /**
     * @return the userLogin
     */
    public UserLogin getUserLogin() {
        return userLogin;
    }

    /**
     * @param userLogin the userLogin to set
     */
    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * @return the systemRole
     */
    public SystemRole getSystemRole() {
        return systemRole;
    }

    /**
     * @param systemRole the systemRole to set
     */
    public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }

    /**
     * @return the school
     */
    public School getSchool() {
        return school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(School school) {
        this.school = school;
    }

    /**
     * @return the studentData
     */
    public StudentData getStudentData() {
        return studentData;
    }

    /**
     * @param studentData the studentData to set
     */
    public void setStudentData(StudentData studentData) {
        this.studentData = studentData;
    }

    /**
     * @return the schoolStudentData
     */
    public SchoolStudentData getSchoolStudentData() {
        return schoolStudentData;
    }

    /**
     * @param schoolStudentData the schoolStudentData to set
     */
    public void setSchoolStudentData(SchoolStudentData schoolStudentData) {
        this.schoolStudentData = schoolStudentData;
    }

    /**
     * @return the examBatch
     */
    public ExamBatch getExamBatch() {
        return examBatch;
    }

    /**
     * @param examBatch the examBatch to set
     */
    public void setExamBatch(ExamBatch examBatch) {
        this.examBatch = examBatch;
    }

    /**
     * @return the examBatchClass
     */
    public ExamBatchClass getExamBatchClass() {
        return examBatchClass;
    }

    /**
     * @param examBatchClass the examBatchClass to set
     */
    public void setExamBatchClass(ExamBatchClass examBatchClass) {
        this.examBatchClass = examBatchClass;
    }

    /**
     * @return the editMode
     */
    public String getEditMode() {
        return editMode;
    }

    /**
     * @param editMode the editMode to set
     */
    public void setEditMode(String editMode) {
        this.editMode = editMode;
    }

    /**
     * @return the employeeModel
     */
    public DataModel<EmployeeData> getEmployeeModel() {
        return employeeModel;
    }

    /**
     * @param employeeModel the employeeModel to set
     */
    public void setEmployeeModel(DataModel<EmployeeData> employeeModel) {
        this.employeeModel = employeeModel;
    }

    /**
     * @return the parentModel
     */
    public DataModel<Parent> getParentModel() {
        return parentModel;
    }

    /**
     * @param parentModel the parentModel to set
     */
    public void setParentModel(DataModel<Parent> parentModel) {
        this.parentModel = parentModel;
    }

    /**
     * @return the schoolModel
     */
    public DataModel<School> getSchoolModel() {
        return schoolModel;
    }

    /**
     * @param schoolModel the schoolModel to set
     */
    public void setSchoolModel(DataModel<School> schoolModel) {
        this.schoolModel = schoolModel;
    }

    /**
     * @return the schoolStudentModel
     */
    public DataModel<SchoolStudentData> getSchoolStudentModel() {
        return schoolStudentModel;
    }

    /**
     * @param schoolStudentModel the schoolStudentModel to set
     */
    public void setSchoolStudentModel(DataModel<SchoolStudentData> schoolStudentModel) {
        this.schoolStudentModel = schoolStudentModel;
    }

    /**
     * @return the systemRoleModel
     */
    public DataModel<SystemRole> getSystemRoleModel() {
        return systemRoleModel;
    }

    /**
     * @param systemRoleModel the systemRoleModel to set
     */
    public void setSystemRoleModel(DataModel<SystemRole> systemRoleModel) {
        this.systemRoleModel = systemRoleModel;
    }

    /**
     * @return the userLoginModel
     */
    public DataModel<UserLogin> getUserLoginModel() {
        return userLoginModel;
    }

    /**
     * @param userLoginModel the userLoginModel to set
     */
    public void setUserLoginModel(DataModel<UserLogin> userLoginModel) {
        this.userLoginModel = userLoginModel;
    }

    /**
     * @return the messageModel
     */
    public DataModel<MessageData> getMessageModel() {
        return messageModel;
    }

    /**
     * @param messageModel the messageModel to set
     */
    public void setMessageModel(DataModel<MessageData> messageModel) {
        this.messageModel = messageModel;
    }

    /**
     * @return the examBatchModel
     */
    public DataModel<ExamBatch> getExamBatchModel() {
        return examBatchModel;
    }

    /**
     * @param examBatchModel the examBatchModel to set
     */
    public void setExamBatchModel(DataModel<ExamBatch> examBatchModel) {
        this.examBatchModel = examBatchModel;
    }

    /**
     * @return the examModel
     */
    public DataModel<Exam> getExamModel() {
        return examModel;
    }

    /**
     * @param examModel the examModel to set
     */
    public void setExamModel(DataModel<Exam> examModel) {
        this.examModel = examModel;
    }

    /**
     * @return the assignmentModel
     */
    public DataModel<Assignment> getAssignmentModel() {
        return assignmentModel;
    }

    /**
     * @param assignmentModel the assignmentModel to set
     */
    public void setAssignmentModel(DataModel<Assignment> assignmentModel) {
        this.assignmentModel = assignmentModel;
    }

    /**
     * @return the assignmentScoreModel
     */
    public DataModel<AssignmentScore> getAssignmentScoreModel() {
        return assignmentScoreModel;
    }

    /**
     * @param assignmentScoreModel the assignmentScoreModel to set
     */
    public void setAssignmentScoreModel(DataModel<AssignmentScore> assignmentScoreModel) {
        this.assignmentScoreModel = assignmentScoreModel;
    }

    /**
     * @return the examScoreModel
     */
    public DataModel<ExamScore> getExamScoreModel() {
        return examScoreModel;
    }

    /**
     * @param examScoreModel the examScoreModel to set
     */
    public void setExamScoreModel(DataModel<ExamScore> examScoreModel) {
        this.setExamScoreModel(examScoreModel);
    }

    /**
     * @return the examScoreTable
     */
    public ExamScoreTable getExamScoreTable() {
        return examScoreTable;
    }

    /**
     * @param examScoreTable the examScoreTable to set
     */
    public void setExamScoreTable(ExamScoreTable examScoreTable) {
        this.examScoreTable = examScoreTable;
    }

    /**
     * @return the termExamRecordModel
     */
    public DataModel<TermExamRecord> getTermExamRecordModel() {
        return termExamRecordModel;
    }

    /**
     * @param termExamRecordModel the termExamRecordModel to set
     */
    public void setTermExamRecordModel(DataModel<TermExamRecord> termExamRecordModel) {
        this.termExamRecordModel = termExamRecordModel;
    }

    /**
     * @return the termExamRecordDetailsModel
     */
    public DataModel<TermExamRecordDetails> getTermExamRecordDetailsModel() {
        return termExamRecordDetailsModel;
    }

    /**
     * @param termExamRecordDetailsModel the termExamRecordDetailsModel to set
     */
    public void setTermExamRecordDetailsModel(DataModel<TermExamRecordDetails> termExamRecordDetailsModel) {
        this.termExamRecordDetailsModel = termExamRecordDetailsModel;
    }

    /**
     * @return the studentAttendanceList
     */
    public List<Attendance<SchoolStudent>> getStudentAttendanceList() {
        return studentAttendanceList;
    }

    /**
     * @param studentAttendanceList the studentAttendanceList to set
     */
    public void setStudentAttendanceList(List<Attendance<SchoolStudent>> studentAttendanceList) {
        this.studentAttendanceList = studentAttendanceList;
    }

    /**
     * @return the studenAttendanceTable
     */
    public StudentAttendanceTable getStudentAttendanceTable() {
        return studentAttendanceTable;
    }

    /**
     * @param studentAttendanceTable the studentAttendanceTable to set
     */
    public void setStudentAttendanceTable(StudentAttendanceTable studentAttendanceTable) {
        this.studentAttendanceTable = studentAttendanceTable;
    }

    /**
     * @return the attendanceSummaryModel
     */
    public DataModel<AttendanceSummary> getAttendanceSummaryModel() {
        return attendanceSummaryModel;
    }

    /**
     * @param attendanceSummaryModel the attendanceSummaryModel to set
     */
    public void setAttendanceSummaryModel(
            DataModel<AttendanceSummary> attendanceSummaryModel) {
        this.attendanceSummaryModel = attendanceSummaryModel;
    }

    /**
     * @return the employeeAttendanceList
     */
    public List<Attendance<Employee>> getEmployeeAttendanceList() {
        return employeeAttendanceList;
    }

    /**
     * @param employeeAttendanceList the employeeAttendanceList to set
     */
    public void setEmployeeAttendanceList(List<Attendance<Employee>> employeeAttendanceList) {
        this.employeeAttendanceList = employeeAttendanceList;
    }

    /**
     * @return the employeeAttendanceTable
     */
    public EmployeeAttendanceTable getEmployeeAttendanceTable() {
        return employeeAttendanceTable;
    }

    /**
     * @param employeeAttendanceTable the employeeAttendanceTable to set
     */
    public void setEmployeeAttendanceTable(EmployeeAttendanceTable employeeAttendanceTable) {
        this.employeeAttendanceTable = employeeAttendanceTable;
    }

    /**
     * @return the ttEntryTable
     */
    public TtEntryTable getTtEntryTable() {
        return ttEntryTable;
    }

    /**
     * @param ttEntryTable the ttEntryTable to set
     */
    public void setTtEntryTable(TtEntryTable ttEntryTable) {
        this.ttEntryTable = ttEntryTable;
    }

    /**
     * @return the timetableEntry
     */
    public TimetableEntry getTimetableEntry() {
        return timetableEntry;
    }

    /**
     * @param timetableEntry the timetableEntry to set
     */
    public void setTimetableEntry(TimetableEntry timetableEntry) {
        this.timetableEntry = timetableEntry;
    }

    /**
     * @return the ebookModel
     */
    public DataModel<Ebook> getEbookModel() {
        return ebookModel;
    }

    /**
     * @param ebookModel the ebookModel to set
     */
    public void setEbookModel(DataModel<Ebook> ebookModel) {
        this.ebookModel = ebookModel;
    }

    /**
     * @return the picAlbumModel
     */
    public DataModel<PicAlbum> getPicAlbumModel() {
        return picAlbumModel;
    }

    /**
     * @param picAlbumModel the picAlbumModel to set
     */
    public void setPicAlbumModel(DataModel<PicAlbum> picAlbumModel) {
        this.picAlbumModel = picAlbumModel;
    }
}