/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Michael
 * @since Aug 21, 2013
 * @company Okmich Ltd.
 */
@Entity
@Table(name = "employee_payroll_item")
public class EmployeePayrollItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "employee_payroll_item_id")
    private Integer employeePayrollItemId;
    @Column(name = "pay_structure_item_id")
    private Integer payStructureItemId;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "gross")
    private BigDecimal gross;
    @Basic(optional = false)
    @Column(name = "tax")
    private BigDecimal tax;
    @Basic(optional = false)
    @Column(name = "net")
    private BigDecimal net;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "modified_by")
    private String modifiedBy;
    @Basic(optional = false)
    @Column(name = "modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @JoinColumn(name = "employee_payroll_id", referencedColumnName = "employee_payroll_id")
    @ManyToOne(optional = false)
    private EmployeePayroll employeePayroll;
    @Transient
    private boolean applyTax;

    public EmployeePayrollItem() {
        this.employeePayroll = new EmployeePayroll();
    }

    public EmployeePayrollItem(Integer employeePayrollItemId) {
        this.employeePayrollItemId = employeePayrollItemId;
    }

    public Integer getEmployeePayrollItemId() {
        return employeePayrollItemId;
    }

    public void setEmployeePayrollItemId(Integer employeePayrollItemId) {
        this.employeePayrollItemId = employeePayrollItemId;
    }

    /**
     * @return the payStructureItemId
     */
    public Integer getPayStructureItemId() {
        return payStructureItemId;
    }

    /**
     * @param payStructureItemId the payStructureItemId to set
     */
    public void setPayStructureItemId(Integer payStructureItemId) {
        this.payStructureItemId = payStructureItemId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getGross() {
        return gross;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getNet() {
        return net;
    }

    public void setNet(BigDecimal net) {
        this.net = net;
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

    public EmployeePayroll getEmployeePayroll() {
        return employeePayroll;
    }

    public void setEmployeePayroll(EmployeePayroll employeePayroll) {
        this.employeePayroll = employeePayroll;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeePayrollItemId != null ? employeePayrollItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeePayrollItem)) {
            return false;
        }
        EmployeePayrollItem other = (EmployeePayrollItem) object;
        if ((this.employeePayrollItemId == null && other.employeePayrollItemId != null) || (this.employeePayrollItemId != null && !this.employeePayrollItemId.equals(other.employeePayrollItemId))) {
            return false;
        }
        return true;
    }

    @PrePersist
    @PreUpdate
    protected void trigger() {
        this.net = this.gross.subtract(this.tax);
    }

    @Override
    public String toString() {
        return "com.okmich.schoolruns.common.entity.EmployeePayrollItem[ employeePayrollItemId="
                + employeePayrollItemId + " ]";
    }

    /**
     * @return the applyTax
     */
    public boolean isApplyTax() {
        return applyTax;
    }

    /**
     * @param applyTax the applyTax to set
     */
    public void setApplyTax(boolean applyTax) {
        this.applyTax = applyTax;
    }
}
