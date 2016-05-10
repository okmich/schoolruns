/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.common.entity.StudentClub;
import com.okmich.schoolruns.common.entity.StudentDiscipline;
import com.okmich.schoolruns.common.entity.StudentSport;
import com.okmich.schoolruns.common.entity.StudentSubject;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.school.service.repo.SchoolParentQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolStudentQueryCriteria;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface SchoolStudentService extends Serializable {

    /**
     * if the {@code isNewSchoolYear} is true, a new version of the
     * {@link SchoolStudents} are created for the new year and in each class.
     * However, a simple update is performed if the boolean parameter resolves
     * false.
     *
     * @param studentList - students to be assigned
     * @param schoolStudent - the school student
     * @param isNewSchoolYear - to indicate that the operation is to promote all
     * affected students. If false, a simple update is performed on all the
     * students
     * @throws BusinessException - if error occurs
     */
    void assignStudentToClass(List<SchoolStudentData> studentList, SchoolStudentData schoolStudentData,
            boolean isNewSchoolYear) throws BusinessException;

    /**
     * save a list of students into the system. Implementation is by calling in
     * a for loop the createSchoolStudent(SchoolStudent) method on each of the
     * objects in the list. The assumption that the student already exists in
     * the student database is taken care of
     *
     * @param schoolStudents - a list of school students
     * @throws BusinessException - if error occurs
     */
    void createSchoolStudents(List<SchoolStudentData> schoolStudents) throws BusinessException;

    /**
     * save a newly admitted student into the school for a particular academic
     * year. Assumptions are that the student exists in the database, and has
     * been assigned to a class is taken care of. This method will hopefully be
     * called every year or the {@link assignStudentToClass} method will be
     * called to move students from class to class for a new year session
     *
     * @param schoolStudent - student to be created in school for a year and
     * class
     * @return SchoolStudent - already created student for that year and class
     * @throws BusinessException - if any error occurs
     */
    SchoolStudentData createSchoolStudent(SchoolStudentData schoolStudentData)
            throws BusinessException;

    /**
     * create new school student with the parent information in one.
     *
     * @param schoolStudentData
     * @param parent
     * @return SchoolStudentData
     * @ throws BusinessException
     */
    SchoolStudentData createSchoolStudent(
            SchoolStudentData schoolStudentData, Parent parent) throws BusinessException;

    /**
     * a batch creation functionality for school students and parents.
     * Implementation is by calling the {@link SchoolStudentService#createSchoolStudent(com.okmich.schoolruns.school.service.data.SchoolStudentData, com.okmich.schoolruns.common.entity.Parent)
     * }
     * in the loop for each student
     *
     * @param schoolStudentDataList
     * @param parentList
     * @throws BusinessException - if error occurs
     */
    void createSchoolStudentsWithParents(
            List<SchoolStudentData> schoolStudentDataList, List<Parent> parentList)
            throws BusinessException;

    /**
     *
     *
     * @param schoolStudentData - object to be updated
     * @return SchoolStudent - updated object
     * @throws BusinessException - if error occurs
     */
    SchoolStudentData saveSchoolStudent(SchoolStudentData schoolStudentData) throws BusinessException;

    /**
     * adds a new picture url to the school student record
     *
     * @param schoolStudentData
     * @throws BusinessException
     */
    void addStudentProfilePicture(SchoolStudentData schoolStudentData)
            throws BusinessException;

    /**
     *
     * @param schoolStudentId
     * @return
     */
    SchoolStudentData findSchoolStudent(Integer schoolStudentId);

    /**
     *
     * @param schoolId
     * @return
     */
    List<SchoolStudentData> findSchoolStudents(Integer schoolId);

    /**
     *
     * find a list of school students that satisfy the query criteria passed in
     * as parameter
     *
     * @param criteria
     * @return
     */
    List<SchoolStudentData> findSchoolStudents(SchoolStudentQueryCriteria criteria);

    /**
     *
     * find a list of parents that satisfy the query criteria passed in as
     * parameter
     *
     * @param criteria
     * @return List<Parent>
     */
    List<Parent> findSchoolParents(SchoolParentQueryCriteria criteria);

    /**
     *
     *
     * @param schoolStudentId
     * @return List<SchoolSubject>
     */
    List<SchoolSubject> findSchoolSubjectsByStudent(Integer schoolStudentId);

    /**
     * assigns the school subjects to the student represented by {@link StudentSubject#getSchoolStudent()
     * }. Note that all previously stored student subject will be deleted unless
     * they have be deactivated for such - deactivated simply means that their
     * status now read 'I' instead of 'A'
     *
     * @param schoolSubjects
     * @param studentSubject
     * @throws BusinessException
     */
    void assignSubjectsToStudent(List<SchoolSubject> schoolSubjects,
            StudentSubject studentSubject) throws BusinessException;

    /**
     * returns a list of school subject that should be offered by the class
     * identified by {@code schoolClassId}
     *
     * @param schoolClassId
     * @return List<SchoolSubject>
     */
    List<SchoolSubject> findSubjectsByClass(Integer schoolClassId);

    /**
     *
     *
     * @param studentClub
     * @return StudentClub
     * @throws BusinessException
     */
    StudentClub saveStudentClub(StudentClub studentClub) throws BusinessException;

    /**
     *
     *
     * @param studentId
     * @return
     */
    List<StudentClub> findStudentClubs(Integer studentId);

    /**
     *
     *
     * @param schoolId
     * @param registrationNo
     * @return
     */
    List<StudentClub> findStudentClubs(Integer schoolId, String registrationNo);

    /**
     *
     *
     * @param studentDiscipline
     * @return StudentDiscipline
     * @throws BusinessException
     */
    StudentDiscipline saveStudentDiscipline(StudentDiscipline studentDiscipline) throws BusinessException;

    /**
     *
     *
     * @param studentId
     * @return List<StudentDiscipline>
     */
    List<StudentDiscipline> findStudentDisciplines(Integer studentId);

    /**
     *
     *
     * @param schoolId
     * @param registrationNo
     * @return List<StudentDiscipline>
     */
    List<StudentDiscipline> findStudentDisciplines(Integer schoolId, String registrationNo);

    /**
     *
     *
     * @param studentSport
     * @return StudentSport
     * @throws BusinessException
     */
    StudentSport saveStudentSport(StudentSport studentSport) throws BusinessException;

    /**
     *
     *
     * @param studentId
     * @return List<StudentSport>
     */
    List<StudentSport> findStudentSports(Integer studentId);

    /**
     *
     *
     * @param schoolId
     * @param registrationNo
     * @return List<StudentSport>
     */
    List<StudentSport> findStudentSports(Integer schoolId, String registrationNo);

    /**
     *
     * @param schoolStudentId
     * @return Parent
     */
    Parent findStudentParent(Integer schoolStudentId);
}
