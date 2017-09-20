package com.example.administrator.obdcheckerforaytophix10.logs;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.OBDL;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.logs.fragment.OBDLogsFilesFragment;
import com.example.administrator.obdcheckerforaytophix10.logs.fragment.OBDLogsGraphsFragment;
import com.example.administrator.obdcheckerforaytophix10.logs.fragment.OBDLogsTripsFragment;
import com.example.administrator.obdcheckerforaytophix10.logs.othersetting.OBDLogsOtherGraphs;
import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;

public class OBDLogsActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton radbtn_graphs, radbtn_trips, radbtn_file;
    //被替代的Fragment    别初始化
    private Fragment current_fragment;
    //下面是三页的Fragment  需要初始化   布局底部要设置可点击  防止点击穿透
    private OBDLogsGraphsFragment graphs_fragment;
    private OBDLogsTripsFragment trips_fragment;
    private OBDLogsFilesFragment file_fragment;
    //底部可变的Tv 颜色  Iv src
    private TextView tv_grahps, tv_trips, tv_files;
    private ImageView iv_graphs, iv_trips, iv_files;
    //右上角的按钮点击跳转   根据当前在哪个Fragment 右上角跳转的页面不同
    private ImageView iv_other;
    private int position = 1;
    //返回按钮
    private ImageView iv_return;

    //Graphs  右上角才会出现的Iv
    private ImageView iv_show_graphs;
    //File   右上角才会出现的Tv
    private TextView tv_show_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏0
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_obdlogs);

        //第一次进入把Logs能存的  都存了
        initGrenDaoData();

        initView();
        radbtn_graphs.performClick();


    }

    private void initGrenDaoData() {
        if (DBTool.getOutInstance().isSave("isLogFirst")) {
        } else {
            OBDL obdl = new OBDL(null, "isLogFirst", true);
            DBTool.getOutInstance().insertBean(obdl);
            //四个Enabled   四个Smoothing  四个PID
            obdl.setId(null).setKey("logs_graphs_enabled_1").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("logs_graphs_enabled_2").setIsTure(true);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("logs_graphs_enabled_3").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("logs_graphs_enabled_4").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("logs_graphs_smoothing_1").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("logs_graphs_smoothing_2").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("logs_graphs_smoothing_3").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
            obdl.setId(null).setKey("logs_graphs_smoothing_4").setIsTure(false);
            DBTool.getOutInstance().insertBean(obdl);
        }
    }

    private void initView() {
        graphs_fragment = new OBDLogsGraphsFragment();
        trips_fragment = new OBDLogsTripsFragment();
        file_fragment = new OBDLogsFilesFragment();
        radbtn_graphs = (RadioButton) findViewById(R.id.radbtn_logs_graphs);
        radbtn_trips = (RadioButton) findViewById(R.id.radbtn_logs_trips);
        radbtn_file = (RadioButton) findViewById(R.id.radbtn_logs_files);
        radbtn_graphs.setOnClickListener(this);
        radbtn_trips.setOnClickListener(this);
        radbtn_file.setOnClickListener(this);
        tv_grahps = (TextView) findViewById(R.id.tv_logs_graphs_bottom);
        tv_trips = (TextView) findViewById(R.id.tv_logs_trip_bottom);
        tv_files = (TextView) findViewById(R.id.tv_logs_files_bottom);
        iv_graphs = (ImageView) findViewById(R.id.iv_logs_graphs_bottom);
        iv_trips = (ImageView) findViewById(R.id.iv_logs_trip_bottom);
        iv_files = (ImageView) findViewById(R.id.iv_logs_files_bottom);
        iv_other = (ImageView) findViewById(R.id.iv_logs_main_other);
        iv_other.setOnClickListener(this);
        iv_return = (ImageView) findViewById(R.id.iv_logs_main_return);
        iv_return.setOnClickListener(this);
        iv_show_graphs = (ImageView) findViewById(R.id.iv_logs_show_graphs);
        tv_show_file = (TextView) findViewById(R.id.tv_logs_show_file);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radbtn_logs_graphs:
                startFragmentAdd(graphs_fragment);
                iv_graphs.setImageResource(R.drawable.graphs_g);
                tv_grahps.setTextColor(Color.parseColor("#fe9002"));
                iv_trips.setImageResource(R.drawable.trips_b);
                tv_trips.setTextColor(Color.parseColor("#c8c6c6"));
                iv_files.setImageResource(R.drawable.file_b);
                tv_files.setTextColor(Color.parseColor("#c8c6c6"));
                position = 1;
                iv_show_graphs.setVisibility(View.VISIBLE);
                tv_show_file.setVisibility(View.GONE);
                break;
            case R.id.radbtn_logs_trips:
                startFragmentAdd(trips_fragment);
                iv_graphs.setImageResource(R.drawable.graphs_b);
                tv_grahps.setTextColor(Color.parseColor("#C8C6C6"));
                iv_trips.setImageResource(R.drawable.trips_g);
                tv_trips.setTextColor(Color.parseColor("#fe9002"));
                iv_files.setImageResource(R.drawable.file_b);
                tv_files.setTextColor(Color.parseColor("#C8C6C6"));
                position = 2;
                iv_show_graphs.setVisibility(View.GONE);
                tv_show_file.setVisibility(View.GONE);
                break;
            case R.id.radbtn_logs_files:
                startFragmentAdd(file_fragment);
                iv_graphs.setImageResource(R.drawable.graphs_b);
                tv_grahps.setTextColor(Color.parseColor("#C8C6C6"));
                iv_trips.setImageResource(R.drawable.trips_b);
                tv_trips.setTextColor(Color.parseColor("#C8C6C6"));
                iv_files.setImageResource(R.drawable.file_g);
                tv_files.setTextColor(Color.parseColor("#fe9002"));
                position = 3;
                iv_show_graphs.setVisibility(View.GONE);
                tv_show_file.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_logs_main_other:
                //根据不同位置右上角进入的页面不同
                if (position == 1) {
                    Intent intent = new Intent(OBDLogsActivity.this, OBDLogsOtherGraphs.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent = new Intent("logsFilesEdit");
                    sendBroadcast(intent);
                    //把存的状态取反  存起来
                    SPUtil.put(OBDLogsActivity.this , "OBDLogsEditStatus" , !(boolean)SPUtil.get(OBDLogsActivity.this , "OBDLogsEditStatus" , false));
                }
                break;
            case R.id.iv_logs_main_return:
                finish();
                break;
        }
    }

    private void startFragmentAdd(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (current_fragment == null) {
            fragmentTransaction.add(R.id.replace_logs_main, fragment).commit();
            current_fragment = fragment;
        }
        if (current_fragment != fragment) {
            // 先判断是否被add过
            if (!fragment.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                fragmentTransaction.hide(current_fragment).add(R.id.replace_logs_main, fragment).commit();
            } else {
                // 隐藏当前的fragment，显示下一个
                fragmentTransaction.hide(current_fragment).show(fragment).commit();
            }
            current_fragment = fragment;

        }
    }


}
