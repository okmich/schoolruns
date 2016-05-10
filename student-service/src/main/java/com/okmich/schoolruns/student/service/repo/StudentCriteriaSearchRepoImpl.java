/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.student.service.repo;

import com.okmich.common.entity.repo.CriteriaSearchWorker;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Repository("studentCriteriaSearchRepo")
@Transactional(readOnly = true)
public class StudentCriteriaSearchRepoImpl implements StudentCriteriaSearchRepo {

    /**
     * studentRepoWorker
     */
    private CriteriaSearchWorker<Student, StudentQueryCriteria> studentRepoWorker;
    /**
     * parentRepoWorker
     */
    private CriteriaSearchWorker<Parent, ParentQueryCriteria> parentRepoWorker;
    /**
     * entityManager
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * default constructor
     */
    public StudentCriteriaSearchRepoImpl() {
        studentRepoWorker =
                new CriteriaSearchWorker<Student, StudentQueryCriteria>();
        parentRepoWorker =
                new CriteriaSearchWorker<Parent, ParentQueryCriteria>();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Student> findStudents(StudentQueryCriteria criteria) {
        return studentRepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Parent> findParents(ParentQueryCriteria criteria) {
        return parentRepoWorker.findByCriteria(entityManager, criteria);
    }
}