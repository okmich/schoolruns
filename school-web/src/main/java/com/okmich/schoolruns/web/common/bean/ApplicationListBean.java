/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.bean;

import com.okmich.common.web.util.staticlist.SmallListData;
import com.okmich.common.web.util.staticlist.SmallListManager;
import com.okmich.common.web.util.staticlist.SmallListTypes;
import com.okmich.schoolruns.common.entity.*;
import com.okmich.schoolruns.common.service.CommonService;
import com.okmich.schoolruns.security.service.SecurityService;
import com.okmich.schoolruns.web.common.FacesUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;

/**
 *
 * @author Michael
 */
@ManagedBean
@ApplicationScoped
public class ApplicationListBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{securityService}")
    private SecurityService securityService;
    private List<AcademicYear> academicYearList;
    private List<AllocationType> allocationTypeList;
    private List<AssignmentType> assignmentTypeList;
    private List<BloodGroup> bloodGroupList;
    private List<ClassType> classTypeList;
    private List<ClubType> clubTypeList;
    private List<Country> countryList;
    private List<EducationalQualification> educationalQualificationList;
    private List<EmployeeCategory> employeeCategoryList;
    private List<EmployeePosition> employeePositionList;
    private List<EmployeeType> employeeTypeList;
    private List<EventType> eventTypeList;
    private List<ExamType> examTypeList;
    private List<Facility> facilityList;
    private List<GradeLevel> gradeLevelList;
    private List<IdentificationMeans> identificationMeansList;
    private List<Language> languageList;
    private List<MaritalStatus> maritalStatusList;
    private List<MessageChannel> messageChannelList;
    private List<Menu> menuList;
    private List<Module> moduleList;
    private List<ParticipantCategory> participantCategoryList;
    private List<ProfessionalQualification> professionalQualificationList;
    private List<PrefectType> prefectTypeList;
    private List<Religion> religionList;
    private List<SportCategory> sportCategoryList;
    private List<Stream> streamList;
    private List<Section> sectionList;
    private List<Subject> subjectList;
    private List<SystemRole> systemRoleList;
    private List<Title> titleList;
    private List<Term> termList;
    private List<WarningLevel> warningLevelList;
    private List<Weekday> weekdayList;
    private List<SelectItem> statusList;
    private List<SelectItem> yesNoStatusList;
    private List<SelectItem> genderList;
    private List<SelectItem> yearList;
    private Map<String, List<SelectItem>> staticListMap;

    public ApplicationListBean() {
    }

    @PostConstruct
    public void init() {
        initializeList();
    }

    /**
     * reinitialiseApplicationList
     *
     * @return
     */
    public String reinitialiseApplicationList() {
        try {
            initializeList();
            //report to user
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "",
                    "List reloaded"));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     */
    private void initializeList() {
        setAcademicYearList(commonService.findAcademicYears());
        setAllocationTypeList(commonService.findAllocationTypes());
        setAssignmentTypeList(commonService.findAssignmentTypes());
        setBloodGroupList(commonService.findBloodGroups());
        setClassTypeList(commonService.findClassTypes());
        setClubTypeList(commonService.findClubTypes());
        setCountryList(commonService.findCountrys());
        setEducationalQualificationList(commonService.findEducationalQualifications());
        setEmployeeCategoryList(commonService.findEmployeeCategories());
        setEmployeePositionList(commonService.findEmployeePositions());
        setEmployeeTypeList(commonService.findEmployeeTypes());
        setEventTypeList(commonService.findEventTypes());
        setExamTypeList(commonService.findExamTypes());
        setFacilityList(commonService.findFacilities());
        setGradeLevelList(commonService.findGradeLevels());
        setLanguageList(commonService.findLanguages());
        setIdentificationMeansList(commonService.findIdentificationMeans());
        setMaritalStatusList(commonService.findMaritalStatuss());
        setMessageChannelList(commonService.findMessageChannels());
        setMenuList(securityService.findAllMenus());
        setModuleList(commonService.findModules());
        setParticipantCategoryList(commonService.findParticipantCategories());
        setPrefectTypeList(commonService.findPrefectTypes());
        setProfessionalQualificationList(commonService.findProfessionalQualifications());
        setReligionList(commonService.findReligions());
        setSectionList(commonService.findSections());
        setSportCategoryList(commonService.findSportCategories());
        setStreamList(commonService.findStreams());
        setSubjectList(commonService.findSubjects());
        setSystemRoleList(securityService.findAdminSystemRoles());
        setTitleList(commonService.findTitles());
        setTermList(commonService.findTerms());
        setWarningLevelList(commonService.findWarningLevels());
        setWeekdayList(commonService.findWeekdays());
    }

    /**
     *
     * @return Map<String, List<SelectItem>>
     */
    public Map<String, List<SelectItem>> getStaticListMap() {
        if (staticListMap == null) {
            Map<String, List<SmallListData>> smallListMap = SmallListManager.getSmallListMap();
            List<SmallListData> smallDataList;
            List<SelectItem> selectItemList;
            staticListMap = new HashMap<>(smallListMap.size());
            for (String keys : smallListMap.keySet()) {
                smallDataList = smallListMap.get(keys);
                selectItemList = new ArrayList<>(smallDataList.size());
                for (SmallListData data : smallDataList) {
                    selectItemList.add(new SelectItem(data.getListKey(), data.getListDescription()));
                }
                //store that entry
                staticListMap.put(keys, selectItemList);
            }
        }
        return staticListMap;
    }

    /**
     * @param commonService the commonService to set
     */
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     * @param securityService the securityService to set
     */
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * @return the academicYearList
     */
    public List<AcademicYear> getAcademicYearList() {
        return academicYearList;
    }

    /**
     * @param academicYearList the academicYearList to set
     */
    public void setAcademicYearList(List<AcademicYear> academicYearList) {
        this.academicYearList = academicYearList;
    }

    /**
     * @return the classTypeList
     */
    public List<ClassType> getClassTypeList() {
        return classTypeList;
    }

    /**
     * @param classTypeList the classTypeList to set
     */
    public void setClassTypeList(List<ClassType> classTypeList) {
        this.classTypeList = classTypeList;
    }

    /**
     * @return the clubList
     */
    public List<ClubType> getClubTypeList() {
        return clubTypeList;
    }

    /**
     * @param clubList the clubList to set
     */
    public void setClubTypeList(List<ClubType> clubTypeList) {
        this.clubTypeList = clubTypeList;
    }

    /**
     * @return the countryList
     */
    public List<Country> getCountryList() {
        return countryList;
    }

    /**
     * @param countryList the countryList to set
     */
    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    /**
     * @return the educationalQualificationList
     */
    public List<EducationalQualification> getEducationalQualificationList() {
        return educationalQualificationList;
    }

    /**
     * @param educationalQualificationList the educationalQualificationList to
     * set
     */
    public void setEducationalQualificationList(List<EducationalQualification> educationalQualificationList) {
        this.educationalQualificationList = educationalQualificationList;
    }

    /**
     * @return the languageList
     */
    public List<Language> getLanguageList() {
        return languageList;
    }

    /**
     * @param languageList the languageList to set
     */
    public void setLanguageList(List<Language> languageList) {
        this.languageList = languageList;
    }

    /**
     * @return the maritalStatusList
     */
    public List<MaritalStatus> getMaritalStatusList() {
        return maritalStatusList;
    }

    /**
     * @param maritalStatusList the maritalStatusList to set
     */
    public void setMaritalStatusList(List<MaritalStatus> maritalStatusList) {
        this.maritalStatusList = maritalStatusList;
    }

    /**
     * @return the moduleList
     */
    public List<Module> getModuleList() {
        return moduleList;
    }

    /**
     * @param moduleList the moduleList to set
     */
    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    /**
     * @return the religionList
     */
    public List<Religion> getReligionList() {
        return religionList;
    }

    /**
     * @param religionList the religionList to set
     */
    public void setReligionList(List<Religion> religionList) {
        this.religionList = religionList;
    }

    /**
     * @return the sectionList
     */
    public List<Section> getSectionList() {
        return sectionList;
    }

    /**
     * @param sectionList the sectionList to set
     */
    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    /**
     * @return the subjectList
     */
    public List<Subject> getSubjectList() {
        return subjectList;
    }

    /**
     * @param subjectList the subjectList to set
     */
    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    /**
     * @return the termList
     */
    public List<Term> getTermList() {
        return termList;
    }

    /**
     * @param termList the termList to set
     */
    public void setTermList(List<Term> termList) {
        this.termList = termList;
    }

    /**
     * @return the genericStatusList
     */
    public List<SelectItem> getStatusList() {
        return getStaticListMap().get(SmallListTypes.SMALLLIST_STATUS_TYPE);
    }

    /**
     * @param genericStatusList the genericStatusList to set
     */
    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    /**
     * @return the menuList
     */
    public List<Menu> getMenuList() {
        return menuList;
    }

    /**
     * @param menuList the menuList to set
     */
    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    /**
     * @return the facilityList
     */
    public List<Facility> getFacilityList() {
        return facilityList;
    }

    /**
     * @param facilityList the facilityList to set
     */
    public void setFacilityList(List<Facility> facilityList) {
        this.facilityList = facilityList;
    }

    /**
     * @return the yesNoStatusList
     */
    public List<SelectItem> getYesNoStatusList() {
        return getStaticListMap().get(SmallListTypes.SMALLLIST_NO_YES_TYPE);
    }

    /**
     * @param yesNoStatusList the yesNoStatusList to set
     */
    public void setYesNoStatusList(List<SelectItem> yesNoStatusList) {
        this.yesNoStatusList = yesNoStatusList;
    }

    /**
     * @return the bloodGroupList
     */
    public List<BloodGroup> getBloodGroupList() {
        return bloodGroupList;
    }

    /**
     * @param bloodGroupList the bloodGroupList to set
     */
    public void setBloodGroupList(List<BloodGroup> bloodGroupList) {
        this.bloodGroupList = bloodGroupList;
    }

    /**
     * @return the prefectTypeList
     */
    public List<PrefectType> getPrefectTypeList() {
        return prefectTypeList;
    }

    /**
     * @param prefectTypeList the prefectTypeList to set
     */
    public void setPrefectTypeList(List<PrefectType> prefectTypeList) {
        this.prefectTypeList = prefectTypeList;
    }

    /**
     * @return the genderList
     */
    public List<SelectItem> getGenderList() {
        return getStaticListMap().get(SmallListTypes.SMALLLIST_GENDER_TYPE);
    }

    /**
     * @param genderList the genderList to set
     */
    public void setGenderList(List<SelectItem> genderList) {
        this.genderList = genderList;
    }

    /**
     * @return the identificationMeansList
     */
    public List<IdentificationMeans> getIdentificationMeansList() {
        return identificationMeansList;
    }

    /**
     * @param identificationMeansList the identificationMeansList to set
     */
    public void setIdentificationMeansList(List<IdentificationMeans> identificationMeansList) {
        this.identificationMeansList = identificationMeansList;
    }

    /**
     * @return the employeeCategoryList
     */
    public List<EmployeeCategory> getEmployeeCategoryList() {
        return employeeCategoryList;
    }

    /**
     * @param employeeCategoryList the employeeCategoryList to set
     */
    public void setEmployeeCategoryList(List<EmployeeCategory> employeeCategoryList) {
        this.employeeCategoryList = employeeCategoryList;
    }

    /**
     * @return the employeePositionList
     */
    public List<EmployeePosition> getEmployeePositionList() {
        return employeePositionList;
    }

    /**
     * @param employeePositionList the employeePositionList to set
     */
    public void setEmployeePositionList(List<EmployeePosition> employeePositionList) {
        this.employeePositionList = employeePositionList;
    }

    /**
     * @return the employeeTypeList
     */
    public List<EmployeeType> getEmployeeTypeList() {
        return employeeTypeList;
    }

    /**
     * @param employeeTypeList the employeeTypeList to set
     */
    public void setEmployeeTypeList(List<EmployeeType> employeeTypeList) {
        this.employeeTypeList = employeeTypeList;
    }

    /**
     * @return the gradeLevelList
     */
    public List<GradeLevel> getGradeLevelList() {
        return gradeLevelList;
    }

    /**
     * @param gradeLevelList the gradeLevelList to set
     */
    public void setGradeLevelList(List<GradeLevel> gradeLevelList) {
        this.gradeLevelList = gradeLevelList;
    }

    /**
     * @return the streamList
     */
    public List<Stream> getStreamList() {
        return streamList;
    }

    /**
     * @param streamList the streamList to set
     */
    public void setStreamList(List<Stream> streamList) {
        this.streamList = streamList;
    }

    /**
     * @return the allocationTypeList
     */
    public List<AllocationType> getAllocationTypeList() {
        return allocationTypeList;
    }

    /**
     * @param allocationTypeList the allocationTypeList to set
     */
    public void setAllocationTypeList(List<AllocationType> allocationTypeList) {
        this.allocationTypeList = allocationTypeList;
    }

    /**
     * @return the eventTypeList
     */
    public List<EventType> getEventTypeList() {
        return eventTypeList;
    }

    /**
     * @param eventTypeList the eventTypeList to set
     */
    public void setEventTypeList(List<EventType> eventTypeList) {
        this.eventTypeList = eventTypeList;
    }

    /**
     * @return the messageChannelList
     */
    public List<MessageChannel> getMessageChannelList() {
        return messageChannelList;
    }

    /**
     * @param messageChannelList the messageChannelList to set
     */
    public void setMessageChannelList(List<MessageChannel> messageChannelList) {
        this.messageChannelList = messageChannelList;
    }

    /**
     * @return the participantCategoryList
     */
    public List<ParticipantCategory> getParticipantCategoryList() {
        return participantCategoryList;
    }

    /**
     * @param participantCategoryList the participantCategoryList to set
     */
    public void setParticipantCategoryList(List<ParticipantCategory> participantCategoryList) {
        this.participantCategoryList = participantCategoryList;
    }

    /**
     * @return the examTypeList
     */
    public List<ExamType> getExamTypeList() {
        return examTypeList;
    }

    /**
     * @param examTypeList the examTypeList to set
     */
    public void setExamTypeList(List<ExamType> examTypeList) {
        this.examTypeList = examTypeList;
    }

    /**
     * @return the assignmentTypeList
     */
    public List<AssignmentType> getAssignmentTypeList() {
        return assignmentTypeList;
    }

    /**
     * @param assignmentTypeList the assignmentTypeList to set
     */
    public void setAssignmentTypeList(List<AssignmentType> assignmentTypeList) {
        this.assignmentTypeList = assignmentTypeList;
    }

    /**
     * @return the warningLevelList
     */
    public List<WarningLevel> getWarningLevelList() {
        return warningLevelList;
    }

    /**
     * @param warningLevelList the warningLevelList to set
     */
    public void setWarningLevelList(List<WarningLevel> warningLevelList) {
        this.warningLevelList = warningLevelList;
    }

    /**
     * @return the sportCategoryList
     */
    public List<SportCategory> getSportCategoryList() {
        return sportCategoryList;
    }

    /**
     * @param sportCategoryList the sportCategoryList to set
     */
    public void setSportCategoryList(List<SportCategory> sportCategoryList) {
        this.sportCategoryList = sportCategoryList;
    }

    /**
     * @return the professionalQualificationList
     */
    public List<ProfessionalQualification> getProfessionalQualificationList() {
        return professionalQualificationList;
    }

    /**
     * @param professionalQualificationList the professionalQualificationList to
     * set
     */
    public void setProfessionalQualificationList(List<ProfessionalQualification> professionalQualificationList) {
        this.professionalQualificationList = professionalQualificationList;
    }

    /**
     * @return the titleList
     */
    public List<Title> getTitleList() {
        return titleList;
    }

    /**
     * @param titleList the titleList to set
     */
    public void setTitleList(List<Title> titleList) {
        this.titleList = titleList;
    }

    /**
     * @return the weekdayList
     */
    public List<Weekday> getWeekdayList() {
        return weekdayList;
    }

    /**
     * @param weekdayList the weekdayList to set
     */
    public void setWeekdayList(List<Weekday> weekdayList) {
        this.weekdayList = weekdayList;
    }

    /**
     * @return the systemRoleList
     */
    public List<SystemRole> getSystemRoleList() {
        return systemRoleList;
    }

    /**
     * @param systemRoleList the systemRoleList to set
     */
    public void setSystemRoleList(List<SystemRole> systemRoleList) {
        this.systemRoleList = systemRoleList;
    }

    /**
     * @return the yearList
     */
    public List<SelectItem> getYearList() {
        if (yearList == null) {
            int thisyear = Calendar.getInstance().get(Calendar.YEAR);
            yearList = new ArrayList<>();
            for (; thisyear >= 1930; thisyear--) {
                yearList.add(new SelectItem(thisyear, String.valueOf(thisyear)));
            }
        }
        return yearList;
    }
}
