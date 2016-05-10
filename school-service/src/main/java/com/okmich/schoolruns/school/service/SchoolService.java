/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.schoolruns.common.entity.*;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.school.service.repo.EbookQueryCriteria;
import com.okmich.schoolruns.school.service.repo.PicAlbumQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolClassQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolClassTeacherQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolSectionQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolSubjectQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SubjectTeacherQueryCriteria;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface SchoolService extends Serializable {

    /**
     *
     * @param classroom
     * @return Classroom
     * @throws BusinessException
     */
    Classroom saveClassroom(Classroom classroom) throws BusinessException;

    /**
     *
     * @param classroomId
     * @return Classroom
     */
    Classroom findClassroom(Integer classroomId);

    /**
     *
     * @param schoolId
     * @return List<Classroom>
     */
    List<Classroom> findSchoolClassrooms(Integer schoolId);

    /**
     *
     * @param school
     * @return
     * @throws BusinessException
     */
    School createSchool(School school) throws BusinessException;

    /**
     *
     * @param school
     * @return
     * @throws BusinessException
     */
    School saveSchool(School school) throws BusinessException;

    /**
     *
     * @param schoolId
     * @return
     * @throws BusinessException
     */
    School findSchool(Integer schoolId) throws BusinessException;

    /**
     *
     *
     * @param criteria
     * @return
     */
    List<School> findSchools(SchoolQueryCriteria criteria);

    /**
     * a user credential for the system identified with the school administrator
     * for this school. This credential will be the only credential for the
     * school with physical identity
     *
     * @param schoolId
     * @return UserLogin - a user credential for the system identified with the
     * school administrator for this school
     */
    UserLogin findSchoolAdminLogin(Integer schoolId);

    /**
     *
     *
     * @param schoolPref
     * @return
     * @throws BusinessException
     */
    SchoolPref saveSchoolPref(SchoolPref schoolPref) throws BusinessException;

    /**
     *
     *
     * @param schoolPref
     * @return
     * @throws BusinessException
     */
    SchoolPref findSchoolPref(Integer schoolId);

    /**
     *
     *
     * @param facilityList
     * @param school
     * @throws BusinessException
     */
    void updateSchoolFacilities(List<Facility> facilityList, School school)
            throws BusinessException;

    /**
     *
     *
     * @param schoolId
     * @return List<Facility>
     */
    List<Facility> findSchoolFacilitiesBySchool(Integer schoolId);

    /**
     *
     *
     * @param moduleList
     * @throws BusinessException
     */
    void assignModuleToSchool(List<Module> moduleList, School school) throws BusinessException;

    /**
     *
     * @param schoolId
     * @return
     * @throws BusinessException
     */
    List<Module> findSchoolModuleBySchool(Integer schoolId) throws BusinessException;

    /**
     *
     *
     * @param schoolClass
     * @return
     * @throws BusinessException
     */
    SchoolClass saveSchoolClass(SchoolClass schoolClass) throws BusinessException;

    /**
     * saves the list of school classes in the database
     *
     * @param schoolClasses
     * @throws BusinessException
     */
    void saveSchoolClasses(List<SchoolClass> schoolClasses) throws BusinessException;

    /**
     *
     * @param schoolClassId
     * @return
     */
    SchoolClass findSchoolClass(Integer schoolClassId);

    /**
     *
     * @param schoolId
     * @return
     */
    List<SchoolClass> findSchoolClasses(Integer schoolId);

    /**
     * find a list of school classes that satisfy the query criteria passed in
     * as parameter
     *
     * @param criteria
     * @return
     */
    List<SchoolClass> findSchoolClasses(SchoolClassQueryCriteria criteria);

    /**
     *
     * @param schoolClassTeacher
     * @return
     * @throws BusinessException
     */
    SchoolClassTeacher saveSchoolClassTeacher(SchoolClassTeacher schoolClassTeacher)
            throws BusinessException;

    /**
     *
     * @param schoolClassTeacherId
     * @return
     */
    SchoolClassTeacher findSchoolClassTeacher(Integer schoolClassTeacherId);

    /**
     *
     * find a list of school class teachers that satisfy the query criteria
     * passed in as parameter
     *
     * @param criteria
     * @return
     */
    List<SchoolClassTeacher> findSchoolClassTeachers(SchoolClassTeacherQueryCriteria criteria);

    /**
     *
     * @param schoolSubject
     * @return
     * @throws BusinessException
     */
    SchoolSubject saveSchoolSubject(SchoolSubject schoolSubject) throws BusinessException;

    /**
     *
     * @param schoolSubjects
     * @throws BusinessException
     */
    void saveSchoolSubjects(List<SchoolSubject> schoolSubjects) throws BusinessException;

    /**
     *
     * @param schoolSubjectId
     * @return
     */
    SchoolSubject findSchoolSubject(Integer schoolSubjectId);

    /**
     *
     * @param schoolId
     * @return
     */
    List<SchoolSubject> findSchoolSubjects(Integer schoolId);

    /**
     *
     * find a list of school subjects that satisfy the query criteria passed in
     * as parameter
     *
     * @param SchoolSubjectQueryCriteria
     * @return List<SchoolSubject> - list that satisfies the criteria object
     */
    List<SchoolSubject> findSchoolSubjects(SchoolSubjectQueryCriteria criteria);

    /**
     *
     *
     * @param subjectTeacher
     * @return
     * @throws BusinessException
     */
    SubjectTeacher saveSubjectTeacher(SubjectTeacher subjectTeacher) throws BusinessException;

    /**
     * school subjects in the list are assigned to the teacher represented by {@link SubjectTeacher#getEmployee()
     * } for the school academic year represented by
     * {@link SubjectTeacher#getSchoolYear()}. All previously existing entries
     * for the employee will be deleted from the database
     *
     * @param schoolSubjects
     * @param subjectTeacher
     * @throws BusinessException
     */
    void assignSubjectsToTeacher(List<SchoolSubject> schoolSubjects,
            SubjectTeacher subjectTeacher) throws BusinessException;

    /**
     *
     * @param subjectTeacherId
     * @return
     */
    SubjectTeacher findSubjectTeacher(Integer subjectTeacherId);

    /**
     * find a list of school subject teacher that satisfy the query criteria
     * passed in as parameter
     *
     * @param criteria
     * @return List<SubjectTeacher>
     */
    List<SubjectTeacher> findSubjectTeachers(SubjectTeacherQueryCriteria criteria);

    /**
     *
     * @param schoolSection
     * @return
     * @throws BusinessException
     */
    SchoolSection saveSchoolSection(SchoolSection schoolSection) throws BusinessException;

    /**
     *
     * @param schoolSectionId
     * @return
     */
    SchoolSection findSchoolSection(Integer schoolSectionId);

    /**
     *
     * @param schoolSectionId
     * @return List<SchoolSection>
     */
    List<SchoolSection> findSchoolSections(Integer schoolId);

    /**
     * find a list of school subject teacher that satisfy the query criteria
     * passed in as parameter
     *
     * @param criteria
     * @return List<SchoolSection>
     */
    List<SchoolSection> findSchoolSections(SchoolSectionQueryCriteria criteria);

    /**
     *
     *
     * @param messageData
     * @throws BusinessException
     */
    void sendNotificationMessage(MessageData messageData) throws BusinessException;

    /**
     *
     * @param club
     * @return Club
     */
    Club saveClub(Club club) throws BusinessException;

    /**
     *
     * @return List<Club>
     */
    List<Club> findClubs(Integer schoolId);

    /**
     *
     * @param ebook
     * @return
     * @throws BusinessException
     */
    Ebook saveEbook(Ebook ebook) throws BusinessException;

    /**
     *
     * @param ebookId
     * @return
     */
    Ebook findEbook(Integer ebookId);

    /**
     *
     * @param criteria
     * @return
     */
    List<Ebook> findEbooks(EbookQueryCriteria criteria);

    /**
     *
     *
     * @param picAlbum
     * @return PicAlbum
     * @throws BusinessException
     */
    PicAlbum savePicAlbum(PicAlbum picAlbum) throws BusinessException;

    /**
     *
     *
     * @param PicAlbumId
     * @return PicAlbum
     */
    PicAlbum findPicAlbum(Integer PicAlbumId);

    /**
     *
     *
     * @param criteria
     * @return List<PicAlbum>
     */
    List<PicAlbum> findPicAlbums(PicAlbumQueryCriteria criteria);
}
