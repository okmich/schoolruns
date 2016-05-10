/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.NestedRelatedEntity;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCString;

/**
 *
 * @author Michael
 */
public class SchoolQueryCriteria extends BaseEntityQueryCriteria {

    public static final String name = "name";
    public static final String contactNo = "contactNo";
    public static final String emailAddress = "emailAddress";
    @RelatedEntity(entityAlias = "c", referencedEntity = "country")
    public static final String countryCode = "countryCode";
    @RelatedEntity(entityAlias = "a", referencedEntity = "address")
    @NestedRelatedEntity(nestedEntityAlias = "s", nestedEntityReferenced = "state")
    public static final String stateCode = "stateCode";
    @RelatedEntity(entityAlias = "a", referencedEntity = "address")
    @NestedRelatedEntity(nestedEntityAlias = "ct", nestedEntityReferenced = "city")
    public static final String cityId = "cityId";

    public SchoolQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "School";
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        setParameter(SchoolQueryCriteria.name, name);
    }

    /**
     *
     * @param name
     * @param wClause
     */
    public void setName(String name, WCString wClause) {
        setParameter(SchoolQueryCriteria.name, wClause, name);
    }

    /**
     *
     * @param contactNo
     * @param wClause
     */
    public void setContactNo(String contactNo, WCString wClause) {
        setParameterCI(SchoolQueryCriteria.contactNo, wClause, contactNo);
    }

    /**
     *
     * @param emailAddress
     * @param wClause
     */
    public void setEmailAddress(String emailAddress, WCString wClause) {
        setParameter(SchoolQueryCriteria.emailAddress, wClause, emailAddress);
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        setParameter(SchoolQueryCriteria.emailAddress, emailAddress);
    }

    /**
     * @param description the description to set
     */
    public void setCountryCode(String countryCode) {
        setParameter(SchoolQueryCriteria.countryCode, countryCode);
    }

    /**
     * @param stateCode the stateCode to set
     */
    public void setStateCode(String stateCode) {
        setParameter(SchoolQueryCriteria.stateCode, stateCode);
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(Integer cityId) {
        setParameter(SchoolQueryCriteria.cityId, cityId);
    }
}
