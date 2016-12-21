package com.example.zpw10018.mycalendarview.calendar.formatter;

import android.support.annotation.NonNull;

import com.example.zpw10018.mycalendarview.calendar.mode.CalendarDayData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by zpw10018 on 2016/12/20.
 */

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
