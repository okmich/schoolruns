/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.school.service.data;

import com.okmich.common.util.api.ArrayTable;
import com.okmich.schoolruns.common.entity.ExamScore;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
        Collection<SchoolSubject> coll = super.getColumnIndexes();
        if (coll == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(coll);
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
        Collection<SchoolStudent> row1 = super.getRowIndexes();
        if (row1 == null) {
            return new ArrayList<>();
        }
        //Collections.so
        List<SchoolStudent> dataList = new ArrayList<>(row1);
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
     * @return List<SchoolStudent>
     */
    public ExamScore getExamScore(SchoolStudent schoolStudent, SchoolSubject schoolSubject) {
        ExamScore examScore = super.getValue(schoolStudent, schoolSubject);
        if (examScore == null) {
            examScore = new ExamScore();
            super.setValue(schoolStudent, schoolSubject, examScore);
        }
        return examScore;
    }
}