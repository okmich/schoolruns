/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.student.service.repo;

import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.Student;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface StudentCriteriaSearchRepo extends Serializable {

    /**
     *
     * @param criteria
     * @return
     */
    public List<Parent> findParents(ParentQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return
     */
    public List<Student> findStudents(StudentQueryCriteria criteria);
}
