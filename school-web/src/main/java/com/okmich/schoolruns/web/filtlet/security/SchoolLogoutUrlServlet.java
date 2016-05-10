/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.filtlet.security;

import com.okmich.schoolruns.web.common.SchoolRunsUtil;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Michael
 */
@WebServlet(name = "SchoolLogoutUrlServlet", urlPatterns = {"/logout"})
public class SchoolLogoutUrlServlet extends HttpServlet {

    private static final String INDEX_PAGE = "/index.xhtml";

    /**
     *
     */
    public SchoolLogoutUrlServlet() {
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String indexUrl = INDEX_PAGE;
        SchoolSessionBean schoolSessionBean = (SchoolSessionBean) request.getSession(true).
                getAttribute("schoolSessionBean");
        Integer schoolId;
        if (schoolSessionBean == null || schoolSessionBean.getSchool() == null
                || schoolSessionBean.getSchool().getSchoolId() == null) {
            schoolSessionBean = SchoolRunsUtil.createNewSchoolSessionBean(request);
            try {
                //try picking up the schId from the cookie value
                schoolId = SchoolRunsUtil.getSchoolIdFromCookies(request.getCookies());
                if (schoolId != 0) { //admin users will always return 0
                    schoolSessionBean.init(schoolId);
                    //create the new url
                    indexUrl = new StringBuilder("/").append(schoolId).append(".sch").toString();
                }
            } catch (Exception ex) {
                Logger.getLogger(SchoolUserFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            schoolId = schoolSessionBean.getSchool().getSchoolId();
            indexUrl = new StringBuilder("/").append(schoolId).append(".sch").toString();
        }
        response.sendRedirect(request.getContextPath() + indexUrl);
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}