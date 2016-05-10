/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.exception.ErrorConstants;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.School;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.common.entity.SchoolParent;
import com.okmich.schoolruns.common.entity.SchoolPref;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.common.entity.State;
import com.okmich.schoolruns.common.entity.StudentClub;
import com.okmich.schoolruns.common.entity.StudentDiscipline;
import com.okmich.schoolruns.common.entity.StudentSport;
import com.okmich.schoolruns.common.entity.StudentSubject;
import com.okmich.schoolruns.common.service.repo.StateRepo;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.school.service.repo.SchoolClassRepo;
import com.okmich.schoolruns.school.service.repo.SchoolCriteriaSearchRepo;
import com.okmich.schoolruns.school.service.repo.SchoolParentQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolParentRepo;
import com.okmich.schoolruns.school.service.repo.SchoolPrefRepo;
import com.okmich.schoolruns.school.service.repo.SchoolRepo;
import com.okmich.schoolruns.school.service.repo.SchoolSectionRepo;
import com.okmich.schoolruns.school.service.repo.SchoolStudentQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolStudentRepo;
import com.okmich.schoolruns.school.service.repo.StudentClubRepo;
import com.okmich.schoolruns.school.service.repo.StudentDisciplineRepo;
import com.okmich.schoolruns.school.service.repo.StudentSportRepo;
import com.okmich.schoolruns.school.service.repo.StudentSubjectRepo;
import com.okmich.schoolruns.security.service.repo.UserRoleRepo;
import com.okmich.schoolruns.student.service.StudentService;
import com.okmich.schoolruns.student.service.data.StudentData;
import com.okmich.schoolruns.student.service.repo.ParentRepo;
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
@Service("schoolStudentService")
@Transactional
public class SchoolStudentServiceImpl implements SchoolStudentService {

    private static final Logger LOG = Logger.getLogger(SchoolStudentServiceImpl.class.getName());
    /**
     * {@link ParentRepo}
     */
    @Autowired
    private ParentRepo parentRepo;
    /**
     * {@link SchoolSectionRepo}
     */
    @Autowired
    private StudentService studentService;
    /**
     * {@link SchoolRepo}
     */
    @Autowired
    private SchoolCriteriaSearchRepo schoolCriteriaSearchRepo;
    /**
     * {@link SchoolPrefRepo}
     */
    @Autowired
    private SchoolPrefRepo schoolPrefRepo;
    /**
     * {@link schoolParentRepo}
     */
    @Autowired
    private SchoolParentRepo schoolParentRepo;
    /**
     * {@link schoolRepo}
     */
    @Autowired
    private SchoolRepo schoolRepo;
    /**
     * {@link schoolClassRepo}
     */
    @Autowired
    private SchoolClassRepo schoolClassRepo;
    /**
     * {@link schoolStudentRepo}
     */
    @Autowired
    private SchoolStudentRepo schoolStudentRepo;
    /**
     * {@link studentSubjectRepo}
     */
    @Autowired
    private StudentSubjectRepo studentSubjectRepo;
    /**
     * {@link studentClubRepo}
     */
    @Autowired
    private StudentClubRepo studentClubRepo;
    /**
     * {@link studentSportRepo}
     */
    @Autowired
    private StudentSportRepo studentSportRepo;
    /**
     * {@link studentDisciplineRepo}
     */
    @Autowired
    private StudentDisciplineRepo studentDisciplineRepo;
    /**
     * {@link UserRoleRepo}
     */
    @Autowired
    private StateRepo stateRepo;

    public SchoolStudentServiceImpl() {
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public void assignStudentToClass(List<SchoolStudentData> studentList,
            SchoolStudentData schoolStudentData, boolean isNewSchoolYears)
            throws BusinessException {
        //create the persistable array of new school student record
        List<SchoolStudent> classStudents = new ArrayList<>();
        //get existing class members
        List<SchoolStudent> _xclassStudents = schoolStudentRepo.findByYearClass(
                schoolStudentData.getSchoolYearId(), schoolStudentData.getSchoolClassId());
        SchoolStudent classStudent;
        if (isNewSchoolYears) {
            //search to ensure that no student exists in that class for that year
            //throw exception if students already exist in that class
            if (!_xclassStudents.isEmpty()) {
                throw new BusinessException(ErrorConstants.SCHOOL_CLASS_NOT_EMPTY);
            }
            for (SchoolStudentData ssd : studentList) {
                classStudent = schoolStudentRepo.findOne(ssd.getSchoolStudentId());
                //create a new version of the school and assign the new class and year
                classStudent.setAdmissionDate(ssd.getAdmissionDate());
                classStudent.setModifiedBy(schoolStudentData.getModifiedBy());
                classStudent.setModifiedTime(new Date());
                classStudent.setPictureUrl(ssd.getPictureUrl());
                classStudent.setRegistrationNo(ssd.getRegistrationNo());
                classStudent.setSchoolClass(
                        new SchoolClass(schoolStudentData.getSchoolClassId()));
                classStudent.setSchoolStudentId(null); //resetting the id for the object
                classStudent.setSchoolYear(new SchoolYear(
                        schoolStudentData.getSchoolYearId()));
                classStudent.setStatus(CommonConstants.STATUS_ACTIVE);
                //classStudent.setStudent(ssd.getStudent());

                classStudents.add(classStudent);
            }
        } else { //we are adding to an already existing class with students
            for (SchoolStudentData ssd : studentList) {
                if (!_xclassStudents.contains(ssd.getSchoolStudent())) {
                    //find the existing student
                    classStudent = schoolStudentRepo.findSchoolStudent(ssd.getSchoolStudentId());
                    //set the class and year
                    classStudent.setSchoolClass(new SchoolClass(
                            schoolStudentData.getSchoolClassId()));
                    classStudent.setSchoolYear(new SchoolYear(
                            schoolStudentData.getSchoolYearId()));
                    //add to classStudents
                    classStudents.add(classStudent);
                } else {
                    _xclassStudents.remove(ssd.getSchoolStudent());
                }
            }
            //what we should have left in _xclassStudents list are those who were deselected
            for (SchoolStudent st : _xclassStudents) {
                st.setSchoolClass(null);
            }
        }
        try {
            schoolStudentRepo.save(_xclassStudents); //removing existing student who were deselected
            schoolStudentRepo.save(classStudents); //saving existing student who were selected
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
    public void createSchoolStudents(List<SchoolStudentData> schoolStudents)
            throws BusinessException {
        //get the schools preference to check for student no policy
        SchoolPref schoolPref = schoolPrefRepo.findSchoolPrefBySchoolYear(
                schoolStudents.get(0).getSchoolYearId());
        if (schoolPref == null) {
            throw new BusinessException("ERROR_NO_SCHOOL_PREF_SETUP");
        }
        try {
            for (SchoolStudentData ssd : schoolStudents) {
                //adjust the variable variables
                setVariablesForSchoolStudent(ssd);
                createSchoolStudent(ssd);
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
    public SchoolStudentData createSchoolStudent(SchoolStudentData schoolStudentData)
            throws BusinessException {
        LOG.entering("SchoolStudentServiceImpl", "createSchoolStudent", schoolStudentData);
        //set status
        schoolStudentData.setModifiedTime(new Date());
        schoolStudentData.setStatus(CommonConstants.STATUS_ACTIVE);
        //get the schools preference to check for student no policy
        SchoolPref schoolPref =
                schoolPrefRepo.findSchoolPrefBySchoolYear(schoolStudentData.getSchoolYearId());

        assert schoolPref != null; //confirm that schoolPref is not null
        //check for reg no generation policy
        if (schoolPref.isSystemGenStudentRegNo()) {
            if (schoolStudentData.getAdmissionDate() == null) {
                schoolStudentData.setAdmissionDate(new Date());
            }
            schoolStudentData.setRegistrationNo(buildStudentNumber(schoolStudentData));
        } else {
            if (schoolStudentData.getRegistrationNo() == null
                    || schoolStudentData.getRegistrationNo().trim().equals("")) {
                throw new BusinessException("ERROR_MISSING_STUDENT_NUMBER");
            }
            if (!schoolStudentRepo.findByRegistrationNo(
                    schoolStudentData.getSchoolId(),
                    schoolStudentData.getRegistrationNo()).isEmpty()) {
                throw new BusinessException("ERROR_DUPLICATE_STUDENT_NUMBER");
            }
        }
        try {
            StudentData _studentData = studentService.saveStudent(
                    schoolStudentData.getStudentData());
            //assign the new value to the school student object
            schoolStudentData.setStudentData(_studentData);
            SchoolStudent schoolStudent = schoolStudentData.getSchoolStudent();
            //save the school student
            schoolStudent = schoolStudentRepo.save(schoolStudent);
            schoolStudentData.setSchoolStudent(schoolStudent);

            return schoolStudentData;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public SchoolStudentData createSchoolStudent(
            SchoolStudentData schoolStudentData, Parent parent) throws BusinessException {
        LOG.entering("SchoolStudentServiceImpl", "createSchoolStudent", schoolStudentData);
        Integer schoolId = schoolStudentData.getSchoolId();
        //parent and student should at first have same residential address
        parent.setAddress1(schoolStudentData.getAddress());
        parent.setModifiedBy(schoolStudentData.getModifiedBy());
        //create the schoolstudent first
        SchoolStudentData _data = createSchoolStudent(schoolStudentData);
        //assign the parent to the student
        StudentData studentData = _data.getStudentData();
        studentData.setModifiedBy(parent.getModifiedBy());
        studentService.assignNewParent(parent, studentData);
        //save the school-parent relationship
        createSchoolParent(parent, schoolId);
        //return the school student data
        return _data;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public void createSchoolStudentsWithParents(
            List<SchoolStudentData> schoolStudentDataList, List<Parent> parentList)
            throws BusinessException {
        for (int i = 0; i < schoolStudentDataList.size(); i++) {
            createSchoolStudent(schoolStudentDataList.get(i), parentList.get(i));
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public SchoolStudentData saveSchoolStudent(SchoolStudentData schoolStudentData)
            throws BusinessException {
        StudentData studentData = schoolStudentData.getStudentData();
        try {
            studentService.saveStudent(studentData);
            SchoolStudent schoolStudent = schoolStudentData.getSchoolStudent();
            schoolStudent = schoolStudentRepo.save(schoolStudent);
            schoolStudentData.setSchoolStudent(schoolStudent);
            return schoolStudentData;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void addStudentProfilePicture(SchoolStudentData schoolStudentData)
            throws BusinessException {
        SchoolStudent _schoolStudent = schoolStudentRepo.findOne(
                schoolStudentData.getSchoolStudentId());
        if (_schoolStudent == null) {
            throw new BusinessException(ErrorConstants.OBJ_DOES_NOT_EXIST);
        }
        _schoolStudent.setPictureUrl(schoolStudentData.getPictureUrl());
        _schoolStudent.setModifiedBy(schoolStudentData.getModifiedBy());

        try {
            schoolStudentRepo.save(_schoolStudent);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public SchoolStudentData findSchoolStudent(Integer schoolStudentId) {
        SchoolStudent schoolStudent = schoolStudentRepo.findOne(schoolStudentId);
        if (schoolStudent == null) {
            return null;
        }
        School school = schoolRepo.findOne(schoolStudent.getSchoolYear().getSchoolId());
        SchoolStudentData data = new SchoolStudentData(schoolStudent);
        data.setSchool(school.getName());

        return data;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<SchoolStudentData> findSchoolStudents(Integer schoolId) {
        List<SchoolStudent> schoolStudents = schoolStudentRepo.findBySchool(schoolId);
        List<SchoolStudentData> dataList = new ArrayList<>(schoolStudents.size());
        for (SchoolStudent ss : schoolStudents) {
            dataList.add(new SchoolStudentData(ss));
        }
        return dataList;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<SchoolStudentData> findSchoolStudents(SchoolStudentQueryCriteria criteria) {
        List<SchoolStudent> schoolStudents = schoolCriteriaSearchRepo.findSchoolStudents(criteria);
        List<SchoolStudentData> dataList = new ArrayList<>(schoolStudents.size());
        for (SchoolStudent ss : schoolStudents) {
            dataList.add(new SchoolStudentData(ss));
        }
        return dataList;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<Parent> findSchoolParents(SchoolParentQueryCriteria criteria) {
        List<SchoolParent> schoolParents = schoolCriteriaSearchRepo.findSchoolParents(criteria);
        List<Parent> parents = new ArrayList<>(schoolParents.size());
        for (SchoolParent sp : schoolParents) {
            parents.add(sp.getParent());
        }
        return parents;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<SchoolSubject> findSchoolSubjectsByStudent(Integer schoolStudentId) {
        return studentSubjectRepo.findSubjectByStudent(schoolStudentId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public void assignSubjectsToStudent(
            List<SchoolSubject> schoolSubjects,
            StudentSubject studentSubject) throws BusinessException {
        //get all school subjects for that student
        List<StudentSubject> allStudentSubjects = studentSubjectRepo.findAllSubjectByStudent(
                studentSubject.getSchoolStudent().getSchoolStudentId());
        //run a for loop and disable all the entry by setting status to I
        for (StudentSubject ss : allStudentSubjects) {
            if (!schoolSubjects.contains(ss.getSchoolSubject())) {
                ss.setStatus(CommonConstants.STATUS_INACTIVE);
            } else {
                //if school subject exists, activate it by setting the record to A
                ss.setStatus(CommonConstants.STATUS_ACTIVE);
            }
            ss.setModifiedBy(studentSubject.getModifiedBy());
            ss.setModifiedTime(new Date());
        }
        //run another loop for all the new school subject
        StudentSubject studentSubject1;
        for (SchoolSubject sSubject : schoolSubjects) {
            if (!allContainsStudentSubject(allStudentSubjects, sSubject)) {
                //if the school subject doesn't exist, create a new one
                studentSubject1 = new StudentSubject();

                studentSubject1.setModifiedBy(studentSubject.getModifiedBy());
                studentSubject1.setModifiedTime(new Date());
                studentSubject1.setSchoolStudent(studentSubject.getSchoolStudent());
                studentSubject1.setSchoolSubject(sSubject);
                studentSubject1.setStatus(CommonConstants.STATUS_ACTIVE);

                allStudentSubjects.add(studentSubject1);
            }
        }
        try {
            studentSubjectRepo.save(allStudentSubjects);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<SchoolSubject> findSubjectsByClass(Integer schoolClassId) {
        return studentSubjectRepo.findSubjectsByClass(schoolClassId);

    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public StudentClub saveStudentClub(StudentClub studentClub) throws BusinessException {
        if (studentClub.getStudentClubId() != null
                && !studentClubRepo.findDuplicate(studentClub.getSchoolStudent(), studentClub.getClub(),
                studentClub.getSchoolYear(), studentClub.getStudentClubId()).isEmpty()) {
            throw new BusinessException("ERROR_DUPLICATE_RECORD");
        }
        studentClub.setStatus(CommonConstants.STATUS_ACTIVE);
        studentClub.setModifiedTime(new Date());
        try {
            return studentClubRepo.save(studentClub);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentClub> findStudentClubs(Integer studentId) {
        return studentClubRepo.findStudentClubs(studentId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentClub> findStudentClubs(Integer schoolId, String registrationNo) {
        return studentClubRepo.findStudentClubs(schoolId, registrationNo);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public StudentDiscipline saveStudentDiscipline(StudentDiscipline studentDiscipline)
            throws BusinessException {
        studentDiscipline.setStatus(CommonConstants.STATUS_ACTIVE);
        studentDiscipline.setModifiedTime(new Date());
        try {
            SchoolStudent _schooStudent = schoolStudentRepo.findOne(
                    studentDiscipline.getSchoolStudent().getSchoolStudentId());
            studentDiscipline.setSchoolStudent(_schooStudent);
            studentDiscipline = studentDisciplineRepo.save(studentDiscipline);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
        return studentDiscipline;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDiscipline> findStudentDisciplines(Integer studentId) {
        return studentDisciplineRepo.findStudentDisciplines(studentId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentDiscipline> findStudentDisciplines(Integer schoolId, String registrationNo) {
        return studentDisciplineRepo.findStudentDisciplines(schoolId, registrationNo);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public StudentSport saveStudentSport(StudentSport studentSport) throws BusinessException {
        if (studentSport.getStudentSportId() != null
                && !studentSportRepo.findDuplicate(studentSport.getSchoolStudent(), studentSport.getSportCategory(),
                studentSport.getSchoolYear(), studentSport.getStudentSportId()).isEmpty()) {
            throw new BusinessException("ERROR_DUPLICATE_RECORD");
        }
        studentSport.setStatus(CommonConstants.STATUS_ACTIVE);
        studentSport.setModifiedTime(new Date());
        try {
            return studentSportRepo.save(studentSport);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentSport> findStudentSports(Integer studentId) {
        return studentSportRepo.findStudentSports(studentId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentSport> findStudentSports(Integer schoolId, String registrationNo) {
        return studentSportRepo.findStudentSports(schoolId, registrationNo);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public Parent findStudentParent(Integer schoolStudentId) {
        return parentRepo.findSchoolStudentParent(schoolStudentId);
    }

    /**
     * generates a String of format YYMM/99999 and recursively create a new one
     * if the generated String already exists in the database for the same
     * school
     *
     * @param schoolStudent - school student for which the registration number
     * is being created
     * @return String - a newly created registration number
     */
    private String buildStudentNumber(SchoolStudentData schoolStudentData) {
        String ids = StringUtil.getIDFromNanoTime(5);
        StringBuilder regisNumber = new StringBuilder(new SimpleDateFormat("yyMM").
                format(schoolStudentData.getAdmissionDate())).append("/").append(ids);
        if (!schoolStudentRepo.findByRegistrationNo(
                schoolStudentData.getSchoolId(), regisNumber.toString()).isEmpty()) {
            return buildStudentNumber(schoolStudentData);
        }
        return regisNumber.toString();
    }

    /**
     *
     * @param SchoolStudent
     * @throws BusinessException
     */
    private void setVariablesForSchoolStudent(SchoolStudentData ssd) throws BusinessException {
        //set variable for school class
        String classCode = ssd.getSchoolClassCode();
        if (classCode != null && !classCode.isEmpty()) {
            SchoolClass schoolClass = schoolClassRepo.findByCode(
                    classCode, ssd.getSchoolId());
            if (schoolClass == null) {
                throw new BusinessException("ERROR_INVALID SCHOOL CLASS ENTERED AS " + classCode);
            }
            ssd.setSchoolClassId(schoolClass.getSchoolClassId());
        }
        //set for home state address
        String stateCode = ssd.getAddress().getState().getStateCode();
        State state;
        if (stateCode != null && !stateCode.trim().isEmpty()) {
            state = stateRepo.findOne(stateCode);
            if (state == null) {
                throw new BusinessException("ERROR_INVALID STATE ADDRESS ENTERED AS " + stateCode);
            }
            ssd.getAddress().setState(state);
        }
        //search for parent
        String parentId = ssd.getParentId();
        Parent parent;
        if (parentId != null && !parentId.trim().isEmpty()) {
            parent = parentRepo.findOne(parentId);
            if (parent == null) {
                throw new BusinessException("ERROR_INVALID PARENT RECORD " + parentId);
            }
            ssd.setParentId(parentId);
        }
    }

    /**
     * returns true if {@code studentSubjects} contains a record whose {@link StudentSubject#getSchoolSubject()
     * } is equal to {@code sSubject}, otherwise returns false given that
     * {@code sSubject} is not null
     *
     * @param studentSubjects - List<StudentSubject>
     * @param sSubject - SchoolSubject
     * @return true if {@code studentSubjects} contains a record whose {@link StudentSubject#getSchoolSubject()
     * } is equal to {@code sSubject}, otherwise returns false given that
     * {@code sSubject} is not null
     */
    private boolean allContainsStudentSubject(List<StudentSubject> studentSubjects,
            SchoolSubject sSubject) {
        if (sSubject == null) {
            return false;
        }
        for (StudentSubject sss : studentSubjects) {
            if (sSubject.equals(sss.getSchoolSubject())) {
                return true;
            }
        }
        return false;
    }

    /**
     * create a new record for a parent and school. if an existing record is
     * found prior to creation, method returns without persisting the new object
     *
     * @param parent
     * @param schoolId
     */
    private void createSchoolParent(Parent parent, Integer schoolId) {
        if (!schoolParentRepo.findExisting(parent.getPhoneNumber(), schoolId).isEmpty()) {
            return;
        }
        //save the new record
        try {
            schoolParentRepo.save(new SchoolParent(parent, schoolId));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}