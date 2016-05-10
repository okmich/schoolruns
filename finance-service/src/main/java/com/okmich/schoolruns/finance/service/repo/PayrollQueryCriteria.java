/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCDate;
import java.util.Date;

/**
 *
 * @author Michael
 * @since Jul 29, 2013
 * @company Okmich Ltd.
 */
public class PayrollQueryCriteria extends BaseEntityQueryCriteria {

    public static final String paymentDate = "paymentDate";
    @RelatedEntity(entityAlias = "a", referencedEntity = "school")
    public static final String schoolId = "schoolId";

    public PayrollQueryCriteria() {
    }

    /**
     *
     * @return
     */
    @Override
    public String getEntityName() {
        return "Payroll";
    }

    /**
     * @param _paymentDate the paymentDate to set
     */
    public void setPaymentDate(Date _paymentDate) {
        setParameter(PayrollQueryCriteria.paymentDate, _paymentDate);
    }

    /**
     * @param paymentDate the paymentDate to set
     */
    public void setPaymentDate(Date date1, WCDate wclause, Date date2) {
        setParameter(PayrollQueryCriteria.paymentDate, wclause, date1, date2);
    }

    /**
     * @param _schoolId the schoolId to set
     */
    public void setSchoolId(Integer _schoolId) {
        setParameter(PayrollQueryCriteria.schoolId, _schoolId);
    }
}
