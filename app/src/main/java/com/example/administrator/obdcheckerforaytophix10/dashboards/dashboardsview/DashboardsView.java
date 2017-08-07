package com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.ConversionUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

/**
 * Created by CHD on 2017/8/7.
 */
//单位统一换算方法  按照适配的 x + 数值     a / 375 = x / width    x = a * width / 375
public class DashboardsView extends View {
    //375  647
    private Paint mPaint;
    private int style = 0;
    private int width;


    public DashboardsView(Context context) {
        super(context);
        initView();
    }


    public DashboardsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DashboardsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(getResources().getColor(R.color.colorWhite));
        width = 0;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (style == 0) {
            drawOutCircle(canvas);
        } else if (style == 1) {

        }


    }

    //Style  画最外层圆  黑色
    private void drawOutCircle(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.colorBlack));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);

        canvas.drawCircle((int) ConversionUtil.myWantValue(width, (float) 75.0), (int) ConversionUtil.myWantValue(width, (float) 75.0),
                (int) ConversionUtil.myWantValue(width, (float) 75.0), mPaint);
    }


    public void setWidth(int width) {
        this.width = width;
        //重新绘制
        invalidate();
    }
}
