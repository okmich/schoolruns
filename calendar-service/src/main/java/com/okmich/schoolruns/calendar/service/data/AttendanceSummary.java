/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.data;

import com.okmich.common.BaseData;
import java.util.Date;

/**
 *
 * @author Michael
 * @since Aug 13, 2013
 * @company Okmich Ltd.
 */
public class AttendanceSummary extends BaseData {

    private Integer id;
    private String name;
    private Date date;
    private long count;
    private long total;

    /**
     *
     */
    public AttendanceSummary() {
    }

    /**
     *
     * @param id
     * @param name
     * @param count
     * @param total
     */
    public AttendanceSummary(Integer id, String name, Integer count, Integer total) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.total = total;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the count
     */
    public long getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(long count) {
        this.count = count;
    }

    /**
     * @return the total
     */
    public long getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(long total) {
        this.total = total;
    }
}
