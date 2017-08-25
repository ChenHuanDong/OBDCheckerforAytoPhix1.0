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
import android.widget.TextView;

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

import ch.ielse.view.SwitchView;

public class OBDStyleActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, TextWatcher {

    private RadioButton btn_frame, btn_axis, btn_needle, btn_range;
    private ScrollView scroll_frame, scroll_axis, scroll_needle, scroll_range;
    private DashboardsView display;
    private SeekBar seek_value, seek_rangles_startangle, seek_rangles_endargle, seek_title_font, seek_title_position, seek_value_size,
            seek_value_position, seek_units_size, seek_units_ver, seek_units_hor, seek_major_width, seek_major_height,
            seek_minor_width, seek_minor_height, seek_lable_size, seek_lable_offset, seek_pointer_width, seek_pointer_length,
            seek_pointer_rad, seek_range_start, seek_range_end;
    private TextView tv_rangles_startangle, tv_rangles_endargle, tv_title_font, tv_title_position, tv_value_size,
            tv_value_position, tv_units_size, tv_units_ver, tv_units_hor, tv_major_width, tv_major_height,
            tv_minor_width, tv_minor_height, tv_lable_size, tv_lable_offset, tv_pointer_width, tv_pointer_length,
            tv_pointer_rad, tv_range_start, tv_range_end;
    private EditText et_back_color, et_back_out_color, et_title_color, et_value_color, et_units_color, et_mojor_color,
            et_minor_color, et_pointer_color, et_center_color, et_range_color;
    private Button btn_back_color, btn_back_out_color, btn_title_color, btn_value_color, btn_units_color, btn_mojor_color,
            btn_minor_color, btn_pointer_color, btn_center_color, btn_range_color;
    //int  类型的颜色 用来保存的
    private int colorPickColor = 0;
    //不同的Style  代表不同的 ET点击
    private int et_style = 0;
    //传过来的ID
    private int displayId = 0;

    private SwitchView iosbtn_value_show, iosbtn_lables_show, iosbtn_lables_rotate, iosbtn_pointer_show, iosbtn_range_show;
    private ImageView iv_finish;

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
        displayId = intent.getIntExtra("DisplayId", 0);
        display.setStyle(0);
        //设置Value  按照角度比输入   旋转角度 = （最大-最小）/ 分度
        //seekBar  是按照百分比
        seek_value.setProgress(0);

        seek_rangles_startangle.setProgress((int) ((Integer) SPUtil.get(this, "dashboardsdisplayconfiguration_start_" + displayId, (int) 0) / 3.6f));
        seek_rangles_endargle.setProgress((int) ((Integer) SPUtil.get(this, "dashboardsdisplayconfiguration_end_" + displayId, (int) 0) / 3.6f));
        //给seekbar   右上角的Tv设置文字
        tv_rangles_startangle.setText((int) (seek_rangles_startangle.getProgress() * 3.6f) + "");
        tv_rangles_endargle.setText((int) (seek_rangles_endargle.getProgress() * 3.6f) + "");
        display.setValue((int) (0 * 3.6f));
        //
        et_back_color.setText((String) SPUtil.get(this, "dashboardsdisplay_style_back_innercolor_" + displayId, "0"));
        et_back_out_color.setText((String) SPUtil.get(this, "dashboardsdisplay_style_back_outercolor_" + displayId, "0"));
        btn_back_color.setBackgroundColor(Color.parseColor("#" + (String) SPUtil.get(this, "dashboardsdisplay_style_back_innercolor_" + displayId, "0")));
        btn_back_out_color.setBackgroundColor(Color.parseColor("#" + (String) SPUtil.get(this, "dashboardsdisplay_style_back_outercolor_" + displayId, "0")));
        //Title
        et_title_color.setText((String) SPUtil.get(this, "dashboardsdisplay_title_color_" + displayId, "00000000"));
        btn_title_color.setBackgroundColor(Color.parseColor("#" + (String) SPUtil.get(this, "dashboardsdisplay_title_color_" + displayId, "0")));
        //title font
        seek_title_font.setProgress((Integer) SPUtil.get(this, "dashboardsdisplay_title_size_" + displayId, 0));
        tv_title_font.setText((Integer) SPUtil.get(this, "dashboardsdisplay_title_size_" + displayId, 0) + "");
        //title position
        seek_title_position.setProgress((Integer) SPUtil.get(this, "dashboardsdisplay_title_position_" + displayId, 0));
        tv_title_position.setText((Integer) SPUtil.get(this, "dashboardsdisplay_title_position_" + displayId, 0) + "");
        //calue visible
        if ((boolean) SPUtil.get(this, "dashboardsdisplay_value_show_" + displayId, true)) {
            iosbtn_value_show.setOpened(true);
        }
        //value color
        btn_value_color.setBackgroundColor(Color.parseColor("#" + (String) SPUtil.get(this, "dashboardsdisplay_value_color_" + displayId, "0")));
        et_value_color.setText((String) SPUtil.get(this, "dashboardsdisplay_value_color_" + displayId, "0"));
        //value  size     默认是12
        seek_value_size.setProgress((int) (Integer) SPUtil.get(this, "dashboardsdisplay_value_size_" + displayId, 0));
        tv_value_size.setText((int) (Integer) SPUtil.get(this, "dashboardsdisplay_value_size_" + displayId, 0) + "");
        //value  position
        seek_value_position.setProgress((Integer) SPUtil.get(this, "dashboardsdisplay_value_position_" + displayId, 0));
        tv_value_position.setText((Integer) SPUtil.get(this, "dashboardsdisplay_value_position_" + displayId, 0) + "");
        //units  color   三个
        btn_units_color.setBackgroundColor(Color.parseColor("#" + SPUtil.get(this, "dashboardsdisplay_units_color_" + displayId, "0")));
        et_units_color.setText((String) SPUtil.get(this, "dashboardsdisplay_units_color_" + displayId, "0"));
        //units size
        seek_units_size.setProgress((Integer) SPUtil.get(this, "dashboardsdisplay_units_size_" + displayId, 0));
        tv_units_size.setText((Integer) SPUtil.get(this, "dashboardsdisplay_units_size_" + displayId, 0) + "");
        //units ver
        seek_units_ver.setProgress((Integer) SPUtil.get(this, "dashboardsdisplay_units_ver_" + displayId, 0));
        tv_units_ver.setText((Integer) SPUtil.get(this, "dashboardsdisplay_units_ver_" + displayId, 0) + "");
        //units hor
        seek_units_hor.setProgress((Integer) SPUtil.get(this, "dashboardsdisplay_units_hor_" + displayId, 0));
        tv_units_hor.setText((Integer) SPUtil.get(this, "dashboardsdisplay_units_hor_" + displayId, 0) + "");
        //major width
        seek_major_width.setProgress((int) SPUtil.get(this, "dashboardsdisplay_major_width_" + displayId, (int) 0));
        tv_major_width.setText((int) SPUtil.get(this, "dashboardsdisplay_major_width_" + displayId, (int) 0) + "");
        //maijor  height   37
        seek_major_height.setProgress((int) SPUtil.get(this, "dashboardsdisplay_major_height_" + displayId, 0));
        tv_major_height.setText((int) SPUtil.get(this, "dashboardsdisplay_major_height_" + displayId, 0) + "");
        //major color
        btn_mojor_color.setBackgroundColor(Color.parseColor("#" + SPUtil.get(this, "dashboardsdisplay_major_color_" + displayId, "0")));
        et_mojor_color.setText((String) SPUtil.get(this, "dashboardsdisplay_major_color_" + displayId, "0"));
        //minor width
        seek_minor_width.setProgress((int) SPUtil.get(this, "dashboardsdisplay_minor_width_" + displayId, (int) 0));
        tv_minor_width.setText((int) SPUtil.get(this, "dashboardsdisplay_minor_width_" + displayId, (int) 0) + "");
        //minor height
        seek_minor_height.setProgress((Integer) SPUtil.get(this, "dashboardsdisplay_minor_height_" + displayId, 0));
        tv_minor_height.setText((Integer) SPUtil.get(this, "dashboardsdisplay_minor_height_" + displayId, 0) + "");
        //minor color
        btn_minor_color.setBackgroundColor(Color.parseColor("#" + SPUtil.get(this, "dashboardsdisplay_minor_color_" + displayId, "0")));
        et_minor_color.setText((String) SPUtil.get(this, "dashboardsdisplay_minor_color_" + displayId, "0"));
        //lables  visible
        if ((boolean) SPUtil.get(this, "dashboardsdisplay_lable_show_" + displayId, true)) {
            iosbtn_lables_show.setOpened(true);
        }
        //lables rotate
        if ((boolean) SPUtil.get(this, "dashboardsdisplay_lable_rotate_" + displayId, true)) {
            iosbtn_lables_rotate.setOpened(true);
        }
        //lable size
        seek_lable_size.setProgress((Integer) SPUtil.get(this, "dashboardsdisplay_lable_size_" + displayId, 0));
        tv_lable_size.setText((Integer) SPUtil.get(this, "dashboardsdisplay_lable_size_" + displayId, 0) + "");
        //lable offset
        seek_lable_offset.setProgress((Integer) SPUtil.get(this, "dashboardsdisplay_lable_offset_" + displayId, 85));
        tv_lable_offset.setText((Integer) SPUtil.get(this, "dashboardsdisplay_lable_offset_" + displayId, 85) + "");
        //pointer show
        if ((boolean) SPUtil.get(this, "dashboardsdisplay_pointer_show_" + displayId, true)) {
            iosbtn_pointer_show.setOpened(true);
        }
        //pointer width
        seek_pointer_width.setProgress((int) SPUtil.get(this, "dashboardsdisplay_pointer_width_" + displayId, 2));
        tv_pointer_width.setText((int) SPUtil.get(this, "dashboardsdisplay_pointer_width_" + displayId, 2) + "");
        //pointer length
        seek_pointer_length.setProgress((Integer) SPUtil.get(this, "dashboardsdisplay_pointer_length_" + displayId, 40));
        tv_pointer_length.setText((Integer) SPUtil.get(this, "dashboardsdisplay_pointer_length_" + displayId, 40) + "");
        //pointer  color
        btn_pointer_color.setBackgroundColor(Color.parseColor("#" + SPUtil.get(this, "dashboardsdisplay_pointer_color_" + displayId, "0")));
        et_pointer_color.setText((String) SPUtil.get(this, "dashboardsdisplay_pointer_color_" + displayId, "0"));
        //point  rad
        seek_pointer_rad.setProgress((Integer) SPUtil.get(this, "dashboardsdisplay_pointer_rad_" + displayId, 5));
        tv_pointer_rad.setText((Integer) SPUtil.get(this, "dashboardsdisplay_pointer_rad_" + displayId, 5) + "");
        //center color
        btn_center_color.setBackgroundColor(Color.parseColor("#" + SPUtil.get(this, "dashboardsdisplay_center_color_" + displayId, "0")));
        et_center_color.setText((String) SPUtil.get(this, "dashboardsdisplay_center_color_" + displayId, "0"));
        //range visible
        if ((boolean) SPUtil.get(this, "dashboardsdisplay_range_visible_" + displayId, false)) {
            iosbtn_range_show.setOpened(true);
        }
        //range startangle
        seek_range_start.setProgress((int) ((int) SPUtil.get(this, "dashboardsdisplay_range_startAngle_" + displayId, 0) / 3.6f));
        tv_range_start.setText((int) ((int) SPUtil.get(this, "dashboardsdisplay_range_startAngle_" + displayId, 0) / 3.6f) + "");
        //range endargle
        seek_range_end.setProgress((int) ((Integer) SPUtil.get(this, "dashboardsdisplay_range_endAngle_" + displayId, 0) / 3.6f));
        tv_range_end.setText((int) ((Integer) SPUtil.get(this, "dashboardsdisplay_range_endAngle_" + displayId, 0) / 3.6f) + "");
        //range  color
        btn_range_color.setBackgroundColor(Color.parseColor("#" + SPUtil.get(this, "dashboardsdisplay_range_color_" + displayId, "0")));
        et_range_color.setText((String) SPUtil.get(this, "dashboardsdisplay_range_color_" + displayId, "0"));


        display.setStartAngle((int) ((Integer) SPUtil.get(this, "dashboardsdisplayconfiguration_start_" + displayId, 0)));
        display.setEndAngle((int) ((Integer) SPUtil.get(this, "dashboardsdisplayconfiguration_end_" + displayId, 0)));
        display.setColor_back_inner_color("#" + SPUtil.get(this, "dashboardsdisplay_style_back_innercolor_" + displayId, "0"));
        display.setColor_back_outer_color("#" + SPUtil.get(this, "dashboardsdisplay_style_back_outercolor_" + displayId, "0"));
        display.setColor_title_color("#" + SPUtil.get(this, "dashboardsdisplay_title_color_" + displayId, "0"));
        display.setTitle_text_size((Integer) SPUtil.get(this, "dashboardsdisplay_title_size_" + displayId, 0));
        display.setTitle_position((Integer) SPUtil.get(this, "dashboardsdisplay_title_position_" + displayId, 0));
        display.setValueshow((Boolean) SPUtil.get(this, "dashboardsdisplay_value_show_" + displayId, true));
        display.setColor_value("#" + SPUtil.get(this, "dashboardsdisplay_value_color_" + displayId, "0"));
        display.setValue_size((Integer) SPUtil.get(this, "dashboardsdisplay_value_size_" + displayId, 0));
        display.setValue_position((Integer) SPUtil.get(this, "dashboardsdisplay_value_position_" + displayId, 0));
        display.setUnits_color("#" + SPUtil.get(this, "dashboardsdisplay_units_color_" + displayId, "0"));
        display.setUnits_size((Integer) SPUtil.get(this, "dashboardsdisplay_units_size_" + displayId, 0));
        display.setUnits_ver((Integer) SPUtil.get(this, "dashboardsdisplay_units_ver_" + displayId, 0));
        display.setUnits_hor((Integer) SPUtil.get(this, "dashboardsdisplay_units_hor_" + displayId, 0));
        display.setMajor_color("#" + SPUtil.get(this, "dashboardsdisplay_major_color_" + displayId, "0"));
        display.setMajor_width((Integer) SPUtil.get(this, "dashboardsdisplay_major_width_" + displayId, 0));
        display.setMajor_height((Integer) SPUtil.get(this, "dashboardsdisplay_major_height_" + displayId, 0));
        display.setMinor_color("#" + SPUtil.get(this, "dashboardsdisplay_major_color_" + displayId, "0"));
        display.setMinor_width((Integer) SPUtil.get(this, "dashboardsdisplay_minor_width_" + displayId, 0));
        display.setMinor_height((Integer) SPUtil.get(this, "dashboardsdisplay_minor_height_" + displayId, 0));
        display.setTextShow((Boolean) SPUtil.get(this, "dashboardsdisplay_lable_show_" + displayId, true));
        display.setTextRotate((Boolean) SPUtil.get(this, "dashboardsdisplay_lable_rotate_" + displayId, false));
        display.setLable_size((Integer) SPUtil.get(this, "dashboardsdisplay_lable_size_" + displayId, 0));
        display.setLable_offset((Integer) SPUtil.get(this, "dashboardsdisplay_lable_offset_" + displayId, 85));
        display.setPointerShow((Boolean) SPUtil.get(this, "dashboardsdisplay_pointer_show_" + displayId, true));
        display.setPoint_width((Integer) SPUtil.get(this, "dashboardsdisplay_pointer_width_" + displayId, 0));
        display.setPoint_length((Integer) SPUtil.get(this, "dashboardsdisplay_pointer_length_" + displayId, 0));
        display.setPoint_color("#" + SPUtil.get(this, "dashboardsdisplay_pointer_color_" + displayId, "0"));
        display.setPoint_rad((Integer) SPUtil.get(this, "dashboardsdisplay_pointer_rad_" + displayId, 0));
        display.setCenter_color("#" + SPUtil.get(this, "dashboardsdisplay_center_color_" + displayId, "0"));
        display.setRange_show((Boolean) SPUtil.get(this, "dashboardsdisplay_range_visible_" + displayId, false));
        display.setRange_startrangle((Integer) SPUtil.get(this, "dashboardsdisplay_range_startAngle_" + displayId, 0));
        display.setRange_endrangle((Integer) SPUtil.get(this, "dashboardsdisplay_range_endAngle_" + displayId, 0));
        display.setRange_color("#" + SPUtil.get(this, "dashboardsdisplay_range_color_" + displayId, ""));
        display.setMax((Integer) SPUtil.get(this, "dashboardsdisplaysizeandlocation_value_max_" + displayId, 0));
        display.setMin((Integer) SPUtil.get(this, "dashboardsdisplaysizeandlocation_value_min_" + displayId, 0));


    }

    //seekbar  滑动监听
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.seek_value:
                display.setValue((int) (i * 3.6f));
                break;
            case R.id.seek_rangles_startangle:
                display.setStartAngle((int) (i * 3.6f));
                tv_rangles_startangle.setText((int) (i * 3.6f) + "");
                //把数据存到SP里面
                SPUtil.put(this, "dashboardsdisplayconfiguration_start_" + displayId, (int) (i * 3.6f));
                break;
            case R.id.seek_rangles_endargle:
                display.setEndAngle((int) (i * 3.6f));
                tv_rangles_endargle.setText((int) (i * 3.6f) + "");
                SPUtil.put(this, "dashboardsdisplayconfiguration_end_" + displayId, (int) (i * 3.6f));
                break;
            case R.id.seek_title_font:
                display.setTitle_text_size(i);
                tv_title_font.setText(i + "");
                SPUtil.put(this, "dashboardsdisplay_title_size_" + displayId, i);
                break;
            case R.id.seek_title_position:
                display.setTitle_position(i);
                tv_title_position.setText(i + "");
                SPUtil.put(this, "dashboardsdisplay_title_position_" + displayId, i);
                break;
            case R.id.seek_value_size:
                display.setValue_size(i);
                tv_value_size.setText(i + "");
                SPUtil.put(this, "dashboardsdisplay_value_size_" + displayId, i);
                break;
            case R.id.seek_value_position:
                display.setValue_position(i);
                tv_value_position.setText(i + "");
                SPUtil.put(this, "dashboardsdisplay_value_position_" + displayId, i);
                break;
            case R.id.seek_units_size:
                display.setUnits_size(i);
                tv_units_size.setText(i + "");
                SPUtil.put(this, "dashboardsdisplay_units_size_" + displayId, i);
                break;
            case R.id.seek_units_ver:
                display.setUnits_ver(i);
                tv_units_ver.setText(i + "");
                SPUtil.put(this, "dashboardsdisplay_units_ver_" + displayId, i);
                break;
            case R.id.seek_units_hor:
                display.setUnits_hor(i);
                tv_units_hor.setText(i + "");
                SPUtil.put(this, "dashboardsdisplay_units_hor_" + displayId, i);
                break;
            case R.id.seek_major_width:
                tv_major_width.setText(i + "");
                display.setMajor_width(i);
                SPUtil.put(this, "dashboardsdisplay_major_width_" + displayId, i);
                break;
            case R.id.seek_major_height:
                display.setMajor_height(i);
                tv_major_height.setText(i + "");
                SPUtil.put(this, "dashboardsdisplay_major_height_" + displayId, i);
                break;
            case R.id.seek_minor_width:
                tv_minor_width.setText(i + "");
                display.setMinor_width(i);
                SPUtil.put(this, "dashboardsdisplay_minor_width_" + displayId, i);
                break;
            case R.id.seek_minor_height:
                display.setMinor_height(i);
                tv_minor_height.setText(i + "");
                SPUtil.put(this, "dashboardsdisplay_minor_height_" + displayId, i);
                break;
            case R.id.seek_lable_size:
                display.setLable_size(i);
                tv_lable_size.setText(i + "");
                SPUtil.put(this, "dashboardsdisplay_lable_size_" + displayId, i);
                break;
            case R.id.seek_lable_offset:
                display.setLable_offset(i);
                tv_lable_offset.setText(i + "");
                SPUtil.put(this, "dashboardsdisplay_lable_offset_" + displayId, i);
                break;
            case R.id.seek_pointer_width:
                display.setPoint_width(i);
                tv_pointer_width.setText(i + "");
                SPUtil.put(this, "dashboardsdisplay_pointer_width_" + displayId, i);
                break;
            case R.id.seek_pointer_length:
                display.setPoint_length(i);
                tv_pointer_length.setText(i + "");
                SPUtil.put(this, "dashboardsdisplay_pointer_length_" + displayId, i);
                break;
            case R.id.seek_pointer_rad:
                display.setPoint_rad(i);
                tv_pointer_rad.setText(i + "");
                SPUtil.put(this, "dashboardsdisplay_pointer_rad_" + displayId, i);
                break;
            case R.id.seek_range_start:
                display.setRange_startrangle((int) (i * 3.6f));
                tv_range_start.setText((int) (i * 3.6f) + "");
                SPUtil.put(this, "dashboardsdisplay_range_startAngle_" + displayId, (int) (i * 3.6f));
                break;
            case R.id.seek_range_end:
                display.setRange_endrangle((int) (i * 3.6f));
                tv_range_end.setText((int) (i * 3.6f) + "");
                SPUtil.put(this, "dashboardsdisplay_range_endAngle_" + displayId, (int) (i * 3.6f));
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
        tv_title_font = (TextView) findViewById(R.id.tv_title_font);
        seek_value.setOnSeekBarChangeListener(this);
        seek_rangles_startangle.setOnSeekBarChangeListener(this);
        seek_rangles_endargle.setOnSeekBarChangeListener(this);
        et_back_color = (EditText) findViewById(R.id.et_back_color);
        btn_back_color = (Button) findViewById(R.id.btn_back_color);
        btn_back_color.setOnClickListener(this);
        //设置文字监听  点击事件
        et_back_color.addTextChangedListener(this);
        et_back_out_color = (EditText) findViewById(R.id.et_back_out_color);
        et_back_out_color.addTextChangedListener(this);
        btn_back_out_color = (Button) findViewById(R.id.btn_back_out_color);
        btn_back_out_color.setOnClickListener(this);
        et_title_color = (EditText) findViewById(R.id.et_title_color);
        et_title_color.addTextChangedListener(this);
        btn_title_color = (Button) findViewById(R.id.btn_title_color);
        btn_title_color.setOnClickListener(this);
        //
        tv_rangles_startangle = (TextView) findViewById(R.id.tv_rangles_startangle);
        tv_rangles_endargle = (TextView) findViewById(R.id.tv_rangles_endargle);
        seek_title_font = (SeekBar) findViewById(R.id.seek_title_font);
        seek_title_font.setOnSeekBarChangeListener(this);
        //
        seek_title_position = (SeekBar) findViewById(R.id.seek_title_position);
        seek_title_position.setOnSeekBarChangeListener(this);
        tv_title_position = (TextView) findViewById(R.id.tv_title_position);
        //
        iosbtn_value_show = (SwitchView) findViewById(R.id.iosbtn_value_show);
        iosbtn_value_show.setOnClickListener(this);
        //
        et_value_color = (EditText) findViewById(R.id.et_value_color);
        et_value_color.addTextChangedListener(this);
        //
        btn_value_color = (Button) findViewById(R.id.btn_value_color);
        btn_value_color.setOnClickListener(this);
        //
        tv_value_size = (TextView) findViewById(R.id.tv_value_size);
        seek_value_size = (SeekBar) findViewById(R.id.seek_value_size);
        seek_value_size.setOnSeekBarChangeListener(this);
        //
        tv_value_position = (TextView) findViewById(R.id.tv_value_position);
        seek_value_position = (SeekBar) findViewById(R.id.seek_value_position);
        seek_value_position.setOnSeekBarChangeListener(this);
        //
        et_units_color = (EditText) findViewById(R.id.et_units_color);
        et_units_color.addTextChangedListener(this);
        btn_units_color = (Button) findViewById(R.id.btn_units_color);
        btn_units_color.setOnClickListener(this);
        //
        tv_units_size = (TextView) findViewById(R.id.tv_units_size);
        seek_units_size = (SeekBar) findViewById(R.id.seek_units_size);
        seek_units_size.setOnSeekBarChangeListener(this);
        //
        tv_units_ver = (TextView) findViewById(R.id.tv_units_ver);
        seek_units_ver = (SeekBar) findViewById(R.id.seek_units_ver);
        seek_units_ver.setOnSeekBarChangeListener(this);
        //
        tv_units_hor = (TextView) findViewById(R.id.tv_units_hor);
        seek_units_hor = (SeekBar) findViewById(R.id.seek_units_hor);
        seek_units_hor.setOnSeekBarChangeListener(this);
        //
        tv_major_width = (TextView) findViewById(R.id.tv_major_width);
        seek_major_width = (SeekBar) findViewById(R.id.seek_major_width);
        seek_major_width.setOnSeekBarChangeListener(this);
        //
        tv_major_height = (TextView) findViewById(R.id.tv_major_height);
        seek_major_height = (SeekBar) findViewById(R.id.seek_major_height);
        seek_major_height.setOnSeekBarChangeListener(this);
        //
        et_mojor_color = (EditText) findViewById(R.id.et_mojor_color);
        et_mojor_color.addTextChangedListener(this);
        btn_mojor_color = (Button) findViewById(R.id.btn_mojor_color);
        btn_mojor_color.setOnClickListener(this);
        //
        tv_minor_width = (TextView) findViewById(R.id.tv_minor_width);
        seek_minor_width = (SeekBar) findViewById(R.id.seek_minor_width);
        seek_minor_width.setOnSeekBarChangeListener(this);
        //
        tv_minor_height = (TextView) findViewById(R.id.tv_minor_height);
        seek_minor_height = (SeekBar) findViewById(R.id.seek_minor_height);
        seek_minor_height.setOnSeekBarChangeListener(this);
        //
        et_minor_color = (EditText) findViewById(R.id.et_minor_color);
        et_minor_color.addTextChangedListener(this);
        btn_minor_color = (Button) findViewById(R.id.btn_minor_color);
        btn_minor_color.setOnClickListener(this);
        //
        iosbtn_lables_show = (SwitchView) findViewById(R.id.iosbtn_lables_show);
        iosbtn_lables_show.setOnClickListener(this);
        //
        iosbtn_lables_rotate = (SwitchView) findViewById(R.id.iosbtn_lables_rotate);
        iosbtn_lables_rotate.setOnClickListener(this);
        //
        tv_lable_size = (TextView) findViewById(R.id.tv_lable_size);
        seek_lable_size = (SeekBar) findViewById(R.id.seek_lable_size);
        seek_lable_size.setOnSeekBarChangeListener(this);
        //
        tv_lable_offset = (TextView) findViewById(R.id.tv_lable_offset);
        seek_lable_offset = (SeekBar) findViewById(R.id.seek_lable_offset);
        seek_lable_offset.setOnSeekBarChangeListener(this);
        //
        iosbtn_pointer_show = (SwitchView) findViewById(R.id.iosbtn_pointer_show);
        iosbtn_pointer_show.setOnClickListener(this);
        //
        tv_pointer_width = (TextView) findViewById(R.id.tv_pointer_width);
        seek_pointer_width = (SeekBar) findViewById(R.id.seek_pointer_width);
        seek_pointer_width.setOnSeekBarChangeListener(this);
        //
        tv_pointer_length = (TextView) findViewById(R.id.tv_pointer_length);
        seek_pointer_length = (SeekBar) findViewById(R.id.seek_pointer_length);
        seek_pointer_length.setOnSeekBarChangeListener(this);
        //
        et_pointer_color = (EditText) findViewById(R.id.et_pointer_color);
        et_pointer_color.addTextChangedListener(this);
        btn_pointer_color = (Button) findViewById(R.id.btn_pointer_color);
        btn_pointer_color.setOnClickListener(this);
        //
        tv_pointer_rad = (TextView) findViewById(R.id.tv_pointer_rad);
        seek_pointer_rad = (SeekBar) findViewById(R.id.seek_pointer_rad);
        seek_pointer_rad.setOnSeekBarChangeListener(this);
        //
        et_center_color = (EditText) findViewById(R.id.et_center_color);
        et_center_color.addTextChangedListener(this);
        btn_center_color = (Button) findViewById(R.id.btn_center_color);
        btn_center_color.setOnClickListener(this);
        //
        iosbtn_range_show = (SwitchView) findViewById(R.id.iosbtn_range_show);
        iosbtn_range_show.setOnClickListener(this);
        //
        tv_range_start = (TextView) findViewById(R.id.tv_range_start);
        seek_range_start = (SeekBar) findViewById(R.id.seek_range_start);
        seek_range_start.setOnSeekBarChangeListener(this);
        //
        tv_range_end = (TextView) findViewById(R.id.tv_range_end);
        seek_range_end = (SeekBar) findViewById(R.id.seek_range_end);
        seek_range_end.setOnSeekBarChangeListener(this);
        //
        et_range_color = (EditText) findViewById(R.id.et_range_color);
        et_range_color.addTextChangedListener(this);
        btn_range_color = (Button) findViewById(R.id.btn_range_color);
        btn_range_color.setOnClickListener(this);
        //
        iv_finish = (ImageView) findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);


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
                //这个以后要改的   _  + displayid          点击的时候还要让左边的ET获得焦点
                showColorPickDialog("dashboardsdisplay_style_back_innercolor_" + displayId, btn_back_color, et_back_color);
                et_style = 1;
                et_back_color.requestFocus();
                break;
            case R.id.btn_back_out_color:
                //点击出现Dialog     点击的时候还要让左边的ET获得焦点
                showColorPickDialog("dashboardsdisplay_style_back_outercolor_" + displayId, btn_back_out_color, et_back_out_color);
                et_style = 2;
                et_back_out_color.requestFocus();
                break;
            case R.id.btn_title_color:
                showColorPickDialog("dashboardsdisplay_title_color_" + displayId, btn_title_color, et_title_color);
                et_style = 3;
                et_title_color.requestFocus();
                break;
            case R.id.btn_value_color:
                showColorPickDialog("dashboardsdisplay_value_color_" + displayId, btn_value_color, et_value_color);
                et_style = 4;
                et_value_color.requestFocus();
                break;
            case R.id.btn_units_color:
                showColorPickDialog("dashboardsdisplay_units_color_" + displayId, btn_units_color, et_units_color);
                et_style = 5;
                et_units_color.requestFocus();
                break;
            case R.id.iosbtn_value_show:
                if ((boolean) SPUtil.get(this, "dashboardsdisplay_value_show_" + displayId, true)) {
                    iosbtn_value_show.setOpened(false);
                    SPUtil.put(this, "dashboardsdisplay_value_show_" + displayId, false);
                    display.setValueshow(false);
                } else {
                    iosbtn_value_show.setOpened(true);
                    SPUtil.put(this, "dashboardsdisplay_value_show_" + displayId, true);
                    display.setValueshow(true);
                }
                break;
            case R.id.iosbtn_lables_show:
                if ((boolean) SPUtil.get(this, "dashboardsdisplay_lable_show_" + displayId, true)) {
                    iosbtn_lables_show.setOpened(false);
                    SPUtil.put(this, "dashboardsdisplay_lable_show_" + displayId, false);
                    display.setTextShow(false);
                } else {
                    iosbtn_lables_show.setOpened(true);
                    SPUtil.put(this, "dashboardsdisplay_lable_show_" + displayId, true);
                    display.setTextShow(true);
                }
                break;
            case R.id.iosbtn_lables_rotate:
                if ((boolean) SPUtil.get(this, "dashboardsdisplay_lable_rotate_" + displayId, false)) {
                    iosbtn_lables_rotate.setOpened(false);
                    SPUtil.put(this, "dashboardsdisplay_lable_rotate_" + displayId, false);
                    display.setTextRotate(false);
                } else {
                    iosbtn_lables_rotate.setOpened(true);
                    SPUtil.put(this, "dashboardsdisplay_lable_rotate_" + displayId, true);
                    display.setTextRotate(true);
                }
                break;
            case R.id.iosbtn_pointer_show:
                if ((boolean) SPUtil.get(this, "dashboardsdisplay_pointer_show_" + displayId, true)) {
                    iosbtn_pointer_show.setOpened(false);
                    display.setPointerShow(false);
                    SPUtil.put(this, "dashboardsdisplay_pointer_show_" + displayId, false);
                } else {
                    SPUtil.put(this, "dashboardsdisplay_pointer_show_" + displayId, true);
                    display.setPointerShow(true);
                    iosbtn_pointer_show.setOpened(true);
                }
                break;
            case R.id.iosbtn_range_show:
                if ((boolean) SPUtil.get(this, "dashboardsdisplay_range_visible_" + displayId, false)) {
                    iosbtn_range_show.setOpened(false);
                    SPUtil.put(this, "dashboardsdisplay_range_visible_" + displayId, false);
                    display.setRange_show(false);
                } else {
                    iosbtn_range_show.setOpened(true);
                    SPUtil.put(this, "dashboardsdisplay_range_visible_" + displayId, true);
                    display.setRange_show(true);
                }
                break;
            case R.id.btn_mojor_color:
                showColorPickDialog("dashboardsdisplay_major_color_" + displayId, btn_mojor_color, et_mojor_color);
                et_style = 6;
                et_mojor_color.requestFocus();
                break;
            case R.id.btn_minor_color:
                showColorPickDialog("dashboardsdisplay_minor_color_" + displayId, btn_minor_color, et_minor_color);
                et_style = 7;
                et_minor_color.requestFocus();
                break;
            case R.id.btn_pointer_color:
                showColorPickDialog("dashboardsdisplay_pointer_color_" + displayId, btn_pointer_color, et_pointer_color);
                et_style = 8;
                et_pointer_color.requestFocus();
                break;
            case R.id.btn_center_color:
                showColorPickDialog("dashboardsdisplay_center_color_" + displayId, btn_center_color, et_center_color);
                et_style = 9;
                et_center_color.requestFocus();
                break;
            case R.id.btn_range_color:
                showColorPickDialog("dashboardsdisplay_range_color_" + displayId, btn_range_color, et_range_color);
                et_style = 10;
                et_range_color.requestFocus();
                break;
            //返回按钮
            case R.id.iv_finish:
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
        iv_show.setBackgroundColor(Color.parseColor("#" + (String) SPUtil.get(this, initkey,
                "00000000")));
        //find colorPickerView   设置属性
        ColorPickerView colorpick = view_dia.findViewById(R.id.colorpick);
        lightnessSlider.setColorPicker(colorpick);
        alphaSlider.setColorPicker(colorpick);
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
                //点击外部取消的时候把数据存到SP里面了
                SPUtil.put(OBDStyleActivity.this, initkey, a);
                if (et_style == 1) {
                    display.setColor_back_inner_color("#" + a);
                    SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_style_back_innercolor_" + displayId, a);
                } else if (et_style == 2) {
                    display.setColor_back_outer_color("#" + a);
                    SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_style_back_outercolor_" + displayId, a);
                } else if (et_style == 3) {
                    display.setColor_title_color("#" + a);
                    SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_title_color_" + displayId, a);
                } else if (et_style == 4) {
                    display.setColor_value("#" + a);
                    SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_value_color_" + displayId, a);
                } else if (et_style == 5) {
                    display.setUnits_color("#" + a);
                    SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_units_color_" + displayId, a);
                } else if (et_style == 6) {
                    display.setMajor_color("#" + a);
                    SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_major_color_" + displayId, a);
                } else if (et_style == 7) {
                    display.setMinor_color("#" + a);
                    SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_minor_color_" + displayId, a);
                } else if (et_style == 8) {
                    display.setPoint_color("#" + a);
                    SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_pointer_color_" + displayId, a);
                } else if (et_style == 9) {
                    display.setCenter_color("#" + a);
                    SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_center_color_" + displayId, a);
                } else if (et_style == 10) {
                    display.setRange_color("#" + a);
                    SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_range_color_" + displayId, a);
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
        if (et_back_color.hasFocus()) {
            String color_str = charSequence.toString();
            String replace = "00000000";
            color_str = color_str.length() > 7 ? color_str :
                    color_str + replace.substring(0, 8 - color_str.length());
            btn_back_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setColor_back_inner_color("#" + color_str);
            //存SP里面
            SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_style_back_innercolor_" + displayId, color_str);
        } else if (et_back_out_color.hasFocus()) {
            String color_str = charSequence.toString();
            String replace = "00000000";
            color_str = color_str.length() > 7 ? color_str :
                    color_str + replace.substring(0, 8 - color_str.length());
            btn_back_out_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setColor_back_outer_color("#" + color_str);
            SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_style_back_outercolor_" + displayId, color_str);
        } else if (et_title_color.hasFocus()) {
            String color_str = charSequence.toString();
            String replace = "00000000";
            color_str = color_str.length() > 7 ? color_str :
                    color_str + replace.substring(0, 8 - color_str.length());
            btn_title_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setColor_title_color("#" + color_str);
            SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_title_color_" + displayId, color_str);
        } else if (et_value_color.hasFocus()) {
            String color_str = charSequence.toString();
            String replace = "00000000";
            color_str = color_str.length() > 7 ? color_str :
                    color_str + replace.substring(0, 8 - color_str.length());
            btn_value_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setColor_value("#" + color_str);
            SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_value_color_" + displayId, color_str);
        } else if (et_units_color.hasFocus()) {
            String color_str = charSequence.toString();
            String replace = "00000000";
            color_str = color_str.length() > 7 ? color_str :
                    color_str + replace.substring(0, 8 - color_str.length());
            btn_units_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setUnits_color("#" + color_str);
            SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_units_color_" + displayId, color_str);
        } else if (et_mojor_color.hasFocus()) {
            String color_str = charSequence.toString();
            String replace = "00000000";
            color_str = color_str.length() > 7 ? color_str :
                    color_str + replace.substring(0, 8 - color_str.length());
            btn_mojor_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setMajor_color("#" + color_str);
            SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_major_color_" + displayId, color_str);
        } else if (et_minor_color.hasFocus()) {
            String color_str = charSequence.toString();
            String replace = "00000000";
            color_str = color_str.length() > 7 ? color_str :
                    color_str + replace.substring(0, 8 - color_str.length());
            btn_minor_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setMinor_color("#" + color_str);
            SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_minor_color_" + displayId, color_str);
        } else if (et_pointer_color.hasFocus()) {
            String color_str = charSequence.toString();
            String replace = "00000000";
            color_str = color_str.length() > 7 ? color_str :
                    color_str + replace.substring(0, 8 - color_str.length());
            btn_pointer_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setPoint_color("#" + color_str);
            SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_pointer_color_" + displayId, color_str);
        } else if (et_center_color.hasFocus()) {
            String color_str = charSequence.toString();
            String replace = "00000000";
            color_str = color_str.length() > 7 ? color_str :
                    color_str + replace.substring(0, 8 - color_str.length());
            btn_center_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setCenter_color("#" + color_str);
            SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_center_color_" + displayId, color_str);
        } else if (et_range_color.hasFocus()) {
            String color_str = charSequence.toString();
            String replace = "00000000";
            color_str = color_str.length() > 7 ? color_str :
                    color_str + replace.substring(0, 8 - color_str.length());
            btn_range_color.setBackgroundColor(Color.parseColor("#" + color_str));
            display.setRange_color("#" + color_str);
            SPUtil.put(OBDStyleActivity.this, "dashboardsdisplay_range_color_" + displayId, color_str);
        }


    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    //点击返回重新绘制图形
    @Override
    public void onBackPressed() {
        //这里要发送广播  然后重新绘制
        Intent intent = new Intent("changeDisplay");
        sendBroadcast(intent);
        finish();
    }


}
