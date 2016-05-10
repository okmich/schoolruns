/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service;

import com.okmich.common.exception.TechnicalException;
import com.okmich.common.util.StringUtil;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 * @since Aug 27, 2013
 * @company Okmich Ltd.
 */
@Service("jdbcService")
@Transactional()
public class JdbcServiceImpl implements JdbcService {

    /**
     * LOG
     */
    private static final Logger LOG = Logger.getLogger(JdbcServiceImpl.class.getName());
    /**
     * dataSource
     */
    @Autowired
    private DataSource dataSource;

    public JdbcServiceImpl() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public CachedRowSet executeQuery(String query) throws TechnicalException {

        try (Connection connection = dataSource.getConnection()) {
            CachedRowSetImpl rowSet;
            try (ResultSet resultSet = connection.prepareStatement(query) //set the result set as readonly
                            .executeQuery()) {
                rowSet = new CachedRowSetImpl();
                rowSet.populate(resultSet);

                return rowSet;
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, null, ex);
                throw new TechnicalException(StringUtil.getNestedErrorMessage(ex));
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new TechnicalException(StringUtil.getNestedErrorMessage(ex));
        }
    }
}