/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.schoolruns.common.entity.Store;
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
public interface StoreRepo extends JpaRepository<Store, Integer> {

    @Query(name = "findStoreByName",
    value = "SELECT s FROM Store s WHERE s.school.schoolId = ?1 AND s.name = ?2")
    List<Store> findStoreByName(Integer schoolId, String name);

    @Query(name = "findStoresBySchool",
    value = "SELECT s FROM Store s WHERE s.school.schoolId = ?1")
    List<Store> findStoresBySchool(Integer schoolId);
}
