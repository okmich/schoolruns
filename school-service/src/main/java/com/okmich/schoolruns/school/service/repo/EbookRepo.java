/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.Ebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface EbookRepo extends JpaRepository<Ebook, Integer> {
}
