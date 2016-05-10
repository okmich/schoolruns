/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.Exam;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface ExamRepo extends JpaRepository<Exam, Integer> {

    /**
     * returns all the exams that comprise an exam Batch
     *
     * @param examBatchId
     * @return List<Exam>
     */
    @Query(name = "findExamsByBatch", value = "SELECT e FROM Exam e WHERE e.examBatch.examBatchId = ?1")
    List<Exam> findExamsByBatch(Integer examBatchId);

    /**
     * returns all the exams that was done in a particular class for an exam
     * Batch
     *
     * @param examBatchId
     * @param schoolClassId
     * @return List<Exam>
     */
    @Query(name = "findExamsByClass", value = "SELECT e FROM Exam e WHERE e.examBatch.examBatchId = ?1 AND "
    + "e.schoolClass.schoolClassId = ?2")
    List<Exam> findExamsByClass(Integer examBatchId, Integer schoolClassId);
}
