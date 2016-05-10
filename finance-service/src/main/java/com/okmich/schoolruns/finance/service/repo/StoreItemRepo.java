/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.finance.service.repo;

import com.okmich.schoolruns.common.entity.StoreItem;
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
public interface StoreItemRepo extends JpaRepository<StoreItem, Integer> {

    /**
     *
     * @param storeId
     * @param name
     * @return List<StoreItem>
     */
    @Query(name = "findStoreItemByName",
    value = "SELECT s FROM StoreItem s WHERE s.store.storeId = ?1 "
    + "AND s.name = ?2 AND s.status = 'A'")
    List<StoreItem> findStoreItemByName(Integer storeId, String name);
}
