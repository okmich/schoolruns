package com.okmich.common;

import com.okmich.common.util.StringUtil;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Michael Enudi
 * @compay Leadway Assurance Company Ltd.
 * @since April 18, 2008, 7:26 AM
 */
public class BaseData implements Serializable {

    protected String modifiedBy;
    protected Date modifiedTime;
    protected String status;
    /**
     * serialVersionUID
     */
    public static final long serialVersionUID = 3487495895819393L;

    /**
     * Creates a new instance of BaseData
     */
    public BaseData() {
    }

    /**
     * @return the modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @param modifiedBy the modifiedBy to set
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return the modifiedTime
     */
    public Date getModifiedTime() {
        return modifiedTime;
    }

    /**
     * @param modifiedTime the modifiedTime to set
     */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
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

    @Override
    public String toString() {
        return StringUtil.toString(this);
    }
}
