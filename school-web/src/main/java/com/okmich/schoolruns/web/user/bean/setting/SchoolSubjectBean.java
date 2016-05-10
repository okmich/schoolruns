/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.setting;

import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.school.service.repo.SchoolSubjectQueryCriteria;
import com.okmich.schoolruns.web.common.bean.ExcelFileImportBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.staff.EmployeeBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.List;
import java.util.logging.Logger;
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
public class SchoolSubjectBean extends _BaseBean {

    private final Logger LOG = Logger.getLogger(EmployeeBean.class.getName());
    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{excelFileImportBean}")
    private ExcelFileImportBean excelFileImportBean;
    private SchoolSubject schoolSubject = new SchoolSubject();
    private DataModel<SchoolSubject> schoolSubjectModel;
    private Integer gradeLevelId;
    private Integer subjectId;

    /**
     * Creates a new instance of SchoolSubjectBean
     */
    public SchoolSubjectBean() {
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
     * @return
     */
    public String saveSchoolSubject() {
        try {
            getSchoolSubject().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getSchoolSubject().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
            SchoolSubject _schoolSubject = schoolService.saveSchoolSubject(getSchoolSubject());
            ((List<SchoolSubject>) getSchoolSubjectModel().getWrappedData()).add(_schoolSubject);
            if (!schoolSessionBean.getSchoolSubjects().contains(_schoolSubject)) {
                schoolSessionBean.getSchoolSubjects().add(_schoolSubject);
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
    public String saveSchoolSubjects() {
        //set the dependent variable
        excelFileImportBean.setSchool(schoolSessionBean.getSchool());
        try {
            List<SchoolSubject> schoolSubjects = excelFileImportBean.reshapeToSchoolSubjects();
            schoolService.saveSchoolSubjects(schoolSubjects);
            setSchoolSubjectModel(null);
            clearForm();
            return "/schooluser/settings/schoolsubject";
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
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        this.schoolSubject = new SchoolSubject();
        setEditMode(null);
        return "";
    }

    /**
     * @return the schoolSubject
     */
    public SchoolSubject getSchoolSubject() {
        if (this.schoolSubject == null) {
            this.schoolSubject = new SchoolSubject();
            this.schoolSubject.setCreditWeight((short) 1);
        }
        return schoolSubject;
    }

    /**
     * @param schoolSubject the schoolSubject to set
     */
    public void setSchoolSubject(SchoolSubject schoolSubject1) {
        this.schoolSubject = schoolSubject1;
    }

    /**
     * @return the schoolSubjectModel
     */
    public DataModel<SchoolSubject> getSchoolSubjectModel() {
        try {
            this.schoolSubjectModel = new ListDataModel<>(
                    schoolService.findSchoolSubjects(buildQueryCriteria()));
        } catch (Exception ex) {
            processException(ex);
        }
        return schoolSubjectModel;
    }

    /**
     * @param schoolSubjectModel the schoolSubjectModel to set
     */
    public void setSchoolSubjectModel(DataModel<SchoolSubject> schoolSubjectModel) {
        this.schoolSubjectModel = schoolSubjectModel;
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
     * @return the subjectId
     */
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     * @param subjectId the subjectId to set
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    private SchoolSubjectQueryCriteria buildQueryCriteria() {
        SchoolSubjectQueryCriteria criteria = new SchoolSubjectQueryCriteria();
        //set school
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        if (this.gradeLevelId != null && this.gradeLevelId != 0) {
            criteria.setGradeLevelId(this.gradeLevelId);
        }
        if (this.subjectId != null && this.subjectId != 0) {
            criteria.setSubjectId(this.subjectId);
        }

        return criteria;
    }
}
