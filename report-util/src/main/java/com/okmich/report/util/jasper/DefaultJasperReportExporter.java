/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.report.util.jasper;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author Michael
 * @since Oct 10, 2013
 * @company Okmich Ltd.
 */
public class DefaultJasperReportExporter extends JasperReportExportable {

    /**
     * connection
     */
    private Connection connection;
    /**
     * jRDataSource
     */
    private JRDataSource jRDataSource;
    /**
     * parameters
     */
    private Map<String, Object> parameters;

    /**
     *
     * @param templateStream
     */
    public DefaultJasperReportExporter(InputStream templateStream) {
        super.setInputStream(templateStream);
    }

    /**
     * @return the connection
     */
    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * @param connection the connection to set
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * @return the jRDataSource
     */
    @Override
    public JRDataSource getJRDataSource() {
        return jRDataSource;
    }

    /**
     * @param jRDataSource the jRDataSource to set
     */
    public void setJRDataSource(JRDataSource jRDataSource) {
        this.jRDataSource = jRDataSource;
    }

    /**
     * @return the parameters
     */
    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
