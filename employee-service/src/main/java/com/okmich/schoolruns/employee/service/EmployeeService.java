/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.employee.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.schoolruns.common.entity.EmpEduQualification;
import com.okmich.schoolruns.common.entity.EmpProfQualification;
import com.okmich.schoolruns.common.entity.EmployeeAward;
import com.okmich.schoolruns.common.entity.EmployeeClub;
import com.okmich.schoolruns.common.entity.EmployeeQuery;
import com.okmich.schoolruns.common.entity.EmployeeSport;
import com.okmich.schoolruns.employee.service.data.EmployeeData;
import com.okmich.schoolruns.employee.service.repo.EmployeeQueryCriteria;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface EmployeeService extends Serializable {

    /**
     *
     *
     * @param employeeData
     * @return Employee - already created employee
     * @throws BusinessException
     */
    EmployeeData createEmployee(EmployeeData employeeData) throws BusinessException;

    /**
     *
     *
     * @param employees
     * @throws BusinessException
     */
    void createEmployees(List<EmployeeData> employeeDataList) throws BusinessException;

    /**
     *
     * @param employeeData
     * @return
     * @throws BusinessException
     */
    EmployeeData saveEmployee(EmployeeData employeeData) throws BusinessException;

    /**
     * adds a new picture url to the employee record
     *
     * @param employeeData
     * @throws BusinessException
     */
    void addEmployeeProfilePicture(EmployeeData employeeData) throws BusinessException;

    /**
     *
     *
     * @param employeeId
     * @return
     * @throws BusinessException
     */
    EmployeeData findEmployee(Integer employeeId);

    /**
     *
     *
     * @param userLoginId
     * @return EmployeeData
     */
    EmployeeData findEmployeeByLogin(Integer userLoginId);

    /**
     *
     *
     * @param criteria
     * @return
     */
    List<EmployeeData> findEmployees(EmployeeQueryCriteria criteria);

    /**
     *
     * @param empEduQualification
     * @return EmpEduQualification
     * @throws BusinessException
     */
    EmpEduQualification saveEmpEduQualification(EmpEduQualification empEduQualification)
            throws BusinessException;

    /**
     *
     * @param empEduQualification
     * @throws BusinessException
     */
    void deleteEmpEduQualification(EmpEduQualification empEduQualification)
            throws BusinessException;

    /**
     *
     * @param employeeId
     * @return List<EmpEduQualification>
     */
    List<EmpEduQualification> findEmpEduQualifications(Integer employeeId);

    /**
     *
     * @param empProfQualification
     * @return
     * @throws BusinessException
     */
    EmpProfQualification saveEmpProfQualification(EmpProfQualification empProfQualification)
            throws BusinessException;

    /**
     *
     * @param empProfQualification
     * @throws BusinessException
     */
    void deleteEmpProfQualification(EmpProfQualification empProfQualification)
            throws BusinessException;

    /**
     *
     * @param employeeId
     * @return
     */
    List<EmpProfQualification> findEmpProfQualifications(Integer employeeId);

    /**
     *
     * @param employeeClub
     * @return EmployeeClub
     * @throws BusinessException
     */
    EmployeeClub saveEmployeeClub(EmployeeClub employeeClub) throws BusinessException;

    /**
     *
     *
     * @param employeeId
     * @return EmployeeClub
     */
    List<EmployeeClub> findEmployeeClubs(Integer employeeId);

    /**
     *
     *
     * @param employeeAward
     * @return
     * @throws BusinessException
     */
    EmployeeAward saveEmployeeAward(EmployeeAward employeeAward) throws BusinessException;

    /**
     *
     *
     * @param employeeAwardId
     * @return
     */
    EmployeeAward findEmployeeAward(Integer employeeAwardId);

    /**
     *
     *
     * @param employeeId
     * @return
     */
    List<EmployeeAward> findEmployeeAwards(Integer employeeId);

    /**
     *
     *
     * @param employeeSport
     * @return
     * @throws BusinessException
     */
    EmployeeSport saveEmployeeSport(EmployeeSport employeeSport) throws BusinessException;

    /**
     *
     *
     * @param employeeSportId
     * @return
     */
    EmployeeSport findEmployeeSport(Integer employeeSportId);

    /**
     *
     *
     * @param employeeId
     * @return
     */
    List<EmployeeSport> findEmployeeSports(Integer employeeId);

    /**
     *
     *
     * @param employeeQuery
     * @return
     * @throws BusinessException
     */
    EmployeeQuery createEmployeeQuery(EmployeeQuery employeeQuery) throws BusinessException;

    /**
     *
     *
     * @param employeeQueryId
     * @return
     */
    EmployeeQuery findEmployeeQuery(Integer employeeQueryId);

    /**
     *
     *
     * @param employeeId
     * @return
     */
    List<EmployeeQuery> findEmployeeQueries(Integer employeeId);

    /**
     *
     *
     * @param employeeQuery
     * @return
     * @throws BusinessException
     */
    EmployeeQuery saveEmployeeQuery(EmployeeQuery employeeQuery) throws BusinessException;
}
