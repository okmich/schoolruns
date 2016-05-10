/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.setting;

import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Classroom;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
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
public class ClassroomBean extends _BaseBean {

    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    private Classroom classroom = new Classroom();
    private DataModel<Classroom> classroomModel;

    /**
     * Creates a new instance of ClassroomBean
     */
    public ClassroomBean() {
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
    public String saveClassroom() {
        try {
            getClassroom().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getClassroom().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
            Classroom _classroom = schoolService.saveClassroom(getClassroom());
            ((List<Classroom>) getClassroomModel().getWrappedData()).add(_classroom);
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(ClassroomBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
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
        getClassroom().setModifiedTime(new Date());
        getClassroom().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getClassroom().setStatus(CommonConstants.STATUS_ACTIVE);
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setClassroom(new Classroom());
        setEditMode(null);
        return "";
    }

    /**
     * @return the classroom
     */
    public Classroom getClassroom() {
        if (this.classroom == null) {
            this.classroom = new Classroom();
        }
        return classroom;
    }

    /**
     * @param classroom the classroom to set
     */
    public void setClassroom(Classroom classroom1) {
        this.classroom = classroom1;
    }

    /**
     * @return the classroomModel
     */
    public DataModel<Classroom> getClassroomModel() {
        try {
            classroomModel = new ListDataModel<Classroom>(
                    schoolService.findSchoolClassrooms(schoolSessionBean.getSchool().getSchoolId()));
        } catch (Exception ex) {
            Logger.getLogger(ClassroomBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return classroomModel;
    }

    /**
     * @param classroomModel the classroomModel to set
     */
    public void setClassroomModel(DataModel<Classroom> classroomModel) {
        this.setClassroomModel(classroomModel);
    }
}
