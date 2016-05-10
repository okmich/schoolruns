/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common;

import com.okmich.schoolruns.common.entity.ExamScore;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.school.service.data.ExamScoreTable;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Michael
 */
public class ClassExamScoresExporter extends BaseExcelExporter {

    private int _rowPointer;
    private Row row;
    private Cell cell;
    private ExamScoreTable _examScoreTable;
    private int fixColumns = 2;

    /**
     *
     *
     * @param sheetName
     * @param examScoreTable
     */
    protected ClassExamScoresExporter(String sheetName, ExamScoreTable examScoreTable) {
        super(sheetName);
        this._examScoreTable = examScoreTable;
    }

    /**
     *
     *
     * @param sheetName
     * @param fileName
     * @param examScoreTable
     */
    protected ClassExamScoresExporter(String sheetName, String fileName, ExamScoreTable examScoreTable) {
        super(sheetName, fileName);
        this._examScoreTable = examScoreTable;
    }

    /**
     *
     *
     * @param sheetName
     * @param fileName
     * @param examScoreTable
     * @return
     */
    public static synchronized ClassExamScoresExporter getInstance(
            String sheetName, String fileName, ExamScoreTable examScoreTable) {
        return new ClassExamScoresExporter(sheetName, fileName, examScoreTable);
    }

    /**
     *
     *
     * @param sheetName
     * @param examScoreTable
     * @return
     */
    public static synchronized ClassExamScoresExporter getInstance(
            String sheetName, ExamScoreTable examScoreTable) {
        return new ClassExamScoresExporter(sheetName, examScoreTable);
    }

    @Override
    public void loadExportableData() throws Exception {

        int p = 0;
        //Sheet sheet = createWorkbookwithSheet("Naira Sales"); will be called with the constructor of this class is called

        for (SchoolStudent schoolStudent : this._examScoreTable.getRowIndexes()) {
            p = fixColumns;
            row = this.worksheet.createRow(this._rowPointer++);
            //name cell
            cell = row.createCell(0);
            inputValueToCell(cell, schoolStudent.getStudent().getFullname());

            //code cell cell
            cell = row.createCell(1);
            inputValueToCell(cell, schoolStudent.getRegistrationNo());

            ExamScore examScore;
            for (SchoolSubject schoolSubject : this._examScoreTable.getColumnIndexes()) {
                examScore = _examScoreTable.getValue(schoolStudent, schoolSubject);
                cell = row.createCell(p++);
                inputValueToCell(cell, Long.valueOf(examScore.getScore()));

            }
        }
    }

    @Override
    protected void insertHeaderRow() throws Exception {
        row = this.worksheet.createRow(this._rowPointer++);
        List<SchoolSubject> list = new ArrayList<>(_examScoreTable.getColumnIndexes());
        for (int j = fixColumns; j < list.size() + fixColumns; j++) {
            cell = row.createCell(j);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellStyle(createHeaderRowCellStyle());
            inputValueToCell(cell, list.get(j).getSubjectCode());
        }
    }
}
