package com.example.zpw10018.mycalendarview.calendar.mode;


import android.support.annotation.NonNull;

import com.example.zpw10018.mycalendarview.calendar.CalendarUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zpw10018 on 2016/12/20.
 */

public class CalendarDayData {
    private final int year;
    private final int month;
    private final int day;

    private Date date;
    private Calendar calendar;

    public CalendarDayData(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date getDate() {
        if (date == null) {
            date = getCalendar().getTime();
        }
        return date;
    }

    public Calendar getCalendar() {
        if (calendar == null) {
            calendar = CalendarUtils.getInstance();
            copyTo(calendar);
        }
        return calendar;
    }

    public void copyTo(@NonNull Calendar calendar) {
        calendar.clear();
        calendar.set(year, month, day);
    }

    public static CalendarDayData from(int year, int month, int day) {
        return new CalendarDayData(year, month, day);
    }

    public boolean isBefore(@NonNull CalendarDayData other) {
        if (other == null) {
            throw new IllegalArgumentException("other cannot be null");
        }
        if (year == other.year) {
            return ((month == other.month) ? (day < other.day) : (month < other.month));
        } else {
            return year < other.year;
        }
    }

    /**
     * Determine if this day is after the given instance
     *
     * @param other the other day to test
     * @return true if this is after other, false if equal or before
     */
    public boolean isAfter(@NonNull CalendarDayData other) {
        if (other == null) {
            throw new IllegalArgumentException("other cannot be null");
        }

        if (year == other.year) {
            return (month == other.month) ? (day > other.day) : (month > other.month);
        } else {
            return year > other.year;
        }
    }


    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}