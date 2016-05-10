/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.web.util.fileexport.xls;

import com.okmich.common.exception.TechnicalException;
import java.io.Serializable;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 *
 * @author Michael Enudi
 * @since Aug 17, 2013
 * @company Okmich Ltd.
 */
public abstract class BaseExportWriter implements Serializable {

    /**
     * reportTitle
     */
    private String reportTitle;
    /**
     * sheetLabel
     */
    private String sheetLabel;
    /**
     *
     */
    protected Workbook workbook;
    protected Sheet sheet;
    private CreationHelper creationHelper;

    /**
     *
     * @param reportTitle
     * @param sheetLabel
     */
    public BaseExportWriter(String reportTitle, String sheetLabel) {
        this.reportTitle = reportTitle;
        this.sheetLabel = sheetLabel;
    }

    /**
     * this is to be implemented by a concrete instance.
     *
     * This Object is implemented and passed into null null null null null null
     * null null null null null null null null null null null null null null
     * null null null null null null null null     {@link ExcelExporter#setExportWriter(com.okmich.common.web.util.fileexport.xls.BaseExportWriter, java.io.OutputStream)
     * }.
     *
     * @throws TechnicalException
     */
    protected abstract void writeObject() throws TechnicalException;

    /**
     * return a cell style to style our report header
     *
     * @return CellStyle
     */
    protected CellStyle getReportTitleStyle() {
        // Create a new font and alter it.
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 24);
        font.setFontName("Times New Roman");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // Fonts are set into a style so create a new one to use.
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(CellStyle.ALIGN_CENTER);

        return style;
    }

    /**
     * return a cell style to style our title header
     *
     * @return CellStyle
     */
    protected CellStyle getCellValuesStyle() {
        // Fonts are set into a style so create a new one to use.
        CellStyle style = workbook.createCellStyle();

        return style;
    }

    /**
     * return a cell style to style our title header
     *
     * @return CellStyle
     */
    protected CellStyle getRowHeaderStyle() {
        // Create a new font and alter it.
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Times New Roman");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // Fonts are set into a style so create a new one to use.
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        return style;
    }

    /**
     * return a cell style to plain style our title header
     *
     * @return CellStyle
     */
    protected CellStyle getRowHeaderPlainStyle() {
        // Create a new font and alter it.
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Times New Roman");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // Fonts are set into a style so create a new one to use.
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font);

        return style;
    }

    /**
     * @return the reportTitle
     */
    public String getReportTitle() {
        return reportTitle;
    }

    /**
     * @param reportTitle the reportTitle to set
     */
    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    /**
     * @return the sheetLabel
     */
    public String getSheetLabel() {
        return sheetLabel;
    }

    /**
     * @param sheetLabel the sheetLabel to set
     */
    public void setSheetLabel(String sheetLabel) {
        this.sheetLabel = sheetLabel;
    }

    /**
     * @param workbook the workbook to set
     */
    protected void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    /**
     * @param sheet the sheet to set
     */
    protected void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    /**
     * @return the creationHelper
     */
    protected CreationHelper getCreationHelper() {
        if (creationHelper == null) {
            creationHelper = this.workbook.getCreationHelper();
        }
        return creationHelper;
    }
}
