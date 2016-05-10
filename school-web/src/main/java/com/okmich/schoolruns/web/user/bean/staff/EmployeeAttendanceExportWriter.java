/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.staff;

import com.okmich.common.exception.TechnicalException;
import com.okmich.common.web.util.fileexport.xls.BaseExportWriter;
import com.okmich.schoolruns.calendar.service.data.Attendance;
import com.okmich.schoolruns.calendar.service.data.EmployeeAttendanceTable;
import com.okmich.schoolruns.common.entity.Employee;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author Michael
 * @since Aug 17, 2013
 * @company Okmich Ltd.
 */
public class EmployeeAttendanceExportWriter extends BaseExportWriter {

    private EmployeeAttendanceTable employeeAttendanceTable;
    private int rowIndex = 0, colIndex = 0;

    /**
     *
     * @param reportTitle
     * @param sheetLabel
     * @param _attendanceTable
     */
    public EmployeeAttendanceExportWriter(String reportTitle, String sheetLabel,
            EmployeeAttendanceTable _attendanceTable) {
        super(reportTitle, sheetLabel);
        this.employeeAttendanceTable = _attendanceTable;
    }

    @Override
    protected void writeObject() throws TechnicalException {
        List<Date> dates = employeeAttendanceTable.getDates();
        final int COLS = dates.size() + 3;

        Row row = sheet.createRow(rowIndex++);
        Cell cell = row.createCell(colIndex);

        //the report title  - REPORT TITLE
        cell.setCellStyle(getReportTitleStyle());
        cell.setCellValue(getReportTitle());
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                COLS - 1 //last column  (0-based)
                ));

        //the report title  - REPORT TITLE
        row = sheet.createRow(rowIndex++);
        colIndex = 0;
        CellStyle cellStyle = getRowHeaderStyle();
        cell = row.createCell(colIndex++);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Employee ID");

        cell = row.createCell(colIndex++);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Employee Name");

        cell = row.createCell(colIndex++);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Staff #");

        for (Date date : dates) {
            cell = row.createCell(colIndex++);
            cell.setCellStyle(cellStyle);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(date);
        }
        //start writing each attendance
        List<Employee> employees = employeeAttendanceTable.getEmployees();
        Attendance<Employee> empAttend;
        for (Employee emp : employees) {
            row = sheet.createRow(rowIndex++);
            colIndex = 0;
            //write employee Id
            cell = row.createCell(colIndex++);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(emp.getEmployeeId());
            //write employees name
            cell = row.createCell(colIndex++);
            cell.setCellValue(getCreationHelper().createRichTextString(emp.getFullname()));
            //write staff number
            cell = row.createCell(colIndex++);
            cell.setCellValue(getCreationHelper().createRichTextString(emp.getStaffNumber()));
            for (Date date : dates) {
                cell = row.createCell(colIndex++);
                empAttend = employeeAttendanceTable.getValue(emp, date);
                cell.setCellValue(getCreationHelper().createRichTextString(empAttend == null ? "" : "x"));
            }
        }
    }
}