/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.schoolruns.common.entity.Payroll;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael Enudi
 * @since Aug 21, 2013
 * @company Okmich Ltd.
 */
@Repository
public interface PayrollRepo extends JpaRepository<Payroll, Integer> {

    @Query(name = "findPayrollForMonth",
    value = "SELECT p.payroll_id FROM payroll p WHERE p.school_id = ?1 AND date_format(p.payment_date, '%m/%Y') = ?2",
    nativeQuery = true)
    List<Object> findPayrollForMonth(Integer schoolId, String monthYear);
}