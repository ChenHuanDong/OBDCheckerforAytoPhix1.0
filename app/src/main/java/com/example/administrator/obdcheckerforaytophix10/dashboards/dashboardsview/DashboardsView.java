package com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.OBDOtherStyleActivity;
import com.example.administrator.obdcheckerforaytophix10.dashboards.OBDStyleActivity;
import com.example.administrator.obdcheckerforaytophix10.main.obd.OBDPopDialog;
import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LcndUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.MathUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

/**
 * Created by CHD on 2017/8/7.
 */
//按照Px设置
//(float) ((38.0 / 300.0) * getWidth())
//按照x375设置
//(float) ((12.0 * getWidth()) / 150.0))

//单位统一换算方法 按照getWidth   getWidth 等比例来
//getWidth  想象成300px

//style 0   设置数值是value

public class DashboardsView extends View implements View.OnClickListener {
    //375  647
    private Context mContext;
    private Paint mPaint = new Paint();
    //0 是不旋转
    private int textStyle = 0;

    private int myDisplayId;
    private int style = 0;
    private int max = 160;
    private int min = 0;

    private int value = 0;
    private int startAngle = 0;
    private int endAngle = 270;
    private String color_back_inner_color = "#00000000";
    private String color_back_outer_color = "#ff000000";
    private String color_title_color = "#fe9002";

    private int title_text_size = 10;
    private int title_position = 35;

    private boolean isValueshow = true;
    private String color_value = "#fe9002";
    private int value_size = 12;
    private int value_position = 100;

    private String units_color = "#fe9002";
    private int units_size = 7;
    private int units_ver = 50;
    private int units_hor = 75;

    private int major_width = 10;
    private int major_height = 74;
    private String major_color = "#ffffffff";

    private int minor_width = 10;
    private int minor_height = 80;
    private String minor_color = "#ffffffff";

    private boolean isTextShow = true;
    private boolean isTextRotate = false;
    private int lable_size = 8;
    private int lable_offset = 85;

    private boolean isPointerShow = true;
    private int point_width = 4;
    private int point_length = 40;
    private String point_color = "#fe9002";
    private int point_rad = 5;
    private String center_color = "#ffffffff";

    private boolean range_show = false;
    private int range_startrangle = 0;
    private int range_endrangle = 360;
    private String range_color = "#91bef0";

    private String style_two_back_color = "#00a6ff";
    private int style_two_back_rad = 60;

    private String style_two_title_color = "#757476";
    private int style_two_title_size = 8;
    private int style_two_title_position = 40;

    private boolean is_two_value_show = true;
    private String style_two_value_color = "#ffffffff";
    private int style_two_value_size = 18;
    private int style_two_value_position = 60;

    private String style_two_units_color = "#757476";
    private int style_two_units_size = 8;
    private int style_two_units_position = 73;

    private String style_two_pointer_color = "#ffffffff";
    private int style_two_pointer_width = 2;

    private boolean style_two_range_show = true;
    private String style_two_range_color = "#00a6ff";

    private String style_three_inner_color = "#000000";
    private String style_three_outer_color = "#000000";
    private int style_three_back_rad = 50;

    private String style_three_title_color = "#ffffffff";
    private int style_three_title_size = 14;
    private int style_three_title_position = 34;

    private boolean style_three_value_show = true;
    private String style_three_value_color = "#ffffffff";
    private int style_three_value_size = 23;
    private int style_three_value_position = 63;

    private String style_three_units_color = "#ffffffff";
    private int style_three_units_size = 14;
    private int style_three_units_position = 80;

    private String style_three_frame_color = "#000000";

    private boolean isRrmoveDisplay = false;
    //拖拽
    private boolean isDrawandMove = false;


    private float style_one_text = 0.0f;
    private int lastX;
    private int lastY;
    private int totalX = 0;
    private int totalY = 0;
    private int dashboard_mode_style;


    public DashboardsView(Context context, int myId) {
        super(context);

        this.setOnClickListener(this);
        mContext = context;
        myDisplayId = myId;
        initView();


    }


    public DashboardsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        this.setOnClickListener(this);
        mContext = context;
    }

    public DashboardsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        this.setOnClickListener(this);
        mContext = context;
    }

    private void initView() {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(getResources().getColor(R.color.colorWhite));
        mPaint.setAntiAlias(true);
        mPaint.setTypeface(null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //判断是否romove  Display
        if (!isRrmoveDisplay) {

            if (style == 0) {


                //绘制最外层黑色圆   带渐变
                drawOutCircle(canvas);

                if (range_show) {
                    //绘制Range
                    drawRange(canvas);
                }

                //绘制四周的长短刻度   以及绘制文字
                drawScale(canvas);
                if (isPointerShow) {
                    //绘制中间的橘黄色指针
                    drawPointer(canvas);
                    //绘制中间的白色圆球
                    drawCenterCircle(canvas);
                }
                //绘制标题
                drawTitle(canvas);
                //绘制 矩形下方 下方数值
                if (isValueshow) {
                    drawBottomText(canvas);
                }
                //绘制单位
                drawUnitslabel(canvas);
            } else if (style == 1) {
                //绘制底层表盘
                drawDown_two(canvas);
                //绘制渐变的圆
                drawDradientCircle(canvas);
                //绘制渐变圆上面的覆盖的两个图片
                drawCoverBitmap(canvas);
                //绘制MPH  标题文字
                draw_two_title_text(canvas);
                //绘制下面蓝色圆弧
                drawTwoBottomArw(canvas);
                //绘制灰色圆弧在蓝色圆弧上面   (这里注意 最后输入结果的时候需要注意一下)  而且画笔只变一下颜色就可以了
                drawTwoTopArc(canvas);
                //自定义字体文字
                if (is_two_value_show) {
                    drawValueText(canvas);
                }
                //绘制指针  三角和线
                drawStyleTwoPointer(canvas);
            } else if (style == 2) {
                //绘制圆角阴影
                drawCircleShadle(canvas);
                //绘制底盘颜色
                drawStyleThreeBottom(canvas);
                //绘制里面的圆角矩形
                drawStyleThreeCenter(canvas);
                //绘制渐变圆
                drawStyleThreeCircle(canvas);
                //绘制文字Top And  Bottom
                drawStyThreeTop(canvas);
                //绘制中心数值
                if (style_three_value_show) {
                    drawStyThreeCenter(canvas);
                }
            }
        }


        if (isDrawandMove) {
            mPaint.setColor(getResources().getColor(R.color.colorDrawMove));
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(0);
            canvas.drawRect(0, 0, getWidth(), getWidth(), mPaint);
        }


    }


    //绘制渐变圆
    private void drawStyleThreeCircle(Canvas canvas) {

        int[] a = {Color.parseColor(style_three_inner_color), Color.parseColor(style_three_outer_color),
                Color.parseColor(style_three_outer_color)};

        float[] b = {0.0f, style_three_back_rad / 100.0f, 1f};

        Shader shader = new RadialGradient(getWidth() / 2, getWidth() / 2, getWidth() / 2,
                a, b
                , Shader.TileMode.CLAMP);

        mPaint.setShader(shader);

        canvas.drawCircle(getWidth() / 2, getWidth() / 2, (float) ((139.0 / 320.0) * getWidth()), mPaint);

        mPaint.setShader(null);

    }


    //绘制Range
    private void drawRange(Canvas canvas) {

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth((float) ((6 / 100.0) * getWidth()));
        mPaint.setColor(Color.parseColor(range_color));

        canvas.save();

        canvas.rotate(90, getWidth() / 2, getWidth() / 2);

        RectF rectF = new RectF((float) ((7 / 100.0) * getWidth()), (float) ((7 / 100.0) * getWidth()),
                (float) ((93 / 100.0) * getWidth()), (float) ((93 / 100.0) * getWidth()));
        canvas.drawArc(rectF, range_startrangle, range_endrangle, false, mPaint);

        canvas.restore();

    }


    //绘制单位
    private void drawUnitslabel(Canvas canvas) {

        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(Color.parseColor(units_color));

        mPaint.setTextSize((float) ((units_size / 100.0) * getWidth()));

        canvas.drawText("F", (float) ((units_ver / 100.0) * getWidth()), (float) ((units_hor / 100.0) * getWidth()), mPaint);


    }


    //绘制中心数值
    private void drawStyThreeCenter(Canvas canvas) {
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize((float) ((style_three_value_size / 100.0) * getWidth()));
        mPaint.setColor(Color.parseColor(style_three_value_color));
        mPaint.setTypeface(LcndUtil.getfont(mContext));

        canvas.drawText("0.00", getWidth() / 2, (float) ((style_three_value_position / 100.0) * getWidth()), mPaint);

        mPaint.setTypeface(null);

    }

    //绘制文字Top And  Bottom
    private void drawStyThreeTop(Canvas canvas) {
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize((float) ((style_three_title_size / 100.0) * getWidth()));
        mPaint.setColor(Color.parseColor(style_three_title_color));
        mPaint.setTypeface(null);

        canvas.drawText("RPM", getWidth() / 2, (float) ((style_three_title_position / 100.0) * getWidth()), mPaint);

        mPaint.setTextSize((float) ((style_three_units_size / 100.0) * getWidth()));
        mPaint.setColor(Color.parseColor(style_three_units_color));

        canvas.drawText("/min", getWidth() / 2, (float) ((style_three_units_position / 100.0) * getWidth()), mPaint);


    }


    //绘制里面的圆角矩形
    private void drawStyleThreeCenter(Canvas canvas) {


        mPaint.setColor(Color.parseColor(style_three_frame_color));


        canvas.drawRect((float) ((21.0 / 320.0) * getWidth()), (float) ((37.0 / 320.0) * getWidth()),
                (float) ((299.0 / 320.0) * getWidth()), (float) ((283.0 / 320.0) * getWidth()), mPaint);
        canvas.drawRect((float) ((37.0 / 320.0) * getWidth()), (float) ((21.0 / 320.0) * getWidth()),
                (float) ((283.0 / 320.0) * getWidth()), (float) ((299.0 / 320.0) * getWidth()), mPaint);

        canvas.drawCircle((float) ((37.0 / 320.0) * getWidth()), (float) ((37.0 / 320.0) * getWidth()),
                (float) ((16.0 / 320.0) * getWidth()), mPaint);
        canvas.drawCircle((float) ((37.0 / 320.0) * getWidth()), (float) ((283.0 / 320.0) * getWidth()),
                (float) ((16.0 / 320.0) * getWidth()), mPaint);
        canvas.drawCircle((float) ((283.0 / 320.0) * getWidth()), (float) ((37.0 / 320.0) * getWidth()),
                (float) ((16.0 / 320.0) * getWidth()), mPaint);
        canvas.drawCircle((float) ((283.0 / 320.0) * getWidth()), (float) ((283.0 / 320.0) * getWidth()),
                (float) ((16.0 / 320.0) * getWidth()), mPaint);


    }

    //绘制底盘颜色
    private void drawStyleThreeBottom(Canvas canvas) {

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(getResources().getColor(R.color.colorDashboardsPointer));

        canvas.drawRect((float) ((16.0 / 320.0) * getWidth()), 0,
                (float) ((304.0 / 320.0) * getWidth()), getWidth(), mPaint);
        canvas.drawRect(0, (float) ((16.0 / 320.0) * getWidth()),
                getWidth(), (float) ((304.0 / 320.0) * getWidth()), mPaint);


        canvas.drawCircle((float) ((16.0 / 320.0) * getWidth()), (float) ((16.0 / 320.0) * getWidth()),
                (float) ((16.0 / 320.0) * getWidth()), mPaint);
        canvas.drawCircle((float) ((304.0 / 320.0) * getWidth()), (float) ((16.0 / 320.0) * getWidth()),
                (float) ((16.0 / 320.0) * getWidth()), mPaint);
        canvas.drawCircle((float) ((16.0 / 320.0) * getWidth()), (float) ((304.0 / 320.0) * getWidth()),
                (float) ((16.0 / 320.0) * getWidth()), mPaint);
        canvas.drawCircle((float) ((304.0 / 320.0) * getWidth()), (float) ((304.0 / 320.0) * getWidth()),
                (float) ((16.0 / 320.0) * getWidth()), mPaint);

    }

    //绘制圆角阴影
    private void drawCircleShadle(Canvas canvas) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.stythreebottom);
        RectF rectF = new RectF(0, 0, getWidth(), getWidth());
        canvas.drawBitmap(bitmap, null, rectF, mPaint);

    }


    //绘制指针  三角和线
    private void drawStyleTwoPointer(Canvas canvas) {

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(Color.parseColor(style_two_pointer_color));


        canvas.save();

        canvas.translate(getWidth() / 2, getWidth() / 2);

        //这里还要偏转角度   最大持续角度是270
        canvas.rotate(-135 + value * 2.7f);

//        指针长度不对？？  鬼知道什么原因   实在不行就根据偏移角度设置三角形和下面线的长度

        Path path = new Path();
        path.moveTo((float) ((-12.0 / 300.0) * getWidth()), (float) ((-142.0 / 300.0) * getWidth()));
        path.lineTo((float) ((12.0 / 300.0) * getWidth()), (float) ((-142.0 / 300.0) * getWidth()));
        path.lineTo(0, (float) ((-122.0 / 300.0) * getWidth()));

        canvas.drawPath(path, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(style_two_pointer_width);

        canvas.drawLine(0, (float) ((-122.0 / 300.0) * getWidth()), 0, (float) ((-102.0 / 300.0) * getWidth()), mPaint);


        canvas.restore();

    }

    //自定义字体文字
    private void drawValueText(Canvas canvas) {

        canvas.save();


        mPaint.setTypeface(LcndUtil.getfont(mContext));
        mPaint.setTextSize((float) ((style_two_value_size / 100.0) * getWidth()));
        mPaint.setColor(Color.parseColor(style_two_value_color));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStrokeWidth(0);
        mPaint.setStyle(Paint.Style.FILL);

        canvas.translate((float) ((85.0 / 300.0) * getWidth()), 0);

        canvas.drawText("2500", (float) ((60.0 / 300.0) * getWidth()), (float) ((style_two_value_position / 100.0) * getWidth()), mPaint);

        mPaint.setTypeface(null);

        canvas.restore();

    }


    //绘制灰色圆弧在蓝色圆弧上面   (这里注意 最后输入结果的时候需要注意一下)  而且画笔只变一下颜色就可以了
    private void drawTwoTopArc(Canvas canvas) {

        mPaint.setStrokeWidth((float) ((13.0 / 300.0) * getWidth()));
        mPaint.setColor(getResources().getColor(R.color.colorStyleTwoArcTop));


        RectF rectF = new RectF((float) ((30.0 / 300.0) * getWidth()), (float) ((30.0 / 300.0) * getWidth()),
                (float) ((270.0 / 300.0) * getWidth()), (float) ((270.0 / 300.0) * getWidth()));
        //52是开始角度   持续角度是76
        if (style_two_range_show) {
            canvas.drawArc(rectF, 52, 76 - (value * 0.76f), false, mPaint);
        } else {
            canvas.drawArc(rectF, 52, 76, false, mPaint);
        }

    }

    //绘制下面蓝色圆弧
    private void drawTwoBottomArw(Canvas canvas) {

        RectF rectF = new RectF((float) ((30.0 / 300.0) * getWidth()), (float) ((30.0 / 300.0) * getWidth()),
                (float) ((270.0 / 300.0) * getWidth()), (float) ((270.0 / 300.0) * getWidth()));

        mPaint.setColor(Color.parseColor(style_two_range_color));

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth((float) ((12.0 / 300.0) * getWidth()));
        mPaint.setAntiAlias(true);
        canvas.drawArc(rectF, 52, 76, false, mPaint);


    }

    //绘制MPH  标题文字
    private void draw_two_title_text(Canvas canvas) {

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        mPaint.setTextSize((float) ((style_two_title_size / 100.0) * getWidth()));
        mPaint.setColor(Color.parseColor(style_two_title_color));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTypeface(null);

        canvas.drawText("MPH", getWidth() / 2, (float) ((style_two_title_position / 100.0) * getWidth()), mPaint);

        mPaint.setTextSize((float) ((style_two_units_size / 100.0) * getWidth()));
        mPaint.setColor(Color.parseColor(style_two_units_color));

        canvas.drawText("R/MIN", getWidth() / 2, (float) ((style_two_units_position / 100.0) * getWidth()), mPaint);

        initView();

    }

    //绘制渐变圆上面的覆盖的两个图片
    private void drawCoverBitmap(Canvas canvas) {
        //圆的半径是102px
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.styletwocentercircle_ui);
        RectF rectF = new RectF((float) ((45.0 / 300.0) * getWidth()), (float) ((38.0 / 300.0) * getWidth()),
                (float) ((255.0 / 300.0) * getWidth()), (float) ((252.0 / 300.0) * getWidth()));
        canvas.drawBitmap(bitmap, null, rectF, mPaint);

        Bitmap bitmap_1 = BitmapFactory.decodeResource(getResources(), R.drawable.yuanhu);
        RectF rectF_1 = new RectF((float) ((48.0 / 300.0) * getWidth()), (float) ((220.0 / 300.0) * getWidth()),
                (float) ((252.0 / 300.0) * getWidth()), (float) ((296.0 / 300.0) * getWidth()));
        canvas.drawBitmap(bitmap_1, null, rectF_1, mPaint);


    }


    //绘制渐变的圆
    private void drawDradientCircle(Canvas canvas) {

        int[] a = {Color.parseColor(style_two_back_color), Color.parseColor(style_two_back_color),
                getResources().getColor(R.color.colorTransparent)};

        float[] b = {0.0f, style_two_back_rad / 100.0f, 1f};

        Shader shader = new RadialGradient(getWidth() / 2, getWidth() / 2, getWidth() / 2,
                a, b
                , Shader.TileMode.CLAMP);

        mPaint.setShader(shader);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);

        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, mPaint);

        mPaint.setShader(null);

    }

    private void drawDown_two(Canvas canvas) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.style_two);
        RectF rectF = new RectF(0, 0, getWidth(), getWidth());
        canvas.drawBitmap(bitmap, null, rectF, mPaint);


    }

    //绘制下方数值
    private void drawBottomText(Canvas canvas) {

        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(Color.parseColor(color_value));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);

        mPaint.setTextSize((float) ((value_size * getWidth()) / 150.0));

        if (style_one_text == 0.0f) {
            canvas.drawText("N/A", getWidth() / 2, (float) ((value_position * getHeight()) / 100.0), mPaint);
        } else {
            canvas.drawText(style_one_text + "", getWidth() / 2, (float) ((value_position * getHeight()) / 100.0), mPaint);
        }
    }

    //绘制标题
    private void drawTitle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor(color_title_color));
        mPaint.setStrokeWidth(0);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize((float) ((title_text_size * getWidth()) / 150.0));

        canvas.save();

        canvas.translate(getWidth() / 2, getWidth() / 2);

        canvas.drawText("Title", 0, (float) (-(title_position / 225.0) * getWidth()), mPaint);

        canvas.restore();
    }


    //绘制中间的白色圆球
    private void drawCenterCircle(Canvas canvas) {


        mPaint.setColor(Color.parseColor(center_color));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);

        canvas.save();

        canvas.translate(getWidth() / 2, getWidth() / 2);
        canvas.drawCircle(0, 0, (float) ((point_rad / 100.0) * getWidth()), mPaint);


        canvas.restore();

        initView();
    }

    //绘制指针
    private void drawPointer(Canvas canvas) {
        canvas.save();

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(Color.parseColor(point_color));

        canvas.translate(getWidth() / 2, getWidth() / 2);

        //指针旋转
        if (startAngle + value < endAngle) {
            canvas.rotate(startAngle + value);
        } else {
            canvas.rotate(endAngle);
        }
        //下面的6也要提出去  是12 / 2
        Path path = new Path();
        path.moveTo((float) (-1 * (point_width / 200.0) * getWidth()), 0);
        path.lineTo((float) ((point_width / 200.0) * getWidth()), 0);
        path.lineTo(0, (float) ((point_length / 100.0) * getWidth()));
        path.close();


        canvas.drawPath(path, mPaint);

        canvas.restore();

        initView();

    }

    //绘制短指针
    private void drawScale(Canvas canvas) {

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth((float) ((minor_width / 1500.0) * getWidth()));
        mPaint.setColor(Color.parseColor(minor_color));

        //起始角度  最终角度  总共的大刻度  两个长刻度之间分隔
        //这些最后都要提出去

        float percent = (float) 8.0;
        float fenge = (float) 5.0;


        canvas.save();
        canvas.translate(getWidth() / 2, getWidth() / 2);
        //先旋转设置的初始角度
        canvas.rotate(startAngle);
        //画短刻度
        for (int i = 0; i < percent * fenge; i++) {
            canvas.save();

            float rAngle = ((endAngle - startAngle) / percent) / fenge;

            canvas.rotate(rAngle * i);
            //43是起始点   40 是终点    40越小线越长
            canvas.drawLine(0, (float) ((43 / 100.0) * getWidth()),
                    0, (float) ((minor_height / 200.0) * getWidth()), mPaint);

            canvas.restore();
        }

        mPaint.setStyle(Paint.Style.FILL);


        mPaint.setStrokeWidth((float) ((major_width / 1500.0) * getWidth()));
        mPaint.setColor(Color.parseColor(major_color));

        //画长指针
        for (int i = 0; i < percent + 1; i++) {
            canvas.save();

            float rAngle = ((endAngle - startAngle) / percent);

            canvas.rotate(rAngle * i);


            //43是起始点   37是终点    37越小线越长
            canvas.drawLine(0, (float) ((43 / 100.0) * getWidth()),
                    0, (float) ((major_height / 200.0) * getWidth()), mPaint);


            canvas.restore();
        }
        canvas.restore();

        //绘制文字
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(getResources().getColor(R.color.colorWhite));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);

        mPaint.setTextSize((float) ((lable_size * getWidth()) / 100.0));
        canvas.save();
        canvas.translate(getWidth() / 2, getWidth() / 2);


        //每大分度的角度
        float rAngle = ((endAngle - startAngle) / percent);
        float rFendu = (max - min) / percent;

        canvas.scale((float) (lable_offset / 100.0), (float) (lable_offset / 100.0));

        for (int i = 0; i < percent + 1; i++) {
            canvas.save();

            //初始旋转    后面再旋转回来
            canvas.rotate(rAngle * i + startAngle);
            canvas.translate(0, (float) ((110.0 / 300.0) * getWidth()));

            //下面两个只能选一个   不旋转是第一个   旋转时第二个
            if (!isTextRotate) {
                canvas.rotate(-rAngle * i - startAngle);
            } else {
                canvas.rotate(180);
            }

            if (isTextShow) {
                canvas.drawText(((int) (rFendu * i) + ((int) min)) + "", 0, 0, mPaint);
            }

            canvas.restore();
        }


        canvas.restore();


        //恢复初始
        initView();


    }

    //Style  画最外层圆  黑色
    private void drawOutCircle(Canvas canvas) {

        mPaint.setColor(getResources().getColor(R.color.colorStyleOneDisplay));

        //如果取的颜色 都是透明  则变成透明画笔
        if (DBTool.getOutInstance().getQueryKey("dashboardsdisplay_style_back_innercolor_" + myDisplayId).getColor().equals("00000000") &&
                DBTool.getOutInstance().getQueryKey("dashboardsdisplay_style_back_outercolor_" + myDisplayId).getColor().equals("00000000")) {
            mPaint.setColor(getResources().getColor(R.color.colorTransparent));
        }

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);

        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, mPaint);


        int[] a = {getResources().getColor(R.color.colorTransparent), Color.parseColor(color_back_inner_color),
                Color.parseColor(color_back_outer_color)};
        float[] b = {0.0f, 0.89333f, 1f};


        Shader shader = new RadialGradient(getWidth() / 2, getWidth() / 2, getWidth() / 2,
                a, b
                , Shader.TileMode.CLAMP);

        mPaint.setShader(shader);

        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, mPaint);

        mPaint.setShader(null);
        //恢复初始
        initView();
    }


    @Override
    public void onClick(View view) {
        //经典  自定义    if  后面加了！  试了为方便调试  最后要去掉！别忘了
        if (DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {

            //经典模式打开只有DC
            final OBDPopDialog dia_dc = new OBDPopDialog(mContext);
            View view_dc = LayoutInflater.from(mContext).inflate(R.layout.dialog_display_edit_dc, null);
            //经典模式下的长按增加一个移除仪表盘
            //自定义模式下不要有

            //不同仪表盘打开出现不同样式
            ImageView iv_rangeline = view_dc.findViewById(R.id.display_range_line);
            RelativeLayout re_range = view_dc.findViewById(R.id.display_range_re);
            ImageView iv_mu = view_dc.findViewById(R.id.display_multipier_line);
            RelativeLayout re_mu = view_dc.findViewById(R.id.display_multipier_re);
            if (!(style == 0)) {
                iv_rangeline.setVisibility(GONE);
                re_range.setVisibility(GONE);
                iv_mu.setVisibility(GONE);
                re_mu.setVisibility(GONE);
            }

            final EditText et_start = view_dc.findViewById(R.id.display_configuration_et_start);
            final EditText et_end = view_dc.findViewById(R.id.display_configuration_et_end);
            //这里做不同仪表盘ID的判断
            et_start.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_value_min_" + myDisplayId).getValue() + "");
            et_end.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_value_max_" + myDisplayId).getValue() + "");


            Button btn_ok = view_dc.findViewById(R.id.display_dc_ok_btn);
            btn_ok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击确定发送广播 把初始值最终值发送
                    Intent intent = new Intent("changeDisplay");
                    if (et_start.getText().length() == 0) {
                        et_start.setText(0 + "");
                    }
                    if (et_end.getText().length() == 0) {
                        et_end.setText(0 + "");
                    }
                    if (Integer.parseInt(et_start.getText().toString()) >= Integer.parseInt(et_end.getText().toString())) {
                        Toast.makeText(mContext, getResources().getString(R.string.minmorethanmax),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_min_" + myDisplayId, Integer.parseInt(et_start.getText().toString()));
                        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_max_" + myDisplayId, Integer.parseInt(et_end.getText().toString()));
                        mContext.sendBroadcast(intent);
                        dia_dc.dismiss();
                    }
                }
            });


            //自定义setWin方法
            setWin(dia_dc);
            dia_dc.setContentView(view_dc);
            dia_dc.setCanceledOnTouchOutside(true);
            dia_dc.show();


            //这个下面就是自定义的了
        } else {
            final OBDPopDialog dialog = new OBDPopDialog(mContext);
            View view_dia = LayoutInflater.from(mContext).inflate(R.layout.dialog_dashboards_view, null);

            //Size and Location
            ImageView iv_sal = view_dia.findViewById(R.id.display_edit_one_sal);
            iv_sal.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    dialog.dismiss();
                    final OBDPopDialog dia_sal = new OBDPopDialog(mContext);
                    View view_sal = LayoutInflater.from(mContext).inflate(R.layout.dialog_display_edit__sal, null);
                    //Width  右边ET
                    final EditText et_width = view_sal.findViewById(R.id.display_sal_width_et);
                    final EditText et_left = view_sal.findViewById(R.id.display_sal_left_et);
                    final EditText et_top = view_sal.findViewById(R.id.display_sal_top_et);
                    //这里做不同仪表盘的判断
                    et_width.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + myDisplayId).getValue() + "");
                    et_left.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_left_" + myDisplayId).getFloValue() + "");
                    et_top.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + myDisplayId).getFloValue() + "");

                    //下方确认按钮
                    Button btn_ok = view_sal.findViewById(R.id.display_sal_ok_btn);
                    //点击OK修改设置  发送广播
                    btn_ok.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (MathUtil.stringToInt(et_width.getText().toString()) >= 10 && MathUtil.stringToInt(et_width.getText().toString()) <= 100 &&
                                    MathUtil.stringToFloat(et_left.getText().toString()) >= (float) 0.0 &&
                                    MathUtil.stringToFloat(et_left.getText().toString()) <= (float) 100.0 &&
                                    MathUtil.stringToFloat(et_top.getText().toString()) >= (float) 0.0 &&
                                    MathUtil.stringToFloat(et_top.getText().toString()) <= (float) 100.0) {

                                dia_sal.dismiss();
                                Intent intent = new Intent("changeDisplay");
                                if (et_width.getText().length() == 0) {
                                    et_width.setText(0 + "");
                                }
                                if (et_left.getText().length() == 0) {
                                    et_left.setText(0 + "");
                                }
                                if (et_top.getText().length() == 0) {
                                    et_top.setText(0 + "");
                                }
                                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_" + myDisplayId, Integer.parseInt(et_width.getText().toString()));
                                DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_" + myDisplayId, Float.parseFloat(et_left.getText().toString()));
                                DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_" + myDisplayId, Float.parseFloat(et_top.getText().toString()));

                                mContext.sendBroadcast(intent);

                            } else {
                                Toast.makeText(mContext, getResources().getString(R.string.yourinputisincorrectPleasereenterit),
                                        Toast.LENGTH_SHORT).show();
                            }


                        }
                    });


                    //自定义setWin方法
                    setWin(dia_sal);
                    dia_sal.setContentView(view_sal);
                    dia_sal.setCanceledOnTouchOutside(true);
                    dia_sal.show();

                }
            });

            //Display Configuration
            ImageView iv_dc = view_dia.findViewById(R.id.display_edit_one_dc);
            iv_dc.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    final OBDPopDialog dia_dc = new OBDPopDialog(mContext);
                    View view_dc = LayoutInflater.from(mContext).inflate(R.layout.dialog_display_edit_dc, null);
                    //自定义模式下  点开要把RemoveDisplay  隐藏  因为外层有了
                    ImageView iv_remove_line = view_dc.findViewById(R.id.display_remove_line);
                    RelativeLayout re_remove = view_dc.findViewById(R.id.display_remove_re);
                    //自定义情况下把  RemoDisplay  取消掉
                    iv_remove_line.setVisibility(GONE);
                    re_remove.setVisibility(GONE);


                    //不同仪表盘打开出现不同样式
                    ImageView iv_rangeline = view_dc.findViewById(R.id.display_range_line);
                    RelativeLayout re_range = view_dc.findViewById(R.id.display_range_re);
                    ImageView iv_mu = view_dc.findViewById(R.id.display_multipier_line);
                    RelativeLayout re_mu = view_dc.findViewById(R.id.display_multipier_re);
                    if (!(style == 0)) {
                        iv_rangeline.setVisibility(GONE);
                        re_range.setVisibility(GONE);
                        iv_mu.setVisibility(GONE);
                        re_mu.setVisibility(GONE);
                    }


                    final EditText et_start = view_dc.findViewById(R.id.display_configuration_et_start);
                    final EditText et_end = view_dc.findViewById(R.id.display_configuration_et_end);
                    //这里做不同仪表盘ID的判断
                    et_start.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_value_min_" + myDisplayId).getValue() + "");
                    et_end.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_value_max_" + myDisplayId).getValue() + "");

                    Button btn_ok = view_dc.findViewById(R.id.display_dc_ok_btn);
                    btn_ok.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent("changeDisplay");
                            if (et_start.getText().length() == 0) {
                                et_start.setText(0 + "");
                            }
                            if (et_end.getText().length() == 0) {
                                et_end.setText(0 + "");
                            }
                            if (Integer.parseInt(et_start.getText().toString()) >= Integer.parseInt(et_end.getText().toString())) {
                                Toast.makeText(mContext, getResources().getString(R.string.minmorethanmax),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_min_" + myDisplayId, Integer.parseInt(et_start.getText().toString()));
                                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocation_value_max_" + myDisplayId, Integer.parseInt(et_end.getText().toString()));
                                mContext.sendBroadcast(intent);
                                dia_dc.dismiss();
                            }

                        }
                    });


                    //自定义setWin方法
                    setWin(dia_dc);
                    dia_dc.setContentView(view_dc);
                    dia_dc.setCanceledOnTouchOutside(true);
                    dia_dc.show();

                }
            });

            //Style
            ImageView iv_style = view_dia.findViewById(R.id.display_edit_one_s);
            iv_style.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    //跳转的时候把参数传过去   根据Style  判断调到那个页面   Style0   Style12
                    if (style == 0) {
                        //这里只用把id传过去
                        Intent intent = new Intent(mContext, OBDStyleActivity.class);
                        intent.putExtra("DisplayId", myDisplayId);
                        mContext.startActivity(intent);
                    } else {
                        //这个是跳转到其他两个的
                        Intent intent = new Intent(mContext, OBDOtherStyleActivity.class);
                        intent.putExtra("DisplayId", myDisplayId);
                        intent.putExtra("DisplayStyle", style);
                        mContext.startActivity(intent);
                    }


                }
            });

            //Dashboards  Style
            ImageView iv_dashboardsstyle = view_dia.findViewById(R.id.display_edit_one_dashboard_style);
            iv_dashboardsstyle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    final OBDPopDialog dialog_dashboardsstyle = new OBDPopDialog(mContext);
                    View view_dasboardsstyle = LayoutInflater.from(mContext).inflate(R.layout.dialog_dashboards_style, null);
                    LinearLayout ll_one = view_dasboardsstyle.findViewById(R.id.ll_style_one);
                    LinearLayout ll_two = view_dasboardsstyle.findViewById(R.id.ll_style_two);
                    LinearLayout ll_three = view_dasboardsstyle.findViewById(R.id.ll_style_three);
                    final ImageView iv_one = view_dasboardsstyle.findViewById(R.id.iv_style_one);
                    final ImageView iv_two = view_dasboardsstyle.findViewById(R.id.iv_style_two);
                    final ImageView iv_three = view_dasboardsstyle.findViewById(R.id.iv_style_three);
                    //把记录Style 的提出去了
                    dashboard_mode_style = DBTool.getOutInstance().getQueryKey("display_style_"+myDisplayId).getValue();
                    //根据数据库存的 判断那个橙色对号显示
                    if (dashboard_mode_style == 0){
                        iv_one.setVisibility(VISIBLE);
                        iv_two.setVisibility(GONE);
                        iv_three.setVisibility(GONE);
                    }else if (dashboard_mode_style == 1){
                        iv_one.setVisibility(GONE);
                        iv_two.setVisibility(VISIBLE);
                        iv_three.setVisibility(GONE);
                    }else {
                        iv_one.setVisibility(GONE);
                        iv_two.setVisibility(GONE);
                        iv_three.setVisibility(VISIBLE);
                    }
                    ll_one.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dashboard_mode_style = 0 ;
                            iv_one.setVisibility(VISIBLE);
                            iv_two.setVisibility(GONE);
                            iv_three.setVisibility(GONE);
                        }
                    });
                    ll_two.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dashboard_mode_style = 1 ;
                            iv_one.setVisibility(GONE);
                            iv_two.setVisibility(VISIBLE);
                            iv_three.setVisibility(GONE);
                        }
                    });
                    ll_three.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dashboard_mode_style = 2 ;
                            iv_one.setVisibility(GONE);
                            iv_two.setVisibility(GONE);
                            iv_three.setVisibility(VISIBLE);
                        }
                    });
                    Button btn_ok = view_dasboardsstyle.findViewById(R.id.btn_style_ok);
                    btn_ok.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dashboard_mode_style  == 0){
                                DBTool.getOutInstance().upDateValueByKey("display_style_"+myDisplayId , 0);
                            }else if (dashboard_mode_style  == 1){
                                DBTool.getOutInstance().upDateValueByKey("display_style_"+myDisplayId , 1);
                            }else {
                                DBTool.getOutInstance().upDateValueByKey("display_style_"+myDisplayId , 2);
                            }
                            Intent intent = new Intent("changeDisplay");
                            mContext.sendBroadcast(intent);
                            dialog_dashboardsstyle.dismiss();
                        }
                    });

                    setWin(dialog_dashboardsstyle);
                    dialog_dashboardsstyle.setContentView(view_dasboardsstyle);
                    dialog_dashboardsstyle.setCanceledOnTouchOutside(true);
                    dialog_dashboardsstyle.show();
                }
            });


            //Remove  Display
            ImageView iv_rd = view_dia.findViewById(R.id.display_edit_one_rd);
            iv_rd.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    final OBDPopDialog dia_rd = new OBDPopDialog(mContext);
                    View view_rd = LayoutInflater.from(mContext).inflate(R.layout.dialog_display_remove_display, null);
                    Button btn_ok = view_rd.findViewById(R.id.btn_display_remove_ok);
                    Button btn_cancel = view_rd.findViewById(R.id.btn_display_remove_cancel);

                    btn_ok.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dia_rd.dismiss();
                            Intent intent = new Intent("changeDisplay");
                            DBTool.getOutInstance().upDateIsTrueByKey("display_isRemoveDisplay_" + myDisplayId, true);
                            mContext.sendBroadcast(intent);
                        }
                    });
                    btn_cancel.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dia_rd.dismiss();
                        }
                    });

                    setPromptWin(dia_rd);
                    dia_rd.setContentView(view_rd);
                    dia_rd.setCanceledOnTouchOutside(true);
                    dia_rd.show();


                }
            });

            //Draw  and  Move
            RelativeLayout re_draw_and_move = view_dia.findViewById(R.id.re_draw_and_move);
            re_draw_and_move.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    isDrawandMove = true;
                    invalidate();
                    dialog.dismiss();
                    Intent intent = new Intent("viewpagerIsScrool");
                    intent.putExtra("scrool", false);
                    mContext.sendBroadcast(intent);
                }
            });


            //BringToFirst
            ImageView iv_btf = view_dia.findViewById(R.id.display_edit_one_btf);
            iv_btf.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击发送广播提醒  把这个ID的Display移到最前
                    Intent intent = new Intent("bringToFirst");
                    intent.putExtra("id", myDisplayId);
                    mContext.sendBroadcast(intent);
                    dialog.dismiss();
                }
            });


            //自定义setWin方法
            setWin(dialog);
            dialog.setContentView(view_dia);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (isDrawandMove) {

            int x = (int) event.getX();
            int y = (int) event.getY();


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = x;
                    lastY = y;
                    totalX = 0;
                    totalY = 0;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int offX = x - lastX;
                    int offY = y - lastY;

                    totalX = totalX + offX;
                    totalY = totalY + offY;

                    offsetLeftAndRight(offX);
                    offsetTopAndBottom(offY);

                    break;
                case MotionEvent.ACTION_UP:

                    float left = DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_left_" + myDisplayId).getFloValue() +
                            (((float) totalX * 100.0f) / ((float) ((int) SPUtil.get(mContext, "screenWidth", 0) + 0.0f)));
                    LogUtil.fussenLog().d("left=" + left);
                    DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_left_" + myDisplayId, left);

                    float top = DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + myDisplayId).getFloValue() +
                            (((float) totalY * 100.0f) / ((float) ((int) SPUtil.get(mContext, "screenHeight", 0) + 0.0f)));
                    LogUtil.fussenLog().d("top=" + top);
                    DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_" + myDisplayId, top);

                    Intent intent = new Intent("changeDisplay");
                    mContext.sendBroadcast(intent);


                    isDrawandMove = false;
                    invalidate();
                    //向Aty传值   可以滑动ViewPager
                    Intent intentVP = new Intent("viewpagerIsScrool");
                    intentVP.putExtra("scrool", true);
                    mContext.sendBroadcast(intentVP);

                    break;
            }


            return true;
        }

        return super.onTouchEvent(event);
    }

    private void setWin(OBDPopDialog dia) {
        Window win = dia.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        win.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = (int) (ScreenUtils.getScreenWidth(mContext) * 0.053333);
        lp.y = (int) (ScreenUtils.getScreenHeight(mContext) * 0.123647);
        win.setAttributes(lp);
    }

    private void setPromptWin(OBDPopDialog dia) {
        Window win = dia.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        win.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = (int) (ScreenUtils.getScreenWidth(mContext) * 0.141333);
        lp.y = (int) (ScreenUtils.getScreenHeight(mContext) * 0.293663);
        win.setAttributes(lp);
    }


    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
        invalidate();
    }

    public void setTextStyle(int textStyle) {
        this.textStyle = textStyle;
        invalidate();
    }

    public int getStyle() {
        return style;
    }

    public int getMyDisplayWidth() {
        return getWidth();
    }

    public void setMyDisplayId(int myDisplayId) {
        this.myDisplayId = myDisplayId;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setValue(int value) {
        this.value = value;
        invalidate();
    }

    public void setEndAngle(int endAngle) {
        this.endAngle = endAngle;
        invalidate();
    }

    public void setColor_back_inner_color(String color_back_inner_color) {
        this.color_back_inner_color = color_back_inner_color;
        invalidate();
    }

    public void setColor_back_outer_color(String color_back_outer_color) {
        this.color_back_outer_color = color_back_outer_color;
        invalidate();
    }


    public void setColor_title_color(String color_title_color) {
        this.color_title_color = color_title_color;
        invalidate();
    }


    public void setTitle_text_size(int title_text_size) {
        this.title_text_size = title_text_size;
        invalidate();
    }

    public void setTitle_position(int title_position) {
        this.title_position = title_position;
        invalidate();
    }

    public void setColor_value(String color_value) {
        this.color_value = color_value;
        invalidate();
    }

    public void setValueshow(boolean valueshow) {
        isValueshow = valueshow;
        invalidate();
    }

    public void setValue_size(int value_size) {
        this.value_size = value_size;
        invalidate();
    }

    public void setValue_position(int value_position) {
        this.value_position = value_position;
        invalidate();
    }

    public void setUnits_color(String units_color) {
        this.units_color = units_color;
        invalidate();
    }

    public void setUnits_size(int units_size) {
        this.units_size = units_size;
        invalidate();
    }

    public void setMajor_width(int major_width) {
        this.major_width = major_width;
        invalidate();
    }

    public void setUnits_ver(int units_ver) {
        this.units_ver = units_ver;
        invalidate();
    }

    public void setUnits_hor(int units_hor) {
        this.units_hor = units_hor;
        invalidate();
    }

    public void setMajor_height(int major_height) {
        this.major_height = major_height;
        invalidate();
    }

    public void setMajor_color(String major_color) {
        this.major_color = major_color;
        invalidate();
    }

    public void setMinor_width(int minor_width) {
        this.minor_width = minor_width;
        invalidate();
    }

    public void setMinor_height(int minor_height) {
        this.minor_height = minor_height;
        invalidate();
    }

    public void setMinor_color(String minor_color) {
        this.minor_color = minor_color;
        invalidate();
    }

    public void setTextShow(boolean textShow) {
        isTextShow = textShow;
        invalidate();
    }

    public void setTextRotate(boolean textRotate) {
        isTextRotate = textRotate;
        invalidate();
    }

    public void setLable_size(int lable_size) {
        this.lable_size = lable_size;
        invalidate();
    }

    public void setLable_offset(int lable_offset) {
        this.lable_offset = lable_offset;
        invalidate();
    }

    public void setPointerShow(boolean pointerShow) {
        isPointerShow = pointerShow;
        invalidate();
    }

    public void setPoint_width(int point_width) {
        this.point_width = point_width;
        invalidate();
    }

    public void setPoint_length(int point_length) {
        this.point_length = point_length;
        invalidate();
    }

    public void setPoint_color(String point_color) {
        this.point_color = point_color;
        invalidate();
    }

    public void setPoint_rad(int point_rad) {
        this.point_rad = point_rad;
        invalidate();
    }

    public void setCenter_color(String center_color) {
        this.center_color = center_color;
        invalidate();
    }

    public void setRange_show(boolean range_show) {
        this.range_show = range_show;
        invalidate();
    }

    public void setRange_startrangle(int range_startrangle) {
        this.range_startrangle = range_startrangle;
        invalidate();
    }

    public void setRange_endrangle(int range_endrangle) {
        this.range_endrangle = range_endrangle;
        invalidate();
    }

    public void setRange_color(String range_color) {
        this.range_color = range_color;
        invalidate();
    }

    public void setStyle_two_back_color(String style_two_back_color) {
        this.style_two_back_color = style_two_back_color;
        invalidate();
    }

    public void setStyle_two_back_rad(int style_two_back_rad) {
        this.style_two_back_rad = style_two_back_rad;
        invalidate();
    }

    public void setStyle_two_title_color(String style_two_title_color) {
        this.style_two_title_color = style_two_title_color;
        invalidate();
    }

    public void setStyle_two_title_size(int style_two_title_size) {
        this.style_two_title_size = style_two_title_size;
        invalidate();
    }

    public void setStyle_two_title_position(int style_two_title_position) {
        this.style_two_title_position = style_two_title_position;
        invalidate();
    }

    public void setIs_two_value_show(boolean is_two_value_show) {
        this.is_two_value_show = is_two_value_show;
        invalidate();
    }

    public void setStyle_two_value_color(String style_two_value_color) {
        this.style_two_value_color = style_two_value_color;
        invalidate();
    }

    public void setStyle_two_value_size(int style_two_value_size) {
        this.style_two_value_size = style_two_value_size;
        invalidate();
    }

    public void setStyle_two_value_position(int style_two_value_position) {
        this.style_two_value_position = style_two_value_position;
        invalidate();
    }

    public void setStyle_two_units_color(String style_two_units_color) {
        this.style_two_units_color = style_two_units_color;
        invalidate();
    }

    public void setStyle_two_units_size(int style_two_units_size) {
        this.style_two_units_size = style_two_units_size;
        invalidate();
    }

    public void setStyle_two_units_position(int style_two_units_position) {
        this.style_two_units_position = style_two_units_position;
        invalidate();
    }

    public void setStyle_two_pointer_color(String style_two_pointer_color) {
        this.style_two_pointer_color = style_two_pointer_color;
        invalidate();
    }

    public void setStyle_two_pointer_width(int style_two_pointer_width) {
        this.style_two_pointer_width = style_two_pointer_width;
        invalidate();
    }

    public void setStyle_two_range_show(boolean style_two_range_show) {
        this.style_two_range_show = style_two_range_show;
        invalidate();
    }


    public void setStyle_two_range_color(String style_two_range_color) {
        this.style_two_range_color = style_two_range_color;
        invalidate();
    }

    public void setStyle_three_inner_color(String style_three_inner_color) {
        this.style_three_inner_color = style_three_inner_color;
        invalidate();
    }

    public void setStyle_three_outer_color(String style_three_outer_color) {
        this.style_three_outer_color = style_three_outer_color;
        invalidate();
    }

    public void setStyle_three_back_rad(int style_three_back_rad) {
        this.style_three_back_rad = style_three_back_rad;
        invalidate();
    }

    public void setStyle_three_title_color(String style_three_title_color) {
        this.style_three_title_color = style_three_title_color;
        invalidate();
    }

    public void setStyle_three_title_size(int style_three_title_size) {
        this.style_three_title_size = style_three_title_size;
        invalidate();
    }

    public void setStyle_three_title_position(int style_three_title_position) {
        this.style_three_title_position = style_three_title_position;
        invalidate();
    }

    public void setStyle_three_value_show(boolean style_three_value_show) {
        this.style_three_value_show = style_three_value_show;
        invalidate();
    }

    public void setStyle_three_value_color(String style_three_value_color) {
        this.style_three_value_color = style_three_value_color;
        invalidate();
    }

    public void setStyle_three_value_size(int style_three_value_size) {
        this.style_three_value_size = style_three_value_size;
        invalidate();
    }

    public void setStyle_three_value_position(int style_three_value_position) {
        this.style_three_value_position = style_three_value_position;
        invalidate();
    }

    public void setStyle_three_units_color(String style_three_units_color) {
        this.style_three_units_color = style_three_units_color;
        invalidate();
    }

    public void setStyle_three_units_size(int style_three_units_size) {
        this.style_three_units_size = style_three_units_size;
        invalidate();
    }

    public void setStyle_three_units_position(int style_three_units_position) {
        this.style_three_units_position = style_three_units_position;
        invalidate();
    }

    public void setStyle_three_frame_color(String style_three_frame_color) {
        this.style_three_frame_color = style_three_frame_color;
        invalidate();
    }

    public int getMyDisplayId() {
        return myDisplayId;
    }

    public void setRemoveDisplay(boolean rrmoveDisplay) {
        isRrmoveDisplay = rrmoveDisplay;
        invalidate();
    }

    public void setStyle_one_text(float style_one_text) {
        this.style_one_text = style_one_text;
        invalidate();
    }
}
