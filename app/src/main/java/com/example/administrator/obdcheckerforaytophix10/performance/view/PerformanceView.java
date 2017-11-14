package com.example.administrator.obdcheckerforaytophix10.performance.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.LcndUtil;

/**
 * Created by CHD on 2017/10/14.
 */

public class PerformanceView extends View {

    private Paint mPaint = new Paint();
    private float angle = 0;
    private float angleSmall = 0;
    private Context mContext;


    public PerformanceView(Context context) {
        super(context);
        mContext = context;
        initPaint();
    }

    public PerformanceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public PerformanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    private void initPaint() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制底部
        drawBottom(canvas);


        //绘制大的刻度文字
        drawBigText(canvas);
        //绘制小的刻度文字
        drawSmallText(canvas);

        //绘制左边指针
        drawLeftPointer(canvas);
        //绘制右指针
        drawRightPointer(canvas);


    }

    //绘制小的刻度文字
    private void drawSmallText(Canvas canvas) {

        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTypeface(LcndUtil.getfont(mContext));
        mPaint.setTextSize(((50f / 600.0f) * getWidth()));
        mPaint.setColor(getResources().getColor(R.color.colorTextColorDemo));
        canvas.drawText("0937", ((int) ((450.0f / 600.0f) * getWidth())), ((int) ((370.0f / 432.0f) * getHeight())), mPaint);

        mPaint.setTypeface(null);

    }

    //绘制大的刻度文字
    private void drawBigText(Canvas canvas) {

        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTypeface(LcndUtil.getfont(mContext));
        mPaint.setTextSize(((105f / 600.0f) * getWidth()));
        mPaint.setColor(getResources().getColor(R.color.colorTextColorDemo));
        canvas.drawText("018", ((int) ((210.0f / 600.0f) * getWidth())), ((int) ((360.0f / 432.0f) * getHeight())), mPaint);

        mPaint.setTypeface(null);


    }

    //绘制右指针
    private void drawRightPointer(Canvas canvas) {
        canvas.save();

        canvas.translate(((int) ((454.0f / 600.0f) * getWidth())), ((int) ((202.0f / 432.0f) * getHeight())));
        canvas.rotate(angleSmall, ((int) ((4.0f / 600.0f) * getWidth())), ((int) ((86.0f / 432.0f) * getHeight())));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.performancesmall);
        RectF rectF = new RectF(0, 0, ((int) ((8.0f / 600.0f) * getWidth())), ((int) ((104.0f / 432.0f) * getHeight())));
        canvas.drawBitmap(bitmap, null, rectF, mPaint);


        canvas.restore();
    }

    //绘制左边指针
    private void drawLeftPointer(Canvas canvas) {
        canvas.save();

        canvas.translate(((int) ((82.0f / 600.0f) * getWidth())), ((int) ((196.0f / 432.0f) * getHeight())));

        canvas.rotate(angle, ((int) ((132.0f / 600.0f) * getWidth())), ((int) ((20.0f / 432.0f) * getHeight())));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.performancebig);
        RectF rectF = new RectF(0, 0, ((int) ((162.0f / 600.0f) * getWidth())), ((int) ((102.0f / 432.0f) * getHeight())));
        canvas.drawBitmap(bitmap, null, rectF, mPaint);

        canvas.restore();
    }

    //绘制底部
    private void drawBottom(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.performanceyb);
        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
        canvas.drawBitmap(bitmap, null, rectF, mPaint);
    }

    public void setAngle(float angle) {
        this.angle = angle;
        invalidate();
    }

    public float getAngle() {
        return angle;
    }


    public float getAngleSmall() {
        return angleSmall;
    }

    public void setAngleSmall(float angleSmall) {
        this.angleSmall = angleSmall;
        invalidate();
    }


}
