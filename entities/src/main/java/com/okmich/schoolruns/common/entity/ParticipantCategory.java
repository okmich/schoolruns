/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
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
@Table(name = "participant_category")
public class ParticipantCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "participant_category_code")
    private String participantCategoryCode;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    public ParticipantCategory() {
    }

    public ParticipantCategory(String participantCategoryCode) {
        this.participantCategoryCode = participantCategoryCode;
    }

    public ParticipantCategory(String participantCategoryCode, String description) {
        this.participantCategoryCode = participantCategoryCode;
        this.description = description;
    }

    public String getParticipantCategoryCode() {
        return participantCategoryCode;
    }

    public void setParticipantCategoryCode(String participantCategoryCode) {
        this.participantCategoryCode = participantCategoryCode;
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
        hash += (participantCategoryCode != null ? participantCategoryCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParticipantCategory)) {
            return false;
        }
        ParticipantCategory other = (ParticipantCategory) object;
        if ((this.participantCategoryCode == null && other.participantCategoryCode != null)
                || (this.participantCategoryCode != null && !this.participantCategoryCode.equals(other.participantCategoryCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.ParticipantCategory[ participantCategoryCode=" + participantCategoryCode + " ]";
    }
}
