/*
 * DateUtil.java
 *
 * Created on October 30, 2007, 8:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.okmich.common.util;

import java.util.Calendar;
import java.util.Date;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

/**
 *
 * @author c-okali
 */
public class DateUtil {

    /**
     * singleton instance
     */
    private static DateUtil _instance;
    /**
     * formatter
     */
    private DateTimeFormatter formatter;

    /**
     * Creates a new instance of PaymentDateUtil
     */
    private DateUtil() {
        formatter = new DateTimeFormatterBuilder().appendDayOfMonth(2).appendLiteral('/').
                appendMonthOfYear(2).appendLiteral('/').appendYear(4, 4).toFormatter();
    }

    /**
     * returns a singleton instance of this class
     *
     * @returns PaymentDateUtil
     */
    public static DateUtil getInstance() {
        if (_instance == null) {
            _instance = new DateUtil();
        }
        return _instance;
    }

    /**
     * removes the time portion of the date field
     *
     * @param startDate
     * @return Date
     */
    public Date clearTimePortion(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * method accepts a String in the format dd/mm/yyyy and outputs a date
     * object
     *
     * @param String - the date as String
     * @return Date -
     */
    public Date getStringAsDate(String dateDesc) {
        Date date = null;
        if (dateDesc != null) {
            DateTime dateTime = formatter.parseDateTime(dateDesc);
            date = dateTime.toDate();
        }
        return date;
    }

    /**
     * method accepts a date object and outputs a String in the format
     * dd/mm/yyyy
     *
     * @param Date - the date as String
     * @return String -
     */
    public String getDateAsString(Date date) {
        String retVal = null;
        if (date != null) {
            LocalDate localDate = LocalDate.fromDateFields(date);
            retVal = formatter.print(localDate.toDateTimeAtCurrentTime());
        }
        return retVal;
    }

    /**
     * method accepts a date object and outputs a the time portion in the format
     * hh:mm:ss AM/PM
     *
     * @param Date - the date as String
     * @return String -
     */
    public String getTimeAsString(Date date) {
        String retVal = null;
        if (date != null) {
            LocalTime localTime = LocalTime.fromDateFields(date);
            String hourVal = localTime.get(DateTimeFieldType.hourOfHalfday()) + "";
            String minuteVal = localTime.get(DateTimeFieldType.minuteOfHour()) + "";
            String secondVal = localTime.get(DateTimeFieldType.secondOfMinute()) + "";
            int amPmVal = localTime.get(DateTimeFieldType.halfdayOfDay());
            retVal = hourVal + ":" + minuteVal + ":" + secondVal + " " + (amPmVal == 0 ? "AM" : "PM");
        }
        return retVal;
    }

    /**
     * method accepts a date object and outputs the full date in the format
     * dd/mm/yyyy hh:mm:ss AM/PM
     *
     * @param Date - the date as String
     * @return String -
     */
    public String getDateTimeAsString(Date date) {
        String retVal = getDateAsString(date) + " " + getTimeAsString(date);
        return retVal;
    }

    /**
     * returns the difference between two dates in terms of Days. If date1
     * passed in is greater than date2, the value return will be positive, else
     * negative except when both date are of the same months and year in which
     * case the value will be 0.
     *
     * @param Date - first date value
     * @param Date - second date value
     * @return int - difference in dates (calculated in months)
     */
    public int getDateDifferenceInDays(Date date1, Date date2) {
        LocalDate local1 = LocalDate.fromDateFields(date1);
        LocalDate local2 = LocalDate.fromDateFields(date2);
        //
        Days days = Days.daysBetween(local1, local2);
        return Math.abs(days.getDays());
    }

    /**
     * returns the difference between two dates as an int in terms of Months. If
     * date1 passed in is greater than date2, the value return will be positive,
     * else negative except when both date are of the same months and year in
     * which case the value will be 0.
     *
     * @param Date - first date value
     * @param Date - second date value
     * @return int - difference in dates (calculated in months)
     */
    public int getDateDifferenceInMonths(Date date1, Date date2) {
        int retValue = 0;
        LocalDate local1 = LocalDate.fromDateFields(date1);
        LocalDate local2 = LocalDate.fromDateFields(date2);
        //get the difference in their years value
        int diffYears = local1.getYear() - local2.getYear();
        if (diffYears != 0) {
            int diffMonth = local1.getMonthOfYear() - local2.getMonthOfYear();
            if (diffMonth != 0) {
                retValue = (diffYears * 12) + diffMonth;
            } else {
                retValue = diffYears * 12;
            }
            return retValue;
        } else {
            Period period = new Period(local1, local2);
            retValue = period.getMonths();
        }
        return retValue;
    }

    /**
     * returns an int representing the years difference between two dates
     *
     * @param date1
     * @param date2
     * @return int
     */
    public int getDateDifferenceInYears(Date date1, Date date2) {
        LocalDate local1 = LocalDate.fromDateFields(date1);
        LocalDate local2 = LocalDate.fromDateFields(date2);
        //
        Period period = new Period(local1, local2);
        return period.getYears();
    }

    /**
     * check the date value of both parameter to check if they are equal. the
     * check is based on Date values only not the time value.
     *
     * @param Date
     * @param Date
     * @return boolean - if both parameters are equal
     */
    public boolean isDateValueEqual(Date date, Date date0) {
        LocalDate local1 = LocalDate.fromDateFields(date);
        LocalDate local2 = LocalDate.fromDateFields(date0);

        return local1.equals(local2);
    }

    /**
     * checks if the String parameter received could be parse successfully into
     * a date format
     *
     * @param String - string representation of a date
     * @return boolean if the String is a true representation of a date
     */
    public boolean isValidDate(String dateAsString) {
        try {
            if (formatter.parseDateTime(dateAsString) != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * returns true if the year property of the date object passed in is a leap
     * year.
     *
     * @param Date
     * @return boolean
     */
    public boolean isLeapYear(Date date) {
        LocalDate local1 = LocalDate.fromDateFields(date);
        int year = local1.getYear();
        return (year % 4) == 0;
    }

    /**
     * returns true if the period covered by both Date values will fall in the
     * Leap year period (which means that the number of days will be 366).
     * <p></p> The sole demacator of this period is Febuary 29th of the Leap
     * Year. <p></p> So if the begin date is in a non-leap year and the end date
     * is in a leap year and the day of the end date is greater than Feb. 29.
     * Then return true.. otherwise if the begin date is in a leap year and the
     * day begin less than Feb. 29, we have a leap year. <p></p> A second and
     * more easy algorithm was to use the
     * <code>getDateDifferenceInDays() + 1</code> method to check. if the result
     * is 366, then return true else return false.
     *
     * @param beginDate
     * @param endDate
     * @return return true if the period coverd equals 366 other return false
     */
    public boolean isLeapYearPeriod(Date beginDate, Date endDate) {
        int diff = getDateDifferenceInDays(beginDate, endDate) + 1;
        if (diff == 366) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * returns a Long specifying the month in the Date object. The value return
     * is 1 based, hence January is 1 while December is 12.
     *
     * @param Date
     * @return Long
     */
    public Long getMonthIdFromDate(Date date) {
        LocalDate localDate = LocalDate.fromDateFields(date);
        int monthId = localDate.getMonthOfYear();
        return new Long(monthId);
    }

    /**
     * returns a date specifying the number of year from a particular date also
     * passed in as parameter. It is like adding (365.25 X years) days to a date
     * and returning the value as a date.
     *
     * @param Data - the start date
     * @param int - X number of years to project to
     * @return Date
     */
    public Date addYearsToDate(Date date, int years) {
        LocalDate asToday = LocalDate.fromDateFields(date);
        LocalDate nextYearDate = asToday.plus(Period.years(years));
        return getDateFromLocalDate(nextYearDate);
    }

    /**
     * returns a data plus the number of months added to it.
     *
     * @param Date - a date value
     * @param int - the number of months to be added to the Date
     * @return Date - the new Date value
     */
    public Date addMonthsToDate(Date date, int number) {
        LocalDate asToday = LocalDate.fromDateFields(date);
        LocalDate nextMonthDate = asToday.plus(Period.months(number));
        return getDateFromLocalDate(nextMonthDate);
    }

    /**
     * returns a data plus the number of days added to it.
     *
     * @param Date - a date value
     * @param int - the number of days to be added to the Date
     * @return Date - the new Date value
     */
    public Date addDaysToDate(Date date, int number) {
        LocalDate asToday = LocalDate.fromDateFields(date);
        LocalDate nextDate = asToday.plus(Period.days(number));
        return getDateFromLocalDate(nextDate);
    }

    /**
     * returns a date specifying the number of year behind a particular date
     * also passed in as parameter. It is like subtracting (365.25 X years) days
     * to a date and returning the value as a date.
     *
     * @param Data - the start date
     * @param int - X number of years to go back in time
     * @return Date
     */
    public Date subtractYearsFromDate(Date date, int years) {
        LocalDate asToday = LocalDate.fromDateFields(date);
        LocalDate nextYearDate = asToday.minus(Period.years(years));
        return getDateFromLocalDate(nextYearDate);
    }

    /**
     * returns a data plus the number of months added to it.
     *
     * @param Date - a date value
     * @param int - the number of months to be added to the Date
     * @return Date - the new Date value
     */
    public Date subtractMonthsFromDate(Date date, int number) {
        LocalDate asToday = LocalDate.fromDateFields(date);
        LocalDate nextMonthDate = asToday.minus(Period.months(number));
        return getDateFromLocalDate(nextMonthDate);
    }

    /**
     * returns a data minux the number of days passed to it.
     *
     * @param Date - a date value
     * @param int - the number of days to be added to the Date
     * @return Date - the new Date value
     */
    public Date subtractDaysFromDate(Date date, int number) {
        LocalDate asToday = LocalDate.fromDateFields(date);
        LocalDate nextDate = asToday.minus(Period.days(number));
        return getDateFromLocalDate(nextDate);
    }

    /**
     * returns a date that does not have an invalid business day. A day can be
     * said to be invalid if it falls on a weekend e.g Saturday and sunday.
     * Hence, if the day is a weekend, the day is moved to the first day of the
     * next week.
     *
     * @param - LocalDate localDate
     * @return LocalDate
     */
    public Date getNextNoneWeekendHolidayDate(final Date date) {
        LocalDate localDate = LocalDate.fromDateFields(date);
        int dayOfWeek = localDate.getDayOfWeek();
        if (dayOfWeek == DateTimeConstants.SATURDAY) {
            localDate = localDate.plusDays(2);
        } else if (dayOfWeek == DateTimeConstants.SUNDAY) {
            localDate = localDate.plusDays(1);
        }
        return getDateFromLocalDate(localDate);
    }

    /**
     * returns a date that does not have an invalid business day. A day can be
     * said to be invalid if it falls on a weekend e.g Saturday and sunday.
     * Hence, if the day is a weekend, the day is moved to the last working day
     * of the same week.
     *
     * @param - date
     * @return date
     */
    public Date getDatePriorWeekendDate(final Date date) {
        LocalDate localDate = LocalDate.fromDateFields(date);
        int dayOfWeek = localDate.getDayOfWeek();
        if (dayOfWeek == DateTimeConstants.SATURDAY) {
            localDate = localDate.minusDays(2);
        } else if (dayOfWeek == DateTimeConstants.SUNDAY) {
            localDate = localDate.minusDays(1);
        }
        return getDateFromLocalDate(localDate);
    }

    /**
     * get a java.util.Date object from a LocalDate object. The purpose of this
     * kind of design is to separate my JODA time implementation from the normal
     * code, so that without the Joda implementation, a redesign of this util
     * classes will not hamper or affect the flow of other codes.
     *
     * This method will also check the date to ensure that the date is not a
     * weekend of holiday
     *
     * @param LocalDate
     * @returns Date
     */
    private Date getDateFromLocalDate(LocalDate localDate) {
        DateTime dateTime = localDate.toDateTimeAtCurrentTime();
        return dateTime.toDate();
    }
    /////////////// date utility code from hamzat salihu ///////////////////////

    public int getDayOfMonthValueFromDate(Date date) {
        if (date == null) {
            return 0;
        }
        LocalDate asToday = LocalDate.fromDateFields(date);
        return asToday.getDayOfMonth();
    }

    public int getMonthValueFromDate(Date date) {
        if (date == null) {
            return 0;
        }
        LocalDate asToday = LocalDate.fromDateFields(date);
        return asToday.getMonthOfYear();
    }

    public int getYearValueFromDate(Date date) {
        if (date == null) {
            return 0;
        }
        LocalDate asToday = LocalDate.fromDateFields(date);
        return asToday.getYear();
    }

    public int getHourOfDayValueFromDate(Date date) {
        if (date == null) {
            return 0;
        }
        LocalTime asToday = LocalTime.fromDateFields(date);
        return asToday.getHourOfDay();
    }

    public int getMinuteOfHourValueFromDate(Date date) {
        if (date == null) {
            return 0;
        }
        LocalTime asToday = LocalTime.fromDateFields(date);
        return asToday.getMinuteOfHour();
    }

    public int getSecondOfMinuteValueFromDate(Date date) {
        if (date == null) {
            return 0;
        }
        LocalTime asToday = LocalTime.fromDateFields(date);
        return asToday.getSecondOfMinute();
    }

    public Date convertXMLDateToUtilDate(XMLGregorianCalendar xmlCalendar) {
        Calendar cal = Calendar.getInstance();
        if (xmlCalendar == null) {
            return null;
        }
        cal.set(xmlCalendar.getYear(), xmlCalendar.getMonth(), xmlCalendar.getDay(),
                xmlCalendar.getHour(), xmlCalendar.getMinute(), xmlCalendar.getSecond());
        return cal.getTime();
    }

    public XMLGregorianCalendar convertUtilDateToXMLDate(Date date) {
        XMLGregorianCalendar xmlCal = null;
        if (date == null) {
            return null;
        }
        try {
            xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            xmlCal.setYear(getYearValueFromDate(date));
            xmlCal.setMonth(getMonthValueFromDate(date));
            xmlCal.setDay(getDayOfMonthValueFromDate(date));
            xmlCal.setHour(getHourOfDayValueFromDate(date));
            xmlCal.setMinute(getMinuteOfHourValueFromDate(date));
            xmlCal.setSecond(getSecondOfMinuteValueFromDate(date));
            xmlCal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);

        } catch (DatatypeConfigurationException ex) {
            ex.printStackTrace();
        }
        return xmlCal;
    }

    public int getAgeFromDate(Date date) {
        Calendar today = Calendar.getInstance();
        Calendar birthday = Calendar.getInstance();
        //set birthday with the birthday passed in as parameter
        birthday.setTime(date);

        LocalDate birthLocal = LocalDate.fromCalendarFields(birthday);
        LocalDate todayLocal = LocalDate.fromCalendarFields(today);

        //get the Period between the two dates
        Period period = new Period(birthLocal, todayLocal);

        return period.getYears();
    }
}
