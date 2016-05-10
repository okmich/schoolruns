/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.web.util.fileimport;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author m-enudi
 */
public final class ExcelFileImportUtil extends FileImportUtil {

    private int rowStartIndex;

    /**
     * hidden constructor
     */
    private ExcelFileImportUtil() {
    }

    /**
     * returns an instance of this class
     *
     * @return ExcelFileImportUtil - an instance of this class
     */
    public static ExcelFileImportUtil getInstance() {
        return new ExcelFileImportUtil();
    }

    /**
     *
     *
     * @param inputStream
     * @return ExcelFileImportData
     */
    public ExcelFileImportData extractContent(InputStream inputStream) {
        ExcelFileImportData fileUploadData = new ExcelFileImportData();

        List<List<String>> contentContainer = new ArrayList<>();
        List<String> columns = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheetAt(0);
            int columnCount
                    = sheet.getRow(getRowStartIndex()).getLastCellNum();

            for (int i = 0; i < columnCount; i++) {
                columns.add(extractCellContentAsString(sheet.getRow(getRowStartIndex()).getCell(i)));
            }

            List<String> rows;
            Cell cell;
            int lastRow = sheet.getLastRowNum();
            Row row;
            for (int i = getRowStartIndex(); i <= lastRow; i++) {
                rows = new ArrayList<>();
                row = sheet.getRow(i);
                for (int j = 0; j < columnCount; j++) {
                    cell = row.getCell(j, Row.CREATE_NULL_AS_BLANK);
                    rows.add(extractCellContentAsString(cell));
                }
                contentContainer.add(rows);
            }
            //remove the first row
            contentContainer.remove(0);
        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(ExcelFileImportUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        fileUploadData.setColumnNames(columns);
        fileUploadData.setData(contentContainer);

        return fileUploadData;
    }

    /**
     *
     *
     * @param cell
     * @return String - the String representation of the content of the cell.
     */
    private String extractCellContentAsString(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    return date != null ? new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue())
                            : null;
                } else {
                    Number number = cell.getNumericCellValue();
                    return number != null ? String.valueOf(number) : null;
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() ? "true" : "false";
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case Cell.CELL_TYPE_BLANK:
                return "";
            default:
                return "";
        }
    }

    /**
     * @return the rowStartIndex
     */
    public int getRowStartIndex() {
        return rowStartIndex;
    }

    /**
     * @param rowStartIndex the rowStartIndex to set
     */
    public void setRowStartIndex(int rowStartIndex) {
        this.rowStartIndex = rowStartIndex;
    }
}
