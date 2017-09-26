package com.hyphenate.easeui.ui.hx.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;

public class MyHxChronometer extends Chronometer {

    public MyHxChronometer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyHxChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHxChronometer(Context context) {
        super(context);
    }
    
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        //continue when view is hidden
        visibility = View.VISIBLE;
        super.onWindowVisibilityChanged(visibility);
    }
}
