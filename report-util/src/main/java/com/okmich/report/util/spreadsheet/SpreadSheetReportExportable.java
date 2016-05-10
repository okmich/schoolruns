/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.report.util.spreadsheet;

import com.okmich.report.util.Exportable;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Michael
 * @since Sep 14, 2013
 * @company Okmich Ltd.
 */
public class SpreadSheetReportExportable implements Exportable {

    public SpreadSheetReportExportable() {
    }

    @Override
    public void exportToStream(OutputStream outputStream) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setInputStream(InputStream inputStream) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
