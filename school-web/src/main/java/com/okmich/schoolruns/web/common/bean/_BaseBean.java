/**
 *
 */
package com.okmich.schoolruns.web.common.bean;

import com.okmich.common.util.StringUtil;
import com.okmich.schoolruns.web.common.FacesUtil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;

/**
 * @author Michael
 *
 */
public abstract class _BaseBean implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private String editMode;
    private String status;
    /**
     * CREATE
     */
    protected final static String CREATE = "CREATE";
    /**
     * EDIT
     */
    protected final static String EDIT = "EDIT";
    /**
     * DELETE
     */
    protected final static String DELETE = "DELETE";
    /**
     * VIEW
     */
    protected final static String VIEW = "VIEW";

    /**
     *
     */
    public _BaseBean() {
    }

    /**
     * @return the editMode
     */
    public String getEditMode() {
        return editMode;
    }

    /**
     * @param editMode the editMode to set
     */
    public void setEditMode(String editMode) {
        this.editMode = editMode;
    }

    /**
     *
     * @param ex
     */
    protected void processException(Exception ex) {
        String errMsg = StringUtil.getNestedErrorMessage(ex);
        if (!errMsg.startsWith("ERROR")) {
            errMsg = "ERROR_CONTACT_ADMINISTRATOR";
        }
        FacesUtil.getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
                errMsg));
        Logger.getLogger(this.getClass().getName()).log(
                Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
