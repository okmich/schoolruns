/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.staff;

import com.okmich.report.util.jasper.DefaultJasperReportExporter;
import com.okmich.schoolruns.common.service.JdbcService;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.BaseReportBean;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
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
public class EmployeeReportBean extends BaseReportBean {

    @ManagedProperty("#{dataSource}")
    private DataSource dataSource;
    @ManagedProperty("#{jdbcService}")
    private JdbcService jdbcService;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private static final String EMP_GENDER_DIST_QUERY = "";
    private static final String EMP_POSITION_DIST_QUERY = "";
    private static final String EMP_CAT_DIST_QUERY = "";
    private static final String EMP_TYPE_DIST_QUERY = "";

    /**
     * Creates a new instance of EmployeeReportBean
     */
    public EmployeeReportBean() {
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
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     *
     * @return
     */
    public String runEmployeeDataPage() {
        Map<String, Object> properties = loadSchoolInfo();
        //add the schoolstudent in session
        try {
            DefaultJasperReportExporter exporter =
                    new DefaultJasperReportExporter(
                    DocumentTemplateLocator.staffDataPageTemplate.value());
            exporter.setParameters(properties);
            exporter.setConnection(dataSource.getConnection());
            exporter.setOutputFileName(sessionBean.getEmployeeData().getFullname()
                    + "-" + System.currentTimeMillis() + ".pdf");
            exporter.setExportFormat(getRequestedFormat());
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
    public String runStaffRegister() {
        Map<String, Object> properties = loadSchoolInfo();
        try {
            DefaultJasperReportExporter exporter =
                    new DefaultJasperReportExporter(
                    DocumentTemplateLocator.staffListingTemplate.value());
            exporter.setParameters(properties);
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

//    /**
//     *
//     * @return
//     */
//    public String runStaffClassRegister() {
//        Map<String, Object> properties = loadSchoolInfo();
//        try {
//            DefaultJasperReportExporter exporter =
//                    new DefaultJasperReportExporter(
//                    DocumentTemplateLocator.staff.value());
//            exporter.setParameters(properties);
//            exporter.setConnection(dataSource.getConnection());
//            HttpServletResponse response = FacesUtil.getHttpServletResponse();
//            response.setContentType("application/pdf");
//            exporter.exportToStream(response.getOutputStream());
//            FacesUtil.getFacesContext().responseComplete();
//        } catch (Exception ex) {
//            processException(ex);
//        }
//        return "";
//    }
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
