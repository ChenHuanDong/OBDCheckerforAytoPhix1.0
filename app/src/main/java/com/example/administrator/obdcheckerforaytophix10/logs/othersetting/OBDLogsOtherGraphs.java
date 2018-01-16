package com.example.administrator.obdcheckerforaytophix10.logs.othersetting;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.administrator.obdcheckerforaytophix10.MainActivity;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

import ch.ielse.view.SwitchView;

public class OBDLogsOtherGraphs extends AppCompatActivity implements View.OnClickListener {

    private SwitchView btn_enabled_one, btn_enabled_two, btn_enabled_three, btn_enabled_four,
            btn_smoothing_one, btn_smoothing_two, btn_smoothing_three, btn_smoothing_four;

    //返回按钮
    private ImageView iv_return;

    //横屏相关
    private MyOrientationEventListener mListener;
    private int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏0
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_obdlogs_other_graphs);

        initView();


    }

    private void initView() {
        orientation = getResources().getConfiguration().orientation;
        mListener = new MyOrientationEventListener(this);
        boolean autoRotateOn = (Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
        if (autoRotateOn) {
            mListener.enable();
        }
        btn_enabled_one = (SwitchView) findViewById(R.id.iosbtn_logs_other_graph_enabled_one);
        btn_enabled_two = (SwitchView) findViewById(R.id.iosbtn_logs_other_graph_enabled_two);
        btn_enabled_three = (SwitchView) findViewById(R.id.iosbtn_logs_other_graph_enabled_three);
        btn_enabled_four = (SwitchView) findViewById(R.id.iosbtn_logs_other_graph_enabled_four);
        btn_smoothing_one = (SwitchView) findViewById(R.id.iosbtn_logs_other_graph_smoothing_one);
        btn_smoothing_two = (SwitchView) findViewById(R.id.iosbtn_logs_other_graph_smoothing_two);
        btn_smoothing_three = (SwitchView) findViewById(R.id.iosbtn_logs_other_graph_smoothing_three);
        btn_smoothing_four = (SwitchView) findViewById(R.id.iosbtn_logs_other_graph_smoothing_four);
        btn_enabled_one.setOnClickListener(this);
        btn_enabled_two.setOnClickListener(this);
        btn_enabled_three.setOnClickListener(this);
        btn_enabled_four.setOnClickListener(this);
        btn_smoothing_one.setOnClickListener(this);
        btn_smoothing_two.setOnClickListener(this);
        btn_smoothing_three.setOnClickListener(this);
        btn_smoothing_four.setOnClickListener(this);
        iv_return = (ImageView) findViewById(R.id.iv_logs_other_graphs_return);
        iv_return.setOnClickListener(this);
        //初始化所有iosButton的状态
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_1").getIsTure()) {
            btn_enabled_one.setOpened(true);
        }
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_2").getIsTure()) {
            btn_enabled_two.setOpened(true);
        }
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_3").getIsTure()) {
            btn_enabled_three.setOpened(true);
        }
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_4").getIsTure()) {
            btn_enabled_four.setOpened(true);
        }
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_smoothing_1").getIsTure()) {
            btn_smoothing_one.setOpened(true);
        }
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_smoothing_2").getIsTure()) {
            btn_smoothing_two.setOpened(true);
        }
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_smoothing_3").getIsTure()) {
            btn_smoothing_three.setOpened(true);
        }
        if (DBTool.getOutInstance().getQueryKey("logs_graphs_smoothing_4").getIsTure()) {
            btn_smoothing_four.setOpened(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iosbtn_logs_other_graph_enabled_one:
                if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_1").getIsTure()){
                    btn_enabled_one.setOpened(false);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_enabled_1" , false);
                }else {
                    btn_enabled_one.setOpened(true);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_enabled_1" , true);
                }
                break;
            case R.id.iosbtn_logs_other_graph_enabled_two:
                if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_2").getIsTure()){
                    btn_enabled_two.setOpened(false);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_enabled_2" , false);
                }else {
                    btn_enabled_two.setOpened(true);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_enabled_2" , true);
                }
                break;
            case R.id.iosbtn_logs_other_graph_enabled_three:
                if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_3").getIsTure()){
                    btn_enabled_three.setOpened(false);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_enabled_3" , false);
                }else {
                    btn_enabled_three.setOpened(true);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_enabled_3" , true);
                }
                break;
            case R.id.iosbtn_logs_other_graph_enabled_four:
                if (DBTool.getOutInstance().getQueryKey("logs_graphs_enabled_4").getIsTure()){
                    btn_enabled_four.setOpened(false);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_enabled_4" , false);
                }else {
                    btn_enabled_four.setOpened(true);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_enabled_4" , true);
                }
                break;
            case R.id.iosbtn_logs_other_graph_smoothing_one:
                if (DBTool.getOutInstance().getQueryKey("logs_graphs_smoothing_1").getIsTure()){
                    btn_smoothing_one.setOpened(false);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_smoothing_1" , false);
                }else {
                    btn_smoothing_one.setOpened(true);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_smoothing_1" , true);
                }
                break;
            case R.id.iosbtn_logs_other_graph_smoothing_two:
                if (DBTool.getOutInstance().getQueryKey("logs_graphs_smoothing_2").getIsTure()){
                    btn_smoothing_two.setOpened(false);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_smoothing_2" , false);
                }else {
                    btn_smoothing_two.setOpened(true);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_smoothing_2" , true);
                }
                break;
            case R.id.iosbtn_logs_other_graph_smoothing_three:
                if (DBTool.getOutInstance().getQueryKey("logs_graphs_smoothing_3").getIsTure()){
                    btn_smoothing_three.setOpened(false);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_smoothing_3" , false);
                }else {
                    btn_smoothing_three.setOpened(true);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_smoothing_3" , true);
                }
                break;
            case R.id.iosbtn_logs_other_graph_smoothing_four:
                if (DBTool.getOutInstance().getQueryKey("logs_graphs_smoothing_4").getIsTure()){
                    btn_smoothing_four.setOpened(false);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_smoothing_4" , false);
                }else {
                    btn_smoothing_four.setOpened(true);
                    DBTool.getOutInstance().upDateIsTrueByKey("logs_graphs_smoothing_4" , true);
                }
                break;
            case R.id.iv_logs_other_graphs_return:
                //点击返回发送广播
                Intent intent = new Intent("changeLogsChart");
                sendBroadcast(intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //点击返回发送广播
        Intent intent = new Intent("changeLogsChart");
        sendBroadcast(intent);
        finish();
    }

    //继承OrientationEventListener类监听手机的旋转
    public class MyOrientationEventListener extends OrientationEventListener {

        public MyOrientationEventListener(Context context) {
            super(context);
        }

        public MyOrientationEventListener(Context context, int rate) {
            super(context, rate);
        }

        @Override
        public void onOrientationChanged(int i) {
            //i  表示偏移角度  -1的话是水平放置  0~359  手机逆时针旋转的话是 慢慢增加的
            int screenOrientation = getResources().getConfiguration().orientation;
            if (((i >= 0) && (i < 45)) || (i > 315)) {
                //设置竖屏
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        && i != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            } else if (i > 255 && i < 315) {
                //设置横屏
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            } else if (i > 45 && i < 135) {
                //设置反向横屏
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                }
            } else if (i > 135 && i < 225) {
                //反向竖屏
                if (screenOrientation != ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                }
            }


        }
    }


}
