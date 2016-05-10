/**
 * 
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Michael
 * 
 */
@MappedSuperclass
public abstract class BaseDto implements Serializable {

	/**
	 * 
	 */
	protected static final long serialVersionUID = -8802522556493663440L;
	@Basic(optional = false)
	@Column(name = "status")
	protected String status;

	/**
	 * 
	 */
	public BaseDto() {
	}

	/**
	 * @return the status
	 */
	public final String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public final void setStatus(String status) {
		this.status = status;
	}
}
