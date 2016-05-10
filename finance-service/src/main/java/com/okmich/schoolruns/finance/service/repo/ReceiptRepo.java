/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.schoolruns.common.entity.Receipt;
import java.math.BigDecimal;
import java.util.Date;
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
public interface ReceiptRepo extends JpaRepository<Receipt, Integer> {

    /**
     *
     * @param receiptId
     * @return
     */
    @Query(name = "findSubReceipt",
    value = "SELECT r FROM Receipt r WHERE r.receipt.receiptId = ?1")
    List<Receipt> findSubReceipt(Integer receiptId);

    @Query(name = "findReceiptYearCount",
    value = "SELECT COUNT(r.receiptId) FROM Receipt r WHERE r.schoolTerm.schoolTermId = ?1"
    + " AND r.txnDate BETWEEN ?2 AND ?3")
    Long findReceiptYearCount(Integer schoolId, Date firstYearDay, Date lastYearDay);

    /**
     *
     * @param receiptId
     * @return
     */
    @Query(name = "findUtilizedSum",
    value = "SELECT sum(r.amount) FROM Receipt r WHERE r.receipt.receiptId = ?1")
    BigDecimal findUtilizedSum(Integer receiptId);
}
