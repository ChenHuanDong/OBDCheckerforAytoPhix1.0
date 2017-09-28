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
    //判断横屏竖屏
    private int orientation = 1;

    private String one_title = "Speed";
    private String one_value = "0";
    private String one_untis = "KM/H";
    private String two_title = "Average fuel consumption";
    private String two_value = "7.6";
    private String two_untis = "L/100KM";
    private String three_title = "Speed";
    private String three_value = "0";
    private String three_untis = "KM/H";
    private String four_title = "Instantaneous fuel consumption";
    private String four_value = "5.6";
    private String four_untis = "L/100KM";
    private String five_title = "Rotational Speed\n";
    private String five_value = "2500";
    private String five_untis = "R/MIN";
    private String six_title = "Mileage";
    private String six_value = "888";
    private String six_untis = "KM";
    //颜色的ID
    private int colorId = R.color.colorHUDtextColor;


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

        //根据传过来的值判断是横屏竖屏   画对应的自定义View  为的是用同一个控件同一个Id

        if (orientation == 2) {
            //先画个的那些线
            drawLine(canvas);
            mPaint.setColor(getResources().getColor(colorId));
            //1
            drawIcon(canvas);
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
        } else if (orientation == 1) {
            drawshuLine(canvas);
            mPaint.setColor(getResources().getColor(colorId));
            //1
            drawShuOne(canvas);
            //2
            drawShuTwo(canvas);
            //3
            drawShuThree(canvas);

        }


    }

    //3
    private void drawShuThree(Canvas canvas) {
        canvas.save();
        canvas.translate(0, getHeight() * 2 / 3);

        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize((float) ((14.0 / 375.0) * getWidth()));

        canvas.drawText(three_title,
                (float) ((20.0 / 375.0) * getWidth()), (float) ((20.0 / 667.0) * getHeight()), mPaint);

        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(three_untis,
                (float) ((355.0 / 375.0) * getWidth()), (float) ((203.0 / 667.0) * getHeight()), mPaint);

        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize((float) ((70.0 / 375.0) * getWidth()));
        canvas.drawText(three_value,
                (float) ((188.0 / 376.0) * getWidth()), (float) ((131.0 / 667.0) * getHeight()), mPaint);


        canvas.restore();

    }

    //2
    private void drawShuTwo(Canvas canvas) {
        canvas.save();
        canvas.translate(0, getHeight() / 3);

        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize((float) ((14.0 / 375.0) * getWidth()));

        canvas.drawText(two_title,
                (float) ((20.0 / 375.0) * getWidth()), (float) ((20.0 / 667.0) * getHeight()), mPaint);

        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(two_untis,
                (float) ((355.0 / 375.0) * getWidth()), (float) ((203.0 / 667.0) * getHeight()), mPaint);

        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize((float) ((70.0 / 375.0) * getWidth()));
        canvas.drawText(two_value,
                (float) ((188.0 / 376.0) * getWidth()), (float) ((131.0 / 667.0) * getHeight()), mPaint);


        canvas.restore();
    }

    private void drawShuOne(Canvas canvas) {
        canvas.save();

        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize((float) ((14.0 / 375.0) * getWidth()));

        canvas.drawText(one_title,
                (float) ((20.0 / 375.0) * getWidth()), (float) ((20.0 / 667.0) * getHeight()), mPaint);

        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(one_untis,
                (float) ((355.0 / 375.0) * getWidth()), (float) ((203.0 / 667.0) * getHeight()), mPaint);

        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize((float) ((70.0 / 375.0) * getWidth()));
        canvas.drawText("7.6",
                (float) ((188.0 / 376.0) * getWidth()), (float) ((131.0 / 667.0) * getHeight()), mPaint);

        canvas.restore();

    }

    //绘制竖屏的线
    private void drawshuLine(Canvas canvas) {

        mPaint.setColor(getResources().getColor(R.color.colorDialogBackGround));
        canvas.drawLine(0, getHeight() / 3, getWidth(), getHeight() / 3, mPaint);
        canvas.drawLine(0, getHeight() * 2 / 3, getWidth(), getHeight() * 2 / 3, mPaint);

    }

    //1
    private void drawIcon(Canvas canvas) {
        canvas.save();
        canvas.translate(-getWidth() / 2, 0);

        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize((float) ((14.0 / 375.0) * getHeight()));

        canvas.drawText(one_title,
                (float) ((343.0 / 667.0) * getWidth()), (float) ((20.0 / 375.0) * getHeight()), mPaint);

        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(one_untis, (float) ((652.0 / 667.0) * getWidth()),
                (float) ((115.0 / 375.0) * getHeight()), mPaint);

        mPaint.setTextSize((float) ((70.0 / 375.0) * getHeight()));

        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(one_value, (float) ((500.0 / 667.0) * getWidth()),
                (float) ((88.0 / 375.0) * getHeight()), mPaint);

        canvas.restore();
    }

    //6
    private void drawLocationSix(Canvas canvas) {
        canvas.save();
        canvas.translate(0, getHeight() * 2 / 3);

        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize((float) ((14.0 / 375.0) * getHeight()));

        canvas.drawText(six_title,
                (float) ((343.0 / 667.0) * getWidth()), (float) ((20.0 / 375.0) * getHeight()), mPaint);

        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(six_untis, (float) ((652.0 / 667.0) * getWidth()),
                (float) ((115.0 / 375.0) * getHeight()), mPaint);

        mPaint.setTextSize((float) ((70.0 / 375.0) * getHeight()));

        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(six_value, (float) ((500.0 / 667.0) * getWidth()),
                (float) ((88.0 / 375.0) * getHeight()), mPaint);


        canvas.restore();
    }

    //5
    private void drawLocationFive(Canvas canvas) {
        canvas.save();
        canvas.translate(-getWidth() / 2, getHeight() * 2 / 3);

        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize((float) ((14.0 / 375.0) * getHeight()));

        canvas.drawText(five_title,
                (float) ((343.0 / 667.0) * getWidth()), (float) ((20.0 / 375.0) * getHeight()), mPaint);

        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(five_untis, (float) ((652.0 / 667.0) * getWidth()),
                (float) ((115.0 / 375.0) * getHeight()), mPaint);

        mPaint.setTextSize((float) ((70.0 / 375.0) * getHeight()));

        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(five_value, (float) ((500.0 / 667.0) * getWidth()),
                (float) ((88.0 / 375.0) * getHeight()), mPaint);


        canvas.restore();
    }

    //4
    private void drawLocationFour(Canvas canvas) {
        canvas.save();
        canvas.translate(0, getHeight() / 3);

        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize((float) ((14.0 / 375.0) * getHeight()));

        canvas.drawText(four_title,
                (float) ((343.0 / 667.0) * getWidth()), (float) ((20.0 / 375.0) * getHeight()), mPaint);

        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(four_untis, (float) ((652.0 / 667.0) * getWidth()),
                (float) ((115.0 / 375.0) * getHeight()), mPaint);

        mPaint.setTextSize((float) ((70.0 / 375.0) * getHeight()));

        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(four_value, (float) ((500.0 / 667.0) * getWidth()),
                (float) ((88.0 / 375.0) * getHeight()), mPaint);


        canvas.restore();
    }

    //3
    private void drawLocationThree(Canvas canvas) {
        canvas.save();
        canvas.translate(-getWidth() / 2, getHeight() / 3);

        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize((float) ((14.0 / 375.0) * getHeight()));

        canvas.drawText(three_title,
                (float) ((343.0 / 667.0) * getWidth()), (float) ((20.0 / 375.0) * getHeight()), mPaint);

        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(three_untis, (float) ((652.0 / 667.0) * getWidth()),
                (float) ((115.0 / 375.0) * getHeight()), mPaint);

        mPaint.setTextSize((float) ((70.0 / 375.0) * getHeight()));

        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(three_value, (float) ((500.0 / 667.0) * getWidth()),
                (float) ((88.0 / 375.0) * getHeight()), mPaint);

        canvas.restore();
    }


    //2
    private void drawLocationTwo(Canvas canvas) {
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize((float) ((14.0 / 375.0) * getHeight()));

        canvas.drawText(two_title,
                (float) ((343.0 / 667.0) * getWidth()), (float) ((20.0 / 375.0) * getHeight()), mPaint);

        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(two_untis, (float) ((652.0 / 667.0) * getWidth()),
                (float) ((115.0 / 375.0) * getHeight()), mPaint);

        mPaint.setTextSize((float) ((70.0 / 375.0) * getHeight()));

        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(two_value, (float) ((500.0 / 667.0) * getWidth()),
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


    public void setOrientation(int orientation) {
        this.orientation = orientation;
        invalidate();
    }

    public void setOne_title(String one_title) {
        this.one_title = one_title;
        invalidate();
    }

    public void setOne_value(String one_value) {
        this.one_value = one_value;
        invalidate();
    }

    public void setOne_untis(String one_untis) {
        this.one_untis = one_untis;
        invalidate();
    }

    public void setTwo_title(String two_title) {
        this.two_title = two_title;
        invalidate();
    }

    public void setTwo_value(String two_value) {
        this.two_value = two_value;
        invalidate();
    }

    public void setTwo_untis(String two_untis) {
        this.two_untis = two_untis;
        invalidate();
    }

    public void setThree_title(String three_title) {
        this.three_title = three_title;
        invalidate();
    }

    public void setThree_value(String three_value) {
        this.three_value = three_value;
        invalidate();
    }

    public void setThree_untis(String three_untis) {
        this.three_untis = three_untis;
        invalidate();
    }

    public void setFour_title(String four_title) {
        this.four_title = four_title;
        invalidate();
    }

    public void setFour_value(String four_value) {
        this.four_value = four_value;
        invalidate();
    }

    public void setFour_untis(String four_untis) {
        this.four_untis = four_untis;
        invalidate();
    }

    public void setFive_title(String five_title) {
        this.five_title = five_title;
        invalidate();
    }

    public void setFive_value(String five_value) {
        this.five_value = five_value;
        invalidate();
    }

    public void setFive_untis(String five_untis) {
        this.five_untis = five_untis;
        invalidate();
    }

    public void setSix_title(String six_title) {
        this.six_title = six_title;
        invalidate();
    }

    public void setSix_value(String six_value) {
        this.six_value = six_value;
        invalidate();
    }

    public void setSix_untis(String six_untis) {
        this.six_untis = six_untis;
        invalidate();
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
        invalidate();
    }
}
