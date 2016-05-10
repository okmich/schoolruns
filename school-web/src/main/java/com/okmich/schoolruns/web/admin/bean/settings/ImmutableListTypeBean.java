/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean.settings;

import com.okmich.schoolruns.common.entity.EventType;
import com.okmich.schoolruns.common.entity.GradeBand;
import com.okmich.schoolruns.common.entity.MessageChannel;
import com.okmich.schoolruns.common.entity.ParticipantCategory;
import com.okmich.schoolruns.common.entity.Weekday;
import com.okmich.schoolruns.common.service.CommonService;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@ViewScoped
public class ImmutableListTypeBean extends _BaseBean {

    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    private DataModel<EventType> eventTypeModel;
    private DataModel<ParticipantCategory> participantCategoryModel;
    private DataModel<MessageChannel> messageChannelModel;
    private DataModel<GradeBand> gradeBandModel;
    private DataModel<Weekday> weekdayModel;

    /**
     * Creates a new instance of ImmutableListTypeBean
     */
    public ImmutableListTypeBean() {
    }

    /**
     * @param commonService the commonService to set
     */
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     * @return the eventTypeModel
     */
    public DataModel<EventType> getEventTypeModel() {
        if (eventTypeModel == null) {
            this.eventTypeModel = new ListDataModel<EventType>(
                    commonService.findEventTypes());
        }
        return eventTypeModel;
    }

    /**
     * @param eventTypeModel the eventTypeModel to set
     */
    public void setEventTypeModel(DataModel<EventType> eventTypeModel) {
        this.eventTypeModel = eventTypeModel;
    }

    /**
     * @return the gradeBandModel
     */
    public DataModel<GradeBand> getGradeBandModel() {
        if (gradeBandModel == null) {
            this.gradeBandModel = new ListDataModel<GradeBand>(
                    commonService.findGradeBands());
        }
        return gradeBandModel;
    }

    /**
     * @param gradeBandModel the gradeBandModel to set
     */
    public void setGradeBandModel(DataModel<GradeBand> gradeBandModel) {
        this.gradeBandModel = gradeBandModel;
    }

    /**
     * @return the participantCategoryModel
     */
    public DataModel<ParticipantCategory> getParticipantCategoryModel() {
        if (participantCategoryModel == null) {
            this.participantCategoryModel = new ListDataModel<ParticipantCategory>(
                    commonService.findParticipantCategories());
        }
        return participantCategoryModel;
    }

    /**
     * @param participantCategoryModel the participantCategoryModel to set
     */
    public void setParticipantCategoryModel(DataModel<ParticipantCategory> participantCategoryModel) {
        this.participantCategoryModel = participantCategoryModel;
    }

    /**
     * @return the messageChannelModel
     */
    public DataModel<MessageChannel> getMessageChannelModel() {
        if (messageChannelModel == null) {
            this.messageChannelModel = new ListDataModel<MessageChannel>(
                    commonService.findMessageChannels());
        }
        return messageChannelModel;
    }

    /**
     * @param messageChannelModel the messageChannelModel to set
     */
    public void setMessageChannelModel(DataModel<MessageChannel> messageChannelModel) {
        this.messageChannelModel = messageChannelModel;
    }

    /**
     * @return the weekdayModel
     */
    public DataModel<Weekday> getWeekdayModel() {
        if (weekdayModel == null) {
            this.weekdayModel = new ListDataModel<Weekday>(
                    commonService.findWeekdays());
        }
        return weekdayModel;
    }

    /**
     * @param weekdayModel the weekdayModel to set
     */
    public void setWeekdayModel(DataModel<Weekday> weekdayModel) {
        this.weekdayModel = weekdayModel;
    }
}
