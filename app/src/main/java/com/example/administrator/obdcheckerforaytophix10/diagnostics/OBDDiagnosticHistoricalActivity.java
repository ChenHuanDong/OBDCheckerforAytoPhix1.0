package com.example.administrator.obdcheckerforaytophix10.diagnostics;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.WindowManager;

import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.diagnostics.historycodes.AdapterDiagnosticHistoryCode;
import com.example.administrator.obdcheckerforaytophix10.diagnostics.historycodes.BeanDiagnosticHistoryCodes;
import com.example.administrator.obdcheckerforaytophix10.tool.MyListView;

import java.util.ArrayList;

public class OBDDiagnosticHistoricalActivity extends AppCompatActivity {

    private MyListView lv;
    private AdapterDiagnosticHistoryCode myAdapter;
    private ArrayList<BeanDiagnosticHistoryCodes> data;


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
        setContentView(R.layout.activity_obddiagnostic_historical);

        initView();

        initData(0, "FORD", "2017-7-25", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(0, "FORD", "2017-7-25", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(0, "FORD", "2017-7-25", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", true);
        initData(1, "P0103", "O2 Sensor Circuit Low Vottage", false);


        myAdapter.setData(data);
        lv.setAdapter(myAdapter);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    private void initData(int type, String title, String content, boolean isRed) {
        BeanDiagnosticHistoryCodes bean = new BeanDiagnosticHistoryCodes();
        bean.setType(type).setTitle(title).setContent(content).setRed(isRed);
        data.add(bean);
    }

    private void initView() {
        orientation = getResources().getConfiguration().orientation;
        mListener = new MyOrientationEventListener(this);
        boolean autoRotateOn = (Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
        if (autoRotateOn) {
            mListener.enable();
        }
        lv = (MyListView) findViewById(R.id.mylv_diagnostic_history);
        myAdapter = new AdapterDiagnosticHistoryCode(this);
        data = new ArrayList<>();
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
