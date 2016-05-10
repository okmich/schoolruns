/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.finance;

import com.okmich.schoolruns.common.entity.FeeGroup;
import com.okmich.schoolruns.finance.service.FinanceSetupService;
import com.okmich.schoolruns.finance.service.repo.FeeGroupQueryCriteria;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@ViewScoped
public class FeeGroupBean extends _BaseBean {

    @ManagedProperty("#{financeSetupService}")
    private FinanceSetupService financeSetupService;
    @ManagedProperty("#{financeSessionBean}")
    private FinanceSessionBean financeSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private DataModel<FeeGroup> feeGroupModel;
    private FeeGroup feeGroup;
    private boolean statutory;
    private Integer gradeLevelId;
    private Integer feeTypeCode;

    public FeeGroupBean() {
    }

    /**
     * @param financeSetupService the financeSetupService to set
     */
    public void setFinanceSetupService(FinanceSetupService financeSetupService) {
        this.financeSetupService = financeSetupService;
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
     * @return
     */
    public String saveFeeGroup() {
        FeeGroup _feeGroup = getFeeGroup();
        _feeGroup.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        _feeGroup.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        try {
            _feeGroup = financeSetupService.saveFeeGroup(_feeGroup);
            List<FeeGroup> feeGroups = ((List<FeeGroup>) feeGroupModel.getWrappedData());
            if (!feeGroups.contains(_feeGroup)) {
                feeGroups.add(_feeGroup);
            } else {
                feeGroups.set(feeGroups.indexOf(_feeGroup), _feeGroup);
            }
            financeSessionBean.getFeeGroups().clear();
            clearForm();
            return "/schooluser/finance/settings/feegroupsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findFeeGroup() {
        FeeGroup feeGroup = getFeeGroup();
        if (feeGroup != null && feeGroup.getFeeGroupId() != null) {
            setEditMode(VIEW);
            setFeeGroup(financeSetupService.findFeeGroup(
                    feeGroup.getFeeGroupId()));
        }
        return "";
    }

    /**
     * @return
     */
    public String findFeeGroups() {
        try {
            doFindFeeGroups();
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        if (getFeeGroup().getFeeGroupId() != null) {
            setFeeGroup(financeSetupService.findFeeGroup(
                    getFeeGroup().getFeeGroupId()));
        }
        setEditMode("MODIFY");
        return "";
    }

    /**
     * @return the feeGroupModel
     */
    public DataModel<FeeGroup> getFeeGroupModel() {
        if (feeGroupModel == null) {
            doFindFeeGroups();
        }
        return feeGroupModel;
    }

    /**
     * @param feeGroupModel the feeGroupModel to set
     */
    public void setFeeGroupModel(DataModel<FeeGroup> feeGroupModel) {
        this.feeGroupModel = feeGroupModel;
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setFeeGroup(new FeeGroup());
        setEditMode(null);
        return "";
    }

    /**
     * @return the feeGroup
     */
    public FeeGroup getFeeGroup() {
        if (feeGroup == null) {
            this.feeGroup = new FeeGroup();
        }
        return feeGroup;
    }

    /**
     * @param feeGroup the feeGroup to set
     */
    public void setFeeGroup(FeeGroup feeGroup) {
        this.feeGroup = feeGroup;
    }

    /**
     * @return the statutory
     */
    public boolean isStatutory() {
        return statutory;
    }

    /**
     * @param statutory the statutory to set
     */
    public void setStatutory(boolean statutory) {
        this.statutory = statutory;
    }

    /**
     * @return the gradeLevelId
     */
    public Integer getGradeLevelId() {
        return gradeLevelId;
    }

    /**
     * @param gradeLevelId the gradeLevelId to set
     */
    public void setGradeLevelId(Integer gradeLevelId) {
        this.gradeLevelId = gradeLevelId;
    }

    /**
     * @return the feeTypeCode
     */
    public Integer getFeeTypeCode() {
        return feeTypeCode;
    }

    /**
     * @param feeTypeCode the feeTypeCode to set
     */
    public void setFeeTypeCode(Integer feeTypeCode) {
        this.feeTypeCode = feeTypeCode;
    }

    /**
     *
     */
    private void doFindFeeGroups() {
        FeeGroupQueryCriteria criteria = new FeeGroupQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        if (this.gradeLevelId != null && this.gradeLevelId != 0) {
            criteria.setGradeLevelId(gradeLevelId);
        }
        if (this.statutory) {
            criteria.setStatutory();
        }
        if (this.feeTypeCode != null && this.feeTypeCode != 0) {
            criteria.setFeeTypeCode(this.feeTypeCode);
        }
        this.feeGroupModel = new ListDataModel<>(
                financeSetupService.findFeeGroups(criteria));
    }
}
