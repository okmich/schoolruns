/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service;

import com.okmich.common.exception.TechnicalException;
import java.io.Serializable;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Michael
 * @since Aug 27, 2013
 * @company Okmich Ltd.
 */
public interface JdbcService extends Serializable {

    /**
     * executes the {@code query} and returns a disconnected rowset
     *
     *
     * @param query - a query string for this implementation to execute
     * @return RowSet - a disconnected result set
     * @throws TechnicalException - if any database error occurs
     */
    CachedRowSet executeQuery(String query) throws TechnicalException;
}
