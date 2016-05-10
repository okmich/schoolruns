/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.employee.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.exception.ErrorConstants;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.EmpEduQualification;
import com.okmich.schoolruns.common.entity.EmpProfQualification;
import com.okmich.schoolruns.common.entity.Employee;
import com.okmich.schoolruns.common.entity.EmployeeAward;
import com.okmich.schoolruns.common.entity.EmployeeClub;
import com.okmich.schoolruns.common.entity.EmployeeQuery;
import com.okmich.schoolruns.common.entity.EmployeeSport;
import com.okmich.schoolruns.common.entity.School;
import com.okmich.schoolruns.common.entity.SystemRole;
import com.okmich.schoolruns.common.entity.UserLogin;
import com.okmich.schoolruns.employee.service.data.EmployeeData;
import com.okmich.schoolruns.employee.service.repo.*;
import com.okmich.schoolruns.security.service.SecurityService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = Logger.getLogger(EmployeeServiceImpl.class.getName());
    /**
     * securityService
     */
    @Autowired
    private SecurityService securityService;
    /**
     * employeeRepo
     */
    @Autowired
    private EmployeeRepo employeeRepo;
    /**
     * employeeClubRepo
     */
    @Autowired
    private EmployeeClubRepo employeeClubRepo;
    /**
     * empEduQualificationRepo
     */
    @Autowired
    private EmpEduQualificationRepo empEduQualificationRepo;
    /**
     * empProfQualificationRepo
     */
    @Autowired
    private EmpProfQualificationRepo empProfQualificationRepo;
    /**
     * employeeAwardRepo
     */
    @Autowired
    private EmployeeAwardRepo employeeAwardRepo;
    /**
     * employeeSportRepo
     */
    @Autowired
    private EmployeeSportRepo employeeSportRepo;
    /**
     * employeeQueryRepo
     */
    @Autowired
    private EmployeeQueryRepo employeeQueryRepo;
    /**
     * employeeCriteriaSearchRepo
     */
    @Autowired
    private EmployeeCriteriaSearchRepo employeeCriteriaSearchRepo;

    /**
     *
     */
    public EmployeeServiceImpl() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public EmployeeData createEmployee(EmployeeData employeeData) throws BusinessException {
        LOG.entering("EmployeeServiceImpl", "createEmployee", employeeData);
        //get the schools preference to check for student no policy
        if (employeeData.getStaffNumber() == null || employeeData.getStaffNumber().isEmpty()) {
            //create one
            employeeData.setStaffNumber(buildStaffNumber(employeeData));
        }
        if (!employeeRepo.findEmployeeByName(employeeData.getSurname(),
                employeeData.getOthernames(), employeeData.getSchoolId()).isEmpty()) {
            throw new BusinessException(ErrorConstants.EMPLOYEE_ALREADY_EXIST);
        }
        if (employeeData.getEmail() != null && !employeeData.getEmail().isEmpty()) {
            if (!employeeRepo.findEmployeeByEmail(employeeData.getEmail()).isEmpty()) {
                throw new BusinessException(ErrorConstants.EMPLOYEE_ALREADY_EXIST);
            }
        }
        //remove whitespaces from 
        employeeData.setMobileNo(employeeData.getMobileNo() == null
                ? employeeData.getMobileNo().replace("\\s", "") : "");
        employeeData.setModifiedTime(new Date());
        employeeData.setStatus(CommonConstants.STATUS_ACTIVE);
        try {
            Employee employee = employeeData.getEmployee();
            //create an user login record for this employee
            UserLogin userLogin = createUserLoginRecord(employee);
            userLogin = securityService.createUserLogin(userLogin);
            //set the userlogin id field for this employee
            employee.setUserLoginId(userLogin.getUserLoginId());
            employee = employeeRepo.save(employee);
            employeeData.setEmployee(employee);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return employeeData;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createEmployees(List<EmployeeData> dataList) throws BusinessException {
        try {
            for (EmployeeData e : dataList) {
                createEmployee(e);
            }
        } catch (BusinessException e) {
            throw e;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EmployeeData saveEmployee(EmployeeData employeeData) throws BusinessException {
        employeeData.setModifiedTime(new Date());
        try {
            Employee employee = employeeData.getEmployee();
            //save the UserLogin associated with this employee
            UserLogin userLogin = securityService.findUserLogin(
                    employeeData.getUserLoginId());
            userLogin.setModifiedBy(employee.getModifiedBy());
            userLogin.setStatus(employee.getStatus());
            userLogin.setEmail(employee.getEmail());
            userLogin.setPhoneNumber(employee.getMobileNo());
            securityService.saveUserLogin(userLogin);
            employee.setUserLoginId(userLogin.getUserLoginId());
            employee = employeeRepo.save(employee);
            employeeData.setEmployee(employee);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return employeeData;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void addEmployeeProfilePicture(EmployeeData employeeData) throws BusinessException {
        Employee _employee = employeeRepo.findOne(employeeData.getEmployeeId());
        if (_employee == null) {
            throw new BusinessException(ErrorConstants.OBJ_DOES_NOT_EXIST);
        }
        _employee.setPictureUrl(employeeData.getPictureUrl());
        _employee.setModifiedBy(employeeData.getModifiedBy());

        try {
            employeeRepo.save(_employee);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EmployeeData findEmployee(Integer employeeId) {
        Employee _emEmployee = employeeRepo.findOne(employeeId);
        if (_emEmployee == null) {
            return null;
        }
        return new EmployeeData(_emEmployee);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EmployeeData findEmployeeByLogin(Integer userLoginId) {
        Employee _emEmployee = employeeRepo.findByUserLogin(userLoginId);
        if (_emEmployee == null) {
            return null;
        }
        return new EmployeeData(_emEmployee);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<EmployeeData> findEmployees(EmployeeQueryCriteria criteria) {
        List<Employee> employees = employeeCriteriaSearchRepo.findEmployees(criteria);
        List<EmployeeData> dataList = new ArrayList<EmployeeData>(employees.size());
        for (Employee employee : employees) {
            dataList.add(new EmployeeData(employee));
        }
        return dataList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EmpEduQualification saveEmpEduQualification(EmpEduQualification empEduQualification)
            throws BusinessException {
        empEduQualification.setModifiedTime(new Date());
        try {
            empEduQualification = empEduQualificationRepo.save(empEduQualification);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return empEduQualification;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void deleteEmpEduQualification(EmpEduQualification empEduQualification) throws BusinessException {
        if (empEduQualification.getEmpEduQualificationId() != null) {
            try {
                empEduQualificationRepo.delete(empEduQualification);
            } catch (Exception e) {
                throw new BusinessException(StringUtil.getNestedErrorMessage(e));
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<EmpEduQualification> findEmpEduQualifications(Integer employeeId) {
        return empEduQualificationRepo.findEmployeeEduQualification(employeeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EmpProfQualification saveEmpProfQualification(EmpProfQualification empProfQualification)
            throws BusinessException {
        empProfQualification.setModifiedTime(new Date());
        try {
            empProfQualification = empProfQualificationRepo.save(empProfQualification);
        } catch (Exception e) {
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
        return empProfQualification;
    }

    @Override
    public void deleteEmpProfQualification(EmpProfQualification empProfQualification) throws BusinessException {
        if (empProfQualification.getEmpProfQualificationId() != null) {
            try {
                empProfQualificationRepo.delete(empProfQualification);
            } catch (Exception e) {
                throw new BusinessException(StringUtil.getNestedErrorMessage(e));
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<EmpProfQualification> findEmpProfQualifications(Integer employeeId) {
        return empProfQualificationRepo.findEmployeeProfQualification(employeeId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public EmployeeClub saveEmployeeClub(EmployeeClub employeeClub) throws BusinessException {
        if (employeeClub.getEmployeeClubId() != null
                && !employeeClubRepo.findDuplicate(employeeClub.getEmployee(), employeeClub.getClub(),
                employeeClub.getSchoolYear(), employeeClub.getEmployeeClubId()).isEmpty()) {
            throw new BusinessException("DUPLICATE_RECORD");
        }
        employeeClub.setStatus(CommonConstants.STATUS_ACTIVE);
        employeeClub.setModifiedTime(new Date());
        try {
            return employeeClubRepo.save(employeeClub);
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
    public List<EmployeeClub> findEmployeeClubs(Integer employeeId) {
        return employeeClubRepo.findEmployeeClubs(employeeId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public EmployeeAward saveEmployeeAward(EmployeeAward employeeAward) throws BusinessException {
        employeeAward.setStatus(CommonConstants.STATUS_ACTIVE);
        employeeAward.setModifiedTime(new Date());
        try {
            return employeeAwardRepo.save(employeeAward);
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
    public EmployeeAward findEmployeeAward(Integer employeeAwardId) {
        return employeeAwardRepo.findOne(employeeAwardId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeAward> findEmployeeAwards(Integer employeeId) {
        return employeeAwardRepo.findEmployeeAward(employeeId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public EmployeeSport saveEmployeeSport(EmployeeSport employeeSport) throws BusinessException {
        employeeSport.setStatus(CommonConstants.STATUS_ACTIVE);
        employeeSport.setModifiedTime(new Date());
        try {
            return employeeSportRepo.save(employeeSport);
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
    public EmployeeSport findEmployeeSport(Integer employeeSportId) {
        return employeeSportRepo.findOne(employeeSportId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeSport> findEmployeeSports(Integer employeeId) {
        return employeeSportRepo.findEmployeeSports(employeeId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public EmployeeQuery createEmployeeQuery(EmployeeQuery employeeQuery) throws BusinessException {
        employeeQuery.setStatus(CommonConstants.STATUS_INACTIVE);
        employeeQuery.setModifiedTime(new Date());
        try {
            return employeeQueryRepo.save(employeeQuery);
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
    public EmployeeQuery findEmployeeQuery(Integer employeeQueryId) {
        return employeeQueryRepo.findOne(employeeQueryId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeQuery> findEmployeeQueries(Integer employeeId) {
        return employeeQueryRepo.findEmployeeQueries(employeeId);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public EmployeeQuery saveEmployeeQuery(EmployeeQuery employeeQuery) throws BusinessException {
        EmployeeQuery _employeeQuery = findEmployeeQuery(employeeQuery.getEmployeeQueryId());
        try {
            _employeeQuery.setModifiedBy(employeeQuery.getModifiedBy());
            _employeeQuery.setQuery(employeeQuery.getQuery());
            _employeeQuery.setQueryDate(employeeQuery.getQueryDate());
            _employeeQuery.setReply(employeeQuery.getReply());
            _employeeQuery.setReplyDate(employeeQuery.getReplyDate());
            _employeeQuery.setModifiedTime(new Date());
            _employeeQuery.setWarningLevel(employeeQuery.getWarningLevel());
            if (employeeQuery.getReply() == null) {
                _employeeQuery.setStatus(CommonConstants.STATUS_ACTIVE);
            }
            return employeeQueryRepo.save(employeeQuery);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     *
     *
     * @param employee
     * @return UserLogin
     */
    private UserLogin createUserLoginRecord(Employee employee) {
        UserLogin userLogin = new UserLogin();

        userLogin.setChangeOnNextLogin(CommonConstants.CONSTANT_YES);
        userLogin.setDefaultSystemRole(new SystemRole(CommonConstants.ROLE_TEACHER));
        userLogin.setEmail(employee.getEmail());
        userLogin.setPhoneNumber(employee.getMobileNo());
        userLogin.setGeneratedPassword(Boolean.TRUE);
        userLogin.setIsAdmin(CommonConstants.CONSTANT_NO);
        userLogin.setLoginAttempts(0);
        userLogin.setModifiedBy(employee.getModifiedBy());
        userLogin.setSchool(new School(employee.getSchoolId()));
        userLogin.setSchoolAdmin(false);
        userLogin.setStatus(employee.getStatus());
        userLogin.setTitle(employee.getFullname());
        userLogin.setUsername(createUsernameforEmployee());
        userLogin.setPassword(userLogin.getUsername());

        return userLogin;
    }

    /**
     *
     *
     * @return
     */
    private String createUsernameforEmployee() {
        String usrname = "E" + StringUtil.getIDFromNanoTime(6);
        if (securityService.findUserLogin(usrname) != null) {
            return createUsernameforEmployee();
        }
        return usrname;
    }

    /**
     *
     * @param employee
     * @return
     */
    private String buildStaffNumber(EmployeeData employeeData) {
        String ids = StringUtil.getIDFromNanoTime(5);
        StringBuilder regisNumber = new StringBuilder(new SimpleDateFormat("yyMM").
                format(employeeData.getDateOfHire())).append("/").append(ids);
        if (!employeeRepo.findByStaffNumber(
                employeeData.getSchoolId(), ids).isEmpty()) {
            return buildStaffNumber(employeeData);
        }
        return regisNumber.toString();
    }
}