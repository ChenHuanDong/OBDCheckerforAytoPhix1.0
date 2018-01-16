package com.example.administrator.obdcheckerforaytophix10.performance.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.obdcheckerforaytophix10.R;

/**
 * Created by CHD on 2017/12/4.
 */

public class PerformanceCircleView extends View {

    private Paint mPaint;

    public PerformanceCircleView(Context context) {
        super(context);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //只画两个  一个是底层的橙色  一个是上层的黑色
        mPaint.setColor(getResources().getColor(R.color.colorDashboardsPointer));
        canvas.drawCircle((float) getWidth() / 2, (float) getWidth() / 2, (float) getWidth() / 2, mPaint);
        mPaint.setColor(getResources().getColor(R.color.performance_text));
        canvas.drawCircle((float) getWidth() / 2, (float) getWidth() / 2, ((float) getWidth() / 2) * (124.0f / 130.0f), mPaint);


    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0f);
    }

    public PerformanceCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PerformanceCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
