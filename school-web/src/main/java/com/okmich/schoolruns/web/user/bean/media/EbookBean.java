/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.media;

import com.okmich.common.entity.criteria.OrderClause;
import com.okmich.common.entity.criteria.WCString;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Ebook;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.school.service.repo.EbookQueryCriteria;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class EbookBean extends _BaseBean {

    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private Ebook ebook;
    private String title;
    private String year;
    private String staff;
    private String type;
    private boolean publicized;
    private Integer subjectId;
    private Integer sectionId;
    private UploadedFile uploadedFile;
    private String link;
    private boolean fileUpload;
    private static final String ONLINE_RESOURCE = "ONLINE RES.";

    /**
     * Creates a new instance of EbookBean
     */
    public EbookBean() {
    }

    public String saveEbook() {
        getEbook().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getEbook().setCreator(userLoginSessionBean.getUserLogin().getTitle());
        getEbook().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        if (fileUpload) {//we have an upload to make
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "",
                    "School Storage not Yet setup"));
            return "";
        } else {
            getEbook().setFileType("URL RESOURCE");
            getEbook().setSourceUrl(getLink());
        }
        try {
            //do a hard code for now
            Ebook _ebook = schoolService.saveEbook(getEbook());

            return "/schooluser/media/elibrarysearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String findEbooks() {
        try {
            List<Ebook> ebooks =
                    schoolService.findEbooks(buildQueryCriteria());
            sessionBean.setEbookModel(
                    new ListDataModel<>(ebooks));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String findEbook() {
        Integer ebookId = Integer.parseInt(FacesUtil.getRequestParameter("ebookId"));
        try {
            Ebook _ebook = schoolService.findEbook(ebookId);
            setEbook(_ebook);
            setLink(_ebook.getSourceUrl());
            setFileUpload(false);
            sessionBean.setEditMode(FacesUtil.getRequestParameter("viewMode"));
            return "/schooluser/media/elibrarydetails";
        } catch (Exception ex) {
            processException(ex);
            return "";
        }
    }

    /**
     *
     * @param event
     */
    public void changeFileTypeEvent(ValueChangeEvent event) {
        HtmlSelectOneMenu menu = (HtmlSelectOneMenu) event.getComponent();
        Object _typeValue = menu.getValue();
        if (_typeValue != null) {
            String value = _typeValue.toString();
            this.fileUpload = !value.equals(ONLINE_RESOURCE);
        }
    }

    /**
     * @param schoolService the schoolService to set
     */
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    /**
     * @param sessionBean the sessionBean to set
     */
    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
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
     * @return the ebook
     */
    public Ebook getEbook() {
        if (this.ebook == null) {
            this.ebook = new Ebook();
        }
        return ebook;
    }

    /**
     * @param ebook the ebook to set
     */
    public void setEbook(Ebook ebook) {
        this.ebook = ebook;
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
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the staff
     */
    public String getStaff() {
        return staff;
    }

    /**
     * @param staff the staff to set
     */
    public void setStaff(String staff) {
        this.staff = staff;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the publicized
     */
    public boolean isPublicized() {
        return publicized;
    }

    /**
     * @param publicized the publicized to set
     */
    public void setPublicized(boolean publicized) {
        this.publicized = publicized;
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

    /**
     * @return the sectionId
     */
    public Integer getSectionId() {
        return sectionId;
    }

    /**
     * @param sectionId the sectionId to set
     */
    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    /**
     *
     * @return
     */
    public String retToSearch() {
        sessionBean.setEditMode(null);
        return "/schooluser/media/elibrarysearch";
    }

    /**
     *
     * @return
     */
    private EbookQueryCriteria buildQueryCriteria() {
        EbookQueryCriteria criteria = new EbookQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        if (this.isPublicized()) {
            criteria.setPublicized();
        }
        if (this.subjectId != null && this.subjectId != 0) {
            criteria.setSubjectId(subjectId);
        }
        if (this.sectionId != null && this.sectionId != 0) {
            criteria.setSectionId(sectionId);
        }
        if (this.year != null && !this.year.isEmpty()) {
            criteria.setYear(year);
        }
        if (this.staff != null && !this.staff.trim().isEmpty()) {
            criteria.setCreator(staff, WCString.LIKE);
        }
        if (this.title != null && !this.title.trim().isEmpty()) {
            criteria.setTitle(title, WCString.LIKE);
        }
        if (this.type != null && !this.type.isEmpty()) {
            criteria.setType(type);
        }
        List<OrderClause> vec = new ArrayList<>(1);
        vec.add(new OrderClause(EbookQueryCriteria.title));
        criteria.setOrderByColumn(vec);

        return criteria;
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
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the fileUpload
     */
    public boolean isFileUpload() {
        return fileUpload;
    }

    /**
     * @param fileUpload the fileUpload to set
     */
    public void setFileUpload(boolean fileUpload) {
        this.fileUpload = fileUpload;
    }
}
