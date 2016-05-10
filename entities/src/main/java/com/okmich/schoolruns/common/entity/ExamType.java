/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "exam_type")
public class ExamType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "exam_type_code")
    private String examTypeCode;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    public ExamType() {
    }

    public ExamType(String examTypeCode) {
        this.examTypeCode = examTypeCode;
    }

    public ExamType(String examTypeCode, String description) {
        this.examTypeCode = examTypeCode;
        this.description = description;
    }

    public String getExamTypeCode() {
        return examTypeCode;
    }

    public void setExamTypeCode(String examTypeCode) {
        this.examTypeCode = examTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (examTypeCode != null ? examTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamType)) {
            return false;
        }
        ExamType other = (ExamType) object;
        if ((this.examTypeCode == null && other.examTypeCode != null) || (this.examTypeCode != null && !this.examTypeCode.equals(other.examTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.ExamType[ examTypeCode=" + examTypeCode + " ]";
    }
}
