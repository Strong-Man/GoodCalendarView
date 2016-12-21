package com.example.zpw10018.mycalendarview.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zpw10018.mycalendarview.R;
import com.example.zpw10018.mycalendarview.calendar.formatter.DateFormatDayFormatter;
import com.example.zpw10018.mycalendarview.calendar.formatter.DayFormatter;
import com.example.zpw10018.mycalendarview.calendar.mode.CalendarDayData;

/**
 * Created by zpw10018 on 2016/12/20.
 */

public class DayView extends LinearLayout {
    private TextView mDayPrimaryTitleTv;
    private TextView mDaySecondTitleTv;
    private ImageView mDayIconIv;
    private LinearLayout mExtendInfoLayout;

    private int selectionColor = Color.GRAY;
    private int fadeTime;
    private Drawable customBackground = null;
    private Drawable selectionDrawable;
    //todo可以去掉
    private Drawable mCircleDrawable;
    private final Rect tempRect = new Rect();

    private CalendarDayData mDateData;
    private DayFormatter mFormatter = new DateFormatDayFormatter();

    public DayView(Context context, CalendarDayData dayData, int fadeTime) {
        super(context);
        mDateData = dayData;
        this.fadeTime = fadeTime;
        init();
    }

    public DayView(Context context, AttributeSet attrs, CalendarDayData dayData, int fadeTime) {
        super(context, attrs);
        mDateData = dayData;
        this.fadeTime = fadeTime;
        init();
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr, CalendarDayData dayData, int fadeTime) {
        super(context, attrs, defStyleAttr);
        mDateData = dayData;
        this.fadeTime = fadeTime;
        init();
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(R.layout.day_view, this);
        mDayPrimaryTitleTv = (TextView) content.findViewById(R.id.tv_day_primary_title);
        mDayIconIv = (ImageView) content.findViewById(R.id.iv_day_icon);
        mDaySecondTitleTv = (TextView) content.findViewById(R.id.tv_second_title);
        mExtendInfoLayout = (LinearLayout) content.findViewById(R.id.ll_extend_info);
    }


    private void regenerateBackground() {
        if (selectionDrawable != null) {
            setBackgroundDrawable(selectionDrawable);
        } else {
            mCircleDrawable = generateBackground(selectionColor, fadeTime, tempRect);
            setBackgroundDrawable(mCircleDrawable);
        }
    }

    private static Drawable generateBackground(int color, int fadeTime, Rect bounds) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.setExitFadeDuration(fadeTime);
        drawable.addState(new int[]{android.R.attr.state_selected}, generateCircleDrawable(color));
        drawable.addState(new int[]{android.R.attr.state_pressed}, generateCircleDrawable(color));
        drawable.addState(new int[]{}, generateCircleDrawable(Color.TRANSPARENT));

        return drawable;
    }

    private static Drawable generateCircleDrawable(final int color) {
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(color);
        return drawable;
    }

    public CalendarDayData getDate() {
        return mDateData;
    }


    public void setPrimaryTitle(CalendarDayData dayData) {
        mDateData = dayData;
        mDayPrimaryTitleTv.setText(getPrimaryTitle());
    }

    public String getPrimaryTitle() {
        return mFormatter.format(mDateData);
    }
}
