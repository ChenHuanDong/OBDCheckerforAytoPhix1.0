package com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsView;
import com.example.administrator.obdcheckerforaytophix10.tool.ConversionUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

/**
 * Created by CHD on 2017/8/4.
 */

public class OBDDashboardsSecondPageFragment extends Fragment {

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
                setMyParams((float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth_one", (int) 0) * 0.01),
                        //          把传过来的数变成Float型   然后 变成百分比  乘以宽度x375
                        (float) (375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_one", (float) 0.0) * 0.01),
                        //
                        (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_one", (float) 0.0) * 0.01)));


        boards_two = new DashboardsView(getActivity(), 2, 1);
        boards_two.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        boards_two.setStartAngle(0);
        mRe.addView(boards_two,
                setMyParams((float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth_two", (int) 0) * 0.01),
                        (float) (375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_two", (float) 0.0) * 0.01),
                        (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_two", (float) 0.0) * 0.01)));


        boards_three = new DashboardsView(getActivity(), 3, 1);
        boards_three.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        boards_three.setStartAngle(0);
        mRe.addView(boards_three,
                setMyParams((float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth_three", (int) 0) * 0.01),
                        (float) (375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_three", (float) 0.0) * 0.01),
                        (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_three", (float) 0.0) * 0.01)));

        boards_four = new DashboardsView(getActivity(), 4, 1);
        boards_four.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        boards_four.setStartAngle(0);
        mRe.addView(boards_four,
                setMyParams((float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth_four", (int) 0) * 0.01),
                        (float) (375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_four", (float) 0.0) * 0.01),
                        (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_four", (float) 0.0) * 0.01)));


        boards_five = new DashboardsView(getActivity(), 5, 1);
        boards_five.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        boards_five.setStartAngle(0);
        mRe.addView(boards_five,
                setMyParams((float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth_five", (int) 0) * 0.01),
                        (float) (375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_five", (float) 0.0) * 0.01),
                        (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_five", (float) 0.0) * 0.01)));


        boards_six = new DashboardsView(getActivity(), 6, 1);
        boards_six.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        boards_six.setStartAngle(0);
        mRe.addView(boards_six,
                setMyParams((float) (375.0f * (int) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocationwidth_six", (int) 0) * 0.01),
                        (float) (375.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_left_six", (float) 0.0) * 0.01),
                        (float) (572.0f * (float) SPUtil.get(getActivity(), "dashboardsdisplaysizeandlocation_top_six", (float) 0.0) * 0.01)));



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
