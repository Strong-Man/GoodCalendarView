package com.example.strongM.mycalendarview.calendar.formatter;

import android.support.annotation.NonNull;


import com.example.strongM.mycalendarview.calendar.CalendarDayData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class DateFormatDayFormatter implements DayFormatter {
    private final DateFormat dateFormat;

    /**
     * Format using a default format
     */
    public DateFormatDayFormatter() {
        this.dateFormat = new SimpleDateFormat("d", Locale.getDefault());
    }

    /**
     * Format using a specific {@linkplain DateFormat}
     *
     * @param format the format to use
     */
    public DateFormatDayFormatter(@NonNull DateFormat format) {
        this.dateFormat = format;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NonNull
    public String format(@NonNull CalendarDayData day) {
        return dateFormat.format(day.getDate());
    }
}
