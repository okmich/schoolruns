/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.bean;

import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.service.CommonService;
import com.okmich.schoolruns.media.service.MediaServiceFactory;
import com.okmich.schoolruns.media.service.data.ImageFile;
import com.okmich.schoolruns.web.admin.bean.SchoolRegBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Michael
 */
@ManagedBean
@SessionScoped
public class PhotoUploadBean extends _BaseBean {

    private static final Logger LOG = Logger.getLogger(PhotoUploadBean.class.getName());
    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    private UploadedFile uploadedFile;
    private String pageTitle;
    private String callingPage;
    private String tag;

    /**
     * Creates a new instance of ExcelFileImportBean
     */
    public PhotoUploadBean() {
    }

    /**
     * @param commonService the commonService to set
     */
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     * @param schoolSessionBean the schoolSessionBean to set
     */
    public void setSchoolSessionBean(SchoolSessionBean schoolSessionBean) {
        this.schoolSessionBean = schoolSessionBean;
    }

    /**
     * @return the uploadedFile
     */
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    /**
     * @param uploadedFile the uploadedFile to set
     */
    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    /**
     * @return the pageTitle
     */
    public String getPageTitle() {
        return pageTitle;
    }

    /**
     * @param pageTitle the pageTitle to set
     */
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    /**
     * @return the callingPage
     */
    public String getCallingPage() {
        return callingPage;
    }

    /**
     * @param callingPage the callingPage to set
     */
    public void setCallingPage(String callingPage) {
        this.callingPage = callingPage;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * clears out all variables
     */
    public void clear() {
        this.callingPage = null;
        this.pageTitle = null;
        this.tag = null;
        this.uploadedFile = null;
    }

    /**
     * uploads a picture to the school's photo album in the schoolruns.okmich
     * google plus account and returns the url
     *
     * @param uploadedFile
     * @param school
     * @return String
     *
     */
    public String saveImageFile(ImageFile imageFile) {
        try {
            //save the logo file on our media service
            imageFile.setAlbumId(schoolSessionBean.getSchoolPref().getPicAlbumId());
            imageFile.setClient("schoolruns.com");
            imageFile.setContentType(uploadedFile.getContentType());
            imageFile.setInputStream(uploadedFile.getInputstream());
            imageFile.setUserId(commonService.findCtrlParameterValue(CommonConstants.CTRL_PARAM_PICASA_SERVICE_USERID));

            imageFile = MediaServiceFactory.getImageService(
                    commonService.findCtrlParameterValue(CommonConstants.CTRL_PARAM_PICASA_SERVICE_USERNAME),
                    commonService.findCtrlParameterValue(CommonConstants.CTRL_PARAM_PICASA_SERVICE_PASSWD))
                    .addPhoto(imageFile);

            return imageFile.getUrl();
        } catch (Exception ex) {
            Logger.getLogger(SchoolRegBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
