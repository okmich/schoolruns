/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "employee_id")
    private Integer employeeId;
    @Basic(optional = false)
    @Column(name = "staff_number")
    private String staffNumber;
    @Basic(optional = false)
    @Column(name = "date_of_hire")
    @Temporal(TemporalType.DATE)
    private Date dateOfHire;
    @Basic(optional = false)
    @Column(name = "surname")
    private String surname;
    @Basic(optional = false)
    @Column(name = "othernames")
    private String othernames;
    @Basic(optional = false)
    @Column(name = "id_number")
    private String idNumber;
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(name = "mobile_no")
    private String mobileNo;
    @Column(name = "office_no")
    private String officeNo;
    @Column(name = "email")
    private String email;
    @Column(name = "gender")
    private String gender;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Column(name = "resign_date")
    @Temporal(TemporalType.DATE)
    private Date resignDate;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "home_town")
    private String homeTown;
    @Column(name = "pay_structure_id")
    private Integer payStructureId;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "school_id")
    @Basic(optional = false)
    private Integer schoolId;
    @Column(name = "user_login_id")
    @Basic(optional = false)
    private Integer userLoginId;
    @JoinColumn(name = "country_code", referencedColumnName = "country_code")
    @ManyToOne
    private Country country;
    @JoinColumn(name = "state_code", referencedColumnName = "state_code")
    @ManyToOne
    private State state;
    @Basic(optional = false)
    @JoinColumn(name = "identification_means_id", referencedColumnName = "identification_means_id")
    @ManyToOne(optional = false)
    private IdentificationMeans identificationMeans;
    @JoinColumn(name = "marital_status_id", referencedColumnName = "marital_status_id")
    @ManyToOne
    private MaritalStatus maritalStatus;
    @JoinColumn(name = "employee_type_id", referencedColumnName = "employee_type_id")
    @ManyToOne
    private EmployeeType employeeType;
    @JoinColumn(name = "employee_category_id", referencedColumnName = "employee_category_id")
    @ManyToOne(optional = false)
    private EmployeeCategory employeeCategory;
    @JoinColumn(name = "employee_position_id", referencedColumnName = "employee_position_id")
    @ManyToOne()
    private EmployeePosition employeePosition;
    @JoinColumn(name = "first_language_id", referencedColumnName = "language_id")
    @ManyToOne
    private Language language;
    @JoinColumn(name = "religion_id", referencedColumnName = "religion_id")
    @ManyToOne
    private Religion religion;
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;

    public Employee() {
    }

    public Employee(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public Date getDateOfHire() {
        return dateOfHire;
    }

    public void setDateOfHire(Date dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOthernames() {
        return othernames;
    }

    public void setOthernames(String othernames) {
        this.othernames = othernames;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOfficeNo() {
        return officeNo;
    }

    public void setOfficeNo(String officeNo) {
        this.officeNo = officeNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public IdentificationMeans getIdentificationMeans() {
        return identificationMeans;
    }

    public void setIdentificationMeans(IdentificationMeans identificationMeans) {
        this.identificationMeans = identificationMeans;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public EmployeeCategory getEmployeeCategory() {
        return employeeCategory;
    }

    public void setEmployeeCategory(EmployeeCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    public Religion getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public Address getAddress() {
        return address;
    }

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
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
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
     * @return the employeePosition
     */
    public EmployeePosition getEmployeePosition() {
        return employeePosition;
    }

    /**
     * @param employeePosition the employeePosition to set
     */
    public void setEmployeePosition(EmployeePosition employeePosition) {
        this.employeePosition = employeePosition;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeId != null ? employeeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.employeeId == null && other.employeeId != null) || (this.employeeId != null && !this.employeeId.equals(other.employeeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.Employee[employeeId=" + employeeId + "]";
    }

    @PrePersist
    @PreUpdate
    private void updateTrigger() {
        setSurname(getSurname().toUpperCase());
        setOthernames(othernames.toUpperCase());
    }

    /**
     * utility to concatenate out a full name for this record
     *
     * @return String
     */
    public String getFullname() {
        return new StringBuilder(this.surname == null ? "" : this.surname).
                append(" ").append(this.othernames == null ? "" : this.othernames).toString();
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
