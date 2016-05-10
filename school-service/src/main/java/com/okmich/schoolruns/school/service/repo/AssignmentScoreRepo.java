/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.AssignmentScore;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface AssignmentScoreRepo extends JpaRepository<AssignmentScore, Integer> {

    /**
     *
     * @param assignmentId
     * @return
     */
    @Query(name = "findByAssignment", value = "SELECT a from AssignmentScore a "
    + "WHERE a.assignment.assignmentId = ?1 ORDER BY a.schoolStudent.student.surname")
    List<AssignmentScore> findByAssignment(Integer assignmentId);

    /**
     * returns a the average weighted score of all {@link AssignmentScore} that
     * was gotten by a student in a subject within an academic term
     *
     * @param schoolStudentId
     * @param schoolSubjectId
     * @param schoolTermId
     * @return List<AssignmentScore>
     */
    @Query(name = "findStudentAssignmentScores", value = "SELECT AVG(a.score/a.assignment.maxScore) from AssignmentScore a "
    + "WHERE a.assignment.assignmentId = ?1 AND a.schoolStudent.schoolStudentId = ?2 "
    + "AND a.assignment.schoolTerm.schoolTermId = ?3")
    float findStudentAssignmentScores(Integer schoolStudentId, Integer schoolSubjectId, Integer schoolTermId);
}
