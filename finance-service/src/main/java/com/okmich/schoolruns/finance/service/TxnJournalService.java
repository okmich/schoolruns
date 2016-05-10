/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.schoolruns.common.entity.TxnJournal;
import com.okmich.schoolruns.common.entity.TxnJournalDetail;
import com.okmich.schoolruns.finance.service.repo.TxnJournalDetailQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.TxnJournalQueryCriteria;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
public interface TxnJournalService extends Serializable {

    /**
     * creates a transaction journal record which comprise on master record and
     * a maximum of two details record. <br /><br />
     *
     *
     * @param txnJournal - transaction master journal to be created access by
     * public, non-package classes
     * @return TxnJournal
     */
    TxnJournal createTxnJournal(TxnJournal txnJournal) throws BusinessException;

    /**
     * creates a txn journal that reverses a previously entered txn journal.
     *
     *
     * @param _txnJournal
     * @return TxnJournal - the reversal record
     * @throws BusinessException
     */
    TxnJournal reverseTxnJournal(TxnJournal _txnJournal) throws BusinessException;

    /**
     * find a transaction record by it ID. The found record is returned as a
     * transaction DTO TxnData
     *
     *
     * @param txnJournalId - txnJornalID
     * @return returns a DTO for the transaction record
     */
    TxnJournal findTxnJornal(Integer txnJournalId);

    /**
     *
     *
     * @param criteria
     * @return
     */
    List<TxnJournal> findTxnJournals(TxnJournalQueryCriteria criteria);

    /**
     *
     *
     * @param txnJournalId
     * @return
     */
    List<TxnJournalDetail> findTxnJournalDetails(Integer txnJournalId);

    /**
     *
     *
     * @param txnJournalDetailsId
     * @return
     */
    TxnJournalDetail findTxnJournalDetail(Integer txnJournalDetailsId);

    /**
     *
     *
     *
     * @param criteria
     * @return
     */
    List<TxnJournalDetail> findTxnJournalDetails(TxnJournalDetailQueryCriteria criteria);
}
