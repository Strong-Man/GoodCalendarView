package com.example.strongM.mycalendarview.calendar;



public interface DateRangeIndex {
    int getCount();

    int indexOf(CalendarDayData day);

    CalendarDayData getItem(int position);
}
