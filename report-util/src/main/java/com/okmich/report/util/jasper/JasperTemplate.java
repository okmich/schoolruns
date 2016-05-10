package com.okmich.report.util.jasper;

import com.okmich.report.util.ExportFormat;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRResultSetDataSource;

public class JasperTemplate {

    public static void main(String[] args) {
        try {
            InputStream in = JasperTemplate.class.getResourceAsStream("state-cities-template.jasper");
//            MyTemplateGenerator test =
//                    new MyTemplateGenerator(in);
            DefaultJasperReportExporter test = new DefaultJasperReportExporter(in);
            test.setConnection(DriverManager.getConnection("jdbc:mysql://localhost:3306/schoolruns", "root", "password"));

            Map<String, Object> map = new HashMap<>();
            map.put("stateCode", "LA");

            test.setParameters(map);

            //test.setExportFormat(ExportFormat.XLSX_FORMAT);
            test.exportToStream(new FileOutputStream("jasper-report-output.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class MyTemplateGenerator extends JasperReportExportable {

        public MyTemplateGenerator(InputStream in) throws IOException {
            super(in);
        }

        @Override
        protected JRDataSource getJRDataSource() {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/schoolruns", "root", "password");
                Statement statement = connection.createStatement();

                return new JRResultSetDataSource(statement.executeQuery("SELECT city.`city_id` AS city_city_id, city.`name` AS city_name, city.`state_code` AS city_state_code, states.`state_code` AS states_state_code, states.`name` AS states_name FROM `states` states INNER JOIN `city` city ON states.`state_code` = city.`state_code` ORDER BY city.`state_code` ASC"));
            } catch (SQLException ex) {
                Logger.getLogger(JasperTemplate.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new JREmptyDataSource();
        }

        @Override
        protected Map<String, Object> getParameters() {
            Map<String, Object> map = new HashMap<>();
            map.put("stateCode", "LA");
            return map;
        }

        @Override
        protected Connection getConnection() {
            return null;
        }
    }
}