/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.Assignment;
import com.okmich.schoolruns.common.entity.Ebook;
import com.okmich.schoolruns.common.entity.Exam;
import com.okmich.schoolruns.common.entity.ExamBatch;
import com.okmich.schoolruns.common.entity.Holiday;
import com.okmich.schoolruns.common.entity.PicAlbum;
import com.okmich.schoolruns.common.entity.School;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.common.entity.SchoolClassTeacher;
import com.okmich.schoolruns.common.entity.SchoolParent;
import com.okmich.schoolruns.common.entity.SchoolSection;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.common.entity.SubjectTeacher;
import com.okmich.schoolruns.common.entity.TermExamRecord;
import com.okmich.schoolruns.common.entity.TermExamRecordDetails;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface SchoolCriteriaSearchRepo extends Serializable {

    /**
     *
     * @param criteria
     * @return List<Assignment>
     */
    List<Assignment> findAssignments(AssignmentQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<Ebook>
     */
    List<Ebook> findEbooks(EbookQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<ExamBatch>
     */
    List<ExamBatch> findExamBatchs(ExamBatchQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<Exam>
     */
    List<Exam> findExams(ExamQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<Holiday>
     */
    List<Holiday> findHolidays(HolidayQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<PicAlbum>
     */
    List<PicAlbum> findPicAlbums(PicAlbumQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<SchoolSection>
     */
    List<SchoolSection> findSchoolSections(SchoolSectionQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<School>
     */
    List<School> findSchools(SchoolQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<SchoolClass>
     */
    List<SchoolClass> findSchoolClasses(SchoolClassQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<SchoolClassTeacher>
     */
    List<SchoolClassTeacher> findSchoolClassTeacher(SchoolClassTeacherQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<SchoolParent>
     */
    List<SchoolParent> findSchoolParents(SchoolParentQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<SchoolStudent>
     */
    List<SchoolStudent> findSchoolStudents(SchoolStudentQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<SchoolSubject>
     */
    List<SchoolSubject> findSchoolSubjects(SchoolSubjectQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<SubjectTeacher>
     */
    List<SubjectTeacher> findSubjectTeachers(SubjectTeacherQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<TermExamRecord>
     */
    List<TermExamRecord> findTermExamRecords(TermExamRecordQueryCriteria criteria);

    /**
     *
     * @param criteria
     * @return List<TermExamRecordDetails>
     */
    List<TermExamRecordDetails> findTermExamRecordDetails(TermExamRecordDetailsQueryCriteria criteria);
}
