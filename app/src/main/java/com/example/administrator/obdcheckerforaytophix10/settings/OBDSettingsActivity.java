package com.example.administrator.obdcheckerforaytophix10.settings;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.administrator.obdcheckerforaytophix10.MainFregmentReplaceActivity;
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.performance.OBDPerformanceActivity;
import com.example.administrator.obdcheckerforaytophix10.tool.LogUtil;

public class OBDSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout re_preferences  , re_information , re_firmwareupdates;

    //横屏相关
    private MyOrientationEventListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏标题栏0
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_obdsettings);

        initView();


    }

    private void initView() {
        mListener = new MyOrientationEventListener(this);
        boolean autoRotateOn = (Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
        if (autoRotateOn) {
            mListener.enable();
        }
        re_preferences = (RelativeLayout) findViewById(R.id.re_settings_preferences);
        re_preferences.setOnClickListener(this);
        re_information = (RelativeLayout) findViewById(R.id.re_settings_information);
        re_information.setOnClickListener(this);
        re_firmwareupdates = (RelativeLayout) findViewById(R.id.re_settings_firmwareupdates);
        re_firmwareupdates.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.re_settings_preferences:
                Intent intent = new Intent(OBDSettingsActivity.this , MainFregmentReplaceActivity.class);
                intent.putExtra("intentKey" , 10);
                startActivity(intent);
                break;
            case R.id.re_settings_information:
                Intent intent_information = new Intent(OBDSettingsActivity.this , MainFregmentReplaceActivity.class);
                intent_information.putExtra("intentKey" , 12);
                startActivity(intent_information);
                break;
            case R.id.re_settings_firmwareupdates:
                Intent intent_firmware = new Intent(OBDSettingsActivity.this , MainFregmentReplaceActivity.class);
                intent_firmware.putExtra("intentKey" , 13);
                startActivity(intent_firmware);
                break;
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
