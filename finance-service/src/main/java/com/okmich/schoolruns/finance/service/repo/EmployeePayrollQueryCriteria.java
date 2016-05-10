/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.RelatedEntity;

/**
 *
 * @author Michael
 * @since Jul 29, 2013
 * @company Okmich Ltd.
 */
public class EmployeePayrollQueryCriteria extends BaseEntityQueryCriteria {

    public static final String employeeId = "employeeId";
    public static final String employeeName = "employeeName";
    public static final String payPeriod = "payPeriod";
    @RelatedEntity(entityAlias = "a", referencedEntity = "payroll")
    public static final String payrollId = "payrollId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "payStructure")
    public static final String payStructureId = "payStructureId";

    public EmployeePayrollQueryCriteria() {
    }

    /**
     *
     * @return
     */
    @Override
    public String getEntityName() {
        return "EmployeePayroll";
    }

    /**
     * @param _employeeId the employeeId to set
     */
    public void setEmployeeId(Integer _employeeId) {
        setParameter(EmployeePayrollQueryCriteria.employeeId, _employeeId);
    }

    /**
     * @param _employeeName the employeeName to set
     */
    public void setEmployeeName(Integer _employeeName) {
        setParameter(EmployeePayrollQueryCriteria.employeeName, _employeeName);
    }

    /**
     * @param _payPeriod the payPeriod to set
     */
    public void setPayPeriod(String _payPeriod) {
        setParameter(EmployeePayrollQueryCriteria.payPeriod, _payPeriod);
    }

    /**
     * @param _payrollId the payrollId to set
     */
    public void setPayrollId(Integer _payrollId) {
        setParameter(EmployeePayrollQueryCriteria.payrollId, _payrollId);
    }

    /**
     * @param _payStructureId the payStructureId to set
     */
    public void setPayStructureId(Integer _payStructureId) {
        setParameter(EmployeePayrollQueryCriteria.payStructureId, _payStructureId);
    }
}
