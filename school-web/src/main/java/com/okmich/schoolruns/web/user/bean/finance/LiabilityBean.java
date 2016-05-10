/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.common.entity.criteria.WCDate;
import com.okmich.common.entity.criteria.WCString;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Liability;
import com.okmich.schoolruns.finance.service.FinanceService;
import com.okmich.schoolruns.finance.service.repo.LiabilityQueryCriteria;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class LiabilityBean extends _BaseBean {

    @ManagedProperty("#{financeService}")
    private FinanceService financeService;
    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private Liability liability;
    private String name;
    private Integer liabilityTypeId;
    private Date fromDate, toDate;

    /**
     * Creates a new instance of LiabilityBean
     */
    public LiabilityBean() {
        this.liability = new Liability();
    }

    /**
     * @param financeService the financeService to set
     */
    public void setFinanceService(FinanceService financeService) {
        this.financeService = financeService;
    }

    /**
     * @param financeSessionBean the financeSessionBean to set
     */
    public void setFinanceSessionBean(FinanceSessionBean financeSessionBean) {
        this.financeSessionBean = financeSessionBean;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @return the liability
     */
    public Liability getLiability() {
        if (liability == null) {
            this.liability = new Liability();
        }
        return liability;
    }

    /**
     * @param liability the liability to set
     */
    public void setLiability(Liability liability) {
        this.liability = liability;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the liabilityTypeId
     */
    public Integer getLiabilityTypeId() {
        return liabilityTypeId;
    }

    /**
     * @param liabilityTypeId the liabilityTypeId to set
     */
    public void setLiabilityTypeId(Integer liabilityTypeId) {
        this.liabilityTypeId = liabilityTypeId;
    }

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String saveLiability() {
        getLiability().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getLiability().setSchool(schoolSessionBean.getSchool());
        try {
            financeService.saveLiability(getLiability());
            return "/schooluser/finance/liabilitysearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String findLiabilities() {
        LiabilityQueryCriteria criteria = buildQueryCriteria();
        try {
            List<Liability> liabilities = financeService.findLiabilities(criteria);
            financeSessionBean.setLiabilityModel(new ListDataModel(liabilities));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String findLiability() {
        if (getLiability().getLiabilityId() != null) {
            setLiability(getLiability());
            return "/schooluser/finance/liabilitydetails";
        }
        return "";
    }

    public String removeLiability() {
        getLiability().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getLiability().setSchool(schoolSessionBean.getSchool());
        try {
            //financeService.removeLiability(getLiability());
            return "/schooluser/finance/liabilitysearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    private LiabilityQueryCriteria buildQueryCriteria() {
        LiabilityQueryCriteria criteria = new LiabilityQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        if (this.name != null && !this.name.trim().isEmpty()) {
            criteria.setDescription(this.name, WCString.LIKE);
        }
        if (this.liabilityTypeId != null && this.liabilityTypeId != 0) {
            criteria.setLiabilityTypeId(this.liabilityTypeId);
        }
        if (this.fromDate != null && this.toDate != null) {
            criteria.setAssumeDateDate(fromDate, WCDate.BETWEEN, toDate);
        }
        if (this.getStatus() != null && !this.getStatus().isEmpty()) {
            criteria.setStatus(this.getStatus());
        } else {
            criteria.setStatus(CommonConstants.STATUS_ACTIVE);
        }

        return criteria;
    }
}
