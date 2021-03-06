package com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsView;
import com.example.administrator.obdcheckerforaytophix10.main.servierbt.BluetoothService;
import com.example.administrator.obdcheckerforaytophix10.tool.ConversionUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

/**
 * Created by CHD on 2017/8/4.
 */

public class OBDDashboardsSecondPageFragment extends Fragment {

    private RelativeLayout mRe;

    private BroadcastReceiver br;
    private DashboardsView boards_one, boards_two;

    private BroadcastReceiver br_bring_to_first, br_data , br_orientation;

    private ServiceConnection mConnection;
    private BluetoothService.MyBinder mBinder;
    private View mViewMain;

    private int orientation;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMain = inflater.inflate(R.layout.fragment_dashboards_base, null);
        return mViewMain;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mRe = view.findViewById(R.id.dashboards_fragment_out_relative);
        orientation = getResources().getConfiguration().orientation;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //新的Display   每次新建必须设置仪表盘id
        boards_one = new DashboardsView(getActivity(), 7);
        boards_two = new DashboardsView(getActivity(), 8);

        if (!DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
            initGreenDaoDisplay(boards_one);
            initGreenDaoDisplay(boards_two);

            if (DBTool.getOutInstance().getQueryKey("display_count").getValue() > 9) {
                for (int i = 10; i < DBTool.getOutInstance().getQueryKey("display_count").getValue() + 1; i++) {
                    if (DBTool.getOutInstance().getQueryKey("display_in_fragment_" + i).getValue() == 0) {
                        DashboardsView view = new DashboardsView(getActivity(), i);
                        initGreenDaoDisplay(view);
                    }
                }
            }
        } else {
            initGreenDaoDisplayClassic(boards_one);
            initGreenDaoDisplayClassic(boards_two);

        }


        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
                    mRe.removeAllViews();
                    initGreenDaoDisplay(boards_one);
                    initGreenDaoDisplay(boards_two);

                    if (DBTool.getOutInstance().getQueryKey("display_count").getValue() > 9) {
                        for (int i = 10; i < DBTool.getOutInstance().getQueryKey("display_count").getValue() + 1; i++) {
                            if (DBTool.getOutInstance().getQueryKey("display_in_fragment_" + i).getValue() == 1) {
                                DashboardsView view = new DashboardsView(getActivity(), i);
                                initGreenDaoDisplay(view);
                            }
                        }
                    }
                } else {
                    //这里是经典的
                    mRe.removeAllViews();
                    initGreenDaoDisplayClassic(boards_one);
                    initGreenDaoDisplayClassic(boards_two);

                }


            }
        };

        br_bring_to_first = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int disId = intent.getIntExtra("id", 0);
                if (disId == 7) {
                    boards_one.bringToFront();
                } else if (disId == 8) {
                    boards_two.bringToFront();
                }


            }
        };

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mBinder = (BluetoothService.MyBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };


        br_data = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getStringExtra("key")) {

                }
            }
        };

        br_orientation = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                orientation = getResources().getConfiguration().orientation;

                mRe.removeAllViews();
                if (!DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
                    initGreenDaoDisplay(boards_one);
                    initGreenDaoDisplay(boards_two);

                    //这个是添加了其他的仪表盘的
                    if (DBTool.getOutInstance().getQueryKey("display_count").getValue() > 9) {
                        for (int i = 10; i < DBTool.getOutInstance().getQueryKey("display_count").getValue() + 1; i++) {
                            //在里面判断是哪个Fragment添加了仪表盘
                            if (DBTool.getOutInstance().getQueryKey("display_in_fragment_" + i).getValue() == 0) {
                                DashboardsView view = new DashboardsView(getActivity(), i);
                                initGreenDaoDisplay(view);
                            }
                        }
                    }
                } else {
                    initGreenDaoDisplayClassic(boards_one);
                    initGreenDaoDisplayClassic(boards_two);

                }
            }
        };


        //监听屏幕旋转广播
        IntentFilter intentOr = new IntentFilter();
        intentOr.addAction("Screen_Orientation_Change");
        getActivity().registerReceiver(br_orientation, intentOr);



        //绑定数据广播
        IntentFilter intentData = new IntentFilter();
        intentData.addAction("bluetoothBT---data");
        getActivity().registerReceiver(br_data, intentData);

        //绑定服务
        Intent intent1 = new Intent(getActivity(), BluetoothService.class);
        getActivity().bindService(intent1, mConnection, getActivity().BIND_AUTO_CREATE);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeDisplay");
        getActivity().registerReceiver(br, intentFilter);

        IntentFilter intent_bring = new IntentFilter();
        intent_bring.addAction("bringToFirst");
        getActivity().registerReceiver(br_bring_to_first, intent_bring);


    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        //四种情况  竖屏-竖屏 竖屏-横屏 横屏-竖屏 横屏-横屏
//        //这个 只要不等于0  就是横变竖灹竖变横
//        int a = getResources().getConfiguration().orientation - orientation;
//        orientation = getResources().getConfiguration().orientation;
//
//        if (a != 0) {
//            if (a > 0) {
//                //竖屏变横屏  要把小变小
//                if (!DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
//                    //自定义
//                    for (int i = 7; i < 9; i++) {
//                        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_" + i, DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + i).getValue() - 10);
//                        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_" + i, DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + i).getFloValue() + 10f);
//                    }
//                } else {
//                    //经典
//                    //dashboardsdisplaysizeandlocationwidth_1_default
//                    for (int i = 7; i < 9; i++) {
//                        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_" + i + "_default", DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + i + "_default").getValue() - 10);
//                        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_" + i + "_default", DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + i + "_default").getFloValue() + 10f);
//                    }
//                }
//
//
//            } else {
//                //横屏变竖屏  要把大变小
//                if (!DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
//                    for (int i = 7; i < 9; i++) {
//                        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_" + i, DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + i).getValue() + 10);
//                        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_" + i, DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + i).getFloValue() - 10f);
//                    }
//                } else {
//                    for (int i = 7; i < 9; i++) {
//                        DBTool.getOutInstance().upDateValueByKey("dashboardsdisplaysizeandlocationwidth_" + i + "_default", DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + i + "_default").getValue() + 10);
//                        DBTool.getOutInstance().upDateFloatByKey("dashboardsdisplaysizeandlocation_top_" + i + "_default", DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + i + "_default").getFloValue() - 10f);
//                    }
//                }
//
//            }
//        }
//
//
//        mRe.removeAllViews();
//        if (!DBTool.getOutInstance().getQueryKey("dashboardsisclassic").getIsTure()) {
//            initGreenDaoDisplay(boards_one);
//            initGreenDaoDisplay(boards_two);
//            //这个是添加了其他的仪表盘的
//            if (DBTool.getOutInstance().getQueryKey("display_count").getValue() > 9) {
//                for (int i = 10; i < DBTool.getOutInstance().getQueryKey("display_count").getValue() + 1; i++) {
//                    //在里面判断是哪个Fragment添加了仪表盘
//                    if (DBTool.getOutInstance().getQueryKey("display_in_fragment_" + i).getValue() == 0) {
//                        DashboardsView view = new DashboardsView(getActivity(), i);
//                        initGreenDaoDisplay(view);
//                    }
//                }
//            }
//        } else {
//            initGreenDaoDisplayClassic(boards_one);
//            initGreenDaoDisplayClassic(boards_two);
//        }
//
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        getActivity().unregisterReceiver(br_orientation);
        getActivity().unregisterReceiver(br);
        getActivity().unregisterReceiver(br_bring_to_first);
        getActivity().unbindService(mConnection);
        getActivity().unregisterReceiver(br_data);
        mBinder.setIsFinishLog(true);
    }


    //自定义  模式下调整仪表盘
    //这里的也可以修改   外面加一个if else  先判断仪表的style    0 1 2
    private void initGreenDaoDisplay(DashboardsView display) {
        //最外层加一个如果
        if (!DBTool.getOutInstance().getQueryKey("display_isRemoveDisplay_" + display.getMyDisplayId()).getIsTure()) {
            display.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
            display.setStyle(DBTool.getOutInstance().getQueryKey("display_style_other" + display.getMyDisplayId()).getValue());
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

            if (display.getStyle() == 1) {
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
            } else if (display.getStyle() == 2) {
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
            }

            //这个不行
//            display.setRemoveDisplay(DBTool.getOutInstance().getQueryKey("display_isRemoveDisplay_" + display.getMyDisplayId()).getIsTure());

            //横竖屏的时候  375  572  也要改

            if (orientation == 1) {
                mRe.addView(display,
                        //      把传过来的数转化成Int型   然后  通过自定义方法变成x123  形式  变成百分比  乘以宽度x375
                        setMyParams((float) (375.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + display.getMyDisplayId()).getValue() * 0.01),
                                //          把传过来的数变成Float型   然后 变成百分比  乘以宽度x375
                                (float) (375.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_left_" + display.getMyDisplayId()).getFloValue() * 0.01),
                                //
                                (float) (572.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + display.getMyDisplayId()).getFloValue() * 0.01)));

            } else {
                mRe.addView(display,
                        //      把传过来的数转化成Int型   然后  通过自定义方法变成x123  形式  变成百分比  乘以宽度x375
                        setMyParams((float) (375.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + display.getMyDisplayId()).getValue() * 0.01),
                                //          把传过来的数变成Float型   然后 变成百分比  乘以宽度x375
                                (float) (572.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + display.getMyDisplayId()).getFloValue() * 0.01),
                                //
                                (float) (375.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_left_" + display.getMyDisplayId()).getFloValue() * 0.01)));

            }
        }


    }

    //经典模式下调整仪表盘
    private void initGreenDaoDisplayClassic(DashboardsView display) {
        display.setBackgroundColor(getResources().getColor(R.color.colorTransparent));

        display.setStyle(DBTool.getOutInstance().getQueryKey("display_style_other" + display.getMyDisplayId()).getValue());

        display.setStartAngle(DBTool.getOutInstance().getQueryKey("dashboardsdisplayconfiguration_start_1_default").getValue());
        display.setEndAngle(DBTool.getOutInstance().getQueryKey("dashboardsdisplayconfiguration_end_1_default").getValue());
        display.setColor_back_inner_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_style_back_innercolor_1_default").getColor());
        display.setColor_back_outer_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_style_back_outercolor_1_default").getColor());
        display.setColor_title_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_title_color_1_default").getColor());
        display.setTitle_text_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_title_size_1_default").getValue());
        display.setTitle_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_title_position_1_default").getValue());
        display.setValueshow(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_value_show_1_default").getIsTure());
        display.setColor_value("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_value_color_1_default").getColor());
        display.setValue_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_value_size_1_default").getValue());
        display.setValue_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_value_position_1_default").getValue());
        display.setUnits_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_units_color_1_default").getColor());
        display.setUnits_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_units_size_1_default").getValue());
        display.setUnits_ver(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_units_ver_1_default").getValue());
        display.setUnits_hor(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_units_hor_1_default").getValue());
        display.setMajor_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_major_color_1_default").getColor());
        display.setMajor_width(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_major_width_1_default").getValue());
        display.setMajor_height(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_major_height_1_default").getValue());
        display.setMinor_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_major_color_1_default").getColor());
        display.setMinor_width(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_minor_width_1_default").getValue());
        display.setMinor_height(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_minor_height_1_default").getValue());
        display.setTextShow(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_lable_show_1_default").getIsTure());
        display.setTextRotate(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_lable_rotate_1_default").getIsTure());
        display.setLable_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_lable_size_1_default").getValue());
        display.setLable_offset(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_lable_offset_1_default").getValue());
        display.setPointerShow(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_pointer_show_1_default").getIsTure());
        display.setPoint_width(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_pointer_width_1_default").getValue());
        display.setPoint_length(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_pointer_length_1_default").getValue());
        display.setPoint_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_pointer_color_1_default").getColor());
        display.setPoint_rad(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_pointer_rad_1_default").getValue());
        display.setCenter_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_center_color_1_default").getColor());
        display.setRange_show(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_range_visible_1_default").getIsTure());
        display.setRange_startrangle(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_range_startAngle_1_default").getValue());
        display.setRange_endrangle(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_range_endAngle_1_default").getValue());
        display.setRange_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_range_color_1_default").getColor());
        //最大值最小值应该是按照自定义和经典一样的设置
        display.setMax(DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_value_max_" + display.getMyDisplayId()).getValue());
        display.setMin(DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_value_min_" + display.getMyDisplayId()).getValue());

        //一会来要把Style 1
        display.setStyle_two_back_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_back_color_1_default").getColor());
        display.setStyle_two_back_rad(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_back_rad_1_default").getValue());
        display.setStyle_two_title_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_color_1_default").getColor());
        display.setStyle_two_title_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_size_1_default").getValue());
        display.setStyle_two_title_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_title_position_1_default").getValue());
        display.setIs_two_value_show(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_show_1_default").getIsTure());
        display.setStyle_two_value_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_color_1_default").getColor());
        display.setStyle_two_value_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_size_1_default").getValue());
        display.setStyle_two_value_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_value_position_1_default").getValue());
        display.setStyle_two_units_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_color_1_default").getColor());
        display.setStyle_two_units_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_size_1_default").getValue());
        display.setStyle_two_units_position((DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_units_position_1_default").getValue()));
        display.setStyle_two_pointer_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_pointer_color_1_default").getColor());
        display.setStyle_two_pointer_width(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_pointer_width_1_default").getValue());
        display.setStyle_two_range_show(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_range_show_1_default").getIsTure());
        display.setStyle_two_range_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_two_range_color_1_default").getColor());

        //Style 2
        display.setStyle_three_inner_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_inner_color_1_default").getColor());
        display.setStyle_three_outer_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_outer_color_1_default").getColor());
        display.setStyle_three_back_rad(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_back_rad_1_default").getValue());
        display.setStyle_three_title_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_color_1_default").getColor());
        display.setStyle_three_title_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_size_1_default").getValue());
        display.setStyle_three_title_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_title_position_1_default").getValue());
        display.setStyle_three_value_show(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_show_1_default").getIsTure());
        display.setStyle_three_value_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_color_1_default").getColor());
        display.setStyle_three_value_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_size_1_default").getValue());
        display.setStyle_three_value_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_value_position_1_default").getValue());
        display.setStyle_three_units_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_color_1_default").getColor());
        display.setStyle_three_units_size(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_size_1_default").getValue());
        display.setStyle_three_units_position(DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_units_position_1_default").getValue());
        display.setStyle_three_frame_color("#" + DBTool.getOutInstance().getQueryKey("dashboardsdisplay_three_frame_color_1_default").getColor());


        if (orientation == 1) {
            mRe.addView(display,
                    //                   把传过来的数转化成Int型   然后  通过自定义方法变成x123  形式  变成百分比  乘以宽度x375
                    setMyParams((float) (375.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + display.getMyDisplayId() + "_default").getValue() * 0.01),
                            //          把传过来的数变成Float型   然后 变成百分比  乘以宽度x375
                            (float) (375.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_left_" + display.getMyDisplayId() + "_default").getFloValue() * 0.01),
                            //
                            (float) (572.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + display.getMyDisplayId() + "_default").getFloValue() * 0.01)));

        } else {
            mRe.addView(display,
                    //                   把传过来的数转化成Int型   然后  通过自定义方法变成x123  形式  变成百分比  乘以宽度x375
                    setMyParams((float) (375.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocationwidth_" + display.getMyDisplayId() + "_default").getValue() * 0.01),
                            //          把传过来的数变成Float型   然后 变成百分比  乘以宽度x375
                            (float) (572.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_top_" + display.getMyDisplayId() + "_default").getFloValue() * 0.01),
                            //
                            (float) (375.0f * DBTool.getOutInstance().getQueryKey("dashboardsdisplaysizeandlocation_left_" + display.getMyDisplayId() + "_default").getFloValue() * 0.01)));
        }


    }

    private RelativeLayout.LayoutParams setMyParams(float widthMy, float leftMy, float topMy) {
        //1080   1776
        int width = 0;
        if (orientation == 1) {
            width = ScreenUtils.getScreenWidth(getActivity());
        } else {
            width = ScreenUtils.getScreenHeight(getActivity());
        }

        //多加上下那两个是为了变横屏的时候  防止因为宽度变大了导致添加的仪表盘太大

        //int width = ScreenUtils.getScreenWidth(getActivity());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) ConversionUtil.myWantValue(width, widthMy),
                (int) ConversionUtil.myWantValue(width, (float) (widthMy + 13.0)));
        params.leftMargin = (int) ConversionUtil.myWantValue(width, leftMy);
        params.topMargin = (int) ConversionUtil.myWantValue(width, topMy);
        return params;
    }


}
