/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.calendar;

import com.okmich.schoolruns.common.entity.Event;
import java.util.Date;
import org.primefaces.model.ScheduleEvent;

/**
 *
 * @author Michael
 */
public class ScheduleEventImpl implements ScheduleEvent {

    private Event event;
    private String id;

    public ScheduleEventImpl() {
        this.event = new Event();
    }

    /**
     *
     * @param _event
     */
    public ScheduleEventImpl(Event _event) {
        this.event = _event;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String string) {
        this.id = string;
    }

    @Override
    public Event getData() {
        return this.event;
    }

    @Override
    public String getTitle() {
        return this.event.getTitle();
    }

    @Override
    public Date getStartDate() {
        return this.event.getStartDate();
    }

    @Override
    public Date getEndDate() {
        return this.event.getEndDate();
    }

    @Override
    public boolean isAllDay() {
        return false;
    }

    @Override
    public String getStyleClass() {
        return null;
    }

    @Override
    public boolean isEditable() {
        return true;
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

    @Override
    public String getDescription() {
        return "description of this event - to be concluded";
    }
}
