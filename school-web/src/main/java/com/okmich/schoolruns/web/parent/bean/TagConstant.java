/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.parent.bean;

import java.io.Serializable;

/**
 *
 * @author Michael
 * @since Oct 11, 2013
 * @company Okmich Ltd.
 */
interface TagConstant extends Serializable {

    /**
     * STUDENT_TAG
     */
    static final String STUDENT_TAG = "${STUDENT_ID}";
    /**
     * SCHOOL_STUDENT_TAG
     */
    static final String SCHOOL_STUDENT_TAG = "${SCHOOL_STUDENT_ID}";
    /**
     * SCHOOL_STUDENT_TAG
     */
    static final String SCHOOL_TERM_TAG = "${SCHOOL_TERM_ID}";
    /**
     * SCHOOL_YEAR_TAG
     */
    static final String SCHOOL_YEAR_TAG = "${SCHOOL_YEAR_ID}";
}
