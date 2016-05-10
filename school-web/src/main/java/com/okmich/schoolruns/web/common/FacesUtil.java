/**
 *
 * @author OKALI CHIGOZIE DAMIAN.
 */
package com.okmich.schoolruns.web.common;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

public class FacesUtil {

    /**
     * APPLICATION_SCOPE - key for Application scope state
     */
    public static final int APPLICATION_SCOPE = 0;
    /**
     * SESSION_SCOPE - key for Session scope state
     */
    public static final int SESSION_SCOPE = 1;
    /**
     * SESSION_SCOPE - key for Request scope state
     */
    public static final int REQUEST_SCOPE = 2;
    /**
     * INIT_PARAMETER_KEY - key for init parameter map(web.xml for configuration
     * of init-params)
     */
    public static final int INIT_PARAMETER_KEY = 3;

    /**
     * Creates a new instance of FacesUtil
     */
    private FacesUtil() {
    }

    /**
     * Gets current FacesContext instance associated with the application
     */
    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * returns a FacesContext instance associated with the application from
     * outside the JSF environment, possibly in a servlet environment.
     */
    public static FacesContext getFacesContext(HttpServletRequest request, HttpServletResponse response) {
        // Get current FacesContext.
        FacesContext facesContext = FacesContext.getCurrentInstance();

        // Check current FacesContext.
        if (facesContext == null) {
            // Create new Lifecycle.
            LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
            Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

            // Create new FacesContext.
            FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
            facesContext = contextFactory.getFacesContext(request.getSession().getServletContext(), request, response, lifecycle);
        }

        return facesContext;
    }

    /**
     * returns the application object
     */
    public static Application getApplication() {
        return getFacesContext().getApplication();
    }

    /**
     * returns the UIViewRoot object associated with the current request
     */
    public static UIViewRoot getViewRoot() {
        return getFacesContext().getViewRoot();
    }

    /**
     * returns a ValueExpression object
     *
     * @param valueExpression - String
     * @param valueType - Class.
     */
    public static ValueExpression createValueExpression(String valueExpression, Class<?> valueType) {
        return getApplication().getExpressionFactory().createValueExpression(getFacesContext().getELContext(), valueExpression, valueType);
    }

    /**
     * returns a MethodExpression object
     *
     * @param methodExpression - String
     * @param expectedReturnType - Class.
     * @param expectedParamType - Class.
     */
    public static MethodExpression createMethodExpression(String methodExpression, Class<?> expectedReturnType, Class<?>[] expectedParamTypes) {
        return getApplication().getExpressionFactory().createMethodExpression(
                getFacesContext().getELContext(), methodExpression, expectedReturnType, expectedParamTypes);
    }

    /**
     * returns a managed bean instance using a value expression string and class
     * type.
     *
     * @param valueExpression - String
     * @param valueType - Class.
     */
    public static Object lookUpManagedBean(String valueExpression, Class<?> valueType) {
        return createValueExpression(valueExpression, valueType).getValue(getFacesContext().getELContext());
    }

    /**
     * binds up a managed bean instance to valid bean scopes using
     * valueExpression,valueType, managedBean
     *
     * @param valueExpression - String
     * @param valueType - Class
     * @param managedBean - Object.
     */
    public static void bindUpManagedBean(String valueExpression, Class<?> valueType, Object managedBean) {
        createValueExpression(valueExpression, valueType).setValue(getFacesContext().getELContext(), managedBean);
    }

    /**
     * returns a managed bean instance using a scope key string and beanName.
     *
     * @param scopeKey - int
     * @param beanName - String.
     */
    public static Object lookUpManagedBean(int scopeKey, String beanName) {
        Object beanObject = null;
        try {
            switch (scopeKey) {
                case APPLICATION_SCOPE:
                    beanObject = (Object) getExternalContext().getApplicationMap().get(beanName);
                    break;
                case SESSION_SCOPE:
                    beanObject = (Object) getExternalContext().getSessionMap().get(beanName);
                    break;
                case REQUEST_SCOPE:
                    beanObject = (Object) getExternalContext().getRequestMap().get(beanName);
                    break;
                default:
                //doNothing
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return beanObject;
    }

    /**
     * stores a managed bean instance using a scope key, beanName, beanObject.
     *
     * @param scopeKey - int
     * @param beanName - String.
     * @param managedBean - Object
     */
    public static void bindUpManagedBean(int scopeKey, String beanName, Object managedBean) {
        try {
            switch (scopeKey) {
                case APPLICATION_SCOPE:
                    getExternalContext().getApplicationMap().put(beanName, managedBean);
                    break;

                case SESSION_SCOPE:
                    getExternalContext().getSessionMap().put(beanName, managedBean);
                    break;

                case REQUEST_SCOPE:
                    getExternalContext().getRequestMap().put(beanName, managedBean);
                    break;

                default:
                //doNothing
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * returns an ExternalContext object associated with the current
     * FacesContext instance
     */
    public static ExternalContext getExternalContext() {
        return (ExternalContext) getFacesContext().getExternalContext();
    }

    /**
     * returns the ServletContext object associated with the current request.
     */
    public static ServletContext getServletContext() {
        return (ServletContext) getExternalContext().getContext();
    }

    /**
     * returns an instance of HttpServletRequest associated with the current
     * request
     */
    public static HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) getExternalContext().getRequest();
    }

    /**
     * returns the login of the user associated with the current request if
     * already authenticated or null if otherwise
     */
    public static String getRemoteUser() {
        return (String) getHttpServletRequest().getRemoteUser();
    }

    /**
     * returns the IP address of the client or proxy associated with the current
     * request
     */
    public static String getRemoteAddress() {
        return (String) getHttpServletRequest().getRemoteAddr();
    }

    /**
     * returns the fully qualified name of the client or proxy associated with
     * the current request
     */
    public static String getRemoteHost() {
        return (String) getHttpServletRequest().getRemoteHost();
    }

    /**
     * returns an instance of HttpServletResponse associated with the current
     * request
     */
    public static HttpServletResponse getHttpServletResponse() {
        return (HttpServletResponse) getExternalContext().getResponse();
    }

    /**
     * returns an instance of HttpSession object associated with the current
     * request
     */
    public static HttpSession getSession() {
        return (HttpSession) getHttpServletRequest().getSession(false);
    }

    /**
     * returns an init parameter value
     */
    public static Object getInitParameter(String name) {
        return (Object) getExternalContext().getInitParameter(name);
    }

    /**
     * returns an object from any of the application scopes et al (application,
     * session, request) based on the state type and map key. Also used for init
     * parameters(web.xml initialization parameters)
     *
     * @param stateType - int
     * @param key - String
     */
    public static Object getMapAttribute(int stateType, String key) {
        Object attributeObject = null;

        try {
            switch (stateType) {
                case APPLICATION_SCOPE:
                    attributeObject = (Object) getExternalContext().getApplicationMap().get(key);
                    break;

                case SESSION_SCOPE:
                    attributeObject = (Object) getExternalContext().getSessionMap().get(key);
                    break;

                case REQUEST_SCOPE:
                    attributeObject = (Object) getExternalContext().getRequestMap().get(key);
                    break;

                case INIT_PARAMETER_KEY:
                    attributeObject = (Object) getExternalContext().getInitParameterMap().get(key);
                    break;

                default:
                //doNothing
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return attributeObject;
    }

    /**
     * saves an object into any of the application scopes et al (application,
     * session, request) based on the state type(scope) and map key. Also used
     * for init parameters(web.xml initialization parameters)
     *
     * @param attribute - Object
     * @param stateType - int
     * @param key - String
     */
    public static void setMapAttribute(Object attribute, int stateType, String key) {
        try {
            switch (stateType) {
                case APPLICATION_SCOPE:
                    getExternalContext().getApplicationMap().put(key, attribute);
                    break;

                case SESSION_SCOPE:
                    getExternalContext().getSessionMap().put(key, attribute);
                    break;

                case REQUEST_SCOPE:
                    getExternalContext().getRequestMap().put(key, attribute);
                    break;

                case INIT_PARAMETER_KEY:
                    getExternalContext().getInitParameterMap().put(key, attribute);
                    break;

                default:
                //doNothing
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * returns a String value as a parameter object from a jsp page. Usage
     * scenarios :
     * <ul>
     * <li>
     * <code>blahblah.jsf?paramValue=1</code>
     * </li>
     * <li>
     * <code>
     * <h:commandLink action="success"
     * actionListener="#{someBean.ELExpressionEvent}">
     * <f:param name="paramValue" value="1"/>
     * <h:outputText value="Click Me"/>
     * </h:commandLink>
     * </code>
     * </li>
     * <li>
     * <code>
     * <h:outputLink value="blahblah.jsf">
     * <f:param name="paramValue" value="1"/>
     * <h:outputText value="Click Me"/>
     * </h:outputLink>
     * </code>
     * </li>
     * </ul>
     *
     * @param name - String
     */
    public static String getRequestParameter(String name) {
        return (String) getExternalContext().getRequestParameterMap().get(name);
    }

    /**
     * returns a request scope object with the associated name
     *
     * @param name - String
     * @ return Object
     */
    public static Object getRequestScopeObject(String name) {
        return (Object) getExternalContext().getRequestMap().get(name);
    }

    /**
     *
     * returns a String object as an attribute (f:attribute) value from these
     * UICommand components that supports ActionEvent Note: Accepts only string
     * as value, el expression not allowed. Usage Example :
     * <li>
     * <code>
     * <h:commandButton actionListener="#{someBean.performActionListener}">
     * <f:attribute name="username" value="myname"/>
     * </h:commandButton>
     * </code>
     * </li>
     *
     * @param event - javax.faces.event.ActionEvent
     * @param name - String
     */
    public static String getComponentAttribute(ActionEvent event, String name) {
        return (String) getComponentAttribute(event.getComponent(), name);
    }

    /**
     *
     * returns a String object as an attribute (f:attribute) value from all
     * UIComponents that supports ValueChangeEvent Note: Accepts only string as
     * value, el expression not allowed. Usage Example :
     * <li>
     * <code>
     * <h:inputText
     * valueChangeListener="#{someBean.performValueChangeListener}">
     * <f:attribute name="username" value="myname"/>
     * </h:inputText>
     * </code>
     * </li>
     *
     * @param event - javax.faces.event.ValueChangeEvent
     * @param name - String
     *
     */
    public static String getComponentAttribute(ValueChangeEvent event, String name) {
        return (String) getComponentAttribute(event.getComponent(), name);
    }

    public static String getComponentAttribute(UIComponent uiComponent, String name) {
        return (String) uiComponent.getAttributes().get(name);
    }
}
