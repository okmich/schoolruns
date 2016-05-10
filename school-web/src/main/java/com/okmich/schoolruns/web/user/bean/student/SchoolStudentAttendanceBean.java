/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.student;

import com.okmich.common.util.DateRange;
import com.okmich.common.web.util.fileexport.xls.ExcelExporter;
import com.okmich.common.web.util.fileimport.ExcelFileImportData;
import com.okmich.schoolruns.calendar.service.AttendanceService;
import com.okmich.schoolruns.calendar.service.DimensionConst;
import com.okmich.schoolruns.calendar.service.PeriodConst;
import com.okmich.schoolruns.calendar.service.data.Attendance;
import com.okmich.schoolruns.calendar.service.data.AttendanceSummary;
import com.okmich.schoolruns.calendar.service.data.StudentAttendanceTable;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolTerm;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.ExcelFileImportBean;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class SchoolStudentAttendanceBean extends _BaseBean {

    @ManagedProperty("#{attendanceService}")
    private AttendanceService attendanceService;
    @ManagedProperty("#{excelFileImportBean}")
    private ExcelFileImportBean excelFileImportBean;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private int schoolClassId;
    private Date startDate;
    private Date endDate;
    private boolean yearFlag;

    /**
     * Creates a new instance of SchoolStudentAttendanceBean
     */
    public SchoolStudentAttendanceBean() {
    }

    /**
     * @param attendanceService the attendanceService to set
     */
    public void setAttendanceService(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * @param excelFileImportBean1 the excelFileImportBean to set
     */
    public void setExcelFileImportBean(ExcelFileImportBean excelFileImportBean1) {
        this.excelFileImportBean = excelFileImportBean1;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @return the schoolClassId
     */
    public int getSchoolClassId() {
        return schoolClassId;
    }

    /**
     * @param schoolClassId the schoolClassId to set
     */
    public void setSchoolClassId(int schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the yearFlag
     */
    public boolean isYearFlag() {
        return yearFlag;
    }

    /**
     * @param yearFlag the yearFlag to set
     */
    public void setYearFlag(boolean yearFlag) {
        this.yearFlag = yearFlag;
    }

    /**
     *
     * @return
     */
    public String saveAttendanceList() {
        List<Attendance<SchoolStudent>> studAttendance =
                sessionBean.getStudentAttendanceList();
        if (studAttendance.isEmpty()) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    "ERROR_EMPTY_LIST"));
            return "";
        }
        SchoolTerm schoolTerm = new SchoolTerm(schoolSessionBean.getSchoolCalendarData().getSchoolTermId());
        schoolTerm.setModifiedBy(userLoginSessionBean.getUserLogin().getModifiedBy());
        try {
            attendanceService.saveClassAttendance(schoolTerm,
                    sessionBean.getStudentAttendanceList().get(0).getAttendDate(),
                    sessionBean.getStudentAttendanceList());

            return "/schooluser/attendance/studentattendance";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String saveAttendanceTable() {
        StudentAttendanceTable studAttendanceTable =
                sessionBean.getStudentAttendanceTable();
        if (studAttendanceTable.isEmpty()) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    "ERROR_EMPTY_LIST"));
            return "";
        }
        SchoolTerm schoolTerm = new SchoolTerm(schoolSessionBean.getSchoolCalendarData().getSchoolTermId());
        schoolTerm.setModifiedBy(userLoginSessionBean.getUserLogin().getModifiedBy());
        try {
            attendanceService.saveClassAttendance(schoolTerm,
                    studAttendanceTable);
            return "/schooluser/attendance/studentattendance";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findAttendanceByDate() {
        if (getStartDate().after(new Date())) {
            FacesUtil.getFacesContext().addMessage("",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR_DATE_LATER_THAN_TODAY", ""));
            return null;
        }
        //find student attendance by date, term and class
        List<Attendance<SchoolStudent>> studAttendance = attendanceService.findStudentAttendances(
                schoolSessionBean.getSchoolCalendarData().getSchoolTermId(),
                schoolClassId, getStartDate());
        if (studAttendance != null) {
            sessionBean.setStudentAttendanceList(studAttendance);
            return "/schooluser/attendance/studentattendancedateentry";
        }
        return null;
    }

    /**
     *
     *
     * @return
     */
    public String findAttendanceByDateRange() {
        if (getEndDate() == null) {
            FacesUtil.getFacesContext().addMessage("",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    "NO_END_DATE_SPECIFIED"));
            return null;
        }
        StudentAttendanceTable studAttendanceTable = getStudentAttendanceTable();
        if (studAttendanceTable != null) {
            sessionBean.setStudentAttendanceTable(studAttendanceTable);
            return "/schooluser/attendance/studentattendancedaterangeentry";
        }
        return null;
    }

    public String findAttendanceSummary() {
        List<AttendanceSummary> attendanceSummarys;
        DimensionConst dimensionConst = DimensionConst.SCHOOL;
        Integer dimensionConstId = schoolSessionBean.getSchool().getSchoolId();
        if (getSchoolClassId() != 0) {
            dimensionConst = DimensionConst.CLASS;
            dimensionConstId = getSchoolClassId();
        }
        PeriodConst periodConst = PeriodConst.TERM;
        int periodInt = schoolSessionBean.getSchoolCalendarData().getSchoolTermId();
        if (yearFlag) {
            periodConst = PeriodConst.YEAR;
            periodInt = schoolSessionBean.getSchoolCalendarData().getSchoolYearId();
        }
        attendanceSummarys = attendanceService.findStudentSummaryAttendance(
                dimensionConst, dimensionConstId, periodConst, periodInt);
        sessionBean.setAttendanceSummaryModel(new ListDataModel(attendanceSummarys));
        //return to the page
        return "/schooluser/attendance/studentattendancesummary";
    }

    public String downloadAttendanceByDateRange() {
        StudentAttendanceTable studAttendanceTable = getStudentAttendanceTable();
        //ge the class in question from the school session "repository"
        SchoolClass schoolClass = schoolSessionBean.getSchoolClasses().get(
                schoolSessionBean.getSchoolClasses().indexOf(new SchoolClass(schoolClassId)));
        //get the student attendance writer
        StudentAttendanceExportWriter writer = new StudentAttendanceExportWriter("Attendance Sheet for Class "
                + schoolClass.getCode(),
                "rollcall", studAttendanceTable);
        try {
            DateFormat df = new SimpleDateFormat("ddMMyyyy");
            HttpServletResponse response = FacesUtil.getHttpServletResponse();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "filename=rollcall-"
                    + schoolClass.getCode().toLowerCase() + "-"
                    + df.format(getStartDate()) + "-" + df.format(endDate));
            //export the excel document to the client
            ExcelExporter.getInstance().setExportWriter(writer, response.getOutputStream());

            FacesUtil.getFacesContext().responseComplete();
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String uploadAttendanceSheet() {
        ExcelFileImportData data = excelFileImportBean.getExcelFileUploadData();
        StudentAttendanceTable table = new StudentAttendanceTable();
        try {
            List<SchoolStudent> schoolStudents = new ArrayList<>();
            SchoolStudent schoolStudent;
            for (List<String> strings : data.getData()) {
                schoolStudent = new SchoolStudent(Double.valueOf(strings.get(0)).intValue());
                schoolStudent.setRegistrationNo(strings.get(2));

                schoolStudents.add(schoolStudent);
            }
            table.setSchoolStudents(schoolStudents);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            int cols = data.getColumnNames().size();
            int rows = data.getData().size();
            Attendance<SchoolStudent> studAttend;
            Date date = null;
            for (int j = 3; j < cols; j++) {
                try {
                    date = sdf.parse(data.getColumnNames().get(j));
                    table.addColumn(date);
                } catch (ParseException ex) {
                    throw new Exception("ERROR_PROCESSING_TABLE_HEADER");
                }
                for (int i = 0; i < rows; i++) {
                    studAttend = new Attendance<>();
                    //set the studAttend object
                    studAttend.setAttendDate(date);
                    studAttend.setAttendanceId(null);
                    studAttend.setModifiedBy("dev");
                    studAttend.setModifiedTime(new Date());
                    String presentMark = data.getData().get(i).get(j);
                    studAttend.setPresent(presentMark == null || presentMark.isEmpty() ? false : true);
                    studAttend.setSchoolTermId(2);
                    studAttend.setType(schoolStudents.get(i));

                    table.addAttendances(studAttend);
                }
            }
            //call the service
            SchoolTerm schoolTerm = new SchoolTerm(
                    schoolSessionBean.getSchoolCalendarData().getSchoolTermId());
            schoolTerm.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            attendanceService.saveUploadedClassAttendance(schoolTerm, schoolClassId, table);
            //reset the grade level id previously set for class
            schoolSessionBean.setGradeLevelId(null);
            return "/schooluser/attendance/studentattendance";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String goHome() {
        sessionBean.setStudentAttendanceList(null);
        sessionBean.setStudentAttendanceTable(null);
        sessionBean.setAttendanceSummaryModel(null);
        sessionBean.setEditMode(null);

        return "/schooluser/attendance/studentattendance";
    }

    /**
     * meant to set the editMode for the sessionBean as the user goes to the
     * attendanceupload page
     *
     * @return
     */
    public String gotoUpload() {
        sessionBean.setEditMode("STUDENT");
        return "/schooluser/attendance/attendanceupload";
    }

    /**
     *
     * @return
     */
    public DataModel<AttendanceSummary> getStudentAttendanceSummary() {
        List<AttendanceSummary> attendSummary = attendanceService.findStudentSummaryAttendance(
                DimensionConst.SCHOOL_STUDENT,
                sessionBean.getSchoolStudentData().getSchoolStudentId(),
                new DateRange(schoolSessionBean.getSchoolCalendarData().getTermStartDate(),
                schoolSessionBean.getSchoolCalendarData().getTermClosingDate()));

        return new ListDataModel<>(attendSummary);
    }

    /**
     *
     *
     * @param schoolClassId
     * @param dateRange
     * @return
     */
    private StudentAttendanceTable getStudentAttendanceTable() {
        //create a data range
        DateRange dateRange = new DateRange(getStartDate(), getEndDate(),
                DateRange.IterationPolicy.valueOf(
                schoolSessionBean.getSchoolPref().getAvailDays()));
        return attendanceService.findStudentsAttendances(
                schoolSessionBean.getSchoolCalendarData().getSchoolTermId(),
                schoolClassId, dateRange);
    }
}