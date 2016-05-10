/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.student.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;

/**
 *
 * @author Michael
 */
public class StudentQueryCriteria extends BaseEntityQueryCriteria {

    public StudentQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "Student";
    }
}
