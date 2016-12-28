package com.example.zpw10018.mycalendarview.calendar;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zpw10018.mycalendarview.R;
import com.example.zpw10018.mycalendarview.calendar.formatter.DateFormatDayFormatter;
import com.example.zpw10018.mycalendarview.calendar.formatter.DayFormatter;

/**
 * Created by zpw10018 on 2016/12/20.
 */

public class DayView extends LinearLayout {
    private LinearLayout mContentLayout;
    private TextView mDayPrimaryTitleTv;
    private TextView mDaySecondTitleTv;
    private ImageView mDayIconIv;
    private LinearLayout mExtendInfoLayout;

    private CalendarDayData mDateData;
    //    private HolidayCalendarObject mHolidayCalendarObject;
    private DayFormatter mFormatter = new DateFormatDayFormatter();

    private boolean isInRange = true;
    private boolean isInMonth = true;
    private boolean isShowOtherData = false;
    private boolean isClick = true;

    public DayView(Context context, CalendarDayData dayData) {
        super(context);
        setGravity(Gravity.CENTER);

        init();
        setDay(dayData);
    }

    public DayView(Context context, CalendarDayData dayData, int fadeTime) {
        super(context);
        mDateData = dayData;
        init();
        setDay(dayData);
    }

    public DayView(Context context, AttributeSet attrs, CalendarDayData dayData, int fadeTime) {
        super(context, attrs);
        mDateData = dayData;
        init();
        setDay(dayData);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr, CalendarDayData dayData, int fadeTime) {
        super(context, attrs, defStyleAttr);
        mDateData = dayData;
        init();
        setDay(dayData);
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(R.layout.day_view, this);
        mContentLayout = (LinearLayout) content.findViewById(R.id.ll_content);
        mDayPrimaryTitleTv = (TextView) content.findViewById(R.id.tv_day_primary_title);
        mDayIconIv = (ImageView) content.findViewById(R.id.iv_day_icon);
        mDaySecondTitleTv = (TextView) content.findViewById(R.id.tv_second_title);
        mExtendInfoLayout = (LinearLayout) content.findViewById(R.id.ll_extend_info);
    }

    public CalendarDayData getDate() {
        return mDateData;
    }


    public void setDayFormatter(DayFormatter formatter) {
        mFormatter = formatter == null ? mFormatter : formatter;
        setPrimaryText(mFormatter.format(getDate()));
    }

    protected void setupSelection(boolean isShowOtherData, boolean inRange, boolean inMonth) {
        this.isShowOtherData = isShowOtherData;
        this.isInMonth = inMonth;
        this.isInRange = inRange;
        setStatus();
    }

    private void setStatus() {
        boolean shouldBeVisible = true;
        if (!isInMonth && !isShowOtherData) {
            shouldBeVisible = false;
        }

        mContentLayout.setVisibility(shouldBeVisible ? View.VISIBLE : View.INVISIBLE);
    }


    public CharSequence getText() {
        return mDayPrimaryTitleTv.getText();
    }

    public void setPrimaryText(CharSequence text) {
        mDayPrimaryTitleTv.setText(text);
    }

    public String getLabel() {
        return mFormatter.format(mDateData);
    }

    public String getPrimaryTitle() {
        return mFormatter.format(mDateData);
    }

    public void setDay(CalendarDayData date) {
        mDateData = date;
        setPrimaryText(getLabel());
    }

    public boolean isCanClick() {
        return isClick;
    }
}
