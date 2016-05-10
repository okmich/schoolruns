/**
 * 
 */
package com.okmich.schoolruns.common.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author Michael
 * 
 */
@MappedSuperclass
public abstract class BaseDomain extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createDate;
	@Column(name = "created_by")
	protected String createdBy;
	@Basic(optional = false)
	@Column(name = "modified_date")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date modifiedDate;
	@Column(name = "modified_by")
	protected String modifiedBy;
	@Version
	@Column(name = "version")
	protected Short version;

	/**
	 * default constructor
	 */
	public BaseDomain() {
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy
	 *            the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the version
	 */
	public Short getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Short version) {
		this.version = version;
	}
	
    @PrePersist
    protected void prePresist() {
        this.setCreateDate(new Date());
        this.setStatus(this.getStatus() == null ? "A" : this.getStatus());
    }

    @PreUpdate
    protected void preUpdate() {
        this.setModifiedDate(new Date());
    }
}
