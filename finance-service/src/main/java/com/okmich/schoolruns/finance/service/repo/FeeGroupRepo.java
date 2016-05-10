/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.schoolruns.common.entity.FeeGroup;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael Enudi
 * @since Jul 23, 2013
 * @company Okmich Ltd.
 */
@Repository
public interface FeeGroupRepo extends JpaRepository<FeeGroup, Integer> {

    /**
     *
     * @param school
     * @param gradeLevelId
     * @param feeTypeCode
     * @return
     */
    @Query(name = "findBySchoolGradeType", value = "SELECT f FROM FeeGroup f where f.schoolId = ?1 AND "
    + "f.gradeLevel.gradeLevelId = ?2 AND f.feeType.feeTypeCode = ?3")
    List<FeeGroup> findBySchoolGradeType(Integer schoolId, Integer gradeLevelId, Integer feeTypeCode);

    /**
     *
     * @param school
     * @param feeTypeCode
     * @return
     */
    @Query(name = "findByTypeApplyMode", value = "SELECT f FROM FeeGroup f where f.schoolId = ?1 AND "
    + "f.feeType.feeTypeCode = ?2 AND f.applyAll = true")
    List<FeeGroup> findByTypeApplyMode(Integer schoolId, Integer feeTypeCode);

    /**
     *
     * @param school
     * @param feeTypeCode
     * @return
     */
    @Query(name = "findBySchoolFeeType", value = "SELECT f FROM FeeGroup f where f.schoolId = ?1 AND "
    + "f.feeType.feeTypeCode = ?2")
    List<FeeGroup> findBySchoolFeeType(Integer schoolId, Integer feeTypeCode);
}
