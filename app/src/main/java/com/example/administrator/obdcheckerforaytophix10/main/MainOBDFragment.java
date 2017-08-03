package com.example.administrator.obdcheckerforaytophix10.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.OBDDashboardsActivity;
import com.example.administrator.obdcheckerforaytophix10.diagnostics.OBDDiagnosticsActivity;
import com.example.administrator.obdcheckerforaytophix10.logs.OBDLogsActivity;
import com.example.administrator.obdcheckerforaytophix10.main.obd.MainOBDPopLsAdapter;
import com.example.administrator.obdcheckerforaytophix10.main.obd.OBDPopDialog;
import com.example.administrator.obdcheckerforaytophix10.montiors.OBDMontiorsActivity;
import com.example.administrator.obdcheckerforaytophix10.performance.OBDPerformanceActivity;
import com.example.administrator.obdcheckerforaytophix10.settings.OBDSettingsActivity;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
//
public class MainOBDFragment extends Fragment implements View.OnClickListener {

    //上方显示连接状态栏          中间偏上详细信息
    private TextView tv_title, tv_detail;
    private Button btn_title;
    //显示是否连接
    private boolean isConnect = false;
    //MainActivity 的下面三个RadioButton
    private RadioButton btn_obd, btn_special, btn_persional;
    //6个ImageView 点击跳转到对应的6个模块
    private ImageView iv_dashboards, iv_diagnostics, iv_montiors, iv_logs, iv_performance, iv_settings;

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
        iv_dashboards = view.findViewById(R.id.obd_iv_main_dashboards);
        iv_diagnostics = view.findViewById(R.id.obd_iv_main_diagnostics);
        iv_montiors = view.findViewById(R.id.obd_iv_main_montiors);
        iv_logs = view.findViewById(R.id.obd_iv_main_logs);
        iv_performance = view.findViewById(R.id.obd_iv_main_performance);
        iv_settings = view.findViewById(R.id.obd_iv_main_settings);
        iv_dashboards.setOnClickListener(this);
        iv_diagnostics.setOnClickListener(this);
        iv_montiors.setOnClickListener(this);
        iv_logs.setOnClickListener(this);
        iv_performance.setOnClickListener(this);
        iv_settings.setOnClickListener(this);
        //设置下面三个按钮不可点击   Fragment中  管理 Activity 控件
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        btn_obd = (RadioButton) activity.findViewById(R.id.btn_main_obd);
        btn_special = (RadioButton) activity.findViewById(R.id.btn_main_special);
        btn_persional = (RadioButton) activity.findViewById(R.id.btn_main_persional);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        //如果未连接则title显示红色字体
        if (!isConnect) {
            tv_title.setTextColor(this.getResources().getColor(R.color.colorTextColorDemo));
            tv_detail.setTextColor(this.getResources().getColor(R.color.colorConnectDetail));
            tv_detail.setText(this.getResources().getText(R.string.main_tv_obd_connectdetail_not));
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.obd_tv_title_connect:
                if (!isConnect) {
                    //下面三个   上面两个  中间六个  全部不能点
                    setMainBtnCli(false);

                    final OBDPopDialog dialog = new OBDPopDialog(getActivity());

                    View search_device = LayoutInflater.from(getActivity()).inflate(R.layout.pop_obd_search_device, null);
                    //LS
                    ListView lv = search_device.findViewById(R.id.obd_pop_ls_device);
                    MainOBDPopLsAdapter mAdapter = new MainOBDPopLsAdapter(getActivity());
                    final ArrayList<String> data = new ArrayList<>();
                    //假数据
                    data.add("OBDCHECK");
                    data.add("OBDLINK");
                    data.add("V100");
                    mAdapter.setData(data);
                    lv.setAdapter(mAdapter);

                    //lv点击item连接对应蓝牙
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //Popup  消失
                            dialog.dismiss();
                            //弹出Dialog
                            final PopupWindow popLoading = new PopupWindow(getActivity());
                            popLoading.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                            popLoading.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                            View view_load = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_after_obd_pop, null);
                            ImageView anim_iv = view_load.findViewById(R.id.dia_pop_anim_iv);
                            anim_iv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //正常应该是加动画  现在先设置一个点击事件
                                    popLoading.dismiss();
                                    //3 + 2 + 6 个按钮全部可以点击
                                    setMainBtnCli(true);


                                    // 这里还应该有一个把  动画变成信号的
                                    tv_title.setTextColor(getActivity().getResources().getColor(R.color.colorConnect));
                                    tv_detail.setTextColor(getActivity().getResources().getColor(R.color.colorConnect));
                                    tv_detail.setText(getActivity().getResources().getString(R.string.main_tv_obd_connectdetail_do));
                                    //连接状态变成已连接
                                    isConnect = true;
                                }
                            });
                            popLoading.setContentView(view_load);
                            popLoading.setOutsideTouchable(false);
                            popLoading.showAsDropDown(tv_title);


                        }
                    });

                    //Pop不能把边框去掉  Dia可以
                    Window win = dialog.getWindow();
                    WindowManager.LayoutParams lp = win.getAttributes();
                    win.setGravity(Gravity.LEFT | Gravity.TOP);
                    lp.x = 0;
                    lp.y = (int) (ScreenUtils.getScreenHeight(getActivity()) * 0.068);

                    win.setAttributes(lp);

                    dialog.setContentView(search_device);
                    dialog.setCanceledOnTouchOutside(false);

                    dialog.show();


                } else {
                    //已连接时点击
                    tv_title.setTextColor(getActivity().getResources().getColor(R.color.colorConnectDetail));
                    tv_detail.setTextColor(getActivity().getResources().getColor(R.color.colorConnectDetail));
                    tv_detail.setText(getActivity().getResources().getString(R.string.main_tv_obd_connectdetail_not));
                    isConnect = false;
                }


                break;
            case R.id.obd_btn_title_connect:
                if (!isConnect) {
                    //下面三个   上面两个  中间六个  全部不能点
                    setMainBtnCli(false);

                    final OBDPopDialog dialog = new OBDPopDialog(getActivity());

                    View search_device = LayoutInflater.from(getActivity()).inflate(R.layout.pop_obd_search_device, null);
                    //LS
                    ListView lv = search_device.findViewById(R.id.obd_pop_ls_device);
                    MainOBDPopLsAdapter mAdapter = new MainOBDPopLsAdapter(getActivity());
                    final ArrayList<String> data = new ArrayList<>();
                    //假数据
                    data.add("OBDCHECK");
                    data.add("OBDLINK");
                    data.add("V100");
                    mAdapter.setData(data);
                    lv.setAdapter(mAdapter);

                    //lv点击item连接对应蓝牙
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //Popup  消失
                            dialog.dismiss();
                            //弹出Dialog
                            final PopupWindow popLoading = new PopupWindow(getActivity());
                            popLoading.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                            popLoading.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                            View view_load = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_after_obd_pop, null);
                            ImageView anim_iv = view_load.findViewById(R.id.dia_pop_anim_iv);
                            anim_iv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //正常应该是加动画  现在先设置一个点击事件
                                    popLoading.dismiss();
                                    //3 + 2 + 6 个按钮全部可以点击
                                    setMainBtnCli(true);


                                    // 这里还应该有一个把  动画变成信号的
                                    tv_title.setTextColor(getActivity().getResources().getColor(R.color.colorConnect));
                                    tv_detail.setTextColor(getActivity().getResources().getColor(R.color.colorConnect));
                                    tv_detail.setText(getActivity().getResources().getString(R.string.main_tv_obd_connectdetail_do));
                                    //连接状态变成已连接
                                    isConnect = true;
                                }
                            });
                            popLoading.setContentView(view_load);
                            popLoading.setOutsideTouchable(false);
                            popLoading.showAsDropDown(tv_title);


                        }
                    });

                    //Pop不能把边框去掉  Dia可以
                    Window win = dialog.getWindow();
                    WindowManager.LayoutParams lp = win.getAttributes();
                    win.setGravity(Gravity.LEFT | Gravity.TOP);
                    lp.x = 0;
                    lp.y = (int) (ScreenUtils.getScreenHeight(getActivity()) * 0.068);

                    win.setAttributes(lp);

                    dialog.setContentView(search_device);
                    dialog.setCanceledOnTouchOutside(false);

                    dialog.show();


                } else {
                    //已连接时点击
                    tv_title.setTextColor(getActivity().getResources().getColor(R.color.colorConnectDetail));
                    tv_detail.setTextColor(getActivity().getResources().getColor(R.color.colorConnectDetail));
                    tv_detail.setText(getActivity().getResources().getString(R.string.main_tv_obd_connectdetail_not));
                    isConnect = false;
                }


                break;
            //下面六个跳转到对应的Aty里面
            case R.id.obd_iv_main_dashboards:
                moveToAty(OBDDashboardsActivity.class);
                break;
            case R.id.obd_iv_main_diagnostics:
                moveToAty(OBDDiagnosticsActivity.class);
                break;
            case R.id.obd_iv_main_montiors:
                moveToAty(OBDMontiorsActivity.class);
                break;
            case R.id.obd_iv_main_logs:
                moveToAty(OBDLogsActivity.class);
                break;
            case R.id.obd_iv_main_performance:
                moveToAty(OBDPerformanceActivity.class);
                break;
            case R.id.obd_iv_main_settings:
                moveToAty(OBDSettingsActivity.class);
                break;
        }
    }

    //跳转到Aty的方法  带动画
    private void moveToAty(Class<?> cls) {
        Intent intent_dashboards = new Intent(getActivity(), cls);
        startActivity(intent_dashboards);
        getActivity().overridePendingTransition(R.anim.slide_right_mid, R.anim.slide_mid_left);
    }

    //发送广播
    private void sendBR(String s, String msg, boolean b) {
        Intent intent = new Intent(s);
        intent.putExtra(msg, b);
        getActivity().sendBroadcast(intent);
        LogUtil.fussenLog().d("已发送广播");
    }

    private void setMainBtnCli (boolean b){
        //
        btn_obd.setClickable(b);
        btn_special.setClickable(b);
        btn_persional.setClickable(b);
        //只能弹出一个Pop
        btn_title.setClickable(b);
        tv_title.setClickable(b);
        //6
        iv_dashboards.setClickable(b);
        iv_diagnostics.setClickable(b);
        iv_montiors.setClickable(b);
        iv_logs.setClickable(b);
        iv_performance.setClickable(b);
        iv_settings.setClickable(b);
    }

}
