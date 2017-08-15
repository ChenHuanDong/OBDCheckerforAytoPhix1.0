package com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.obdcheckerforaytophix10.R;

/**
 * Created by CHD on 2017/8/15.
 */
//          1           2
//          3           4
//          5           6

//(float) ((38.0 / 667.0) * getWidth())
//(float) ((38.0 / 375.0) * getHeight())
public class DashboardsHUDView extends View {

    private Paint mPaint = new Paint();

    public DashboardsHUDView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(getResources().getColor(R.color.colorWhite));
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    public DashboardsHUDView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DashboardsHUDView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //28  换成28 把

        //先画个的那些线
        drawLine(canvas);
        //画左上角的图片     在布局里面画
        //drawIcon(canvas);
        //2
        drawLocationTwo(canvas);
        //3
        drawLocationThree(canvas);
        //4
        drawLocationFour(canvas);
        //5
        drawLocationFive(canvas);
        //6
        drawLocationSix(canvas);

    }

    //6
    private void drawLocationSix(Canvas canvas) {
        canvas.save();
        canvas.translate(0, getHeight() * 2 / 3);
        drawLocationTwo(canvas);
        canvas.restore();
    }

    //5
    private void drawLocationFive(Canvas canvas) {
        canvas.save();
        canvas.translate(-getWidth() / 2, getHeight() * 2 / 3);
        drawLocationTwo(canvas);
        canvas.restore();
    }

    //4
    private void drawLocationFour(Canvas canvas) {
        canvas.save();
        canvas.translate(0, getHeight() / 3);

        drawLocationTwo(canvas);

        canvas.restore();
    }

    //3
    private void drawLocationThree(Canvas canvas) {
        canvas.save();
        canvas.translate(-getWidth() / 2, getHeight() / 3);

        drawLocationTwo(canvas);

        canvas.restore();
    }


    //2
    private void drawLocationTwo(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.colorHUDtextColor));
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize((float) ((14.0 / 375.0) * getHeight()));

        canvas.drawText("Average fuel consumption",
                (float) ((343.0 / 667.0) * getWidth()), (float) ((20.0 / 375.0) * getHeight()), mPaint);
        canvas.drawText("L/100KM", (float) ((594.0 / 667.0) * getWidth()),
                (float) ((115.0 / 375.0) * getHeight()), mPaint);

        mPaint.setTextSize((float) ((70.0 / 375.0) * getHeight()));

        canvas.drawText("7.6", (float) ((453.0 / 667.0) * getWidth()),
                (float) ((88.0 / 375.0) * getHeight()), mPaint);


    }

//    //画左上角的图片
//    private void drawIcon(Canvas canvas) {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jingxicon_ui);
//        RectF rectF = new RectF((float) ((147.0 / 667.0) * getWidth()), (float) ((42.0 / 375.0) * getHeight()),
//                (float) ((187.0 / 667.0) * getWidth()), (float) ((87.0 / 375.0) * getHeight()));
//        canvas.drawBitmap(bitmap, null, rectF, mPaint);
//    }


    //先画个的那些线
    private void drawLine(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.colorDialogBackGround));
        canvas.drawLine(0, getHeight() / 3, getWidth(), getHeight() / 3, mPaint);
        canvas.drawLine(0, getHeight() * 2 / 3, getWidth(), getHeight() * 2 / 3, mPaint);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mPaint);
    }
}
