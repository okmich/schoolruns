/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.student;

import com.okmich.common.entity.criteria.WCString;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.media.service.data.ImageFile;
import com.okmich.schoolruns.school.service.SchoolStudentService;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.school.service.repo.SchoolStudentQueryCriteria;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.ExcelFileImportBean;
import com.okmich.schoolruns.web.common.bean.PhotoUploadBean;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.common.notification.MessageGenerator;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class SchoolStudentBean extends _BaseBean {

    private final Logger LOG = Logger.getLogger(SchoolStudentBean.class.getName());
    @ManagedProperty("#{excelFileImportBean}")
    private ExcelFileImportBean excelFileImportBean;
    @ManagedProperty("#{schoolStudentService}")
    private SchoolStudentService schoolStudentService;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{messageGenerator}")
    private MessageGenerator messageGenerator;
    @ManagedProperty("#{photoUploadBean}")
    private PhotoUploadBean photoUploadBean;
    private SchoolStudentData schoolStudentData;
    private SchoolClass schoolClass;
    private Parent parent;
    private Integer gradeLevel;
    private String gender;
    private String surname;
    private String firstname;
    private Integer schoolClassId;
    private Integer yearId;
    private String admissionNo;

    /**
     * Creates a new instance of SchoolStudentBean
     */
    public SchoolStudentBean() {
    }

    /**
     * @param schoolStudentService the schoolStudentService to set
     */
    public void setSchoolStudentService(SchoolStudentService schoolStudentService) {
        this.schoolStudentService = schoolStudentService;
    }

    /**
     * @param excelFileImportBean1 the excelFileImportBean to set
     */
    public void setExcelFileImportBean(ExcelFileImportBean excelFileImportBean1) {
        this.excelFileImportBean = excelFileImportBean1;
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
     * @param messageGenerator the messageGenerator1 to set
     */
    public void setMessageGenerator(MessageGenerator messageGenerator1) {
        this.messageGenerator = messageGenerator1;
    }

    /**
     * @param photoUploadBean1 the photoUploadBean to set
     */
    public void setPhotoUploadBean(PhotoUploadBean photoUploadBean1) {
        this.photoUploadBean = photoUploadBean1;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean1) {
        this.userLoginSessionBean = userLoginSessionBean1;
    }

    /**
     * @return the schoolStudentData
     */
    public SchoolStudentData getSchoolStudentData() {
        if (schoolStudentData == null) {
            this.schoolStudentData = new SchoolStudentData();
        }
        return schoolStudentData;
    }

    /**
     * @param schoolStudentData the schoolStudentData to set
     */
    public void setSchoolStudentData(SchoolStudentData schoolStudentData) {
        this.schoolStudentData = schoolStudentData;
    }

    /**
     * @return the schoolClass
     */
    public SchoolClass getSchoolClass() {
        if (schoolClass == null) {
            this.schoolClass = new SchoolClass();
        }
        return schoolClass;
    }

    /**
     * @param schoolClass the schoolClass to set
     */
    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    /**
     * @return the parent
     */
    public Parent getParent() {
        if (parent == null) {
            this.parent = new Parent();
        }
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Parent parent) {
        this.parent = parent;
    }

    /**
     * @return the gradeLevel
     */
    public Integer getGradeLevel() {
        return gradeLevel;
    }

    /**
     * @param gradeLevel the gradeLevel to set
     */
    public void setGradeLevel(Integer gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the schoolClassId
     */
    public Integer getSchoolClassId() {
        return schoolClassId;
    }

    /**
     * @return the yearId
     */
    public Integer getYearId() {
        return yearId;
    }

    /**
     * @param yearId the yearId to set
     */
    public void setYearId(Integer yearId) {
        this.yearId = yearId;
    }

    /**
     * @param schoolClassId the schoolClassId to set
     */
    public void setSchoolClassId(Integer schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    /**
     * @return the admissionNo
     */
    public String getAdmissionNo() {
        return admissionNo;
    }

    /**
     * @param admissionNo the admissionNo to set
     */
    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    public String createSchoolStudent() {
        //set the fields
        getSchoolStudentData().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getSchoolStudentData().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        getSchoolStudentData().setSchoolYearId(
                schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
        try {
            SchoolStudentData _data = schoolStudentService.createSchoolStudent(getSchoolStudentData());

            return "/schooluser/student/studentsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String createSchoolStudentWithParent() {
        //set the fields
        getSchoolStudentData().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getSchoolStudentData().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        getSchoolStudentData().setSchoolYearId(
                schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
        try {
            schoolStudentService.createSchoolStudent(getSchoolStudentData(), getParent());
            //send message to parent
            new Thread(new Runnable() {
                @Override
                public void run() {
                    messageGenerator.sendPostParentCreationMessage(getParent(),
                            schoolSessionBean.getSchool());
                }
            }).start();
            return "/schooluser/student/studentsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String createSchoolStudents() {
        try {
            //set the dependent variable
            excelFileImportBean.setSchool(schoolSessionBean.getSchool());
            excelFileImportBean.setSchoolCalendarData(schoolSessionBean.getSchoolCalendarData());
            //reshape the uploaded data
            List<SchoolStudentData> schoolStudents =
                    excelFileImportBean.reshapeToSchoolStudent();
            schoolStudentService.createSchoolStudents(schoolStudents);
            return "/schooluser/student/studentsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String bulkCreateSchoolStudentWithParents() {
        try {
            //set the dependent variable
            excelFileImportBean.setSchool(schoolSessionBean.getSchool());
            excelFileImportBean.setSchoolCalendarData(schoolSessionBean.getSchoolCalendarData());
            //reshape the uploaded data
            final List[] sobjects =
                    excelFileImportBean.reshapeToSchoolStudentWithParent();
            final List<Parent> parents = (List<Parent>) sobjects[1];
            schoolStudentService.createSchoolStudentsWithParents(
                    (List<SchoolStudentData>) sobjects[0], parents);
            //send mail to parents
            messageGenerator.sendPostParentCreationMessage(parents,
                    schoolSessionBean.getSchool());
            return "/schooluser/student/studentsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String findSchoolStudents() {
        try {
            List<SchoolStudentData> schoolStudentList =
                    schoolStudentService.findSchoolStudents(buildQueryCriteria());
            sessionBean.setSchoolStudentModel(
                    new ListDataModel<>(schoolStudentList));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String saveSchoolStudent() {
        sessionBean.getSchoolStudentData().setModifiedBy(
                userLoginSessionBean.getUserLogin().getUsername());
        try {
            schoolStudentService.saveSchoolStudent(sessionBean.getSchoolStudentData());
            return "/schooluser/student/studentsearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findSchoolStudent() {
        if (getSchoolStudentData().getSchoolStudentId() != null) {
            sessionBean.setEditMode(FacesUtil.getRequestParameter("editMode"));
            sessionBean.setSchoolStudentData(getSchoolStudentData());
            return "/schooluser/student/studentdetails";
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String doSchoolStudentDone() {
        sessionBean.setSchoolStudentData(null);
        return "/schooluser/student/studentsearch";
    }

    /**
     *
     * @return
     */
    public String prepareNewProfilePicture() {
        photoUploadBean.clear();
        photoUploadBean.setTag("STUDENT");
        photoUploadBean.setCallingPage("/schooluser/student/studentdetails");
        photoUploadBean.setPageTitle("Upload a new profile picture for "
                + sessionBean.getSchoolStudentData().getFullname());

        return "/schooluser/common/pictureupload";
    }

    /**
     *
     * @return
     */
    public String addProfilePicToStudent() {
        try {
            if (schoolSessionBean.getSchoolPref().getPicAlbumId() == null
                    || schoolSessionBean.getSchoolPref().getPicAlbumId().isEmpty()) {
                throw new Exception("ERROR_NO_ALBUM_CREATED");
            }
            ImageFile imageFile = new ImageFile();
            imageFile.setTitle(sessionBean.getSchoolStudentData().getFullname());
            imageFile.setDescription("Profile picture for " + imageFile.getTitle());

            String profilePicUrl = photoUploadBean.saveImageFile(imageFile);
            sessionBean.getSchoolStudentData().setPictureUrl(profilePicUrl);
            SchoolStudentData ssd = sessionBean.getSchoolStudentData();
            ssd.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());

            //save this to database
            schoolStudentService.addStudentProfilePicture(ssd);
            return "/schooluser/student/studentdetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return SchoolStudentQueryCriteria
     */
    private SchoolStudentQueryCriteria buildQueryCriteria() {
        SchoolStudentQueryCriteria criteria = new SchoolStudentQueryCriteria();
        //criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        criteria.setSchoolYearId(schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
        if (getGradeLevel() != null && getGradeLevel() != 0) {
            criteria.setGradeLevelId(getGradeLevel());
        }
        if (getGender() != null && !getGender().trim().isEmpty()) {
            criteria.setGender(getGender());
        }
        if (getSurname() != null && !getSurname().isEmpty()) {
            criteria.setSurname(getSurname(), WCString.LIKE);
        }
        if (getFirstname() != null && !getFirstname().isEmpty()) {
            criteria.setFirstname(getFirstname(), WCString.LIKE);
        }
        if (getSchoolClassId() != null && getSchoolClassId() != 0) {
            criteria.setSchoolClassId(getSchoolClassId());
        }
        if (getAdmissionNo() != null && !getAdmissionNo().trim().isEmpty()) {
            criteria.setRegistrationNo(getAdmissionNo(), WCString.LIKE);
        }
        return criteria;
    }

    /**
     *
     * @param _data
     */
    private void addSchoolStudentDataToModel(SchoolStudentData _data) {
        Object wrappedData = sessionBean.getSchoolStudentModel();
        List<SchoolStudentData> _dataList;
        if (wrappedData == null) {
            _dataList = new ArrayList<>();
        } else {
            _dataList = (List<SchoolStudentData>) wrappedData;
        }
        if (_dataList.isEmpty()) {
            _dataList.add(_data);
        } else {
            _dataList.add(0, _data);
        }
        sessionBean.getSchoolStudentModel().setWrappedData(_dataList);
    }
}
