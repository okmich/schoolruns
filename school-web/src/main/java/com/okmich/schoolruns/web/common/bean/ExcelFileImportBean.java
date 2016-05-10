/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common.bean;

import com.okmich.common.util.api.CommonConstants;
import com.okmich.common.web.util.fileimport.ExcelFileImportData;
import com.okmich.common.web.util.fileimport.ExcelFileImportUtil;
import com.okmich.common.web.util.fileimport.FileImportUtil;
import com.okmich.schoolruns.calendar.service.data.Attendance;
import com.okmich.schoolruns.calendar.service.data.EmployeeAttendanceTable;
import com.okmich.schoolruns.calendar.service.data.SchoolCalendarData;
import com.okmich.schoolruns.calendar.service.data.StudentAttendanceTable;
import com.okmich.schoolruns.common.entity.Address;
import com.okmich.schoolruns.common.entity.GradeLevel;
import com.okmich.schoolruns.common.entity.Parent;
import com.okmich.schoolruns.common.entity.School;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.common.entity.Section;
import com.okmich.schoolruns.common.entity.State;
import com.okmich.schoolruns.common.entity.Stream;
import com.okmich.schoolruns.common.entity.Subject;
import com.okmich.schoolruns.common.entity.Title;
import com.okmich.schoolruns.employee.service.data.EmployeeData;
import com.okmich.schoolruns.school.service.data.ExamScoreTable;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.web.common.FacesUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
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
public class ExcelFileImportBean extends _BaseBean {

    private final Logger LOG = Logger.getLogger(ExcelFileImportBean.class.getName());
    private School school;
    private SchoolCalendarData schoolCalendarData;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private UploadedFile uploadedFile;
    private ExcelFileImportData excelFileUploadData;
    private ExcelFileImportUtil excelFileImportUtil;
    private static final String ACTION_CODE_PARAM_CODE = "ACTIONCODE";
    private static final String ACTION_DESC_PARAM_CODE = "ACTIONDESC";
    private static final String BACK_PATH_PARAM_CODE = "BACKPATH";

    /**
     * Creates a new instance of ExcelFileImportBean
     */
    public ExcelFileImportBean() {
    }

    /**
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean1) {
        this.userLoginSessionBean = userLoginSessionBean1;
    }

    /**
     *
     * @return
     */
    public String doExcelImport() {
        excelFileImportUtil = ExcelFileImportUtil.getInstance();
        int beginReadIndex = 0; //the row index for the system to begin reading data
        try {
            String rowIndex = FacesUtil.getRequestParameter("ROWSTART");
            if (rowIndex != null) {
                try {
                    beginReadIndex = Integer.valueOf(rowIndex);
                } catch (Exception e) {
                    beginReadIndex = 0;
                }
            }
            excelFileImportUtil.setRowStartIndex(beginReadIndex);
            excelFileUploadData = excelFileImportUtil.extractContent(
                    getUploadedFile().getInputstream());
            excelFileUploadData.setField1(FacesUtil.getRequestParameter(ACTION_CODE_PARAM_CODE));
            excelFileUploadData.setField2(FacesUtil.getRequestParameter(ACTION_DESC_PARAM_CODE));
            excelFileUploadData.setField3(FacesUtil.getRequestParameter(BACK_PATH_PARAM_CODE));

            return "/common/fileuploadcontentview";
        } catch (IOException ex) {
            Logger.getLogger(ExcelFileImportBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex.getMessage(), ""));
        }
        return null;
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
     * @return the excelFileImportUtil
     */
    public ExcelFileImportUtil getExcelFileImportUtil() {
        return excelFileImportUtil;
    }

    /**
     * @param excelFileImportUtil the excelFileImportUtil to set
     */
    public void setExcelFileImportUtil(ExcelFileImportUtil excelFileImportUtil) {
        this.excelFileImportUtil = excelFileImportUtil;
    }

    /**
     * @return the excelFileUploadData
     */
    public ExcelFileImportData getExcelFileUploadData() {
        return excelFileUploadData;
    }

    /**
     * @param excelFileUploadData the excelFileUploadData to set
     */
    public void setExcelFileUploadData(ExcelFileImportData excelFileUploadData) {
        this.excelFileUploadData = excelFileUploadData;
    }

    /**
     *
     *
     * @param data
     * @return
     */
    public List<SchoolStudentData> reshapeToSchoolStudent() throws Exception {
        List<SchoolStudentData> schoolStudents = new ArrayList<>(
                getExcelFileUploadData().getData().size());
        SchoolStudentData _schoolStudent;
        Address _address;
        for (List<String> rec : getExcelFileUploadData().getData()) {
            _schoolStudent = new SchoolStudentData();
            //access the fields by index and populated the school student object
            _schoolStudent.setSurname(rec.get(0)); //surname
            _schoolStudent.setFirstname(rec.get(1));
            _schoolStudent.setOthernames(rec.get(2));
            _schoolStudent.setGender(rec.get(3));
            try {
                _schoolStudent.setBirthDate(
                        FileImportUtil.toDate(rec.get(4)));
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
                throw new Exception("Error occured with the birth date column."
                        + " Check the date format. It has to be dd/MM/yyyy");
            }
            _schoolStudent.setRegistrationNo(rec.get(5));
            try {
                _schoolStudent.setAdmissionDate(FileImportUtil.toDate(rec.get(6)));
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
                throw new Exception("Error occured with the admission date column."
                        + " Check the date format. It has to be dd/MM/yyyy");
            }

            _schoolStudent.setSchoolClassCode(rec.get(7));
            //address
            _address = new Address();
            _address.setStreetLine1(rec.get(8));
            _address.setState(new State(rec.get(9)));
            _schoolStudent.setAddress(_address);
            //parent's phone number
            _schoolStudent.setParentId(rec.get(10));

            //add the utility fields
            _schoolStudent.setSchoolId(getSchool().getSchoolId());
            _schoolStudent.setSchoolYearId(getSchoolCalendarData().getSchoolYearId());
            _schoolStudent.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            _schoolStudent.setStatus(CommonConstants.STATUS_ACTIVE);

            schoolStudents.add(_schoolStudent);
        }
        return schoolStudents;
    }

    /**
     *
     *
     * @param data
     * @return
     */
    public List[] reshapeToSchoolStudentWithParent() throws Exception {
        List<SchoolStudentData> schoolStudents = new ArrayList<>(
                getExcelFileUploadData().getData().size());
        List<Parent> parents = new ArrayList<>(
                getExcelFileUploadData().getData().size());
        SchoolStudentData _schoolStudent;
        Parent _parent;
        Address _address;
        for (List<String> rec : getExcelFileUploadData().getData()) {
            _schoolStudent = new SchoolStudentData();
            _parent = new Parent();
            //access the fields by index and populated the school student object
            _schoolStudent.setSurname(rec.get(0)); //surname
            _schoolStudent.setFirstname(rec.get(1));
            _schoolStudent.setOthernames(rec.get(2));
            _schoolStudent.setGender(rec.get(3));
            try {
                _schoolStudent.setBirthDate(
                        FileImportUtil.toDate(rec.get(4)));
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
                throw new Exception("Error occured with the birth date column."
                        + " Check the date format. It has to be dd/MM/yyyy");
            }
            _schoolStudent.setRegistrationNo(rec.get(5));
            try {
                _schoolStudent.setAdmissionDate(FileImportUtil.toDate(rec.get(6)));
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
                throw new Exception("Error occured with the admission date column."
                        + " Check the date format. It has to be dd/MM/yyyy");
            }

            _schoolStudent.setSchoolClassCode(rec.get(7));
            //address
            _address = new Address();
            _address.setStreetLine1(rec.get(8));
            _address.setState(new State(rec.get(9)));
            _schoolStudent.setAddress(_address);
            //parent's phone number
            _parent.setPhoneNumber(rec.get(10));
            _parent.setSurname(rec.get(11));
            _parent.setOthernames(rec.get(12));
            _parent.setEmail(rec.get(13));
            _parent.setRelationship(rec.get(14));

            //add the utility fields
            _schoolStudent.setSchoolId(getSchool().getSchoolId());
            _schoolStudent.setSchoolYearId(getSchoolCalendarData().getSchoolYearId());
            _schoolStudent.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            _schoolStudent.setStatus(CommonConstants.STATUS_ACTIVE);
            _parent.setStatus(CommonConstants.STATUS_ACTIVE);

            schoolStudents.add(_schoolStudent);
            parents.add(_parent);
        }
        //create an array of list from the school student and parent list
        List[] retList = {schoolStudents, parents};
        //return the list
        return retList;
    }

    /**
     *
     *
     * @param data
     * @return List<Employee>
     * @throws Exception
     */
    public List<EmployeeData> reshapeToEmployeeData() throws Exception {
        List<EmployeeData> employees = new ArrayList<>(
                getExcelFileUploadData().getData().size());
        EmployeeData _employeeData;

        for (List<String> rec : getExcelFileUploadData().getData()) {
            _employeeData = new EmployeeData();

            _employeeData.setSurname(rec.get(0));
            _employeeData.setOthernames(rec.get(1));
            _employeeData.setGender(rec.get(2));
            try {
                _employeeData.setBirthDate(FileImportUtil.toDate(rec.get(3)));
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
                throw new Exception("Error occured with the birth date column."
                        + " Check the date format. It has to be dd/MM/yyyy");
            }
            _employeeData.setEmail(rec.get(4));
            _employeeData.setMobileNo(rec.get(5));
            try {
                _employeeData.setDateOfHire(FileImportUtil.toDate(rec.get(6)));
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
                throw new Exception("Error occured with the hire date column."
                        + " Check the date format. It has to be dd/MM/yyyy");
            }
            _employeeData.setStaffNumber(rec.get(7));
            _employeeData.setStateCode(rec.get(8));
            _employeeData.setHomeTown(rec.get(9));
            if (rec.get(10) == null) {
                //if the category is not specified, set to the default category - TEACHING STAFF
                _employeeData.setEmployeeCategoryId(CommonConstants.EMPLOYEE_DEFAULT_CATEGORY);
            } else {
                _employeeData.setEmployeeCategoryId(rec.get(10));
            }

            //set defaults
            _employeeData.setEmployeePositionId(CommonConstants.EMPLOYEE_DEFAULT_POSITION);
            _employeeData.setEmployeeTypeId(CommonConstants.EMPLOYEE_DEFAULT_TYPE);
            _employeeData.setIdentificationMeansId(CommonConstants.EMPLOYEE_DEFAULT_ID_MEANS);
            _employeeData.setIdNumber(_employeeData.getStaffNumber());
            _employeeData.setStatus(CommonConstants.STATUS_ACTIVE);
            _employeeData.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            _employeeData.setSchoolId(getSchool().getSchoolId());

            employees.add(_employeeData);
        }
        return employees;
    }

    /**
     *
     *
     * @param data
     * @return
     */
    public List<Parent> reshapeToParent() throws Exception {
        List<Parent> parents = new ArrayList<>(
                getExcelFileUploadData().getData().size());
        Parent _parent;
        Address _address;
        String id = null;
        for (List<String> rec : getExcelFileUploadData().getData()) {
            _parent = new Parent();
            //first is the mobile number
            _parent.setPhoneNumber(rec.get(0));
            //second is title
            id = rec.get(1);
            if (id != null && !id.isEmpty()) {
                _parent.setTitle(new Title(id));
            }
            //3 is surname
            _parent.setSurname(rec.get(2));
            //4 is othernames
            _parent.setOthernames(rec.get(3));
            //5 is email
            _parent.setEmail(rec.get(4));
            //6 is alternate phone number
            _parent.setAlternateNumber(rec.get(5));
            //7 is relationship
            _parent.setRelationship(rec.get(6));
            //address - 8
            _address = new Address();
            _address.setStreetLine1(rec.get(7));
            _parent.setAddress1(_address);

            //9 is state of origin
            id = rec.get(8);
            if (id != null && !id.isEmpty()) {
                _parent.setState(new State(id));
            }
            //utility fields
            _parent.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            _parent.setStatus(CommonConstants.STATUS_ACTIVE);

            parents.add(_parent);
        }
        return parents;
    }

    /**
     *
     *
     * @param uploadedData - a list of list of String gotten from
     * {@link ExcelFileImportBean#excelFileUploadData}
     *
     * @return List<SchoolClass> - a list of school Classes
     */
    public List<SchoolClass> reshapeToSchoolClass() {
        List<SchoolClass> schoolClasses = new ArrayList<>(
                getExcelFileUploadData().getData().size());
        SchoolClass _sc;
        GradeLevel _gradeLevel;
        Section _section;
        String id;
        Stream _stream;
        for (List<String> rec : getExcelFileUploadData().getData()) {
            _sc = new SchoolClass();

            _sc.setCode(rec.get(0));
            _sc.setDescription(rec.get(1));
            _sc.setSameSubjectFlag(FileImportUtil.toBoolean(rec.get(2)));

            id = rec.get(3);
            _gradeLevel = new GradeLevel(0, id, null, null); //set the description only
            _sc.setGradeLevel(_gradeLevel);

            id = rec.get(4);
            if (id != null && !id.trim().isEmpty()) {
                _section = new Section();
                _section.setDescription(rec.get(4));
//                _sc.setSection(_section);
            }

            id = rec.get(5);
            if (id != null && !id.trim().isEmpty()) {
                _stream = new Stream();
                _stream.setDescription(rec.get(5));
                _sc.setStream(_stream);
            }

            //utility
            _sc.setSchoolId(getSchool().getSchoolId());
            _sc.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            _sc.setModifiedTime(new Date());
            _sc.setStatus(CommonConstants.STATUS_ACTIVE);

            schoolClasses.add(_sc);
        }
        return schoolClasses;
    }

    /**
     *
     * @param uploadedData
     * @return List<SchoolSubject>
     * @throws Exception if error occurs
     */
    public List<SchoolSubject> reshapeToSchoolSubjects()
            throws Exception {
        List<SchoolSubject> schoolClasses = new ArrayList<>(
                getExcelFileUploadData().getData().size());
        SchoolSubject _ss;
        GradeLevel _gradeLevel;
        Subject _subject;
        for (List<String> rec : getExcelFileUploadData().getData()) {
            _ss = new SchoolSubject();

            _ss.setSubjectCode(rec.get(0));
            try {
                _subject = new Subject(0, rec.get(1), null, null); //set the description only
                _ss.setSubject(_subject);
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
                throw new Exception("Error occured with the subject ID column."
                        + " Check the 2nd Sheet to ensure correct subject IDs");
            }
            try {
                _gradeLevel = new GradeLevel(0, rec.get(2), null, null); //set the description only
                _ss.setGradeLevel(_gradeLevel);
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
                throw new Exception("Error occured with the grade level ID column."
                        + " Check the 2nd Sheet to ensure correct grade Level IDs");
            }
            try {
                _ss.setCreditWeight(Short.valueOf(rec.get(3)));
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getMessage(), ex);
                _ss.setCreditWeight((short) 1); //on error set the default weight of 1
            }
            //utility
            _ss.setSchoolId(getSchool().getSchoolId());
            _ss.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            _ss.setModifiedTime(new Date());
            _ss.setStatus(CommonConstants.STATUS_ACTIVE);

            schoolClasses.add(_ss);
        }
        return schoolClasses;
    }

    /**
     * @return the school
     */
    public School getSchool() {
        return school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(School school) {
        this.school = school;
    }

    /**
     * @return the schoolCalendarData
     */
    public SchoolCalendarData getSchoolCalendarData() {
        return schoolCalendarData;
    }

    /**
     * @param schoolCalendarData the schoolCalendarData to set
     */
    public void setSchoolCalendarData(SchoolCalendarData schoolCalendarData) {
        this.schoolCalendarData = schoolCalendarData;
    }

    public ExamScoreTable reshapeToExamScoreTable() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     *
     *
     * @return @throws Exception
     */
    public StudentAttendanceTable reshapeToStudentAttendanceTable() throws Exception {
        StudentAttendanceTable studAttendTable;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //we are generally going to cut the columns names such that date reading
        //will begin from the fourth column as the school student id, fullname and 
        //admission number occupies the first three column
        List<Date> dates = new ArrayList<>(getExcelFileUploadData().getColumnNames().size() - 3);
        for (int i = 3; i < getExcelFileUploadData().getColumnNames().size(); i++) {
            try {
                dates.add(df.parse(getExcelFileUploadData().getColumnNames().get(i)));
            } catch (ParseException ex) {
                Logger.getLogger(ExcelFileImportBean.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("ERROR_ATTENDANCE_SHEET_FIELD_ALTERED");
            }
        }
        studAttendTable = new StudentAttendanceTable(dates);
        List<SchoolStudent> schoolStudents = new ArrayList<>(getExcelFileUploadData().getData().size());
        SchoolStudent _schoolStudent;
        for (List<String> rec : getExcelFileUploadData().getData()) {
            _schoolStudent = new SchoolStudent(Integer.valueOf(rec.get(0)));
            //there is no reason to set the name
            _schoolStudent.setRegistrationNo(rec.get(2));
            //add to the school student
            schoolStudents.add(_schoolStudent);
        }
        studAttendTable.setSchoolStudents(schoolStudents);
        //with the table rows and cols definition fully set, we begin reading the 
        //data in each cell of the table
        Attendance<SchoolStudent> studentAttendance;
        for (List<String> rec : getExcelFileUploadData().getData()) {
            _schoolStudent = new SchoolStudent(Integer.valueOf(rec.get(0)));
            //there is no reason to set the name
            _schoolStudent.setRegistrationNo(rec.get(2));
            for (int i = 3; i < rec.size(); i++) {
                String val = rec.get(i);//value from the excel
                if (val != null && !val.isEmpty()) {
                    studentAttendance = new Attendance<>();
                    studentAttendance.setType(_schoolStudent);
                    studentAttendance.setAttendDate(dates.get(i - 3));
                    studentAttendance.setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());

                    studAttendTable.addAttendances(studentAttendance);
                }
            }
        }
        return studAttendTable;
    }

    public EmployeeAttendanceTable reshapeToEmployeeAttendanceTable() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
