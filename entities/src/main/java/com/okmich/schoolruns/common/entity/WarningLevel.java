/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "warning_level")
public class WarningLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "warning_level_id")
    private Integer warningLevelId;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "warningLevel")
    private List<StudentDiscipline> studentDisciplineList;

    public WarningLevel() {
    }

    public WarningLevel(Integer warningLevelId) {
        this.warningLevelId = warningLevelId;
    }

    public WarningLevel(Integer warningLevelId, String description) {
        this.warningLevelId = warningLevelId;
        this.description = description;
    }

    public Integer getWarningLevelId() {
        return warningLevelId;
    }

    public void setWarningLevelId(Integer warningLevelId) {
        this.warningLevelId = warningLevelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<StudentDiscipline> getStudentDisciplineList() {
        return studentDisciplineList;
    }

    public void setStudentDisciplineList(List<StudentDiscipline> studentDisciplineList) {
        this.studentDisciplineList = studentDisciplineList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (warningLevelId != null ? warningLevelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WarningLevel)) {
            return false;
        }
        WarningLevel other = (WarningLevel) object;
        if ((this.warningLevelId == null && other.warningLevelId != null) || (this.warningLevelId != null && !this.warningLevelId.equals(other.warningLevelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.WarningLevel[ warningLevelId=" + warningLevelId + " ]";
    }
}
