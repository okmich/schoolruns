/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.employee.service.data;

import com.okmich.common.BaseData;
import com.okmich.schoolruns.common.entity.Address;
import com.okmich.schoolruns.common.entity.Country;
import com.okmich.schoolruns.common.entity.Employee;
import com.okmich.schoolruns.common.entity.EmployeeCategory;
import com.okmich.schoolruns.common.entity.EmployeePosition;
import com.okmich.schoolruns.common.entity.EmployeeType;
import com.okmich.schoolruns.common.entity.IdentificationMeans;
import com.okmich.schoolruns.common.entity.Language;
import com.okmich.schoolruns.common.entity.MaritalStatus;
import com.okmich.schoolruns.common.entity.Religion;
import com.okmich.schoolruns.common.entity.School;
import com.okmich.schoolruns.common.entity.State;
import com.okmich.schoolruns.common.entity.UserLogin;
import java.util.Date;

/**
 *
 * @author Michael
 */
public class EmployeeData extends BaseData {

    private Integer employeeId;
    private String staffNumber;
    private Date dateOfHire;
    private String surname;
    private String othernames;
    private String idNumber;
    private Date birthDate;
    private String mobileNo;
    private String officeNo;
    private String email;
    private String gender;
    private String homeTown;
    private String countryCode;
    private String country;
    private String pictureUrl;
    private String stateCode;
    private String state;
    private Date resignDate;
    private Integer payStructureId;
    private String identificationMeansId;
    private String identificationMeans;
    private String maritalStatusId;
    private String maritalStatus;
    private String employeeTypeId;
    private String employeeType;
    private String employeeCategoryId;
    private String employeeCategory;
    private Integer employeePositionId;
    private String employeePosition;
    private Integer languageId;
    private String language;
    private String religionId;
    private String religion;
    private Integer schoolId;
    private Address address;
    private Integer userLoginId;
    private String username;

    public EmployeeData() {
    }

    public EmployeeData(Employee employee) {
        setEmployee(employee);
    }

    /**
     * @return the employeeId
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the staffNumber
     */
    public String getStaffNumber() {
        return staffNumber;
    }

    /**
     * @param staffNumber the staffNumber to set
     */
    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    /**
     * @return the dateOfHire
     */
    public Date getDateOfHire() {
        return dateOfHire;
    }

    /**
     * @param dateOfHire the dateOfHire to set
     */
    public void setDateOfHire(Date dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the othernames
     */
    public String getOthernames() {
        return othernames;
    }

    /**
     * @param othernames the othernames to set
     */
    public void setOthernames(String othernames) {
        this.othernames = othernames;
    }

    /**
     * @return the idNumber
     */
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * @param idNumber the idNumber to set
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * @param mobileNo the mobileNo to set
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * @return the officeNo
     */
    public String getOfficeNo() {
        return officeNo;
    }

    /**
     * @param officeNo the officeNo to set
     */
    public void setOfficeNo(String officeNo) {
        this.officeNo = officeNo;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the homeTown
     */
    public String getHomeTown() {
        return homeTown;
    }

    /**
     * @param homeTown the homeTown to set
     */
    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the stateCode
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * @param stateCode the stateCode to set
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the identificationMeansId
     */
    public String getIdentificationMeansId() {
        return identificationMeansId;
    }

    /**
     * @param identificationMeansId the identificationMeansId to set
     */
    public void setIdentificationMeansId(String identificationMeansId) {
        this.identificationMeansId = identificationMeansId;
    }

    /**
     * @return the identificationMeans
     */
    public String getIdentificationMeans() {
        return identificationMeans;
    }

    /**
     * @param identificationMeans the identificationMeans to set
     */
    public void setIdentificationMeans(String identificationMeans) {
        this.identificationMeans = identificationMeans;
    }

    /**
     * @return the maritalStatusId
     */
    public String getMaritalStatusId() {
        return maritalStatusId;
    }

    /**
     * @param maritalStatusId the maritalStatusId to set
     */
    public void setMaritalStatusId(String maritalStatusId) {
        this.maritalStatusId = maritalStatusId;
    }

    /**
     * @return the maritalStatus
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * @param maritalStatus the maritalStatus to set
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * @return the employeeTypeId
     */
    public String getEmployeeTypeId() {
        return employeeTypeId;
    }

    /**
     * @param employeeTypeId the employeeTypeId to set
     */
    public void setEmployeeTypeId(String employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }

    /**
     * @return the employeeType
     */
    public String getEmployeeType() {
        return employeeType;
    }

    /**
     * @param employeeType the employeeType to set
     */
    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    /**
     * @return the employeeCategoryId
     */
    public String getEmployeeCategoryId() {
        return employeeCategoryId;
    }

    /**
     * @param employeeCategoryId the employeeCategoryId to set
     */
    public void setEmployeeCategoryId(String employeeCategoryId) {
        this.employeeCategoryId = employeeCategoryId;
    }

    /**
     * @return the employeeCategory
     */
    public String getEmployeeCategory() {
        return employeeCategory;
    }

    /**
     * @param employeeCategory the employeeCategory to set
     */
    public void setEmployeeCategory(String employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    /**
     * @return the employeePositionId
     */
    public Integer getEmployeePositionId() {
        return employeePositionId;
    }

    /**
     * @param employeePositionId the employeePositionId to set
     */
    public void setEmployeePositionId(Integer employeePositionId) {
        this.employeePositionId = employeePositionId;
    }

    /**
     * @return the employeePosition
     */
    public String getEmployeePosition() {
        return employeePosition;
    }

    /**
     * @param employeePosition the employeePosition to set
     */
    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    /**
     * @return the languageId
     */
    public Integer getLanguageId() {
        return languageId;
    }

    /**
     * @param languageId the languageId to set
     */
    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the religionId
     */
    public String getReligionId() {
        return religionId;
    }

    /**
     * @param religionId the religionId to set
     */
    public void setReligionId(String religionId) {
        this.religionId = religionId;
    }

    /**
     * @return the religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * @param religion the religion to set
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     * @return the schoolId
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        if (address == null) {
            address = new Address();
        }
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return the userLoginId
     */
    public Integer getUserLoginId() {
        return userLoginId;
    }

    /**
     * @param userLoginId the userLoginId to set
     */
    public void setUserLoginId(Integer userLoginId) {
        this.userLoginId = userLoginId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the resignDate
     */
    public Date getResignDate() {
        return resignDate;
    }

    /**
     * @param resignDate the resignDate to set
     */
    public void setResignDate(Date resignDate) {
        this.resignDate = resignDate;
    }

    /**
     * @return the payStructureId
     */
    public Integer getPayStructureId() {
        return payStructureId;
    }

    /**
     * @param payStructureId the payStructureId to set
     */
    public void setPayStructureId(Integer payStructureId) {
        this.payStructureId = payStructureId;
    }

    /**
     * @return the address
     */
    public Employee getEmployee() {
        Employee employee = new Employee();

        employee.setAddress(this.address);
        employee.setBirthDate(this.birthDate);
        if (this.countryCode != null && !this.countryCode.isEmpty()) {
            employee.setCountry(new Country(this.countryCode));
        }
        employee.setDateOfHire(getDateOfHire());
        employee.setEmail(getEmail());
        if (this.employeeCategoryId != null && !this.employeeCategoryId.isEmpty()) {
            employee.setEmployeeCategory(new EmployeeCategory(this.employeeCategoryId));
        }
        employee.setEmployeeId(getEmployeeId());
        if (this.employeePositionId != null && this.employeePositionId != 0) {
            employee.setEmployeePosition(new EmployeePosition(this.employeePositionId));
        }
        if (this.employeeTypeId != null && !this.employeeTypeId.isEmpty()) {
            employee.setEmployeeType(new EmployeeType(this.employeeTypeId));
        }
        employee.setGender(getGender());
        employee.setHomeTown(getHomeTown());
        employee.setIdNumber(getIdNumber());
        if (this.identificationMeansId != null && !this.identificationMeansId.isEmpty()) {
            employee.setIdentificationMeans(new IdentificationMeans(
                    this.identificationMeansId));
        }
        if (this.languageId != null && this.languageId != 0) {
            employee.setLanguage(new Language(this.languageId));
        }
        if (this.maritalStatusId != null && !this.maritalStatusId.isEmpty()) {
            employee.setMaritalStatus(new MaritalStatus(this.maritalStatusId));
        }
        employee.setMobileNo(this.mobileNo);
        employee.setModifiedBy(this.modifiedBy);
        employee.setModifiedTime(this.modifiedTime);
        employee.setOfficeNo(this.officeNo);
        employee.setOthernames(this.othernames);
        employee.setPictureUrl(this.pictureUrl);
        employee.setPayStructureId(this.payStructureId);
        if (this.religionId != null && !this.religionId.isEmpty()) {
            employee.setReligion(new Religion(religionId));
        }
        employee.setResignDate(this.resignDate);
        if (this.schoolId != null) {
            employee.setSchoolId(this.schoolId);
        }
        employee.setStaffNumber(this.staffNumber);
        if (this.stateCode != null && !this.stateCode.isEmpty()) {
            employee.setState(new State(this.stateCode));
        }
        employee.setStatus(this.status);
        employee.setSurname(this.surname);
        employee.setUserLoginId(this.userLoginId);

        return employee;
    }

    /**
     * @param address the address to set
     */
    public final void setEmployee(Employee employee) {
        if (employee == null) {
            return;
        }
        setAddress(employee.getAddress());
        setBirthDate(employee.getBirthDate());
        Country _country = employee.getCountry();
        if (_country != null) {
            setCountry(_country.getDescription());
            setCountryCode(_country.getCountryCode());
        }
        setDateOfHire(employee.getDateOfHire());
        setEmail(employee.getEmail());
        EmployeeCategory _employeeCategory = employee.getEmployeeCategory();
        if (_employeeCategory != null) {
            setEmployeeCategory(_employeeCategory.getDescription());
            setEmployeeCategoryId(_employeeCategory.getEmployeeCategoryId());
        }
        setEmployeeId(employee.getEmployeeId());
        EmployeePosition _employeePosition = employee.getEmployeePosition();
        if (_employeePosition != null) {
            setEmployeePosition(_employeePosition.getDescription());
            setEmployeePositionId(_employeePosition.getEmployeePositionId());
        }
        EmployeeType _employeeType = employee.getEmployeeType();
        if (_employeeType != null) {
            setEmployeeType(_employeeType.getDescription());
            setEmployeeTypeId(_employeeType.getEmployeeTypeId());
        }
        setGender(employee.getGender());
        setHomeTown(employee.getHomeTown());
        setIdNumber(employee.getIdNumber());
        IdentificationMeans _identificationMeans = employee.getIdentificationMeans();
        if (_identificationMeans != null) {
            setIdentificationMeans(_identificationMeans.getDescription());
            setIdentificationMeansId(_identificationMeans.getIdentificationMeansId());
        }
        Language _language = employee.getLanguage();
        if (_language != null) {
            setLanguage(_language.getLanguage());
            setLanguageId(_language.getLanguageId());
        }
        MaritalStatus _maritalStatus = employee.getMaritalStatus();
        if (_maritalStatus != null) {
            setMaritalStatus(_maritalStatus.getDescription());
            setMaritalStatusId(_maritalStatus.getMaritalStatusId());
        }
        setMobileNo(employee.getMobileNo());
        setModifiedBy(employee.getModifiedBy());
        setModifiedTime(employee.getModifiedTime());
        setOfficeNo(employee.getOfficeNo());
        setOthernames(employee.getOthernames());
        setPictureUrl(employee.getPictureUrl());
        setPayStructureId(employee.getPayStructureId());
        Religion _religion = employee.getReligion();
        if (_religion != null) {
            setReligion(_religion.getDescription());
            setReligionId(_religion.getReligionId());
        }
        setResignDate(employee.getResignDate());
        setSchoolId(employee.getSchoolId());

        setStaffNumber(employee.getStaffNumber());
        State _state = employee.getState();
        if (_state != null) {
            setState(_state.getName());
            setStateCode(_state.getStateCode());
        }
        setStatus(employee.getStatus());
        setSurname(employee.getSurname());
        setUserLoginId(employee.getUserLoginId());

    }

    /**
     * @return a UserLogin for this employee
     */
    public UserLogin getUserLogin() {
        UserLogin userLogin = new UserLogin();

        userLogin.setEmail(this.email);
        userLogin.setModifiedBy(this.modifiedBy);
        userLogin.setModifiedTime(this.modifiedTime);
        userLogin.setPhoneNumber(this.mobileNo);
        userLogin.setSchool(new School(this.schoolId));
        userLogin.setStatus(this.status);
        userLogin.setUserLoginId(this.userLoginId);
        userLogin.setUsername(this.username);

        return userLogin;
    }

    /**
     * utility to concatenate out a full name for this record
     *
     * @return String
     */
    public String getFullname() {
        return new StringBuilder(this.surname == null ? "" : this.surname).
                append(" ").append(this.othernames == null ? "" : this.othernames).
                toString();
    }

    /**
     * @return the pictureUrl
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * @param pictureUrl the pictureUrl to set
     */
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
