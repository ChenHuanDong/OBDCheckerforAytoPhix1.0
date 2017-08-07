package com.example.administrator.obdcheckerforaytophix10.dashboards.dashthreefragment;

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
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

/**
 * Created by CHD on 2017/8/4.
 */

public class OBDDashboardsFirstPageFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout mRe;


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

        final DashboardsView boards_one = new DashboardsView(getActivity());

        boards_one.setStartAngle(0);


        int width = ScreenUtils.getScreenWidth(getActivity());
        //1080   1776
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) ConversionUtil.myWantValue(width, (float) 150.0),
                (int) ConversionUtil.myWantValue(width, (float) 150.0));
        params.leftMargin = (int) ConversionUtil.myWantValue(width, (float) 25.0);
        params.topMargin = (int) ConversionUtil.myWantValue(width, (float) 10.0);
        mRe.addView(boards_one, params);

        boards_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boards_one.setTextStyle(1);
            }
        });


    }


    @Override
    public void onClick(View view) {

    }
}
