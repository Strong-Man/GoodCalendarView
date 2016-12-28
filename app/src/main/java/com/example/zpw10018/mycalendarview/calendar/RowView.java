package com.example.zpw10018.mycalendarview.calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.zpw10018.mycalendarview.R;


/**
 * Created by zpw10018 on 2016/12/22.
 */

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
