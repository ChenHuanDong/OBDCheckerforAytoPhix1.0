package com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsView;
import com.example.administrator.obdcheckerforaytophix10.tool.ConversionUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.LcndUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.MathUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

import java.io.File;

/**
 * Created by CHD on 2017/8/4.
 */

public class OBDDashboardsFirstPageFragment extends Fragment {

    private RelativeLayout mRe;

    private BroadcastReceiver br;
    private DashboardsView boards_one, boards_two, boards_three, boards_four, boards_five, boards_six;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboards_base, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mRe = view.findViewById(R.id.dashboards_fragment_out_relative);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //新的Display   每次新建必须设置仪表盘id
        boards_one = new DashboardsView(getActivity(), 1, 0);
        initDisplay(boards_one);
        boards_two = new DashboardsView(getActivity(), 2, 0);
        initDisplay(boards_two);
        boards_three = new DashboardsView(getActivity(), 3, 0);
        initDisplay(boards_three);
        boards_four = new DashboardsView(getActivity(), 4, 0);
        initDisplay(boards_four);
        boards_five = new DashboardsView(getActivity(), 5, 0);
        initDisplay(boards_five);
        boards_six = new DashboardsView(getActivity(), 6, 0);
        initDisplay(boards_six);

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mRe.removeAllViews();
                initDisplay(boards_one);
                initDisplay(boards_two);
                initDisplay(boards_three);
                initDisplay(boards_four);
                initDisplay(boards_five);
                initDisplay(boards_six);



            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeDisplay");
        getActivity().registerReceiver(br, intentFilter);

    }

    private void initDisplay(DashboardsView display) {
        display.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        display.setStartAngle((Integer) SPUtil.get(getActivity(), "dashboardsdisplayconfiguration_start_" + display.getMyDisplayId(), 0));
        display.setEndAngle((Integer) SPUtil.get(getActivity(), "dashboardsdisplayconfiguration_end_" + display.getMyDisplayId(), 0));
        display.setColor_back_inner_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_style_back_innercolor_" + display.getMyDisplayId(), "0"));
        display.setColor_back_outer_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_style_back_outercolor_" + display.getMyDisplayId(), "0"));
        display.setColor_title_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_title_color_" + display.getMyDisplayId(), "0"));
        display.setTitle_text_size((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_title_size_" + display.getMyDisplayId(), 0));
        display.setTitle_position((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_title_position_" + display.getMyDisplayId(), 0));
        display.setValueshow((Boolean) SPUtil.get(getActivity(), "dashboardsdisplay_value_show_" + display.getMyDisplayId(), true));
        display.setColor_value("#" + SPUtil.get(getActivity(), "dashboardsdisplay_value_color_" + display.getMyDisplayId(), "0"));
        display.setValue_size((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_value_size_" + display.getMyDisplayId(), 0));
        display.setValue_position((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_value_position_" + display.getMyDisplayId(), 0));
        display.setUnits_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_units_color_" + display.getMyDisplayId(), "0"));
        display.setUnits_size((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_units_size_" + display.getMyDisplayId(), 0));
        display.setUnits_ver((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_units_ver_" + display.getMyDisplayId(), 0));
        display.setUnits_hor((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_units_hor_" + display.getMyDisplayId(), 0));
        display.setMajor_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_major_color_" + display.getMyDisplayId(), "0"));
        display.setMajor_width((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_major_width_" + display.getMyDisplayId(), 0));
        display.setMajor_height((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_major_height_" + display.getMyDisplayId(), 0));
        display.setMinor_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_major_color_" + display.getMyDisplayId(), "0"));
        display.setMinor_width((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_minor_width_" + display.getMyDisplayId(), 0));
        display.setMinor_height((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_minor_height_" + display.getMyDisplayId(), 0));
        display.setTextShow((Boolean) SPUtil.get(getActivity(), "dashboardsdisplay_lable_show_" + display.getMyDisplayId(), true));
        display.setTextRotate((Boolean) SPUtil.get(getActivity(), "dashboardsdisplay_lable_rotate_" + display.getMyDisplayId(), false));
        display.setLable_size((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_lable_size_" + display.getMyDisplayId(), 0));
        display.setLable_offset((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_lable_offset_" + display.getMyDisplayId(), 85));
        display.setPointerShow((Boolean) SPUtil.get(getActivity(), "dashboardsdisplay_pointer_show_" + display.getMyDisplayId(), true));
        display.setPoint_width((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_pointer_width_" + display.getMyDisplayId(), 0));
        display.setPoint_length((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_pointer_length_" + display.getMyDisplayId(), 0));
        display.setPoint_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_pointer_color_" + display.getMyDisplayId(), "0"));
        display.setPoint_rad((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_pointer_rad_" + display.getMyDisplayId(), 0));
        display.setCenter_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_center_color_" + display.getMyDisplayId(), "0"));
        display.setRange_show((Boolean) SPUtil.get(getActivity(), "dashboardsdisplay_range_visible_" + display.getMyDisplayId(), false));
        display.setRange_startrangle((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_range_startAngle_" + display.getMyDisplayId(), 0));
        display.setRange_endrangle((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_range_endAngle_" + display.getMyDisplayId(), 0));
        display.setRange_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_range_color_" + display.getMyDisplayId(), ""));
        display.setMax((Integer) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_value_max_" + display.getMyDisplayId(), 0));
        display.setMin((Integer) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_value_min_" + display.getMyDisplayId(), 0));
        //一会来要把Style 1
        display.setStyle_two_back_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_two_back_color_" + display.getMyDisplayId(), "0"));
        display.setStyle_two_back_rad((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_two_back_rad_" + display.getMyDisplayId(), 0));
        display.setStyle_two_title_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_two_title_color_" + display.getMyDisplayId(), "0"));
        display.setStyle_two_title_size((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_two_title_size_" + display.getMyDisplayId(), 0));
        display.setStyle_two_title_position((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_two_title_position_" + display.getMyDisplayId(), 0));
        display.setIs_two_value_show((Boolean) SPUtil.get(getActivity(), "dashboardsdisplay_two_value_show_" + display.getMyDisplayId(), true));
        display.setStyle_two_value_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_two_value_color_" + display.getMyDisplayId(), "0"));
        display.setStyle_two_value_size((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_two_value_size_" + display.getMyDisplayId(), 0));
        display.setStyle_two_value_position((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_two_value_position_" + display.getMyDisplayId(), 0));
        display.setStyle_two_units_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_two_units_color_" + display.getMyDisplayId(), "0"));
        display.setStyle_two_units_size((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_two_units_size_" + display.getMyDisplayId(), 0));
        display.setStyle_two_units_position((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_two_units_position_" + display.getMyDisplayId(), 0));
        display.setStyle_two_pointer_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_two_pointer_color_" + display.getMyDisplayId(), "0"));
        display.setStyle_two_pointer_width((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_two_pointer_width_" + display.getMyDisplayId(), 0));
        display.setStyle_two_range_show((Boolean) SPUtil.get(getActivity(), "dashboardsdisplay_two_range_show_" + display.getMyDisplayId(), true));
        display.setStyle_two_range_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_two_range_color_" + display.getMyDisplayId(), "0"));
        //Style 2
        display.setStyle_three_inner_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_three_inner_color_" + display.getMyDisplayId(), "0"));
        display.setStyle_three_outer_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_three_outer_color_" + display.getMyDisplayId(), "0"));
        display.setStyle_three_back_rad((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_three_back_rad_" + display.getMyDisplayId(), 0));
        display.setStyle_three_title_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_three_title_color_" + display.getMyDisplayId(), "0"));
        display.setStyle_three_title_size((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_three_title_size_" + display.getMyDisplayId(), 0));
        display.setStyle_three_title_position((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_three_title_position_" + display.getMyDisplayId(), 0));
        display.setStyle_three_value_show((Boolean) SPUtil.get(getActivity(), "dashboardsdisplay_three_value_show_" + display.getMyDisplayId(), true));
        display.setStyle_three_value_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_three_value_color_" + display.getMyDisplayId(), "0"));
        display.setStyle_three_value_size((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_three_value_size_" + display.getMyDisplayId(), 0));
        display.setStyle_three_value_position((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_three_value_position_" + display.getMyDisplayId(), 0));
        display.setStyle_three_units_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_three_units_color_" + display.getMyDisplayId(), "0"));
        display.setStyle_three_units_size((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_three_units_size_" + display.getMyDisplayId(), 0));
        display.setStyle_three_units_position((Integer) SPUtil.get(getActivity(), "dashboardsdisplay_three_units_position_" + display.getMyDisplayId(), 0));
        display.setStyle_three_frame_color("#" + SPUtil.get(getActivity(), "dashboardsdisplay_three_frame_color_" + display.getMyDisplayId(), "0"));

        mRe.addView(display,
                //                   把传过来的数转化成Int型   然后  通过自定义方法变成x123  形式  变成百分比  乘以宽度x375
                setMyParams((float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth_" + display.getMyDisplayId(), (int) 0) * 0.01),
                        //          把传过来的数变成Float型   然后 变成百分比  乘以宽度x375
                        (float) (375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_" + display.getMyDisplayId(), (float) 0.0) * 0.01),
                        //
                        (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_" + display.getMyDisplayId(), (float) 0.0) * 0.01)));

    }

    private RelativeLayout.LayoutParams setMyParams(float widthMy, float leftMy, float topMy) {
        //1080   1776
        int width = ScreenUtils.getScreenWidth(getActivity());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) ConversionUtil.myWantValue(width, widthMy),
                (int) ConversionUtil.myWantValue(width, (float) (widthMy + 13.0)));
        params.leftMargin = (int) ConversionUtil.myWantValue(width, leftMy);
        params.topMargin = (int) ConversionUtil.myWantValue(width, topMy);
        return params;
    }

    private DashboardsView editSizeAndLocation(Intent intent, DashboardsView display, int displayId, int displayStyle, float width, float left, float top, int id) {
        mRe.removeView(display);
        display.setMyDisplayId(displayId);
        display.setStyle(displayStyle);
        display.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        display.setStartAngle(0);
        mRe.addView(display,
                //                   把传过来的数转化成Int型   然后  通过自定义方法变成x123  形式  变成百分比  乘以宽度x375
                setMyParams((float) (375.0f * width * 0.01),
                        //          把传过来的数变成Float型   然后 变成百分比  乘以宽度x375
                        (float) (375.0f * left * 0.01),
                        //
                        (float) (572.0f * top * 0.01)));
        SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocationwidth_" + id, MathUtil.stringToInt(intent.getStringExtra("width")));
        SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocation_left_" + id, MathUtil.stringToFloat(intent.getStringExtra("left")));
        SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocation_top_" + id, MathUtil.stringToFloat(intent.getStringExtra("top")));

        return display;
    }

    private void editDisplayConfiguration(DashboardsView display, Intent intent, int spid) {
        display.setMin(MathUtil.stringToInt(intent.getStringExtra("startValue")));
        display.setMax(MathUtil.stringToInt(intent.getStringExtra("endValue")));
        SPUtil.put(getActivity(), "dashboardsdisplayconfiguration_start_" + spid,
                MathUtil.stringToInt(intent.getStringExtra("startValue")));
        SPUtil.put(getActivity(), "dashboardsdisplayconfiguration_end_" + spid,
                MathUtil.stringToInt(intent.getStringExtra("endValue")));
    }


}
