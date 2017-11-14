package com.example.administrator.obdcheckerforaytophix10.diagnostics;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.diagnostics.freezeframe.AdapterDiagnosticFreeze;
import com.example.administrator.obdcheckerforaytophix10.diagnostics.freezeframe.BeanDiagnosticFreezeFrame;
import com.example.administrator.obdcheckerforaytophix10.diagnostics.readinesstest.AdapterReadinessCommon;
import com.example.administrator.obdcheckerforaytophix10.diagnostics.troublecode.AdapterDiagnoticsTrouble;
import com.example.administrator.obdcheckerforaytophix10.diagnostics.troublecode.BeanDiagnoticsTroubleCode;
import com.example.administrator.obdcheckerforaytophix10.main.servierbt.BluetoothService;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;
import com.example.administrator.obdcheckerforaytophix10.tool.MyListView;
import com.example.administrator.obdcheckerforaytophix10.tool.SPUtil;

import java.util.ArrayList;

public class OBDDiagnosticsActivity extends AppCompatActivity implements View.OnClickListener {

    //下面四个可切换的Re  中间四个设置隐藏显示  Iv Te 是图标
    private RelativeLayout re_trouble_code, re_freeze_frame, re_readiness_test, re_report;
    private ImageView iv_trouble_code, iv_freeze_frame, iv_readiness_test, iv_report;
    private TextView tv_trouble_code, tv_freeze_frame, tv_readiness_test, tv_report;
    private LinearLayout ll_trouble_code, ll_freeze_frame, ll_readiness_test, ll_report;
    //Trouble  Code
    private ListView lv_trouble_code;
    private ArrayList<BeanDiagnoticsTroubleCode> data_trouble_code;
    private AdapterDiagnoticsTrouble myAdapterTroubleCode;
    private TextView tv_progress;
    private View view_troublecode_head;
    //Readiness Test
    private MyListView lv_readiness_complete, lv_readiness_undfinished, lv_readiness_notsupport;
    private AdapterReadinessCommon myAdapterReadinessComplete, myAdapterReadinessUndfinished, myAdapterReadinessNotSupport;
    private ArrayList<String> data_readiness_complete, data_readiness_undfinished, data_readiness_notsupport;
    //Freeze  Frame
    private ListView lv_freeze_frame;
    private AdapterDiagnosticFreeze myAdapterFreezeFrame;
    private ArrayList<BeanDiagnosticFreezeFrame> dataFreezeFrame;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                tv_progress.setText(((int) (msg.obj) * 10 + 10) + "%");
            }

        }
    };

    //接收故障码的广播
    private BroadcastReceiver mBR;

    //用 int型代表四种 的状态
    private int stateDiagnoic = 1;
    //右上角的刷新按钮
    private ImageView iv_flash;


    private ServiceConnection mConnection;
    private BluetoothService.MyBinder mBinder;
//    private BeanDiagnoticsTroubleCode mBeanTroubleCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏0
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_obddiagnostics);

        initView();
        re_trouble_code.performClick();

        //  Trouble Code 初始化
        MyTroubleCode();
        //  Readiness Test 初始化
        MyReadinessTest();
        //Freeze  Frame
        MyFreezeFrame();


        //初始化服务
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mBinder = (BluetoothService.MyBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        //绑定服务
        Intent intent1 = new Intent(OBDDiagnosticsActivity.this, BluetoothService.class);
        bindService(intent1, mConnection, BIND_AUTO_CREATE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    //这里要先获取是否连接然后再发送
                    SPUtil.put(OBDDiagnosticsActivity.this, "test", 8);
                    mBinder.writeData(new byte[]{0x30, 0x33, (byte) 0x0D});
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        mBR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getIntExtra("state" , 0)){
                    case 1:
                        data_trouble_code.add(new BeanDiagnoticsTroubleCode(intent.getStringExtra("key") + "", "-----", intent.getBooleanExtra("red", true)));
                        myAdapterTroubleCode.setData(data_trouble_code);
                        break;
                    case 2:
                        Toast.makeText(OBDDiagnosticsActivity.this , "清除故障码成功" , Toast.LENGTH_SHORT).show();
                        break;
                }


            }
        };


        //注册接收故障码的广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("bluetoothBT---errorcode");
        registerReceiver(mBR, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        unregisterReceiver(mBR);
    }

    private void MyFreezeFrame() {
        //lv_freeze_frame   myAdapterFreezeFrame    dataFreezeFrame
        BeanDiagnosticFreezeFrame bean = new BeanDiagnosticFreezeFrame();
        for (int i = 0; i < 30; i++) {
            bean.setUpData("Number of trouble code, MIL indicator on...").setBottomData("MIL on,# Trouble codes4,Misfire: Available=False");
            dataFreezeFrame.add(bean);
        }
        myAdapterFreezeFrame.setData(dataFreezeFrame);
        lv_freeze_frame.setAdapter(myAdapterFreezeFrame);
        View view = LayoutInflater.from(this).inflate(R.layout.lvhead_diagnostic_freeze_frame, null);
        lv_freeze_frame.addHeaderView(view);

    }

    private void MyReadinessTest() {
//lv_readiness_complete  myAdapterReadinessComplete   data_readiness_complete
        data_readiness_complete.add("NMHC Catalyst Monitor");
        myAdapterReadinessComplete.setData(data_readiness_complete);
        lv_readiness_complete.setAdapter(myAdapterReadinessComplete);
        View view = LayoutInflater.from(this).inflate(R.layout.lvhead_diagnostic_readiness_complete, null);
        lv_readiness_complete.addHeaderView(view);
//lv_readiness_undfinished  myAdapterReadinessUndfinished   data_readiness_undfinished
        data_readiness_undfinished.add("Heated Catalyst Monitor");
        myAdapterReadinessUndfinished.setData(data_readiness_undfinished);
        lv_readiness_undfinished.setAdapter(myAdapterReadinessUndfinished);
//lv_readiness_notsupport   myAdapterReadinessNotSupport  data_readiness_notsupport
        for (int i = 0; i < 30; i++) {
            data_readiness_notsupport.add("Misfire minitor");
        }
        myAdapterReadinessNotSupport.setData(data_readiness_notsupport);
        lv_readiness_notsupport.setAdapter(myAdapterReadinessNotSupport);


    }

    private void MyTroubleCode() {
//        mBeanTroubleCode = new BeanDiagnoticsTroubleCode();
        //这个是添加的假数据现在不用了
//        for (int i = 0; i < 50; i++) {
//            bean.setTitle("P0103").setItem("02---" + (i + 1)).setRed(true);
//            data_trouble_code.add(bean);
//        }
        myAdapterTroubleCode.setData(data_trouble_code);
        lv_trouble_code.setAdapter(myAdapterTroubleCode);

        final ProgressBar pb = view_troublecode_head.findViewById(R.id.pb_diagnostic_troublecode);

        //添加加的数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pb.setProgress(i * 10 + 10);
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = i;
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
        //为底部橘色圆环添加动画
        ImageView iv_troublecode_wait = view_troublecode_head.findViewById(R.id.iv_diagno_trouble_wait);
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_troublecode_wait, "rotation", 0, 360);
        animator.setDuration(5000);
        animator.setRepeatCount(-1);
        animator.start();
        lv_trouble_code.addHeaderView(view_troublecode_head);
        View view_troublecode_foot = LayoutInflater.from(this).inflate(R.layout.lvfoot_diagnostic_tiouble_code, null);
        Button btn_clearcode = view_troublecode_foot.findViewById(R.id.btn_diagnostic_clearcode);
        Button btn_historical = view_troublecode_foot.findViewById(R.id.btn_diagnostic_historical);
        btn_clearcode.setOnClickListener(this);
        btn_historical.setOnClickListener(this);
        lv_trouble_code.addFooterView(view_troublecode_foot);


    }

    private void initView() {
        re_trouble_code = (RelativeLayout) findViewById(R.id.re_diagnostic_trouble_code);
        re_freeze_frame = (RelativeLayout) findViewById(R.id.re_diagnostic_freeze_frame);
        re_readiness_test = (RelativeLayout) findViewById(R.id.re_diagnostic_readiness_test);
        re_report = (RelativeLayout) findViewById(R.id.re_diagnostic_report);
        re_trouble_code.setOnClickListener(this);
        re_freeze_frame.setOnClickListener(this);
        re_readiness_test.setOnClickListener(this);
        re_report.setOnClickListener(this);
        iv_trouble_code = (ImageView) findViewById(R.id.iv_diagnostic_trouble_code);
        iv_freeze_frame = (ImageView) findViewById(R.id.iv_diagnostic_freeze_frame);
        iv_readiness_test = (ImageView) findViewById(R.id.iv_diagnostic_readiness_test);
        iv_report = (ImageView) findViewById(R.id.iv_diagnostic_report);
        tv_trouble_code = (TextView) findViewById(R.id.tv_diagnostic_trouble_code);
        tv_freeze_frame = (TextView) findViewById(R.id.tv_diagnostic_freeze_frame);
        tv_readiness_test = (TextView) findViewById(R.id.tv_diagnostic_readiness_test);
        tv_report = (TextView) findViewById(R.id.tv_diagnostic_report);
        ll_trouble_code = (LinearLayout) findViewById(R.id.ll_mid_trouble_code);
        ll_freeze_frame = (LinearLayout) findViewById(R.id.ll_mid_freeze_frame);
        ll_readiness_test = (LinearLayout) findViewById(R.id.ll_mid_readiness_rest);
        ll_report = (LinearLayout) findViewById(R.id.ll_mid_report);
        //Trouble  Code
        lv_trouble_code = (ListView) findViewById(R.id.lv_diagnostic_trouble_code);
        myAdapterTroubleCode = new AdapterDiagnoticsTrouble(this);
        data_trouble_code = new ArrayList<>();
        view_troublecode_head = LayoutInflater.from(this).inflate(R.layout.lvhead_diagnostic_tiouble_code, null);
        tv_progress = view_troublecode_head.findViewById(R.id.tv_diagnostic_trouble_code_progressbar);

        //Readiness Test
        lv_readiness_complete = (MyListView) findViewById(R.id.mylv_complete);
        myAdapterReadinessComplete = new AdapterReadinessCommon(this);
        data_readiness_complete = new ArrayList<>();
        lv_readiness_undfinished = (MyListView) findViewById(R.id.mylv_undfinished);
        myAdapterReadinessUndfinished = new AdapterReadinessCommon(this);
        data_readiness_undfinished = new ArrayList<>();
        lv_readiness_notsupport = (MyListView) findViewById(R.id.mylv_notsupport);
        myAdapterReadinessNotSupport = new AdapterReadinessCommon(this);
        data_readiness_notsupport = new ArrayList<>();
        //Freeze  Frame
        lv_freeze_frame = (ListView) findViewById(R.id.lv_diagnostic_freeze_frame);
        myAdapterFreezeFrame = new AdapterDiagnosticFreeze(this);
        dataFreezeFrame = new ArrayList<>();

        //右上角刷新按钮
        iv_flash = (ImageView) findViewById(R.id.iv_diagno_flash);
        iv_flash.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.re_diagnostic_trouble_code:
                stateDiagnoic = 1;
                ll_trouble_code.setVisibility(View.VISIBLE);
                ll_freeze_frame.setVisibility(View.GONE);
                ll_readiness_test.setVisibility(View.GONE);
                ll_report.setVisibility(View.GONE);
                iv_trouble_code.setImageResource(R.drawable.guzhang_y);
                iv_freeze_frame.setImageResource(R.drawable.freeze_b);
                iv_readiness_test.setImageResource(R.drawable.readiness_b);
                iv_report.setImageResource(R.drawable.report_balck);
                tv_trouble_code.setTextColor(getResources().getColor(R.color.colorDashboardsPointer));
                tv_freeze_frame.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_readiness_test.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_report.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                break;
            case R.id.re_diagnostic_freeze_frame:
                stateDiagnoic = 2;
                ll_trouble_code.setVisibility(View.GONE);
                ll_freeze_frame.setVisibility(View.VISIBLE);
                ll_readiness_test.setVisibility(View.GONE);
                ll_report.setVisibility(View.GONE);
                iv_trouble_code.setImageResource(R.drawable.guzhang_b);
                iv_freeze_frame.setImageResource(R.drawable.freeze_y);
                iv_readiness_test.setImageResource(R.drawable.readiness_b);
                iv_report.setImageResource(R.drawable.report_balck);
                tv_trouble_code.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_freeze_frame.setTextColor(getResources().getColor(R.color.colorDashboardsPointer));
                tv_readiness_test.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_report.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                break;
            case R.id.re_diagnostic_readiness_test:
                stateDiagnoic = 3;
                ll_trouble_code.setVisibility(View.GONE);
                ll_freeze_frame.setVisibility(View.GONE);
                ll_readiness_test.setVisibility(View.VISIBLE);
                ll_report.setVisibility(View.GONE);
                iv_trouble_code.setImageResource(R.drawable.guzhang_b);
                iv_freeze_frame.setImageResource(R.drawable.freeze_b);
                iv_readiness_test.setImageResource(R.drawable.readiness_y);
                iv_report.setImageResource(R.drawable.report_balck);
                tv_trouble_code.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_freeze_frame.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_readiness_test.setTextColor(getResources().getColor(R.color.colorDashboardsPointer));
                tv_report.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                break;
            case R.id.re_diagnostic_report:
                stateDiagnoic = 4;
                ll_trouble_code.setVisibility(View.GONE);
                ll_freeze_frame.setVisibility(View.GONE);
                ll_readiness_test.setVisibility(View.GONE);
                ll_report.setVisibility(View.VISIBLE);
                iv_trouble_code.setImageResource(R.drawable.guzhang_b);
                iv_freeze_frame.setImageResource(R.drawable.freeze_b);
                iv_readiness_test.setImageResource(R.drawable.readiness_b);
                iv_report.setImageResource(R.drawable.report_y);
                tv_trouble_code.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_freeze_frame.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_readiness_test.setTextColor(getResources().getColor(R.color.colorTextColorDemo));
                tv_report.setTextColor(getResources().getColor(R.color.colorDashboardsPointer));
                break;
            case R.id.btn_diagnostic_historical:
                Intent intent = new Intent(OBDDiagnosticsActivity.this, OBDDiagnosticHistoricalActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_diagnostic_clearcode:
                //清除故障码
                SPUtil.put(OBDDiagnosticsActivity.this, "test", 12);
                mBinder.writeData(new byte[]{0x30, 0x34, (byte) 0x0D});
                break;
            case R.id.iv_diagno_flash:
                switch (stateDiagnoic){
                    case 1:
                        data_trouble_code.clear();
                        myAdapterTroubleCode.setData(data_trouble_code);
                        SPUtil.put(OBDDiagnosticsActivity.this, "test", 8);
                        mBinder.writeData(new byte[]{0x30, 0x33, (byte) 0x0D});
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
                break;

        }
    }
}
