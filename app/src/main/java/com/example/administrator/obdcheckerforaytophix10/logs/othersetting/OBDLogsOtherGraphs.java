package com.example.administrator.obdcheckerforaytophix10.logs.othersetting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

import ch.ielse.view.SwitchView;

public class OBDLogsOtherGraphs extends AppCompatActivity implements View.OnClickListener {

    private SwitchView btn_enabled_one, btn_enabled_two, btn_enabled_three, btn_enabled_four,
            btn_smoothing_one, btn_smoothing_two, btn_smoothing_three, btn_smoothing_four;

    //返回按钮
    private ImageView iv_return;

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
}
