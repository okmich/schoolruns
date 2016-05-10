/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.staff;

import com.okmich.common.util.DateRange;
import com.okmich.common.web.util.fileexport.xls.ExcelExporter;
import com.okmich.common.web.util.fileimport.ExcelFileImportData;
import com.okmich.schoolruns.calendar.service.AttendanceService;
import com.okmich.schoolruns.calendar.service.DimensionConst;
import com.okmich.schoolruns.calendar.service.PeriodConst;
import com.okmich.schoolruns.calendar.service.data.Attendance;
import com.okmich.schoolruns.calendar.service.data.AttendanceSummary;
import com.okmich.schoolruns.calendar.service.data.EmployeeAttendanceTable;
import com.okmich.schoolruns.common.entity.Employee;
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
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class StaffAttendanceBean extends _BaseBean {

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
    private Date startDate;
    private Date endDate;
    private boolean yearFlag;

    /**
     * Creates a new instance of SchoolStudentAttendanceBean
     */
    public StaffAttendanceBean() {
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
        List<Attendance<Employee>> empAttendance =
                sessionBean.getEmployeeAttendanceList();
        if (empAttendance.isEmpty()) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    "ERROR_EMPTY_LIST"));
            return "";
        }
        SchoolTerm schoolTerm = new SchoolTerm(schoolSessionBean.getSchoolCalendarData().getSchoolTermId());
        schoolTerm.setModifiedBy(userLoginSessionBean.getUserLogin().getModifiedBy());
        try {
            attendanceService.saveEmployeeAttendance(schoolTerm,
                    empAttendance.get(0).getAttendDate(),
                    empAttendance);
            return "/schooluser/attendance/staffattendance";
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
        EmployeeAttendanceTable empAttendanceTable =
                sessionBean.getEmployeeAttendanceTable();
        if (empAttendanceTable.isEmpty()) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                    "ERROR_EMPTY_LIST"));
            return "";
        }
        SchoolTerm schoolTerm = new SchoolTerm(schoolSessionBean.getSchoolCalendarData().getSchoolTermId());
        schoolTerm.setModifiedBy(userLoginSessionBean.getUserLogin().getModifiedBy());
        try {
            attendanceService.saveEmployeeAttendance(schoolTerm,
                    empAttendanceTable);
            return "/schooluser/attendance/staffattendance";
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
        List<Attendance<Employee>> empAttendance = attendanceService.findEmployeeAttendances(
                schoolSessionBean.getSchoolCalendarData().getSchoolTermId(),
                getStartDate());
        if (empAttendance != null) {
            sessionBean.setEmployeeAttendanceList(empAttendance);
            return "/schooluser/attendance/staffattendancedateentry";
        }
        return null;
    }

    /**
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
        EmployeeAttendanceTable empAttendanceTable = getEmployeeAttendanceTable();
        if (empAttendanceTable != null) {
            sessionBean.setEmployeeAttendanceTable(empAttendanceTable);
            return "/schooluser/attendance/staffattendancedaterangeentry";
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String findAttendanceSummary() {
        List<AttendanceSummary> attendanceSummarys;
        Integer dimensionConstId = schoolSessionBean.getSchool().getSchoolId();
        PeriodConst periodConst = PeriodConst.TERM;
        int periodInt = schoolSessionBean.getSchoolCalendarData().getSchoolTermId();
        if (yearFlag) {
            periodConst = PeriodConst.YEAR;
            periodInt = schoolSessionBean.getSchoolCalendarData().getSchoolYearId();
        }
        attendanceSummarys = attendanceService.findEmployeeSummaryAttendance(
                DimensionConst.SCHOOL, dimensionConstId, periodConst, periodInt);
        sessionBean.setAttendanceSummaryModel(new ListDataModel(attendanceSummarys));
        //return to the page
        return "/schooluser/attendance/staffattendancesummary";
    }

    /**
     *
     * @return
     */
    public String downloadAttendanceByDateRange() {
        EmployeeAttendanceTable empAttendanceTable = getEmployeeAttendanceTable();
        //get the student attendance writer
        StaffAttendanceExportWriter writer = new StaffAttendanceExportWriter("Attendance Sheet for All Staff ",
                "rollcall", empAttendanceTable);
        try {
            DateFormat df = new SimpleDateFormat("ddMMyyyy");
            HttpServletResponse response = FacesUtil.getHttpServletResponse();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "filename=rollcall-staff-"
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
        EmployeeAttendanceTable table = new EmployeeAttendanceTable();
        try {
            List<Employee> employees = new ArrayList<>();
            Employee employee;
            for (List<String> strings : data.getData()) {
                employee = new Employee(Double.valueOf(strings.get(0)).intValue());
                employee.setStaffNumber(strings.get(2));

                employees.add(employee);
            }
            table.setEmployees(employees);
            //List<Date> dates = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            //table.setDates(dates);
            int cols = data.getColumnNames().size();
            int rows = data.getData().size();
            Attendance<Employee> empAttend;
            Date date = null;
            for (int j = 3; j < cols; j++) {
                try {
                    date = sdf.parse(data.getColumnNames().get(j));
                    table.addColumn(date);
                } catch (ParseException ex) {
                    throw new Exception("ERROR_PROCESSING_TABLE_HEADER");
                }
                for (int i = 0; i < rows; i++) {
                    empAttend = new Attendance<>();
                    //set the empAttend object
                    empAttend.setAttendDate(date);
                    empAttend.setAttendanceId(null);
                    empAttend.setModifiedBy("dev");
                    empAttend.setModifiedTime(new Date());
                    String presentMark = data.getData().get(i).get(j);
                    empAttend.setPresent(presentMark == null || presentMark.isEmpty() ? false : true);
                    empAttend.setSchoolTermId(2);
                    empAttend.setType(employees.get(i));

                    table.addAttendances(empAttend);
                }
            }
            return "/schooluser/attendance/staffattendance";
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
        sessionBean.setEmployeeAttendanceList(null);
        sessionBean.setEmployeeAttendanceTable(null);
        sessionBean.setAttendanceSummaryModel(null);
        sessionBean.setEditMode(null);

        return "/schooluser/attendance/staffattendance";
    }

    /**
     * meant to set the editMode for the sessionBean as the user goes to the
     * attendanceupload page
     *
     * @return
     */
    public String gotoUpload() {
        sessionBean.setEditMode("STAFF");
        return "/schooluser/attendance/attendanceupload";
    }

    /**
     *
     *
     * @param schoolClassId
     * @param dateRange
     * @return
     */
    private EmployeeAttendanceTable getEmployeeAttendanceTable() {
        //create a data range
        DateRange dateRange = new DateRange(getStartDate(), getEndDate(),
                DateRange.IterationPolicy.valueOf(
                schoolSessionBean.getSchoolPref().getAvailDays()));
        return attendanceService.findEmployeesAttendances(
                schoolSessionBean.getSchoolCalendarData().getSchoolTermId(),
                dateRange);
    }
}