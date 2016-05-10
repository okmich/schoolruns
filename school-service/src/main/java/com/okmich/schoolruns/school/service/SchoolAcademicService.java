/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service;

import com.okmich.common.exception.BusinessException;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Assignment;
import com.okmich.schoolruns.common.entity.AssignmentScore;
import com.okmich.schoolruns.common.entity.Exam;
import com.okmich.schoolruns.common.entity.ExamBatch;
import com.okmich.schoolruns.common.entity.ExamBatchClass;
import com.okmich.schoolruns.common.entity.ExamScore;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.common.entity.TermExamRecord;
import com.okmich.schoolruns.common.entity.TermExamRecordDetails;
import com.okmich.schoolruns.school.service.data.ExamScoreTable;
import com.okmich.schoolruns.school.service.repo.AssignmentQueryCriteria;
import com.okmich.schoolruns.school.service.repo.ExamBatchQueryCriteria;
import com.okmich.schoolruns.school.service.repo.ExamQueryCriteria;
import com.okmich.schoolruns.school.service.repo.TermExamRecordDetailsQueryCriteria;
import com.okmich.schoolruns.school.service.repo.TermExamRecordQueryCriteria;
import java.util.List;

/**
 *
 * @author Michael
 */
public interface SchoolAcademicService {

    /**
     * saves a new or existing assignment object to the system. If the
     * Assignment instance is existing and does not have status
     * {@link CommonConstants#STATUS_INACTIVE}, a {@link BusinessException} is
     * thrown
     *
     * @param assignment
     * @return Assignment
     * @throws BusinessException - if error occurs during database operation or
     * if the object is not new and the status is not
     * {@link CommonConstants#STATUS_INACTIVE} though it is existing
     */
    Assignment saveAssignment(Assignment assignment) throws BusinessException;

    /**
     * returns an Assignment instance for with the primary key is passed in as
     * parameter
     *
     * @param assignmentId
     * @return Assignment object with id as parameter
     */
    Assignment findAssignment(Integer assignmentId);

    /**
     *
     *
     * @param criteria
     * @return
     */
    List<Assignment> findAssignments(AssignmentQueryCriteria criteria);

    /**
     * deletes a previous entered assignment from the database. This is only
     * possible if the status of the assignment is
     * {@link CommonConstants#STATUS_INACTIVE} else a {@link BusinessException}
     * will be thrown
     *
     * @param assignment - assignment to be deleted from the system
     * @throws BusinessException - if the status is not
     * {@link CommonConstants#STATUS_INACTIVE}
     */
    void deleteAssignment(Assignment assignment) throws BusinessException;

    /**
     *
     *
     * @param assignment
     * @throws BusinessException
     */
    void prepareAssignmentScoreSheet(Assignment assignment) throws BusinessException;

    /**
     *
     *
     * @param assignmentId
     * @return
     */
    List<AssignmentScore> findAssignmentScores(Integer assignmentId);

    /**
     *
     *
     * @param assignment
     * @param assignmentScores
     * @throws BusinessException
     */
    void saveAssignmentScores(Assignment assignment,
            List<AssignmentScore> assignmentScores) throws BusinessException;

    /**
     *
     *
     * @param assignment
     * @param assignmentScores
     * @throws BusinessException
     */
    void commitAssignmentScores(Assignment assignment,
            List<AssignmentScore> assignmentScores) throws BusinessException;

    /**
     *
     *
     * @param examBatch
     * @return
     * @throws BusinessException
     */
    ExamBatch createExamBatch(ExamBatch examBatch) throws BusinessException;

    /**
     *
     *
     * @param examBatchId
     * @return
     */
    ExamBatch findExamBatch(Integer examBatchId);

    /**
     *
     *
     * @param criteria
     * @return List<ExamBatch>
     */
    List<ExamBatch> findExamBatchs(ExamBatchQueryCriteria criteria);

    /**
     *
     *
     * @param examBatch
     * @throws BusinessException
     */
    void saveExamBatch(ExamBatch examBatch) throws BusinessException;

    /**
     *
     *
     * @param examBatch
     * @param schoolClasses
     * @throws BusinessException
     */
    void saveExamBatchClasses(ExamBatch examBatch, List<SchoolClass> schoolClasses) throws BusinessException;

    /**
     * rolls out the individuals exams that make up the exam session. The status
     * of the exam session is set to LOCKED and the individual exam records are
     * created and save
     *
     * @param examBatch - the exam session to roll out
     * @param examBatch - the updated version of the examBatch
     * @throws BusinessException - if error occurs or if there are no registered
     * classes participating in the examination
     */
    ExamBatch beginExamBatch(ExamBatch examBatch) throws BusinessException;

    /**
     *
     *
     * @param examBatchId
     * @return List<SchoolClass>
     */
    List<SchoolClass> findExamBatchClasses(Integer examBatchId);

    /**
     * find the ExamBatch for the class. This object is the parent object for
     * all the exams that will be held in that class for the session
     *
     * @param examBatchId
     * @param schooClassId
     * @return ExamBatchClass
     */
    ExamBatchClass findExamBatchClass(Integer examBatchId, Integer schooClassId);

    /**
     *
     *
     * @param examId
     * @return
     */
    Exam findExam(Integer examId);

    /**
     *
     *
     * @param exam
     * @throws BusinessException
     */
    void saveExam(Exam exam) throws BusinessException;

    /**
     *
     *
     * @param criteria
     * @return
     */
    List<Exam> findExams(ExamQueryCriteria criteria);

    /**
     * prepares the score sheet for a particular exam
     *
     * @param exam
     * @throws BusinessException
     */
    void prepareExamScoreSheet(Exam exam) throws BusinessException;

    /**
     *
     *
     * @param examBatch
     * @param schoolClassId
     * @throws BusinessException
     */
    void prepareClassExamScoreSheet(ExamBatch examBatch, Integer schoolClassId) throws BusinessException;

    /**
     *
     *
     * @param examBatch
     * @throws BusinessException
     */
    void prepareBatchExamScoreSheet(ExamBatch examBatch) throws BusinessException;

    /**
     *
     *
     * @param exam
     * @param examScores
     * @throws BusinessException
     */
    void commitExamScores(Exam exam, List<ExamScore> examScores) throws BusinessException;

    /**
     *
     *
     * @param examBatchClass
     * @param examScoreTable
     * @throws BusinessException
     */
    void commitExamScores(ExamBatchClass examBatchClass,
            ExamScoreTable examScoreTable) throws BusinessException;

    /**
     *
     *
     * @param examId
     * @return List<ExamScore>
     */
    List<ExamScore> findExamScore(Integer examId);

    /**
     *
     *
     * @param examBatchId
     * @param schoolClassId
     * @return ExamScoreTable
     */
    ExamScoreTable findClassExamsScores(Integer examBatchId, Integer schoolClassId);

    /**
     * save the exam scores for an exam
     *
     * @param exam
     * @param examScores
     * @throws BusinessException
     */
    void saveExamScores(Exam exam, List<ExamScore> examScores) throws BusinessException;

    /**
     * save the exam scores for all the exams taken by a particular class
     *
     * @param examBatchClass
     * @param examScoreTable
     * @throws BusinessException
     */
    void saveExamScores(ExamBatchClass examBatchClass,
            ExamScoreTable examScoreTable) throws BusinessException;

    /**
     * processes all the scores and publishes the outcome of all exams of a
     * particular class in an ExamBatch. Note that the definition of the
     * {@link SchoolClass} and {@link ExamBatch} are composed in the
     * {@link ExamBatchClass} <br /><br /> Once the processes is complete,
     * {@link TermExamRecord} object is created for every student that partook
     * in any exam for that ExamBatch. The {@link TermExamRecord} is a summary
     * object. The details are stored in a list of {@link TermExamRecordDetails}
     * objects. <br /><br /> Note that after this method call, the
     * ExamBatchClass in context will have the STATUS_ACTIVE state and so will
     * all the exams for this class <br /><br /> <strong>Note:</strong>We must
     * however note, that the purpose of this method is mainly geared towards
     * the final term Exam. So publishing a mock exam or Admission exams may not
     * give a predictable result for continous assessment
     *
     * @param examBatchClass - defines the {@link ExamBatch} and the
     * {@link SchoolClass}
     * @return List<TermExamRecord> - a list of term record summary for the exam
     * @throws BusinessException - if error occurs
     */
    List<TermExamRecord> publishExamScores(ExamBatchClass examBatchClass)
            throws BusinessException;

    /**
     * processes all scores and continuous assessment result for every student
     * in every class that participated in the ExamBatch.<br /> <br /> This
     * method is mostly necessary for processing end of term results of the
     * school
     *
     * @param examBatch - the examBatch whose academic record will be processed
     * @throws BusinessException - if error occurs
     */
    void publishExamScores(ExamBatch examBatch) throws BusinessException;

    /**
     * returns a list of {@link TermExamRecord} that match the query criteria
     * defined by {@link TermExamRecordQueryCriteria}
     *
     * @param criteria - TermExamRecordQueryCriteria
     * @return a list of {@link TermExamRecord} that match the query criteria
     * defined by {@link TermExamRecordQueryCriteria}
     */
    List<TermExamRecord> findTermExamRecords(TermExamRecordQueryCriteria criteria);

    /**
     * find and return the {@link TermExamRecord) whose primary identifier is
     * {@code termExamRecordId} or null is none is found. <br />Also the entire
     * details -  are also returned along with the {@link TermExamRecord) object.
     *
     * @param termexamRecordId
     * @return TermExamRecord - would contain a List of {@link TermExamRecordDetails}
     */
    TermExamRecord findTermExamRecord(Integer termExamRecordId);

    /**
     * returns a list of {@link TermExamRecordDetails} that match the query
     * criteria defined by {@link TermExamRecordDetailsQueryCriteria}
     *
     * @param criteria - TermExamRecordDetailsQueryCriteria
     * @return a list of {@link TermExamRecordDetails} that match the query
     * criteria defined by {@link TermExamRecordDetailsQueryCriteria}
     */
    List<TermExamRecordDetails> findTermExamRecordDetails(TermExamRecordDetailsQueryCriteria criteria);
}