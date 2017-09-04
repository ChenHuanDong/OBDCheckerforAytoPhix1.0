package com.example.administrator.obdcheckerforaytophix10.dashboards;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsView;
import com.example.administrator.obdcheckerforaytophix10.main.obd.OBDPopDialog;
import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;
import com.example.administrator.obdcheckerforaytophix10.tool.StringUtil;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.slider.AlphaSlider;
import com.flask.colorpicker.slider.LightnessSlider;

import ch.ielse.view.SwitchView;

public class OBDOtherStyleActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private DashboardsView display;
    //传过来的id
    private int displayId = 0;
    //传过来的Style
    private int displayStyle = 0;
    private ScrollView styleTwo, styleThree;
    //int  类型的颜色 用来保存的
    private int colorPickColor = 0;
    //不同的Style  代表不同的 ET点击
    private int et_style = 0;

    private EditText et_two_back_color, et_two_title_color, et_two_value_color, et_two_units_color, et_two_pointer_dolor,
            et_two_range_color, et_three_inner_color, et_three_outer_color, et_three_title_color, et_three_value_color,
            et_three_units_color, et_three_frame_color;
    private Button btn_two_back_color, btn_two_title_color, btn_two_value_color, btn_two_units_color, btn_two_pointer_dolor,
            btn_two_range_color, btn_three_inner_color, btn_three_outer_color, btn_three_title_color, btn_three_value_color,
            btn_three_units_color, btn_three_frame_color;

    private TextView tv_two_back_rad, tv_two_title_font, tv_two_title_position, tv_two_value_size, tv_two_value_position,
            tv_two_units_size, tv_two_units_position, tv_two_pointer_width, tv_three_back_rad, tv_three_title_size,
            tv_three_title_position, tv_three_value_size, tv_three_value_position, tv_three_units_size, tv_three_units_position;
    private SeekBar seek_two_back_rad, seek_two_title_font, seek_two_title_position, seek_two_value_size, seek_two_value_position,
            seek_two_units_size, seek_two_units_position, seek_two_pointer_width, seek_value, seek_three_back_rad, seek_three_title_size,
            seek_three_title_position, seek_three_value_size, seek_three_value_position, seek_three_units_size, seek_three_units_position;

    private SwitchView iosbtn_two_value_show, iosbtn_two_range_show, iosbtn_three_value_show;

    private ImageView iv_finish;

    private RelativeLayout mRe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏0
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_obdother_style);

        initView();
        Intent intent = getIntent();
        displayId = intent.getIntExtra("DisplayId", 0);

        display = new DashboardsView(this, displayId);

        display.setClickable(false);
        displayStyle = intent.getIntExtra("DisplayStyle", 0);
        if (displayStyle == 1) {
            display.setStyle(1);
            styleTwo.setVisibility(View.VISIBLE);
            styleThree.setVisibility(View.GONE);
        } else {
            display.setStyle(2);
            styleTwo.setVisibility(View.GONE);
            styleThree.setVisibility(View.VISIBLE);
        }
        //style 2 back color
        btn_two_back_color.setBackgroundColor(Color.parseColor("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_back_color_" + displayId).getColor()));
        et_two_back_color.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_back_color_" + displayId).getColor());

        //style 2 back rad
        seek_two_back_rad.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_back_rad_" + displayId).getValue());
        tv_two_back_rad.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_back_rad_" + displayId).getValue() + "");

        //style 2 title color
        btn_two_title_color.setBackgroundColor(Color.parseColor("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_color_" + displayId).getColor()));
        et_two_title_color.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_color_" + displayId).getColor());

        //style 2 title font
        seek_two_title_font.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_size_" + displayId).getValue());
        tv_two_title_font.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_size_" + displayId).getValue() + "");

        //syule 2 title position
        seek_two_title_position.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_position_" + displayId).getValue());
        tv_two_title_position.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_position_" + displayId).getValue() + "");

        //style 2 value  show
        if (DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_show_" + displayId).getIsTure()) {
            iosbtn_two_value_show.setOpened(true);
        }
        //style 2 value color
        btn_two_value_color.setBackgroundColor(Color.parseColor("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_color_" + displayId).getColor()));
        et_two_value_color.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_color_" + displayId).getColor());

        //style 2 calue
        seek_two_value_size.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_size_" + displayId).getValue());
        tv_two_value_size.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_size_" + displayId).getValue() + "");

        //style 2 value position
        seek_two_value_position.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_position_" + displayId).getValue());
        tv_two_value_position.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_position_" + displayId).getValue() + "");

        //style 2 unites color
        btn_two_units_color.setBackgroundColor(Color.parseColor("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_color_" + displayId).getColor()));
        et_two_units_color.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_color_" + displayId).getColor());

        //style 2 units size
        seek_two_units_size.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_size_" + displayId).getValue());

        tv_two_units_size.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_size_" + displayId).getValue() + "");

        //style 2 units position
        seek_two_units_position.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_position_" + displayId).getValue());
        tv_two_units_position.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_position_" + displayId).getValue() + "");

        //style 2 pointer color
        btn_two_pointer_dolor.setBackgroundColor(Color.parseColor("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_pointer_color_" + displayId).getColor()));
        et_two_pointer_dolor.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_pointer_color_" + displayId).getColor());

        //style 2 pointer width
        seek_two_pointer_width.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_pointer_width_" + displayId).getValue());
        tv_two_pointer_width.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_pointer_width_" + displayId).getValue() + "");

        //style 2 range show
        if (DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_range_show_" + displayId).getIsTure()) {
            iosbtn_two_range_show.setOpened(true);
        }
        //style 2 range color
        btn_two_range_color.setBackgroundColor(Color.parseColor("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_range_color_" + displayId).getColor()));
        et_two_range_color.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_range_color_" + displayId).getColor());

        //style 3 back inner
        btn_three_inner_color.setBackgroundColor(Color.parseColor("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_inner_color_" + displayId).getColor()));
        et_three_inner_color.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_inner_color_" + displayId).getColor());

        //style 3 back outer
        btn_three_outer_color.setBackgroundColor(Color.parseColor("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_outer_color_" + displayId).getColor()));
        et_three_outer_color.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_outer_color_" + displayId).getColor());

        //style 3 back rad
        seek_three_back_rad.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_back_rad_" + displayId).getValue());
        tv_three_back_rad.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_back_rad_" + displayId).getValue() + "");

        //style 3 title color
        btn_three_title_color.setBackgroundColor(Color.parseColor("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_color_" + displayId).getColor()));
        et_three_title_color.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_color_" + displayId).getColor());

        //style 3 title size
        seek_three_title_size.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_size_" + displayId).getValue());
        tv_three_title_size.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_size_" + displayId).getValue() + "");

        //style 3 title position
        seek_three_title_position.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_position_" + displayId).getValue());

        tv_three_title_position.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_position_" + displayId).getValue() + "");

        //style 3 value show
        if (DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_show_" + displayId).getIsTure()) {
            iosbtn_three_value_show.setOpened(true);
        }
        //style 3 value color
        btn_three_value_color.setBackgroundColor(Color.parseColor("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_color_" + displayId).getColor()));
        et_three_value_color.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_color_" + displayId).getColor());

        //style 3 value size
        seek_three_value_size.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_size_" + displayId).getValue());
        tv_three_value_size.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_size_" + displayId).getValue() + "");

        //style 3 value position
        seek_three_value_position.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_position_" + displayId).getValue());
        tv_three_value_position.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_position_" + displayId).getValue() + "");

        //style 3 units color
        btn_three_units_color.setBackgroundColor(Color.parseColor("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_color_" + displayId).getColor()));

        et_three_units_color.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_color_" + displayId).getColor());

        //style 3 units size
        seek_three_units_size.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_size_" + displayId).getValue());
        tv_three_units_size.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_size_" + displayId).getValue() + "");

        //style 3 units position
        seek_three_units_position.setProgress(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_position_" + displayId).getValue());
        tv_three_units_position.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_position_" + displayId).getValue() + "");

        //style 3 frame color
        btn_three_frame_color.setBackgroundColor(Color.parseColor("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_frame_color_" + displayId).getColor()));
        et_three_frame_color.setText(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_frame_color_" + displayId).getColor());

        //初始化
        display.setStyle_two_back_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_back_color_" + displayId).getColor());
        display.setStyle_two_back_rad(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_back_rad_" + displayId).getValue());
        display.setStyle_two_title_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_color_" + displayId).getColor());
        display.setStyle_two_title_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_size_" + displayId).getValue());
        display.setStyle_two_title_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_position_" + displayId).getValue());
        display.setIs_two_value_show(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_show_" + displayId).getIsTure());
        display.setStyle_two_value_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_color_" + displayId).getColor());
        display.setStyle_two_value_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_size_" + displayId).getValue());
        display.setStyle_two_value_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_position_" + displayId).getValue());
        display.setStyle_two_units_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_color_" + displayId).getColor());
        display.setStyle_two_units_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_size_" + displayId).getValue());
        display.setStyle_two_units_position((DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_position_" + displayId).getValue()));
        display.setStyle_two_pointer_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_pointer_color_" + displayId).getColor());
        display.setStyle_two_pointer_width(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_pointer_width_" + displayId).getValue());
        display.setStyle_two_range_show(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_range_show_" + displayId).getIsTure());
        display.setStyle_two_range_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_range_color_" + displayId).getColor());

        //Style 2
        display.setStyle_three_inner_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_inner_color_" + displayId).getColor());
        display.setStyle_three_outer_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_outer_color_" + displayId).getColor());
        display.setStyle_three_back_rad(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_back_rad_" + displayId).getValue());
        display.setStyle_three_title_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_color_" + displayId).getColor());
        display.setStyle_three_title_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_size_" + displayId).getValue());
        display.setStyle_three_title_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_position_" + displayId).getValue());
        display.setStyle_three_value_show(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_show_" + displayId).getIsTure());
        display.setStyle_three_value_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_color_" + displayId).getColor());
        display.setStyle_three_value_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_size_" + displayId).getValue());
        display.setStyle_three_value_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_position_" + displayId).getValue());
        display.setStyle_three_units_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_color_" + displayId).getColor());
        display.setStyle_three_units_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_size_" + displayId).getValue());
        display.setStyle_three_units_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_position_" + displayId).getValue());
        display.setStyle_three_frame_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_frame_color_" + displayId).getColor());

        mRe.addView(display);


    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.seek_two_back_rad:
                tv_two_back_rad.setText(i + "");
                display.setStyle_two_back_rad(i);
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_back_rad_" + displayId, i);
                break;
            case R.id.seek_two_title_font:
                display.setStyle_two_title_size(i);
                tv_two_title_font.setText(i + "");
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_title_size_" + displayId, i);
                break;
            case R.id.seek_two_title_position:
                display.setStyle_two_title_position(i);
                tv_two_title_position.setText(i + "");
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_title_position_" + displayId, i);
                break;
            case R.id.seek_two_value_size:
                display.setStyle_two_value_size(i);
                tv_two_value_size.setText(i + "");
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_value_size_" + displayId, i);
                break;
            case R.id.seek_two_value_position:
                tv_two_value_position.setText(i + "");
                display.setStyle_two_value_position(i);
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_value_position_" + displayId, i);
                break;
            case R.id.seek_two_units_size:
                display.setStyle_two_units_size(i);
                tv_two_units_size.setText(i + "");
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_units_size_" + displayId, i);
                break;
            case R.id.seek_two_units_position:
                display.setStyle_two_units_position(i);
                tv_two_units_position.setText(i + "");
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_units_position_" + displayId, i);
                break;
            case R.id.seek_two_pointer_width:
                tv_two_pointer_width.setText(i + "");
                display.setStyle_two_pointer_width(i);
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_two_pointer_width_" + displayId, i);
                break;
            case R.id.seek_value:
                display.setValue(i);
                break;
            case R.id.seek_three_back_rad:
                display.setStyle_three_back_rad(i);
                tv_three_back_rad.setText(i + "");
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_back_rad_" + displayId, i);
                break;
            case R.id.seek_three_title_size:
                display.setStyle_three_title_size(i);
                tv_three_title_size.setText(i + "");
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_title_size_" + displayId, i);
                break;
            case R.id.seek_three_title_position:
                display.setStyle_three_title_position(i);
                tv_three_title_position.setText(i + "");
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_title_position_" + displayId, i);
                break;
            case R.id.seek_three_value_size:
                tv_three_value_size.setText(i + "");
                display.setStyle_three_value_size(i);
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_value_size_" + displayId, i);
                break;
            case R.id.seek_three_value_position:
                tv_three_value_position.setText(i + "");
                display.setStyle_three_value_position(i);
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_value_position_" + displayId, i);
                break;
            case R.id.seek_three_units_size:
                tv_three_units_size.setText(i + "");
                display.setStyle_three_units_size(i);
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_units_size_" + displayId, i);
                break;
            case R.id.seek_three_units_position:
                tv_three_units_position.setText(i + "");
                display.setStyle_three_units_position(i);
                DBTool.getOutInstance().upDateValueByKey("dashboardsdisplay_three_units_position_" + displayId, i);
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

        mRe = (RelativeLayout) findViewById(R.id.re_other_style_display);

        styleTwo = (ScrollView) findViewById(R.id.scroll_style_two);
        styleThree = (ScrollView) findViewById(R.id.scroll_style_three);
        //
        et_two_back_color = (EditText) findViewById(R.id.et_two_back_color);
        et_two_back_color.addTextChangedListener(this);
        btn_two_back_color = (Button) findViewById(R.id.btn_two_back_color);
        btn_two_back_color.setOnClickListener(this);
        //
        tv_two_back_rad = (TextView) findViewById(R.id.tv_two_back_rad);
        seek_two_back_rad = (SeekBar) findViewById(R.id.seek_two_back_rad);
        seek_two_back_rad.setOnSeekBarChangeListener(this);
        //
        et_two_title_color = (EditText) findViewById(R.id.et_two_title_color);
        et_two_title_color.addTextChangedListener(this);
        btn_two_title_color = (Button) findViewById(R.id.btn_two_title_color);
        btn_two_title_color.setOnClickListener(this);
        //
        tv_two_title_font = (TextView) findViewById(R.id.tv_two_title_font);
        seek_two_title_font = (SeekBar) findViewById(R.id.seek_two_title_font);
        seek_two_title_font.setOnSeekBarChangeListener(this);
        //
        tv_two_title_position = (TextView) findViewById(R.id.tv_two_title_position);
        seek_two_title_position = (SeekBar) findViewById(R.id.seek_two_title_position);
        seek_two_title_position.setOnSeekBarChangeListener(this);
        //
        iosbtn_two_value_show = (SwitchView) findViewById(R.id.iosbtn_two_value_show);
        iosbtn_two_value_show.setOnClickListener(this);
        //
        et_two_value_color = (EditText) findViewById(R.id.et_two_value_color);
        et_two_value_color.addTextChangedListener(this);
        btn_two_value_color = (Button) findViewById(R.id.btn_two_value_color);
        btn_two_value_color.setOnClickListener(this);
        //
        tv_two_value_size = (TextView) findViewById(R.id.tv_two_value_size);
        seek_two_value_size = (SeekBar) findViewById(R.id.seek_two_value_size);
        seek_two_value_size.setOnSeekBarChangeListener(this);
        //
        tv_two_value_position = (TextView) findViewById(R.id.tv_two_value_position);
        seek_two_value_position = (SeekBar) findViewById(R.id.seek_two_value_position);
        seek_two_value_position.setOnSeekBarChangeListener(this);
        //
        et_two_units_color = (EditText) findViewById(R.id.et_two_units_color);
        et_two_units_color.addTextChangedListener(this);
        btn_two_units_color = (Button) findViewById(R.id.btn_two_units_color);
        btn_two_units_color.setOnClickListener(this);
        //
        tv_two_units_size = (TextView) findViewById(R.id.tv_two_units_size);
        seek_two_units_size = (SeekBar) findViewById(R.id.seek_two_units_size);
        seek_two_units_size.setOnSeekBarChangeListener(this);
        //
        tv_two_units_position = (TextView) findViewById(R.id.tv_two_units_position);
        seek_two_units_position = (SeekBar) findViewById(R.id.seek_two_units_position);
        seek_two_units_position.setOnSeekBarChangeListener(this);
        //
        et_two_pointer_dolor = (EditText) findViewById(R.id.et_two_pointer_dolor);
        et_two_pointer_dolor.addTextChangedListener(this);
        btn_two_pointer_dolor = (Button) findViewById(R.id.btn_two_pointer_dolor);
        btn_two_pointer_dolor.setOnClickListener(this);
        //
        tv_two_pointer_width = (TextView) findViewById(R.id.tv_two_pointer_width);
        seek_two_pointer_width = (SeekBar) findViewById(R.id.seek_two_pointer_width);
        seek_two_pointer_width.setOnSeekBarChangeListener(this);
        //
        iosbtn_two_range_show = (SwitchView) findViewById(R.id.iosbtn_two_range_show);
        iosbtn_two_range_show.setOnClickListener(this);
        //
        et_two_range_color = (EditText) findViewById(R.id.et_two_range_color);
        et_two_range_color.addTextChangedListener(this);
        btn_two_range_color = (Button) findViewById(R.id.btn_two_range_color);
        btn_two_range_color.setOnClickListener(this);
        //
        seek_value = (SeekBar) findViewById(R.id.seek_value);
        seek_value.setOnSeekBarChangeListener(this);
        //
        et_three_inner_color = (EditText) findViewById(R.id.et_three_inner_color);
        et_three_inner_color.addTextChangedListener(this);
        btn_three_inner_color = (Button) findViewById(R.id.btn_three_inner_color);
        btn_three_inner_color.setOnClickListener(this);
        //
        et_three_outer_color = (EditText) findViewById(R.id.et_three_outer_color);
        et_three_outer_color.addTextChangedListener(this);
        btn_three_outer_color = (Button) findViewById(R.id.btn_three_outer_color);
        btn_three_outer_color.setOnClickListener(this);
        //
        tv_three_back_rad = (TextView) findViewById(R.id.tv_three_back_rad);
        seek_three_back_rad = (SeekBar) findViewById(R.id.seek_three_back_rad);
        seek_three_back_rad.setOnSeekBarChangeListener(this);
        //
        et_three_title_color = (EditText) findViewById(R.id.et_three_title_color);
        et_three_title_color.addTextChangedListener(this);
        btn_three_title_color = (Button) findViewById(R.id.btn_three_title_color);
        btn_three_title_color.setOnClickListener(this);
        //
        tv_three_title_size = (TextView) findViewById(R.id.tv_three_title_size);
        seek_three_title_size = (SeekBar) findViewById(R.id.seek_three_title_size);
        seek_three_title_size.setOnSeekBarChangeListener(this);
        //
        tv_three_title_position = (TextView) findViewById(R.id.tv_three_title_position);
        seek_three_title_position = (SeekBar) findViewById(R.id.seek_three_title_position);
        seek_three_title_position.setOnSeekBarChangeListener(this);
        //
        iosbtn_three_value_show = (SwitchView) findViewById(R.id.iosbtn_three_value_show);
        iosbtn_three_value_show.setOnClickListener(this);
        //
        et_three_value_color = (EditText) findViewById(R.id.et_three_value_color);
        et_three_value_color.addTextChangedListener(this);
        btn_three_value_color = (Button) findViewById(R.id.btn_three_value_color);
        btn_three_value_color.setOnClickListener(this);
        //
        tv_three_value_size = (TextView) findViewById(R.id.tv_three_value_size);
        seek_three_value_size = (SeekBar) findViewById(R.id.seek_three_value_size);
        seek_three_value_size.setOnSeekBarChangeListener(this);
        //
        tv_three_value_position = (TextView) findViewById(R.id.tv_three_value_position);
        seek_three_value_position = (SeekBar) findViewById(R.id.seek_three_value_position);
        seek_three_value_position.setOnSeekBarChangeListener(this);
        //
        et_three_units_color = (EditText) findViewById(R.id.et_three_units_color);
        et_three_units_color.addTextChangedListener(this);
        btn_three_units_color = (Button) findViewById(R.id.btn_three_units_color);
        btn_three_units_color.setOnClickListener(this);
        //
        tv_three_units_size = (TextView) findViewById(R.id.tv_three_units_size);
        seek_three_units_size = (SeekBar) findViewById(R.id.seek_three_units_size);
        seek_three_units_size.setOnSeekBarChangeListener(this);
        //
        tv_three_units_position = (TextView) findViewById(R.id.tv_three_units_position);
        seek_three_units_position = (SeekBar) findViewById(R.id.seek_three_units_position);
        seek_three_units_position.setOnSeekBarChangeListener(this);
        //
        et_three_frame_color = (EditText) findViewById(R.id.et_three_frame_color);
        et_three_frame_color.addTextChangedListener(this);
        btn_three_frame_color = (Button) findViewById(R.id.btn_three_frame_color);
        btn_three_frame_color.setOnClickListener(this);
        //
        iv_finish = (ImageView) findViewById(R.id.iv_other_finish);
        iv_finish.setOnClickListener(this);

    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_two_back_color:
                showColorPickDialog("dashboardsdisplay_two_back_color_" + displayId, btn_two_back_color, et_two_back_color);
                et_two_back_color.requestFocus();
                et_style = 1;
                break;
            case R.id.btn_two_title_color:
                showColorPickDialog("dashboardsdisplay_two_title_color_" + displayId, btn_two_title_color, et_two_title_color);
                et_two_title_color.requestFocus();
                et_style = 2;
                break;
            case R.id.iosbtn_two_value_show:
                if ((boolean) SPUtil.get(this, "dashboardsdisplay_two_value_show_" + displayId, true)) {
                    iosbtn_two_value_show.setOpened(false);
                    display.setIs_two_value_show(false);
                    SPUtil.put(this, "dashboardsdisplay_two_value_show_" + displayId, false);
                } else {
                    iosbtn_two_value_show.setOpened(true);
                    display.setIs_two_value_show(true);
                    SPUtil.put(this, "dashboardsdisplay_two_value_show_" + displayId, true);
                }
                break;
            case R.id.iosbtn_two_range_show:
                if ((boolean) SPUtil.get(this, "dashboardsdisplay_two_range_show_" + displayId, true)) {
                    iosbtn_two_range_show.setOpened(false);
                    display.setStyle_two_range_show(false);
                    SPUtil.put(this, "dashboardsdisplay_two_range_show_" + displayId, false);
                } else {
                    iosbtn_two_range_show.setOpened(true);
                    display.setStyle_two_range_show(true);
                    SPUtil.put(this, "dashboardsdisplay_two_range_show_" + displayId, true);
                }
                break;
            case R.id.iosbtn_three_value_show:
                if ((boolean) SPUtil.get(this, "dashboardsdisplay_three_value_show_" + displayId, true)) {
                    iosbtn_three_value_show.setOpened(false);
                    SPUtil.put(this, "dashboardsdisplay_three_value_show_" + displayId, false);
                    display.setStyle_three_value_show(false);
                } else {
                    iosbtn_three_value_show.setOpened(true);
                    SPUtil.put(this, "dashboardsdisplay_three_value_show_" + displayId, true);
                    display.setStyle_three_value_show(true);
                }
                break;
            case R.id.btn_two_value_color:
                showColorPickDialog("dashboardsdisplay_two_value_color_" + displayId, btn_two_value_color, et_two_value_color);
                et_two_value_color.requestFocus();
                et_style = 3;
                break;
            case R.id.btn_two_units_color:
                showColorPickDialog("dashboardsdisplay_two_units_color_" + displayId, btn_two_units_color, et_two_units_color);
                et_two_units_color.requestFocus();
                et_style = 4;
                break;
            case R.id.btn_two_pointer_dolor:
                showColorPickDialog("dashboardsdisplay_two_pointer_color_" + displayId, btn_two_pointer_dolor, et_two_pointer_dolor);
                et_two_pointer_dolor.requestFocus();
                et_style = 5;
                break;
            case R.id.btn_two_range_color:
                showColorPickDialog("dashboardsdisplay_two_range_color_" + displayId, btn_two_range_color, et_two_range_color);
                et_two_range_color.requestFocus();
                et_style = 6;
                break;
            case R.id.btn_three_inner_color:
                showColorPickDialog("dashboardsdisplay_three_inner_color_" + displayId, btn_three_inner_color, et_three_inner_color);
                et_three_inner_color.requestFocus();
                et_style = 7;
                break;
            case R.id.btn_three_outer_color:
                showColorPickDialog("dashboardsdisplay_three_outer_color_" + displayId, btn_three_outer_color, et_three_outer_color);
                et_three_outer_color.requestFocus();
                et_style = 8;
                break;
            case R.id.btn_three_title_color:
                showColorPickDialog("dashboardsdisplay_three_title_color_" + displayId, btn_three_title_color, et_three_title_color);
                et_three_title_color.requestFocus();
                et_style = 9;
                break;
            case R.id.btn_three_value_color:
                showColorPickDialog("dashboardsdisplay_three_value_color_" + displayId, btn_three_value_color, et_three_value_color);
                et_three_value_color.requestFocus();
                et_style = 10;
                break;
            case R.id.btn_three_units_color:
                showColorPickDialog("dashboardsdisplay_three_units_color_" + displayId, btn_three_units_color, et_three_units_color);
                et_three_units_color.requestFocus();
                et_style = 11;
                break;
            case R.id.btn_three_frame_color:
                showColorPickDialog("dashboardsdisplay_three_frame_color_" + displayId, btn_three_frame_color, et_three_frame_color);
                et_three_frame_color.requestFocus();
                et_style = 12;
                break;
            case R.id.iv_finish:
                //这里要发送广播  然后重新绘制
                Intent intent = new Intent("changeDisplay");
                sendBroadcast(intent);
                finish();
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
        iv_show.setBackgroundColor(Color.parseColor("#" + DBTool.getOutInstance().getQueryKey(initkey).getColor()));

        //find colorPickerView   设置属性
        ColorPickerView colorpick = view_dia.findViewById(R.id.colorpick);
        lightnessSlider.setColorPicker(colorpick);
        alphaSlider.setColorPicker(colorpick);
        colorpick.setDensity(12);
        //设置默认颜色
        colorpick.setInitialColor(Color.parseColor("#" + DBTool.getOutInstance().getQueryKey(initkey).getColor()), true);

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
                //点击外部取消的时候把数据存到SP里面了
                DBTool.getOutInstance().upDateColorByKey(initkey, a);
                if (et_style == 1) {
                    display.setStyle_two_back_color("#" + a);
                } else if (et_style == 2) {
                    display.setStyle_two_title_color("#" + a);
                } else if (et_style == 3) {
                    display.setStyle_two_value_color("#" + a);
                } else if (et_style == 4) {
                    display.setStyle_two_units_color("#" + a);
                } else if (et_style == 5) {
                    display.setStyle_two_pointer_color("#" + a);
                } else if (et_style == 6) {
                    display.setStyle_two_range_color("#" + a);
                } else if (et_style == 7) {
                    display.setStyle_three_inner_color("#" + a);
                } else if (et_style == 8) {
                    display.setStyle_three_outer_color("#" + a);
                } else if (et_style == 9) {
                    display.setStyle_three_title_color("#" + a);
                } else if (et_style == 10) {
                    display.setStyle_three_value_color("#" + a);
                } else if (et_style == 11) {
                    display.setStyle_three_units_color("#" + a);
                } else if (et_style == 12) {
                    display.setStyle_three_frame_color("#" + a);
                }


            }
        });

        setWin(dialog);
        dialog.setContentView(view_dia);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }


    //et  监听
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String color_str = charSequence.toString();
        String replace = "00000000";
        color_str = color_str.length() > 7 ? color_str :
                color_str + replace.substring(0, 8 - color_str.length());
        if (et_two_back_color.hasFocus()) {
            btn_two_back_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setStyle_two_back_color("#" + color_str);
            DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_back_color_" + displayId, color_str);
        } else if (et_two_title_color.hasFocus()) {
            btn_two_title_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setStyle_two_title_color("#" + color_str);
            DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_title_color_" + displayId, color_str);
        } else if (et_two_value_color.hasFocus()) {
            btn_two_value_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setStyle_two_value_color("#" + color_str);
            DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_value_color_" + displayId, color_str);
        } else if (et_two_units_color.hasFocus()) {
            btn_two_units_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setStyle_two_units_color("#" + color_str);
            DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_units_color_" + displayId, color_str);
        } else if (et_two_pointer_dolor.hasFocus()) {
            btn_two_pointer_dolor.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setStyle_two_pointer_color("#" + color_str);
            DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_pointer_color_" + displayId, color_str);
        } else if (et_two_range_color.hasFocus()) {
            btn_two_range_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setStyle_two_range_color("#" + color_str);
            DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_two_range_color_" + displayId, color_str);
        } else if (et_three_inner_color.hasFocus()) {
            btn_three_inner_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setStyle_three_inner_color("#" + color_str);
            DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_inner_color_" + displayId, color_str);
        } else if (et_three_outer_color.hasFocus()) {
            btn_three_outer_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setStyle_three_outer_color("#" + color_str);
            DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_outer_color_" + displayId, color_str);
        } else if (et_three_title_color.hasFocus()) {
            btn_three_title_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setStyle_three_title_color("#" + color_str);
            DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_title_color_" + displayId, color_str);
        } else if (et_three_value_color.hasFocus()) {
            btn_three_value_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setStyle_three_value_color("#" + color_str);
            DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_value_color_" + displayId, color_str);
        } else if (et_three_units_color.hasFocus()) {
            btn_three_units_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setStyle_three_units_color("#" + color_str);
            DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_units_color_" + displayId, color_str);
        } else if (et_three_frame_color.hasFocus()) {
            btn_three_frame_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setStyle_three_frame_color("#" + color_str);
            DBTool.getOutInstance().upDateColorByKey("dashboardsdisplay_three_frame_color_" + displayId, color_str);
        }


    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void setWin(OBDPopDialog dia) {
        Window win = dia.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        win.setGravity(Gravity.LEFT | Gravity.TOP);
        lp.x = (int) (ScreenUtils.getScreenWidth(this) * 0.053333);
        lp.y = (int) (ScreenUtils.getScreenHeight(this) * 0.05);
        win.setAttributes(lp);
    }

    @Override
    public void onBackPressed() {
        //这里要发送广播  然后重新绘制
        Intent intent = new Intent("changeDisplay");
        sendBroadcast(intent);
        finish();
    }
}
