package com.example.zpw10018.mycalendarview.calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.zpw10018.mycalendarview.calendar.mode.CalendarDayData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import static java.util.Calendar.DATE;

/**
 * Created by zpw10018 on 2016/12/21.
 */

public abstract class CalendarPagerView extends ViewGroup {
    protected static final int DEFAULT_DAYS_IN_WEEK = 7;
    protected static final int DEFAULT_MAX_WEEKS = 6;
    protected static final int DAY_NAMES_ROW = 1;
    private static final Calendar tempWorkingCalendar = CalendarUtils.getInstance();
    private final ArrayList<WeekDayView> weekDayViews = new ArrayList<>();

    private CalendarDayData firstViewDay;
    private CalendarDayData minDate = null;
    private CalendarDayData maxDate = null;
    private int firstDayOfWeek;

    private final ArrayList<DayView> dayViews = new ArrayList<>();


    public CalendarPagerView(Context context, CalendarDayData firstViewDay,
                             int firstDayOfWeek) {
        super(context);
        this.firstViewDay = firstViewDay;
        this.firstDayOfWeek = firstDayOfWeek;
        buildWeekDays(resetAndGetWorkingCalendar());
        buildDayViews(dayViews, resetAndGetWorkingCalendar());
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

        //The spec width should be a correct multiple
        final int measureTileWidth = specWidthSize / DEFAULT_DAYS_IN_WEEK;
        final int measureTileHeight = specHeightSize / getRows();

        //Just use the spec sizes
        setMeasuredDimension(specWidthSize, specHeightSize);

        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    measureTileWidth,
                    MeasureSpec.EXACTLY
            );

            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    measureTileHeight,
                    MeasureSpec.EXACTLY
            );

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();

        final int parentLeft = 0;

        int childTop = 0;
        int childLeft = parentLeft;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            child.layout(childLeft, childTop, childLeft + width, childTop + height);

            childLeft += width;

            //We should warp every so many children
            if (i % DEFAULT_DAYS_IN_WEEK == (DEFAULT_DAYS_IN_WEEK - 1)) {
                childLeft = parentLeft;
                childTop += height;
            }

        }
    }

    protected abstract int getRows();

    private void buildWeekDays(Calendar calendar) {
        for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
            WeekDayView weekDayView = new WeekDayView(getContext(), CalendarUtils.getDayOfWeek(calendar));
            weekDayViews.add(weekDayView);
            addView(weekDayView);
            calendar.add(DATE, 1);
        }
    }

    public void setMinimumDate(CalendarDayData minDate) {
        this.minDate = minDate;
        updateUi();
    }

    public void setMaximumDate(CalendarDayData maxDate) {
        this.maxDate = maxDate;
        updateUi();
    }

    protected void updateUi() {
        for (DayView dayView : dayViews) {
            CalendarDayData day = dayView.getDate();
            //todo 设置dayView 是否显示本月之外的数据等
//            dayView.setupSelection(
//                    showOtherDates, day.isInRange(minDate, maxDate), isDayEnabled(day));
        }
        postInvalidate();
    }

    public void setSelectedDates(Collection<CalendarDayData> dates) {
        for (DayView dayView : dayViews) {
            CalendarDayData day = dayView.getDate();
            //todo 设置 dayView 选中状态
//            dayView.setChecked(dates != null && dates.contains(day));
        }
        postInvalidate();
    }

    protected abstract void buildDayViews(ArrayList<DayView> dayViews, Calendar calendar);

    protected Calendar resetAndGetWorkingCalendar() {
        getFirstViewDay().copyTo(tempWorkingCalendar);
        //noinspection ResourceType
        tempWorkingCalendar.setFirstDayOfWeek(getFirstDayOfWeek());
        //得到这天是周的第几天
        int dow = CalendarUtils.getDayOfWeek(tempWorkingCalendar);
        //距离周的第一天相差多少
        int delta = getFirstDayOfWeek() - dow;
        //If the delta is positive, we want to remove a week
//        boolean removeRow = showOtherMonths(showOtherDates) ? delta >= 0 : delta > 0;
//        if (removeRow) {
//            delta -= DEFAULT_DAYS_IN_WEEK;
//        }
        tempWorkingCalendar.add(DATE, delta);
        return tempWorkingCalendar;
    }

    protected CalendarDayData getFirstViewDay() {
        return firstViewDay;
    }

    protected int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }
}
