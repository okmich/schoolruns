/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.filtlet.security;

import com.okmich.schoolruns.web.common.ThemeUtil;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.io.IOException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Michael
 */
public class ThemeFilter implements Filter {

    private Map<String, String> themeMap;

    public ThemeFilter() {
        themeMap = ThemeUtil.getThemeMap();
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hrequest = (HttpServletRequest) request;
        SchoolSessionBean schoolSessionBean = (SchoolSessionBean) hrequest
                .getSession(true).getAttribute("schoolSessionBean");
        if (schoolSessionBean != null
                && !schoolSessionBean.getWebTheme().isEmpty()) {
            chain.doFilter(request, response);
        } else {
            String themeOrSchool = "sunny";
            String requestURI = hrequest.getServletPath() == null
                    || hrequest.getServletPath().isEmpty() ? "/none-specific"
                    : hrequest.getServletPath();
            themeOrSchool = requestURI.split("/").length > 0 ? requestURI
                    .split("/")[1] : "";
            if (!themeMap.containsKey(themeOrSchool)) { // incase the user types
                // something very
                // different
                themeOrSchool = "sunny";
            }
            // create the UserPrefBean and set the webThem
            schoolSessionBean = new SchoolSessionBean();
            schoolSessionBean.setWebTheme(themeOrSchool);
            hrequest.getSession(true).setAttribute("schoolSessionBean",
                    schoolSessionBean);
            // redirect to context path
            ((HttpServletResponse) response).sendRedirect(hrequest
                    .getContextPath());
        }
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
