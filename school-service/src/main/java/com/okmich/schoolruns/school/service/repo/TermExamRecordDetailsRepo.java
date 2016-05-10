/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.TermExamRecordDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface TermExamRecordDetailsRepo extends JpaRepository<TermExamRecordDetails, Integer> {

    @Query(name = "findTermExamRecordDetails", value = "SELECT t FROM TermExamRecordDetails t "
    + "WHERE t.termExamRecord.termExamRecordId = ?1")
    List<TermExamRecordDetails> findTermExamRecordDetails(Integer termExamRecordId);
}
