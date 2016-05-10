/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.Module;
import com.okmich.schoolruns.common.entity.SchoolModule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Michael
 */
public interface SchoolModuleRepo extends JpaRepository<SchoolModule, Integer> {

    /**
     *
     * @param schoolId
     * @return List<SchoolModule>
     */
    @Query(name = "findSchoolModuleBySchool", value = "SELECT s FROM SchoolModule s WHERE s.school.schoolId = ?1")
    List<SchoolModule> findSchoolModuleBySchool(Integer schoolId);

    /**
     *
     * @param schoolId
     * @return List<Module>
     */
    @Query(name = "findModuleBySchool", value = "SELECT s.module FROM SchoolModule s WHERE s.school.schoolId = ?1")
    List<Module> findModuleBySchool(Integer schoolId);

    /**
     *
     * @param schoolId
     * @param moduleId
     * @return SchoolModule
     */
    @Query(name = "findBySchoolModule", value = "SELECT s FROM SchoolModule s WHERE s.school.schoolId = ?1 AND s.module.moduleId = ?2")
    SchoolModule findBySchoolModule(Integer schoolId, Integer moduleId);
}
