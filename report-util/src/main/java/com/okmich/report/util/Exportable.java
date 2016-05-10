/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.report.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 *
 * @author Michael
 * @since Sep 14, 2013
 * @company Okmich Ltd.
 */
public interface Exportable extends Serializable {

    /**
     *
     *
     * @param outputStream
     * @throws Exception
     */
    void exportToStream(OutputStream outputStream) throws Exception;

    /**
     *
     *
     * @param inputStream
     */
    void setInputStream(InputStream inputStream);
}
