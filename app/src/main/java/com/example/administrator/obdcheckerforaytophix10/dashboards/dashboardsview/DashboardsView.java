package com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.main.obd.OBDPopDialog;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.MathUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

/**
 * Created by CHD on 2017/8/7.
 */
//按照Px设置
//(float) (-(38.0 / 300.0) * getWidth())
//按照x375设置
//(float) ((12.0 * getWidth()) / 150.0))

//单位统一换算方法 按照getWidth   getWidth 等比例来
//getWidth  想象成300px
public class DashboardsView extends View implements View.OnClickListener {
    //375  647
    private Paint mPaint = new Paint();
    private int style = 0;
    private float startAngle = (float) 0.0;
    //0 是不旋转
    private int textStyle = 0;
    private Context mContext;


    public DashboardsView(Context context) {
        super(context);
        initView();
        this.setOnClickListener(this);
        mContext = context;
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
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (style == 0) {
            //绘制最外层黑色圆
            drawOutCircle(canvas);
            //绘制四周的长短刻度   以及绘制文字
            drawScale(canvas);
            //绘制中间的橘黄色指针
            drawPointer(canvas);
            //绘制中间的白色圆球
            drawCenterCircle(canvas);
            //绘制标题
            drawTitle(canvas);
            //绘制下方数值
            drawBottomText(canvas);
        } else if (style == 1) {

        }


    }

    //绘制下方数值
    private void drawBottomText(Canvas canvas) {

        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(getResources().getColor(R.color.colorDashboardsPointer));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        mPaint.setTextSize((float) ((12.0 * getWidth()) / 150.0));

        canvas.drawText("N/A", getWidth() / 2, getHeight(), mPaint);

    }

    //绘制标题
    private void drawTitle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.colorDashboardsPointer));
        mPaint.setStrokeWidth(0);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize((float) ((10.0 * getWidth()) / 150.0));

        canvas.save();

        canvas.translate(getWidth() / 2, getWidth() / 2);
        canvas.drawText("Title", 0, (float) (-(38.0 / 300.0) * getWidth()), mPaint);

        canvas.restore();
    }


    //绘制中间的白色圆球
    private void drawCenterCircle(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.colorWhite));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);

        canvas.save();

        canvas.translate(getWidth() / 2, getWidth() / 2);
        canvas.drawCircle(0, 0, (float) ((15.0 / 300.0) * getWidth()), mPaint);


        canvas.restore();

        initView();
    }

    //绘制指针
    private void drawPointer(Canvas canvas) {
        canvas.save();

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(getResources().getColor(R.color.colorDashboardsPointer));

        canvas.translate(getWidth() / 2, getWidth() / 2);
        //初始角度  要在这里设置
        canvas.rotate(startAngle);

        //下面的6也要提出去  是12 / 2
        Path path = new Path();
        path.moveTo((float) (-1 * (6.0 / 300.0) * getWidth()), 0);
        path.lineTo((float) ((6.0 / 300.0) * getWidth()), 0);
        path.lineTo(0, (float) ((120.0 / 300.0) * getWidth()));
        path.close();

        canvas.drawPath(path, mPaint);

        canvas.restore();

        initView();

    }

    //绘制短指针
    private void drawScale(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth((float) ((2.0 / 300.0) * getWidth()));
        mPaint.setColor(getResources().getColor(R.color.colorWhite));

        //起始角度  最终角度  总共的大刻度  两个长刻度之间分隔
        //这些最后都要提出去
        float endAngle = (float) 360.0;
        float percent = (float) 8.0;
        float fenge = (float) 5.0;


        canvas.save();
        canvas.translate(getWidth() / 2, getWidth() / 2);
        //先旋转设置的初始角度
        canvas.rotate(startAngle);
        //画短指针
        for (int i = 0; i < percent * fenge; i++) {
            canvas.save();

            float rAngle = ((endAngle - startAngle) / percent) / fenge;

            canvas.rotate(rAngle * i);

            canvas.drawLine(0, (float) ((130.0 / 300.0) * getWidth()),
                    0, (float) ((120.0 / 300.0) * getWidth()), mPaint);

            canvas.restore();
        }

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth((float) ((2.0 / 300.0) * getWidth()));
        mPaint.setColor(getResources().getColor(R.color.colorWhite));

        //画长指针
        for (int i = 0; i < percent + 1; i++) {
            canvas.save();

            float rAngle = ((endAngle - startAngle) / percent);

            canvas.rotate(rAngle * i);

            canvas.drawLine(0, (float) ((130.0 / 300.0) * getWidth()),
                    0, (float) ((110.0 / 300.0) * getWidth()), mPaint);


            canvas.restore();
        }
        canvas.restore();

        //绘制文字
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(getResources().getColor(R.color.colorWhite));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        mPaint.setTextSize((float) ((12.0 * getWidth()) / 150.0));

        float min = (float) 0.0;
        float max = (float) 160.0;

        canvas.save();
        canvas.translate(getWidth() / 2, getWidth() / 2);

        for (int i = 0; i < percent + 1; i++) {

            canvas.save();

            if (textStyle == 0) {
                canvas.translate(0, (float) ((10.0 / 300.0) * getWidth()));
                canvas.scale(0.9f, 0.9f);
            } else {
                canvas.scale(0.9f, 0.9f);
            }
            float rAngle = ((endAngle - startAngle) / percent);
            float rFendu = (max - min) / percent;

            canvas.rotate(rAngle * i);

            if (textStyle == 0) {
                canvas.rotate(-rAngle * i, 0, (float) ((100.0 / 300.0) * getWidth()));
            } else {
                canvas.rotate(180, 0, (float) ((100.0 / 300.0) * getWidth()));
            }

            //本来是100的  微调成110
            canvas.drawText(((int) (rFendu * i)) + "", 0, (float) ((100.0 / 300.0) * getWidth()), mPaint);

            canvas.restore();

        }

        canvas.restore();


        //恢复初始
        initView();


    }

    //Style  画最外层圆  黑色
    private void drawOutCircle(Canvas canvas) {

        mPaint.setColor(getResources().getColor(R.color.colorBlack));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yb_ui);
        RectF rectF = new RectF(0, 0, getWidth(), getWidth());
        canvas.drawBitmap(bitmap, null, rectF, mPaint);


        //恢复初始
        initView();
    }


    @Override
    public void onClick(View view) {

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
                et_width.setText(SPUtil.get(mContext, "dashboardsdisplaysizeandlocationwidth", 40) + "");


                //下方确认按钮
                Button btn_ok = view_sal.findViewById(R.id.display_sal_ok_btn);
                //点击OK修改设置  发送广播
                btn_ok.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (MathUtil.stringToInt(et_width.getText().toString()) >= 10 && MathUtil.stringToInt(et_width.getText().toString()) <= 100) {
                            dia_sal.dismiss();
                            Intent intent = new Intent("dashboardsdisplaysizeandlocation");
                            intent.putExtra("width", et_width.getText().toString());
                            mContext.sendBroadcast(intent);
                        } else {
                            Toast.makeText(mContext , getResources().getString(R.string.yourinputisincorrectPleasereenterit) ,
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
                OBDPopDialog dia_dc = new OBDPopDialog(mContext);
                View view_dc = LayoutInflater.from(mContext).inflate(R.layout.dialog_display_edit_dc, null);
                //自定义setWin方法
                setWin(dia_dc);
                dia_dc.setContentView(view_dc);
                dia_dc.setCanceledOnTouchOutside(true);
                dia_dc.show();

            }
        });


        //自定义setWin方法
        setWin(dialog);
        dialog.setContentView(view_dia);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();


    }

    private void setWin(OBDPopDialog dia) {
        Window win = dia.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        win.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = (int) (ScreenUtils.getScreenWidth(mContext) * 0.053333);
        lp.y = (int) (ScreenUtils.getScreenHeight(mContext) * 0.123647);
        win.setAttributes(lp);
    }


    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
        invalidate();
    }

    public void setTextStyle(int textStyle) {
        this.textStyle = textStyle;
        invalidate();
    }


}
