/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.filtlet.security;

import com.okmich.schoolruns.web.common.SchoolRunsUtil;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Michael
 */
@WebServlet(name = "SchoolUrlServlet", urlPatterns = {"*.sch"})
public class SchoolUrlServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String servletPath = request.getServletPath();
        String sId = servletPath.substring(servletPath.lastIndexOf("/") + 1, servletPath.indexOf(".sch"));

        //get the path and then the school id
        Integer schlId;
        try {
            schlId = Integer.valueOf(sId);
        } catch (Exception e) {
            schlId = 0;
        }
        //fetch the school pref as well as school info
        SchoolSessionBean schoolSessionBean = (SchoolSessionBean) request.getSession(true).
                getAttribute("schoolSessionBean");
        if (schoolSessionBean == null || schoolSessionBean.getAppTitle() == null
                || schoolSessionBean.getSchool() == null) {
            schoolSessionBean = SchoolRunsUtil.createNewSchoolSessionBean(request);
        }
        try {
            schoolSessionBean.init(schlId);
            request.getSession(true).setAttribute("schoolSessionBean", schoolSessionBean);
            //set the school id as cookie
            Cookie cookie = new Cookie("schoolId", String.valueOf(schlId));
            cookie.setMaxAge(2592000); //30 days value  of seconds
            response.addCookie(cookie);
        } catch (Exception ex) {
            Logger.getLogger(SchoolUrlServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        //redirect to login.xhtml
        response.sendRedirect(request.getContextPath() + "/index.xhtml");
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
