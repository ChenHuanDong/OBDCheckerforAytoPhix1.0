package com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.obdcheckerforaytophix10.R;

/**
 * Created by CHD on 2017/8/4.
 */
//下面的小点
public class DashboardsMainSelecterPoint extends View {

    private int r = 9;
    private boolean isSelected = false;
    private Paint mPaint;

    public DashboardsMainSelecterPoint(Context context) {
        super(context);
        initPaint();
    }


    public DashboardsMainSelecterPoint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public DashboardsMainSelecterPoint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.colorConnectDetail));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, r, mPaint);

    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
        invalidate();
    }
}
