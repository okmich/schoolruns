/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.schoolruns.common.entity.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface CommonService extends Serializable {

    /**
     *
     * @param academicYear
     * @return AcademicYear
     * @throws BusinessException
     */
    AcademicYear saveAcademicYear(AcademicYear academicYear) throws BusinessException;

    /**
     *
     * @param academicYearId
     * @return AcademicYear
     */
    AcademicYear findAcademicYear(Integer academicYearId);

    /**
     *
     * @return List<AcademicYear>
     */
    List<AcademicYear> findAcademicYears();

    /**
     *
     * @param allocationType
     * @return AllocationType
     * @throws BusinessException
     */
    AllocationType saveAllocationType(AllocationType allocationType) throws BusinessException;

    /**
     *
     * @param allocationTypeId
     * @return AllocationType
     */
    AllocationType findAllocationType(Integer allocationTypeId);

    /**
     *
     * @return List<AllocationType>
     */
    List<AllocationType> findAllocationTypes();

    /**
     *
     * @param assignmentType
     * @return AssignmentType
     * @throws BusinessException
     */
    AssignmentType saveAssignmentType(AssignmentType assignmentType) throws BusinessException;

    /**
     *
     * @param assignmentTypeCode
     * @return AssignmentType
     */
    AssignmentType findAssignmentType(String assignmentTypeCode);

    /**
     *
     * @return List<AssignmentType>
     */
    List<AssignmentType> findAssignmentTypes();

    /**
     *
     * @param bloodGroup
     * @return BloodGroup
     * @throws BusinessException
     */
    BloodGroup saveBloodGroup(BloodGroup bloodGroup) throws BusinessException;

    /**
     *
     * @param bloodGroupId
     * @return BloodGroup
     */
    BloodGroup findBloodGroup(String bloodGroupId);

    /**
     *
     * @return List<BloodGroup>
     */
    List<BloodGroup> findBloodGroups();

    /**
     *
     *
     * @param city
     * @return City
     * @throws BusinessException
     */
    City saveCity(City city) throws BusinessException;

    /**
     *
     * @param cityId
     * @return City
     */
    City findCity(Integer cityId);

    /**
     *
     * @param stateCode
     * @return List<City>
     */
    List<City> findCitiesByState(String stateCode);

    /**
     *
     * @param classType
     * @return ClassType
     * @throws BusinessException
     */
    ClassType saveClassType(ClassType classType) throws BusinessException;

    /**
     *
     * @param classTypeId
     * @return ClassType
     */
    ClassType findClassType(Integer classTypeId);

    /**
     *
     * @return List<ClassType>
     */
    List<ClassType> findClassTypes();

    /**
     *
     * @param clubType
     * @return ClubType
     * @throws BusinessException
     */
    ClubType saveClubType(ClubType clubType) throws BusinessException;

    /**
     *
     * @param clubTypeId
     * @return ClubType
     */
    ClubType findClubType(Integer clubTypeId);

    /**
     *
     * @return List<Club>
     */
    List<ClubType> findClubTypes();

    /**
     *
     * @param country
     * @return Country
     * @throws BusinessException
     */
    Country saveCountry(Country country) throws BusinessException;

    /**
     *
     * @param countryCode
     * @return Country
     */
    Country findCountry(String countryCode);

    /**
     *
     * @return List<Country>
     */
    List<Country> findCountrys();

    /**
     *
     * @param ctrlParameter
     * @return CtrlParameter
     * @throws BusinessException
     */
    CtrlParameter saveCtrlParameter(CtrlParameter ctrlParameter) throws BusinessException;

    /**
     *
     * @param code
     * @return CtrlParameter
     * @throws BusinessException
     */
    CtrlParameter findCtrlParameter(String code);

    /**
     *
     *
     * @param code
     * @return String
     */
    String findCtrlParameterValue(String code);

    /**
     *
     * @return List<CtrlParameter>
     */
    List<CtrlParameter> findCtrlParameters();

    /**
     *
     * @param educationalQualification
     * @return EducationalQualification
     * @throws BusinessException
     */
    EducationalQualification saveEducationalQualification(
            EducationalQualification educationalQualification) throws BusinessException;

    /**
     *
     * @param educationalQualificationId
     * @return EducationalQualification
     */
    EducationalQualification findEducationalQualification(
            String educationalQualificationId);

    /**
     *
     * @return List<EducationalQualification>
     */
    List<EducationalQualification> findEducationalQualifications();

    /**
     *
     * @param employeeCategory
     * @return EmployeeCategory
     * @throws BusinessException
     */
    EmployeeCategory saveEmployeeCategory(EmployeeCategory employeeCategory)
            throws BusinessException;

    /**
     *
     * @param id
     * @return EmployeeCategory
     */
    EmployeeCategory findEmployeeCategory(String id);

    /**
     *
     * @return
     */
    List<EmployeeCategory> findEmployeeCategories();

    /**
     *
     * @param employeeType
     * @return
     * @throws BusinessException
     */
    EmployeeType saveEmployeeType(EmployeeType employeeType) throws BusinessException;

    /**
     *
     * @param id
     * @return EmployeeType
     */
    EmployeeType findEmployeeType(String id);

    /**
     *
     * @return
     */
    List<EmployeeType> findEmployeeTypes();

    /**
     *
     * @param employeePosition
     * @return
     * @throws BusinessException
     */
    EmployeePosition saveEmployeePosition(EmployeePosition employeePosition) throws BusinessException;

    /**
     *
     * @param id
     * @return EmployeePosition
     */
    EmployeePosition findEmployeePosition(Integer id);

    /**
     *
     * @return
     */
    List<EmployeePosition> findEmployeePositions();

    /**
     *
     * @param eventType
     * @return EventType
     * @throws BusinessException
     */
    EventType saveEventType(EventType eventType) throws BusinessException;

    /**
     *
     * @param id
     * @return EventType
     */
    EventType findEventType(Integer id);

    /**
     *
     * @return List<EventType>
     */
    List<EventType> findEventTypes();

    /**
     *
     * @param examType
     * @return ExamType
     * @throws BusinessException
     */
    ExamType saveExamType(ExamType examType) throws BusinessException;

    /**
     *
     * @param id
     * @return ExamType
     */
    ExamType findExamType(Integer id);

    /**
     *
     * @return List<ExamType>
     */
    List<ExamType> findExamTypes();

    /**
     *
     *
     * @param facility
     * @return Facility
     * @throws BusinessException
     */
    Facility saveFacility(Facility facility) throws BusinessException;

    /**
     *
     * @param facilityId
     * @return Facility
     */
    Facility findFacility(Integer facilityId);

    /**
     *
     * @return
     */
    List<Facility> findFacilities();

    /**
     *
     * @param gradeBand
     * @return GradeBand
     * @throws BusinessException
     */
    GradeBand saveGradeBand(GradeBand gradeBand) throws BusinessException;

    /**
     * returns the (@code GradeBand} whose gradeBandId is {@code gradeBandId}
     *
     * @param gradeLevelId - the grade band id
     * @return GradeBand - the (@code GradeBand} whose gradeBandId is
     * {@code gradeBandId}
     */
    GradeBand findGradeBand(Integer gradeBandId);

    /**
     * returns a GradeBand for which {@code scoreValue} is greater than or
     * equals to the minScore but less than the maxScore with 100 as the
     * exception for maxScore
     *
     * @param scoreValue -
     * @return GradeBand
     */
    GradeBand findGradeBand(float scoreValue);

    /**
     *
     * @return List<GradeBand>
     */
    List<GradeBand> findGradeBands();

    /**
     *
     * @param gradeLevel
     * @return GradeLevel
     * @throws BusinessException
     */
    GradeLevel saveGradeLevel(GradeLevel gradeLevel) throws BusinessException;

    /**
     * find a grade level by primary key
     *
     * @param gradeLevelId
     * @return GradeLevel
     */
    GradeLevel findGradeLevel(Integer gradeLevelId);

    /**
     * find a grade level by description
     *
     * @param description
     * @return GradeLevel
     */
    GradeLevel findGradeLevel(String description);

    /**
     *
     * @return List<GradeLevel>
     */
    List<GradeLevel> findGradeLevels();

    /**
     *
     * @param idMeans
     * @return IdentificationMeans
     * @throws BusinessException
     */
    IdentificationMeans saveIdentificationMeans(IdentificationMeans idMeans) throws BusinessException;

    /**
     *
     * @param idMeansId
     * @return IdentificationMeans
     */
    IdentificationMeans findIdentificationMeans(Integer idMeansId);

    /**
     *
     * @return List<IdentificationMeans>
     */
    List<IdentificationMeans> findIdentificationMeans();

    /**
     *
     * @param language
     * @return Language
     * @throws BusinessException
     */
    Language saveLanguage(Language language) throws BusinessException;

    /**
     *
     * @param languageId
     * @return Language
     */
    Language findLanguage(Integer languageId);

    /**
     *
     * @return List<Language>
     */
    List<Language> findLanguages();

    /**
     *
     * @param maritalStatus
     * @return MaritalStatus
     * @throws BusinessException
     */
    MaritalStatus saveMaritalStatus(MaritalStatus maritalStatus) throws BusinessException;

    /**
     *
     * @param maritalStatusId
     * @return MaritalStatus
     */
    MaritalStatus findMaritalStatus(Integer maritalStatusId);

    /**
     *
     * @return List<MessageChannel>
     */
    List<MessageChannel> findMessageChannels();

    /**
     *
     * @param moduleId
     * @return Language
     */
    Module findModule(Integer moduleId);

    /**
     *
     * @return List<Module>
     */
    List<Module> findModules();

    /**
     *
     * @param participantCategory
     * @return ParticipantCategory
     * @throws BusinessException
     */
    ParticipantCategory saveParticipantCategory(
            ParticipantCategory participantCategory) throws BusinessException;

    /**
     *
     * @param code
     * @return ParticipantCategory
     */
    ParticipantCategory findParticipantCategory(String code);

    /**
     *
     * @return List<ParticipantCategory>
     */
    List<ParticipantCategory> findParticipantCategories();

    /**
     *
     * @param professionalQualification
     * @return ProfessionalQualification
     * @throws BusinessException
     */
    ProfessionalQualification saveProfessionalQualification(
            ProfessionalQualification professionalQualification) throws BusinessException;

    /**
     *
     * @param professionalQualificationId
     * @return ProfessionalQualification
     */
    ProfessionalQualification findProfessionalQualification(
            Integer professionalQualificationId);

    /**
     *
     * @return List<ProfessionalQualification>
     */
    List<ProfessionalQualification> findProfessionalQualifications();

    /**
     *
     * @param religion
     * @return Religion
     * @throws BusinessException
     */
    Religion saveReligion(Religion religion) throws BusinessException;

    /**
     *
     * @param religionId
     * @return Religion
     */
    Religion findReligion(Integer religionId);

    /**
     *
     * @return List<Religion>
     */
    List<Religion> findReligions();

    /**
     *
     * @return List<MaritalStatus>
     */
    List<MaritalStatus> findMaritalStatuss();

    /**
     *
     * @param prefectType
     * @return PrefectType
     * @throws BusinessException
     */
    PrefectType savePrefectType(PrefectType prefectType) throws BusinessException;

    /**
     *
     * @param prefectTypeId
     * @return PrefectType
     */
    PrefectType findPrefectType(Integer prefectTypeId);

    /**
     *
     * @return List<PrefectType>
     */
    List<PrefectType> findPrefectTypes();

    /**
     *
     * @param section
     * @return Section
     * @throws BusinessException
     */
    Section saveSection(Section section) throws BusinessException;

    /**
     *
     * @param sectionId
     * @return Section
     */
    Section findSection(Integer sectionId);

    /**
     * find the section with the description
     *
     * @param desc
     * @return Section
     */
    Section findSection(String desc);

    /**
     *
     * @return List<Section>
     */
    List<Section> findSections();

    /**
     *
     * @param sportCategory
     * @return SportCategory
     * @throws BusinessException
     */
    SportCategory saveSportCategory(SportCategory sportCategory) throws BusinessException;

    /**
     *
     * @param sportCategoryId
     * @return SportCategory
     */
    SportCategory findSportCategory(Integer sportCategoryId);

    /**
     *
     * @return List<SportCategory>
     */
    List<SportCategory> findSportCategories();

    /**
     *
     * @param stream
     * @return Stream
     * @throws BusinessException
     */
    Stream saveStream(Stream stream) throws BusinessException;

    /**
     *
     * @param streamId
     * @return Stream
     */
    Stream findStream(Integer streamId);

    /**
     * find the stream with the desc value
     *
     * @param desc
     * @return Stream
     */
    Stream findStream(String desc);

    /**
     *
     * @return List<Stream>
     */
    List<Stream> findStreams();

    /**
     *
     * @param states
     * @return States
     * @throws BusinessException
     */
    State saveStates(State states) throws BusinessException;

    /**
     *
     * @param stateCode
     * @return States
     */
    State findState(String stateCode);

    /**
     *
     * @return List<States>
     */
    List<State> findStatess();

    /**
     *
     * @param studyMedium
     * @return StudyMedium
     * @throws BusinessException
     */
    StudyMedium saveStudyMedium(StudyMedium studyMedium) throws BusinessException;

    /**
     *
     * @param studyMediumId
     * @return StudyMedium
     */
    StudyMedium findStudyMedium(Integer studyMediumId);

    /**
     *
     * @return List<StudyMedium>
     */
    List<StudyMedium> findStudyMediums();

    /**
     *
     * @param subject
     * @return Subject
     * @throws BusinessException
     */
    Subject saveSubject(Subject subject) throws BusinessException;

    /**
     * find subject by primary key
     *
     * @param subjectId
     * @return Subject
     */
    Subject findSubject(Integer subjectId);

    /**
     * find subject by description
     *
     * @param description
     * @return Subject
     */
    Subject findSubject(String description);

    /**
     *
     * @return List<Subject>
     */
    List<Subject> findSubjects();

    /**
     *
     * @param term
     * @return Term
     * @throws BusinessException
     */
    Term saveTerm(Term term) throws BusinessException;

    /**
     *
     * @param termId
     * @return Term
     */
    Term findTerm(Integer termId);

    /**
     *
     * @return List<Term>
     */
    List<Term> findTerms();

    /**
     *
     * @param title
     * @return Title
     * @throws BusinessException
     */
    Title saveTitle(Title title) throws BusinessException;

    /**
     *
     * @param titleCode
     * @return Title
     */
    Title findTitle(String titleCode);

    /**
     *
     * @return List<Title>
     */
    List<Title> findTitles();

    /**
     *
     * @param warningLevel
     * @return WarningLevel
     * @throws BusinessException
     */
    WarningLevel saveWarningLevel(WarningLevel warningLevel) throws BusinessException;

    /**
     *
     * @param warningLevelId
     * @return WarningLevel
     */
    WarningLevel findWarningLevel(Integer warningLevelId);

    /**
     *
     * @return List<WarningLevel>
     */
    List<WarningLevel> findWarningLevels();

    /**
     *
     * @return List<Weekday>
     */
    List<Weekday> findWeekdays();
}