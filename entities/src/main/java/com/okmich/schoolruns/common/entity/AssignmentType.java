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
@Table(name = "assignment_type")
public class AssignmentType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "assignment_type_code")
    private String assignmentTypeCode;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    public AssignmentType() {
    }

    public AssignmentType(String assignmentTypeCode) {
        this.assignmentTypeCode = assignmentTypeCode;
    }

    public AssignmentType(String assignmentTypeCode, String description) {
        this.assignmentTypeCode = assignmentTypeCode;
        this.description = description;
    }

    public String getAssignmentTypeCode() {
        return assignmentTypeCode;
    }

    public void setAssignmentTypeCode(String assignmentTypeCode) {
        this.assignmentTypeCode = assignmentTypeCode;
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
        hash += (assignmentTypeCode != null ? assignmentTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssignmentType)) {
            return false;
        }
        AssignmentType other = (AssignmentType) object;
        if ((this.assignmentTypeCode == null && other.assignmentTypeCode != null) || (this.assignmentTypeCode != null && !this.assignmentTypeCode.equals(other.assignmentTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.AssignmentType[ assignmentTypeCode=" + assignmentTypeCode + " ]";
    }
}
