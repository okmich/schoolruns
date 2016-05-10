/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.user.bean.student;

import com.okmich.common.entity.criteria.OrderClause;
import com.okmich.common.entity.criteria.WCBase;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.common.entity.StudentSubject;
import com.okmich.schoolruns.school.service.SchoolService;
import com.okmich.schoolruns.school.service.SchoolStudentService;
import com.okmich.schoolruns.school.service.data.SchoolStudentData;
import com.okmich.schoolruns.school.service.repo.SchoolStudentQueryCriteria;
import com.okmich.schoolruns.school.service.repo.SchoolSubjectQueryCriteria;
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
import org.primefaces.model.DualListModel;

/**
 *
 * @author Michael
 */
@ManagedBean
@ViewScoped
public final class StudentAllocationBean extends _BaseBean {

    @ManagedProperty("#{schoolService}")
    private SchoolService schoolService;
    @ManagedProperty("#{schoolStudentService}")
    private SchoolStudentService schoolStudentService;
    @ManagedProperty("#{schoolSessionBean}")
    private SchoolSessionBean schoolSessionBean;
    @ManagedProperty("#{userLoginSessionBean}")
    private UserLoginSessionBean userLoginSessionBean;
    private SchoolStudentData schoolStudentData;
    private StudentSubject studentSubject;
    private List<SchoolStudentData> schoolStudentList;
    private DualListModel<SchoolStudentData> schoolStudentDualList;
    private DualListModel<SchoolSubject> schoolSubjectDualList;
    private Integer schoolYearId;
    private Integer schoolClassId;
    private Integer gradeLevelId;
    private boolean newSchoolYear;

    /**
     * Creates a new instance of StudentAllocationBean
     */
    public StudentAllocationBean() {
    }

    /**
     * @param schoolStudentService1 the schoolStudentService1 to set
     */
    public void setSchoolStudentService(SchoolStudentService schoolStudentService1) {
        this.schoolStudentService = schoolStudentService1;
    }

    /**
     * @param schoolService the schoolService to set
     */
    public void setSchoolService(SchoolService schoolService1) {
        this.schoolService = schoolService1;
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
    public String doStudentSubjectAssignment() {
        List<SchoolSubject> schoolSubjects = getSchoolSubjectDualList().getTarget();
        try {
            if (schoolSubjects.isEmpty()) {
                throw new Exception("EMPTY_SELECTION");
            }
            getStudentSubject().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());
            getStudentSubject().setSchoolStudent(
                    new SchoolStudent(this.schoolStudentData.getSchoolStudentId()));

            schoolStudentService.assignSubjectsToStudent(schoolSubjects,
                    getStudentSubject());
            //set source and target
            schoolSubjectDualList.getSource().clear();
            schoolSubjectDualList.getTarget().clear();
            setSchoolStudentData(null);

            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Subject(s) assigned to Student", ""));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String doStudentClassAssignment() {
        List<SchoolStudentData> schoolStudents = getSchoolStudentDualList().getTarget();
        try {
            if (schoolStudents.isEmpty()) {
                throw new Exception("EMPTY_SELECTION");
            }
            //check that the user did not enter new batch when moving across a similar year
            if (isNewSchoolYear()
                    && getSchoolYearId().equals(schoolSessionBean.getSchoolCalendarData().getSchoolYearId())) {
                throw new Exception("INVALID_NEW_BATCH_YEAR_OPTION");
            }
            //check backward assignment
            if (getSchoolYearId() > schoolSessionBean.getSchoolCalendarData().getSchoolYearId()) {
                throw new Exception("BACKWARD_YEAR_ASSIGNMENT");
            }
            getSchoolStudentData().setSchoolYearId(
                    schoolSessionBean.getSchoolCalendarData().getSchoolYearId());
            getSchoolStudentData().setModifiedBy(userLoginSessionBean.getUserLogin().getUsername());

            schoolStudentService.assignStudentToClass(schoolStudents,
                    getSchoolStudentData(), isNewSchoolYear());
            //set source and target
            schoolStudentDualList.getSource().clear();
            schoolStudentDualList.getTarget().clear();
            //report success
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Student(s) assigned to class", ""));
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public String searchSchoolStudent() {
        try {
            List<SchoolStudentData> _schoolStudentList =
                    findSchoolStudents(getSchoolYearId(), getSchoolClassId());
            schoolStudentDualList.setSource(_schoolStudentList);
        } catch (Exception ex) {
            processException(ex);
        }
        return "";
    }

    public void fireTargetOnSelectClass(ValueChangeEvent event) {
        HtmlSelectOneMenu newClassMenu = (HtmlSelectOneMenu) event.getComponent();
        Object _classValue = newClassMenu.getValue();
        List<SchoolStudentData> _schoolStudentList;
        try {
            //set the school class
            if (_classValue == null) {
                _schoolStudentList = new ArrayList<SchoolStudentData>();
            } else {
                Integer _schoolClassId = Integer.valueOf(newClassMenu.getValue().toString());
                _schoolStudentList =
                        findSchoolStudents(
                        schoolSessionBean.getSchoolCalendarData().getSchoolYearId(),
                        _schoolClassId);
            }
            //set the target
            schoolStudentDualList.setTarget(_schoolStudentList);
        } catch (Exception ex) {
            processException(ex);
        }
    }

    /**
     *
     * @param event
     */
    public void fireSourceOnSelectYear(ValueChangeEvent event) {
        HtmlSelectOneMenu newYearMenu = (HtmlSelectOneMenu) event.getComponent();
        Object _yearValue = newYearMenu.getValue();
        if (_yearValue == null) {
            this.schoolYearId = schoolSessionBean.getSchoolCalendarData().getSchoolYearId();
        } else {
            this.schoolYearId = Integer.valueOf(_yearValue.toString());
        }
        try {
            //set source target
            schoolStudentDualList.getSource().clear();
            schoolStudentDualList.getTarget().clear();
        } catch (Exception ex) {
            processException(ex);
        }
    }

    public void fireSourceOnSelectClass(ValueChangeEvent event) {
        HtmlSelectOneMenu newClassMenu = (HtmlSelectOneMenu) event.getComponent();
        Object _classValue = newClassMenu.getValue();
        if (schoolYearId == null) {
            this.schoolYearId = schoolSessionBean.getSchoolCalendarData().getSchoolYearId();
        }
        try {
            this.schoolClassId = _classValue == null ? null : Integer.valueOf(_classValue.toString());
            SchoolStudentQueryCriteria criteria = new SchoolStudentQueryCriteria();
            criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
            boolean isSameYear = schoolSessionBean.getSchoolCalendarData().getSchoolYearId()
                    .equals(getSchoolYearId());
            if (isSameYear) {
                //if the year is same as the current year. add class is null as a 
                //criteria
                criteria.setSchoolClassId(WCBase.IS_NULL);
            } else {
                criteria.setSchoolYearId(getSchoolYearId());
            }
            //set the school class
            if (getSchoolClassId() != null && !isSameYear) {
                criteria.setSchoolClassId(getSchoolClassId());
            }
            addStudentSearchOrderBy(criteria);
            //set the target
            schoolStudentDualList.setSource(
                    schoolStudentService.findSchoolStudents(criteria));
        } catch (Exception ex) {
            processException(ex);
        }
    }

    /**
     *
     * @param event
     */
    public void fireOnSelectClassForSubject(ValueChangeEvent event) {
        HtmlSelectOneMenu newClassMenu = (HtmlSelectOneMenu) event.getComponent();
        Object _classValue = newClassMenu.getValue();
        if (_classValue == null) {
            schoolSubjectDualList.setSource(new ArrayList<SchoolSubject>());
            return;
        }
        schoolClassId = Integer.valueOf(_classValue.toString());
        SchoolClass schoolClass = schoolService.findSchoolClass(schoolClassId);
        if (schoolClass.isSameSubjectFlag()) {
            FacesUtil.getFacesContext().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "INVALID CLASS OPERATION",
                    " - students in this " + schoolClass.getCode() + " must offer the same subject"));
            return;
        }
        this.gradeLevelId = schoolClass.getGradeLevel().getGradeLevelId();
        try {
            //set the school student drop-down list
            setSchoolStudentList(findSchoolStudents(
                    schoolSessionBean.getSchoolCalendarData().getSchoolYearId(),
                    schoolClassId));
            //set the dual list source list box
            setDualListSourceWithSubject();
        } catch (Exception ex) {
            processException(ex);
        }
    }

    public void fireOnSelectStudentForSubject(ValueChangeEvent event) {
        HtmlSelectOneMenu studentMenu = (HtmlSelectOneMenu) event.getComponent();
        Object studentVal = studentMenu.getValue();
        try {
            if (studentVal == null) {
                schoolSubjectDualList.setTarget(new ArrayList<SchoolSubject>());
                return;
            }
            Integer schoolStudentId = Integer.valueOf(studentVal.toString());
            this.schoolStudentData = new SchoolStudentData(schoolStudentId);
            List<SchoolSubject> schoolSubjects = schoolStudentService.findSchoolSubjectsByStudent(
                    schoolStudentId);

            //set the dual list source list box
            setDualListSourceWithSubject();
            //set the dual list target list box
            schoolSubjectDualList.getSource().removeAll(schoolSubjects);
            schoolSubjectDualList.setTarget(schoolSubjects);
        } catch (Exception ex) {
            processException(ex);
        }
    }

    /**
     *
     * @param _schoolStudentDualList
     */
    public void setSchoolStudentDualList(DualListModel<SchoolStudentData> _schoolStudentDualList) {
        this.schoolStudentDualList = _schoolStudentDualList;
    }

    /**
     * @return the schoolClassDualList
     */
    public DualListModel<SchoolStudentData> getSchoolStudentDualList() {
        if (this.schoolStudentDualList == null) {
            this.schoolStudentDualList = new DualListModel<>(
                    new ArrayList(), new ArrayList());
        }
        return schoolStudentDualList;
    }

    /**
     * @param schoolClassDualList the schoolClassDualList to set
     */
    public void setSchoolClassDualList(DualListModel<SchoolStudentData> schoolStudentDualList) {
        this.schoolStudentDualList = schoolStudentDualList;
    }

    /**
     * @return the schoolSubjectDualList
     */
    public DualListModel<SchoolSubject> getSchoolSubjectDualList() {
        if (this.schoolSubjectDualList == null) {
            this.schoolSubjectDualList = new DualListModel<SchoolSubject>(
                    new ArrayList(), new ArrayList());
        }
        return schoolSubjectDualList;
    }

    /**
     * @param schoolSubjectDualList the schoolSubjectDualList to set
     */
    public void setSchoolSubjectDualList(DualListModel<SchoolSubject> schoolSubjectDualList) {
        this.schoolSubjectDualList = schoolSubjectDualList;
    }

    /**
     * @return the schoolStudent
     */
    public SchoolStudentData getSchoolStudentData() {
        if (schoolStudentData == null) {
            this.schoolStudentData = new SchoolStudentData();
        }
        return schoolStudentData;
    }

    /**
     * @param schoolStudent the schoolStudent to set
     */
    public void setSchoolStudentData(SchoolStudentData schoolStudent) {
        this.schoolStudentData = schoolStudent;
    }

    /**
     * @return the studentSubject
     */
    public StudentSubject getStudentSubject() {
        if (studentSubject == null) {
            this.studentSubject = new StudentSubject();
        }
        return studentSubject;
    }

    /**
     * @param studentSubject the studentSubject to set
     */
    public void setStudentSubject(StudentSubject studentSubject) {
        this.studentSubject = studentSubject;
    }

    /**
     * @return the schoolStudentList
     */
    public List<SchoolStudentData> getSchoolStudentList() {
        return schoolStudentList;
    }

    /**
     * @param schoolStudentList the schoolStudentList to set
     */
    public void setSchoolStudentList(List<SchoolStudentData> schoolStudentList) {
        this.schoolStudentList = schoolStudentList;
    }

    /**
     * we did a non-standard item here - initializing fied
     *
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
     * @return the schoolClassId
     */
    public Integer getSchoolClassId() {
        return schoolClassId;
    }

    /**
     * @param schoolClassId the schoolClassId to set
     */
    public void setSchoolClassId(Integer schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    /**
     * @return the isNewSchoolYear
     */
    public boolean isNewSchoolYear() {
        return newSchoolYear;
    }

    /**
     * @param isNewSchoolYear the isNewSchoolYear to set
     */
    public void setNewSchoolYear(boolean isNewSchoolYear) {
        this.newSchoolYear = isNewSchoolYear;
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
     *
     *
     * @param _schoolYearId
     * @param _schoolClassId
     * @return List<SchoolStudentData>
     */
    private List<SchoolStudentData> findSchoolStudents(
            Integer _schoolYearId, Integer _schoolClassId) {
        System.out.printf(">>>> %d >>>>>>> %d ", _schoolYearId, _schoolClassId);
        SchoolStudentQueryCriteria criteria = new SchoolStudentQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        if (_schoolYearId != null && _schoolYearId != 0) {
            criteria.setSchoolYearId(_schoolYearId);
        }
        if (_schoolClassId != null && _schoolClassId != 0) {
            criteria.setSchoolClassId(_schoolClassId);
        }
        //do order by
        addStudentSearchOrderBy(criteria);
        //
        return schoolStudentService.findSchoolStudents(criteria);
    }

    /**
     * find all the school subjects for a particular gradeLevel. Return all
     * school subjects for school if the gradeLevelId is null
     *
     * @param gradeLevelId
     * @return List<SchoolSubject>
     */
    private List<SchoolSubject> findSchoolSubjects(Integer gradeLevelId) {
        SchoolSubjectQueryCriteria criteria = new SchoolSubjectQueryCriteria();
        criteria.setSchoolId(schoolSessionBean.getSchool().getSchoolId());
        if (gradeLevelId != null) {
            criteria.setGradeLevelId(gradeLevelId);
        }
        //do order by
        List<OrderClause> orderClauseList = new ArrayList<OrderClause>();
        orderClauseList.add(new OrderClause(SchoolSubjectQueryCriteria.gradeLevelId));

        criteria.setOrderByColumn(orderClauseList);

        return schoolService.findSchoolSubjects(criteria);
    }

    /**
     *
     *
     * @param criteria
     */
    private void addStudentSearchOrderBy(SchoolStudentQueryCriteria criteria) {
        //do order by
        List<OrderClause> orderClauseList = new ArrayList<>();
        orderClauseList.add(new OrderClause(SchoolStudentQueryCriteria.surname));
        criteria.setOrderByColumn(orderClauseList);
    }

    /**
     *
     */
    private void setDualListSourceWithSubject() {
        schoolSubjectDualList.setSource(findSchoolSubjects(
                this.gradeLevelId));
    }
}