/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.notification.service.repo;

import com.okmich.common.entity.criteria.BaseEntityQueryCriteria;
import com.okmich.common.entity.criteria.RelatedEntity;
import com.okmich.common.entity.criteria.WCDate;
import com.okmich.common.entity.criteria.WCString;
import java.util.Date;

/**
 *
 * @author Michael
 */
public class MessageQueryCriteria extends BaseEntityQueryCriteria {

    public static final String title = "title";
    public static final String liveDate = "liveDate";
    public static final String expiryDate = "expiryDate";
    @RelatedEntity(entityAlias = "a", referencedEntity = "school")
    public static final String schoolId = "schoolId";
    @RelatedEntity(entityAlias = "b", referencedEntity = "participantCategory")
    public static final String participantCategoryCode = "participantCategoryCode";
    @RelatedEntity(entityAlias = "c", referencedEntity = "messageChannel")
    public static final String messageChannelCode = "messageChannelCode";

    public MessageQueryCriteria() {
    }

    @Override
    public String getEntityName() {
        return "Message";
    }

    /**
     * @param _title the title to set
     */
    public void setTitle(String _title, WCString wClause) {
        setParameter(MessageQueryCriteria.title, wClause, _title);
    }

    /**
     * @param _liveDate the liveDate to set
     */
    public void setLiveDate(Date _liveDate, WCDate wclause) {
        setParameter(MessageQueryCriteria.liveDate, wclause, _liveDate);
    }

    /**
     * @param _expiryDate the expiryDate to set
     */
    public void setExpiryDate(Date _expiryDate, WCDate wclause) {
        setParameter(MessageQueryCriteria.expiryDate, wclause, _expiryDate);
    }

    /**
     * @param _schoolId the schoolId to set
     */
    public void setSchoolId(Integer _schoolId) {
        setParameter(MessageQueryCriteria.schoolId, _schoolId);
    }

    /**
     * @param pcCode the participantCategoryCode to set
     */
    public void setParticipantCategoryCode(String pcCode) {
        setParameter(MessageQueryCriteria.participantCategoryCode, pcCode);
    }

    /**
     * @param mcCode the messageChannelCode to set
     */
    public void setMessageChannelCode(String mcCode) {
        setParameter(MessageQueryCriteria.messageChannelCode, mcCode);
    }
}