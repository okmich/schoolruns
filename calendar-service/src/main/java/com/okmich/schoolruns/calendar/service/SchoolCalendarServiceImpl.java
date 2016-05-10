package com.okmich.schoolruns.calendar.service;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.okmich.common.exception.BusinessException;
import com.okmich.common.exception.ErrorConstants;
import com.okmich.common.util.StringUtil;
import com.okmich.common.util.api.CommonConstants;
import com.okmich.schoolruns.calendar.service.data.SchoolCalendarData;
import com.okmich.schoolruns.calendar.service.data.TtEntryTable;
import com.okmich.schoolruns.calendar.service.repo.CalendarCriteriaSearchRepo;
import com.okmich.schoolruns.calendar.service.repo.ClassPeriodRepo;
import com.okmich.schoolruns.calendar.service.repo.EventRepo;
import com.okmich.schoolruns.calendar.service.repo.HolidayQueryCriteria;
import com.okmich.schoolruns.calendar.service.repo.HolidayRepo;
import com.okmich.schoolruns.calendar.service.repo.TimetableEntryRepo;
import com.okmich.schoolruns.common.entity.ClassPeriod;
import com.okmich.schoolruns.common.entity.Event;
import com.okmich.schoolruns.common.entity.EventType;
import com.okmich.schoolruns.common.entity.Holiday;
import com.okmich.schoolruns.common.entity.ParticipantCategory;
import com.okmich.schoolruns.common.entity.SchoolClass;
import com.okmich.schoolruns.common.entity.SchoolPref;
import com.okmich.schoolruns.common.entity.SchoolStudent;
import com.okmich.schoolruns.common.entity.SchoolSubject;
import com.okmich.schoolruns.common.entity.SchoolTerm;
import com.okmich.schoolruns.common.entity.SchoolYear;
import com.okmich.schoolruns.common.entity.TimetableEntry;
import com.okmich.schoolruns.common.entity.Weekday;
import com.okmich.schoolruns.common.service.repo.WeekdayRepo;
import com.okmich.schoolruns.school.service.repo.SchoolClassRepo;
import com.okmich.schoolruns.school.service.repo.SchoolPrefRepo;
import com.okmich.schoolruns.school.service.repo.SchoolStudentRepo;
import com.okmich.schoolruns.school.service.repo.SchoolTermRepo;
import com.okmich.schoolruns.school.service.repo.SchoolYearRepo;
import com.okmich.schoolruns.school.service.repo.StudentSubjectRepo;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michael
 */
@Service("schoolCalendarService")
@Transactional
public class SchoolCalendarServiceImpl implements SchoolCalendarService {

    /**
     * LOG - class logger
     */
    private static final Logger LOG = Logger.getLogger(SchoolCalendarServiceImpl.class.getName());
    /**
     * classPeriodRepo
     */
    @Autowired
    private ClassPeriodRepo classPeriodRepo;
    /**
     * eventRepo
     */
    @Autowired
    private EventRepo eventRepo;
    /**
     * holidayRepo
     */
    @Autowired
    private HolidayRepo holidayRepo;
    /**
     * calendarCriteriaSearchRepo
     */
    @Autowired
    private CalendarCriteriaSearchRepo calendarCriteriaSearchRepo;
    /**
     * schoolClassRepo
     */
    @Autowired
    private SchoolClassRepo schoolClassRepo;
    /**
     * schoolStudentRepo
     */
    @Autowired
    private SchoolStudentRepo schoolStudentRepo;
    /**
     * schoolTermRepo
     */
    @Autowired
    private SchoolTermRepo schoolTermRepo;
    /**
     * studentSubjectRepo
     */
    @Autowired
    private StudentSubjectRepo studentSubjectRepo;
    /**
     * schoolPrefRepo
     */
    @Autowired
    private SchoolPrefRepo schoolPrefRepo;
    /**
     * schoolYearRepo
     */
    @Autowired
    private SchoolYearRepo schoolYearRepo;
    /**
     * schoolTermRepo
     */
    @Autowired
    private TimetableEntryRepo timetableEntryRepo;
    /**
     * weekdayRepo
     */
    @Autowired
    private WeekdayRepo weekdayRepo;

    public SchoolCalendarServiceImpl() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public SchoolCalendarData createKickOffYearTerm(SchoolCalendarData schoolCalendarData)
            throws BusinessException {
        schoolCalendarData.setModifiedTime(new Date());
        schoolCalendarData.setStatus(CommonConstants.STATUS_ACTIVE);
        schoolCalendarData.setTermStartDate(
                schoolCalendarData.getYearStartDate());
        schoolCalendarData.setTermId(1);
        try {
            SchoolYear schoolYear = schoolCalendarData.getSchoolYear();
            schoolYear.setCurrentYear(true);
            schoolYear = schoolYearRepo.save(schoolYear);
            schoolCalendarData.setSchoolYear(schoolYear);

            SchoolTerm schoolTerm = schoolCalendarData.getSchoolTerm();
            schoolTerm.setSchoolYearId(schoolYear.getSchoolYearId());
            schoolTerm.setStartDate(schoolYear.getStartDate());
            schoolTerm.setCurrentTerm(true);

            schoolTerm = schoolTermRepo.save(schoolTerm);
            schoolCalendarData.setSchoolTerm(schoolTerm);

            return schoolCalendarData;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public SchoolYear saveSchoolYear(SchoolYear schoolYear) throws BusinessException {
        try {
            List<SchoolYear> schoolYears = schoolYearRepo.findOverlappingYears(
                    schoolYear.getSchoolId(), schoolYear.getStartDate(), schoolYear.getClosingDate());
            if (!schoolYears.isEmpty() && !schoolYears.contains(schoolYear)) {
                throw new BusinessException(ErrorConstants.OVERLAPPING_PREVIOUS_SCHOOL_YEARS);
            }
            //if this is an update, ensure that the data boundaries are in sync
            //with the date boundaries of the composing terms
            if (schoolYear.getSchoolYearId() != null) {
                List<SchoolTerm> schoolTerms = schoolTermRepo.findTermsBySchoolYear(
                        schoolYear.getSchoolYearId());
                for (SchoolTerm schoolTerm : schoolTerms) {
                    if (schoolYear.getStartDate().after(schoolTerm.getStartDate())) {
                        //something is wrong
                        throw new BusinessException(ErrorConstants.CONFLICTING_TERM_DATES);
                    }
                    if (schoolYear.getClosingDate().before(schoolTerm.getClosingDate())) {
                        //something is wrong
                        throw new BusinessException(ErrorConstants.CONFLICTING_TERM_DATES);
                    }
                }
            }
            if (schoolYear.isCurrentYear()) { //did the user make this the current year
                //find any existing current year
                SchoolYear _schoolYear = schoolYearRepo.findSchoolCurrentYear(
                        schoolYear.getSchoolId());
                if (_schoolYear != null && !_schoolYear.equals(schoolYear)) {
                    _schoolYear.setCurrentYear(false); //remove the existing current year flag
                    schoolYearRepo.save(_schoolYear);
                }
            }
            schoolYear.setModifiedTime(new Date());
            schoolYearRepo.save(schoolYear);
            return schoolYear;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public SchoolYear findSchoolYear(int schoolYearId) {
        return schoolYearRepo.findOne(schoolYearId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<SchoolYear> findSchoolYears(int schoolId) {
        return schoolYearRepo.findAllBySchool(schoolId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public SchoolYear findSchoolCurrentYear(int schoolId) throws BusinessException {
        return schoolYearRepo.findSchoolCurrentYear(schoolId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public SchoolTerm saveSchoolTerm(SchoolTerm schoolTerm) throws BusinessException {
        List<SchoolTerm> schoolTerms = schoolTermRepo.findOverlappingTerms(
                schoolTerm.getSchoolYearId(), schoolTerm.getStartDate());
        if (!schoolTerms.isEmpty()) {
            throw new BusinessException(ErrorConstants.OVERLAPPING_START_DATE);
        }
        SchoolYear schoolYear = schoolYearRepo.findOne(schoolTerm.getSchoolYearId());
        if (schoolYear.getStartDate().after(schoolTerm.getStartDate())
                || schoolYear.getClosingDate().before(schoolTerm.getClosingDate())) {
            throw new BusinessException(ErrorConstants.OUT_OF_YEAR_DATE_BOUNDARIES);
        }
        try {
            if (schoolTerm.isCurrentTerm()) {//did the user make this the current term
                //find any existing current term
                SchoolTerm _schoolTerm = schoolTermRepo.findSchoolCurrentTerm(
                        schoolYear.getSchoolId());
                if (_schoolTerm != null && !_schoolTerm.equals(schoolTerm)) {
                    _schoolTerm.setCurrentTerm(false); //remove the existing current term flag
                    schoolTermRepo.save(_schoolTerm);
                }
            }
            schoolTerm.setModifiedTime(new Date());
            schoolTerm.setStatus(CommonConstants.STATUS_ACTIVE);
            schoolTermRepo.save(schoolTerm);
            return schoolTerm;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public SchoolTerm findSchoolTerm(int schoolTermId) {
        return schoolTermRepo.findOne(schoolTermId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<SchoolTerm> findSchoolTerms(int schoolId, int schoolYearId) {
        return schoolTermRepo.findTermsBySchoolYear(schoolYearId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public SchoolTerm findSchoolCurrentTerm(int schoolId) throws BusinessException {
        SchoolYear schoolYear = schoolYearRepo.findSchoolCurrentYear(schoolId);
        if (schoolYear == null) {
            return null;
        }
        return schoolTermRepo.findSchoolCurrentTerm(schoolYear.getSchoolYearId());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public SchoolCalendarData findSchoolCurrentPeriod(int schoolId) throws BusinessException {
        SchoolYear schoolYear = findSchoolCurrentYear(schoolId);
        if (schoolYear == null) {
            throw new BusinessException(ErrorConstants.NO_CURRENT_YEAR_SET);
        }
        List<SchoolTerm> schoolTerms = findSchoolTerms(schoolId, schoolYear.getSchoolYearId());
        SchoolTerm schoolTerm = null;
        for (SchoolTerm st : schoolTerms) {
            if (st.isCurrentTerm()) {
                schoolTerm = st;
                break;
            }
        }
        return new SchoolCalendarData(schoolYear, schoolTerm);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ClassPeriod saveClassPeriod(ClassPeriod classPeriod) throws BusinessException {


        //save the period to database
        try {
            classPeriod = classPeriodRepo.save(classPeriod);
            return classPeriod;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public ClassPeriod findClassPeriod(Integer classPeriodId) {
        return classPeriodRepo.findOne(classPeriodId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClassPeriod> findClassPeriods(Integer schoolSectionId) {
        return classPeriodRepo.findClassPeriods(schoolSectionId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Event createEvent(Event event) throws BusinessException {
        //save the event to database
        try {
            event = eventRepo.save(event);
            return event;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public Event findEvent(Integer eventId) throws BusinessException {
        return eventRepo.findOne(eventId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<Event> findSchoolEvents(Integer schoolYearId) {
        return eventRepo.findSchoolEvents(schoolYearId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public TimetableEntry createTimetableEntry(TimetableEntry ttEntry) throws BusinessException {
        //check for duplicate in the event that employee was set
        if (ttEntry.getEmployee() != null && ttEntry.getEmployee().getEmployeeId() != null) {
            List<TimetableEntry> timetableEntries =
                    timetableEntryRepo.findDuplicateEntries(ttEntry.getWeekday().getCode(),
                    ttEntry.getClassPeriod().getClassPeriodId(),
                    ttEntry.getEmployee().getEmployeeId());
            if (!timetableEntries.isEmpty()) {
                throw new BusinessException(ErrorConstants.DUPLICATION_DETECTED);
            }
        }
        //verify that the ttEntry conform to timetable
        //preference settings for the school
        verifyTimetableWithSchoolPref(ttEntry);
        try {
            ttEntry.setModifiedTime(new Date());
            ttEntry.setStatus(CommonConstants.STATUS_ACTIVE);
            //save
            return timetableEntryRepo.save(ttEntry);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public TimetableEntry findTimetableEntry(Integer timetableEntryId) {
        TimetableEntry entry = timetableEntryRepo.findOne(timetableEntryId);
        if (entry == null) {
            entry = new TimetableEntry();
        }
        return entry;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public TtEntryTable findClassTimetable(Integer schoolClassId, SchoolPref schoolPref) {
        String schoolDayPolicy = schoolPref.getAvailDays();
        List<Weekday> weekdays = getWeekdays(schoolDayPolicy);

        SchoolClass schoolClass = schoolClassRepo.findOne(schoolClassId);
        List<ClassPeriod> classPeriods = classPeriodRepo.findClassPeriods(
                schoolClass.getSchoolSection().getSchoolSectionId());
        //create the definition for TtEntryTable data structure
        TtEntryTable ttEntryTable = new TtEntryTable();
        ttEntryTable.setClassPeriods(classPeriods);
        ttEntryTable.setWeekdays(weekdays);
        //get all relevant time table entry for the class
        List<TimetableEntry> timetableEntries =
                timetableEntryRepo.findTimetableEntriesForClass(schoolClassId);
        ttEntryTable.addTimetableEntries(timetableEntries);

        return ttEntryTable;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public List<TimetableEntry> findTeacherTimetable(Integer employeeId) {
        return timetableEntryRepo.findTimetableEntriesForTeacher(employeeId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(readOnly = true)
    public TtEntryTable findStudentTimetable(Integer schoolStudentId) {
        SchoolStudent schoolStudent = schoolStudentRepo.findOne(schoolStudentId);
        if (schoolStudent == null) {
            //throw exception
        }
        SchoolClass schoolClass = schoolStudent.getSchoolClass();
        if (schoolClass.isSameSubjectFlag()) {
            //return findClassTimetable(schoolClass.getSchoolClassId());
            return null;
        } else {
            List<SchoolSubject> schoolSubjects = studentSubjectRepo.findSubjectByStudent(schoolStudentId);
            return null;
            //return timetableEntryRepo.findTimetableEntriesForSubjects(schoolSubjects);
        }
    }

//    /**
//     * {@inheritDoc }
//     */
//    @Override
//    public void saveTimetableEntry(TimetableEntry timetableEntry) throws BusinessException {
//        if (timetableEntry.getTimetableEntryId() == null) {
//            throw new BusinessException("NULL ID - cannot perform update");
//        }
//        List<TimetableEntry> timetableEntries =
//                timetableEntryRepo.findDuplicateEntries(timetableEntry.getWeekday().getCode(),
//                timetableEntry.getSchoolSubject().getSchoolSubjectId(),
//                timetableEntry.getClassPeriod().getClassPeriodId(),
//                timetableEntry.getSchoolClass().getSchoolClassId());
//        if (!timetableEntries.isEmpty()
//                && !timetableEntries.get(0).equals(timetableEntry)) {
//            throw new BusinessException("DUPLICATE_ENTRY - week day, subject, time, class");
//        }
//        try {
//            timetableEntry.setModifiedTime(new Date());
//            //save
//            timetableEntryRepo.save(timetableEntry);
//        } catch (Exception ex) {
//            LOG.log(Level.SEVERE, null, ex);
//            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
//        }
//    }
    /**
     * {@inheritDoc }
     */
    @Override
    public void deleteTimetableEntry(TimetableEntry timetableEntry) throws BusinessException {
        try {
            timetableEntryRepo.delete(timetableEntry);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Holiday createHoliday(Holiday holiday) throws BusinessException {
        holiday.setStatus(CommonConstants.STATUS_ACTIVE);
        holiday.setModifiedTime(new Date());
        try {
            holiday = holidayRepo.save(holiday);
            //create an event for this holiday
            Event event = new Event();
            event.setDescription(holiday.getDescription());
            event.setEndDate(holiday.getEndDate());
            event.setEventType(new EventType(3));
            event.setHolidayId(holiday.getHolidayId());
            event.setModifiedBy(holiday.getModifiedBy());
            event.setModifiedTime(new Date());
            event.setParticipantCategory(
                    new ParticipantCategory("" + CommonConstants.PART_CAT_STAFF));
            event.setSchoolYearId(holiday.getSchoolYearId());
            event.setStartDate(holiday.getStartDate());
            event.setStatus(holiday.getStatus());
            event.setTitle(holiday.getDescription());

            eventRepo.save(event);

            return holiday;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Holiday findHoliday(Integer holidayId) throws BusinessException {
        return holidayRepo.findOne(holidayId);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Holiday> findHolidays(HolidayQueryCriteria criteria) throws BusinessException {
        return calendarCriteriaSearchRepo.findHolidays(criteria);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Holiday saveHoliday(Holiday holiday) throws BusinessException {
        if (holiday.getHolidayId() == null) { //this is new
            return createHoliday(holiday);
        }
        holiday.setModifiedTime(new Date());
        try {
            //edit its corresponding holiday
            holiday = holidayRepo.save(holiday);
            //create an event for this holiday
            Event event = eventRepo.findEventForHoliday(holiday.getHolidayId());
            event.setDescription(holiday.getDescription());
            event.setEndDate(holiday.getEndDate());
            event.setModifiedBy(holiday.getModifiedBy());
            event.setModifiedTime(new Date());
            event.setSchoolYearId(holiday.getSchoolYearId());
            event.setStartDate(holiday.getStartDate());
            event.setTitle(holiday.getDescription());

            eventRepo.save(event);
            return holidayRepo.save(holiday);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new BusinessException(StringUtil.getNestedErrorMessage(ex));
        }
    }

    /**
     *
     * @param schoolDayPolicy
     * @return
     */
    private List<Weekday> getWeekdays(String schoolDayPolicy) {
        List<Weekday> weekdays = weekdayRepo.findAll();
        switch (schoolDayPolicy) {
            case "WEEKEND_WITH_FRIDAY":
                for (int i = 2; i <= 5; i++) {
                    weekdays.remove(new Weekday(String.valueOf(i)));
                }
                break;
            case "WEEKENDS":
                for (int i = 2; i <= 6; i++) {
                    weekdays.remove(new Weekday(String.valueOf(i)));
                }
                break;
            case "WEEKDAYS":
                weekdays.remove(new Weekday(String.valueOf(1)));
                weekdays.remove(new Weekday(String.valueOf(7)));
                break;
            case "ALL_DAYS":
        }
        return weekdays;
    }

    /**
     * verify that the structure of the timetable object conforms to the school
     * timetable settings
     *
     * @param ttEntry
     * @throws {@link BusinessException}
     */
    private void verifyTimetableWithSchoolPref(TimetableEntry ttEntry) {
        //todo
    }
}