/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.calendar;

import com.okmich.common.util.DateUtil;
import com.okmich.schoolruns.calendar.service.SchoolCalendarService;
import com.okmich.schoolruns.common.entity.Event;
import com.okmich.schoolruns.common.entity.SchoolTerm;
import com.okmich.schoolruns.notification.service.data.MessageData;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolNotificationBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class SchoolCalendarBean extends _BaseBean {

    private static final Logger LOG = Logger.getLogger(SchoolCalendarBean.class.getName());
    @ManagedProperty("#{schoolCalendarService}")
    private SchoolCalendarService schoolCalendarService;
    @ManagedProperty("#{schoolNotificationBean}")
    private SchoolNotificationBean schoolNotificationBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private DataModel eventModel;
    private ScheduleModel schoolScheduleModel;
    private ScheduleEvent scheduleEvent;
    private Event event;

    /**
     * Creates a new instance of SchoolCalendarBean
     */
    public SchoolCalendarBean() {
        this.event = new Event();
        this.scheduleEvent = new ScheduleEventImpl(this.event);
    }

    /**
     * @param schoolCalendarService the schoolCalendarService to set
     */
    public void setSchoolCalendarService(SchoolCalendarService schoolCalendarService) {
        this.schoolCalendarService = schoolCalendarService;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @param schoolNotificationBean the schoolNotificationBean to set
     */
    public void setSchoolNotificationBean(SchoolNotificationBean schoolNotificationBean) {
        this.schoolNotificationBean = schoolNotificationBean;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     *
     *
     * @return
     */
    public String saveEvent() {
        try {
            getEvent().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getEvent().setSchoolYearId(
                    schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
            schoolCalendarService.createEvent(getEvent());
            return "/schooluser/calendar/schoolcalendar";
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return "";
    }

    public String prepareNotifyOnEvent() {
        if (getEvent() != null) {
            MessageData messageData = new MessageData();

            messageData.setContent(getEvent().getDescription());
            messageData.setExpiryDate(getEvent().getEndDate());
            messageData.setLiveDate(getEvent().getStartDate());
            messageData.setParticipantCategory(
                    getEvent().getParticipantCategory().getDescription());
            messageData.setParticipantCategoryCode(getEvent().
                    getParticipantCategory().getParticipantCategoryCode());
            messageData.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
            messageData.setTitle(getEvent().getTitle());

            schoolNotificationBean.setMessageData(messageData);

            return "/schooluser/notification/newnotification";
        }
        return "";
    }

    public void onEventSelect(SelectEvent e) {
        setScheduleEvent((ScheduleEventImpl) e.getObject());
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        ScheduleEventImpl scheduleEventImpl = (ScheduleEventImpl) event.getScheduleEvent();
        //add the difference in days
        Date newDate = DateUtil.getInstance().addDaysToDate(
                scheduleEventImpl.getEndDate(), event.getDayDelta());
        scheduleEventImpl.getEvent().setEndDate(newDate);
        setScheduleEvent(scheduleEventImpl);
    }

    /**
     * @return the schoolScheduleModel
     */
    public ScheduleModel getSchoolScheduleModel() {
        if (this.schoolScheduleModel == null) {
            this.schoolScheduleModel = new DefaultScheduleModel();
            List<Event> events = schoolCalendarService.findSchoolEvents(
                    schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
            for (Event e : events) {
                this.schoolScheduleModel.addEvent(new ScheduleEventImpl(e));
            }
        }
        return schoolScheduleModel;
    }

    /**
     * @param schoolScheduleModel the schoolScheduleModel to set
     */
    public void setSchoolScheduleModel(ScheduleModel schoolScheduleModel) {
        this.schoolScheduleModel = schoolScheduleModel;
    }

    /**
     * @return the scheduleEvent
     */
    public ScheduleEvent getScheduleEvent() {
        return scheduleEvent;
    }

    /**
     * @param scheduleEvent the scheduleEvent to set
     */
    public void setScheduleEvent(ScheduleEvent scheduleEvent) {
        this.scheduleEvent = scheduleEvent;
    }

    /**
     * @return the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @param event the event to set
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * @return the eventModel
     */
    public DataModel getEventModel() {
        if (eventModel == null) {
            List<Event> events = schoolCalendarService.findSchoolEvents(
                    schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
            eventModel = new ListDataModel(events);
        }
        return eventModel;
    }

    /**
     * @param eventModel the eventModel to set
     */
    public void setEventModel(DataModel eventModel) {
        this.eventModel = eventModel;
    }
}