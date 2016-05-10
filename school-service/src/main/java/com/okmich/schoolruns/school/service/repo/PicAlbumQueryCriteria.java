/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.WCDate;
import com.okmich.common.entity.criteria.WCString;
import java.util.Date;

/**
 *
 *
 * @author Michael
 */
public class PicAlbumQueryCriteria extends BaseEntityQueryCriteria {

    /**
     * title
     */
    public static final String title = "title";
    /**
     * title
     */
    public static final String description = "description";
    /**
     * createDate
     */
    public static final String createDate = "createDate";
    /**
     * schoolId
     */
    public static final String schoolId = "schoolId";
    /**
     * schoolYearId
     */
    public static final String schoolYearId = "schoolYearId";

    /**
     * default constructor
     */
    public PicAlbumQueryCriteria() {
    }

    /**
     *
     * @return
     */
    @Override
    public String getEntityName() {
        return "PicAlbum";
    }

    /**
     * @param title the title to set
     * @param wclause the WCString to sets
     */
    public void setTitle(String title, WCString wclause) {
        setParameter(PicAlbumQueryCriteria.title, wclause, title);
    }

    /**
     * @param description the description to set
     * @param wclause the WCString to sets
     */
    public void setDescription(String description, WCString wclause) {
        setParameter(PicAlbumQueryCriteria.description, wclause, description);
    }

    /**
     *
     * @param val1
     * @param wclause
     * @param val2
     */
    @Override
    public void setCreateDate(Date val1, WCDate wclause, Date val2) {
        setParameter(PicAlbumQueryCriteria.createDate, wclause, val1, val2);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(PicAlbumQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param schoolYearId the schoolYearId to set
     */
    public void setSchoolYearId(Integer schoolYearId) {
        setParameter(PicAlbumQueryCriteria.schoolYearId, schoolYearId);
    }
}