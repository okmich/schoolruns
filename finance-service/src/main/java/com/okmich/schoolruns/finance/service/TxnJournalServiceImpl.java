/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.exception.ErrorConstants;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Account;
import com.okmich.schoolruns.common.entity.TxnJournal;
import com.okmich.schoolruns.common.entity.TxnJournalDetail;
import com.okmich.schoolruns.common.entity.TxnType;
import com.okmich.schoolruns.finance.service.repo.FinanceCriteriaSearchRepo;
import com.okmich.schoolruns.finance.service.repo.TxnJournalDetailQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.TxnJournalDetailRepo;
import com.okmich.schoolruns.finance.service.repo.TxnJournalQueryCriteria;
import com.okmich.schoolruns.finance.service.repo.TxnJournalRepo;
import com.okmich.schoolruns.finance.service.repo.TxnTypeRepo;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
@Service("txnJournalService")
@Transactional
public class TxnJournalServiceImpl implements TxnJournalService {

    /**
     * LOG
     */
    private static final Logger LOG = Logger.getLogger(TxnJournalServiceImpl.class.getName());
    /**
     * txnJournalDetailRepo
     */
    @Autowired
    private TxnJournalDetailRepo txnJournalDetailRepo;
    /**
     * txnJournalRepo
     */
    @Autowired
    private TxnJournalRepo txnJournalRepo;
    /**
     * txnJournalDetailRepo
     */
    @Autowired
    private TxnTypeRepo txnTypeRepo;
    /**
     * financeCriteriaSearchRepo
     */
    @Autowired
    private FinanceCriteriaSearchRepo financeCriteriaSearchRepo;

    public TxnJournalServiceImpl() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public TxnJournal createTxnJournal(TxnJournal txnJournal) throws BusinessException {
        txnJournal.setModifiedTime(new Date());
        if (txnJournal.getEffectiveDate() == null) {
            txnJournal.setEffectiveDate(txnJournal.getTxnDate());
        }
        txnJournal.setStatus(CommonConstants.STATUS_ACTIVE);
        TxnType txnType = txnTypeRepo.findOne(txnJournal.getTxnType().getTxnTypeId());
        TxnJournalDetail debitDetails, creditDetails;
        try {
            txnJournal = txnJournalRepo.save(txnJournal);
            debitDetails = createTxnDebitJournal(txnType.getDebitAccount(), txnJournal);
            creditDetails = createTxnCreditJournal(txnType.getCreditAccount(), txnJournal);
            if (debitDetails == null && creditDetails == null) {
                throw new BusinessException(ErrorConstants.NO_ACCT_SPECIFIED);
            }
            if (debitDetails != null) {
                txnJournalDetailRepo.save(debitDetails);
            } else { //this else was added to fix a bug
                txnType.setDebitAccount(null);
            }
            if (creditDetails != null) {
                txnJournalDetailRepo.save(creditDetails);
            } else { //this else was added to fix a bug to reset a null entity branch to its previous state
                txnType.setCreditAccount(null);
            }
            return txnJournal;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public TxnJournal reverseTxnJournal(TxnJournal _txnJournal) throws BusinessException {
        _txnJournal.setModifiedTime(new Date());
        if (_txnJournal.getEffectiveDate() == null) {
            _txnJournal.setEffectiveDate(_txnJournal.getTxnDate());
        }
        _txnJournal.setStatus(CommonConstants.STATUS_ACTIVE);
        TxnType txnType = txnTypeRepo.findOne(_txnJournal.getTxnType().getTxnTypeId());
        TxnJournalDetail debitDetails, creditDetails;
        try {
            _txnJournal = txnJournalRepo.save(_txnJournal);
            //switch the debit and credit accounts and that will do
            //creating a debit detail with the credit account
            debitDetails = createTxnDebitJournal(txnType.getCreditAccount(), _txnJournal);
            //creating a credit detail with the debit account
            creditDetails = createTxnCreditJournal(txnType.getDebitAccount(), _txnJournal);
            if (debitDetails == null && creditDetails == null) {
                throw new BusinessException(ErrorConstants.NO_ACCT_SPECIFIED);
            }
            if (debitDetails != null) {
                txnJournalDetailRepo.save(debitDetails);
            } else { //this else was added to fix a bug
                txnType.setCreditAccount(null);
            }
            if (creditDetails != null) {
                txnJournalDetailRepo.save(creditDetails);
            } else { //this else was added to fix a bug to reset a null entity branch to its previous state
                txnType.setDebitAccount(null);
            }
            return _txnJournal;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public TxnJournal findTxnJornal(Integer txnJournalId) {
        return txnJournalRepo.findOne(txnJournalId);
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<TxnJournal> findTxnJournals(TxnJournalQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findTxnJournals(criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<TxnJournalDetail> findTxnJournalDetails(Integer txnJournalId) {
        return txnJournalDetailRepo.findTxnDetails(txnJournalId);
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public TxnJournalDetail findTxnJournalDetail(Integer txnJournalDetailsId) {
        return txnJournalDetailRepo.findOne(txnJournalDetailsId);
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<TxnJournalDetail> findTxnJournalDetails(TxnJournalDetailQueryCriteria criteria) {
        return financeCriteriaSearchRepo.findTxnJournalDetails(criteria);
    }

    /**
     *
     *
     * @param debitAccount
     * @param txnJournal
     * @return TxnJournalDetail
     */
    private TxnJournalDetail createTxnDebitJournal(Account debitAccount, TxnJournal txnJournal) {
        if (debitAccount == null || debitAccount.getAccountCode() == null) {
            return null;
        }
        TxnJournalDetail dt = new TxnJournalDetail();

        dt.setAccountCode(debitAccount.getAccountCode());
        dt.setAmount(txnJournal.getAmount().abs());
        dt.setDcSign(FinanceConstants.DC_SIGN_DEBIT);
        dt.setModifiedBy(txnJournal.getModifiedBy());
        dt.setModifiedTime(new Date());
        dt.setStatus(CommonConstants.STATUS_ACTIVE);
        dt.setTxnJournal(txnJournal);

        return dt;
    }

    /**
     *
     *
     *
     * @param creditAccount
     * @param txnJournal
     * @return TxnJournalDetail
     */
    private TxnJournalDetail createTxnCreditJournal(Account creditAccount, TxnJournal txnJournal) {
        if (creditAccount == null || creditAccount.getAccountCode() == null) {
            return null;
        }
        TxnJournalDetail ctj = new TxnJournalDetail();

        ctj.setAccountCode(creditAccount.getAccountCode());
        ctj.setAmount(txnJournal.getAmount().abs().negate());
        ctj.setDcSign(FinanceConstants.DC_SIGN_CREDIT);
        ctj.setModifiedBy(txnJournal.getModifiedBy());
        ctj.setModifiedTime(new Date());
        ctj.setStatus(CommonConstants.STATUS_ACTIVE);
        ctj.setTxnJournal(txnJournal);

        return ctj;
    }
}
