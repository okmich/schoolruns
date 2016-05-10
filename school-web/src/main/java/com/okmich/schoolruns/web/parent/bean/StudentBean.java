/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.parent.bean;

import com.okmich.common.exception.TechnicalException;
import com.okmich.schoolruns.calendar.service.AttendanceService;
import com.okmich.schoolruns.common.entity.StudentAward;
import com.okmich.schoolruns.common.entity.StudentClub;
import com.okmich.schoolruns.common.entity.StudentDiscipline;
import com.okmich.schoolruns.common.entity.StudentSport;
import com.okmich.schoolruns.common.service.JdbcService;
import com.okmich.schoolruns.finance.service.FinanceService;
import com.okmich.schoolruns.school.service.SchoolStudentService;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.security.service.SecurityService;
import com.okmich.schoolruns.student.service.StudentService;
import com.okmich.schoolruns.student.service.data.StudentData;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.ResultSetDataModel;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Michael
 */
@ManagedBean
@SessionScoped
public class StudentBean extends _BaseBean {

    @ManagedProperty("#{attendanceService}")
    private AttendanceService attendanceService;
    @ManagedProperty("#{financeService}")
    private FinanceService financeService;
    @ManagedProperty("#{jdbcService}")
    private JdbcService jdbcService;
    @ManagedProperty("#{schoolStudentService}")
    private SchoolStudentService schoolStudentService;
    @ManagedProperty("#{studentService}")
    private StudentService studentService;
    @ManagedProperty("#{securityService}")
    private SecurityService securityService;
    @ManagedProperty("#{parentSessionBean}")
    private ParentSessionBean parentSessionBean;
    private DataModel schoolStudentModel;
    private DataModel<StudentAward> studentAwardModel;
    private DataModel<StudentClub> studentClubModel;
    private DataModel<StudentDiscipline> studentDisciplineModel;
    private DataModel<StudentSport> studentSportModel;
    private DataModel studentFinanceModel;
    private DataModel studentTermExamModel;
    private StudentData studentData;
    private SchoolStudentData schoolStudentData;
    private StudentAward studentAward;
    private StudentClub studentClub;
    private StudentDiscipline studentDiscipline;
    private StudentSport studentSport;
    /**
     * QUERY_SCHOOL_STUDENT_RECORDS
     */
    private static final String QUERY_SCHOOL_STUDENT_RECORDS = "SELECT v.school_student_id, "
            + "v.student_id, v.fullname, v.school_name, v.school_id, v.registration_no, "
            + "v.grade_level, v.class_code, v.class_description, v.prefect_flag, v.prefect_type, "
            + "v.admission_date, v.academic_year  FROM v_school_student v "
            + "where v.student_id = ${STUDENT_ID} order by v.school_year_id desc, "
            + "v.school_class_id desc";
    /**
     * STUDENT_TAG
     */
    private static final String STUDENT_TAG = "${STUDENT_ID}";

    /**
     * Creates a new instance of StudentBean
     */
    public StudentBean() {
    }

    /**
     * @param attendanceService the attendanceService to set
     */
    public void setAttendanceService(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * @param financeService the financeService to set
     */
    public void setFinanceService(FinanceService financeService) {
        this.financeService = financeService;
    }

    /**
     * @param jdbcService1 the jdbcService to set
     */
    public void setJdbcService(JdbcService jdbcService1) {
        this.jdbcService = jdbcService1;
    }

    /**
     * @param schoolStudentService the schoolStudentService to set
     */
    public void setSchoolStudentService(SchoolStudentService schoolStudentService) {
        this.schoolStudentService = schoolStudentService;
    }

    /**
     * @param studentService the studentService to set
     */
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * @param securityService the securityService to set
     */
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * @param parentSessionBean the parentSessionBean to set
     */
    public void setParentSessionBean(ParentSessionBean parentSessionBean) {
        this.parentSessionBean = parentSessionBean;
    }

    /**
     * any cleanup to clear session variable
     */
    private void cleanUp() {
    }

    /**
     * return the user to the parent home screen
     *
     * @return
     */
    public String goHome() {
        setSchoolStudentModel(null);
        setStudentData(null);
        cleanUp();
        return "/parent/console";
    }

    /**
     * return to the current ward's home page where the most current general
     * details of the students are available
     *
     * @return String
     */
    public String goStudentHome() {
        if (this.studentData == null) {
            String studentIdString = FacesUtil.getRequestParameter("studentId");
            Integer studentId = Integer.parseInt(studentIdString);
            this.studentData = studentService.findStudent(studentId);
        }
        return "/parent/student/index";
    }

    public String findSchoolStudentDetails() {
        String ssId = FacesUtil.getRequestParameter("schoolStudentId");
        Integer _ssId = Integer.parseInt(ssId, 10);

        this.schoolStudentData = schoolStudentService.findSchoolStudent(
                _ssId);
        return "";
    }

    /**
     * return to the current ward's biodata page
     *
     * @return String
     */
    public String findBiodata() {
        return "/parent/student/biodata";
    }

    /**
     * return to the current ward's attendance summary search page
     *
     * @return String
     */
    public String findAttendanceHistory() {
        return "/parent/student/attendance/index";
    }

    /**
     * return to the current ward's academic history search page
     *
     * @return String
     */
    public String findAssignmentHistory() {
        return "/parent/student/assignments/index";
    }

    /**
     * return to the current ward's academic history search page
     *
     * @return String
     */
    public String findTermExamHistory() {
        return "/parent/student/exams/index";
    }

    /**
     * return to the current ward's clubs search page
     *
     * @return String
     */
    public String findStudentClubs() {
        List<StudentClub> studentClubs = schoolStudentService.findStudentClubs(
                this.studentData.getStudentId());
        setStudentClubModel(new ListDataModel<>(studentClubs));

        return "/parent/student/clubsearch";
    }

    /**
     * return to the current ward's disciplinary issues search page
     *
     * @return String
     */
    public String findDisciplinaryIssues() {
        List<StudentDiscipline> studentDisciplines = schoolStudentService.findStudentDisciplines(
                this.studentData.getStudentId());
        setStudentDisciplineModel(new ListDataModel<>(studentDisciplines));
        return "/parent/student/discipline";
    }

    /**
     * return to the current ward's awards history search page
     *
     * @return String
     */
    public String findAwards() {
        List<StudentAward> studentAwards = studentService.findStudentAwards(
                this.studentData.getStudentId());
        setStudentAwardModel(new ListDataModel<>(studentAwards));
        return "/parent/student/awards";
    }

    /**
     * return to the current ward's sport activity history search page
     *
     * @return String
     */
    public String findSportActivities() {
        List<StudentSport> studentSports = schoolStudentService.findStudentSports(
                this.studentData.getStudentId());
        setStudentSportModel(new ListDataModel<>(studentSports));
        return "/parent/student/sportssearch";
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
    public void setSchoolStudentData(SchoolStudentData schoolStudentData1) {
        this.schoolStudentData = schoolStudentData1;
    }

    /**
     * @return the schoolStudentModel
     */
    public DataModel getSchoolStudentModel() {
        //set the school student model if null
        if (this.schoolStudentModel == null) {
            try {
                //load the schoolstudentDataList
                String query = QUERY_SCHOOL_STUDENT_RECORDS.replace(STUDENT_TAG,
                        "" + studentData.getStudentId());

                CachedRowSet cachedRowSet = jdbcService.executeQuery(query);
                //set the data model
                this.schoolStudentModel = new ResultSetDataModel(cachedRowSet);
                if (cachedRowSet.size() > 0) {
                    Integer schoolStudentId = cachedRowSet.getInt("school_student_id");
                    this.schoolStudentData = schoolStudentService.findSchoolStudent(
                            schoolStudentId);
                    cachedRowSet.first();
                }
            } catch (TechnicalException | SQLException ex) {
                processException(ex);
            }
        }
        return schoolStudentModel;
    }

    /**
     * @param schoolStudentModel the schoolStudentModel to set
     */
    public void setSchoolStudentModel(DataModel<SchoolStudentData> schoolStudentModel) {
        this.schoolStudentModel = schoolStudentModel;
    }

    /**
     * @return the studentAwardModel
     */
    public DataModel<StudentAward> getStudentAwardModel() {
        return studentAwardModel;
    }

    /**
     * @param studentAwardModel the studentAwardModel to set
     */
    public void setStudentAwardModel(DataModel<StudentAward> studentAwardModel) {
        this.studentAwardModel = studentAwardModel;
    }

    /**
     * @return the studentClubModel
     */
    public DataModel<StudentClub> getStudentClubModel() {
        return studentClubModel;
    }

    /**
     * @param studentClubModel the studentClubModel to set
     */
    public void setStudentClubModel(DataModel<StudentClub> studentClubModel) {
        this.studentClubModel = studentClubModel;
    }

    /**
     * @return the studentDisciplineModel
     */
    public DataModel<StudentDiscipline> getStudentDisciplineModel() {
        return studentDisciplineModel;
    }

    /**
     * @param studentDisciplineModel the studentDisciplineModel to set
     */
    public void setStudentDisciplineModel(DataModel<StudentDiscipline> studentDisciplineModel) {
        this.studentDisciplineModel = studentDisciplineModel;
    }

    /**
     * @return the studentSportModel
     */
    public DataModel<StudentSport> getStudentSportModel() {
        return studentSportModel;
    }

    /**
     * @param studentSportModel the studentSportModel to set
     */
    public void setStudentSportModel(DataModel<StudentSport> studentSportModel) {
        this.studentSportModel = studentSportModel;
    }

    /**
     * @return the studentAward
     */
    public StudentAward getStudentAward() {
        return studentAward;
    }

    /**
     * @param studentAward the studentAward to set
     */
    public void setStudentAward(StudentAward studentAward) {
        this.studentAward = studentAward;
    }

    /**
     * @return the studentClub
     */
    public StudentClub getStudentClub() {
        return studentClub;
    }

    /**
     * @param studentClub the studentClub to set
     */
    public void setStudentClub(StudentClub studentClub) {
        this.studentClub = studentClub;
    }

    /**
     * @return the studentDiscipline
     */
    public StudentDiscipline getStudentDiscipline() {
        return studentDiscipline;
    }

    /**
     * @param studentDiscipline the studentDiscipline to set
     */
    public void setStudentDiscipline(StudentDiscipline studentDiscipline) {
        this.studentDiscipline = studentDiscipline;
    }

    /**
     * @return the studentSport
     */
    public StudentSport getStudentSport() {
        return studentSport;
    }

    /**
     * @param studentSport the studentSport to set
     */
    public void setStudentSport(StudentSport studentSport) {
        this.studentSport = studentSport;
    }

    /**
     * @return the studentFinanceModel
     */
    public DataModel getStudentFinanceModel() {
        return studentFinanceModel;
    }

    /**
     * @param studentFinanceModel the studentFinanceModel to set
     */
    public void setStudentFinanceModel(DataModel studentFinanceModel) {
        this.studentFinanceModel = studentFinanceModel;
    }

    /**
     * @return the studentTermExamModel
     */
    public DataModel getStudentTermExamModel() {
        return studentTermExamModel;
    }

    /**
     * @param studentTermExamModel the studentTermExamModel to set
     */
    public void setStudentTermExamModel(DataModel studentTermExamModel) {
        this.studentTermExamModel = studentTermExamModel;
    }
}