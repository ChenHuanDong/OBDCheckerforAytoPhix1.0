package com.example.administrator.obdcheckerforaytophix10.main;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.MainActivity;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainOBDFragment extends Fragment implements View.OnClickListener {

    //上方显示连接状态栏          中间偏上详细信息
    private TextView tv_title, tv_detail;
    private Button btn_title;
    //显示是否连接
    private boolean isConnect = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_obd, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }


    private void initView(View view) {
        tv_title = view.findViewById(R.id.obd_tv_title_connect);
        btn_title = view.findViewById(R.id.obd_btn_title_connect);
        tv_title.setOnClickListener(this);
        btn_title.setOnClickListener(this);
        tv_detail = view.findViewById(R.id.obd_tv_detail_connect);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        //如果未连接则title显示红色字体
        if (!isConnect) {
            tv_title.setTextColor(this.getResources().getColor(R.color.colorDisConnect));
            tv_detail.setTextColor(this.getResources().getColor(R.color.colorDisConnect));
            tv_detail.setText(this.getResources().getText(R.string.main_tv_obd_connectdetail_not));
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.obd_tv_title_connect:
                if (!isConnect) {
                    //设置下面三个按钮不可点击   Fragment中  管理 Activity 控件
                    AppCompatActivity activity = (AppCompatActivity) getActivity();
                    RadioButton btn_obd = (RadioButton) activity.findViewById(R.id.btn_main_obd);
                    RadioButton btn_special = (RadioButton) activity.findViewById(R.id.btn_main_special);
                    RadioButton btn_persional = (RadioButton) activity.findViewById(R.id.btn_main_persional);
                    btn_obd.setClickable(false);
                    btn_special.setClickable(false);
                    btn_persional.setClickable(false);
                    //只能弹出一个Pop
                    btn_title.setClickable(false);
                    tv_title.setClickable(false);

                    PopupWindow mPop = new PopupWindow(getActivity());
                    mPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    mPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    View search_device = LayoutInflater.from(getActivity()).inflate(R.layout.pop_obd_search_device, null);
                    mPop.setContentView(search_device);
                    mPop.setOutsideTouchable(false);
                    mPop.showAsDropDown(tv_title);

                }
                break;
            case R.id.obd_btn_title_connect:

                break;
        }
    }
    //发送广播
    private void sendBR(String s, String msg, boolean b) {
        Intent intent = new Intent(s);
        intent.putExtra(msg, b);
        getActivity().sendBroadcast(intent);
        LogUtil.fussenLog().d("已发送广播");
    }
}
