/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.employee.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.NestedRelatedEntity;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCDate;
import com.okmich.common.entity.criteria.WCString;
import java.util.Date;

/**
 *
 * @author Michael
 */
public class EmployeeQueryCriteria extends BaseEntityQueryCriteria {

    public static final String staffNumber = "staffNumber";
    public static final String dateOfHire = "dateOfHire";
    public static final String surname = "surname";
    public static final String othernames = "othernames";
    public static final String idNumber = "idNumber";
    public static final String birthDate = "birthDate";
    public static final String mobileNo = "mobileNo";
    public static final String officeNo = "officeNo";
    public static final String email = "email";
    public static final String gender = "gender";
    public static final String homeTown = "homeTown";
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "a", referencedEntity = "country")
    public static final String countryCode = "countryCode";
    @RelatedEntity(entityAlias = "b", referencedEntity = "state")
    public static final String stateCode = "stateCode";
    @RelatedEntity(entityAlias = "d", referencedEntity = "maritalStatus")
    public static final String maritalStatusId = "maritalStatusId";
    @RelatedEntity(entityAlias = "e", referencedEntity = "employeeType")
    public static final String employeeTypeId = "employeeTypeId";
    @RelatedEntity(entityAlias = "f", referencedEntity = "employeeCategory")
    public static final String employeeCategoryId = "employeeCategoryId";
    @RelatedEntity(entityAlias = "g", referencedEntity = "language")
    public static final String languageId = "languageId";
    @RelatedEntity(entityAlias = "h", referencedEntity = "religion")
    public static final String religionId = "religionId";
    @RelatedEntity(entityAlias = "j", referencedEntity = "address")
    @NestedRelatedEntity(nestedEntityAlias = "z", nestedEntityReferenced = "city")
    public static final String cityId = "cityId";

    public EmployeeQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "Employee";
    }

    /**
     * @param _staffNumber the staffNumber to set
     */
    public void setStaffNumber(String _staffNumber) {
        setParameter(EmployeeQueryCriteria.staffNumber, _staffNumber);
    }

    /**
     * @param _dateOfHire the dateOfHire to set
     */
    public void setDateOfHire(Date _dateOfHire) {
        setParameter(EmployeeQueryCriteria.dateOfHire, _dateOfHire);
    }

    /**
     *
     * @param _dateOfHire
     * @param wClause
     * @param _dateOfHire1
     */
    public void setDateOfHire(Date _dateOfHire, WCDate wClause, Date _dateOfHire1) {
        setParameter(EmployeeQueryCriteria.dateOfHire, wClause, _dateOfHire, _dateOfHire1);
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        setParameter(EmployeeQueryCriteria.surname, surname);
    }

    /**
     *
     * @param surname
     * @param wClause
     */
    public void setSurname(String surname, WCString wClause) {
        setParameter(EmployeeQueryCriteria.surname, wClause, surname);
    }

    /**
     *
     * @param surname
     * @param wClause
     */
    public void setOthernames(String othernames, WCString wClause) {
        setParameter(EmployeeQueryCriteria.othernames, wClause, othernames);
    }

    /**
     * @param idNumber the idNumber to set
     */
    public void setIdNumber(String idNumber) {
        setParameter(EmployeeQueryCriteria.idNumber, idNumber);
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        setParameter(EmployeeQueryCriteria.birthDate, birthDate);
    }

    /**
     * @param mobileNo the mobileNo to set
     */
    public void setMobileNo(String mobileNo) {
        setParameter(EmployeeQueryCriteria.mobileNo, mobileNo);
    }

    /**
     * @param officeNo the officeNo to set
     */
    public void setOfficeNo(String officeNo) {
        setParameter(EmployeeQueryCriteria.officeNo, officeNo);
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        setParameter(EmployeeQueryCriteria.email, email);
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        setParameter(EmployeeQueryCriteria.gender, gender);
    }

    /**
     * @param homeTown the homeTown to set
     */
    public void setHomeTown(String homeTown) {
        setParameter(EmployeeQueryCriteria.homeTown, homeTown);
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        setParameter(EmployeeQueryCriteria.countryCode, countryCode);
    }

    /**
     * @param stateCode the stateCode to set
     */
    public void setStateCode(String stateCode) {
        setParameter(EmployeeQueryCriteria.stateCode, stateCode);
    }

    /**
     * @param _maritalStatusId the maritalStatusId to set
     */
    public void setMaritalStatusId(String _maritalStatusId) {
        setParameter(EmployeeQueryCriteria.maritalStatusId, _maritalStatusId);
    }

    /**
     * @param _employeeTypeId the employeeTypeId to set
     */
    public void setEmployeeTypeId(String _employeeTypeId) {
        setParameter(EmployeeQueryCriteria.employeeTypeId, _employeeTypeId);
    }

    /**
     * @param employeeCategoryId the employeeCategoryId to set
     */
    public void setEmployeeCategoryId(String employeeCategoryId) {
        setParameter(EmployeeQueryCriteria.employeeCategoryId, employeeCategoryId);
    }

    /**
     * @param employeeCategoryId the employeeCategoryId to set
     * @param wclause
     */
    public void setEmployeeCategoryId(String employeeCategoryId, WCString wclause) {
        setParameter(EmployeeQueryCriteria.employeeCategoryId, wclause, employeeCategoryId);
    }

    /**
     * @param languageId the languageId to set
     */
    public void setLanguageId(Integer languageId) {
        setParameter(EmployeeQueryCriteria.languageId, languageId);
    }

    /**
     * @param religionId the religionId to set
     */
    public void setReligionId(String religionId) {
        setParameter(EmployeeQueryCriteria.religionId, religionId);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(EmployeeQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(Integer cityId) {
        setParameter(EmployeeQueryCriteria.cityId, cityId);
    }
}