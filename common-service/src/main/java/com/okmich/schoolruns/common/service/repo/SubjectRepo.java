/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.service.repo;

import com.okmich.schoolruns.common.entity.Subject;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface SubjectRepo extends JpaRepository<Subject, Integer> {

    @Query(name = "getAvailableSubjectsList",
    value = "SELECT s FROM Subject s WHERE s.subjectId in ?1")
    List<Subject> getAvailableSubjectsList(List<Integer> selectedSubjectsIdList);

    @Query(name = "findSubjectByDesc",
    value = "SELECT s FROM Subject s WHERE s.description = ?1")
    List<Subject> findSubjectByDesc(String searchDescription);
}
