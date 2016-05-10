/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.setting;

import com.okmich.schoolruns.common.entity.Club;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
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
public class ClubBean extends _BaseBean {

    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    private Club club;
    private DataModel<Club> clubModel;

    /**
     * Creates a new instance of ClubBean
     */
    public ClubBean() {
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
    public String saveSchoolClub() {
        try {
            getClub().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getClub().setSchool(schoolSessionBean.getSchool());
            Club _club = schoolService.saveClub(getClub());
            ((List<Club>) getClubModel().getWrappedData()).add(_club);
            if (!schoolSessionBean.getSchoolClubs().contains(_club)) {
                schoolSessionBean.getSchoolClubs().add(_club);
            }
            clearForm();
            return "";
        } catch (Exception ex) {
            Logger.getLogger(ClubBean.class.getName()).log(Level.SEVERE, null, ex);
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
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setClub(null);
        setEditMode(null);
        return "";
    }

    /**
     * @return the club
     */
    public Club getClub() {
        if (this.club == null) {
            this.club = new Club();
        }
        return club;
    }

    /**
     * @param club the club1 to set
     */
    public void setClub(Club club1) {
        this.club = club1;
    }

    /**
     * @return the clubModel
     */
    public DataModel<Club> getClubModel() {
        try {
            clubModel = new ListDataModel<>(
                    schoolService.findClubs(schoolSessionBean.getSchool().getSchoolId()));
        } catch (Exception ex) {
            Logger.getLogger(ClubBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return clubModel;
    }

    /**
     * @param clubModel the clubModel to set
     */
    public void setClubModel(DataModel<Club> clubModel) {
        this.setClubModel(clubModel);
    }
}
