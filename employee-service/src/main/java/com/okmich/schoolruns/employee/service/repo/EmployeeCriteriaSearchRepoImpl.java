/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.employee.service.repo;

import com.okmich.common.entity.repo.CriteriaSearchWorker;
import com.okmich.schoolruns.common.entity.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Repository("employeeCriteriaSearchRepo")
@Transactional(readOnly = true)
public class EmployeeCriteriaSearchRepoImpl implements EmployeeCriteriaSearchRepo {

    /**
     *
     */
    private CriteriaSearchWorker<Employee, EmployeeQueryCriteria> repoWorker;
    @PersistenceContext
    private EntityManager entityManager;

    public EmployeeCriteriaSearchRepoImpl() {
        repoWorker =
                new CriteriaSearchWorker<Employee, EmployeeQueryCriteria>();
    }

    @Override
    public List<Employee> findEmployees(EmployeeQueryCriteria criteria) {
        return repoWorker.findByCriteria(entityManager, criteria);
    }
}
