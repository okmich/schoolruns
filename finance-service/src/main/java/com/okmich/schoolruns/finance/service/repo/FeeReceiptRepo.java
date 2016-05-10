/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.schoolruns.common.entity.FeeGroup;
import com.okmich.schoolruns.common.entity.FeeReceipt;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import java.math.BigDecimal;
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
public interface FeeReceiptRepo extends JpaRepository<FeeReceipt, Integer> {

    @Query(name = "findTotalPaid", value = "SELECT SUM(f.amount) FROM FeeReceipt f "
    + "WHERE f.schoolStudent = ?1 AND f.feeGroup = ?2")
    BigDecimal findTotalPaidBySchoolStudent(SchoolStudent schoolStudent, FeeGroup feeGroup);
}
