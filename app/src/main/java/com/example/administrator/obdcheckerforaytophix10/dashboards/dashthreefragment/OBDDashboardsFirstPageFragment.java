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
        boards_one.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        mRe.addView(boards_one,
                //                   把传过来的数转化成Int型   然后  通过自定义方法变成x123  形式  变成百分比  乘以宽度x375
                setMyParams((float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth_one", (int) 0) * 0.01),
                        //          把传过来的数变成Float型   然后 变成百分比  乘以宽度x375
                        (float) (375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_one", (float) 0.0) * 0.01),
                        //
                        (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_one", (float) 0.0) * 0.01)));


        boards_two = new DashboardsView(getActivity(), 2, 0);
        boards_two.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        boards_two.setStartAngle(0);
        mRe.addView(boards_two,
                setMyParams((float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth_two", (int) 0) * 0.01),
                        (float) (375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_two", (float) 0.0) * 0.01),
                        (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_two", (float) 0.0) * 0.01)));


        boards_three = new DashboardsView(getActivity(), 3, 0);
        boards_three.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        boards_three.setStartAngle(0);
        mRe.addView(boards_three,
                setMyParams((float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth_three", (int) 0) * 0.01),
                        (float) (375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_three", (float) 0.0) * 0.01),
                        (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_three", (float) 0.0) * 0.01)));

        boards_four = new DashboardsView(getActivity(), 4, 0);
        boards_four.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        boards_four.setStartAngle(0);
        mRe.addView(boards_four,
                setMyParams((float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth_four", (int) 0) * 0.01),
                        (float) (375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_four", (float) 0.0) * 0.01),
                        (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_four", (float) 0.0) * 0.01)));


        boards_five = new DashboardsView(getActivity(), 5, 0);
        boards_five.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        boards_five.setStartAngle(0);
        mRe.addView(boards_five,
                setMyParams((float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth_five", (int) 0) * 0.01),
                        (float) (375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_five", (float) 0.0) * 0.01),
                        (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_five", (float) 0.0) * 0.01)));


        boards_six = new DashboardsView(getActivity(), 6, 0);
        boards_six.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        boards_six.setStartAngle(0);
        mRe.addView(boards_six,
                setMyParams((float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth_six", (int) 0) * 0.01),
                        (float) (375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_six", (float) 0.0) * 0.01),
                        (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_six", (float) 0.0) * 0.01)));


        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String identity = intent.getStringExtra("identity");
                if (identity.equals("SizeAndLocation")) {
                    int myId = intent.getIntExtra("myId", 0);
                    if (myId == 1) {
                        editSizeAndLocation(intent, boards_one, 1, 0, (float) MathUtil.stringToInt(intent.getStringExtra("width")), MathUtil.stringToFloat(intent.getStringExtra("left")), MathUtil.stringToFloat(intent.getStringExtra("top"))
                                , "one");
                    } else if (myId == 2) {
                        editSizeAndLocation(intent, boards_two, 2, 0, (float) MathUtil.stringToInt(intent.getStringExtra("width")), MathUtil.stringToFloat(intent.getStringExtra("left")), MathUtil.stringToFloat(intent.getStringExtra("top"))
                                , "two");
                    } else if (myId == 3) {
                        editSizeAndLocation(intent, boards_three, 3, 0, (float) MathUtil.stringToInt(intent.getStringExtra("width")), MathUtil.stringToFloat(intent.getStringExtra("left")), MathUtil.stringToFloat(intent.getStringExtra("top"))
                                , "three");
                    } else if (myId == 4) {
                        editSizeAndLocation(intent, boards_four, 4, 0, (float) MathUtil.stringToInt(intent.getStringExtra("width")), MathUtil.stringToFloat(intent.getStringExtra("left")), MathUtil.stringToFloat(intent.getStringExtra("top"))
                                , "four");
                    } else if (myId == 5) {
                        editSizeAndLocation(intent, boards_five, 5, 0, (float) MathUtil.stringToInt(intent.getStringExtra("width")), MathUtil.stringToFloat(intent.getStringExtra("left")), MathUtil.stringToFloat(intent.getStringExtra("top"))
                                , "five");
                    } else if (myId == 6) {
                        editSizeAndLocation(intent, boards_six, 6, 0, (float) MathUtil.stringToInt(intent.getStringExtra("width")), MathUtil.stringToFloat(intent.getStringExtra("left")), MathUtil.stringToFloat(intent.getStringExtra("top"))
                                , "six");
                    }
                } else if (identity.equals("BringToFirst")) {
                    int myId = intent.getIntExtra("myId", 0);
                    if (myId == 1) {
                        boards_one.bringToFront();
                    } else if (myId == 2) {
                        boards_two.bringToFront();
                    } else if (myId == 3) {
                        boards_three.bringToFront();
                    } else if (myId == 4) {
                        boards_four.bringToFront();
                    } else if (myId == 5) {
                        boards_five.bringToFront();
                    } else if (myId == 6) {
                        boards_six.bringToFront();
                    }
                } else if (identity.equals("DisplayConfiguration")) {
                    int myId = intent.getIntExtra("myId", 0);
                    if (myId == 1) {
                        editDisplayConfiguration(boards_one, intent, "one");
                    } else if (myId == 2) {
                        editDisplayConfiguration(boards_two, intent, "two");
                    } else if (myId == 3) {
                        editDisplayConfiguration(boards_three, intent, "three");
                    } else if (myId == 4) {
                        editDisplayConfiguration(boards_four, intent, "four");
                    } else if (myId == 5) {
                        editDisplayConfiguration(boards_five, intent, "five");
                    } else if (myId == 6) {
                        editDisplayConfiguration(boards_six, intent, "six");
                    }
                } else if (identity.equals("RemoveDisplay")) {
                    int myId = intent.getIntExtra("myId", 0);
                    if (myId == 1) {
                        mRe.removeView(boards_one);
                    } else if (myId == 2) {
                        mRe.removeView(boards_two);
                    } else if (myId == 3) {
                        mRe.removeView(boards_three);
                    } else if (myId == 4) {
                        mRe.removeView(boards_four);
                    } else if (myId == 5) {
                        mRe.removeView(boards_five);
                    } else if (myId == 6) {
                        mRe.removeView(boards_six);
                    }
                }


            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("dashboardsdisplaysizeandlocation");
        getActivity().registerReceiver(br, intentFilter);

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

    private DashboardsView editSizeAndLocation(Intent intent, DashboardsView display, int displayId, int displayStyle, float width, float left, float top, String id) {
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

    private void editDisplayConfiguration(DashboardsView display, Intent intent, String spid) {
        display.setMin(MathUtil.stringToInt(intent.getStringExtra("startValue")));
        display.setMax(MathUtil.stringToInt(intent.getStringExtra("endValue")));
        SPUtil.put(getActivity(), "dashboardsdisplayconfiguration_start_" + spid,
                MathUtil.stringToInt(intent.getStringExtra("startValue")));
        SPUtil.put(getActivity(), "dashboardsdisplayconfiguration_end_" + spid,
                MathUtil.stringToInt(intent.getStringExtra("endValue")));
    }


}
