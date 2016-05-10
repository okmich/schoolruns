/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.report.util.jasper;

import com.okmich.report.util.ExportFormat;
import com.okmich.report.util.Exportable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author Michael
 * @since Sep 14, 2013
 * @company Okmich Ltd.
 */
public abstract class JasperReportExportable implements Exportable {

    /**
     * inputStream
     */
    private InputStream inputStream;
    /**
     * reportExportOption
     */
    private ExportFormat exportFormatOption;
    /**
     *
     */
    private String outputFileName;

    /**
     * Creates a new instance of JasperReportExportable
     */
    public JasperReportExportable() {
    }

    /**
     *
     * @param in - input stream
     */
    public JasperReportExportable(InputStream in) {
        this.inputStream = in;
    }

    /**
     *
     * @param inputStream
     */
    @Override
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * @return the exportFormatOption
     */
    public ExportFormat getExportFormat() {
        return exportFormatOption == null
                ? ExportFormat.PDF_FORMAT : exportFormatOption;
    }

    /**
     * @param exportFormatOption the exportFormatOption to set
     */
    public void setExportFormat(ExportFormat exportFormatOption) {
        this.exportFormatOption = exportFormatOption;
    }

    /**
     * @return the outputFileName
     */
    public String getOutputFileName() {
        return outputFileName;
    }

    /**
     * @param outputFileName the outputFileName to set
     */
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    /**
     *
     * @param outputStream
     * @throws Exception
     */
    @Override
    public void exportToStream(OutputStream outputStream) throws Exception {
        if (this.inputStream == null) {
            throw new IllegalArgumentException("no template file stream set");
        }
        //generate the jasperPrint
        JasperPrint jasperPrint = null;
        try {
            if (getConnection() != null) {
                try {
                    jasperPrint = JasperFillManager.fillReport(this.inputStream,
                            getParameters(), getConnection());
                } finally {
                    getConnection().close();
                }
            } else if (getJRDataSource() != null) {
                jasperPrint = JasperFillManager.fillReport(this.inputStream,
                        getParameters(), getJRDataSource());
            } else {
                throw new IllegalStateException("no_data_source_specified");
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        //export the generated jasper print to stream
        exportReportToStream(jasperPrint, outputStream);
    }

    /**
     * hook method for which all implementation of this class must override. The
     * principle is that this class will provide database connection needed for
     * the jasperprint to be generated
     *
     * @return Connection
     */
    protected abstract Connection getConnection();

    /**
     * hook method for which all implementation of this class must override. The
     * principle is that this class will provide datasource for the document to
     * produce
     *
     * @return JRDataSource
     */
    protected abstract JRDataSource getJRDataSource();

    /**
     * hook method for which all implementation of this class must override. The
     * principle is that this class will provide a map of parameters for the
     * report.
     *
     * @return Map<String, Object>
     */
    protected abstract Map<String, Object> getParameters();

    /**
     * handles the export of report in any format
     *
     * @param jasperPrint
     * @param outputStream
     * @param response
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.io.IOException
     * @throws java.lang.Exception
     */
    private void exportReportToStream(JasperPrint jasperPrint, OutputStream outputStream)
            throws JRException, IOException, Exception {
        switch (getExportFormat()) {
            case HTML_FORMAT:
                exportToStreamAsHTML(jasperPrint, outputStream);
                break;
            case CSV_FORMAT:
                exportToStreamAsCSV(jasperPrint, outputStream);
            case XLS_FORMAT:
                exportToStreamAsXLS(jasperPrint, outputStream);
            case XLSX_FORMAT:
                exportToStreamAsXlsx(jasperPrint, outputStream);
            case PDF_FORMAT:
            default:
                exportToStreamAsPDF(jasperPrint, outputStream);
        }
    }

    /**
     * base method to export report as a PDF document
     *
     * @param jasperPrint
     * @param outputStream
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.io.IOException
     */
    private void exportToStreamAsPDF(JasperPrint jasperPrint, OutputStream outputStream)
            throws JRException, IOException {

        JRPdfExporter exporter = new JRPdfExporter(DefaultJasperReportsContext.getInstance());
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, getOutputFileName());

        writeExport(exporter, outputStream);
    }

    /**
     * base method to export the report as an Excel document
     *
     * @param jasperPrint
     * @param outputStream
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.io.IOException
     */
    private void exportToStreamAsXLS(JasperPrint jasperPrint, OutputStream outputStream)
            throws JRException, IOException {
        JRXlsExporter exporter = new JRXlsExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, getOutputFileName());
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

        writeExport(exporter, outputStream);
    }

    /**
     * base method to export the report as an Excel document *.xlsx format
     *
     * @param jasperPrint
     * @param outputStream
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.io.IOException
     */
    private void exportToStreamAsXlsx(JasperPrint jasperPrint, OutputStream outputStream)
            throws JRException, IOException {
        JRXlsxExporter exporter = new JRXlsxExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, getOutputFileName());
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
        exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

        writeExport(exporter, outputStream);
    }

    /**
     * base method to export the report as an html document
     *
     * @param jasperPrint
     * @param outputStream
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.io.IOException
     */
    private void exportToStreamAsHTML(JasperPrint jasperPrint, OutputStream outputStream)
            throws JRException, IOException {
        JRHtmlExporter exporter = new JRHtmlExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, getOutputFileName());

        writeExport(exporter, outputStream);
    }

    /**
     * base method to export the report as an html document
     *
     * @param jasperPrint
     * @param outputStream
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.io.IOException
     */
    private void exportToStreamAsCSV(JasperPrint jasperPrint, OutputStream outputStream)
            throws JRException, IOException {
        JRCsvExporter exporter = new JRCsvExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, getOutputFileName());

        writeExport(exporter, outputStream);
    }

    /**
     *
     *
     * @param exporter
     * @param fbos
     * @param outputStream
     * @throws JRException
     * @throws IOException
     */
    private void writeExport(JRExporter exporter, OutputStream outputStream)
            throws JRException, IOException {
        exporter.exportReport();

        if (outputStream != null) {
            outputStream.flush();
        }
    }
}