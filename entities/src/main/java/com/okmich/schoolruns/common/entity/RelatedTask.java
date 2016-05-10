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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 * @since Oct 1, 2013
 * @company Okmich Ltd.
 */
@Entity
@Table(name = "related_task")
public class RelatedTask implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "related_task_id")
    private Integer relatedTaskId;
    @Basic(optional = false)
    @Column(name = "context_code")
    private String contextCode;
    @Basic(optional = false)
    @Column(name = "label")
    private String label;
    @Column(name = "icon")
    private String icon;
    @Column(name = "outcome")
    private String outcome;
    @Column(name = "url")
    private String url;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;

    public RelatedTask() {
    }

    public RelatedTask(Integer relatedTaskId) {
        this.relatedTaskId = relatedTaskId;
    }

    public Integer getRelatedTaskId() {
        return relatedTaskId;
    }

    public void setRelatedTaskId(Integer relatedTaskId) {
        this.relatedTaskId = relatedTaskId;
    }

    public String getContextCode() {
        return contextCode;
    }

    public void setContextCode(String contextCode) {
        this.contextCode = contextCode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (relatedTaskId != null ? relatedTaskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelatedTask)) {
            return false;
        }
        RelatedTask other = (RelatedTask) object;
        if ((this.relatedTaskId == null && other.relatedTaskId != null) || (this.relatedTaskId != null && !this.relatedTaskId.equals(other.relatedTaskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.RelatedTask[ relatedTaskId=" + relatedTaskId + " ]";
    }
}
