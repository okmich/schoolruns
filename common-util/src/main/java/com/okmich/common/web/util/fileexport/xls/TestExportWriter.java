/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.web.util.fileexport.xls;

import com.okmich.common.exception.TechnicalException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 *
 * @author Michael
 * @since Aug 17, 2013
 * @company Okmich Ltd.
 */
public class TestExportWriter extends BaseExportWriter {

    public TestExportWriter(String reportTitle, String sheetLabel) {
        super(reportTitle, sheetLabel);
    }

    @Override
    protected void writeObject() throws TechnicalException {
        Row row = sheet.createRow((short) 0);
        Cell cell = row.createCell((short) 0);

        cell.setCellStyle(getReportTitleStyle());
        cell.setCellValue("This is a test of merging");

        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                6 //last column  (0-based)
                ));
        //row
        row = sheet.createRow((short) 1);
        CellStyle cellStyle = getRowHeaderStyle();
        for (int i = 0; i <= 6; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("Title Exam " + i);
        }
    }

    public static void main(String[] args) {
        TestExportWriter writer = new TestExportWriter("Testing", "testing");
        try {
            ExcelExporter.getInstance().setExportWriter(writer, new FileOutputStream("testing.xlsx"));
        } catch (Exception ex) {
            Logger.getLogger(TestExportWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
