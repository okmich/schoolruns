/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.ExamBatchClass;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface ExamBatchClassRepo extends JpaRepository<ExamBatchClass, Integer> {

    @Query(name = "findExamBatchClasses",
    value = "SELECT e.schoolClass FROM ExamBatchClass e WHERE e.examBatch.examBatchId = ?1")
    List<SchoolClass> findExamBatchClasses(Integer examBatchId);

    @Query(name = "findExamBatches",
    value = "SELECT e FROM ExamBatchClass e WHERE e.examBatch.examBatchId = ?1")
    List<ExamBatchClass> findExamBatches(Integer examBatchId);

    @Query(name = "findExamBatchClass",
    value = "SELECT e FROM ExamBatchClass e WHERE e.examBatch.examBatchId = ?1 AND e.schoolClass.schoolClassId = ?2")
    List<ExamBatchClass> findExamBatchClass(Integer examBatchId, Integer schoolClassId);

    @Query(name = "findSchoolStudentForExamBatch",
    value = "SELECT s FROM SchoolStudent s WHERE s.schoolClass IN "
    + "(SELECT e.schoolClass FROM ExamBatchClass e WHERE e.examBatch.examBatchId = ?1)")
    List<SchoolStudent> findSchoolStudentForExamBatch(Integer examBatchId);
}
