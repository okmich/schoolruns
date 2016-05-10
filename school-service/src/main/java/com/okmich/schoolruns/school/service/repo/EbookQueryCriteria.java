/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCBase;
import com.okmich.common.entity.criteria.WCDate;
import com.okmich.common.entity.criteria.WCString;
import java.util.Date;

/**
 *
 *
 * @author Michael
 */
public class EbookQueryCriteria extends BaseEntityQueryCriteria {

    /**
     * title
     */
    public static final String title = "title";
    /**
     * title
     */
    public static final String description = "description";
    /**
     * year
     */
    public static final String year = "year";
    /**
     * author
     */
    public static final String author = "author";
    /**
     * type
     */
    public static final String type = "type";
    /**
     * fileType
     */
    public static final String fileType = "fileType";
    /**
     * creator
     */
    public static final String creator = "creator";
    /**
     * createDate
     */
    public static final String createDate = "createDate";
    /**
     * publicized
     */
    public static final String publicized = "publicized";
    /**
     * schoolId
     */
    public static final String schoolId = "schoolId";
    /**
     * subjectId
     */
    @RelatedEntity(entityAlias = "a", referencedEntity = "subject")
    public static final String subjectId = "subjectId";
    /**
     * sectionId
     */
    @RelatedEntity(entityAlias = "b", referencedEntity = "section")
    public static final String sectionId = "sectionId";

    /**
     * default constructor
     */
    public EbookQueryCriteria() {
    }

    /**
     *
     * @return
     */
    @Override
    public String getEntityName() {
        return "Ebook";
    }

    /**
     * @param title the title to set
     * @param wclause the WCString to sets
     */
    public void setTitle(String title, WCString wclause) {
        setParameter(EbookQueryCriteria.title, wclause, title);
    }

    /**
     * @param description the description to set
     * @param wclause the WCString to sets
     */
    public void setDescription(String description, WCString wclause) {
        setParameter(EbookQueryCriteria.description, wclause, description);
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        setParameter(EbookQueryCriteria.year, year);
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author, WCString wclause) {
        setParameter(EbookQueryCriteria.author, wclause, author);
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        setParameter(EbookQueryCriteria.type, type);
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        setParameter(EbookQueryCriteria.fileType, fileType);
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(String creator, WCString wclause) {
        setParameter(EbookQueryCriteria.creator, wclause, creator);
    }

    /**
     *
     * @param val1
     * @param wclause
     * @param val2
     */
    @Override
    public void setCreateDate(Date val1, WCDate wclause, Date val2) {
        setParameter(EbookQueryCriteria.createDate, wclause, val1, val2);
    }

    /**
     *
     */
    public void setPublicized() {
        setParameter(EbookQueryCriteria.publicized);
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(Integer schoolId) {
        setParameter(EbookQueryCriteria.schoolId, schoolId);
    }

    /**
     * @param subjectId the subjectId to set
     */
    public void setSubjectId(Integer subjectId) {
        setParameter(EbookQueryCriteria.subjectId, subjectId);
    }

    /**
     * @param sectionId the sectionId to set
     */
    public void setSectionId(Integer sectionId) {
        setParameter(EbookQueryCriteria.sectionId, sectionId);
    }
}