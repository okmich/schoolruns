/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCString;

/**
 *
 * @author Michael
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
public class AccountQueryCriteria extends BaseEntityQueryCriteria {

    public static final String accountCode = "accountCode";
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "accountType")
    public static final String accountTypeId = "accountTypeId";

    public AccountQueryCriteria() {
        super(true);
    }

    @Override
    public String getEntityName() {
        return "Account";
    }

    /**
     * @param _accountCode the accountCode to set
     */
    public void setAccountCode(String _accountCode) {
        setParameter(AccountQueryCriteria.accountCode, WCString.LIKE, _accountCode);
    }

    /**
     * @param _schoolId the schoolId to set
     */
    public void setSchoolId(Integer _schoolId) {
        setParameter(AccountQueryCriteria.schoolId, _schoolId);
    }

    /**
     * @param _accountTypeId the accountTypeId to set
     */
    public void setAccountTypeId(String _accountTypeId) {
        setParameter(AccountQueryCriteria.accountTypeId, _accountTypeId);
    }
}
