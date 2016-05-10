/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.data;

import com.okmich.common.util.api.ArrayTable;
import com.okmich.schoolruns.common.entity.ClassPeriod;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.TimetableEntry;
import com.okmich.schoolruns.common.entity.Weekday;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Michael
 * @since Aug 30, 2013
 * @company Okmich Ltd.
 */
public class TtEntryTable extends ArrayTable<TimetableEntry, Weekday, ClassPeriod> {

    /**
     * default constructor
     */
    public TtEntryTable() {
        super();
    }

    /**
     *
     *
     * @param classPeriod
     */
    public TtEntryTable(List<Weekday> weekdays) {
        this();
        setWeekdays(weekdays);
    }

    /**
     *
     * @param timetableEntries
     */
    public void addTimetableEntries(List<TimetableEntry> timetableEntries) {
        for (TimetableEntry timetableEntry : timetableEntries) {
            super.setValue(timetableEntry.getWeekday(),
                    timetableEntry.getClassPeriod(), timetableEntry);
        }
    }

    /**
     *
     *
     * @param timetableEntry
     */
    public void addTimetableEntry(TimetableEntry timetableEntry) {
        super.setValue(timetableEntry.getWeekday(),
                timetableEntry.getClassPeriod(), timetableEntry);
    }

    /**
     *
     * @param classPeriods
     */
    public void setClassPeriods(List<ClassPeriod> classPeriods) {
        super.setTableHeader(classPeriods);
    }

    /**
     *
     * @return List<ClassPeriod>
     */
    public List<ClassPeriod> getClassPeriods() {
        Collection<ClassPeriod> coll = super.getColumnIndexes();
        if (coll == null) {
            return new ArrayList<>();
        }
        //Collections.so
        List<ClassPeriod> subList = new ArrayList<>(coll);
        Collections.sort(subList, new Comparator<ClassPeriod>() {
            @Override
            public int compare(ClassPeriod o1, ClassPeriod o2) {
                return o1.getPeriodNumber().compareTo(
                        o2.getPeriodNumber());
            }
        });

        return subList;
    }

    /**
     *
     * @param weekdays
     */
    public void setWeekdays(List<Weekday> weekdays) {
        super.addRows(weekdays);
    }

    /**
     *
     * @return List<Weekday>
     */
    public List<Weekday> getWeekdays() {
        Collection<Weekday> row1 = super.getRowIndexes();
        if (row1 == null) {
            return new ArrayList<>();
        }
        //Collections.so
        List<Weekday> dataList = new ArrayList<>(row1);
        Collections.sort(dataList, new Comparator<Weekday>() {
            @Override
            public int compare(Weekday o1, Weekday o2) {
                return o1.getCode().compareTo(
                        o2.getCode());
            }
        });
        return dataList;
    }

    /**
     *
     * @return List<Weekday>
     */
    public TimetableEntry getTimetableEntry(Weekday weekday, ClassPeriod classPeriod) {
        TimetableEntry timetableEntry = super.getValue(weekday, classPeriod);
        if (timetableEntry == null) {
            timetableEntry = new TimetableEntry(weekday, classPeriod);
            super.setValue(weekday, classPeriod, timetableEntry);
        }
        return timetableEntry;
    }
}
