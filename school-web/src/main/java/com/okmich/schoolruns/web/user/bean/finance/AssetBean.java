/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.common.entity.criteria.WCDate;
import com.okmich.common.entity.criteria.WCString;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Asset;
import com.okmich.schoolruns.finance.service.FinanceService;
import com.okmich.schoolruns.finance.service.repo.AssetQueryCriteria;
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
public class AssetBean extends _BaseBean {

    @ManagedProperty("#{financeService}")
    private FinanceService financeService;
    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private Asset asset;
    private String name;
    private Integer assetTypeCode;
    private Date fromDate, toDate;

    /**
     * Creates a new instance of AssetBean
     */
    public AssetBean() {
        this.asset = new Asset();
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
     * @return the asset
     */
    public Asset getAsset() {
        if (asset == null) {
            this.asset = new Asset();
        }
        return asset;
    }

    /**
     * @param asset the asset to set
     */
    public void setAsset(Asset asset) {
        this.asset = asset;
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
     * @return the assetTypeCode
     */
    public Integer getAssetTypeCode() {
        return assetTypeCode;
    }

    /**
     * @param assetTypeCode the assetTypeCode to set
     */
    public void setAssetTypeCode(Integer assetTypeCode) {
        this.assetTypeCode = assetTypeCode;
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

    public String saveAsset() {
        getAsset().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getAsset().setSchool(schoolSessionBean.getSchool());
        try {
            Asset _asset = financeService.saveAsset(getAsset());
            return "/schooluser/finance/assetsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String findAssets() {
        AssetQueryCriteria criteria = buildQueryCriteria();
        try {
            List<Asset> assets = financeService.findAssets(criteria);
            financeSessionBean.setAssetModel(new ListDataModel(assets));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String findAsset() {
        if (getAsset().getAssetId() != null) {
            setAsset(getAsset());
            return "/schooluser/finance/assetdetails";
        }
        return "";
    }

    public String removeAsset() {
        getAsset().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getAsset().setSchool(schoolSessionBean.getSchool());
        try {
            financeService.removeAsset(getAsset());
            return "/schooluser/finance/assetsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    private AssetQueryCriteria buildQueryCriteria() {
        AssetQueryCriteria criteria = new AssetQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());

        if (this.name != null && !this.name.trim().isEmpty()) {
            criteria.setDescription(this.name, WCString.LIKE);
        }
        if (this.assetTypeCode != null && this.assetTypeCode != 0) {
            criteria.setAssetTypeId(this.assetTypeCode);
        }
        if (this.fromDate != null && this.toDate != null) {
            criteria.setAcquireDate(fromDate, WCDate.BETWEEN, toDate);
        }
        if (this.getStatus() != null && !this.getStatus().isEmpty()) {
            criteria.setStatus(this.getStatus());
        } else {
            criteria.setStatus(CommonConstants.STATUS_ACTIVE);
        }

        return criteria;
    }
}
