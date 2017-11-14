package com.example.administrator.obdcheckerforaytophix10.dashboards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.obdcheckerforaytophix10.MainFregmentReplaceActivity;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.FileLTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

/**
 * Created by CHD on 2017/9/25.
 */

public class OBDHUDSettingFragment extends Fragment implements View.OnClickListener, FragmentBackListener {

    private ImageView iv_return;
    private RelativeLayout re_green, re_lightblue, re_white, re_yellow, re_blue;
    private ImageView iv_green, iv_lightblue, iv_white, iv_yellow, iv_blue;
    //记录数据库现在存的那个颜色
    private int color_value = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_obdhudsetting, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iv_return = view.findViewById(R.id.iv_return_hud_setting);
        iv_return.setOnClickListener(this);
        re_green = view.findViewById(R.id.re_hud_setting_green);
        re_green.setOnClickListener(this);
        re_lightblue = view.findViewById(R.id.re_hud_setting_lightblue);
        re_lightblue.setOnClickListener(this);
        re_white = view.findViewById(R.id.re_hud_setting_white);
        re_white.setOnClickListener(this);
        re_yellow = view.findViewById(R.id.re_hud_setting_yellow);
        re_yellow.setOnClickListener(this);
        re_blue = view.findViewById(R.id.re_hud_setting_blue);
        re_blue.setOnClickListener(this);
        iv_green = view.findViewById(R.id.iv_hud_dui_green);
        iv_green.setVisibility(View.GONE);
        iv_lightblue = view.findViewById(R.id.iv_hud_dui_lightblue);
        iv_lightblue.setVisibility(View.GONE);
        iv_white = view.findViewById(R.id.iv_hud_dui_white);
        iv_white.setVisibility(View.GONE);
        iv_yellow = view.findViewById(R.id.iv_hud_dui_yellow);
        iv_yellow.setVisibility(View.GONE);
        iv_blue = view.findViewById(R.id.iv_hud_dui_blue);
        iv_blue.setVisibility(View.GONE);
        color_value = FileLTool.getOutInstance().getQueryKey("obdHudcolor").getValue();
        if (color_value == 1) {
            iv_green.setVisibility(View.VISIBLE);
        } else if (color_value == 2) {
            iv_lightblue.setVisibility(View.VISIBLE);
        } else if (color_value == 3) {
            iv_white.setVisibility(View.VISIBLE);
        } else if (color_value == 4) {
            iv_yellow.setVisibility(View.VISIBLE);
        } else if (color_value == 5) {
            iv_blue.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return_hud_setting:
                FileLTool.getOutInstance().upDateValueByKey("obdHudcolor", color_value);
                Intent intent = new Intent("changedHudColor");
                getActivity().sendBroadcast(intent);
                getActivity().finish();
                break;
            case R.id.re_hud_setting_green:
                iv_green.setVisibility(View.VISIBLE);
                iv_lightblue.setVisibility(View.GONE);
                iv_white.setVisibility(View.GONE);
                iv_yellow.setVisibility(View.GONE);
                iv_blue.setVisibility(View.GONE);
                color_value = 1;
                break;
            case R.id.re_hud_setting_lightblue:
                iv_green.setVisibility(View.GONE);
                iv_lightblue.setVisibility(View.VISIBLE);
                iv_white.setVisibility(View.GONE);
                iv_yellow.setVisibility(View.GONE);
                iv_blue.setVisibility(View.GONE);
                color_value = 2;
                break;
            case R.id.re_hud_setting_white:
                iv_green.setVisibility(View.GONE);
                iv_lightblue.setVisibility(View.GONE);
                iv_white.setVisibility(View.VISIBLE);
                iv_yellow.setVisibility(View.GONE);
                iv_blue.setVisibility(View.GONE);
                color_value = 3;
                break;
            case R.id.re_hud_setting_yellow:
                iv_green.setVisibility(View.GONE);
                iv_lightblue.setVisibility(View.GONE);
                iv_white.setVisibility(View.GONE);
                iv_yellow.setVisibility(View.VISIBLE);
                iv_blue.setVisibility(View.GONE);
                color_value = 4;
                break;
            case R.id.re_hud_setting_blue:
                iv_green.setVisibility(View.GONE);
                iv_lightblue.setVisibility(View.GONE);
                iv_white.setVisibility(View.GONE);
                iv_yellow.setVisibility(View.GONE);
                iv_blue.setVisibility(View.VISIBLE);
                color_value = 5;
                break;
        }
    }


    @Override
    public void onbackForward() {
        FileLTool.getOutInstance().upDateValueByKey("obdHudcolor", color_value);
        Intent intent = new Intent("changedHudColor");
        getActivity().sendBroadcast(intent);
        getActivity().finish();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() instanceof MainFregmentReplaceActivity){
            ((MainFregmentReplaceActivity)getActivity()).setBackListener(this);
            ((MainFregmentReplaceActivity)getActivity()).setInterception(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(getActivity() instanceof MainFregmentReplaceActivity){
            ((MainFregmentReplaceActivity)getActivity()).setBackListener(null);
            ((MainFregmentReplaceActivity)getActivity()).setInterception(false);
        }
    }
}

