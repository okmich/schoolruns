/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.student.service.repo;

import com.okmich.schoolruns.common.entity.StudentAward;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface StudentAwardRepo extends JpaRepository<StudentAward, Integer> {

    @Query(name = "findStudentAward",
    value = "SELECT s FROM StudentAward s WHERE s.student.studentId = ?1")
    List<StudentAward> findStudentAward(Integer studentId);
}