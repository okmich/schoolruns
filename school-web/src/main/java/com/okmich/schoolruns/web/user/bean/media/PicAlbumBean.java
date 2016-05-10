/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.media;

import com.okmich.schoolruns.common.entity.PicAlbum;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class PicAlbumBean extends _BaseBean {

    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private PicAlbum photoAlbum;
    private String title;
    private Integer schoolYearId;

    /**
     * Creates a new instance of EbookBean
     */
    public PicAlbumBean() {
    }

    /**
     * @param schoolService the schoolService to set
     */
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @return the photoAlbum
     */
    public PicAlbum getPhotoAlbum() {
        return photoAlbum;
    }

    /**
     * @param photoAlbum the photoAlbum to set
     */
    public void setPhotoAlbum(PicAlbum photoAlbum) {
        this.photoAlbum = photoAlbum;
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

    /**
     * @return the schoolYearId
     */
    public Integer getSchoolYearId() {
        return schoolYearId;
    }

    /**
     * @param schoolYearId the schoolYearId to set
     */
    public void setSchoolYearId(Integer schoolYearId) {
        this.schoolYearId = schoolYearId;
    }

    public String savePicAlbum() {
        return null;
    }

    public String findPicAlbums() {
        try {
//            List<Ebook> ebooks =
//                    schoolService.findEbooks(buildQueryCriteria());
//            sessionBean.setEbookModel(
//                    new ListDataModel<>(ebooks));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String findPicAlbum() {
//        Integer ebookId = Integer.parseInt(FacesUtil.getRequestParameter("ebookId"));
//        try {
//            Ebook _ebook = schoolService.findEbook(ebookId);
//            setEbook(_ebook);
//            sessionBean.setEditMode(FacesUtil.getRequestParameter("viewMode"));
//            return "/schooluser/media/elibrarydetails";
//        } catch (Exception ex) {
//            processException(ex);
//            return "";
//        }
        return "";
    }
}
