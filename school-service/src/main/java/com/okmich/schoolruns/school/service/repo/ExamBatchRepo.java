/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.ExamBatch;
import com.okmich.schoolruns.common.entity.SchoolSection;
import com.okmich.schoolruns.common.entity.SchoolTerm;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface ExamBatchRepo extends JpaRepository<ExamBatch, Integer> {

    /**
     *
     *
     * @param schoolTermId
     * @param examBatchId
     * @param schoolSectionId
     * @return
     */
    @Query(name = "findDupTermExamBatch", value = "SELECT e FROM ExamBatch e "
    + "WHERE e.examType.examTypeCode = 'TFE' AND e.schoolTerm = ?1 "
    + "AND e.examBatchId <> ?2 AND "
    + "(e.schoolSection = ?2 OR e.schoolSection IS NULL)")
    List<ExamBatch> findDupTermExamBatch(SchoolTerm schoolTerm,
            Integer examBatchId, SchoolSection schoolSection);

    /**
     *
     *
     * @param schoolTermId
     * @param schoolSectionId
     * @return List<ExamBatch>
     */
    @Query(name = "findDupSectionTermExamBatch", value = "SELECT e FROM ExamBatch e WHERE "
    + "e.examType.examTypeCode = 'TFE' AND e.schoolTerm = ?1 AND "
    + "(e.schoolSection = ?2 OR e.schoolSection IS NULL)")
    List<ExamBatch> findDupSectionTermExamBatch(SchoolTerm schoolTerm,
            SchoolSection schoolSection);
}
