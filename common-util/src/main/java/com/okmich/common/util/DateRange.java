/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 *
 *
 * @author Michael Enudi
 * @since 13/08/2013
 */
public class DateRange implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7218814691361129211L;
    /**
     * DAY_MILLI_SECONDS - the number of milliseconds in one day which is equal
     * to 1000 * 60 * 60 * 24
     */
    private static final int DAY_MILLI_SECONDS = 86400000;
    /**
     * _startDate - start of period
     */
    private long _startDate;
    /**
     * _endDate - end of the date range
     */
    private long _endDate;
    /**
     * the list of single dates between the ranges inclusive
     */
    private List<Date> _dates;
    /**
     * the list of single dates which should be removed from the counted dates
     */
    private List<Date> _exclusion;

    /**
     * creates an instance of this class with the startDate and endDate
     *
     * @param startDate - the beginning of the period
     * @param endDate - the end of the period
     * @throws IllegalArgumentException if any of the startDate or endDate is
     * null or if the endDate is earlier than the startDate
     */
    public DateRange(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("dates cannot be null");
        }
        startDate = clearTime(startDate);
        endDate = clearTime(endDate);
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException(
                    "start date cannot be after end date");
        }
        this._startDate = startDate == null ? 0 : startDate.getTime();
        this._endDate = endDate == null ? 0 : endDate.getTime();
        //initialize the exclusion list
        this._exclusion = new ArrayList<>();
    }

    /**
     * constructs an instance of this class from two dates and also sets the
	 * {@link IterationPolicy }
     *
     * @param startDate - the beginning of the period
     * @param endDate - the end of the period
     * @param iPolicy - - the Iteration or traversal policy
     * @throws IllegalArgumentException if any of the startDate or endDate is
     * null or if the endDate is earlier than the startDate
     */
    public DateRange(Date startDate, Date endDate, IterationPolicy iPolicy) {
        this(startDate, endDate);
        setIterationPolicy(iPolicy);
    }

    /**
     * @return the _startDate
     */
    public Date getStartDate() {
        return new Date(_startDate);
    }

    /**
     * @return the _endDate
     */
    public Date getEndDate() {
        return new Date(_endDate);
    }

    /**
     * /** returns the number of days contained by the instance of the class.
     * <p> The outcome of this class is determined by the traversal strategy
     * defined by the {@link IterationPolicy}. For example if the policy is set
     * to {@link IterationPolicy#WEEKDAYS}, then this method return the number
     * of week days contained by this date range. </p>
     *
     * @return number of days contained by this instance as defined by the
     * IterationPolicy
     */
    public long dayCount() {
        return _dates.size();
    }

    /**
     * returns an ordered list of dates in the list as defined by the
     * {@link IterationPolicy}.
     *
     * @return a list of dates (ordered) contained by this instance
     */
    public List<Date> days() {
        if (_dates == null) {
            //most likely the iteration policy has not been set so we will use 
            //the default value of IterationPolicy.WEEKDAYS
            setIterationPolicy(IterationPolicy.WEEKDAYS);
        }
        return _dates;
    }

    /**
     * sets the strategy of traversing the the range of dates for enumeration.
     *
     * @param iPolicy
     */
    public void setIterationPolicy(IterationPolicy iPolicy) {
        synchronized (this) {
            switch (iPolicy) {
                case ALL_DAYS:
                    this._dates = new AllDaysCounter().getDays(this._startDate,
                            this._endDate);
                    break;
                case WEEKDAYS:
                    this._dates = new WeekDaysCounter().getDays(this._startDate,
                            this._endDate);
                    break;
                case WEEKENDS:
                    this._dates = new WeekendCounter().getDays(this._startDate,
                            this._endDate);
                    break;
                case WEEKEND_WITH_FRIDAY:
                    this._dates = new WeekendFridayCounter().getDays(
                            this._startDate, this._endDate);
            }
            //remove excluded dates
            _dates.removeAll(_exclusion);
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return new StringBuilder("[").append(sdf.format(new Date(_startDate)))
                .append(" - ").append(sdf.format(new Date(_endDate)))
                .append("]").toString();
    }

    /**
     * removes the time portion of the date field
     *
     * @param startDate
     * @return Date
     */
    private Date clearTime(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * @param exclusion the _exclusion to set
     */
    public void setExclusions(List<Date> exclusion) {
        for (Date _d : exclusion) {
            _d = clearTime(_d);
            this._exclusion.add(_d);
        }
    }

    /**
     * An Enum that defines the various kind of policies with which the range
     * will be iterated. <p> Examples of the Constant includes ALL_DAYS,
     * WEEKDAYS, WEEKENDS, WEEKEND_WITH_FRIDAY </p>
     *
     * @author m-enudi
     */
    public static enum IterationPolicy {

        ALL_DAYS, WEEKDAYS, WEEKENDS, WEEKEND_WITH_FRIDAY;
    }

    /**
     *
     *
     * @author Michael
     */
    private interface DayCounter {

        List<Date> getDays(long d1, long d2);
    }

    /**
     *
     *
     * @author m-enudi
     */
    private class AllDaysCounter implements DayCounter {

        @Override
        public List<Date> getDays(long d1, long d2) {
            List<Date> dates = new ArrayList<>();

            for (long k = d1; k <= d2; k += DAY_MILLI_SECONDS) {
                dates.add(new Date(k));
            }
            return dates;
        }
    }

    /**
     *
     *
     * @author Michael
     */
    private class WeekDaysCounter implements DayCounter {

        List<Date> mdates = new ArrayList<>();

        WeekDaysCounter() {
        }

        @Override
        public List<Date> getDays(long d1, long d2) {
            long k = d1;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(k);

            //while we are not yet on saturday add days to the list
            while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                mdates.add(cal.getTime());

                k += DAY_MILLI_SECONDS;
                cal.setTimeInMillis(k);
            }
            //there are two days for the weekend, so we will skip two days
            k += DAY_MILLI_SECONDS * 2;
            //we should be resuming the countring from MONDAY
            for (; k <= d2; k += DAY_MILLI_SECONDS * 7) {
                addDays(k, d2);
            }
            return mdates;
        }

        private void addDays(long k, long d2) {
            //add only the first five days from the list
            for (int i = 0; i < 5 && k <= d2; i++, k += DAY_MILLI_SECONDS) {
                mdates.add(new Date(k));
            }
        }
    }

    /**
     *
     *
     * @author Michael
     */
    private class WeekendCounter implements DayCounter {

        List<Date> mdates = new ArrayList<>();

        WeekendCounter() {
        }

        @Override
        public List<Date> getDays(long d1, long d2) {
            long k = d1;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(k);

            // read up dates till we are on SATURDAY
            while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                k += DAY_MILLI_SECONDS;
                cal.setTimeInMillis(k);
            }

            // iterates all days till saturdays
            for (; k <= d2; k += DAY_MILLI_SECONDS * 7) {
                addDays(k, d2);
            }

            return mdates;
        }

        private void addDays(long k, long d2) {

            for (int i = 0; i < 2 && k <= d2; i++, k += DAY_MILLI_SECONDS) {
                mdates.add(new Date(k));
            }
        }
    }

    /**
     *
     *
     * @author Michael
     */
    private class WeekendFridayCounter implements DayCounter {

        List<Date> mdates = new ArrayList<>();

        WeekendFridayCounter() {
        }

        @Override
        public List<Date> getDays(long d1, long d2) {
            long k = d1;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(k);

            // read up dates till we are on SATURDAY
            while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                k += DAY_MILLI_SECONDS;
                cal.setTimeInMillis(k);
            }
            // iterates all days till saturdays
            for (; k <= d2; k += DAY_MILLI_SECONDS * 7) {
                addDays(k, d2);
            }
            return mdates;
        }

        private void addDays(long k, long d2) {

            for (int i = 0; i < 3 && k <= d2; i++, k += DAY_MILLI_SECONDS) {
                mdates.add(new Date(k));
            }
        }
    }
}
