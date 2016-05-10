/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.common.entity.repo.CriteriaSearchWorker;
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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Repository("schoolCriteriaSearchRepo")
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class SchoolCriteriaSearchRepoImpl implements SchoolCriteriaSearchRepo {

    /**
     * assignmentrepoWorker - assignments Criteria Search Repo
     */
    private CriteriaSearchWorker<Assignment, AssignmentQueryCriteria> assignmentrepoWorker;
    /**
     * ebookRepoWorker - ebook Criteria Search Repo
     */
    private CriteriaSearchWorker<Ebook, EbookQueryCriteria> ebookRepoWorker;
    /**
     * examBatchrepoWorker - exam batch Criteria Search Repo
     */
    private CriteriaSearchWorker<ExamBatch, ExamBatchQueryCriteria> examBatchrepoWorker;
    /**
     * examrepoWorker - exam Criteria Search Repo
     */
    private CriteriaSearchWorker<Exam, ExamQueryCriteria> examrepoWorker;
    /**
     * holidayRepoWorker - holiday Criteria Search Repo
     */
    private CriteriaSearchWorker<Holiday, HolidayQueryCriteria> holidayRepoWorker;
    /**
     * picAlbumRepoWorker - picture album Criteria Search Repo
     */
    private CriteriaSearchWorker<PicAlbum, PicAlbumQueryCriteria> picAlbumRepoWorker;
    /**
     * schoolSectionrepoWorker - Section head Criteria Search Repo
     */
    private CriteriaSearchWorker<SchoolSection, SchoolSectionQueryCriteria> schoolSectionrepoWorker;
    /**
     * schoolClassrepoWorker - School Class Criteria Search Repo
     */
    private CriteriaSearchWorker<SchoolClass, SchoolClassQueryCriteria> schoolClassrepoWorker;
    /**
     * schoolClassTeacherrepoWorker - School Class teacher Criteria Search Repo
     */
    private CriteriaSearchWorker<SchoolClassTeacher, SchoolClassTeacherQueryCriteria> schoolClassTeacherRepoWorker;
    /**
     * schoolrepoWorker - School Criteria Search Repo
     */
    private CriteriaSearchWorker<School, SchoolQueryCriteria> schoolrepoWorker;
    /**
     * schoolParentrepoWorker - school parents Criteria Search Repo
     */
    private CriteriaSearchWorker<SchoolParent, SchoolParentQueryCriteria> schoolParentrepoWorker;
    /**
     * schoolStudentrepoWorker - School students Criteria Search Repo
     */
    private CriteriaSearchWorker<SchoolStudent, SchoolStudentQueryCriteria> schoolStudentrepoWorker;
    /**
     * schoolSubjectrepoWorker - School subject Criteria Search Repo
     */
    private CriteriaSearchWorker<SchoolSubject, SchoolSubjectQueryCriteria> schoolSubjectrepoWorker;
    /**
     * schoolSubjectrepoWorker - School subject Criteria Search Repo
     */
    private CriteriaSearchWorker<SubjectTeacher, SubjectTeacherQueryCriteria> subjectTeacherrepoWorker;
    /**
     * termExamRecordRepoWorker - term exam record Criteria Search Repo
     */
    private CriteriaSearchWorker<TermExamRecord, TermExamRecordQueryCriteria> termExamRecordRepoWorker;
    /**
     * termExamRecordDetailsRepoWorker - term exam record details Criteria
     * Search Repo
     */
    private CriteriaSearchWorker<TermExamRecordDetails, TermExamRecordDetailsQueryCriteria> termExamRecordDetailsRepoWorker;
    /**
     * entityManager
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     *
     */
    public SchoolCriteriaSearchRepoImpl() {
        assignmentrepoWorker = new CriteriaSearchWorker<>();
        ebookRepoWorker = new CriteriaSearchWorker<>();
        examBatchrepoWorker = new CriteriaSearchWorker<>();
        examrepoWorker = new CriteriaSearchWorker<>();
        picAlbumRepoWorker = new CriteriaSearchWorker<>();
        schoolSectionrepoWorker = new CriteriaSearchWorker<>();
        schoolrepoWorker = new CriteriaSearchWorker<>();
        schoolClassrepoWorker = new CriteriaSearchWorker<>();
        schoolClassTeacherRepoWorker = new CriteriaSearchWorker<>();
        schoolStudentrepoWorker = new CriteriaSearchWorker<>();
        schoolSubjectrepoWorker = new CriteriaSearchWorker<>();
        subjectTeacherrepoWorker = new CriteriaSearchWorker<>();
        termExamRecordRepoWorker = new CriteriaSearchWorker<>();
        termExamRecordDetailsRepoWorker = new CriteriaSearchWorker<>();
        holidayRepoWorker = new CriteriaSearchWorker<>();
        schoolParentrepoWorker = new CriteriaSearchWorker<>();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Assignment> findAssignments(AssignmentQueryCriteria criteria) {
        return assignmentrepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<ExamBatch> findExamBatchs(ExamBatchQueryCriteria criteria) {
        return examBatchrepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Ebook> findEbooks(EbookQueryCriteria criteria) {
        return ebookRepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Exam> findExams(ExamQueryCriteria criteria) {
        return examrepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Holiday> findHolidays(HolidayQueryCriteria criteria) {
        return holidayRepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<PicAlbum> findPicAlbums(PicAlbumQueryCriteria criteria) {
        return picAlbumRepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<SchoolSection> findSchoolSections(SchoolSectionQueryCriteria criteria) {
        return schoolSectionrepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<School> findSchools(SchoolQueryCriteria criteria) {
        return schoolrepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<SchoolClass> findSchoolClasses(SchoolClassQueryCriteria criteria) {
        return schoolClassrepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<SchoolClassTeacher> findSchoolClassTeacher(
            SchoolClassTeacherQueryCriteria criteria) {
        return schoolClassTeacherRepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<SchoolStudent> findSchoolStudents(SchoolStudentQueryCriteria criteria) {
        return schoolStudentrepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<SchoolSubject> findSchoolSubjects(SchoolSubjectQueryCriteria criteria) {
        return schoolSubjectrepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<SubjectTeacher> findSubjectTeachers(SubjectTeacherQueryCriteria criteria) {
        return subjectTeacherrepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<TermExamRecord> findTermExamRecords(TermExamRecordQueryCriteria criteria) {
        return termExamRecordRepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<TermExamRecordDetails> findTermExamRecordDetails(TermExamRecordDetailsQueryCriteria criteria) {
        return termExamRecordDetailsRepoWorker.findByCriteria(entityManager, criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<SchoolParent> findSchoolParents(SchoolParentQueryCriteria criteria) {
        return schoolParentrepoWorker.findByCriteria(entityManager, criteria);
    }
}