package com.example.zpw10018.mycalendarview.calendar.formatter;

import android.support.annotation.NonNull;

import com.example.zpw10018.mycalendarview.calendar.mode.CalendarDayData;

/**
 * Created by zpw10018 on 2016/12/20.
 */

public interface DayFormatter {
    String format(@NonNull CalendarDayData day);
}
