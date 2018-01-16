package com.example.administrator.obdcheckerforaytophix10.performance;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.obdcheckerforaytophix10.MainActivity;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.dashboards.dashboardsview.DashboardsView;
import com.example.administrator.obdcheckerforaytophix10.main.obd.OBDDialogP;
import com.example.administrator.obdcheckerforaytophix10.main.obd.OBDPopDialog;
import com.example.administrator.obdcheckerforaytophix10.main.servierbt.BluetoothService;
import com.example.administrator.obdcheckerforaytophix10.performance.view.PerformanceView;
import com.example.administrator.obdcheckerforaytophix10.tool.ConversionUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.DBTool;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.ScreenUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.net.PasswordAuthentication;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

//这些屏蔽的都别删
public class OBDPerformanceActivity extends AppCompatActivity implements View.OnClickListener {

    //    private TextView tvTitle, tvTwo, tvThree, tvFour, tvFive, tvSix;
//    private Button btnStart, btnStop, btnStartQ, btnStopQ;
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

    private ServiceConnection mConnection;
    private BluetoothService.MyBinder mBinder;
    //接收测试的广播
    private BroadcastReceiver mBR;
    //最下面开始于结束按钮
    private ImageView ivFinish, ivOther;
    //这个是记录界面状态的，防止一按物理返回键  把所有Dialog退出了
    //  只有在 ！= 2 的时候 点击物理返回键会退出一级Dialog  初始是一，在退出二级的时候变成二（物理退出，按返回按钮退出）
    //  并且在一级物理监听判断后变成1
    private int pageState = 1;

    //计时器
    private Chronometer timer;
    //让计时器显示小时
    private static int xiaoshi = 0;
    //Start Report
    private Button btnStart, btnReport;
    private TextView tvSpeed, tvZhuan;

    private EditText etSpeedStart, etSpeedEnd, etBrake, etDistance;
    //这个是上面四个ET的内容
    private int startSpeed, endSpeed, brakeSpeed, totalDistance;
    //下面的都是测试的结果
    //         距离测试结果（时间） 刹车距离测试结果（距离） 速度测试结果（时间)刹车测试的时间,100m时间
    private float distanceTestResult, brakeTestResult, speedTestResult, brakeTime, oneTime;
    //最大速度
    private int maxSpeed;
    //判断是否开始测试
    //              是否开始刹车    是否开始距离   是否速度测试  是否一百米测试
    private boolean isBrakeTest, isDistance, isSpeedTest, isOneDis;
    private long startTime, midTime, endTime;
    //计数
    private int i = 0;
    //总的距离
    private float distance = 0f;
    //每段的距离
    private float perDistance = 0f;


    //横竖屏相关
    //横屏相关
    private MyOrientationEventListener mListener;
    private String brKey;
    private int brValue;

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

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mBinder = (BluetoothService.MyBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        mBR = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                brKey = intent.getStringExtra("key");
                brValue = intent.getIntExtra("data", 0);
                switch (brKey) {
                    case "车速":
                        tvSpeed.setText(brValue + "");
                        i++;
                        if (i == 1) {
                            endTime = System.currentTimeMillis() - startTime;
                            midTime = endTime;
                            //  * 0.27777 这个是变单位
                            distance = (float) ((endTime / 1000.f) * brValue * 0.2777f);
                            perDistance = distance;
                        } else if (i == 2) {
                            //midTime 就是这次的时间
                            midTime = System.currentTimeMillis() - endTime - startTime;
                            //记录这次时间下次用
                            endTime = System.currentTimeMillis();
                            distance = (float) ((midTime / 1000.f) * brValue * 0.2777f) + distance;
                            perDistance = (float) ((midTime / 1000.f) * brValue * 0.2777f);
                        } else {
                            midTime = System.currentTimeMillis() - endTime;
                            //记录这次时间下次用
                            endTime = System.currentTimeMillis();
                            distance = (float) ((midTime / 1000.f) * brValue * 0.2777f) + distance;
                            perDistance = (float) ((midTime / 1000.f) * brValue * 0.2777f);
                        }
                        //现在  startTime 是起始时间点   midTime是和上次间隔的时间   endTime是结束时间点
                        //要想获得总共的时间就endTime - startTime

                        //3距离测试
                        if (distance > totalDistance & isDistance) {
                            distanceTestResult = ((endTime - startTime) / 1000.f);
                            isDistance = false;
                        }

                        //2刹车测试
                        if (brValue <= brakeSpeed & isBrakeTest) {
                            brakeTestResult = brakeTestResult + perDistance;
                            brakeTime = brakeTime + midTime;
                            //如果速度到0  就停止叠加
                            if (brValue <= 0) {
                                isBrakeTest = false;
                            }
                        }

                        //1速度测试
                        if (brValue <= startSpeed & isSpeedTest) {
                            speedTestResult = speedTestResult + (midTime / 1000.f);
                            //如果速度到了设置的就停止
                            if (brValue >= endSpeed) {
                                isSpeedTest = false;
                            }
                        }


                        //0-100m时间
                        if (isOneDis & distance >= 100) {
                            oneTime = endTime - startTime;
                            isOneDis = false;
                        }

                        //最大速度
                        if (brValue > maxSpeed) {
                            maxSpeed = brValue;
                        }


                        break;
                    case "转速":
                        tvZhuan.setText(brValue + "");
                        break;
                }


            }


        };


        //绑定服务
        Intent intent = new Intent(OBDPerformanceActivity.this, BluetoothService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("bluetoothBT---performance");
        registerReceiver(mBR, intentFilter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        unregisterReceiver(mBR);
    }

    private void initView() {
        mListener = new MyOrientationEventListener(this);
        boolean autoRotateOn = (Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
        if (autoRotateOn) {
            mListener.enable();
        }
        ivFinish = (ImageView) findViewById(R.id.iv_finish_performance);
        ivFinish.setOnClickListener(this);
        btnStart = (Button) findViewById(R.id.btn_performance_start);
        btnStart.setOnClickListener(this);
        btnReport = (Button) findViewById(R.id.btn_performance_report);
        btnReport.setOnClickListener(this);
        timer = (Chronometer) findViewById(R.id.performance_chronometer);
        //时间监听
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                xiaoshi = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60 / 60);
                chronometer.setFormat("0" + String.valueOf(xiaoshi) + ":%s");
            }
        });
        tvSpeed = (TextView) findViewById(R.id.tv_perfoemance_speed);
        tvZhuan = (TextView) findViewById(R.id.tv_perfoemance_zhuan);
        etSpeedStart = (EditText) findViewById(R.id.et_speedtest_start);
        etSpeedEnd = (EditText) findViewById(R.id.et_speedtest_end);
        etBrake = (EditText) findViewById(R.id.et_braketest);
        etDistance = (EditText) findViewById(R.id.et_distance);
        brakeTestResult = 0f;
        distanceTestResult = 0f;
        speedTestResult = 0f;
        isBrakeTest = false;
        isDistance = false;
        isSpeedTest = false;
        startSpeed = 0;
        endSpeed = 100;
        brakeSpeed = 100;
        totalDistance = 100;
        brakeTime = 0f;
        oneTime = 0f;
        isOneDis = false;
        maxSpeed = 0;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_performance_start:
                if (etSpeedStart.getText().toString().equals("")) {
                    etSpeedStart.setText("0");
                    startSpeed = Integer.parseInt(etSpeedStart.getText().toString());
                }
                if (etSpeedEnd.getText().toString().equals("")) {
                    etSpeedEnd.setText("0");
                    endSpeed = Integer.parseInt(etSpeedEnd.getText().toString());
                }
                if (etBrake.getText().toString().equals("")) {
                    etBrake.setText("0");
                    brakeSpeed = Integer.parseInt(etBrake.getText().toString());
                }
                if (etDistance.getText().toString().equals("")) {
                    etDistance.setText("0");
                    totalDistance = Integer.parseInt(etDistance.getText().toString());
                }
                //需要先判断是否连接上产品   如果没连接线连接
                if (mBinder.getConnect()) {
                    //这个是已经连接了
                    //开始之前还需要做一些判断  就是关于输入的那些设置的判断
                    if (startSpeed >= endSpeed) {
                        Toast.makeText(OBDPerformanceActivity.this, getResources().getString(R.string.endspeedmustmorethanspeed), Toast.LENGTH_SHORT).show();
                    } else {

                        timer.setBase(SystemClock.elapsedRealtime());
                        //开始计时
                        timer.start();
                        SPUtil.put(OBDPerformanceActivity.this, "test", 14);
                        mBinder.setIsFinishLog(false);
                        mBinder.writeData(new byte[]{0x30, 0x31, 0x30, 0x64, (byte) 0x0D});
                        //初始化开始时间
                        startTime = System.currentTimeMillis();
                        i = 0;

                        //初始化  那些判断
                        isDistance = true;
                        isBrakeTest = true;
                        isSpeedTest = true;
                        isOneDis = true;
                        //初始化  结果
                        distanceTestResult = 0f;
                        brakeTestResult = 0f;
                        speedTestResult = 0f;
                        brakeTime = 0f;
                        oneTime = 0f;
                        maxSpeed = 0;
                    }

                } else {
                    Toast.makeText(OBDPerformanceActivity.this, getResources().getString(R.string.pleaseconnectdevicefirst), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.iv_finish_performance:
                mBinder.setIsFinishLog(true);
                finish();
                break;
            case R.id.btn_performance_report:
                //点击的时候把系统时间记录下来，然后在这里算一下，放到Time里面吧
                //这里就是停止   把最终得到的数据展示
                mBinder.setIsFinishLog(true);
                timer.stop();

                OBDDialogP dialog = new OBDDialogP(OBDPerformanceActivity.this);
                View viewDialog = LayoutInflater.from(OBDPerformanceActivity.this).inflate(R.layout.dialog_perfoemance_other, null);
                //加速度的Tv
                TextView tvAccleTitle = viewDialog.findViewById(R.id.tv_per_accle_title);
                tvAccleTitle.setText(startSpeed + "-" + endSpeed + " km/h");
                //加速度的结果
                TextView tvAccleResult = viewDialog.findViewById(R.id.tv_per_accle_result);
                tvAccleResult.setText(distanceTestResult + "s");
                //刹车距离
                TextView tvBrakeDis = viewDialog.findViewById(R.id.tv_per_brake_dis);
                tvBrakeDis.setText(brakeTestResult + "m");
                //刹车时间
                TextView tvBrakeTime = viewDialog.findViewById(R.id.tv_per_brake_time);
                tvBrakeTime.setText(brakeTime + "s");
                //最大速度
                TextView tvmaxSpeed = viewDialog.findViewById(R.id.tv_per_brake_maxSpeed);
                tvmaxSpeed.setText(maxSpeed + " km/h");
                //上面标题的最大速度
                TextView tvTitleMaxSpeed = viewDialog.findViewById(R.id.tv_per_title_maxSpeed);
                tvTitleMaxSpeed.setText(maxSpeed + " km/h");
                //0-100m  时间
                TextView tvZeroToOne = viewDialog.findViewById(R.id.tv_per_zerotoone_time);
                tvZeroToOne.setText(oneTime + "s");
                //标题展示的时间
                TextView tvTitleTime = viewDialog.findViewById(R.id.tv_per_title_timer);
                tvTitleTime.setText(timer.getText()+"");


                //点击物理返回键退出dialog
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                            dialog.dismiss();
                        }
                        return false;
                    }
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(viewDialog);
                dialog.show();
                setPromptWin(dialog);
                break;
        }


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


    private void setPromptWin(OBDDialogP dia) {
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dia.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dia.getWindow().setAttributes(lp);
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
