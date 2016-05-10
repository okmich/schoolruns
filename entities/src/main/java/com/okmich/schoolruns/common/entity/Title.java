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
@Table(name = "title")
public class Title implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "title_code")
    private String titleCode;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    public Title() {
    }

    /**
     *
     * @param _titleCode
     */
    public Title(String _titleCode) {
        this.titleCode = _titleCode;
    }

    /**
     * @return the titleCode
     */
    public String getTitleCode() {
        return titleCode;
    }

    /**
     * @param titleCode the titleCode to set
     */
    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getTitleCode() != null ? getTitleCode().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Title)) {
            return false;
        }
        Title other = (Title) object;
        if ((this.titleCode == null && other.titleCode != null)
                || (this.titleCode != null && !this.titleCode.equals(other.titleCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.Title[ titleCode=" + getTitleCode() + " ]";
    }
}
