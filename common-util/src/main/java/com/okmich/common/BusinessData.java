package com.okmich.common;

/**
 *
 * @author Michael Enudi
 * @compay Leadway Assurance Company Ltd.
 * @since April 18, 2008, 7:13 AM
 */
public abstract class BusinessData extends BaseData {

    private Short version;

    /**
     * Creates a new instance of BusinessData
     */
    public BusinessData() {
    }

    /**
     * @return the version
     */
    public Short getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Short version) {
        this.version = version;
    }
}
