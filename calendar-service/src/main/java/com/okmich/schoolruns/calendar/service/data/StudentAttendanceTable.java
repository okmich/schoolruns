/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.data;

import com.okmich.common.util.api.ArrayTable;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Michael
 * @since Aug 13, 2013
 * @company Okmich Ltd.
 */
public final class StudentAttendanceTable extends ArrayTable<Attendance<SchoolStudent>, SchoolStudent, Date> {

    public StudentAttendanceTable() {
        super();
    }

    /**
     *
     *
     * @param date
     */
    public StudentAttendanceTable(List<Date> date) {
        this();
        setDates(date);
    }

    /**
     *
     * @param attendances
     */
    public void addAttendances(List<Attendance<SchoolStudent>> attendances) {
        for (Attendance<SchoolStudent> attendance : attendances) {
            super.setValue(attendance.getType(),
                    attendance.getAttendDate(), attendance);
        }
    }

    /**
     *
     *
     * @param attendance
     */
    public void addAttendances(Attendance<SchoolStudent> attendance) {
        super.setValue(attendance.getType(),
                attendance.getAttendDate(), attendance);
    }

    /**
     *
     * @param dates
     */
    public void setDates(List<Date> dates) {
        super.setTableHeader(dates);
    }

    /**
     *
     * @return List<Date>
     */
    public List<Date> getDates() {
        Collection<Date> coll = super.getColumnIndexes();
        if (coll == null) {
            return new ArrayList<>();
        }
        //Collections.so
        List<Date> dateList = new ArrayList<>(coll);
        Collections.sort(dateList);
        return dateList;
    }

    /**
     *
     * @param schoolStudents
     */
    public void setSchoolStudents(List<SchoolStudent> entities) {
        super.addRows(entities);
    }

    /**
     *
     * @return List<T>
     */
    public List<SchoolStudent> getSchoolStudents() {
        Collection<SchoolStudent> coll = super.getRowIndexes();
        if (coll == null) {
            return new ArrayList<>();
        }
        //Collections.so
        List<SchoolStudent> dataList = new ArrayList<>(coll);
        Collections.sort(dataList, new Comparator<SchoolStudent>() {
            @Override
            public int compare(SchoolStudent o1, SchoolStudent o2) {
                return o1.getStudent().getFullname().compareTo(
                        o2.getStudent().getFullname());
            }
        });
        return dataList;
    }

    /**
     *
     * @return List<T>
     */
    public Attendance<SchoolStudent> getAttendance(SchoolStudent ss, Date date) {
        Attendance<SchoolStudent> studAttend = super.getValue(ss, date);
        if (studAttend == null) {
            studAttend = new Attendance<>();
            studAttend.setAttendDate(date);
            studAttend.setType(ss);
            super.setValue(ss, date, studAttend);
        }
        return studAttend;
    }
}