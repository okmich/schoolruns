/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.schoolruns.common.entity.TxnJournalDetail;
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
public interface TxnJournalDetailRepo extends JpaRepository<TxnJournalDetail, Integer> {

    @Query(name = "findTxnDetails",
    value = "SELECT t FROM TxnJournalDetail t WHERE t.txnJournal.txnJournalId = ?1")
    List<TxnJournalDetail> findTxnDetails(Integer txnJournalId);
}
