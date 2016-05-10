/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.staff;

import com.okmich.common.entity.criteria.WCString;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.employee.service.EmployeeService;
import com.okmich.schoolruns.employee.service.data.EmployeeData;
import com.okmich.schoolruns.employee.service.repo.EmployeeQueryCriteria;
import com.okmich.schoolruns.media.service.data.ImageFile;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.ExcelFileImportBean;
import com.okmich.schoolruns.web.common.bean.PhotoUploadBean;
import com.okmich.schoolruns.web.common.bean.SessionBean;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.common.notification.MessageGenerator;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@RequestScoped
public class EmployeeBean extends _BaseBean {

    private final Logger LOG = Logger.getLogger(EmployeeBean.class.getName());
    @ManagedProperty("#{messageGenerator}")
    private MessageGenerator messageGenerator;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty("#{employeeService}")
    private EmployeeService employeeService;
    @ManagedProperty("#{excelFileImportBean}")
    private ExcelFileImportBean excelFileImportBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    @ManagedProperty("#{photoUploadBean}")
    private PhotoUploadBean photoUploadBean;
    private EmployeeData employeeData;
    private String surname;
    private String othernames;
    private String staffNumber;
    private String employeeStatus;
    private String employeeType;
    private String employeeCategory;
    private String status;
    private String gender;

    /**
     * Creates a new instance of EmployeeBean
     */
    public EmployeeBean() {
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
     * @param employeeService the employeeService to set
     */
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * @param excelFileImportBean the excelFileImportBean to set
     */
    public void setExcelFileImportBean(ExcelFileImportBean excelFileImportBean1) {
        this.excelFileImportBean = excelFileImportBean1;
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

    public String createEmployee() {
        //set the fields
        getEmployeeData().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getEmployeeData().setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        try {
            setEmployeeData(employeeService.createEmployee(getEmployeeData()));
            //send message to employee
            new Thread(new Runnable() {
                @Override
                public void run() {
                    messageGenerator.sendPostEmployeeCreationMessage(getEmployeeData());
                }
            }).start();
            return "/schooluser/staff/employeesearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String createEmployees() {
        //set the dependent variable
        excelFileImportBean.setSchool(schoolSessionBean.getSchool());
        try {
            final List<EmployeeData> employees =
                    excelFileImportBean.reshapeToEmployeeData();
            //call the business servers
            employeeService.createEmployees(employees);
            //send message to employees
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //send messages to all
                        messageGenerator.sendPostEmployeeCreationMessage(employees);
                    } catch (Exception e) {
                        processException(e);
                    }
                }
            }).start();
            return "/schooluser/staff/employeesearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String findEmployees() {
        try {
            List<EmployeeData> employeeList = employeeService.findEmployees(buildQueryCriteria());
            sessionBean.setEmployeeModel(new ListDataModel<>(employeeList));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String saveEmployee() {
        sessionBean.getEmployeeData().setModifiedBy(
                userLoginSessionBean.getUserLogin().getUsername());
        try {
            employeeService.saveEmployee(sessionBean.getEmployeeData());
            return "/schooluser/staff/employeesearch";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String findEmployee() {
        if (getEmployeeData().getEmployeeId() != null) {
            sessionBean.setEditMode(FacesUtil.getRequestParameter("editMode"));
            sessionBean.setEmployeeData(getEmployeeData());
            return "/schooluser/staff/employeedetails";
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String doEmployeeDone() {
        sessionBean.setEmployeeData(null);
        return "/schooluser/staff/employeesearch";
    }

    /**
     *
     * @return
     */
    public String prepareNewProfilePicture() {
        photoUploadBean.clear();
        photoUploadBean.setTag("EMPLOYEE");
        photoUploadBean.setCallingPage("/schooluser/staff/employeedetails");
        photoUploadBean.setPageTitle("Upload a new profile picture for "
                + sessionBean.getEmployeeData().getFullname());

        return "/schooluser/common/pictureupload";
    }

    /**
     *
     * @return
     */
    public String addProfilePicToEmployee() {
        try {
            if (schoolSessionBean.getSchoolPref().getPicAlbumId() == null
                    || schoolSessionBean.getSchoolPref().getPicAlbumId().isEmpty()) {
                throw new Exception("ERROR_NO_ALBUM_CREATED");
            }
            ImageFile imageFile = new ImageFile();
            imageFile.setTitle(sessionBean.getEmployeeData().getFullname());
            imageFile.setDescription("Profile picture for " + imageFile.getTitle());

            String profilePicUrl = photoUploadBean.saveImageFile(imageFile);
            sessionBean.getEmployeeData().setPictureUrl(profilePicUrl);
            EmployeeData data = sessionBean.getEmployeeData();
            data.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());

            //save this to database
            employeeService.addEmployeeProfilePicture(data);
            return "/schooluser/staff/employeedetails";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     * @return the employee
     */
    public EmployeeData getEmployeeData() {
        if (employeeData == null) {
            this.employeeData = new EmployeeData();
        }
        return employeeData;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployeeData(EmployeeData employeeData) {
        this.employeeData = employeeData;
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
     * @return the othernames
     */
    public String getOthernames() {
        return othernames;
    }

    /**
     * @param othernames the othernames to set
     */
    public void setOthernames(String othernames) {
        this.othernames = othernames;
    }

    /**
     * @return the staffNumber
     */
    public String getStaffNumber() {
        return staffNumber;
    }

    /**
     * @param staffNumber the staffNumber to set
     */
    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    /**
     * @return the employeeStatus
     */
    public String getEmployeeStatus() {
        return employeeStatus;
    }

    /**
     * @param employeeStatus the employeeStatus to set
     */
    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    /**
     * @return the employeeType
     */
    public String getEmployeeType() {
        return employeeType;
    }

    /**
     * @param employeeType the employeeType to set
     */
    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    /**
     * @return the employeeCategory
     */
    public String getEmployeeCategory() {
        return employeeCategory;
    }

    /**
     * @param employeeCategory the employeeCategory to set
     */
    public void setEmployeeCategory(String employeeCategory) {
        this.employeeCategory = employeeCategory;
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

    private EmployeeQueryCriteria buildQueryCriteria() {
        EmployeeQueryCriteria criteria = new EmployeeQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());

        if (getStaffNumber() != null && !getStaffNumber().trim().equals("")) {
            criteria.setStaffNumber(staffNumber);
        }
        if (getGender() != null && !getGender().trim().isEmpty()) {
            criteria.setGender(gender);
        }
        if (getStatus() != null && !getStatus().trim().isEmpty()) {
            criteria.setStaffNumber(status);
        }
        if (getEmployeeCategory() != null && !getEmployeeCategory().trim().isEmpty()) {
            criteria.setEmployeeCategoryId(employeeCategory);
        }
        if (getEmployeeType() != null && !getEmployeeType().trim().isEmpty()) {
            criteria.setEmployeeTypeId(employeeType);
        }
        if (getSurname() != null && !getSurname().trim().equals("")) {
            criteria.setSurname(surname, WCString.LIKE);
        }
        if (getOthernames() != null && !getOthernames().trim().equals("")) {
            criteria.setOthernames(getOthernames(), WCString.LIKE);
        }
        return criteria;
    }
//    /**
//     *
//     *
//     *
//     */
//    private class EmployeeDataPageGenerator extends SchoolDocGenerator {
//
//        private EmployeeData _employeeData;
//        private School _school;
//
//        /**
//         *
//         * @param data
//         * @param school
//         * @throws IOException
//         */
//        private EmployeeDataPageGenerator(EmployeeData data, School school)
//                throws IOException {
//            super("staffDataPageTemplate",
//                    DocumentTemplateLocator.staffDataPageTemplate);
//            this._employeeData = data;
//            this._school = school;
//        }
//
//        @Override
//        public Map<String, Object> getDocumentParameter() {
//
//            Map<String, Object> context = new HashMap<>();
//            String url = schoolSessionBean.getSchoolPref().getLogoUrl();
//            getFieldsMetadata().addFieldAsImage("photo", NullImageBehaviour.KeepImageTemplate);
//            //set the default school fields
//            setSchoolDefaultFields(context, _school, url);
//            //set other context
//            context.put("employeeData", _employeeData);
//            context.put("address", getAddress(_employeeData.getAddress()));
//            context.put("birthDate",
//                    getDate(_employeeData.getBirthDate()));
//            context.put("dateOfHire",
//                    getDate(_employeeData.getDateOfHire()));
//            String photoUrl = _employeeData.getPictureUrl();
//            if (photoUrl != null && !photoUrl.trim().isEmpty()) {
//                try {
//                    context.put("photo", new UrlImageProvider(new URL(photoUrl)));
//                } catch (MalformedURLException ex) {
//                    Logger.getLogger(EmployeeDataPageGenerator.class.getName()).
//                            log(Level.SEVERE, null, ex);
//                }
//            }
//
//
//            //return context
//            return context;
//        }
//    }
}
