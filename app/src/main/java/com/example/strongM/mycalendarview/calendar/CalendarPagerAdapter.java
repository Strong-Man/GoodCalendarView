package com.example.strongM.mycalendarview.calendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import com.example.strongM.mycalendarview.calendar.formatter.CalendarWeekDayFormatter;
import com.example.strongM.mycalendarview.calendar.formatter.DateFormatDayFormatter;
import com.example.strongM.mycalendarview.calendar.formatter.DayFormatter;
import com.example.strongM.mycalendarview.calendar.formatter.TitleFormatter;
import com.example.strongM.mycalendarview.calendar.formatter.WeekDayFormatter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


abstract class CalendarPagerAdapter<V extends CalendarPagerView> extends PagerAdapter {
    protected ICalendarView calendarView;
    private final ArrayDeque<V> currentViews;
    private List<CalendarDayData> selectedDates = new ArrayList<CalendarDayData>();
    private final CalendarDayData today;
    private CalendarDayData minDate;
    private CalendarDayData maxDate;

    private DateRangeIndex rangeIndex;
    private TitleFormatter titleFormatter = null;
    private WeekDayFormatter weekDayFormatter = new CalendarWeekDayFormatter();
    private DayFormatter dayFormatter = new DateFormatDayFormatter();
    protected Context context;

    private boolean selectionEnabled = true;
    private boolean isShowOtherDates = false;

    protected CalendarPagerAdapter(ICalendarView calendarView, Context context) {
        this.calendarView = calendarView;
        this.today = CalendarDayData.today();
        this.context = context;
        currentViews = new ArrayDeque<V>();
        currentViews.iterator();

        setRangeDates(null, null);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleFormatter == null ? "" : titleFormatter.format(getItem(position));
    }

    @Override
    public int getCount() {
        Log.d("zpw", "count: " + rangeIndex.getCount());
        return rangeIndex.getCount();
    }

    @Override
    public int getItemPosition(Object object) {
        if (!(isInstanceOfView(object))) {
            return POSITION_NONE;
        }
        CalendarPagerView pagerView = (CalendarPagerView) object;
        CalendarDayData firstViewDay = pagerView.getFirstViewDay();
        if (firstViewDay == null) {
            return POSITION_NONE;
        }
        int index = indexOf((V) object);
        if (index < 0) {
            return POSITION_NONE;
        }
        return index;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        V pagerView = createView(position);
        pagerView.setWeekDayFormatter(weekDayFormatter);
        pagerView.setDayFormatter(dayFormatter);
        pagerView.setMinimumDate(minDate);
        pagerView.setMaximumDate(maxDate);
        pagerView.setSelectedDates(selectedDates);

        container.addView(pagerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        currentViews.add(pagerView);
        return pagerView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        CalendarPagerView pagerView = (CalendarPagerView) object;
        currentViews.remove(pagerView);
        container.removeView(pagerView);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setRangeDates(CalendarDayData min, CalendarDayData max) {
        this.minDate = min;
        this.maxDate = max;
        for (V pagerView : currentViews) {
            pagerView.setMinimumDate(min);
            pagerView.setMaximumDate(max);
        }

        if (min == null) {
            min = CalendarDayData.from(today.getYear() - 1, today.getMonth(), today.getDay());
        }

        if (max == null) {
            max = CalendarDayData.from(today.getYear() + 1, today.getMonth(), today.getDay());
        }

        rangeIndex = createRangeIndex(min, max);

        notifyDataSetChanged();
        invalidateSelectedDates();
    }

    public void setShowOtherDates(boolean isShow) {
        isShowOtherDates = isShow;
        for (V pagerView : currentViews) {
            pagerView.setShowOtherDates(isShow);
        }
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        this.weekDayFormatter = formatter;
        for (V pagerView : currentViews) {
            pagerView.setWeekDayFormatter(formatter);
        }
    }

    public void setDayFormatter(DayFormatter formatter) {
        this.dayFormatter = formatter;
        for (V pagerView : currentViews) {
            pagerView.setDayFormatter(formatter);
        }
    }

    private void invalidateSelectedDates() {
        validateSelectedDates();
        for (V pagerView : currentViews) {
            pagerView.setSelectedDates(selectedDates);
        }
    }

    private void validateSelectedDates() {
        for (int i = 0; i < selectedDates.size(); i++) {
            CalendarDayData date = selectedDates.get(i);

            if ((minDate != null && minDate.isAfter(date)) || (maxDate != null && maxDate.isBefore(date))) {
                selectedDates.remove(i);
                i -= 1;
            }
        }
    }

    protected abstract V createView(int position);

    protected abstract int indexOf(V view);

    protected abstract boolean isInstanceOfView(Object object);

    protected abstract DateRangeIndex createRangeIndex(CalendarDayData min, CalendarDayData max);

    public void clearSelections() {
        selectedDates.clear();
        invalidateSelectedDates();
    }


    public int getIndexForDay(CalendarDayData day) {
        if (day == null) {
            return getCount() / 2;
        }
        if (minDate != null && day.isBefore(minDate)) {
            return 0;
        }
        if (maxDate != null && day.isAfter(maxDate)) {
            return getCount() - 1;
        }
        return rangeIndex.indexOf(day);
    }

    public CalendarDayData getItem(int position) {
        return rangeIndex.getItem(position);
    }

    public DateRangeIndex getRangeIndex() {
        return rangeIndex;
    }

    public List<CalendarDayData> getSelectedDates() {
        return Collections.unmodifiableList(selectedDates);
    }

    public void setDateSelected(CalendarDayData day, boolean selected) {
        if (selected) {
            if (!selectedDates.contains(day)) {
                selectedDates.add(day);
                invalidateSelectedDates();
            }
        } else {
            if (selectedDates.contains(day)) {
                selectedDates.remove(day);
                invalidateSelectedDates();
            }
        }
    }
}
