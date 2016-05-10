/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.bean;

import java.io.InputStream;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Michael
 */
@ManagedBean
@ViewScoped
public class FileDownloadBean implements Serializable {

    /**
     * Creates a new instance of FileDownloadBean
     */
    public FileDownloadBean() {
    }

    public StreamedContent getSampleUploadClassesFileFormat() {
        InputStream stream = this.getClass().
                getResourceAsStream(
                "/templates/sample_excel_sheets/sample_upload_file_format_classes.xlsx");
        return new DefaultStreamedContent(stream,
                "application/vnd.ms-excel", "sample_upload_file_format_classes.xlsx");
    }

    public StreamedContent getSampleUploadStaffsFileFormat() {
        InputStream stream = this.getClass().
                getResourceAsStream(
                "/templates/sample_excel_sheets/sample_upload_file_format_staff.xlsx");
        return new DefaultStreamedContent(stream,
                "application/vnd.ms-excel", "sample_upload_file_format_staff.xlsx");
    }

    public StreamedContent getSampleUploadStudentsFileFormat() {
        InputStream stream = this.getClass().
                getResourceAsStream(
                "/templates/sample_excel_sheets/sample_upload_file_format_students .xlsx");
        return new DefaultStreamedContent(stream,
                "application/vnd.ms-excel", "sample_upload_file_format_students .xlsx");
    }

    public StreamedContent getSampleUploadSubjectsFileFormat() {
        InputStream stream = this.getClass().
                getResourceAsStream(
                "/templates/sample_excel_sheets/sample_upload_file_format_subjects .xlsx");
        return new DefaultStreamedContent(stream,
                "application/vnd.ms-excel", "sample_upload_file_format_subjects .xlsx");
    }
}
