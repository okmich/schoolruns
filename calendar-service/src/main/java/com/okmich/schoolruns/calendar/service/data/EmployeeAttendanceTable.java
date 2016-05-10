/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.data;

import com.okmich.common.util.api.ArrayTable;
import com.okmich.schoolruns.common.entity.Employee;
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
public final class EmployeeAttendanceTable extends ArrayTable<Attendance<Employee>, Employee, Date> {

    public EmployeeAttendanceTable() {
        super();
    }

    /**
     *
     *
     * @param date
     */
    public EmployeeAttendanceTable(List<Date> date) {
        this();
        setDates(date);
    }

    /**
     *
     * @param attendances
     */
    public void addAttendances(List<Attendance<Employee>> attendances) {
        for (Attendance<Employee> attendance : attendances) {
            super.setValue(attendance.getType(),
                    attendance.getAttendDate(), attendance);
        }
    }

    /**
     *
     *
     * @param attendance
     */
    public void addAttendances(Attendance<Employee> attendance) {
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
    public void setEmployees(List<Employee> entities) {
        super.addRows(entities);
    }

    /**
     *
     * @return List<T>
     */
    public List<Employee> getEmployees() {
        Collection<Employee> coll = super.getRowIndexes();
        if (coll == null) {
            return new ArrayList<>();
        }
        //Collections.so
        List<Employee> dataList = new ArrayList<>(coll);
        Collections.sort(dataList, new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                if (o1 == null || o2 == null) {
                    return 0;
                }
                return o1.getFullname().compareTo(
                        o2.getFullname());
            }
        });
        return dataList;
    }

    /**
     *
     * @return List<T>
     */
    public Attendance<Employee> getAttendance(Employee emp, Date date) {
        Attendance<Employee> empAttend = super.getValue(emp, date);
        if (empAttend == null) {
            empAttend = new Attendance<>();
            empAttend.setAttendDate(date);
            empAttend.setType(emp);
            super.setValue(emp, date, empAttend);
        }
        return empAttend;
    }
}