package com.example.administrator.obdcheckerforaytophix10.dashboards;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.SeekBar;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsView;
import com.example.administrator.obdcheckerforaytophix10.main.obd.OBDPopDialog;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;
import com.example.administrator.obdcheckerforaytophix10.tool.StringUtil;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.flask.colorpicker.slider.AlphaSlider;
import com.flask.colorpicker.slider.LightnessSlider;

public class OBDStyleActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, TextWatcher {

    private RadioButton btn_frame, btn_axis, btn_needle, btn_range;
    private ScrollView scroll_frame, scroll_axis, scroll_needle, scroll_range;
    private DashboardsView display;
    private SeekBar seek_value, seek_rangles_startangle, seek_rangles_endargle;
    private EditText et_back_color, et_back_out_color;
    private Button btn_back_color, btn_back_out_color;
    //int  类型的颜色 用来保存的
    private int colorPickColor = 0;
    //不同的Style  代表不同的 ET点击
    private int et_style = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏0
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_obdstyle);

        initView();
        Intent intent = getIntent();


        //设置Value  按照角度比输入   旋转角度 = （最大-最小）/ 分度
        //seekBar  是按照百分比
        seek_value.setProgress(0);
        display.setValue(0 * 3.6f);
        display.setStartAngle(90);
        display.setEndAngle(360);
        //ET宽度改成x70
        et_back_color.setText(intent.getStringExtra("back_inner_color"));
        et_back_out_color.setText(intent.getStringExtra("back_outer_color"));
        btn_back_color.setBackgroundColor(Color.parseColor("#" + et_back_color.getText().toString()));
        btn_back_out_color.setBackgroundColor(Color.parseColor("#" + et_back_out_color.getText().toString()));


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.seek_value:
                display.setValue(i * 3.6f);
                break;
            case R.id.seek_rangles_startangle:
                display.setStartAngle(i * 3.6f);
                break;
            case R.id.seek_rangles_endargle:
                display.setEndAngle(i * 3.6f);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    private void initView() {
        btn_frame = (RadioButton) findViewById(R.id.style_frame);
        btn_axis = (RadioButton) findViewById(R.id.style_axis);
        btn_needle = (RadioButton) findViewById(R.id.style_needle);
        btn_range = (RadioButton) findViewById(R.id.style_range);
        btn_frame.setOnClickListener(this);
        btn_axis.setOnClickListener(this);
        btn_needle.setOnClickListener(this);
        btn_range.setOnClickListener(this);
        scroll_frame = (ScrollView) findViewById(R.id.style_scroll_frame);
        scroll_axis = (ScrollView) findViewById(R.id.style_scroll_axis);
        scroll_needle = (ScrollView) findViewById(R.id.style_scroll_needle);
        scroll_range = (ScrollView) findViewById(R.id.style_scroll_range);
        display = (DashboardsView) findViewById(R.id.display_test);
        seek_value = (SeekBar) findViewById(R.id.seek_value);
        seek_rangles_startangle = (SeekBar) findViewById(R.id.seek_rangles_startangle);
        seek_rangles_endargle = (SeekBar) findViewById(R.id.seek_rangles_endargle);
        seek_value.setOnSeekBarChangeListener(this);
        seek_rangles_startangle.setOnSeekBarChangeListener(this);
        seek_rangles_endargle.setOnSeekBarChangeListener(this);
        et_back_color = (EditText) findViewById(R.id.et_back_color);
        btn_back_color = (Button) findViewById(R.id.btn_back_color);
        btn_back_color.setOnClickListener(this);
        //设置文字监听  点击事件
        et_back_color.addTextChangedListener(this);
        et_back_color.setOnClickListener(this);
        et_back_out_color = (EditText) findViewById(R.id.et_back_out_color);
        et_back_out_color.setOnClickListener(this);
        et_back_out_color.addTextChangedListener(this);
        btn_back_out_color = (Button) findViewById(R.id.btn_back_out_color);
        btn_back_out_color.setOnClickListener(this);

        //初始让四个Scrool隐藏
        scroll_frame.setVisibility(View.GONE);
        scroll_axis.setVisibility(View.GONE);
        scroll_needle.setVisibility(View.GONE);
        scroll_range.setVisibility(View.GONE);
        //Frame  已经被点击过
        btn_frame.performClick();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.style_frame:
                scroll_frame.setVisibility(View.VISIBLE);
                scroll_axis.setVisibility(View.GONE);
                scroll_needle.setVisibility(View.GONE);
                scroll_range.setVisibility(View.GONE);
                btn_frame.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_axis.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                btn_needle.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                btn_range.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                break;
            case R.id.style_axis:
                scroll_axis.setVisibility(View.VISIBLE);
                scroll_frame.setVisibility(View.GONE);
                scroll_needle.setVisibility(View.GONE);
                scroll_range.setVisibility(View.GONE);
                btn_frame.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                btn_axis.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_needle.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                btn_range.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                break;
            case R.id.style_needle:
                scroll_needle.setVisibility(View.VISIBLE);
                scroll_frame.setVisibility(View.GONE);
                scroll_axis.setVisibility(View.GONE);
                scroll_range.setVisibility(View.GONE);
                btn_frame.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                btn_axis.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                btn_needle.setTextColor(getResources().getColor(R.color.colorBlack));
                btn_range.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                break;
            case R.id.style_range:
                scroll_range.setVisibility(View.VISIBLE);
                scroll_frame.setVisibility(View.GONE);
                scroll_axis.setVisibility(View.GONE);
                scroll_needle.setVisibility(View.GONE);
                btn_frame.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                btn_axis.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                btn_needle.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                btn_range.setTextColor(getResources().getColor(R.color.colorBlack));
                break;
            case R.id.btn_back_color:
                //点击弹出调色盘Dialog
                //滴定仪弹出Dialog方法
                showColorPickDialog("dashboardsdisplay_style_back_innercolor", btn_back_color, et_back_color);
                et_style = 1;
                break;
            case R.id.btn_back_out_color:
                //点击出现Dialog
                showColorPickDialog("dashboardsdisplay_style_back_outercolor", btn_back_out_color, et_back_out_color);
                et_style = 2;
                break;
            case R.id.et_back_color:
                //点击Et  更换Style  判断输入内容
                et_style = 1;
                break;
            case R.id.et_back_out_color:
                et_style = 2;
                break;
        }
    }

    private void showColorPickDialog(final String initkey, final Button btn_out, final EditText et_out) {
        OBDPopDialog dialog = new OBDPopDialog(this);
        View view_dia = LayoutInflater.from(this).inflate(R.layout.dialog_colorpick, null);
        AlphaSlider alphaSlider = view_dia.findViewById(R.id.alphaSlider);
        LightnessSlider lightnessSlider = view_dia.findViewById(R.id.lightnessSlider);
        final ImageView iv_show = view_dia.findViewById(R.id.iv_show);
        //设置默认右下角展示图片
        iv_show.setBackgroundColor(Color.parseColor("#" + (String) SPUtil.get(this, initkey,
                "00000000")));
        //find colorPickerView   设置属性
        ColorPickerView colorpick = view_dia.findViewById(R.id.colorpick);
        colorpick.setDensity(12);
        //设置默认颜色
        colorpick.setInitialColor(Color.parseColor("#" + (String) SPUtil.get(this, initkey,
                "00000000")), true);

        colorpick.setSelected(true);
        colorpick.setAlphaSlider(alphaSlider);
        colorpick.setLightnessSlider(lightnessSlider);
        colorpick.addOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int i) {
                iv_show.setBackgroundColor(Color.argb(StringUtil.getAlpha(i), StringUtil.getRed(i),
                        StringUtil.getGreen(i), StringUtil.getBlue(i)));
                colorPickColor = i;
            }
        });
        colorpick.addOnColorSelectedListener(new OnColorSelectedListener() {
            @Override
            public void onColorSelected(int i) {

            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                //点击外部取消事件
                btn_out.setBackgroundColor(Color.argb(StringUtil.getAlpha(colorPickColor),
                        StringUtil.getRed(colorPickColor),
                        StringUtil.getGreen(colorPickColor), StringUtil.getBlue(colorPickColor)));
                et_out.setText(Integer.toHexString(colorPickColor));
                //把数据存进去
                String a = Integer.toHexString(colorPickColor);
                a = a.length() > 7 ? a :
                        a + "00000000".substring(0, 8 - a.length());
                SPUtil.put(OBDStyleActivity.this, initkey, a);
                if (et_style == 1) {
                    display.setColor_back_outer_color("#" + a);
                } else if (et_style == 2) {
                    display.setColor_back_inner_color("#" + a);
                }


            }
        });

        setWin(dialog);
        dialog.setContentView(view_dia);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void setWin(OBDPopDialog dia) {
        Window win = dia.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        win.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = (int) (ScreenUtils.getScreenWidth(this) * 0.053333);
        lp.y = (int) (ScreenUtils.getScreenHeight(this) * 0.05);
        win.setAttributes(lp);
    }


    //下面是EditText监听
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (et_style == 1) {
            String color_str = charSequence.toString();
            String replace = "00000000";
            color_str = color_str.length() > 7 ? color_str :
                    color_str + replace.substring(0, 8 - color_str.length());
            btn_back_color.setBackgroundColor(Color.parseColor("#" + color_str));
        } else if (et_style == 2) {
            String color_str = charSequence.toString();
            String replace = "00000000";
            color_str = color_str.length() > 7 ? color_str :
                    color_str + replace.substring(0, 8 - color_str.length());
            btn_back_out_color.setBackgroundColor(Color.parseColor("#" + color_str));
        }


    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    //点击返回重新绘制图形
    @Override
    public void onBackPressed() {
        //这里要发送广播  然后重新绘制

    }
}
