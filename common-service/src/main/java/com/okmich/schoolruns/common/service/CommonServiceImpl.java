/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.exception.ErrorConstants;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.*;
import com.okmich.schoolruns.common.service.repo.*;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Service("commonService")
@Transactional
public class CommonServiceImpl implements CommonService {

    /**
     * academicYearRepo
     */
    @Autowired
    private AcademicYearRepo academicYearRepo;
    /**
     * allocationTypeRepo
     */
    @Autowired
    private AllocationTypeRepo allocationTypeRepo;
    /**
     * assignmentTypeRepo
     */
    @Autowired
    private AssignmentTypeRepo assignmentTypeRepo;
    /**
     * cityRepo
     */
    @Autowired
    private CityRepo cityRepo;
    /**
     * ClassTypeRepo
     */
    @Autowired
    private BloodGroupRepo bloodGroupRepo;
    /**
     * ClassTypeRepo
     */
    @Autowired
    private ClassTypeRepo classTypeRepo;
    /**
     * clubRepo
     */
    @Autowired
    private ClubTypeRepo clubTypeRepo;
    /**
     * clubRepo
     */
    @Autowired
    private CtrlParameterRepo ctrlParameterRepo;
    /**
     * countryRepo
     */
    @Autowired
    private CountryRepo countryRepo;
    /**
     * educationalQualificationRepo
     */
    @Autowired
    private EducationalQualificationRepo educationalQualificationRepo;
    /**
     * employeePositionRepo
     */
    @Autowired
    private EmployeePositionRepo employeePositionRepo;
    /**
     * employeeCategoryRepo
     */
    @Autowired
    private EmployeeCategoryRepo employeeCategoryRepo;
    /**
     * employeeTypeRepo
     */
    @Autowired
    private EmployeeTypeRepo employeeTypeRepo;
    /**
     * {@link eventTypeRepo}
     */
    @Autowired
    private EventTypeRepo eventTypeRepo;
    /**
     * examTypeRepo
     */
    @Autowired
    private ExamTypeRepo examTypeRepo;
    /**
     * {@link facilityRepo}
     */
    @Autowired
    private FacilityRepo facilityRepo;
    /**
     * gradeLevelRepo
     */
    @Autowired
    private GradeLevelRepo gradeLevelRepo;
    /**
     * gradeBandRepo
     */
    @Autowired
    private GradeBandRepo gradeBandRepo;
    /**
     * languageRepo
     */
    @Autowired
    private IdentificationMeansRepo identificationMeansRepo;
    /**
     * languageRepo
     */
    @Autowired
    private LanguageRepo languageRepo;
    /**
     * maritalStatusRepo
     */
    @Autowired
    private MaritalStatusRepo maritalStatusRepo;
    /**
     * messageChannelRepo
     */
    @Autowired
    private MessageChannelRepo messageChannelRepo;
    /**
     * moduleRepo
     */
    @Autowired
    private ModuleRepo moduleRepo;
    /**
     * participantCategoryRepo
     */
    @Autowired
    private ParticipantCategoryRepo participantCategoryRepo;
    /**
     * prefectTypeRepo
     */
    @Autowired
    private PrefectTypeRepo prefectTypeRepo;
    /**
     * professionalQualificationRepo
     */
    @Autowired
    private ProfessionalQualificationRepo professionalQualificationRepo;
    /**
     * religionRepo
     */
    @Autowired
    private ReligionRepo religionRepo;
    /**
     * sectionRepo
     */
    @Autowired
    private SectionRepo sectionRepo;
    /**
     * stateRepo
     */
    @Autowired
    private StateRepo stateRepo;
    /**
     * sportCategoryRepo
     */
    @Autowired
    private SportCategoryRepo sportCategoryRepo;
    /**
     * stateRepo
     */
    @Autowired
    private StreamRepo streamRepo;
    /**
     * studyMediumRepo
     */
    @Autowired
    private StudyMediumRepo studyMediumRepo;
    /**
     * subjectRepo
     */
    @Autowired
    private SubjectRepo subjectRepo;
    /**
     * termRepo
     */
    @Autowired
    private TermRepo termRepo;
    /**
     * titleRepo
     */
    @Autowired
    private TitleRepo titleRepo;
    /**
     * warningLevelRepo
     */
    @Autowired
    private WarningLevelRepo warningLevelRepo;
    /**
     * weekdayRepo
     */
    @Autowired
    private WeekdayRepo weekdayRepo;

    public CommonServiceImpl() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AcademicYear saveAcademicYear(AcademicYear academicYear) throws BusinessException {
        academicYear.setModifiedTime(new Date());
        try {
            academicYear = academicYearRepo.save(academicYear);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return academicYear;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public AcademicYear findAcademicYear(Integer academicYearId) {
        AcademicYear academicYear = academicYearRepo.findOne(academicYearId);
        return academicYear;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AcademicYear> findAcademicYears() {
        return academicYearRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AllocationType saveAllocationType(AllocationType allocationType) throws BusinessException {
        allocationType.setModifiedTime(new Date());
        try {
            allocationType = allocationTypeRepo.save(allocationType);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return allocationType;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public AllocationType findAllocationType(Integer allocationTypeId) {
        return allocationTypeRepo.findOne(allocationTypeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<AllocationType> findAllocationTypes() {
        return allocationTypeRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public AssignmentType saveAssignmentType(AssignmentType assignmentType) throws BusinessException {
        try {
            assignmentType = assignmentTypeRepo.save(assignmentType);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return assignmentType;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public AssignmentType findAssignmentType(String assignmentTypeId) {
        return assignmentTypeRepo.findOne(assignmentTypeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<AssignmentType> findAssignmentTypes() {
        return assignmentTypeRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public BloodGroup saveBloodGroup(BloodGroup bloodGroup) throws BusinessException {
        bloodGroup.setModifiedTime(new Date());
        try {
            bloodGroup = bloodGroupRepo.save(bloodGroup);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return bloodGroup;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public BloodGroup findBloodGroup(String bloodGroupId) {
        return bloodGroupRepo.findOne(bloodGroupId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<BloodGroup> findBloodGroups() {
        return bloodGroupRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public City saveCity(City city) throws BusinessException {
        city.setModifiedTime(new Date());
        try {
            city = cityRepo.save(city);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return city;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public City findCity(Integer cityId) {
        City city = cityRepo.findOne(cityId);
        return city;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<City> findCitiesByState(String stateCode) {
        if (stateCode == null) {
            return cityRepo.findAll();
        } else {
            return cityRepo.findCityByState(stateCode);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ClassType saveClassType(ClassType classType) throws BusinessException {
        classType.setModifiedTime(new Date());
        try {
            classType = classTypeRepo.save(classType);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return classType;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ClassType findClassType(Integer classTypeId) {
        return classTypeRepo.findOne(classTypeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ClassType> findClassTypes() {
        return classTypeRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ClubType saveClubType(ClubType clubType) throws BusinessException {
        try {
            clubType = clubTypeRepo.save(clubType);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return clubType;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ClubType findClubType(Integer clubTypeId) {
        ClubType clubType = clubTypeRepo.findOne(clubTypeId);
        return clubType;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ClubType> findClubTypes() {
        return clubTypeRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Country saveCountry(Country country) throws BusinessException {
        country.setModifiedTime(new Date());
        try {
            country = countryRepo.save(country);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return country;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Country findCountry(String countryCode) {
        Country country = countryRepo.findOne(countryCode);
        return country;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Country> findCountrys() {
        return countryRepo.findAll(new Sort("description"));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CtrlParameter saveCtrlParameter(CtrlParameter ctrlParameter) throws BusinessException {
        ctrlParameter.setModifiedTime(new Date());
        try {
            ctrlParameter = ctrlParameterRepo.save(ctrlParameter);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return ctrlParameter;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public CtrlParameter findCtrlParameter(String code) {
        CtrlParameter ctrlParameter = ctrlParameterRepo.findOne(code);
        return ctrlParameter;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String findCtrlParameterValue(String code) {
        CtrlParameter ctrlParameter = ctrlParameterRepo.findOne(code);
        if (ctrlParameter != null && CommonConstants.STATUS_ACTIVE.equals(
                ctrlParameter.getStatus())) {
            return ctrlParameter.getValue();
        }
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CtrlParameter> findCtrlParameters() {
        return ctrlParameterRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public EducationalQualification saveEducationalQualification(
            EducationalQualification educationalQualification) throws BusinessException {
        educationalQualification.setModifiedTime(new Date());
        try {
            educationalQualification = educationalQualificationRepo.
                    save(educationalQualification);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return educationalQualification;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public EducationalQualification findEducationalQualification(
            String educationalQualificationId) {
        EducationalQualification educationalQualification =
                educationalQualificationRepo.findOne(educationalQualificationId);
        return educationalQualification;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<EducationalQualification> findEducationalQualifications() {
        return educationalQualificationRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EmployeeCategory saveEmployeeCategory(EmployeeCategory employeeCategory)
            throws BusinessException {
        employeeCategory.setModifiedTime(new Date());
        try {
            employeeCategory = employeeCategoryRepo.save(employeeCategory);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return employeeCategory;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EmployeeCategory findEmployeeCategory(String id) {
        return employeeCategoryRepo.findOne(id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<EmployeeCategory> findEmployeeCategories() {
        return employeeCategoryRepo.findActiveEmployeeCategories();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EmployeeType saveEmployeeType(EmployeeType employeeType) throws BusinessException {
        employeeType.setModifiedTime(new Date());
        try {
            employeeType = employeeTypeRepo.save(employeeType);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return employeeType;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EmployeeType findEmployeeType(String id) {
        EmployeeType _employeeType = employeeTypeRepo.findOne(id);
        return _employeeType;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<EmployeeType> findEmployeeTypes() {
        return employeeTypeRepo.findActiveEmployeeType();
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public EmployeePosition saveEmployeePosition(EmployeePosition employeePosition)
            throws BusinessException {
        employeePosition.setModifiedTime(new Date());
        try {
            employeePosition = employeePositionRepo.save(employeePosition);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return employeePosition;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public EmployeePosition findEmployeePosition(Integer id) {
        EmployeePosition _employeePosition = employeePositionRepo.findOne(id);
        return _employeePosition;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<EmployeePosition> findEmployeePositions() {
        return employeePositionRepo.findActiveEmployeePosition();
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public EventType saveEventType(EventType eventType) throws BusinessException {
        eventType.setModifiedTime(new Date());
        if (eventType.getStatus() == null) {
            eventType.setStatus(CommonConstants.STATUS_ACTIVE);
        }
        try {
            eventType = eventTypeRepo.save(eventType);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return eventType;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public EventType findEventType(Integer id) {
        return eventTypeRepo.findOne(id);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<EventType> findEventTypes() {
        return eventTypeRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public ExamType saveExamType(ExamType examType) throws BusinessException {
        try {
            examType = examTypeRepo.save(examType);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return examType;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public ExamType findExamType(Integer id) {
        return examTypeRepo.findOne(id);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<ExamType> findExamTypes() {
        return examTypeRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public Facility saveFacility(Facility facility) throws BusinessException {
        try {
            facility.setModifiedTime(new Date());
            facility = facilityRepo.save(facility);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return facility;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public Facility findFacility(Integer facilityId) {
        return facilityRepo.findOne(facilityId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<Facility> findFacilities() {
        return facilityRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public GradeBand saveGradeBand(GradeBand gradeBand) throws BusinessException {
        //check duplicate code
        //check overlapping min and max scores
        gradeBand.setModifiedTime(new Date());
        try {
            gradeBand = gradeBandRepo.save(gradeBand);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return gradeBand;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public GradeBand findGradeBand(Integer gradeBandId) {
        return gradeBandRepo.findOne(gradeBandId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public GradeBand findGradeBand(float scoreValue) {
        return gradeBandRepo.findGradeBand(scoreValue);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<GradeBand> findGradeBands() {
        return gradeBandRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public GradeLevel saveGradeLevel(GradeLevel gradeLevel) throws BusinessException {
        gradeLevel.setModifiedTime(new Date());
        try {
            gradeLevel = gradeLevelRepo.save(gradeLevel);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return gradeLevel;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GradeLevel findGradeLevel(Integer gradeLevelId) {
        GradeLevel gradeLevel = gradeLevelRepo.findOne(gradeLevelId);
        return gradeLevel;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public GradeLevel findGradeLevel(String description) {
        List<GradeLevel> gradeLevels = gradeLevelRepo.findGradeByGradeName(description);
        if (gradeLevels.isEmpty()) {
            return null;
        }
        return gradeLevels.get(0);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<GradeLevel> findGradeLevels() {
        return gradeLevelRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public IdentificationMeans saveIdentificationMeans(IdentificationMeans idMeans)
            throws BusinessException {
        try {
            idMeans = identificationMeansRepo.save(idMeans);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return idMeans;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public IdentificationMeans findIdentificationMeans(Integer idMeansId) {
        return identificationMeansRepo.findOne(idMeansId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<IdentificationMeans> findIdentificationMeans() {
        return identificationMeansRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Language saveLanguage(Language language) throws BusinessException {
        language.setModifiedTime(new Date());
        try {
            language = languageRepo.save(language);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return language;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Language findLanguage(Integer languageId) {
        Language language = languageRepo.findOne(languageId);
        return language;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Language> findLanguages() {
        return languageRepo.findAll(new Sort(new Sort.Order("language")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public MaritalStatus saveMaritalStatus(MaritalStatus maritalStatus) throws BusinessException {
        maritalStatus.setModifiedTime(new Date());
        try {
            maritalStatus = maritalStatusRepo.save(maritalStatus);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return maritalStatus;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public MaritalStatus findMaritalStatus(Integer maritalStatusId) {
        MaritalStatus maritalStatus = maritalStatusRepo.findOne(maritalStatusId);
        return maritalStatus;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<MaritalStatus> findMaritalStatuss() {
        return maritalStatusRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<MessageChannel> findMessageChannels() {
        return messageChannelRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Module findModule(Integer moduleId) {
        return moduleRepo.findOne(moduleId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<Module> findModules() {
        return moduleRepo.findAll(new Sort(new Sort.Order("name")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ParticipantCategory saveParticipantCategory(
            ParticipantCategory participantCategory) throws BusinessException {
        try {
            participantCategory = participantCategoryRepo.save(
                    participantCategory);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return participantCategory;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ParticipantCategory findParticipantCategory(String id) {
        return participantCategoryRepo.findOne(id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<ParticipantCategory> findParticipantCategories() {
        return participantCategoryRepo.findAll(
                new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public PrefectType savePrefectType(PrefectType prefectType) throws BusinessException {
        prefectType.setModifiedTime(new Date());
        try {
            prefectType = prefectTypeRepo.save(prefectType);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return prefectType;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public PrefectType findPrefectType(Integer prefectTypeId) {
        return prefectTypeRepo.findOne(prefectTypeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<PrefectType> findPrefectTypes() {
        return prefectTypeRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProfessionalQualification saveProfessionalQualification(
            ProfessionalQualification professionalQualification) throws BusinessException {
        professionalQualification.setModifiedTime(new Date());
        try {
            professionalQualification = professionalQualificationRepo.
                    save(professionalQualification);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return professionalQualification;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ProfessionalQualification findProfessionalQualification(
            Integer professionalQualificationId) {
        ProfessionalQualification professionalQualification =
                professionalQualificationRepo.findOne(professionalQualificationId);
        return professionalQualification;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ProfessionalQualification> findProfessionalQualifications() {
        return professionalQualificationRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Religion saveReligion(Religion religion) throws BusinessException {
        religion.setModifiedTime(new Date());
        try {
            religion = religionRepo.save(religion);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return religion;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Religion findReligion(Integer religionId) {
        Religion religion = religionRepo.findOne(religionId);
        return religion;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Religion> findReligions() {
        return religionRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Section saveSection(Section section) throws BusinessException {
        section.setModifiedTime(new Date());
        try {
            section = sectionRepo.save(section);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return section;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Section findSection(Integer sectionId) {
        return sectionRepo.findOne(sectionId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public Section findSection(String desc) {
        List<Section> sections = sectionRepo.findAnySectionByName(desc);
        if (sections.isEmpty()) {
            return null;
        }
        return sections.get(0);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Section> findSections() {
        return sectionRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SportCategory saveSportCategory(SportCategory sportCategory) throws BusinessException {
        try {
            sportCategory = sportCategoryRepo.save(sportCategory);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return sportCategory;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public SportCategory findSportCategory(Integer sportCategoryId) {
        return sportCategoryRepo.findOne(sportCategoryId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<SportCategory> findSportCategories() {
        return sportCategoryRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public State saveStates(State states) throws BusinessException {
        states.setModifiedTime(new Date());
        try {
            states = stateRepo.save(states);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return states;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public State findState(String stateCode) {
        State states = stateRepo.findOne(stateCode);
        return states;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<State> findStatess() {
        return stateRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Stream saveStream(Stream stream) throws BusinessException {
        stream.setModifiedTime(new Date());
        try {
            stream = streamRepo.save(stream);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return stream;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public Stream findStream(Integer streamId) {
        return streamRepo.findOne(streamId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public Stream findStream(String desc) {
        List<Stream> streams = streamRepo.findAnyStreamByName(desc);
        if (streams.isEmpty()) {
            return null;
        }
        return streams.get(0);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<Stream> findStreams() {
        return streamRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public StudyMedium saveStudyMedium(StudyMedium studyMedium) throws BusinessException {
        studyMedium.setModifiedTime(new Date());
        try {
            studyMedium = studyMediumRepo.save(studyMedium);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return studyMedium;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public StudyMedium findStudyMedium(Integer studyMediumId) {
        StudyMedium studyMedium = studyMediumRepo.findOne(studyMediumId);
        return studyMedium;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<StudyMedium> findStudyMediums() {
        return studyMediumRepo.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Subject saveSubject(Subject subject) throws BusinessException {
        subject.setModifiedTime(new Date());
        try {
            subject = subjectRepo.save(subject);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return subject;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Subject findSubject(Integer subjectId) {
        Subject subject = subjectRepo.findOne(subjectId);
        return subject;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public Subject findSubject(String description) {
        List<Subject> subjects = subjectRepo.findSubjectByDesc(description);
        if (subjects.isEmpty()) {
            return null;
        }
        return subjects.get(0);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Subject> findSubjects() {
        return subjectRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Term saveTerm(Term term) throws BusinessException {
        term.setModifiedTime(new Date());
        try {
            term = termRepo.save(term);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return term;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Term findTerm(Integer termId) {
        Term term = termRepo.findOne(termId);
        return term;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Term> findTerms() {
        return termRepo.findAll();
    }

    @Override
    public Title saveTitle(Title title) throws BusinessException {
        if (titleRepo.findOne(title.getTitleCode()) != null) {
            throw new BusinessException(ErrorConstants.ERROR_CODE_ALREADY_EXISTS);
        }
        if (!titleRepo.getTitleByName(title.getDescription()).isEmpty()) {
            throw new BusinessException(ErrorConstants.ERROR_DESC_ALREADY_EXISTS);
        }
        try {
            title = titleRepo.save(title);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return title;
    }

    @Override
    @Transactional(readOnly = true)
    public Title findTitle(String titleCode) {
        return titleRepo.findOne(titleCode);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<Title> findTitles() {
        return titleRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public WarningLevel saveWarningLevel(WarningLevel warningLevel) throws BusinessException {
        try {
            warningLevel = warningLevelRepo.save(warningLevel);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return warningLevel;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public WarningLevel findWarningLevel(Integer warningLevelId) {
        WarningLevel warningLevel = warningLevelRepo.findOne(warningLevelId);
        return warningLevel;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<WarningLevel> findWarningLevels() {
        return warningLevelRepo.findAll(new Sort(new Sort.Order("description")));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Weekday> findWeekdays() {
        return weekdayRepo.findAll();
    }
}
