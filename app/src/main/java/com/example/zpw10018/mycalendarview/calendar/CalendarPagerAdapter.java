package com.example.zpw10018.mycalendarview.calendar;

import android.support.v4.view.PagerAdapter;

import com.example.zpw10018.mycalendarview.calendar.mode.CalendarDayData;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zpw10018 on 2016/12/21.
 */

abstract class CalendarPagerAdapter<V extends CalendarPagerView> extends PagerAdapter {
    private final ArrayDeque<V> currentViews;
    private List<CalendarDayData> selectedDates = new ArrayList<>();
    private final CalendarDayData today;
    private CalendarDayData minDate;
    private CalendarDayData maxDate;

    private DateRangeIndex rangeIndex;

    protected CalendarPagerAdapter(ArrayDeque<V> currentViews, CalendarDayData today) {
        this.currentViews = currentViews;
        this.today = today;
        currentViews = new ArrayDeque<>();
        currentViews.iterator();
    }


    public void setRangeDates(CalendarDayData min, CalendarDayData max) {
        this.minDate = min;
        this.maxDate = max;
        for (V pagerView : currentViews) {
            pagerView.setMinimumDate(min);
            pagerView.setMaximumDate(max);
        }

        if (min == null) {
            min = CalendarDayData.from(today.getYear() - 200, today.getMonth(), today.getDay());
        }

        if (max == null) {
            max = CalendarDayData.from(today.getYear() + 200, today.getMonth(), today.getDay());
        }

        rangeIndex = createRangeIndex(min, max);

        notifyDataSetChanged();
        invalidateSelectedDates();
    }

    private void invalidateSelectedDates() {
        validateSelectedDates();
        for (V pagerView : currentViews) {
            pagerView.setSelectedDates(selectedDates);
        }
    }

    private void validateSelectedDates() {
        for (int i = 0; i < selectedDates.size(); i++) {
            CalendarDayData date = selectedDates.get(i);

            if ((minDate != null && minDate.isAfter(date)) || (maxDate != null && maxDate.isBefore(date))) {
                selectedDates.remove(i);
                mcv.onDateUnselected(date);
                i -= 1;
            }
        }
    }

    protected abstract DateRangeIndex createRangeIndex(CalendarDayData min, CalendarDayData max);
}
