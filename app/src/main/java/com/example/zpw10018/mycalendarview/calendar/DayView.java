package com.example.zpw10018.mycalendarview.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zpw10018.mycalendarview.R;

/**
 * Created by zpw10018 on 2016/12/20.
 */

public class DayView extends LinearLayout {
    private TextView mDayNumTv;
    private TextView mDayTitleTv;
    private ImageView mDayIconIv;
    private LinearLayout mExtendInfoLayout;

    public DayView(Context context) {
        super(context);
        init();
    }

    public DayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(R.layout.day_view, this);
        mDayNumTv = (TextView) content.findViewById(R.id.tv_day_num);
        mDayIconIv = (ImageView) content.findViewById(R.id.iv_day_icon);
        mDayTitleTv = (TextView) content.findViewById(R.id.tv_day_title);
        mExtendInfoLayout = (LinearLayout) content.findViewById(R.id.ll_extend_info);
    }
}
