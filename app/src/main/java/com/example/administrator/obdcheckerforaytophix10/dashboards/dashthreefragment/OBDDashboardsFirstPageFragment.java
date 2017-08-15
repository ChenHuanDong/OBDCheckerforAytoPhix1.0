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
        boards_one = new DashboardsView(getActivity(), 1, 1);
        boards_one.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        boards_one.setStartAngle(0);
        mRe.addView(boards_one,
                //                   把传过来的数转化成Int型   然后  通过自定义方法变成x123  形式  变成百分比  乘以宽度x375
                setMyParams((float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth", (int) 0) * 0.01),
                        //          把传过来的数变成Float型   然后 变成百分比  乘以宽度x375
                        (float) (375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_one", (float) 0.0) * 0.01),
                        //
                        (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_one", (float) 0.0) * 0.01)));
        //如果是Style2   圆盘中间字体需要换成指定的字体

//        if (boards_one.getStyle() == 1) {
//
//            TextView tv = new TextView(getActivity());
//            //和下面一下
//            tv.setTextSize((float) ((35.0 / 300.0) *
//                    (float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth", (int) 0) * 0.01)));
//
//            tv.setBackgroundColor(Color.rgb(0x91, 0xbe, 0xf0));
//            tv.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
//            tv.setText("2500");
//            tv.setGravity(Gravity.CENTER);
//            tv.setTypeface(font);
//
//
//            mRe.addView(tv, setMyParams_styleTwo(
//                    (float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth", (int) 0) * 0.01 * 0.4333),
//                    (float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth", (int) 0) * 0.01 * 0.1333),
//                    (float) ((375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_one", (float) 0.0) * 0.01)
//                            + (int) ((85.0 / 300.0) *
//                            (float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth", (int) 0) * 0.01))
//                    ),
//
//                    (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_one", (float) 0.0) * 0.01)
//                            + (int) ((140.0 / 300.0) *
//                            (float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth", (int) 0) * 0.01))
//                    )
//
//            );
//
//
//        }


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
                int myId = intent.getIntExtra("myId", 0);

                if (myId == 1) {
                    mRe.removeView(boards_one);
                    boards_one = new DashboardsView(getActivity(), 1, 0);
                    boards_one.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    boards_one.setStartAngle(0);
                    mRe.addView(boards_one,
                            //                   把传过来的数转化成Int型   然后  通过自定义方法变成x123  形式  变成百分比  乘以宽度x375
                            setMyParams((float) (375.0f * (float) MathUtil.stringToInt(intent.getStringExtra("width")) * 0.01),
                                    //          把传过来的数变成Float型   然后 变成百分比  乘以宽度x375
                                    (float) (375.0f * MathUtil.stringToFloat(intent.getStringExtra("left")) * 0.01),
                                    //
                                    (float) (572.0f * MathUtil.stringToFloat(intent.getStringExtra("top")) * 0.01)));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocationwidth", MathUtil.stringToInt(intent.getStringExtra("width")));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocation_left_one", MathUtil.stringToFloat(intent.getStringExtra("left")));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocation_top_one", MathUtil.stringToFloat(intent.getStringExtra("top")));
                } else if (myId == 2) {
                    mRe.removeView(boards_two);
                    boards_two = new DashboardsView(getActivity(), 2, 0);
                    boards_two.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    boards_two.setStartAngle(0);
                    mRe.addView(boards_two,
                            setMyParams((float) (375.0f * (float) MathUtil.stringToInt(intent.getStringExtra("width")) * 0.01),
                                    (float) (375.0f * MathUtil.stringToFloat(intent.getStringExtra("left")) * 0.01),
                                    (float) (572.0f * MathUtil.stringToFloat(intent.getStringExtra("top")) * 0.01)));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocationwidth_two", MathUtil.stringToInt(intent.getStringExtra("width")));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocation_left_two", MathUtil.stringToFloat(intent.getStringExtra("left")));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocation_top_two", MathUtil.stringToFloat(intent.getStringExtra("top")));
                } else if (myId == 3) {
                    mRe.removeView(boards_three);
                    boards_three = new DashboardsView(getActivity(), 3, 0);
                    boards_three.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    boards_three.setStartAngle(0);
                    mRe.addView(boards_three,
                            setMyParams((float) (375.0f * (float) MathUtil.stringToInt(intent.getStringExtra("width")) * 0.01),
                                    (float) (375.0f * MathUtil.stringToFloat(intent.getStringExtra("left")) * 0.01),
                                    (float) (572.0f * MathUtil.stringToFloat(intent.getStringExtra("top")) * 0.01)));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocationwidth_three", MathUtil.stringToInt(intent.getStringExtra("width")));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocation_left_three", MathUtil.stringToFloat(intent.getStringExtra("left")));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocation_top_three", MathUtil.stringToFloat(intent.getStringExtra("top")));
                } else if (myId == 4) {
                    mRe.removeView(boards_four);
                    boards_four = new DashboardsView(getActivity(), 4, 0);
                    boards_four.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    boards_four.setStartAngle(0);
                    mRe.addView(boards_four,
                            setMyParams((float) (375.0f * (float) MathUtil.stringToInt(intent.getStringExtra("width")) * 0.01),
                                    (float) (375.0f * MathUtil.stringToFloat(intent.getStringExtra("left")) * 0.01),
                                    (float) (572.0f * MathUtil.stringToFloat(intent.getStringExtra("top")) * 0.01)));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocationwidth_four", MathUtil.stringToInt(intent.getStringExtra("width")));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocation_left_four", MathUtil.stringToFloat(intent.getStringExtra("left")));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocation_top_four", MathUtil.stringToFloat(intent.getStringExtra("top")));
                } else if (myId == 5) {
                    mRe.removeView(boards_five);
                    boards_five = new DashboardsView(getActivity(), 5, 0);
                    boards_five.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    boards_five.setStartAngle(0);
                    mRe.addView(boards_five,
                            setMyParams((float) (375.0f * (float) MathUtil.stringToInt(intent.getStringExtra("width")) * 0.01),
                                    (float) (375.0f * MathUtil.stringToFloat(intent.getStringExtra("left")) * 0.01),
                                    (float) (572.0f * MathUtil.stringToFloat(intent.getStringExtra("top")) * 0.01)));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocationwidth_five", MathUtil.stringToInt(intent.getStringExtra("width")));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocation_left_five", MathUtil.stringToFloat(intent.getStringExtra("left")));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocation_top_five", MathUtil.stringToFloat(intent.getStringExtra("top")));
                } else if (myId == 6) {
                    mRe.removeView(boards_six);
                    boards_six = new DashboardsView(getActivity(), 6, 0);
                    boards_six.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
                    boards_six.setStartAngle(0);
                    mRe.addView(boards_six,
                            setMyParams((float) (375.0f * (float) MathUtil.stringToInt(intent.getStringExtra("width")) * 0.01),
                                    (float) (375.0f * MathUtil.stringToFloat(intent.getStringExtra("left")) * 0.01),
                                    (float) (572.0f * MathUtil.stringToFloat(intent.getStringExtra("top")) * 0.01)));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocationwidth_six", MathUtil.stringToInt(intent.getStringExtra("width")));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocation_left_six", MathUtil.stringToFloat(intent.getStringExtra("left")));
                    SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocation_top_six", MathUtil.stringToFloat(intent.getStringExtra("top")));
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




}
