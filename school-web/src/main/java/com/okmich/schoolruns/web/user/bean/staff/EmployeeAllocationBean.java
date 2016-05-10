/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.staff;

import com.okmich.common.entity.criteria.OrderClause;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.common.entity.Employee;
import com.okmich.schoolruns.common.entity.SchoolClassTeacher;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.common.entity.SubjectTeacher;
import com.okmich.schoolruns.employee.service.EmployeeService;
import com.okmich.schoolruns.employee.service.data.EmployeeData;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.school.service.repo.SchoolClassTeacherQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolSubjectQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SubjectTeacherQueryCriteria;
import com.okmich.schoolruns.web.common.FacesUtil;
import com.okmich.schoolruns.web.common.bean.UserLoginSessionBean;
import com.okmich.schoolruns.web.common.bean._BaseBean;
import com.okmich.schoolruns.web.user.bean.SchoolSessionBean;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@ViewScoped
public class EmployeeAllocationBean extends _BaseBean {

    @ManagedProperty("#{employeeService}")
    private EmployeeService employeeService;
    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private SubjectTeacher subjectTeacher;
    private SchoolSubject schoolSubject;
    private SchoolClassTeacher schoolClassTeacher;
    private DataModel<SchoolClassTeacher> schoolClassTeacherModel;
    private DualListModel<SchoolSubject> schoolSubjectDualList;
    private Integer gradeLevelId;
    private Integer employeeId;
    private Integer schoolYearId;
    private List<SchoolSubject> __schoolSubjects;

    /**
     * Creates a new instance of EmployeeAllocationBean
     */
    public EmployeeAllocationBean() {
        this.schoolClassTeacherModel = new ListDataModel<SchoolClassTeacher>();
        this.schoolSubjectDualList = new DualListModel<SchoolSubject>();
    }

    /**
     * @param employeeService the employeeService to set
     */
    public void setEmployeeService(EmployeeService employeeService1) {
        this.employeeService = employeeService1;
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
     * @param userLoginSessionBean the userLoginSessionBean to set
     */
    public void setUserLoginSessionBean(UserLoginSessionBean userLoginSessionBean1) {
        this.userLoginSessionBean = userLoginSessionBean1;
    }

    /**
     *
     * @return
     */
    public String doAssignSubjectTeacher() {
        //set the fields
        getSubjectTeacher().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getSubjectTeacher().setSchoolYearId(
                schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
        getSubjectTeacher().setEmployee(new Employee(this.employeeId));
        try {
            schoolService.assignSubjectsToTeacher(schoolSubjectDualList.getTarget(),
                    getSubjectTeacher());
            setEmployeeId(null);
            setGradeLevelId(null);
            schoolSubjectDualList.getSource().clear();
            schoolSubjectDualList.getTarget().clear();
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Assignment successful!!!", ""));
            return "";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String doAssignSchoolClassTeacher() {
        //set the fields
        getSchoolClassTeacher().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getSchoolClassTeacher().setSchoolYear(new SchoolYear(
                schoolSessionBean.getSchoolCalendarData().getSchoolYearId()));
        try {
            schoolService.saveSchoolClassTeacher(getSchoolClassTeacher());
            clearForm();
            return "";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String deassignSchoolClassTeacher() {
        //set the fields
        getSchoolClassTeacher().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
        getSchoolClassTeacher().setModifiedBy(CommonConstants.STATUS_INACTIVE);
        getSchoolClassTeacher().setSchoolYear(new SchoolYear(
                schoolSessionBean.getSchoolCalendarData().getSchoolYearId()));
        try {
            schoolService.saveSchoolClassTeacher(getSchoolClassTeacher());
            return "";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @param event
     */
    public void fireOnSelectGradesForSubject(ValueChangeEvent event) {
        HtmlSelectOneMenu gradeMenu = (HtmlSelectOneMenu) event.getComponent();
        Object gradeVal = gradeMenu.getValue();
        Integer _gradeLevelId = null;
        if (gradeVal != null) {
            _gradeLevelId = Integer.valueOf(gradeVal.toString());
        }
        try {
            getSubjectForGrade(_gradeLevelId);
            schoolSubjectDualList.setSource(this.__schoolSubjects);
        } catch (Exception ex) {
            processException(ex);
        }
    }

    /**
     *
     * @param event
     */
    public void fireOnSelectTeacherForSubject(ValueChangeEvent event) {
        HtmlSelectOneMenu empMenu = (HtmlSelectOneMenu) event.getComponent();
        Object empVal = empMenu.getValue();
        if (empVal == null) {
            schoolSubjectDualList.getTarget().clear();
            return;
        }
        this.employeeId = Integer.valueOf(empVal.toString());
        SubjectTeacherQueryCriteria criteria = new SubjectTeacherQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        //criteria.setSchoolYearId(schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
        criteria.setEmployeeId(employeeId);

        try {
            //
            List<SubjectTeacher> subjectTeachers = schoolService.findSubjectTeachers(criteria);
            schoolSubjectDualList.getTarget().clear();
            schoolSubjectDualList.setSource(this.__schoolSubjects);
            for (SubjectTeacher st : subjectTeachers) {
                schoolSubjectDualList.getSource().remove(st.getSchoolSubject());
                schoolSubjectDualList.getTarget().add(st.getSchoolSubject());
            }
        } catch (Exception ex) {
            processException(ex);
        }
    }

    /**
     *
     * @param event
     */
    public void fireOnSelectTeacherForClasses(ValueChangeEvent event) {
        HtmlSelectOneMenu empMenu = (HtmlSelectOneMenu) event.getComponent();
        Object empVal = empMenu.getValue();
        if (empVal == null) {
            return;
        }
        employeeId = Integer.valueOf(empVal.toString());
        try {
            findSchoolClassTeachers(employeeId);
        } catch (Exception ex) {
            processException(ex);
        }
    }

    /**
     *
     * @param actionEvent
     */
    public String prepareToAddSchoolClassTeacher() {
        if (this.employeeId == null) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO EMPLOYEE SELECTED", ""));
            return "";
        }
        try {
            EmployeeData _employeeData = employeeService.findEmployee(getEmployeeId());
            if (_employeeData != null) {
                getSchoolClassTeacher().setEmployee(_employeeData.getEmployee());
                setEditMode("CREATE");
                System.out.println(getEditMode());
            }
            return "";
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @param actionEvent
     */
    public String prepareToEditSchoolClassTeacher() {
        if (getSchoolClassTeacher().getSchoolClassTeacherId() == null) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO EMPLOYEE SELECTED", ""));
            return null;
        }
        setEditMode("MODIFY");
        return "";
    }

    /**
     *
     * @param actionEvent
     */
    public String prepareToDeleteSchoolClassTeacher() {
        if (getSchoolClassTeacher().getSchoolClassTeacherId() == null) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO EMPLOYEE SELECTED", ""));
            return null;
        }
        setEditMode("VIEW");
        return "";
    }

    /**
     *
     * @return
     */
    public String clearForm() {
        setSchoolClassTeacher(new SchoolClassTeacher());
        setEditMode(null);
        return "";
    }

    /**
     * @return the subjectTeacher
     */
    public SubjectTeacher getSubjectTeacher() {
        if (subjectTeacher == null) {
            this.subjectTeacher = new SubjectTeacher();
        }
        return subjectTeacher;
    }

    /**
     * @param subjectTeacher the subjectTeacher to set
     */
    public void setSubjectTeacher(SubjectTeacher subjectTeacher) {
        this.subjectTeacher = subjectTeacher;
    }

    /**
     * @return the schoolClassTeacher
     */
    public SchoolClassTeacher getSchoolClassTeacher() {
        if (schoolClassTeacher == null) {
            this.schoolClassTeacher = new SchoolClassTeacher();
        }
        return schoolClassTeacher;
    }

    /**
     * @param schoolClassTeacher the schoolClassTeacher to set
     */
    public void setSchoolClassTeacher(SchoolClassTeacher schoolClassTeacher) {
        this.schoolClassTeacher = schoolClassTeacher;
    }

    /**
     * @return the schoolClassTeacherModel
     */
    public DataModel<SchoolClassTeacher> getSchoolClassTeacherModel() {
        if (schoolClassTeacherModel == null) {
            schoolClassTeacherModel = new ListDataModel<>(
                    new ArrayList<SchoolClassTeacher>());
        }
        return schoolClassTeacherModel;
    }

    /**
     * @param schoolClassTeacherModel the schoolClassTeacherModel to set
     */
    public void setSchoolClassTeacherModel(
            DataModel<SchoolClassTeacher> schoolClassTeacherModel) {
        this.schoolClassTeacherModel = schoolClassTeacherModel;
    }

    /**
     * @return the schoolSubjectDualList
     */
    public DualListModel<SchoolSubject> getSchoolSubjectDualList() {
        return schoolSubjectDualList;
    }

    /**
     * @param schoolSubjectDualList the schoolSubjectDualList to set
     */
    public void setSchoolSubjectDualList(DualListModel<SchoolSubject> schoolSubjectDualList) {
        this.schoolSubjectDualList = schoolSubjectDualList;
    }

    /**
     * @return the schoolSubject
     */
    public SchoolSubject getSchoolSubject() {
        if (schoolSubject == null) {
            this.schoolSubject = new SchoolSubject();
        }
        return schoolSubject;
    }

    /**
     * @param schoolSubject the schoolSubject to set
     */
    public void setSchoolSubject(SchoolSubject schoolSubject) {
        this.schoolSubject = schoolSubject;
    }

    /**
     *
     */
    private void findSchoolClassTeachers(Integer _employeeId) {

        SchoolClassTeacherQueryCriteria criteria = new SchoolClassTeacherQueryCriteria();
        criteria.setEmployeeId(_employeeId);
        criteria.setStatus(CommonConstants.STATUS_ACTIVE);
        List<SchoolClassTeacher> schoolClassTeachers =
                schoolService.findSchoolClassTeachers(criteria);
        this.schoolClassTeacherModel =
                new ListDataModel<>(schoolClassTeachers);
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
     * @return the employeeId
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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

    /**
     *
     * @param gradeLevelId
     */
    private void getSubjectForGrade(Integer gradeLevelId) {
        SchoolSubjectQueryCriteria criteria = new SchoolSubjectQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        if (gradeLevelId != null) {
            criteria.setGradeLevelId(Integer.valueOf(gradeLevelId));
        }
        List<OrderClause> orderClauseList = new ArrayList<>(1);
        orderClauseList.add(new OrderClause(SchoolSubjectQueryCriteria.gradeLevelId));
        criteria.setOrderByColumn(orderClauseList);

        this.__schoolSubjects = schoolService.findSchoolSubjects(criteria);
    }
}
