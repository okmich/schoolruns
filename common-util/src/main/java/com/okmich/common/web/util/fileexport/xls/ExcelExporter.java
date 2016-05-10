/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.web.util.fileexport.xls;

import java.io.OutputStream;
import java.io.Serializable;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Michael
 * @since Aug 17, 2013
 * @company Okmich Ltd.
 */
public class ExcelExporter implements Serializable {

    /**
     * _workbook
     */
    private Workbook _workbook;
    /**
     * _sheet
     */
    private Sheet _sheet;

    /**
     * default exporter
     */
    public ExcelExporter() {
    }

    /**
     *
     *
     * @return ExcelExporter
     */
    public static ExcelExporter getInstance() {
        return new ExcelExporter();
    }

    /**
     *
     *
     * @param exportWriter - the BaseExportWriter implementation
     * @param outputStream - the stream this export will be written to
     */
    public void setExportWriter(BaseExportWriter exportWriter, OutputStream outputStream)
            throws Exception {
        _workbook = new XSSFWorkbook();
        exportWriter.setWorkbook(_workbook);
        //add the worksheet
        _sheet = _workbook.createSheet(exportWriter.getSheetLabel());
        exportWriter.setSheet(_sheet);
        //call the exportWriter
        exportWriter.writeObject();
        //write the workbook to otput stream
        _workbook.write(outputStream);

        outputStream.flush();
        outputStream.close();
    }
}
