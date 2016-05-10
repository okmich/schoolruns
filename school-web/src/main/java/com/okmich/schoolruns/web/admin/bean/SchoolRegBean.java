/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.admin.bean;

import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.calendar.service.SchoolCalendarService;
import com.okmich.schoolruns.calendar.service.data.SchoolCalendarData;
import com.okmich.schoolruns.common.entity.Facility;
import com.okmich.schoolruns.common.entity.Module;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.School;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.common.entity.SchoolPref;
import com.okmich.schoolruns.common.entity.SchoolSection;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.common.entity.Weekday;
import com.okmich.schoolruns.common.service.CommonService;
import com.okmich.schoolruns.employee.service.EmployeeService;
import com.okmich.schoolruns.employee.service.data.EmployeeData;
import com.okmich.schoolruns.media.service.MediaServiceFactory;
import com.okmich.schoolruns.media.service.data.ImageFile;
import com.okmich.schoolruns.media.service.data.PhotoAlbum;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.school.service.SchoolStudentService;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.school.service.repo.SchoolClassQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolSubjectQueryCriteria;
import com.okmich.schoolruns.student.service.StudentService;
import com.okmich.schoolruns.web.common.ThemeUtil;
import com.okmich.schoolruns.web.common.bean.ExcelFileImportBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.common.notification.MessageGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Michael
 */
@ManagedBean
@SessionScoped
public class SchoolRegBean extends _BaseBean {

    private static final Logger LOG = Logger.getLogger(SchoolRegBean.class.getName());
    @ManagedProperty("#{commonService}")
    private CommonService commonService;
    @ManagedProperty("#{employeeService}")
    private EmployeeService employeeService;
    @ManagedProperty("#{excelFileImportBean}")
    private ExcelFileImportBean excelFileImportBean;
    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{schoolStudentService}")
    private SchoolStudentService schoolStudentService;
    @ManagedProperty("#{schoolCalendarService}")
    private SchoolCalendarService schoolCalendarService;
    @ManagedProperty("#{studentService}")
    private StudentService studentService;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{messageGenerator}")
    private MessageGenerator messageGenerator;
    private School school;
    private SchoolPref schoolPref;
    private SchoolClass schoolClass;
    private SchoolSection schoolSection;
    private SchoolSubject schoolSubject;
    private UploadedFile logoFile;
    private List<Module> moduleList;
    private List<Facility> facilityList;
    private List<Weekday> weekdayList;
    private List<SchoolSection> schoolSectionModel;
    private SchoolCalendarData schoolCalendarData;
    private DataModel<SchoolClass> schoolClassModel;
    private DataModel<SchoolSubject> schoolSubjectModel;
    private Integer gradeLevelId;
    private Integer subjectId;
    private static final List<String> imageTypes = new ArrayList<>();

    public SchoolRegBean() {
        imageTypes.add("image/bmp");
        imageTypes.add("image/gif");
        imageTypes.add("image/jpeg");
        imageTypes.add("image/jpg");
        imageTypes.add("image/png");
        imageTypes.add("image/tiff");
    }

    /**
     * @param employeeService1 the employeeService to set
     */
    public void setEmployeeService(EmployeeService employeeService1) {
        this.employeeService = employeeService1;
    }

    /**
     * @param excelFileImportBean the excelFileImportBean to set
     */
    public void setExcelFileImportBean(ExcelFileImportBean excelFileImportBean1) {
        this.excelFileImportBean = excelFileImportBean1;
    }

    /**
     * @param schoolCalendarService the schoolCalendarService to set
     */
    public void setSchoolCalendarService(SchoolCalendarService schoolCalendarService) {
        this.schoolCalendarService = schoolCalendarService;
    }

    /**
     * @param schoolService the schoolService to set
     */
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    /**
     * @param schoolStudentService1 the schoolStudentService to set
     */
    public void setSchoolStudentService(SchoolStudentService schoolStudentService1) {
        this.schoolStudentService = schoolStudentService1;
    }

    /**
     * @param studentService1 the studentService to set
     */
    public void setStudentService(StudentService studentService1) {
        this.studentService = studentService1;
    }

    /**
     * @param commonService the commonService to set
     */
    public void setCommonService(CommonService commonService1) {
        this.commonService = commonService1;
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean) {
        this.userLoginSessionBean = userLoginSessionBean;
    }

    /**
     * @param messageGenerator the messageGenerator to set
     */
    public void setMessageGenerator(MessageGenerator messageGenerator1) {
        this.messageGenerator = messageGenerator1;
    }

    /**
     *
     * @return
     */
    public String createSchool() {
        School _school = getSchool();
        _school.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        try {
            setSchool(schoolService.createSchool(_school));
            //initiatiate a mail to the schools admin
            messageGenerator.sendPostSchoolCreationMessage(getSchool());
            //if everything works fine here, we should move to the next page here
            return "/admin/_schoolreg/_schoolfacility";
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    public String saveSchoolFacilities() {
        try {
            LOG.log(Level.SEVERE, getFacilityList().toString());
            getSchool().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            schoolService.updateSchoolFacilities(getFacilityList(), getSchool());
            //if everything works fine, we should move to the next page here
            setSchoolPref(new SchoolPref(getSchool().getSchoolId()));
            getSchoolPref().setSubMaxScore(100);
            getSchoolPref().setFinalQuizWeight(25);
            return "/admin/_schoolreg/_schooluipref";
        } catch (Exception e) {
            processException(e);
        }
        return null;
    }

    public String saveSchoolPref() {
        SchoolPref _schoolPref = getSchoolPref();
        _schoolPref.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        _schoolPref.setSchoolId(getSchool().getSchoolId());
        try {
            if (getLogoFile() != null) {
                verifyImage(getLogoFile().getContentType());
                ImageFile _ImageFile = saveImageFile(getSchool(), getLogoFile());
                if (_ImageFile != null) { //null logg is accepted
                    getSchoolPref().setLogoUrl(_ImageFile.getUrl());
                }
            }
            if (_schoolPref.getPicAlbumId() == null) {
                String albumId = createSchoolPicAlbum(this.school);
                schoolPref.setPicAlbumId(albumId);
            }
            schoolService.saveSchoolPref(getSchoolPref());
            //if everything works fine, we should move to the next page here
            return "/admin/_schoolreg/_schoolmodule";
        } catch (Exception e) {
            processException(e);
        }
        return null;
    }

    public String saveSchoolModules() {
        try {
            LOG.log(Level.SEVERE, getModuleList().toString());
            getSchool().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            schoolService.assignModuleToSchool(getModuleList(), getSchool());
            //if everything works fine, we should move to the next page here
            return "/admin/_schoolreg/_academicyear";
        } catch (Exception e) {
            processException(e);
        }
        return null;
    }

    public String saveSchoolYearAndTerm() {
        try {
            getSchoolCalendarData().setStatus(CommonConstants.STATUS_ACTIVE);
            getSchoolCalendarData().setModifiedBy(
                    userLoginSessionBean.getUserLogin().getUsername());
            //create the new calendar features
            setSchoolCalendarData(
                    schoolCalendarService.createKickOffYearTerm(schoolCalendarData));
            //if everything works fine, we should move to the next page here
            return "/admin/_schoolreg/_schoolsections";
        } catch (Exception e) {
            processException(e);
        }
        return null;
    }

    /**
     * ******************************************************************************
     * *****************************SCHOOL CLASSES SETUP BEGINS
     * ******************************************************************************
     */
    /**
     * @return
     */
    public String saveSchoolClass() {
        try {
            getSchoolClass().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getSchoolClass().setSchoolId(getSchool().getSchoolId());
            SchoolClass _schoolClass = schoolService.saveSchoolClass(getSchoolClass());
            ((List<SchoolClass>) getSchoolClassModel().getWrappedData()).add(_schoolClass);
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
        excelFileImportBean.setSchool(getSchool());
        try {
            List<SchoolClass> schoolClasses = excelFileImportBean.reshapeToSchoolClass();
            //call service method to save
            schoolService.saveSchoolClasses(schoolClasses);
            setSchoolClassModel(null);
            clearForm();
            //if everything works fine, we should move to the next page here
            return "/admin/_schoolreg/_schoolsubject";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * @return the schoolClassModel
     */
    public DataModel<SchoolClass> getSchoolClassModel() {
        try {
            schoolClassModel = new ListDataModel<>(
                    schoolService.findSchoolClasses(buildClassQueryCriteria()));
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

    private SchoolClassQueryCriteria buildClassQueryCriteria() {
        SchoolClassQueryCriteria criteria = new SchoolClassQueryCriteria();
        //set school
        criteria.setSchoolId(getSchool().getSchoolId());
        if (this.getGradeLevelId() != null && this.getGradeLevelId() != 0) {
            criteria.setGradeLevelId(this.getGradeLevelId());
        }

        return criteria;
    }

    /**
     * ******************************************************************************
     * **************************SCHOOL CLASSES SETUP ENDS*******************
     * ******************************************************************************
     */
    /**
     * ******************************************************************************
     * *****************************SCHOOL SECTIONS SETUP BEGINS
     * ******************************************************************************
     */
    /**
     * @return
     */
    public String saveSchoolSection() {
        try {
            getSchoolSection().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getSchoolSection().setSchool(getSchool());
            schoolService.saveSchoolSection(getSchoolSection());
            clearForm();
            return "";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * @return the schoolSectionModel
     */
    public List<SchoolSection> getSchoolSections() {
        try {
            schoolSectionModel =
                    schoolService.findSchoolSections(getSchool().getSchoolId());
        } catch (Exception ex) {
            processException(ex);
        }
        return schoolSectionModel;
    }

    /**
     * ******************************************************************************
     * **************************SCHOOL SECTIONS SETUP ENDS*******************
     * ******************************************************************************
     */
    /**
     *
     * @return
     */
    public String clearForm() {
        setSchoolClass(null);
        setSchoolSection(null);
        setSchoolSubject(null);
        setEditMode(null);
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
     * ******************************************************************************
     * **************************SCHOOL SUBJECTS SETUP STARTS**************
     * ******************************************************************************
     */
    /**
     * @return
     */
    public String saveSchoolSubject() {
        try {
            getSchoolSubject().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getSchoolSubject().setSchoolId(getSchool().getSchoolId());
            SchoolSubject _schoolSubject = schoolService.saveSchoolSubject(getSchoolSubject());
            ((List<SchoolSubject>) getSchoolSubjectModel().getWrappedData()).add(_schoolSubject);
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
        excelFileImportBean.setSchool(getSchool());
        try {
            List<SchoolSubject> schoolSubjects = excelFileImportBean.reshapeToSchoolSubjects();
            schoolService.saveSchoolSubjects(schoolSubjects);
            setSchoolSubjectModel(null);
            clearForm();
            //if everything works fine, we should move to the next page here
            return "/admin/_schoolreg/_schoolstaff";
        } catch (Exception ex) {
            processException(ex);
        }
        return null;
    }

    /**
     * @return the schoolSubjectModel
     */
    public DataModel<SchoolSubject> getSchoolSubjectModel() {
        try {
            this.schoolSubjectModel = new ListDataModel<>(
                    schoolService.findSchoolSubjects(buildSubjectQueryCriteria()));
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

    private SchoolSubjectQueryCriteria buildSubjectQueryCriteria() {
        SchoolSubjectQueryCriteria criteria = new SchoolSubjectQueryCriteria();
        //set school
        criteria.setSchoolId(getSchool().getSchoolId());
        if (this.gradeLevelId != null) {
            criteria.setGradeLevelId(this.gradeLevelId);
        }
        if (this.getSubjectId() != null) {
            criteria.setSubjectId(this.getSubjectId());
        }

        return criteria;
    }

    /**
     * ******************************************************************************
     * *************************SCHOOL SUBJECTS SETUP ENDS*********************
     * ******************************************************************************
     */
    /**
     * ******************************************************************************
     * *************************SCHOOL DAYS SETUP ENDS*********************
     * ******************************************************************************
     */
    /**
     *
     * @return
     */
    public String saveSchoolStaffs() {
        try {
            //set the dependent variable
            excelFileImportBean.setSchool(getSchool());
            //process
            List<EmployeeData> employees =
                    excelFileImportBean.reshapeToEmployeeData();
            //call the business servers
            employeeService.createEmployees(employees);
            //if everything works fine, we should move to the next page here
            return "/admin/_schoolreg/_schoolparents";
        } catch (Exception e) {
            processException(e);
        }
        return null;
    }

    public String saveSchoolStudents() {
        try {
            //set the dependent variable
            excelFileImportBean.setSchool(getSchool());
            excelFileImportBean.setSchoolCalendarData(getSchoolCalendarData());
            //save the students
            List<SchoolStudentData> schoolStudents =
                    excelFileImportBean.reshapeToSchoolStudent();
            schoolStudentService.createSchoolStudents(schoolStudents);
            //if everything works fine, we should move to the next page here
            return "/admin/_schoolreg/_finish";
        } catch (Exception e) {
            processException(e);
        }
        return null;
    }

    public String saveParents() {
        try {
            //save the parent records
            final List<Parent> parents =
                    excelFileImportBean.reshapeToParent();
            studentService.createParents(parents);
            //send message to parent
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (Parent _parent : parents) {
                        messageGenerator.sendPostParentCreationMessage(_parent,
                                getSchool());
                    }
                }
            }).start();
            //if everything works fine, we should move to the next page here
            return "/admin/_schoolreg/_schoolstudents";
        } catch (Exception e) {
            processException(e);
        }
        return null;
    }

    public String finish() {
        clearForm();
        setSchool(null);
        setSchoolPref(null);
        setLogoFile(null);
        return "/admin/school/schoolsearch";
    }

    /**
     * uploads school logo to the logo folder in the photo service of the
     * schoolruns.okmich google plus account
     *
     * @param uploadedFile
     * @param school
     * @return ImageFile
     *
     */
    public ImageFile saveImageFile(School school, UploadedFile uploadedFile) {
        try {
            //save the logo file on our media service
            ImageFile imageFile = new ImageFile();
            imageFile.setAlbumId(commonService.findCtrlParameterValue(CommonConstants.CTRL_PARAM_DEFAULT_PICASA_LOGO_FOLDER));
            imageFile.setClient("schoolruns.com");
            imageFile.setContentType(uploadedFile.getContentType());
            imageFile.setDescription(school.getName() + " logo");
            imageFile.setInputStream(uploadedFile.getInputstream());
            imageFile.setTitle("School Logo");
            imageFile.setUserId(commonService.findCtrlParameterValue(CommonConstants.CTRL_PARAM_PICASA_SERVICE_USERID));

            return MediaServiceFactory.getImageService(
                    commonService.findCtrlParameterValue(CommonConstants.CTRL_PARAM_PICASA_SERVICE_USERNAME),
                    commonService.findCtrlParameterValue(CommonConstants.CTRL_PARAM_PICASA_SERVICE_PASSWD))
                    .addPhoto(imageFile);
        } catch (Exception ex) {
            Logger.getLogger(SchoolRegBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * creates a picture album for the school in the schoolruns.okmich google
     * plus account. The album will be used to store school pictures like
     * passports of students and staff
     *
     * @param school
     * @return String - the id for the album created
     */
    public String createSchoolPicAlbum(School school) {
        try {
            //save the logo file on our media service
            PhotoAlbum photoAlbum = new PhotoAlbum();
            photoAlbum.setDescription("Image and Passport Album for " + school.getName());
            photoAlbum.setTitle(school.getName());
            photoAlbum.setUserId(
                    commonService.findCtrlParameterValue(
                    CommonConstants.CTRL_PARAM_PICASA_SERVICE_USERID));

            photoAlbum = MediaServiceFactory.getImageService(
                    commonService.findCtrlParameterValue(CommonConstants.CTRL_PARAM_PICASA_SERVICE_USERNAME),
                    commonService.findCtrlParameterValue(CommonConstants.CTRL_PARAM_PICASA_SERVICE_PASSWD))
                    .createPhotoAlbum(photoAlbum);
            return photoAlbum.getAlbumId();
        } catch (Exception ex) {
            Logger.getLogger(SchoolRegBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * @return the school
     */
    public School getSchool() {
        if (school == null) {
            setSchool(new School());
        }
        return school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(School school) {
        this.school = school;
    }

    /**
     * @return the schoolPref
     */
    public SchoolPref getSchoolPref() {
        if (schoolPref == null) {
            setSchoolPref(new SchoolPref(getSchool().getSchoolId()));
        }
        return schoolPref;
    }

    /**
     * @param schoolPref the schoolPref to set
     */
    public void setSchoolPref(SchoolPref schoolPref) {
        this.schoolPref = schoolPref;
    }

    /**
     * @return the logoFile
     */
    public UploadedFile getLogoFile() {
        return logoFile;
    }

    /**
     * @param logoFile the logoFile to set
     */
    public void setLogoFile(UploadedFile logoFile) {
        this.logoFile = logoFile;
    }

    /**
     *
     */
    public Map<String, String> getAvailableThemes() {
        return ThemeUtil.getThemeMap();
    }

    /**
     * @return the moduleList
     */
    public List<Facility> getFacilityList() {
        if (facilityList == null) {
            facilityList = new ArrayList<>();
        }
        return facilityList;
    }

    /**
     * @param facilityList the facilityList to set
     */
    public void setFacilityList(List<Facility> facilityList) {
        this.facilityList = facilityList;
    }

    /**
     * @return the moduleList
     */
    public List<Module> getModuleList() {
        if (moduleList == null) {
            moduleList = new ArrayList<>();
        }
        return moduleList;
    }

    /**
     * @param moduleList the moduleList to set
     */
    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    /**
     * @return the weekdayList
     */
    public List<Weekday> getWeekdayList() {
        if (weekdayList == null) {
            weekdayList = new ArrayList<>();
        }
        return weekdayList;
    }

    /**
     * @param weekdayList the weekdayList to set
     */
    public void setWeekdayList(List<Weekday> weekdayList) {
        this.weekdayList = weekdayList;
    }

    /**
     * @return the schoolCalendarData
     */
    public SchoolCalendarData getSchoolCalendarData() {
        if (schoolCalendarData == null) {
            this.schoolCalendarData = new SchoolCalendarData();
            this.schoolCalendarData.setSchoolId(getSchool().getSchoolId());
        }
        return schoolCalendarData;
    }

    /**
     * @param schoolCalendarData the schoolCalendarData to set
     */
    public void setSchoolCalendarData(SchoolCalendarData schoolCalendarData) {
        this.schoolCalendarData = schoolCalendarData;
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
     * @return the schoolSection
     */
    public SchoolSection getSchoolSection() {
        if (this.schoolSection == null) {
            this.schoolSection = new SchoolSection();
        }
        return schoolSection;
    }

    /**
     * @param schoolSection the schoolSection to set
     */
    public void setSchoolSection(SchoolSection schoolSection) {
        this.schoolSection = schoolSection;
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

    /**
     *
     * @param contentType
     */
    private void verifyImage(String contentType) throws Exception {
        if (contentType != null && !imageTypes.contains(contentType)) {
            throw new Exception("ERROR_INVALID_CONTENT_TYPE");
        }
    }
}
