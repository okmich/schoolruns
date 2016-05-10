/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service;

import com.okmich.common.entity.criteria.WCString;
import com.okmich.common.exception.BusinessException;
import com.okmich.common.exception.ErrorConstants;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Classroom;
import com.okmich.schoolruns.common.entity.Club;
import com.okmich.schoolruns.common.entity.Country;
import com.okmich.schoolruns.common.entity.Ebook;
import com.okmich.schoolruns.common.entity.Facility;
import com.okmich.schoolruns.common.entity.GradeLevel;
import com.okmich.schoolruns.common.entity.Module;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.PicAlbum;
import com.okmich.schoolruns.common.entity.School;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.common.entity.SchoolClassTeacher;
import com.okmich.schoolruns.common.entity.SchoolFacility;
import com.okmich.schoolruns.common.entity.SchoolModule;
import com.okmich.schoolruns.common.entity.SchoolPref;
import com.okmich.schoolruns.common.entity.SchoolSection;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.common.entity.Stream;
import com.okmich.schoolruns.common.entity.StudentSubject;
import com.okmich.schoolruns.common.entity.Subject;
import com.okmich.schoolruns.common.entity.SubjectTeacher;
import com.okmich.schoolruns.common.entity.SystemRole;
import com.okmich.schoolruns.common.entity.UserLogin;
import com.okmich.schoolruns.common.entity.UserRole;
import com.okmich.schoolruns.common.service.CommonService;
import com.okmich.schoolruns.employee.service.EmployeeService;
import com.okmich.schoolruns.employee.service.data.EmployeeData;
import com.okmich.schoolruns.employee.service.repo.EmployeeQueryCriteria;
import com.okmich.schoolruns.notification.service.NotificationService;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.notification.service.data.MessageRecipient;
import com.okmich.schoolruns.school.service.repo.ClassroomRepo;
import com.okmich.schoolruns.school.service.repo.ClubRepo;
import com.okmich.schoolruns.school.service.repo.EbookQueryCriteria;
import com.okmich.schoolruns.school.service.repo.EbookRepo;
import com.okmich.schoolruns.school.service.repo.PicAlbumQueryCriteria;
import com.okmich.schoolruns.school.service.repo.PicAlbumRepo;
import com.okmich.schoolruns.school.service.repo.SchoolClassQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolClassRepo;
import com.okmich.schoolruns.school.service.repo.SchoolClassTeacherQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolClassTeacherRepo;
import com.okmich.schoolruns.school.service.repo.SchoolCriteriaSearchRepo;
import com.okmich.schoolruns.school.service.repo.SchoolFacilityRepo;
import com.okmich.schoolruns.school.service.repo.SchoolModuleRepo;
import com.okmich.schoolruns.school.service.repo.SchoolPrefRepo;
import com.okmich.schoolruns.school.service.repo.SchoolQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolRepo;
import com.okmich.schoolruns.school.service.repo.SchoolSectionQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolSectionRepo;
import com.okmich.schoolruns.school.service.repo.SchoolSubjectQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolSubjectRepo;
import com.okmich.schoolruns.school.service.repo.SchoolYearRepo;
import com.okmich.schoolruns.school.service.repo.SubjectTeacherQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SubjectTeacherRepo;
import com.okmich.schoolruns.security.service.SecurityService;
import com.okmich.schoolruns.security.service.repo.UserLoginQueryCriteria;
import com.okmich.schoolruns.security.service.repo.UserLoginRepo;
import com.okmich.schoolruns.security.service.repo.UserRoleRepo;
import com.okmich.schoolruns.student.service.StudentService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Service("schoolService")
@Transactional
public class SchoolServiceImpl implements SchoolService {

    private static final Logger LOG = Logger.getLogger(SchoolServiceImpl.class.getName());
    /**
     * {@link employeeService}
     */
    @Autowired
    private EmployeeService employeeService;
    /**
     * {@link notificationService}
     */
    @Autowired
    private NotificationService notificationService;
    /**
     * {@link SecurityService}
     */
    @Autowired
    private SecurityService securityService;
    /**
     * {@link studentService}
     */
    @Autowired
    private StudentService studentService;
    /**
     * {@link commonService}
     */
    @Autowired
    private CommonService commonService;
    /**
     * {@link ClassroomRepo}
     */
    @Autowired
    private ClassroomRepo classroomRepo;
    /**
     * {@link SchoolSectionRepo}
     */
    @Autowired
    private SchoolSectionRepo schoolSectionRepo;
    /**
     * {@link SchoolRepo}
     */
    @Autowired
    private SchoolRepo schoolRepo;
    /**
     * {@link schoolCriteriaSearchRepo}
     */
    @Autowired
    private SchoolCriteriaSearchRepo schoolCriteriaSearchRepo;
    /**
     * {@link schoolFacilityRepo}
     */
    @Autowired
    private SchoolFacilityRepo schoolFacilityRepo;
    /**
     * {@link SchoolPrefRepo}
     */
    @Autowired
    private SchoolPrefRepo schoolPrefRepo;
    /**
     * {@link SchoolModuleRepo}
     */
    @Autowired
    private SchoolModuleRepo schoolModuleRepo;
    /**
     * {@link schoolClassRepo}
     */
    @Autowired
    private SchoolClassRepo schoolClassRepo;
    /**
     * {@link schoolClassRepo}
     */
    @Autowired
    private SchoolClassTeacherRepo schoolClassTeacherRepo;
    /**
     * {@link schoolSubjectRepo}
     */
    @Autowired
    private SchoolSubjectRepo schoolSubjectRepo;
    /**
     * {@link schoolYearRepo}
     */
    @Autowired
    private SchoolYearRepo schoolYearRepo;
    /**
     * {@link subjectTeacherRepo}
     */
    @Autowired
    private SubjectTeacherRepo subjectTeacherRepo;
    /**
     * {@link userLoginRepo}
     */
    @Autowired
    private UserLoginRepo userLoginRepo;
    /**
     * {@link UserRoleRepo}
     */
    @Autowired
    private UserRoleRepo userRoleRepo;
    /**
     * {@link ClubRepo}
     */
    @Autowired
    private ClubRepo clubRepo;
    /**
     * {@link EbookRepo}
     */
    @Autowired
    private EbookRepo ebookRepo;
    /**
     * {@link PicAlbumRepo}
     */
    @Autowired
    private PicAlbumRepo picAlbumRepo;

    /**
     *
     *
     */
    public SchoolServiceImpl() {
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public Classroom saveClassroom(Classroom classroom) throws BusinessException {
        //search for duplicate
        List<Classroom> classRooms = classroomRepo.findDupClassrooms(
                classroom.getSchoolId(), classroom.getCode());
        if (!classRooms.isEmpty()) {
            if (classroom.getClassroomId() == null) {
                throw new BusinessException("ERROR_DUPLICATE_CLASSROOM");
            } else {
                if (classRooms.remove(classroom) && !classRooms.isEmpty()) {
                    throw new BusinessException("ERROR_DUPLICATE_CLASSROOM");
                }
            }
        }
        try {
            classroom = classroomRepo.save(classroom);
            //return the saved object
            return classroom;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public Classroom findClassroom(Integer classroomId) {
        return classroomRepo.findOne(classroomId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<Classroom> findSchoolClassrooms(Integer schoolId) {
        return classroomRepo.findSchoolClassrooms(schoolId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public School createSchool(School school) throws BusinessException {
        //check for existing school name in a particular city
        List<School> schools = schoolRepo.findSameSchoolTown(school.getName(),
                school.getAddress().getCity().getCityId());
        if (schools.size() > 0) {
            throw new BusinessException(ErrorConstants.SCHOOL_NAME_CITY_ALREADY_EXIST);
        }
        //check for duplication email address
        schools = schoolRepo.findSchoolByEmail(school.getEmailAddress());
        if (schools.size() > 0) {
            throw new BusinessException(ErrorConstants.SCHOOL_EMAIL_ALREADY_EXIST);
        }
        school.setModifiedTime(new Date());
        school.setStatus(CommonConstants.STATUS_ACTIVE);
        //set the default country
        if (school.getCountry().getCountryCode() == null
                || school.getCountry().getCountryCode().trim().isEmpty()) {
            String nationalAffliation = commonService.findCtrlParameterValue(
                    CommonConstants.CTRL_PARAM_DEFAULT_COUNTRY_KEY);
            if (nationalAffliation == null) {
                throw new BusinessException(ErrorConstants.NO_NATIONAL_AFFILIATION);
            }
            school.setCountry(new Country(nationalAffliation));
        }
        try {
            school.setSchoolId(createIDForSchool(null));
            school = schoolRepo.save(school);
            //create an admin user login record for this school here
            UserLogin userLogin = createSchoolAdmin(school);
            userLogin.setSchool(school);
            userLogin = securityService.createUserLogin(userLogin);
            //create a userrole for this userlogin in this school
            UserRole userRole = new UserRole();
            userRole.setModifiedBy(school.getModifiedBy());
            userRole.setStatus(CommonConstants.STATUS_ACTIVE);
            userRole.setSystemRole(new SystemRole(2)); //bad hardcoding done here
            userRole.setUserLogin(userLogin);

            userRoleRepo.save(userRole);
            //return the saved object
            return school;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public School saveSchool(School school) throws BusinessException {
        School _school = schoolRepo.findOne(school.getSchoolId());
        if (_school == null) {
            throw new BusinessException(ErrorConstants.OBJ_DOES_NOT_EXIST);
        }
        try {
            _school.setAddress(school.getAddress());
            _school.setContactNo(school.getContactNo());
            _school.setCountry(school.getCountry());
            _school.setEmailAddress(school.getEmailAddress());
            _school.setFaxNo(school.getFaxNo());
            _school.setModifiedBy(school.getModifiedBy());
            _school.setModifiedTime(new Date());
            _school.setName(school.getName());
            _school.setSlogan(school.getSlogan());
            _school.setStartedDate(school.getStartedDate());
            _school.setStatus(school.getStatus());
            _school.setVersion(school.getVersion());
            _school.setWebSite(school.getWebSite());
            _school = schoolRepo.save(_school);
            //return the saved object
            return _school;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public School findSchool(Integer schoolId) throws BusinessException {
        School _school = schoolRepo.findOne(schoolId);
        if (_school == null) {
            throw new BusinessException(ErrorConstants.OBJ_DOES_NOT_EXIST);
        }
        return _school;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<School> findSchools(SchoolQueryCriteria criteria) {
        return schoolCriteriaSearchRepo.findSchools(criteria);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public UserLogin findSchoolAdminLogin(Integer schoolId) {
        List<UserLogin> userLogins = userLoginRepo.findSchoolAdmin(schoolId);
        if (userLogins.isEmpty()) {
            return null;
        } else {
            return userLogins.get(0);
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public SchoolPref saveSchoolPref(SchoolPref schoolPref) throws BusinessException {
        try {
            schoolPref.setSchool(null);
            schoolPref = schoolPrefRepo.save(schoolPref);
            //return the saved object
            return schoolPref;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public SchoolPref findSchoolPref(Integer schoolId) {
        SchoolPref _schoolPref = schoolPrefRepo.findOne(schoolId);
        return _schoolPref;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public void updateSchoolFacilities(List<Facility> facilityList, School school)
            throws BusinessException {
        List<SchoolFacility> _schoolFacilities =
                schoolFacilityRepo.findSchoolFacilityBySchool(school.getSchoolId());
        try {
            //delete all the school module
            schoolFacilityRepo.delete(_schoolFacilities);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }

        SchoolFacility schoolFacility;
        List<SchoolFacility> schoolFacilities = new ArrayList<>();
        for (Facility fac : facilityList) {
            schoolFacility = new SchoolFacility();

            schoolFacility.setFacility(fac);
            schoolFacility.setModifiedBy(school.getModifiedBy());
            schoolFacility.setModifiedTime(new Date());
            schoolFacility.setSchool(school);

            schoolFacilities.add(schoolFacility);
        }
        try {
            //create a list of school module
            schoolFacilityRepo.save(schoolFacilities);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<Facility> findSchoolFacilitiesBySchool(Integer schoolId) {
        return schoolFacilityRepo.findSchoolFacilities(schoolId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public void assignModuleToSchool(List<Module> moduleList, School school) throws BusinessException {
        List<SchoolModule> _schoolModules =
                schoolModuleRepo.findSchoolModuleBySchool(school.getSchoolId());
        try {
            //delete all the school module
            schoolModuleRepo.delete(_schoolModules);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }

        SchoolModule schoolModule;
        List<SchoolModule> schoolModules = new ArrayList<>();
        for (Module mod : moduleList) {
            schoolModule = new SchoolModule();

            schoolModule.setModifiedBy(school.getModifiedBy());
            schoolModule.setModifiedTime(new Date());
            schoolModule.setModule(mod);
            schoolModule.setSchool(school);

            schoolModules.add(schoolModule);
        }
        try {
            //create a list of school module
            schoolModuleRepo.save(schoolModules);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<Module> findSchoolModuleBySchool(Integer schoolId) {
        return schoolModuleRepo.findModuleBySchool(schoolId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public SchoolClass saveSchoolClass(SchoolClass schoolClass)
            throws BusinessException {
        //confirm that a duplicate class don't exist in the system
        SchoolClass _schClass = schoolClassRepo.findByCode(schoolClass.getCode(),
                schoolClass.getSchoolId());
        //check that the new object possesses an existing ID - if ID exist then the
        //operation is an update
        if (schoolClass.getSchoolClassId() == null && _schClass != null) {
            throw new BusinessException(ErrorConstants.CLASS_WITH_CODE_ALREADY_EXIST);
        }
        try {
            return schoolClassRepo.save(schoolClass);
        } catch (Exception ex) {
            Logger.getLogger(SchoolServiceImpl.class.getName()).log(
                    Level.SEVERE, StringUtil.getNestedErrorMessage(ex), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public void saveSchoolClasses(List<SchoolClass> schoolClasses) throws BusinessException {
        try {
            for (SchoolClass sc : schoolClasses) {
                //adjust the variable variables
                setVariablesForSchoolClass(sc);
                saveSchoolClass(sc);
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public SchoolClass findSchoolClass(Integer schoolClassId) {
        return schoolClassRepo.findOne(schoolClassId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<SchoolClass> findSchoolClasses(Integer schoolId) {
        return schoolClassRepo.findBySchool(schoolId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<SchoolClass> findSchoolClasses(SchoolClassQueryCriteria criteria) {
        return schoolCriteriaSearchRepo.findSchoolClasses(criteria);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public SchoolClassTeacher saveSchoolClassTeacher(
            SchoolClassTeacher schoolClassTeacher) throws BusinessException {
        List<SchoolClassTeacher> schoolClassTeachers = schoolClassTeacherRepo.findBySchoolTeacher(
                schoolClassTeacher.getSchoolClass().getSchoolClassId(),
                schoolClassTeacher.getEmployee().getEmployeeId(),
                schoolClassTeacher.getAllocationType().getAllocationTypeId(),
                schoolClassTeacher.getSchoolYear().getSchoolYearId());
        if (!schoolClassTeachers.isEmpty()
                && (schoolClassTeacher.getSchoolClassTeacherId() == null
                || !schoolClassTeachers.contains(schoolClassTeacher))) {
            throw new BusinessException(ErrorConstants.DUPLICATE_CLASS_TEACHER_ALLOC);
        }
        try {
            schoolClassTeacher.setModifiedTime(new Date());
            schoolClassTeacher.setStatus(CommonConstants.STATUS_ACTIVE);
            return schoolClassTeacherRepo.save(schoolClassTeacher);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public SchoolClassTeacher findSchoolClassTeacher(Integer schoolClassTeacherId) {
        return schoolClassTeacherRepo.findOne(schoolClassTeacherId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<SchoolClassTeacher> findSchoolClassTeachers(
            SchoolClassTeacherQueryCriteria criteria) {
        return schoolCriteriaSearchRepo.findSchoolClassTeacher(criteria);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public SchoolSubject saveSchoolSubject(SchoolSubject schoolSubject)
            throws BusinessException {
        List<SchoolSubject> schoolSubjects = schoolSubjectRepo.findByCode(schoolSubject.getSchoolId(),
                schoolSubject.getSubjectCode());
        if (!schoolSubjects.isEmpty() && !schoolSubjects.contains(schoolSubject)) {
            throw new BusinessException(ErrorConstants.DUPLICATE_DUPLICATE_SUBJECT_CODE);
        }
        schoolSubjects = schoolSubjectRepo.findByGradeSubject(schoolSubject.getSchoolId(),
                schoolSubject.getGradeLevel().getGradeLevelId(),
                schoolSubject.getSubject().getSubjectId());
        if (!schoolSubjects.isEmpty() && !schoolSubjects.contains(schoolSubject)) {
            throw new BusinessException(ErrorConstants.DUPLICATE_GRADE_SUBJECT);
        }
        schoolSubject.setStatus(CommonConstants.STATUS_ACTIVE);
        //we are now trying to check if the subject code was entered.
        //If not, please create subject code for schools
        if (schoolSubject.getSubjectCode() == null
                || schoolSubject.getSubjectCode().trim().isEmpty()) {
            formulateSubjectCode(schoolSubject);
        }
        try {
            schoolSubject = schoolSubjectRepo.save(schoolSubject);
            return schoolSubject;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void saveSchoolSubjects(List<SchoolSubject> schoolSubjects) throws BusinessException {
        try {
            for (SchoolSubject ss : schoolSubjects) {
                //adjust the variable variables
                setVariablesForSchoolSubject(ss);
                saveSchoolSubject(ss);
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public SchoolSubject findSchoolSubject(Integer schoolSubjectId) {
        return schoolSubjectRepo.findOne(schoolSubjectId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<SchoolSubject> findSchoolSubjects(Integer schoolId) {
        return schoolSubjectRepo.findBySchool(schoolId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<SchoolSubject> findSchoolSubjects(SchoolSubjectQueryCriteria criteria) {
        return schoolCriteriaSearchRepo.findSchoolSubjects(criteria);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public SubjectTeacher saveSubjectTeacher(SubjectTeacher subjectTeacher)
            throws BusinessException {
        List<SubjectTeacher> subjectTeachers = subjectTeacherRepo.findBySubjectTeacher(
                subjectTeacher.getEmployee().getEmployeeId(),
                subjectTeacher.getSchoolSubject().getSchoolSubjectId());
        if (!subjectTeachers.isEmpty()) {
            throw new BusinessException(ErrorConstants.DUPLICATE_SUBJECT_TEACHER);
        }
        try {
            subjectTeacher.setStatus(CommonConstants.STATUS_ACTIVE);
            return subjectTeacherRepo.save(subjectTeacher);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public void assignSubjectsToTeacher(
            List<SchoolSubject> schoolSubjects, SubjectTeacher subjectTeacher)
            throws BusinessException {
        //get all school subjects for that employee
        List<SubjectTeacher> allStudentTeachers = subjectTeacherRepo.findAllSubjectByTeacher(
                subjectTeacher.getEmployee().getEmployeeId());
        //run a for loop and disable all the entry by setting status to I
        for (SubjectTeacher st : allStudentTeachers) {
            if (!schoolSubjects.contains(st.getSchoolSubject())) {
                st.setStatus(CommonConstants.STATUS_INACTIVE);
            } else {
                //if school subject exists, activate it by setting the record to A
                st.setStatus(CommonConstants.STATUS_ACTIVE);
            }
            st.setModifiedBy(subjectTeacher.getModifiedBy());
            st.setModifiedTime(new Date());
        }
        //run another loop for all the new subject teacher
        SubjectTeacher subjectTeacher1;
        for (SchoolSubject sSubject : schoolSubjects) {
            if (!allContainsSubjectTeacher(allStudentTeachers, sSubject)) {
                //if the school subject doesn't exist, create a new one
                subjectTeacher1 = new SubjectTeacher();

                subjectTeacher1.setModifiedBy(subjectTeacher.getModifiedBy());
                subjectTeacher1.setModifiedTime(new Date());
                subjectTeacher1.setEmployee(subjectTeacher.getEmployee());
                subjectTeacher1.setSchoolYearId(subjectTeacher.getSchoolYearId());
                subjectTeacher1.setSchoolSubject(sSubject);
                subjectTeacher1.setStatus(CommonConstants.STATUS_ACTIVE);

                allStudentTeachers.add(subjectTeacher1);
            }
        }
        try {
            subjectTeacherRepo.save(allStudentTeachers);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public SubjectTeacher findSubjectTeacher(Integer subjectTeacherId) {
        return subjectTeacherRepo.findOne(subjectTeacherId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<SubjectTeacher> findSubjectTeachers(SubjectTeacherQueryCriteria criteria) {
        return schoolCriteriaSearchRepo.findSubjectTeachers(criteria);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public SchoolSection saveSchoolSection(SchoolSection schoolSection) throws BusinessException {
        if (schoolSection.getSchoolSectionId() == null) {
            List<SchoolSection> schoolSections = schoolSectionRepo.findBySchoolSection(
                    schoolSection.getSection().getSectionId(),
                    schoolSection.getSchool().getSchoolId());
            if (!schoolSections.isEmpty()) {
                throw new BusinessException(ErrorConstants.DUPLICATE_SCHOOL_SECTION);
            }
        }
        try {
            schoolSection.setStatus(CommonConstants.STATUS_ACTIVE);
            schoolSection.setModifiedTime(new Date());
            return schoolSectionRepo.save(schoolSection);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public SchoolSection findSchoolSection(Integer schoolSectionId) {
        return schoolSectionRepo.findOne(schoolSectionId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<SchoolSection> findSchoolSections(Integer schoolId) {
        return schoolSectionRepo.findAllBySchool(schoolId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public List<SchoolSection> findSchoolSections(SchoolSectionQueryCriteria criteria) {
        return schoolCriteriaSearchRepo.findSchoolSections(criteria);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public Club saveClub(Club club) throws BusinessException {
        if (!clubRepo.findSchoolClubByType(
                club.getSchool().getSchoolId(),
                club.getClubType().getClubTypeId()).isEmpty() && club.getClubId() == null) {
            throw new BusinessException(ErrorConstants.DUPLICATE_SCHOOL_CLUB);
        }
        try {
            club.setModifiedTime(new Date());
            return clubRepo.save(club);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<Club> findClubs(Integer schoolId) {
        return clubRepo.findSchoolClubs(schoolId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public void sendNotificationMessage(MessageData messageData) throws BusinessException {

        switch (messageData.getParticipantCategoryCode().charAt(0)) {
            case CommonConstants.PART_CAT_SCHOOL_ADMIN:
                UserLoginQueryCriteria userLoginCriteria = new UserLoginQueryCriteria();
                userLoginCriteria.setStatus(CommonConstants.STATUS_ACTIVE);
                userLoginCriteria.setSchoolAdmin();
                List<UserLogin> userLogins = securityService.findUserLoginList(userLoginCriteria);
                for (UserLogin userLogin : userLogins) {
                    messageData.getMessageRecipients().add(
                            new MessageRecipient(userLogin.getUserLoginId(), userLogin.getSchool().getName(),
                            getAddressForParticipant(messageData.getMessageChannelCode(), userLogin.getEmail(), userLogin.getPhoneNumber())));
                }
                break;
            case CommonConstants.PART_CAT_PARENTS:
                //TODO
                SchoolYear schoolYear = schoolYearRepo.findSchoolCurrentYear(messageData.getSchoolId());
                List<Parent> parents = studentService.findSchoolParents(schoolYear.getSchoolYearId());
                for (Parent p : parents) {
                    messageData.getMessageRecipients().add(
                            new MessageRecipient(p.getPhoneNumber(), p.getFullname(),
                            getAddressForParticipant(messageData.getMessageChannelCode(), p.getEmail(), p.getPhoneNumber())));
                }
                break;
            case CommonConstants.PART_CAT_NON_TEACHING_STAFF:
                EmployeeQueryCriteria criteria = new EmployeeQueryCriteria();
                criteria.setSchoolId(messageData.getSchoolId());
                criteria.setEmployeeCategoryId(CommonConstants.EMPLOYEE_DEFAULT_CATEGORY,
                        WCString.NOT_EQUAL);
                criteria.setStatus(CommonConstants.STATUS_ACTIVE);
                List<EmployeeData> employeeList = employeeService.findEmployees(criteria);
                for (EmployeeData e : employeeList) {
                    messageData.getMessageRecipients().add(
                            new MessageRecipient(e.getEmployeeId(), e.getFullname(),
                            getAddressForParticipant(messageData.getMessageChannelCode(), e.getEmail(), e.getMobileNo())));
                }
                break;
            case CommonConstants.PART_CAT_TEACHING_STAFF:
                criteria = new EmployeeQueryCriteria();
                criteria.setSchoolId(messageData.getSchoolId());
                criteria.setStatus(CommonConstants.STATUS_ACTIVE);
                criteria.setEmployeeCategoryId(CommonConstants.EMPLOYEE_DEFAULT_CATEGORY);
                employeeList = employeeService.findEmployees(criteria);
                for (EmployeeData e : employeeList) {
                    messageData.getMessageRecipients().add(
                            new MessageRecipient(e.getEmployeeId(), e.getFullname(),
                            getAddressForParticipant(messageData.getMessageChannelCode(), e.getEmail(), e.getMobileNo())));
                }
                break;
            case CommonConstants.PART_CAT_STAFF:
                criteria = new EmployeeQueryCriteria();
                criteria.setSchoolId(messageData.getSchoolId());
                criteria.setStatus(CommonConstants.STATUS_ACTIVE);
                employeeList = employeeService.findEmployees(criteria);
                for (EmployeeData e : employeeList) {
                    messageData.getMessageRecipients().add(
                            new MessageRecipient(e.getEmployeeId(), e.getFullname(),
                            getAddressForParticipant(messageData.getMessageChannelCode(), e.getEmail(), e.getMobileNo())));
                }
                break;
            case CommonConstants.PART_CAT_CUSTOM:
                break;
            default:
                throw new BusinessException(ErrorConstants.INVALID_PARTICIPANT_CODE);
        }
        try {
            notificationService.sendMessage(messageData);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public Ebook saveEbook(Ebook ebook) throws BusinessException {
        if (ebook.getEbookId() == null) { //new ebook
            ebook.setCreateDate(new Date());
            ebook.setStatus(CommonConstants.STATUS_ACTIVE);
        }
        try {
            ebook.setModifiedTime(new Date());
            ebook = ebookRepo.save(ebook);
            //return the saved object
            return ebook;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public Ebook findEbook(Integer ebookId) {
        return ebookRepo.findOne(ebookId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<Ebook> findEbooks(EbookQueryCriteria criteria) {
        return schoolCriteriaSearchRepo.findEbooks(criteria);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public PicAlbum savePicAlbum(PicAlbum picAlbum) throws BusinessException {
        if (picAlbum.getPicAlbumId() == null) { //a new photoAlbum
            if (!picAlbumRepo.findByTitle(picAlbum.getSchoolId(),
                    picAlbum.getTitle().toUpperCase()).isEmpty()) {
                throw new BusinessException(ErrorConstants.DUPLICATE_ALBUM);
            }
            picAlbum.setCreateDate(new Date());
        }
        try {
            picAlbum.setModifiedTime(new Date());
            picAlbum = picAlbumRepo.save(picAlbum);
            //return the saved object
            return picAlbum;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public PicAlbum findPicAlbum(Integer photoAlbumId) {
        return picAlbumRepo.findOne(photoAlbumId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<PicAlbum> findPicAlbums(PicAlbumQueryCriteria criteria) {
        return schoolCriteriaSearchRepo.findPicAlbums(criteria);
    }

    /**
     *
     * @param school
     * @return
     */
    private UserLogin createSchoolAdmin(School school) {
        UserLogin userLogin = new UserLogin();

        userLogin.setChangeOnNextLogin(CommonConstants.CONSTANT_YES);
        userLogin.setDefaultSystemRole(new SystemRole(CommonConstants.ROLE_SCHOOL_ADMIN));
        userLogin.setEmail(school.getEmailAddress());
        userLogin.setGeneratedPassword(true);
        userLogin.setIsAdmin(CommonConstants.CONSTANT_NO);
        userLogin.setLoginAttempts(0);
        userLogin.setModifiedBy(school.getModifiedBy());
        userLogin.setPhoneNumber(school.getContactNo());
        userLogin.setSchool(school);
        userLogin.setSchoolAdmin(true);
        userLogin.setTitle(school.getName());
        userLogin.setStatus(CommonConstants.STATUS_ACTIVE);
        userLogin.setUsername("admin" + System.nanoTime() % 10000);
        userLogin.setPassword(userLogin.getUsername());

        return userLogin;
    }

    /**
     * a simple set of steps to create an ID for a school such that the integer
     * must be minimum of 6 digits in length. The first two digit will represent
     * the year (yy) while the next 3 digit will represent the day of the year
     * the Id is being generated. The final digit will represent the count of
     * school registered for that day of the year and will increase the day
     * value if the count exceeds 9
     *
     * @param Integer - id
     * @return Integer - a six digit integer
     */
    private Integer createIDForSchool(Integer xval) {
        Integer id = xval;
        if (xval != null) {
            id++;
        } else {
            String ids = new SimpleDateFormat("yyD").format(new Date());
            id = Integer.valueOf(ids + "" + 0);
        }
        if (schoolRepo.findOne(id) != null) { //ID already in use
            id = createIDForSchool(id);
        }
        return id;
    }

    /**
     *
     * @param ss
     */
    private void setVariablesForSchoolSubject(SchoolSubject ss) throws BusinessException {
        String desc = ss.getSubject().getDescription();
        if (desc != null) {
            Subject subject = commonService.findSubject(desc);
            if (subject == null) {
                throw new BusinessException("ERROR_INVALID SUBJECT DETECTED - " + (ss.getSubjectCode() == null ? "" : ss.getSubjectCode()));
            }
            ss.setSubject(subject);
        }
        desc = ss.getGradeLevel().getDescription();
        if (desc != null) {
            GradeLevel gradeLevel = commonService.findGradeLevel(desc);
            if (gradeLevel == null) {
                throw new BusinessException("ERROR_INVALID Grade Level ENTERED WITH SUBJECT " + (ss.getSubjectCode() == null ? "" : ss.getSubjectCode()));
            }
            ss.setGradeLevel(gradeLevel);
        }

    }

    /**
     *
     *
     * @param sc
     * @throws BusinessException
     */
    private void setVariablesForSchoolClass(SchoolClass sc) throws BusinessException {
        String desc = sc.getGradeLevel().getDescription();
        if (desc != null) {
            GradeLevel gradeLevel = commonService.findGradeLevel(desc);
            if (gradeLevel == null) {
                throw new BusinessException("ERROR_INVALID Grade Level ENTERED WITH Class " + sc.getCode());
            }
            sc.setGradeLevel(gradeLevel);
        }
//        desc = sc.getSection().getDescription();
//        if (desc != null) {
//            Section section = commonService.findSection(desc);
//            if (section != null) {
//                sc.setSection(section);
//            }
//        }
        desc = sc.getStream().getDescription();
        if (desc != null) {
            Stream stream = commonService.findStream(desc);
            if (stream != null) {
                sc.setStream(stream);
            }
        }
    }

    /**
     * returns true if {@code sSubject} is not null and {@code studentSubjects}
     * contains a record whose {@link StudentSubject#getSchoolSubject()
     * } is equal to {@code sSubject}, otherwise returns false.
     *
     * @param studentTeachers - List<SubjectTeacher>
     * @param sSubject - SchoolSubject
     * @return true true if {@code sSubject} is not null and
     * {@code studentSubjects} contains a record whose {@link StudentSubject#getSchoolSubject()
     * } is equal to {@code sSubject}, otherwise returns false.
     */
    private boolean allContainsSubjectTeacher(List<SubjectTeacher> studentTeachers,
            SchoolSubject sSubject) {
        if (sSubject == null) {
            return false;
        }
        for (SubjectTeacher st : studentTeachers) {
            if (sSubject.equals(st.getSchoolSubject())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param messageChannelCode
     * @param email
     * @param phoneNumber
     * @return
     */
    private String getAddressForParticipant(String messageChannelCode, String email, String phoneNumber) {
        if (messageChannelCode.charAt(0) == CommonConstants.MSG_CHNNL_EMAIL) {
            return email;
        } else if (messageChannelCode.charAt(0) == CommonConstants.MSG_CHNNL_SMS) {
            return phoneNumber;
        } else {
            return "";
        }
    }

    /**
     * create a String representing subject code for a school subject. The
     * formulate takes the default code for the subject and the default code for
     * the grade level and returns a concatenation of both
     *
     * @param schoolSubject
     */
    private void formulateSubjectCode(SchoolSubject schoolSubject) throws BusinessException {
        Subject subject = commonService.findSubject(schoolSubject.getSubject().getSubjectId());
        if (subject == null) {
            throw new BusinessException(ErrorConstants.INVALID_SUBJECT);
        }
        GradeLevel gradeLevel = commonService.findGradeLevel(schoolSubject.getGradeLevel().getGradeLevelId());
        if (gradeLevel == null) {
            throw new BusinessException(ErrorConstants.INVALID_SUBJECT);
        }
        schoolSubject.setSubjectCode(
                new StringBuilder(subject.getDefaultCode()).append(
                gradeLevel.getLevelCode()).toString());
    }
}
