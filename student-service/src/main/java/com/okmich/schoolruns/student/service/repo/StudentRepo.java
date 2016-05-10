/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.student.service.repo;

import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Michael
 */
public interface StudentRepo extends JpaRepository<Student, Integer> {

    /**
     *
     * @param studentId
     * @return List<Parent>
     */
    @Query(name = "findStudentsParent", value = "SELECT s.parent FROM Student s "
    + "WHERE s.studentId = ?1")
    List<Parent> findStudentParent(Integer studentId);

    /**
     *
     *
     * @param parentId
     * @return
     */
    @Query(name = "findParentWards", value = "SELECT s FROM Student s WHERE "
    + "s.parent.phoneNumber = ?1")
    List<Student> findParentWards(String phoneNumber);
}
