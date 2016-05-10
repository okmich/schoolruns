/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.report.util.jasper.DefaultJasperReportExporter;
import com.okmich.schoolruns.common.service.JdbcService;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.BaseReportBean;
import com.okmich.schoolruns.web.common.template.DocumentTemplateLocator;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class FinanceReportBean extends BaseReportBean {

    @ManagedProperty("#{dataSource}")
    private DataSource dataSource;
    @ManagedProperty("#{jdbcService}")
    private JdbcService jdbcService;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;

    /**
     * Creates a new instance of FinanceReportBean
     */
    public FinanceReportBean() {
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @param jdbcService the jdbcService to set
     */
    public void setJdbcService(JdbcService jdbcService) {
        this.jdbcService = jdbcService;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @param financeSessionBean the financeSessionBean to set
     */
    public void setFinanceSessionBean(FinanceSessionBean financeSessionBean) {
        this.financeSessionBean = financeSessionBean;
    }

    /**
     *
     * @return
     */
    public String printReceipt() {
        Map<String, Object> properties = loadSchoolInfo();
        //add the schoolstudent in session
        properties.put("receiptId", financeSessionBean.getReceipt().getReceiptId());
        try {
            DefaultJasperReportExporter exporter =
                    new DefaultJasperReportExporter(
                    DocumentTemplateLocator.receiptTemplate.value());
            exporter.setParameters(properties);
            exporter.setOutputFileName(System.currentTimeMillis() + ".pdf");
            exporter.setConnection(dataSource.getConnection());
            HttpServletResponse response = FacesUtil.getHttpServletResponse();
            response.setContentType("application/pdf");
            exporter.exportToStream(response.getOutputStream());
            FacesUtil.getFacesContext().responseComplete();
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    private Map<String, Object> loadSchoolInfo() {
        Map<String, Object> properties = getSchoolTitleParameters(
                jdbcService.executeQuery(
                getSchoolTitleQuery(
                schoolSessionBean.getSchool().getSchoolId())));

        return properties;
    }
}
