package com.example.strongM.mycalendarview.calendar;

import android.content.Context;
import android.widget.LinearLayout;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import static java.util.Calendar.DATE;

public class MonthView extends CalendarPagerView{

    public MonthView(Context context, CalendarDayData firstViewDay, int firstDayOfWeek, int titleHeight) {
        super(context, firstViewDay, firstDayOfWeek, titleHeight);
    }

    public CalendarDayData getFirstDayOfMonth() {
        return getFirstViewDay();
    }

    @Override
    protected boolean isDayEnabled(CalendarDayData day) {
        return day.getMonth() == getFirstViewDay().getMonth();
    }

    @Override
    protected int getRows() {
        return DEFAULT_MAX_WEEKS;
    }

    @Override
    protected void buildRowDayViews(ArrayList<DayView> dayViews, Calendar calendar) {
        for (int r = 0; r < DEFAULT_MAX_WEEKS; r++) {
            RowView rowView = new RowView(getContext());
            for (int i = 0; i < DEFAULT_DAYS_IN_WEEK; i++) {
                addDayView(dayViews, calendar, rowView, r);
            }
            addView(rowView, new LayoutParams());
            dayRowViews.add(rowView);
        }
    }

    @Override
    protected void addDayView(Collection<DayView> dayViews, Calendar calendar, RowView rowView, int dayRow) {
        CalendarDayData day = CalendarDayData.from(calendar);
        DayView dayView = new DayView(getContext(), day);
        dayViews.add(dayView);
        rowView.addView(dayView, new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
        calendar.add(DATE, 1);
        addDayViewClickListener(dayView, dayRow);

    }

    protected void addDayViewClickListener(final DayView dayView, final int dayRow) {

    }
}
