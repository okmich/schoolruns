/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.schoolruns.common.entity.Expense;
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
public interface ExpenseRepo extends JpaRepository<Expense, Integer> {
    
    @Query(name = "findExpenseReversal",
    value = "SELECT e FROM Expense e WHERE e.prevExpenseId = ?1 AND e.reversal = true")
    List<Expense> findExpenseReversal(Integer expenseId);
}
