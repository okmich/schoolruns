/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.filtlet.security;

import com.okmich.schoolruns.web.common.SchoolRunsUtil;
import com.okmich.schoolruns.web.parent.bean.ParentSessionBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
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
@WebFilter(filterName = "ParentFilter", urlPatterns = {"/parent/*"},
dispatcherTypes = {DispatcherType.REQUEST})
public class ParentFilter implements Filter {

    private FilterConfig filterConfig = null;
    private static final String PARENT_URI = "/parent/";
    private static final String PARENT_INDEX_URI = "/parent/index.xhtml";

    public ParentFilter() {
    }

    /**
     *
     * @return
     */
    private boolean isGlobalUrl(String uri) {
        //note that here we exclude none xhtml pages or resources
        if (PARENT_URI.equals(uri) || PARENT_INDEX_URI.equals(uri)
                || uri.endsWith(PARENT_INDEX_URI) || uri.endsWith(PARENT_URI)
                || uri.endsWith("/parent")) {
            return true;
        }
        return false;
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
        //cast to HttpServletRequest
        HttpServletRequest hrequest = (HttpServletRequest) request;
        String requestURI = hrequest.getRequestURI();
        if (!isGlobalUrl(requestURI)) {

            //next we need to check if the parentSessionBean is set asa valid session
            ParentSessionBean parentSessionBean = (ParentSessionBean) hrequest.getSession(true).
                    getAttribute("parentSessionBean");
            if (parentSessionBean == null || parentSessionBean.getUserLogin() == null
                    || parentSessionBean.getUserLogin().getUserLoginId() == null) {

                ((HttpServletResponse) response).sendRedirect(hrequest.getContextPath() + PARENT_URI);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
}
