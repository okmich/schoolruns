/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.schoolruns.common.entity.PicAlbum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Michael
 */
@Repository
public interface PicAlbumRepo extends JpaRepository<PicAlbum, Integer> {

    @Query(name = "findByTitle",
    value = "SELECT p FROM PicAlbum p WHERE p.schoolId = ?1 AND LOWER(p.title) = ?2")
    List<PicAlbum> findByTitle(Integer schoolId, String title);
}
