/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCString;

/**
 *
 * @author Michael
 */
public class SchoolParentQueryCriteria extends BaseEntityQueryCriteria {

    @RelatedEntity(entityAlias = "a", referencedEntity = "parent")
    public static final String surname = "surname";
    @RelatedEntity(entityAlias = "a", referencedEntity = "parent")
    public static final String othernames = "othernames";
    @RelatedEntity(entityAlias = "a", referencedEntity = "parent")
    public static final String email = "email";
    @RelatedEntity(entityAlias = "a", referencedEntity = "parent")
    public static final String phoneNumber = "phoneNumber";
    @RelatedEntity(entityAlias = "a", referencedEntity = "parent")
    public static final String alternateNumber = "alternateNumber";
    @RelatedEntity(entityAlias = "a", referencedEntity = "parent")
    public static final String relationship = "relationship";
    @RelatedEntity(entityAlias = "a", referencedEntity = "parent")
    public static final String userLoginId = "userLoginId";
    public static final String schoolId = "schoolId";

    public SchoolParentQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "SchoolParent";
    }

    /**
     * @param _surname the surname to set
     */
    public void setSurname(String _surname) {
        setParameter(SchoolParentQueryCriteria.surname, _surname);
    }

    /**
     *
     * @param _surname
     * @param wclause
     */
    public void setSurname(String _surname, WCString wclause) {
        setParameter(SchoolParentQueryCriteria.surname, wclause, _surname);
    }

    /**
     * @param _othernames the othernames to set
     */
    public void setOthernames(String _othernames, WCString wclause) {
        setParameter(SchoolParentQueryCriteria.othernames, wclause, _othernames);
    }

    /**
     * @param _email the email to set
     */
    public void setEmail(String _email) {
        setParameter(SchoolParentQueryCriteria.email, _email);
    }

    /**
     *
     * @param _email
     * @param wclause
     */
    public void setEmail(String _email, WCString wclause) {
        setParameter(SchoolParentQueryCriteria.email, wclause, _email);
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        setParameter(SchoolParentQueryCriteria.phoneNumber, phoneNumber);
    }

    /**
     *
     * @param phoneNumber
     * @param wclause
     */
    public void setPhoneNumber(String phoneNumber, WCString wclause) {
        setParameter(SchoolParentQueryCriteria.phoneNumber, wclause, phoneNumber);
    }

    /**
     *
     * @param alternateNumber
     * @param wclause
     */
    public void setAlternateNumber(String alternateNumber, WCString wclause) {
        setParameter(SchoolParentQueryCriteria.phoneNumber, wclause, phoneNumber);
    }

    /**
     * @param relationship the relationship to set
     */
    public void setRelationship(String relationship) {
        setParameter(SchoolParentQueryCriteria.relationship, relationship);
    }

    /**
     * @param _schoolId the _schoolId to set
     */
    public void setSchoolId(Integer _schoolId) {
        setParameter(SchoolParentQueryCriteria.schoolId, _schoolId);
    }

    /**
     * @param _userLoginId the userLoginId to set
     */
    public void setUserLoginId(Integer _userLoginId) {
        setParameter(SchoolParentQueryCriteria.userLoginId, _userLoginId);
    }
}