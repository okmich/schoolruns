/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.exception.ErrorConstants;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.*;
import com.okmich.schoolruns.common.service.repo.GradeBandRepo;
import com.okmich.schoolruns.employee.service.repo.EmployeeRepo;
import com.okmich.schoolruns.school.service.data.ExamScoreTable;
import com.okmich.schoolruns.school.service.repo.*;
import com.okmich.schoolruns.security.service.SecurityService;
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
@Service("schoolAcademicService")
@Transactional
public class SchoolAcademicServiceImpl implements SchoolAcademicService {

    /**
     * LOG - class logger
     */
    private static final Logger LOG = Logger.getLogger(SchoolAcademicServiceImpl.class.getName());
    /**
     * assignmentRepo
     */
    @Autowired
    private AssignmentRepo assignmentRepo;
    /**
     * assignmentScoreRepo
     */
    @Autowired
    private AssignmentScoreRepo assignmentScoreRepo;
    /**
     * examBatchRepo
     */
    @Autowired
    private ExamBatchRepo examBatchRepo;
    /**
     * examBatchClassRepo
     */
    @Autowired
    private ExamBatchClassRepo examBatchClassRepo;
    /**
     * examRepo
     */
    @Autowired
    private ExamRepo examRepo;
    /**
     * examScoreRepo
     */
    @Autowired
    private ExamScoreRepo examScoreRepo;
    /**
     * gradeBandRepo
     */
    @Autowired
    private GradeBandRepo gradeBandRepo;
    /**
     * termExamRecordDetailsRepo
     */
    @Autowired
    private TermExamRecordDetailsRepo termExamRecordDetailsRepo;
    /**
     * termExamRecordRepo
     */
    @Autowired
    private TermExamRecordRepo termExamRecordRepo;
    /**
     * schoolCriteriaSearchRepo
     */
    @Autowired
    private SchoolCriteriaSearchRepo schoolCriteriaSearchRepo;
    /**
     * schoolStudentRepo
     */
    @Autowired
    private SchoolStudentRepo schoolStudentRepo;
    /**
     * schoolSubjectRepo
     */
    @Autowired
    private SchoolSubjectRepo schoolSubjectRepo;
    /**
     * schoolClassRepo
     */
    @Autowired
    private SchoolClassRepo schoolClassRepo;
    /**
     * schoolClassTeacherRepo
     */
    @Autowired
    private SchoolClassTeacherRepo schoolClassTeacherRepo;
    /**
     * subjectTeacherRepo
     */
    @Autowired
    private SubjectTeacherRepo subjectTeacherRepo;
    /**
     * studentSubjectRepo
     */
    @Autowired
    private StudentSubjectRepo studentSubjectRepo;
    /**
     * employeeRepo
     */
    @Autowired
    private EmployeeRepo employeeRepo;
    /**
     * schoolPrefRepo
     */
    @Autowired
    private SchoolPrefRepo schoolPrefRepo;
    /**
     * securityService
     */
    @Autowired
    private SecurityService securityService;

    /**
     * default constructor
     */
    public SchoolAcademicServiceImpl() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Assignment saveAssignment(Assignment assignment) throws BusinessException {
        if (assignment.getStatus() != null && !assignment.getStatus().equals(CommonConstants.STATUS_INACTIVE)) {
            throw new BusinessException(ErrorConstants.INVALID_ASSIGNMENT_STATE);
        }
        try {
            assignment.setStatus(CommonConstants.STATUS_INACTIVE);
            return assignmentRepo.save(assignment);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public Assignment findAssignment(Integer assignmentId) {
        return assignmentRepo.findOne(assignmentId);
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public List<Assignment> findAssignments(AssignmentQueryCriteria criteria) {
        return schoolCriteriaSearchRepo.findAssignments(criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public List<AssignmentScore> findAssignmentScores(Integer assignmentId) {
        return assignmentScoreRepo.findByAssignment(assignmentId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void saveAssignmentScores(Assignment assignment,
            List<AssignmentScore> assignmentScores) throws BusinessException {
        Assignment _assignment = assignmentRepo.findOne(assignment.getAssignmentId());
        //ensure that the exam has not been committed before
        if (!_assignment.getStatus().equals(CommonConstants.STATUS_LOCKED)) {
            throw new BusinessException(ErrorConstants.SCORES_NOT_AVAILABLE_FOR_SAVING);
        }
        //verify authourity to save
        verifyAuthorityToCommit(assignment.getModifiedBy(), _assignment.getSchoolClass(),
                _assignment.getSchoolSubject());
        //set modified by field
        for (AssignmentScore asss : assignmentScores) {
            asss.setModifiedBy(assignment.getModifiedBy());
        }
        try {
            assignmentScoreRepo.save(assignmentScores);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void deleteAssignment(Assignment assignment) throws BusinessException {
        Assignment _assignment = findAssignment(assignment.getAssignmentId());
        if (!_assignment.getStatus().equals(CommonConstants.STATUS_INACTIVE)) {
            throw new BusinessException(ErrorConstants.CANNOT_DELETE_ASSIGNMENT);
        }
        try {
            assignmentRepo.delete(_assignment);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void prepareAssignmentScoreSheet(Assignment assignment) throws BusinessException {
        Assignment _assignment = findAssignment(assignment.getAssignmentId());
        if (_assignment == null
                || assignment.getStatus().equals(CommonConstants.STATUS_ACTIVE)) {
            throw new BusinessException(ErrorConstants.CANNOT_FIND_ASSIGNMENT);
        }
        if (_assignment.getStatus().equals(CommonConstants.STATUS_LOCKED)) {
            throw new BusinessException(ErrorConstants.SCORE_SHEET_ALREADY_PREPARED);
        }
        List<SchoolStudent> schoolStudents = findSubjectStudents(
                _assignment.getSchoolTerm().getSchoolTermId(),
                _assignment.getSchoolClass(),
                _assignment.getSchoolSubject());
        List<AssignmentScore> assignmentScores = new ArrayList<>(schoolStudents.size());
        AssignmentScore assignmentScore;
        for (SchoolStudent ss : schoolStudents) {
            assignmentScore = new AssignmentScore();

            assignmentScore.setAssignment(_assignment);
            //assignmentScore.set() // a new object
            assignmentScore.setModifiedBy(assignment.getModifiedBy());
            assignmentScore.setModifiedTime(new Date());
            assignmentScore.setSchoolStudent(ss);
            assignmentScore.setScore((short) 0);
            assignmentScore.setStatus(CommonConstants.STATUS_INACTIVE);

            assignmentScores.add(assignmentScore);
        }
        try {
            //set the status to LOCK for the exam object
            _assignment.setStatus(CommonConstants.STATUS_LOCKED);
            _assignment.setModifiedBy(assignment.getModifiedBy());
            assignmentScoreRepo.save(assignmentScores);
            assignmentRepo.save(_assignment);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void commitAssignmentScores(Assignment assignment,
            List<AssignmentScore> assignmentScores) throws BusinessException {
        Assignment _assignment = assignmentRepo.findOne(assignment.getAssignmentId());
        //ensure that the exam has not been committed before
        if (_assignment.getStatus().equals(CommonConstants.STATUS_ACTIVE)) {
            throw new BusinessException(ErrorConstants.EXAM_ALREADY_COMMITTED);
        }
        //verify authourity to commit
        verifyAuthorityToCommit(assignment.getModifiedBy(),
                _assignment.getSchoolClass(), _assignment.getSchoolSubject());
        //
        for (AssignmentScore es : assignmentScores) {
            es.setStatus(CommonConstants.STATUS_ACTIVE);
        }
        try {
            _assignment.setModifiedBy(assignment.getModifiedBy());
            _assignment.setModifiedTime(new Date());
            _assignment.setStatus(CommonConstants.STATUS_ACTIVE);
            assignmentScoreRepo.save(assignmentScores);
            assignmentRepo.save(_assignment);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ExamBatch createExamBatch(ExamBatch examBatch) throws BusinessException {
        if (CommonConstants.EXAM_TYPE_FINAL_EXAM.equals(
                examBatch.getExamType().getExamTypeCode())
                && !examBatchRepo.findDupSectionTermExamBatch(
                examBatch.getSchoolTerm(), examBatch.getSchoolSection()).isEmpty()) {
            throw new BusinessException(ErrorConstants.TERM_FINAL_EXAM_ALREADY_EXISTS);
        }
        try {
            examBatch.setStatus(CommonConstants.STATUS_INACTIVE);
            examBatch.setModifiedTime(new Date());
            //save to database
            return examBatchRepo.save(examBatch);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public ExamBatch findExamBatch(Integer examBatchId) {
        return examBatchRepo.findOne(examBatchId);
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public List<ExamBatch> findExamBatchs(ExamBatchQueryCriteria criteria) {
        return schoolCriteriaSearchRepo.findExamBatchs(criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void saveExamBatch(ExamBatch examBatch) throws BusinessException {
        List<Exam> exams = examRepo.findExamsByBatch(examBatch.getExamBatchId());
        for (Exam e : exams) {
            if (e.getExamTime().before(examBatch.getStartDate())) {
                throw new BusinessException(ErrorConstants.INVALID_DATE_ADJUSTMENT);
            }
        }
        if (!examBatchRepo.findDupTermExamBatch(examBatch.getSchoolTerm(),
                examBatch.getExamBatchId(), examBatch.getSchoolSection()).isEmpty()) {
            throw new BusinessException(ErrorConstants.DUPLICATE_TERM_EXAM);
        }
        ExamBatch _examBatch = findExamBatch(examBatch.getExamBatchId());
        try {
            _examBatch.setDescription(examBatch.getDescription());
            _examBatch.setEndDate(examBatch.getEndDate());
            _examBatch.setStartDate(examBatch.getStartDate());
            _examBatch.setModifiedBy(examBatch.getModifiedBy());
            _examBatch.setSchoolSection(examBatch.getSchoolSection());
            _examBatch.setModifiedTime(new Date());
            _examBatch.setNotifyParentBegin(examBatch.isNotifyParentBegin());
            _examBatch.setNotifyParentResult(examBatch.isNotifyParentResult());
            //save to database
            examBatchRepo.save(_examBatch);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void saveExamBatchClasses(ExamBatch examBatch, List<SchoolClass> schoolClasses)
            throws BusinessException {
        //exam batch
        ExamBatch _examBatch = findExamBatch(examBatch.getExamBatchId());
        if (!_examBatch.getStatus().equals(CommonConstants.STATUS_INACTIVE)) {
            throw new BusinessException(ErrorConstants.INVALID_OPERATION);
        }
        try {
            //
            List<ExamBatchClass> examBatchClasses = examBatchClassRepo.findExamBatches(
                    _examBatch.getExamBatchId());
            //delete the existing list
            examBatchClassRepo.delete(examBatchClasses);
            //save the whole new list
            List<ExamBatchClass> dataList = new ArrayList<>(schoolClasses.size());
            ExamBatchClass _examBatchClass;
            for (SchoolClass schoolClass : schoolClasses) {
                _examBatchClass = new ExamBatchClass();
                _examBatchClass.setExamBatch(_examBatch);
                _examBatchClass.setStatus(CommonConstants.STATUS_ACTIVE);
                _examBatchClass.setModifiedBy(examBatch.getModifiedBy());
                _examBatchClass.setModifiedTime(new Date());
                _examBatchClass.setSchoolClass(schoolClass);
                //add to the collection
                dataList.add(_examBatchClass);
            }
            //save 
            examBatchClassRepo.save(dataList);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public ExamBatchClass findExamBatchClass(Integer examBatchId, Integer schooClassId) {
        List<ExamBatchClass> examBatchClasses = examBatchClassRepo
                .findExamBatchClass(examBatchId, schooClassId);
        if (examBatchClasses.isEmpty()) {
            return null;
        }
        return examBatchClasses.get(0);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<SchoolClass> findExamBatchClasses(Integer examBatchId) {
        return examBatchClassRepo.findExamBatchClasses(examBatchId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ExamBatch beginExamBatch(ExamBatch examBatch) throws BusinessException {
        ExamBatch _eBatch = examBatchRepo.findOne(examBatch.getExamBatchId());
        List<Exam> examList = new ArrayList<>();
        SchoolPref schoolPref = schoolPrefRepo.findSchoolPrefBySchoolYear(
                _eBatch.getSchoolTerm().getSchoolYearId());
        if (schoolPref == null) {
            throw new BusinessException(ErrorConstants.NO_SCHOOL_PREF_SETTINGS);
        }
        //get all the calsses registered for the exam session
        List<SchoolClass> schoolClasses = findExamBatchClasses(_eBatch.getExamBatchId());
        if (schoolClasses.isEmpty()) {
            throw new BusinessException(ErrorConstants.NO_REGISTERED_CLASS);
        }
        try {
            List<SchoolSubject> schoolSubjects;
            Exam exam;
            for (SchoolClass sc : schoolClasses) {
                schoolSubjects = schoolSubjectRepo.findByGrade(
                        sc.getSchoolId(), sc.getGradeLevel().getGradeLevelId());
                for (SchoolSubject ss : schoolSubjects) {
                    //create an exam entry for each school subject
                    exam = new Exam();
                    exam.setExamBatch(_eBatch);
                    exam.setExamTime(_eBatch.getStartDate());
                    exam.setMaxScore(schoolPref.getSubMaxScore().shortValue());
                    exam.setModifiedBy(examBatch.getModifiedBy());
                    exam.setModifiedTime(new Date());
                    exam.setSchoolClass(sc);
                    exam.setSchoolSubject(ss);
                    exam.setSchoolTerm(examBatch.getSchoolTerm());
                    exam.setStatus(CommonConstants.STATUS_INACTIVE);
                    //add to list
                    examList.add(exam);
                }
            }
            //save to database
            examRepo.save(examList);
            //change the exam batch status
            _eBatch.setStatus(CommonConstants.STATUS_LOCKED);
            _eBatch.setModifiedBy(examBatch.getModifiedBy());
            _eBatch.setModifiedTime(new Date());

            return examBatchRepo.save(_eBatch);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public Exam findExam(Integer examId) {
        return examRepo.findOne(examId);
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public List<Exam> findExams(ExamQueryCriteria criteria) {
        return schoolCriteriaSearchRepo.findExams(criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void saveExam(Exam exam) throws BusinessException {
        //check the parent exam batch and ensure that change in date does not cross 
        //the exam batch dates
        ExamBatch examBatch = findExamBatch(exam.getExamBatch().getExamBatchId());
        if (examBatch.getStartDate().before(exam.getExamTime())) {
            throw new BusinessException(ErrorConstants.INVALID_EXAM_DATE);
        }
        Exam _exam = findExam(exam.getExamId());
        _exam.setMaxScore(exam.getMaxScore());
        _exam.setModifiedBy(exam.getModifiedBy());
        _exam.setExamTime(exam.getExamTime());
        try {
            _exam.setModifiedTime(new Date());
            //save to database
            examRepo.save(_exam);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void prepareExamScoreSheet(Exam exam) throws BusinessException {
        Exam _exam = findExam(exam.getExamId());
        if (_exam == null) {
            throw new BusinessException(ErrorConstants.CANNOT_FIND_EXAM);
        }
        if (_exam.getStatus().equals(CommonConstants.STATUS_LOCKED)) {
            throw new BusinessException(ErrorConstants.SCORE_SHEET_ALREADY_PREPARED);
        }
        List<SchoolStudent> schoolStudents = findSubjectStudents(
                _exam.getSchoolTerm().getSchoolTermId(),
                _exam.getSchoolClass(), _exam.getSchoolSubject());
        List<ExamScore> examScores = new ArrayList<>(schoolStudents.size());
        ExamScore examScore;
        for (SchoolStudent ss : schoolStudents) {
            examScore = new ExamScore();

            examScore.setExam(_exam);
            //examScore.setExamScoreId() // a new object
            examScore.setModifiedBy(exam.getModifiedBy());
            examScore.setModifiedTime(new Date());
            examScore.setSchoolStudent(ss);
            examScore.setScore((short) 0);
            examScore.setStatus(CommonConstants.STATUS_INACTIVE);

            examScores.add(examScore);
        }
        try {
            //set the status to LOCK for the exam object
            _exam.setStatus(CommonConstants.STATUS_LOCKED);
            _exam.setModifiedBy(exam.getModifiedBy());
            examScoreRepo.save(examScores);
            examRepo.save(_exam);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void prepareClassExamScoreSheet(ExamBatch examBatch, Integer schoolClassId)
            throws BusinessException {
        List<Exam> exams = examRepo.findExamsByClass(examBatch.getExamBatchId(), schoolClassId);
        for (Exam exam : exams) {
            if (!exam.getStatus().equals(CommonConstants.STATUS_LOCKED)) { //has not been previous prepared
                exam.setModifiedBy(examBatch.getModifiedBy());
                prepareExamScoreSheet(exam);
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void prepareBatchExamScoreSheet(ExamBatch examBatch) throws BusinessException {
        List<Exam> exams = examRepo.findExamsByBatch(examBatch.getExamBatchId());
        for (Exam exam : exams) {
            if (!exam.getStatus().equals(CommonConstants.STATUS_LOCKED)) { //has not been previous prepared
                exam.setModifiedBy(examBatch.getModifiedBy());
                prepareExamScoreSheet(exam);
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public List<ExamScore> findExamScore(Integer examId) {
        //get the exam score
        return examScoreRepo.findExamScores(examId);
    }

    /**
     * {@inheritDoc }
     */
    @Transactional(readOnly = true)
    @Override
    public ExamScoreTable findClassExamsScores(Integer examBatchId, Integer schoolClassId) {
        //get tehe examBatch
        ExamBatch _examBatch = examBatchRepo.findOne(examBatchId);
        //get the class
        SchoolClass schoolClass = schoolClassRepo.findOne(schoolClassId);
        //get all subjects for that grade
        List<SchoolSubject> schoolSubjects = schoolSubjectRepo.findByGrade(
                schoolClass.getSchoolId(),
                schoolClass.getGradeLevel().getGradeLevelId());
        //set the school subjects for this class
        ExamScoreTable examScoreTable = new ExamScoreTable(schoolSubjects);
        //find all school students in this class
        List<SchoolStudent> schoolStudents = schoolStudentRepo.findByClass(
                _examBatch.getSchoolTerm().getSchoolTermId(),
                schoolClassId);
        //set the rows for the score sheet table
        examScoreTable.addRows(schoolStudents);
        //now find all exam scores 
        List<ExamScore> examScores = examScoreRepo.findClassExamScores(examBatchId, schoolClassId);
        //add to the table
        examScoreTable.addExamScores(examScores);
        //return table
        return examScoreTable;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void commitExamScores(Exam exam, List<ExamScore> examScores) throws BusinessException {
        //we must first verify that the approval of this is the subject teacher 
        //for the course or the staff assigned to the class or an admin
        //get the subject teacher
        Exam _exam = examRepo.findOne(exam.getExamId());
        //ensure that the exam has not been committed before
        if (_exam.getStatus().equals(CommonConstants.STATUS_ACTIVE)) {
            throw new BusinessException(ErrorConstants.EXAM_ALREADY_COMMITTED);
        }
        //verify authourity to commit
        verifyAuthorityToCommit(exam.getModifiedBy(),
                _exam.getSchoolClass(), _exam.getSchoolSubject());
        //
        for (ExamScore es : examScores) {
            es.setStatus(CommonConstants.STATUS_ACTIVE);
        }
        try {
            _exam.setModifiedBy(exam.getModifiedBy());
            _exam.setModifiedTime(new Date());
            _exam.setStatus(CommonConstants.STATUS_ACTIVE);
            examScoreRepo.save(examScores);
            examRepo.save(_exam);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void commitExamScores(ExamBatchClass examBatchClass,
            ExamScoreTable examScoreTable) throws BusinessException {
        List<Exam> exams = examRepo.findExamsByClass(examBatchClass.getExamBatch().getExamBatchId(),
                examBatchClass.getSchoolClass().getSchoolClassId());
        List<ExamScore> examScores;
        try {
            for (Exam exam : exams) {
                //remember only to commit those exams that have not been committed 
                //or only those that are still locked
                if (exam.getStatus().equals(CommonConstants.STATUS_LOCKED)) {
                    examScores = (List<ExamScore>) examScoreTable.getColumn(exam.getSchoolSubject());
                    //commit the exam scores
                    exam.setModifiedBy(examBatchClass.getModifiedBy());
                    commitExamScores(exam, examScores);
                }
            }
            //set the status of the exam batch class object
            ExamBatchClass _examBatchClass = examBatchClassRepo.findOne(examBatchClass.getExamBatchClassId());
            if (_examBatchClass != null) {
                _examBatchClass.setStatus(CommonConstants.STATUS_ACTIVE);
                examBatchClassRepo.save(_examBatchClass);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void saveExamScores(Exam exam, List<ExamScore> examScores) throws BusinessException {
        Exam _exam = examRepo.findOne(exam.getExamId());
        //ensure that the exam has not been committed before
        if (!_exam.getStatus().equals(CommonConstants.STATUS_LOCKED)) {
            throw new BusinessException(ErrorConstants.EXAMSCORE_NOT_AVAILABLE_FOR_SAVING);
        }
        //verify authourity to save
        verifyAuthorityToCommit(exam.getModifiedBy(), _exam.getSchoolClass(), _exam.getSchoolSubject());
        //set modified by field
        for (ExamScore es : examScores) {
            es.setModifiedBy(exam.getModifiedBy());
        }
        try {
            examScoreRepo.save(examScores);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void saveExamScores(ExamBatchClass examBatchClass,
            ExamScoreTable examScoreTable) throws BusinessException {
        List<Exam> exams = examRepo.findExamsByClass(examBatchClass.getExamBatch().getExamBatchId(),
                examBatchClass.getSchoolClass().getSchoolClassId());
        List<ExamScore> examScores;
        try {
            for (Exam exam : exams) {
                //remember only to save those exams that have not been committed 
                //or only those that are still locked
                if (exam.getStatus().equals(CommonConstants.STATUS_LOCKED)) {
                    examScores = (List<ExamScore>) examScoreTable.getColumn(exam.getSchoolSubject());
                    //commit the exam scores
                    exam.setModifiedBy(examBatchClass.getModifiedBy());
                    saveExamScores(exam, examScores);
                }
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, StringUtil.getNestedErrorMessage(e), e);
            throw new BusinessException(StringUtil.getNestedErrorMessage(e));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<TermExamRecord> publishExamScores(ExamBatchClass examBatchClass)
            throws BusinessException {
        ExamBatch _examBatch = findExamBatch(examBatchClass.getExamBatch().getExamBatchId());
        if (!CommonConstants.EXAM_TYPE_FINAL_EXAM.equals(_examBatch.getExamType().getExamTypeCode())) {
            throw new BusinessException(ErrorConstants.INVALID_EXAM_TYPE);
        }
        List<TermExamRecord> termExamRecords = new ArrayList<>();
        TermExamRecord termExamRecord;
        TermExamRecordDetails terd;
        //get the school pref
        SchoolPref schoolPref = examBatchClass.getSchoolPref();
        if (schoolPref == null) {
            schoolPref = schoolPrefRepo.findSchoolPrefBySchoolYear(
                    _examBatch.getSchoolTerm().getSchoolYearId());
        }
        //declare assignment average score
        float avgCAScore = 0.0f, finalScore = 0.0f, termScore = 0.0f;
        int termCreditWeight = 0;
        //the boolean value for continuous assessment
        boolean isQuizCA = schoolPref.isQuizContAssessment();
        GradeBand gradeBand; //gradeBand
        List<SchoolStudent> schoolStudents = schoolStudentRepo.findByClass(
                _examBatch.getSchoolTerm().getSchoolTermId(),
                examBatchClass.getSchoolClass().getSchoolClassId());
        for (SchoolStudent schoolStudent : schoolStudents) {//iterate through the students
            termExamRecord = createTermExamRecord(_examBatch, schoolStudent);
            //set modified by
            termExamRecord.setModifiedBy(examBatchClass.getModifiedBy());
            //save to database
            termExamRecord = termExamRecordRepo.save(termExamRecord);

            List<ExamScore> examScores = examScoreRepo.findStudentExamScores(schoolStudent.getSchoolStudentId());
            for (ExamScore examScore : examScores) {
                //check if school supports continuous assessment
                if (isQuizCA) {
                    avgCAScore = calculateWeightedCAScore(schoolStudent, examScore.getExam(), schoolPref);
                }
                finalScore = avgCAScore + ((examScore.getScore() / examScore.getExam().getMaxScore())
                        / (schoolPref.getSubMaxScore() - schoolPref.getFinalQuizWeight()));
                //add to term score
                termScore += finalScore;
                termCreditWeight += examScore.getExam().getSchoolSubject().getCreditWeight();
                //get gradeBand
                gradeBand = gradeBandRepo.findGradeBand(finalScore);

                terd = new TermExamRecordDetails();

                terd.setAvgAssignmentScore(avgCAScore);
                terd.setComment(gradeBand.getDescription());
                terd.setEmployee(employeeRepo.findByUsername(examBatchClass.getModifiedBy()));
                terd.setExamScore(examScore);
                terd.setFinalScore(finalScore);
                terd.setGradeBand(gradeBand);
                terd.setModifiedBy(examBatchClass.getModifiedBy());
                terd.setModifiedTime(new Date());
                terd.setSchoolSubject(examScore.getExam().getSchoolSubject());
                terd.setStatus(CommonConstants.STATUS_ACTIVE);
                terd.setTermExamRecord(termExamRecord);
                terd.setTotalCredit((short) (terd.getSchoolSubject().getCreditWeight() * gradeBand.getCreditPower()));

                //save terd
                termExamRecordDetailsRepo.save(terd);
            }
            termExamRecord.setTotalTermScore(termScore);
            termExamRecord.setTotalcreditWeight(termCreditWeight);
            termExamRecord.setCgpa(termScore / termCreditWeight);
            //update termExamRecord and add to the list of term record
            termExamRecords.add(termExamRecordRepo.save(termExamRecord));
        }
        return termExamRecords;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void publishExamScores(ExamBatch examBatch) throws BusinessException {
        ExamBatch _examBatch = findExamBatch(examBatch.getExamBatchId());
        if (!CommonConstants.STATUS_LOCKED.equals(_examBatch.getStatus())) {
            throw new BusinessException(ErrorConstants.INVALID_EXAM_BATCH_STATE);
        }
        List<ExamBatchClass> examBatchClasses = examBatchClassRepo.findExamBatches(examBatch.getExamBatchId());
        //iterate to confirm that all exambatchClasses are either LOCKED OR ACTIVE
        for (ExamBatchClass ebc : examBatchClasses) {
            if (CommonConstants.STATUS_INACTIVE.equals(ebc.getStatus())) {
                throw new BusinessException(ErrorConstants.INVALID_EXAM_BATCH_CLASS_STATE,
                        ebc.getSchoolClass().getCode());
            }
        }
        //iterate again to publish classes for LOCKED batch classes only
        for (ExamBatchClass ebc : examBatchClasses) {
            if (CommonConstants.STATUS_LOCKED.equals(ebc.getStatus())) {
                ebc.setModifiedBy(examBatch.getModifiedBy());
                //call the publishExamScores(ebc) for the exam batch classes
                publishExamScores(ebc);
            }
        }
        //update exam batch now.
        _examBatch.setStatus(CommonConstants.STATUS_ACTIVE);
        _examBatch.setModifiedTime(new Date());
        _examBatch.setModifiedBy(examBatch.getModifiedBy());

        examBatchRepo.save(_examBatch);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<TermExamRecord> findTermExamRecords(TermExamRecordQueryCriteria criteria) {
        return schoolCriteriaSearchRepo.findTermExamRecords(criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public TermExamRecord findTermExamRecord(Integer termExamRecordId) {
        TermExamRecord termExamRecord = termExamRecordRepo.findOne(termExamRecordId);
        if (termExamRecord == null) {
            return null;
        }
        //add a list of all the composing exam record details
        termExamRecord.setTermExamRecordDetailsList(
                termExamRecordDetailsRepo.findTermExamRecordDetails(termExamRecordId));
        return termExamRecord;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<TermExamRecordDetails> findTermExamRecordDetails(
            TermExamRecordDetailsQueryCriteria criteria) {
        return schoolCriteriaSearchRepo.findTermExamRecordDetails(criteria);
    }

    /**
     * verifies the user identified by username if the has an authority to
     * perform a commit on issues for the subject {@link SchoolSubject} and of
     * class {@link SchoolClass}
     *
     * A user is authorized to commit on a class and subject if the user is a
     * school administrator or the user is a {@link SchoolClassTeacher} with
     * allocationType of 'FORM_MASTER' or if the user has a record for which {@link SubjectTeacher
     * } is not null
     *
     * @param username - username of entity
     * @param schoolClass - class
     * @param schoolSubject - subject
     * @throws BusinessException - throws error is authority check fails
     */
    private void verifyAuthorityToCommit(String username, SchoolClass schoolClass,
            SchoolSubject schoolSubject) throws BusinessException {
        UserLogin ul = securityService.findUserLogin(username);
        if (ul == null) {
            throw new BusinessException(ErrorConstants.INVALID_USERNAME);
        }
        //check if school admin
        if (ul.isSchoolAdmin()) {
            return;
        }
        // Employee employee = employeeRepo.findByUserLogin(ul.getUserLoginId());
        //get school subject teacher

    }

    /**
     * find all {@link SchoolStudent} that are registered to take a particular
     * subject. It is resolved as either all students in a class for a subject
     * for which the {@link SchoolClass#sameSubjectFlag} is true or all students
     * registered for a particular {@link SchoolSubject} students
     *
     * @param schoolTermId - the school term in context
     * @param schoolClass - the school class
     * @param schoolSubject - the school subject offered
     * @return List<SchoolStudent> - the list of student in the schoolClass that
     * offer the schoolSubject
     */
    private List<SchoolStudent> findSubjectStudents(Integer schoolTermId,
            SchoolClass schoolClass, SchoolSubject schoolSubject) {
        //we need to check if the students are doing elective or a compulsory course
        List<SchoolStudent> schoolStudents;
        if (schoolClass.isSameSubjectFlag()) {
            schoolStudents = schoolStudentRepo.findByClass(schoolTermId,
                    schoolClass.getSchoolClassId());
        } else {
            schoolStudents = studentSubjectRepo.findStudentBySubject(
                    schoolSubject.getSchoolSubjectId());
        }

        return schoolStudents;
    }

    /**
     *
     *
     * @param _examBatch
     * @param schoolStudent
     * @return
     */
    private TermExamRecord createTermExamRecord(ExamBatch _examBatch, SchoolStudent schoolStudent) {
        TermExamRecord termExamRecord = new TermExamRecord();

        termExamRecord.setCgpa(0);
        termExamRecord.setClassPosition((short) 1);
        termExamRecord.setComments(null);
        termExamRecord.setExamBatch(_examBatch);
        termExamRecord.setModifiedBy(null);
        termExamRecord.setModifiedTime(new Date());
        termExamRecord.setPerfDescription(null);
        termExamRecord.setSchoolStudent(schoolStudent);
        termExamRecord.setStatus(CommonConstants.STATUS_ACTIVE);
        termExamRecord.setTermExamRecordId(null);
        termExamRecord.setTotalTermScore(0);
        termExamRecord.setTotalcreditWeight(0);

        return termExamRecord;
    }

    /**
     *
     *
     * @param schoolStudent
     * @param exam
     * @param schoolPref
     * @return
     */
    private float calculateWeightedCAScore(SchoolStudent schoolStudent, Exam exam, SchoolPref schoolPref) {
        //get the weight average score for assignments of a student 
        float aScore = assignmentScoreRepo.findStudentAssignmentScores(
                schoolStudent.getSchoolStudentId(), exam.getSchoolSubject().getSchoolSubjectId(),
                exam.getSchoolTerm().getSchoolTermId());
        return aScore * schoolPref.getFinalQuizWeight();
    }
}
