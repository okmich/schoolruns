/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.student.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.exception.ErrorConstants;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.*;
import com.okmich.schoolruns.common.service.CommonService;
import com.okmich.schoolruns.security.service.SecurityService;
import com.okmich.schoolruns.student.service.data.StudentData;
import com.okmich.schoolruns.student.service.repo.ParentQueryCriteria;
import com.okmich.schoolruns.student.service.repo.ParentRepo;
import com.okmich.schoolruns.student.service.repo.StudentAwardRepo;
import com.okmich.schoolruns.student.service.repo.StudentCriteriaSearchRepo;
import com.okmich.schoolruns.student.service.repo.StudentQueryCriteria;
import com.okmich.schoolruns.student.service.repo.StudentRepo;
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
@Service("studentService")
@Transactional
public class StudentServiceImpl implements StudentService {

    /**
     * LOG - class logger
     */
    private static final Logger LOG = Logger.getLogger(StudentServiceImpl.class.getName());
    /**
     * securityService
     */
    @Autowired
    private CommonService commonService;
    /**
     * securityService
     */
    @Autowired
    private SecurityService securityService;
    /**
     * parentRepo
     */
    @Autowired
    private ParentRepo parentRepo;
    /**
     * studentCriteriaSearchRepo
     */
    @Autowired
    private StudentCriteriaSearchRepo studentCriteriaSearchRepo;
    /**
     * studentRepo
     */
    @Autowired
    private StudentRepo studentRepo;
    /**
     * studentAwardRepo
     */
    @Autowired
    private StudentAwardRepo studentAwardRepo;

    /**
     * default constructor
     */
    public StudentServiceImpl() {
    }

    /**
     * {@inheritDoc )
     */
    @Override
    public StudentData saveStudent(StudentData studentData) throws BusinessException {
        studentData.setModifiedTime(new Date());
        studentData.setStatus(CommonConstants.STATUS_ACTIVE);
        try {
            Student student = studentData.getStudent();
            student = studentRepo.save(student);
            return new StudentData(student);
        } catch (Exception e) {
            Logger.getLogger(StudentServiceImpl.class.getName()).log(
                    Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc )
     */
    @Override
    @Transactional(readOnly = true)
    public StudentData findStudent(Integer studentId) {
        Student student = studentRepo.findOne(studentId);
        if (student == null) {
            return null;
        }
        return new StudentData(student);
    }

    /**
     * {@inheritDoc )
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentData> findStudents(StudentQueryCriteria criteria) {
        List<Student> students = studentCriteriaSearchRepo.findStudents(criteria);
        List<StudentData> dataList = new ArrayList<StudentData>(students.size());
        for (Student student : students) {
            dataList.add(new StudentData(student));
        }
        return dataList;
    }

    /**
     * {@inheritDoc )
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentData> findParentWards(String phoneNumber) {
        List<Student> students = studentRepo.findParentWards(phoneNumber);
        List<StudentData> dataList = new ArrayList<StudentData>(students.size());
        for (Student student : students) {
            dataList.add(new StudentData(student));
        }
        return dataList;
    }

    /**
     * {@inheritDoc )
     */
    @Override
    public void assignNewParent(Parent parent, StudentData studentData) throws BusinessException {
        Student _student = studentRepo.findOne(studentData.getStudentId());
        if (_student == null) {
            throw new BusinessException(ErrorConstants.CANNOT_FIND_STUDENT);
        }
        if (_student.getParent() != null) {
            throw new BusinessException(ErrorConstants.CANNOT_REPLACE_RELATIONSHIP);
        }
        Parent _parent = createParent(parent);
        try {
            _student.setParent(_parent);
            _student.setModifiedBy(studentData.getModifiedBy());
            _student.setModifiedTime(new Date());
            //save student record
            studentRepo.save(_student);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc )
     */
    @Override
    public void assignParentToStudent(StudentData studentData, Parent parent) throws BusinessException {
        Student _student = studentRepo.findOne(studentData.getStudentId());
        if (_student == null) {
            throw new BusinessException(ErrorConstants.CANNOT_FIND_STUDENT);
        }
        if (_student.getParent() != null) {
            throw new BusinessException(ErrorConstants.CANNOT_REPLACE_RELATIONSHIP);
        }
        Parent _parent = findParent(parent.getPhoneNumber());
        if (_parent == null) {
            throw new BusinessException(ErrorConstants.CANNOT_FIND_PARENT);
        }
        try {
            _student.setParent(_parent);
            _student.setModifiedBy(studentData.getModifiedBy());
            _student.setModifiedTime(new Date());
            //save student record
            studentRepo.save(_student);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc )
     */
    @Override
    public void deassignParentFromStudent(StudentData studentData, Parent parent) throws BusinessException {
        Student _student = studentRepo.findOne(studentData.getStudentId());
        if (_student == null) {
            throw new BusinessException(ErrorConstants.CANNOT_FIND_STUDENT);
        }
        if (_student.getParent() == null) {
            throw new BusinessException(ErrorConstants.CANNOT_REPLACE_NULL_RELATIONSHIP);
        }
        Parent _parent = findParent(parent.getPhoneNumber());
        if (_parent == null) {
            throw new BusinessException(ErrorConstants.CANNOT_FIND_PARENT);
        }
        try {
            _student.setParent(null);
            _student.setModifiedBy(studentData.getModifiedBy());
            _student.setModifiedTime(new Date());
            //save student record
            studentRepo.save(_student);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc )
     */
    @Override
    public Parent createParent(Parent parent) throws BusinessException {
        if (parent.getPhoneNumber() == null || parent.getPhoneNumber().isEmpty()) {
            throw new BusinessException(ErrorConstants.INVALID_PHONE_NUMBER);
        }
        //remove whitespaces from 
        parent.setPhoneNumber(parent.getPhoneNumber().replaceAll("\\s", ""));
        Parent _parent = findParent(parent.getPhoneNumber());
        if (_parent != null) {
            _parent.setModifiedBy(parent.getModifiedBy());
            _parent.setModifiedTime(new Date());

            return parentRepo.save(_parent);
        }
        parent.setStatus(CommonConstants.STATUS_ACTIVE);
        try {
            parent.setModifiedTime(new Date());
            //create an user login record for this employee
            UserLogin userLogin = createUserLoginRecord(parent);
            userLogin = securityService.createUserLogin(userLogin);
            //set the userlogin id field for this employee
            parent.setUserLoginId(userLogin.getUserLoginId());
            return parentRepo.save(parent);
        } catch (Exception e) {
            Logger.getLogger(StudentServiceImpl.class.getName()).log(
                    Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc )
     */
    @Override
    public void createParents(List<Parent> parents) throws BusinessException {
        try {
            for (Parent parent : parents) {
                //adjust the variable variables
                setVariablesForParent(parent);
                createParent(parent);
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc )
     */
    @Override
    public Parent saveParent(Parent parent) throws BusinessException {
        parent.setModifiedTime(new Date());
        try {//save the UserLogin associated with this employee
            UserLogin userLogin = securityService.findUserLoginForParent(
                    parent.getPhoneNumber());
            userLogin.setModifiedBy(parent.getModifiedBy());
            userLogin.setStatus(parent.getStatus());
            userLogin.setEmail(parent.getEmail());
            userLogin.setPhoneNumber(parent.getPhoneNumber());
            securityService.saveUserLogin(userLogin);
            parent.setUserLoginId(userLogin.getUserLoginId());
            return parentRepo.save(parent);
        } catch (Exception e) {
            Logger.getLogger(StudentServiceImpl.class.getName()).log(
                    Level.SEVERE, e.getMessage(), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc )
     */
    @Override
    @Transactional(readOnly = true)
    public Parent findParent(String phoneNumber) {
        return parentRepo.findOne(phoneNumber);
    }

    /**
     * {@inheritDoc )
     */
    @Override
    @Transactional(readOnly = true)
    public List<Parent> findParents(ParentQueryCriteria criteria) {
        return studentCriteriaSearchRepo.findParents(criteria);
    }

    /**
     * {@inheritDoc )
     */
    @Override
    @Transactional(readOnly = true)
    public Parent findStudentParent(Integer studentId) {
        List<Parent> parents = studentRepo.findStudentParent(studentId);
        if (parents.isEmpty()) {
            return null;
        } else {
            return parents.get(0); //return the first and supposedly only element
        }
    }

    /**
     * {@inheritDoc )
     */
    @Override
    @Transactional(readOnly = true)
    public List<Parent> findParentsForSchoolSection(Integer schoolYearId, Integer schoolSectionId) {
        return parentRepo.findSchoolSectionParents(schoolYearId, schoolSectionId);
    }

    /**
     * {@inheritDoc )
     */
    @Override
    @Transactional(readOnly = true)
    public List<Parent> findParentsForSchoolGradeLevel(Integer schoolYearId, Integer gradeLevelId) {
        return parentRepo.findSchoolGradeParents(schoolYearId, gradeLevelId);
    }

    /**
     * {@inheritDoc )
     */
    @Override
    @Transactional(readOnly = true)
    public List<Parent> findParentsForSchoolClass(Integer schoolYearId, Integer schoolClassId) {
        return parentRepo.findSchoolClassParents(schoolYearId, schoolClassId);
    }

    /**
     * {@inheritDoc )
     */
    @Override
    @Transactional(readOnly = true)
    public List<Parent> findSchoolParents(Integer schoolYearId) {
        return parentRepo.findSchoolParents(new SchoolYear(schoolYearId));
    }

    /**
     * {@inheritDoc )
     */
    @Override
    public StudentAward saveStudentAward(StudentAward studentAward) throws BusinessException {
        studentAward.setStatus(CommonConstants.STATUS_ACTIVE);
        studentAward.setModifiedTime(new Date());
        try {
            return studentAwardRepo.save(studentAward);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc )
     */
    @Override
    @Transactional(readOnly = true)
    public StudentAward findStudentAward(Integer studentAwardId) {
        return studentAwardRepo.findOne(studentAwardId);
    }

    /**
     * {@inheritDoc )
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentAward> findStudentAwards(Integer studentId) {
        return studentAwardRepo.findStudentAward(studentId);
    }

    /**
     *
     *
     * @param employee
     * @return UserLogin
     */
    private UserLogin createUserLoginRecord(Parent parent) {
        UserLogin userLogin = new UserLogin();

        userLogin.setChangeOnNextLogin(CommonConstants.CONSTANT_YES);
        userLogin.setDefaultSystemRole(new SystemRole(CommonConstants.ROLE_PARENT));
        userLogin.setEmail(parent.getEmail());
        userLogin.setPhoneNumber(parent.getPhoneNumber());
        userLogin.setGeneratedPassword(Boolean.TRUE);
        userLogin.setIsAdmin(CommonConstants.CONSTANT_NO);
        userLogin.setLoginAttempts(0);
        userLogin.setModifiedBy(parent.getModifiedBy());
        userLogin.setSchool(null);
        userLogin.setSchoolAdmin(false);
        userLogin.setStatus(parent.getStatus());
        userLogin.setTitle(parent.getFullname());
        userLogin.setUsername(parent.getPhoneNumber());
        userLogin.setPassword(userLogin.getUsername()); //parents' phone number

        return userLogin;
    }

    /**
     *
     * @param parent
     */
    private void setVariablesForParent(Parent parent) throws BusinessException {
        Title title = parent.getTitle();
        if (title != null) {
            if (commonService.findTitle(title.getTitleCode()) == null) {
                throw new BusinessException("ERROR_TITLE_CODE " + title.getTitleCode());
            }
        }
        State state = parent.getState();
        if (state != null) {
            if (commonService.findState(state.getStateCode()) == null) {
                throw new BusinessException("ERROR_STATE_CODE " + state.getStateCode());
            }
        }
    }
}
