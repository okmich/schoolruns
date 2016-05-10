/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean;

import com.okmich.common.entity.criteria.WCString;
import com.okmich.common.exception.BusinessException;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.calendar.service.SchoolCalendarService;
import com.okmich.schoolruns.calendar.service.data.SchoolCalendarData;
import com.okmich.schoolruns.common.entity.Facility;
import com.okmich.schoolruns.common.entity.Module;
import com.okmich.schoolruns.common.entity.School;
import com.okmich.schoolruns.common.entity.SchoolPref;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.common.entity.UserLogin;
import com.okmich.schoolruns.common.entity.Weekday;
import com.okmich.schoolruns.media.service.data.ImageFile;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.school.service.repo.SchoolQueryCriteria;
import com.okmich.schoolruns.security.service.SecurityService;
import com.okmich.schoolruns.security.service.repo.UserLoginQueryCriteria;
import com.okmich.schoolruns.web.admin.bean.settings.CountryBean;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Michael
 */
@ManagedBean
@ViewScoped
public final class SchoolBean extends _BaseBean {

    private static final Logger LOG = Logger.getLogger(SchoolBean.class.getName());
    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{securityService}")
    private SecurityService securityService;
    @ManagedProperty("#{schoolCalendarService}")
    private SchoolCalendarService schoolCalendarService;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{schoolRegBean}")
    private SchoolRegBean schoolRegBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private School school;
    private SchoolPref schoolPref;
    private SchoolCalendarData schoolCalendarData;
    private UserLogin userLogin;
    private List<Facility> facilityList;
    private List<Module> moduleList;
    private List<Weekday> weekdayList;
    private String schoolName;
    private String stateCode;
    private Integer cityId;
    private String contactNo;
    private UploadedFile logoFile;
    private DataModel<SchoolCalendarData> schoolCalendarModel;

    /**
     * constructor
     */
    public SchoolBean() {
        if (school == null) {
            school = new School();
        }
    }

    /**
     * @param schoolService the schoolService to set
     */
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    /**
     * @param securityService the securityService to set
     */
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * @param schoolCalendarService the schoolCalendarService to set
     */
    public void setSchoolCalendarService(SchoolCalendarService schoolCalendarService) {
        this.schoolCalendarService = schoolCalendarService;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * @param schoolRegBean1 the schoolRegBean1 to set
     */
    public void setSchoolRegBean(SchoolRegBean schoolRegBean1) {
        this.schoolRegBean = schoolRegBean1;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean1) {
        this.userLoginSessionBean = userLoginSessionBean1;
    }

    /**
     * @return the school
     */
    public School getSchool() {
        return school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(School school) {
        this.school = school;
    }

    private SchoolQueryCriteria buildQueryCriteria() {
        SchoolQueryCriteria criteria = new SchoolQueryCriteria();

        if (this.schoolName != null && !this.schoolName.trim().isEmpty()) {
            criteria.setName(this.schoolName, WCString.LIKE);
        }
        if (this.stateCode != null && !this.stateCode.trim().isEmpty()) {
            criteria.setStateCode(this.stateCode);
        }
        if (this.cityId != null && this.cityId != 0) {
            criteria.setCityId(this.cityId);
        }
        if (this.contactNo != null && !this.contactNo.trim().isEmpty()) {
            criteria.setContactNo(this.contactNo, WCString.LIKE);
        }
        return criteria;
    }

    /**
     * @return the schoolName
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * @param schoolName the schoolName to set
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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
     * @return the cityId
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the contactNo
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * @param contactNo the contactNo to set
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    /**
     * @return the schoolPref
     */
    public SchoolPref getSchoolPref() {
        if (schoolPref == null) {
            try {
                schoolPref = schoolService.findSchoolPref(
                        sessionBean.getSchool().getSchoolId());
                if (schoolPref == null) {
                    schoolPref = new SchoolPref();
                    schoolPref.setSchool(sessionBean.getSchool());
                    schoolPref.setSchoolId(sessionBean.getSchool().getSchoolId());
                }
            } catch (Exception ex) {
                //create an empty school preference anyways
            }
        }
        return schoolPref;
    }

    /**
     * @param schoolPref the schoolPref to set
     */
    public void setSchoolPref(SchoolPref schoolPref) {
        this.schoolPref = schoolPref;
    }

    /**
     * @return the userLogin
     */
    public UserLogin getUserLogin() {
        try {
            if (userLogin == null) {
                UserLoginQueryCriteria crit = new UserLoginQueryCriteria();
                crit.setSchoolId(sessionBean.getSchool().getSchoolId());
                List<UserLogin> userLogins = securityService.findUserLoginList(crit);
                if (!userLogins.isEmpty()) {
                    userLogin = userLogins.get(0);
                } else {
                    throw new Exception("Error occured retrieving data");
                }
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return userLogin;
    }

    /**
     * @param userLogin the userLogin to set
     */
    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * @return the facilityList
     */
    public List<Facility> getFacilityList() {
        try {
            if (facilityList == null) {
                facilityList = schoolService.findSchoolFacilitiesBySchool(
                        sessionBean.getSchool().getSchoolId());
            }
        } catch (Exception ex) {
            processException(ex);
        }
        return facilityList;
    }

    /**
     * @param facilityList the facilityList to set
     */
    public void setFacilityList(List<Facility> facilityList) {
        this.facilityList = facilityList;
    }

    /**
     * @return the moduleList
     */
    public List<Module> getModuleList() {
        try {
            if (moduleList == null) {
                moduleList = schoolService.findSchoolModuleBySchool(
                        sessionBean.getSchool().getSchoolId());
            }
        } catch (BusinessException ex) {
            processException(ex);
        }
        return moduleList;
    }

    /**
     * @param moduleList the moduleList to set
     */
    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    /**
     * @param weekdayList the weekdayList to set
     */
    public void setWeekdayList(List<Weekday> weekdayList) {
        this.weekdayList = weekdayList;
    }

    /**
     * @return the logoFile
     */
    public UploadedFile getLogoFile() {
        return logoFile;
    }

    /**
     * @param logoFile the logoFile to set
     */
    public void setLogoFile(UploadedFile logoFile) {
        this.logoFile = logoFile;
    }

    /**
     * @return the schoolCalendarData
     */
    public SchoolCalendarData getSchoolCalendarData() {
        if (schoolCalendarData == null) {
            setSchoolCalendarData(new SchoolCalendarData());
        }
        return schoolCalendarData;
    }

    /**
     * @param schoolCalendarData the schoolCalendarData to set
     */
    public void setSchoolCalendarData(SchoolCalendarData schoolCalendarData) {
        this.schoolCalendarData = schoolCalendarData;
    }

    /**
     * @return the schoolYearModel
     */
    public DataModel<SchoolCalendarData> getSchoolCalendarModel() {
        if (this.schoolCalendarModel == null) {
            List<SchoolCalendarData> schoolCalendarList;
            List<SchoolYear> schoolYearList = schoolCalendarService.findSchoolYears(
                    sessionBean.getSchool().getSchoolId());
            schoolCalendarList = new ArrayList<>(schoolYearList.size());
            for (SchoolYear schoolYear : schoolYearList) {
                schoolCalendarList.add(new SchoolCalendarData(schoolYear));
            }
            schoolCalendarModel = new ListDataModel<>(schoolCalendarList);
        }
        return schoolCalendarModel;
    }

    /**
     * @param schoolCalendarModel the schoolCalendarModel to set
     */
    public void setSchoolCalendarModel(DataModel<SchoolCalendarData> schoolCalendarModel) {
        this.schoolCalendarModel = schoolCalendarModel;
    }

    public String saveSchoolPref() {
        try {
            schoolPref.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            schoolPref.setSchool(sessionBean.getSchool());
            schoolPref.setSchoolId(sessionBean.getSchool().getSchoolId());
            if (getLogoFile() != null) {
                ImageFile _imageFile = schoolRegBean.saveImageFile(
                        sessionBean.getSchool(), getLogoFile());
                if (_imageFile != null) {
                    schoolPref.setLogoUrl(_imageFile.getUrl());
                }
            }
            if (schoolPref.getPicAlbumId() == null) {
                String albumId = schoolRegBean.createSchoolPicAlbum(
                        sessionBean.getSchool());
                schoolPref.setPicAlbumId(albumId);
            }
            schoolService.saveSchoolPref(schoolPref);
            return "/admin/school/schooldetails";
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Error"));
        }
        return null;
    }

    public String saveSchoolAdmin() {
        try {
            userLogin.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            securityService.saveUserLogin(userLogin);
            return "/admin/school/schooldetails";
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "Error"));
        }
        return null;
    }

    public String resetAdminPassword() {
        try {
            userLogin.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            securityService.saveUserLogin(userLogin);
            return "/admin/school/schooldetails";
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), "Error"));
        }
        return null;
    }

    public String saveSchoolModules() {
        School _school = sessionBean.getSchool();
        _school.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            schoolService.assignModuleToSchool(moduleList, _school);
            return "/admin/school/schooldetails";
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Error"));
        }
        return null;
    }

    public String saveSchoolFacilities() {
        School _school = sessionBean.getSchool();
        _school.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            schoolService.updateSchoolFacilities(facilityList, _school);
            return "/admin/school/schooldetails";
        } catch (Exception e) {
            processException(e);
        }
        return null;
    }

    public String saveSchool() {
        setSchool(sessionBean.getSchool());
        try {
            getSchool().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            schoolService.saveSchool(getSchool());
            return doSchoolDone();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "Error"));
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String findSchools() {
        try {
            List<School> schoolList = schoolService.findSchools(buildQueryCriteria());
            sessionBean.setSchoolModel(new ListDataModel<>(schoolList));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findSchool() {
        Integer schoolId = Integer.parseInt(FacesUtil.getRequestParameter("schoolId"));
        try {
            School _school = schoolService.findSchool(schoolId);
            setSchool(_school);

        } catch (Exception ex) {
            processException(ex);
            return "";
        }
        if (getSchool() != null && getSchool().getSchoolId() != null) {
            try {
                sessionBean.setSchool(getSchool());
                sessionBean.setEditMode(FacesUtil.getRequestParameter("viewMode"));
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage(), e);
                FacesUtil.getFacesContext().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
            }
            return "/admin/school/schooldetails";
        }
        return null;
    }

    public String saveSchoolYearAndTerm() {
        try {
            getSchoolCalendarData().setStatus(CommonConstants.STATUS_ACTIVE);
            getSchoolCalendarData().setModifiedBy(
                    userLoginSessionBean.getUserLogin().getUsername());
            //create the new calendar features
            setSchoolCalendarData(
                    schoolCalendarService.createKickOffYearTerm(schoolCalendarData));
            clearSchoolYearForm();
        } catch (Exception e) {
            processException(e);
        }
        return "";
    }

    /**
     * @return
     */
    public String saveSchoolYear() {
        SchoolYear schoolYear = schoolCalendarData.getSchoolYear();
        try {
            schoolYear.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            schoolYear.setSchoolId(sessionBean.getSchool().getSchoolId());
            schoolCalendarService.saveSchoolYear(schoolYear);
            clearSchoolYearForm();
            return "";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareSchoolYearForModify() {
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearSchoolYearForm() {
        setSchoolCalendarData(new SchoolCalendarData());
        setEditMode(null);
        return "";
    }

    /**
     *
     * @return
     */
    public String doSchoolDone() {
        setSchool(new School());
        setEditMode(null);
        return "/admin/school/schoolsearch";
    }
}
