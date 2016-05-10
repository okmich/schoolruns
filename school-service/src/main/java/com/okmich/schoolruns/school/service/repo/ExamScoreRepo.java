/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.ExamScore;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface ExamScoreRepo extends JpaRepository<ExamScore, Integer> {

    /**
     * returns a list of {@link ExamScore} for an exam
     *
     * @param examId - id of exam
     * @return list of {@link ExamScore} returned
     */
    @Query(name = "findExamScores", value = "SELECT e FROM ExamScore e WHERE e.exam.examId = ?1")
    List<ExamScore> findExamScores(Integer examId);

    /**
     * returns a list of {@link ExamScore} for all exams held for a
     * {@link SchoolClass} in a particular examBatch
     *
     * @param examBatchId - the exam batch
     * @param schoolClassId - the school class
     * @return List<ExamScore> - list of all scores for all exams in a
     * examination batch
     */
    @Query(name = "findClassExamScores", value = "SELECT e FROM ExamScore e WHERE e.exam.examBatch.examBatchId = ?1 AND "
    + "e.exam.schoolClass.schoolClassId = ?2")
    List<ExamScore> findClassExamScores(Integer examBatchId, Integer schoolClassId);

    /**
     *
     *
     * @param schoolStudentId
     * @return
     */
    @Query(name = "findStudentExamScores", value = "SELECT e FROM ExamScore e WHERE e.schoolStudent.schoolStudentId = ?1")
    List<ExamScore> findStudentExamScores(Integer schoolStudentId);
}
