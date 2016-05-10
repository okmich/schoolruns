/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.student.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.StudentAward;
import com.okmich.schoolruns.student.service.data.StudentData;
import com.okmich.schoolruns.student.service.repo.ParentQueryCriteria;
import com.okmich.schoolruns.student.service.repo.StudentQueryCriteria;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface StudentService extends Serializable {

    /**
     *
     * @param studentData
     * @return
     * @throws BusinessException
     */
    StudentData saveStudent(StudentData studentData) throws BusinessException;

    /**
     *
     *
     * @param studentId
     * @return
     */
    StudentData findStudent(Integer studentId);

    /**
     *
     *
     * @param criteria
     * @return
     */
    List<StudentData> findStudents(StudentQueryCriteria criteria);

    /**
     *
     * @param phoneNumber
     * @return
     */
    List<StudentData> findParentWards(String phoneNumber);

    /**
     * create a list of parent record passed into this method.
     *
     * method is strictly transactional
     *
     * @param parents
     * @throws BusinessException
     */
    void createParents(List<Parent> parents) throws BusinessException;

    /**
     *
     *
     * @param parent
     * @return Parent
     * @throws BusinessException
     */
    Parent createParent(Parent parent) throws BusinessException;

    /**
     *
     *
     * @param parent
     * @return Parent
     * @throws BusinessException
     */
    Parent saveParent(Parent parent) throws BusinessException;

    /**
     * create a new parent and create a relationship with the student
     *
     * @param parent
     * @param studentData
     * @throws BusinessException
     */
    void assignNewParent(Parent parent, StudentData studentData) throws BusinessException;

    /**
     * creates a relationship line of a parent with a student who is currently
     * not connected with any parent. If the student currently has a not null
     * parent property and this method is called, a {@link BusinessException} is
     * thrown
     *
     * @param studentData
     * @param parent
     * @throws BusinessException
     */
    void assignParentToStudent(StudentData studentData, Parent parent) throws BusinessException;

    /**
     * deletes a relationship line of a parent with a student who is currently
     * not connected with any parent. If the student currently has a null parent
     * property and this method is called, a {@link BusinessException} is thrown
     *
     * @param studentData 4
     * @param parent
     * @throws BusinessException
     */
    void deassignParentFromStudent(StudentData studentData, Parent parent) throws BusinessException;

    /**
     *
     *
     * @param phoneNumber
     * @return Parent
     */
    Parent findParent(String phoneNumber);

    /**
     *
     *
     * @param studentId
     * @return Parent
     */
    Parent findStudentParent(Integer studentId);

    /**
     *
     *
     * @param schoolYearId
     * @return List<Parent>
     */
    List<Parent> findSchoolParents(Integer schoolYearId);

    /**
     *
     *
     * @param schoolYearId
     * @param schoolSectionId
     * @return
     */
    List<Parent> findParentsForSchoolSection(Integer schoolYearId, Integer schoolSectionId);

    /**
     *
     *
     * @param schoolYearId
     * @param gradeLevelId
     * @return
     */
    List<Parent> findParentsForSchoolGradeLevel(Integer schoolYearId, Integer gradeLevelId);

    /**
     *
     *
     * @param schoolYearId
     * @param schoolClassId
     * @return
     */
    List<Parent> findParentsForSchoolClass(Integer schoolYearId, Integer schoolClassId);

    /**
     *
     * @param criteria
     * @return
     */
    List<Parent> findParents(ParentQueryCriteria criteria);

    /**
     *
     *
     * @param studentAward
     * @return
     * @throws BusinessException
     */
    StudentAward saveStudentAward(StudentAward studentAward) throws BusinessException;

    /**
     *
     *
     * @param studentAwardId
     * @return
     */
    StudentAward findStudentAward(Integer studentAwardId);

    /**
     *
     *
     * @param studentId
     * @return
     */
    List<StudentAward> findStudentAwards(Integer studentId);
}
