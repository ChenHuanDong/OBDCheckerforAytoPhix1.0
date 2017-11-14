package com.example.administrator.obdcheckerforaytophix10.performance;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.main.obd.OBDPopDialog;
import com.example.administrator.obdcheckerforaytophix10.main.servierbt.BluetoothService;
import com.example.administrator.obdcheckerforaytophix10.performance.view.PerformanceView;
import com.example.administrator.obdcheckerforaytophix10.tool.ConversionUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;

//
public class OBDPerformanceActivity extends AppCompatActivity implements View.OnClickListener {

//    private TextView tvTitle, tvTwo, tvThree, tvFour, tvFive, tvSix;
//
//    private Button btnStart, btnStop, btnStartQ, btnStopQ;
//
//    //这个是百公里加速时间的判断
//    private boolean isyibai = false;
//    //到100m的距离
//    private boolean isyibaidis = false;
//    //刹车的距离
//    private boolean isshache = false;
//    //刹车距离用的  判断是不一样速度超过一百
//    private boolean isStartyibai = false;
//    //这个是新的用于计算刹车距离的
//    private float disShaChe;
//
//    private long startTime, midTime, endTime;
//    private float distance;
//
//    private ServiceConnection mConnection;
//    private BluetoothService.MyBinder mBinder;
//    //接收测试的广播
//    private BroadcastReceiver mBR;
//
//    private int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏0
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_obdperformance);

        initView();

//        mConnection = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                mBinder = (BluetoothService.MyBinder) service;
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//
//            }
//        };
//
//        mBR = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                tvTitle.setText(intent.getIntExtra("车速", 0) + "");
//                tvTwo.setText("现在的时间:" + ((System.currentTimeMillis() - startTime) / 1000f));
//                // i 是次数
//                i++;
//                // midTime  是每次最后一段的时间
//                if (i == 1) {
//                    endTime = System.currentTimeMillis() - startTime;
//                    distance = (float) ((endTime / 1000.f) * intent.getIntExtra("车速", 0) * 0.2777f);
//                } else if (i == 2) {
//                    midTime = System.currentTimeMillis() - endTime - startTime;
//                    //记录这次时间下次用
//                    endTime = System.currentTimeMillis();
//                    distance = (float) ((midTime / 1000.f) * intent.getIntExtra("车速", 0) * 0.2777f) + distance;
//                } else {
//                    midTime = System.currentTimeMillis() - endTime;
//                    //记录这次时间下次用
//                    endTime = System.currentTimeMillis();
//                    distance = (float) ((midTime / 1000.f) * intent.getIntExtra("车速", 0) * 0.2777f) + distance;
//                }
//
//                tvSix.setText("现在的距离:" + distance);
//
//                if (!isyibai) {
//                    if (intent.getIntExtra("车速", 0) > 100) {
//                        tvThree.setText("百公里加速的时间:" + ((System.currentTimeMillis() - startTime) / 1000f));
//                        isyibai = true;
//                    }
//                }
//
//                if (!isyibaidis) {
//                    if (distance >= 100) {
//                        tvFive.setText("到100m的时间:" + ((System.currentTimeMillis() - startTime) / 1000f));
//                        isyibaidis = true;
//                    }
//                }
//
//                //这个是刹车距离
//                if (!isshache) {
//                    if (isStartyibai) {
//                        if (intent.getIntExtra("车速", 0) < 100) {
//                            //不用想第一次进入这里肯定是  i > 2 的情况
//                            disShaChe = (float) ((midTime / 1000.f) * intent.getIntExtra("车速", 0) * 0.2777f) + disShaChe;
//                            tvFour.setText("刹车距离(100km/h-0km/h):" + disShaChe);
//                            if (intent.getIntExtra("车速", 0) == 0){
//                                disShaChe = (float) ((midTime / 1000.f) * intent.getIntExtra("车速", 0) * 0.2777f) + disShaChe;
//                                tvFour.setText("刹车距离(100km/h-0km/h):" + disShaChe);
//                                isshache = true;
//                            }
//                        }
//                    }
//                    if (intent.getIntExtra("车速", 0) > 100) {
//                        //开始测试
//                        isStartyibai = true;
//                    }
//                }
//
//
//
//
//
//
//            }
//
//
//        };


//        //绑定服务
//        Intent intent = new Intent(OBDPerformanceActivity.this, BluetoothService.class);
//        bindService(intent, mConnection, BIND_AUTO_CREATE);
//        //注册广播
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("bluetoothBT---performance");
//        registerReceiver(mBR, intentFilter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(mConnection);
//        unregisterReceiver(mBR);
    }

    private void initView() {
//        tvTitle = (TextView) findViewById(R.id.tv_perfor);
//        btnStart = (Button) findViewById(R.id.btn_perfor_start);
//        btnStop = (Button) findViewById(R.id.btn_perfor_stop);
//        btnStart.setOnClickListener(this);
//        btnStop.setOnClickListener(this);
//        btnStartQ = (Button) findViewById(R.id.btn_perfor_startQ);
//        btnStopQ = (Button) findViewById(R.id.btn_perfor_stopQ);
//        btnStartQ.setOnClickListener(this);
//        btnStopQ.setOnClickListener(this);
//        startTime = 0;
//        midTime = 0;
//        endTime = 0;
//        tvTwo = (TextView) findViewById(R.id.tv_perfor_two);
//        tvThree = (TextView) findViewById(R.id.tv_perfor_three);
//        tvFour = (TextView) findViewById(R.id.tv_perfor_four);
//        tvFive = (TextView) findViewById(R.id.tv_perfor_five);
//        tvSix = (TextView) findViewById(R.id.tv_perfor_six);
//        distance = 0;
//        disShaChe = 0;


    }


    @Override
    public void onClick(View view) {

//        switch (view.getId()) {
//            case R.id.btn_perfor_start:
//                SPUtil.put(OBDPerformanceActivity.this, "test", 14);
//                mBinder.setIsFinishLog(false);
//                mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
//                //点击开始记录这个时间的
//                startTime = System.currentTimeMillis();
//                break;
//            case R.id.btn_perfor_stop:
//                mBinder.setIsFinishLog(true);
//                i = 0;
//                isyibai = false;
//                isyibaidis = false;
//                isshache = false;
//                isStartyibai = false;
//                tvThree.setText("百公里加速的时间:");
//                tvFive.setText("到100m的时间:");
//                break;
//        }



    }


}
