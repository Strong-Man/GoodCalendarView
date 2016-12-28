package com.example.strongM.mycalendarview.calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.strongM.mycalendarview.R;


public class RowView extends LinearLayout {
    private static final int COLUMN = 7;
    private int distanceTop, distanceBottom;

    public RowView(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_downline_common));
    }



    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() >= COLUMN) {
            return;
        }
        super.addView(child, index, params);
    }

    public void setDistanceTop(int distanceTop) {
        this.distanceTop = distanceTop;
    }

    public void setDistanceBottom(int distanceBottom) {
        this.distanceBottom = distanceBottom;
    }

    public  int getDistanceTop(){
        return  distanceTop;
    }

    public int getDistanceBottom(){
        return  distanceBottom;
    }
}
