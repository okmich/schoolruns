/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.student.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.NestedRelatedEntity;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCString;

/**
 *
 * @author Michael
 */
public class ParentQueryCriteria extends BaseEntityQueryCriteria {

    public static final String surname = "surname";
    public static final String othernames = "othernames";
    public static final String email = "email";
    public static final String phoneNumber = "phoneNumber";
    public static final String alternateNumber = "alternateNumber";
    public static final String relationship = "relationship";
    public static final String jobIndustry = "jobIndustry";
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "state")
    @RelatedEntity(entityAlias = "a", referencedEntity = "address1")
    public static final String stateCode = "stateCode";
    @RelatedEntity(entityAlias = "a", referencedEntity = "address1")
    @NestedRelatedEntity(nestedEntityAlias = "y", nestedEntityReferenced = "city")
    public static final String cityId = "cityId";
    public static final String userLoginId = "userLoginId";

    public ParentQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "Parent";
    }

    /**
     * @param _surname the surname to set
     */
    public void setSurname(String _surname) {
        setParameter(ParentQueryCriteria.surname, _surname);
    }

    /**
     *
     * @param _surname
     * @param wclause
     */
    public void setSurname(String _surname, WCString wclause) {
        setParameter(ParentQueryCriteria.surname, wclause, _surname);
    }

    /**
     * @param _othernames the othernames to set
     */
    public void setOthernames(String _othernames, WCString wclause) {
        setParameter(ParentQueryCriteria.othernames, wclause, _othernames);
    }

    /**
     * @param _email the email to set
     */
    public void setEmail(String _email) {
        setParameter(ParentQueryCriteria.email, _email);
    }

    /**
     *
     * @param _email
     * @param wclause
     */
    public void setEmail(String _email, WCString wclause) {
        setParameter(ParentQueryCriteria.email, wclause, _email);
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        setParameter(ParentQueryCriteria.phoneNumber, phoneNumber);
    }

    /**
     *
     * @param phoneNumber
     * @param wclause
     */
    public void setPhoneNumber(String phoneNumber, WCString wclause) {
        setParameter(ParentQueryCriteria.phoneNumber, wclause, phoneNumber);
    }

    /**
     *
     * @param alternateNumber
     * @param wclause
     */
    public void setAlternateNumber(String alternateNumber, WCString wclause) {
        setParameter(ParentQueryCriteria.phoneNumber, wclause, phoneNumber);
    }

    /**
     * @param relationship the relationship to set
     */
    public void setRelationship(String relationship) {
        setParameter(ParentQueryCriteria.relationship, relationship);
    }

    /**
     * @param jobIndustry the jobIndustry to set
     */
    public void setJobIndustry(String jobIndustry) {
        setParameter(ParentQueryCriteria.jobIndustry, jobIndustry);
    }

    /**
     * @param stateCode the stateCode to set
     */
    public void setStateCode(String stateCode) {
        setParameter(ParentQueryCriteria.stateCode, stateCode);
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(Integer cityId) {
        setParameter(ParentQueryCriteria.cityId, cityId);
    }

    /**
     * @param _userLoginId the userLoginId to set
     */
    public void setUserLoginId(Integer _userLoginId) {
        setParameter(ParentQueryCriteria.userLoginId, _userLoginId);
    }
}