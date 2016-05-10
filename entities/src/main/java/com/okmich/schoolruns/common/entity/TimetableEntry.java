/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "timetable_entry")
public class TimetableEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "timetable_entry_id")
    private Integer timetableEntryId;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @JoinColumn(name = "classroom_id", referencedColumnName = "classroom_id")
    @ManyToOne
    private Classroom classroom;
    @JoinColumn(name = "school_subject_id", referencedColumnName = "school_subject_id")
    @ManyToOne(optional = false)
    private SchoolSubject schoolSubject;
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @ManyToOne
    private Employee employee;
    @JoinColumn(name = "class_period_id", referencedColumnName = "class_period_id")
    @ManyToOne(optional = false)
    private ClassPeriod classPeriod;
    @JoinColumn(name = "school_class_id", referencedColumnName = "school_class_id")
    @ManyToOne(optional = false)
    private SchoolClass schoolClass;
    @JoinColumn(name = "weekday_code", referencedColumnName = "code")
    @ManyToOne(optional = false)
    private Weekday weekday;

    /**
     *
     */
    public TimetableEntry() {
        this.schoolClass = new SchoolClass();
        this.schoolSubject = new SchoolSubject();
        this.classroom = new Classroom();
        this.employee = new Employee();
        if (this.classPeriod == null) {
            this.classPeriod = new ClassPeriod();
        }
        if (this.weekday == null) {
            this.weekday = new Weekday();
        }
    }

    /**
     *
     * @param _timeTableEntryId
     */
    public TimetableEntry(Integer _timeTableEntryId) {
        this();
        this.timetableEntryId = _timeTableEntryId;
    }

    /**
     *
     *
     * @param _weekday
     * @param _classPeriod
     */
    public TimetableEntry(Weekday _weekday, ClassPeriod _classPeriod) {
        this(null);
        this.weekday = _weekday;
        this.classPeriod = _classPeriod;
    }

    /**
     * @return the timetableEntryId
     */
    public Integer getTimetableEntryId() {
        return timetableEntryId;
    }

    /**
     * @param timetableEntryId the timetableEntryId to set
     */
    public void setTimetableEntryId(Integer timetableEntryId) {
        this.timetableEntryId = timetableEntryId;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the modifiedBy
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @param modifiedBy the modifiedBy to set
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return the modifiedTime
     */
    public Date getModifiedTime() {
        return modifiedTime;
    }

    /**
     * @param modifiedTime the modifiedTime to set
     */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * @return the classroom
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * @param classroom the classroom to set
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    /**
     * @return the classPeriod
     */
    public ClassPeriod getClassPeriod() {
        return classPeriod;
    }

    /**
     * @param classPeriod the classPeriod to set
     */
    public void setClassPeriod(ClassPeriod classPeriod) {
        this.classPeriod = classPeriod;
    }

    /**
     * @return the schoolClass
     */
    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    /**
     * @param schoolClass the schoolClass to set
     */
    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    /**
     * @return the weekday
     */
    public Weekday getWeekday() {
        return weekday;
    }

    /**
     * @param weekday the weekday to set
     */
    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }

    /**
     * @return the schoolSubject
     */
    public SchoolSubject getSchoolSubject() {
        return schoolSubject;
    }

    /**
     * @param schoolSubject the schoolSubject to set
     */
    public void setSchoolSubject(SchoolSubject schoolSubject) {
        this.schoolSubject = schoolSubject;
    }

    /**
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getTimetableEntryId() != null ? getTimetableEntryId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TimetableEntry)) {
            return false;
        }
        TimetableEntry other = (TimetableEntry) object;
        if ((this.getTimetableEntryId() == null && other.getTimetableEntryId() != null)
                || (this.getTimetableEntryId() != null && !this.timetableEntryId.equals(other.timetableEntryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.TimetableEntry[ timetableEntryId="
                + getTimetableEntryId() + " ]";
    }

    @PrePersist
    @PreUpdate
    protected void trigger() {
        if (getClassroom().getClassroomId() == null) {
            setClassroom(null);
        }
        if (getEmployee().getEmployeeId() == null) {
            setEmployee(null);
        }
    }
}
