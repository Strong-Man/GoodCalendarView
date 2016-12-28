package com.example.zpw10018.mycalendarview.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.zpw10018.mycalendarview.calendar.formatter.DayFormatter;
import com.example.zpw10018.mycalendarview.calendar.formatter.WeekDayFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zpw10018 on 2016/12/22.
 */

public class VerticalDisplayCalendarView extends LinearLayout implements ICalendarView {
    private ViewPager pager;
    private MonthPagerAdapter adapter;


    private CalendarDayData minDate = null;
    private CalendarDayData maxDate = null;
    private CalendarDayData currentMonth;
    private int firstDayOfWeek = 1;
    private int weekTitleHeight = 0;

    public VerticalDisplayCalendarView(Context context) {
        super(context);
        init();
    }

    public VerticalDisplayCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalDisplayCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    protected void init() {
        pager = new VerticalViewPager(getContext());
        addView(pager, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        adapter = new MonthPagerAdapter(this, getContext());
        pager.setAdapter(adapter);
        setCurrentDate(CalendarDayData.today());
        setRangeDates(minDate, maxDate);
    }

    private void setRangeDates(CalendarDayData min, CalendarDayData max) {
        CalendarDayData c = currentMonth;
        adapter.setRangeDates(min, max);
        currentMonth = c;
        if (min != null) {
            currentMonth = min.isAfter(currentMonth) ? min : currentMonth;
        }
        int position = adapter.getIndexForDay(c);
        pager.setCurrentItem(position, false);
    }

    public void setCurrentDate(CalendarDayData day) {
        if (day == null) {
            return;
        }
        int index = adapter.getIndexForDay(day);
        pager.setCurrentItem(index, true);
    }

    public void setShowOtherDates(boolean isShow) {
        adapter.setShowOtherDates(isShow);
    }

    public void clearSelection() {
        List<CalendarDayData> dates = getSelectedDates();
        adapter.clearSelections();
    }

    public void setSelectedDate(@Nullable Calendar calendar) {
        setSelectedDate(CalendarDayData.from(calendar));
    }

    public void setSelectedDate(Date date) {
        setSelectedDate(CalendarDayData.from(date));
    }

    public void setSelectedDate(@Nullable CalendarDayData date) {
        clearSelection();
        if (date != null) {
            setDateSelected(date, true);
        }
    }

    public void setDateSelected(Calendar calendar, boolean selected) {
        setDateSelected(CalendarDayData.from(calendar), selected);
    }

    public void setDateSelected(Date date, boolean selected) {
        setDateSelected(CalendarDayData.from(date), selected);
    }

    public void setDateSelected(CalendarDayData day, boolean selected) {
        if (day == null) {
            return;
        }
        adapter.setDateSelected(day, selected);
    }

    public void setCurrentDate(Calendar calendar) {
        setCurrentDate(CalendarDayData.from(calendar));
    }

    public void setCurrentDate(Date date) {
        setCurrentDate(CalendarDayData.from(date));
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        adapter.setWeekDayFormatter(formatter);
    }

    public void setDayFormatter(DayFormatter formatter) {
        adapter.setDayFormatter(formatter);
    }

    @Override
    public int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }

    @Override
    public int getWeekTitleHeight() {
        return weekTitleHeight;
    }

    public List<CalendarDayData> getSelectedDates() {
        return adapter.getSelectedDates();
    }
}
