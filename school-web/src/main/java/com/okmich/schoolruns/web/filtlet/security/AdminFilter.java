/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.filtlet.security;

import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Michael
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = {"/admin/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class AdminFilter implements Filter {

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    private static final String INDEX_PAGE = "/index.xhtml";

    public AdminFilter() {
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
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest hrequest = (HttpServletRequest) request;
        UserLoginSessionBean userLoginSessionBean = (UserLoginSessionBean) hrequest.getSession(true).
                getAttribute("userLoginSessionBean");
        //verify that the user is still online or still have an active session
        if (userLoginSessionBean == null
                || userLoginSessionBean.getUserLogin() == null
                || userLoginSessionBean.getUserLogin().getUserLoginId() == null) {
            String indexUrl = INDEX_PAGE;
            ((HttpServletResponse) response).sendRedirect(hrequest.getContextPath() + indexUrl);
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * Init method for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
    }
}
