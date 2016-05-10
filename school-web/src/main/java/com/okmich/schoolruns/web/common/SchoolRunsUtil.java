/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common;

import com.okmich.schoolruns.calendar.service.SchoolCalendarService;
import com.okmich.schoolruns.employee.service.EmployeeService;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.school.service.SchoolStudentService;
import com.okmich.schoolruns.web.parent.bean.ParentSessionBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Michael
 * @since Aug 11, 2013
 * @company Okmich Ltd.
 */
public final class SchoolRunsUtil {

    private SchoolRunsUtil() {
    }

    /**
     *
     *
     * @param cookies
     * @return Integer
     */
    public static Integer getSchoolIdFromCookies(Cookie[] cookies) {
        if (cookies == null) {
            return 0;
        }
        for (Cookie cookie : cookies) {
            if ("schoolId".equals(cookie.getName())) {
                try {
                    return Integer.valueOf(cookie.getValue());
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    /**
     * we are manually creating a schoolsessionbean and passing all the
     * supposed-to-be automatically injected reference into the bean
     *
     * @param request
     * @return SchoolSessionBean
     */
    public static SchoolSessionBean createNewSchoolSessionBean(HttpServletRequest request) {
        SchoolSessionBean bean = new SchoolSessionBean();

        ApplicationContext appContext = WebApplicationContextUtils
                .getWebApplicationContext(request.getServletContext());

        bean.setEmployeeService(appContext.getBean(EmployeeService.class));
        bean.setSchoolCalendarService(appContext.getBean(SchoolCalendarService.class));
        bean.setSchoolService(appContext.getBean(SchoolService.class));

        return bean;
    }

    /**
     * we are manually creating a parentsessionbean and passing all the
     * supposed-to-be automatically injected reference into the bean
     *
     * @param request
     * @return SchoolSessionBean
     */
    public static ParentSessionBean createNewParentSessionBean(HttpServletRequest request) {
        ParentSessionBean bean = new ParentSessionBean();

        ApplicationContext appContext = WebApplicationContextUtils
                .getWebApplicationContext(request.getServletContext());

        bean.setSchoolStudentService(appContext.getBean(SchoolStudentService.class));

        return bean;
    }
}
