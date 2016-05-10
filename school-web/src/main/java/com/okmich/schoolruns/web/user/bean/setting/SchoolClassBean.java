/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.setting;

import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.school.service.repo.SchoolClassQueryCriteria;
import com.okmich.schoolruns.web.common.bean.ExcelFileImportBean;
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
public class SchoolClassBean extends _BaseBean {

    @ManagedProperty("#{excelFileImportBean}")
    private ExcelFileImportBean excelFileImportBean;
    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    private SchoolClass schoolClass;
    private DataModel<SchoolClass> schoolClassModel;
    private Integer gradeLevelId;
    private Integer schoolSectionId;

    /**
     * Creates a new instance of SchoolClassBean
     */
    public SchoolClassBean() {
    }

    /**
     * @param excelFileImportBean the excelFileImportBean to set
     */
    public void setExcelFileImportBean(ExcelFileImportBean excelFileImportBean1) {
        this.excelFileImportBean = excelFileImportBean1;
    }

    /**
     * @param schoolService the schoolService to set
     */
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @return
     */
    public String saveSchoolClass() {
        try {
            getSchoolClass().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getSchoolClass().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
            SchoolClass _schoolClass = schoolService.saveSchoolClass(getSchoolClass());
            ((List<SchoolClass>) getSchoolClassModel().getWrappedData()).add(_schoolClass);
            if (!schoolSessionBean.getSchoolClasses().contains(_schoolClass)) {
                schoolSessionBean.getSchoolClasses().add(_schoolClass);
            }
            clearForm();
            return "";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * @return
     */
    public String saveSchoolClasses() {
        //set the dependent variable
        excelFileImportBean.setSchool(schoolSessionBean.getSchool());
        try {
            List<SchoolClass> schoolClasses = excelFileImportBean.reshapeToSchoolClass();
            //call the save method on the list
            schoolService.saveSchoolClasses(schoolClasses);
            setSchoolClassModel(null);
            clearForm();
            return "/schooluser/settings/schoolclasses";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForCreate() {
        setEditMode("CREATE");
        return "";
    }

    /**
     *
     * @return
     */
    public String prepareForModify() {
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setSchoolClass(null);
        setEditMode(null);
        return "";
    }

    /**
     * @return the schoolClass
     */
    public SchoolClass getSchoolClass() {
        if (this.schoolClass == null) {
            this.schoolClass = new SchoolClass();
        }
        return schoolClass;
    }

    /**
     * @param schoolClass the schoolClass to set
     */
    public void setSchoolClass(SchoolClass schoolClass1) {
        this.schoolClass = schoolClass1;
    }

    /**
     * @return the schoolClassModel
     */
    public DataModel<SchoolClass> getSchoolClassModel() {
        try {
            schoolClassModel = new ListDataModel<>(
                    schoolService.findSchoolClasses(buildQueryCriteria()));
        } catch (Exception ex) {
            processException(ex);
        }
        return schoolClassModel;
    }

    /**
     * @param schoolClassModel the schoolClassModel to set
     */
    public void setSchoolClassModel(DataModel<SchoolClass> schoolClassModel) {
        this.setSchoolClassModel(schoolClassModel);
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
    public void setGradeLevelId(Integer gradeLevel) {
        this.gradeLevelId = gradeLevel;
    }

    /**
     * @return the schoolSectionId
     */
    public Integer getSchoolSectionId() {
        return schoolSectionId;
    }

    /**
     * @param schoolSectionId the schoolSectionId to set
     */
    public void setSchoolSectionId(Integer schoolSectionId) {
        this.schoolSectionId = schoolSectionId;
    }

    private SchoolClassQueryCriteria buildQueryCriteria() {
        SchoolClassQueryCriteria criteria = new SchoolClassQueryCriteria();
        //set school
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        if (this.gradeLevelId != null && this.gradeLevelId != 0) {
            criteria.setGradeLevelId(this.gradeLevelId);
        }
        if (this.schoolSectionId != null && this.schoolSectionId != 0) {
            criteria.setSchoolSectionId(this.schoolSectionId);
        }

        return criteria;
    }
}
