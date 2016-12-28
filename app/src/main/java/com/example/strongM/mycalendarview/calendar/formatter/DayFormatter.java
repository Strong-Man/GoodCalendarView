package com.example.strongM.mycalendarview.calendar.formatter;

import android.support.annotation.NonNull;

import com.example.strongM.mycalendarview.calendar.CalendarDayData;



public interface DayFormatter {
    String format(@NonNull CalendarDayData day);
}
