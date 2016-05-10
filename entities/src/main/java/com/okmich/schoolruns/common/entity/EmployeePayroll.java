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

/**
 *
 * @author Michael
 * @since Aug 21, 2013
 * @company Okmich Ltd.
 */
@Entity
@Table(name = "employee_payroll")
public class EmployeePayroll implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "employee_payroll_id")
    private Integer employeePayrollId;
    @Basic(optional = false)
    @Column(name = "employee_id")
    private Integer employeeId;
    @Basic(optional = false)
    @Column(name = "employee_name")
    private String employeeName;
    @Column(name = "employee_category")
    private String employeeCategory;
    @Column(name = "employee_position")
    private String employeePosition;
    @Basic(optional = false)
    @Column(name = "pay_period")
    private String payPeriod;
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
    @JoinColumn(name = "payroll_id", referencedColumnName = "payroll_id")
    @ManyToOne(optional = false)
    private Payroll payroll;
    @JoinColumn(name = "pay_structure_id", referencedColumnName = "pay_structure_id")
    @ManyToOne(optional = false)
    private PayStructure payStructure;

    public EmployeePayroll() {
        this.payroll = new Payroll();
        this.payStructure = new PayStructure();
        this.tax = BigDecimal.ZERO;
    }

    public EmployeePayroll(Integer employeePayrollId) {
        this();
        this.employeePayrollId = employeePayrollId;
    }

    public Integer getEmployeePayrollId() {
        return employeePayrollId;
    }

    public void setEmployeePayrollId(Integer employeePayrollId) {
        this.employeePayrollId = employeePayrollId;
    }

    /**
     * @return the employeeId
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeCategory() {
        return employeeCategory;
    }

    public void setEmployeeCategory(String employeeCategory) {
        this.employeeCategory = employeeCategory;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
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

    public Payroll getPayroll() {
        return payroll;
    }

    public void setPayroll(Payroll payroll) {
        this.payroll = payroll;
    }

    public PayStructure getPayStructure() {
        return payStructure;
    }

    public void setPayStructure(PayStructure payStructure) {
        this.payStructure = payStructure;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeePayrollId != null ? employeePayrollId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeePayroll)) {
            return false;
        }
        EmployeePayroll other = (EmployeePayroll) object;
        if ((this.employeePayrollId == null && other.employeePayrollId != null) || (this.employeePayrollId != null && !this.employeePayrollId.equals(other.employeePayrollId))) {
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
        return "com.okmich.schoolruns.common.entity.EmployeePayroll[ employeePayrollId=" + employeePayrollId + " ]";
    }
}