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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsView;
import com.example.administrator.obdcheckerforaytophix10.tool.ConversionUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.MathUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

/**
 * Created by CHD on 2017/8/4.
 */

public class OBDDashboardsFirstPageFragment extends Fragment {

    private RelativeLayout mRe;

    private BroadcastReceiver br;
    private DashboardsView boards_one;


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

        //新的Display
        boards_one = new DashboardsView(getActivity());
        boards_one.setBackgroundColor(getResources().getColor(R.color.colorOBDbackground));
        boards_one.setStartAngle(0);
        mRe.addView(boards_one, setMyParams((float) (375.0f *0.01 * MathUtil.stringToInt(SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth", 40).toString())),
                25.0f, 10.0f));


        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mRe.removeView(boards_one);


                boards_one = new DashboardsView(getActivity());
                boards_one.setBackgroundColor(getResources().getColor(R.color.colorOBDbackground));
                boards_one.setStartAngle(0);
                mRe.addView(boards_one, setMyParams((float) (375.0f * (float) MathUtil.stringToInt(intent.getStringExtra("width")) * 0.01),
                        0.0f, 0.0f));
                SPUtil.put(getActivity(), "dashboardsdisplaysizeandlocationwidth", MathUtil.stringToInt(intent.getStringExtra("width")));


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
