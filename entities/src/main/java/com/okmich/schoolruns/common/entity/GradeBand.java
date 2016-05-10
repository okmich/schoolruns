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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "grade_band")
public class GradeBand implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "grade_band_code")
    private String gradeBandCode;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "min_score")
    private short minScore;
    @Basic(optional = false)
    @Column(name = "max_score")
    private short maxScore;
    @Basic(optional = false)
    @Column(name = "credit_power")
    private short creditPower;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;

    public GradeBand() {
    }

    public GradeBand(String gradeBandCode) {
        this.gradeBandCode = gradeBandCode;
    }

    public String getGradeBandCode() {
        return gradeBandCode;
    }

    public void setGradeBandCode(String gradeBandCode) {
        this.gradeBandCode = gradeBandCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getMinScore() {
        return minScore;
    }

    public void setMinScore(short minScore) {
        this.minScore = minScore;
    }

    public short getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(short maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * @return the creditPower
     */
    public short getCreditPower() {
        return creditPower;
    }

    /**
     * @param creditPower the creditPower to set
     */
    public void setCreditPower(short creditPower) {
        this.creditPower = creditPower;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gradeBandCode != null ? gradeBandCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GradeBand)) {
            return false;
        }
        GradeBand other = (GradeBand) object;
        if ((this.gradeBandCode == null && other.gradeBandCode != null) || (this.gradeBandCode != null && !this.gradeBandCode.equals(other.gradeBandCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.GradeBand[ gradeBandCode=" + gradeBandCode + " ]";
    }
}
