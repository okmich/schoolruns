/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.dao;

/**
 *
 * @author Michael
 * @since Aug 13, 2013
 * @company Okmich Ltd.
 */
public interface SqlScripts {

    String WHERE_PLACEHOLDER = "#WHERE_CLAUSE# ";
    /**
     * FIND_CLASS_ATTENDANCE_FOR_DAY
     */
    String FIND_CLASS_ATTENDANCE_FOR_DAY = "select ss.school_student_id, st.surname, "
            + "st.firstname, sc.code,  sa.attend_date, sa.student_attendance_id, "
            + "stm.school_term_id from student_attendance sa right join school_student ss "
            + "on ss.school_student_id = sa.school_student_id AND "
            + "(attend_date = date(?) or attend_date is null) join student st on "
            + "ss.student_id = st.student_id join school_class sc on "
            + "ss.school_class_id = sc.school_class_id left join school_term stm on "
            + "stm.school_term_id in (select school_term_id from school_term where school_year_id = ss.school_year_id) "
            + "where ss.school_class_id = ? AND stm.school_term_id = ? "
            + "order by sc.code, st.surname, st.firstname";
    /**
     * STUDENT_SUMMARY_ATTENDANCE_SQL
     */
    String STUDENT_SUMMARY_ATTENDANCE_SQL = "select ss.school_student_id, st.surname, "
            + "st.firstname, ss.registration_no, count(sa.attend_date) attend_count from student_attendance sa "
            + "right join school_student ss on ss.school_student_id = sa.school_student_id "
            + "join student st on ss.student_id = st.student_id where 1 = 1 " + WHERE_PLACEHOLDER
            + " group by ss.school_student_id, st.surname, st.firstname, ss.registration_no";
    /**
     * FIND_EMP_ATTENDANCE_FOR_DAY
     */
    String FIND_EMP_ATTENDANCE_FOR_DAY = "select ee.employee_id, ee.surname, "
            + "ee.othernames, ee.staff_number, ea.employee_attendance_id, ea.attend_date, ea.school_term_id "
            + "from employee_attendance ea right join employee ee on ee.employee_id = "
            + "ea.employee_id AND (ea.attend_date = date(?) or ea.attend_date is null) "
            + "where date_of_hire < (select start_date from school_term where school_term_id = ?) "
            + "order by ee.surname, ee.othernames";
    /**
     * EMP_SUMMARY_ATTENDANCE_SQL
     */
    String EMP_SUMMARY_ATTENDANCE_SQL = "select ee.employee_id, ee.surname, ee.othernames, "
            + "ee.staff_number, count(ea.attend_date) attend_count from employee_attendance ea "
            + "right join employee ee on ee.employee_id = ea.employee_id  "
            + "where  1 = 1 " + WHERE_PLACEHOLDER
            + " group by ee.employee_id, ee.surname, ee.othernames, ee.staff_number "
            + "order by ee.surname, ee.othernames";
}
