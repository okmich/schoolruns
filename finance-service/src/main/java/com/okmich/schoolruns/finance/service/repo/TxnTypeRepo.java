/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.schoolruns.common.entity.TxnType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael Enudi
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
@Repository
public interface TxnTypeRepo extends JpaRepository<TxnType, Integer> {

    /**
     *
     *
     * @param schoolId
     * @param txnTypeCode
     * @return List<TxnType>
     */
    @Query(name = "findSchoolTxnType", value = "SELECT t FROM TxnType t "
    + "WHERE t.schoolId = ?1 AND t.txnTypeCode = ?2")
    List<TxnType> findSchoolTxnType(Integer schoolId, String txnTypeCode);
}
