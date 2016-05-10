/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.calendar.service.data;

import com.okmich.common.util.api.ArrayTable;
import com.okmich.schoolruns.common.entity.ExamScore;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import java.util.List;

/**
 *
 * @author Michael
 */
public final class ExamScoreTable extends ArrayTable<ExamScore, SchoolStudent, SchoolSubject> {

    /**
     * default constructor
     */
    public ExamScoreTable() {
        super();
    }

    /**
     *
     *
     * @param schoolSubject
     */
    public ExamScoreTable(List<SchoolSubject> schoolSubject) {
        this();
        setSchoolSubjects(schoolSubject);
    }

    /**
     *
     * @param examScores
     */
    public void addExamScores(List<ExamScore> examScores) {
        for (ExamScore examScore : examScores) {
            super.setValue(examScore.getSchoolStudent(),
                    examScore.getExam().getSchoolSubject(), examScore);
        }
    }

    /**
     *
     *
     * @param examScore
     */
    public void addExamScores(ExamScore examScore) {
        super.setValue(examScore.getSchoolStudent(),
                examScore.getExam().getSchoolSubject(), examScore);
    }

    /**
     *
     * @param schoolSubjects
     */
    public void setSchoolSubjects(List<SchoolSubject> schoolSubjects) {
        super.setTableHeader(schoolSubjects);
    }

    /**
     *
     * @return List<SchoolSubject>
     */
    public List<SchoolSubject> getSchoolSubjects() {
        return (List<SchoolSubject>) super.getColumnIndexes();
    }

    /**
     *
     * @param schoolStudents
     */
    public void setSchoolStudents(List<SchoolStudent> schoolStudents) {
        super.addRows(schoolStudents);
    }

    /**
     *
     * @return List<SchoolStudent>
     */
    public List<SchoolStudent> getSchoolStudents() {
        return (List<SchoolStudent>) super.getRowIndexes();
    }

    /**
     *
     * @return List<SchoolStudent>
     */
    public ExamScore getExamScore(SchoolStudent schoolStudent, SchoolSubject schoolSubject) {
        return super.getValue(schoolStudent, schoolSubject);
    }
}