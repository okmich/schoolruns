/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.student.service.data;

import com.okmich.common.BaseData;
import com.okmich.schoolruns.common.entity.Address;
import com.okmich.schoolruns.common.entity.Country;
import com.okmich.schoolruns.common.entity.Language;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.State;
import com.okmich.schoolruns.common.entity.Student;
import java.util.Date;

/**
 *
 * @author Michael
 */
public class StudentData extends BaseData {

    private Integer studentId;
    private String surname;
    private String firstname;
    private String othernames;
    private String gender;
    private Date birthDate;
    private String bloodGroup;
    private String genotype;
    private String parentId;
    private String parent;
    private Address address;
    private String countryCode;
    private String country;
    private Integer languageId;
    private String language;
    private String stateOriginCode;
    private String stateOrigin;

    public StudentData() {
    }

    public StudentData(Student student) {
        setStudent(student);
    }

    /**
     * @return the studentId
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
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
     * @return the bloodGroup
     */
    public String getBloodGroup() {
        return bloodGroup;
    }

    /**
     * @param bloodGroup the bloodGroup to set
     */
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    /**
     * @return the genotype
     */
    public String getGenotype() {
        return genotype;
    }

    /**
     * @param genotype the genotype to set
     */
    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the parent
     */
    public String getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(String parent) {
        this.parent = parent;
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
     * @return the stateOriginCode
     */
    public String getStateOriginCode() {
        return stateOriginCode;
    }

    /**
     * @param stateOriginCode the stateOriginCode to set
     */
    public void setStateOriginCode(String stateOriginCode) {
        this.stateOriginCode = stateOriginCode;
    }

    /**
     * @return the stateOrigin
     */
    public String getStateOrigin() {
        return stateOrigin;
    }

    /**
     * @param stateOrigin the stateOrigin to set
     */
    public void setStateOrigin(String stateOrigin) {
        this.stateOrigin = stateOrigin;
    }

    public final Student getStudent() {
        Student student = new Student();

        student.setAddress(getAddress());
        student.setBirthDate(this.birthDate);
        student.setBloodGroup(this.bloodGroup);
        if (getCountryCode() != null && !getCountryCode().isEmpty()) {
            student.setCountry(new Country(getCountryCode()));
        }
        student.setFirstname(this.firstname);
        student.setGender(this.gender);
        student.setGenotype(this.genotype);
        if (getLanguageId() != null && getLanguageId() != 0) {
            student.setLanguage(new Language(getLanguageId()));
        }
        student.setModifiedBy(this.modifiedBy);
        student.setModifiedTime(this.modifiedTime);
        student.setOthernames(this.othernames);
        if (getParentId() != null && !getParentId().isEmpty()) {
            student.setParent(new Parent(getParentId()));
        }
        if (getStateOriginCode() != null && !getStateOriginCode().isEmpty()) {
            student.setState(new State(getStateOriginCode()));
        }
        student.setStatus(this.status);
        student.setStudentId(this.studentId);
        student.setSurname(this.surname);

        return student;
    }

    public final void setStudent(Student student) {
        if (student == null) {
            return;
        }
        setAddress(student.getAddress());
        setBirthDate(student.getBirthDate());
        setBloodGroup(student.getBloodGroup());
        Country _country = student.getCountry();
        if (_country != null) {
            setCountry(_country.getDescription());
            setCountryCode(_country.getCountryCode());
        }
        setFirstname(student.getFirstname());
        setGender(student.getGender());
        setGenotype(student.getGenotype());
        Language _language = student.getLanguage();
        if (_language != null) {
            setLanguage(_language.getLanguage());
            setLanguageId(_language.getLanguageId());
        }
        setModifiedBy(student.getModifiedBy());
        setModifiedTime(student.getModifiedTime());
        setOthernames(student.getOthernames());
        Parent _parent = student.getParent();
        if (_parent != null) {
            setParent(_parent.getFullname());
            setParentId(_parent.getPhoneNumber());
        }
        State _state = student.getState();
        if (_state != null) {
            setStateOrigin(_state.getName());
            setStateOriginCode(_state.getStateCode());
        }
        setStatus(student.getStatus());
        setStudentId(student.getStudentId());
        setSurname(student.getSurname());
    }

    /**
     * utility to concatenate out a full name for this record
     *
     * @return String
     */
    public String getFullname() {
        return new StringBuilder(this.surname == null ? "" : this.surname).
                append(" ").append(this.firstname == null ? "" : this.firstname).toString();
    }
}
