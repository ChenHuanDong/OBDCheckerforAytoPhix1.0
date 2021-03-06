package com.example.administrator.obdcheckerforaytophix10.montiors;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
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
import com.example.administrator.obdcheckerforaytophix10.R;
import com.example.administrator.obdcheckerforaytophix10.montiors.modenine.OBDMonitorsModeNineFragment;
import com.example.administrator.obdcheckerforaytophix10.montiors.modesix.OBDMonitorsModeSixFragment;
import com.example.administrator.obdcheckerforaytophix10.montiors.otwo.OBDMonitorsOTwoFragment;
import com.example.administrator.obdcheckerforaytophix10.montiors.test.OBDMonitorsTestFragment;

public class OBDMontiorsActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton radbtn_test, radbtn_otwo, radbtn_modesix, radbtn_modenine;
    private Fragment current_fragment, test_fragment, otwo_fragment, modesix_fragment, modenine_fragment;
    private ImageView iv_test, iv_otwo, iv_modesix, iv_modenine , iv_return;
    private TextView tv_test, tv_otwo, tv_modesix, tv_modenine;
    //右上角按钮
    private ImageView iv_other;

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

        setContentView(R.layout.activity_obdmontiors);

        initView();
        radbtn_test.performClick();


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    private void initView() {
        orientation = getResources().getConfiguration().orientation;
        mListener = new MyOrientationEventListener(this);
        boolean autoRotateOn = (Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1);
        if (autoRotateOn) {
            mListener.enable();
        }
        radbtn_test = (RadioButton) findViewById(R.id.radbtn_monitors_test);
        radbtn_otwo = (RadioButton) findViewById(R.id.radbtn_monitors_otwo);
        radbtn_modesix = (RadioButton) findViewById(R.id.radbtn_monitors_modesix);
        radbtn_modenine = (RadioButton) findViewById(R.id.radbtn_monitors_modenine);
        radbtn_test.setOnClickListener(this);
        radbtn_otwo.setOnClickListener(this);
        radbtn_modesix.setOnClickListener(this);
        radbtn_modenine.setOnClickListener(this);
        iv_test = (ImageView) findViewById(R.id.iv_monitors_test_bottom);
        tv_test = (TextView) findViewById(R.id.tv_monitors_test_bottom);
        iv_otwo = (ImageView) findViewById(R.id.iv_monitors_o_bottom);
        tv_otwo = (TextView) findViewById(R.id.tv_monitors_o_bottom);
        iv_modesix = (ImageView) findViewById(R.id.iv_monitors_modesix_bottom);
        tv_modesix = (TextView) findViewById(R.id.tv_monitors_modesix_bottom);
        iv_modenine = (ImageView) findViewById(R.id.iv_monitors_modenine_bottom);
        tv_modenine = (TextView) findViewById(R.id.tv_monitors_modenine_bottom);
        iv_other = (ImageView) findViewById(R.id.iv_monitors_main_other);
        iv_other.setOnClickListener(this);
        iv_return = (ImageView) findViewById(R.id.iv_monitors_main_return);
        iv_return.setOnClickListener(this);
        test_fragment = new OBDMonitorsTestFragment();
        otwo_fragment = new OBDMonitorsOTwoFragment();
        modesix_fragment = new OBDMonitorsModeSixFragment();
        modenine_fragment = new OBDMonitorsModeNineFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.radbtn_monitors_test:
                startFragmentAdd(test_fragment);
                iv_test.setImageResource(R.drawable.monitor_g);
                iv_otwo.setImageResource(R.drawable.o2_b);
                iv_modesix.setImageResource(R.drawable.fine_b);
                iv_modenine.setImageResource(R.drawable.model_b);
                tv_test.setTextColor(Color.parseColor("#fe9002"));
                tv_otwo.setTextColor(Color.parseColor("#c8c6c6"));
                tv_modesix.setTextColor(Color.parseColor("#c8c6c6"));
                tv_modenine.setTextColor(Color.parseColor("#c8c6c6"));
                break;
            case R.id.radbtn_monitors_otwo:
                startFragmentAdd(otwo_fragment);
                iv_test.setImageResource(R.drawable.monitor_b);
                iv_otwo.setImageResource(R.drawable.o2_g);
                iv_modesix.setImageResource(R.drawable.fine_b);
                iv_modenine.setImageResource(R.drawable.model_b);
                tv_test.setTextColor(Color.parseColor("#c8c6c6"));
                tv_otwo.setTextColor(Color.parseColor("#fe9002"));
                tv_modesix.setTextColor(Color.parseColor("#c8c6c6"));
                tv_modenine.setTextColor(Color.parseColor("#c8c6c6"));
                break;
            case R.id.radbtn_monitors_modesix:
                startFragmentAdd(modesix_fragment);
                iv_test.setImageResource(R.drawable.monitor_b);
                iv_otwo.setImageResource(R.drawable.o2_b);
                iv_modesix.setImageResource(R.drawable.fine_g);
                iv_modenine.setImageResource(R.drawable.model_b);
                tv_test.setTextColor(Color.parseColor("#c8c6c6"));
                tv_otwo.setTextColor(Color.parseColor("#c8c6c6"));
                tv_modesix.setTextColor(Color.parseColor("#fe9002"));
                tv_modenine.setTextColor(Color.parseColor("#c8c6c6"));
                break;
            case R.id.radbtn_monitors_modenine:
                startFragmentAdd(modenine_fragment);
                iv_test.setImageResource(R.drawable.monitor_b);
                iv_otwo.setImageResource(R.drawable.o2_b);
                iv_modesix.setImageResource(R.drawable.fine_b);
                iv_modenine.setImageResource(R.drawable.model_g);
                tv_test.setTextColor(Color.parseColor("#c8c6c6"));
                tv_otwo.setTextColor(Color.parseColor("#c8c6c6"));
                tv_modesix.setTextColor(Color.parseColor("#c8c6c6"));
                tv_modenine.setTextColor(Color.parseColor("#fe9002"));
                break;
            case R.id.iv_monitors_main_other:
                break;
            case R.id.iv_monitors_main_return:
                finish();
                break;
        }
    }


    private void startFragmentAdd(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (current_fragment == null) {
            fragmentTransaction.add(R.id.replace_monitors_main, fragment).commitAllowingStateLoss();
            current_fragment = fragment;
        }
        if (current_fragment != fragment) {
            // 先判断是否被add过
            if (!fragment.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                fragmentTransaction.hide(current_fragment).add(R.id.replace_monitors_main, fragment).commitAllowingStateLoss();
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
