package com.example.strongM.mycalendarview.calendar;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.example.strongM.mycalendarview.calendar.formatter.CalendarWeekDayFormatter;
import com.example.strongM.mycalendarview.calendar.formatter.WeekDayFormatter;

import java.util.Calendar;



public class WeekDayView extends TextView {
    private WeekDayFormatter mFormatter = new CalendarWeekDayFormatter();

    //一周由周几开始
    private int mDayOfWeek;

    public WeekDayView(Context context, int dayOfWeek) {
        super(context);
        mDayOfWeek = dayOfWeek;
        setGravity(Gravity.CENTER);
        setDayOfWeek(dayOfWeek);
    }

    public void setDayOfWeek(int dayOfWeek) {
        mDayOfWeek = dayOfWeek;
        setText(mFormatter.format(dayOfWeek));
    }

    public void setDayOfWeek(Calendar calendar) {
        setDayOfWeek(CalendarUtils.getDayOfWeek(calendar));
    }

    public void setWeekDayFormatter(WeekDayFormatter formatter) {
        mFormatter = formatter == null ? mFormatter : formatter;
        setDayOfWeek(mDayOfWeek);
    }

}
