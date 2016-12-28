package com.example.zpw10018.mycalendarview.calendar;


/**
 * Created by zpw10018 on 2016/12/21.
 */

public interface DateRangeIndex {
    int getCount();

    int indexOf(CalendarDayData day);

    CalendarDayData getItem(int position);
}
