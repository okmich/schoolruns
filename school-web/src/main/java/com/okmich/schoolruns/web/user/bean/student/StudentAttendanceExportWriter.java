/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.student;

import com.okmich.common.exception.TechnicalException;
import com.okmich.common.web.util.fileexport.xls.BaseExportWriter;
import com.okmich.schoolruns.calendar.service.data.Attendance;
import com.okmich.schoolruns.calendar.service.data.StudentAttendanceTable;
import com.okmich.schoolruns.common.entity.SchoolStudent;
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
public class StudentAttendanceExportWriter extends BaseExportWriter {

    private StudentAttendanceTable studentAttendanceTable;
    private int rowIndex = 0, colIndex = 0;

    /**
     *
     * @param reportTitle
     * @param sheetLabel
     * @param _attendanceTable
     */
    public StudentAttendanceExportWriter(String reportTitle, String sheetLabel,
            StudentAttendanceTable _attendanceTable) {
        super(reportTitle, sheetLabel);
        this.studentAttendanceTable = _attendanceTable;
    }

    @Override
    protected void writeObject() throws TechnicalException {
        List<Date> dates = studentAttendanceTable.getDates();
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
        cell.setCellValue("Student ID");

        cell = row.createCell(colIndex++);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Student Name");

        cell = row.createCell(colIndex++);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Admission #");

        short dataFormat = getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy");
        for (Date date : dates) {
            cell = row.createCell(colIndex++);
            cellStyle.setDataFormat(dataFormat);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(date);
        }
        //start writing each attendance
        List<SchoolStudent> schoolStudents = studentAttendanceTable.getSchoolStudents();
        Attendance<SchoolStudent> studAttend;
        for (SchoolStudent ss : schoolStudents) {
            row = sheet.createRow(rowIndex++);
            colIndex = 0;
            //write school student Id
            cell = row.createCell(colIndex++);
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue(ss.getSchoolStudentId());
            //write student name
            cell = row.createCell(colIndex++);
            cell.setCellValue(getCreationHelper().createRichTextString(ss.getStudent().getFullname()));
            //write admission number
            cell = row.createCell(colIndex++);
            cell.setCellValue(getCreationHelper().createRichTextString(ss.getRegistrationNo()));
            for (Date date : dates) {
                cell = row.createCell(colIndex++);
                studAttend = studentAttendanceTable.getValue(ss, date);
                cell.setCellValue(getCreationHelper().createRichTextString(studAttend == null ? "" : "x"));
            }
        }
    }
}