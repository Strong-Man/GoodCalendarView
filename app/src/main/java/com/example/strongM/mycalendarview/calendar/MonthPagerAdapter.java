package com.example.strongM.mycalendarview.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;


public class MonthPagerAdapter extends CalendarPagerAdapter<MonthView> {


    protected MonthPagerAdapter(ICalendarView calendarView, Context context) {
        super(calendarView, context);
    }

    @Override
    protected MonthView createView(int position) {
        return new MonthView(context, getItem(position), calendarView.getFirstDayOfWeek(), calendarView.getWeekTitleHeight());
    }

    @Override
    protected int indexOf(MonthView view) {
        CalendarDayData month = view.getFirstDayOfMonth();
        return getRangeIndex().indexOf(month);
    }

    @Override
    protected boolean isInstanceOfView(Object object) {
        return object instanceof MonthView;
    }

    @Override
    protected DateRangeIndex createRangeIndex(CalendarDayData min, CalendarDayData max) {
        return new Monthly(min, max);
    }


    public static class Monthly implements DateRangeIndex {

        private final CalendarDayData min;
        private final int count;

        private SparseArrayCompat<CalendarDayData> dayCache = new SparseArrayCompat<CalendarDayData>();

        public Monthly(@NonNull CalendarDayData min, @NonNull CalendarDayData max) {
            this.min = CalendarDayData.from(min.getYear(), min.getMonth(), 1);
            max = CalendarDayData.from(max.getYear(), max.getMonth(), 1);
            this.count = indexOf(max) + 1;
        }

        public int getCount() {
            return count;
        }

        public int indexOf(CalendarDayData day) {
            int yDiff = day.getYear() - min.getYear();
            int mDiff = day.getMonth() - min.getMonth();

            return (yDiff * 12) + mDiff;
        }

        //viewpager当前显示页的月的第一天
        public CalendarDayData getItem(int position) {

            CalendarDayData re = dayCache.get(position);
            if (re != null) {
                return re;
            }

            int numY = position / 12;
            int numM = position % 12;

            int year = min.getYear() + numY;
            int month = min.getMonth() + numM;
            if (month >= 12) {
                year += 1;
                month -= 12;
            }

            re = CalendarDayData.from(year, month, 1);
            dayCache.put(position, re);
            return re;
        }
    }
}
