package com.example.administrator.obdcheckerforaytophix10.logs;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.MainActivity;
import com.example.administrator.obdcheckerforaytophix10.OBDL;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.logs.fragment.OBDLogsFilesFragment;
import com.example.administrator.obdcheckerforaytophix10.logs.fragment.OBDLogsGraphsFragment;
import com.example.administrator.obdcheckerforaytophix10.logs.fragment.OBDLogsTripsFragment;
import com.example.administrator.obdcheckerforaytophix10.logs.othersetting.OBDLogsOtherGraphs;
import com.example.administrator.obdcheckerforaytophix10.main.servierbt.BluetoothService;
import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.FileLTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;

import java.util.ArrayList;

//回放的Fragment   LogsDetailChartFragment


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

    //标题栏底部是文字还是开始暂停按钮  和上面覆盖的真实按钮
    private TextView tv_title;
    private ImageView iv_start, iv_stop, iv_real_start, iv_real_stop;

    //测试添加假数据的线程
    private ThreadSafe adddataThread;

    //接收图表的数据
    private BroadcastReceiver br;
    private int yValue = 0;
    private ArrayList<Integer> data;

    //绑定服务
    private ServiceConnection mConnection;
    private BluetoothService.MyBinder mBinder;

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
        setContentView(R.layout.activity_obdlogs);

        //第一次进入把Logs能存的  都存了
        initGrenDaoData();

        initView();
        radbtn_graphs.performClick();

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mBinder = (BluetoothService.MyBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };


        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                yValue = intent.getIntExtra("yValue", 0);
                data.add(yValue);

            }
        };


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("changeChartData");
        registerReceiver(br, intentFilter);

        //绑定服务
        Intent intent1 = new Intent(OBDLogsActivity.this, BluetoothService.class);
        bindService(intent1, mConnection, BIND_AUTO_CREATE);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinder.setIsFinishLog(true);
        unregisterReceiver(br);
        unbindService(mConnection);
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
        orientation = getResources().getConfiguration().orientation;
        mListener = new MyOrientationEventListener(this);
        boolean autoRotateOn = (Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
        if (autoRotateOn) {
            mListener.enable();
        }
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
        tv_title = (TextView) findViewById(R.id.tv_logs_main_title);
        iv_start = (ImageView) findViewById(R.id.iv_logs_mian_start);
        iv_stop = (ImageView) findViewById(R.id.iv_logs_mian_stop);
        iv_real_start = (ImageView) findViewById(R.id.iv_logs_start_real);
        iv_real_stop = (ImageView) findViewById(R.id.iv_logs_stop_real);
        iv_real_start.setClickable(false);
        iv_real_stop.setClickable(false);
        iv_real_start.setOnClickListener(this);
        iv_real_stop.setOnClickListener(this);
        //线程初始化在这里
//        adddataThread = new ThreadSafe();
//        adddataThread.setContext(OBDLogsActivity.this);
        //
        data = new ArrayList<>();
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
                tv_title.setVisibility(View.GONE);
                iv_start.setVisibility(View.VISIBLE);
                iv_stop.setVisibility(View.VISIBLE);
                iv_real_start.setClickable(true);
                iv_real_stop.setClickable(true);
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
                tv_title.setVisibility(View.VISIBLE);
                iv_start.setVisibility(View.GONE);
                iv_stop.setVisibility(View.GONE);
                iv_real_start.setClickable(false);
                iv_real_stop.setClickable(false);
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
                tv_title.setVisibility(View.VISIBLE);
                iv_start.setVisibility(View.GONE);
                iv_stop.setVisibility(View.GONE);
                iv_real_start.setClickable(false);
                iv_real_stop.setClickable(false);
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
                    SPUtil.put(OBDLogsActivity.this, "OBDLogsEditStatus", !(boolean) SPUtil.get(OBDLogsActivity.this, "OBDLogsEditStatus", false));
                }
                break;
            case R.id.iv_logs_main_return:
                //在这里把那个保存的存一遍
                mBinder.setIsFinishLog(true);
                finish();
                break;
            //点击开始获取数据并且发送广播
            case R.id.iv_logs_start_real:
                //在这里添加假数据
                //初始化在initView里面
//                adddataThread.start();
                iv_real_start.setClickable(false);
                SPUtil.put(OBDLogsActivity.this, "test", 13);
                mBinder.setIsFinishLog(false);
                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                break;
            //点击结束本次动态数据
            case R.id.iv_logs_stop_real:
                iv_real_start.setClickable(true);
//                adddataThread.setExit(true);
                //这个是存储  不过现在真是数据的话不在这里了   那存在哪里了？？？？
//                FileLTool.getOutInstance().upDateColorByKey("testList", data);;
                mBinder.setIsFinishLog(true);
                break;
        }
    }

    private void startFragmentAdd(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (current_fragment == null) {
            fragmentTransaction.add(R.id.replace_logs_main, fragment).commitAllowingStateLoss();
            current_fragment = fragment;
        }
        if (current_fragment != fragment) {
            // 先判断是否被add过
            if (!fragment.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                fragmentTransaction.hide(current_fragment).add(R.id.replace_logs_main, fragment).commitAllowingStateLoss();
            } else {
                // 隐藏当前的fragment，显示下一个
                fragmentTransaction.hide(current_fragment).show(fragment).commitAllowingStateLoss();
            }
            current_fragment = fragment;

        }
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
