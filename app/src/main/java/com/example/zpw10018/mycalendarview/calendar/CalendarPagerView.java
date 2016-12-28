package com.example.zpw10018.mycalendarview.calendar;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.example.zpw10018.mycalendarview.calendar.formatter.DayFormatter;
import com.example.zpw10018.mycalendarview.calendar.formatter.WeekDayFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import static java.util.Calendar.DATE;

/**
 * Created by zpw10018 on 2016/12/21.
 */

public abstract class CalendarPagerView extends ViewGroup {
    protected static final int DEFAULT_DAYS_IN_WEEK = 7;
    protected static final int DEFAULT_MAX_WEEKS = 5;

    private static final Calendar tempWorkingCalendar = CalendarUtils.getInstance();
    private CalendarDayData firstViewDay;
    private CalendarDayData minDate = null;
    private CalendarDayData maxDate = null;
    private int firstDayOfWeek;
    protected int titleHeight;

    //每行的（除week title）
    protected ArrayList<RowView> dayRowViews = new ArrayList<RowView>();
    protected RowView weekTitleRowView;
    protected final ArrayList<WeekDayView> weekDayViews = new ArrayList<WeekDayView>();
    protected final ArrayList<DayView> dayViews = new ArrayList<DayView>();

    private boolean isShowOtherDates = false;


    public CalendarPagerView(Context context, CalendarDayData firstViewDay,
                             int firstDayOfWeek, int titleHeight) {
        super(context);
        setClipChildren(false);
        setClipToPadding(false);

        this.firstViewDay = firstViewDay;
        this.firstDayOfWeek = firstDayOfWeek;
        this.titleHeight = titleHeight;
        buildWeekDays(resetAndGetWorkingCalendar());
        buildRowDayViews(dayViews, resetAndGetWorkingCalendar());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        //We expect to be somewhere inside a MaterialCalendarView, which should measure EXACTLY
        if (specHeightMode == MeasureSpec.UNSPECIFIED || specWidthMode == MeasureSpec.UNSPECIFIED) {
            throw new IllegalStateException("CalendarPagerView should never be left to decide it's size");
        }

        final int measureRowWidth = specWidthSize;
        final int measureRowHeight = (specHeightSize - titleHeight) / getRows();

        //Just use the spec sizes
        setMeasuredDimension(specWidthSize, specHeightSize);

        int count = getChildCount();

        Log.d("zpw", "spec: count" + count);


        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    measureRowWidth,
                    MeasureSpec.EXACTLY
            );

            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    i == 0 ? titleHeight : measureRowHeight,
                    MeasureSpec.EXACTLY
            );

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int childTop = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            child.layout(0, childTop, width, childTop + height);
            if (child instanceof RowView) {
                ((RowView) child).setDistanceTop(childTop);
                ((RowView) child).setDistanceBottom(getHeight() - childTop);
            }
            childTop += height;

        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams();
    }

    protected abstract int getRows();

    private void buildWeekDays(Calendar calendar) {
        weekTitleRowView = new RowView(getContext());
        for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
            WeekDayView weekDayView = new WeekDayView(getContext(), CalendarUtils.getDayOfWeek(calendar));
            weekDayViews.add(weekDayView);
            weekTitleRowView.addView(weekDayView, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
            calendar.add(DATE, 1);
        }
        addView(weekTitleRowView, new LayoutParams());
    }

    protected void updateUi() {
        for (DayView dayView : dayViews) {
            CalendarDayData day = dayView.getDate();
            dayView.setupSelection(
                    isShowOtherDates, day.isInRange(minDate, maxDate), isDayEnabled(day));
        }
        postInvalidate();
    }

    public void setSelectedDates(Collection<CalendarDayData> dates) {
        if (dates == null || dates.isEmpty()) {
            return;
        }
        for (DayView dayView : dayViews) {
            CalendarDayData day = dayView.getDate();
            //todo 设置 dayView 选中状态
        }
        postInvalidate();
    }

    public void setShowOtherDates(boolean iShow) {
        isShowOtherDates = iShow;
        updateUi();
    }

    protected abstract void buildRowDayViews(ArrayList<DayView> dayViews, Calendar calendar);

    protected abstract void addDayView(Collection<DayView> dayViews, Calendar calendar, RowView rowView, int dayRow);

    protected abstract boolean isDayEnabled(CalendarDayData day);
    protected Calendar resetAndGetWorkingCalendar() {
        getFirstViewDay().copyTo(tempWorkingCalendar);
        //noinspection ResourceType
        tempWorkingCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        //得到这天是周的第几天
        int dow = CalendarUtils.getDayOfWeek(tempWorkingCalendar);
        //距离周的第一天相差多少
        int delta = getFirstDayOfWeek() - dow;
        tempWorkingCalendar.add(DATE, delta);
        return tempWorkingCalendar;
    }

    protected CalendarDayData getFirstViewDay() {
        return firstViewDay;
    }

    protected int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }


    public void setMinimumDate(CalendarDayData minDate) {
        this.minDate = minDate;
        this.setRangeDate(minDate, maxDate);

    }

    public void setMaximumDate(CalendarDayData maxDate) {
        this.maxDate = maxDate;
        this.setRangeDate(minDate, maxDate);
    }

    public void setRangeDate(CalendarDayData minDate, CalendarDayData maxDate) {
        this.minDate = minDate;
        this.maxDate = maxDate;
        updateUi();
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        for (WeekDayView dayView : weekDayViews) {
            dayView.setWeekDayFormatter(formatter);
        }
    }

    public void setDayFormatter(DayFormatter formatter) {
        for (DayView dayView : dayViews) {
            dayView.setDayFormatter(formatter);
        }
    }

    protected static class LayoutParams extends MarginLayoutParams {

        /**
         * {@inheritDoc}
         */
        public LayoutParams() {
            super(MATCH_PARENT, WRAP_CONTENT);
        }
    }
}
