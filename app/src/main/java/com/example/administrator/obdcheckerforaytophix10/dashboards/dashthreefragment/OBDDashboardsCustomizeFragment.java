package com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsView;
import com.example.administrator.obdcheckerforaytophix10.tool.ConversionUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

/**
 * Created by CHD on 2017/9/7.
 */

public class OBDDashboardsCustomizeFragment extends Fragment {

    private RelativeLayout mRe;
    private TextView tv_nothing;
    private BroadcastReceiver br;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboards_customize, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mRe = view.findViewById(R.id.dashboards_fragment_out_relative);
        tv_nothing = view.findViewById(R.id.tv_dashboards_customize_fragment);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tv_nothing.setVisibility(View.VISIBLE);

        if (!DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
            if (DBTool.getOutInstance().getQueryKey("display_count").getValue() > 9) {
                for (int i = 10; i < DBTool.getOutInstance().getQueryKey("display_count").getValue() + 1; i++) {
                    if (DBTool.getOutInstance().getQueryKey("display_in_fragment_" + i).getValue() == 3) {
                        DashboardsView view = new DashboardsView(getActivity(), i);
                        initGreenDaoDisplay(view);
                        tv_nothing.setVisibility(View.GONE);
                    }
                }
            }
        }else {
        }

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                tv_nothing.setVisibility(View.VISIBLE);
                if (!DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
                    mRe.removeAllViews();
                    if (DBTool.getOutInstance().getQueryKey("display_count").getValue() > 9) {
                        for (int i = 10; i < DBTool.getOutInstance().getQueryKey("display_count").getValue() + 1; i++) {
                            if (DBTool.getOutInstance().getQueryKey("display_in_fragment_" + i).getValue() == 3) {
                                DashboardsView view = new DashboardsView(getActivity(), i);
                                initGreenDaoDisplay(view);
                                tv_nothing.setVisibility(View.GONE);
                            }
                        }
                    }
                } else {
                }

            }
        };


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeDisplay");
        getActivity().registerReceiver(br, intentFilter);



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(br);
    }

    private void initGreenDaoDisplay(DashboardsView display) {
        display.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        display.setStyle(DBTool.getOutInstance().getQueryKey("display_style_"+display.getMyDisplayId()).getValue());
        display.setStartAngle(DBTool.getOutInstance().getQueryKey("dashboardsdisplayconfiguration_start_" + display.getMyDisplayId()).getValue());
        display.setEndAngle(DBTool.getOutInstance().getQueryKey("dashboardsdisplayconfiguration_end_" + display.getMyDisplayId()).getValue());
        display.setColor_back_inner_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_style_back_innercolor_" + display.getMyDisplayId()).getColor());
        display.setColor_back_outer_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_style_back_outercolor_" + display.getMyDisplayId()).getColor());
        display.setColor_title_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_title_color_" + display.getMyDisplayId()).getColor());
        display.setTitle_text_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_title_size_" + display.getMyDisplayId()).getValue());
        display.setTitle_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_title_position_" + display.getMyDisplayId()).getValue());
        display.setValueshow(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_value_show_" + display.getMyDisplayId()).getIsTure());
        display.setColor_value("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_value_color_" + display.getMyDisplayId()).getColor());
        display.setValue_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_value_size_" + display.getMyDisplayId()).getValue());
        display.setValue_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_value_position_" + display.getMyDisplayId()).getValue());
        display.setUnits_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_units_color_" + display.getMyDisplayId()).getColor());
        display.setUnits_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_units_size_" + display.getMyDisplayId()).getValue());
        display.setUnits_ver(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_units_ver_" + display.getMyDisplayId()).getValue());
        display.setUnits_hor(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_units_hor_" + display.getMyDisplayId()).getValue());
        display.setMajor_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_major_color_" + display.getMyDisplayId()).getColor());
        display.setMajor_width(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_major_width_" + display.getMyDisplayId()).getValue());
        display.setMajor_height(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_major_height_" + display.getMyDisplayId()).getValue());
        display.setMinor_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_major_color_" + display.getMyDisplayId()).getColor());
        display.setMinor_width(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_minor_width_" + display.getMyDisplayId()).getValue());
        display.setMinor_height(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_minor_height_" + display.getMyDisplayId()).getValue());
        display.setTextShow(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_lable_show_" + display.getMyDisplayId()).getIsTure());
        display.setTextRotate(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_lable_rotate_" + display.getMyDisplayId()).getIsTure());
        display.setLable_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_lable_size_" + display.getMyDisplayId()).getValue());
        display.setLable_offset(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_lable_offset_" + display.getMyDisplayId()).getValue());
        display.setPointerShow(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_pointer_show_" + display.getMyDisplayId()).getIsTure());
        display.setPoint_width(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_pointer_width_" + display.getMyDisplayId()).getValue());
        display.setPoint_length(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_pointer_length_" + display.getMyDisplayId()).getValue());
        display.setPoint_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_pointer_color_" + display.getMyDisplayId()).getColor());
        display.setPoint_rad(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_pointer_rad_" + display.getMyDisplayId()).getValue());
        display.setCenter_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_center_color_" + display.getMyDisplayId()).getColor());
        display.setRange_show(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_range_visible_" + display.getMyDisplayId()).getIsTure());
        display.setRange_startrangle(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_range_startAngle_" + display.getMyDisplayId()).getValue());
        display.setRange_endrangle(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_range_endAngle_" + display.getMyDisplayId()).getValue());
        display.setRange_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_range_color_" + display.getMyDisplayId()).getColor());
        display.setMax(DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_value_max_" + display.getMyDisplayId()).getValue());
        display.setMin(DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_value_min_" + display.getMyDisplayId()).getValue());

        //一会来要把Style 1
        display.setStyle_two_back_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_back_color_" + display.getMyDisplayId()).getColor());
        display.setStyle_two_back_rad(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_back_rad_" + display.getMyDisplayId()).getValue());
        display.setStyle_two_title_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_color_" + display.getMyDisplayId()).getColor());
        display.setStyle_two_title_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_size_" + display.getMyDisplayId()).getValue());
        display.setStyle_two_title_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_position_" + display.getMyDisplayId()).getValue());
        display.setIs_two_value_show(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_show_" + display.getMyDisplayId()).getIsTure());
        display.setStyle_two_value_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_color_" + display.getMyDisplayId()).getColor());
        display.setStyle_two_value_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_size_" + display.getMyDisplayId()).getValue());
        display.setStyle_two_value_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_position_" + display.getMyDisplayId()).getValue());
        display.setStyle_two_units_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_color_" + display.getMyDisplayId()).getColor());
        display.setStyle_two_units_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_size_" + display.getMyDisplayId()).getValue());
        display.setStyle_two_units_position((DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_position_" + display.getMyDisplayId()).getValue()));
        display.setStyle_two_pointer_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_pointer_color_" + display.getMyDisplayId()).getColor());
        display.setStyle_two_pointer_width(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_pointer_width_" + display.getMyDisplayId()).getValue());
        display.setStyle_two_range_show(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_range_show_" + display.getMyDisplayId()).getIsTure());
        display.setStyle_two_range_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_range_color_" + display.getMyDisplayId()).getColor());

        //Style 2
        display.setStyle_three_inner_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_inner_color_" + display.getMyDisplayId()).getColor());
        display.setStyle_three_outer_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_outer_color_" + display.getMyDisplayId()).getColor());
        display.setStyle_three_back_rad(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_back_rad_" + display.getMyDisplayId()).getValue());
        display.setStyle_three_title_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_color_" + display.getMyDisplayId()).getColor());
        display.setStyle_three_title_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_size_" + display.getMyDisplayId()).getValue());
        display.setStyle_three_title_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_position_" + display.getMyDisplayId()).getValue());
        display.setStyle_three_value_show(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_show_" + display.getMyDisplayId()).getIsTure());
        display.setStyle_three_value_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_color_" + display.getMyDisplayId()).getColor());
        display.setStyle_three_value_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_size_" + display.getMyDisplayId()).getValue());
        display.setStyle_three_value_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_position_" + display.getMyDisplayId()).getValue());
        display.setStyle_three_units_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_color_" + display.getMyDisplayId()).getColor());
        display.setStyle_three_units_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_size_" + display.getMyDisplayId()).getValue());
        display.setStyle_three_units_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_position_" + display.getMyDisplayId()).getValue());
        display.setStyle_three_frame_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_frame_color_" + display.getMyDisplayId()).getColor());


        display.setRemoveDisplay(DBTool.getOutInstance().getQueryKey("display_isRemoveDisplay_" + display.getMyDisplayId()).getIsTure());


        mRe.addView(display,
                //                   把传过来的数转化成Int型   然后  通过自定义方法变成x123  形式  变成百分比  乘以宽度x375
                setMyParams((float) (375.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + display.getMyDisplayId()).getValue() * 0.01),
                        //          把传过来的数变成Float型   然后 变成百分比  乘以宽度x375
                        (float) (375.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_left_" + display.getMyDisplayId()).getFloValue() * 0.01),
                        //
                        (float) (572.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + display.getMyDisplayId()).getFloValue() * 0.01)));

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


}
