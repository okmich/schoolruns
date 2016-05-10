/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Michael
 */
public abstract class BaseExcelExporter implements Serializable {

    protected String valueType;
    protected DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    protected DecimalFormat numberFormat = new DecimalFormat("#,##0");
    protected DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    protected Workbook workbook;
    protected Sheet worksheet;
    private String _fileName;
    private CreationHelper creationHelper;
    private CellStyle dateCellStyle;
    private CellStyle decimalCellStyle;
    private CellStyle digitCellStyle;

    protected BaseExcelExporter(String sheetName) {
        createWorkbookwithSheet(sheetName);
    }

    protected BaseExcelExporter(String sheetName, String fileName) {
        this(sheetName);
        this._fileName = fileName;
    }

    /**
     *
     *
     * @return an empty String which signifies a successful execution
     * @throws Exception
     */
    public String runExport() throws Exception {
        //insert headers to the excel sheet
        insertHeaderRow();
        //call the client's implementation of the loadExportableData();
        loadExportableData();

        HttpServletResponse response = FacesUtil.getHttpServletResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "filename="
                + (this._fileName == null ? System.currentTimeMillis() : this._fileName)
                + ".xlsx");
        try {
            this.workbook.write(response.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(BaseExcelExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesUtil.getFacesContext().responseComplete();

        return "";
    }

    /**
     * @return the dateCellStyle
     */
    public CellStyle getDateCellStyle() {
        if (this.dateCellStyle == null) {
            this.dateCellStyle = this.workbook.createCellStyle();
            this.dateCellStyle.setDataFormat(
                    getCreationHelper().createDataFormat().getFormat("dd/MM/yyyy"));
        }
        return dateCellStyle;
    }

    /**
     * @return the decimalCellStyle
     */
    public CellStyle getDecimalCellStyle() {
        if (this.decimalCellStyle == null) {
            this.decimalCellStyle = this.workbook.createCellStyle();
            this.decimalCellStyle.setDataFormat(
                    getCreationHelper().createDataFormat().getFormat("#,##0.00"));
        }
        return decimalCellStyle;
    }

    /**
     * @return the digitCellStyle
     */
    public CellStyle getDigitCellStyle() {
        if (this.digitCellStyle == null) {
            this.digitCellStyle = this.workbook.createCellStyle();
            this.digitCellStyle.setDataFormat(
                    getCreationHelper().createDataFormat().getFormat("#,##0"));
        }
        return digitCellStyle;
    }

    /**
     *
     * @param isBoldFont
     * @return
     */
    protected CellStyle createHeaderRowCellStyle() {
        CellStyle cellStyle = this.workbook.createCellStyle();
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setFillBackgroundColor(HSSFColor.GREY_50_PERCENT.index);
        cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        Font font = this.workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        //set the font
        cellStyle.setFont(font);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        return cellStyle;
    }

    /**
     *
     *
     * @param cell
     * @param bigValue
     */
    protected void inputValueToCell(Cell cell, BigDecimal bigValue) {
        inputValueToCell(cell, bigValue.doubleValue());
    }

    /**
     *
     *
     * @param cell
     * @param date
     */
    protected void inputValueToCell(Cell cell, Date date) {
        if (date == null) {
            inputValueToCell(cell, "");
            return;
        }
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellStyle(getDateCellStyle());
        cell.setCellValue(date);
    }

    /**
     *
     *
     * @param cell
     * @param dblValue
     */
    protected void inputValueToCell(Cell cell, Double dblValue) {
        if (dblValue == null) {
            inputValueToCell(cell, "");
            return;
        }
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellStyle(getDecimalCellStyle());
        cell.setCellValue(dblValue);
    }

    /**
     *
     *
     * @param cell
     * @param lngValue
     */
    protected void inputValueToCell(Cell cell, Long lngValue) {
        if (lngValue == null) {
            inputValueToCell(cell, "");
            return;
        }
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellStyle(getDigitCellStyle());
        cell.setCellValue(lngValue);
    }

    /**
     *
     * @param cell
     * @param value
     */
    protected void inputValueToCell(Cell cell, String value) {
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(value == null ? "" : value);
    }

    private CreationHelper getCreationHelper() {
        if (this.creationHelper == null) {
            this.creationHelper = this.workbook.getCreationHelper();
        }
        return this.creationHelper;
    }

    /**
     *
     *
     * @throws Exception
     */
    protected abstract void insertHeaderRow() throws Exception;

    /**
     *
     *
     */
    protected abstract void loadExportableData() throws Exception;

    /**
     *
     * @param sheetName
     * @return
     */
    private void createWorkbookwithSheet(String sheetName) {
        Workbook wb = new HSSFWorkbook();
        this.workbook = wb;
        this.worksheet = workbook.createSheet(sheetName);
    }
}